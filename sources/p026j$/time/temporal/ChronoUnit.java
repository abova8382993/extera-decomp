package p026j$.time.temporal;

import kotlin.jvm.internal.LongCompanionObject;
import p026j$.time.Duration;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public enum ChronoUnit implements InterfaceC2382r {
    NANOS("Nanos"),
    MICROS("Micros"),
    MILLIS("Millis"),
    SECONDS("Seconds"),
    MINUTES("Minutes"),
    HOURS("Hours"),
    HALF_DAYS("HalfDays"),
    DAYS("Days"),
    WEEKS("Weeks"),
    MONTHS("Months"),
    YEARS("Years"),
    DECADES("Decades"),
    CENTURIES("Centuries"),
    MILLENNIA("Millennia"),
    ERAS("Eras"),
    FOREVER("Forever");


    /* JADX INFO: renamed from: a */
    public final String f939a;

    static {
        Duration.m575G(1L);
        Duration.m575G(1000L);
        Duration.m575G(1000000L);
        Duration.ofSeconds(1L);
        Duration.ofSeconds(60L);
        Duration.ofSeconds(3600L);
        Duration.ofSeconds(43200L);
        Duration.ofSeconds(86400L);
        Duration.ofSeconds(604800L);
        Duration.ofSeconds(2629746L);
        Duration.ofSeconds(31556952L);
        Duration.ofSeconds(315569520L);
        Duration.ofSeconds(3155695200L);
        Duration.ofSeconds(31556952000L);
        Duration.ofSeconds(31556952000000000L);
        Duration.ofSeconds(LongCompanionObject.MAX_VALUE, 999999999L);
    }

    ChronoUnit(String str) {
        this.f939a = str;
    }

    @Override // p026j$.time.temporal.InterfaceC2382r
    public long between(Temporal temporal, Temporal temporal2) {
        return temporal.mo586o(temporal2, this);
    }

    @Override // p026j$.time.temporal.InterfaceC2382r
    /* JADX INFO: renamed from: t */
    public final Temporal mo834t(Temporal temporal, long j) {
        return temporal.mo583b(j, this);
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f939a;
    }
}
