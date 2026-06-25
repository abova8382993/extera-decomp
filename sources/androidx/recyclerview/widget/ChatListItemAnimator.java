package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.BotHelpCell;
import org.telegram.p035ui.Cells.ChatActionCell;
import org.telegram.p035ui.Cells.ChatMessageCell;
import org.telegram.p035ui.Cells.UserInfoCell;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.ChatGreetingsView;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.ThanosEffect;
import org.telegram.p035ui.TextMessageEnterTransition;
import org.telegram.p035ui.VoiceMessageEnterTransition;

/* JADX INFO: loaded from: classes.dex */
public abstract class ChatListItemAnimator extends DefaultItemAnimator {
    public static final Interpolator DEFAULT_INTERPOLATOR = new CubicBezierInterpolator(0.19919472913616398d, 0.010644531250000006d, 0.27920937042459737d, 0.91025390625d);
    private final ChatActivity activity;
    long alphaEnterDelay;
    private ChatGreetingsView chatGreetingsView;
    private Utilities.Callback0Return<ThanosEffect> getThanosEffectContainer;
    private RecyclerView.ViewHolder greetingsSticker;
    private final RecyclerListView recyclerListView;
    private final Theme.ResourcesProvider resourcesProvider;
    private boolean reversePositions;
    private boolean shouldAnimateEnterFromBottom;
    private Utilities.Callback0Return<Boolean> supportsThanosEffectContainer;
    private HashMap<Integer, MessageObject.GroupedMessages> willRemovedGroup = new HashMap<>();
    private ArrayList<MessageObject.GroupedMessages> willChangedGroups = new ArrayList<>();
    HashMap<RecyclerView.ViewHolder, Animator> animators = new HashMap<>();
    ArrayList<View> thanosViews = new ArrayList<>();
    ArrayList<Runnable> runOnAnimationsEnd = new ArrayList<>();
    HashMap<Long, Long> groupIdToEnterDelay = new HashMap<>();
    private final ArrayList<RecyclerView.ViewHolder> toBeSnapped = new ArrayList<>();

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public long getChangeDuration() {
        return 250L;
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator
    public long getMoveAnimationDelay() {
        return 0L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public long getMoveDuration() {
        return 250L;
    }

    public void onAnimationStart() {
    }

    public ChatListItemAnimator(ChatActivity chatActivity, RecyclerListView recyclerListView, Theme.ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
        this.activity = chatActivity;
        this.recyclerListView = recyclerListView;
        this.translationInterpolator = DEFAULT_INTERPOLATOR;
        this.alwaysCreateMoveAnimationIfPossible = true;
        setSupportsChangeAnimations(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0067  */
    @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void runPendingAnimations() {
        /*
            r6 = this;
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r0 = r6.mPendingRemovals
            boolean r0 = r0.isEmpty()
            java.util.ArrayList<androidx.recyclerview.widget.DefaultItemAnimator$MoveInfo> r1 = r6.mPendingMoves
            boolean r1 = r1.isEmpty()
            java.util.ArrayList<androidx.recyclerview.widget.DefaultItemAnimator$ChangeInfo> r2 = r6.mPendingChanges
            boolean r2 = r2.isEmpty()
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r3 = r6.mPendingAdditions
            boolean r3 = r3.isEmpty()
            if (r0 == 0) goto L21
            if (r1 == 0) goto L21
            if (r3 == 0) goto L21
            if (r2 == 0) goto L21
            return
        L21:
            boolean r0 = r6.shouldAnimateEnterFromBottom
            r1 = 0
            if (r0 == 0) goto L6c
            r0 = r1
            r2 = r0
        L28:
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r3 = r6.mPendingAdditions
            int r3 = r3.size()
            if (r0 >= r3) goto L6b
            boolean r3 = r6.reversePositions
            r4 = 1
            if (r3 == 0) goto L59
            org.telegram.ui.Components.RecyclerListView r3 = r6.recyclerListView
            androidx.recyclerview.widget.RecyclerView$Adapter r3 = r3.getAdapter()
            if (r3 != 0) goto L3f
            r3 = r1
            goto L49
        L3f:
            org.telegram.ui.Components.RecyclerListView r3 = r6.recyclerListView
            androidx.recyclerview.widget.RecyclerView$Adapter r3 = r3.getAdapter()
            int r3 = r3.getItemCount()
        L49:
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r5 = r6.mPendingAdditions
            java.lang.Object r5 = r5.get(r0)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r5 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r5
            int r5 = r5.getLayoutPosition()
            int r3 = r3 - r4
            if (r5 != r3) goto L68
            goto L67
        L59:
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r3 = r6.mPendingAdditions
            java.lang.Object r3 = r3.get(r0)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r3 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r3
            int r3 = r3.getLayoutPosition()
            if (r3 != 0) goto L68
        L67:
            r2 = r4
        L68:
            int r0 = r0 + 1
            goto L28
        L6b:
            r1 = r2
        L6c:
            r6.onAnimationStart()
            if (r1 == 0) goto L75
            r6.runMessageEnterTransition()
            goto L78
        L75:
            r6.runAlphaEnterTransition()
        L78:
            r0 = 2
            float[] r0 = new float[r0]
            r0 = {x009a: FILL_ARRAY_DATA , data: [0, 1065353216} // fill-array
            android.animation.ValueAnimator r0 = android.animation.ValueAnimator.ofFloat(r0)
            androidx.recyclerview.widget.ChatListItemAnimator$$ExternalSyntheticLambda0 r1 = new androidx.recyclerview.widget.ChatListItemAnimator$$ExternalSyntheticLambda0
            r1.<init>()
            r0.addUpdateListener(r1)
            long r1 = r6.getRemoveDuration()
            long r3 = r6.getMoveDuration()
            long r1 = r1 + r3
            r0.setDuration(r1)
            r0.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ChatListItemAnimator.runPendingAnimations():void");
    }

    public /* synthetic */ void lambda$runPendingAnimations$0(ValueAnimator valueAnimator) {
        ChatActivity chatActivity = this.activity;
        if (chatActivity != null) {
            chatActivity.onListItemAnimatorTick();
        } else {
            this.recyclerListView.invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:163:0x00ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void runAlphaEnterTransition() {
        /*
            Method dump skipped, instruction units count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ChatListItemAnimator.runAlphaEnterTransition():void");
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$1 */
    public class RunnableC07241 implements Runnable {
        final /* synthetic */ boolean val$finalThanos;
        final /* synthetic */ ArrayList val$moves;

        public RunnableC07241(ArrayList arrayList, boolean z) {
            arrayList = arrayList;
            z = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            ArrayList arrayList = arrayList;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                DefaultItemAnimator.MoveInfo moveInfo = (DefaultItemAnimator.MoveInfo) obj;
                ChatListItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo, z);
            }
            arrayList.clear();
            ChatListItemAnimator.this.mMovesList.remove(arrayList);
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$2 */
    /* JADX INFO: loaded from: classes4.dex */
    public class RunnableC07282 implements Runnable {
        final /* synthetic */ ArrayList val$changes;

        public RunnableC07282(ArrayList arrayList) {
            arrayList = arrayList;
        }

        @Override // java.lang.Runnable
        public void run() {
            ArrayList arrayList = arrayList;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ChatListItemAnimator.this.animateChangeImpl((DefaultItemAnimator.ChangeInfo) obj);
            }
            arrayList.clear();
            ChatListItemAnimator.this.mChangesList.remove(arrayList);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$ZhmpZs-u8pZI6IPqdJ0RRLbxS2o */
    public static /* synthetic */ int m2047$r8$lambda$ZhmpZsu8pZI6IPqdJ0RRLbxS2o(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        return viewHolder2.itemView.getTop() - viewHolder.itemView.getTop();
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void runMessageEnterTransition() {
        /*
            Method dump skipped, instruction units count: 202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ChatListItemAnimator.runMessageEnterTransition():void");
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public boolean animateAppearance(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        int height;
        boolean zAnimateAppearance = super.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
        if (zAnimateAppearance && this.shouldAnimateEnterFromBottom) {
            boolean z = false;
            for (int i = 0; i < this.mPendingAdditions.size(); i++) {
                if (this.mPendingAdditions.get(i).getLayoutPosition() == 0) {
                    z = true;
                }
            }
            if (z) {
                height = 0;
                for (int i2 = 0; i2 < this.mPendingAdditions.size(); i2++) {
                    height += this.mPendingAdditions.get(i2).itemView.getHeight();
                }
            } else {
                height = 0;
            }
            for (int i3 = 0; i3 < this.mPendingAdditions.size(); i3++) {
                this.mPendingAdditions.get(i3).itemView.setTranslationY(height);
            }
        }
        return zAnimateAppearance;
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
        boolean z = this.shouldAnimateEnterFromBottom;
        View view = viewHolder.itemView;
        if (!z) {
            view.setScaleX(0.9f);
            viewHolder.itemView.setScaleY(0.9f);
        } else if (view instanceof ChatMessageCell) {
            ((ChatMessageCell) view).getTransitionParams().messageEntering = true;
        }
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    public void animateAddImpl(RecyclerView.ViewHolder viewHolder, int i) {
        View view = viewHolder.itemView;
        ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        this.mAddAnimations.add(viewHolder);
        view.setTranslationY(i);
        viewHolder.itemView.setScaleX(1.0f);
        viewHolder.itemView.setScaleY(1.0f);
        View view2 = viewHolder.itemView;
        ChatMessageCell chatMessageCell = view2 instanceof ChatMessageCell ? (ChatMessageCell) view2 : null;
        if (chatMessageCell == null || !chatMessageCell.getTransitionParams().ignoreAlpha) {
            viewHolder.itemView.setAlpha(1.0f);
        }
        ChatActivity chatActivity = this.activity;
        if (chatActivity != null && chatMessageCell != null && chatActivity.animatingMessageObjects.contains(chatMessageCell.getMessageObject())) {
            this.activity.animatingMessageObjects.remove(chatMessageCell.getMessageObject());
            if (this.activity.getChatActivityEnterView().canShowMessageTransition()) {
                if (chatMessageCell.getMessageObject().isVoice()) {
                    if (Math.abs(view.getTranslationY()) < view.getMeasuredHeight() * 3.0f) {
                        new VoiceMessageEnterTransition(chatMessageCell, this.activity.getChatActivityEnterView(), this.recyclerListView, this.activity.messageEnterTransitionContainer, this.resourcesProvider).start();
                    }
                } else if (SharedConfig.getDevicePerformanceClass() != 0 && Math.abs(view.getTranslationY()) < this.recyclerListView.getMeasuredHeight()) {
                    ChatActivity chatActivity2 = this.activity;
                    new TextMessageEnterTransition(chatMessageCell, chatActivity2, this.recyclerListView, chatActivity2.messageEnterTransitionContainer, this.resourcesProvider).start();
                }
                this.activity.getChatActivityEnterView().startMessageTransition();
            }
        }
        viewPropertyAnimatorAnimate.translationY(0.0f).setDuration(getMoveDuration()).setInterpolator(this.translationInterpolator).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.ChatListItemAnimator.3
            final /* synthetic */ ViewPropertyAnimator val$animation;
            final /* synthetic */ RecyclerView.ViewHolder val$holder;
            final /* synthetic */ View val$view;

            public C07293(RecyclerView.ViewHolder viewHolder2, View view3, ViewPropertyAnimator viewPropertyAnimatorAnimate2) {
                viewHolder = viewHolder2;
                view = view3;
                viewPropertyAnimator = viewPropertyAnimatorAnimate2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                ChatListItemAnimator.this.dispatchAddStarting(viewHolder);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                view.setTranslationY(0.0f);
                View view3 = view;
                if (view3 instanceof ChatMessageCell) {
                    ((ChatMessageCell) view3).getTransitionParams().messageEntering = false;
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                View view3 = view;
                if (view3 instanceof ChatMessageCell) {
                    ((ChatMessageCell) view3).getTransitionParams().messageEntering = false;
                }
                viewPropertyAnimator.setListener(null);
                if (ChatListItemAnimator.this.mAddAnimations.remove(viewHolder)) {
                    ChatListItemAnimator.this.dispatchAddFinished(viewHolder);
                    ChatListItemAnimator.this.dispatchFinishedWhenDone();
                }
            }
        }).start();
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$3 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C07293 extends AnimatorListenerAdapter {
        final /* synthetic */ ViewPropertyAnimator val$animation;
        final /* synthetic */ RecyclerView.ViewHolder val$holder;
        final /* synthetic */ View val$view;

        public C07293(RecyclerView.ViewHolder viewHolder2, View view3, ViewPropertyAnimator viewPropertyAnimatorAnimate2) {
            viewHolder = viewHolder2;
            view = view3;
            viewPropertyAnimator = viewPropertyAnimatorAnimate2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ChatListItemAnimator.this.dispatchAddStarting(viewHolder);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            view.setTranslationY(0.0f);
            View view3 = view;
            if (view3 instanceof ChatMessageCell) {
                ((ChatMessageCell) view3).getTransitionParams().messageEntering = false;
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            View view3 = view;
            if (view3 instanceof ChatMessageCell) {
                ((ChatMessageCell) view3).getTransitionParams().messageEntering = false;
            }
            viewPropertyAnimator.setListener(null);
            if (ChatListItemAnimator.this.mAddAnimations.remove(viewHolder)) {
                ChatListItemAnimator.this.dispatchAddFinished(viewHolder);
                ChatListItemAnimator.this.dispatchFinishedWhenDone();
            }
        }
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateRemove(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("animate remove");
        }
        boolean zAnimateRemove = super.animateRemove(viewHolder, itemHolderInfo);
        if (zAnimateRemove && itemHolderInfo != null) {
            int i = itemHolderInfo.top;
            int top = viewHolder.itemView.getTop();
            int left = viewHolder.itemView.getLeft() - itemHolderInfo.left;
            if (top - i != 0) {
                viewHolder.itemView.setTranslationY(-r1);
            }
            View view = viewHolder.itemView;
            if (view instanceof ChatMessageCell) {
                ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                if (left != 0) {
                    chatMessageCell.setAnimationOffsetX(-left);
                }
                if (itemHolderInfo instanceof ItemHolderInfoExtended) {
                    ItemHolderInfoExtended itemHolderInfoExtended = (ItemHolderInfoExtended) itemHolderInfo;
                    chatMessageCell.setImageCoords(itemHolderInfoExtended.imageX, itemHolderInfoExtended.imageY, itemHolderInfoExtended.imageWidth, itemHolderInfoExtended.imageHeight);
                    return zAnimateRemove;
                }
            } else if (left != 0) {
                view.setTranslationX(-left);
            }
        }
        return zAnimateRemove;
    }

    /* JADX WARN: Removed duplicated region for block: B:381:0x0339  */
    @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.SimpleItemAnimator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean animateMove(androidx.recyclerview.widget.RecyclerView.ViewHolder r23, androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo r24, int r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instruction units count: 1288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ChatListItemAnimator.animateMove(androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo, int, int, int, int):boolean");
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator
    public void animateMoveImpl(RecyclerView.ViewHolder viewHolder, DefaultItemAnimator.MoveInfo moveInfo) {
        animateMoveImpl(viewHolder, moveInfo, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0061  */
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void animateMoveImpl(final androidx.recyclerview.widget.RecyclerView.ViewHolder r22, androidx.recyclerview.widget.DefaultItemAnimator.MoveInfo r23, boolean r24) {
        /*
            Method dump skipped, instruction units count: 712
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ChatListItemAnimator.animateMoveImpl(androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.DefaultItemAnimator$MoveInfo, boolean):void");
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$4 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C07304 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ float val$animateFrom;
        final /* synthetic */ BotHelpCell val$botCell;

        public C07304(BotHelpCell botHelpCell, float f) {
            botHelpCell = botHelpCell;
            f = f;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            float measuredHeight = ((((ChatListItemAnimator.this.recyclerListView.getMeasuredHeight() - ChatListItemAnimator.this.activity.getChatListViewPadding()) - ChatListItemAnimator.this.activity.blurredViewBottomOffset) / 2.0f) - (botHelpCell.getMeasuredHeight() / 2.0f)) + ChatListItemAnimator.this.activity.getChatListViewPadding();
            botHelpCell.setTranslationY((f * (1.0f - fFloatValue)) + ((((float) botHelpCell.getTop()) > measuredHeight ? measuredHeight - botHelpCell.getTop() : 0.0f) * fFloatValue));
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$5 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C07315 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ float val$animateFrom;
        final /* synthetic */ UserInfoCell val$cell;

        public C07315(UserInfoCell userInfoCell, float f) {
            userInfoCell = userInfoCell;
            f = f;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            float measuredHeight = ((((ChatListItemAnimator.this.recyclerListView.getMeasuredHeight() - ChatListItemAnimator.this.activity.getChatListViewPadding()) - ChatListItemAnimator.this.activity.blurredViewBottomOffset) / 2.0f) - (userInfoCell.getMeasuredHeight() / 2.0f)) + ChatListItemAnimator.this.activity.getChatListViewPadding();
            userInfoCell.setTranslationY((f * (1.0f - fFloatValue)) + ((((float) userInfoCell.getTop()) > measuredHeight ? measuredHeight - userInfoCell.getTop() : 0.0f) * fFloatValue));
        }
    }

    public static /* synthetic */ void $r8$lambda$q2tWCM9_t3HWHSWL2BYl63l1nLY(MoveInfoExtended moveInfoExtended, ChatMessageCell.TransitionParams transitionParams, boolean z, float f, float f2, ChatMessageCell chatMessageCell, int[] iArr, RecyclerView.ViewHolder viewHolder, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f3 = 1.0f - fFloatValue;
        float f4 = (moveInfoExtended.imageX * f3) + (transitionParams.animateToImageX * fFloatValue);
        float f5 = (moveInfoExtended.imageY * f3) + (transitionParams.animateToImageY * fFloatValue);
        float f6 = (moveInfoExtended.imageWidth * f3) + (transitionParams.animateToImageW * fFloatValue);
        float f7 = (moveInfoExtended.imageHeight * f3) + (transitionParams.animateToImageH * fFloatValue);
        if (z) {
            float f8 = (f * f3) + (f2 * fFloatValue);
            transitionParams.captionEnterProgress = f8;
            if (chatMessageCell.getCurrentMessagesGroup() != null) {
                chatMessageCell.getCurrentMessagesGroup().transitionParams.captionEnterProgress = f8;
            }
        }
        if (transitionParams.animateRadius) {
            int[] iArr2 = transitionParams.animateToRadius;
            chatMessageCell.getPhotoImage().setRoundRadius((int) ((iArr[0] * f3) + (iArr2[0] * fFloatValue)), (int) ((iArr[1] * f3) + (iArr2[1] * fFloatValue)), (int) ((iArr[2] * f3) + (iArr2[2] * fFloatValue)), (int) ((iArr[3] * f3) + (iArr2[3] * fFloatValue)));
        }
        chatMessageCell.setImageCoords(f4, f5, f6, f7);
        viewHolder.itemView.invalidate();
    }

    /* JADX INFO: renamed from: $r8$lambda$qA9X7o-vu6MzHoXlAlzncqZZGW4 */
    public static /* synthetic */ void m2050$r8$lambda$qA9X7ovu6MzHoXlAlzncqZZGW4(MoveInfoExtended moveInfoExtended, ChatMessageCell.TransitionParams transitionParams, ChatMessageCell chatMessageCell, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        boolean z = moveInfoExtended.animateBackgroundOnly;
        int i = moveInfoExtended.deltaLeft;
        if (z) {
            transitionParams.deltaLeft = (-i) * fFloatValue;
            transitionParams.deltaRight = (-moveInfoExtended.deltaRight) * fFloatValue;
            transitionParams.deltaTop = (-moveInfoExtended.deltaTop) * fFloatValue;
            transitionParams.deltaBottom = (-moveInfoExtended.deltaBottom) * fFloatValue;
        } else {
            transitionParams.deltaLeft = ((-i) * fFloatValue) - chatMessageCell.getAnimationOffsetX();
            transitionParams.deltaRight = ((-moveInfoExtended.deltaRight) * fFloatValue) - chatMessageCell.getAnimationOffsetX();
            transitionParams.deltaTop = ((-moveInfoExtended.deltaTop) * fFloatValue) - chatMessageCell.getTranslationY();
            transitionParams.deltaBottom = ((-moveInfoExtended.deltaBottom) * fFloatValue) - chatMessageCell.getTranslationY();
        }
        chatMessageCell.invalidate();
    }

    public static /* synthetic */ void $r8$lambda$YYecdgGZNsi0ivRcwpVm6roqNgs(MessageObject.GroupedMessages.TransitionParams transitionParams, MoveInfoExtended moveInfoExtended, boolean z, float f, float f2, RecyclerListView recyclerListView, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        transitionParams.offsetTop = moveInfoExtended.groupOffsetTop * fFloatValue;
        transitionParams.offsetBottom = moveInfoExtended.groupOffsetBottom * fFloatValue;
        transitionParams.offsetLeft = moveInfoExtended.groupOffsetLeft * fFloatValue;
        transitionParams.offsetRight = moveInfoExtended.groupOffsetRight * fFloatValue;
        if (z) {
            transitionParams.captionEnterProgress = (f * fFloatValue) + (f2 * (1.0f - fFloatValue));
        }
        if (recyclerListView != null) {
            recyclerListView.invalidate();
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$6 */
    public class C07326 extends AnimatorListenerAdapter {
        final /* synthetic */ MessageObject.GroupedMessages.TransitionParams val$groupTransitionParams;

        public C07326(MessageObject.GroupedMessages.TransitionParams transitionParams) {
            transitionParams = transitionParams;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            MessageObject.GroupedMessages.TransitionParams transitionParams = transitionParams;
            transitionParams.backgroundChangeBounds = false;
            transitionParams.drawBackgroundForDeletedItems = false;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$FBXPwnxBw-HzEwANDozv1cOm6AU */
    public static /* synthetic */ void m2046$r8$lambda$FBXPwnxBwHzEwANDozv1cOm6AU(ChatMessageCell.TransitionParams transitionParams, ChatMessageCell chatMessageCell, ValueAnimator valueAnimator) {
        transitionParams.changePinnedBottomProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        chatMessageCell.invalidate();
    }

    /* JADX INFO: renamed from: $r8$lambda$aRrFzpT1ocseHn-5PxS5FEA7rNs */
    public static /* synthetic */ void m2049$r8$lambda$aRrFzpT1ocseHn5PxS5FEA7rNs(ChatMessageCell.TransitionParams transitionParams, ChatMessageCell chatMessageCell, ValueAnimator valueAnimator) {
        transitionParams.animateChangeProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        chatMessageCell.invalidate();
    }

    /* JADX INFO: renamed from: $r8$lambda$_2wsYLN-OJFHCLdCam-OD89maqw */
    public static /* synthetic */ void m2048$r8$lambda$_2wsYLNOJFHCLdCamOD89maqw(ChatActionCell.TransitionParams transitionParams, ChatActionCell chatActionCell, ValueAnimator valueAnimator) {
        transitionParams.animateChangeProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        chatActionCell.invalidate();
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$7 */
    public class C07337 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$deltaY;
        final /* synthetic */ RecyclerView.ViewHolder val$holder;
        final /* synthetic */ View val$view;

        public C07337(RecyclerView.ViewHolder viewHolder, int i, View view) {
            viewHolder = viewHolder;
            i = i;
            view = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ChatListItemAnimator.this.dispatchMoveStarting(viewHolder);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            if (i != 0) {
                view.setTranslationY(0.0f);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            animator.removeAllListeners();
            ChatListItemAnimator.this.restoreTransitionParams(viewHolder.itemView);
            View view = viewHolder.itemView;
            if (view instanceof ChatMessageCell) {
                ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                if (chatMessageCell.makeVisibleAfterChange) {
                    chatMessageCell.makeVisibleAfterChange = false;
                    chatMessageCell.setVisibility(0);
                }
                MessageObject.GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                if (currentMessagesGroup != null) {
                    currentMessagesGroup.transitionParams.reset();
                }
            }
            if (ChatListItemAnimator.this.mMoveAnimations.remove(viewHolder)) {
                ChatListItemAnimator.this.dispatchMoveFinished(viewHolder);
                ChatListItemAnimator.this.dispatchFinishedWhenDone();
            }
        }
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, int i, int i2, int i3, int i4) {
        float translationX;
        if (viewHolder == viewHolder2) {
            return animateMove(viewHolder, itemHolderInfo, i, i2, i3, i4);
        }
        View view = viewHolder.itemView;
        if (view instanceof ChatMessageCell) {
            translationX = ((ChatMessageCell) view).getAnimationOffsetX();
        } else {
            translationX = view.getTranslationX();
        }
        float translationY = viewHolder.itemView.getTranslationY();
        float alpha = viewHolder.itemView.getAlpha();
        resetAnimation(viewHolder);
        int i5 = (int) ((i3 - i) - translationX);
        int i6 = (int) ((i4 - i2) - translationY);
        View view2 = viewHolder.itemView;
        if (view2 instanceof ChatMessageCell) {
            ((ChatMessageCell) view2).setAnimationOffsetX(translationX);
        } else {
            view2.setTranslationX(translationX);
        }
        viewHolder.itemView.setTranslationY(translationY);
        viewHolder.itemView.setAlpha(alpha);
        if (viewHolder2 != null) {
            resetAnimation(viewHolder2);
            View view3 = viewHolder2.itemView;
            if (view3 instanceof ChatMessageCell) {
                ((ChatMessageCell) view3).setAnimationOffsetX(-i5);
            } else {
                view3.setTranslationX(-i5);
            }
            viewHolder2.itemView.setTranslationY(-i6);
            viewHolder2.itemView.setAlpha(0.0f);
        }
        this.mPendingChanges.add(new DefaultItemAnimator.ChangeInfo(viewHolder, viewHolder2, i, i2, i3, i4));
        checkIsRunning();
        return true;
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator
    public void animateChangeImpl(DefaultItemAnimator.ChangeInfo changeInfo) {
        RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
        View view = viewHolder == null ? null : viewHolder.itemView;
        RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
        View view2 = viewHolder2 != null ? viewHolder2.itemView : null;
        if (view != null) {
            ViewPropertyAnimator duration = view.animate().setDuration(getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            duration.translationX(changeInfo.toX - changeInfo.fromX);
            duration.translationY(changeInfo.toY - changeInfo.fromY);
            duration.alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.ChatListItemAnimator.8
                final /* synthetic */ DefaultItemAnimator.ChangeInfo val$changeInfo;
                final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
                final /* synthetic */ View val$view;

                public C07348(DefaultItemAnimator.ChangeInfo changeInfo2, ViewPropertyAnimator duration2, View view3) {
                    changeInfo = changeInfo2;
                    viewPropertyAnimator = duration2;
                    view = view3;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    ChatListItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    viewPropertyAnimator.setListener(null);
                    view.setAlpha(1.0f);
                    view.setScaleX(1.0f);
                    view.setScaleX(1.0f);
                    View view3 = view;
                    if (view3 instanceof ChatMessageCell) {
                        ((ChatMessageCell) view3).setAnimationOffsetX(0.0f);
                    } else {
                        view3.setTranslationX(0.0f);
                    }
                    view.setTranslationY(0.0f);
                    if (ChatListItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder)) {
                        ChatListItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                        ChatListItemAnimator.this.dispatchFinishedWhenDone();
                    }
                }
            }).start();
        }
        if (view2 != null) {
            ViewPropertyAnimator viewPropertyAnimatorAnimate = view2.animate();
            this.mChangeAnimations.add(changeInfo2.newHolder);
            viewPropertyAnimatorAnimate.translationX(0.0f).translationY(0.0f).setDuration(getChangeDuration()).alpha(1.0f).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.ChatListItemAnimator.9
                final /* synthetic */ DefaultItemAnimator.ChangeInfo val$changeInfo;
                final /* synthetic */ View val$newView;
                final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;

                public C07359(DefaultItemAnimator.ChangeInfo changeInfo2, ViewPropertyAnimator viewPropertyAnimatorAnimate2, View view22) {
                    changeInfo = changeInfo2;
                    viewPropertyAnimator = viewPropertyAnimatorAnimate2;
                    view = view22;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    ChatListItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    viewPropertyAnimator.setListener(null);
                    view.setAlpha(1.0f);
                    view.setScaleX(1.0f);
                    view.setScaleX(1.0f);
                    View view3 = view;
                    if (view3 instanceof ChatMessageCell) {
                        ((ChatMessageCell) view3).setAnimationOffsetX(0.0f);
                    } else {
                        view3.setTranslationX(0.0f);
                    }
                    view.setTranslationY(0.0f);
                    if (ChatListItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder)) {
                        ChatListItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                        ChatListItemAnimator.this.dispatchFinishedWhenDone();
                    }
                }
            }).start();
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$8 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C07348 extends AnimatorListenerAdapter {
        final /* synthetic */ DefaultItemAnimator.ChangeInfo val$changeInfo;
        final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
        final /* synthetic */ View val$view;

        public C07348(DefaultItemAnimator.ChangeInfo changeInfo2, ViewPropertyAnimator duration2, View view3) {
            changeInfo = changeInfo2;
            viewPropertyAnimator = duration2;
            view = view3;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ChatListItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            viewPropertyAnimator.setListener(null);
            view.setAlpha(1.0f);
            view.setScaleX(1.0f);
            view.setScaleX(1.0f);
            View view3 = view;
            if (view3 instanceof ChatMessageCell) {
                ((ChatMessageCell) view3).setAnimationOffsetX(0.0f);
            } else {
                view3.setTranslationX(0.0f);
            }
            view.setTranslationY(0.0f);
            if (ChatListItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder)) {
                ChatListItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                ChatListItemAnimator.this.dispatchFinishedWhenDone();
            }
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$9 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C07359 extends AnimatorListenerAdapter {
        final /* synthetic */ DefaultItemAnimator.ChangeInfo val$changeInfo;
        final /* synthetic */ View val$newView;
        final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;

        public C07359(DefaultItemAnimator.ChangeInfo changeInfo2, ViewPropertyAnimator viewPropertyAnimatorAnimate2, View view22) {
            changeInfo = changeInfo2;
            viewPropertyAnimator = viewPropertyAnimatorAnimate2;
            view = view22;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ChatListItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            viewPropertyAnimator.setListener(null);
            view.setAlpha(1.0f);
            view.setScaleX(1.0f);
            view.setScaleX(1.0f);
            View view3 = view;
            if (view3 instanceof ChatMessageCell) {
                ((ChatMessageCell) view3).setAnimationOffsetX(0.0f);
            } else {
                view3.setTranslationX(0.0f);
            }
            view.setTranslationY(0.0f);
            if (ChatListItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder)) {
                ChatListItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                ChatListItemAnimator.this.dispatchFinishedWhenDone();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public RecyclerView.ItemAnimator.ItemHolderInfo recordPreLayoutInformation(RecyclerView.State state, RecyclerView.ViewHolder viewHolder, int i, List<Object> list) {
        RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfoRecordPreLayoutInformation = super.recordPreLayoutInformation(state, viewHolder, i, list);
        View view = viewHolder.itemView;
        if (!(view instanceof ChatMessageCell)) {
            return itemHolderInfoRecordPreLayoutInformation;
        }
        ItemHolderInfoExtended itemHolderInfoExtended = new ItemHolderInfoExtended();
        itemHolderInfoExtended.left = itemHolderInfoRecordPreLayoutInformation.left;
        itemHolderInfoExtended.top = itemHolderInfoRecordPreLayoutInformation.top;
        itemHolderInfoExtended.right = itemHolderInfoRecordPreLayoutInformation.right;
        itemHolderInfoExtended.bottom = itemHolderInfoRecordPreLayoutInformation.bottom;
        ChatMessageCell.TransitionParams transitionParams = ((ChatMessageCell) view).getTransitionParams();
        itemHolderInfoExtended.imageX = transitionParams.lastDrawingImageX;
        itemHolderInfoExtended.imageY = transitionParams.lastDrawingImageY;
        itemHolderInfoExtended.imageWidth = transitionParams.lastDrawingImageW;
        itemHolderInfoExtended.imageHeight = transitionParams.lastDrawingImageH;
        return itemHolderInfoExtended;
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator
    public void onAllAnimationsDone() {
        super.onAllAnimationsDone();
        this.recyclerListView.setClipChildren(true);
        while (!this.runOnAnimationsEnd.isEmpty()) {
            this.runOnAnimationsEnd.remove(0).run();
        }
        cancelAnimators();
    }

    private void cancelAnimators() {
        ThanosEffect thanosEffectRun;
        ArrayList arrayList = new ArrayList(this.animators.values());
        this.animators.clear();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Animator animator = (Animator) obj;
            if (animator != null) {
                animator.cancel();
            }
        }
        if (this.thanosViews.isEmpty() || (thanosEffectRun = this.getThanosEffectContainer.run()) == null) {
            return;
        }
        thanosEffectRun.kill();
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void endAnimation(RecyclerView.ViewHolder viewHolder) {
        ThanosEffect thanosEffectRun;
        Animator animatorRemove = this.animators.remove(viewHolder);
        if (animatorRemove != null) {
            animatorRemove.cancel();
        }
        if (this.thanosViews.contains(viewHolder.itemView) && (thanosEffectRun = this.getThanosEffectContainer.run()) != null) {
            thanosEffectRun.cancel(viewHolder.itemView);
        }
        super.endAnimation(viewHolder);
        restoreTransitionParams(viewHolder.itemView);
    }

    public void restoreTransitionParams(View view) {
        view.setAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setTranslationY(0.0f);
        if (view instanceof BotHelpCell) {
            int measuredHeight = (this.recyclerListView.getMeasuredHeight() / 2) - (view.getMeasuredHeight() / 2);
            ((BotHelpCell) view).setAnimating(false);
            if (view.getTop() > measuredHeight) {
                view.setTranslationY(measuredHeight - view.getTop());
                return;
            } else {
                view.setTranslationY(0.0f);
                return;
            }
        }
        if (view instanceof UserInfoCell) {
            int measuredHeight2 = (this.recyclerListView.getMeasuredHeight() / 2) - (view.getMeasuredHeight() / 2);
            ((UserInfoCell) view).setAnimating(false);
            if (view.getTop() > measuredHeight2) {
                view.setTranslationY(measuredHeight2 - view.getTop());
                return;
            } else {
                view.setTranslationY(0.0f);
                return;
            }
        }
        if (view instanceof ChatMessageCell) {
            ChatMessageCell chatMessageCell = (ChatMessageCell) view;
            chatMessageCell.getTransitionParams().resetAnimation();
            chatMessageCell.setAnimationOffsetX(0.0f);
        } else if (view instanceof ChatActionCell) {
            ((ChatActionCell) view).getTransitionParams().resetAnimation();
        } else {
            view.setTranslationX(0.0f);
        }
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public void endAnimations() {
        ArrayList<DefaultItemAnimator.ChangeInfo> arrayList;
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("end animations");
        }
        ArrayList<MessageObject.GroupedMessages> arrayList2 = this.willChangedGroups;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            MessageObject.GroupedMessages groupedMessages = arrayList2.get(i);
            i++;
            groupedMessages.transitionParams.isNewGroup = false;
        }
        this.willChangedGroups.clear();
        cancelAnimators();
        ChatGreetingsView chatGreetingsView = this.chatGreetingsView;
        if (chatGreetingsView != null) {
            chatGreetingsView.stickerToSendView.setAlpha(1.0f);
        }
        this.greetingsSticker = null;
        this.chatGreetingsView = null;
        for (int size2 = this.mPendingMoves.size() - 1; size2 >= 0; size2--) {
            DefaultItemAnimator.MoveInfo moveInfo = this.mPendingMoves.get(size2);
            restoreTransitionParams(moveInfo.holder.itemView);
            dispatchMoveFinished(moveInfo.holder);
            this.mPendingMoves.remove(size2);
        }
        for (int size3 = this.mPendingRemovals.size() - 1; size3 >= 0; size3--) {
            RecyclerView.ViewHolder viewHolder = this.mPendingRemovals.get(size3);
            restoreTransitionParams(viewHolder.itemView);
            dispatchRemoveFinished(viewHolder);
            this.mPendingRemovals.remove(size3);
        }
        for (int size4 = this.mPendingAdditions.size() - 1; size4 >= 0; size4--) {
            RecyclerView.ViewHolder viewHolder2 = this.mPendingAdditions.get(size4);
            restoreTransitionParams(viewHolder2.itemView);
            dispatchAddFinished(viewHolder2);
            this.mPendingAdditions.remove(size4);
        }
        int size5 = this.mPendingChanges.size();
        while (true) {
            size5--;
            arrayList = this.mPendingChanges;
            if (size5 < 0) {
                break;
            } else {
                endChangeAnimationIfNecessary(arrayList.get(size5));
            }
        }
        arrayList.clear();
        if (isRunning()) {
            for (int size6 = this.mMovesList.size() - 1; size6 >= 0; size6--) {
                ArrayList<DefaultItemAnimator.MoveInfo> arrayList3 = this.mMovesList.get(size6);
                for (int size7 = arrayList3.size() - 1; size7 >= 0; size7--) {
                    DefaultItemAnimator.MoveInfo moveInfo2 = arrayList3.get(size7);
                    restoreTransitionParams(moveInfo2.holder.itemView);
                    dispatchMoveFinished(moveInfo2.holder);
                    arrayList3.remove(size7);
                    if (arrayList3.isEmpty()) {
                        this.mMovesList.remove(arrayList3);
                    }
                }
            }
            for (int size8 = this.mAdditionsList.size() - 1; size8 >= 0; size8--) {
                ArrayList<RecyclerView.ViewHolder> arrayList4 = this.mAdditionsList.get(size8);
                for (int size9 = arrayList4.size() - 1; size9 >= 0; size9--) {
                    RecyclerView.ViewHolder viewHolder3 = arrayList4.get(size9);
                    restoreTransitionParams(viewHolder3.itemView);
                    dispatchAddFinished(viewHolder3);
                    arrayList4.remove(size9);
                    if (arrayList4.isEmpty()) {
                        this.mAdditionsList.remove(arrayList4);
                    }
                }
            }
            for (int size10 = this.mChangesList.size() - 1; size10 >= 0; size10--) {
                ArrayList<DefaultItemAnimator.ChangeInfo> arrayList5 = this.mChangesList.get(size10);
                for (int size11 = arrayList5.size() - 1; size11 >= 0; size11--) {
                    endChangeAnimationIfNecessary(arrayList5.get(size11));
                    if (arrayList5.isEmpty()) {
                        this.mChangesList.remove(arrayList5);
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            dispatchAnimationsFinished();
        }
    }

    @Override // androidx.recyclerview.widget.DefaultItemAnimator
    public boolean endChangeAnimationIfNecessary(DefaultItemAnimator.ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        ThanosEffect thanosEffectRun;
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("end change if necessary");
        }
        Animator animatorRemove = this.animators.remove(viewHolder);
        if (animatorRemove != null) {
            animatorRemove.cancel();
        }
        if (this.thanosViews.contains(viewHolder.itemView) && (thanosEffectRun = this.getThanosEffectContainer.run()) != null) {
            thanosEffectRun.cancel(viewHolder.itemView);
        }
        boolean z = false;
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else {
            if (changeInfo.oldHolder != viewHolder) {
                return false;
            }
            changeInfo.oldHolder = null;
            z = true;
        }
        restoreTransitionParams(viewHolder.itemView);
        dispatchChangeFinished(viewHolder, z);
        return true;
    }

    public void groupWillTransformToSingleMessage(MessageObject.GroupedMessages groupedMessages) {
        this.willRemovedGroup.put(Integer.valueOf(groupedMessages.messages.get(0).getId()), groupedMessages);
    }

    public void groupWillChanged(MessageObject.GroupedMessages groupedMessages) {
        if (groupedMessages == null) {
            return;
        }
        int size = groupedMessages.messages.size();
        MessageObject.GroupedMessages.TransitionParams transitionParams = groupedMessages.transitionParams;
        if (size == 0) {
            transitionParams.drawBackgroundForDeletedItems = true;
            return;
        }
        if (transitionParams.top == 0 && transitionParams.bottom == 0 && transitionParams.left == 0 && transitionParams.right == 0) {
            int childCount = this.recyclerListView.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                View childAt = this.recyclerListView.getChildAt(i);
                if (childAt instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    MessageObject messageObject = chatMessageCell.getMessageObject();
                    if (chatMessageCell.getTransitionParams().wasDraw && groupedMessages.messages.contains(messageObject)) {
                        groupedMessages.transitionParams.top = chatMessageCell.getTop() + chatMessageCell.getPaddingTop() + chatMessageCell.getBackgroundDrawableTop();
                        groupedMessages.transitionParams.bottom = chatMessageCell.getTop() + chatMessageCell.getPaddingTop() + chatMessageCell.getBackgroundDrawableBottom();
                        groupedMessages.transitionParams.left = chatMessageCell.getLeft() + chatMessageCell.getBackgroundDrawableLeft();
                        groupedMessages.transitionParams.right = chatMessageCell.getLeft() + chatMessageCell.getBackgroundDrawableRight();
                        groupedMessages.transitionParams.drawCaptionLayout = chatMessageCell.hasCaptionLayout();
                        groupedMessages.transitionParams.pinnedTop = chatMessageCell.isPinnedTop();
                        groupedMessages.transitionParams.pinnedBotton = chatMessageCell.isPinnedBottom();
                        groupedMessages.transitionParams.isNewGroup = true;
                        break;
                    }
                }
                i++;
            }
        }
        this.willChangedGroups.add(groupedMessages);
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x024e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void animateAddImpl(androidx.recyclerview.widget.RecyclerView.ViewHolder r29) {
        /*
            Method dump skipped, instruction units count: 624
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.ChatListItemAnimator.animateAddImpl(androidx.recyclerview.widget.RecyclerView$ViewHolder):void");
    }

    public static /* synthetic */ void $r8$lambda$KbxCrKnHJmMtgf00BJ3KTcoIZDE(ChatMessageCell chatMessageCell, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        chatMessageCell.getTransitionParams().animateChangeProgress = fFloatValue;
        if (chatMessageCell.getTransitionParams().animateChangeProgress > 1.0f) {
            chatMessageCell.getTransitionParams().animateChangeProgress = 1.0f;
        }
        float f9 = 1.0f - fFloatValue;
        chatMessageCell.getPhotoImage().setImageCoords(f + (f2 * f9), f3 + (f4 * f9), (f5 * f9) + (f6 * fFloatValue), (f7 * f9) + (f8 * fFloatValue));
        chatMessageCell.invalidate();
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$10 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C072510 extends AnimatorListenerAdapter {
        final /* synthetic */ float val$finalToX;
        final /* synthetic */ float val$finalToY;
        final /* synthetic */ ChatMessageCell val$messageCell;
        final /* synthetic */ float val$toH;
        final /* synthetic */ float val$toW;

        public C072510(ChatMessageCell chatMessageCell, float f, float f2, float f3, float f4) {
            chatMessageCell = chatMessageCell;
            f = f;
            f = f2;
            f = f3;
            f = f4;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            chatMessageCell.getTransitionParams().resetAnimation();
            chatMessageCell.getPhotoImage().setImageCoords(f, f, f, f);
            if (ChatListItemAnimator.this.chatGreetingsView != null) {
                ChatListItemAnimator.this.chatGreetingsView.stickerToSendView.setAlpha(1.0f);
            }
            chatMessageCell.invalidate();
        }
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$11 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C072611 extends AnimatorListenerAdapter {
        final /* synthetic */ RecyclerView.ViewHolder val$holder;
        final /* synthetic */ View val$view;

        public C072611(RecyclerView.ViewHolder viewHolder, View view) {
            viewHolder = viewHolder;
            view = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ChatListItemAnimator.this.dispatchAddStarting(viewHolder);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            view.setAlpha(1.0f);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            animator.removeAllListeners();
            view.setAlpha(1.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            view.setTranslationY(0.0f);
            view.setTranslationY(0.0f);
            if (ChatListItemAnimator.this.mAddAnimations.remove(viewHolder)) {
                ChatListItemAnimator.this.dispatchAddFinished(viewHolder);
                ChatListItemAnimator.this.dispatchFinishedWhenDone();
            }
        }
    }

    public void animateRemoveImpl(final RecyclerView.ViewHolder viewHolder, boolean z) {
        Utilities.Callback0Return<ThanosEffect> callback0Return;
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("animate remove impl ".concat(z ? " with thanos" : _UrlKt.FRAGMENT_ENCODE_SET));
        }
        final View view = viewHolder.itemView;
        this.mRemoveAnimations.add(viewHolder);
        if (z && (callback0Return = this.getThanosEffectContainer) != null) {
            ThanosEffect thanosEffectRun = callback0Return.run();
            dispatchRemoveStarting(viewHolder);
            thanosEffectRun.animate(view, new Runnable() { // from class: androidx.recyclerview.widget.ChatListItemAnimator$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$animateRemoveImpl$9(view, viewHolder);
                }
            });
            this.thanosViews.add(view);
        } else {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, view.getAlpha(), 0.0f);
            dispatchRemoveStarting(viewHolder);
            objectAnimatorOfFloat.setDuration(getRemoveDuration());
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.ChatListItemAnimator.12
                final /* synthetic */ RecyclerView.ViewHolder val$holder;
                final /* synthetic */ View val$view;

                public C072712(final View view2, final RecyclerView.ViewHolder viewHolder2) {
                    view = view2;
                    viewHolder = viewHolder2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    animator.removeAllListeners();
                    view.setAlpha(1.0f);
                    view.setScaleX(1.0f);
                    view.setScaleY(1.0f);
                    view.setTranslationX(0.0f);
                    view.setTranslationY(0.0f);
                    if (ChatListItemAnimator.this.mRemoveAnimations.remove(viewHolder)) {
                        ChatListItemAnimator.this.dispatchRemoveFinished(viewHolder);
                        ChatListItemAnimator.this.dispatchFinishedWhenDone();
                    }
                }
            });
            this.animators.put(viewHolder2, objectAnimatorOfFloat);
            objectAnimatorOfFloat.start();
        }
        this.recyclerListView.stopScroll();
    }

    public /* synthetic */ void lambda$animateRemoveImpl$9(View view, RecyclerView.ViewHolder viewHolder) {
        view.setVisibility(0);
        if (this.mRemoveAnimations.remove(viewHolder)) {
            dispatchRemoveFinished(viewHolder);
            dispatchFinishedWhenDone();
        }
        this.thanosViews.remove(view);
    }

    /* JADX INFO: renamed from: androidx.recyclerview.widget.ChatListItemAnimator$12 */
    /* JADX INFO: loaded from: classes4.dex */
    public class C072712 extends AnimatorListenerAdapter {
        final /* synthetic */ RecyclerView.ViewHolder val$holder;
        final /* synthetic */ View val$view;

        public C072712(final View view2, final RecyclerView.ViewHolder viewHolder2) {
            view = view2;
            viewHolder = viewHolder2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            animator.removeAllListeners();
            view.setAlpha(1.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
            if (ChatListItemAnimator.this.mRemoveAnimations.remove(viewHolder)) {
                ChatListItemAnimator.this.dispatchRemoveFinished(viewHolder);
                ChatListItemAnimator.this.dispatchFinishedWhenDone();
            }
        }
    }

    private void animateRemoveGroupImpl(final ArrayList<RecyclerView.ViewHolder> arrayList) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1045d("animate remove group impl with thanos");
        }
        this.mRemoveAnimations.addAll(arrayList);
        ThanosEffect thanosEffectRun = this.getThanosEffectContainer.run();
        for (int i = 0; i < arrayList.size(); i++) {
            dispatchRemoveStarting(arrayList.get(i));
        }
        final ArrayList<View> arrayList2 = new ArrayList<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(arrayList.get(i2).itemView);
        }
        thanosEffectRun.animateGroup(arrayList2, new Runnable() { // from class: androidx.recyclerview.widget.ChatListItemAnimator$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$animateRemoveGroupImpl$10(arrayList2, arrayList);
            }
        });
        this.thanosViews.add(arrayList2.get(0));
        this.recyclerListView.stopScroll();
    }

    public /* synthetic */ void lambda$animateRemoveGroupImpl$10(ArrayList arrayList, ArrayList arrayList2) {
        for (int i = 0; i < arrayList.size(); i++) {
            ((View) arrayList.get(i)).setVisibility(0);
        }
        if (this.mRemoveAnimations.removeAll(arrayList2)) {
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                dispatchRemoveFinished((RecyclerView.ViewHolder) arrayList2.get(i2));
            }
            dispatchFinishedWhenDone();
        }
        this.thanosViews.removeAll(arrayList);
    }

    public void setShouldAnimateEnterFromBottom(boolean z) {
        this.shouldAnimateEnterFromBottom = z;
    }

    public void onDestroy() {
        onAllAnimationsDone();
    }

    public boolean willRemoved(View view) {
        RecyclerView.ViewHolder childViewHolder = this.recyclerListView.getChildViewHolder(view);
        if (childViewHolder != null) {
            return this.mPendingRemovals.contains(childViewHolder) || this.mRemoveAnimations.contains(childViewHolder);
        }
        return false;
    }

    public boolean willAddedFromAlpha(View view) {
        RecyclerView.ViewHolder childViewHolder;
        if (this.shouldAnimateEnterFromBottom || (childViewHolder = this.recyclerListView.getChildViewHolder(view)) == null) {
            return false;
        }
        return this.mPendingAdditions.contains(childViewHolder) || this.mAddAnimations.contains(childViewHolder);
    }

    public void onGreetingStickerTransition(RecyclerView.ViewHolder viewHolder, ChatGreetingsView chatGreetingsView) {
        this.greetingsSticker = viewHolder;
        this.chatGreetingsView = chatGreetingsView;
        this.shouldAnimateEnterFromBottom = false;
    }

    public void setReversePositions(boolean z) {
        this.reversePositions = z;
    }

    public class MoveInfoExtended extends DefaultItemAnimator.MoveInfo {
        public boolean animateBackgroundOnly;
        public boolean animateChangeGroupBackground;
        public boolean animateChangeInternal;
        boolean animateImage;
        public boolean animatePinnedBottom;
        boolean animateRemoveGroup;
        int deltaBottom;
        int deltaLeft;
        int deltaRight;
        int deltaTop;
        public int groupOffsetBottom;
        public int groupOffsetLeft;
        public int groupOffsetRight;
        public int groupOffsetTop;
        float imageHeight;
        float imageWidth;
        float imageX;
        float imageY;

        public MoveInfoExtended(RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            super(viewHolder, i, i2, i3, i4);
        }
    }

    public class ItemHolderInfoExtended extends RecyclerView.ItemAnimator.ItemHolderInfo {
        float imageHeight;
        float imageWidth;
        float imageX;
        float imageY;

        public ItemHolderInfoExtended() {
        }
    }

    public void prepareThanos(RecyclerView.ViewHolder viewHolder) {
        MessageObject messageObject;
        if (viewHolder == null) {
            return;
        }
        this.toBeSnapped.add(viewHolder);
        View view = viewHolder.itemView;
        if (!(view instanceof ChatMessageCell) || (messageObject = ((ChatMessageCell) view).getMessageObject()) == null) {
            return;
        }
        messageObject.deletedByThanos = true;
    }

    public void setOnSnapMessage(Utilities.Callback0Return<Boolean> callback0Return, Utilities.Callback0Return<ThanosEffect> callback0Return2) {
        this.supportsThanosEffectContainer = callback0Return;
        this.getThanosEffectContainer = callback0Return2;
    }
}
