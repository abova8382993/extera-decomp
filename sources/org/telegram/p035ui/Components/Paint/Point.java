package org.telegram.p035ui.Components.Paint;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes7.dex */
public class Point {
    public boolean edge;

    /* JADX INFO: renamed from: x */
    public double f1585x;

    /* JADX INFO: renamed from: y */
    public double f1586y;

    /* JADX INFO: renamed from: z */
    public double f1587z;

    public Point(double d, double d2, double d3) {
        this.f1585x = d;
        this.f1586y = d2;
        this.f1587z = d3;
    }

    public Point(double d, double d2, double d3, boolean z) {
        this.f1585x = d;
        this.f1586y = d2;
        this.f1587z = d3;
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
        return this.f1585x == point.f1585x && this.f1586y == point.f1586y && this.f1587z == point.f1587z;
    }

    public Point multiplySum(Point point, double d) {
        return new Point((this.f1585x + point.f1585x) * d, (this.f1586y + point.f1586y) * d, (this.f1587z + point.f1587z) * d);
    }

    public Point add(Point point) {
        return new Point(this.f1585x + point.f1585x, this.f1586y + point.f1586y, this.f1587z + point.f1587z);
    }

    public Point substract(Point point) {
        return new Point(this.f1585x - point.f1585x, this.f1586y - point.f1586y, this.f1587z - point.f1587z);
    }

    public Point multiplyByScalar(double d) {
        return new Point(this.f1585x * d, this.f1586y * d, this.f1587z * d);
    }

    public float getDistanceTo(Point point) {
        return (float) Math.sqrt(Math.pow(this.f1585x - point.f1585x, 2.0d) + Math.pow(this.f1586y - point.f1586y, 2.0d) + Math.pow(this.f1587z - point.f1587z, 2.0d));
    }

    public PointF toPointF() {
        return new PointF((float) this.f1585x, (float) this.f1586y);
    }
}
