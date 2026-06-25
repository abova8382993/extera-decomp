package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u001a\u0010\u0005\u001a\u00020\u0002*\u00060\u0000j\u0002`\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a&\u0010\t\u001a\u00020\u0002*\u00060\u0000j\u0002`\u00012\n\u0010\u0006\u001a\u00060\u0000j\u0002`\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\b\u001a*\u0010\t\u001a\u00020\u0002*\u00060\u0000j\u0002`\u00012\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\u0000ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a&\u0010\u0011\u001a\u00020\u000e*\u00060\u0000j\u0002`\u00012\n\u0010\u0006\u001a\u00060\u0000j\u0002`\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a\u001e\u0010\u0014\u001a\u00060\u0000j\u0002`\u0001*\u00060\u0000j\u0002`\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0017\u001a\u00060\u0000j\u0002`\u0001*\u00060\u0000j\u0002`\u00012\n\u0010\u0006\u001a\u00060\u0000j\u0002`\u0001H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a+\u0010\u0019\u001a\u00060\u0000j\u0002`\u0001*\u00060\u0000j\u0002`\u00012\n\u0010\u0006\u001a\u00060\u0000j\u0002`\u0001H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a'\u0010\u001d\u001a\u00060\u0000j\u0002`\u0001*\u00060\u0000j\u0002`\u00012\u0006\u0010\u001a\u001a\u00020\u0002H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a'\u0010\u001f\u001a\u00060\u0000j\u0002`\u0001*\u00060\u0000j\u0002`\u00012\u0006\u0010\u001a\u001a\u00020\u0002H\u0080\u0002ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001c\u001a6\u0010%\u001a\u00060\u0000j\u0002`\u00012\n\u0010 \u001a\u00060\u0000j\u0002`\u00012\n\u0010!\u001a\u00060\u0000j\u0002`\u00012\u0006\u0010\"\u001a\u00020\u0002H\u0000ø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a&\u0010*\u001a\u00060\u0000j\u0002`\u0001*\u00060\u0000j\u0002`\u00012\u0006\u0010'\u001a\u00020&H\u0000ø\u0001\u0000¢\u0006\u0004\b(\u0010)\"\u001c\u0010,\u001a\u00020\u0002*\u00060\u0000j\u0002`\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u0004\"\u001c\u0010.\u001a\u00020\u0002*\u00060\u0000j\u0002`\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u0004*\f\b\u0000\u0010/\"\u00020\u00002\u00020\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u00060"}, m877d2 = {"Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", _UrlKt.FRAGMENT_ENCODE_SET, "getDistance-DnnuFBc", "(J)F", "getDistance", "other", "dotProduct-ybeJwSQ", "(JJ)F", "dotProduct", "otherX", "otherY", "dotProduct-5P9i7ZU", "(JFF)F", _UrlKt.FRAGMENT_ENCODE_SET, "clockwise-ybeJwSQ", "(JJ)Z", "clockwise", "getDirection-DnnuFBc", "(J)J", "getDirection", "minus-ybeJwSQ", "(JJ)J", "minus", "plus-ybeJwSQ", "plus", "operand", "times-so9K2fw", "(JF)J", "times", "div-so9K2fw", "div", "start", "stop", "fraction", "interpolate-dLqxh1s", "(JJF)J", "interpolate", "Landroidx/graphics/shapes/PointTransformer;", "f", "transformed-so9K2fw", "(JLandroidx/graphics/shapes/PointTransformer;)J", "transformed", "getX-DnnuFBc", "x", "getY-DnnuFBc", "y", "Point", "graphics-shapes_release"}, m878k = 2, m879mv = {1, 8, 0}, m881xi = 48)
@SourceDebugExtension({"SMAP\nPoint.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Point.kt\nandroidx/graphics/shapes/PointKt\n+ 2 FloatFloatPair.kt\nandroidx/collection/FloatFloatPair\n+ 3 PackingHelpers.jvm.kt\nandroidx/collection/internal/PackingHelpers_jvmKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,139:1\n48#2:140\n54#2:142\n48#2:144\n54#2:146\n48#2:148\n54#2:150\n22#3:141\n22#3:143\n22#3:145\n22#3:149\n1#4:147\n*S KotlinDebug\n*F\n+ 1 Point.kt\nandroidx/graphics/shapes/PointKt\n*L\n27#1:140\n30#1:142\n32#1:144\n32#1:146\n137#1:148\n137#1:150\n27#1:141\n30#1:143\n32#1:145\n137#1:149\n*E\n"})
public abstract class PointKt {
    /* JADX INFO: renamed from: getX-DnnuFBc */
    public static final float m2027getXDnnuFBc(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* JADX INFO: renamed from: getY-DnnuFBc */
    public static final float m2028getYDnnuFBc(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* JADX INFO: renamed from: getDistance-DnnuFBc */
    public static final float m2026getDistanceDnnuFBc(long j) {
        return (float) Math.sqrt((m2027getXDnnuFBc(j) * m2027getXDnnuFBc(j)) + (m2028getYDnnuFBc(j) * m2028getYDnnuFBc(j)));
    }

    /* JADX INFO: renamed from: dotProduct-ybeJwSQ */
    public static final float m2024dotProductybeJwSQ(long j, long j2) {
        return (m2027getXDnnuFBc(j) * m2027getXDnnuFBc(j2)) + (m2028getYDnnuFBc(j) * m2028getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: dotProduct-5P9i7ZU */
    public static final float m2023dotProduct5P9i7ZU(long j, float f, float f2) {
        return (m2027getXDnnuFBc(j) * f) + (m2028getYDnnuFBc(j) * f2);
    }

    /* JADX INFO: renamed from: clockwise-ybeJwSQ */
    public static final boolean m2021clockwiseybeJwSQ(long j, long j2) {
        return (m2027getXDnnuFBc(j) * m2028getYDnnuFBc(j2)) - (m2028getYDnnuFBc(j) * m2027getXDnnuFBc(j2)) > 0.0f;
    }

    /* JADX INFO: renamed from: getDirection-DnnuFBc */
    public static final long m2025getDirectionDnnuFBc(long j) {
        float fM2026getDistanceDnnuFBc = m2026getDistanceDnnuFBc(j);
        if (fM2026getDistanceDnnuFBc <= 0.0f) {
            g$$ExternalSyntheticBUOutline1.m207m("Can't get the direction of a 0-length vector");
            return 0L;
        }
        return m2022divso9K2fw(j, fM2026getDistanceDnnuFBc);
    }

    /* JADX INFO: renamed from: minus-ybeJwSQ */
    public static final long m2030minusybeJwSQ(long j, long j2) {
        return FloatFloatPair.m1951constructorimpl(m2027getXDnnuFBc(j) - m2027getXDnnuFBc(j2), m2028getYDnnuFBc(j) - m2028getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: plus-ybeJwSQ */
    public static final long m2031plusybeJwSQ(long j, long j2) {
        return FloatFloatPair.m1951constructorimpl(m2027getXDnnuFBc(j) + m2027getXDnnuFBc(j2), m2028getYDnnuFBc(j) + m2028getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: times-so9K2fw */
    public static final long m2032timesso9K2fw(long j, float f) {
        return FloatFloatPair.m1951constructorimpl(m2027getXDnnuFBc(j) * f, m2028getYDnnuFBc(j) * f);
    }

    /* JADX INFO: renamed from: div-so9K2fw */
    public static final long m2022divso9K2fw(long j, float f) {
        return FloatFloatPair.m1951constructorimpl(m2027getXDnnuFBc(j) / f, m2028getYDnnuFBc(j) / f);
    }

    /* JADX INFO: renamed from: interpolate-dLqxh1s */
    public static final long m2029interpolatedLqxh1s(long j, long j2, float f) {
        return FloatFloatPair.m1951constructorimpl(Utils.interpolate(m2027getXDnnuFBc(j), m2027getXDnnuFBc(j2), f), Utils.interpolate(m2028getYDnnuFBc(j), m2028getYDnnuFBc(j2), f));
    }

    /* JADX INFO: renamed from: transformed-so9K2fw */
    public static final long m2033transformedso9K2fw(long j, PointTransformer pointTransformer) {
        long jMo2034transformXgqJiTY = pointTransformer.mo2034transformXgqJiTY(m2027getXDnnuFBc(j), m2028getYDnnuFBc(j));
        return FloatFloatPair.m1951constructorimpl(Float.intBitsToFloat((int) (jMo2034transformXgqJiTY >> 32)), Float.intBitsToFloat((int) (jMo2034transformXgqJiTY & 4294967295L)));
    }
}
