package p026j$.time.temporal;

import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: renamed from: j$.time.temporal.a */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public enum EnumC2365a implements InterfaceC2380p {
    NANO_OF_SECOND("NanoOfSecond", C2384t.m847f(0, 999999999)),
    NANO_OF_DAY("NanoOfDay", C2384t.m847f(0, 86399999999999L)),
    MICRO_OF_SECOND("MicroOfSecond", C2384t.m847f(0, 999999)),
    MICRO_OF_DAY("MicroOfDay", C2384t.m847f(0, 86399999999L)),
    MILLI_OF_SECOND("MilliOfSecond", C2384t.m847f(0, 999)),
    MILLI_OF_DAY("MilliOfDay", C2384t.m847f(0, 86399999)),
    SECOND_OF_MINUTE("SecondOfMinute", C2384t.m847f(0, 59), 0),
    SECOND_OF_DAY("SecondOfDay", C2384t.m847f(0, 86399)),
    MINUTE_OF_HOUR("MinuteOfHour", C2384t.m847f(0, 59), 0),
    MINUTE_OF_DAY("MinuteOfDay", C2384t.m847f(0, 1439)),
    HOUR_OF_AMPM("HourOfAmPm", C2384t.m847f(0, 11)),
    CLOCK_HOUR_OF_AMPM("ClockHourOfAmPm", C2384t.m847f(1, 12)),
    HOUR_OF_DAY("HourOfDay", C2384t.m847f(0, 23), 0),
    CLOCK_HOUR_OF_DAY("ClockHourOfDay", C2384t.m847f(1, 24)),
    AMPM_OF_DAY("AmPmOfDay", C2384t.m847f(0, 1), 0),
    DAY_OF_WEEK("DayOfWeek", C2384t.m847f(1, 7), 0),
    ALIGNED_DAY_OF_WEEK_IN_MONTH("AlignedDayOfWeekInMonth", C2384t.m847f(1, 7)),
    ALIGNED_DAY_OF_WEEK_IN_YEAR("AlignedDayOfWeekInYear", C2384t.m847f(1, 7)),
    DAY_OF_MONTH("DayOfMonth", C2384t.m848g(28, 31), 0),
    DAY_OF_YEAR("DayOfYear", C2384t.m848g(365, 366)),
    EPOCH_DAY("EpochDay", C2384t.m847f(-365243219162L, 365241780471L)),
    ALIGNED_WEEK_OF_MONTH("AlignedWeekOfMonth", C2384t.m848g(4, 5)),
    ALIGNED_WEEK_OF_YEAR("AlignedWeekOfYear", C2384t.m847f(1, 53)),
    MONTH_OF_YEAR("MonthOfYear", C2384t.m847f(1, 12), 0),
    PROLEPTIC_MONTH("ProlepticMonth", C2384t.m847f(-11999999988L, 11999999999L)),
    YEAR_OF_ERA("YearOfEra", C2384t.m848g(999999999, 1000000000)),
    YEAR("Year", C2384t.m847f(-999999999, 999999999), 0),
    ERA("Era", C2384t.m847f(0, 1), 0),
    INSTANT_SECONDS("InstantSeconds", C2384t.m847f(Long.MIN_VALUE, LongCompanionObject.MAX_VALUE)),
    OFFSET_SECONDS("OffsetSeconds", C2384t.m847f(-64800, 64800));


    /* JADX INFO: renamed from: a */
    public final String f941a;

    /* JADX INFO: renamed from: b */
    public final C2384t f942b;

    static {
        ChronoUnit chronoUnit = ChronoUnit.NANOS;
    }

    EnumC2365a(String str, C2384t c2384t) {
        this.f941a = str;
        this.f942b = c2384t;
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: B */
    public final C2384t mo835B(InterfaceC2376l interfaceC2376l) {
        return interfaceC2376l.mo573m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: I */
    public final C2384t mo836I() {
        return this.f942b;
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: P */
    public final long mo837P(InterfaceC2376l interfaceC2376l) {
        return interfaceC2376l.mo572k(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: V */
    public final Temporal mo838V(Temporal temporal, long j) {
        return temporal.mo582a(j, this);
    }

    /* JADX INFO: renamed from: X */
    public final void m839X(long j) {
        this.f942b.m850b(j, this);
    }

    /* JADX INFO: renamed from: Y */
    public final boolean m840Y() {
        return ordinal() < DAY_OF_WEEK.ordinal();
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    public final boolean isDateBased() {
        return ordinal() >= DAY_OF_WEEK.ordinal() && ordinal() <= ERA.ordinal();
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: t */
    public final boolean mo841t(InterfaceC2376l interfaceC2376l) {
        return interfaceC2376l.mo571i(this);
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f941a;
    }

    EnumC2365a(String str, C2384t c2384t, int i) {
        this.f941a = str;
        this.f942b = c2384t;
    }
}
