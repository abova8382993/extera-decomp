package p026j$.time.chrono;

import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.chrono.e0 */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC2294e0 {

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ int[] f767a;

    static {
        int[] iArr = new int[EnumC2365a.values().length];
        f767a = iArr;
        try {
            iArr[EnumC2365a.PROLEPTIC_MONTH.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f767a[EnumC2365a.YEAR_OF_ERA.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f767a[EnumC2365a.YEAR.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
