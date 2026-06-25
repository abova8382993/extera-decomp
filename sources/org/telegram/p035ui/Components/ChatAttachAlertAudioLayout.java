package org.telegram.p035ui.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.util.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import me.vkryl.android.animator.BoolAnimator;
import me.vkryl.android.animator.FactorAnimator;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AiTonesController$$ExternalSyntheticLambda0;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.utils.TextWatcherImpl;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ActionBar.ThemeDescription;
import org.telegram.p035ui.Cells.SharedAudioCell;
import org.telegram.p035ui.Components.ChatAttachAlert;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.p035ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.p035ui.Components.blur3.drawable.color.impl.BlurredBackgroundProviderImpl;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
@SuppressLint({"ViewConstructor"})
public class ChatAttachAlertAudioLayout extends ChatAttachAlert.AttachAlertLayout implements NotificationCenter.NotificationCenterDelegate, FactorAnimator.Target {
    private int LOAD_MORE_SEARCH_CHATS;
    private int LOAD_MORE_SEARCH_GLOBAL;
    private int LOAD_MORE_SEARCH_PROFILE;
    private final BoolAnimator animatorFadeVisible;
    private ArrayList<MediaController.AudioEntry> audioEntries;
    private float currentPanTranslationProgress;
    private AudioSelectDelegate delegate;
    private final View fadeView;
    private boolean failedToResolveGlobalAudioBot;
    private ArrayList<MediaController.AudioEntry> foundGlobal;
    private ArrayList<MediaController.AudioEntry> foundInChats;
    private FragmentContextView fragmentContextView;
    private FrameLayout fragmentContextViewWrapper;
    private final FrameLayout frameLayout;
    private TLRPC.User globalAudioBot;
    private int globalAudioMessageId;
    private String globalAudioOffset;
    private String lastSearchChatsQuery;
    private String lastSearchGlobalQuery;
    private UniversalRecyclerView listView;
    private boolean loadingAudio;
    private boolean loadingSearchChats;
    private boolean loadingSearchGlobal;
    private int maxSelectedFiles;
    private MessageObject playingAudio;
    private int preMeasuredAvailableHeight;
    private ArrayList<MediaController.AudioEntry> profileEntries;
    private String query;
    private boolean resolvingGlobalAudioBot;
    private MessagesController.SavedMusicList savedMusicList;
    private boolean searchChatsHasMore;
    private int searchChatsNextRate;
    private int searchChatsRequestId;
    private final Runnable searchChatsRunnable;
    private final FragmentSearchField searchField;
    private boolean searchGlobalHasMore;
    private int searchGlobalRequestId;
    private final Runnable searchGlobalRunnable;
    private final HashSet<MediaController.AudioEntry> selectedAudios;
    private boolean sendPressed;
    private DialogsActivityTopPanelLayout topPanelLayout;
    private final Runnable updateWithSavingScrollRunnable;

    public interface AudioSelectDelegate {
        void didSelectAudio(ArrayList<MessageObject> arrayList, CharSequence charSequence, boolean z, int i, int i2, long j, boolean z2, long j2);
    }

    public ChatAttachAlertAudioLayout(final ChatAttachAlert chatAttachAlert, Context context, Theme.ResourcesProvider resourcesProvider) {
        super(chatAttachAlert, context, resourcesProvider);
        this.animatorFadeVisible = new BoolAnimator(0, this, CubicBezierInterpolator.EASE_OUT_QUINT, 380L);
        this.maxSelectedFiles = -1;
        this.audioEntries = new ArrayList<>();
        this.selectedAudios = new HashSet<>();
        this.profileEntries = new ArrayList<>();
        this.foundInChats = new ArrayList<>();
        this.foundGlobal = new ArrayList<>();
        this.LOAD_MORE_SEARCH_CHATS = 1;
        this.LOAD_MORE_SEARCH_GLOBAL = 2;
        this.LOAD_MORE_SEARCH_PROFILE = 3;
        this.updateWithSavingScrollRunnable = new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1();
            }
        };
        this.searchChatsRequestId = -1;
        this.searchChatsRunnable = new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.searchChats();
            }
        };
        this.searchGlobalRequestId = -1;
        this.searchGlobalRunnable = new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.searchGlobal();
            }
        };
        this.globalAudioMessageId = -1000000000;
        NotificationCenter.getInstance(this.parentAlert.currentAccount).addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance(this.parentAlert.currentAccount).addObserver(this, NotificationCenter.messagePlayingDidStart);
        NotificationCenter.getInstance(this.parentAlert.currentAccount).addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance(this.parentAlert.currentAccount).addObserver(this, NotificationCenter.musicListLoaded);
        loadAudio();
        ChatAttachAlert.SearchFadeView searchFadeView = new ChatAttachAlert.SearchFadeView(context, Theme.key_windowBackgroundWhite, resourcesProvider);
        this.fadeView = searchFadeView;
        searchFadeView.setVisibility(4);
        FrameLayout frameLayout = new FrameLayout(context);
        this.frameLayout = frameLayout;
        ChatAttachAlert.AttachSearchField attachSearchField = new ChatAttachAlert.AttachSearchField(context, this.parentAlert, resourcesProvider);
        this.searchField = attachSearchField;
        attachSearchField.setPadding(AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f), AndroidUtilities.m1036dp(4.0f));
        attachSearchField.editText.addTextChangedListener(new TextWatcherImpl() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout.1
            public C40401() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                TextUtils.isEmpty(ChatAttachAlertAudioLayout.this.query);
                ChatAttachAlertAudioLayout.this.query = editable.toString().trim();
                AndroidUtilities.cancelRunOnUIThread(ChatAttachAlertAudioLayout.this.searchChatsRunnable);
                if (!TextUtils.isEmpty(ChatAttachAlertAudioLayout.this.query)) {
                    ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
                    chatAttachAlertAudioLayout.loadingSearchChats = chatAttachAlertAudioLayout.query != null && ChatAttachAlertAudioLayout.this.query.length() >= 0;
                    if (!TextUtils.equals(ChatAttachAlertAudioLayout.this.lastSearchChatsQuery, ChatAttachAlertAudioLayout.this.query)) {
                        ChatAttachAlertAudioLayout.this.foundInChats.clear();
                        ChatAttachAlertAudioLayout.this.searchChatsNextRate = 0;
                        ChatAttachAlertAudioLayout.this.searchChatsHasMore = false;
                    }
                    AndroidUtilities.runOnUIThread(ChatAttachAlertAudioLayout.this.searchChatsRunnable, 1500L);
                }
                AndroidUtilities.cancelRunOnUIThread(ChatAttachAlertAudioLayout.this.searchGlobalRunnable);
                if (!TextUtils.isEmpty(ChatAttachAlertAudioLayout.this.query)) {
                    ChatAttachAlertAudioLayout chatAttachAlertAudioLayout2 = ChatAttachAlertAudioLayout.this;
                    chatAttachAlertAudioLayout2.loadingSearchGlobal = (chatAttachAlertAudioLayout2.query == null || ChatAttachAlertAudioLayout.this.query.length() < 3 || TextUtils.isEmpty(MessagesController.getInstance(ChatAttachAlertAudioLayout.this.parentAlert.currentAccount).config.musicSearchUsername.get())) ? false : true;
                    if (!TextUtils.equals(ChatAttachAlertAudioLayout.this.lastSearchGlobalQuery, ChatAttachAlertAudioLayout.this.query)) {
                        ChatAttachAlertAudioLayout.this.foundGlobal.clear();
                        ChatAttachAlertAudioLayout.this.searchGlobalHasMore = false;
                    }
                    AndroidUtilities.runOnUIThread(ChatAttachAlertAudioLayout.this.searchGlobalRunnable, 1500L);
                }
                ChatAttachAlertAudioLayout.this.updateWithSavingScroll();
            }
        });
        attachSearchField.editText.setHint(LocaleController.getString(C2797R.string.SearchMusic));
        frameLayout.addView(searchFadeView, LayoutHelper.createFrameMatchParent());
        FrameLayout.LayoutParams layoutParamsCreateFrame = LayoutHelper.createFrame(-1, 48.0f, 51, 7.0f, 8.0f, 7.0f, 4.0f);
        ((ViewGroup.MarginLayoutParams) layoutParamsCreateFrame).topMargin += AndroidUtilities.statusBarHeight;
        frameLayout.addView(attachSearchField, layoutParamsCreateFrame);
        DialogsActivityTopPanelLayout dialogsActivityTopPanelLayout = new DialogsActivityTopPanelLayout(context);
        this.topPanelLayout = dialogsActivityTopPanelLayout;
        dialogsActivityTopPanelLayout.setPadding(AndroidUtilities.m1036dp(11.0f), AndroidUtilities.m1036dp(21.0f), AndroidUtilities.m1036dp(11.0f), AndroidUtilities.m1036dp(21.0f));
        this.topPanelLayout.setOnAnimatedHeightChangedListener(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0(chatAttachAlert);
            }
        });
        FrameLayout frameLayout2 = new FrameLayout(context);
        this.fragmentContextViewWrapper = frameLayout2;
        this.topPanelLayout.addView(frameLayout2);
        this.topPanelLayout.setViewVisible(this.fragmentContextViewWrapper, true, false);
        C40412 c40412 = new FragmentContextView(context, chatAttachAlert.baseFragment, frameLayout, false, resourcesProvider) { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout.2
            public C40412(Context context2, BaseFragment baseFragment, View frameLayout3, boolean z, Theme.ResourcesProvider resourcesProvider2) {
                super(context2, baseFragment, frameLayout3, z, resourcesProvider2);
            }

            @Override // org.telegram.p035ui.Components.FragmentContextView, android.view.View
            public void setVisibility(int i) {
                ChatAttachAlertAudioLayout.this.topPanelLayout.setViewVisible(ChatAttachAlertAudioLayout.this.fragmentContextViewWrapper, i == 0);
            }
        };
        this.fragmentContextView = c40412;
        c40412.isInsideBubble = true;
        this.fragmentContextViewWrapper.addView(c40412);
        FrameLayout.LayoutParams layoutParamsCreateFrame2 = LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 8.0f, 0.0f, 4.0f);
        ((ViewGroup.MarginLayoutParams) layoutParamsCreateFrame2).topMargin += AndroidUtilities.statusBarHeight + AndroidUtilities.m1036dp(27.0f);
        frameLayout3.addView(this.topPanelLayout, layoutParamsCreateFrame2);
        C40423 c40423 = new UniversalRecyclerView(context2, chatAttachAlert.currentAccount, 0, new Utilities.Callback2() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda4
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda5
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onItemClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, new Utilities.Callback5Return() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda6
            @Override // org.telegram.messenger.Utilities.Callback5Return
            public final Object run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return Boolean.valueOf(this.f$0.onItemLongClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue()));
            }
        }, resourcesProvider2) { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout.3
            public C40423(Context context2, int i, int i2, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider2) {
                super(context2, i, i2, callback2, callback5, callback5Return, resourcesProvider2);
            }

            @Override // org.telegram.p035ui.Components.RecyclerListView
            public boolean allowSelectChildAtPosition(float f, float f2) {
                return f2 >= ((float) ((ChatAttachAlertAudioLayout.this.parentAlert.scrollOffsetY[0] + AndroidUtilities.m1036dp(30.0f)) + (!ChatAttachAlertAudioLayout.this.parentAlert.inBubbleMode ? AndroidUtilities.statusBarHeight : 0)));
            }

            @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
            public void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
                chatAttachAlertAudioLayout.parentAlert.updateLayout(chatAttachAlertAudioLayout, true, 0);
            }

            @Override // org.telegram.p035ui.Components.UniversalRecyclerView
            public void onLayoutUpdate() {
                ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
                chatAttachAlertAudioLayout.parentAlert.updateLayout(chatAttachAlertAudioLayout, true, 0);
            }
        };
        this.listView = c40423;
        c40423.adapter.setApplyBackground(false);
        this.listView.setSections();
        UniversalRecyclerView universalRecyclerView = this.listView;
        this.iBlur3Capture = universalRecyclerView;
        this.iBlur3CaptureView = universalRecyclerView;
        this.occupyStatusBar = true;
        this.occupyNavigationBar = true;
        universalRecyclerView.setClipToPadding(false);
        this.listView.setHorizontalScrollBarEnabled(false);
        this.listView.setVerticalScrollBarEnabled(false);
        addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        this.listView.setGlowColor(getThemedColor(Theme.key_dialogScrollGlow));
        this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout.4
            public C40434() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
                chatAttachAlertAudioLayout.parentAlert.updateLayout(chatAttachAlertAudioLayout, true, i2);
            }
        });
        addView(frameLayout3, LayoutHelper.createFrame(-1, 200, 51));
        this.listView.adapter.update(false);
        checkUi_listViewPadding();
        int i = this.parentAlert.currentAccount;
        this.savedMusicList = new MessagesController.SavedMusicList(i, UserConfig.getInstance(i).getClientUserId());
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertAudioLayout$1 */
    public class C40401 implements TextWatcherImpl {
        public C40401() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            TextUtils.isEmpty(ChatAttachAlertAudioLayout.this.query);
            ChatAttachAlertAudioLayout.this.query = editable.toString().trim();
            AndroidUtilities.cancelRunOnUIThread(ChatAttachAlertAudioLayout.this.searchChatsRunnable);
            if (!TextUtils.isEmpty(ChatAttachAlertAudioLayout.this.query)) {
                ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
                chatAttachAlertAudioLayout.loadingSearchChats = chatAttachAlertAudioLayout.query != null && ChatAttachAlertAudioLayout.this.query.length() >= 0;
                if (!TextUtils.equals(ChatAttachAlertAudioLayout.this.lastSearchChatsQuery, ChatAttachAlertAudioLayout.this.query)) {
                    ChatAttachAlertAudioLayout.this.foundInChats.clear();
                    ChatAttachAlertAudioLayout.this.searchChatsNextRate = 0;
                    ChatAttachAlertAudioLayout.this.searchChatsHasMore = false;
                }
                AndroidUtilities.runOnUIThread(ChatAttachAlertAudioLayout.this.searchChatsRunnable, 1500L);
            }
            AndroidUtilities.cancelRunOnUIThread(ChatAttachAlertAudioLayout.this.searchGlobalRunnable);
            if (!TextUtils.isEmpty(ChatAttachAlertAudioLayout.this.query)) {
                ChatAttachAlertAudioLayout chatAttachAlertAudioLayout2 = ChatAttachAlertAudioLayout.this;
                chatAttachAlertAudioLayout2.loadingSearchGlobal = (chatAttachAlertAudioLayout2.query == null || ChatAttachAlertAudioLayout.this.query.length() < 3 || TextUtils.isEmpty(MessagesController.getInstance(ChatAttachAlertAudioLayout.this.parentAlert.currentAccount).config.musicSearchUsername.get())) ? false : true;
                if (!TextUtils.equals(ChatAttachAlertAudioLayout.this.lastSearchGlobalQuery, ChatAttachAlertAudioLayout.this.query)) {
                    ChatAttachAlertAudioLayout.this.foundGlobal.clear();
                    ChatAttachAlertAudioLayout.this.searchGlobalHasMore = false;
                }
                AndroidUtilities.runOnUIThread(ChatAttachAlertAudioLayout.this.searchGlobalRunnable, 1500L);
            }
            ChatAttachAlertAudioLayout.this.updateWithSavingScroll();
        }
    }

    public /* synthetic */ void lambda$new$0(ChatAttachAlert chatAttachAlert) {
        chatAttachAlert.blur3_InvalidateBlur();
        checkUi_listViewPadding();
        this.parentAlert.updateLayout(this, true, 0);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertAudioLayout$2 */
    public class C40412 extends FragmentContextView {
        public C40412(Context context2, BaseFragment baseFragment, View frameLayout3, boolean z, Theme.ResourcesProvider resourcesProvider2) {
            super(context2, baseFragment, frameLayout3, z, resourcesProvider2);
        }

        @Override // org.telegram.p035ui.Components.FragmentContextView, android.view.View
        public void setVisibility(int i) {
            ChatAttachAlertAudioLayout.this.topPanelLayout.setViewVisible(ChatAttachAlertAudioLayout.this.fragmentContextViewWrapper, i == 0);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertAudioLayout$3 */
    public class C40423 extends UniversalRecyclerView {
        public C40423(Context context2, int i, int i2, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider2) {
            super(context2, i, i2, callback2, callback5, callback5Return, resourcesProvider2);
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView
        public boolean allowSelectChildAtPosition(float f, float f2) {
            return f2 >= ((float) ((ChatAttachAlertAudioLayout.this.parentAlert.scrollOffsetY[0] + AndroidUtilities.m1036dp(30.0f)) + (!ChatAttachAlertAudioLayout.this.parentAlert.inBubbleMode ? AndroidUtilities.statusBarHeight : 0)));
        }

        @Override // org.telegram.p035ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
            chatAttachAlertAudioLayout.parentAlert.updateLayout(chatAttachAlertAudioLayout, true, 0);
        }

        @Override // org.telegram.p035ui.Components.UniversalRecyclerView
        public void onLayoutUpdate() {
            ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
            chatAttachAlertAudioLayout.parentAlert.updateLayout(chatAttachAlertAudioLayout, true, 0);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.ChatAttachAlertAudioLayout$4 */
    public class C40434 extends RecyclerView.OnScrollListener {
        public C40434() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            ChatAttachAlertAudioLayout chatAttachAlertAudioLayout = ChatAttachAlertAudioLayout.this;
            chatAttachAlertAudioLayout.parentAlert.updateLayout(chatAttachAlertAudioLayout, true, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkUi_listViewPadding() {
        /*
            r4 = this;
            org.telegram.ui.Components.ChatAttachAlert r0 = r4.parentAlert
            org.telegram.ui.Components.SizeNotifierFrameLayout r0 = r0.sizeNotifierFrameLayout
            int r0 = r0.measureKeyboardHeight()
            r1 = 1101004800(0x41a00000, float:20.0)
            int r1 = org.telegram.messenger.AndroidUtilities.m1036dp(r1)
            r2 = 0
            if (r0 <= r1) goto L1d
            r0 = 1090519040(0x41000000, float:8.0)
            int r0 = org.telegram.messenger.AndroidUtilities.m1036dp(r0)
            org.telegram.ui.Components.ChatAttachAlert r1 = r4.parentAlert
            r1.setAllowNestedScroll(r2)
            goto L3f
        L1d:
            boolean r0 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r0 != 0) goto L33
            android.graphics.Point r0 = org.telegram.messenger.AndroidUtilities.displaySize
            int r1 = r0.x
            int r0 = r0.y
            if (r1 <= r0) goto L33
            int r0 = r4.preMeasuredAvailableHeight
            float r0 = (float) r0
            r1 = 1080033280(0x40600000, float:3.5)
            float r0 = r0 / r1
            int r0 = (int) r0
            goto L39
        L33:
            int r0 = r4.preMeasuredAvailableHeight
            int r0 = r0 / 5
            int r0 = r0 * 2
        L39:
            org.telegram.ui.Components.ChatAttachAlert r1 = r4.parentAlert
            r3 = 1
            r1.setAllowNestedScroll(r3)
        L3f:
            int r1 = org.telegram.messenger.AndroidUtilities.statusBarHeight
            int r0 = r0 + r1
            r1 = 1113587712(0x42600000, float:56.0)
            int r1 = org.telegram.messenger.AndroidUtilities.m1036dp(r1)
            int r0 = r0 + r1
            float r0 = (float) r0
            org.telegram.ui.Components.DialogsActivityTopPanelLayout r1 = r4.topPanelLayout
            r3 = 0
            float r1 = r1.getAnimatedHeightWithPadding(r3)
            float r0 = r0 + r1
            int r0 = (int) r0
            org.telegram.ui.Components.UniversalRecyclerView r1 = r4.listView
            int r4 = r4.listPaddingBottom
            r1.setPadding(r2, r0, r2, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertAudioLayout.checkUi_listViewPadding():void");
    }

    private void convertProfileMusicToEntries() {
        MediaController.AudioEntry audioEntry;
        MessagesController.SavedMusicList savedMusicList = this.savedMusicList;
        if (savedMusicList == null) {
            return;
        }
        int size = savedMusicList.list.size();
        if (size < this.profileEntries.size()) {
            ArrayList<MediaController.AudioEntry> arrayList = this.profileEntries;
            arrayList.subList(size, arrayList.size()).clear();
        }
        for (int i = 0; i < size; i++) {
            int size2 = this.profileEntries.size();
            ArrayList<MediaController.AudioEntry> arrayList2 = this.profileEntries;
            if (i >= size2) {
                audioEntry = new MediaController.AudioEntry();
                arrayList2.add(audioEntry);
            } else {
                audioEntry = arrayList2.get(i);
            }
            if (audioEntry.messageObject != this.savedMusicList.list.get(i)) {
                MessageObject messageObject = this.savedMusicList.list.get(i);
                int i2 = this.globalAudioMessageId;
                this.globalAudioMessageId = i2 - 1;
                audioEntry.f1148id = i2;
                audioEntry.messageObject = messageObject;
            }
        }
    }

    public boolean needPlayMessage(MessageObject messageObject) {
        this.playingAudio = messageObject;
        ArrayList<MessageObject> arrayList = new ArrayList<>();
        arrayList.add(messageObject);
        return MediaController.getInstance().setPlaylist(arrayList, messageObject, 0L);
    }

    /* JADX WARN: Removed duplicated region for block: B:268:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0270  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillItems(java.util.ArrayList<org.telegram.p035ui.Components.UItem> r19, org.telegram.p035ui.Components.UniversalAdapter r20) {
        /*
            Method dump skipped, instruction units count: 1156
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertAudioLayout.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    public void updateWithSavingScroll() {
        AndroidUtilities.cancelRunOnUIThread(this.updateWithSavingScrollRunnable);
        AndroidUtilities.runOnUIThread(this.updateWithSavingScrollRunnable);
    }

    public /* synthetic */ void lambda$new$1() {
        int i = -1;
        boolean zCanScrollVertically = this.listView.canScrollVertically(-1);
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= this.listView.getChildCount()) {
                break;
            }
            View childAt = this.listView.getChildAt(i3);
            int childAdapterPosition = this.listView.getChildAdapterPosition(childAt);
            int top = childAt.getTop();
            if (childAdapterPosition >= 0) {
                i2 = top;
                i = childAdapterPosition;
                break;
            } else {
                i3++;
                i2 = top;
                i = childAdapterPosition;
            }
        }
        this.listView.adapter.update(true);
        if (!zCanScrollVertically) {
            this.listView.layoutManager.scrollToPositionWithOffset(0, 0);
        } else if (i >= 0) {
            UniversalRecyclerView universalRecyclerView = this.listView;
            universalRecyclerView.layoutManager.scrollToPositionWithOffset(i, i2 - universalRecyclerView.getPaddingTop());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onItemClick(org.telegram.p035ui.Components.UItem r19, android.view.View r20, int r21, float r22, float r23) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            if (r1 == 0) goto L14
            int r3 = r1.f1708id
            int r4 = r0.LOAD_MORE_SEARCH_PROFILE
            if (r3 != r4) goto L14
            org.telegram.messenger.MessagesController$SavedMusicList r0 = r0.savedMusicList
            r0.load()
            return
        L14:
            if (r1 == 0) goto L20
            int r3 = r1.f1708id
            int r4 = r0.LOAD_MORE_SEARCH_CHATS
            if (r3 != r4) goto L20
            r0.searchChats()
            return
        L20:
            if (r1 == 0) goto L2c
            int r3 = r1.f1708id
            int r4 = r0.LOAD_MORE_SEARCH_GLOBAL
            if (r3 != r4) goto L2c
            r0.searchGlobal()
            return
        L2c:
            boolean r3 = r2 instanceof org.telegram.p035ui.Cells.SharedAudioCell
            if (r3 != 0) goto L31
            return
        L31:
            org.telegram.ui.Cells.SharedAudioCell r2 = (org.telegram.p035ui.Cells.SharedAudioCell) r2
            java.lang.Object r3 = r2.getTag()
            org.telegram.messenger.MediaController$AudioEntry r3 = (org.telegram.messenger.MediaController.AudioEntry) r3
            org.telegram.ui.Components.ChatAttachAlert r4 = r0.parentAlert
            boolean r5 = r4.isStoryAudioPicker
            r6 = 1
            if (r5 != 0) goto L89
            boolean r4 = r4.isPollAttach
            if (r4 == 0) goto L45
            goto L89
        L45:
            java.util.HashSet<org.telegram.messenger.MediaController$AudioEntry> r4 = r0.selectedAudios
            boolean r4 = r4.contains(r3)
            r5 = 0
            if (r4 == 0) goto L59
            java.util.HashSet<org.telegram.messenger.MediaController$AudioEntry> r4 = r0.selectedAudios
            r4.remove(r3)
            r1.checked = r5
            r2.setChecked(r5, r6)
            goto Lad
        L59:
            int r4 = r0.maxSelectedFiles
            if (r4 < 0) goto L7d
            java.util.HashSet<org.telegram.messenger.MediaController$AudioEntry> r4 = r0.selectedAudios
            int r4 = r4.size()
            int r7 = r0.maxSelectedFiles
            if (r4 < r7) goto L7d
            int r1 = org.telegram.messenger.C2797R.string.PassportUploadMaxReached
            java.lang.String r2 = "Files"
            java.lang.Object[] r3 = new java.lang.Object[r5]
            java.lang.String r2 = org.telegram.messenger.LocaleController.formatPluralString(r2, r7, r3)
            java.lang.Object[] r2 = new java.lang.Object[]{r2}
            java.lang.String r1 = org.telegram.messenger.LocaleController.formatString(r1, r2)
            r0.showErrorBox(r1)
            return
        L7d:
            r1.checked = r6
            java.util.HashSet<org.telegram.messenger.MediaController$AudioEntry> r1 = r0.selectedAudios
            r1.add(r3)
            r2.setChecked(r6, r6)
        L87:
            r5 = r6
            goto Lad
        L89:
            r0.sendPressed = r6
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            org.telegram.messenger.MessageObject r1 = r3.messageObject
            r8.add(r1)
            org.telegram.ui.Components.ChatAttachAlertAudioLayout$AudioSelectDelegate r7 = r0.delegate
            org.telegram.ui.Components.ChatAttachAlert r1 = r0.parentAlert
            org.telegram.ui.Components.EditTextEmoji r1 = r1.getCommentView()
            android.text.Editable r9 = r1.getText()
            r15 = 0
            r16 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r7.didSelectAudio(r8, r9, r10, r11, r12, r13, r15, r16)
            goto L87
        Lad:
            org.telegram.ui.Components.ChatAttachAlert r0 = r0.parentAlert
            if (r5 == 0) goto Lb2
            goto Lb3
        Lb2:
            r6 = 2
        Lb3:
            r0.updateCountButton(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.ChatAttachAlertAudioLayout.onItemClick(org.telegram.ui.Components.UItem, android.view.View, int, float, float):void");
    }

    public boolean onItemLongClick(UItem uItem, View view, int i, float f, float f2) {
        onItemClick(uItem, view, i, f, f2);
        return true;
    }

    public void setupBlurredSearchField(BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory) {
        FragmentSearchField fragmentSearchField = this.searchField;
        if (fragmentSearchField != null) {
            fragmentSearchField.setupBlurredBackground(blurredBackgroundDrawableViewFactory.create(fragmentSearchField, BlurredBackgroundProviderImpl.topPanel(this.resourcesProvider)));
        }
        View view = this.topPanelLayout;
        if (view != null) {
            BlurredBackgroundDrawable blurredBackgroundDrawableCreate = blurredBackgroundDrawableViewFactory.create(view, BlurredBackgroundProviderImpl.topPanel(this.resourcesProvider));
            blurredBackgroundDrawableCreate.setRadius(AndroidUtilities.m1036dp(24.0f));
            blurredBackgroundDrawableCreate.setPadding(AndroidUtilities.m1036dp(7.0f));
            this.topPanelLayout.setBlurredBackground(blurredBackgroundDrawableCreate);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onDestroy() {
        onHide();
        NotificationCenter.getInstance(this.parentAlert.currentAccount).removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance(this.parentAlert.currentAccount).removeObserver(this, NotificationCenter.messagePlayingDidStart);
        NotificationCenter.getInstance(this.parentAlert.currentAccount).removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance(this.parentAlert.currentAccount).removeObserver(this, NotificationCenter.musicListLoaded);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onHide() {
        if (this.playingAudio != null && MediaController.getInstance().isPlayingMessage(this.playingAudio)) {
            MediaController.getInstance().cleanupPlayer(true, true);
        }
        this.playingAudio = null;
    }

    private boolean isSearching() {
        return !TextUtils.isEmpty(this.query);
    }

    public void setMaxSelectedFiles(int i) {
        this.maxSelectedFiles = i;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void scrollToTop() {
        this.listView.smoothScrollToPosition(0);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getCurrentItemTop() {
        if (this.listView.getChildCount() <= 0) {
            return Integer.MAX_VALUE;
        }
        boolean z = false;
        int top = Integer.MAX_VALUE;
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            int childAdapterPosition = this.listView.getChildAdapterPosition(childAt);
            if (childAdapterPosition == 0) {
                z = true;
            }
            if (childAdapterPosition >= 0 && childAt.getTop() < top) {
                top = childAt.getTop();
            }
        }
        if (top == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        int iM1036dp = (((top - AndroidUtilities.m1036dp(56.0f)) - ((int) this.topPanelLayout.getAnimatedHeightWithPadding(0.0f))) - AndroidUtilities.statusBarHeight) - AndroidUtilities.m1036dp(8.0f);
        int i2 = (iM1036dp <= 0 || !z) ? 0 : iM1036dp;
        if (iM1036dp >= 0 && z) {
            this.animatorFadeVisible.setValue(false, true);
        } else {
            this.animatorFadeVisible.setValue(true, true);
            iM1036dp = i2;
        }
        this.frameLayout.setTranslationY(iM1036dp);
        return iM1036dp + AndroidUtilities.m1036dp(12.0f);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.m1036dp(4.0f);
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public boolean onDismiss() {
        if (this.playingAudio != null && MediaController.getInstance().isPlayingMessage(this.playingAudio)) {
            MediaController.getInstance().cleanupPlayer(true, true);
        }
        return super.onDismiss();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getListTopPadding() {
        return (this.listView.getPaddingTop() - AndroidUtilities.m1036dp(56.0f)) - ((int) this.topPanelLayout.getAnimatedHeightWithPadding(0.0f));
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onPreMeasure(int i, int i2) {
        this.preMeasuredAvailableHeight = i2;
        checkUi_listViewPadding();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onShow(ChatAttachAlert.AttachAlertLayout attachAlertLayout) {
        searchChats();
        this.savedMusicList.load();
        this.listView.layoutManager.scrollToPositionWithOffset(0, 0);
        this.listView.adapter.update(false);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onHidden() {
        this.selectedAudios.clear();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        UniversalRecyclerView universalRecyclerView;
        int i3 = NotificationCenter.messagePlayingDidReset;
        if (i == i3 || i == NotificationCenter.messagePlayingDidStart || i == NotificationCenter.messagePlayingPlayStateChanged) {
            if (i == i3 || i == NotificationCenter.messagePlayingPlayStateChanged) {
                int childCount = this.listView.getChildCount();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = this.listView.getChildAt(i4);
                    if (childAt instanceof SharedAudioCell) {
                        SharedAudioCell sharedAudioCell = (SharedAudioCell) childAt;
                        if (sharedAudioCell.getMessage() != null) {
                            sharedAudioCell.updateButtonState(false, true);
                        }
                    }
                }
                return;
            }
            if (i == NotificationCenter.messagePlayingDidStart && ((MessageObject) objArr[0]).eventId == 0) {
                int childCount2 = this.listView.getChildCount();
                for (int i5 = 0; i5 < childCount2; i5++) {
                    View childAt2 = this.listView.getChildAt(i5);
                    if (childAt2 instanceof SharedAudioCell) {
                        SharedAudioCell sharedAudioCell2 = (SharedAudioCell) childAt2;
                        if (sharedAudioCell2.getMessage() != null) {
                            sharedAudioCell2.updateButtonState(false, true);
                        }
                    }
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.musicListLoaded && objArr[0] == this.savedMusicList && (universalRecyclerView = this.listView) != null) {
            universalRecyclerView.adapter.update(true);
        }
    }

    private void showErrorBox(String str) {
        new AlertDialog.Builder(getContext(), this.resourcesProvider).setTitle(LocaleController.getString(C2797R.string.AppName)).setMessage(str).setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null).show();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getSelectedItemsCount() {
        return this.selectedAudios.size();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public boolean sendSelectedItems(final boolean z, final int i, final int i2, final long j, final boolean z2) {
        if (this.selectedAudios.size() == 0 || this.delegate == null || this.sendPressed) {
            return false;
        }
        this.sendPressed = true;
        final ArrayList arrayList = new ArrayList();
        Iterator<MediaController.AudioEntry> it = this.selectedAudios.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().messageObject);
        }
        ChatAttachAlert chatAttachAlert = this.parentAlert;
        return AlertsCreator.ensurePaidMessageConfirmation(chatAttachAlert.currentAccount, chatAttachAlert.getDialogId(), arrayList.size() + this.parentAlert.getAdditionalMessagesCount(), new Utilities.Callback() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda8
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$sendSelectedItems$2(arrayList, z, i, i2, j, z2, (Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$sendSelectedItems$2(ArrayList arrayList, boolean z, int i, int i2, long j, boolean z2, Long l) {
        this.delegate.didSelectAudio(arrayList, this.parentAlert.getCommentView().getText(), z, i, i2, j, z2, l.longValue());
        this.parentAlert.dismiss(true);
    }

    public ArrayList<MessageObject> getSelected() {
        ArrayList<MessageObject> arrayList = new ArrayList<>();
        Iterator<MediaController.AudioEntry> it = this.selectedAudios.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().messageObject);
        }
        return arrayList;
    }

    public void setDelegate(AudioSelectDelegate audioSelectDelegate) {
        this.delegate = audioSelectDelegate;
    }

    private void loadAudio() {
        this.loadingAudio = true;
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadAudio$4();
            }
        });
    }

    public /* synthetic */ void lambda$loadAudio$4() {
        String[] strArr = {"_id", "artist", "title", "_data", "duration", "album"};
        final ArrayList arrayList = new ArrayList();
        try {
            Cursor cursorQuery = ApplicationLoader.applicationContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, strArr, "is_music != 0", null, "title");
            int i = -2000000000;
            while (cursorQuery.moveToNext()) {
                try {
                    MediaController.AudioEntry audioEntry = new MediaController.AudioEntry();
                    audioEntry.f1148id = cursorQuery.getInt(0);
                    audioEntry.author = cursorQuery.getString(1);
                    audioEntry.title = cursorQuery.getString(2);
                    audioEntry.path = cursorQuery.getString(3);
                    audioEntry.duration = (int) (cursorQuery.getLong(4) / 1000);
                    audioEntry.genre = cursorQuery.getString(5);
                    File file = new File(audioEntry.path);
                    TLRPC.TL_message tL_message = new TLRPC.TL_message();
                    tL_message.out = true;
                    tL_message.f1271id = i;
                    tL_message.peer_id = new TLRPC.TL_peerUser();
                    TLRPC.TL_peerUser tL_peerUser = new TLRPC.TL_peerUser();
                    tL_message.from_id = tL_peerUser;
                    TLRPC.Peer peer = tL_message.peer_id;
                    long clientUserId = UserConfig.getInstance(this.parentAlert.currentAccount).getClientUserId();
                    tL_peerUser.user_id = clientUserId;
                    peer.user_id = clientUserId;
                    tL_message.date = (int) (System.currentTimeMillis() / 1000);
                    tL_message.message = _UrlKt.FRAGMENT_ENCODE_SET;
                    tL_message.attachPath = audioEntry.path;
                    TLRPC.TL_messageMediaDocument tL_messageMediaDocument = new TLRPC.TL_messageMediaDocument();
                    tL_message.media = tL_messageMediaDocument;
                    tL_messageMediaDocument.flags |= 3;
                    tL_messageMediaDocument.document = new TLRPC.TL_document();
                    tL_message.flags |= 768;
                    String fileExtension = FileLoader.getFileExtension(file);
                    TLRPC.Document document = tL_message.media.document;
                    document.f1253id = 0L;
                    document.access_hash = 0L;
                    document.file_reference = new byte[0];
                    document.date = tL_message.date;
                    StringBuilder sb = new StringBuilder();
                    sb.append("audio/");
                    if (fileExtension.length() <= 0) {
                        fileExtension = "mp3";
                    }
                    sb.append(fileExtension);
                    document.mime_type = sb.toString();
                    tL_message.media.document.size = (int) file.length();
                    tL_message.media.document.dc_id = 0;
                    TLRPC.TL_documentAttributeAudio tL_documentAttributeAudio = new TLRPC.TL_documentAttributeAudio();
                    tL_documentAttributeAudio.duration = audioEntry.duration;
                    tL_documentAttributeAudio.title = audioEntry.title;
                    tL_documentAttributeAudio.performer = audioEntry.author;
                    tL_documentAttributeAudio.flags = 3 | tL_documentAttributeAudio.flags;
                    tL_message.media.document.attributes.add(tL_documentAttributeAudio);
                    TLRPC.TL_documentAttributeFilename tL_documentAttributeFilename = new TLRPC.TL_documentAttributeFilename();
                    tL_documentAttributeFilename.file_name = file.getName();
                    tL_message.media.document.attributes.add(tL_documentAttributeFilename);
                    audioEntry.messageObject = new MessageObject(this.parentAlert.currentAccount, tL_message, false, true);
                    AudioInfo audioInfo = AudioInfo.getAudioInfo(file);
                    if (audioInfo != null && audioInfo.getCover() != null) {
                        int iM1036dp = AndroidUtilities.m1036dp(44.0f);
                        Bitmap cover = audioInfo.getCover();
                        if (cover.getWidth() > iM1036dp || cover.getHeight() > iM1036dp) {
                            float f = iM1036dp;
                            float fMin = Math.min(f / cover.getWidth(), f / cover.getHeight());
                            audioEntry.messageObject.audioCover = Bitmap.createScaledBitmap(cover, (int) (cover.getWidth() * fMin), (int) (cover.getHeight() * fMin), true);
                        } else {
                            audioEntry.messageObject.audioCover = cover;
                        }
                    }
                    arrayList.add(audioEntry);
                    i--;
                } finally {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$loadAudio$3(arrayList);
                        }
                    });
                }
            }
            cursorQuery.close();
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadAudio$3(arrayList);
            }
        });
    }

    public /* synthetic */ void lambda$loadAudio$3(ArrayList arrayList) {
        this.loadingAudio = false;
        this.audioEntries = arrayList;
        updateWithSavingScroll();
    }

    public void searchChats() {
        AndroidUtilities.cancelRunOnUIThread(this.searchChatsRunnable);
        String str = this.query;
        if (str != null && str.length() > 0 && this.query.length() < 3) {
            if (this.loadingSearchChats) {
                this.loadingSearchChats = false;
                updateWithSavingScroll();
                return;
            }
            return;
        }
        if (!TextUtils.equals(this.lastSearchChatsQuery, this.query)) {
            this.foundInChats.clear();
            this.searchChatsNextRate = 0;
            this.searchChatsHasMore = false;
        }
        if (!this.foundInChats.isEmpty() && !this.searchChatsHasMore) {
            if (this.loadingSearchChats) {
                this.loadingSearchChats = false;
                updateWithSavingScroll();
                return;
            }
            return;
        }
        final int i = this.parentAlert.currentAccount;
        final MessagesController messagesController = MessagesController.getInstance(i);
        ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
        int i2 = this.searchChatsRequestId;
        if (i2 >= 0) {
            connectionsManager.cancelRequest(i2, true);
            this.searchChatsRequestId = -1;
        }
        TLRPC.TL_messages_searchGlobal tL_messages_searchGlobal = new TLRPC.TL_messages_searchGlobal();
        tL_messages_searchGlobal.filter = new TLRPC.TL_inputMessagesFilterMusic();
        String str2 = this.query;
        this.lastSearchChatsQuery = str2;
        if (str2 == null) {
            str2 = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        tL_messages_searchGlobal.f1370q = str2;
        tL_messages_searchGlobal.limit = this.foundInChats.isEmpty() ? 3 : 15;
        if (this.foundInChats.size() > 0) {
            ArrayList<MediaController.AudioEntry> arrayList = this.foundInChats;
            MessageObject messageObject = arrayList.get(arrayList.size() - 1).messageObject;
            tL_messages_searchGlobal.offset_id = messageObject.getId();
            tL_messages_searchGlobal.offset_rate = this.searchChatsNextRate;
            tL_messages_searchGlobal.offset_peer = messagesController.getInputPeer(MessageObject.getPeerId(messageObject.messageOwner.peer_id));
        } else {
            tL_messages_searchGlobal.offset_rate = 0;
            tL_messages_searchGlobal.offset_id = 0;
            tL_messages_searchGlobal.offset_peer = new TLRPC.TL_inputPeerEmpty();
        }
        this.searchChatsRequestId = connectionsManager.sendRequestTyped(tL_messages_searchGlobal, new AiTonesController$$ExternalSyntheticLambda0(), new Utilities.Callback2() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda7
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$searchChats$5(messagesController, i, (TLRPC.messages_Messages) obj, (TLRPC.TL_error) obj2);
            }
        });
        updateWithSavingScroll();
    }

    public /* synthetic */ void lambda$searchChats$5(MessagesController messagesController, int i, TLRPC.messages_Messages messages_messages, TLRPC.TL_error tL_error) {
        TLRPC.TL_documentAttributeAudio tL_documentAttributeAudio;
        this.searchChatsRequestId = -1;
        boolean z = false;
        this.loadingSearchChats = false;
        if (messages_messages != null) {
            messagesController.putUsers(messages_messages.users, false);
            messagesController.putChats(messages_messages.chats, false);
            ArrayList<TLRPC.Message> arrayList = messages_messages.messages;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                TLRPC.Message message = arrayList.get(i2);
                i2++;
                MediaController.AudioEntry audioEntry = new MediaController.AudioEntry();
                MessageObject messageObject = new MessageObject(i, message, false, true);
                audioEntry.messageObject = messageObject;
                TLRPC.Document document = messageObject.getDocument();
                if (document != null) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= document.attributes.size()) {
                            tL_documentAttributeAudio = null;
                            break;
                        } else {
                            if (document.attributes.get(i3) instanceof TLRPC.TL_documentAttributeAudio) {
                                tL_documentAttributeAudio = (TLRPC.TL_documentAttributeAudio) document.attributes.get(i3);
                                break;
                            }
                            i3++;
                        }
                    }
                    if (tL_documentAttributeAudio != null) {
                        audioEntry.author = tL_documentAttributeAudio.performer;
                        audioEntry.title = tL_documentAttributeAudio.title;
                        audioEntry.duration = (int) tL_documentAttributeAudio.duration;
                        this.foundInChats.add(audioEntry);
                    }
                }
            }
            int i4 = messages_messages.next_rate;
            this.searchChatsNextRate = i4;
            if (i4 != 0 || (messages_messages.count > 0 && this.foundInChats.size() < messages_messages.count)) {
                z = true;
            }
            this.searchChatsHasMore = z;
            updateWithSavingScroll();
        }
    }

    public void searchGlobal() {
        String str;
        AndroidUtilities.cancelRunOnUIThread(this.searchGlobalRunnable);
        if (TextUtils.isEmpty(this.query) || this.query.length() < 3) {
            if (this.loadingSearchGlobal) {
                this.loadingSearchGlobal = false;
                updateWithSavingScroll();
                return;
            }
            return;
        }
        if (!TextUtils.equals(this.lastSearchGlobalQuery, this.query)) {
            this.foundGlobal.clear();
            this.searchGlobalHasMore = false;
        }
        final int i = this.parentAlert.currentAccount;
        final MessagesController messagesController = MessagesController.getInstance(i);
        ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
        int i2 = this.searchGlobalRequestId;
        if (i2 >= 0) {
            connectionsManager.cancelRequest(i2, true);
            this.searchGlobalRequestId = -1;
        }
        String str2 = messagesController.config.musicSearchUsername.get();
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        if (this.globalAudioBot == null) {
            this.globalAudioBot = messagesController.getUser(str2);
        }
        if (this.globalAudioBot == null) {
            if (this.resolvingGlobalAudioBot || this.failedToResolveGlobalAudioBot) {
                return;
            }
            this.resolvingGlobalAudioBot = true;
            messagesController.getUserNameResolver().resolve(str2, new Consumer() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda11
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$searchGlobal$6(messagesController, (Long) obj);
                }
            });
            return;
        }
        TLRPC.User currentUser = UserConfig.getInstance(i).getCurrentUser();
        TLRPC.TL_messages_getInlineBotResults tL_messages_getInlineBotResults = new TLRPC.TL_messages_getInlineBotResults();
        tL_messages_getInlineBotResults.bot = messagesController.getInputUser(this.globalAudioBot);
        tL_messages_getInlineBotResults.peer = MessagesController.getInputPeer(currentUser);
        boolean zIsEmpty = this.foundGlobal.isEmpty();
        String str3 = _UrlKt.FRAGMENT_ENCODE_SET;
        if (zIsEmpty || (str = this.globalAudioOffset) == null) {
            str = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        tL_messages_getInlineBotResults.offset = str;
        String str4 = this.query;
        if (str4 != null) {
            str3 = str4;
        }
        this.lastSearchGlobalQuery = str3;
        tL_messages_getInlineBotResults.query = str3;
        this.searchGlobalRequestId = connectionsManager.sendRequestTyped(tL_messages_getInlineBotResults, new AiTonesController$$ExternalSyntheticLambda0(), new Utilities.Callback2() { // from class: org.telegram.ui.Components.ChatAttachAlertAudioLayout$$ExternalSyntheticLambda12
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$searchGlobal$7(messagesController, i, (TLRPC.messages_BotResults) obj, (TLRPC.TL_error) obj2);
            }
        });
        updateWithSavingScroll();
    }

    public /* synthetic */ void lambda$searchGlobal$6(MessagesController messagesController, Long l) {
        this.resolvingGlobalAudioBot = false;
        TLRPC.User user = l == null ? null : messagesController.getUser(l);
        this.globalAudioBot = user;
        this.failedToResolveGlobalAudioBot = user == null;
        if (user != null) {
            searchGlobal();
        }
    }

    public /* synthetic */ void lambda$searchGlobal$7(MessagesController messagesController, int i, TLRPC.messages_BotResults messages_botresults, TLRPC.TL_error tL_error) {
        TLRPC.TL_documentAttributeAudio tL_documentAttributeAudio;
        this.searchGlobalRequestId = -1;
        this.loadingSearchGlobal = false;
        if (messages_botresults != null) {
            messagesController.putUsers(messages_botresults.users, false);
            ArrayList<TLRPC.BotInlineResult> arrayList = messages_botresults.results;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                TLRPC.BotInlineResult botInlineResult = arrayList.get(i2);
                i2++;
                TLRPC.BotInlineResult botInlineResult2 = botInlineResult;
                if (botInlineResult2 instanceof TLRPC.TL_botInlineMediaResult) {
                    TLRPC.TL_botInlineMediaResult tL_botInlineMediaResult = (TLRPC.TL_botInlineMediaResult) botInlineResult2;
                    if (tL_botInlineMediaResult.document != null) {
                        TLRPC.TL_message tL_message = new TLRPC.TL_message();
                        tL_message.out = true;
                        int i3 = this.globalAudioMessageId;
                        this.globalAudioMessageId = i3 - 1;
                        tL_message.f1271id = i3;
                        tL_message.peer_id = new TLRPC.TL_peerUser();
                        TLRPC.TL_peerUser tL_peerUser = new TLRPC.TL_peerUser();
                        tL_message.from_id = tL_peerUser;
                        TLRPC.Peer peer = tL_message.peer_id;
                        long clientUserId = UserConfig.getInstance(i).getClientUserId();
                        tL_peerUser.user_id = clientUserId;
                        peer.user_id = clientUserId;
                        tL_message.date = (int) (System.currentTimeMillis() / 1000);
                        tL_message.message = _UrlKt.FRAGMENT_ENCODE_SET;
                        TLRPC.TL_messageMediaDocument tL_messageMediaDocument = new TLRPC.TL_messageMediaDocument();
                        tL_message.media = tL_messageMediaDocument;
                        tL_messageMediaDocument.flags |= 3;
                        tL_messageMediaDocument.document = tL_botInlineMediaResult.document;
                        tL_message.flags |= 768;
                        MediaController.AudioEntry audioEntry = new MediaController.AudioEntry();
                        MessageObject messageObject = new MessageObject(i, tL_message, false, true);
                        audioEntry.messageObject = messageObject;
                        TLRPC.Document document = messageObject.getDocument();
                        if (document != null) {
                            int i4 = 0;
                            while (true) {
                                if (i4 >= document.attributes.size()) {
                                    tL_documentAttributeAudio = null;
                                    break;
                                } else {
                                    if (document.attributes.get(i4) instanceof TLRPC.TL_documentAttributeAudio) {
                                        tL_documentAttributeAudio = (TLRPC.TL_documentAttributeAudio) document.attributes.get(i4);
                                        break;
                                    }
                                    i4++;
                                }
                            }
                            if (tL_documentAttributeAudio != null) {
                                audioEntry.author = tL_documentAttributeAudio.performer;
                                audioEntry.title = tL_documentAttributeAudio.title;
                                audioEntry.duration = (int) tL_documentAttributeAudio.duration;
                                this.foundGlobal.add(audioEntry);
                            }
                        }
                    }
                }
            }
            this.globalAudioOffset = messages_botresults.next_offset;
            this.searchGlobalHasMore = !TextUtils.isEmpty(r10);
            updateWithSavingScroll();
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onContainerTranslationUpdated(float f) {
        this.currentPanTranslationProgress = f;
        super.onContainerTranslationUpdated(f);
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
        if (i == 0) {
            this.fadeView.setAlpha(f);
            this.fadeView.setVisibility(f > 0.0f ? 0 : 4);
        }
    }

    public static final class EmptyView extends FrameLayout implements Theme.Colorable {
        public BackupImageView imageView;
        public LinearLayout layout;
        private final Theme.ResourcesProvider resourcesProvider;
        public TextView subtitleView;
        public TextView titleView;

        public EmptyView(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.resourcesProvider = resourcesProvider;
            setPadding(0, AndroidUtilities.m1036dp(42.0f), 0, AndroidUtilities.m1036dp(42.0f));
            setTag(-33024);
            LinearLayout linearLayout = new LinearLayout(context);
            this.layout = linearLayout;
            linearLayout.setOrientation(1);
            addView(this.layout, LayoutHelper.createLinear(-1, -2, 17));
            BackupImageView backupImageView = new BackupImageView(context);
            this.imageView = backupImageView;
            backupImageView.setImageDrawable(new RLottieDrawable(C2797R.raw.utyan_empty, "utyan_empty", AndroidUtilities.m1036dp(120.0f), AndroidUtilities.m1036dp(120.0f)));
            this.layout.addView(this.imageView, LayoutHelper.createLinear(120, 120, 17, 0, 0, 0, 0));
            TextView textView = new TextView(context);
            this.titleView = textView;
            textView.setTextSize(1, 20.0f);
            this.titleView.setTypeface(AndroidUtilities.bold());
            this.titleView.setGravity(17);
            this.layout.addView(this.titleView, LayoutHelper.createLinear(-1, -2, 17, 32, 12, 32, 8));
            TextView textView2 = new TextView(context);
            this.subtitleView = textView2;
            textView2.setTextSize(1, 14.0f);
            this.subtitleView.setGravity(17);
            this.layout.addView(this.subtitleView, LayoutHelper.createLinear(-1, -2, 17, 32, 0, 32, 0));
            updateColors();
        }

        public void set(CharSequence charSequence, CharSequence charSequence2) {
            this.titleView.setText(charSequence);
            this.subtitleView.setText(charSequence2);
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), i2);
        }

        @Override // org.telegram.ui.ActionBar.Theme.Colorable
        public void updateColors() {
            this.titleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider));
            this.subtitleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText, this.resourcesProvider));
        }

        public static final class Factory extends UItem.UItemFactory<EmptyView> {
            @Override // org.telegram.ui.Components.UItem.UItemFactory
            /* JADX INFO: renamed from: isClickable */
            public boolean getIsClickableValue() {
                return false;
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            /* JADX INFO: renamed from: isShadow */
            public boolean getIsShadowValue() {
                return true;
            }

            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public EmptyView createView(Context context, RecyclerListView recyclerListView, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new EmptyView(context, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((EmptyView) view).set(uItem.text, uItem.subtext);
            }

            /* JADX INFO: renamed from: as */
            public static UItem m1146as(CharSequence charSequence, CharSequence charSequence2) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.text = charSequence;
                uItemOfFactory.subtext = charSequence2;
                return uItemOfFactory;
            }
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_dialogScrollGlow));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{SharedAudioCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_checkbox));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{SharedAudioCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_checkboxCheck));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedAudioCell.class}, Theme.chat_contextResult_titleTextPaint, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedAudioCell.class}, Theme.chat_contextResult_descriptionTextPaint, null, null, Theme.key_windowBackgroundWhiteGrayText2));
        return arrayList;
    }
}
