package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes5.dex */
@SinceKotlin(version = "1.5")
@Metadata(m876d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\n\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087@\u0018\u0000 s2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001sB\u0011\bA\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0000H\u0097\u008a\u0004¢\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0087\u008a\u0004¢\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0010H\u0087\u008a\u0004¢\u0006\u0004\b\u0011\u0010\u0012J\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0013H\u0087\u008a\u0004¢\u0006\u0004\b\u0014\u0010\u0015J\u0019\u0010\u0016\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\b\u0017\u0010\fJ\u0019\u0010\u0016\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\u008a\u0004¢\u0006\u0004\b\u0018\u0010\u000fJ\u0019\u0010\u0016\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\u008a\u0004¢\u0006\u0004\b\u0019\u0010\u0012J\u0019\u0010\u0016\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\u008a\u0004¢\u0006\u0004\b\u001a\u0010\u001bJ\u0019\u0010\u001c\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\b\u001d\u0010\fJ\u0019\u0010\u001c\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\u008a\u0004¢\u0006\u0004\b\u001e\u0010\u000fJ\u0019\u0010\u001c\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\u008a\u0004¢\u0006\u0004\b\u001f\u0010\u0012J\u0019\u0010\u001c\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\u008a\u0004¢\u0006\u0004\b \u0010\u001bJ\u0019\u0010!\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\b\"\u0010\fJ\u0019\u0010!\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\u008a\u0004¢\u0006\u0004\b#\u0010\u000fJ\u0019\u0010!\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\u008a\u0004¢\u0006\u0004\b$\u0010\u0012J\u0019\u0010!\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\u008a\u0004¢\u0006\u0004\b%\u0010\u001bJ\u0019\u0010&\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\b'\u0010\fJ\u0019\u0010&\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\u008a\u0004¢\u0006\u0004\b(\u0010\u000fJ\u0019\u0010&\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\u008a\u0004¢\u0006\u0004\b)\u0010\u0012J\u0019\u0010&\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\u008a\u0004¢\u0006\u0004\b*\u0010\u001bJ\u0019\u0010+\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\b,\u0010\fJ\u0019\u0010+\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\u008a\u0004¢\u0006\u0004\b-\u0010\u000fJ\u0019\u0010+\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\u008a\u0004¢\u0006\u0004\b.\u0010\u0012J\u0019\u0010+\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\u008a\u0004¢\u0006\u0004\b/\u0010\u001bJ\u0019\u00100\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\u0088\u0004¢\u0006\u0004\b1\u0010\fJ\u0019\u00100\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\u0088\u0004¢\u0006\u0004\b2\u0010\u000fJ\u0019\u00100\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\u0088\u0004¢\u0006\u0004\b3\u0010\u0012J\u0019\u00100\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\u0088\u0004¢\u0006\u0004\b4\u0010\u001bJ\u0019\u00105\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\u0088\u0004¢\u0006\u0004\b6\u00107J\u0019\u00105\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\rH\u0087\u0088\u0004¢\u0006\u0004\b8\u00109J\u0019\u00105\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\u0088\u0004¢\u0006\u0004\b:\u0010\u0012J\u0019\u00105\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\u0088\u0004¢\u0006\u0004\b;\u0010\u001bJ\u0011\u0010<\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\b=\u0010\u0005J\u0011\u0010>\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\b?\u0010\u0005J\u0019\u0010@\u001a\u00020A2\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\bB\u0010CJ\u0019\u0010D\u001a\u00020A2\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008a\u0004¢\u0006\u0004\bE\u0010CJ\u0019\u0010F\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008c\u0004¢\u0006\u0004\bG\u00107J\u0019\u0010H\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008c\u0004¢\u0006\u0004\bI\u00107J\u0019\u0010J\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\u008c\u0004¢\u0006\u0004\bK\u00107J\u0011\u0010L\u001a\u00020\u0000H\u0087\u0088\u0004¢\u0006\u0004\bM\u0010\u0005J\u0011\u0010N\u001a\u00020\u0003H\u0087\u0088\u0004¢\u0006\u0004\bO\u0010\u0005J\u0011\u0010P\u001a\u00020QH\u0087\u0088\u0004¢\u0006\u0004\bR\u0010SJ\u0011\u0010T\u001a\u00020\tH\u0087\u0088\u0004¢\u0006\u0004\bU\u0010VJ\u0011\u0010W\u001a\u00020XH\u0087\u0088\u0004¢\u0006\u0004\bY\u0010ZJ\u0011\u0010[\u001a\u00020\u0000H\u0087\u0088\u0004¢\u0006\u0004\b\\\u0010\u0005J\u0011\u0010]\u001a\u00020\rH\u0087\u0088\u0004¢\u0006\u0004\b^\u0010SJ\u0011\u0010_\u001a\u00020\u0010H\u0087\u0088\u0004¢\u0006\u0004\b`\u0010VJ\u0011\u0010a\u001a\u00020\u0013H\u0087\u0088\u0004¢\u0006\u0004\bb\u0010ZJ\u0011\u0010c\u001a\u00020dH\u0087\u0088\u0004¢\u0006\u0004\be\u0010fJ\u0011\u0010g\u001a\u00020hH\u0087\u0088\u0004¢\u0006\u0004\bi\u0010jJ\u0011\u0010k\u001a\u00020lH\u0097\u0080\u0004¢\u0006\u0004\bm\u0010nJ\u0014\u0010o\u001a\u00020p2\b\u0010\n\u001a\u0004\u0018\u00010qHÖ\u0083\u0004J\n\u0010r\u001a\u00020\tHÖ\u0081\u0004R\u0017\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0084\b¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006t"}, m877d2 = {"Lkotlin/UByte;", _UrlKt.FRAGMENT_ENCODE_SET, "data", _UrlKt.FRAGMENT_ENCODE_SET, "constructor-impl", "(B)B", "getData$annotations", "()V", "compareTo", _UrlKt.FRAGMENT_ENCODE_SET, "other", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "plus", "plus-7apg3OU", "plus-xj2QHRw", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "(BJ)J", "minus", "minus-7apg3OU", "minus-xj2QHRw", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "times", "times-7apg3OU", "times-xj2QHRw", "times-WZ4Q5Ns", "times-VKZWuLQ", "div", "div-7apg3OU", "div-xj2QHRw", "div-WZ4Q5Ns", "div-VKZWuLQ", "rem", "rem-7apg3OU", "rem-xj2QHRw", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "floorDiv", "floorDiv-7apg3OU", "floorDiv-xj2QHRw", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "mod", "mod-7apg3OU", "(BB)B", "mod-xj2QHRw", "(BS)S", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "inc", "inc-w2LRezQ", "dec", "dec-w2LRezQ", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-7apg3OU", "and", "and-7apg3OU", "or", "or-7apg3OU", "xor", "xor-7apg3OU", "inv", "inv-w2LRezQ", "toByte", "toByte-impl", "toShort", _UrlKt.FRAGMENT_ENCODE_SET, "toShort-impl", "(B)S", "toInt", "toInt-impl", "(B)I", "toLong", _UrlKt.FRAGMENT_ENCODE_SET, "toLong-impl", "(B)J", "toUByte", "toUByte-w2LRezQ", "toUShort", "toUShort-Mh2AYeg", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toFloat", _UrlKt.FRAGMENT_ENCODE_SET, "toFloat-impl", "(B)F", "toDouble", _UrlKt.FRAGMENT_ENCODE_SET, "toDouble-impl", "(B)D", "toString", _UrlKt.FRAGMENT_ENCODE_SET, "toString-impl", "(B)Ljava/lang/String;", "equals", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "hashCode", "Companion", "kotlin-stdlib"}, m878k = 1, m879mv = {2, 3, 0}, m881xi = 48)
@JvmInline
public final class UByte implements Comparable<UByte> {
    public static final byte MAX_VALUE = -1;
    public static final byte MIN_VALUE = 0;
    public static final int SIZE_BITS = 8;
    public static final int SIZE_BYTES = 1;
    private final byte data;

    /* JADX INFO: renamed from: box-impl */
    public static final /* synthetic */ UByte m3506boximpl(byte b2) {
        return new UByte(b2);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    /* JADX INFO: renamed from: constructor-impl */
    public static byte m3512constructorimpl(byte b2) {
        return b2;
    }

    /* JADX INFO: renamed from: equals-impl */
    public static boolean m3518equalsimpl(byte b2, Object obj) {
        return (obj instanceof UByte) && b2 == ((UByte) obj).getData();
    }

    /* JADX INFO: renamed from: equals-impl0 */
    public static final boolean m3519equalsimpl0(byte b2, byte b3) {
        return b2 == b3;
    }

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    /* JADX INFO: renamed from: hashCode-impl */
    public static int m3524hashCodeimpl(byte b2) {
        return Byte.hashCode(b2);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toByte-impl */
    private static final byte m3550toByteimpl(byte b2) {
        return b2;
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toInt-impl */
    private static final int m3553toIntimpl(byte b2) {
        return b2 & MAX_VALUE;
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toLong-impl */
    private static final long m3554toLongimpl(byte b2) {
        return ((long) b2) & 255;
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toShort-impl */
    private static final short m3555toShortimpl(byte b2) {
        return (short) (b2 & 255);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toUByte-w2LRezQ */
    private static final byte m3557toUBytew2LRezQ(byte b2) {
        return b2;
    }

    public boolean equals(Object other) {
        return m3518equalsimpl(this.data, other);
    }

    public int hashCode() {
        return m3524hashCodeimpl(this.data);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ byte getData() {
        return this.data;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UByte uByte) {
        return Intrinsics.compare(getData() & MAX_VALUE, uByte.getData() & MAX_VALUE);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    private /* synthetic */ UByte(byte b2) {
        this.data = b2;
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: compareTo-7apg3OU */
    private int m3507compareTo7apg3OU(byte b2) {
        return Intrinsics.compare(getData() & MAX_VALUE, b2 & MAX_VALUE);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: compareTo-7apg3OU */
    private static int m3508compareTo7apg3OU(byte b2, byte b3) {
        return Intrinsics.compare(b2 & MAX_VALUE, b3 & MAX_VALUE);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: compareTo-xj2QHRw */
    private static final int m3511compareToxj2QHRw(byte b2, short s) {
        return Intrinsics.compare(b2 & MAX_VALUE, s & UShort.MAX_VALUE);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: compareTo-WZ4Q5Ns */
    private static final int m3510compareToWZ4Q5Ns(byte b2, int i) {
        return Integer.compare(UInt.m3589constructorimpl(b2 & MAX_VALUE) ^ Integer.MIN_VALUE, i ^ Integer.MIN_VALUE);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: compareTo-VKZWuLQ */
    private static final int m3509compareToVKZWuLQ(byte b2, long j) {
        return Long.compare(ULong.m3668constructorimpl(((long) b2) & 255) ^ Long.MIN_VALUE, j ^ Long.MIN_VALUE);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: plus-7apg3OU */
    private static final int m3536plus7apg3OU(byte b2, byte b3) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) + UInt.m3589constructorimpl(b3 & MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: plus-xj2QHRw */
    private static final int m3539plusxj2QHRw(byte b2, short s) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) + UInt.m3589constructorimpl(s & UShort.MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: plus-WZ4Q5Ns */
    private static final int m3538plusWZ4Q5Ns(byte b2, int i) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) + i);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: plus-VKZWuLQ */
    private static final long m3537plusVKZWuLQ(byte b2, long j) {
        return ULong.m3668constructorimpl(ULong.m3668constructorimpl(((long) b2) & 255) + j);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: minus-7apg3OU */
    private static final int m3527minus7apg3OU(byte b2, byte b3) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) - UInt.m3589constructorimpl(b3 & MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: minus-xj2QHRw */
    private static final int m3530minusxj2QHRw(byte b2, short s) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) - UInt.m3589constructorimpl(s & UShort.MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: minus-WZ4Q5Ns */
    private static final int m3529minusWZ4Q5Ns(byte b2, int i) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) - i);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: minus-VKZWuLQ */
    private static final long m3528minusVKZWuLQ(byte b2, long j) {
        return ULong.m3668constructorimpl(ULong.m3668constructorimpl(((long) b2) & 255) - j);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: times-7apg3OU */
    private static final int m3546times7apg3OU(byte b2, byte b3) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) * UInt.m3589constructorimpl(b3 & MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: times-xj2QHRw */
    private static final int m3549timesxj2QHRw(byte b2, short s) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) * UInt.m3589constructorimpl(s & UShort.MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: times-WZ4Q5Ns */
    private static final int m3548timesWZ4Q5Ns(byte b2, int i) {
        return UInt.m3589constructorimpl(UInt.m3589constructorimpl(b2 & MAX_VALUE) * i);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: times-VKZWuLQ */
    private static final long m3547timesVKZWuLQ(byte b2, long j) {
        return ULong.m3668constructorimpl(ULong.m3668constructorimpl(((long) b2) & 255) * j);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: div-7apg3OU */
    private static final int m3514div7apg3OU(byte b2, byte b3) {
        return UByte$$ExternalSyntheticBackport0.m885m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(b3 & MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: div-xj2QHRw */
    private static final int m3517divxj2QHRw(byte b2, short s) {
        return UByte$$ExternalSyntheticBackport0.m885m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(s & UShort.MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: div-WZ4Q5Ns */
    private static final int m3516divWZ4Q5Ns(byte b2, int i) {
        return UByte$$ExternalSyntheticBackport0.m885m(UInt.m3589constructorimpl(b2 & MAX_VALUE), i);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: div-VKZWuLQ */
    private static final long m3515divVKZWuLQ(byte b2, long j) {
        return UByte$$ExternalSyntheticBackport3.m888m(ULong.m3668constructorimpl(((long) b2) & 255), j);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: rem-7apg3OU */
    private static final int m3542rem7apg3OU(byte b2, byte b3) {
        return UByte$$ExternalSyntheticBackport1.m886m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(b3 & MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: rem-xj2QHRw */
    private static final int m3545remxj2QHRw(byte b2, short s) {
        return UByte$$ExternalSyntheticBackport1.m886m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(s & UShort.MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: rem-WZ4Q5Ns */
    private static final int m3544remWZ4Q5Ns(byte b2, int i) {
        return UByte$$ExternalSyntheticBackport1.m886m(UInt.m3589constructorimpl(b2 & MAX_VALUE), i);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: rem-VKZWuLQ */
    private static final long m3543remVKZWuLQ(byte b2, long j) {
        return UByte$$ExternalSyntheticBackport2.m887m(ULong.m3668constructorimpl(((long) b2) & 255), j);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: floorDiv-7apg3OU */
    private static final int m3520floorDiv7apg3OU(byte b2, byte b3) {
        return UByte$$ExternalSyntheticBackport0.m885m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(b3 & MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: floorDiv-xj2QHRw */
    private static final int m3523floorDivxj2QHRw(byte b2, short s) {
        return UByte$$ExternalSyntheticBackport0.m885m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(s & UShort.MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: floorDiv-WZ4Q5Ns */
    private static final int m3522floorDivWZ4Q5Ns(byte b2, int i) {
        return UByte$$ExternalSyntheticBackport0.m885m(UInt.m3589constructorimpl(b2 & MAX_VALUE), i);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: floorDiv-VKZWuLQ */
    private static final long m3521floorDivVKZWuLQ(byte b2, long j) {
        return UByte$$ExternalSyntheticBackport3.m888m(ULong.m3668constructorimpl(((long) b2) & 255), j);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: mod-7apg3OU */
    private static final byte m3531mod7apg3OU(byte b2, byte b3) {
        return m3512constructorimpl((byte) UByte$$ExternalSyntheticBackport1.m886m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(b3 & MAX_VALUE)));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: mod-xj2QHRw */
    private static final short m3534modxj2QHRw(byte b2, short s) {
        return UShort.m3775constructorimpl((short) UByte$$ExternalSyntheticBackport1.m886m(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(s & UShort.MAX_VALUE)));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: mod-WZ4Q5Ns */
    private static final int m3533modWZ4Q5Ns(byte b2, int i) {
        return UByte$$ExternalSyntheticBackport1.m886m(UInt.m3589constructorimpl(b2 & MAX_VALUE), i);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: mod-VKZWuLQ */
    private static final long m3532modVKZWuLQ(byte b2, long j) {
        return UByte$$ExternalSyntheticBackport2.m887m(ULong.m3668constructorimpl(((long) b2) & 255), j);
    }

    @InlineOnly
    /* JADX INFO: renamed from: inc-w2LRezQ */
    private static final byte m3525incw2LRezQ(byte b2) {
        return m3512constructorimpl((byte) (b2 + 1));
    }

    @InlineOnly
    /* JADX INFO: renamed from: dec-w2LRezQ */
    private static final byte m3513decw2LRezQ(byte b2) {
        return m3512constructorimpl((byte) (b2 - 1));
    }

    @InlineOnly
    /* JADX INFO: renamed from: rangeTo-7apg3OU */
    private static final UIntRange m3540rangeTo7apg3OU(byte b2, byte b3) {
        return new UIntRange(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(b3 & MAX_VALUE), null);
    }

    @SinceKotlin(version = "1.9")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    /* JADX INFO: renamed from: rangeUntil-7apg3OU */
    private static final UIntRange m3541rangeUntil7apg3OU(byte b2, byte b3) {
        return URangesKt.m4779untilJ1ME1BU(UInt.m3589constructorimpl(b2 & MAX_VALUE), UInt.m3589constructorimpl(b3 & MAX_VALUE));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: and-7apg3OU */
    private static final byte m3505and7apg3OU(byte b2, byte b3) {
        return m3512constructorimpl((byte) (b2 & b3));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: or-7apg3OU */
    private static final byte m3535or7apg3OU(byte b2, byte b3) {
        return m3512constructorimpl((byte) (b2 | b3));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: xor-7apg3OU */
    private static final byte m3561xor7apg3OU(byte b2, byte b3) {
        return m3512constructorimpl((byte) (b2 ^ b3));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: inv-w2LRezQ */
    private static final byte m3526invw2LRezQ(byte b2) {
        return m3512constructorimpl((byte) (~b2));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toUShort-Mh2AYeg */
    private static final short m3560toUShortMh2AYeg(byte b2) {
        return UShort.m3775constructorimpl((short) (b2 & 255));
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toUInt-pVg5ArA */
    private static final int m3558toUIntpVg5ArA(byte b2) {
        return UInt.m3589constructorimpl(b2 & MAX_VALUE);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toULong-s-VKNKU */
    private static final long m3559toULongsVKNKU(byte b2) {
        return ULong.m3668constructorimpl(((long) b2) & 255);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toFloat-impl */
    private static final float m3552toFloatimpl(byte b2) {
        return (float) UnsignedKt.uintToDouble(b2 & MAX_VALUE);
    }

    @IntrinsicConstEvaluation
    @InlineOnly
    /* JADX INFO: renamed from: toDouble-impl */
    private static final double m3551toDoubleimpl(byte b2) {
        return UnsignedKt.uintToDouble(b2 & MAX_VALUE);
    }

    @IntrinsicConstEvaluation
    /* JADX INFO: renamed from: toString-impl */
    public static String m3556toStringimpl(byte b2) {
        return String.valueOf(b2 & MAX_VALUE);
    }

    @IntrinsicConstEvaluation
    public String toString() {
        return m3556toStringimpl(this.data);
    }
}
