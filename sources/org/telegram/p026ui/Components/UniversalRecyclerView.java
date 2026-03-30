package org.telegram.p026ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;
import androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticNonNull0;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.google.android.exoplayer2.util.Consumer;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.Utilities;
import org.telegram.p026ui.ActionBar.BaseFragment;
import org.telegram.p026ui.ActionBar.Theme;
import org.telegram.p026ui.Components.RecyclerListView;

/* JADX INFO: loaded from: classes3.dex */
public class UniversalRecyclerView extends RecyclerListView {
    public final UniversalAdapter adapter;
    private boolean doNotDetachViews;
    public ItemTouchHelper itemTouchHelper;
    public LinearLayoutManager layoutManager;
    private boolean reorderHandleOnly;
    private boolean reorderingAllowed;
    private boolean reorderingOnOtherAxis;

    protected void swappedElements() {
    }

    public void doNotDetachViews() {
        this.doNotDetachViews = true;
    }

    public UniversalRecyclerView(BaseFragment baseFragment, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return) {
        this(baseFragment.getContext(), baseFragment.getCurrentAccount(), baseFragment.getClassGuid(), callback2, callback5, callback5Return, baseFragment.getResourceProvider());
    }

    public UniversalRecyclerView(Context context, int i, int i2, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider) {
        this(context, i, i2, false, callback2, callback5, callback5Return, resourcesProvider);
    }

    public UniversalRecyclerView(Context context, int i, int i2, boolean z, Utilities.Callback2 callback2, Utilities.Callback5 callback5, Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider) {
        this(context, i, i2, z, callback2, callback5, callback5Return, resourcesProvider, -1, 1);
    }

    public UniversalRecyclerView(Context context, int i, int i2, boolean z, Utilities.Callback2 callback2, final Utilities.Callback5 callback5, final Utilities.Callback5Return callback5Return, Theme.ResourcesProvider resourcesProvider, int i3, int i4) {
        super(context, resourcesProvider);
        if (i3 == -1) {
            C50981 c50981 = new LinearLayoutManager(context, i4, false) { // from class: org.telegram.ui.Components.UniversalRecyclerView.1
                C50981(Context context2, int i42, boolean z2) {
                    super(context2, i42, z2);
                }

                @Override // androidx.recyclerview.widget.LinearLayoutManager
                protected int getExtraLayoutSpace(RecyclerView.State state) {
                    return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
                }
            };
            this.layoutManager = c50981;
            setLayoutManager(c50981);
        } else {
            C50992 c50992 = new ExtendedGridLayoutManager(context2, i3) { // from class: org.telegram.ui.Components.UniversalRecyclerView.2
                C50992(Context context2, int i32) {
                    super(context2, i32);
                }

                @Override // androidx.recyclerview.widget.LinearLayoutManager
                protected int getExtraLayoutSpace(RecyclerView.State state) {
                    return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
                }
            };
            c50992.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.UniversalRecyclerView.3
                final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

                C51003(ExtendedGridLayoutManager c509922) {
                    extendedGridLayoutManager = c509922;
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i5) {
                    int i6;
                    UniversalAdapter universalAdapter = UniversalRecyclerView.this.adapter;
                    if (universalAdapter == null) {
                        return extendedGridLayoutManager.getSpanCount();
                    }
                    UItem item = universalAdapter.getItem(i5);
                    return (item == null || (i6 = item.spanCount) == -1) ? extendedGridLayoutManager.getSpanCount() : i6;
                }
            });
            this.layoutManager = c509922;
            setLayoutManager(c509922);
        }
        UniversalAdapter universalAdapter = new UniversalAdapter(this, context2, i, i2, z, callback2, resourcesProvider);
        this.adapter = universalAdapter;
        setAdapter(universalAdapter);
        setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda4
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view, int i5) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i5);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view, int i5, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i5, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i5, float f, float f2) {
                this.f$0.lambda$new$0(callback5, view, i5, f, f2);
            }
        });
        setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListenerExtended() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public final boolean onItemClick(View view, int i5, float f, float f2) {
                return this.f$0.lambda$new$1(callback5Return, view, i5, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public /* synthetic */ void onLongClickRelease() {
                RecyclerListView.OnItemLongClickListenerExtended.CC.$default$onLongClickRelease(this);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public /* synthetic */ void onMove(float f, float f2) {
                RecyclerListView.OnItemLongClickListenerExtended.CC.$default$onMove(this, f, f2);
            }
        });
        C51014 c51014 = new DefaultItemAnimator() { // from class: org.telegram.ui.Components.UniversalRecyclerView.4
            C51014() {
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onMoveAnimationUpdate(viewHolder);
                UniversalRecyclerView.this.invalidate();
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            protected void onRemoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onRemoveAnimationUpdate(viewHolder);
                if (UniversalRecyclerView.this.hasSections()) {
                    UniversalRecyclerView.this.invalidate();
                }
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            protected void onAddAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onAddAnimationUpdate(viewHolder);
                if (UniversalRecyclerView.this.hasSections()) {
                    UniversalRecyclerView.this.invalidate();
                }
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            protected void onChangeAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onChangeAnimationUpdate(viewHolder);
                if (UniversalRecyclerView.this.hasSections()) {
                    UniversalRecyclerView.this.invalidate();
                }
            }
        };
        c51014.setSupportsChangeAnimations(false);
        c51014.setDelayAnimations(false);
        c51014.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        c51014.setDurations(350L);
        setItemAnimator(c51014);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$1 */
    class C50981 extends LinearLayoutManager {
        C50981(Context context2, int i42, boolean z2) {
            super(context2, i42, z2);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        protected int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$2 */
    class C50992 extends ExtendedGridLayoutManager {
        C50992(Context context2, int i32) {
            super(context2, i32);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        protected int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$3 */
    class C51003 extends GridLayoutManager.SpanSizeLookup {
        final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

        C51003(ExtendedGridLayoutManager c509922) {
            extendedGridLayoutManager = c509922;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i5) {
            int i6;
            UniversalAdapter universalAdapter = UniversalRecyclerView.this.adapter;
            if (universalAdapter == null) {
                return extendedGridLayoutManager.getSpanCount();
            }
            UItem item = universalAdapter.getItem(i5);
            return (item == null || (i6 = item.spanCount) == -1) ? extendedGridLayoutManager.getSpanCount() : i6;
        }
    }

    public /* synthetic */ void lambda$new$0(Utilities.Callback5 callback5, View view, int i, float f, float f2) {
        UItem item = this.adapter.getItem(i);
        if (item == null || !item.enabled || callback5 == null) {
            return;
        }
        callback5.run(item, view, Integer.valueOf(i), Float.valueOf(f), Float.valueOf(f2));
    }

    public /* synthetic */ boolean lambda$new$1(Utilities.Callback5Return callback5Return, View view, int i, float f, float f2) {
        UItem item = this.adapter.getItem(i);
        if (item == null || callback5Return == null) {
            return false;
        }
        return ((Boolean) callback5Return.run(item, view, Integer.valueOf(i), Float.valueOf(f), Float.valueOf(f2))).booleanValue();
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$4 */
    class C51014 extends DefaultItemAnimator {
        C51014() {
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onMoveAnimationUpdate(viewHolder);
            UniversalRecyclerView.this.invalidate();
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        protected void onRemoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onRemoveAnimationUpdate(viewHolder);
            if (UniversalRecyclerView.this.hasSections()) {
                UniversalRecyclerView.this.invalidate();
            }
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        protected void onAddAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onAddAnimationUpdate(viewHolder);
            if (UniversalRecyclerView.this.hasSections()) {
                UniversalRecyclerView.this.invalidate();
            }
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        protected void onChangeAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onChangeAnimationUpdate(viewHolder);
            if (UniversalRecyclerView.this.hasSections()) {
                UniversalRecyclerView.this.invalidate();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$5 */
    /* JADX INFO: loaded from: classes5.dex */
    class C51025 extends LinearLayoutManager {
        C51025(Context context, int i, boolean z) {
            super(context, i, z);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        protected int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    public void makeHorizontal() {
        C51025 c51025 = new LinearLayoutManager(getContext(), 0, false) { // from class: org.telegram.ui.Components.UniversalRecyclerView.5
            C51025(Context context, int i, boolean z) {
                super(context, i, z);
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
            }
        };
        this.layoutManager = c51025;
        setLayoutManager(c51025);
    }

    public void setSpanCount(int i) {
        LinearLayoutManager linearLayoutManager = this.layoutManager;
        if (linearLayoutManager instanceof ExtendedGridLayoutManager) {
            ((ExtendedGridLayoutManager) linearLayoutManager).setSpanCount(i);
            return;
        }
        if (!OnBackPressedDispatcher$$ExternalSyntheticNonNull0.m1m(linearLayoutManager) || i == -1) {
            return;
        }
        C51036 c51036 = new ExtendedGridLayoutManager(getContext(), i) { // from class: org.telegram.ui.Components.UniversalRecyclerView.6
            C51036(Context context, int i2) {
                super(context, i2);
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
            }
        };
        c51036.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.UniversalRecyclerView.7
            final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

            C51047(ExtendedGridLayoutManager c510362) {
                extendedGridLayoutManager = c510362;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i2) {
                int i3;
                UniversalAdapter universalAdapter = UniversalRecyclerView.this.adapter;
                if (universalAdapter == null) {
                    return extendedGridLayoutManager.getSpanCount();
                }
                UItem item = universalAdapter.getItem(i2);
                return (item == null || (i3 = item.spanCount) == -1) ? extendedGridLayoutManager.getSpanCount() : i3;
            }
        });
        this.layoutManager = c510362;
        setLayoutManager(c510362);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$6 */
    /* JADX INFO: loaded from: classes5.dex */
    class C51036 extends ExtendedGridLayoutManager {
        C51036(Context context, int i2) {
            super(context, i2);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        protected int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$7 */
    /* JADX INFO: loaded from: classes5.dex */
    class C51047 extends GridLayoutManager.SpanSizeLookup {
        final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

        C51047(ExtendedGridLayoutManager c510362) {
            extendedGridLayoutManager = c510362;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i2) {
            int i3;
            UniversalAdapter universalAdapter = UniversalRecyclerView.this.adapter;
            if (universalAdapter == null) {
                return extendedGridLayoutManager.getSpanCount();
            }
            UItem item = universalAdapter.getItem(i2);
            return (item == null || (i3 = item.spanCount) == -1) ? extendedGridLayoutManager.getSpanCount() : i3;
        }
    }

    public int getSpanCount() {
        LinearLayoutManager linearLayoutManager = this.layoutManager;
        if (linearLayoutManager instanceof ExtendedGridLayoutManager) {
            return ((ExtendedGridLayoutManager) linearLayoutManager).getSpanCount();
        }
        return -1;
    }

    public void listenReorder(Utilities.Callback2 callback2) {
        listenReorder(callback2, false);
    }

    public void setReorderHandleOnly(boolean z) {
        this.reorderHandleOnly = z;
    }

    public void listenReorder(Utilities.Callback2 callback2, boolean z) {
        this.reorderingOnOtherAxis = z;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelperCallback());
        this.itemTouchHelper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this);
        this.adapter.listenReorder(callback2);
    }

    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        ItemTouchHelper itemTouchHelper = this.itemTouchHelper;
        if (itemTouchHelper == null || viewHolder == null) {
            return;
        }
        itemTouchHelper.startDrag(viewHolder);
    }

    public boolean isReorderAllowed() {
        return this.reorderingAllowed;
    }

    public void allowReorder(boolean z) {
        if (this.reorderingAllowed == z) {
            return;
        }
        UniversalAdapter universalAdapter = this.adapter;
        this.reorderingAllowed = z;
        universalAdapter.updateReorder(z);
        AndroidUtilities.forEachViews((RecyclerView) this, new Consumer() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda3
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$allowReorder$2((View) obj);
            }
        });
    }

    public /* synthetic */ void lambda$allowReorder$2(View view) {
        this.adapter.updateReorder(getChildViewHolder(view), this.reorderingAllowed);
    }

    @Override // org.telegram.p026ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (!hasSections()) {
            this.adapter.drawWhiteSections(canvas, this);
        }
        super.dispatchDraw(canvas);
    }

    public UItem findItemByItemId(int i) {
        for (int i2 = 0; i2 < this.adapter.getItemCount(); i2++) {
            UItem item = this.adapter.getItem(i2);
            if (item != null && item.f2056id == i) {
                return item;
            }
        }
        return null;
    }

    public View findViewByItemId(int i) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.adapter.getItemCount()) {
                i2 = -1;
                break;
            }
            UItem item = this.adapter.getItem(i2);
            if (item != null && item.f2056id == i) {
                break;
            }
            i2++;
        }
        return findViewByPosition(i2);
    }

    public int findPositionByItemId(int i) {
        for (int i2 = 0; i2 < this.adapter.getItemCount(); i2++) {
            UItem item = this.adapter.getItem(i2);
            if (item != null && item.f2056id == i) {
                return i2;
            }
        }
        return -1;
    }

    private class TouchHelperCallback extends ItemTouchHelper.Callback {
        /* synthetic */ TouchHelperCallback(UniversalRecyclerView universalRecyclerView, UniversalRecyclerViewIA universalRecyclerViewIA) {
            this();
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        }

        private TouchHelperCallback() {
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean isLongPressDragEnabled() {
            return UniversalRecyclerView.this.reorderingAllowed && !UniversalRecyclerView.this.reorderHandleOnly;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (UniversalRecyclerView.this.reorderingAllowed && UniversalRecyclerView.this.adapter.isReorderItem(viewHolder.getAdapterPosition())) {
                int i = 15;
                if (UniversalRecyclerView.this.layoutManager.getOrientation() == 0) {
                    if (!UniversalRecyclerView.this.reorderingOnOtherAxis) {
                        i = 12;
                    }
                } else if (!UniversalRecyclerView.this.reorderingOnOtherAxis) {
                    i = 3;
                }
                return ItemTouchHelper.Callback.makeMovementFlags(i, 0);
            }
            return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            if (!UniversalRecyclerView.this.adapter.isReorderItem(viewHolder.getAdapterPosition()) || UniversalRecyclerView.this.adapter.getReorderSectionId(viewHolder.getAdapterPosition()) != UniversalRecyclerView.this.adapter.getReorderSectionId(viewHolder2.getAdapterPosition())) {
                return false;
            }
            UniversalRecyclerView.this.adapter.swapElements(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
            UniversalRecyclerView.this.swappedElements();
            return true;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                UniversalRecyclerView.this.hideSelector(false);
            }
            if (i == 0) {
                if (viewHolder != null) {
                    viewHolder.itemView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1081dp(16.0f), Theme.getColor(Theme.key_windowBackgroundWhite)));
                    viewHolder.itemView.setAlpha(1.0f);
                }
                UniversalRecyclerView.this.adapter.reorderDone();
            } else {
                UniversalRecyclerView.this.cancelClickRunnables(false);
                if (viewHolder != null) {
                    UniversalRecyclerView.this.setDraggingChild(viewHolder.itemView);
                    viewHolder.itemView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1081dp(16.0f), Theme.getColor(Theme.key_windowBackgroundWhite)));
                    viewHolder.itemView.setAlpha(1.0f);
                    viewHolder.itemView.setPressed(true);
                    viewHolder.itemView.bringToFront();
                }
            }
            super.onSelectedChanged(viewHolder, i);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            UniversalRecyclerView.this.setDraggingChild(null);
            viewHolder.itemView.setPressed(false);
            viewHolder.itemView.setAlpha(1.0f);
            viewHolder.itemView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1081dp(16.0f), Theme.getColor(Theme.key_windowBackgroundWhite)));
        }
    }

    @Override // org.telegram.p026ui.Components.RecyclerListView
    public void setSections() {
        setSections(AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(16.0f), false);
    }

    @Override // org.telegram.p026ui.Components.RecyclerListView
    public void setSections(boolean z) {
        setSections(AndroidUtilities.m1081dp(12.0f), AndroidUtilities.m1081dp(16.0f), z);
    }

    @Override // org.telegram.p026ui.Components.RecyclerListView
    public void setSections(int i, float f, boolean z) {
        super.setSections(new Utilities.CallbackReturn() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.CallbackReturn
            public final Object run(Object obj) {
                return this.f$0.lambda$setSections$3((View) obj);
            }
        }, new Utilities.CallbackReturn() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.CallbackReturn
            public final Object run(Object obj) {
                Integer num = (Integer) obj;
                return Boolean.valueOf((UniversalAdapter.isShadow(num.intValue()) || (ExteraConfig.sectionsSeparatedHeaders && UniversalAdapter.isHeader(num.intValue()))) ? false : true);
            }
        }, i, f, new Utilities.Callback5() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.lambda$setSections$5((Canvas) obj, (RectF) obj2, ((Float) obj3).floatValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, z);
    }

    public /* synthetic */ Boolean lambda$setSections$3(View view) {
        UItem item;
        if (view.getParent() != this) {
            return Boolean.FALSE;
        }
        RecyclerView.ViewHolder childViewHolder = getChildViewHolder(view);
        Object tag = childViewHolder.itemView.getTag(C2702R.id.parent_tag);
        if ((tag instanceof Boolean) && ((Boolean) tag).booleanValue()) {
            return Boolean.FALSE;
        }
        int adapterPosition = childViewHolder.getAdapterPosition();
        if (adapterPosition != -1 && (item = this.adapter.getItem(adapterPosition)) != null && item.transparent) {
            return Boolean.FALSE;
        }
        int itemViewType = childViewHolder.getItemViewType();
        if (ExteraConfig.sectionsSeparatedHeaders && UniversalAdapter.isHeader(itemViewType)) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(!UniversalAdapter.isShadow(itemViewType));
    }

    public /* synthetic */ void lambda$setSections$5(Canvas canvas, RectF rectF, float f, float f2, float f3) {
        super.drawBackgroundRect(canvas, rectF, f, f2, f3);
    }
}
