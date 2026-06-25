package p026j$.time.chrono;

import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.chrono.h */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC2299h {

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ int[] f777a;

    static {
        int[] iArr = new int[EnumC2365a.values().length];
        f777a = iArr;
        try {
            iArr[EnumC2365a.INSTANT_SECONDS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f777a[EnumC2365a.OFFSET_SECONDS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
    }
}
