package p026j$.time.chrono;

import p026j$.time.temporal.ChronoUnit;

/* JADX INFO: renamed from: j$.time.chrono.e */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC2293e {

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ int[] f766a;

    static {
        int[] iArr = new int[ChronoUnit.values().length];
        f766a = iArr;
        try {
            iArr[ChronoUnit.NANOS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f766a[ChronoUnit.MICROS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f766a[ChronoUnit.MILLIS.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f766a[ChronoUnit.SECONDS.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f766a[ChronoUnit.MINUTES.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f766a[ChronoUnit.HOURS.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f766a[ChronoUnit.HALF_DAYS.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
    }
}
