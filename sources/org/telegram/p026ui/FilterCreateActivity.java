package org.telegram.p026ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.text.style.ReplacementSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.components.IconSelectorAlert;
import com.exteragram.messenger.utils.p014ui.FolderIcons;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.ActionBarMenu;
import org.telegram.p026ui.ActionBar.ActionBarMenuItem;
import org.telegram.p026ui.ActionBar.AlertDialog;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.BottomSheet;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.ActionBar.ThemeDescription;
import org.telegram.p026ui.Cells.EditEmojiTextCell;
import org.telegram.p026ui.Cells.HeaderCell;
import org.telegram.p026ui.Cells.ShadowSectionCell;
import org.telegram.p026ui.Cells.TextCell;
import org.telegram.p026ui.Cells.TextInfoPrivacyCell;
import org.telegram.p026ui.Cells.UserCell;
import org.telegram.p026ui.Components.AnimatedColor;
import org.telegram.p026ui.Components.AnimatedEmojiDrawable;
import org.telegram.p026ui.Components.AnimatedEmojiSpan;
import org.telegram.p026ui.Components.AnimatedTextView;
import org.telegram.p026ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.p026ui.Components.BulletinFactory;
import org.telegram.p026ui.Components.CombinedDrawable;
import org.telegram.p026ui.Components.CubicBezierInterpolator;
import org.telegram.p026ui.Components.EditTextCaption;
import org.telegram.p026ui.Components.EditTextEmoji;
import org.telegram.p026ui.Components.EditTextSuggestionsFix;
import org.telegram.p026ui.Components.EmojiView;
import org.telegram.p026ui.Components.FolderBottomSheet;
import org.telegram.p026ui.Components.HintView;
import org.telegram.p026ui.Components.ItemOptions;
import org.telegram.p026ui.Components.LayoutHelper;
import org.telegram.p026ui.Components.ListView.AdapterWithDiffUtils;
import org.telegram.p026ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p026ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p026ui.Components.QRCodeBottomSheet;
import org.telegram.p026ui.Components.RLottieImageView;
import org.telegram.p026ui.Components.RecyclerListView;
import org.telegram.p026ui.Components.ScaleStateListAnimator;
import org.telegram.p026ui.Components.SizeNotifierFrameLayout;
import org.telegram.p026ui.Components.Text;
import org.telegram.p026ui.Components.UndoView;
import org.telegram.p026ui.Components.spoilers.SpoilersTextView;
import org.telegram.p026ui.FilterCreateActivity;
import org.telegram.p026ui.PeerColorActivity;
import org.telegram.p026ui.UsersSelectActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p025tl.TL_chatlists;

/* JADX INFO: loaded from: classes6.dex */
public class FilterCreateActivity extends BaseFragment {
    private ListAdapter adapter;
    private CreateLinkCell createLinkCell;
    private boolean creatingNew;
    private boolean doNotCloseWhenSave;
    private ActionBarMenuItem doneItem;
    private boolean excludeExpanded;
    private MessagesController.DialogFilter filter;
    private HeaderCellColorPreview folderTagsHeader;
    private boolean hasUserChanged;
    private boolean includeExpanded;
    private ArrayList invites;
    private ArrayList items;
    private RecyclerListView listView;
    private boolean loadingInvites;
    private boolean nameChangedManually;
    private EditEmojiTextCell nameEditTextCell;
    private HeaderCellWithRight nameHeaderCell;
    private int nameRow;
    private ArrayList newAlwaysShow;
    private boolean newFilterAnimations;
    private int newFilterColor;
    private String newFilterEmoticon;
    private int newFilterFlags;
    private CharSequence newFilterName;
    private ArrayList newNeverShow;
    private LongSparseIntArray newPinned;
    private ArrayList oldItems;
    private int requestingInvitesReqId;
    private HintView saveHintView;
    float shiftDp;
    private Runnable showBulletinOnResume;
    private boolean showedUpdateBulletin;
    private UndoView undoView;

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean isSupportEdgeToEdge() {
        return true;
    }

    private boolean canCreateLink() {
        return !(TextUtils.isEmpty(this.newFilterName) && TextUtils.isEmpty(this.filter.name)) && (this.newFilterFlags & (~(MessagesController.DIALOG_FILTER_FLAG_CHATLIST | MessagesController.DIALOG_FILTER_FLAG_CHATLIST_ADMIN))) == 0 && this.newNeverShow.isEmpty() && !this.newAlwaysShow.isEmpty();
    }

    public static class HintInnerCell extends FrameLayout {
        private RLottieImageView imageView;

        public HintInnerCell(Context context) {
            super(context);
            RLottieImageView rLottieImageView = new RLottieImageView(context);
            this.imageView = rLottieImageView;
            rLottieImageView.setAnimation(C2702R.raw.filter_new, 100, 100);
            this.imageView.setScaleType(ImageView.ScaleType.CENTER);
            this.imageView.playAnimation();
            addView(this.imageView, LayoutHelper.createFrame(100, 100.0f, 17, 0.0f, 0.0f, 0.0f, 0.0f));
            this.imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$HintInnerCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(view);
                }
            });
        }

        public /* synthetic */ void lambda$new$0(View view) {
            if (this.imageView.isPlaying()) {
                return;
            }
            this.imageView.setProgress(0.0f);
            this.imageView.playAnimation();
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(156.0f), TLObject.FLAG_30));
        }
    }

    public FilterCreateActivity() {
        this(null, null);
    }

    public FilterCreateActivity(MessagesController.DialogFilter dialogFilter) {
        this(dialogFilter, null);
    }

    public FilterCreateActivity(MessagesController.DialogFilter dialogFilter, ArrayList arrayList) {
        this.nameRow = -1;
        this.newFilterAnimations = true;
        this.invites = new ArrayList();
        this.oldItems = new ArrayList();
        this.items = new ArrayList();
        this.shiftDp = -5.0f;
        this.filter = dialogFilter;
        if (dialogFilter == null) {
            MessagesController.DialogFilter dialogFilter2 = new MessagesController.DialogFilter();
            this.filter = dialogFilter2;
            dialogFilter2.f1551id = 2;
            while (getMessagesController().dialogFiltersById.get(this.filter.f1551id) != null) {
                this.filter.f1551id++;
            }
            MessagesController.DialogFilter dialogFilter3 = this.filter;
            dialogFilter3.name = _UrlKt.FRAGMENT_ENCODE_SET;
            dialogFilter3.color = (int) (Math.random() * 8.0d);
            this.creatingNew = true;
        }
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(AndroidUtilities.m1081dp(17.0f));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.filter.name);
        this.newFilterName = spannableStringBuilder;
        CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(spannableStringBuilder, textPaint.getFontMetricsInt(), false);
        this.newFilterName = charSequenceReplaceEmoji;
        this.newFilterName = MessageObject.replaceAnimatedEmoji(charSequenceReplaceEmoji, this.filter.entities, textPaint.getFontMetricsInt());
        boolean z = !this.filter.title_noanimate;
        this.newFilterAnimations = z;
        AnimatedEmojiDrawable.toggleAnimations(this.currentAccount, z);
        MessagesController.DialogFilter dialogFilter4 = this.filter;
        this.newFilterEmoticon = dialogFilter4.emoticon;
        this.newFilterFlags = dialogFilter4.flags;
        this.newFilterColor = dialogFilter4.color;
        ArrayList arrayList2 = new ArrayList(this.filter.alwaysShow);
        this.newAlwaysShow = arrayList2;
        if (arrayList != null) {
            arrayList2.addAll(arrayList);
        }
        this.newNeverShow = new ArrayList(this.filter.neverShow);
        this.newPinned = this.filter.pinnedDialogs.clone();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        updateRows();
        return super.onFragmentCreate();
    }

    public void loadInvites() {
        MessagesController.DialogFilter dialogFilter;
        if (this.loadingInvites || (dialogFilter = this.filter) == null || !dialogFilter.isChatlist()) {
            return;
        }
        this.loadingInvites = true;
        TL_chatlists.TL_chatlists_getExportedInvites tL_chatlists_getExportedInvites = new TL_chatlists.TL_chatlists_getExportedInvites();
        TL_chatlists.TL_inputChatlistDialogFilter tL_inputChatlistDialogFilter = new TL_chatlists.TL_inputChatlistDialogFilter();
        tL_chatlists_getExportedInvites.chatlist = tL_inputChatlistDialogFilter;
        tL_inputChatlistDialogFilter.filter_id = this.filter.f1551id;
        this.requestingInvitesReqId = getConnectionsManager().sendRequest(tL_chatlists_getExportedInvites, new RequestDelegate() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda19
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$loadInvites$1(tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$loadInvites$1(final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadInvites$0(tLObject);
            }
        });
    }

    public /* synthetic */ void lambda$loadInvites$0(TLObject tLObject) {
        this.loadingInvites = false;
        if (tLObject instanceof TL_chatlists.TL_chatlists_exportedInvites) {
            TL_chatlists.TL_chatlists_exportedInvites tL_chatlists_exportedInvites = (TL_chatlists.TL_chatlists_exportedInvites) tLObject;
            getMessagesController().putChats(tL_chatlists_exportedInvites.chats, false);
            getMessagesController().putUsers(tL_chatlists_exportedInvites.users, false);
            this.invites.clear();
            this.invites.addAll(tL_chatlists_exportedInvites.invites);
            updateRows();
        }
        this.requestingInvitesReqId = 0;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.requestingInvitesReqId != 0) {
            getConnectionsManager().cancelRequest(this.requestingInvitesReqId, true);
        }
    }

    private void updateRows() {
        updateRows(true);
    }

    private void updateRows(boolean z) {
        String string;
        this.oldItems.clear();
        this.oldItems.addAll(this.items);
        this.items.clear();
        this.items.add(ItemInner.asAnimatedHeader(LocaleController.getString(C2702R.string.FilterNameHeader), hasAnimatedEmojis(this.newFilterName) ? LocaleController.getString(this.newFilterAnimations ? C2702R.string.FilterNameAnimationsDisable : C2702R.string.FilterNameAnimationsEnable) : null, new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateRows$2(view);
            }
        }));
        this.nameRow = this.items.size();
        this.items.add(ItemInner.asEdit());
        this.items.add(ItemInner.asShadow(null));
        this.items.add(ItemInner.asHeader(LocaleController.getString(C2702R.string.FilterInclude)));
        this.items.add(ItemInner.asButton(C2702R.drawable.msg2_chats_add, LocaleController.getString(C2702R.string.FilterAddChats), false).whenClicked(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateRows$3(view);
            }
        }));
        if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_CONTACTS) != 0) {
            this.items.add(ItemInner.asChat(true, LocaleController.getString(C2702R.string.FilterContacts), "contacts", MessagesController.DIALOG_FILTER_FLAG_CONTACTS));
        }
        if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS) != 0) {
            this.items.add(ItemInner.asChat(true, LocaleController.getString(C2702R.string.FilterNonContacts), "non_contacts", MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS));
        }
        if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_GROUPS) != 0) {
            this.items.add(ItemInner.asChat(true, LocaleController.getString(C2702R.string.FilterGroups), "groups", MessagesController.DIALOG_FILTER_FLAG_GROUPS));
        }
        if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_CHANNELS) != 0) {
            this.items.add(ItemInner.asChat(true, LocaleController.getString(C2702R.string.FilterChannels), "channels", MessagesController.DIALOG_FILTER_FLAG_CHANNELS));
        }
        if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_BOTS) != 0) {
            this.items.add(ItemInner.asChat(true, LocaleController.getString(C2702R.string.FilterBots), "bots", MessagesController.DIALOG_FILTER_FLAG_BOTS));
        }
        if (!this.newAlwaysShow.isEmpty()) {
            int size = (this.includeExpanded || this.newAlwaysShow.size() < 8) ? this.newAlwaysShow.size() : Math.min(5, this.newAlwaysShow.size());
            for (int i = 0; i < size; i++) {
                this.items.add(ItemInner.asChat(true, ((Long) this.newAlwaysShow.get(i)).longValue()));
            }
            if (size != this.newAlwaysShow.size()) {
                this.items.add(ItemInner.asButton(C2702R.drawable.arrow_more, LocaleController.formatPluralString("FilterShowMoreChats", this.newAlwaysShow.size() - 5, new Object[0]), false).whenClicked(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$updateRows$4(view);
                    }
                }));
            }
        }
        this.items.add(ItemInner.asShadow(LocaleController.getString(C2702R.string.FilterIncludeInfo)));
        if (!this.filter.isChatlist()) {
            this.items.add(ItemInner.asHeader(LocaleController.getString(C2702R.string.FilterExclude)));
            this.items.add(ItemInner.asButton(C2702R.drawable.msg2_chats_add, LocaleController.getString(C2702R.string.FilterRemoveChats), false).whenClicked(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateRows$5(view);
                }
            }));
            if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED) != 0) {
                this.items.add(ItemInner.asChat(false, LocaleController.getString(C2702R.string.FilterMuted), "muted", MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED));
            }
            if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ) != 0) {
                this.items.add(ItemInner.asChat(false, LocaleController.getString(C2702R.string.FilterRead), "read", MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ));
            }
            if ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_ARCHIVED) != 0) {
                this.items.add(ItemInner.asChat(false, LocaleController.getString(C2702R.string.FilterArchived), "archived", MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_ARCHIVED));
            }
            if (!this.newNeverShow.isEmpty()) {
                int size2 = (this.excludeExpanded || this.newNeverShow.size() < 8) ? this.newNeverShow.size() : Math.min(5, this.newNeverShow.size());
                for (int i2 = 0; i2 < size2; i2++) {
                    this.items.add(ItemInner.asChat(false, ((Long) this.newNeverShow.get(i2)).longValue()));
                }
                if (size2 != this.newNeverShow.size()) {
                    this.items.add(ItemInner.asButton(C2702R.drawable.arrow_more, LocaleController.formatPluralString("FilterShowMoreChats", this.newNeverShow.size() - 5, new Object[0]), false).whenClicked(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda7
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$updateRows$6(view);
                        }
                    }));
                }
            }
            this.items.add(ItemInner.asShadow(LocaleController.getString(C2702R.string.FilterExcludeInfo)));
        }
        if (getMessagesController().folderTags || !getUserConfig().isPremium()) {
            this.items.add(new ItemInner(9, false));
            this.items.add(new ItemInner(10, false));
            this.items.add(ItemInner.asShadow(LocaleController.getString(C2702R.string.FolderTagColorInfo)));
        }
        if (this.invites.isEmpty()) {
            this.items.add(ItemInner.asHeader(LocaleController.getString(C2702R.string.FilterShareFolder), true));
            this.items.add(ItemInner.asButton(C2702R.drawable.msg2_link2, LocaleController.getString(C2702R.string.FilterShareFolderButton), false));
            this.items.add(ItemInner.asShadow(LocaleController.getString(C2702R.string.FilterInviteLinksHintNew)));
        } else {
            this.items.add(ItemInner.asHeader(LocaleController.getString(C2702R.string.FilterInviteLinks), true));
            this.items.add(ItemInner.asCreateLink());
            for (int i3 = 0; i3 < this.invites.size(); i3++) {
                this.items.add(ItemInner.asLink((TL_chatlists.TL_exportedChatlistInvite) this.invites.get(i3)));
            }
            ArrayList arrayList = this.items;
            MessagesController.DialogFilter dialogFilter = this.filter;
            if (dialogFilter != null && dialogFilter.isChatlist()) {
                string = LocaleController.getString(C2702R.string.FilterInviteLinksHintNew);
            } else {
                string = LocaleController.getString(C2702R.string.FilterInviteLinksHint);
            }
            arrayList.add(ItemInner.asShadow(string));
        }
        if (!this.creatingNew) {
            this.items.add(ItemInner.asButton(0, LocaleController.getString(C2702R.string.FilterDelete), true).whenClicked(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.deleteFolder(view);
                }
            }));
            this.items.add(ItemInner.asShadow(null));
        }
        ListAdapter listAdapter = this.adapter;
        if (listAdapter != null) {
            if (z) {
                listAdapter.setItems(this.oldItems, this.items);
            } else {
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    public /* synthetic */ void lambda$updateRows$2(View view) {
        String string;
        this.newFilterAnimations = !this.newFilterAnimations;
        HeaderCellWithRight headerCellWithRight = this.nameHeaderCell;
        if (headerCellWithRight != null) {
            AnimatedTextView animatedTextView = headerCellWithRight.rightTextView;
            if (hasAnimatedEmojis(this.newFilterName)) {
                string = LocaleController.getString(this.newFilterAnimations ? C2702R.string.FilterNameAnimationsDisable : C2702R.string.FilterNameAnimationsEnable);
            } else {
                string = null;
            }
            animatedTextView.setText(string);
        }
        AnimatedEmojiDrawable.toggleAnimations(this.currentAccount, this.newFilterAnimations);
        checkDoneButton(true);
        ActionBar actionBar = this.actionBar;
        if (actionBar != null) {
            if (actionBar.getTitleTextView() != null) {
                this.actionBar.getTitleTextView().setEmojiCacheType(this.newFilterAnimations ? 0 : 26);
            }
            if (this.actionBar.getTitleTextView2() != null) {
                this.actionBar.getTitleTextView2().setEmojiCacheType(this.newFilterAnimations ? 0 : 26);
            }
        }
    }

    public /* synthetic */ void lambda$updateRows$3(View view) {
        selectChatsFor(true);
    }

    public /* synthetic */ void lambda$updateRows$4(View view) {
        this.includeExpanded = true;
        updateRows();
    }

    public /* synthetic */ void lambda$updateRows$5(View view) {
        selectChatsFor(false);
    }

    public /* synthetic */ void lambda$updateRows$6(View view) {
        this.excludeExpanded = true;
        updateRows();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(C2702R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        ActionBarMenu actionBarMenuCreateMenu = this.actionBar.createMenu();
        if (this.creatingNew) {
            this.actionBar.setTitle(LocaleController.getString(C2702R.string.FilterNew));
        } else {
            Paint.FontMetricsInt titleFontMetricsInt = this.actionBar.getTitleFontMetricsInt();
            this.actionBar.setTitle(MessageObject.replaceAnimatedEmoji(Emoji.replaceEmoji(this.filter.name, titleFontMetricsInt, false), this.filter.entities, titleFontMetricsInt));
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                if (actionBar.getTitleTextView() != null) {
                    this.actionBar.getTitleTextView().setEmojiCacheType(this.newFilterAnimations ? 0 : 26);
                }
                if (this.actionBar.getTitleTextView2() != null) {
                    this.actionBar.getTitleTextView2().setEmojiCacheType(this.newFilterAnimations ? 0 : 26);
                }
            }
        }
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.FilterCreateActivity.1
            C53971() {
            }

            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    if (FilterCreateActivity.this.checkDiscard(true)) {
                        FilterCreateActivity.this.finishFragment();
                    }
                } else if (i == 1) {
                    FilterCreateActivity.this.processDone();
                }
            }
        });
        this.doneItem = actionBarMenuCreateMenu.addItem(1, LocaleController.getString(C2702R.string.Save).toUpperCase());
        C53982 c53982 = new SizeNotifierFrameLayout(context) { // from class: org.telegram.ui.FilterCreateActivity.2
            C53982(Context context2) {
                super(context2);
            }

            @Override // android.view.ViewGroup
            public void addView(View view) {
                if (view instanceof EmojiView) {
                    ViewGroup.LayoutParams layoutParams = ((EmojiView) view).getLayoutParams();
                    if (layoutParams == null) {
                        layoutParams = new FrameLayout.LayoutParams(-1, -2);
                    }
                    if (layoutParams instanceof FrameLayout.LayoutParams) {
                        ((FrameLayout.LayoutParams) layoutParams).gravity = 87;
                    }
                    view.setLayoutParams(layoutParams);
                }
                super.addView(view);
            }

            /* JADX WARN: Removed duplicated region for block: B:187:0x006d  */
            /* JADX WARN: Removed duplicated region for block: B:196:0x0083  */
            /* JADX WARN: Removed duplicated region for block: B:199:0x0093  */
            @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected void onLayout(boolean r11, int r12, int r13, int r14, int r15) {
                /*
                    r10 = this;
                    int r11 = r10.getChildCount()
                    int r0 = r10.measureKeyboardHeight()
                    int r1 = r10.getPaddingLeft()
                    int r14 = r14 - r12
                    int r12 = r10.getPaddingRight()
                    int r14 = r14 - r12
                    int r12 = r10.getPaddingTop()
                    int r15 = r15 - r13
                    int r13 = r10.getPaddingBottom()
                    int r13 = r15 - r13
                    r2 = 0
                L1e:
                    if (r2 >= r11) goto La8
                    android.view.View r3 = r10.getChildAt(r2)
                    int r4 = r3.getVisibility()
                    r5 = 8
                    if (r4 == r5) goto La4
                    android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
                    android.widget.FrameLayout$LayoutParams r4 = (android.widget.FrameLayout.LayoutParams) r4
                    int r5 = r3.getMeasuredWidth()
                    int r6 = r3.getMeasuredHeight()
                    int r7 = r4.gravity
                    r8 = -1
                    if (r7 != r8) goto L41
                    r7 = 51
                L41:
                    int r8 = r10.getLayoutDirection()
                    int r8 = android.view.Gravity.getAbsoluteGravity(r7, r8)
                    r7 = r7 & 112(0x70, float:1.57E-43)
                    r8 = r8 & 7
                    r9 = 1
                    if (r8 == r9) goto L5d
                    r9 = 5
                    if (r8 == r9) goto L57
                    int r8 = r4.leftMargin
                    int r8 = r8 + r1
                    goto L69
                L57:
                    int r8 = r14 - r5
                    int r9 = r4.rightMargin
                L5b:
                    int r8 = r8 - r9
                    goto L69
                L5d:
                    int r8 = r14 - r1
                    int r8 = r8 - r5
                    int r8 = r8 / 2
                    int r8 = r8 + r1
                    int r9 = r4.leftMargin
                    int r8 = r8 + r9
                    int r9 = r4.rightMargin
                    goto L5b
                L69:
                    r9 = 16
                    if (r7 == r9) goto L83
                    r9 = 48
                    if (r7 == r9) goto L80
                    r9 = 80
                    if (r7 == r9) goto L79
                    int r4 = r4.topMargin
                L77:
                    int r4 = r4 + r12
                    goto L8f
                L79:
                    int r7 = r13 - r6
                    int r4 = r4.bottomMargin
                L7d:
                    int r4 = r7 - r4
                    goto L8f
                L80:
                    int r4 = r4.topMargin
                    goto L77
                L83:
                    int r7 = r13 - r12
                    int r7 = r7 - r6
                    int r7 = r7 / 2
                    int r7 = r7 + r12
                    int r9 = r4.topMargin
                    int r7 = r7 + r9
                    int r4 = r4.bottomMargin
                    goto L7d
                L8f:
                    boolean r7 = r3 instanceof org.telegram.p026ui.Components.EmojiView
                    if (r7 == 0) goto L9f
                    boolean r4 = org.telegram.messenger.AndroidUtilities.isTablet()
                    if (r4 == 0) goto L9c
                    int r4 = r15 - r6
                    goto L9f
                L9c:
                    int r4 = r15 + r0
                    int r4 = r4 - r6
                L9f:
                    int r5 = r5 + r8
                    int r6 = r6 + r4
                    r3.layout(r8, r4, r5, r6)
                La4:
                    int r2 = r2 + 1
                    goto L1e
                La8:
                    super.notifyHeightChanged()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.FilterCreateActivity.C53982.onLayout(boolean, int, int, int, int):void");
            }
        };
        this.fragmentView = c53982;
        C53982 c539822 = c53982;
        c539822.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        C53993 c53993 = new RecyclerListView(context2) { // from class: org.telegram.ui.FilterCreateActivity.3
            @Override // android.view.ViewGroup, android.view.View
            public boolean requestFocus(int i, Rect rect) {
                return false;
            }

            C53993(Context context2) {
                super(context2);
            }

            @Override // org.telegram.p026ui.Components.RecyclerListView
            public Integer getSelectorColor(int i) {
                ItemInner itemInner = (i < 0 || i >= FilterCreateActivity.this.items.size()) ? null : (ItemInner) FilterCreateActivity.this.items.get(i);
                if (itemInner != null && itemInner.isRed) {
                    return Integer.valueOf(Theme.multAlpha(getThemedColor(Theme.key_text_RedRegular), 0.12f));
                }
                return Integer.valueOf(getThemedColor(Theme.key_listSelector));
            }
        };
        this.listView = c53993;
        c53993.setSections();
        this.actionBar.setAdaptiveBackground(this.listView);
        this.listView.setLayoutManager(new LinearLayoutManager(context2, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        c539822.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerListView recyclerListView = this.listView;
        ListAdapter listAdapter = new ListAdapter(context2);
        this.adapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda9
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$createView$8(view, i);
            }
        });
        this.listView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda10
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view, int i) {
                return this.f$0.lambda$createView$9(view, i);
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setSupportsChangeAnimations(false);
        defaultItemAnimator.setDelayAnimations(false);
        defaultItemAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        defaultItemAnimator.setDurations(350L);
        this.listView.setItemAnimator(defaultItemAnimator);
        this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.FilterCreateActivity.4
            C54004() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (!FilterCreateActivity.this.listView.scrollingByUser || FilterCreateActivity.this.nameEditTextCell == null || FilterCreateActivity.this.nameEditTextCell.editTextEmoji == null) {
                    return;
                }
                if (FilterCreateActivity.this.nameEditTextCell.editTextEmoji.isPopupShowing()) {
                    FilterCreateActivity.this.nameEditTextCell.editTextEmoji.hidePopup(true);
                } else {
                    FilterCreateActivity.this.nameEditTextCell.editTextEmoji.closeKeyboard();
                }
            }
        });
        checkDoneButton(false);
        loadInvites();
        return this.fragmentView;
    }

    /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$1 */
    class C53971 extends ActionBar.ActionBarMenuOnItemClick {
        C53971() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if (i == -1) {
                if (FilterCreateActivity.this.checkDiscard(true)) {
                    FilterCreateActivity.this.finishFragment();
                }
            } else if (i == 1) {
                FilterCreateActivity.this.processDone();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$2 */
    class C53982 extends SizeNotifierFrameLayout {
        C53982(Context context2) {
            super(context2);
        }

        @Override // android.view.ViewGroup
        public void addView(View view) {
            if (view instanceof EmojiView) {
                ViewGroup.LayoutParams layoutParams = ((EmojiView) view).getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new FrameLayout.LayoutParams(-1, -2);
                }
                if (layoutParams instanceof FrameLayout.LayoutParams) {
                    ((FrameLayout.LayoutParams) layoutParams).gravity = 87;
                }
                view.setLayoutParams(layoutParams);
            }
            super.addView(view);
        }

        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean v, int v2, int v3, int v4, int v5) {
            /*
                this = this;
                int r11 = r10.getChildCount()
                int r0 = r10.measureKeyboardHeight()
                int r1 = r10.getPaddingLeft()
                int r14 = r14 - r12
                int r12 = r10.getPaddingRight()
                int r14 = r14 - r12
                int r12 = r10.getPaddingTop()
                int r15 = r15 - r13
                int r13 = r10.getPaddingBottom()
                int r13 = r15 - r13
                r2 = 0
            L1e:
                if (r2 >= r11) goto La8
                android.view.View r3 = r10.getChildAt(r2)
                int r4 = r3.getVisibility()
                r5 = 8
                if (r4 == r5) goto La4
                android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
                android.widget.FrameLayout$LayoutParams r4 = (android.widget.FrameLayout.LayoutParams) r4
                int r5 = r3.getMeasuredWidth()
                int r6 = r3.getMeasuredHeight()
                int r7 = r4.gravity
                r8 = -1
                if (r7 != r8) goto L41
                r7 = 51
            L41:
                int r8 = r10.getLayoutDirection()
                int r8 = android.view.Gravity.getAbsoluteGravity(r7, r8)
                r7 = r7 & 112(0x70, float:1.57E-43)
                r8 = r8 & 7
                r9 = 1
                if (r8 == r9) goto L5d
                r9 = 5
                if (r8 == r9) goto L57
                int r8 = r4.leftMargin
                int r8 = r8 + r1
                goto L69
            L57:
                int r8 = r14 - r5
                int r9 = r4.rightMargin
            L5b:
                int r8 = r8 - r9
                goto L69
            L5d:
                int r8 = r14 - r1
                int r8 = r8 - r5
                int r8 = r8 / 2
                int r8 = r8 + r1
                int r9 = r4.leftMargin
                int r8 = r8 + r9
                int r9 = r4.rightMargin
                goto L5b
            L69:
                r9 = 16
                if (r7 == r9) goto L83
                r9 = 48
                if (r7 == r9) goto L80
                r9 = 80
                if (r7 == r9) goto L79
                int r4 = r4.topMargin
            L77:
                int r4 = r4 + r12
                goto L8f
            L79:
                int r7 = r13 - r6
                int r4 = r4.bottomMargin
            L7d:
                int r4 = r7 - r4
                goto L8f
            L80:
                int r4 = r4.topMargin
                goto L77
            L83:
                int r7 = r13 - r12
                int r7 = r7 - r6
                int r7 = r7 / 2
                int r7 = r7 + r12
                int r9 = r4.topMargin
                int r7 = r7 + r9
                int r4 = r4.bottomMargin
                goto L7d
            L8f:
                boolean r7 = r3 instanceof org.telegram.p026ui.Components.EmojiView
                if (r7 == 0) goto L9f
                boolean r4 = org.telegram.messenger.AndroidUtilities.isTablet()
                if (r4 == 0) goto L9c
                int r4 = r15 - r6
                goto L9f
            L9c:
                int r4 = r15 + r0
                int r4 = r4 - r6
            L9f:
                int r5 = r5 + r8
                int r6 = r6 + r4
                r3.layout(r8, r4, r5, r6)
            La4:
                int r2 = r2 + 1
                goto L1e
            La8:
                super.notifyHeightChanged()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.FilterCreateActivity.C53982.onLayout(boolean, int, int, int, int):void");
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$3 */
    class C53993 extends RecyclerListView {
        @Override // android.view.ViewGroup, android.view.View
        public boolean requestFocus(int i, Rect rect) {
            return false;
        }

        C53993(Context context2) {
            super(context2);
        }

        @Override // org.telegram.p026ui.Components.RecyclerListView
        public Integer getSelectorColor(int i) {
            ItemInner itemInner = (i < 0 || i >= FilterCreateActivity.this.items.size()) ? null : (ItemInner) FilterCreateActivity.this.items.get(i);
            if (itemInner != null && itemInner.isRed) {
                return Integer.valueOf(Theme.multAlpha(getThemedColor(Theme.key_text_RedRegular), 0.12f));
            }
            return Integer.valueOf(getThemedColor(Theme.key_listSelector));
        }
    }

    public /* synthetic */ void lambda$createView$8(View view, int i) {
        final ItemInner itemInner;
        if (getParentActivity() == null || (itemInner = (ItemInner) this.items.get(i)) == null) {
            return;
        }
        if (itemInner.onClickListener != null) {
            itemInner.onClickListener.onClick(view);
            return;
        }
        int i2 = itemInner.viewType;
        if (i2 == 1) {
            UserCell userCell = (UserCell) view;
            showRemoveAlert(itemInner, userCell.getName(), userCell.getCurrentObject(), itemInner.include);
            return;
        }
        if (i2 == 7) {
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$7(itemInner);
                }
            };
            if (this.doneItem.isEnabled()) {
                save(false, runnable);
                return;
            } else {
                runnable.run();
                return;
            }
        }
        if (i2 == 8 || (i2 == 4 && itemInner.iconResId == C2702R.drawable.msg2_link2)) {
            onClickCreateLink(view);
        }
    }

    public /* synthetic */ void lambda$createView$7(ItemInner itemInner) {
        FilterChatlistActivity filterChatlistActivity = new FilterChatlistActivity(this.filter, itemInner.link);
        filterChatlistActivity.setOnEdit(new FilterCreateActivity$$ExternalSyntheticLambda23(this));
        filterChatlistActivity.setOnDelete(new FilterCreateActivity$$ExternalSyntheticLambda24(this));
        presentFragment(filterChatlistActivity);
    }

    public /* synthetic */ boolean lambda$createView$9(View view, int i) {
        ItemInner itemInner = (ItemInner) this.items.get(i);
        if (itemInner == null || !(view instanceof UserCell)) {
            return false;
        }
        UserCell userCell = (UserCell) view;
        showRemoveAlert(itemInner, userCell.getName(), userCell.getCurrentObject(), itemInner.include);
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$4 */
    class C54004 extends RecyclerView.OnScrollListener {
        C54004() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (!FilterCreateActivity.this.listView.scrollingByUser || FilterCreateActivity.this.nameEditTextCell == null || FilterCreateActivity.this.nameEditTextCell.editTextEmoji == null) {
                return;
            }
            if (FilterCreateActivity.this.nameEditTextCell.editTextEmoji.isPopupShowing()) {
                FilterCreateActivity.this.nameEditTextCell.editTextEmoji.hidePopup(true);
            } else {
                FilterCreateActivity.this.nameEditTextCell.editTextEmoji.closeKeyboard();
            }
        }
    }

    public boolean hasAnimatedEmojis(CharSequence charSequence) {
        if (!(charSequence instanceof Spanned)) {
            return false;
        }
        Spanned spanned = (Spanned) charSequence;
        AnimatedEmojiSpan[] animatedEmojiSpanArr = (AnimatedEmojiSpan[]) spanned.getSpans(0, spanned.length(), AnimatedEmojiSpan.class);
        return animatedEmojiSpanArr != null && animatedEmojiSpanArr.length > 0;
    }

    private void onClickCreateLink(View view) {
        if (this.creatingNew && this.doneItem.getAlpha() > 0.0f) {
            float f = -this.shiftDp;
            this.shiftDp = f;
            AndroidUtilities.shakeViewSpring(view, f);
            BotWebViewVibrationEffect.APP_ERROR.vibrate();
            this.doNotCloseWhenSave = true;
            showSaveHint();
            return;
        }
        if (!canCreateLink()) {
            float f2 = -this.shiftDp;
            this.shiftDp = f2;
            AndroidUtilities.shakeViewSpring(view, f2);
            BotWebViewVibrationEffect.APP_ERROR.vibrate();
            if (TextUtils.isEmpty(this.newFilterName) && TextUtils.isEmpty(this.filter.name)) {
                BulletinFactory.m1195of(this).createErrorBulletin(LocaleController.getString(C2702R.string.FilterInviteErrorEmptyName)).show();
                return;
            }
            if ((this.newFilterFlags & (~(MessagesController.DIALOG_FILTER_FLAG_CHATLIST | MessagesController.DIALOG_FILTER_FLAG_CHATLIST_ADMIN))) != 0) {
                if (!this.newNeverShow.isEmpty()) {
                    BulletinFactory.m1195of(this).createErrorBulletin(LocaleController.getString(C2702R.string.FilterInviteErrorTypesExcluded)).show();
                    return;
                } else {
                    BulletinFactory.m1195of(this).createErrorBulletin(LocaleController.getString(C2702R.string.FilterInviteErrorTypes)).show();
                    return;
                }
            }
            if (this.newAlwaysShow.isEmpty()) {
                BulletinFactory.m1195of(this).createErrorBulletin(LocaleController.getString(C2702R.string.FilterInviteErrorEmpty)).show();
                return;
            } else {
                BulletinFactory.m1195of(this).createErrorBulletin(LocaleController.getString(C2702R.string.FilterInviteErrorExcluded)).show();
                return;
            }
        }
        save(false, new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onClickCreateLink$13();
            }
        });
    }

    public /* synthetic */ void lambda$onClickCreateLink$13() {
        getMessagesController().updateFilterDialogs(this.filter);
        ArrayList<TLRPC.InputPeer> arrayList = new ArrayList<>();
        for (int i = 0; i < this.filter.alwaysShow.size(); i++) {
            long jLongValue = this.filter.alwaysShow.get(i).longValue();
            if (jLongValue < 0 && canAddToFolder(getMessagesController().getChat(Long.valueOf(-jLongValue)))) {
                arrayList.add(getMessagesController().getInputPeer(jLongValue));
            }
        }
        if (arrayList.size() > (getUserConfig().isPremium() ? getMessagesController().dialogFiltersChatsLimitPremium : getMessagesController().dialogFiltersChatsLimitDefault)) {
            showDialog(new LimitReachedBottomSheet(this, getContext(), 4, this.currentAccount, null));
            return;
        }
        if (!arrayList.isEmpty()) {
            TL_chatlists.TL_chatlists_exportChatlistInvite tL_chatlists_exportChatlistInvite = new TL_chatlists.TL_chatlists_exportChatlistInvite();
            TL_chatlists.TL_inputChatlistDialogFilter tL_inputChatlistDialogFilter = new TL_chatlists.TL_inputChatlistDialogFilter();
            tL_chatlists_exportChatlistInvite.chatlist = tL_inputChatlistDialogFilter;
            tL_inputChatlistDialogFilter.filter_id = this.filter.f1551id;
            tL_chatlists_exportChatlistInvite.peers = arrayList;
            tL_chatlists_exportChatlistInvite.title = _UrlKt.FRAGMENT_ENCODE_SET;
            getConnectionsManager().sendRequest(tL_chatlists_exportChatlistInvite, new RequestDelegate() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda30
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$onClickCreateLink$12(tLObject, tL_error);
                }
            });
            return;
        }
        FilterChatlistActivity filterChatlistActivity = new FilterChatlistActivity(this.filter, null);
        filterChatlistActivity.setOnEdit(new FilterCreateActivity$$ExternalSyntheticLambda23(this));
        filterChatlistActivity.setOnDelete(new FilterCreateActivity$$ExternalSyntheticLambda24(this));
        presentFragment(filterChatlistActivity);
    }

    public /* synthetic */ void lambda$onClickCreateLink$12(final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onClickCreateLink$11(tL_error, tLObject);
            }
        });
    }

    public /* synthetic */ void lambda$onClickCreateLink$11(TLRPC.TL_error tL_error, TLObject tLObject) {
        if (processErrors(tL_error, this, BulletinFactory.m1195of(this)) && (tLObject instanceof TL_chatlists.TL_chatlists_exportedChatlistInvite)) {
            hideNew(0);
            getMessagesController().loadRemoteFilters(true);
            final TL_chatlists.TL_chatlists_exportedChatlistInvite tL_chatlists_exportedChatlistInvite = (TL_chatlists.TL_chatlists_exportedChatlistInvite) tLObject;
            FilterChatlistActivity filterChatlistActivity = new FilterChatlistActivity(this.filter, tL_chatlists_exportedChatlistInvite.invite);
            filterChatlistActivity.setOnEdit(new FilterCreateActivity$$ExternalSyntheticLambda23(this));
            filterChatlistActivity.setOnDelete(new FilterCreateActivity$$ExternalSyntheticLambda24(this));
            presentFragment(filterChatlistActivity);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onClickCreateLink$10(tL_chatlists_exportedChatlistInvite);
                }
            }, 200L);
        }
    }

    public /* synthetic */ void lambda$onClickCreateLink$10(TL_chatlists.TL_chatlists_exportedChatlistInvite tL_chatlists_exportedChatlistInvite) {
        onEdit(tL_chatlists_exportedChatlistInvite.invite);
    }

    private void showSaveHint() {
        HintView hintView = this.saveHintView;
        if (hintView == null || hintView.getVisibility() != 0) {
            C54015 c54015 = new HintView(getContext(), 6, true) { // from class: org.telegram.ui.FilterCreateActivity.5
                C54015(Context context, int i, boolean z) {
                    super(context, i, z);
                }

                @Override // android.view.View
                public void setVisibility(int i) {
                    super.setVisibility(i);
                    if (i != 0) {
                        try {
                            ((ViewGroup) getParent()).removeView(this);
                        } catch (Exception unused) {
                        }
                    }
                }
            };
            this.saveHintView = c54015;
            c54015.textView.setMaxWidth(AndroidUtilities.displaySize.x);
            this.saveHintView.setExtraTranslationY(AndroidUtilities.m1081dp(-16.0f));
            this.saveHintView.setText(LocaleController.getString(C2702R.string.FilterFinishCreating));
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
            marginLayoutParams.rightMargin = AndroidUtilities.m1081dp(3.0f);
            getParentLayout().getOverlayContainerView().addView(this.saveHintView, marginLayoutParams);
            this.saveHintView.showForView(this.doneItem, true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$5 */
    class C54015 extends HintView {
        C54015(Context context, int i, boolean z) {
            super(context, i, z);
        }

        @Override // android.view.View
        public void setVisibility(int i) {
            super.setVisibility(i);
            if (i != 0) {
                try {
                    ((ViewGroup) getParent()).removeView(this);
                } catch (Exception unused) {
                }
            }
        }
    }

    public static boolean canAddToFolder(TLRPC.Chat chat) {
        if (ChatObject.canUserDoAdminAction(chat, 3)) {
            return true;
        }
        return ChatObject.isPublic(chat) && !chat.join_request;
    }

    public void onDelete(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite) {
        if (tL_exportedChatlistInvite == null) {
            return;
        }
        int i = 0;
        while (true) {
            if (i >= this.invites.size()) {
                i = -1;
                break;
            } else if (TextUtils.equals(((TL_chatlists.TL_exportedChatlistInvite) this.invites.get(i)).url, tL_exportedChatlistInvite.url)) {
                break;
            } else {
                i++;
            }
        }
        if (i >= 0) {
            this.invites.remove(i);
            if (this.invites.isEmpty()) {
                this.filter.flags &= ~MessagesController.DIALOG_FILTER_FLAG_CHATLIST;
            }
            updateRows();
        }
    }

    public void onEdit(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite) {
        if (tL_exportedChatlistInvite == null) {
            return;
        }
        int i = 0;
        while (true) {
            if (i >= this.invites.size()) {
                i = -1;
                break;
            } else if (TextUtils.equals(((TL_chatlists.TL_exportedChatlistInvite) this.invites.get(i)).url, tL_exportedChatlistInvite.url)) {
                break;
            } else {
                i++;
            }
        }
        if (i < 0) {
            this.invites.add(tL_exportedChatlistInvite);
        } else {
            this.invites.set(i, tL_exportedChatlistInvite);
        }
        updateRows();
    }

    public void deleteFolder(View view) {
        MessagesController.DialogFilter dialogFilter = this.filter;
        if (dialogFilter != null && dialogFilter.isChatlist()) {
            FolderBottomSheet.showForDeletion(this, this.filter.f1551id, new Utilities.Callback() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda16
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$deleteFolder$14((Boolean) obj);
                }
            });
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString(C2702R.string.FilterDelete));
        builder.setMessage(LocaleController.getString(C2702R.string.FilterDeleteAlert));
        builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
        builder.setPositiveButton(LocaleController.getString(C2702R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda17
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$deleteFolder$17(alertDialog, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        showDialog(alertDialogCreate);
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    public /* synthetic */ void lambda$deleteFolder$14(Boolean bool) {
        finishFragment();
    }

    public /* synthetic */ void lambda$deleteFolder$17(AlertDialog alertDialog, int i) {
        final AlertDialog alertDialog2;
        if (getParentActivity() != null) {
            alertDialog2 = new AlertDialog(getParentActivity(), 3);
            alertDialog2.setCanCancel(false);
            alertDialog2.show();
        } else {
            alertDialog2 = null;
        }
        TLRPC.TL_messages_updateDialogFilter tL_messages_updateDialogFilter = new TLRPC.TL_messages_updateDialogFilter();
        tL_messages_updateDialogFilter.f1737id = this.filter.f1551id;
        getConnectionsManager().sendRequest(tL_messages_updateDialogFilter, new RequestDelegate() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda25
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$deleteFolder$16(alertDialog2, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$deleteFolder$16(final AlertDialog alertDialog, TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteFolder$15(alertDialog);
            }
        });
    }

    public /* synthetic */ void lambda$deleteFolder$15(AlertDialog alertDialog) {
        if (alertDialog != null) {
            try {
                alertDialog.dismiss();
            } catch (Exception e) {
                FileLog.m1093e(e);
            }
        }
        getMessagesController().removeFilter(this.filter);
        getMessagesStorage().deleteDialogFilter(this.filter);
        finishFragment();
    }

    private void onUpdate(boolean z, ArrayList arrayList, ArrayList arrayList2) {
        int size;
        int size2;
        if (arrayList != null && arrayList2 != null) {
            size = 0;
            for (int i = 0; i < arrayList.size(); i++) {
                if (!arrayList2.contains(arrayList.get(i))) {
                    size++;
                }
            }
            size2 = 0;
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                if (!arrayList.contains(arrayList2.get(i2))) {
                    size2++;
                }
            }
        } else if (arrayList != null) {
            size = arrayList.size();
            size2 = 0;
        } else if (arrayList2 != null) {
            size2 = arrayList2.size();
            size = 0;
        } else {
            size = 0;
            size2 = 0;
        }
        if (!z) {
            if (size2 > 0) {
                onUpdate(false, size2);
            }
        } else if (size2 > 0 && size2 > size) {
            onUpdate(true, size2);
        } else if (size > 0) {
            onUpdate(false, size);
        }
    }

    private void selectChatsFor(final boolean z) {
        UsersSelectActivity usersSelectActivity = new UsersSelectActivity(z, z ? this.newAlwaysShow : this.newNeverShow, this.newFilterFlags);
        usersSelectActivity.noChatTypes = this.filter.isChatlist();
        usersSelectActivity.setDelegate(new UsersSelectActivity.FilterUsersActivityDelegate() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda15
            @Override // org.telegram.ui.UsersSelectActivity.FilterUsersActivityDelegate
            public final void didSelectChats(ArrayList arrayList, int i) {
                this.f$0.lambda$selectChatsFor$18(z, arrayList, i);
            }
        });
        presentFragment(usersSelectActivity);
    }

    public /* synthetic */ void lambda$selectChatsFor$18(boolean z, ArrayList arrayList, int i) {
        this.newFilterFlags = i;
        if (z) {
            onUpdate(true, this.newAlwaysShow, arrayList);
            this.newAlwaysShow = arrayList;
            for (int i2 = 0; i2 < this.newAlwaysShow.size(); i2++) {
                this.newNeverShow.remove(this.newAlwaysShow.get(i2));
            }
            ArrayList arrayList2 = new ArrayList();
            int size = this.newPinned.size();
            for (int i3 = 0; i3 < size; i3++) {
                long jKeyAt = this.newPinned.keyAt(i3);
                Long lValueOf = Long.valueOf(jKeyAt);
                if (!DialogObject.isEncryptedDialog(jKeyAt) && !this.newAlwaysShow.contains(lValueOf)) {
                    arrayList2.add(lValueOf);
                }
            }
            int size2 = arrayList2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                this.newPinned.delete(((Long) arrayList2.get(i4)).longValue());
            }
        } else {
            onUpdate(false, this.newNeverShow, arrayList);
            this.newNeverShow = arrayList;
            for (int i5 = 0; i5 < this.newNeverShow.size(); i5++) {
                Long l = (Long) this.newNeverShow.get(i5);
                this.newAlwaysShow.remove(l);
                this.newPinned.delete(l.longValue());
            }
        }
        fillFilterName();
        checkDoneButton(false);
        updateRows();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        updateRows();
        Runnable runnable = this.showBulletinOnResume;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onBackPressed(boolean z) {
        EditTextEmoji editTextEmoji;
        EditEmojiTextCell editEmojiTextCell = this.nameEditTextCell;
        if (editEmojiTextCell == null || (editTextEmoji = editEmojiTextCell.editTextEmoji) == null || !editTextEmoji.isPopupShowing()) {
            return checkDiscard(z);
        }
        if (!z) {
            return false;
        }
        this.nameEditTextCell.editTextEmoji.hidePopup(true);
        return false;
    }

    private void fillFilterName() {
        if (this.creatingNew) {
            if (TextUtils.isEmpty(this.newFilterName) || !this.nameChangedManually) {
                Pair emoticonFromFlags = FolderIcons.getEmoticonFromFlags(this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_ALL_CHATS);
                String str = (String) emoticonFromFlags.first;
                String str2 = (String) emoticonFromFlags.second;
                if (str != null && str.length() > 12) {
                    str = _UrlKt.FRAGMENT_ENCODE_SET;
                }
                this.newFilterName = str;
                this.newFilterEmoticon = str2;
                HeaderCellColorPreview headerCellColorPreview = this.folderTagsHeader;
                if (headerCellColorPreview != null) {
                    headerCellColorPreview.setPreviewText(AnimatedEmojiSpan.cloneSpans(str, -1, headerCellColorPreview.getPreviewTextPaint().getFontMetricsInt(), 0.5f), false);
                }
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.listView.findViewHolderForAdapterPosition(this.nameRow);
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    this.adapter.onViewAttachedToWindow(viewHolderFindViewHolderForAdapterPosition);
                }
            }
        }
    }

    public boolean checkDiscard(boolean z) {
        if (this.doneItem.getAlpha() != 1.0f) {
            return true;
        }
        if (!z) {
            return false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        if (this.creatingNew) {
            builder.setTitle(LocaleController.getString(C2702R.string.FilterDiscardNewTitle));
            builder.setMessage(LocaleController.getString(C2702R.string.FilterDiscardNewAlert));
            builder.setPositiveButton(LocaleController.getString(C2702R.string.FilterDiscardNewSave), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$checkDiscard$19(alertDialog, i);
                }
            });
        } else {
            builder.setTitle(LocaleController.getString(C2702R.string.FilterDiscardTitle));
            builder.setMessage(LocaleController.getString(C2702R.string.FilterDiscardAlert));
            builder.setPositiveButton(LocaleController.getString(C2702R.string.ApplyTheme), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda1
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$checkDiscard$20(alertDialog, i);
                }
            });
        }
        builder.setNegativeButton(LocaleController.getString(C2702R.string.PassportDiscard), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$checkDiscard$21(alertDialog, i);
            }
        });
        showDialog(builder.create());
        return false;
    }

    public /* synthetic */ void lambda$checkDiscard$19(AlertDialog alertDialog, int i) {
        processDone();
    }

    public /* synthetic */ void lambda$checkDiscard$20(AlertDialog alertDialog, int i) {
        processDone();
    }

    public /* synthetic */ void lambda$checkDiscard$21(AlertDialog alertDialog, int i) {
        finishFragment();
    }

    private void showRemoveAlert(final ItemInner itemInner, CharSequence charSequence, Object obj, final boolean z) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        if (z) {
            builder.setTitle(LocaleController.getString(C2702R.string.FilterRemoveInclusionTitle));
            if (obj instanceof String) {
                builder.setMessage(LocaleController.formatString(C2702R.string.FilterRemoveInclusionText, charSequence));
            } else if (obj instanceof TLRPC.User) {
                builder.setMessage(LocaleController.formatString(C2702R.string.FilterRemoveInclusionUserText, charSequence));
            } else {
                builder.setMessage(LocaleController.formatString(C2702R.string.FilterRemoveInclusionChatText, charSequence));
            }
        } else {
            builder.setTitle(LocaleController.getString(C2702R.string.FilterRemoveExclusionTitle));
            if (obj instanceof String) {
                builder.setMessage(LocaleController.formatString(C2702R.string.FilterRemoveExclusionText, charSequence));
            } else if (obj instanceof TLRPC.User) {
                builder.setMessage(LocaleController.formatString(C2702R.string.FilterRemoveExclusionUserText, charSequence));
            } else {
                builder.setMessage(LocaleController.formatString(C2702R.string.FilterRemoveExclusionChatText, charSequence));
            }
        }
        builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), null);
        builder.setPositiveButton(LocaleController.getString(C2702R.string.StickersRemove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda21
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$showRemoveAlert$22(itemInner, z, alertDialog, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        showDialog(alertDialogCreate);
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    public /* synthetic */ void lambda$showRemoveAlert$22(ItemInner itemInner, boolean z, AlertDialog alertDialog, int i) {
        if (itemInner.flags > 0) {
            this.newFilterFlags = (~itemInner.flags) & this.newFilterFlags;
        } else {
            (z ? this.newAlwaysShow : this.newNeverShow).remove(Long.valueOf(itemInner.did));
        }
        fillFilterName();
        updateRows();
        checkDoneButton(true);
        if (z) {
            onUpdate(false, 1);
        }
    }

    public void processDone() {
        HintView hintView = this.saveHintView;
        if (hintView != null) {
            hintView.hide(true);
            this.saveHintView = null;
        }
        save(true, new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processDone$23();
            }
        });
    }

    public /* synthetic */ void lambda$processDone$23() {
        if (this.doNotCloseWhenSave) {
            this.doNotCloseWhenSave = false;
            Paint.FontMetricsInt titleFontMetricsInt = this.actionBar.getTitleFontMetricsInt();
            this.actionBar.setTitleAnimated(MessageObject.replaceAnimatedEmoji(Emoji.replaceEmoji(this.filter.name, titleFontMetricsInt, false), this.filter.entities, titleFontMetricsInt), true, 220L);
            return;
        }
        finishFragment();
    }

    private void save(boolean z, final Runnable runnable) {
        CharSequence[] charSequenceArr = {this.newFilterName};
        saveFilterToServer(this.filter, this.newFilterFlags, charSequenceArr[0].toString(), getMediaDataController().getEntities(charSequenceArr, false), !this.newFilterAnimations, this.newFilterColor, this.newFilterEmoticon, this.newAlwaysShow, this.newNeverShow, this.newPinned, this.creatingNew, false, this.hasUserChanged, true, z, this, new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$save$24(runnable);
            }
        });
    }

    public /* synthetic */ void lambda$save$24(Runnable runnable) {
        this.hasUserChanged = false;
        this.creatingNew = false;
        this.filter.flags = this.newFilterFlags;
        checkDoneButton(true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
        if (runnable != null) {
            runnable.run();
        }
    }

    private static void processAddFilter(MessagesController.DialogFilter dialogFilter, int i, String str, String str2, ArrayList arrayList, boolean z, int i2, ArrayList arrayList2, ArrayList arrayList3, boolean z2, boolean z3, boolean z4, boolean z5, BaseFragment baseFragment, Runnable runnable) {
        if (dialogFilter.flags != i || z4) {
            dialogFilter.pendingUnreadCount = -1;
            if (z5) {
                dialogFilter.unreadCount = -1;
            }
        }
        dialogFilter.flags = i;
        dialogFilter.name = str2;
        dialogFilter.emoticon = str;
        dialogFilter.entities = arrayList;
        dialogFilter.color = i2;
        dialogFilter.neverShow = arrayList3;
        dialogFilter.alwaysShow = arrayList2;
        dialogFilter.title_noanimate = z;
        if (z2) {
            baseFragment.getMessagesController().addFilter(dialogFilter, z3);
        } else {
            baseFragment.getMessagesController().onFilterUpdate(dialogFilter);
        }
        baseFragment.getMessagesStorage().saveDialogFilter(dialogFilter, z3, true);
        if (z3) {
            TLRPC.TL_messages_updateDialogFiltersOrder tL_messages_updateDialogFiltersOrder = new TLRPC.TL_messages_updateDialogFiltersOrder();
            ArrayList<MessagesController.DialogFilter> dialogFilters = baseFragment.getMessagesController().getDialogFilters();
            int size = dialogFilters.size();
            for (int i3 = 0; i3 < size; i3++) {
                tL_messages_updateDialogFiltersOrder.order.add(Integer.valueOf(dialogFilters.get(i3).f1551id));
            }
            baseFragment.getConnectionsManager().sendRequest(tL_messages_updateDialogFiltersOrder, null);
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void saveFilterToServer(final MessagesController.DialogFilter dialogFilter, final int i, final String str, final ArrayList arrayList, final boolean z, final int i2, final String str2, final ArrayList arrayList2, final ArrayList arrayList3, LongSparseIntArray longSparseIntArray, final boolean z2, final boolean z3, final boolean z4, final boolean z5, final boolean z6, final BaseFragment baseFragment, final Runnable runnable) {
        AlertDialog alertDialog;
        ArrayList arrayList4;
        ArrayList arrayList5;
        final LongSparseIntArray longSparseIntArray2 = longSparseIntArray;
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        int i3 = 3;
        if (z6) {
            alertDialog = new AlertDialog(baseFragment.getParentActivity(), 3);
            alertDialog.setCanCancel(false);
            alertDialog.show();
        } else {
            alertDialog = null;
        }
        TLRPC.TL_messages_updateDialogFilter tL_messages_updateDialogFilter = new TLRPC.TL_messages_updateDialogFilter();
        tL_messages_updateDialogFilter.f1737id = dialogFilter.f1551id;
        int i4 = 1;
        tL_messages_updateDialogFilter.flags |= 1;
        TLRPC.TL_dialogFilter tL_dialogFilter = new TLRPC.TL_dialogFilter();
        tL_messages_updateDialogFilter.filter = tL_dialogFilter;
        tL_dialogFilter.contacts = (i & MessagesController.DIALOG_FILTER_FLAG_CONTACTS) != 0;
        tL_dialogFilter.non_contacts = (i & MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS) != 0;
        tL_dialogFilter.groups = (i & MessagesController.DIALOG_FILTER_FLAG_GROUPS) != 0;
        tL_dialogFilter.broadcasts = (i & MessagesController.DIALOG_FILTER_FLAG_CHANNELS) != 0;
        tL_dialogFilter.bots = (i & MessagesController.DIALOG_FILTER_FLAG_BOTS) != 0;
        tL_dialogFilter.exclude_muted = (i & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED) != 0;
        tL_dialogFilter.exclude_read = (i & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ) != 0;
        tL_dialogFilter.exclude_archived = (i & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_ARCHIVED) != 0;
        tL_dialogFilter.f1617id = dialogFilter.f1551id;
        tL_dialogFilter.title = new TLRPC.TL_textWithEntities();
        TLRPC.TL_dialogFilter tL_dialogFilter2 = tL_messages_updateDialogFilter.filter;
        TLRPC.TL_textWithEntities tL_textWithEntities = tL_dialogFilter2.title;
        tL_textWithEntities.text = str;
        tL_textWithEntities.entities = arrayList;
        tL_dialogFilter2.title_noanimate = z;
        if (i2 < 0) {
            tL_dialogFilter2.flags &= -134217729;
            tL_dialogFilter2.color = 0;
        } else {
            tL_dialogFilter2.flags |= 134217728;
            tL_dialogFilter2.color = i2;
        }
        if (str2 != null) {
            tL_dialogFilter2.emoticon = str2;
            tL_dialogFilter2.flags |= 33554432;
        }
        MessagesController messagesController = baseFragment.getMessagesController();
        ArrayList arrayList6 = new ArrayList();
        if (longSparseIntArray2.size() != 0) {
            int size = longSparseIntArray2.size();
            for (int i5 = 0; i5 < size; i5++) {
                long jKeyAt = longSparseIntArray2.keyAt(i5);
                if (!DialogObject.isEncryptedDialog(jKeyAt)) {
                    arrayList6.add(Long.valueOf(jKeyAt));
                }
            }
            Collections.sort(arrayList6, new Comparator() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda11
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return FilterCreateActivity.$r8$lambda$EAiVXe9Yl9sL1hdHBEq1o50JJaY(longSparseIntArray2, (Long) obj, (Long) obj2);
                }
            });
        }
        int i6 = 0;
        while (i6 < i3) {
            if (i6 == 0) {
                arrayList4 = tL_messages_updateDialogFilter.filter.include_peers;
                arrayList5 = arrayList2;
            } else if (i6 == i4) {
                arrayList4 = tL_messages_updateDialogFilter.filter.exclude_peers;
                arrayList5 = arrayList3;
            } else {
                arrayList4 = tL_messages_updateDialogFilter.filter.pinned_peers;
                arrayList5 = arrayList6;
            }
            int size2 = arrayList5.size();
            int i7 = 0;
            while (i7 < size2) {
                Long l = (Long) arrayList5.get(i7);
                int i8 = size2;
                long jLongValue = l.longValue();
                if ((i6 != 0 || longSparseIntArray2.indexOfKey(jLongValue) < 0) && !DialogObject.isEncryptedDialog(jLongValue)) {
                    if (jLongValue > 0) {
                        TLRPC.User user = messagesController.getUser(l);
                        if (user != null) {
                            TLRPC.TL_inputPeerUser tL_inputPeerUser = new TLRPC.TL_inputPeerUser();
                            tL_inputPeerUser.user_id = jLongValue;
                            tL_inputPeerUser.access_hash = user.access_hash;
                            arrayList4.add(tL_inputPeerUser);
                        }
                    } else {
                        long j = -jLongValue;
                        TLRPC.Chat chat = messagesController.getChat(Long.valueOf(j));
                        if (chat != null) {
                            if (ChatObject.isChannel(chat)) {
                                TLRPC.TL_inputPeerChannel tL_inputPeerChannel = new TLRPC.TL_inputPeerChannel();
                                tL_inputPeerChannel.channel_id = j;
                                tL_inputPeerChannel.access_hash = chat.access_hash;
                                arrayList4.add(tL_inputPeerChannel);
                            } else {
                                TLRPC.TL_inputPeerChat tL_inputPeerChat = new TLRPC.TL_inputPeerChat();
                                tL_inputPeerChat.chat_id = j;
                                arrayList4.add(tL_inputPeerChat);
                            }
                        }
                    }
                }
                i7++;
                longSparseIntArray2 = longSparseIntArray;
                size2 = i8;
            }
            i6++;
            longSparseIntArray2 = longSparseIntArray;
            i3 = 3;
            i4 = 1;
        }
        final AlertDialog alertDialog2 = alertDialog;
        baseFragment.getConnectionsManager().sendRequest(tL_messages_updateDialogFilter, new RequestDelegate() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda12
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilterCreateActivity.m13479$r8$lambda$Snfwkw2cJEKYpbZLGRytZdZmHU(z, alertDialog, dialogFilter, i, str, str, arrayList, z, i, arrayList, arrayList, z, z, z, z, baseFragment, runnable);
                    }
                });
            }
        });
        if (z6) {
            return;
        }
        processAddFilter(dialogFilter, i, str2, str, arrayList, z, i2, arrayList2, arrayList3, z2, z3, z4, z5, baseFragment, null);
    }

    public static /* synthetic */ int $r8$lambda$EAiVXe9Yl9sL1hdHBEq1o50JJaY(LongSparseIntArray longSparseIntArray, Long l, Long l2) {
        int i = longSparseIntArray.get(l.longValue());
        int i2 = longSparseIntArray.get(l2.longValue());
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    /* JADX INFO: renamed from: $r8$lambda$Sn-fwkw2cJEKYpbZLGRytZdZmHU */
    public static /* synthetic */ void m13479$r8$lambda$Snfwkw2cJEKYpbZLGRytZdZmHU(boolean z, AlertDialog alertDialog, MessagesController.DialogFilter dialogFilter, int i, String str, String str2, ArrayList arrayList, boolean z2, int i2, ArrayList arrayList2, ArrayList arrayList3, boolean z3, boolean z4, boolean z5, boolean z6, BaseFragment baseFragment, Runnable runnable) {
        if (!z) {
            if (runnable != null) {
                runnable.run();
            }
        } else {
            if (alertDialog != null) {
                try {
                    alertDialog.dismiss();
                } catch (Exception e) {
                    FileLog.m1093e(e);
                }
            }
            processAddFilter(dialogFilter, i, str, str2, arrayList, z2, i2, arrayList2, arrayList3, z3, z4, z5, z6, baseFragment, runnable);
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean canBeginSlide() {
        return checkDiscard(true);
    }

    private boolean hasChanges() {
        this.hasUserChanged = false;
        if (this.filter.alwaysShow.size() != this.newAlwaysShow.size()) {
            this.hasUserChanged = true;
        }
        if (this.filter.neverShow.size() != this.newNeverShow.size()) {
            this.hasUserChanged = true;
        }
        MessagesController.DialogFilter dialogFilter = this.filter;
        if (dialogFilter.color != this.newFilterColor) {
            this.hasUserChanged = true;
        }
        if (!this.hasUserChanged) {
            Collections.sort(dialogFilter.alwaysShow);
            Collections.sort(this.newAlwaysShow);
            if (!this.filter.alwaysShow.equals(this.newAlwaysShow)) {
                this.hasUserChanged = true;
            }
            Collections.sort(this.filter.neverShow);
            Collections.sort(this.newNeverShow);
            if (!this.filter.neverShow.equals(this.newNeverShow)) {
                this.hasUserChanged = true;
            }
        }
        MessagesController.DialogFilter dialogFilter2 = this.filter;
        if (dialogFilter2.title_noanimate == (!this.newFilterAnimations) && TextUtils.equals(dialogFilter2.name, this.newFilterName) && TextUtils.equals(this.filter.emoticon, this.newFilterEmoticon) && this.filter.flags == this.newFilterFlags) {
            return this.hasUserChanged;
        }
        return true;
    }

    public void checkDoneButton(boolean z) {
        boolean zHasChanges = !TextUtils.isEmpty(this.newFilterName) && this.newFilterName.length() <= 12;
        if (zHasChanges) {
            boolean z2 = ((this.newFilterFlags & MessagesController.DIALOG_FILTER_FLAG_ALL_CHATS) == 0 && this.newAlwaysShow.isEmpty()) ? false : true;
            zHasChanges = (!z2 || this.creatingNew) ? z2 : hasChanges();
        }
        if (this.doneItem.isEnabled() == zHasChanges) {
            return;
        }
        this.doneItem.setEnabled(zHasChanges);
        if (z) {
            this.doneItem.animate().alpha(zHasChanges ? 1.0f : 0.0f).scaleX(zHasChanges ? 1.0f : 0.0f).scaleY(zHasChanges ? 1.0f : 0.0f).setDuration(180L).start();
            return;
        }
        this.doneItem.setAlpha(zHasChanges ? 1.0f : 0.0f);
        this.doneItem.setScaleX(zHasChanges ? 1.0f : 0.0f);
        this.doneItem.setScaleY(zHasChanges ? 1.0f : 0.0f);
    }

    static class ItemInner extends AdapterWithDiffUtils.Item {
        private String chatType;
        private long did;
        private int flags;
        private int iconResId;
        private boolean include;
        private boolean isRed;
        private TL_chatlists.TL_exportedChatlistInvite link;
        private boolean newSpan;
        private View.OnClickListener onClickListener;
        private CharSequence subtext;
        private CharSequence text;

        public ItemInner(int i, boolean z) {
            super(i, z);
        }

        public static ItemInner asHeader(CharSequence charSequence) {
            ItemInner itemInner = new ItemInner(0, false);
            itemInner.text = charSequence;
            return itemInner;
        }

        public static ItemInner asHeader(CharSequence charSequence, boolean z) {
            ItemInner itemInner = new ItemInner(0, false);
            itemInner.text = charSequence;
            itemInner.newSpan = z;
            return itemInner;
        }

        public static ItemInner asAnimatedHeader(CharSequence charSequence, CharSequence charSequence2, View.OnClickListener onClickListener) {
            ItemInner itemInner = new ItemInner(11, false);
            itemInner.text = charSequence;
            itemInner.subtext = charSequence2;
            itemInner.onClickListener = onClickListener;
            return itemInner;
        }

        public static ItemInner asChat(boolean z, long j) {
            ItemInner itemInner = new ItemInner(1, false);
            itemInner.include = z;
            itemInner.did = j;
            return itemInner;
        }

        public static ItemInner asChat(boolean z, CharSequence charSequence, String str, int i) {
            ItemInner itemInner = new ItemInner(1, false);
            itemInner.include = z;
            itemInner.text = charSequence;
            itemInner.chatType = str;
            itemInner.flags = i;
            return itemInner;
        }

        public static ItemInner asEdit() {
            return new ItemInner(2, false);
        }

        public static ItemInner asShadow(CharSequence charSequence) {
            ItemInner itemInner = new ItemInner(TextUtils.isEmpty(charSequence) ? 3 : 6, false);
            itemInner.text = charSequence;
            return itemInner;
        }

        public static ItemInner asLink(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite) {
            ItemInner itemInner = new ItemInner(7, false);
            itemInner.link = tL_exportedChatlistInvite;
            return itemInner;
        }

        public static ItemInner asButton(int i, CharSequence charSequence, boolean z) {
            ItemInner itemInner = new ItemInner(4, false);
            itemInner.iconResId = i;
            itemInner.text = charSequence;
            itemInner.isRed = z;
            return itemInner;
        }

        public static ItemInner asCreateLink() {
            return new ItemInner(8, false);
        }

        public ItemInner whenClicked(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public boolean isShadow() {
            int i = this.viewType;
            return i == 3 || i == 6;
        }

        public boolean equals(Object obj) {
            TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite;
            TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite2;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ItemInner itemInner = (ItemInner) obj;
            int i = this.viewType;
            if (i != itemInner.viewType) {
                return false;
            }
            if (i == 11) {
                return TextUtils.equals(this.text, itemInner.text) && TextUtils.equals(this.subtext, itemInner.subtext);
            }
            if ((i == 0 || i == 1 || i == 3 || i == 4) && !TextUtils.equals(this.text, itemInner.text)) {
                return false;
            }
            int i2 = this.viewType;
            if (i2 == 0) {
                return this.newSpan == itemInner.newSpan;
            }
            if (i2 == 1) {
                return this.did == itemInner.did && TextUtils.equals(this.chatType, itemInner.chatType) && this.flags == itemInner.flags;
            }
            if (i2 == 7 && (tL_exportedChatlistInvite = this.link) != (tL_exportedChatlistInvite2 = itemInner.link)) {
                if (TextUtils.equals(tL_exportedChatlistInvite.url, tL_exportedChatlistInvite2.url)) {
                    TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite3 = this.link;
                    boolean z = tL_exportedChatlistInvite3.revoked;
                    TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite4 = itemInner.link;
                    if (z != tL_exportedChatlistInvite4.revoked || !TextUtils.equals(tL_exportedChatlistInvite3.title, tL_exportedChatlistInvite4.title) || this.link.peers.size() != itemInner.link.peers.size()) {
                    }
                }
                return false;
            }
            return true;
        }
    }

    class ListAdapter extends AdapterWithDiffUtils {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return (itemViewType == 3 || itemViewType == 0 || itemViewType == 2 || itemViewType == 5 || itemViewType == 9 || itemViewType == 11) ? false : true;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return FilterCreateActivity.this.items.size();
        }

        /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$ListAdapter$1 */
        class C54161 extends EditEmojiTextCell {
            @Override // org.telegram.p026ui.Cells.EditEmojiTextCell
            public int emojiCacheType() {
                return 25;
            }

            C54161(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout, String str, boolean z, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                super(context, sizeNotifierFrameLayout, str, z, i, i2, resourcesProvider);
            }
        }

        public /* synthetic */ void lambda$onCreateViewHolder$1(View view) {
            FilterCreateActivity filterCreateActivity = FilterCreateActivity.this;
            IconSelectorAlert.show(filterCreateActivity, view, filterCreateActivity.newFilterEmoticon == null ? "📁" : FilterCreateActivity.this.newFilterEmoticon, new IconSelectorAlert.OnIconSelectedListener() { // from class: org.telegram.ui.FilterCreateActivity$ListAdapter$$ExternalSyntheticLambda2
                @Override // com.exteragram.messenger.components.IconSelectorAlert.OnIconSelectedListener
                public final void onIconSelected(String str) {
                    this.f$0.lambda$onCreateViewHolder$0(str);
                }
            });
        }

        public /* synthetic */ void lambda$onCreateViewHolder$0(String str) {
            FilterCreateActivity.this.newFilterEmoticon = str;
            FilterCreateActivity.this.nameEditTextCell.setIcon(FolderIcons.getTabIcon(FilterCreateActivity.this.newFilterEmoticon), true);
            FilterCreateActivity.this.checkDoneButton(true);
        }

        /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$ListAdapter$2 */
        class C54172 implements TextWatcher {
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            C54172() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                String string;
                if (!TextUtils.equals(editable, FilterCreateActivity.this.newFilterName)) {
                    FilterCreateActivity.this.nameChangedManually = !TextUtils.isEmpty(editable);
                    FilterCreateActivity.this.newFilterName = AnimatedEmojiSpan.onlyEmojiSpans(editable);
                    if (FilterCreateActivity.this.folderTagsHeader != null) {
                        FilterCreateActivity.this.folderTagsHeader.setPreviewText(AnimatedEmojiSpan.cloneSpans(FilterCreateActivity.this.newFilterName, -1, FilterCreateActivity.this.folderTagsHeader.getPreviewTextPaint().getFontMetricsInt(), 0.5f), true);
                    }
                    if (FilterCreateActivity.this.nameHeaderCell != null) {
                        AnimatedTextView animatedTextView = FilterCreateActivity.this.nameHeaderCell.rightTextView;
                        FilterCreateActivity filterCreateActivity = FilterCreateActivity.this;
                        if (filterCreateActivity.hasAnimatedEmojis(filterCreateActivity.newFilterName)) {
                            string = LocaleController.getString(FilterCreateActivity.this.newFilterAnimations ? C2702R.string.FilterNameAnimationsDisable : C2702R.string.FilterNameAnimationsEnable);
                        } else {
                            string = null;
                        }
                        animatedTextView.setText(string);
                    }
                    ((BaseFragment) FilterCreateActivity.this).actionBar.setTitle(AnimatedEmojiSpan.cloneSpans(FilterCreateActivity.this.newFilterName, -1, ((BaseFragment) FilterCreateActivity.this).actionBar.getTitleFontMetricsInt()));
                }
                FilterCreateActivity.this.checkDoneButton(true);
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$ListAdapter$3 */
        class C54183 extends LinkCell {
            C54183(Context context, BaseFragment baseFragment, int i, int i2) {
                super(context, baseFragment, i, i2);
            }

            @Override // org.telegram.ui.FilterCreateActivity.LinkCell
            protected void onDelete(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite) {
                FilterCreateActivity.this.onDelete(tL_exportedChatlistInvite);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View headerCell;
            ListAdapter listAdapter;
            switch (i) {
                case 0:
                    headerCell = new HeaderCell(this.mContext, 22);
                    break;
                case 1:
                    UserCell userCell = new UserCell(this.mContext, 6, 0, false);
                    userCell.setSelfAsSavedMessages(true);
                    headerCell = userCell;
                    break;
                case 2:
                    FilterCreateActivity filterCreateActivity = FilterCreateActivity.this;
                    C54161 c54161 = new EditEmojiTextCell(this.mContext, (SizeNotifierFrameLayout) FilterCreateActivity.this.fragmentView, LocaleController.getString(C2702R.string.FilterNameHint), false, 12, 4, ((BaseFragment) FilterCreateActivity.this).resourceProvider) { // from class: org.telegram.ui.FilterCreateActivity.ListAdapter.1
                        @Override // org.telegram.p026ui.Cells.EditEmojiTextCell
                        public int emojiCacheType() {
                            return 25;
                        }

                        C54161(Context context, SizeNotifierFrameLayout sizeNotifierFrameLayout, String str, boolean z, int i2, int i22, Theme.ResourcesProvider resourcesProvider) {
                            super(context, sizeNotifierFrameLayout, str, z, i2, i22, resourcesProvider);
                        }
                    };
                    filterCreateActivity.nameEditTextCell = c54161;
                    c54161.setOnChangeIconListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$ListAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$onCreateViewHolder$1(view);
                        }
                    });
                    c54161.setIcon(FolderIcons.getTabIcon(FilterCreateActivity.this.newFilterEmoticon), false);
                    c54161.setAllowEntities(false);
                    c54161.editTextEmoji.getEditText().setEmojiColor(Integer.valueOf(FilterCreateActivity.this.getThemedColor(Theme.key_featuredStickers_addButton)));
                    c54161.editTextEmoji.setEmojiViewCacheType(25);
                    c54161.editTextEmoji.setText(FilterCreateActivity.this.newFilterName);
                    AnimatedEmojiDrawable.toggleAnimations(((BaseFragment) FilterCreateActivity.this).currentAccount, FilterCreateActivity.this.newFilterAnimations);
                    EditTextCaption editText = c54161.editTextEmoji.getEditText();
                    editText.addTextChangedListener(new EditTextSuggestionsFix());
                    editText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.FilterCreateActivity.ListAdapter.2
                        @Override // android.text.TextWatcher
                        public void beforeTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
                        }

                        @Override // android.text.TextWatcher
                        public void onTextChanged(CharSequence charSequence, int i2, int i22, int i3) {
                        }

                        C54172() {
                        }

                        @Override // android.text.TextWatcher
                        public void afterTextChanged(Editable editable) {
                            String string;
                            if (!TextUtils.equals(editable, FilterCreateActivity.this.newFilterName)) {
                                FilterCreateActivity.this.nameChangedManually = !TextUtils.isEmpty(editable);
                                FilterCreateActivity.this.newFilterName = AnimatedEmojiSpan.onlyEmojiSpans(editable);
                                if (FilterCreateActivity.this.folderTagsHeader != null) {
                                    FilterCreateActivity.this.folderTagsHeader.setPreviewText(AnimatedEmojiSpan.cloneSpans(FilterCreateActivity.this.newFilterName, -1, FilterCreateActivity.this.folderTagsHeader.getPreviewTextPaint().getFontMetricsInt(), 0.5f), true);
                                }
                                if (FilterCreateActivity.this.nameHeaderCell != null) {
                                    AnimatedTextView animatedTextView = FilterCreateActivity.this.nameHeaderCell.rightTextView;
                                    FilterCreateActivity filterCreateActivity2 = FilterCreateActivity.this;
                                    if (filterCreateActivity2.hasAnimatedEmojis(filterCreateActivity2.newFilterName)) {
                                        string = LocaleController.getString(FilterCreateActivity.this.newFilterAnimations ? C2702R.string.FilterNameAnimationsDisable : C2702R.string.FilterNameAnimationsEnable);
                                    } else {
                                        string = null;
                                    }
                                    animatedTextView.setText(string);
                                }
                                ((BaseFragment) FilterCreateActivity.this).actionBar.setTitle(AnimatedEmojiSpan.cloneSpans(FilterCreateActivity.this.newFilterName, -1, ((BaseFragment) FilterCreateActivity.this).actionBar.getTitleFontMetricsInt()));
                            }
                            FilterCreateActivity.this.checkDoneButton(true);
                        }
                    });
                    editText.setPadding(AndroidUtilities.m1081dp(7.0f), editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom());
                    c54161.editTextEmoji.getEditText().setImeOptions(268435462);
                    headerCell = c54161;
                    break;
                case 3:
                    listAdapter = this;
                    headerCell = new ShadowSectionCell(listAdapter.mContext);
                    break;
                case 4:
                    listAdapter = this;
                    headerCell = new ButtonCell(listAdapter.mContext);
                    break;
                case 5:
                    listAdapter = this;
                    headerCell = new HintInnerCell(listAdapter.mContext);
                    break;
                case 6:
                default:
                    headerCell = new TextInfoPrivacyCell(this.mContext);
                    break;
                case 7:
                    Context context = this.mContext;
                    FilterCreateActivity filterCreateActivity2 = FilterCreateActivity.this;
                    listAdapter = this;
                    headerCell = new LinkCell(context, filterCreateActivity2, ((BaseFragment) filterCreateActivity2).currentAccount, FilterCreateActivity.this.filter.f1551id) { // from class: org.telegram.ui.FilterCreateActivity.ListAdapter.3
                        C54183(Context context2, BaseFragment filterCreateActivity22, int i2, int i22) {
                            super(context2, filterCreateActivity22, i2, i22);
                        }

                        @Override // org.telegram.ui.FilterCreateActivity.LinkCell
                        protected void onDelete(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite) {
                            FilterCreateActivity.this.onDelete(tL_exportedChatlistInvite);
                        }
                    };
                    break;
                case 8:
                    headerCell = new CreateLinkCell(this.mContext);
                    break;
                case 9:
                    headerCell = FilterCreateActivity.this.new HeaderCellColorPreview(this.mContext);
                    break;
                case 10:
                    headerCell = new PeerColorActivity.PeerColorGrid(FilterCreateActivity.this.getContext(), 2, ((BaseFragment) FilterCreateActivity.this).currentAccount, ((BaseFragment) FilterCreateActivity.this).resourceProvider);
                    break;
                case 11:
                    FilterCreateActivity filterCreateActivity3 = FilterCreateActivity.this;
                    headerCell = filterCreateActivity3.new HeaderCellWithRight(this.mContext, ((BaseFragment) filterCreateActivity3).resourceProvider);
                    break;
            }
            return new RecyclerListView.Holder(headerCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType != 2 && itemViewType == 9) {
                ((HeaderCellColorPreview) viewHolder.itemView).setPreviewText(AnimatedEmojiSpan.cloneSpans(FilterCreateActivity.this.newFilterName, -1, FilterCreateActivity.this.folderTagsHeader.getPreviewTextPaint().getFontMetricsInt(), 0.5f), true);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 2) {
                EditEmojiTextCell editEmojiTextCell = (EditEmojiTextCell) viewHolder.itemView;
                editEmojiTextCell.editTextEmoji.hidePopup(true);
                editEmojiTextCell.editTextEmoji.closeKeyboard();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            String string;
            String string2;
            ItemInner itemInner = (ItemInner) FilterCreateActivity.this.items.get(i);
            if (itemInner == null) {
                return;
            }
            int i2 = i + 1;
            boolean z = i2 < FilterCreateActivity.this.items.size() && !((ItemInner) FilterCreateActivity.this.items.get(i2)).isShadow();
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 0) {
                HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                if (itemInner.newSpan) {
                    headerCell.setText(FilterCreateActivity.withNew(0, itemInner.text, false));
                    return;
                } else {
                    headerCell.setText(itemInner.text);
                    return;
                }
            }
            if (itemViewType != 1) {
                if (itemViewType == 4) {
                    ButtonCell buttonCell = (ButtonCell) viewHolder.itemView;
                    buttonCell.setRed(itemInner.isRed);
                    buttonCell.set(itemInner.iconResId, itemInner.text, z);
                    return;
                }
                switch (itemViewType) {
                    case 6:
                        ((TextInfoPrivacyCell) viewHolder.itemView).setText(itemInner.text);
                        break;
                    case 7:
                        ((LinkCell) viewHolder.itemView).setInvite(itemInner.link, z);
                        break;
                    case 8:
                        FilterCreateActivity.this.createLinkCell = (CreateLinkCell) viewHolder.itemView;
                        FilterCreateActivity.this.createLinkCell.setDivider(z);
                        break;
                    case 9:
                        FilterCreateActivity.this.folderTagsHeader = (HeaderCellColorPreview) viewHolder.itemView;
                        FilterCreateActivity.this.folderTagsHeader.setPreviewText(AnimatedEmojiSpan.cloneSpans(FilterCreateActivity.this.newFilterName, -1, FilterCreateActivity.this.folderTagsHeader.getPreviewTextPaint().getFontMetricsInt(), 0.5f), false);
                        FilterCreateActivity.this.folderTagsHeader.setPreviewColor(FilterCreateActivity.this.getUserConfig().isPremium() ? FilterCreateActivity.this.newFilterColor : -1, false);
                        FilterCreateActivity.this.folderTagsHeader.setText(LocaleController.getString(C2702R.string.FolderTagColor));
                        break;
                    case 10:
                        final PeerColorActivity.PeerColorGrid peerColorGrid = (PeerColorActivity.PeerColorGrid) viewHolder.itemView;
                        peerColorGrid.setCloseAsLock(!FilterCreateActivity.this.getUserConfig().isPremium());
                        peerColorGrid.setSelected(FilterCreateActivity.this.getUserConfig().isPremium() ? FilterCreateActivity.this.newFilterColor : -1, false);
                        peerColorGrid.setOnColorClick(new Utilities.Callback() { // from class: org.telegram.ui.FilterCreateActivity$ListAdapter$$ExternalSyntheticLambda1
                            @Override // org.telegram.messenger.Utilities.Callback
                            public final void run(Object obj) {
                                this.f$0.lambda$onBindViewHolder$2(peerColorGrid, (Integer) obj);
                            }
                        });
                        break;
                    case 11:
                        HeaderCellWithRight headerCellWithRight = (HeaderCellWithRight) viewHolder.itemView;
                        FilterCreateActivity.this.nameHeaderCell = headerCellWithRight;
                        headerCellWithRight.setText(itemInner.text);
                        headerCellWithRight.rightTextView.setText(itemInner.subtext);
                        headerCellWithRight.rightTextView.setOnClickListener(itemInner.onClickListener);
                        break;
                }
                return;
            }
            UserCell userCell = (UserCell) viewHolder.itemView;
            if (itemInner.chatType != null) {
                userCell.setData(itemInner.chatType, itemInner.text, null, 0, z);
                return;
            }
            long j = itemInner.did;
            if (j > 0) {
                TLRPC.User user = FilterCreateActivity.this.getMessagesController().getUser(Long.valueOf(j));
                if (user != null) {
                    if (user.bot) {
                        string2 = LocaleController.getString(C2702R.string.Bot);
                    } else if (user.contact) {
                        string2 = LocaleController.getString(C2702R.string.FilterContact);
                    } else {
                        string2 = LocaleController.getString(C2702R.string.FilterNonContact);
                    }
                    userCell.setData(user, null, string2, 0, z);
                    return;
                }
                return;
            }
            TLRPC.Chat chat = FilterCreateActivity.this.getMessagesController().getChat(Long.valueOf(-j));
            if (chat != null) {
                if (chat.participants_count != 0) {
                    if (ChatObject.isChannelAndNotMegaGroup(chat)) {
                        string = LocaleController.formatPluralStringComma("Subscribers", chat.participants_count);
                    } else {
                        string = LocaleController.formatPluralStringComma("Members", chat.participants_count);
                    }
                } else if (!ChatObject.isPublic(chat)) {
                    if (ChatObject.isChannel(chat) && !chat.megagroup) {
                        string = LocaleController.getString(C2702R.string.ChannelPrivate);
                    } else {
                        string = LocaleController.getString(C2702R.string.MegaPrivate);
                    }
                } else if (ChatObject.isChannel(chat) && !chat.megagroup) {
                    string = LocaleController.getString(C2702R.string.ChannelPublic);
                } else {
                    string = LocaleController.getString(C2702R.string.MegaPublic);
                }
                userCell.setData(chat, null, string, 0, z);
            }
        }

        public /* synthetic */ void lambda$onBindViewHolder$2(PeerColorActivity.PeerColorGrid peerColorGrid, Integer num) {
            if (!FilterCreateActivity.this.getUserConfig().isPremium()) {
                FilterCreateActivity.this.showDialog(new PremiumFeatureBottomSheet(FilterCreateActivity.this, 35, true));
                return;
            }
            FilterCreateActivity filterCreateActivity = FilterCreateActivity.this;
            int iIntValue = num.intValue();
            filterCreateActivity.newFilterColor = iIntValue;
            peerColorGrid.setSelected(iIntValue, true);
            if (FilterCreateActivity.this.folderTagsHeader != null) {
                FilterCreateActivity.this.folderTagsHeader.setPreviewColor(!FilterCreateActivity.this.getUserConfig().isPremium() ? -1 : FilterCreateActivity.this.newFilterColor, true);
            }
            FilterCreateActivity.this.checkDoneButton(true);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            ItemInner itemInner = (ItemInner) FilterCreateActivity.this.items.get(i);
            if (itemInner == null) {
                return 3;
            }
            return itemInner.viewType;
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        ArrayList arrayList = new ArrayList();
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda13
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.lambda$getThemeDescriptions$28();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{HeaderCell.class, TextCell.class, UserCell.class}, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        int i = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_text_RedRegular));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueText4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"ImageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_switchTrackChecked));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{UserCell.class}, new String[]{"adminTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_profile_creatorIcon));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"imageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayIcon));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, (Paint[]) null, (Drawable[]) null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteGrayText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, (Paint[]) null, (Drawable[]) null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteBlueText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, Theme.avatarDrawables, null, Theme.key_avatar_text));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundRed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundOrange));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundViolet));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundGreen));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundCyan));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundBlue));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundPink));
        return arrayList;
    }

    public /* synthetic */ void lambda$getThemeDescriptions$28() {
        RecyclerListView recyclerListView = this.listView;
        if (recyclerListView != null) {
            int childCount = recyclerListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.listView.getChildAt(i);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(0);
                }
            }
        }
    }

    private static class ButtonCell extends FrameLayout {
        private boolean divider;
        private ImageView imageView;
        private int lastIconResId;
        private TextView textView;
        private Boolean translateText;

        public ButtonCell(Context context) {
            super(context);
            this.divider = true;
            this.translateText = null;
            ImageView imageView = new ImageView(context);
            this.imageView = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            addView(this.imageView, LayoutHelper.createFrame(24, 24.0f, (LocaleController.isRTL ? 5 : 3) | 16, 24.0f, 0.0f, 24.0f, 0.0f));
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextSize(1, 16.0f);
            this.textView.setLines(1);
            this.textView.setSingleLine();
            TextView textView2 = this.textView;
            boolean z = LocaleController.isRTL;
            textView2.setPadding(z ? 24 : 0, 0, z ? 0 : 24, 0);
            this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
            TextView textView3 = this.textView;
            boolean z2 = LocaleController.isRTL;
            addView(textView3, LayoutHelper.createFrame(-1, -2.0f, 23, z2 ? 0.0f : 72.0f, 0.0f, z2 ? 72.0f : 0.0f, 0.0f));
        }

        public void setRed(boolean z) {
            this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(z ? Theme.key_text_RedBold : Theme.key_windowBackgroundWhiteBlueText2), PorterDuff.Mode.MULTIPLY));
            this.textView.setTextColor(Theme.getColor(z ? Theme.key_text_RedRegular : Theme.key_windowBackgroundWhiteBlueText4));
        }

        public void set(int i, CharSequence charSequence, boolean z) {
            int i2 = LocaleController.isRTL ? -1 : 1;
            boolean z2 = false;
            if (i == 0) {
                this.imageView.setVisibility(8);
            } else {
                this.imageView.setVisibility(0);
                this.imageView.setImageResource(i);
            }
            if (LocaleController.isRTL) {
                ((ViewGroup.MarginLayoutParams) this.textView.getLayoutParams()).rightMargin = AndroidUtilities.m1081dp(i == 0 ? 24.0f : 72.0f);
            } else {
                ((ViewGroup.MarginLayoutParams) this.textView.getLayoutParams()).leftMargin = AndroidUtilities.m1081dp(i == 0 ? 24.0f : 72.0f);
            }
            this.textView.setText(charSequence);
            if (!z && i != 0) {
                z2 = true;
            }
            Boolean bool = this.translateText;
            if (bool == null || bool.booleanValue() != z2) {
                this.translateText = Boolean.valueOf(z2);
                if (this.lastIconResId == i) {
                    this.textView.clearAnimation();
                    this.textView.animate().translationX(z2 ? AndroidUtilities.m1081dp(i2 * (-7)) : 0.0f).setDuration(180L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                } else {
                    this.textView.setTranslationX(z2 ? AndroidUtilities.m1081dp(i2 * (-7)) : 0.0f);
                }
            }
            this.divider = z;
            setWillNotDraw(!z);
            this.lastIconResId = i;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (this.divider) {
                canvas.drawRect(this.textView.getLeft(), getMeasuredHeight() - 1, this.textView.getRight(), getMeasuredHeight(), Theme.dividerPaint);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(48.0f), TLObject.FLAG_30));
        }
    }

    private static class CreateLinkCell extends FrameLayout {
        ImageView imageView;
        boolean needDivider;
        TextView textView;

        public CreateLinkCell(Context context) {
            super(context);
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.textView.setTextSize(1, 16.0f);
            this.textView.setText(LocaleController.getString(C2702R.string.CreateNewLink));
            this.textView.setGravity(LocaleController.isRTL ? 5 : 3);
            TextView textView2 = this.textView;
            boolean z = LocaleController.isRTL;
            textView2.setPadding(z ? 16 : 0, 0, z ? 0 : 16, 0);
            TextView textView3 = this.textView;
            boolean z2 = LocaleController.isRTL;
            addView(textView3, LayoutHelper.createFrame(-1, -2.0f, 23, z2 ? 0.0f : 64.0f, 0.0f, z2 ? 64.0f : 0.0f, 0.0f));
            this.imageView = new ImageView(context);
            Drawable drawable = context.getResources().getDrawable(C2702R.drawable.poll_add_circle);
            Drawable drawable2 = context.getResources().getDrawable(C2702R.drawable.poll_add_plus);
            int color = Theme.getColor(Theme.key_featuredStickers_addButton);
            PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
            drawable.setColorFilter(new PorterDuffColorFilter(color, mode));
            drawable2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_checkboxCheck), mode));
            this.imageView.setImageDrawable(new CombinedDrawable(drawable, drawable2));
            this.imageView.setScaleType(ImageView.ScaleType.CENTER);
            ImageView imageView = this.imageView;
            boolean z3 = LocaleController.isRTL;
            addView(imageView, LayoutHelper.createFrame(32, 32.0f, (z3 ? 5 : 3) | 16, z3 ? 0.0f : 16.0f, 0.0f, z3 ? 16.0f : 0.0f, 0.0f));
        }

        public void setText(String str) {
            this.textView.setText(str);
        }

        public void setDivider(boolean z) {
            if (this.needDivider != z) {
                this.needDivider = z;
                setWillNotDraw(!z);
            }
        }

        @Override // android.view.View
        public void setEnabled(boolean z) {
            super.setEnabled(z);
            this.textView.setAlpha(z ? 1.0f : 0.5f);
            this.imageView.setAlpha(z ? 1.0f : 0.5f);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (this.needDivider) {
                canvas.drawRect(this.textView.getLeft(), getMeasuredHeight() - 1, this.textView.getRight(), getMeasuredHeight(), Theme.dividerPaint);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(45.0f), TLObject.FLAG_30));
        }
    }

    static class LinkCell extends FrameLayout {
        private int currentAccount;
        private int filterId;
        private BaseFragment fragment;
        private TL_chatlists.TL_exportedChatlistInvite lastInvite;
        private boolean lastRevoked;
        protected String lastUrl;
        Drawable linkIcon;
        boolean needDivider;
        ImageView optionsIcon;
        Paint paint;
        float revokeT;
        Drawable revokedLinkIcon;
        Paint revokedPaint;
        AnimatedTextView subtitleTextView;
        AnimatedTextView titleTextView;
        private ValueAnimator valueAnimator;

        protected abstract void onDelete(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite);

        public LinkCell(Context context, BaseFragment baseFragment, int i, int i2) {
            super(context);
            this.fragment = baseFragment;
            this.currentAccount = i;
            this.filterId = i2;
            setImportantForAccessibility(1);
            AnimatedTextView animatedTextView = new AnimatedTextView(context, true, true, false);
            this.titleTextView = animatedTextView;
            animatedTextView.setTextSize(AndroidUtilities.m1081dp(15.66f));
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.titleTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.titleTextView.setEllipsizeByGradient(true);
            AnimatedTextView animatedTextView2 = this.titleTextView;
            boolean z = LocaleController.isRTL;
            addView(animatedTextView2, LayoutHelper.createFrame(-1, 20.0f, 55, z ? 56.0f : 64.0f, 10.33f, z ? 64.0f : 56.0f, 0.0f));
            AnimatedTextView animatedTextView3 = new AnimatedTextView(context, false, false, false);
            this.subtitleTextView = animatedTextView3;
            animatedTextView3.setTextSize(AndroidUtilities.m1081dp(13.0f));
            this.subtitleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
            this.subtitleTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            AnimatedTextView animatedTextView4 = this.subtitleTextView;
            boolean z2 = LocaleController.isRTL;
            addView(animatedTextView4, LayoutHelper.createFrame(-1, 16.0f, 55, z2 ? 56.0f : 64.0f, 33.33f, z2 ? 64.0f : 56.0f, 0.0f));
            ImageView imageView = new ImageView(context);
            this.optionsIcon = imageView;
            imageView.setImageDrawable(getContext().getResources().getDrawable(C2702R.drawable.ic_ab_other));
            this.optionsIcon.setScaleType(ImageView.ScaleType.CENTER);
            this.optionsIcon.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector)));
            ImageView imageView2 = this.optionsIcon;
            int color = Theme.getColor(Theme.key_stickers_menu);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            imageView2.setColorFilter(new PorterDuffColorFilter(color, mode));
            this.optionsIcon.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$LinkCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(view);
                }
            });
            this.optionsIcon.setContentDescription(LocaleController.getString(C2702R.string.AccDescrMoreOptions));
            ImageView imageView3 = this.optionsIcon;
            boolean z3 = LocaleController.isRTL;
            addView(imageView3, LayoutHelper.createFrame(40, 40.0f, (z3 ? 3 : 5) | 16, z3 ? 8.0f : 4.0f, 4.0f, z3 ? 4.0f : 8.0f, 4.0f));
            Paint paint = new Paint();
            this.paint = paint;
            paint.setColor(Theme.getColor(Theme.key_featuredStickers_addButton));
            Paint paint2 = new Paint();
            this.revokedPaint = paint2;
            paint2.setColor(Theme.getColor(Theme.key_color_red));
            Drawable drawableMutate = getContext().getResources().getDrawable(C2702R.drawable.msg_link_1).mutate();
            this.linkIcon = drawableMutate;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(-1, mode));
            Drawable drawableMutate2 = getContext().getResources().getDrawable(C2702R.drawable.msg_link_2).mutate();
            this.revokedLinkIcon = drawableMutate2;
            drawableMutate2.setColorFilter(new PorterDuffColorFilter(-1, mode));
            setWillNotDraw(false);
        }

        public /* synthetic */ void lambda$new$0(View view) {
            options();
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int measuredWidth = LocaleController.isRTL ? getMeasuredWidth() - AndroidUtilities.m1081dp(32.0f) : AndroidUtilities.m1081dp(32.0f);
            float f = measuredWidth;
            canvas.drawCircle(f, getMeasuredHeight() / 2.0f, AndroidUtilities.m1081dp(16.0f), this.paint);
            if (this.revokeT > 0.0f) {
                canvas.drawCircle(f, getMeasuredHeight() / 2.0f, AndroidUtilities.m1081dp(16.0f) * this.revokeT, this.revokedPaint);
            }
            float f2 = this.revokeT;
            if (f2 < 1.0f) {
                this.linkIcon.setAlpha((int) ((1.0f - f2) * 255.0f));
                this.linkIcon.setBounds(measuredWidth - AndroidUtilities.m1081dp(14.0f), (getMeasuredHeight() / 2) - AndroidUtilities.m1081dp(14.0f), AndroidUtilities.m1081dp(14.0f) + measuredWidth, (getMeasuredHeight() / 2) + AndroidUtilities.m1081dp(14.0f));
                this.linkIcon.draw(canvas);
            }
            float f3 = this.revokeT;
            if (f3 > 0.0f) {
                this.revokedLinkIcon.setAlpha((int) (f3 * 255.0f));
                this.revokedLinkIcon.setBounds(measuredWidth - AndroidUtilities.m1081dp(14.0f), (getMeasuredHeight() / 2) - AndroidUtilities.m1081dp(14.0f), measuredWidth + AndroidUtilities.m1081dp(14.0f), (getMeasuredHeight() / 2) + AndroidUtilities.m1081dp(14.0f));
                this.revokedLinkIcon.draw(canvas);
            }
            if (this.needDivider) {
                canvas.drawRect(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1081dp(64.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1081dp(64.0f) : 0), getMeasuredHeight(), Theme.dividerPaint);
            }
        }

        public void setRevoked(boolean z, boolean z2) {
            this.lastRevoked = z;
            if ((z ? 1.0f : 0.0f) != this.revokeT) {
                ValueAnimator valueAnimator = this.valueAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.valueAnimator = null;
                }
                if (z2) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.revokeT, z ? 1.0f : 0.0f);
                    this.valueAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.FilterCreateActivity$LinkCell$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$setRevoked$1(valueAnimator2);
                        }
                    });
                    this.valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.FilterCreateActivity.LinkCell.1
                        final /* synthetic */ boolean val$value;

                        C54151(boolean z3) {
                            z = z3;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            LinkCell linkCell = LinkCell.this;
                            linkCell.revokeT = z ? 1.0f : 0.0f;
                            linkCell.invalidate();
                        }
                    });
                    this.valueAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    this.valueAnimator.setDuration(350L);
                    this.valueAnimator.start();
                    return;
                }
                this.revokeT = z3 ? 1.0f : 0.0f;
                invalidate();
            }
        }

        public /* synthetic */ void lambda$setRevoked$1(ValueAnimator valueAnimator) {
            this.revokeT = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
        }

        /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$LinkCell$1 */
        class C54151 extends AnimatorListenerAdapter {
            final /* synthetic */ boolean val$value;

            C54151(boolean z3) {
                z = z3;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LinkCell linkCell = LinkCell.this;
                linkCell.revokeT = z ? 1.0f : 0.0f;
                linkCell.invalidate();
            }
        }

        public void setInvite(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite, boolean z) {
            boolean z2 = this.lastInvite == tL_exportedChatlistInvite;
            this.lastInvite = tL_exportedChatlistInvite;
            String strSubstring = tL_exportedChatlistInvite.url;
            this.lastUrl = strSubstring;
            if (strSubstring.startsWith("http://")) {
                strSubstring = strSubstring.substring(7);
            }
            if (strSubstring.startsWith("https://")) {
                strSubstring = strSubstring.substring(8);
            }
            if (TextUtils.isEmpty(tL_exportedChatlistInvite.title)) {
                this.titleTextView.setText(strSubstring, z2);
            } else {
                this.titleTextView.setText(tL_exportedChatlistInvite.title, z2);
            }
            this.subtitleTextView.setText(LocaleController.formatPluralString("FilterInviteChats", tL_exportedChatlistInvite.peers.size(), new Object[0]), z2);
            if (this.needDivider != z) {
                this.needDivider = z;
                invalidate();
            }
            setRevoked(tL_exportedChatlistInvite.revoked, z2);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(60.0f), TLObject.FLAG_30));
        }

        public void options() {
            BaseFragment baseFragment = this.fragment;
            if (baseFragment instanceof FilterCreateActivity) {
                RecyclerListView recyclerListView = ((FilterCreateActivity) baseFragment).listView;
                ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(this.fragment, this);
                itemOptionsMakeOptions.setScrimViewBackground(recyclerListView.getClipBackground(this));
                itemOptionsMakeOptions.add(C2702R.drawable.msg_qrcode, LocaleController.getString(C2702R.string.GetQRCode), new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$LinkCell$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.qrcode();
                    }
                });
                itemOptionsMakeOptions.add(C2702R.drawable.msg_delete, (CharSequence) LocaleController.getString(C2702R.string.DeleteLink), true, new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$LinkCell$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.deleteLink();
                    }
                });
                if (LocaleController.isRTL) {
                    itemOptionsMakeOptions.setGravity(3);
                }
                itemOptionsMakeOptions.show();
            }
        }

        private String getSlug() {
            String str = this.lastUrl;
            if (str == null) {
                return null;
            }
            return str.substring(str.lastIndexOf(47) + 1);
        }

        public void deleteLink() {
            String slug = getSlug();
            if (slug == null) {
                return;
            }
            TL_chatlists.TL_chatlists_deleteExportedInvite tL_chatlists_deleteExportedInvite = new TL_chatlists.TL_chatlists_deleteExportedInvite();
            TL_chatlists.TL_inputChatlistDialogFilter tL_inputChatlistDialogFilter = new TL_chatlists.TL_inputChatlistDialogFilter();
            tL_chatlists_deleteExportedInvite.chatlist = tL_inputChatlistDialogFilter;
            tL_inputChatlistDialogFilter.filter_id = this.filterId;
            tL_chatlists_deleteExportedInvite.slug = slug;
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$LinkCell$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$deleteLink$4();
                }
            };
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_chatlists_deleteExportedInvite, new RequestDelegate() { // from class: org.telegram.ui.FilterCreateActivity$LinkCell$$ExternalSyntheticLambda5
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$deleteLink$6(runnable, tLObject, tL_error);
                }
            });
            AndroidUtilities.runOnUIThread(runnable, 150L);
        }

        public /* synthetic */ void lambda$deleteLink$4() {
            onDelete(this.lastInvite);
        }

        public /* synthetic */ void lambda$deleteLink$6(final Runnable runnable, TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$LinkCell$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$deleteLink$5(tL_error, runnable);
                }
            });
        }

        public /* synthetic */ void lambda$deleteLink$5(TLRPC.TL_error tL_error, Runnable runnable) {
            if (tL_error != null) {
                BulletinFactory.m1195of(this.fragment).createErrorBulletin(LocaleController.getString(C2702R.string.UnknownError)).show();
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
        }

        public void qrcode() {
            if (this.lastUrl == null) {
                return;
            }
            QRCodeBottomSheet qRCodeBottomSheet = new QRCodeBottomSheet(getContext(), LocaleController.getString(C2702R.string.InviteByQRCode), this.lastUrl, LocaleController.getString(C2702R.string.QRCodeLinkHelpFolder), false);
            qRCodeBottomSheet.setCenterAnimation(C2702R.raw.qr_code_logo);
            qRCodeBottomSheet.show();
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            String str;
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            StringBuilder sb = new StringBuilder();
            TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite = this.lastInvite;
            String str2 = _UrlKt.FRAGMENT_ENCODE_SET;
            if (tL_exportedChatlistInvite == null || TextUtils.isEmpty(tL_exportedChatlistInvite.title)) {
                str = _UrlKt.FRAGMENT_ENCODE_SET;
            } else {
                str = this.lastInvite.title + "\n ";
            }
            sb.append(str);
            sb.append(LocaleController.getString(C2702R.string.InviteLink));
            sb.append(", ");
            sb.append((Object) this.subtitleTextView.getText());
            TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite2 = this.lastInvite;
            if (tL_exportedChatlistInvite2 != null && TextUtils.isEmpty(tL_exportedChatlistInvite2.title)) {
                str2 = "\n\n" + this.lastInvite.url;
            }
            sb.append(str2);
            accessibilityNodeInfo.setContentDescription(sb.toString());
        }
    }

    public static void hideNew(int i) {
        MessagesController.getGlobalMainSettings().edit().putBoolean("n_" + i, true).apply();
    }

    public static CharSequence withNew(int i, CharSequence charSequence, boolean z) {
        Context context;
        if (i >= 0) {
            if (!MessagesController.getGlobalMainSettings().getBoolean("n_" + i, false) && (context = ApplicationLoader.applicationContext) != null) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
                spannableStringBuilder.append((CharSequence) "  ");
                SpannableString spannableString = new SpannableString("NEW");
                if (z) {
                    Drawable drawableMutate = context.getResources().getDrawable(C2702R.drawable.msg_other_new_outline).mutate();
                    drawableMutate.setBounds(0, -AndroidUtilities.m1081dp(8.0f), drawableMutate.getIntrinsicWidth(), drawableMutate.getIntrinsicHeight() - AndroidUtilities.m1081dp(8.0f));
                    spannableString.setSpan(new ColorImageSpan(drawableMutate, 0), 0, spannableString.length(), 33);
                } else {
                    Drawable drawableMutate2 = context.getResources().getDrawable(C2702R.drawable.msg_other_new_filled).mutate();
                    Drawable drawableMutate3 = context.getResources().getDrawable(C2702R.drawable.msg_other_new_filled_text).mutate();
                    int color = Theme.getColor(Theme.key_featuredStickers_unread);
                    PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
                    drawableMutate2.setColorFilter(new PorterDuffColorFilter(color, mode));
                    drawableMutate3.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_buttonText), mode));
                    CombinedDrawable combinedDrawable = new CombinedDrawable(drawableMutate2, drawableMutate3);
                    combinedDrawable.setBounds(0, 0, combinedDrawable.getIntrinsicWidth(), combinedDrawable.getIntrinsicHeight());
                    spannableString.setSpan(new ImageSpan(combinedDrawable, 0), 0, spannableString.length(), 33);
                }
                spannableStringBuilder.append((CharSequence) spannableString);
                return spannableStringBuilder;
            }
        }
        return charSequence;
    }

    public static class ColorImageSpan extends ImageSpan {
        int lastColor;

        public ColorImageSpan(Drawable drawable, int i) {
            super(drawable, i);
        }

        @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            if (paint.getColor() != this.lastColor && getDrawable() != null) {
                Drawable drawable = getDrawable();
                int color = paint.getColor();
                this.lastColor = color;
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            }
            super.draw(canvas, charSequence, i, i2, f, i3, i4, i5, paint);
        }
    }

    /* JADX INFO: loaded from: classes.dex */
    public static class NewSpan extends ReplacementSpan {
        Paint bgPaint;
        private int color;
        private int fontSize;
        float height;
        StaticLayout layout;
        private boolean outline;
        private CharSequence text;
        TextPaint textPaint;
        public boolean usePaintAlpha;
        float width;

        public NewSpan(boolean z, int i) {
            this.textPaint = new TextPaint(1);
            this.bgPaint = new Paint(1);
            this.text = "NEW";
            this.outline = z;
            this.fontSize = i;
            this.textPaint.setTypeface(AndroidUtilities.bold());
            if (z) {
                this.bgPaint.setStyle(Paint.Style.STROKE);
                this.bgPaint.setStrokeWidth(AndroidUtilities.dpf2(1.33f));
                this.textPaint.setTextSize(AndroidUtilities.m1081dp(i < 0 ? 10.0f : i));
                this.textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                this.textPaint.setStrokeWidth(AndroidUtilities.dpf2(0.2f));
                this.textPaint.setLetterSpacing(0.03f);
                return;
            }
            this.bgPaint.setStyle(Paint.Style.FILL);
            this.textPaint.setTextSize(AndroidUtilities.m1081dp(i < 0 ? 12.0f : i));
        }

        public void setTypeface(Typeface typeface) {
            this.textPaint.setTypeface(typeface);
        }

        public NewSpan(float f) {
            this.textPaint = new TextPaint(1);
            this.bgPaint = new Paint(1);
            this.text = "NEW";
            this.outline = false;
            this.textPaint.setTypeface(AndroidUtilities.bold());
            this.bgPaint.setStyle(Paint.Style.FILL);
            this.textPaint.setTextSize(AndroidUtilities.m1081dp(f));
        }

        public void setColor(int i) {
            this.color = i;
        }

        public void setText(CharSequence charSequence) {
            this.text = charSequence;
            if (this.layout != null) {
                this.layout = null;
                makeLayout();
            }
        }

        public StaticLayout makeLayout() {
            if (this.layout == null) {
                StaticLayout staticLayout = new StaticLayout(this.text, this.textPaint, AndroidUtilities.displaySize.x, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                this.layout = staticLayout;
                this.width = staticLayout.getLineWidth(0);
                this.height = this.layout.getHeight();
            }
            return this.layout;
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            makeLayout();
            return (int) (AndroidUtilities.m1081dp(10.0f) + this.width);
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            float fM1081dp;
            makeLayout();
            float alpha = this.usePaintAlpha ? paint.getAlpha() / 255.0f : 1.0f;
            int color = this.color;
            if (color == 0) {
                color = paint.getColor();
            }
            this.bgPaint.setColor(color);
            if (this.outline) {
                this.textPaint.setColor(color);
            } else {
                this.textPaint.setColor(AndroidUtilities.computePerceivedBrightness(color) > 0.721f ? -16777216 : -1);
            }
            this.bgPaint.setAlpha((int) (r4.getAlpha() * alpha));
            this.textPaint.setAlpha((int) (r4.getAlpha() * alpha));
            float fM1081dp2 = f + AndroidUtilities.m1081dp(2.0f);
            float fM1081dp3 = (i4 - this.height) + AndroidUtilities.m1081dp(1.0f);
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(fM1081dp2, fM1081dp3, this.width + fM1081dp2, this.height + fM1081dp3);
            if (this.outline) {
                fM1081dp = AndroidUtilities.m1081dp(3.66f);
                rectF.left -= AndroidUtilities.m1081dp(4.0f);
                rectF.top -= AndroidUtilities.m1081dp(2.33f);
                rectF.right += AndroidUtilities.m1081dp(3.66f);
                rectF.bottom += AndroidUtilities.m1081dp(1.33f);
            } else {
                fM1081dp = AndroidUtilities.m1081dp(4.4f);
                rectF.inset(AndroidUtilities.m1081dp(-4.0f), AndroidUtilities.m1081dp(this.fontSize == 8 ? -3.66f : -2.33f));
            }
            canvas.drawRoundRect(rectF, fM1081dp, fM1081dp, this.bgPaint);
            canvas.save();
            canvas.translate(fM1081dp2, fM1081dp3);
            this.layout.draw(canvas);
            canvas.restore();
        }
    }

    /* JADX INFO: loaded from: classes.dex */
    public static class TextSpan extends ReplacementSpan {
        Paint bgPaint = new Paint(1);
        private int colorKey;
        private final Theme.ResourcesProvider resourcesProvider;
        private Text text;

        public TextSpan(String str, float f, int i, Theme.ResourcesProvider resourcesProvider) {
            this.resourcesProvider = resourcesProvider;
            this.colorKey = i;
            this.text = new Text(str, f, AndroidUtilities.bold());
            this.bgPaint.setStyle(Paint.Style.FILL);
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return (int) (AndroidUtilities.m1081dp(9.33f) + this.text.getWidth());
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            int color = Theme.getColor(this.colorKey, this.resourcesProvider);
            this.bgPaint.setColor(Theme.multAlpha(color, 0.15f));
            float f2 = (i5 + i3) / 2.0f;
            float fM1081dp = AndroidUtilities.m1081dp(14.66f);
            RectF rectF = AndroidUtilities.rectTmp;
            float f3 = fM1081dp / 2.0f;
            rectF.set(f, f2 - f3, this.text.getWidth() + f + AndroidUtilities.m1081dp(9.33f), f3 + f2);
            canvas.drawRoundRect(rectF, AndroidUtilities.m1081dp(4.0f), AndroidUtilities.m1081dp(4.0f), this.bgPaint);
            this.text.draw(canvas, f + AndroidUtilities.m1081dp(4.66f), f2, color, 1.0f);
        }
    }

    private void onUpdate(final boolean z, final int i) {
        MessagesController.DialogFilter dialogFilter;
        if (!this.showedUpdateBulletin && (dialogFilter = this.filter) != null && dialogFilter.isChatlist() && this.filter.isMyChatlist()) {
            this.showedUpdateBulletin = true;
            this.showBulletinOnResume = new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onUpdate$29(z, i);
                }
            };
            if (getLayoutContainer() != null) {
                this.showBulletinOnResume.run();
                this.showBulletinOnResume = null;
            }
        }
    }

    public /* synthetic */ void lambda$onUpdate$29(boolean z, int i) {
        String pluralString;
        BulletinFactory bulletinFactoryM1195of = BulletinFactory.m1195of(this);
        int i2 = z ? C2702R.raw.folder_in : C2702R.raw.folder_out;
        if (z) {
            pluralString = LocaleController.formatPluralString("FolderLinkAddedChats", i, new Object[0]);
        } else {
            pluralString = LocaleController.formatPluralString("FolderLinkRemovedChats", i, new Object[0]);
        }
        bulletinFactoryM1195of.createSimpleBulletin(i2, pluralString, LocaleController.getString(C2702R.string.FolderLinkChatlistUpdate)).setDuration(5000).show();
    }

    public static class FilterInvitesBottomSheet extends BottomSheetWithRecyclerListView {
        private AdapterWithDiffUtils adapter;
        private FrameLayout bulletinContainer;
        private TextView button;
        private MessagesController.DialogFilter filter;
        private ArrayList invites;
        private final ArrayList items;
        private final ArrayList oldItems;

        public static void show(final BaseFragment baseFragment, final MessagesController.DialogFilter dialogFilter, final Runnable runnable) {
            final long jCurrentTimeMillis = System.currentTimeMillis();
            TL_chatlists.TL_chatlists_getExportedInvites tL_chatlists_getExportedInvites = new TL_chatlists.TL_chatlists_getExportedInvites();
            TL_chatlists.TL_inputChatlistDialogFilter tL_inputChatlistDialogFilter = new TL_chatlists.TL_inputChatlistDialogFilter();
            tL_chatlists_getExportedInvites.chatlist = tL_inputChatlistDialogFilter;
            tL_inputChatlistDialogFilter.filter_id = dialogFilter.f1551id;
            baseFragment.getConnectionsManager().sendRequest(tL_chatlists_getExportedInvites, new RequestDelegate() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$$ExternalSyntheticLambda0
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FilterCreateActivity.FilterInvitesBottomSheet.$r8$lambda$PtxB57vVtG7SpSeyo74XZjhcgzQ(baseFragment, tLObject, dialogFilter, tL_error, runnable, j);
                        }
                    });
                }
            });
        }

        public static /* synthetic */ void $r8$lambda$PtxB57vVtG7SpSeyo74XZjhcgzQ(BaseFragment baseFragment, TLObject tLObject, MessagesController.DialogFilter dialogFilter, TLRPC.TL_error tL_error, Runnable runnable, long j) {
            if (baseFragment == null || baseFragment.getContext() == null) {
                return;
            }
            if (tLObject instanceof TL_chatlists.TL_chatlists_exportedInvites) {
                TL_chatlists.TL_chatlists_exportedInvites tL_chatlists_exportedInvites = (TL_chatlists.TL_chatlists_exportedInvites) tLObject;
                baseFragment.getMessagesController().putChats(tL_chatlists_exportedInvites.chats, false);
                baseFragment.getMessagesController().putUsers(tL_chatlists_exportedInvites.users, false);
                new FilterInvitesBottomSheet(baseFragment, dialogFilter, tL_chatlists_exportedInvites.invites).show();
            } else if (tL_error != null && "FILTER_ID_INVALID".equals(tL_error.text) && !dialogFilter.isDefault()) {
                new FilterInvitesBottomSheet(baseFragment, dialogFilter, null).show();
            } else {
                BulletinFactory.m1195of(baseFragment).createErrorBulletin(LocaleController.getString(C2702R.string.UnknownError)).show();
            }
            if (runnable != null) {
                AndroidUtilities.runOnUIThread(runnable, Math.max(0L, 200 - (System.currentTimeMillis() - j)));
            }
        }

        public FilterInvitesBottomSheet(BaseFragment baseFragment, MessagesController.DialogFilter dialogFilter, ArrayList arrayList) {
            super(baseFragment, false, false);
            this.invites = new ArrayList();
            this.oldItems = new ArrayList();
            this.items = new ArrayList();
            this.filter = dialogFilter;
            if (arrayList != null) {
                this.invites.addAll(arrayList);
            }
            updateRows(false);
            this.actionBar.setTitle(getTitle());
            fixNavigationBar(Theme.getColor(Theme.key_dialogBackground));
            TextView textView = new TextView(getContext());
            this.button = textView;
            textView.setTextSize(1, 14.0f);
            this.button.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
            this.button.setTypeface(AndroidUtilities.bold());
            this.button.setBackground(Theme.AdaptiveRipple.filledRectByKey(Theme.key_featuredStickers_addButton, 8.0f));
            this.button.setText(LocaleController.getString(C2702R.string.FolderLinkShareButton));
            this.button.setGravity(17);
            this.button.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$2(view);
                }
            });
            FrameLayout.LayoutParams layoutParamsCreateFrame = LayoutHelper.createFrame(-1, 48.0f, 87, 16.0f, 10.0f, 16.0f, 10.0f);
            int i = layoutParamsCreateFrame.leftMargin;
            int i2 = this.backgroundPaddingLeft;
            layoutParamsCreateFrame.leftMargin = i + i2;
            layoutParamsCreateFrame.rightMargin += i2;
            this.containerView.addView(this.button, layoutParamsCreateFrame);
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.bulletinContainer = frameLayout;
            this.containerView.addView(frameLayout, LayoutHelper.createFrame(-1, 100.0f, 80, 6.0f, 0.0f, 6.0f, 0.0f));
            updateCreateInviteButton();
        }

        public /* synthetic */ void lambda$new$2(View view) {
            createLink();
        }

        public void updateCreateInviteButton() {
            this.button.setVisibility(this.invites.isEmpty() ? 0 : 8);
            this.recyclerListView.setPadding(AndroidUtilities.m1081dp(6.0f), 0, AndroidUtilities.m1081dp(6.0f), this.invites.isEmpty() ? AndroidUtilities.m1081dp(68.0f) : 0);
        }

        @Override // org.telegram.p026ui.Components.BottomSheetWithRecyclerListView
        protected CharSequence getTitle() {
            return getTitle(null);
        }

        protected CharSequence getTitle(TextView textView) {
            Object objReplaceAnimatedEmoji;
            if (this.filter == null) {
                objReplaceAnimatedEmoji = _UrlKt.FRAGMENT_ENCODE_SET;
            } else {
                Paint.FontMetricsInt fontMetricsInt = textView == null ? null : textView.getPaint().getFontMetricsInt();
                objReplaceAnimatedEmoji = MessageObject.replaceAnimatedEmoji(Emoji.replaceEmoji(new SpannableStringBuilder(this.filter.name), fontMetricsInt, false), this.filter.entities, fontMetricsInt);
            }
            return LocaleController.formatSpannable(C2702R.string.FolderLinkShareTitle2, objReplaceAnimatedEmoji);
        }

        public void updateRows(boolean z) {
            this.oldItems.clear();
            this.oldItems.addAll(this.items);
            this.items.clear();
            this.items.add(ItemInner.asHeader(null));
            if (!this.invites.isEmpty()) {
                this.items.add(ItemInner.asShadow(null));
                this.items.add(ItemInner.asCreateLink());
                for (int i = 0; i < this.invites.size(); i++) {
                    this.items.add(ItemInner.asLink((TL_chatlists.TL_exportedChatlistInvite) this.invites.get(i)));
                }
            }
            AdapterWithDiffUtils adapterWithDiffUtils = this.adapter;
            if (adapterWithDiffUtils != null) {
                if (z) {
                    adapterWithDiffUtils.setItems(this.oldItems, this.items);
                } else {
                    notifyDataSetChanged();
                }
            }
        }

        /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1 */
        class C54081 extends AdapterWithDiffUtils {
            C54081() {
            }

            private RecyclerView.Adapter realAdapter() {
                return ((BottomSheetWithRecyclerListView) FilterInvitesBottomSheet.this).recyclerListView.getAdapter();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemChanged(int i) {
                realAdapter().notifyItemChanged(i + 1);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemChanged(int i, Object obj) {
                realAdapter().notifyItemChanged(i + 1, obj);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemInserted(int i) {
                realAdapter().notifyItemInserted(i + 1);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemMoved(int i, int i2) {
                realAdapter().notifyItemMoved(i + 1, i2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemRangeChanged(int i, int i2) {
                realAdapter().notifyItemRangeChanged(i + 1, i2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemRangeChanged(int i, int i2, Object obj) {
                realAdapter().notifyItemRangeChanged(i + 1, i2, obj);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemRangeInserted(int i, int i2) {
                realAdapter().notifyItemRangeInserted(i + 1, i2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemRangeRemoved(int i, int i2) {
                realAdapter().notifyItemRangeRemoved(i + 1, i2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyItemRemoved(int i) {
                realAdapter().notifyItemRemoved(i + 1);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void notifyDataSetChanged() {
                realAdapter().notifyDataSetChanged();
            }

            @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
            public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                return itemViewType == 8 || itemViewType == 7;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View textInfoPrivacyCell;
                if (i == 8) {
                    textInfoPrivacyCell = new CreateLinkCell(FilterInvitesBottomSheet.this.getContext());
                    textInfoPrivacyCell.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
                } else if (i == 7) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(FilterInvitesBottomSheet.this.getContext(), null, ((BottomSheet) FilterInvitesBottomSheet.this).currentAccount, FilterInvitesBottomSheet.this.filter.f1551id);
                    anonymousClass1.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
                    textInfoPrivacyCell = anonymousClass1;
                } else if (i == 6 || i == 3) {
                    textInfoPrivacyCell = new TextInfoPrivacyCell(FilterInvitesBottomSheet.this.getContext());
                    textInfoPrivacyCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
                } else {
                    FilterInvitesBottomSheet filterInvitesBottomSheet = FilterInvitesBottomSheet.this;
                    textInfoPrivacyCell = filterInvitesBottomSheet.new HeaderView(filterInvitesBottomSheet.getContext());
                }
                return new RecyclerListView.Holder(textInfoPrivacyCell);
            }

            /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1 */
            class AnonymousClass1 extends LinkCell {
                AnonymousClass1(Context context, BaseFragment baseFragment, int i, int i2) {
                    super(context, baseFragment, i, i2);
                }

                @Override // org.telegram.ui.FilterCreateActivity.LinkCell
                public void options() {
                    ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(FilterInvitesBottomSheet.this.container, this);
                    itemOptionsMakeOptions.add(C2702R.drawable.msg_copy, LocaleController.getString(C2702R.string.CopyLink), new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.copy();
                        }
                    });
                    itemOptionsMakeOptions.add(C2702R.drawable.msg_qrcode, LocaleController.getString(C2702R.string.GetQRCode), new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.qrcode();
                        }
                    });
                    itemOptionsMakeOptions.add(C2702R.drawable.msg_delete, (CharSequence) LocaleController.getString(C2702R.string.DeleteLink), true, new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.deleteLink();
                        }
                    });
                    if (LocaleController.isRTL) {
                        itemOptionsMakeOptions.setGravity(3);
                    }
                    itemOptionsMakeOptions.show();
                }

                public void copy() {
                    String str = this.lastUrl;
                    if (str != null && AndroidUtilities.addToClipboard(str)) {
                        BulletinFactory.m1194of(FilterInvitesBottomSheet.this.bulletinContainer, null).createCopyLinkBulletin().show();
                    }
                }

                @Override // org.telegram.ui.FilterCreateActivity.LinkCell
                protected void onDelete(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite) {
                    FilterInvitesBottomSheet.this.invites.remove(tL_exportedChatlistInvite);
                    FilterInvitesBottomSheet.this.updateCreateInviteButton();
                    FilterInvitesBottomSheet.this.updateRows(true);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int i) {
                return ((ItemInner) FilterInvitesBottomSheet.this.items.get(i)).viewType;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                int itemViewType = viewHolder.getItemViewType();
                ItemInner itemInner = (ItemInner) FilterInvitesBottomSheet.this.items.get(i);
                int i2 = i + 1;
                boolean z = i2 < FilterInvitesBottomSheet.this.items.size() && !((ItemInner) FilterInvitesBottomSheet.this.items.get(i2)).isShadow();
                if (itemViewType == 7) {
                    ((LinkCell) viewHolder.itemView).setInvite(itemInner.link, z);
                    return;
                }
                if (itemViewType != 6 && itemViewType != 3) {
                    if (itemViewType != 0 && itemViewType == 8) {
                        CreateLinkCell createLinkCell = (CreateLinkCell) viewHolder.itemView;
                        createLinkCell.setText(LocaleController.getString(C2702R.string.CreateNewInviteLink));
                        createLinkCell.setDivider(z);
                        return;
                    }
                    return;
                }
                TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                if (itemViewType == 6) {
                    textInfoPrivacyCell.setFixedSize(0);
                    textInfoPrivacyCell.setText(itemInner.text);
                } else {
                    textInfoPrivacyCell.setFixedSize(12);
                    textInfoPrivacyCell.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return FilterInvitesBottomSheet.this.items.size();
            }
        }

        @Override // org.telegram.p026ui.Components.BottomSheetWithRecyclerListView
        protected RecyclerListView.SelectionAdapter createAdapter(RecyclerListView recyclerListView) {
            C54081 c54081 = new AdapterWithDiffUtils() { // from class: org.telegram.ui.FilterCreateActivity.FilterInvitesBottomSheet.1
                C54081() {
                }

                private RecyclerView.Adapter realAdapter() {
                    return ((BottomSheetWithRecyclerListView) FilterInvitesBottomSheet.this).recyclerListView.getAdapter();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemChanged(int i) {
                    realAdapter().notifyItemChanged(i + 1);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemChanged(int i, Object obj) {
                    realAdapter().notifyItemChanged(i + 1, obj);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemInserted(int i) {
                    realAdapter().notifyItemInserted(i + 1);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemMoved(int i, int i2) {
                    realAdapter().notifyItemMoved(i + 1, i2);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemRangeChanged(int i, int i2) {
                    realAdapter().notifyItemRangeChanged(i + 1, i2);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemRangeChanged(int i, int i2, Object obj) {
                    realAdapter().notifyItemRangeChanged(i + 1, i2, obj);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemRangeInserted(int i, int i2) {
                    realAdapter().notifyItemRangeInserted(i + 1, i2);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemRangeRemoved(int i, int i2) {
                    realAdapter().notifyItemRangeRemoved(i + 1, i2);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyItemRemoved(int i) {
                    realAdapter().notifyItemRemoved(i + 1);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void notifyDataSetChanged() {
                    realAdapter().notifyDataSetChanged();
                }

                @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
                public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                    int itemViewType = viewHolder.getItemViewType();
                    return itemViewType == 8 || itemViewType == 7;
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                    View textInfoPrivacyCell;
                    if (i == 8) {
                        textInfoPrivacyCell = new CreateLinkCell(FilterInvitesBottomSheet.this.getContext());
                        textInfoPrivacyCell.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
                    } else if (i == 7) {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(FilterInvitesBottomSheet.this.getContext(), null, ((BottomSheet) FilterInvitesBottomSheet.this).currentAccount, FilterInvitesBottomSheet.this.filter.f1551id);
                        anonymousClass1.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
                        textInfoPrivacyCell = anonymousClass1;
                    } else if (i == 6 || i == 3) {
                        textInfoPrivacyCell = new TextInfoPrivacyCell(FilterInvitesBottomSheet.this.getContext());
                        textInfoPrivacyCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
                    } else {
                        FilterInvitesBottomSheet filterInvitesBottomSheet = FilterInvitesBottomSheet.this;
                        textInfoPrivacyCell = filterInvitesBottomSheet.new HeaderView(filterInvitesBottomSheet.getContext());
                    }
                    return new RecyclerListView.Holder(textInfoPrivacyCell);
                }

                /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1 */
                class AnonymousClass1 extends LinkCell {
                    AnonymousClass1(Context context, BaseFragment baseFragment, int i, int i2) {
                        super(context, baseFragment, i, i2);
                    }

                    @Override // org.telegram.ui.FilterCreateActivity.LinkCell
                    public void options() {
                        ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(FilterInvitesBottomSheet.this.container, this);
                        itemOptionsMakeOptions.add(C2702R.drawable.msg_copy, LocaleController.getString(C2702R.string.CopyLink), new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.copy();
                            }
                        });
                        itemOptionsMakeOptions.add(C2702R.drawable.msg_qrcode, LocaleController.getString(C2702R.string.GetQRCode), new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.qrcode();
                            }
                        });
                        itemOptionsMakeOptions.add(C2702R.drawable.msg_delete, (CharSequence) LocaleController.getString(C2702R.string.DeleteLink), true, new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$1$1$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.deleteLink();
                            }
                        });
                        if (LocaleController.isRTL) {
                            itemOptionsMakeOptions.setGravity(3);
                        }
                        itemOptionsMakeOptions.show();
                    }

                    public void copy() {
                        String str = this.lastUrl;
                        if (str != null && AndroidUtilities.addToClipboard(str)) {
                            BulletinFactory.m1194of(FilterInvitesBottomSheet.this.bulletinContainer, null).createCopyLinkBulletin().show();
                        }
                    }

                    @Override // org.telegram.ui.FilterCreateActivity.LinkCell
                    protected void onDelete(TL_chatlists.TL_exportedChatlistInvite tL_exportedChatlistInvite) {
                        FilterInvitesBottomSheet.this.invites.remove(tL_exportedChatlistInvite);
                        FilterInvitesBottomSheet.this.updateCreateInviteButton();
                        FilterInvitesBottomSheet.this.updateRows(true);
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemViewType(int i) {
                    return ((ItemInner) FilterInvitesBottomSheet.this.items.get(i)).viewType;
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                    int itemViewType = viewHolder.getItemViewType();
                    ItemInner itemInner = (ItemInner) FilterInvitesBottomSheet.this.items.get(i);
                    int i2 = i + 1;
                    boolean z = i2 < FilterInvitesBottomSheet.this.items.size() && !((ItemInner) FilterInvitesBottomSheet.this.items.get(i2)).isShadow();
                    if (itemViewType == 7) {
                        ((LinkCell) viewHolder.itemView).setInvite(itemInner.link, z);
                        return;
                    }
                    if (itemViewType != 6 && itemViewType != 3) {
                        if (itemViewType != 0 && itemViewType == 8) {
                            CreateLinkCell createLinkCell = (CreateLinkCell) viewHolder.itemView;
                            createLinkCell.setText(LocaleController.getString(C2702R.string.CreateNewInviteLink));
                            createLinkCell.setDivider(z);
                            return;
                        }
                        return;
                    }
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (itemViewType == 6) {
                        textInfoPrivacyCell.setFixedSize(0);
                        textInfoPrivacyCell.setText(itemInner.text);
                    } else {
                        textInfoPrivacyCell.setFixedSize(12);
                        textInfoPrivacyCell.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return FilterInvitesBottomSheet.this.items.size();
                }
            };
            this.adapter = c54081;
            return c54081;
        }

        class HeaderView extends FrameLayout {
            private final ImageView closeImageView;
            private final ImageView imageView;
            private final TextView subtitleView;
            private final SpoilersTextView titleView;

            public HeaderView(Context context) {
                String string;
                super(context);
                ImageView imageView = new ImageView(context);
                this.imageView = imageView;
                ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
                imageView.setScaleType(scaleType);
                imageView.setImageResource(C2702R.drawable.msg_limit_links);
                imageView.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
                imageView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1081dp(22.0f), Theme.getColor(Theme.key_featuredStickers_addButton)));
                addView(imageView, LayoutHelper.createFrame(54, 44.0f, 49, 0.0f, 22.0f, 0.0f, 0.0f));
                SpoilersTextView spoilersTextView = new SpoilersTextView(context);
                this.titleView = spoilersTextView;
                spoilersTextView.setTypeface(AndroidUtilities.bold());
                spoilersTextView.setTextSize(1, 20.0f);
                int i = Theme.key_dialogTextBlack;
                spoilersTextView.setTextColor(Theme.getColor(i));
                spoilersTextView.setGravity(1);
                spoilersTextView.setText(FilterInvitesBottomSheet.this.getTitle(spoilersTextView));
                spoilersTextView.cacheType = (FilterInvitesBottomSheet.this.filter == null || !FilterInvitesBottomSheet.this.filter.title_noanimate) ? 0 : 26;
                addView(spoilersTextView, LayoutHelper.createFrame(-2, -2.0f, 49, 20.0f, 84.0f, 20.0f, 0.0f));
                TextView textView = new TextView(context);
                this.subtitleView = textView;
                if (FilterInvitesBottomSheet.this.invites.isEmpty()) {
                    string = LocaleController.getString(C2702R.string.FolderLinkShareSubtitleEmpty);
                } else {
                    string = LocaleController.getString(C2702R.string.FolderLinkShareSubtitle);
                }
                textView.setText(string);
                textView.setLines(2);
                textView.setGravity(1);
                textView.setTextSize(1, 14.0f);
                textView.setTextColor(Theme.getColor(i));
                addView(textView, LayoutHelper.createFrame(-2, -2.0f, 49, 30.0f, 117.0f, 30.0f, 0.0f));
                ImageView imageView2 = new ImageView(context);
                this.closeImageView = imageView2;
                imageView2.setScaleType(scaleType);
                imageView2.setImageResource(C2702R.drawable.msg_close);
                imageView2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText5), PorterDuff.Mode.MULTIPLY));
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$HeaderView$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$new$0(view);
                    }
                });
                addView(imageView2, LayoutHelper.createFrame(48, 48.0f, 53, 0.0f, -4.0f, 2.0f, 0.0f));
            }

            public /* synthetic */ void lambda$new$0(View view) {
                FilterInvitesBottomSheet.this.lambda$new$0();
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(171.0f), TLObject.FLAG_30));
            }
        }

        private void createLink() {
            ArrayList<TLRPC.InputPeer> arrayList = new ArrayList<>();
            for (int i = 0; i < this.filter.alwaysShow.size(); i++) {
                long jLongValue = this.filter.alwaysShow.get(i).longValue();
                if (jLongValue < 0 && FilterCreateActivity.canAddToFolder(getBaseFragment().getMessagesController().getChat(Long.valueOf(-jLongValue)))) {
                    arrayList.add(getBaseFragment().getMessagesController().getInputPeer(jLongValue));
                }
            }
            if (arrayList.isEmpty()) {
                lambda$new$0();
                getBaseFragment().presentFragment(new FilterChatlistActivity(this.filter, null));
                return;
            }
            TL_chatlists.TL_chatlists_exportChatlistInvite tL_chatlists_exportChatlistInvite = new TL_chatlists.TL_chatlists_exportChatlistInvite();
            TL_chatlists.TL_inputChatlistDialogFilter tL_inputChatlistDialogFilter = new TL_chatlists.TL_inputChatlistDialogFilter();
            tL_chatlists_exportChatlistInvite.chatlist = tL_inputChatlistDialogFilter;
            tL_inputChatlistDialogFilter.filter_id = this.filter.f1551id;
            tL_chatlists_exportChatlistInvite.peers = arrayList;
            tL_chatlists_exportChatlistInvite.title = _UrlKt.FRAGMENT_ENCODE_SET;
            getBaseFragment().getConnectionsManager().sendRequest(tL_chatlists_exportChatlistInvite, new RequestDelegate() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$$ExternalSyntheticLambda4
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$createLink$4(tLObject, tL_error);
                }
            });
        }

        public /* synthetic */ void lambda$createLink$4(final TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createLink$3(tL_error, tLObject);
                }
            });
        }

        public /* synthetic */ void lambda$createLink$3(TLRPC.TL_error tL_error, TLObject tLObject) {
            if (FilterCreateActivity.processErrors(tL_error, getBaseFragment(), BulletinFactory.m1194of(this.bulletinContainer, null)) && (tLObject instanceof TL_chatlists.TL_chatlists_exportedChatlistInvite)) {
                FilterCreateActivity.hideNew(0);
                lambda$new$0();
                getBaseFragment().getMessagesController().loadRemoteFilters(true);
                getBaseFragment().presentFragment(new FilterChatlistActivity(this.filter, ((TL_chatlists.TL_chatlists_exportedChatlistInvite) tLObject).invite));
            }
        }

        @Override // org.telegram.p026ui.Components.BottomSheetWithRecyclerListView
        public void onViewCreated(FrameLayout frameLayout) {
            super.onViewCreated(frameLayout);
            this.recyclerListView.setOverScrollMode(2);
            this.recyclerListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.FilterCreateActivity$FilterInvitesBottomSheet$$ExternalSyntheticLambda2
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                public final void onItemClick(View view, int i) {
                    this.f$0.lambda$onViewCreated$5(view, i);
                }
            });
            DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
            defaultItemAnimator.setSupportsChangeAnimations(false);
            defaultItemAnimator.setDelayAnimations(false);
            defaultItemAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            defaultItemAnimator.setDurations(350L);
            this.recyclerListView.setItemAnimator(defaultItemAnimator);
        }

        public /* synthetic */ void lambda$onViewCreated$5(View view, int i) {
            int i2 = i - 1;
            if (i2 < 0 || i2 >= this.items.size()) {
                return;
            }
            ItemInner itemInner = (ItemInner) this.items.get(i2);
            int i3 = itemInner.viewType;
            if (i3 == 7) {
                lambda$new$0();
                getBaseFragment().presentFragment(new FilterChatlistActivity(this.filter, itemInner.link));
            } else if (i3 == 8) {
                createLink();
            }
        }
    }

    public static boolean processErrors(TLRPC.TL_error tL_error, BaseFragment baseFragment, BulletinFactory bulletinFactory) {
        if (tL_error != null && !TextUtils.isEmpty(tL_error.text)) {
            if ("INVITE_PEERS_TOO_MUCH".equals(tL_error.text)) {
                new LimitReachedBottomSheet(baseFragment, baseFragment.getContext(), 4, baseFragment.getCurrentAccount(), null).show();
            } else if ("PEERS_LIST_EMPTY".equals(tL_error.text)) {
                bulletinFactory.createErrorBulletin(LocaleController.getString(C2702R.string.FolderLinkNoChatsError)).show();
            } else if ("USER_CHANNELS_TOO_MUCH".equals(tL_error.text)) {
                bulletinFactory.createErrorBulletin(LocaleController.getString(C2702R.string.FolderLinkOtherAdminLimitError)).show();
            } else if ("CHANNELS_TOO_MUCH".equals(tL_error.text)) {
                new LimitReachedBottomSheet(baseFragment, baseFragment.getContext(), 5, baseFragment.getCurrentAccount(), null).show();
            } else if ("INVITES_TOO_MUCH".equals(tL_error.text)) {
                new LimitReachedBottomSheet(baseFragment, baseFragment.getContext(), 12, baseFragment.getCurrentAccount(), null).show();
            } else if ("CHATLISTS_TOO_MUCH".equals(tL_error.text)) {
                new LimitReachedBottomSheet(baseFragment, baseFragment.getContext(), 13, baseFragment.getCurrentAccount(), null).show();
            } else if ("INVITE_SLUG_EXPIRED".equals(tL_error.text)) {
                bulletinFactory.createErrorBulletin(LocaleController.getString(C2702R.string.NoFolderFound)).show();
            } else if ("FILTER_INCLUDE_TOO_MUCH".equals(tL_error.text)) {
                new LimitReachedBottomSheet(baseFragment, baseFragment.getContext(), 4, baseFragment.getCurrentAccount(), null).show();
            } else if ("DIALOG_FILTERS_TOO_MUCH".equals(tL_error.text)) {
                new LimitReachedBottomSheet(baseFragment, baseFragment.getContext(), 3, baseFragment.getCurrentAccount(), null).show();
            } else {
                bulletinFactory.createErrorBulletin(LocaleController.getString(C2702R.string.UnknownError)).show();
            }
        }
        return true;
    }

    private class HeaderCellColorPreview extends HeaderCell {
        private final AnimatedColor animatedColor;
        private int currentColor;
        public final TextView noTag;
        private boolean noTagShown;
        public final AnimatedTextView previewView;

        public HeaderCellColorPreview(Context context) {
            super(context, Theme.key_windowBackgroundWhiteBlueHeader, 22, 15, false, ((BaseFragment) FilterCreateActivity.this).resourceProvider);
            TextView textView = new TextView(getContext());
            this.noTag = textView;
            textView.setTextSize(1, 14.0f);
            textView.setTextColor(FilterCreateActivity.this.getThemedColor(Theme.key_windowBackgroundWhiteGrayText2));
            textView.setText(LocaleController.getString(FilterCreateActivity.this.getUserConfig().isPremium() ? C2702R.string.FolderTagNoColor : C2702R.string.FolderTagNoColorPremium));
            textView.setGravity(5);
            int i = (LocaleController.isRTL ? 3 : 5) | 48;
            int i2 = this.padding;
            addView(textView, LayoutHelper.createFrame(-1, -1.0f, i, i2, 16.66f, i2, this.bottomMargin));
            textView.setAlpha(0.0f);
            C54131 c54131 = new AnimatedTextView(getContext(), false, true, true) { // from class: org.telegram.ui.FilterCreateActivity.HeaderCellColorPreview.1
                private final Paint backgroundPaint = new Paint(1);
                final /* synthetic */ FilterCreateActivity val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C54131(Context context2, boolean z, boolean z2, boolean z3, FilterCreateActivity filterCreateActivity) {
                    super(context2, z, z2, z3);
                    filterCreateActivity = filterCreateActivity;
                    this.backgroundPaint = new Paint(1);
                }

                @Override // android.view.View
                protected void dispatchDraw(Canvas canvas) {
                    int i3 = HeaderCellColorPreview.this.animatedColor.set(HeaderCellColorPreview.this.currentColor);
                    setTextColor(i3);
                    this.backgroundPaint.setColor(Theme.multAlpha(i3, Theme.isCurrentThemeDark() ? 0.2f : 0.1f));
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set((getWidth() - getDrawable().getCurrentWidth()) - AndroidUtilities.dpf2(9.32f), (getHeight() - AndroidUtilities.dpf2(14.66f)) / 2.0f, getWidth(), (getHeight() + AndroidUtilities.dpf2(14.66f)) / 2.0f);
                    canvas.drawRoundRect(rectF, AndroidUtilities.m1081dp(4.0f), AndroidUtilities.m1081dp(4.0f), this.backgroundPaint);
                    super.dispatchDraw(canvas);
                }
            };
            this.previewView = c54131;
            this.animatedColor = new AnimatedColor(c54131, 0L, 320L, CubicBezierInterpolator.EASE_OUT_QUINT);
            c54131.setTextSize(AndroidUtilities.m1081dp(10.0f));
            c54131.setTypeface(AndroidUtilities.bold());
            c54131.setGravity(5);
            c54131.setPadding(AndroidUtilities.m1081dp(4.66f), 0, AndroidUtilities.m1081dp(4.66f), 0);
            int i3 = LocaleController.isRTL ? 3 : 5;
            int i4 = this.padding;
            addView(c54131, LayoutHelper.createFrame(-1, -1.0f, i3 | 48, i4, 16.66f, i4, this.bottomMargin));
        }

        /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$HeaderCellColorPreview$1 */
        class C54131 extends AnimatedTextView {
            private final Paint backgroundPaint = new Paint(1);
            final /* synthetic */ FilterCreateActivity val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C54131(Context context2, boolean z, boolean z2, boolean z3, FilterCreateActivity filterCreateActivity) {
                super(context2, z, z2, z3);
                filterCreateActivity = filterCreateActivity;
                this.backgroundPaint = new Paint(1);
            }

            @Override // android.view.View
            protected void dispatchDraw(Canvas canvas) {
                int i3 = HeaderCellColorPreview.this.animatedColor.set(HeaderCellColorPreview.this.currentColor);
                setTextColor(i3);
                this.backgroundPaint.setColor(Theme.multAlpha(i3, Theme.isCurrentThemeDark() ? 0.2f : 0.1f));
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set((getWidth() - getDrawable().getCurrentWidth()) - AndroidUtilities.dpf2(9.32f), (getHeight() - AndroidUtilities.dpf2(14.66f)) / 2.0f, getWidth(), (getHeight() + AndroidUtilities.dpf2(14.66f)) / 2.0f);
                canvas.drawRoundRect(rectF, AndroidUtilities.m1081dp(4.0f), AndroidUtilities.m1081dp(4.0f), this.backgroundPaint);
                super.dispatchDraw(canvas);
            }
        }

        public void setPreviewColor(int i, boolean z) {
            this.noTag.setText(LocaleController.getString(FilterCreateActivity.this.getUserConfig().isPremium() ? C2702R.string.FolderTagNoColor : C2702R.string.FolderTagNoColorPremium));
            int themedColor = 0;
            boolean z2 = i < 0;
            if (!z2) {
                FilterCreateActivity filterCreateActivity = FilterCreateActivity.this;
                int[] iArr = Theme.keys_avatar_nameInMessage;
                themedColor = filterCreateActivity.getThemedColor(iArr[i % iArr.length]);
            }
            this.currentColor = themedColor;
            if (!z2) {
                this.previewView.setEmojiColor(themedColor);
            }
            if (!z) {
                this.animatedColor.set(this.currentColor, true);
            }
            if (z2 != this.noTagShown) {
                this.noTagShown = z2;
                ViewPropertyAnimator duration = this.noTag.animate().alpha(z2 ? 1.0f : 0.0f).setDuration(320L);
                CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
                duration.setInterpolator(cubicBezierInterpolator).start();
                this.previewView.animate().alpha(z2 ? 0.0f : 1.0f).setDuration(320L).setInterpolator(cubicBezierInterpolator).start();
            }
        }

        public void setPreviewText(CharSequence charSequence, boolean z) {
            if (charSequence == null) {
                charSequence = _UrlKt.FRAGMENT_ENCODE_SET;
            }
            boolean z2 = false;
            if (charSequence.length() > 12) {
                charSequence = charSequence.subSequence(0, 12);
            }
            AnimatedTextView animatedTextView = this.previewView;
            CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(charSequence, animatedTextView.getPaint().getFontMetricsInt(), false);
            if (z && !LocaleController.isRTL) {
                z2 = true;
            }
            animatedTextView.setText(charSequenceReplaceEmoji, z2);
        }

        public TextPaint getPreviewTextPaint() {
            return this.previewView.getPaint();
        }
    }

    private class HeaderCellWithRight extends HeaderCell {
        private final AnimatedTextView rightTextView;

        public HeaderCellWithRight(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            C54141 c54141 = new AnimatedTextView(context, true, true, true) { // from class: org.telegram.ui.FilterCreateActivity.HeaderCellWithRight.1
                final /* synthetic */ FilterCreateActivity val$this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C54141(Context context2, boolean z, boolean z2, boolean z3, FilterCreateActivity filterCreateActivity) {
                    super(context2, z, z2, z3);
                    filterCreateActivity = filterCreateActivity;
                }

                @Override // org.telegram.p026ui.Components.AnimatedTextView, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(i, i2);
                    setPivotX(getMeasuredWidth());
                }
            };
            this.rightTextView = c54141;
            c54141.setGravity(LocaleController.isRTL ? 3 : 5);
            c54141.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader, resourcesProvider));
            c54141.setTextSize(AndroidUtilities.dpf2(15.0f));
            addView(c54141, LayoutHelper.createFrame(-1, 18.0f, (LocaleController.isRTL ? 3 : 5) | 48, 22.0f, 17.0f, 22.0f, 0.0f));
            ScaleStateListAnimator.apply(c54141, 0.04f, 1.2f);
        }

        /* JADX INFO: renamed from: org.telegram.ui.FilterCreateActivity$HeaderCellWithRight$1 */
        class C54141 extends AnimatedTextView {
            final /* synthetic */ FilterCreateActivity val$this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C54141(Context context2, boolean z, boolean z2, boolean z3, FilterCreateActivity filterCreateActivity) {
                super(context2, z, z2, z3);
                filterCreateActivity = filterCreateActivity;
            }

            @Override // org.telegram.p026ui.Components.AnimatedTextView, android.view.View
            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
                setPivotX(getMeasuredWidth());
            }
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onInsets(int i, int i2, int i3, int i4) {
        this.listView.setPadding(0, 0, 0, i4);
        this.listView.setClipToPadding(false);
        UndoView undoView = this.undoView;
        if (undoView != null) {
            undoView.setTranslationY(-i4);
        }
    }
}
