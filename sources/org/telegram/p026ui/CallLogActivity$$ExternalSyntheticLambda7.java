package org.telegram.p026ui;

import android.graphics.Canvas;
import android.view.View;
import org.telegram.p026ui.Components.UniversalRecyclerView;
import org.telegram.p026ui.Components.blur3.ViewGroupPartRenderer;

/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class CallLogActivity$$ExternalSyntheticLambda7 implements ViewGroupPartRenderer.DrawChildMethod {
    public final /* synthetic */ UniversalRecyclerView f$0;

    public /* synthetic */ CallLogActivity$$ExternalSyntheticLambda7(UniversalRecyclerView universalRecyclerView) {
        this.f$0 = universalRecyclerView;
    }

    @Override // org.telegram.ui.Components.blur3.ViewGroupPartRenderer.DrawChildMethod
    public final boolean drawChild(Canvas canvas, View view, long j) {
        return this.f$0.drawChild(canvas, view, j);
    }
}
