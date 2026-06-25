package org.telegram.p035ui.Charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import org.telegram.p035ui.Charts.data.ChartData;
import org.telegram.p035ui.Charts.view_data.LineViewData;

/* JADX INFO: loaded from: classes6.dex */
public class LinearBarChartView extends BaseChartView<ChartData, LineViewData> {
    public LinearBarChartView(Context context) {
        super(context);
    }

    @Override // org.telegram.p035ui.Charts.BaseChartView
    public void init() {
        this.useMinHeight = true;
        super.init();
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01e0  */
    @Override // org.telegram.p035ui.Charts.BaseChartView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawChart(android.graphics.Canvas r23) {
        /*
            Method dump skipped, instruction units count: 499
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Charts.LinearBarChartView.drawChart(android.graphics.Canvas):void");
    }

    @Override // org.telegram.p035ui.Charts.BaseChartView
    public void drawPickerChart(Canvas canvas) {
        int i;
        float f;
        float f2;
        int i2;
        float f3;
        float f4;
        float f5;
        float f6;
        getMeasuredHeight();
        getMeasuredHeight();
        int size = this.lines.size();
        T t = this.chartData;
        if (t != 0) {
            float[] fArr = t.xPercentage;
            float f7 = fArr.length < 2 ? 1.0f : fArr[1] * this.pickerWidth;
            int i3 = 0;
            while (i3 < size) {
                LineViewData lineViewData = (LineViewData) this.lines.get(i3);
                float f8 = 0.0f;
                if (lineViewData.enabled || lineViewData.alpha != 0.0f) {
                    lineViewData.bottomLinePath.reset();
                    int length = this.chartData.xPercentage.length;
                    long[] jArr = lineViewData.line.f1518y;
                    lineViewData.chartPath.reset();
                    int i4 = 0;
                    int i5 = 0;
                    while (i4 < length) {
                        long j = jArr[i4];
                        if (j < 0) {
                            i2 = size;
                            f3 = f7;
                            f2 = f8;
                        } else {
                            T t2 = this.chartData;
                            f2 = f8;
                            float f9 = t2.xPercentage[i4] * this.pickerWidth;
                            boolean z = BaseChartView.ANIMATE_PICKER_SIZES;
                            if (z) {
                                i2 = size;
                                f4 = this.pickerMaxHeight;
                                f3 = f7;
                            } else {
                                i2 = size;
                                f3 = f7;
                                f4 = t2.maxValue;
                            }
                            if (z) {
                                f6 = this.pickerMinHeight;
                                f5 = f4;
                            } else {
                                f5 = f4;
                                f6 = t2.minValue;
                            }
                            float f10 = (1.0f - ((j - f6) / (f5 - f6))) * this.pikerHeight;
                            if (!BaseChartView.USE_LINES) {
                                Path path = lineViewData.bottomLinePath;
                                if (i4 == 0) {
                                    path.moveTo(f9 - (f3 / 2.0f), f10);
                                } else {
                                    path.lineTo(f9 - (f3 / 2.0f), f10);
                                }
                                lineViewData.bottomLinePath.lineTo(f9 + (f3 / 2.0f), f10);
                            } else if (i5 == 0) {
                                float[] fArr2 = lineViewData.linesPathBottom;
                                float f11 = f3 / 2.0f;
                                fArr2[i5] = f9 - f11;
                                fArr2[i5 + 1] = f10;
                                float f12 = f9 + f11;
                                fArr2[i5 + 2] = f12;
                                fArr2[i5 + 3] = f10;
                                int i6 = i5 + 5;
                                fArr2[i5 + 4] = f12;
                                i5 += 6;
                                fArr2[i6] = f10;
                            } else {
                                int i7 = length - 1;
                                float[] fArr3 = lineViewData.linesPathBottom;
                                if (i4 == i7) {
                                    float f13 = f3 / 2.0f;
                                    float f14 = f9 - f13;
                                    fArr3[i5] = f14;
                                    fArr3[i5 + 1] = f10;
                                    fArr3[i5 + 2] = f14;
                                    fArr3[i5 + 3] = f10;
                                    float f15 = f9 + f13;
                                    fArr3[i5 + 4] = f15;
                                    fArr3[i5 + 5] = f10;
                                    fArr3[i5 + 6] = f15;
                                    fArr3[i5 + 7] = f10;
                                    int i8 = i5 + 9;
                                    fArr3[i5 + 8] = f15;
                                    i5 += 10;
                                    fArr3[i8] = f2;
                                } else {
                                    float f16 = f3 / 2.0f;
                                    float f17 = f9 - f16;
                                    fArr3[i5] = f17;
                                    fArr3[i5 + 1] = f10;
                                    fArr3[i5 + 2] = f17;
                                    fArr3[i5 + 3] = f10;
                                    float f18 = f9 + f16;
                                    fArr3[i5 + 4] = f18;
                                    fArr3[i5 + 5] = f10;
                                    int i9 = i5 + 7;
                                    fArr3[i5 + 6] = f18;
                                    i5 += 8;
                                    fArr3[i9] = f10;
                                }
                            }
                        }
                        i4++;
                        size = i2;
                        f8 = f2;
                        f7 = f3;
                    }
                    i = size;
                    f = f7;
                    float f19 = f8;
                    lineViewData.linesPathBottomSize = i5;
                    if (lineViewData.enabled || lineViewData.alpha != f19) {
                        lineViewData.bottomLinePaint.setAlpha((int) (lineViewData.alpha * 255.0f));
                        if (BaseChartView.USE_LINES) {
                            canvas.drawLines(lineViewData.linesPathBottom, 0, lineViewData.linesPathBottomSize, lineViewData.bottomLinePaint);
                        } else {
                            canvas.drawPath(lineViewData.bottomLinePath, lineViewData.bottomLinePaint);
                        }
                    }
                } else {
                    i = size;
                    f = f7;
                }
                i3++;
                size = i;
                f7 = f;
            }
        }
    }

    @Override // org.telegram.p035ui.Charts.BaseChartView
    public LineViewData createLineViewData(ChartData.Line line) {
        return new LineViewData(line, true);
    }
}
