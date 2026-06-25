package p026j$.time;

import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.m */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC2357m {

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ int[] f923a;

    static {
        int[] iArr = new int[EnumC2365a.values().length];
        f923a = iArr;
        try {
            iArr[EnumC2365a.DAY_OF_MONTH.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f923a[EnumC2365a.MONTH_OF_YEAR.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
    }
}
