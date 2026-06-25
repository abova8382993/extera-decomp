package kotlin.collections;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m876d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u001a)\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\u0006\u0010\u0007\u001a)\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\n\u0010\u000b\u001a)\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\r\u0010\u000e\u001a)\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\u000f\u0010\u0010\u001a)\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\u0012\u0010\u0013\u001a)\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\u0014\u0010\u0015\u001a)\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\u0017\u0010\u0018\u001a)\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0083\u0080\u0004¢\u0006\u0004\b\u0019\u0010\u001a\u001a)\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0081\u0080\u0004¢\u0006\u0004\b\u001e\u0010\u000b\u001a)\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0081\u0080\u0004¢\u0006\u0004\b\u001f\u0010\u0010\u001a)\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0081\u0080\u0004¢\u0006\u0004\b \u0010\u0015\u001a)\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0081\u0080\u0004¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, m877d2 = {"partition", _UrlKt.FRAGMENT_ENCODE_SET, "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "quickSort", _UrlKt.FRAGMENT_ENCODE_SET, "quickSort-4UcCI2c", "([BII)V", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort-Aa5vz7o", "([SII)V", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "quickSort-oBK06Vg", "([III)V", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "quickSort--nroSd4", "([JII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-Aa5vz7o", "sortArray-oBK06Vg", "sortArray--nroSd4", "kotlin-stdlib"}, m878k = 2, m879mv = {2, 3, 0}, m881xi = 48)
public final class UArraySortingKt {
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m3954partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM3570getw2LRezQ = UByteArray.m3570getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM3570getw2LRezQ = UByteArray.m3570getw2LRezQ(bArr, i) & UByte.MAX_VALUE;
                i3 = bM3570getw2LRezQ & UByte.MAX_VALUE;
                if (Intrinsics.compare(iM3570getw2LRezQ, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m3570getw2LRezQ(bArr, i2) & UByte.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM3570getw2LRezQ2 = UByteArray.m3570getw2LRezQ(bArr, i);
                UByteArray.m3575setVurrAj0(bArr, i, UByteArray.m3570getw2LRezQ(bArr, i2));
                UByteArray.m3575setVurrAj0(bArr, i2, bM3570getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m3958quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM3954partition4UcCI2c = m3954partition4UcCI2c(bArr, i, i2);
        int i3 = iM3954partition4UcCI2c - 1;
        if (i < i3) {
            m3958quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM3954partition4UcCI2c < i2) {
            m3958quickSort4UcCI2c(bArr, iM3954partition4UcCI2c, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m3955partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM3833getMh2AYeg = UShortArray.m3833getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM3833getMh2AYeg = UShortArray.m3833getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM3833getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM3833getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m3833getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM3833getMh2AYeg2 = UShortArray.m3833getMh2AYeg(sArr, i);
                UShortArray.m3838set01HTLdE(sArr, i, UShortArray.m3833getMh2AYeg(sArr, i2));
                UShortArray.m3838set01HTLdE(sArr, i2, sM3833getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m3959quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM3955partitionAa5vz7o = m3955partitionAa5vz7o(sArr, i, i2);
        int i3 = iM3955partitionAa5vz7o - 1;
        if (i < i3) {
            m3959quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM3955partitionAa5vz7o < i2) {
            m3959quickSortAa5vz7o(sArr, iM3955partitionAa5vz7o, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m3956partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM3649getpVg5ArA = UIntArray.m3649getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (Integer.compare(UIntArray.m3649getpVg5ArA(iArr, i) ^ Integer.MIN_VALUE, iM3649getpVg5ArA ^ Integer.MIN_VALUE) < 0) {
                i++;
            }
            while (Integer.compare(UIntArray.m3649getpVg5ArA(iArr, i2) ^ Integer.MIN_VALUE, iM3649getpVg5ArA ^ Integer.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM3649getpVg5ArA2 = UIntArray.m3649getpVg5ArA(iArr, i);
                UIntArray.m3654setVXSXFK8(iArr, i, UIntArray.m3649getpVg5ArA(iArr, i2));
                UIntArray.m3654setVXSXFK8(iArr, i2, iM3649getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m3960quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM3956partitionoBK06Vg = m3956partitionoBK06Vg(iArr, i, i2);
        int i3 = iM3956partitionoBK06Vg - 1;
        if (i < i3) {
            m3960quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM3956partitionoBK06Vg < i2) {
            m3960quickSortoBK06Vg(iArr, iM3956partitionoBK06Vg, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m3953partitionnroSd4(long[] jArr, int i, int i2) {
        long jM3728getsVKNKU = ULongArray.m3728getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (Long.compare(ULongArray.m3728getsVKNKU(jArr, i) ^ Long.MIN_VALUE, jM3728getsVKNKU ^ Long.MIN_VALUE) < 0) {
                i++;
            }
            while (Long.compare(ULongArray.m3728getsVKNKU(jArr, i2) ^ Long.MIN_VALUE, jM3728getsVKNKU ^ Long.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM3728getsVKNKU2 = ULongArray.m3728getsVKNKU(jArr, i);
                ULongArray.m3733setk8EXiF4(jArr, i, ULongArray.m3728getsVKNKU(jArr, i2));
                ULongArray.m3733setk8EXiF4(jArr, i2, jM3728getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m3957quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM3953partitionnroSd4 = m3953partitionnroSd4(jArr, i, i2);
        int i3 = iM3953partitionnroSd4 - 1;
        if (i < i3) {
            m3957quickSortnroSd4(jArr, i, i3);
        }
        if (iM3953partitionnroSd4 < i2) {
            m3957quickSortnroSd4(jArr, iM3953partitionnroSd4, i2);
        }
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m3962sortArray4UcCI2c(byte[] bArr, int i, int i2) {
        m3958quickSort4UcCI2c(bArr, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m3963sortArrayAa5vz7o(short[] sArr, int i, int i2) {
        m3959quickSortAa5vz7o(sArr, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m3964sortArrayoBK06Vg(int[] iArr, int i, int i2) {
        m3960quickSortoBK06Vg(iArr, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m3961sortArraynroSd4(long[] jArr, int i, int i2) {
        m3957quickSortnroSd4(jArr, i, i2 - 1);
    }
}
