package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import java.util.Arrays;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes.dex */
public class Cubic {
    public static final Companion Companion = new Companion(null);
    private final float[] points;

    public /* synthetic */ Cubic(long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4);
    }

    public Cubic(float[] points) {
        Intrinsics.checkNotNullParameter(points, "points");
        this.points = points;
        if (points.length != 8) {
            throw new IllegalArgumentException("Points array size should be 8");
        }
    }

    public /* synthetic */ Cubic(float[] fArr, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new float[8] : fArr);
    }

    public final float[] getPoints$graphics_shapes_release() {
        return this.points;
    }

    public final float getAnchor0X() {
        return this.points[0];
    }

    public final float getAnchor0Y() {
        return this.points[1];
    }

    public final float getControl0X() {
        return this.points[2];
    }

    public final float getControl0Y() {
        return this.points[3];
    }

    public final float getControl1X() {
        return this.points[4];
    }

    public final float getControl1Y() {
        return this.points[5];
    }

    public final float getAnchor1X() {
        return this.points[6];
    }

    public final float getAnchor1Y() {
        return this.points[7];
    }

    private Cubic(long j, long j2, long j3, long j4) {
        this(new float[]{PointKt.m2118getXDnnuFBc(j), PointKt.m2119getYDnnuFBc(j), PointKt.m2118getXDnnuFBc(j2), PointKt.m2119getYDnnuFBc(j2), PointKt.m2118getXDnnuFBc(j3), PointKt.m2119getYDnnuFBc(j3), PointKt.m2118getXDnnuFBc(j4), PointKt.m2119getYDnnuFBc(j4)});
    }

    /* JADX INFO: renamed from: pointOnCurve-OOQOV4g$graphics_shapes_release, reason: not valid java name */
    public final long m2111pointOnCurveOOQOV4g$graphics_shapes_release(float f) {
        float f2 = 1 - f;
        float f3 = f2 * f2 * f2;
        float f4 = 3 * f;
        float f5 = f4 * f2 * f2;
        float f6 = f4 * f * f2;
        float f7 = f * f * f;
        return FloatFloatPair.m2042constructorimpl((getAnchor0X() * f3) + (getControl0X() * f5) + (getControl1X() * f6) + (getAnchor1X() * f7), (getAnchor0Y() * f3) + (getControl0Y() * f5) + (getControl1Y() * f6) + (getAnchor1Y() * f7));
    }

    public final boolean zeroLength$graphics_shapes_release() {
        return Math.abs(getAnchor0X() - getAnchor1X()) < 1.0E-4f && Math.abs(getAnchor0Y() - getAnchor1Y()) < 1.0E-4f;
    }

    private final boolean zeroIsh(float f) {
        return Math.abs(f) < 1.0E-4f;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01c9 A[PHI: r3 r7
  0x01c9: PHI (r3v15 float) = (r3v12 float), (r3v21 float) binds: [B:85:0x0218, B:62:0x01c7] A[DONT_GENERATE, DONT_INLINE]
  0x01c9: PHI (r7v14 float) = (r7v10 float), (r7v16 float) binds: [B:85:0x0218, B:62:0x01c7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x021b A[PHI: r9 r15
  0x021b: PHI (r9v5 float) = (r9v2 float), (r9v3 float), (r9v3 float), (r9v2 float), (r9v2 float), (r9v2 float) binds: [B:65:0x01d2, B:77:0x0201, B:79:0x0205, B:51:0x01a6, B:54:0x01b0, B:56:0x01b4] A[DONT_GENERATE, DONT_INLINE]
  0x021b: PHI (r15v13 float) = (r15v9 float), (r15v10 float), (r15v10 float), (r15v9 float), (r15v9 float), (r15v9 float) binds: [B:65:0x01d2, B:77:0x0201, B:79:0x0205, B:51:0x01a6, B:54:0x01b0, B:56:0x01b4] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculateBounds$graphics_shapes_release(float[] r23, boolean r24) {
        /*
            Method dump skipped, instruction units count: 549
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.graphics.shapes.Cubic.calculateBounds$graphics_shapes_release(float[], boolean):void");
    }

    public final Pair split(float f) {
        float f2 = 1 - f;
        long jM2111pointOnCurveOOQOV4g$graphics_shapes_release = m2111pointOnCurveOOQOV4g$graphics_shapes_release(f);
        float f3 = f2 * f2;
        float f4 = 2 * f2 * f;
        float f5 = f * f;
        return TuplesKt.m1081to(CubicKt.Cubic(getAnchor0X(), getAnchor0Y(), (getAnchor0X() * f2) + (getControl0X() * f), (getAnchor0Y() * f2) + (getControl0Y() * f), (getAnchor0X() * f3) + (getControl0X() * f4) + (getControl1X() * f5), (getAnchor0Y() * f3) + (getControl0Y() * f4) + (getControl1Y() * f5), PointKt.m2118getXDnnuFBc(jM2111pointOnCurveOOQOV4g$graphics_shapes_release), PointKt.m2119getYDnnuFBc(jM2111pointOnCurveOOQOV4g$graphics_shapes_release)), CubicKt.Cubic(PointKt.m2118getXDnnuFBc(jM2111pointOnCurveOOQOV4g$graphics_shapes_release), PointKt.m2119getYDnnuFBc(jM2111pointOnCurveOOQOV4g$graphics_shapes_release), (getControl0X() * f3) + (getControl1X() * f4) + (getAnchor1X() * f5), (getControl0Y() * f3) + (getControl1Y() * f4) + (getAnchor1Y() * f5), (getControl1X() * f2) + (getAnchor1X() * f), (getControl1Y() * f2) + (getAnchor1Y() * f), getAnchor1X(), getAnchor1Y()));
    }

    public final Cubic reverse() {
        return CubicKt.Cubic(getAnchor1X(), getAnchor1Y(), getControl1X(), getControl1Y(), getControl0X(), getControl0Y(), getAnchor0X(), getAnchor0Y());
    }

    public String toString() {
        return "anchor0: (" + getAnchor0X() + ", " + getAnchor0Y() + ") control0: (" + getControl0X() + ", " + getControl0Y() + "), control1: (" + getControl1X() + ", " + getControl1Y() + "), anchor1: (" + getAnchor1X() + ", " + getAnchor1Y() + ')';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Cubic) {
            return Arrays.equals(this.points, ((Cubic) obj).points);
        }
        return false;
    }

    public final Cubic transformed(PointTransformer f) {
        Intrinsics.checkNotNullParameter(f, "f");
        MutableCubic mutableCubic = new MutableCubic();
        ArraysKt.copyInto$default(this.points, mutableCubic.getPoints$graphics_shapes_release(), 0, 0, 0, 14, (Object) null);
        mutableCubic.transform(f);
        return mutableCubic;
    }

    public int hashCode() {
        return Arrays.hashCode(this.points);
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Cubic straightLine(float f, float f2, float f3, float f4) {
            return CubicKt.Cubic(f, f2, Utils.interpolate(f, f3, 0.33333334f), Utils.interpolate(f2, f4, 0.33333334f), Utils.interpolate(f, f3, 0.6666667f), Utils.interpolate(f2, f4, 0.6666667f), f3, f4);
        }

        public final Cubic circularArc(float f, float f2, float f3, float f4, float f5, float f6) {
            float f7 = f3 - f;
            float f8 = f4 - f2;
            long jDirectionVector = Utils.directionVector(f7, f8);
            float f9 = f5 - f;
            float f10 = f6 - f2;
            long jDirectionVector2 = Utils.directionVector(f9, f10);
            long jM2131rotate90DnnuFBc = Utils.m2131rotate90DnnuFBc(jDirectionVector);
            long jM2131rotate90DnnuFBc2 = Utils.m2131rotate90DnnuFBc(jDirectionVector2);
            boolean z = PointKt.m2114dotProduct5P9i7ZU(jM2131rotate90DnnuFBc, f9, f10) >= 0.0f;
            float fM2115dotProductybeJwSQ = PointKt.m2115dotProductybeJwSQ(jDirectionVector, jDirectionVector2);
            if (fM2115dotProductybeJwSQ > 0.999f) {
                return straightLine(f3, f4, f5, f6);
            }
            float fDistance = ((((Utils.distance(f7, f8) * 4.0f) / 3.0f) * (((float) Math.sqrt(2 * r9)) - ((float) Math.sqrt(r5 - (fM2115dotProductybeJwSQ * fM2115dotProductybeJwSQ))))) / (1 - fM2115dotProductybeJwSQ)) * (z ? 1.0f : -1.0f);
            return CubicKt.Cubic(f3, f4, f3 + (PointKt.m2118getXDnnuFBc(jM2131rotate90DnnuFBc) * fDistance), f4 + (PointKt.m2119getYDnnuFBc(jM2131rotate90DnnuFBc) * fDistance), f5 - (PointKt.m2118getXDnnuFBc(jM2131rotate90DnnuFBc2) * fDistance), f6 - (PointKt.m2119getYDnnuFBc(jM2131rotate90DnnuFBc2) * fDistance), f5, f6);
        }
    }
}
