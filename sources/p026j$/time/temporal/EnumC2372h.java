package p026j$.time.temporal;

import p026j$.time.Duration;

/* JADX INFO: renamed from: j$.time.temporal.h */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public enum EnumC2372h implements InterfaceC2382r {
    WEEK_BASED_YEARS("WeekBasedYears"),
    QUARTER_YEARS("QuarterYears");


    /* JADX INFO: renamed from: a */
    public final String f947a;

    static {
        Duration.ofSeconds(31556952L);
        Duration.ofSeconds(7889238L);
    }

    EnumC2372h(String str) {
        this.f947a = str;
    }

    @Override // p026j$.time.temporal.InterfaceC2382r
    public final long between(Temporal temporal, Temporal temporal2) {
        if (temporal.getClass() != temporal2.getClass()) {
            return temporal.mo586o(temporal2, this);
        }
        int i = AbstractC2366b.f943a[ordinal()];
        if (i == 1) {
            EnumC2371g enumC2371g = AbstractC2373i.f950c;
            return Math.subtractExact(temporal2.mo572k(enumC2371g), temporal.mo572k(enumC2371g));
        }
        if (i == 2) {
            return temporal.mo586o(temporal2, ChronoUnit.MONTHS) / 3;
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override // p026j$.time.temporal.InterfaceC2382r
    /* JADX INFO: renamed from: t */
    public final Temporal mo834t(Temporal temporal, long j) {
        int i = AbstractC2366b.f943a[ordinal()];
        if (i == 1) {
            return temporal.mo582a(Math.addExact(temporal.mo570g(r4), j), AbstractC2373i.f950c);
        }
        if (i == 2) {
            return temporal.mo583b(j / 4, ChronoUnit.YEARS).mo583b((j % 4) * 3, ChronoUnit.MONTHS);
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f947a;
    }
}
