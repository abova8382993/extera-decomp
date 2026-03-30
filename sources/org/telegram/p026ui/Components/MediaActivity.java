package org.telegram.p026ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Property;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserObject;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.ActionBarMenuItem;
import org.telegram.p026ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p026ui.ActionBar.AlertDialog;
import org.telegram.p026ui.ActionBar.BackDrawable;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.SimpleTextView;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.ActionBar.ThemeDescription;
import org.telegram.p026ui.Components.BottomPagerTabs;
import org.telegram.p026ui.Components.Bulletin;
import org.telegram.p026ui.Components.FloatingDebug.FloatingDebugController;
import org.telegram.p026ui.Components.FloatingDebug.FloatingDebugProvider;
import org.telegram.p026ui.Components.Paint.ShapeDetector;
import org.telegram.p026ui.Components.SharedMediaLayout;
import org.telegram.p026ui.ProfileActivity;
import org.telegram.p026ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p025tl.TL_stories;

/* JADX INFO: loaded from: classes5.dex */
public class MediaActivity extends BaseFragment implements SharedMediaLayout.SharedMediaPreloaderDelegate, FloatingDebugProvider, NotificationCenter.NotificationCenterDelegate {
    private SparseArray actionModeMessageObjects;
    private Runnable applyBulletin;
    ProfileActivity.AvatarImageView avatarImageView;
    private BackDrawable backDrawable;
    private ButtonWithCounterView button;
    private FrameLayout buttonContainer;
    private ActionBarMenuSubItem calendarItem;
    private TLRPC.ChatFull currentChatInfo;
    private TLRPC.UserFull currentUserInfo;
    private ActionBarMenuItem deleteItem;
    private long dialogId;
    private boolean filterPhotos;
    private boolean filterVideos;
    private final boolean[] firstSubtitleCheck;
    private String hashtag;
    private int initialTab;
    private int lastTab;
    private SimpleTextView[] nameTextView;
    private ActionBarMenuItem optionsItem;
    private AnimatedTextView selectedTextView;
    SharedMediaLayout sharedMediaLayout;
    private SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader;
    private int shiftDp;
    private ActionBarMenuSubItem showPhotosItem;
    private ActionBarMenuSubItem showVideosItem;
    private int storiesCount;
    private final ValueAnimator[] subtitleAnimator;
    private final boolean[] subtitleShown;
    private final float[] subtitleT;
    private AnimatedTextView[] subtitleTextView;
    private StoriesTabsView tabsView;
    private FrameLayout[] titles;
    private FrameLayout titlesContainer;
    private long topicId;
    private int type;
    private String username;
    private ActionBarMenuSubItem zoomInItem;
    private ActionBarMenuSubItem zoomOutItem;

    public MediaActivity(Bundle bundle, SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader) {
        super(bundle);
        this.titles = new FrameLayout[2];
        this.nameTextView = new SimpleTextView[2];
        this.subtitleTextView = new AnimatedTextView[2];
        this.filterPhotos = true;
        this.filterVideos = true;
        this.shiftDp = -12;
        this.subtitleShown = new boolean[2];
        this.subtitleT = new float[2];
        this.firstSubtitleCheck = new boolean[]{true, true};
        this.subtitleAnimator = new ValueAnimator[2];
        this.sharedMediaPreloader = sharedMediaPreloader;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        this.type = getArguments().getInt("type", 0);
        this.dialogId = getArguments().getLong("dialog_id");
        this.topicId = getArguments().getLong("topic_id", 0L);
        this.hashtag = getArguments().getString("hashtag", _UrlKt.FRAGMENT_ENCODE_SET);
        this.username = getArguments().getString("username", _UrlKt.FRAGMENT_ENCODE_SET);
        this.storiesCount = getArguments().getInt("storiesCount", -1);
        int i = this.type;
        this.initialTab = getArguments().getInt("start_from", i == 2 ? 9 : i == 1 ? 8 : 0);
        getNotificationCenter().addObserver(this, NotificationCenter.userInfoDidLoad);
        getNotificationCenter().addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        getNotificationCenter().addObserver(this, NotificationCenter.storiesEnabledUpdate);
        if (DialogObject.isUserDialog(this.dialogId) && this.topicId == 0) {
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(this.dialogId));
            if (UserObject.isUserSelf(user)) {
                getMessagesController().loadUserInfo(user, false, this.classGuid);
                this.currentUserInfo = getMessagesController().getUserFull(this.dialogId);
            }
        }
        if (this.sharedMediaPreloader == null) {
            this.sharedMediaPreloader = new SharedMediaLayout.SharedMediaPreloader(this);
        }
        this.sharedMediaPreloader.addDelegate(this);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        getNotificationCenter().removeObserver(this, NotificationCenter.userInfoDidLoad);
        getNotificationCenter().removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        getNotificationCenter().removeObserver(this, NotificationCenter.storiesEnabledUpdate);
        Runnable runnable = this.applyBulletin;
        if (runnable != null) {
            this.applyBulletin = null;
            AndroidUtilities.runOnUIThread(runnable);
        }
        Bulletin.removeDelegate(this);
        SharedMediaLayout sharedMediaLayout = this.sharedMediaLayout;
        if (sharedMediaLayout != null) {
            sharedMediaLayout.onDestroy();
        }
        SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader = this.sharedMediaPreloader;
        if (sharedMediaPreloader != null) {
            sharedMediaPreloader.onDestroy(this);
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i != NotificationCenter.userInfoDidLoad) {
            int i3 = NotificationCenter.didReceiveNewMessages;
            return;
        }
        if (((Long) objArr[0]).longValue() == this.dialogId) {
            TLRPC.UserFull userFull = (TLRPC.UserFull) objArr[1];
            this.currentUserInfo = userFull;
            SharedMediaLayout sharedMediaLayout = this.sharedMediaLayout;
            if (sharedMediaLayout != null) {
                sharedMediaLayout.setUserInfo(userFull);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:306:0x06ba  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View createView(android.content.Context r34) {
        /*
            Method dump skipped, instruction units count: 1764
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.Components.MediaActivity.createView(android.content.Context):android.view.View");
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$1 */
    class C43481 extends ActionBar.ActionBarMenuOnItemClick {
        C43481() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if (i == -1) {
                if (MediaActivity.this.sharedMediaLayout.closeActionMode(true)) {
                    return;
                }
                MediaActivity.this.finishFragment();
                return;
            }
            if (i != 2) {
                if (i == 10) {
                    SharedMediaLayout sharedMediaLayout = MediaActivity.this.sharedMediaLayout;
                    sharedMediaLayout.showMediaCalendar(sharedMediaLayout.getClosestTab(), false);
                    return;
                } else {
                    if (i == 11) {
                        MediaActivity.this.sharedMediaLayout.closeActionMode(true);
                        MediaActivity.this.sharedMediaLayout.getSearchItem().openSearch(false);
                        return;
                    }
                    return;
                }
            }
            if (MediaActivity.this.actionModeMessageObjects != null) {
                final ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < MediaActivity.this.actionModeMessageObjects.size(); i2++) {
                    TL_stories.StoryItem storyItem = ((MessageObject) MediaActivity.this.actionModeMessageObjects.valueAt(i2)).storyItem;
                    if (storyItem != null) {
                        arrayList.add(storyItem);
                    }
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MediaActivity.this.getContext(), MediaActivity.this.getResourceProvider());
                builder.setTitle(LocaleController.getString(arrayList.size() > 1 ? C2702R.string.DeleteStoriesTitle : C2702R.string.DeleteStoryTitle));
                builder.setMessage(LocaleController.formatPluralString("DeleteStoriesSubtitle", arrayList.size(), new Object[0]));
                builder.setPositiveButton(LocaleController.getString(C2702R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.MediaActivity$1$$ExternalSyntheticLambda0
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        this.f$0.lambda$onItemClick$0(arrayList, alertDialog, i3);
                    }
                });
                builder.setNegativeButton(LocaleController.getString(C2702R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.MediaActivity$1$$ExternalSyntheticLambda1
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i3) {
                        alertDialog.dismiss();
                    }
                });
                AlertDialog alertDialogCreate = builder.create();
                alertDialogCreate.show();
                alertDialogCreate.redPositive();
            }
        }

        public /* synthetic */ void lambda$onItemClick$0(ArrayList arrayList, AlertDialog alertDialog, int i) {
            MediaActivity.this.getMessagesController().getStoriesController().deleteStories(MediaActivity.this.dialogId, arrayList);
            MediaActivity.this.sharedMediaLayout.closeActionMode(false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$2 */
    class C43492 extends SizeNotifierFrameLayout {
        final /* synthetic */ FrameLayout val$avatarContainer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C43492(Context context, FrameLayout frameLayout) {
            super(context);
            frameLayout = frameLayout;
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            ((FrameLayout.LayoutParams) MediaActivity.this.sharedMediaLayout.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight() + (((BaseFragment) MediaActivity.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            layoutParams.topMargin = ((BaseFragment) MediaActivity.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0;
            layoutParams.height = ActionBar.getCurrentActionBarHeight();
            for (int i3 = 0; i3 < 2; i3++) {
                if (MediaActivity.this.nameTextView[i3] != null) {
                    ((FrameLayout.LayoutParams) MediaActivity.this.nameTextView[i3].getLayoutParams()).topMargin = (((ActionBar.getCurrentActionBarHeight() / 2) - AndroidUtilities.m1081dp(22.0f)) / 2) + AndroidUtilities.m1081dp((AndroidUtilities.isTablet() || getResources().getConfiguration().orientation != 2) ? 5.0f : 4.0f);
                }
                if (MediaActivity.this.subtitleTextView[i3] != null) {
                    ((FrameLayout.LayoutParams) MediaActivity.this.subtitleTextView[i3].getLayoutParams()).topMargin = ((ActionBar.getCurrentActionBarHeight() / 2) + (((ActionBar.getCurrentActionBarHeight() / 2) - AndroidUtilities.m1081dp(19.0f)) / 2)) - AndroidUtilities.m1081dp(7.0f);
                }
            }
            ((FrameLayout.LayoutParams) MediaActivity.this.avatarImageView.getLayoutParams()).topMargin = (ActionBar.getCurrentActionBarHeight() - AndroidUtilities.m1081dp(42.0f)) / 2;
            super.onMeasure(i, i2);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            SharedMediaLayout sharedMediaLayout = MediaActivity.this.sharedMediaLayout;
            if (sharedMediaLayout != null && sharedMediaLayout.isInFastScroll()) {
                return MediaActivity.this.sharedMediaLayout.dispatchFastScrollEvent(motionEvent);
            }
            SharedMediaLayout sharedMediaLayout2 = MediaActivity.this.sharedMediaLayout;
            if (sharedMediaLayout2 == null || !sharedMediaLayout2.checkPinchToZoom(motionEvent)) {
                return super.dispatchTouchEvent(motionEvent);
            }
            return true;
        }

        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout
        protected void drawList(Canvas canvas, boolean z, ArrayList arrayList) {
            MediaActivity.this.sharedMediaLayout.drawListForBlur(canvas, arrayList);
        }
    }

    public /* synthetic */ void lambda$createView$1(View view) {
        this.optionsItem.toggleSubMenu();
    }

    public /* synthetic */ void lambda$createView$2(View view) {
        Boolean boolZoomIn = this.sharedMediaLayout.zoomIn();
        if (boolZoomIn == null) {
            return;
        }
        boolean zBooleanValue = boolZoomIn.booleanValue();
        this.zoomOutItem.setEnabled(true);
        this.zoomOutItem.animate().alpha(this.zoomOutItem.isEnabled() ? 1.0f : 0.5f).start();
        this.zoomInItem.setEnabled(zBooleanValue);
        this.zoomInItem.animate().alpha(this.zoomInItem.isEnabled() ? 1.0f : 0.5f).start();
    }

    public /* synthetic */ void lambda$createView$3(View view) {
        Boolean boolZoomOut = this.sharedMediaLayout.zoomOut();
        if (boolZoomOut == null) {
            return;
        }
        this.zoomOutItem.setEnabled(boolZoomOut.booleanValue());
        this.zoomOutItem.animate().alpha(this.zoomOutItem.isEnabled() ? 1.0f : 0.5f).start();
        this.zoomInItem.setEnabled(true);
        this.zoomInItem.animate().alpha(this.zoomInItem.isEnabled() ? 1.0f : 0.5f).start();
    }

    public /* synthetic */ void lambda$createView$4(View view) {
        boolean z = this.filterPhotos;
        if (z && !this.filterVideos) {
            BotWebViewVibrationEffect.APP_ERROR.vibrate();
            ActionBarMenuSubItem actionBarMenuSubItem = this.showPhotosItem;
            int i = -this.shiftDp;
            this.shiftDp = i;
            AndroidUtilities.shakeViewSpring(actionBarMenuSubItem, i);
            return;
        }
        ActionBarMenuSubItem actionBarMenuSubItem2 = this.showPhotosItem;
        boolean z2 = !z;
        this.filterPhotos = z2;
        actionBarMenuSubItem2.setChecked(z2);
        this.sharedMediaLayout.setStoriesFilter(this.filterPhotos, this.filterVideos);
    }

    public /* synthetic */ void lambda$createView$5(View view) {
        boolean z = this.filterVideos;
        if (z && !this.filterPhotos) {
            BotWebViewVibrationEffect.APP_ERROR.vibrate();
            ActionBarMenuSubItem actionBarMenuSubItem = this.showVideosItem;
            int i = -this.shiftDp;
            this.shiftDp = i;
            AndroidUtilities.shakeViewSpring(actionBarMenuSubItem, i);
            return;
        }
        ActionBarMenuSubItem actionBarMenuSubItem2 = this.showVideosItem;
        boolean z2 = !z;
        this.filterVideos = z2;
        actionBarMenuSubItem2.setChecked(z2);
        this.sharedMediaLayout.setStoriesFilter(this.filterPhotos, this.filterVideos);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$3 */
    class C43503 extends ProfileActivity.AvatarImageView {
        C43503(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            if (getImageReceiver().hasNotThumb()) {
                accessibilityNodeInfo.setText(LocaleController.getString(C2702R.string.AccDescrProfilePicture));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, LocaleController.getString(C2702R.string.Open)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, LocaleController.getString(C2702R.string.AccDescrOpenInPhotoViewer)));
                return;
            }
            accessibilityNodeInfo.setVisibleToUser(false);
        }
    }

    public /* synthetic */ void lambda$createView$6(Integer num) {
        this.sharedMediaLayout.scrollToPage(num.intValue() + 8);
    }

    public /* synthetic */ void lambda$createView$10(View view) {
        int i;
        Runnable runnable = this.applyBulletin;
        if (runnable != null) {
            runnable.run();
            this.applyBulletin = null;
        }
        Bulletin.hideVisible();
        final boolean z = this.sharedMediaLayout.getClosestTab() == 9;
        final ArrayList arrayList = new ArrayList();
        if (this.actionModeMessageObjects != null) {
            i = 0;
            for (int i2 = 0; i2 < this.actionModeMessageObjects.size(); i2++) {
                TL_stories.StoryItem storyItem = ((MessageObject) this.actionModeMessageObjects.valueAt(i2)).storyItem;
                if (storyItem != null) {
                    arrayList.add(storyItem);
                    i++;
                }
            }
        } else {
            i = 0;
        }
        this.sharedMediaLayout.closeActionMode(false);
        if (z) {
            this.sharedMediaLayout.scrollToPage(8);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        final boolean[] zArr = new boolean[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            TL_stories.StoryItem storyItem2 = (TL_stories.StoryItem) arrayList.get(i3);
            zArr[i3] = storyItem2.pinned;
            storyItem2.pinned = z;
        }
        getMessagesController().getStoriesController().updateStoriesInLists(this.dialogId, arrayList);
        final boolean[] zArr2 = {false};
        this.applyBulletin = new Runnable() { // from class: org.telegram.ui.Components.MediaActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$7(arrayList, z);
            }
        };
        Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.MediaActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$8(zArr2, arrayList, zArr);
            }
        };
        (z ? BulletinFactory.m1195of(this).createSimpleBulletin(C2702R.raw.contact_check, LocaleController.formatPluralString("StorySavedTitle", i, new Object[0]), LocaleController.getString("StorySavedSubtitle"), LocaleController.getString("Undo"), runnable2).show() : BulletinFactory.m1195of(this).createSimpleBulletin(C2702R.raw.chats_archived, LocaleController.formatPluralString("StoryArchived", i, new Object[0]), LocaleController.getString("Undo"), 5000, runnable2).show()).setOnHideListener(new Runnable() { // from class: org.telegram.ui.Components.MediaActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$9(zArr2);
            }
        });
    }

    public /* synthetic */ void lambda$createView$7(ArrayList arrayList, boolean z) {
        getMessagesController().getStoriesController().updateStoriesPinned(this.dialogId, arrayList, z, null);
    }

    public /* synthetic */ void lambda$createView$8(boolean[] zArr, ArrayList arrayList, boolean[] zArr2) {
        zArr[0] = true;
        AndroidUtilities.cancelRunOnUIThread(this.applyBulletin);
        for (int i = 0; i < arrayList.size(); i++) {
            ((TL_stories.StoryItem) arrayList.get(i)).pinned = zArr2[i];
        }
        getMessagesController().getStoriesController().updateStoriesInLists(this.dialogId, arrayList);
    }

    public /* synthetic */ void lambda$createView$9(boolean[] zArr) {
        Runnable runnable;
        if (!zArr[0] && (runnable = this.applyBulletin) != null) {
            runnable.run();
        }
        this.applyBulletin = null;
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$4 */
    class C43514 implements Bulletin.Delegate {
        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ boolean allowLayoutChanges() {
            return Bulletin.Delegate.CC.$default$allowLayoutChanges(this);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ boolean bottomOffsetAnimated() {
            return Bulletin.Delegate.CC.$default$bottomOffsetAnimated(this);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ boolean clipWithGradient(int i) {
            return Bulletin.Delegate.CC.$default$clipWithGradient(this, i);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ int getTopOffset(int i) {
            return Bulletin.Delegate.CC.$default$getTopOffset(this, i);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ void onBottomOffsetChange(float f) {
            Bulletin.Delegate.CC.$default$onBottomOffsetChange(this, f);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ void onHide(Bulletin bulletin) {
            Bulletin.Delegate.CC.$default$onHide(this, bulletin);
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public /* synthetic */ void onShow(Bulletin bulletin) {
            Bulletin.Delegate.CC.$default$onShow(this, bulletin);
        }

        C43514() {
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public int getBottomOffset(int i) {
            return AndroidUtilities.m1081dp(64.0f);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$5 */
    class C43525 implements SharedMediaLayout.Delegate {
        @Override // org.telegram.ui.Components.SharedMediaLayout.Delegate
        public boolean canSearchMembers() {
            return false;
        }

        @Override // org.telegram.ui.Components.SharedMediaLayout.Delegate
        public TLRPC.Chat getCurrentChat() {
            return null;
        }

        @Override // org.telegram.ui.Components.SharedMediaLayout.Delegate
        public RecyclerListView getListView() {
            return null;
        }

        @Override // org.telegram.ui.Components.SharedMediaLayout.Delegate
        public boolean isFragmentOpened() {
            return true;
        }

        @Override // org.telegram.ui.Components.SharedMediaLayout.Delegate
        public boolean onMemberClick(TLRPC.ChatParticipant chatParticipant, boolean z, boolean z2, View view) {
            return false;
        }

        @Override // org.telegram.ui.Components.SharedMediaLayout.Delegate
        public void scrollToSharedMedia() {
        }

        C43525() {
        }

        @Override // org.telegram.ui.Components.SharedMediaLayout.Delegate
        public void updateSelectedMediaTabText() {
            MediaActivity.this.updateMediaCount();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$6 */
    class C43536 extends SharedMediaLayout {
        private AnimatorSet actionModeAnimation;
        final /* synthetic */ FrameLayout val$avatarContainer;
        final /* synthetic */ SizeNotifierFrameLayout val$fragmentView;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C43536(Context context, long j, SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader, int i, ArrayList arrayList, TLRPC.ChatFull chatFull, TLRPC.UserFull userFull, int i2, int i3, BaseFragment baseFragment, SharedMediaLayout.Delegate delegate, int i4, Theme.ResourcesProvider resourcesProvider, FrameLayout frameLayout, SizeNotifierFrameLayout sizeNotifierFrameLayout) {
            super(context, j, sharedMediaPreloader, i, arrayList, chatFull, userFull, i2, i3, baseFragment, delegate, i4, resourcesProvider);
            frameLayout = frameLayout;
            sizeNotifierFrameLayout = sizeNotifierFrameLayout;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void onSelectedTabChanged() {
            super.onSelectedTabChanged();
            MediaActivity.this.updateMediaCount();
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        public String getStoriesHashtag() {
            return MediaActivity.this.hashtag;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        public String getStoriesHashtagUsername() {
            return MediaActivity.this.username;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected boolean canShowSearchItem() {
            return (MediaActivity.this.type == 1 || MediaActivity.this.type == 2) ? false : true;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void onSearchStateChanged(boolean z) {
            AndroidUtilities.removeAdjustResize(MediaActivity.this.getParentActivity(), ((BaseFragment) MediaActivity.this).classGuid);
            AndroidUtilities.updateViewVisibilityAnimated(frameLayout, !z, 0.95f, true);
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void drawBackgroundWithBlur(Canvas canvas, float f, Rect rect, Paint paint) {
            sizeNotifierFrameLayout.drawBlurRect(canvas, getY() + f, rect, paint, true);
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void invalidateBlur() {
            sizeNotifierFrameLayout.invalidateBlur();
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected boolean isStoriesView() {
            return MediaActivity.this.type == 1 || MediaActivity.this.type == 2;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected boolean customTabs() {
            return MediaActivity.this.type == 1 || MediaActivity.this.type == 2 || MediaActivity.this.type == 3;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected boolean includeStories() {
            return MediaActivity.this.type == 1 || MediaActivity.this.type == 2;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected boolean includeSavedDialogs() {
            return MediaActivity.this.type == 0 && MediaActivity.this.dialogId == MediaActivity.this.getUserConfig().getClientUserId() && MediaActivity.this.topicId == 0;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected boolean isArchivedOnlyStoriesView() {
            return MediaActivity.this.type == 2;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected int getInitialTab() {
            return MediaActivity.this.initialTab;
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void showActionMode(boolean z) {
            if (MediaActivity.this.type == 0) {
                super.showActionMode(z);
                return;
            }
            if (this.isActionModeShowed == z) {
                return;
            }
            this.isActionModeShowed = z;
            AnimatorSet animatorSet = this.actionModeAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            if (MediaActivity.this.type == 1 || MediaActivity.this.type == 2) {
                disableScroll(z);
            }
            if (z) {
                MediaActivity.this.selectedTextView.setVisibility(0);
                if (MediaActivity.this.buttonContainer != null) {
                    MediaActivity.this.buttonContainer.setVisibility(0);
                }
            } else {
                MediaActivity.this.titlesContainer.setVisibility(0);
            }
            float f = 0.0f;
            MediaActivity.this.backDrawable.setRotation(z ? 1.0f : 0.0f, true);
            this.actionModeAnimation = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            AnimatedTextView animatedTextView = MediaActivity.this.selectedTextView;
            float[] fArr = {z ? 1.0f : 0.0f};
            Property property = View.ALPHA;
            arrayList.add(ObjectAnimator.ofFloat(animatedTextView, (Property<AnimatedTextView, Float>) property, fArr));
            arrayList.add(ObjectAnimator.ofFloat(MediaActivity.this.titlesContainer, (Property<FrameLayout, Float>) property, z ? 0.0f : 1.0f));
            if (MediaActivity.this.buttonContainer != null) {
                arrayList.add(ObjectAnimator.ofFloat(MediaActivity.this.buttonContainer, (Property<FrameLayout, Float>) property, z ? 1.0f : 0.0f));
                arrayList.add(ObjectAnimator.ofFloat(MediaActivity.this.buttonContainer, (Property<FrameLayout, Float>) View.TRANSLATION_Y, z ? 0.0f : MediaActivity.this.buttonContainer.getMeasuredHeight()));
            }
            if (MediaActivity.this.deleteItem != null) {
                MediaActivity.this.deleteItem.setVisibility(0);
                arrayList.add(ObjectAnimator.ofFloat(MediaActivity.this.deleteItem, (Property<ActionBarMenuItem, Float>) property, z ? 1.0f : 0.0f));
            }
            boolean z2 = getStoriesCount(getClosestTab()) == 0;
            if (MediaActivity.this.optionsItem != null) {
                MediaActivity.this.optionsItem.setVisibility(0);
                ActionBarMenuItem actionBarMenuItem = MediaActivity.this.optionsItem;
                if (!z && !z2) {
                    f = 1.0f;
                }
                arrayList.add(ObjectAnimator.ofFloat(actionBarMenuItem, (Property<ActionBarMenuItem, Float>) property, f));
            }
            if (MediaActivity.this.tabsView != null) {
                arrayList.add(ObjectAnimator.ofFloat(MediaActivity.this.tabsView, (Property<StoriesTabsView, Float>) property, z ? 0.4f : 1.0f));
            }
            this.actionModeAnimation.playTogether(arrayList);
            this.actionModeAnimation.setDuration(300L);
            this.actionModeAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            this.actionModeAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.MediaActivity.6.1
                final /* synthetic */ boolean val$empty;
                final /* synthetic */ boolean val$show;

                AnonymousClass1(boolean z3, boolean z22) {
                    z = z3;
                    z = z22;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    C43536.this.actionModeAnimation = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (C43536.this.actionModeAnimation == null) {
                        return;
                    }
                    C43536.this.actionModeAnimation = null;
                    if (!z) {
                        MediaActivity.this.selectedTextView.setVisibility(4);
                        if (MediaActivity.this.buttonContainer != null) {
                            MediaActivity.this.buttonContainer.setVisibility(4);
                        }
                        if (MediaActivity.this.deleteItem != null) {
                            MediaActivity.this.deleteItem.setVisibility(8);
                        }
                        if (!z || MediaActivity.this.optionsItem == null) {
                            return;
                        }
                        MediaActivity.this.optionsItem.setVisibility(8);
                        return;
                    }
                    MediaActivity.this.titlesContainer.setVisibility(4);
                    if (MediaActivity.this.optionsItem != null) {
                        MediaActivity.this.optionsItem.setVisibility(8);
                    }
                }
            });
            this.actionModeAnimation.start();
        }

        /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$6$1 */
        class AnonymousClass1 extends AnimatorListenerAdapter {
            final /* synthetic */ boolean val$empty;
            final /* synthetic */ boolean val$show;

            AnonymousClass1(boolean z3, boolean z22) {
                z = z3;
                z = z22;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                C43536.this.actionModeAnimation = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (C43536.this.actionModeAnimation == null) {
                    return;
                }
                C43536.this.actionModeAnimation = null;
                if (!z) {
                    MediaActivity.this.selectedTextView.setVisibility(4);
                    if (MediaActivity.this.buttonContainer != null) {
                        MediaActivity.this.buttonContainer.setVisibility(4);
                    }
                    if (MediaActivity.this.deleteItem != null) {
                        MediaActivity.this.deleteItem.setVisibility(8);
                    }
                    if (!z || MediaActivity.this.optionsItem == null) {
                        return;
                    }
                    MediaActivity.this.optionsItem.setVisibility(8);
                    return;
                }
                MediaActivity.this.titlesContainer.setVisibility(4);
                if (MediaActivity.this.optionsItem != null) {
                    MediaActivity.this.optionsItem.setVisibility(8);
                }
            }
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void onActionModeSelectedUpdate(SparseArray sparseArray) {
            int size = sparseArray.size();
            MediaActivity.this.actionModeMessageObjects = sparseArray;
            if (MediaActivity.this.type == 1 || MediaActivity.this.type == 2) {
                MediaActivity.this.selectedTextView.cancelAnimation();
                MediaActivity.this.selectedTextView.setText(LocaleController.formatPluralString("StoriesSelected", size, new Object[0]), !LocaleController.isRTL);
                if (MediaActivity.this.button != null) {
                    MediaActivity.this.button.setEnabled(size > 0);
                    MediaActivity.this.button.setCount(size, true);
                    if (MediaActivity.this.sharedMediaLayout.getClosestTab() == 8) {
                        MediaActivity.this.button.setText(LocaleController.formatPluralString("ArchiveStories", size, new Object[0]), true);
                    }
                }
            }
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void onTabProgress(float f) {
            if (MediaActivity.this.type != 1) {
                return;
            }
            float f2 = f - 8.0f;
            if (MediaActivity.this.tabsView != null) {
                MediaActivity.this.tabsView.setProgress(f2);
            }
            float f3 = 1.0f - f2;
            MediaActivity.this.titles[0].setAlpha(f3);
            MediaActivity.this.titles[0].setTranslationX(AndroidUtilities.m1081dp(-12.0f) * f2);
            MediaActivity.this.titles[1].setAlpha(f2);
            MediaActivity.this.titles[1].setTranslationX(AndroidUtilities.m1081dp(12.0f) * f3);
        }

        @Override // org.telegram.p026ui.Components.SharedMediaLayout
        protected void onTabScroll(boolean z) {
            if (MediaActivity.this.tabsView != null) {
                MediaActivity.this.tabsView.setScrolling(z);
            }
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onBackPressed(boolean z) {
        if (hasShownSheet()) {
            if (z) {
                closeSheet();
            }
            return false;
        }
        if (!this.sharedMediaLayout.isActionModeShown()) {
            return super.onBackPressed(z);
        }
        if (z) {
            this.sharedMediaLayout.closeActionMode(false);
        }
        return false;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean isSwipeBackEnabled(MotionEvent motionEvent) {
        if (this.sharedMediaLayout.isSwipeBackEnabled()) {
            return this.sharedMediaLayout.isCurrentTabFirst();
        }
        return false;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean canBeginSlide() {
        if (this.sharedMediaLayout.isSwipeBackEnabled()) {
            return super.canBeginSlide();
        }
        return false;
    }

    public void updateMediaCount() {
        SharedMediaLayout sharedMediaLayout = this.sharedMediaLayout;
        if (sharedMediaLayout != null) {
            if (this.subtitleTextView[0] == null) {
                return;
            }
            int closestTab = sharedMediaLayout.getClosestTab();
            if (this.type != 3 || closestTab == 8) {
                int[] lastMediaCount = this.sharedMediaPreloader.getLastMediaCount();
                boolean z = LocaleController.isRTL;
                boolean z2 = !z;
                int i = (this.type == 1 && closestTab != 8) ? 1 : 0;
                if (closestTab == 8 || closestTab == 9) {
                    ActionBarMenuSubItem actionBarMenuSubItem = this.zoomOutItem;
                    if (actionBarMenuSubItem != null) {
                        actionBarMenuSubItem.setEnabled(this.sharedMediaLayout.canZoomOut());
                        ActionBarMenuSubItem actionBarMenuSubItem2 = this.zoomOutItem;
                        actionBarMenuSubItem2.setAlpha(actionBarMenuSubItem2.isEnabled() ? 1.0f : 0.5f);
                    }
                    ActionBarMenuSubItem actionBarMenuSubItem3 = this.zoomInItem;
                    if (actionBarMenuSubItem3 != null) {
                        actionBarMenuSubItem3.setEnabled(this.sharedMediaLayout.canZoomIn());
                        ActionBarMenuSubItem actionBarMenuSubItem4 = this.zoomInItem;
                        actionBarMenuSubItem4.setAlpha(actionBarMenuSubItem4.isEnabled() ? 1.0f : 0.5f);
                    }
                    int storiesCount = this.sharedMediaLayout.getStoriesCount(8);
                    if (storiesCount > 0) {
                        if (this.type == 3) {
                            if (TextUtils.isEmpty(this.subtitleTextView[0].getText())) {
                                showSubtitle(0, true, true);
                                this.subtitleTextView[0].setText(LocaleController.formatPluralStringSpaced("FoundStories", storiesCount), z2);
                            }
                        } else {
                            showSubtitle(0, true, true);
                            this.subtitleTextView[0].setText(LocaleController.formatPluralString("ProfileMyStoriesCount", storiesCount, new Object[0]), z2);
                        }
                    } else {
                        showSubtitle(0, false, true);
                    }
                    if (this.type == 1) {
                        int storiesCount2 = this.sharedMediaLayout.getStoriesCount(9);
                        if (storiesCount2 > 0) {
                            showSubtitle(1, true, true);
                            this.subtitleTextView[1].setText(LocaleController.formatPluralString("ProfileStoriesArchiveCount", storiesCount2, new Object[0]), z2);
                        } else {
                            showSubtitle(1, false, true);
                        }
                    }
                    if (this.optionsItem != null) {
                        SharedMediaLayout sharedMediaLayout2 = this.sharedMediaLayout;
                        final boolean z3 = sharedMediaLayout2.getStoriesCount(sharedMediaLayout2.getClosestTab()) <= 0;
                        if (!z3) {
                            this.optionsItem.setVisibility(0);
                        }
                        this.optionsItem.animate().alpha(z3 ? 0.0f : 1.0f).withEndAction(new Runnable() { // from class: org.telegram.ui.Components.MediaActivity$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$updateMediaCount$11(z3);
                            }
                        }).setDuration(220L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                    }
                    ButtonWithCounterView buttonWithCounterView = this.button;
                    if (buttonWithCounterView != null) {
                        boolean z4 = !z && this.lastTab == closestTab;
                        if (closestTab == 8) {
                            SparseArray sparseArray = this.actionModeMessageObjects;
                            buttonWithCounterView.setText(LocaleController.formatPluralString("ArchiveStories", sparseArray == null ? 0 : sparseArray.size(), new Object[0]), z4);
                        } else {
                            buttonWithCounterView.setText(LocaleController.getString(C2702R.string.SaveToProfile), z4);
                        }
                        this.lastTab = closestTab;
                    }
                    if (this.calendarItem != null) {
                        boolean z5 = this.sharedMediaLayout.getStoriesCount(closestTab) > 0;
                        this.calendarItem.setEnabled(z5);
                        this.calendarItem.setAlpha(z5 ? 1.0f : 0.5f);
                        return;
                    }
                    return;
                }
                if (closestTab == 11) {
                    showSubtitle(i, true, true);
                    this.subtitleTextView[i].setText(LocaleController.formatPluralString("SavedDialogsTabCount", getMessagesController().getSavedMessagesController().getAllCount(), new Object[0]), z2);
                    return;
                }
                if (closestTab >= 0) {
                    if (closestTab >= lastMediaCount.length || lastMediaCount[closestTab] >= 0) {
                        if (closestTab == 0) {
                            showSubtitle(i, true, true);
                            if (this.sharedMediaLayout.getPhotosVideosTypeFilter() == 1) {
                                this.subtitleTextView[i].setText(LocaleController.formatPluralString("Photos", lastMediaCount[6], new Object[0]), z2);
                                return;
                            } else if (this.sharedMediaLayout.getPhotosVideosTypeFilter() == 2) {
                                this.subtitleTextView[i].setText(LocaleController.formatPluralString("Videos", lastMediaCount[7], new Object[0]), z2);
                                return;
                            } else {
                                this.subtitleTextView[i].setText(LocaleController.formatPluralString("Media", lastMediaCount[0], new Object[0]), z2);
                                return;
                            }
                        }
                        if (closestTab == 1) {
                            showSubtitle(i, true, true);
                            this.subtitleTextView[i].setText(LocaleController.formatPluralString("Files", lastMediaCount[1], new Object[0]), z2);
                            return;
                        }
                        if (closestTab == 2) {
                            showSubtitle(i, true, true);
                            this.subtitleTextView[i].setText(LocaleController.formatPluralString("Voice", lastMediaCount[2], new Object[0]), z2);
                            return;
                        }
                        if (closestTab == 3) {
                            showSubtitle(i, true, true);
                            this.subtitleTextView[i].setText(LocaleController.formatPluralString("Links", lastMediaCount[3], new Object[0]), z2);
                            return;
                        }
                        if (closestTab == 4) {
                            showSubtitle(i, true, true);
                            this.subtitleTextView[i].setText(LocaleController.formatPluralString("MusicFiles", lastMediaCount[4], new Object[0]), z2);
                        } else if (closestTab == 5) {
                            showSubtitle(i, true, true);
                            this.subtitleTextView[i].setText(LocaleController.formatPluralString("GIFs", lastMediaCount[5], new Object[0]), z2);
                        } else if (closestTab == 10) {
                            showSubtitle(i, true, true);
                            MessagesController.ChannelRecommendations channelRecommendations = MessagesController.getInstance(this.currentAccount).getChannelRecommendations(-this.dialogId);
                            this.subtitleTextView[i].setText(LocaleController.formatPluralString("Channels", channelRecommendations == null ? 0 : channelRecommendations.more + channelRecommendations.chats.size(), new Object[0]), z2);
                        }
                    }
                }
            }
        }
    }

    public /* synthetic */ void lambda$updateMediaCount$11(boolean z) {
        if (z) {
            this.optionsItem.setVisibility(8);
        }
    }

    public void setChatInfo(TLRPC.ChatFull chatFull) {
        this.currentChatInfo = chatFull;
    }

    public long getDialogId() {
        return this.dialogId;
    }

    private void showSubtitle(final int i, boolean z, boolean z2) {
        int i2 = this.type;
        if (i2 == 3) {
            return;
        }
        if (i == 1 && i2 == 2) {
            return;
        }
        boolean[] zArr = this.subtitleShown;
        if (zArr[i] != z || this.firstSubtitleCheck[i]) {
            boolean[] zArr2 = this.firstSubtitleCheck;
            boolean z3 = !zArr2[i] && z2;
            zArr2[i] = false;
            zArr[i] = z;
            ValueAnimator valueAnimator = this.subtitleAnimator[i];
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.subtitleAnimator[i] = null;
            }
            if (z3) {
                this.subtitleTextView[i].setVisibility(0);
                this.subtitleAnimator[i] = ValueAnimator.ofFloat(this.subtitleT[i], z ? 1.0f : 0.0f);
                this.subtitleAnimator[i].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.MediaActivity$$ExternalSyntheticLambda13
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$showSubtitle$12(i, valueAnimator2);
                    }
                });
                this.subtitleAnimator[i].addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.MediaActivity.7
                    final /* synthetic */ int val$i;
                    final /* synthetic */ boolean val$show;

                    C43547(final int i3, boolean z4) {
                        i = i3;
                        z = z4;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        MediaActivity.this.subtitleT[i] = z ? 1.0f : 0.0f;
                        MediaActivity.this.nameTextView[i].setScaleX(z ? 1.0f : 1.111f);
                        MediaActivity.this.nameTextView[i].setScaleY(z ? 1.0f : 1.111f);
                        MediaActivity.this.nameTextView[i].setTranslationY(z ? 0.0f : AndroidUtilities.m1081dp(8.0f));
                        MediaActivity.this.subtitleTextView[i].setAlpha(z ? 1.0f : 0.0f);
                        if (z) {
                            return;
                        }
                        MediaActivity.this.subtitleTextView[i].setVisibility(8);
                    }
                });
                this.subtitleAnimator[i3].setDuration(320L);
                this.subtitleAnimator[i3].setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.subtitleAnimator[i3].start();
                return;
            }
            this.subtitleT[i3] = z4 ? 1.0f : 0.0f;
            this.nameTextView[i3].setScaleX(z4 ? 1.0f : 1.111f);
            this.nameTextView[i3].setScaleY(z4 ? 1.0f : 1.111f);
            this.nameTextView[i3].setTranslationY(z4 ? 0.0f : AndroidUtilities.m1081dp(8.0f));
            this.subtitleTextView[i3].setAlpha(z4 ? 1.0f : 0.0f);
            this.subtitleTextView[i3].setVisibility(z4 ? 0 : 8);
        }
    }

    public /* synthetic */ void lambda$showSubtitle$12(int i, ValueAnimator valueAnimator) {
        this.subtitleT[i] = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.nameTextView[i].setScaleX(AndroidUtilities.lerp(1.111f, 1.0f, this.subtitleT[i]));
        this.nameTextView[i].setScaleY(AndroidUtilities.lerp(1.111f, 1.0f, this.subtitleT[i]));
        this.nameTextView[i].setTranslationY(AndroidUtilities.lerp(AndroidUtilities.m1081dp(8.0f), 0, this.subtitleT[i]));
        this.subtitleTextView[i].setAlpha(this.subtitleT[i]);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MediaActivity$7 */
    class C43547 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$i;
        final /* synthetic */ boolean val$show;

        C43547(final int i3, boolean z4) {
            i = i3;
            z = z4;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            MediaActivity.this.subtitleT[i] = z ? 1.0f : 0.0f;
            MediaActivity.this.nameTextView[i].setScaleX(z ? 1.0f : 1.111f);
            MediaActivity.this.nameTextView[i].setScaleY(z ? 1.0f : 1.111f);
            MediaActivity.this.nameTextView[i].setTranslationY(z ? 0.0f : AndroidUtilities.m1081dp(8.0f));
            MediaActivity.this.subtitleTextView[i].setAlpha(z ? 1.0f : 0.0f);
            if (z) {
                return;
            }
            MediaActivity.this.subtitleTextView[i].setVisibility(8);
        }
    }

    @Override // org.telegram.ui.Components.SharedMediaLayout.SharedMediaPreloaderDelegate
    public void mediaCountUpdated() {
        SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader;
        SharedMediaLayout sharedMediaLayout = this.sharedMediaLayout;
        if (sharedMediaLayout != null && (sharedMediaPreloader = this.sharedMediaPreloader) != null) {
            sharedMediaLayout.setNewMediaCounts(sharedMediaPreloader.getLastMediaCount());
        }
        updateMediaCount();
    }

    public void updateColors() {
        if (this.sharedMediaLayout.getSearchOptionsItem() != null) {
            this.sharedMediaLayout.getSearchOptionsItem().setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_windowBackgroundWhiteBlackText), PorterDuff.Mode.MULTIPLY));
        }
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        ActionBar actionBar = this.actionBar;
        int i = Theme.key_windowBackgroundWhiteBlackText;
        actionBar.setItemsColor(Theme.getColor(i), false);
        this.actionBar.setItemsColor(Theme.getColor(i), true);
        this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultSelector), false);
        this.actionBar.setTitleColor(Theme.getColor(i));
        SimpleTextView simpleTextView = this.nameTextView[0];
        if (simpleTextView != null) {
            simpleTextView.setTextColor(Theme.getColor(i));
        }
        SimpleTextView simpleTextView2 = this.nameTextView[1];
        if (simpleTextView2 != null) {
            simpleTextView2.setTextColor(Theme.getColor(i));
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.Components.MediaActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.updateColors();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_actionBarActionModeDefaultSelector));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.addAll(this.sharedMediaLayout.getThemeDescriptions());
        return arrayList;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        if (getLastStoryViewer() != null && getLastStoryViewer().isShown()) {
            return false;
        }
        int color = Theme.getColor(Theme.key_windowBackgroundWhite);
        if (this.actionBar.isActionModeShowed()) {
            color = Theme.getColor(Theme.key_actionBarActionModeDefault);
        }
        return ColorUtils.calculateLuminance(color) > 0.699999988079071d;
    }

    @Override // org.telegram.p026ui.Components.FloatingDebug.FloatingDebugProvider
    public List onGetDebugItems() {
        StringBuilder sb = new StringBuilder();
        sb.append(ShapeDetector.isLearning(getContext()) ? "Disable" : "Enable");
        sb.append(" shape detector learning debug");
        return Arrays.asList(new FloatingDebugController.DebugItem(sb.toString(), new Runnable() { // from class: org.telegram.ui.Components.MediaActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onGetDebugItems$13();
            }
        }));
    }

    public /* synthetic */ void lambda$onGetDebugItems$13() {
        ShapeDetector.setLearning(getContext(), !ShapeDetector.isLearning(getContext()));
    }

    private class StoriesTabsView extends BottomPagerTabs {
        public StoriesTabsView(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }

        @Override // org.telegram.p026ui.Components.BottomPagerTabs
        public BottomPagerTabs.Tab[] createTabs() {
            return new BottomPagerTabs.Tab[]{new BottomPagerTabs.Tab(0, C2702R.raw.msg_stories_saved, 20, 40, LocaleController.getString(C2702R.string.ProfileMyStoriesTab)), new BottomPagerTabs.Tab(1, C2702R.raw.msg_stories_archive, 0, 0, LocaleController.getString(C2702R.string.ProfileStoriesArchiveTab))};
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public int getNavigationBarColor() {
        int themedColor = getThemedColor(Theme.key_windowBackgroundWhite);
        return (getLastStoryViewer() == null || !getLastStoryViewer().attachedToParent()) ? themedColor : getLastStoryViewer().getNavigationBarColor(themedColor);
    }
}
