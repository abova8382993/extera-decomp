package org.telegram.p035ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearSmoothScrollerCustom;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.Premium.boosts.BoostRepository;
import org.telegram.p035ui.Components.Premium.boosts.adapters.SelectorAdapter;
import org.telegram.p035ui.Components.Premium.boosts.cells.selector.SelectorBtnCell;
import org.telegram.p035ui.Components.Premium.boosts.cells.selector.SelectorHeaderCell;
import org.telegram.p035ui.Components.Premium.boosts.cells.selector.SelectorSearchCell;
import org.telegram.p035ui.Components.Premium.boosts.cells.selector.SelectorUserCell;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes6.dex */
public class MultiContactsSelectorBottomSheet extends BottomSheetWithRecyclerListView {
    private static MultiContactsSelectorBottomSheet instance;
    private final ButtonWithCounterView actionButton;
    private final HashMap<Long, TLRPC.User> allSelectedObjects;
    private final SelectorBtnCell buttonContainer;
    private final List<TLRPC.TL_contact> contacts;
    private final List<String> contactsLetters;
    private final Map<String, List<TLRPC.TL_contact>> contactsMap;
    private Boolean filterBots;
    private Boolean filterPremium;
    private final List<TLRPC.User> foundUsers;
    private final SelectorHeaderCell headerView;
    private final List<TLRPC.TL_topPeer> hints;
    private final ArrayList<SelectorAdapter.Item> items;
    private int lastRequestId;
    private int listPaddingTop;
    private int maxCount;
    private final ArrayList<SelectorAdapter.Item> oldItems;
    private String query;
    private float recipientsBtnExtraSpace;
    private ReplacementSpan recipientsBtnSpaceSpan;
    private final Runnable remoteSearchRunnable;
    private final SelectorSearchCell searchField;
    private final View sectionCell;
    private final HashSet<Long> selectedIds;
    private SelectorAdapter selectorAdapter;
    private SelectorListener selectorListener;

    public interface SelectorListener {
        void onUserSelected(List<Long> list);
    }

    public static MultiContactsSelectorBottomSheet open(Boolean bool, Boolean bool2, int i, SelectorListener selectorListener) {
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment == null) {
            return null;
        }
        MultiContactsSelectorBottomSheet multiContactsSelectorBottomSheet = instance;
        if (multiContactsSelectorBottomSheet != null) {
            return multiContactsSelectorBottomSheet;
        }
        MultiContactsSelectorBottomSheet multiContactsSelectorBottomSheet2 = new MultiContactsSelectorBottomSheet(lastFragment, true, i, bool, bool2, selectorListener);
        multiContactsSelectorBottomSheet2.show();
        instance = multiContactsSelectorBottomSheet2;
        return multiContactsSelectorBottomSheet2;
    }

    private boolean filter(TLRPC.User user) {
        if (user == null) {
            return false;
        }
        if (this.filterBots != null && UserObject.isBot(user) != this.filterBots.booleanValue()) {
            return false;
        }
        Boolean bool = this.filterPremium;
        return bool == null || user.premium == bool.booleanValue();
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$1 */
    public class RunnableC61301 implements Runnable {
        public RunnableC61301() {
        }

        @Override // java.lang.Runnable
        public void run() {
            String str = MultiContactsSelectorBottomSheet.this.query;
            if (str != null) {
                MultiContactsSelectorBottomSheet.this.loadData(str);
            }
        }
    }

    public void loadData(final String str) {
        if (this.lastRequestId >= 0) {
            ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.lastRequestId, true);
            this.lastRequestId = -1;
        }
        Boolean bool = this.filterBots;
        BoostRepository.searchContactsLocally(str, bool != null && bool.booleanValue(), new Utilities.Callback() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda7
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$loadData$1(str, (List) obj);
            }
        });
    }

    public /* synthetic */ void lambda$loadData$1(String str, List list) {
        final HashSet hashSet = new HashSet();
        this.foundUsers.clear();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                TLRPC.User user = (TLRPC.User) it.next();
                if (user != null && !hashSet.contains(Long.valueOf(user.f1407id)) && filter(user)) {
                    this.foundUsers.add(user);
                    hashSet.add(Long.valueOf(user.f1407id));
                }
            }
        }
        Boolean bool = this.filterBots;
        if (bool != null && bool.booleanValue()) {
            this.lastRequestId = BoostRepository.searchContacts(str, true, new Utilities.Callback() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda8
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$loadData$0(hashSet, (List) obj);
                }
            });
        } else {
            updateList(true, true);
        }
    }

    public /* synthetic */ void lambda$loadData$0(HashSet hashSet, List list) {
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                TLRPC.User user = (TLRPC.User) it.next();
                if (user != null && !hashSet.contains(Long.valueOf(user.f1407id)) && filter(user)) {
                    this.foundUsers.add(user);
                    hashSet.add(Long.valueOf(user.f1407id));
                }
            }
        }
        updateList(true, true);
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$2 */
    public class C61312 extends ReplacementSpan {
        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        }

        public C61312() {
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return (int) MultiContactsSelectorBottomSheet.this.recipientsBtnExtraSpace;
        }
    }

    public void createRecipientsBtnSpaceSpan() {
        this.recipientsBtnSpaceSpan = new ReplacementSpan() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.2
            @Override // android.text.style.ReplacementSpan
            public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            }

            public C61312() {
            }

            @Override // android.text.style.ReplacementSpan
            public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
                return (int) MultiContactsSelectorBottomSheet.this.recipientsBtnExtraSpace;
            }
        };
    }

    public MultiContactsSelectorBottomSheet(BaseFragment baseFragment, boolean z, final int i, Boolean bool, Boolean bool2, SelectorListener selectorListener) {
        super(baseFragment, z, false, false, baseFragment.getResourceProvider());
        this.oldItems = new ArrayList<>();
        ArrayList<SelectorAdapter.Item> arrayList = new ArrayList<>();
        this.items = arrayList;
        HashSet<Long> hashSet = new HashSet<>();
        this.selectedIds = hashSet;
        ArrayList arrayList2 = new ArrayList();
        this.contacts = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        this.hints = arrayList3;
        this.foundUsers = new ArrayList();
        HashMap map = new HashMap();
        this.contactsMap = map;
        ArrayList arrayList4 = new ArrayList();
        this.contactsLetters = arrayList4;
        this.allSelectedObjects = new LinkedHashMap();
        this.listPaddingTop = AndroidUtilities.m1036dp(120.0f);
        this.lastRequestId = -1;
        this.remoteSearchRunnable = new Runnable() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.1
            public RunnableC61301() {
            }

            @Override // java.lang.Runnable
            public void run() {
                String str = MultiContactsSelectorBottomSheet.this.query;
                if (str != null) {
                    MultiContactsSelectorBottomSheet.this.loadData(str);
                }
            }
        };
        this.maxCount = i;
        this.filterBots = bool;
        this.filterPremium = bool2;
        this.selectorListener = selectorListener;
        this.actionBar.setTitle(getTitle());
        C61323 c61323 = new SelectorHeaderCell(getContext(), this.resourcesProvider) { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.3
            public C61323(Context context, Theme.ResourcesProvider resourcesProvider) {
                super(context, resourcesProvider);
            }

            @Override // org.telegram.p035ui.Components.Premium.boosts.cells.selector.SelectorHeaderCell
            public int getHeaderHeight() {
                if (getResources().getConfiguration().orientation == 2) {
                    return AndroidUtilities.m1036dp(48.0f);
                }
                return AndroidUtilities.m1036dp(54.0f);
            }
        };
        this.headerView = c61323;
        c61323.setOnCloseClickListener(new Runnable() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
        c61323.setText(getTitle());
        c61323.setCloseImageVisible(false);
        c61323.backDrawable.setRotation(0.0f, false);
        createRecipientsBtnSpaceSpan();
        C61334 c61334 = new SelectorSearchCell(getContext(), this.resourcesProvider, null) { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.4
            private boolean isKeyboardVisible;

            public C61334(Context context, Theme.ResourcesProvider resourcesProvider, Runnable runnable) {
                super(context, resourcesProvider, runnable);
            }

            @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
                super.onLayout(z2, i2, i3, i4, i5);
                MultiContactsSelectorBottomSheet.this.listPaddingTop = getMeasuredHeight() + AndroidUtilities.m1036dp(64.0f);
                MultiContactsSelectorBottomSheet.this.selectorAdapter.notifyChangedLast();
                if (this.isKeyboardVisible != MultiContactsSelectorBottomSheet.this.isKeyboardVisible()) {
                    boolean zIsKeyboardVisible = MultiContactsSelectorBottomSheet.this.isKeyboardVisible();
                    this.isKeyboardVisible = zIsKeyboardVisible;
                    if (zIsKeyboardVisible) {
                        MultiContactsSelectorBottomSheet.this.scrollToTop(true);
                    }
                }
            }
        };
        this.searchField = c61334;
        int i2 = Theme.key_dialogBackground;
        c61334.setBackgroundColor(getThemedColor(i2));
        c61334.setOnSearchTextChange(new Utilities.Callback() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.onSearch((String) obj);
            }
        });
        c61334.setHintText(LocaleController.getString(C2797R.string.Search), false);
        C61345 c61345 = new View(getContext()) { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.5
            public C61345(Context context) {
                super(context);
            }

            @Override // android.view.View
            public void onDraw(Canvas canvas) {
                canvas.drawColor(MultiContactsSelectorBottomSheet.this.getThemedColor(Theme.key_graySection));
            }
        };
        this.sectionCell = c61345;
        ViewGroup viewGroup = this.containerView;
        int i3 = this.backgroundPaddingLeft;
        viewGroup.addView(c61323, 0, LayoutHelper.createFrameMarginPx(-1, -2.0f, 55, i3, 0, i3, 0));
        ViewGroup viewGroup2 = this.containerView;
        int i4 = this.backgroundPaddingLeft;
        viewGroup2.addView(c61334, LayoutHelper.createFrameMarginPx(-1, -2.0f, 55, i4, 0, i4, 0));
        ViewGroup viewGroup3 = this.containerView;
        int i5 = this.backgroundPaddingLeft;
        viewGroup3.addView(c61345, LayoutHelper.createFrameMarginPx(-1, 1.0f, 55, i5, 0, i5, 0));
        SelectorBtnCell selectorBtnCell = new SelectorBtnCell(getContext(), this.resourcesProvider, null);
        this.buttonContainer = selectorBtnCell;
        selectorBtnCell.setClickable(true);
        selectorBtnCell.setOrientation(1);
        selectorBtnCell.setPadding(AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(10.0f));
        selectorBtnCell.setBackgroundColor(Theme.getColor(i2, this.resourcesProvider));
        C61356 c61356 = new ButtonWithCounterView(getContext(), this.resourcesProvider) { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.6
            public C61356(Context context, Theme.ResourcesProvider resourcesProvider) {
                super(context, resourcesProvider);
            }

            @Override // org.telegram.p035ui.Stories.recorder.ButtonWithCounterView
            public float calculateCounterWidth(float f, float f2) {
                boolean z2 = MultiContactsSelectorBottomSheet.this.recipientsBtnExtraSpace == 0.0f;
                MultiContactsSelectorBottomSheet.this.recipientsBtnExtraSpace = f;
                if (z2) {
                    MultiContactsSelectorBottomSheet.this.createRecipientsBtnSpaceSpan();
                    MultiContactsSelectorBottomSheet.this.updateActionButton(false);
                }
                return f;
            }
        };
        this.actionButton = c61356;
        c61356.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$2(view);
            }
        });
        selectorBtnCell.addView(c61356, LayoutHelper.createLinear(-1, 48, 87));
        ViewGroup viewGroup4 = this.containerView;
        int i6 = this.backgroundPaddingLeft;
        viewGroup4.addView(selectorBtnCell, LayoutHelper.createFrameMarginPx(-1, -2.0f, 87, i6, 0, i6, 0));
        this.selectorAdapter.setData(arrayList, this.recyclerListView);
        RecyclerListView recyclerListView = this.recyclerListView;
        int i7 = this.backgroundPaddingLeft;
        recyclerListView.setPadding(i7, 0, i7, AndroidUtilities.m1036dp(60.0f));
        this.recyclerListView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.7
            public C61367() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i8) {
                if (i8 == 1) {
                    AndroidUtilities.hideKeyboard(MultiContactsSelectorBottomSheet.this.searchField.getEditText());
                }
            }
        });
        this.recyclerListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i8, float f, float f2) {
                this.f$0.lambda$new$4(i, view, i8, f, f2);
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setDurations(350L);
        defaultItemAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        defaultItemAnimator.setDelayAnimations(false);
        defaultItemAnimator.setSupportsChangeAnimations(false);
        this.recyclerListView.setItemAnimator(defaultItemAnimator);
        this.recyclerListView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet.8
            public C61378() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                if (recyclerView.getChildAdapterPosition(view) == MultiContactsSelectorBottomSheet.this.items.size()) {
                    rect.bottom = MultiContactsSelectorBottomSheet.this.listPaddingTop;
                }
            }
        });
        c61334.setText(_UrlKt.FRAGMENT_ENCODE_SET);
        c61334.spansContainer.removeAllSpans(false);
        c61334.updateSpans(false, hashSet, new Runnable() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$5();
            }
        }, null);
        c61323.setText(getTitle());
        updateActionButton(false);
        arrayList2.addAll(ContactsController.getInstance(this.currentAccount).contacts);
        map.putAll(ContactsController.getInstance(this.currentAccount).usersSectionsDict);
        arrayList4.addAll(ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray);
        arrayList3.addAll(MediaDataController.getInstance(this.currentAccount).hints);
        Boolean bool3 = this.filterBots;
        if (bool3 != null && bool3.booleanValue()) {
            arrayList3.addAll(MediaDataController.getInstance(this.currentAccount).webapps);
        }
        updateList(false, true);
        fixNavigationBar();
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$3 */
    public class C61323 extends SelectorHeaderCell {
        public C61323(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }

        @Override // org.telegram.p035ui.Components.Premium.boosts.cells.selector.SelectorHeaderCell
        public int getHeaderHeight() {
            if (getResources().getConfiguration().orientation == 2) {
                return AndroidUtilities.m1036dp(48.0f);
            }
            return AndroidUtilities.m1036dp(54.0f);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$4 */
    public class C61334 extends SelectorSearchCell {
        private boolean isKeyboardVisible;

        public C61334(Context context, Theme.ResourcesProvider resourcesProvider, Runnable runnable) {
            super(context, resourcesProvider, runnable);
        }

        @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
            super.onLayout(z2, i2, i3, i4, i5);
            MultiContactsSelectorBottomSheet.this.listPaddingTop = getMeasuredHeight() + AndroidUtilities.m1036dp(64.0f);
            MultiContactsSelectorBottomSheet.this.selectorAdapter.notifyChangedLast();
            if (this.isKeyboardVisible != MultiContactsSelectorBottomSheet.this.isKeyboardVisible()) {
                boolean zIsKeyboardVisible = MultiContactsSelectorBottomSheet.this.isKeyboardVisible();
                this.isKeyboardVisible = zIsKeyboardVisible;
                if (zIsKeyboardVisible) {
                    MultiContactsSelectorBottomSheet.this.scrollToTop(true);
                }
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$5 */
    public class C61345 extends View {
        public C61345(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            canvas.drawColor(MultiContactsSelectorBottomSheet.this.getThemedColor(Theme.key_graySection));
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$6 */
    public class C61356 extends ButtonWithCounterView {
        public C61356(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }

        @Override // org.telegram.p035ui.Stories.recorder.ButtonWithCounterView
        public float calculateCounterWidth(float f, float f2) {
            boolean z2 = MultiContactsSelectorBottomSheet.this.recipientsBtnExtraSpace == 0.0f;
            MultiContactsSelectorBottomSheet.this.recipientsBtnExtraSpace = f;
            if (z2) {
                MultiContactsSelectorBottomSheet.this.createRecipientsBtnSpaceSpan();
                MultiContactsSelectorBottomSheet.this.updateActionButton(false);
            }
            return f;
        }
    }

    public /* synthetic */ void lambda$new$2(View view) {
        next();
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$7 */
    public class C61367 extends RecyclerView.OnScrollListener {
        public C61367() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i8) {
            if (i8 == 1) {
                AndroidUtilities.hideKeyboard(MultiContactsSelectorBottomSheet.this.searchField.getEditText());
            }
        }
    }

    public /* synthetic */ void lambda$new$4(int i, View view, int i2, float f, float f2) {
        if (view instanceof SelectorUserCell) {
            TLRPC.User user = ((SelectorUserCell) view).getUser();
            long j = user.f1407id;
            boolean zContains = this.selectedIds.contains(Long.valueOf(j));
            HashSet<Long> hashSet = this.selectedIds;
            if (zContains) {
                hashSet.remove(Long.valueOf(j));
            } else {
                hashSet.add(Long.valueOf(j));
                this.allSelectedObjects.put(Long.valueOf(j), user);
            }
            if (this.selectedIds.size() == i + 1) {
                this.selectedIds.remove(Long.valueOf(j));
                showMaximumUsersToast();
            } else {
                this.searchField.updateSpans(true, this.selectedIds, new Runnable() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$3();
                    }
                }, null);
                updateList(true, false);
                clearSearchAfterSelect();
            }
        }
    }

    public /* synthetic */ void lambda$new$3() {
        updateList(true, false);
    }

    /* JADX INFO: renamed from: org.telegram.ui.MultiContactsSelectorBottomSheet$8 */
    public class C61378 extends RecyclerView.ItemDecoration {
        public C61378() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            if (recyclerView.getChildAdapterPosition(view) == MultiContactsSelectorBottomSheet.this.items.size()) {
                rect.bottom = MultiContactsSelectorBottomSheet.this.listPaddingTop;
            }
        }
    }

    public /* synthetic */ void lambda$new$5() {
        updateList(true, false);
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public void onPreDraw(Canvas canvas, int i, float f) {
        this.headerView.setTranslationY(Math.max(i, AndroidUtilities.statusBarHeight + (((this.headerView.getMeasuredHeight() - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(40.0f)) / 2.0f)) + AndroidUtilities.m1036dp(8.0f));
        this.searchField.setTranslationY(this.headerView.getTranslationY() + this.headerView.getMeasuredHeight());
        this.sectionCell.setTranslationY(this.searchField.getTranslationY() + this.searchField.getMeasuredHeight());
        this.recyclerListView.setTranslationY(((this.headerView.getMeasuredHeight() + this.searchField.getMeasuredHeight()) + this.sectionCell.getMeasuredHeight()) - AndroidUtilities.m1036dp(8.0f));
    }

    private void next() {
        if (this.selectedIds.size() == 0 || this.selectorListener == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (TLRPC.User user : this.allSelectedObjects.values()) {
            if (this.selectedIds.contains(Long.valueOf(user.f1407id))) {
                arrayList.add(Long.valueOf(user.f1407id));
            }
        }
        this.selectorListener.onUserSelected(arrayList);
        lambda$new$0();
    }

    public void scrollToTop(boolean z) {
        if (z) {
            LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(getContext(), 2, 0.6f);
            linearSmoothScrollerCustom.setTargetPosition(1);
            linearSmoothScrollerCustom.setOffset(AndroidUtilities.m1036dp(36.0f));
            this.recyclerListView.getLayoutManager().startSmoothScroll(linearSmoothScrollerCustom);
            return;
        }
        this.recyclerListView.scrollToPosition(0);
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet
    public void dismissInternal() {
        super.dismissInternal();
        instance = null;
        AndroidUtilities.cancelRunOnUIThread(this.remoteSearchRunnable);
    }

    private void showMaximumUsersToast() {
        BulletinFactory.m1142of(this.container, this.resourcesProvider).createSimpleBulletin(C2797R.raw.chats_infotip, LocaleController.formatPluralString("BotMultiContactsSelectorLimit", this.maxCount, new Object[0])).show(true);
        try {
            this.container.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
    }

    private void updateList(boolean z, boolean z2) {
        updateItems(z, z2);
        updateCheckboxes(z);
        updateActionButton(z);
    }

    private void updateCheckboxes(boolean z) {
        int childAdapterPosition;
        int i = -1;
        int i2 = 0;
        for (int i3 = 0; i3 < this.recyclerListView.getChildCount(); i3++) {
            View childAt = this.recyclerListView.getChildAt(i3);
            if ((childAt instanceof SelectorUserCell) && (childAdapterPosition = this.recyclerListView.getChildAdapterPosition(childAt)) > 0) {
                if (i == -1) {
                    i = childAdapterPosition;
                }
                int i4 = childAdapterPosition - 1;
                if (i4 >= 0 && i4 < this.items.size()) {
                    SelectorAdapter.Item item = this.items.get(i4);
                    SelectorUserCell selectorUserCell = (SelectorUserCell) childAt;
                    selectorUserCell.setChecked(item.checked, z);
                    TLRPC.Chat chat = item.chat;
                    if (chat != null) {
                        selectorUserCell.setCheckboxAlpha(this.selectorAdapter.getParticipantsCount(chat) > 200 ? 0.3f : 1.0f, z);
                    } else {
                        selectorUserCell.setCheckboxAlpha(1.0f, z);
                    }
                }
                i2 = childAdapterPosition;
            }
        }
        if (z) {
            this.selectorAdapter.notifyItemRangeChanged(0, i);
            SelectorAdapter selectorAdapter = this.selectorAdapter;
            selectorAdapter.notifyItemRangeChanged(i2, selectorAdapter.getItemCount() - i2);
        }
    }

    public void updateActionButton(boolean z) {
        this.actionButton.setShowZero(false);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (this.selectedIds.size() == 0) {
            spannableStringBuilder.append((CharSequence) "d").setSpan(this.recipientsBtnSpaceSpan, 0, 1, 33);
            Boolean bool = this.filterBots;
            if (bool == null || !bool.booleanValue()) {
                spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.ChooseUsers));
            } else {
                spannableStringBuilder.append((CharSequence) LocaleController.getString(this.maxCount > 1 ? C2797R.string.ChooseBots : C2797R.string.ChooseBot));
            }
        } else {
            spannableStringBuilder.append((CharSequence) LocaleController.getString(C2797R.string.GiftPremiumProceedBtn));
        }
        this.actionButton.setCount(this.selectedIds.size(), true);
        this.actionButton.setText(spannableStringBuilder, z, false);
        this.actionButton.setEnabled(true);
    }

    public void onSearch(String str) {
        this.query = str;
        AndroidUtilities.cancelRunOnUIThread(this.remoteSearchRunnable);
        AndroidUtilities.runOnUIThread(this.remoteSearchRunnable, 100L);
    }

    private void clearSearchAfterSelect() {
        if (isSearching()) {
            this.query = null;
            this.searchField.setText(_UrlKt.FRAGMENT_ENCODE_SET);
            AndroidUtilities.cancelRunOnUIThread(this.remoteSearchRunnable);
            updateItems(true, true);
        }
    }

    private void updateSectionCell(boolean z) {
        HashSet<Long> hashSet = this.selectedIds;
        if (hashSet == null) {
            return;
        }
        int size = hashSet.size();
        SelectorAdapter selectorAdapter = this.selectorAdapter;
        if (size > 0) {
            selectorAdapter.setTopSectionClickListener(new View.OnClickListener() { // from class: org.telegram.ui.MultiContactsSelectorBottomSheet$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateSectionCell$6(view);
                }
            });
        } else {
            selectorAdapter.setTopSectionClickListener(null);
        }
    }

    public /* synthetic */ void lambda$updateSectionCell$6(View view) {
        this.selectedIds.clear();
        this.searchField.spansContainer.removeAllSpans(true);
        updateList(true, false);
    }

    private boolean isSearching() {
        return !TextUtils.isEmpty(this.query);
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void updateItems(boolean z, boolean z2) {
        int iM1036dp;
        int iM1036dp2;
        SelectorAdapter selectorAdapter;
        this.oldItems.clear();
        this.oldItems.addAll(this.items);
        this.items.clear();
        if (isSearching()) {
            iM1036dp2 = 0;
            for (TLRPC.User user : this.foundUsers) {
                iM1036dp2 += AndroidUtilities.m1036dp(56.0f);
                this.items.add(SelectorAdapter.Item.asUser(user, this.selectedIds.contains(Long.valueOf(user.f1407id))));
            }
        } else {
            if (this.hints.isEmpty()) {
                iM1036dp = 0;
            } else {
                ArrayList arrayList = new ArrayList();
                Iterator<TLRPC.TL_topPeer> it = this.hints.iterator();
                iM1036dp = 0;
                while (it.hasNext()) {
                    TLRPC.User user2 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(it.next().peer.user_id));
                    if (!user2.self && !user2.bot && !UserObject.isService(user2.f1407id) && !UserObject.isDeleted(user2) && filter(user2)) {
                        iM1036dp += AndroidUtilities.m1036dp(56.0f);
                        arrayList.add(SelectorAdapter.Item.asUser(user2, this.selectedIds.contains(Long.valueOf(user2.f1407id))));
                    }
                }
                if (!arrayList.isEmpty()) {
                    iM1036dp += AndroidUtilities.m1036dp(32.0f);
                    this.items.add(SelectorAdapter.Item.asTopSection(LocaleController.getString(C2797R.string.GiftPremiumFrequentContacts)));
                    this.items.addAll(arrayList);
                }
            }
            long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
            Boolean bool = this.filterBots;
            if (bool != null && bool.booleanValue()) {
                ArrayList arrayList2 = new ArrayList();
                ArrayList<TLRPC.Dialog> allDialogs = MessagesController.getInstance(this.currentAccount).getAllDialogs();
                int size = allDialogs.size();
                int i = 0;
                while (i < size) {
                    TLRPC.Dialog dialog = allDialogs.get(i);
                    i++;
                    TLRPC.Dialog dialog2 = dialog;
                    if (dialog2.f1251id >= 0) {
                        TLRPC.User user3 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(dialog2.f1251id));
                        if (filter(user3)) {
                            iM1036dp += AndroidUtilities.m1036dp(56.0f);
                            arrayList2.add(SelectorAdapter.Item.asUser(user3, this.selectedIds.contains(Long.valueOf(user3.f1407id))));
                        }
                    }
                }
                if (!arrayList2.isEmpty()) {
                    iM1036dp += AndroidUtilities.m1036dp(32.0f);
                    this.items.add(SelectorAdapter.Item.asTopSection(LocaleController.getString(C2797R.string.SearchApps)));
                    this.items.addAll(arrayList2);
                }
            }
            for (String str : this.contactsLetters) {
                ArrayList arrayList3 = new ArrayList();
                List<TLRPC.TL_contact> list = this.contactsMap.get(str);
                if (list != null) {
                    for (TLRPC.TL_contact tL_contact : list) {
                        if (tL_contact.user_id != clientUserId) {
                            TLRPC.User user4 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(tL_contact.user_id));
                            if (filter(user4)) {
                                iM1036dp += AndroidUtilities.m1036dp(56.0f);
                                arrayList3.add(SelectorAdapter.Item.asUser(user4, this.selectedIds.contains(Long.valueOf(user4.f1407id))));
                            }
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        iM1036dp += AndroidUtilities.m1036dp(32.0f);
                        this.items.add(SelectorAdapter.Item.asLetter(str.toUpperCase()));
                        this.items.addAll(arrayList3);
                    }
                }
            }
            iM1036dp2 = iM1036dp;
        }
        if (this.items.isEmpty()) {
            this.items.add(SelectorAdapter.Item.asNoUsers());
            iM1036dp2 += AndroidUtilities.m1036dp(150.0f);
        }
        this.items.add(SelectorAdapter.Item.asPad(Math.max(0, ((int) (AndroidUtilities.displaySize.y * 0.6f)) - iM1036dp2)));
        updateSectionCell(z);
        if (!z2 || (selectorAdapter = this.selectorAdapter) == null) {
            return;
        }
        if (z) {
            selectorAdapter.setItems(this.oldItems, this.items);
        } else {
            selectorAdapter.notifyDataSetChanged();
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateItems(false, true);
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public CharSequence getTitle() {
        Boolean bool = this.filterBots;
        if (bool == null || !bool.booleanValue()) {
            return LocaleController.getString(C2797R.string.ChooseUsers);
        }
        return LocaleController.getString(this.maxCount > 1 ? C2797R.string.ChooseBots : C2797R.string.ChooseBot);
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public RecyclerListView.SelectionAdapter createAdapter(RecyclerListView recyclerListView) {
        SelectorAdapter selectorAdapter = new SelectorAdapter(getContext(), true, this.resourcesProvider);
        this.selectorAdapter = selectorAdapter;
        selectorAdapter.setGreenSelector(true);
        return this.selectorAdapter;
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* JADX INFO: renamed from: dismiss */
    public void lambda$new$0() {
        AndroidUtilities.hideKeyboard(this.searchField.getEditText());
        super.lambda$new$0();
    }
}
