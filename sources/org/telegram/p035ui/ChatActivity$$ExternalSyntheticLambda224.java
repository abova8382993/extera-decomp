package org.telegram.p035ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import org.telegram.p035ui.ChatActivity;
import org.telegram.p035ui.Components.blur3.capture.IBlur3Capture;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class ChatActivity$$ExternalSyntheticLambda224 implements IBlur3Capture {
    public final /* synthetic */ ChatActivity.ChatActivityFragmentView f$0;

    @Override // org.telegram.p035ui.Components.blur3.capture.IBlur3Capture
    public final void capture(Canvas canvas, RectF rectF) {
        this.f$0.drawList(canvas, rectF);
    }
}
