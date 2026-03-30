package org.telegram.p026ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.p026ui.ActionBar.ActionBar;
import org.telegram.p026ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.ActionBar.ThemeDescription;
import org.telegram.p026ui.Cells.GroupCreateUserCell;
import org.telegram.p026ui.Cells.HeaderCell;
import org.telegram.p026ui.Cells.ShadowSectionCell;
import org.telegram.p026ui.Cells.TextCell;
import org.telegram.p026ui.Cells.TextInfoPrivacyCell;
import org.telegram.p026ui.Cells.TextSettingsCell;
import org.telegram.p026ui.Components.AutoDeletePopupWrapper;
import org.telegram.p026ui.Components.AvatarDrawable;
import org.telegram.p026ui.Components.BackupImageView;
import org.telegram.p026ui.Components.EditTextEmoji;
import org.telegram.p026ui.Components.FillLastLinearLayoutManager;
import org.telegram.p026ui.Components.FragmentFloatingButton;
import org.telegram.p026ui.Components.ImageUpdater;
import org.telegram.p026ui.Components.LayoutHelper;
import org.telegram.p026ui.Components.ListView.AdapterWithDiffUtils;
import org.telegram.p026ui.Components.RLottieDrawable;
import org.telegram.p026ui.Components.RLottieImageView;
import org.telegram.p026ui.Components.RadialProgressView;
import org.telegram.p026ui.Components.RecyclerListView;
import org.telegram.p026ui.Components.SizeNotifierFrameLayout;
import org.telegram.p026ui.Components.VerticalPositionAutoAnimator;
import org.telegram.p026ui.LocationActivity;
import org.telegram.p026ui.PhotoViewer;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes6.dex */
public class GroupCreateFinalActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate, ImageUpdater.ImageUpdaterDelegate {
    private GroupCreateAdapter adapter;
    private TLRPC.FileLocation avatar;
    private AnimatorSet avatarAnimation;
    private TLRPC.FileLocation avatarBig;
    private AvatarDrawable avatarDrawable;
    private RLottieImageView avatarEditor;
    private BackupImageView avatarImage;
    private View avatarOverlay;
    private RadialProgressView avatarProgressView;
    private RLottieDrawable cameraDrawable;
    private boolean canToggleTopics;
    private int chatType;
    private boolean createAfterUpload;
    private String currentGroupCreateAddress;
    private Location currentGroupCreateLocation;
    private GroupCreateFinalActivityDelegate delegate;
    private boolean donePressed;
    private EditTextEmoji editText;
    private FrameLayout editTextContainer;
    private FragmentFloatingButton floatingButton;
    private boolean forImport;
    private ImageUpdater imageUpdater;
    private TLRPC.VideoSize inputEmojiMarkup;
    private TLRPC.InputFile inputPhoto;
    private TLRPC.InputFile inputVideo;
    private String inputVideoPath;
    private FillLastLinearLayoutManager linearLayoutManager;
    private RecyclerListView listView;
    private String nameToSet;
    ActionBarPopupWindow popupWindow;
    private int reqId;
    private ArrayList selectedContacts;
    private Drawable shadowDrawable;
    private int ttlPeriod;
    private double videoTimestamp;

    public interface GroupCreateFinalActivityDelegate {
        void didFailChatCreation();

        void didFinishChatCreation(GroupCreateFinalActivity groupCreateFinalActivity, long j);

        void didStartChatCreation();
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public /* synthetic */ boolean canFinishFragment() {
        return ImageUpdater.ImageUpdaterDelegate.CC.$default$canFinishFragment(this);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public /* synthetic */ void didUploadFailed() {
        ImageUpdater.ImageUpdaterDelegate.CC.$default$didUploadFailed(this);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public /* synthetic */ PhotoViewer.PlaceProviderObject getCloseIntoObject() {
        return ImageUpdater.ImageUpdaterDelegate.CC.$default$getCloseIntoObject(this);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    protected boolean hideKeyboardOnShow() {
        return false;
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public /* synthetic */ boolean supportsBulletin() {
        return ImageUpdater.ImageUpdaterDelegate.CC.$default$supportsBulletin(this);
    }

    public GroupCreateFinalActivity(Bundle bundle) {
        super(bundle);
        this.chatType = bundle.getInt("chatType", 0);
        this.avatarDrawable = new AvatarDrawable();
        this.currentGroupCreateAddress = bundle.getString("address");
        this.currentGroupCreateLocation = (Location) bundle.getParcelable("location");
        this.forImport = bundle.getBoolean("forImport", false);
        this.nameToSet = bundle.getString("title", null);
        this.canToggleTopics = bundle.getBoolean("canToggleTopics", true);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatDidFailCreate);
        ImageUpdater imageUpdater = new ImageUpdater(true, 2, true);
        this.imageUpdater = imageUpdater;
        imageUpdater.parentFragment = this;
        imageUpdater.setDelegate(this);
        long[] longArray = getArguments().getLongArray("result");
        int i = 0;
        if (longArray != null) {
            this.selectedContacts = new ArrayList(longArray.length);
            for (long j : longArray) {
                this.selectedContacts.add(Long.valueOf(j));
            }
        }
        final ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.selectedContacts.size(); i2++) {
            Long l = (Long) this.selectedContacts.get(i2);
            if (getMessagesController().getUser(l) == null) {
                arrayList.add(l);
            }
        }
        if (!arrayList.isEmpty()) {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final ArrayList arrayList2 = new ArrayList();
            MessagesStorage.getInstance(this.currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onFragmentCreate$0(arrayList2, arrayList, countDownLatch);
                }
            });
            try {
                countDownLatch.await();
            } catch (Exception e) {
                FileLog.m1093e(e);
            }
            if (arrayList.size() != arrayList2.size() || arrayList2.isEmpty()) {
                return false;
            }
            int size = arrayList2.size();
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                getMessagesController().putUser((TLRPC.User) obj, true);
            }
        }
        this.ttlPeriod = getUserConfig().getGlobalTTl() * 60;
        return super.onFragmentCreate();
    }

    public /* synthetic */ void lambda$onFragmentCreate$0(ArrayList arrayList, ArrayList arrayList2, CountDownLatch countDownLatch) {
        arrayList.addAll(MessagesStorage.getInstance(this.currentAccount).getUsers(arrayList2));
        countDownLatch.countDown();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatDidFailCreate);
        this.imageUpdater.clear();
        if (this.reqId != 0) {
            ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.reqId, true);
        }
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onDestroy();
        }
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onResume();
        }
        GroupCreateAdapter groupCreateAdapter = this.adapter;
        if (groupCreateAdapter != null) {
            groupCreateAdapter.notifyDataSetChanged();
        }
        this.imageUpdater.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onPause();
        }
        this.imageUpdater.onPause();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void dismissCurrentDialog() {
        if (this.imageUpdater.dismissCurrentDialog(this.visibleDialog)) {
            return;
        }
        super.dismissCurrentDialog();
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean dismissDialogOnPause(Dialog dialog) {
        return this.imageUpdater.dismissDialogOnPause(dialog) && super.dismissDialogOnPause(dialog);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        this.imageUpdater.onRequestPermissionsResultFragment(i, strArr, iArr);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public boolean onBackPressed(boolean z) {
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji == null || !editTextEmoji.isPopupShowing()) {
            return super.onBackPressed(z);
        }
        if (!z) {
            return false;
        }
        this.editText.hidePopup(true);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setDefaultGroupName() {
        /*
            r14 = this;
            org.telegram.messenger.UserConfig r0 = r14.getUserConfig()
            org.telegram.tgnet.TLRPC$User r0 = r0.getCurrentUser()
            java.util.ArrayList r1 = r14.selectedContacts
            int r1 = r1.size()
            r2 = 1
            int r1 = r1 + r2
            r3 = 2
            if (r1 < r3) goto Lc6
            r4 = 5
            if (r1 > r4) goto Lc6
            org.telegram.ui.Components.EditTextEmoji r5 = r14.editText
            android.text.Editable r5 = r5.getText()
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto Lc6
            r5 = 0
            java.lang.String r6 = ""
            if (r1 == r3) goto L96
            r7 = 3
            if (r1 == r7) goto L7b
            r8 = 4
            if (r1 == r8) goto L5a
            if (r1 == r4) goto L31
            goto Lae
        L31:
            java.lang.String r1 = "GroupCreateMembersFive"
            int r9 = org.telegram.messenger.C2702R.string.GroupCreateMembersFive     // Catch: java.lang.Exception -> L58
            java.lang.String r0 = r0.first_name     // Catch: java.lang.Exception -> L58
            java.lang.String r10 = r14.getFirstNameByPos(r5)     // Catch: java.lang.Exception -> L58
            java.lang.String r11 = r14.getFirstNameByPos(r2)     // Catch: java.lang.Exception -> L58
            java.lang.String r12 = r14.getFirstNameByPos(r3)     // Catch: java.lang.Exception -> L58
            java.lang.String r13 = r14.getFirstNameByPos(r7)     // Catch: java.lang.Exception -> L58
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Exception -> L58
            r4[r5] = r0     // Catch: java.lang.Exception -> L58
            r4[r2] = r10     // Catch: java.lang.Exception -> L58
            r4[r3] = r11     // Catch: java.lang.Exception -> L58
            r4[r7] = r12     // Catch: java.lang.Exception -> L58
            r4[r8] = r13     // Catch: java.lang.Exception -> L58
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r9, r4)     // Catch: java.lang.Exception -> L58
            goto Lae
        L58:
            r0 = move-exception
            goto Lab
        L5a:
            java.lang.String r1 = "GroupCreateMembersFour"
            int r4 = org.telegram.messenger.C2702R.string.GroupCreateMembersFour     // Catch: java.lang.Exception -> L58
            java.lang.String r0 = r0.first_name     // Catch: java.lang.Exception -> L58
            java.lang.String r9 = r14.getFirstNameByPos(r5)     // Catch: java.lang.Exception -> L58
            java.lang.String r10 = r14.getFirstNameByPos(r2)     // Catch: java.lang.Exception -> L58
            java.lang.String r11 = r14.getFirstNameByPos(r3)     // Catch: java.lang.Exception -> L58
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch: java.lang.Exception -> L58
            r8[r5] = r0     // Catch: java.lang.Exception -> L58
            r8[r2] = r9     // Catch: java.lang.Exception -> L58
            r8[r3] = r10     // Catch: java.lang.Exception -> L58
            r8[r7] = r11     // Catch: java.lang.Exception -> L58
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r4, r8)     // Catch: java.lang.Exception -> L58
            goto Lae
        L7b:
            java.lang.String r1 = "GroupCreateMembersThree"
            int r4 = org.telegram.messenger.C2702R.string.GroupCreateMembersThree     // Catch: java.lang.Exception -> L58
            java.lang.String r0 = r0.first_name     // Catch: java.lang.Exception -> L58
            java.lang.String r8 = r14.getFirstNameByPos(r5)     // Catch: java.lang.Exception -> L58
            java.lang.String r9 = r14.getFirstNameByPos(r2)     // Catch: java.lang.Exception -> L58
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch: java.lang.Exception -> L58
            r7[r5] = r0     // Catch: java.lang.Exception -> L58
            r7[r2] = r8     // Catch: java.lang.Exception -> L58
            r7[r3] = r9     // Catch: java.lang.Exception -> L58
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r4, r7)     // Catch: java.lang.Exception -> L58
            goto Lae
        L96:
            java.lang.String r1 = "GroupCreateMembersTwo"
            int r4 = org.telegram.messenger.C2702R.string.GroupCreateMembersTwo     // Catch: java.lang.Exception -> L58
            java.lang.String r0 = r0.first_name     // Catch: java.lang.Exception -> L58
            java.lang.String r7 = r14.getFirstNameByPos(r5)     // Catch: java.lang.Exception -> L58
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Exception -> L58
            r3[r5] = r0     // Catch: java.lang.Exception -> L58
            r3[r2] = r7     // Catch: java.lang.Exception -> L58
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r4, r3)     // Catch: java.lang.Exception -> L58
            goto Lae
        Lab:
            org.telegram.messenger.FileLog.m1093e(r0)
        Lae:
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto Lc6
            org.telegram.ui.Components.EditTextEmoji r0 = r14.editText
            r0.setText(r6)
            org.telegram.ui.Components.EditTextEmoji r0 = r14.editText
            android.text.Editable r1 = r0.getText()
            int r1 = r1.length()
            r0.setSelection(r5, r1)
        Lc6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCreateFinalActivity.setDefaultGroupName():void");
    }

    private String getFirstNameByPos(int i) {
        return getMessagesController().getUser((Long) this.selectedContacts.get(i)).first_name;
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public View createView(Context context) {
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            editTextEmoji.onDestroy();
        }
        this.actionBar.setBackButtonImage(C2702R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString(C2702R.string.NewGroup));
        ActionBar actionBar = this.actionBar;
        int i = Theme.key_windowBackgroundGray;
        actionBar.setBackgroundColor(getThemedColor(i));
        this.actionBar.setCastShadows(false);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.GroupCreateFinalActivity.1
            C56521() {
            }

            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i2) {
                if (i2 == -1) {
                    GroupCreateFinalActivity.this.finishFragment();
                }
            }
        });
        C56542 c56542 = new SizeNotifierFrameLayout(context) { // from class: org.telegram.ui.GroupCreateFinalActivity.2
            private boolean ignoreLayout;

            C56542(Context context2) {
                super(context2);
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i2, int i3) {
                int size = View.MeasureSpec.getSize(i2);
                int size2 = View.MeasureSpec.getSize(i3);
                setMeasuredDimension(size, size2);
                int paddingTop = size2 - getPaddingTop();
                measureChildWithMargins(((BaseFragment) GroupCreateFinalActivity.this).actionBar, i2, 0, i3, 0);
                if (measureKeyboardHeight() > AndroidUtilities.m1081dp(20.0f) && !GroupCreateFinalActivity.this.editText.isPopupShowing()) {
                    this.ignoreLayout = true;
                    GroupCreateFinalActivity.this.editText.hideEmojiView();
                    this.ignoreLayout = false;
                }
                int childCount = getChildCount();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = getChildAt(i4);
                    if (childAt != null && childAt.getVisibility() != 8 && childAt != ((BaseFragment) GroupCreateFinalActivity.this).actionBar) {
                        if (GroupCreateFinalActivity.this.editText != null && GroupCreateFinalActivity.this.editText.isPopupView(childAt)) {
                            if (AndroidUtilities.isInMultiwindow || AndroidUtilities.isTablet()) {
                                if (AndroidUtilities.isTablet()) {
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(AndroidUtilities.isTablet() ? 200.0f : 320.0f), (paddingTop - AndroidUtilities.statusBarHeight) + getPaddingTop()), TLObject.FLAG_30));
                                } else {
                                    childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec((paddingTop - AndroidUtilities.statusBarHeight) + getPaddingTop(), TLObject.FLAG_30));
                                }
                            } else {
                                childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, TLObject.FLAG_30));
                            }
                        } else {
                            measureChildWithMargins(childAt, i2, 0, i3, 0);
                        }
                    }
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:81:0x0071  */
            /* JADX WARN: Removed duplicated region for block: B:89:0x008d  */
            /* JADX WARN: Removed duplicated region for block: B:92:0x00a1  */
            /* JADX WARN: Removed duplicated region for block: B:96:0x00b3  */
            /* JADX WARN: Removed duplicated region for block: B:98:0x00bd  */
            @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected void onLayout(boolean r11, int r12, int r13, int r14, int r15) {
                /*
                    Method dump skipped, instruction units count: 212
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCreateFinalActivity.C56542.onLayout(boolean, int, int, int, int):void");
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        c56542.setBackgroundColor(getThemedColor(i));
        this.fragmentView = c56542;
        c56542.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.fragmentView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return GroupCreateFinalActivity.$r8$lambda$9jDkFcdWVIDNQSQrylARqKCyd5o(view, motionEvent);
            }
        });
        this.shadowDrawable = context2.getResources().getDrawable(C2702R.drawable.greydivider_top).mutate();
        C56553 c56553 = new LinearLayout(context2) { // from class: org.telegram.ui.GroupCreateFinalActivity.3
            C56553(Context context2) {
                super(context2);
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                boolean zDrawChild = super.drawChild(canvas, view, j);
                if (view == GroupCreateFinalActivity.this.listView && GroupCreateFinalActivity.this.shadowDrawable != null) {
                    int measuredHeight = GroupCreateFinalActivity.this.editTextContainer.getMeasuredHeight();
                    GroupCreateFinalActivity.this.shadowDrawable.setBounds(0, measuredHeight, getMeasuredWidth(), GroupCreateFinalActivity.this.shadowDrawable.getIntrinsicHeight() + measuredHeight);
                    GroupCreateFinalActivity.this.shadowDrawable.draw(canvas);
                }
                return zDrawChild;
            }
        };
        c56553.setOrientation(1);
        c56542.addView(c56553, LayoutHelper.createFrame(-1, -1.0f));
        FrameLayout frameLayout = new FrameLayout(context2);
        this.editTextContainer = frameLayout;
        frameLayout.setBackground(Theme.createRoundRectDrawableShadowed(AndroidUtilities.m1081dp(16.0f), getThemedColor(Theme.key_windowBackgroundWhite)));
        c56553.addView(this.editTextContainer, LayoutHelper.createLinear(-1, -2, 9.0f, 0.0f, 9.0f, 0.0f));
        C56564 c56564 = new BackupImageView(context2) { // from class: org.telegram.ui.GroupCreateFinalActivity.4
            C56564(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void invalidate() {
                if (GroupCreateFinalActivity.this.avatarOverlay != null) {
                    GroupCreateFinalActivity.this.avatarOverlay.invalidate();
                }
                super.invalidate();
            }

            @Override // android.view.View
            public void invalidate(int i2, int i3, int i4, int i5) {
                if (GroupCreateFinalActivity.this.avatarOverlay != null) {
                    GroupCreateFinalActivity.this.avatarOverlay.invalidate();
                }
                super.invalidate(i2, i3, i4, i5);
            }
        };
        this.avatarImage = c56564;
        c56564.setRoundRadius(ExteraConfig.getAvatarCorners(56.0f, false, this.chatType == 5));
        this.avatarDrawable.setInfo(5L, null, null);
        this.avatarImage.setImageDrawable(this.avatarDrawable);
        this.avatarImage.setContentDescription(LocaleController.getString(C2702R.string.ChoosePhoto));
        FrameLayout frameLayout2 = this.editTextContainer;
        BackupImageView backupImageView = this.avatarImage;
        boolean z = LocaleController.isRTL;
        frameLayout2.addView(backupImageView, LayoutHelper.createFrame(56, 56.0f, (z ? 5 : 3) | 48, z ? 0.0f : 24.0f, 20.0f, z ? 24.0f : 0.0f, 12.0f));
        Paint paint = new Paint(1);
        paint.setColor(1426063360);
        C56575 c56575 = new View(context2) { // from class: org.telegram.ui.GroupCreateFinalActivity.5
            final /* synthetic */ Paint val$paint;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C56575(Context context2, Paint paint2) {
                super(context2);
                paint = paint2;
            }

            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                if (GroupCreateFinalActivity.this.avatarImage != null && GroupCreateFinalActivity.this.avatarProgressView.getVisibility() == 0 && GroupCreateFinalActivity.this.avatarImage.getImageReceiver().hasNotThumb()) {
                    paint.setAlpha((int) (GroupCreateFinalActivity.this.avatarImage.getImageReceiver().getCurrentAlpha() * 85.0f * GroupCreateFinalActivity.this.avatarProgressView.getAlpha()));
                    canvas.drawRoundRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), ExteraConfig.getAvatarCorners(getMeasuredWidth(), true), ExteraConfig.getAvatarCorners(getMeasuredWidth(), true), paint);
                }
            }
        };
        this.avatarOverlay = c56575;
        FrameLayout frameLayout3 = this.editTextContainer;
        boolean z2 = LocaleController.isRTL;
        frameLayout3.addView(c56575, LayoutHelper.createFrame(56, 56.0f, (z2 ? 5 : 3) | 48, z2 ? 0.0f : 24.0f, 20.0f, z2 ? 24.0f : 0.0f, 12.0f));
        this.avatarOverlay.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$4(view);
            }
        });
        this.cameraDrawable = new RLottieDrawable(C2702R.raw.camera, _UrlKt.FRAGMENT_ENCODE_SET + C2702R.raw.camera, AndroidUtilities.m1081dp(52.0f), AndroidUtilities.m1081dp(52.0f), false, null);
        C56586 c56586 = new RLottieImageView(context2) { // from class: org.telegram.ui.GroupCreateFinalActivity.6
            C56586(Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void invalidate(int i2, int i3, int i4, int i5) {
                super.invalidate(i2, i3, i4, i5);
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }

            @Override // android.view.View
            public void invalidate() {
                super.invalidate();
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }
        };
        this.avatarEditor = c56586;
        c56586.setScaleType(ImageView.ScaleType.CENTER);
        this.avatarEditor.setAnimation(this.cameraDrawable);
        this.avatarEditor.setEnabled(false);
        this.avatarEditor.setClickable(false);
        this.avatarEditor.setPadding(AndroidUtilities.m1081dp(0.0f), 0, 0, AndroidUtilities.m1081dp(1.0f));
        FrameLayout frameLayout4 = this.editTextContainer;
        RLottieImageView rLottieImageView = this.avatarEditor;
        boolean z3 = LocaleController.isRTL;
        frameLayout4.addView(rLottieImageView, LayoutHelper.createFrame(56, 56.0f, (z3 ? 5 : 3) | 48, z3 ? 0.0f : 24.0f, 20.0f, z3 ? 24.0f : 0.0f, 12.0f));
        C56597 c56597 = new RadialProgressView(context2) { // from class: org.telegram.ui.GroupCreateFinalActivity.7
            C56597(Context context2) {
                super(context2);
            }

            @Override // org.telegram.p026ui.Components.RadialProgressView, android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }
        };
        this.avatarProgressView = c56597;
        c56597.setSize(AndroidUtilities.m1081dp(30.0f));
        this.avatarProgressView.setProgressColor(-1);
        this.avatarProgressView.setNoProgress(false);
        FrameLayout frameLayout5 = this.editTextContainer;
        RadialProgressView radialProgressView = this.avatarProgressView;
        boolean z4 = LocaleController.isRTL;
        frameLayout5.addView(radialProgressView, LayoutHelper.createFrame(56, 56.0f, (z4 ? 5 : 3) | 48, z4 ? 0.0f : 24.0f, 20.0f, z4 ? 24.0f : 0.0f, 12.0f));
        showAvatarProgress(false, false);
        EditTextEmoji editTextEmoji2 = new EditTextEmoji(context2, c56542, this, 0, false);
        this.editText = editTextEmoji2;
        int i2 = this.chatType;
        editTextEmoji2.setHint(LocaleController.getString((i2 == 0 || i2 == 4 || i2 == 5) ? C2702R.string.EnterGroupNamePlaceholder : C2702R.string.EnterListName));
        String str = this.nameToSet;
        if (str != null) {
            this.editText.setText(str);
            EditTextEmoji editTextEmoji3 = this.editText;
            editTextEmoji3.setSelection(editTextEmoji3.getText().length());
            this.nameToSet = null;
        }
        setDefaultGroupName();
        this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        this.editText.getEditText().setSingleLine(true);
        FrameLayout frameLayout6 = this.editTextContainer;
        EditTextEmoji editTextEmoji4 = this.editText;
        boolean z5 = LocaleController.isRTL;
        frameLayout6.addView(editTextEmoji4, LayoutHelper.createFrame(-1, -2.0f, 16, z5 ? 24.0f : 96.0f, 12.0f, z5 ? 96.0f : 24.0f, 12.0f));
        RecyclerListView recyclerListView = new RecyclerListView(context2);
        this.listView = recyclerListView;
        recyclerListView.setSections();
        this.linearLayoutManager = new FillLastLinearLayoutManager(context2, 1, this.listView);
        RecyclerListView recyclerListView2 = this.listView;
        GroupCreateAdapter groupCreateAdapter = new GroupCreateAdapter(context2);
        this.adapter = groupCreateAdapter;
        recyclerListView2.setAdapter(groupCreateAdapter);
        this.listView.setLayoutManager(this.linearLayoutManager);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        c56553.addView(this.listView, LayoutHelper.createLinear(-1, -1));
        this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.GroupCreateFinalActivity.8
            C56608() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i3) {
                if (i3 == 1) {
                    AndroidUtilities.hideKeyboard(GroupCreateFinalActivity.this.editText);
                }
            }
        });
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view, int i3) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i3);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view, int i3, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i3, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i3, float f, float f2) {
                this.f$0.lambda$createView$6(view, i3, f, f2);
            }
        });
        FragmentFloatingButton fragmentFloatingButton = new FragmentFloatingButton(context2, this.resourceProvider);
        this.floatingButton = fragmentFloatingButton;
        VerticalPositionAutoAnimator.attach(fragmentFloatingButton);
        c56542.addView(this.floatingButton, FragmentFloatingButton.createDefaultLayoutParams());
        this.floatingButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$7(view);
            }
        });
        this.floatingButton.setContentDescription(LocaleController.getString(C2702R.string.Done));
        this.floatingButton.imageView.setImageResource(C2702R.drawable.checkbig);
        return this.fragmentView;
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$1 */
    class C56521 extends ActionBar.ActionBarMenuOnItemClick {
        C56521() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i2) {
            if (i2 == -1) {
                GroupCreateFinalActivity.this.finishFragment();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$2 */
    class C56542 extends SizeNotifierFrameLayout {
        private boolean ignoreLayout;

        C56542(Context context2) {
            super(context2);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i2, int i3) {
            int size = View.MeasureSpec.getSize(i2);
            int size2 = View.MeasureSpec.getSize(i3);
            setMeasuredDimension(size, size2);
            int paddingTop = size2 - getPaddingTop();
            measureChildWithMargins(((BaseFragment) GroupCreateFinalActivity.this).actionBar, i2, 0, i3, 0);
            if (measureKeyboardHeight() > AndroidUtilities.m1081dp(20.0f) && !GroupCreateFinalActivity.this.editText.isPopupShowing()) {
                this.ignoreLayout = true;
                GroupCreateFinalActivity.this.editText.hideEmojiView();
                this.ignoreLayout = false;
            }
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (childAt != null && childAt.getVisibility() != 8 && childAt != ((BaseFragment) GroupCreateFinalActivity.this).actionBar) {
                    if (GroupCreateFinalActivity.this.editText != null && GroupCreateFinalActivity.this.editText.isPopupView(childAt)) {
                        if (AndroidUtilities.isInMultiwindow || AndroidUtilities.isTablet()) {
                            if (AndroidUtilities.isTablet()) {
                                childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.m1081dp(AndroidUtilities.isTablet() ? 200.0f : 320.0f), (paddingTop - AndroidUtilities.statusBarHeight) + getPaddingTop()), TLObject.FLAG_30));
                            } else {
                                childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec((paddingTop - AndroidUtilities.statusBarHeight) + getPaddingTop(), TLObject.FLAG_30));
                            }
                        } else {
                            childAt.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, TLObject.FLAG_30));
                        }
                    } else {
                        measureChildWithMargins(childAt, i2, 0, i3, 0);
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:81:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x008d  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:96:0x00b3  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x00bd  */
        @Override // org.telegram.p026ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected void onLayout(boolean r11, int r12, int r13, int r14, int r15) {
            /*
                Method dump skipped, instruction units count: 212
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.GroupCreateFinalActivity.C56542.onLayout(boolean, int, int, int, int):void");
        }

        @Override // android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$9jDkFcdWVIDNQSQrylARqKCyd5o(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$3 */
    class C56553 extends LinearLayout {
        C56553(Context context2) {
            super(context2);
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            boolean zDrawChild = super.drawChild(canvas, view, j);
            if (view == GroupCreateFinalActivity.this.listView && GroupCreateFinalActivity.this.shadowDrawable != null) {
                int measuredHeight = GroupCreateFinalActivity.this.editTextContainer.getMeasuredHeight();
                GroupCreateFinalActivity.this.shadowDrawable.setBounds(0, measuredHeight, getMeasuredWidth(), GroupCreateFinalActivity.this.shadowDrawable.getIntrinsicHeight() + measuredHeight);
                GroupCreateFinalActivity.this.shadowDrawable.draw(canvas);
            }
            return zDrawChild;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$4 */
    class C56564 extends BackupImageView {
        C56564(Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public void invalidate() {
            if (GroupCreateFinalActivity.this.avatarOverlay != null) {
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }
            super.invalidate();
        }

        @Override // android.view.View
        public void invalidate(int i2, int i3, int i4, int i5) {
            if (GroupCreateFinalActivity.this.avatarOverlay != null) {
                GroupCreateFinalActivity.this.avatarOverlay.invalidate();
            }
            super.invalidate(i2, i3, i4, i5);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$5 */
    class C56575 extends View {
        final /* synthetic */ Paint val$paint;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C56575(Context context2, Paint paint2) {
            super(context2);
            paint = paint2;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            if (GroupCreateFinalActivity.this.avatarImage != null && GroupCreateFinalActivity.this.avatarProgressView.getVisibility() == 0 && GroupCreateFinalActivity.this.avatarImage.getImageReceiver().hasNotThumb()) {
                paint.setAlpha((int) (GroupCreateFinalActivity.this.avatarImage.getImageReceiver().getCurrentAlpha() * 85.0f * GroupCreateFinalActivity.this.avatarProgressView.getAlpha()));
                canvas.drawRoundRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), ExteraConfig.getAvatarCorners(getMeasuredWidth(), true), ExteraConfig.getAvatarCorners(getMeasuredWidth(), true), paint);
            }
        }
    }

    public /* synthetic */ void lambda$createView$4(View view) {
        this.imageUpdater.openMenu(this.avatar != null, new Runnable() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$2();
            }
        }, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$createView$3(dialogInterface);
            }
        }, 0);
        this.cameraDrawable.setCurrentFrame(0);
        this.cameraDrawable.setCustomEndFrame(43);
        this.avatarEditor.playAnimation();
    }

    public /* synthetic */ void lambda$createView$2() {
        this.avatar = null;
        this.avatarBig = null;
        this.inputPhoto = null;
        this.inputVideo = null;
        this.inputVideoPath = null;
        this.inputEmojiMarkup = null;
        this.videoTimestamp = 0.0d;
        showAvatarProgress(false, true);
        this.avatarImage.setImage((ImageLocation) null, (String) null, this.avatarDrawable, (Object) null);
        this.avatarEditor.setAnimation(this.cameraDrawable);
        this.cameraDrawable.setCurrentFrame(0);
    }

    public /* synthetic */ void lambda$createView$3(DialogInterface dialogInterface) {
        if (!this.imageUpdater.isUploadingImage()) {
            this.cameraDrawable.setCustomEndFrame(86);
            this.avatarEditor.playAnimation();
        } else {
            this.cameraDrawable.setCurrentFrame(0, false);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$6 */
    class C56586 extends RLottieImageView {
        C56586(Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public void invalidate(int i2, int i3, int i4, int i5) {
            super.invalidate(i2, i3, i4, i5);
            GroupCreateFinalActivity.this.avatarOverlay.invalidate();
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            GroupCreateFinalActivity.this.avatarOverlay.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$7 */
    class C56597 extends RadialProgressView {
        C56597(Context context2) {
            super(context2);
        }

        @Override // org.telegram.p026ui.Components.RadialProgressView, android.view.View
        public void setAlpha(float f) {
            super.setAlpha(f);
            GroupCreateFinalActivity.this.avatarOverlay.invalidate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$8 */
    class C56608 extends RecyclerView.OnScrollListener {
        C56608() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i3) {
            if (i3 == 1) {
                AndroidUtilities.hideKeyboard(GroupCreateFinalActivity.this.editText);
            }
        }
    }

    public /* synthetic */ void lambda$createView$6(View view, int i, float f, float f2) {
        if (view instanceof TextSettingsCell) {
            if (!AndroidUtilities.isMapsInstalled(this)) {
                return;
            }
            LocationActivity locationActivity = new LocationActivity(4);
            locationActivity.setDialogId(0L);
            locationActivity.setDelegate(new LocationActivity.LocationActivityDelegate() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda9
                @Override // org.telegram.ui.LocationActivity.LocationActivityDelegate
                public final void didSelectLocation(TLRPC.MessageMedia messageMedia, int i2, boolean z, int i3, long j) {
                    this.f$0.lambda$createView$5(messageMedia, i2, z, i3, j);
                }
            });
            presentFragment(locationActivity);
        }
        if (!(view instanceof TextCell) || this.chatType == 5) {
            return;
        }
        ActionBarPopupWindow actionBarPopupWindow = this.popupWindow;
        if (actionBarPopupWindow == null || !actionBarPopupWindow.isShowing()) {
            AutoDeletePopupWrapper autoDeletePopupWrapper = new AutoDeletePopupWrapper(getContext(), null, new AutoDeletePopupWrapper.Callback() { // from class: org.telegram.ui.GroupCreateFinalActivity.9
                @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
                public /* synthetic */ void showGlobalAutoDeleteScreen() {
                    AutoDeletePopupWrapper.Callback.CC.$default$showGlobalAutoDeleteScreen(this);
                }

                C56619() {
                }

                @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
                public void dismiss() {
                    GroupCreateFinalActivity.this.popupWindow.dismiss();
                }

                @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
                public void setAutoDeleteHistory(int i2, int i3) {
                    GroupCreateFinalActivity.this.ttlPeriod = i2;
                    AndroidUtilities.updateVisibleRows(GroupCreateFinalActivity.this.listView);
                }
            }, true, 1, null);
            autoDeletePopupWrapper.lambda$updateItems$7(this.ttlPeriod);
            ActionBarPopupWindow actionBarPopupWindow2 = new ActionBarPopupWindow(autoDeletePopupWrapper.windowLayout, -2, -2);
            this.popupWindow = actionBarPopupWindow2;
            actionBarPopupWindow2.setPauseNotifications(true);
            this.popupWindow.setDismissAnimationDuration(220);
            this.popupWindow.setOutsideTouchable(true);
            this.popupWindow.setClippingEnabled(true);
            this.popupWindow.setAnimationStyle(C2702R.style.PopupContextAnimation);
            this.popupWindow.setFocusable(true);
            autoDeletePopupWrapper.windowLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(1000.0f), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1081dp(1000.0f), Integer.MIN_VALUE));
            this.popupWindow.setInputMethodMode(2);
            this.popupWindow.getContentView().setFocusableInTouchMode(true);
            this.popupWindow.showAtLocation(getFragmentView(), 0, (int) (view.getX() + f), (int) (view.getY() + f2 + (autoDeletePopupWrapper.windowLayout.getMeasuredHeight() / 2.0f)));
            this.popupWindow.dimBehind();
        }
    }

    public /* synthetic */ void lambda$createView$5(TLRPC.MessageMedia messageMedia, int i, boolean z, int i2, long j) {
        this.currentGroupCreateLocation.setLatitude(messageMedia.geo.lat);
        this.currentGroupCreateLocation.setLongitude(messageMedia.geo._long);
        this.currentGroupCreateAddress = messageMedia.address;
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$9 */
    class C56619 implements AutoDeletePopupWrapper.Callback {
        @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
        public /* synthetic */ void showGlobalAutoDeleteScreen() {
            AutoDeletePopupWrapper.Callback.CC.$default$showGlobalAutoDeleteScreen(this);
        }

        C56619() {
        }

        @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
        public void dismiss() {
            GroupCreateFinalActivity.this.popupWindow.dismiss();
        }

        @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
        public void setAutoDeleteHistory(int i2, int i3) {
            GroupCreateFinalActivity.this.ttlPeriod = i2;
            AndroidUtilities.updateVisibleRows(GroupCreateFinalActivity.this.listView);
        }
    }

    public /* synthetic */ void lambda$createView$7(View view) {
        if (this.donePressed) {
            return;
        }
        if (this.editText.length() == 0) {
            this.editText.performHapticFeedback(3, 2);
            AndroidUtilities.shakeView(this.editText);
            return;
        }
        this.donePressed = true;
        AndroidUtilities.hideKeyboard(this.editText);
        this.editText.setEnabled(false);
        if (this.imageUpdater.isUploadingImage()) {
            this.createAfterUpload = true;
        } else {
            showEditDoneProgress(true);
            this.reqId = getMessagesController().createChat(this.editText.getText().toString(), this.selectedContacts, null, this.chatType, this.forImport, this.currentGroupCreateLocation, this.currentGroupCreateAddress, this.ttlPeriod, this);
        }
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void onUploadProgressChanged(float f) {
        RadialProgressView radialProgressView = this.avatarProgressView;
        if (radialProgressView == null) {
            return;
        }
        radialProgressView.setProgress(f);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void didStartUpload(boolean z, boolean z2) {
        RadialProgressView radialProgressView = this.avatarProgressView;
        if (radialProgressView == null) {
            return;
        }
        radialProgressView.setProgress(0.0f);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void didUploadPhoto(final TLRPC.InputFile inputFile, final TLRPC.InputFile inputFile2, final double d, final String str, final TLRPC.PhotoSize photoSize, final TLRPC.PhotoSize photoSize2, boolean z, final TLRPC.VideoSize videoSize) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$didUploadPhoto$8(inputFile, inputFile2, videoSize, str, d, photoSize2, photoSize);
            }
        });
    }

    public /* synthetic */ void lambda$didUploadPhoto$8(TLRPC.InputFile inputFile, TLRPC.InputFile inputFile2, TLRPC.VideoSize videoSize, String str, double d, TLRPC.PhotoSize photoSize, TLRPC.PhotoSize photoSize2) {
        if (inputFile != null || inputFile2 != null || videoSize != null) {
            this.inputPhoto = inputFile;
            this.inputVideo = inputFile2;
            this.inputEmojiMarkup = videoSize;
            this.inputVideoPath = str;
            this.videoTimestamp = d;
            if (this.createAfterUpload) {
                GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate = this.delegate;
                if (groupCreateFinalActivityDelegate != null) {
                    groupCreateFinalActivityDelegate.didStartChatCreation();
                }
                getMessagesController().createChat(this.editText.getText().toString(), this.selectedContacts, null, this.chatType, this.forImport, this.currentGroupCreateLocation, this.currentGroupCreateAddress, this.ttlPeriod, this);
            }
            showAvatarProgress(false, true);
            this.avatarEditor.setImageDrawable(null);
            return;
        }
        TLRPC.FileLocation fileLocation = photoSize.location;
        this.avatar = fileLocation;
        this.avatarBig = photoSize2.location;
        this.avatarImage.setImage(ImageLocation.getForLocal(fileLocation), "50_50", this.avatarDrawable, (Object) null);
        showAvatarProgress(true, false);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public String getInitialSearchString() {
        return this.editText.getText().toString();
    }

    public void setDelegate(GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate) {
        this.delegate = groupCreateFinalActivityDelegate;
    }

    private void showAvatarProgress(boolean z, boolean z2) {
        if (this.avatarEditor == null) {
            return;
        }
        AnimatorSet animatorSet = this.avatarAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.avatarAnimation = null;
        }
        if (!z2) {
            if (z) {
                this.avatarEditor.setAlpha(1.0f);
                this.avatarEditor.setVisibility(4);
                this.avatarProgressView.setAlpha(1.0f);
                this.avatarProgressView.setVisibility(0);
                return;
            }
            this.avatarEditor.setAlpha(1.0f);
            this.avatarEditor.setVisibility(0);
            this.avatarProgressView.setAlpha(0.0f);
            this.avatarProgressView.setVisibility(4);
            return;
        }
        this.avatarAnimation = new AnimatorSet();
        if (z) {
            this.avatarProgressView.setVisibility(0);
            AnimatorSet animatorSet2 = this.avatarAnimation;
            RLottieImageView rLottieImageView = this.avatarEditor;
            Property property = View.ALPHA;
            animatorSet2.playTogether(ObjectAnimator.ofFloat(rLottieImageView, (Property<RLottieImageView, Float>) property, 0.0f), ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) property, 1.0f));
        } else {
            this.avatarEditor.setVisibility(0);
            AnimatorSet animatorSet3 = this.avatarAnimation;
            RLottieImageView rLottieImageView2 = this.avatarEditor;
            Property property2 = View.ALPHA;
            animatorSet3.playTogether(ObjectAnimator.ofFloat(rLottieImageView2, (Property<RLottieImageView, Float>) property2, 1.0f), ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) property2, 0.0f));
        }
        this.avatarAnimation.setDuration(180L);
        this.avatarAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.GroupCreateFinalActivity.10
            final /* synthetic */ boolean val$show;

            C565310(boolean z3) {
                z = z3;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (GroupCreateFinalActivity.this.avatarAnimation == null || GroupCreateFinalActivity.this.avatarEditor == null) {
                    return;
                }
                if (z) {
                    GroupCreateFinalActivity.this.avatarEditor.setVisibility(4);
                } else {
                    GroupCreateFinalActivity.this.avatarProgressView.setVisibility(4);
                }
                GroupCreateFinalActivity.this.avatarAnimation = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                GroupCreateFinalActivity.this.avatarAnimation = null;
            }
        });
        this.avatarAnimation.start();
    }

    /* JADX INFO: renamed from: org.telegram.ui.GroupCreateFinalActivity$10 */
    class C565310 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$show;

        C565310(boolean z3) {
            z = z3;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (GroupCreateFinalActivity.this.avatarAnimation == null || GroupCreateFinalActivity.this.avatarEditor == null) {
                return;
            }
            if (z) {
                GroupCreateFinalActivity.this.avatarEditor.setVisibility(4);
            } else {
                GroupCreateFinalActivity.this.avatarProgressView.setVisibility(4);
            }
            GroupCreateFinalActivity.this.avatarAnimation = null;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            GroupCreateFinalActivity.this.avatarAnimation = null;
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onActivityResultFragment(int i, int i2, Intent intent) {
        super.onActivityResultFragment(i, i2, intent);
        this.imageUpdater.onActivityResult(i, i2, intent);
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void saveSelfArgs(Bundle bundle) {
        String str;
        ImageUpdater imageUpdater = this.imageUpdater;
        if (imageUpdater != null && (str = imageUpdater.currentPicturePath) != null) {
            bundle.putString("path", str);
        }
        EditTextEmoji editTextEmoji = this.editText;
        if (editTextEmoji != null) {
            String string = editTextEmoji.getText().toString();
            if (string.length() != 0) {
                bundle.putString("nameTextView", string);
            }
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void restoreSelfArgs(Bundle bundle) {
        ImageUpdater imageUpdater = this.imageUpdater;
        if (imageUpdater != null) {
            imageUpdater.currentPicturePath = bundle.getString("path");
        }
        String string = bundle.getString("nameTextView");
        if (string != null) {
            EditTextEmoji editTextEmoji = this.editText;
            if (editTextEmoji != null) {
                editTextEmoji.setText(string);
            } else {
                this.nameToSet = string;
            }
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            this.editText.openKeyboard();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.updateInterfaces) {
            if (this.listView == null) {
                return;
            }
            int iIntValue = ((Integer) objArr[0]).intValue();
            if ((MessagesController.UPDATE_MASK_AVATAR & iIntValue) == 0 && (MessagesController.UPDATE_MASK_NAME & iIntValue) == 0 && (MessagesController.UPDATE_MASK_STATUS & iIntValue) == 0) {
                return;
            }
            int childCount = this.listView.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = this.listView.getChildAt(i3);
                if (childAt instanceof GroupCreateUserCell) {
                    ((GroupCreateUserCell) childAt).update(iIntValue);
                }
            }
            return;
        }
        if (i == NotificationCenter.chatDidFailCreate) {
            this.reqId = 0;
            this.donePressed = false;
            showEditDoneProgress(false);
            EditTextEmoji editTextEmoji = this.editText;
            if (editTextEmoji != null) {
                editTextEmoji.setEnabled(true);
            }
            GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate = this.delegate;
            if (groupCreateFinalActivityDelegate != null) {
                groupCreateFinalActivityDelegate.didFailChatCreation();
                return;
            }
            return;
        }
        if (i == NotificationCenter.chatDidCreated) {
            this.reqId = 0;
            long jLongValue = ((Long) objArr[0]).longValue();
            GroupCreateFinalActivityDelegate groupCreateFinalActivityDelegate2 = this.delegate;
            if (groupCreateFinalActivityDelegate2 != null) {
                groupCreateFinalActivityDelegate2.didFinishChatCreation(this, jLongValue);
            } else {
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
                Bundle bundle = new Bundle();
                bundle.putLong("chat_id", jLongValue);
                bundle.putBoolean("just_created_chat", true);
                presentFragment(new ChatActivity(bundle), true);
            }
            if (this.inputPhoto == null && this.inputVideo == null && this.inputEmojiMarkup == null) {
                return;
            }
            getMessagesController().changeChatAvatar(jLongValue, null, this.inputPhoto, this.inputVideo, this.inputEmojiMarkup, this.videoTimestamp, this.inputVideoPath, this.avatar, this.avatarBig, null);
        }
    }

    private void showEditDoneProgress(boolean z) {
        FragmentFloatingButton fragmentFloatingButton = this.floatingButton;
        if (fragmentFloatingButton != null) {
            fragmentFloatingButton.setProgressVisible(z, true);
        }
    }

    public class GroupCreateAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        ArrayList items = new ArrayList();
        private int usersStartRow;

        public GroupCreateAdapter(Context context) {
            this.context = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            this.items.clear();
            this.items.add(new InnerItem(0));
            if (GroupCreateFinalActivity.this.chatType == 5) {
                this.items.add(new InnerItem(6));
                this.items.add(new InnerItem(5, LocaleController.getString(C2702R.string.ForumToggleDescription)));
            } else {
                this.items.add(new InnerItem(4));
                this.items.add(new InnerItem(5, LocaleController.getString(C2702R.string.GroupCreateAutodeleteDescription)));
            }
            if (GroupCreateFinalActivity.this.currentGroupCreateAddress != null) {
                this.items.add(new InnerItem(1));
                this.items.add(new InnerItem(3));
                this.items.add(new InnerItem(0));
            }
            if (GroupCreateFinalActivity.this.selectedContacts.size() > 0) {
                this.items.add(new InnerItem(1));
                this.usersStartRow = this.items.size();
                for (int i = 0; i < GroupCreateFinalActivity.this.selectedContacts.size(); i++) {
                    this.items.add(new InnerItem(2));
                }
                this.items.add(new InnerItem(7));
            }
            super.notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.items.size();
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 3 || viewHolder.getItemViewType() == 4) {
                return true;
            }
            return viewHolder.getItemViewType() == 6 && GroupCreateFinalActivity.this.canToggleTopics;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View textCell;
            if (i == 0) {
                textCell = new ShadowSectionCell(this.context);
            } else if (i == 1) {
                HeaderCell headerCell = new HeaderCell(this.context);
                headerCell.setHeight(46);
                textCell = headerCell;
            } else if (i == 2) {
                textCell = new GroupCreateUserCell(this.context, 0, 3, false);
            } else if (i == 4) {
                textCell = new TextCell(this.context);
            } else if (i == 5) {
                textCell = new TextInfoPrivacyCell(this.context);
            } else if (i == 6) {
                textCell = new TextCell(this.context, 23, false, true, GroupCreateFinalActivity.this.getResourceProvider());
            } else if (i != 7) {
                textCell = new TextSettingsCell(this.context);
            } else {
                View view = new View(this.context);
                view.setTag(-33024);
                textCell = view;
            }
            return new RecyclerListView.Holder(textCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            String tTLString;
            switch (viewHolder.getItemViewType()) {
                case 1:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (GroupCreateFinalActivity.this.currentGroupCreateAddress != null && i == 1) {
                        headerCell.setText(LocaleController.getString(C2702R.string.AttachLocation));
                    } else {
                        headerCell.setText(LocaleController.formatPluralString("Members", GroupCreateFinalActivity.this.selectedContacts.size(), new Object[0]));
                    }
                    break;
                case 2:
                    GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) viewHolder.itemView;
                    groupCreateUserCell.setObject(GroupCreateFinalActivity.this.getMessagesController().getUser((Long) GroupCreateFinalActivity.this.selectedContacts.get(i - this.usersStartRow)), null, null);
                    groupCreateUserCell.setDrawDivider(i != this.items.size() - 1);
                    break;
                case 3:
                    ((TextSettingsCell) viewHolder.itemView).setText(GroupCreateFinalActivity.this.currentGroupCreateAddress, false);
                    break;
                case 4:
                    TextCell textCell = (TextCell) viewHolder.itemView;
                    if (GroupCreateFinalActivity.this.ttlPeriod == 0) {
                        tTLString = LocaleController.getString(C2702R.string.PasswordOff);
                    } else {
                        tTLString = LocaleController.formatTTLString(GroupCreateFinalActivity.this.ttlPeriod);
                    }
                    textCell.setTextAndValueAndIcon(LocaleController.getString(C2702R.string.AutoDeleteMessages), tTLString, ((BaseFragment) GroupCreateFinalActivity.this).fragmentBeginToShow, C2702R.drawable.msg_autodelete, false);
                    break;
                case 5:
                    ((TextInfoPrivacyCell) viewHolder.itemView).setText(((InnerItem) this.items.get(i)).string);
                    break;
                case 6:
                    TextCell textCell2 = (TextCell) viewHolder.itemView;
                    textCell2.setTextAndCheckAndIcon(LocaleController.getString(C2702R.string.ChannelTopics), true, C2702R.drawable.msg_topics, false);
                    textCell2.getCheckBox().setAlpha(0.75f);
                    break;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return ((InnerItem) this.items.get(i)).viewType;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 2) {
                ((GroupCreateUserCell) viewHolder.itemView).recycle();
            }
        }

        private class InnerItem extends AdapterWithDiffUtils.Item {
            String string;

            public InnerItem(int i) {
                super(i, true);
            }

            public InnerItem(int i, String str) {
                super(i, true);
                this.string = str;
            }
        }
    }

    @Override // org.telegram.p026ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        ArrayList arrayList = new ArrayList();
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.GroupCreateFinalActivity$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.lambda$getThemeDescriptions$9();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        ActionBar actionBar = this.actionBar;
        int i = ThemeDescription.FLAG_BACKGROUND;
        int i2 = Theme.key_windowBackgroundGray;
        arrayList.add(new ThemeDescription(actionBar, i, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollActive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollInactive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        EditTextEmoji editTextEmoji = this.editText;
        int i3 = ThemeDescription.FLAG_TEXTCOLOR;
        int i4 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(editTextEmoji, i3, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_groupcreate_hintText));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_CURSORCOLOR, null, null, null, null, Theme.key_groupcreate_cursor));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ShadowSectionCell.class}, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{GroupCreateUserCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_groupcreate_sectionText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{GroupCreateUserCell.class}, new String[]{"statusTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{GroupCreateUserCell.class}, null, Theme.avatarDrawables, themeDescriptionDelegate, Theme.key_avatar_text));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundRed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundOrange));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundViolet));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundGreen));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundCyan));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundBlue));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundPink));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
        return arrayList;
    }

    public /* synthetic */ void lambda$getThemeDescriptions$9() {
        RecyclerListView recyclerListView = this.listView;
        if (recyclerListView != null) {
            int childCount = recyclerListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.listView.getChildAt(i);
                if (childAt instanceof GroupCreateUserCell) {
                    ((GroupCreateUserCell) childAt).update(0);
                }
            }
        }
        FragmentFloatingButton fragmentFloatingButton = this.floatingButton;
        if (fragmentFloatingButton != null) {
            fragmentFloatingButton.updateColors();
        }
    }
}
