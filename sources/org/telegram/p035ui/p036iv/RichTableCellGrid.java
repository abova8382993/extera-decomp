package org.telegram.p035ui.p036iv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.AnimatedFloat;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.tgnet.p034tl.TL_iv;

/* JADX INFO: loaded from: classes7.dex */
public class RichTableCellGrid extends ViewGroup {
    private int[] colStarts;
    private int[] colWidths;
    private final Paint headerPaint;
    private final Paint linePaint;
    private TableModel model;
    private Theme.ResourcesProvider resourcesProvider;
    private int[] rowHeights;
    private int[] rowStarts;
    private final RectF selRect;
    private int selectedFillBaseAlpha;
    private final Paint selectedPaint;
    private int selectedStrokeBaseAlpha;
    private final Paint selectedStrokePaint;
    private AnimatedFloat selectionFade;
    private CellSelectionProvider selectionProvider;
    private final Paint stripPaint;

    public interface CellSelectionProvider {
        boolean isSelected(TL_iv.pageTableCell pagetablecell);
    }

    public void setSelectionProvider(CellSelectionProvider cellSelectionProvider) {
        this.selectionProvider = cellSelectionProvider;
        invalidate();
    }

    public RichTableCellGrid(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.colWidths = new int[0];
        this.rowHeights = new int[0];
        this.colStarts = new int[0];
        this.rowStarts = new int[0];
        Paint paint = new Paint(1);
        this.linePaint = paint;
        this.headerPaint = new Paint(1);
        this.stripPaint = new Paint(1);
        this.selectedPaint = new Paint(1);
        Paint paint2 = new Paint(1);
        this.selectedStrokePaint = paint2;
        this.selRect = new RectF();
        this.resourcesProvider = resourcesProvider;
        setWillNotDraw(false);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        paint.setStrokeWidth(AndroidUtilities.m1036dp(1.0f));
        paint2.setStyle(style);
        paint2.setStrokeWidth(AndroidUtilities.m1036dp(2.0f));
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        this.selectionFade = new AnimatedFloat(this, 0L, 220L, CubicBezierInterpolator.EASE_OUT_QUINT);
        applyColors();
    }

    public void applyColors() {
        this.linePaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputField, this.resourcesProvider));
        int color = Theme.getColor(Theme.key_switchTrack, this.resourcesProvider);
        int iRed = Color.red(color);
        int iGreen = Color.green(color);
        int iBlue = Color.blue(color);
        this.headerPaint.setColor(Color.argb(34, iRed, iGreen, iBlue));
        this.stripPaint.setColor(Color.argb(20, iRed, iGreen, iBlue));
        int color2 = Theme.getColor(Theme.key_chat_inTextSelectionHighlight, this.resourcesProvider);
        this.selectedFillBaseAlpha = 80;
        this.selectedStrokeBaseAlpha = 200;
        this.selectedPaint.setColor(Color.argb(80, Color.red(color2), Color.green(color2), Color.blue(color2)));
        this.selectedStrokePaint.setColor(Color.argb(this.selectedStrokeBaseAlpha, Color.red(color2), Color.green(color2), Color.blue(color2)));
        invalidate();
    }

    public void setModel(TableModel tableModel) {
        this.model = tableModel;
        rebuildHosts();
    }

    public TableModel getModel() {
        return this.model;
    }

    public RichTableCellHost hostForAnchor(TL_iv.pageTableCell pagetablecell) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof RichTableCellHost) {
                RichTableCellHost richTableCellHost = (RichTableCellHost) childAt;
                if (richTableCellHost.cell == pagetablecell) {
                    return richTableCellHost;
                }
            }
        }
        return null;
    }

    private void rebuildHosts() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            if (getChildAt(childCount) instanceof RichTableCellHost) {
                removeViewAt(childCount);
            }
        }
        TableModel tableModel = this.model;
        if (tableModel == null) {
            return;
        }
        int size = tableModel.anchors().size();
        for (int i = 0; i < size; i++) {
            TL_iv.pageTableCell pagetablecell = this.model.anchors().get(i);
            RichTableCellHost richTableCellHost = new RichTableCellHost(getContext(), this.resourcesProvider);
            richTableCellHost.bind(pagetablecell);
            addView(richTableCellHost);
        }
    }

    public void rebindAfterModelChange() {
        rebuildHosts();
        requestLayout();
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00c0  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMeasure(int r17, int r18) {
        /*
            Method dump skipped, instruction units count: 474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.p036iv.RichTableCellGrid.onMeasure(int, int):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.model == null) {
            return;
        }
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            if (childAt instanceof RichTableCellHost) {
                RichTableCellHost richTableCellHost = (RichTableCellHost) childAt;
                int iAnchorRowOf = this.model.anchorRowOf(richTableCellHost.cell);
                int iAnchorColOf = this.model.anchorColOf(richTableCellHost.cell);
                if (iAnchorRowOf >= 0 && iAnchorColOf >= 0) {
                    int i6 = this.colStarts[iAnchorColOf];
                    int i7 = this.rowStarts[iAnchorRowOf];
                    richTableCellHost.layout(i6, i7, richTableCellHost.getMeasuredWidth() + i6, richTableCellHost.getMeasuredHeight() + i7);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        TL_iv.pageBlockTable pageblocktable;
        drawCellBackgrounds(canvas);
        drawSelectionFill(canvas);
        super.dispatchDraw(canvas);
        TableModel tableModel = this.model;
        if (tableModel != null && (pageblocktable = tableModel.block) != null && pageblocktable.bordered) {
            drawBorders(canvas);
        }
        drawSelectionOutline(canvas);
    }

    private void drawCellBackgrounds(Canvas canvas) {
        TableModel tableModel = this.model;
        if (tableModel == null) {
            return;
        }
        TL_iv.pageBlockTable pageblocktable = tableModel.block;
        boolean z = pageblocktable != null && pageblocktable.striped;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof RichTableCellHost) {
                RichTableCellHost richTableCellHost = (RichTableCellHost) childAt;
                int iAnchorRowOf = this.model.anchorRowOf(richTableCellHost.cell);
                int iAnchorColOf = this.model.anchorColOf(richTableCellHost.cell);
                if (iAnchorRowOf >= 0 && iAnchorColOf >= 0) {
                    int iSpanCol = TableModel.spanCol(richTableCellHost.cell);
                    int iSpanRow = TableModel.spanRow(richTableCellHost.cell);
                    int[] iArr = this.colStarts;
                    int i2 = iArr[iAnchorColOf];
                    int i3 = this.rowStarts[iAnchorRowOf];
                    int i4 = iArr[Math.min(iAnchorColOf + iSpanCol, this.model.colCount)];
                    int i5 = this.rowStarts[Math.min(iSpanRow + iAnchorRowOf, this.model.rowCount)];
                    if (richTableCellHost.cell.header) {
                        canvas.drawRect(i2, i3, i4, i5, this.headerPaint);
                    } else if (z && iAnchorRowOf % 2 == 0) {
                        canvas.drawRect(i2, i3, i4, i5, this.stripPaint);
                    }
                }
            }
        }
    }

    private boolean isSelected(int i, int i2) {
        CellSelectionProvider cellSelectionProvider;
        TableModel tableModel = this.model;
        if (tableModel == null || (cellSelectionProvider = this.selectionProvider) == null || i < 0 || i >= tableModel.rowCount || i2 < 0 || i2 >= tableModel.colCount) {
            return false;
        }
        return cellSelectionProvider.isSelected(tableModel.grid[i][i2]);
    }

    private boolean hasAnySelection() {
        TableModel tableModel = this.model;
        if (tableModel != null && this.selectionProvider != null) {
            Iterator<TL_iv.pageTableCell> it = tableModel.anchors().iterator();
            while (it.hasNext()) {
                if (this.selectionProvider.isSelected(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void drawSelectionFill(Canvas canvas) {
        if (this.model == null) {
            return;
        }
        float f = this.selectionFade.set(hasAnySelection() ? 1.0f : 0.0f);
        if (f <= 0.001f) {
            return;
        }
        this.selectedPaint.setAlpha((int) (this.selectedFillBaseAlpha * f));
        for (TL_iv.pageTableCell pagetablecell : this.model.anchors()) {
            CellSelectionProvider cellSelectionProvider = this.selectionProvider;
            if (cellSelectionProvider != null && cellSelectionProvider.isSelected(pagetablecell)) {
                int iAnchorRowOf = this.model.anchorRowOf(pagetablecell);
                int iAnchorColOf = this.model.anchorColOf(pagetablecell);
                if (iAnchorRowOf >= 0 && iAnchorColOf >= 0) {
                    int iSpanCol = TableModel.spanCol(pagetablecell);
                    int iSpanRow = TableModel.spanRow(pagetablecell);
                    int[] iArr = this.colStarts;
                    canvas.drawRect(iArr[iAnchorColOf], this.rowStarts[iAnchorRowOf], iArr[Math.min(iAnchorColOf + iSpanCol, this.model.colCount)], this.rowStarts[Math.min(iAnchorRowOf + iSpanRow, this.model.rowCount)], this.selectedPaint);
                }
            }
        }
    }

    private void drawSelectionOutline(Canvas canvas) {
        if (this.model == null) {
            return;
        }
        float f = this.selectionFade.get();
        if (f <= 0.001f) {
            return;
        }
        this.selectedStrokePaint.setAlpha((int) (this.selectedStrokeBaseAlpha * f));
        this.selectedStrokePaint.setStrokeWidth(AndroidUtilities.m1036dp(2.0f) * Math.max(0.4f, f));
        int i = 0;
        while (true) {
            int i2 = this.model.rowCount;
            if (i > i2) {
                break;
            }
            int[] iArr = this.rowStarts;
            int i3 = i < i2 ? iArr[i] : iArr[i2];
            int i4 = 0;
            int i5 = -1;
            while (true) {
                if (i4 >= this.model.colCount) {
                    break;
                }
                if (isSelected(i - 1, i4) != isSelected(i, i4)) {
                    if (i5 < 0) {
                        i5 = this.colStarts[i4];
                    }
                } else if (i5 >= 0) {
                    float f2 = i3;
                    canvas.drawLine(i5, f2, this.colStarts[i4], f2, this.selectedStrokePaint);
                    i5 = -1;
                }
                i4++;
            }
            if (i5 >= 0) {
                float f3 = i3;
                canvas.drawLine(i5, f3, this.colStarts[r7], f3, this.selectedStrokePaint);
            }
            i++;
        }
        int i6 = 0;
        while (true) {
            int i7 = this.model.colCount;
            if (i6 > i7) {
                return;
            }
            int[] iArr2 = this.colStarts;
            int i8 = i6 < i7 ? iArr2[i6] : iArr2[i7];
            int i9 = 0;
            int i10 = -1;
            while (true) {
                if (i9 >= this.model.rowCount) {
                    break;
                }
                if (isSelected(i9, i6 - 1) != isSelected(i9, i6)) {
                    if (i10 < 0) {
                        i10 = this.rowStarts[i9];
                    }
                } else if (i10 >= 0) {
                    float f4 = i8;
                    canvas.drawLine(f4, i10, f4, this.rowStarts[i9], this.selectedStrokePaint);
                    i10 = -1;
                }
                i9++;
            }
            if (i10 >= 0) {
                float f5 = i8;
                canvas.drawLine(f5, i10, f5, this.rowStarts[r7], this.selectedStrokePaint);
            }
            i6++;
        }
    }

    private void drawBorders(Canvas canvas) {
        float strokeWidth = this.linePaint.getStrokeWidth() / 2.0f;
        float fDpf2 = AndroidUtilities.dpf2(3.0f);
        RectF rectF = this.selRect;
        int[] iArr = this.colStarts;
        int[] iArr2 = this.rowStarts;
        TableModel tableModel = this.model;
        rectF.set(iArr[0] + strokeWidth, iArr2[0] + strokeWidth, iArr[tableModel.colCount] - strokeWidth, iArr2[tableModel.rowCount] - strokeWidth);
        canvas.drawRoundRect(this.selRect, fDpf2, fDpf2, this.linePaint);
        for (int i = 1; i < this.model.colCount; i++) {
            int i2 = this.colStarts[i];
            int i3 = -1;
            int i4 = 0;
            while (true) {
                TableModel tableModel2 = this.model;
                if (i4 >= tableModel2.rowCount) {
                    break;
                }
                TL_iv.pageTableCell[] pagetablecellArr = tableModel2.grid[i4];
                if (pagetablecellArr[i - 1] != pagetablecellArr[i]) {
                    if (i3 < 0) {
                        i3 = this.rowStarts[i4];
                    }
                } else if (i3 >= 0) {
                    float f = i2;
                    canvas.drawLine(f, i3, f, this.rowStarts[i4], this.linePaint);
                    i3 = -1;
                }
                i4++;
            }
            if (i3 >= 0) {
                float f2 = i2;
                canvas.drawLine(f2, i3, f2, this.rowStarts[r8], this.linePaint);
            }
        }
        for (int i5 = 1; i5 < this.model.rowCount; i5++) {
            int i6 = this.rowStarts[i5];
            int i7 = -1;
            int i8 = 0;
            while (true) {
                TableModel tableModel3 = this.model;
                if (i8 >= tableModel3.colCount) {
                    break;
                }
                TL_iv.pageTableCell[][] pagetablecellArr2 = tableModel3.grid;
                if (pagetablecellArr2[i5 - 1][i8] != pagetablecellArr2[i5][i8]) {
                    if (i7 < 0) {
                        i7 = this.colStarts[i8];
                    }
                } else if (i7 >= 0) {
                    float f3 = i6;
                    canvas.drawLine(i7, f3, this.colStarts[i8], f3, this.linePaint);
                    i7 = -1;
                }
                i8++;
            }
            if (i7 >= 0) {
                float f4 = i6;
                canvas.drawLine(i7, f4, this.colStarts[r7], f4, this.linePaint);
            }
        }
    }
}
