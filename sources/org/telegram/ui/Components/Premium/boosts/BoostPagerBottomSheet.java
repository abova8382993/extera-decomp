package org.telegram.ui.Components.Premium.boosts;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.tgnet.tl.TL_stories;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.Bulletin;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.Premium.boosts.BoostViaGiftsBottomSheet;
import org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet;
import org.telegram.ui.Components.ViewPagerFixed;
import org.telegram.ui.Stories.DarkThemeResourceProvider;

/* JADX INFO: loaded from: classes5.dex */
public class BoostPagerBottomSheet extends BottomSheet {
    private static BoostPagerBottomSheet instance;
    private boolean isLandscapeOrientation;
    private BoostViaGiftsBottomSheet leftSheet;
    private final SelectorBottomSheet rightSheet;
    private final ViewPagerFixed viewPager;

    @Override // org.telegram.ui.ActionBar.BottomSheet
    protected boolean canDismissWithSwipe() {
        return false;
    }

    public static void show(BaseFragment baseFragment, long j, Theme.ResourcesProvider resourcesProvider) {
        show(baseFragment, resourcesProvider, j, null);
    }

    public static void show(BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, long j, TL_stories.PrepaidGiveaway prepaidGiveaway) {
        if (instance != null) {
            return;
        }
        boolean z = resourcesProvider instanceof DarkThemeResourceProvider;
        BaseFragment darkFragmentWrapper = z ? new DarkFragmentWrapper(baseFragment) : baseFragment;
        BoostPagerBottomSheet boostPagerBottomSheet = new BoostPagerBottomSheet(baseFragment.getParentActivity(), true, new BoostViaGiftsBottomSheet(darkFragmentWrapper, false, false, j, prepaidGiveaway), new SelectorBottomSheet(darkFragmentWrapper, false, j), darkFragmentWrapper.getResourceProvider(), z);
        boostPagerBottomSheet.show();
        instance = boostPagerBottomSheet;
    }

    public static BoostPagerBottomSheet getInstance() {
        return instance;
    }

    public BoostPagerBottomSheet(Context context, boolean z, BoostViaGiftsBottomSheet boostViaGiftsBottomSheet, SelectorBottomSheet selectorBottomSheet, Theme.ResourcesProvider resourcesProvider, boolean z2) {
        super(context, z, resourcesProvider);
        this.leftSheet = boostViaGiftsBottomSheet;
        this.rightSheet = selectorBottomSheet;
        setApplyBottomPadding(false);
        setApplyTopPadding(false);
        this.useBackgroundTopPadding = false;
        setBackgroundColor(0);
        fixNavigationBar();
        AndroidUtilities.setLightStatusBar(getWindow(), isLightStatusBar());
        checkScreenOrientation();
        AnonymousClass1 anonymousClass1 = new ViewPagerFixed(getContext()) { // from class: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet.1
            private boolean isKeyboardVisible;
            private boolean isScrolling;
            final /* synthetic */ BoostViaGiftsBottomSheet val$leftSheet;
            final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;
            final /* synthetic */ SelectorBottomSheet val$rightSheet;
            private final Path path = new Path();
            private final Paint backgroundPaint = new Paint(1);
            private final boolean isTablet = AndroidUtilities.isTablet();

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(Context context2, SelectorBottomSheet selectorBottomSheet2, Theme.ResourcesProvider resourcesProvider2, BoostViaGiftsBottomSheet boostViaGiftsBottomSheet2) {
                super(context2);
                selectorBottomSheet = selectorBottomSheet2;
                resourcesProvider = resourcesProvider2;
                boostViaGiftsBottomSheet = boostViaGiftsBottomSheet2;
                this.path = new Path();
                this.backgroundPaint = new Paint(1);
                this.isTablet = AndroidUtilities.isTablet();
            }

            @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z3, int i, int i2, int i3, int i4) {
                super.onLayout(z3, i, i2, i3, i4);
                if (this.isKeyboardVisible != BoostPagerBottomSheet.this.isKeyboardVisible()) {
                    boolean zIsKeyboardVisible = BoostPagerBottomSheet.this.isKeyboardVisible();
                    this.isKeyboardVisible = zIsKeyboardVisible;
                    if (zIsKeyboardVisible) {
                        selectorBottomSheet.scrollToTop(true);
                    }
                }
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed
            public void onTabAnimationUpdate(boolean z3) {
                float positionAnimated = BoostPagerBottomSheet.this.viewPager.getPositionAnimated();
                if (positionAnimated > 0.0f && positionAnimated < 1.0f) {
                    if (!this.isScrolling) {
                        this.isScrolling = true;
                        BoostPagerBottomSheet.this.hideKeyboardIfVisible();
                    }
                } else {
                    this.isScrolling = false;
                }
                BoostPagerBottomSheet.this.viewPager.invalidate();
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed
            protected void onScrollEnd() {
                this.isScrolling = false;
                BoostPagerBottomSheet.this.viewPager.invalidate();
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                float positionAnimated;
                float f;
                float f2;
                float f3;
                this.backgroundPaint.setColor(Theme.getColor(Theme.key_dialogBackground, resourcesProvider));
                if (this.isScrolling) {
                    int top = boostViaGiftsBottomSheet.getTop() + AndroidUtilities.dp(10.0f);
                    int top2 = selectorBottomSheet.getTop();
                    int iAbs = Math.abs(top - top2);
                    if (BoostPagerBottomSheet.this.viewPager.getCurrentPosition() == 0) {
                        positionAnimated = iAbs * BoostPagerBottomSheet.this.viewPager.getPositionAnimated();
                        if (top < top2) {
                            f2 = top;
                            f3 = f2 + positionAnimated;
                        } else {
                            f = top;
                            f3 = f - positionAnimated;
                        }
                    } else {
                        positionAnimated = iAbs * (1.0f - BoostPagerBottomSheet.this.viewPager.getPositionAnimated());
                        if (top2 < top) {
                            f2 = top2;
                            f3 = f2 + positionAnimated;
                        } else {
                            f = top2;
                            f3 = f - positionAnimated;
                        }
                    }
                    int i = (int) f3;
                    float fDp = AndroidUtilities.dp(14.0f);
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, i, getWidth(), getHeight() + AndroidUtilities.dp(8.0f));
                    canvas.drawRoundRect(rectF, fDp, fDp, this.backgroundPaint);
                    canvas.save();
                    this.path.rewind();
                    this.path.addRoundRect(rectF, fDp, fDp, Path.Direction.CW);
                    canvas.clipPath(this.path);
                    super.dispatchDraw(canvas);
                    canvas.restore();
                    return;
                }
                if (this.isTablet || BoostPagerBottomSheet.this.isLandscapeOrientation) {
                    canvas.clipRect(0, 0, getMeasuredWidth(), getMeasuredHeight());
                }
                super.dispatchDraw(canvas);
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed
            protected float getAvailableTranslationX() {
                if (this.isTablet || BoostPagerBottomSheet.this.isLandscapeOrientation) {
                    return getMeasuredWidth();
                }
                return super.getAvailableTranslationX();
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed
            protected boolean canScroll(MotionEvent motionEvent) {
                return BoostPagerBottomSheet.this.viewPager.getCurrentPosition() == 1;
            }
        };
        this.viewPager = anonymousClass1;
        anonymousClass1.setOverScrollMode(2);
        anonymousClass1.setClipToPadding(false);
        anonymousClass1.setAdapter(new ViewPagerFixed.Adapter() { // from class: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet.2
            final /* synthetic */ BoostViaGiftsBottomSheet val$leftSheet;
            final /* synthetic */ SelectorBottomSheet val$rightSheet;

            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public void bindView(View view, int i, int i2) {
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public int getItemCount() {
                return 2;
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public int getItemViewType(int i) {
                return i;
            }

            AnonymousClass2(BoostViaGiftsBottomSheet boostViaGiftsBottomSheet2, SelectorBottomSheet selectorBottomSheet2) {
                boostViaGiftsBottomSheet = boostViaGiftsBottomSheet2;
                selectorBottomSheet = selectorBottomSheet2;
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public View createView(int i) {
                if (i == 0) {
                    return boostViaGiftsBottomSheet.getContainerView();
                }
                return selectorBottomSheet.getContainerView();
            }
        });
        anonymousClass1.setPosition(0);
        setCustomView(anonymousClass1);
        boostViaGiftsBottomSheet2.setOnCloseClick(new Runnable() { // from class: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
        boostViaGiftsBottomSheet2.setActionListener(new BoostViaGiftsBottomSheet.ActionListener() { // from class: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet.3
            final /* synthetic */ SelectorBottomSheet val$rightSheet;

            AnonymousClass3(SelectorBottomSheet selectorBottomSheet2) {
                selectorBottomSheet = selectorBottomSheet2;
            }

            @Override // org.telegram.ui.Components.Premium.boosts.BoostViaGiftsBottomSheet.ActionListener
            public void onAddChat(List list) {
                selectorBottomSheet.prepare(list, 2);
                BoostPagerBottomSheet.this.viewPager.scrollToPosition(1);
            }

            @Override // org.telegram.ui.Components.Premium.boosts.BoostViaGiftsBottomSheet.ActionListener
            public void onSelectUser(List list) {
                selectorBottomSheet.prepare(list, 1);
                BoostPagerBottomSheet.this.viewPager.scrollToPosition(1);
            }

            @Override // org.telegram.ui.Components.Premium.boosts.BoostViaGiftsBottomSheet.ActionListener
            public void onSelectCountries(List list) {
                selectorBottomSheet.prepare(list, 3);
                BoostPagerBottomSheet.this.viewPager.scrollToPosition(1);
            }
        });
        selectorBottomSheet2.setSelectedObjectsListener(new SelectorBottomSheet.SelectedObjectsListener() { // from class: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet.4
            final /* synthetic */ BoostViaGiftsBottomSheet val$leftSheet;
            final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;

            AnonymousClass4(BoostViaGiftsBottomSheet boostViaGiftsBottomSheet2, Theme.ResourcesProvider resourcesProvider2) {
                boostViaGiftsBottomSheet = boostViaGiftsBottomSheet2;
                resourcesProvider = resourcesProvider2;
            }

            @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
            public void onChatsSelected(List list, boolean z3) {
                BoostPagerBottomSheet.this.viewPager.scrollToPosition(0);
                boostViaGiftsBottomSheet.onChatsSelected(list, !BoostPagerBottomSheet.this.isKeyboardVisible());
            }

            @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
            public void onUsersSelected(List list) {
                BoostPagerBottomSheet.this.viewPager.scrollToPosition(0);
                boostViaGiftsBottomSheet.onUsersSelected(list);
            }

            @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
            public void onCountrySelected(List list) {
                BoostPagerBottomSheet.this.viewPager.scrollToPosition(0);
                boostViaGiftsBottomSheet.onCountrySelected(list);
            }

            @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
            public void onShowToast(String str) {
                BulletinFactory.of(BoostPagerBottomSheet.this.container, resourcesProvider).createSimpleBulletin(R.raw.chats_infotip, str).show(true);
            }
        });
        selectorBottomSheet2.setOnCloseClick(new Runnable() { // from class: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openCrafting$9();
            }
        });
        loadData(z2);
        Bulletin.addDelegate(this.container, new Bulletin.Delegate() { // from class: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet.5
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
            public /* synthetic */ int getBottomOffset(int i) {
                return Bulletin.Delegate.CC.$default$getBottomOffset(this, i);
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

            AnonymousClass5() {
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getTopOffset(int i) {
                return AndroidUtilities.statusBarHeight;
            }
        });
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet$1 */
    class AnonymousClass1 extends ViewPagerFixed {
        private boolean isKeyboardVisible;
        private boolean isScrolling;
        final /* synthetic */ BoostViaGiftsBottomSheet val$leftSheet;
        final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;
        final /* synthetic */ SelectorBottomSheet val$rightSheet;
        private final Path path = new Path();
        private final Paint backgroundPaint = new Paint(1);
        private final boolean isTablet = AndroidUtilities.isTablet();

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Context context2, SelectorBottomSheet selectorBottomSheet2, Theme.ResourcesProvider resourcesProvider2, BoostViaGiftsBottomSheet boostViaGiftsBottomSheet2) {
            super(context2);
            selectorBottomSheet = selectorBottomSheet2;
            resourcesProvider = resourcesProvider2;
            boostViaGiftsBottomSheet = boostViaGiftsBottomSheet2;
            this.path = new Path();
            this.backgroundPaint = new Paint(1);
            this.isTablet = AndroidUtilities.isTablet();
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z3, int i, int i2, int i3, int i4) {
            super.onLayout(z3, i, i2, i3, i4);
            if (this.isKeyboardVisible != BoostPagerBottomSheet.this.isKeyboardVisible()) {
                boolean zIsKeyboardVisible = BoostPagerBottomSheet.this.isKeyboardVisible();
                this.isKeyboardVisible = zIsKeyboardVisible;
                if (zIsKeyboardVisible) {
                    selectorBottomSheet.scrollToTop(true);
                }
            }
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed
        public void onTabAnimationUpdate(boolean z3) {
            float positionAnimated = BoostPagerBottomSheet.this.viewPager.getPositionAnimated();
            if (positionAnimated > 0.0f && positionAnimated < 1.0f) {
                if (!this.isScrolling) {
                    this.isScrolling = true;
                    BoostPagerBottomSheet.this.hideKeyboardIfVisible();
                }
            } else {
                this.isScrolling = false;
            }
            BoostPagerBottomSheet.this.viewPager.invalidate();
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed
        protected void onScrollEnd() {
            this.isScrolling = false;
            BoostPagerBottomSheet.this.viewPager.invalidate();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            float positionAnimated;
            float f;
            float f2;
            float f3;
            this.backgroundPaint.setColor(Theme.getColor(Theme.key_dialogBackground, resourcesProvider));
            if (this.isScrolling) {
                int top = boostViaGiftsBottomSheet.getTop() + AndroidUtilities.dp(10.0f);
                int top2 = selectorBottomSheet.getTop();
                int iAbs = Math.abs(top - top2);
                if (BoostPagerBottomSheet.this.viewPager.getCurrentPosition() == 0) {
                    positionAnimated = iAbs * BoostPagerBottomSheet.this.viewPager.getPositionAnimated();
                    if (top < top2) {
                        f2 = top;
                        f3 = f2 + positionAnimated;
                    } else {
                        f = top;
                        f3 = f - positionAnimated;
                    }
                } else {
                    positionAnimated = iAbs * (1.0f - BoostPagerBottomSheet.this.viewPager.getPositionAnimated());
                    if (top2 < top) {
                        f2 = top2;
                        f3 = f2 + positionAnimated;
                    } else {
                        f = top2;
                        f3 = f - positionAnimated;
                    }
                }
                int i = (int) f3;
                float fDp = AndroidUtilities.dp(14.0f);
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, i, getWidth(), getHeight() + AndroidUtilities.dp(8.0f));
                canvas.drawRoundRect(rectF, fDp, fDp, this.backgroundPaint);
                canvas.save();
                this.path.rewind();
                this.path.addRoundRect(rectF, fDp, fDp, Path.Direction.CW);
                canvas.clipPath(this.path);
                super.dispatchDraw(canvas);
                canvas.restore();
                return;
            }
            if (this.isTablet || BoostPagerBottomSheet.this.isLandscapeOrientation) {
                canvas.clipRect(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
            super.dispatchDraw(canvas);
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed
        protected float getAvailableTranslationX() {
            if (this.isTablet || BoostPagerBottomSheet.this.isLandscapeOrientation) {
                return getMeasuredWidth();
            }
            return super.getAvailableTranslationX();
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed
        protected boolean canScroll(MotionEvent motionEvent) {
            return BoostPagerBottomSheet.this.viewPager.getCurrentPosition() == 1;
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet$2 */
    class AnonymousClass2 extends ViewPagerFixed.Adapter {
        final /* synthetic */ BoostViaGiftsBottomSheet val$leftSheet;
        final /* synthetic */ SelectorBottomSheet val$rightSheet;

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public void bindView(View view, int i, int i2) {
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public int getItemCount() {
            return 2;
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public int getItemViewType(int i) {
            return i;
        }

        AnonymousClass2(BoostViaGiftsBottomSheet boostViaGiftsBottomSheet2, SelectorBottomSheet selectorBottomSheet2) {
            boostViaGiftsBottomSheet = boostViaGiftsBottomSheet2;
            selectorBottomSheet = selectorBottomSheet2;
        }

        @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
        public View createView(int i) {
            if (i == 0) {
                return boostViaGiftsBottomSheet.getContainerView();
            }
            return selectorBottomSheet.getContainerView();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet$3 */
    class AnonymousClass3 implements BoostViaGiftsBottomSheet.ActionListener {
        final /* synthetic */ SelectorBottomSheet val$rightSheet;

        AnonymousClass3(SelectorBottomSheet selectorBottomSheet2) {
            selectorBottomSheet = selectorBottomSheet2;
        }

        @Override // org.telegram.ui.Components.Premium.boosts.BoostViaGiftsBottomSheet.ActionListener
        public void onAddChat(List list) {
            selectorBottomSheet.prepare(list, 2);
            BoostPagerBottomSheet.this.viewPager.scrollToPosition(1);
        }

        @Override // org.telegram.ui.Components.Premium.boosts.BoostViaGiftsBottomSheet.ActionListener
        public void onSelectUser(List list) {
            selectorBottomSheet.prepare(list, 1);
            BoostPagerBottomSheet.this.viewPager.scrollToPosition(1);
        }

        @Override // org.telegram.ui.Components.Premium.boosts.BoostViaGiftsBottomSheet.ActionListener
        public void onSelectCountries(List list) {
            selectorBottomSheet.prepare(list, 3);
            BoostPagerBottomSheet.this.viewPager.scrollToPosition(1);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet$4 */
    class AnonymousClass4 implements SelectorBottomSheet.SelectedObjectsListener {
        final /* synthetic */ BoostViaGiftsBottomSheet val$leftSheet;
        final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;

        AnonymousClass4(BoostViaGiftsBottomSheet boostViaGiftsBottomSheet2, Theme.ResourcesProvider resourcesProvider2) {
            boostViaGiftsBottomSheet = boostViaGiftsBottomSheet2;
            resourcesProvider = resourcesProvider2;
        }

        @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
        public void onChatsSelected(List list, boolean z3) {
            BoostPagerBottomSheet.this.viewPager.scrollToPosition(0);
            boostViaGiftsBottomSheet.onChatsSelected(list, !BoostPagerBottomSheet.this.isKeyboardVisible());
        }

        @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
        public void onUsersSelected(List list) {
            BoostPagerBottomSheet.this.viewPager.scrollToPosition(0);
            boostViaGiftsBottomSheet.onUsersSelected(list);
        }

        @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
        public void onCountrySelected(List list) {
            BoostPagerBottomSheet.this.viewPager.scrollToPosition(0);
            boostViaGiftsBottomSheet.onCountrySelected(list);
        }

        @Override // org.telegram.ui.Components.Premium.boosts.SelectorBottomSheet.SelectedObjectsListener
        public void onShowToast(String str) {
            BulletinFactory.of(BoostPagerBottomSheet.this.container, resourcesProvider).createSimpleBulletin(R.raw.chats_infotip, str).show(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.Premium.boosts.BoostPagerBottomSheet$5 */
    class AnonymousClass5 implements Bulletin.Delegate {
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
        public /* synthetic */ int getBottomOffset(int i) {
            return Bulletin.Delegate.CC.$default$getBottomOffset(this, i);
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

        AnonymousClass5() {
        }

        @Override // org.telegram.ui.Components.Bulletin.Delegate
        public int getTopOffset(int i) {
            return AndroidUtilities.statusBarHeight;
        }
    }

    private void checkScreenOrientation() {
        this.isLandscapeOrientation = getContext().getResources().getConfiguration().orientation == 2;
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet
    public void dismissInternal() {
        super.dismissInternal();
        Bulletin.removeDelegate(this.container);
        BoostViaGiftsBottomSheet boostViaGiftsBottomSheet = this.leftSheet;
        if (boostViaGiftsBottomSheet != null) {
            boostViaGiftsBottomSheet.lambda$new$0();
        }
        instance = null;
        this.leftSheet = null;
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet
    public void onConfigurationChanged(Configuration configuration) {
        this.rightSheet.onConfigurationChanged(configuration);
        checkScreenOrientation();
        super.onConfigurationChanged(configuration);
    }

    private void loadData(boolean z) {
        if (z) {
            return;
        }
        MessagesController.getInstance(this.currentAccount).getStoriesController().loadSendAs();
    }

    public void hideKeyboardIfVisible() {
        if (isKeyboardVisible()) {
            AndroidUtilities.hideKeyboard(this.rightSheet.getContainerView());
        }
    }

    @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog
    /* JADX INFO: renamed from: onBackPressed */
    public void lambda$openCrafting$9() {
        if (this.viewPager.getCurrentPosition() > 0) {
            if (this.rightSheet.hasChanges()) {
                return;
            }
            hideKeyboardIfVisible();
            this.viewPager.scrollToPosition(0);
            return;
        }
        super.lambda$openCrafting$9();
    }

    private boolean isLightStatusBar() {
        return ColorUtils.calculateLuminance(Theme.getColor(Theme.key_dialogBackground, this.resourcesProvider)) > 0.699999988079071d;
    }
}
