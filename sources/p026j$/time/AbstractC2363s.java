package p026j$.time;

import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.s */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC2363s {

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ int[] f934a;

    /* JADX INFO: renamed from: b */
    public static final /* synthetic */ int[] f935b;

    static {
        int[] iArr = new int[ChronoUnit.values().length];
        f935b = iArr;
        try {
            iArr[ChronoUnit.YEARS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f935b[ChronoUnit.DECADES.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f935b[ChronoUnit.CENTURIES.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f935b[ChronoUnit.MILLENNIA.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f935b[ChronoUnit.ERAS.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        int[] iArr2 = new int[EnumC2365a.values().length];
        f934a = iArr2;
        try {
            iArr2[EnumC2365a.YEAR_OF_ERA.ordinal()] = 1;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f934a[EnumC2365a.YEAR.ordinal()] = 2;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f934a[EnumC2365a.ERA.ordinal()] = 3;
        } catch (NoSuchFieldError unused8) {
        }
    }
}
