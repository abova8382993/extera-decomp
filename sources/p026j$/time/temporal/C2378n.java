package p026j$.time.temporal;

/* JADX INFO: renamed from: j$.time.temporal.n */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class C2378n implements InterfaceC2377m {

    /* JADX INFO: renamed from: a */
    public final /* synthetic */ int f956a;

    /* JADX INFO: renamed from: b */
    public final /* synthetic */ int f957b;

    public /* synthetic */ C2378n(int i, int i2) {
        this.f956a = i2;
        this.f957b = i;
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        int i = this.f956a;
        int i2 = this.f957b;
        switch (i) {
            case 0:
                int iMo570g = temporal.mo570g(EnumC2365a.DAY_OF_WEEK);
                if (iMo570g == i2) {
                    return temporal;
                }
                return temporal.mo583b(iMo570g - i2 >= 0 ? 7 - r0 : -r0, ChronoUnit.DAYS);
            default:
                int iMo570g2 = temporal.mo570g(EnumC2365a.DAY_OF_WEEK);
                if (iMo570g2 == i2) {
                    return temporal;
                }
                return temporal.mo584c(i2 - iMo570g2 >= 0 ? 7 - r2 : -r2, ChronoUnit.DAYS);
        }
    }
}
