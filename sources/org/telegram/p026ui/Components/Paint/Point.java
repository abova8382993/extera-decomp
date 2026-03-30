package org.telegram.p026ui.Components.Paint;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes5.dex */
public class Point {
    public boolean edge;

    /* JADX INFO: renamed from: x */
    public double f1932x;

    /* JADX INFO: renamed from: y */
    public double f1933y;

    /* JADX INFO: renamed from: z */
    public double f1934z;

    public Point(double d, double d2, double d3) {
        this.f1932x = d;
        this.f1933y = d2;
        this.f1934z = d3;
    }

    public Point(double d, double d2, double d3, boolean z) {
        this.f1932x = d;
        this.f1933y = d2;
        this.f1934z = d3;
        this.edge = z;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point point = (Point) obj;
        return this.f1932x == point.f1932x && this.f1933y == point.f1933y && this.f1934z == point.f1934z;
    }

    Point multiplySum(Point point, double d) {
        return new Point((this.f1932x + point.f1932x) * d, (this.f1933y + point.f1933y) * d, (this.f1934z + point.f1934z) * d);
    }

    Point add(Point point) {
        return new Point(this.f1932x + point.f1932x, this.f1933y + point.f1933y, this.f1934z + point.f1934z);
    }

    Point substract(Point point) {
        return new Point(this.f1932x - point.f1932x, this.f1933y - point.f1933y, this.f1934z - point.f1934z);
    }

    Point multiplyByScalar(double d) {
        return new Point(this.f1932x * d, this.f1933y * d, this.f1934z * d);
    }

    float getDistanceTo(Point point) {
        return (float) Math.sqrt(Math.pow(this.f1932x - point.f1932x, 2.0d) + Math.pow(this.f1933y - point.f1933y, 2.0d) + Math.pow(this.f1934z - point.f1934z, 2.0d));
    }

    PointF toPointF() {
        return new PointF((float) this.f1932x, (float) this.f1933y);
    }
}
