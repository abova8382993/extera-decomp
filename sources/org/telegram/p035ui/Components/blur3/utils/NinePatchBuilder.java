package org.telegram.p035ui.Components.blur3.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.NinePatchDrawable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NinePatchBuilder {

    /* JADX INFO: loaded from: classes7.dex */
    public interface NinePathRenderer {
        void draw(Canvas canvas, RectF rectF, float[] fArr);
    }

    public static NinePatchDrawable createNinePatch(Bitmap[] bitmapArr, final int i, float[] fArr, final float f, final int i2, final float f2, final float f3, int i3) {
        return createNinePatch(bitmapArr, fArr, f, f2, f3, i3, new NinePathRenderer() { // from class: org.telegram.ui.Components.blur3.utils.NinePatchBuilder$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.blur3.utils.NinePatchBuilder.NinePathRenderer
            public final void draw(Canvas canvas, RectF rectF, float[] fArr2) {
                NinePatchBuilder.$r8$lambda$86sFOu1PZqKXvzaMlCbF6VL6Vy8(i, f, f2, f3, i2, canvas, rectF, fArr2);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$86sFOu1PZqKXvzaMlCbF6VL6Vy8(int i, float f, float f2, float f3, int i2, Canvas canvas, RectF rectF, float[] fArr) {
        Path path = new Path();
        path.addRoundRect(rectF, fArr, Path.Direction.CW);
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(i);
        if (f > 0.0f) {
            paint.setShadowLayer(f, f2, f3, i2);
        }
        canvas.drawPath(path, paint);
        if (f > 0.0f) {
            paint.clearShadowLayer();
            canvas.drawPath(path, paint);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.NinePatchDrawable createNinePatch(android.graphics.Bitmap[] r33, float[] r34, float r35, float r36, float r37, int r38, org.telegram.ui.Components.blur3.utils.NinePatchBuilder.NinePathRenderer r39) {
        /*
            Method dump skipped, instruction units count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.blur3.utils.NinePatchBuilder.createNinePatch(android.graphics.Bitmap[], float[], float, float, float, int, org.telegram.ui.Components.blur3.utils.NinePatchBuilder$NinePathRenderer):android.graphics.drawable.NinePatchDrawable");
    }

    public static ByteBuffer createNinePatchChunk(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(84).order(ByteOrder.nativeOrder());
        byteBufferOrder.put((byte) 1);
        byteBufferOrder.put((byte) 2);
        byteBufferOrder.put((byte) 2);
        byteBufferOrder.put((byte) 9);
        byteBufferOrder.putInt(0);
        byteBufferOrder.putInt(0);
        byteBufferOrder.putInt(i5);
        byteBufferOrder.putInt(i7);
        byteBufferOrder.putInt(i6);
        byteBufferOrder.putInt(i8);
        byteBufferOrder.putInt(0);
        byteBufferOrder.putInt(i);
        byteBufferOrder.putInt(i2);
        byteBufferOrder.putInt(i3);
        byteBufferOrder.putInt(i4);
        byteBufferOrder.putInt(1);
        byteBufferOrder.putInt(1);
        byteBufferOrder.putInt(1);
        byteBufferOrder.putInt(1);
        byteBufferOrder.putInt(i9);
        byteBufferOrder.putInt(1);
        byteBufferOrder.putInt(1);
        byteBufferOrder.putInt(1);
        byteBufferOrder.putInt(1);
        return byteBufferOrder;
    }
}
