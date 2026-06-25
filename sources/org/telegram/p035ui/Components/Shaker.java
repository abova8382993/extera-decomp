package org.telegram.p035ui.Components;

import android.graphics.Canvas;
import android.view.View;
import java.util.Objects;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Utilities;

/* JADX INFO: loaded from: classes3.dex */
public class Shaker {
    private final Runnable invalidate;

    /* JADX INFO: renamed from: r */
    private final float f1685r;
    private final long start;

    /* JADX INFO: renamed from: sx */
    private final float f1686sx;

    /* JADX INFO: renamed from: sy */
    private final float f1687sy;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Shaker(View view) {
        this(new Shaker$$ExternalSyntheticLambda0(view));
        Objects.requireNonNull(view);
    }

    public Shaker(Runnable runnable) {
        this.start = System.currentTimeMillis();
        this.invalidate = runnable;
        this.f1685r = AndroidUtilities.lerp(5.0f, 9.0f, Utilities.clamp01(Utilities.fastRandom.nextFloat()));
        this.f1686sx = AndroidUtilities.lerp(2.5f, 5.0f, Utilities.clamp01(Utilities.fastRandom.nextFloat()));
        this.f1687sy = AndroidUtilities.lerp(2.5f, 5.2f, Utilities.clamp01(Utilities.fastRandom.nextFloat()));
    }

    public void concat(Canvas canvas, float f) {
        concat(canvas, f, 0.0f, 0.0f);
    }

    public void concat(Canvas canvas, float f, float f2, float f3) {
        Runnable runnable;
        float fCurrentTimeMillis = (System.currentTimeMillis() - this.start) / 1000.0f;
        canvas.translate(f2, f3);
        canvas.rotate(((float) Math.sin(((double) (this.f1685r * fCurrentTimeMillis)) * 3.141592653589793d)) * 1.0f * f);
        canvas.translate(((float) Math.cos(((double) (this.f1686sx * fCurrentTimeMillis)) * 3.141592653589793d)) * AndroidUtilities.m1036dp(0.5f) * f, ((float) Math.sin(((double) (fCurrentTimeMillis * this.f1687sy)) * 3.141592653589793d)) * AndroidUtilities.m1036dp(0.5f) * f);
        canvas.translate(-f2, -f3);
        if (f <= 0.0f || (runnable = this.invalidate) == null) {
            return;
        }
        runnable.run();
    }
}
