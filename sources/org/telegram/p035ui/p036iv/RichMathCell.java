package org.telegram.p035ui.p036iv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.TextSelectionHelper;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.UItem;
import org.telegram.p035ui.Components.UniversalAdapter;
import org.telegram.p035ui.Components.UniversalRecyclerView;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.p034tl.TL_iv;
import ru.noties.jlatexmath.JLatexMathDrawable;

/* JADX INFO: loaded from: classes7.dex */
public class RichMathCell extends FrameLayout implements Theme.Colorable, TextSelectionHelper.ArticleSelectableView {
    private final Paint backgroundPaint;
    private Bitmap bitmap;
    private final View clickView;
    private BlockRow currentRow;
    private Delegate delegate;
    private final Paint mathPaint;
    private int paintColor;
    private final TextPaint placeholderPaint;
    private final Theme.ResourcesProvider resourcesProvider;
    private final Paint selectionPaint;
    private Layout stubLayout;
    private final TextPaint stubPaint;

    public interface Delegate {
        TextSelectionHelper.ArticleTextSelectionHelper getSelectionHelper();

        void onEditMath(BlockRow blockRow);
    }

    public RichMathCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.mathPaint = new Paint(1);
        this.backgroundPaint = new Paint(1);
        this.selectionPaint = new Paint(1);
        TextPaint textPaint = new TextPaint();
        this.stubPaint = textPaint;
        TextPaint textPaint2 = new TextPaint(1);
        this.placeholderPaint = textPaint2;
        this.paintColor = 0;
        this.resourcesProvider = resourcesProvider;
        setWillNotDraw(false);
        textPaint.setTextSize(1.0f);
        textPaint2.setTextSize(AndroidUtilities.m1036dp(15.0f));
        textPaint2.setTextAlign(Paint.Align.CENTER);
        setPadding(0, AndroidUtilities.m1036dp(6.0f), 0, AndroidUtilities.m1036dp(6.0f));
        View view = new View(context);
        this.clickView = view;
        view.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.iv.RichMathCell$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$0(view2);
            }
        });
        addView(view, LayoutHelper.createFrame(-1, -1, 119));
        updateColors();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        Delegate delegate;
        BlockRow blockRow = this.currentRow;
        if (blockRow == null || (delegate = this.delegate) == null) {
            return;
        }
        delegate.onEditMath(blockRow);
    }

    public void bind(BlockRow blockRow, Delegate delegate) {
        this.currentRow = blockRow;
        this.delegate = delegate;
        rebuild();
    }

    public BlockRow getRow() {
        return this.currentRow;
    }

    private String getSource() {
        BlockRow blockRow = this.currentRow;
        if (blockRow == null) {
            return null;
        }
        TL_iv.PageBlock pageBlock = blockRow.block;
        if (pageBlock instanceof TL_iv.pageBlockMath) {
            return ((TL_iv.pageBlockMath) pageBlock).source;
        }
        return null;
    }

    public void rebuild() {
        this.bitmap = null;
        String source = getSource();
        if (!TextUtils.isEmpty(source)) {
            try {
                JLatexMathDrawable jLatexMathDrawableBuild = JLatexMathDrawable.builder(source).textSize(AndroidUtilities.m1036dp(20.0f)).build();
                int intrinsicWidth = jLatexMathDrawableBuild.getIntrinsicWidth();
                int intrinsicHeight = jLatexMathDrawableBuild.getIntrinsicHeight();
                if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ALPHA_8);
                    jLatexMathDrawableBuild.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
                    jLatexMathDrawableBuild.draw(new Canvas(bitmapCreateBitmap));
                    this.bitmap = bitmapCreateBitmap;
                }
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
        requestLayout();
        invalidate();
    }

    @Override // org.telegram.ui.ActionBar.Theme.Colorable
    public void updateColors() {
        this.backgroundPaint.setColor(Theme.getColor(Theme.key_chat_inFileBackground, this.resourcesProvider));
        this.selectionPaint.setColor(Theme.getColor(Theme.key_chat_inTextSelectionHighlight, this.resourcesProvider));
        this.placeholderPaint.setColor(Theme.multAlpha(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider), 0.5f));
        this.paintColor = 0;
        invalidate();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int iM1036dp;
        int paddingBottom;
        int size = View.MeasureSpec.getSize(i);
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            iM1036dp = bitmap.getHeight() + getPaddingTop();
            paddingBottom = getPaddingBottom();
        } else {
            iM1036dp = AndroidUtilities.m1036dp(64.0f) + getPaddingTop();
            paddingBottom = getPaddingBottom();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(iM1036dp + paddingBottom, TLObject.FLAG_30));
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int paddingTop = getPaddingTop();
        int measuredHeight = getMeasuredHeight() - getPaddingBottom();
        if (this.bitmap != null) {
            int i = this.paintColor;
            int i2 = Theme.key_windowBackgroundWhiteBlackText;
            if (i != Theme.getColor(i2, this.resourcesProvider)) {
                Paint paint = this.mathPaint;
                int color = Theme.getColor(i2, this.resourcesProvider);
                this.paintColor = color;
                paint.setColor(color);
            }
            canvas.drawBitmap(this.bitmap, (getMeasuredWidth() - this.bitmap.getWidth()) / 2.0f, paddingTop, this.mathPaint);
        } else {
            float f = paddingTop;
            canvas.drawRect(AndroidUtilities.m1036dp(16.0f), f, getMeasuredWidth() - AndroidUtilities.m1036dp(16.0f), measuredHeight, this.backgroundPaint);
            canvas.drawText("Tap to add an equation", getMeasuredWidth() / 2.0f, (f + ((measuredHeight - paddingTop) / 2.0f)) - ((this.placeholderPaint.descent() + this.placeholderPaint.ascent()) / 2.0f), this.placeholderPaint);
        }
        if (isCellSelected()) {
            canvas.drawRect(AndroidUtilities.m1036dp(16.0f), paddingTop, getMeasuredWidth() - AndroidUtilities.m1036dp(16.0f), measuredHeight, this.selectionPaint);
        }
    }

    private boolean isCellSelected() {
        TextSelectionHelper.ArticleTextSelectionHelper selectionHelper;
        int childAdapterPosition;
        Delegate delegate = this.delegate;
        return delegate != null && (selectionHelper = delegate.getSelectionHelper()) != null && selectionHelper.isInSelectionMode() && (getParent() instanceof RecyclerView) && (childAdapterPosition = ((RecyclerView) getParent()).getChildAdapterPosition(this)) >= 0 && childAdapterPosition >= selectionHelper.getStartCell() && childAdapterPosition <= selectionHelper.getEndCell();
    }

    @Override // org.telegram.ui.Cells.TextSelectionHelper.ArticleSelectableView
    public void fillTextLayoutBlocks(ArrayList<TextSelectionHelper.TextLayoutBlock> arrayList) {
        if (this.stubLayout == null) {
            this.stubLayout = new StaticLayout("•", this.stubPaint, Math.max(1, getMeasuredWidth()), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
        final Layout layout = this.stubLayout;
        arrayList.add(new TextSelectionHelper.TextLayoutBlock() { // from class: org.telegram.ui.iv.RichMathCell.1
            @Override // org.telegram.ui.Cells.TextSelectionHelper.TextLayoutBlock
            public int getRow() {
                return 0;
            }

            @Override // org.telegram.ui.Cells.TextSelectionHelper.TextLayoutBlock
            public int getX() {
                return 0;
            }

            @Override // org.telegram.ui.Cells.TextSelectionHelper.TextLayoutBlock
            public int getY() {
                return 0;
            }

            @Override // org.telegram.ui.Cells.TextSelectionHelper.TextLayoutBlock
            public Layout getLayout() {
                return layout;
            }
        });
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.stubLayout = null;
    }

    public static final class Factory extends UItem.UItemFactory<RichMathCell> {
        @Override // org.telegram.ui.Components.UItem.UItemFactory
        /* JADX INFO: renamed from: isClickable */
        public boolean getIsClickableValue() {
            return false;
        }

        static {
            UItem.UItemFactory.setup(new Factory());
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public RichMathCell createView(Context context, RecyclerListView recyclerListView, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
            return new RichMathCell(context, resourcesProvider);
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
            ((RichMathCell) view).bind((BlockRow) uItem.object, (Delegate) uItem.object2);
        }

        /* JADX INFO: renamed from: of */
        public static UItem m1236of(BlockRow blockRow, Delegate delegate) {
            UItem uItemOfFactory = UItem.ofFactory(Factory.class);
            uItemOfFactory.object = blockRow;
            uItemOfFactory.object2 = delegate;
            return uItemOfFactory;
        }
    }
}
