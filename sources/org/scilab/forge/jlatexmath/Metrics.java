package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class Metrics {

    /* JADX INFO: renamed from: d */
    private final float f1093d;

    /* JADX INFO: renamed from: h */
    private final float f1094h;

    /* JADX INFO: renamed from: i */
    private final float f1095i;

    /* JADX INFO: renamed from: s */
    private final float f1096s;

    /* JADX INFO: renamed from: w */
    private final float f1097w;

    public Metrics(float f, float f2, float f3, float f4, float f5, float f6) {
        this.f1097w = f * f5;
        this.f1094h = f2 * f5;
        this.f1093d = f3 * f5;
        this.f1095i = f4 * f5;
        this.f1096s = f6;
    }

    public float getWidth() {
        return this.f1097w;
    }

    public float getHeight() {
        return this.f1094h;
    }

    public float getDepth() {
        return this.f1093d;
    }

    public float getItalic() {
        return this.f1095i;
    }

    public float getSize() {
        return this.f1096s;
    }
}
