package p026j$.time.temporal;

import java.util.Map;
import p026j$.time.C2284c;
import p026j$.time.C2351g;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.format.C2349y;
import p026j$.time.format.EnumC2350z;

/* JADX INFO: renamed from: j$.time.temporal.j */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public enum EnumC2374j implements InterfaceC2380p {
    JULIAN_DAY("JulianDay", 2440588),
    MODIFIED_JULIAN_DAY("ModifiedJulianDay", 40587),
    RATA_DIE("RataDie", 719163);

    private static final long serialVersionUID = -7501623920830201812L;

    /* JADX INFO: renamed from: a */
    public final transient String f952a;

    /* JADX INFO: renamed from: b */
    public final transient C2384t f953b;

    /* JADX INFO: renamed from: c */
    public final transient long f954c;

    static {
        ChronoUnit chronoUnit = ChronoUnit.NANOS;
    }

    EnumC2374j(String str, long j) {
        this.f952a = str;
        this.f953b = C2384t.m847f((-365243219162L) + j, 365241780471L + j);
        this.f954c = j;
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: B */
    public final C2384t mo835B(InterfaceC2376l interfaceC2376l) {
        if (interfaceC2376l.mo571i(EnumC2365a.EPOCH_DAY)) {
            return this.f953b;
        }
        C2351g.m804i("Unsupported field: ", this);
        return null;
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: G */
    public final InterfaceC2376l mo842G(Map map, C2349y c2349y, EnumC2350z enumC2350z) {
        long jLongValue = ((Long) map.remove(this)).longValue();
        InterfaceC2304k interfaceC2304kM719s = InterfaceC2304k.m719s(c2349y);
        EnumC2350z enumC2350z2 = EnumC2350z.LENIENT;
        long j = this.f954c;
        if (enumC2350z == enumC2350z2) {
            return interfaceC2304kM719s.mo708r(Math.subtractExact(jLongValue, j));
        }
        this.f953b.m850b(jLongValue, this);
        return interfaceC2304kM719s.mo708r(jLongValue - j);
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: I */
    public final C2384t mo836I() {
        return this.f953b;
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: P */
    public final long mo837P(InterfaceC2376l interfaceC2376l) {
        return interfaceC2376l.mo572k(EnumC2365a.EPOCH_DAY) + this.f954c;
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: V */
    public final Temporal mo838V(Temporal temporal, long j) {
        if (this.f953b.m853e(j)) {
            return temporal.mo582a(Math.subtractExact(j, this.f954c), EnumC2365a.EPOCH_DAY);
        }
        throw new C2284c("Invalid value: " + this.f952a + " " + j);
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    public final boolean isDateBased() {
        return true;
    }

    @Override // p026j$.time.temporal.InterfaceC2380p
    /* JADX INFO: renamed from: t */
    public final boolean mo841t(InterfaceC2376l interfaceC2376l) {
        return interfaceC2376l.mo571i(EnumC2365a.EPOCH_DAY);
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f952a;
    }
}
