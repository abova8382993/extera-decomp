package org.telegram.p026ui.Charts;

import android.animation.Animator;
import org.telegram.p026ui.Charts.data.ChartData;
import org.telegram.p026ui.Charts.view_data.StackLinearViewData;

/* JADX INFO: loaded from: classes6.dex */
public class PieChartViewData extends StackLinearViewData {
    Animator animator;
    float drawingPart;
    float selectionA;

    public PieChartViewData(ChartData.Line line) {
        super(line);
    }
}
