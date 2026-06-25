package org.telegram.p035ui.bots;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.INavigationLayout;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.HeaderCell;
import org.telegram.p035ui.Components.BulletinFactory;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.LinkSpanDrawable;
import org.telegram.p035ui.Components.Premium.GLIcon.GLIconRenderer;
import org.telegram.p035ui.Components.Premium.GLIcon.GLIconTextureView;
import org.telegram.p035ui.Components.Premium.StarParticlesView;
import org.telegram.p035ui.Components.Premium.boosts.cells.selector.SelectorUserCell;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.TableView;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;
import org.telegram.p035ui.Components.UniversalRecyclerView;
import org.telegram.p035ui.GradientHeaderActivity;
import org.telegram.p035ui.ProfileActivity;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p034tl.TL_bots;
import org.telegram.tgnet.p034tl.TL_payments;

/* JADX INFO: loaded from: classes7.dex */
public class AffiliateProgramFragment extends GradientHeaderActivity implements NotificationCenter.NotificationCenterDelegate {
    private FrameLayout aboveTitleView;
    private UniversalAdapter adapter;
    private boolean attached;
    private final long bot_id;
    private ButtonWithCounterView button;
    private LinearLayout buttonLayout;
    private LinkSpanDrawable.LinksTextView buttonSubtext;
    private View emptyLayout;
    private GLIconTextureView iconTextureView;
    private TL_payments.starRefProgram initialProgram;
    private boolean new_program;
    private TL_payments.starRefProgram program;
    private final Runnable updateTimerRunnable = new Runnable() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$7();
        }
    };
    private String[] durationTexts = null;
    private final List<Integer> durationValues = Arrays.asList(1, 3, 6, 12, 24, 36, 0);

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity, org.telegram.p035ui.ActionBar.BaseFragment
    public boolean isSupportEdgeToEdge() {
        return true;
    }

    public AffiliateProgramFragment(long j) {
        this.bot_id = j;
        setWhiteBackground(true);
        setMinusHeaderHeight(AndroidUtilities.m1036dp(60.0f));
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity, org.telegram.p035ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        this.useFillLastLayoutManager = false;
        this.particlesViewHeight = AndroidUtilities.m1036dp(238.0f);
        C74331 c74331 = new View(context) { // from class: org.telegram.ui.bots.AffiliateProgramFragment.1
            public C74331(final Context context2) {
                super(context2);
            }

            @Override // android.view.View
            public void onMeasure(int i, int i2) {
                int measuredHeight;
                AffiliateProgramFragment affiliateProgramFragment = AffiliateProgramFragment.this;
                if (affiliateProgramFragment.isLandscapeMode) {
                    measuredHeight = (affiliateProgramFragment.statusBarHeight + ((BaseFragment) affiliateProgramFragment).actionBar.getMeasuredHeight()) - AndroidUtilities.m1036dp(16.0f);
                } else {
                    int iM1036dp = AndroidUtilities.m1036dp(140.0f);
                    AffiliateProgramFragment affiliateProgramFragment2 = AffiliateProgramFragment.this;
                    int measuredHeight2 = iM1036dp + affiliateProgramFragment2.statusBarHeight;
                    if (affiliateProgramFragment2.backgroundView.getMeasuredHeight() + AndroidUtilities.m1036dp(24.0f) > measuredHeight2) {
                        measuredHeight2 = AffiliateProgramFragment.this.backgroundView.getMeasuredHeight() + AndroidUtilities.m1036dp(24.0f);
                    }
                    measuredHeight = measuredHeight2;
                }
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec((int) (measuredHeight - (((GradientHeaderActivity) AffiliateProgramFragment.this).yOffset * 2.5f)), TLObject.FLAG_30));
            }
        };
        this.emptyLayout = c74331;
        c74331.setBackgroundColor(Theme.getColor(Theme.key_dialogBackgroundGray));
        super.createView(context2);
        FrameLayout frameLayout = new FrameLayout(context2);
        this.aboveTitleView = frameLayout;
        frameLayout.setClickable(true);
        GLIconTextureView gLIconTextureView = new GLIconTextureView(context2, 1, 3);
        this.iconTextureView = gLIconTextureView;
        GLIconRenderer gLIconRenderer = gLIconTextureView.mRenderer;
        gLIconRenderer.colorKey1 = Theme.key_starsGradient1;
        gLIconRenderer.colorKey2 = Theme.key_starsGradient2;
        gLIconRenderer.updateColors();
        this.iconTextureView.setStarParticlesView(this.particlesView);
        this.aboveTitleView.addView(this.iconTextureView, LayoutHelper.createFrame(190, 190.0f, 17, 0.0f, 32.0f, 0.0f, 24.0f));
        configureHeader(LocaleController.getString(C2797R.string.BotAffiliateProgramTitle), LocaleController.getString(C2797R.string.BotAffiliateProgramText), this.aboveTitleView, null);
        LinearLayout linearLayout = new LinearLayout(context2);
        this.buttonLayout = linearLayout;
        linearLayout.setOrientation(1);
        this.buttonLayout.setBackgroundColor(getThemedColor(Theme.key_windowBackgroundWhite));
        View view = new View(context2);
        view.setBackgroundColor(getThemedColor(Theme.key_divider));
        this.buttonLayout.addView(view, LayoutHelper.createLinear(-1.0f, 1.0f / AndroidUtilities.density));
        ButtonWithCounterView round = new ButtonWithCounterView(context2, this.resourceProvider) { // from class: org.telegram.ui.bots.AffiliateProgramFragment.2
            @Override // org.telegram.p035ui.Stories.recorder.ButtonWithCounterView
            public boolean subTextSplitToWords() {
                return false;
            }

            public C74342(final Context context2, Theme.ResourcesProvider resourcesProvider) {
                super(context2, resourcesProvider);
            }
        }.setRound();
        this.button = round;
        round.setText(LocaleController.getString(C2797R.string.AffiliateProgramStart), false);
        this.button.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$createView$4(context2, view2);
            }
        });
        this.buttonLayout.addView(this.button, LayoutHelper.createLinear(-1, 48, 10.0f, 10.0f, 10.0f, 7.0f));
        LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(context2, this.resourceProvider);
        this.buttonSubtext = linksTextView;
        linksTextView.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText2));
        this.buttonSubtext.setLinkTextColor(getThemedColor(Theme.key_chat_messageLinkIn));
        this.buttonSubtext.setTextSize(1, 12.0f);
        this.buttonSubtext.setGravity(17);
        this.buttonLayout.addView(this.buttonSubtext, LayoutHelper.createLinear(-1, -2, 32.0f, 1.0f, 32.0f, 8.0f));
        update(false);
        ((FrameLayout) this.fragmentView).addView(this.buttonLayout, LayoutHelper.createFrame(-1, -2, 87));
        this.listView.setPadding(0, 0, 0, AndroidUtilities.m1036dp(84.0f));
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view2, int i) {
                this.f$0.lambda$createView$5(view2, i);
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setSupportsChangeAnimations(false);
        defaultItemAnimator.setDelayAnimations(false);
        defaultItemAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        defaultItemAnimator.setDurations(350L);
        this.listView.setItemAnimator(defaultItemAnimator);
        return this.fragmentView;
    }

    /* JADX INFO: renamed from: org.telegram.ui.bots.AffiliateProgramFragment$1 */
    public class C74331 extends View {
        public C74331(final Context context2) {
            super(context2);
        }

        @Override // android.view.View
        public void onMeasure(int i, int i2) {
            int measuredHeight;
            AffiliateProgramFragment affiliateProgramFragment = AffiliateProgramFragment.this;
            if (affiliateProgramFragment.isLandscapeMode) {
                measuredHeight = (affiliateProgramFragment.statusBarHeight + ((BaseFragment) affiliateProgramFragment).actionBar.getMeasuredHeight()) - AndroidUtilities.m1036dp(16.0f);
            } else {
                int iM1036dp = AndroidUtilities.m1036dp(140.0f);
                AffiliateProgramFragment affiliateProgramFragment2 = AffiliateProgramFragment.this;
                int measuredHeight2 = iM1036dp + affiliateProgramFragment2.statusBarHeight;
                if (affiliateProgramFragment2.backgroundView.getMeasuredHeight() + AndroidUtilities.m1036dp(24.0f) > measuredHeight2) {
                    measuredHeight2 = AffiliateProgramFragment.this.backgroundView.getMeasuredHeight() + AndroidUtilities.m1036dp(24.0f);
                }
                measuredHeight = measuredHeight2;
            }
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec((int) (measuredHeight - (((GradientHeaderActivity) AffiliateProgramFragment.this).yOffset * 2.5f)), TLObject.FLAG_30));
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.bots.AffiliateProgramFragment$2 */
    public class C74342 extends ButtonWithCounterView {
        @Override // org.telegram.p035ui.Stories.recorder.ButtonWithCounterView
        public boolean subTextSplitToWords() {
            return false;
        }

        public C74342(final Context context2, Theme.ResourcesProvider resourcesProvider) {
            super(context2, resourcesProvider);
        }
    }

    public /* synthetic */ void lambda$createView$4(Context context, View view) {
        if (this.button.isEnabled()) {
            FrameLayout frameLayout = new FrameLayout(context);
            TableView tableView = new TableView(context, this.resourceProvider);
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$2();
                }
            };
            tableView.addRow(LocaleController.getString(C2797R.string.AffiliateProgramCommission), percents(this.program.commission_permille));
            String string = LocaleController.getString(C2797R.string.AffiliateProgramDuration);
            int i = this.program.duration_months;
            tableView.addRow(string, i <= 0 ? LocaleController.getString(C2797R.string.Infinity) : (i < 12 || i % 12 != 0) ? LocaleController.formatPluralString("Months", i, new Object[0]) : LocaleController.formatPluralString("Years", i / 12, new Object[0]));
            frameLayout.addView(tableView, LayoutHelper.createFrame(-1, -2.0f, 119, 24.0f, 0.0f, 24.0f, 0.0f));
            new AlertDialog.Builder(context, this.resourceProvider).setTitle(LocaleController.getString(C2797R.string.AffiliateProgramAlert)).setMessage(LocaleController.getString(this.new_program ? C2797R.string.AffiliateProgramStartAlertText : C2797R.string.AffiliateProgramUpdateAlertText)).setView(frameLayout).setPositiveButton(LocaleController.getString(this.new_program ? C2797R.string.AffiliateProgramStartAlertButton : C2797R.string.AffiliateProgramUpdateAlertButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda5
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i2) {
                    runnable.run();
                }
            }).setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null).show();
        }
    }

    public /* synthetic */ void lambda$createView$2() {
        TL_bots.updateStarRefProgram updatestarrefprogram = new TL_bots.updateStarRefProgram();
        updatestarrefprogram.bot = getMessagesController().getInputUser(this.bot_id);
        TL_payments.starRefProgram starrefprogram = this.program;
        updatestarrefprogram.commission_permille = starrefprogram.commission_permille;
        int i = starrefprogram.duration_months;
        updatestarrefprogram.duration_months = i;
        int i2 = updatestarrefprogram.flags;
        if (i > 0) {
            updatestarrefprogram.flags = i2 | 1;
            starrefprogram.duration_months = i | 1;
        } else {
            updatestarrefprogram.flags = i2 & (-2);
            starrefprogram.duration_months = i & (-2);
        }
        final AlertDialog alertDialog = new AlertDialog(getContext(), 3);
        alertDialog.showDelayed(150L);
        getConnectionsManager().sendRequest(updatestarrefprogram, new RequestDelegate() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda14
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$createView$1(alertDialog, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$createView$1(final AlertDialog alertDialog, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$0(alertDialog, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$createView$0(AlertDialog alertDialog, TLObject tLObject, TLRPC.TL_error tL_error) {
        alertDialog.dismiss();
        if (!(tLObject instanceof TL_payments.starRefProgram)) {
            if (tL_error != null) {
                BulletinFactory.showError(tL_error);
                return;
            }
            return;
        }
        TL_payments.starRefProgram starrefprogram = (TL_payments.starRefProgram) tLObject;
        TLRPC.UserFull userFull = getMessagesController().getUserFull(this.bot_id);
        if (userFull != null) {
            userFull.starref_program = starrefprogram;
            getMessagesStorage().updateUserInfo(userFull, false);
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.userInfoDidLoad, Long.valueOf(this.bot_id), userFull);
        }
        closeToProfile(false);
    }

    public /* synthetic */ void lambda$createView$5(View view, int i) {
        UniversalAdapter universalAdapter = this.adapter;
        if (universalAdapter == null) {
            return;
        }
        int i2 = universalAdapter.getItem(i).f1708id;
        if (i2 == 4) {
            end();
        } else if (i2 == 2) {
            presentFragment(new SuggestedAffiliateProgramsFragment(this.bot_id));
        }
    }

    private void closeToProfile(boolean z) {
        BaseFragment backgroundFragment = null;
        if (getParentLayout() != null && getParentLayout().getFragmentStack() != null) {
            INavigationLayout parentLayout = getParentLayout();
            List<BaseFragment> fragmentStack = parentLayout.getFragmentStack();
            int size = fragmentStack.size() - 1;
            while (true) {
                if (size <= 0) {
                    size = -1;
                    break;
                }
                BaseFragment baseFragment = fragmentStack.get(size);
                if ((baseFragment instanceof ProfileActivity) && ((ProfileActivity) baseFragment).getDialogId() == this.bot_id) {
                    backgroundFragment = baseFragment;
                    break;
                }
                size--;
            }
            if (backgroundFragment != null) {
                for (int size2 = fragmentStack.size() - 1; size2 > size; size2--) {
                    parentLayout.removeFragmentFromStack(fragmentStack.get(size2));
                }
                finishFragment();
            } else {
                finishFragment();
                backgroundFragment = parentLayout.getBackgroundFragment();
            }
        } else {
            finishFragment();
        }
        if (backgroundFragment != null) {
            if (z) {
                BulletinFactory.m1143of(backgroundFragment).createSimpleBulletin(C2797R.raw.linkbroken, LocaleController.getString(C2797R.string.AffiliateProgramEndedTitle), AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.AffiliateProgramEndedText))).show();
            } else {
                BulletinFactory.m1143of(backgroundFragment).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.getString(C2797R.string.AffiliateProgramStartedTitle), LocaleController.getString(C2797R.string.AffiliateProgramStartedText)).show();
            }
        }
    }

    private void update(boolean z) {
        this.button.setText(LocaleController.getString((this.new_program || this.program.end_date != 0) ? C2797R.string.AffiliateProgramStart : C2797R.string.AffiliateProgramUpdate), z);
        AndroidUtilities.cancelRunOnUIThread(this.updateTimerRunnable);
        this.updateTimerRunnable.run();
        this.buttonSubtext.setText(AndroidUtilities.replaceSingleTag(LocaleController.getString((this.new_program || this.program.end_date != 0) ? C2797R.string.AffiliateProgramStartInfo : C2797R.string.AffiliateProgramUpdateInfo), new Runnable() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$update$6();
            }
        }));
        updateEnabled();
        UniversalAdapter universalAdapter = this.adapter;
        if (universalAdapter != null) {
            universalAdapter.update(z);
        }
    }

    public /* synthetic */ void lambda$update$6() {
        Browser.openUrl(getContext(), LocaleController.getString((this.new_program || this.program.end_date != 0) ? C2797R.string.AffiliateProgramUpdateInfoLink : C2797R.string.AffiliateProgramStartInfoLink));
    }

    public /* synthetic */ void lambda$new$7() {
        ButtonWithCounterView buttonWithCounterView = this.button;
        int i = this.program.end_date;
        buttonWithCounterView.setSubText(i == 0 ? null : SelectorUserCell.buildCountDownTime(((long) (i - getConnectionsManager().getCurrentTime())) * 1000), true);
        if (this.program.end_date == 0 || !this.attached) {
            return;
        }
        AndroidUtilities.runOnUIThread(this.updateTimerRunnable, 1000L);
    }

    private void updateEnabled() {
        TL_payments.starRefProgram starrefprogram;
        ButtonWithCounterView buttonWithCounterView = this.button;
        TL_payments.starRefProgram starrefprogram2 = this.program;
        buttonWithCounterView.setEnabled(starrefprogram2.end_date == 0 && !((starrefprogram = this.initialProgram) != null && starrefprogram.commission_permille == starrefprogram2.commission_permille && starrefprogram.duration_months == starrefprogram2.duration_months));
    }

    public class BulletinTextView extends TextView {
        public BulletinTextView(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            canvas.drawCircle(AndroidUtilities.m1036dp(3.5f), AndroidUtilities.m1036dp(11.5f), AndroidUtilities.m1036dp(2.5f), getPaint());
        }
    }

    private void end() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        linearLayout.setPadding(AndroidUtilities.m1036dp(24.0f), 0, AndroidUtilities.m1036dp(24.0f), 0);
        TextView textView = new TextView(getContext());
        textView.setTextSize(1, 16.0f);
        int i = Theme.key_windowBackgroundWhiteBlackText;
        textView.setTextColor(Theme.getColor(i, this.resourceProvider));
        textView.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.AffiliateProgramStopText)));
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 0.0f, 0.0f, 0.0f, 17.0f));
        BulletinTextView bulletinTextView = new BulletinTextView(getContext());
        bulletinTextView.setPadding(AndroidUtilities.m1036dp(15.0f), 0, 0, 0);
        bulletinTextView.setTextSize(1, 16.0f);
        bulletinTextView.setTextColor(Theme.getColor(i, this.resourceProvider));
        bulletinTextView.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.AffiliateProgramStopText1)));
        linearLayout.addView(bulletinTextView, LayoutHelper.createLinear(-1, -2, 0.0f, 0.0f, 0.0f, 17.0f));
        BulletinTextView bulletinTextView2 = new BulletinTextView(getContext());
        bulletinTextView2.setPadding(AndroidUtilities.m1036dp(15.0f), 0, 0, 0);
        bulletinTextView2.setTextSize(1, 16.0f);
        bulletinTextView2.setTextColor(Theme.getColor(i, this.resourceProvider));
        bulletinTextView2.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.AffiliateProgramStopText2)));
        linearLayout.addView(bulletinTextView2, LayoutHelper.createLinear(-1, -2, 0.0f, 0.0f, 0.0f, 17.0f));
        BulletinTextView bulletinTextView3 = new BulletinTextView(getContext());
        bulletinTextView3.setPadding(AndroidUtilities.m1036dp(15.0f), 0, 0, 0);
        bulletinTextView3.setTextSize(1, 16.0f);
        bulletinTextView3.setTextColor(Theme.getColor(i, this.resourceProvider));
        bulletinTextView3.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2797R.string.AffiliateProgramStopText3)));
        linearLayout.addView(bulletinTextView3, LayoutHelper.createLinear(-1, -2, 0.0f, 0.0f, 0.0f, 10.0f));
        new AlertDialog.Builder(getContext(), this.resourceProvider).setTitle(LocaleController.getString(C2797R.string.AffiliateProgramAlert)).setView(linearLayout).setPositiveButton(LocaleController.getString(C2797R.string.AffiliateProgramStopButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda9
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                this.f$0.lambda$end$10(alertDialog, i2);
            }
        }).setNegativeButton(LocaleController.getString(C2797R.string.Cancel), null).makeRed(-1).show();
    }

    public /* synthetic */ void lambda$end$10(AlertDialog alertDialog, int i) {
        TL_bots.updateStarRefProgram updatestarrefprogram = new TL_bots.updateStarRefProgram();
        updatestarrefprogram.bot = getMessagesController().getInputUser(this.bot_id);
        updatestarrefprogram.commission_permille = 0;
        final AlertDialog alertDialog2 = new AlertDialog(getContext(), 3);
        alertDialog2.showDelayed(150L);
        getConnectionsManager().sendRequest(updatestarrefprogram, new RequestDelegate() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda10
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$end$9(alertDialog2, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$end$9(final AlertDialog alertDialog, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$end$8(alertDialog, tLObject, tL_error);
            }
        });
    }

    public /* synthetic */ void lambda$end$8(AlertDialog alertDialog, TLObject tLObject, TLRPC.TL_error tL_error) {
        alertDialog.dismiss();
        if (!(tLObject instanceof TL_payments.starRefProgram)) {
            if (tL_error != null) {
                BulletinFactory.showError(tL_error);
                return;
            }
            return;
        }
        TL_payments.starRefProgram starrefprogram = (TL_payments.starRefProgram) tLObject;
        TLRPC.UserFull userFull = getMessagesController().getUserFull(this.bot_id);
        if (userFull != null) {
            TL_payments.starRefProgram starrefprogram2 = this.program;
            starrefprogram2.flags |= 2;
            starrefprogram2.end_date = getConnectionsManager().getCurrentTime() + (getConnectionsManager().isTestBackend() ? 300 : 86400);
            userFull.starref_program = starrefprogram;
            getMessagesStorage().updateUserInfo(userFull, false);
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.userInfoDidLoad, Long.valueOf(this.bot_id), userFull);
        }
        closeToProfile(true);
    }

    public TL_payments.starRefProgram getDefaultProgram() {
        TL_payments.starRefProgram starrefprogram = new TL_payments.starRefProgram();
        starrefprogram.commission_permille = Utilities.clamp(50, getMessagesController().starrefMaxCommissionPermille, getMessagesController().starrefMinCommissionPermille);
        starrefprogram.duration_months = 1;
        return starrefprogram;
    }

    public void fillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
        if (getContext() == null) {
            return;
        }
        arrayList.add(UItem.asFullyCustom(getHeader(getContext())));
        arrayList.add(FeatureCell.Factory.m1227as(C2797R.drawable.menu_feature_premium, LocaleController.getString(C2797R.string.BotAffiliateProgramFeature1Title), LocaleController.getString(C2797R.string.BotAffiliateProgramFeature1)));
        arrayList.add(FeatureCell.Factory.m1227as(C2797R.drawable.msg_channel, LocaleController.getString(C2797R.string.BotAffiliateProgramFeature2Title), LocaleController.getString(C2797R.string.BotAffiliateProgramFeature2)));
        arrayList.add(FeatureCell.Factory.m1227as(C2797R.drawable.menu_feature_links2, LocaleController.getString(C2797R.string.BotAffiliateProgramFeature3Title), LocaleController.getString(C2797R.string.BotAffiliateProgramFeature3)));
        arrayList.add(UItem.asShadow(1, null));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.AffiliateProgramCommission)));
        UItem uItemAsIntSlideView = UItem.asIntSlideView(1, getMessagesController().starrefMinCommissionPermille, this.program.commission_permille, getMessagesController().starrefMaxCommissionPermille, new Utilities.CallbackReturn() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda11
            @Override // org.telegram.messenger.Utilities.CallbackReturn
            public final Object run(Object obj) {
                Integer num = (Integer) obj;
                return String.format(Locale.US, "%.1f%%", Float.valueOf(num.intValue() / 10.0f));
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda12
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$fillItems$12((Integer) obj);
            }
        });
        TL_payments.starRefProgram starrefprogram = this.initialProgram;
        arrayList.add(uItemAsIntSlideView.setMinSliderValue(starrefprogram == null ? -1 : starrefprogram.commission_permille));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.AffiliateProgramCommissionInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2797R.string.AffiliateProgramDuration)));
        if (this.durationTexts == null) {
            this.durationTexts = new String[this.durationValues.size()];
            for (int i = 0; i < this.durationValues.size(); i++) {
                int iIntValue = this.durationValues.get(i).intValue();
                if (iIntValue == 0) {
                    this.durationTexts[i] = LocaleController.getString(C2797R.string.Infinity);
                } else if (iIntValue < 12 || iIntValue % 12 != 0) {
                    this.durationTexts[i] = LocaleController.formatPluralString("MonthsShort", iIntValue, new Object[0]);
                } else {
                    this.durationTexts[i] = LocaleController.formatPluralString("YearsShort", iIntValue / 12, new Object[0]);
                }
            }
        }
        UItem uItemAsSlideView = UItem.asSlideView(this.durationTexts, this.durationValues.indexOf(Integer.valueOf(this.program.duration_months)), new Utilities.Callback() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda13
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$fillItems$13((Integer) obj);
            }
        });
        TL_payments.starRefProgram starrefprogram2 = this.initialProgram;
        if (starrefprogram2 != null) {
            int i2 = starrefprogram2.duration_months;
            List<Integer> list = this.durationValues;
            if (i2 <= 0) {
                uItemAsSlideView.setMinSliderValue(list.size() - 1);
            } else {
                int size = list.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    if (this.durationValues.get(size).intValue() > 0 && this.durationValues.get(size).intValue() <= this.initialProgram.duration_months) {
                        uItemAsSlideView.setMinSliderValue(size);
                        break;
                    }
                    size--;
                }
            }
        }
        arrayList.add(uItemAsSlideView);
        arrayList.add(UItem.asShadow(LocaleController.getString(C2797R.string.AffiliateProgramDurationInfo)));
        arrayList.add(ColorfulTextCell.Factory.m1226as(2, getThemedColor(Theme.key_color_green), C2797R.drawable.filled_earn_stars, LocaleController.getString(C2797R.string.AffiliateProgramExistingProgramsTitle), LocaleController.getString(C2797R.string.AffiliateProgramExistingProgramsText)));
        arrayList.add(UItem.asShadow(3, null));
        if (!this.new_program && this.program.end_date == 0) {
            arrayList.add(UItem.asButton(4, LocaleController.getString(C2797R.string.AffiliateProgramStop)).red());
            arrayList.add(UItem.asShadow(5, null));
        }
        arrayList.add(UItem.asShadow(6, null));
        arrayList.add(UItem.asShadow(7, null));
    }

    public /* synthetic */ void lambda$fillItems$12(Integer num) {
        this.program.commission_permille = num.intValue();
        updateEnabled();
    }

    public /* synthetic */ void lambda$fillItems$13(Integer num) {
        this.program.duration_months = this.durationValues.get(num.intValue()).intValue();
        updateEnabled();
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity
    public StarParticlesView createParticlesView() {
        return makeParticlesView(getContext(), 75, 1);
    }

    /* JADX INFO: renamed from: org.telegram.ui.bots.AffiliateProgramFragment$3 */
    public class C74353 extends StarParticlesView {
        public C74353(Context context) {
            super(context);
            setClipWithGradient();
        }

        @Override // org.telegram.p035ui.Components.Premium.StarParticlesView
        public void configure() {
            super.configure();
            StarParticlesView.Drawable drawable = this.drawable;
            drawable.useGradient = true;
            drawable.useBlur = false;
            drawable.forceMaxAlpha = true;
            drawable.checkBounds = true;
            drawable.init();
        }

        @Override // org.telegram.p035ui.Components.Premium.StarParticlesView
        public int getStarsRectWidth() {
            return getMeasuredWidth();
        }
    }

    public static StarParticlesView makeParticlesView(Context context, int i, int i2) {
        return new StarParticlesView(context) { // from class: org.telegram.ui.bots.AffiliateProgramFragment.3
            public C74353(Context context2) {
                super(context2);
                setClipWithGradient();
            }

            @Override // org.telegram.p035ui.Components.Premium.StarParticlesView
            public void configure() {
                super.configure();
                StarParticlesView.Drawable drawable = this.drawable;
                drawable.useGradient = true;
                drawable.useBlur = false;
                drawable.forceMaxAlpha = true;
                drawable.checkBounds = true;
                drawable.init();
            }

            @Override // org.telegram.p035ui.Components.Premium.StarParticlesView
            public int getStarsRectWidth() {
                return getMeasuredWidth();
            }
        };
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        this.attached = true;
        this.new_program = true;
        this.program = getDefaultProgram();
        this.initialProgram = null;
        TLRPC.UserFull userFull = getMessagesController().getUserFull(this.bot_id);
        if (userFull != null) {
            this.new_program = false;
            TL_payments.starRefProgram starrefprogram = userFull.starref_program;
            this.program = starrefprogram;
            if (starrefprogram == null) {
                this.new_program = true;
                this.program = getDefaultProgram();
                this.initialProgram = null;
            } else {
                TL_payments.starRefProgram starrefprogram2 = new TL_payments.starRefProgram();
                this.initialProgram = starrefprogram2;
                TL_payments.starRefProgram starrefprogram3 = this.program;
                starrefprogram2.commission_permille = starrefprogram3.commission_permille;
                starrefprogram2.duration_months = starrefprogram3.duration_months;
            }
        } else {
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(this.bot_id));
            if (user != null) {
                getMessagesController().loadFullUser(user, getClassGuid(), true, new Utilities.Callback() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda0
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onFragmentCreate$15((TLRPC.UserFull) obj);
                    }
                });
            }
        }
        return super.onFragmentCreate();
    }

    public /* synthetic */ void lambda$onFragmentCreate$15(final TLRPC.UserFull userFull) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFragmentCreate$14(userFull);
            }
        });
    }

    public /* synthetic */ void lambda$onFragmentCreate$14(TLRPC.UserFull userFull) {
        if (userFull != null) {
            this.new_program = false;
            TL_payments.starRefProgram starrefprogram = userFull.starref_program;
            this.program = starrefprogram;
            if (starrefprogram == null) {
                this.new_program = true;
                this.program = getDefaultProgram();
                this.initialProgram = null;
            } else {
                TL_payments.starRefProgram starrefprogram2 = new TL_payments.starRefProgram();
                this.initialProgram = starrefprogram2;
                TL_payments.starRefProgram starrefprogram3 = this.program;
                starrefprogram2.commission_permille = starrefprogram3.commission_permille;
                starrefprogram2.duration_months = starrefprogram3.duration_months;
            }
        }
        update(true);
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        this.attached = false;
        AndroidUtilities.cancelRunOnUIThread(this.updateTimerRunnable);
        super.onFragmentDestroy();
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity
    public View getHeader(Context context) {
        return super.getHeader(context);
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity, org.telegram.p035ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        GLIconTextureView gLIconTextureView = this.iconTextureView;
        if (gLIconTextureView != null) {
            gLIconTextureView.setPaused(false);
            this.iconTextureView.setDialogVisible(false);
        }
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity, org.telegram.p035ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        GLIconTextureView gLIconTextureView = this.iconTextureView;
        if (gLIconTextureView != null) {
            gLIconTextureView.setPaused(true);
            this.iconTextureView.setDialogVisible(true);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.bots.AffiliateProgramFragment$4 */
    public class C74364 extends UniversalAdapter {
        public C74364(RecyclerListView recyclerListView, Context context, int i, int i2, boolean z, Utilities.Callback2 callback2, Theme.ResourcesProvider resourcesProvider) {
            super(recyclerListView, context, i, i2, z, callback2, resourcesProvider);
        }

        @Override // org.telegram.p035ui.Components.UniversalAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i == 42) {
                HeaderCell headerCell = new HeaderCell(AffiliateProgramFragment.this.getContext(), Theme.key_windowBackgroundWhiteBlueHeader, 21, 0, false, ((BaseFragment) AffiliateProgramFragment.this).resourceProvider);
                headerCell.setHeight(25);
                return new RecyclerListView.Holder(headerCell);
            }
            return super.onCreateViewHolder(viewGroup, i);
        }
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity
    public RecyclerView.Adapter<?> createAdapter() {
        C74364 c74364 = new UniversalAdapter(this.listView, getContext(), this.currentAccount, this.classGuid, true, new Utilities.Callback2() { // from class: org.telegram.ui.bots.AffiliateProgramFragment$$ExternalSyntheticLambda6
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, getResourceProvider()) { // from class: org.telegram.ui.bots.AffiliateProgramFragment.4
            public C74364(RecyclerListView recyclerListView, Context context, int i, int i2, boolean z, Utilities.Callback2 callback2, Theme.ResourcesProvider resourcesProvider) {
                super(recyclerListView, context, i, i2, z, callback2, resourcesProvider);
            }

            @Override // org.telegram.p035ui.Components.UniversalAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                if (i == 42) {
                    HeaderCell headerCell = new HeaderCell(AffiliateProgramFragment.this.getContext(), Theme.key_windowBackgroundWhiteBlueHeader, 21, 0, false, ((BaseFragment) AffiliateProgramFragment.this).resourceProvider);
                    headerCell.setHeight(25);
                    return new RecyclerListView.Holder(headerCell);
                }
                return super.onCreateViewHolder(viewGroup, i);
            }
        };
        this.adapter = c74364;
        return c74364;
    }

    public static class FeatureCell extends FrameLayout {
        private ImageView imageView;
        private final Theme.ResourcesProvider resourcesProvider;
        private LinearLayout textLayout;
        private TextView textView;
        private TextView titleView;

        public FeatureCell(Context context, Theme.ResourcesProvider resourcesProvider) {
            this(context, false, resourcesProvider);
        }

        public FeatureCell(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.resourcesProvider = resourcesProvider;
            ImageView imageView = new ImageView(context);
            this.imageView = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            ImageView imageView2 = this.imageView;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            imageView2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i, resourcesProvider), PorterDuff.Mode.SRC_IN));
            addView(this.imageView, LayoutHelper.createFrame(24, 24.0f, 51, 20.0f, 11.46f, 0.0f, 0.0f));
            LinearLayout linearLayout = new LinearLayout(context);
            this.textLayout = linearLayout;
            linearLayout.setOrientation(1);
            addView(this.textLayout, LayoutHelper.createFrame(-1, -2.0f, 23, 64.0f, z ? 2.0f : 9.8f, 24.0f, z ? 4.0f : 9.8f));
            TextView textView = new TextView(context);
            this.titleView = textView;
            textView.setTextColor(Theme.getColor(i, resourcesProvider));
            this.titleView.setTypeface(AndroidUtilities.bold());
            this.titleView.setTextSize(1, 14.0f);
            this.textLayout.addView(this.titleView, LayoutHelper.createLinear(-1, -2, 55, 0, 0, 0, 1));
            TextView textView2 = new TextView(context);
            this.textView = textView2;
            textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2, resourcesProvider));
            this.textView.setTextSize(1, 14.0f);
            this.textLayout.addView(this.textView, LayoutHelper.createLinear(-1, -2, 55, 0, 0, 0, 0));
        }

        public void set(int i, CharSequence charSequence, CharSequence charSequence2) {
            this.imageView.setImageResource(i);
            this.titleView.setText(charSequence);
            this.textView.setText(charSequence2);
        }

        public void setText(CharSequence charSequence) {
            this.textView.setText(charSequence);
        }

        public static class Factory extends UItem.UItemFactory<FeatureCell> {
            @Override // org.telegram.ui.Components.UItem.UItemFactory
            /* JADX INFO: renamed from: isClickable */
            public boolean getIsClickableValue() {
                return false;
            }

            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public FeatureCell createView(Context context, RecyclerListView recyclerListView, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new FeatureCell(context, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((FeatureCell) view).set(uItem.iconResId, uItem.text, uItem.subtext);
            }

            /* JADX INFO: renamed from: as */
            public static UItem m1227as(int i, CharSequence charSequence, CharSequence charSequence2) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.iconResId = i;
                uItemOfFactory.text = charSequence;
                uItemOfFactory.subtext = charSequence2;
                return uItemOfFactory;
            }
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public int getNavigationBarColor() {
        return getThemedColor(Theme.key_windowBackgroundWhite);
    }

    public static class ColorfulTextCell extends FrameLayout {
        private final ImageView arrowView;
        private final ImageView imageView;
        private final FrameLayout.LayoutParams imageViewLayoutParams;
        private final TextView percentView;
        private final Theme.ResourcesProvider resourcesProvider;
        private final LinearLayout textLayout;
        private final FrameLayout.LayoutParams textLayoutLayoutParams;
        private final TextView textView;
        private final TextView titleView;

        public ColorfulTextCell(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.resourcesProvider = resourcesProvider;
            ImageView imageView = new ImageView(context);
            this.imageView = imageView;
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            imageView.setColorFilter(new PorterDuffColorFilter(-1, mode));
            ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
            imageView.setScaleType(scaleType);
            FrameLayout.LayoutParams layoutParamsCreateFrame = LayoutHelper.createFrame(28, 28.0f, 51, 17.0f, 14.33f, 0.0f, 0.0f);
            this.imageViewLayoutParams = layoutParamsCreateFrame;
            addView(imageView, layoutParamsCreateFrame);
            LinearLayout linearLayout = new LinearLayout(context);
            this.textLayout = linearLayout;
            linearLayout.setOrientation(1);
            FrameLayout.LayoutParams layoutParamsCreateFrame2 = LayoutHelper.createFrame(-1, -2.0f, 55, 62.0f, 10.0f, 40.0f, 8.66f);
            this.textLayoutLayoutParams = layoutParamsCreateFrame2;
            addView(linearLayout, layoutParamsCreateFrame2);
            TextView textView = new TextView(context);
            this.titleView = textView;
            textView.setTypeface(AndroidUtilities.bold());
            textView.setTextSize(1, 15.0f);
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
            linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 55, 0, 0, 0, 0));
            TextView textView2 = new TextView(context);
            this.textView = textView2;
            textView2.setTextSize(1, 14.0f);
            textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2, resourcesProvider));
            linearLayout.addView(textView2, LayoutHelper.createLinear(-1, -2, 55, 0, 3, 0, 0));
            ImageView imageView2 = new ImageView(context);
            this.arrowView = imageView2;
            imageView2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_switchTrack, resourcesProvider), mode));
            imageView2.setImageResource(C2797R.drawable.msg_arrowright);
            imageView2.setScaleType(scaleType);
            addView(imageView2, LayoutHelper.createFrame(24, 24.0f, 21, 0.0f, 0.0f, 10.0f, 0.0f));
            boolean zIsCurrentThemeMonet = Theme.isCurrentThemeMonet();
            int color = Theme.getColor(Theme.key_chats_actionIcon);
            int color2 = Theme.getColor(Theme.key_chats_actionBackground);
            TextView textView3 = new TextView(context);
            this.percentView = textView3;
            textView3.setTextColor(zIsCurrentThemeMonet ? color : -1);
            textView3.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(4.0f), zIsCurrentThemeMonet ? color2 : Theme.getColor(Theme.key_color_green, resourcesProvider)));
            textView3.setTextSize(1, 13.0f);
            textView3.setTypeface(AndroidUtilities.bold());
            textView3.setPadding(AndroidUtilities.m1036dp(5.0f), 0, AndroidUtilities.m1036dp(4.0f), 0);
            textView3.setGravity(17);
            textView3.setVisibility(8);
            addView(textView3, LayoutHelper.createFrame(-2, 18.0f, 21, 0.0f, 0.0f, 35.33f, 0.0f));
        }

        public void set(int i, int i2, CharSequence charSequence, CharSequence charSequence2) {
            this.imageView.setImageResource(i2);
            boolean zIsCurrentThemeMonet = Theme.isCurrentThemeMonet();
            int color = Theme.getColor(Theme.key_chats_actionIcon);
            this.imageView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(9.0f), i));
            ImageView imageView = this.imageView;
            if (!zIsCurrentThemeMonet) {
                color = -1;
            }
            imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            this.titleView.setText(charSequence);
            boolean zIsEmpty = TextUtils.isEmpty(charSequence2);
            FrameLayout.LayoutParams layoutParams = this.imageViewLayoutParams;
            if (zIsEmpty) {
                layoutParams.topMargin = AndroidUtilities.m1036dp(10.0f);
                this.imageViewLayoutParams.bottomMargin = AndroidUtilities.m1036dp(10.0f);
                this.titleView.setTypeface(null);
                this.titleView.setTextSize(1, 16.0f);
                FrameLayout.LayoutParams layoutParams2 = this.textLayoutLayoutParams;
                layoutParams2.topMargin = 0;
                layoutParams2.bottomMargin = 0;
                layoutParams2.gravity = 23;
                this.textView.setVisibility(8);
                return;
            }
            layoutParams.topMargin = AndroidUtilities.m1036dp(14.33f);
            this.imageViewLayoutParams.bottomMargin = AndroidUtilities.m1036dp(10.0f);
            this.titleView.setTypeface(AndroidUtilities.bold());
            this.titleView.setTextSize(1, 15.0f);
            this.textLayoutLayoutParams.topMargin = AndroidUtilities.m1036dp(10.0f);
            this.textLayoutLayoutParams.bottomMargin = AndroidUtilities.m1036dp(8.66f);
            this.textLayoutLayoutParams.gravity = 55;
            this.textView.setText(charSequence2);
            this.textView.setVisibility(0);
        }

        public void setPercent(CharSequence charSequence) {
            boolean zIsEmpty = TextUtils.isEmpty(charSequence);
            TextView textView = this.percentView;
            if (zIsEmpty) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                this.percentView.setText(charSequence);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), i2);
        }

        public static class Factory extends UItem.UItemFactory<ColorfulTextCell> {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public ColorfulTextCell createView(Context context, RecyclerListView recyclerListView, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new ColorfulTextCell(context, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((ColorfulTextCell) view).set(uItem.intValue, uItem.iconResId, uItem.text, uItem.subtext);
            }

            /* JADX INFO: renamed from: as */
            public static UItem m1226as(int i, int i2, int i3, CharSequence charSequence, CharSequence charSequence2) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.f1708id = i;
                uItemOfFactory.intValue = i2;
                uItemOfFactory.iconResId = i3;
                uItemOfFactory.text = charSequence;
                uItemOfFactory.subtext = charSequence2;
                return uItemOfFactory;
            }
        }
    }

    public static CharSequence percents(int i) {
        float f = i / 10.0f;
        if (((int) f) == f) {
            return String.format(Locale.US, "%d%%", Integer.valueOf(i / 10));
        }
        return String.format(Locale.US, "%.1f%%", Float.valueOf(f));
    }

    @Override // org.telegram.p035ui.GradientHeaderActivity, org.telegram.p035ui.ActionBar.BaseFragment
    public void onInsets(int i, int i2, int i3, int i4) {
        this.listView.setPadding(0, 0, 0, AndroidUtilities.m1036dp(84.0f) + i4);
        this.listView.setClipToPadding(false);
        this.buttonLayout.setPadding(0, 0, 0, i4);
    }
}
