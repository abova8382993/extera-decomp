package org.telegram.p035ui.Components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.text.style.ReplacementSpan;
import android.view.View;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.web.WebInstantView;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class TextPaintImageReceiverSpan extends ReplacementSpan {
    private boolean alignTop;
    private boolean baselineMode;
    private int contentWidth;
    private int depth;
    private int height;
    private ImageReceiver imageReceiver;
    private int lastDrawTop;
    private float lastDrawX;
    private boolean lastDrawn;
    private int scrollX;
    private int width;

    public static /* synthetic */ void $r8$lambda$njb1CVCkH53R0cVT72Kh89O4uj4() {
    }

    public boolean isScrollable() {
        return this.contentWidth > this.width;
    }

    public int getMaxScroll() {
        return Math.max(0, this.contentWidth - this.width);
    }

    public void setScroll(int i) {
        this.scrollX = Utilities.clamp(i, getMaxScroll(), 0);
    }

    public int getScroll() {
        return this.scrollX;
    }

    public void setScrollableContentWidth(int i) {
        this.contentWidth = i;
    }

    public boolean containsPoint(float f, float f2) {
        if (!this.lastDrawn || !isScrollable()) {
            return false;
        }
        float f3 = this.lastDrawX;
        if (f < f3 || f > f3 + this.width) {
            return false;
        }
        int i = this.lastDrawTop;
        return f2 >= ((float) i) && f2 <= ((float) (i + this.height));
    }

    public TextPaintImageReceiverSpan(View view, TLRPC.Document document, Object obj, int i, int i2, boolean z, boolean z2) {
        String str = String.format(Locale.US, "%d_%d_i", Integer.valueOf(i), Integer.valueOf(i2));
        this.width = i;
        this.height = i2;
        this.contentWidth = i;
        ImageReceiver imageReceiver = new ImageReceiver(view);
        this.imageReceiver = imageReceiver;
        imageReceiver.setInvalidateAll(true);
        if (z2) {
            this.imageReceiver.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Components.TextPaintImageReceiverSpan$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
                public final void didSetImage(ImageReceiver imageReceiver2, boolean z3, boolean z4, boolean z5) {
                    TextPaintImageReceiverSpan.$r8$lambda$4MMEUN7lGItDcmEJ4PJPQuQdpRk(imageReceiver2, z3, z4, z5);
                }
            });
        }
        this.imageReceiver.setImage(ImageLocation.getForDocument(document), str, ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 90), document), str, -1L, null, obj, 1);
        this.alignTop = z;
    }

    public static /* synthetic */ void $r8$lambda$4MMEUN7lGItDcmEJ4PJPQuQdpRk(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        if (imageReceiver.canInvertBitmap()) {
            imageReceiver.setColorFilter(new ColorMatrixColorFilter(new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f}));
        }
    }

    public TextPaintImageReceiverSpan(View view, WebInstantView.WebPhoto webPhoto, Object obj, int i, int i2, boolean z, boolean z2) {
        this.width = i;
        this.height = i2;
        this.contentWidth = i;
        ImageReceiver imageReceiver = new ImageReceiver(view);
        this.imageReceiver = imageReceiver;
        imageReceiver.setInvalidateAll(true);
        if (z2) {
            this.imageReceiver.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Components.TextPaintImageReceiverSpan$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
                public final void didSetImage(ImageReceiver imageReceiver2, boolean z3, boolean z4, boolean z5) {
                    TextPaintImageReceiverSpan.$r8$lambda$hL00ZSraoBvcqklhxroC5of8_Ck(imageReceiver2, z3, z4, z5);
                }
            });
        }
        WebInstantView.loadPhoto(webPhoto, this.imageReceiver, new Runnable() { // from class: org.telegram.ui.Components.TextPaintImageReceiverSpan$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TextPaintImageReceiverSpan.$r8$lambda$njb1CVCkH53R0cVT72Kh89O4uj4();
            }
        });
        this.alignTop = z;
    }

    public static /* synthetic */ void $r8$lambda$hL00ZSraoBvcqklhxroC5of8_Ck(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        if (imageReceiver.canInvertBitmap()) {
            imageReceiver.setColorFilter(new ColorMatrixColorFilter(new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f}));
        }
    }

    public TextPaintImageReceiverSpan(View view, Bitmap bitmap, int i, int i2, int i3, int i4) {
        this.width = i;
        this.height = i2;
        this.contentWidth = i;
        ImageReceiver imageReceiver = new ImageReceiver(view);
        this.imageReceiver = imageReceiver;
        imageReceiver.setInvalidateAll(true);
        this.imageReceiver.setImageBitmap(bitmap);
        this.imageReceiver.setColorFilter(new PorterDuffColorFilter(i3, PorterDuff.Mode.SRC_IN));
        this.depth = i4;
        this.baselineMode = true;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        if (fontMetricsInt != null) {
            if (this.baselineMode) {
                int i3 = this.height;
                int i4 = this.depth;
                int i5 = -(i3 - i4);
                fontMetricsInt.ascent = i5;
                fontMetricsInt.top = i5;
                fontMetricsInt.descent = i4;
                fontMetricsInt.bottom = i4;
            } else if (this.alignTop) {
                int iM1036dp = (fontMetricsInt.descent - fontMetricsInt.ascent) - AndroidUtilities.m1036dp(4.0f);
                int i6 = this.height - iM1036dp;
                fontMetricsInt.descent = i6;
                fontMetricsInt.bottom = i6;
                int i7 = 0 - iM1036dp;
                fontMetricsInt.ascent = i7;
                fontMetricsInt.top = i7;
            } else {
                int iM1036dp2 = ((-this.height) / 2) - AndroidUtilities.m1036dp(4.0f);
                fontMetricsInt.ascent = iM1036dp2;
                fontMetricsInt.top = iM1036dp2;
                int i8 = this.height;
                int iM1036dp3 = (i8 - (i8 / 2)) - AndroidUtilities.m1036dp(4.0f);
                fontMetricsInt.descent = iM1036dp3;
                fontMetricsInt.bottom = iM1036dp3;
            }
        }
        return this.width;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        canvas.save();
        if (this.baselineMode) {
            int i6 = this.height;
            int i7 = i4 - (i6 - this.depth);
            this.lastDrawX = f;
            this.lastDrawTop = i7;
            this.lastDrawn = true;
            int i8 = this.contentWidth;
            int i9 = this.width;
            if (i8 > i9) {
                float f2 = i7;
                canvas.clipRect(f, f2, i9 + f, i7 + i6);
                this.imageReceiver.setImageCoords((int) (f - this.scrollX), f2, this.contentWidth, this.height);
            } else {
                this.imageReceiver.setImageCoords((int) f, i7, i9, i6);
            }
        } else if (this.alignTop) {
            this.imageReceiver.setImageCoords((int) f, i3 - 1, this.width, this.height);
        } else {
            int iM1036dp = (i5 - AndroidUtilities.m1036dp(4.0f)) - i3;
            this.imageReceiver.setImageCoords((int) f, i3 + ((iM1036dp - r4) / 2), this.width, this.height);
        }
        this.imageReceiver.draw(canvas);
        canvas.restore();
    }
}
