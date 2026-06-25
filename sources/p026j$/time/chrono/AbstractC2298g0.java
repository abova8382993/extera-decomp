package p026j$.time.chrono;

import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.chrono.g0 */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC2298g0 {

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ int[] f776a;

    static {
        int[] iArr = new int[EnumC2365a.values().length];
        f776a = iArr;
        try {
            iArr[EnumC2365a.DAY_OF_MONTH.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f776a[EnumC2365a.DAY_OF_YEAR.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f776a[EnumC2365a.ALIGNED_WEEK_OF_MONTH.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f776a[EnumC2365a.YEAR_OF_ERA.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f776a[EnumC2365a.PROLEPTIC_MONTH.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f776a[EnumC2365a.YEAR.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f776a[EnumC2365a.ERA.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
    }
}
