package org.telegram.ui.Components;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Delegates.MemberRequestsDelegate;
import org.telegram.ui.LaunchActivity;

/* JADX INFO: loaded from: classes5.dex */
public abstract class MemberRequestsBottomSheet extends UsersAlertBase {
    private final FlickerLoadingView currentLoadingView;
    private final MemberRequestsDelegate delegate;
    private boolean enterEventSent;
    private final StickerEmptyView membersEmptyView;
    private final StickerEmptyView membersSearchEmptyView;
    private final int touchSlop;
    private float yOffset;

    public MemberRequestsBottomSheet(BaseFragment baseFragment, long j) {
        super(baseFragment.getParentActivity(), false, baseFragment.getCurrentAccount(), baseFragment.getResourceProvider());
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.needSnapToTop = false;
        this.isEmptyViewVisible = false;
        AnonymousClass1 anonymousClass1 = new MemberRequestsDelegate(baseFragment, this.container, j, false) { // from class: org.telegram.ui.Components.MemberRequestsBottomSheet.1
            AnonymousClass1(BaseFragment baseFragment2, FrameLayout frameLayout, long j2, boolean z) {
                super(baseFragment2, frameLayout, j2, z);
            }

            @Override // org.telegram.ui.Delegates.MemberRequestsDelegate
            protected void onImportersChanged(String str, boolean z, boolean z2) {
                if (!hasAllImporters()) {
                    if (MemberRequestsBottomSheet.this.membersEmptyView.getVisibility() != 4) {
                        MemberRequestsBottomSheet.this.membersEmptyView.setVisibility(4);
                    }
                } else if (z2) {
                    MemberRequestsBottomSheet.this.searchView.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
                } else {
                    super.onImportersChanged(str, z, z2);
                }
            }
        };
        this.delegate = anonymousClass1;
        anonymousClass1.setShowLastItemDivider(false);
        setDimBehindAlpha(75);
        this.searchView.searchEditText.setHint(LocaleController.getString(R.string.SearchMemberRequests));
        MemberRequestsDelegate.Adapter adapter = anonymousClass1.getAdapter();
        this.listViewAdapter = adapter;
        this.searchListViewAdapter = adapter;
        this.listView.setAdapter(adapter);
        anonymousClass1.setRecyclerView(this.listView);
        int iIndexOfChild = ((ViewGroup) this.listView.getParent()).indexOfChild(this.listView);
        FlickerLoadingView loadingView = anonymousClass1.getLoadingView();
        this.currentLoadingView = loadingView;
        this.containerView.addView(loadingView, iIndexOfChild, LayoutHelper.createFrame(-1, -1.0f));
        StickerEmptyView emptyView = anonymousClass1.getEmptyView();
        this.membersEmptyView = emptyView;
        this.containerView.addView(emptyView, iIndexOfChild, LayoutHelper.createFrame(-1, -1.0f));
        StickerEmptyView searchEmptyView = anonymousClass1.getSearchEmptyView();
        this.membersSearchEmptyView = searchEmptyView;
        this.containerView.addView(searchEmptyView, iIndexOfChild, LayoutHelper.createFrame(-1, -1.0f));
        anonymousClass1.lambda$new$8();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.MemberRequestsBottomSheet$1 */
    class AnonymousClass1 extends MemberRequestsDelegate {
        AnonymousClass1(BaseFragment baseFragment2, FrameLayout frameLayout, long j2, boolean z) {
            super(baseFragment2, frameLayout, j2, z);
        }

        @Override // org.telegram.ui.Delegates.MemberRequestsDelegate
        protected void onImportersChanged(String str, boolean z, boolean z2) {
            if (!hasAllImporters()) {
                if (MemberRequestsBottomSheet.this.membersEmptyView.getVisibility() != 4) {
                    MemberRequestsBottomSheet.this.membersEmptyView.setVisibility(4);
                }
            } else if (z2) {
                MemberRequestsBottomSheet.this.searchView.searchEditText.setText(_UrlKt.FRAGMENT_ENCODE_SET);
            } else {
                super.onImportersChanged(str, z, z2);
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog
    public void show() {
        if (this.delegate.isNeedRestoreList && this.scrollOffsetY == 0) {
            this.scrollOffsetY = AndroidUtilities.dp(8.0f);
        }
        super.show();
        this.delegate.isNeedRestoreList = false;
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog
    /* JADX INFO: renamed from: onBackPressed */
    public void lambda$openCrafting$9() {
        if (this.delegate.onBackPressed(true)) {
            super.lambda$openCrafting$9();
        }
    }

    public boolean isNeedRestoreDialog() {
        return this.delegate.isNeedRestoreList;
    }

    @Override // org.telegram.ui.Components.UsersAlertBase
    protected void setTranslationY(int i) {
        super.setTranslationY(i);
        this.currentLoadingView.setTranslationY(this.frameLayout.getMeasuredHeight() + i);
        float f = i;
        this.membersEmptyView.setTranslationY(f);
        this.membersSearchEmptyView.setTranslationY(f);
    }

    @Override // org.telegram.ui.Components.UsersAlertBase
    protected void updateLayout() {
        if (this.listView.getChildCount() <= 0) {
            int paddingTop = this.listView.getVisibility() == 0 ? this.listView.getPaddingTop() - AndroidUtilities.dp(8.0f) : 0;
            if (this.scrollOffsetY != paddingTop) {
                this.scrollOffsetY = paddingTop;
                setTranslationY(paddingTop);
                return;
            }
            return;
        }
        super.updateLayout();
    }

    @Override // org.telegram.ui.Components.UsersAlertBase
    protected void search(String str) {
        super.search(str);
        this.delegate.setQuery(str);
    }

    @Override // org.telegram.ui.Components.UsersAlertBase
    protected void onSearchViewTouched(MotionEvent motionEvent, final EditTextBoldCursor editTextBoldCursor) {
        BaseFragment baseFragment;
        if (motionEvent.getAction() == 0) {
            this.yOffset = this.scrollOffsetY;
            this.delegate.setAdapterItemsEnabled(false);
        } else if (motionEvent.getAction() == 1 && Math.abs(this.scrollOffsetY - this.yOffset) < this.touchSlop && !this.enterEventSent) {
            Activity activityFindActivity = AndroidUtilities.findActivity(getContext());
            if (activityFindActivity instanceof LaunchActivity) {
                LaunchActivity launchActivity = (LaunchActivity) activityFindActivity;
                baseFragment = (BaseFragment) launchActivity.getActionBarLayout().getFragmentStack().get(launchActivity.getActionBarLayout().getFragmentStack().size() - 1);
            } else {
                baseFragment = null;
            }
            if (baseFragment instanceof ChatActivity) {
                boolean zNeedEnterText = ((ChatActivity) baseFragment).needEnterText();
                this.enterEventSent = true;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.MemberRequestsBottomSheet$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSearchViewTouched$1(editTextBoldCursor);
                    }
                }, zNeedEnterText ? 200L : 0L);
            } else {
                this.enterEventSent = true;
                setFocusable(true);
                editTextBoldCursor.requestFocus();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.MemberRequestsBottomSheet$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AndroidUtilities.showKeyboard(editTextBoldCursor);
                    }
                });
            }
        }
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.delegate.setAdapterItemsEnabled(true);
        }
    }

    public /* synthetic */ void lambda$onSearchViewTouched$1(final EditTextBoldCursor editTextBoldCursor) {
        setFocusable(true);
        editTextBoldCursor.requestFocus();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.MemberRequestsBottomSheet$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AndroidUtilities.showKeyboard(editTextBoldCursor);
            }
        });
    }
}
