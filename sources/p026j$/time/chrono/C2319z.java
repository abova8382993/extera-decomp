package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import p026j$.time.AbstractC2283b;
import p026j$.time.C2351g;
import p026j$.time.Instant;
import p026j$.time.LocalDate;
import p026j$.time.ZoneId;
import p026j$.time.format.EnumC2350z;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;

/* JADX INFO: renamed from: j$.time.chrono.z */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2319z extends AbstractC2285a implements Serializable {

    /* JADX INFO: renamed from: c */
    public static final C2319z f819c = new C2319z();
    private static final long serialVersionUID = 1039765215346859963L;

    private C2319z() {
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: A */
    public final List mo701A() {
        return AbstractC2283b.m674c(EnumC2290c0.values());
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: C */
    public final InterfaceC2305l mo702C(int i) {
        if (i == 0) {
            return EnumC2290c0.BEFORE_ROC;
        }
        if (i == 1) {
            return EnumC2290c0.ROC;
        }
        C2351g.m797b("Invalid era: ", i);
        return null;
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: D */
    public final int mo703D(InterfaceC2305l interfaceC2305l, int i) {
        if (interfaceC2305l instanceof EnumC2290c0) {
            return interfaceC2305l == EnumC2290c0.ROC ? i : 1 - i;
        }
        throw new ClassCastException("Era must be MinguoEra");
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: H */
    public final InterfaceC2287b mo704H(InterfaceC2376l interfaceC2376l) {
        return interfaceC2376l instanceof C2288b0 ? (C2288b0) interfaceC2376l : new C2288b0(LocalDate.m588G(interfaceC2376l));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: L */
    public final InterfaceC2287b mo705L() {
        return new C2288b0(LocalDate.m588G(LocalDate.m589c0(AbstractC2283b.m675d())));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: Q */
    public final InterfaceC2287b mo706Q(int i, int i2, int i3) {
        return new C2288b0(LocalDate.m593of(i + 1911, i2, i3));
    }

    @Override // p026j$.time.chrono.AbstractC2285a, p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: S */
    public final InterfaceC2287b mo685S(Map map, EnumC2350z enumC2350z) {
        return (C2288b0) super.mo685S(map, enumC2350z);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: T */
    public final ChronoZonedDateTime mo707T(Instant instant, ZoneId zoneId) {
        return C2303j.m716G(this, instant, zoneId);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    public final String getId() {
        return "Minguo";
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: r */
    public final InterfaceC2287b mo708r(long j) {
        return new C2288b0(LocalDate.m590d0(j));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: v */
    public final String mo709v() {
        return "roc";
    }

    public Object writeReplace() {
        return new C2292d0((byte) 1, this);
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: x */
    public final InterfaceC2287b mo710x(int i, int i2) {
        return new C2288b0(LocalDate.m591e0(i + 1911, i2));
    }

    @Override // p026j$.time.chrono.InterfaceC2304k
    /* JADX INFO: renamed from: z */
    public final C2384t mo711z(EnumC2365a enumC2365a) {
        int i = AbstractC2318y.f818a[enumC2365a.ordinal()];
        if (i == 1) {
            C2384t c2384t = EnumC2365a.PROLEPTIC_MONTH.f942b;
            return C2384t.m847f(c2384t.f965a - 22932, c2384t.f968d - 22932);
        }
        if (i == 2) {
            C2384t c2384t2 = EnumC2365a.YEAR.f942b;
            return C2384t.m848g(c2384t2.f968d - 1911, (-c2384t2.f965a) + 1912);
        }
        if (i != 3) {
            return enumC2365a.f942b;
        }
        C2384t c2384t3 = EnumC2365a.YEAR.f942b;
        return C2384t.m847f(c2384t3.f965a - 1911, c2384t3.f968d - 1911);
    }
}
