package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.debug.DebugConfig;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.RecyclerListView;

/* JADX INFO: loaded from: classes3.dex */
public class UniversalRecyclerView extends RecyclerListView {
    public final UniversalAdapter adapter;
    private boolean doNotDetachViews;
    public ItemTouchHelper itemTouchHelper;
    public LinearLayoutManager layoutManager;
    private boolean reorderHandleOnly;
    private boolean reorderingAllowed;
    private boolean reorderingOnOtherAxis;

    public void onLayoutUpdate() {
    }

    public void swappedElements() {
    }

    public void doNotDetachViews() {
        this.doNotDetachViews = true;
    }

    public UniversalRecyclerView(BaseFragment baseFragment, Utilities.Callback2<ArrayList<UItem>, UniversalAdapter> callback2, Utilities.Callback5<UItem, View, Integer, Float, Float> callback5, Utilities.Callback5Return<UItem, View, Integer, Float, Float, Boolean> callback5Return) {
        this(baseFragment.getContext(), baseFragment.getCurrentAccount(), baseFragment.getClassGuid(), callback2, callback5, callback5Return, baseFragment.getResourceProvider());
    }

    public UniversalRecyclerView(Context context, int i, int i2, Utilities.Callback2<ArrayList<UItem>, UniversalAdapter> callback2, Utilities.Callback5<UItem, View, Integer, Float, Float> callback5, Utilities.Callback5Return<UItem, View, Integer, Float, Float, Boolean> callback5Return, Theme.ResourcesProvider resourcesProvider) {
        this(context, i, i2, false, callback2, callback5, callback5Return, resourcesProvider);
    }

    public UniversalRecyclerView(Context context, int i, int i2, boolean z, Utilities.Callback2<ArrayList<UItem>, UniversalAdapter> callback2, Utilities.Callback5<UItem, View, Integer, Float, Float> callback5, Utilities.Callback5Return<UItem, View, Integer, Float, Float, Boolean> callback5Return, Theme.ResourcesProvider resourcesProvider) {
        this(context, i, i2, z, callback2, callback5, callback5Return, resourcesProvider, -1, 1);
    }

    public UniversalRecyclerView(Context context, int i, int i2, boolean z, Utilities.Callback2<ArrayList<UItem>, UniversalAdapter> callback2, final Utilities.Callback5<UItem, View, Integer, Float, Float> callback5, final Utilities.Callback5Return<UItem, View, Integer, Float, Float, Boolean> callback5Return, Theme.ResourcesProvider resourcesProvider, int i3, int i4) {
        super(context, resourcesProvider);
        if (i3 == -1) {
            C52841 c52841 = new LinearLayoutManager(context, i4, false) { // from class: org.telegram.ui.Components.UniversalRecyclerView.1
                public C52841(Context context2, int i42, boolean z2) {
                    super(context2, i42, z2);
                }

                @Override // androidx.recyclerview.widget.LinearLayoutManager
                public int getExtraLayoutSpace(RecyclerView.State state) {
                    return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
                }
            };
            this.layoutManager = c52841;
            setLayoutManager(c52841);
        } else {
            C52852 c52852 = new ExtendedGridLayoutManager(context2, i3) { // from class: org.telegram.ui.Components.UniversalRecyclerView.2
                public C52852(Context context2, int i32) {
                    super(context2, i32);
                }

                @Override // androidx.recyclerview.widget.LinearLayoutManager
                public int getExtraLayoutSpace(RecyclerView.State state) {
                    return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
                }
            };
            c52852.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.UniversalRecyclerView.3
                final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

                public C52863(ExtendedGridLayoutManager c528522) {
                    extendedGridLayoutManager = c528522;
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
            this.layoutManager = c528522;
            setLayoutManager(c528522);
        }
        UniversalAdapter universalAdapter = new UniversalAdapter(this, context2, i, i2, z, callback2, resourcesProvider);
        this.adapter = universalAdapter;
        setAdapter(universalAdapter);
        setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda4
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
        });
        C52874 c52874 = new DefaultItemAnimator() { // from class: org.telegram.ui.Components.UniversalRecyclerView.4
            public C52874() {
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            public void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onMoveAnimationUpdate(viewHolder);
                UniversalRecyclerView.this.invalidate();
                UniversalRecyclerView.this.onLayoutUpdate();
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            public void onRemoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onRemoveAnimationUpdate(viewHolder);
                if (UniversalRecyclerView.this.hasSections()) {
                    UniversalRecyclerView.this.invalidate();
                }
                UniversalRecyclerView.this.onLayoutUpdate();
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            public void onAddAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onAddAnimationUpdate(viewHolder);
                if (UniversalRecyclerView.this.hasSections()) {
                    UniversalRecyclerView.this.invalidate();
                }
                UniversalRecyclerView.this.onLayoutUpdate();
            }

            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            public void onChangeAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onChangeAnimationUpdate(viewHolder);
                if (UniversalRecyclerView.this.hasSections()) {
                    UniversalRecyclerView.this.invalidate();
                }
                UniversalRecyclerView.this.onLayoutUpdate();
            }
        };
        c52874.setSupportsChangeAnimations(false);
        c52874.setDelayAnimations(false);
        c52874.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        c52874.setDurations(350L);
        setItemAnimator(c52874);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$1 */
    public class C52841 extends LinearLayoutManager {
        public C52841(Context context2, int i42, boolean z2) {
            super(context2, i42, z2);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$2 */
    public class C52852 extends ExtendedGridLayoutManager {
        public C52852(Context context2, int i32) {
            super(context2, i32);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$3 */
    public class C52863 extends GridLayoutManager.SpanSizeLookup {
        final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

        public C52863(ExtendedGridLayoutManager c528522) {
            extendedGridLayoutManager = c528522;
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
    public class C52874 extends DefaultItemAnimator {
        public C52874() {
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        public void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onMoveAnimationUpdate(viewHolder);
            UniversalRecyclerView.this.invalidate();
            UniversalRecyclerView.this.onLayoutUpdate();
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        public void onRemoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onRemoveAnimationUpdate(viewHolder);
            if (UniversalRecyclerView.this.hasSections()) {
                UniversalRecyclerView.this.invalidate();
            }
            UniversalRecyclerView.this.onLayoutUpdate();
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        public void onAddAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onAddAnimationUpdate(viewHolder);
            if (UniversalRecyclerView.this.hasSections()) {
                UniversalRecyclerView.this.invalidate();
            }
            UniversalRecyclerView.this.onLayoutUpdate();
        }

        @Override // androidx.recyclerview.widget.DefaultItemAnimator
        public void onChangeAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
            super.onChangeAnimationUpdate(viewHolder);
            if (UniversalRecyclerView.this.hasSections()) {
                UniversalRecyclerView.this.invalidate();
            }
            UniversalRecyclerView.this.onLayoutUpdate();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$5 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C52885 extends LinearLayoutManager {
        public C52885(Context context, int i, boolean z) {
            super(context, i, z);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    public void makeHorizontal() {
        C52885 c52885 = new LinearLayoutManager(getContext(), 0, false) { // from class: org.telegram.ui.Components.UniversalRecyclerView.5
            public C52885(Context context, int i, boolean z) {
                super(context, i, z);
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public int getExtraLayoutSpace(RecyclerView.State state) {
                return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
            }
        };
        this.layoutManager = c52885;
        setLayoutManager(c52885);
    }

    public void setSpanCount(int i) {
        LinearLayoutManager linearLayoutManager = this.layoutManager;
        if (linearLayoutManager instanceof ExtendedGridLayoutManager) {
            ((ExtendedGridLayoutManager) linearLayoutManager).setSpanCount(i);
            return;
        }
        if (linearLayoutManager == null || i == -1) {
            return;
        }
        C52896 c52896 = new ExtendedGridLayoutManager(getContext(), i) { // from class: org.telegram.ui.Components.UniversalRecyclerView.6
            public C52896(Context context, int i2) {
                super(context, i2);
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public int getExtraLayoutSpace(RecyclerView.State state) {
                return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
            }
        };
        c52896.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.UniversalRecyclerView.7
            final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

            public C52907(ExtendedGridLayoutManager c528962) {
                extendedGridLayoutManager = c528962;
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
        this.layoutManager = c528962;
        setLayoutManager(c528962);
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$6 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C52896 extends ExtendedGridLayoutManager {
        public C52896(Context context, int i2) {
            super(context, i2);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public int getExtraLayoutSpace(RecyclerView.State state) {
            return UniversalRecyclerView.this.doNotDetachViews ? AndroidUtilities.displaySize.y : super.getExtraLayoutSpace(state);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.UniversalRecyclerView$7 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C52907 extends GridLayoutManager.SpanSizeLookup {
        final /* synthetic */ ExtendedGridLayoutManager val$layoutManager1;

        public C52907(ExtendedGridLayoutManager c528962) {
            extendedGridLayoutManager = c528962;
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

    public void listenReorder(Utilities.Callback2<Integer, ArrayList<UItem>> callback2) {
        listenReorder(callback2, false);
    }

    public void setReorderHandleOnly(boolean z) {
        this.reorderHandleOnly = z;
    }

    public void listenReorder(Utilities.Callback2<Integer, ArrayList<UItem>> callback2, boolean z) {
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
        AndroidUtilities.forEachViews((RecyclerView) this, (Consumer<View>) new Consumer() { // from class: org.telegram.ui.Components.UniversalRecyclerView$$ExternalSyntheticLambda3
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$allowReorder$2((View) obj);
            }
        });
    }

    public /* synthetic */ void lambda$allowReorder$2(View view) {
        this.adapter.updateReorder(getChildViewHolder(view), this.reorderingAllowed);
    }

    @Override // org.telegram.p035ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        if (!hasSections()) {
            this.adapter.drawWhiteSections(canvas, this);
        }
        super.dispatchDraw(canvas);
    }

    public UItem findItemByItemId(int i) {
        for (int i2 = 0; i2 < this.adapter.getItemCount(); i2++) {
            UItem item = this.adapter.getItem(i2);
            if (item != null && item.f1708id == i) {
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
            if (item != null && item.f1708id == i) {
                break;
            }
            i2++;
        }
        return findViewByPosition(i2);
    }

    public View findViewByItemObject(Object obj) {
        int i = 0;
        while (true) {
            if (i >= this.adapter.getItemCount()) {
                i = -1;
                break;
            }
            UItem item = this.adapter.getItem(i);
            if (item != null && item.object == obj) {
                break;
            }
            i++;
        }
        return findViewByPosition(i);
    }

    public int findPositionByItemId(int i) {
        for (int i2 = 0; i2 < this.adapter.getItemCount(); i2++) {
            UItem item = this.adapter.getItem(i2);
            if (item != null && item.f1708id == i) {
                return i2;
            }
        }
        return -1;
    }

    public class TouchHelperCallback extends ItemTouchHelper.Callback {
        public /* synthetic */ TouchHelperCallback(UniversalRecyclerView universalRecyclerView, UniversalRecyclerViewIA universalRecyclerViewIA) {
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
                int orientation = UniversalRecyclerView.this.layoutManager.getOrientation();
                UniversalRecyclerView universalRecyclerView = UniversalRecyclerView.this;
                int i = 15;
                if (orientation == 0) {
                    if (!universalRecyclerView.reorderingOnOtherAxis) {
                        i = 12;
                    }
                } else if (!universalRecyclerView.reorderingOnOtherAxis) {
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
                    viewHolder.itemView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(DebugConfig.getSectionRadiusDp()), Theme.getColor(Theme.key_windowBackgroundWhite)));
                    viewHolder.itemView.setAlpha(1.0f);
                }
                UniversalRecyclerView.this.adapter.reorderDone();
            } else {
                UniversalRecyclerView.this.cancelClickRunnables(false);
                if (viewHolder != null) {
                    UniversalRecyclerView.this.setDraggingChild(viewHolder.itemView);
                    viewHolder.itemView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(DebugConfig.getSectionRadiusDp()), Theme.getColor(Theme.key_windowBackgroundWhite)));
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
            viewHolder.itemView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1036dp(DebugConfig.getSectionRadiusDp()), Theme.getColor(Theme.key_windowBackgroundWhite)));
        }
    }

    @Override // org.telegram.p035ui.Components.RecyclerListView
    public void setSections() {
        setSections(AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(DebugConfig.getSectionRadiusDp()), false);
    }

    @Override // org.telegram.p035ui.Components.RecyclerListView
    public void setSections(boolean z) {
        setSections(AndroidUtilities.m1036dp(12.0f), AndroidUtilities.m1036dp(DebugConfig.getSectionRadiusDp()), z);
    }

    @Override // org.telegram.p035ui.Components.RecyclerListView
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
                return Boolean.valueOf((UniversalAdapter.isShadow(num.intValue()) || (ExteraConfig.getSectionsSeparatedHeaders() && UniversalAdapter.isHeader(num.intValue()))) ? false : true);
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
        Object tag = childViewHolder.itemView.getTag(C2797R.id.parent_tag);
        if ((tag instanceof Boolean) && ((Boolean) tag).booleanValue()) {
            return Boolean.FALSE;
        }
        int adapterPosition = childViewHolder.getAdapterPosition();
        if (adapterPosition != -1 && (item = this.adapter.getItem(adapterPosition)) != null && item.transparent) {
            return Boolean.FALSE;
        }
        int itemViewType = childViewHolder.getItemViewType();
        if (ExteraConfig.getSectionsSeparatedHeaders() && UniversalAdapter.isHeader(itemViewType)) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(!UniversalAdapter.isShadow(itemViewType));
    }

    public /* synthetic */ void lambda$setSections$5(Canvas canvas, RectF rectF, float f, float f2, float f3) {
        super.drawBackgroundRect(canvas, rectF, f, f2, f3);
    }
}
