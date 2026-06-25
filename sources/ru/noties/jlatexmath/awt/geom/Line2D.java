package ru.noties.jlatexmath.awt.geom;

/* JADX INFO: loaded from: classes7.dex */
public class Line2D {

    public static class Float {

        /* JADX INFO: renamed from: x1 */
        public double f1888x1;

        /* JADX INFO: renamed from: x2 */
        public double f1889x2;

        /* JADX INFO: renamed from: y1 */
        public double f1890y1;

        /* JADX INFO: renamed from: y2 */
        public double f1891y2;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4) {
            setLine(f, f2, f3, f4);
        }

        public void setLine(double d, double d2, double d3, double d4) {
            this.f1888x1 = d;
            this.f1890y1 = d2;
            this.f1889x2 = d3;
            this.f1891y2 = d4;
        }

        public String toString() {
            return "Float{x1=" + this.f1888x1 + ", y1=" + this.f1890y1 + ", x2=" + this.f1889x2 + ", y2=" + this.f1891y2 + '}';
        }
    }
}
