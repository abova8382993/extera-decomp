package p026j$.time.chrono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import p026j$.time.C2284c;
import p026j$.time.C2351g;
import p026j$.time.C2354j;
import p026j$.time.Instant;
import p026j$.time.ZoneId;
import p026j$.time.format.EnumC2350z;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;

/* JADX INFO: renamed from: j$.time.chrono.k */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public interface InterfaceC2304k extends Comparable {
    /* JADX INFO: renamed from: of */
    static InterfaceC2304k m718of(String str) {
        ConcurrentHashMap concurrentHashMap = AbstractC2285a.f758a;
        Objects.requireNonNull(str, "id");
        while (true) {
            ConcurrentHashMap concurrentHashMap2 = AbstractC2285a.f758a;
            InterfaceC2304k interfaceC2304k = (InterfaceC2304k) concurrentHashMap2.get(str);
            if (interfaceC2304k == null) {
                interfaceC2304k = (InterfaceC2304k) AbstractC2285a.f759b.get(str);
            }
            if (interfaceC2304k != null) {
                return interfaceC2304k;
            }
            if (concurrentHashMap2.get("ISO") != null) {
                for (InterfaceC2304k interfaceC2304k2 : ServiceLoader.load(InterfaceC2304k.class)) {
                    if (str.equals(interfaceC2304k2.getId()) || str.equals(interfaceC2304k2.mo709v())) {
                        return interfaceC2304k2;
                    }
                }
                C2351g.m796a("Unknown chronology: ".concat(str));
                return null;
            }
            C2307n c2307n = C2307n.f786l;
            c2307n.getClass();
            AbstractC2285a.m680B(c2307n, "Hijrah-umalqura");
            C2314u c2314u = C2314u.f807c;
            c2314u.getClass();
            AbstractC2285a.m680B(c2314u, "Japanese");
            C2319z c2319z = C2319z.f819c;
            c2319z.getClass();
            AbstractC2285a.m680B(c2319z, "Minguo");
            C2296f0 c2296f0 = C2296f0.f770c;
            c2296f0.getClass();
            AbstractC2285a.m680B(c2296f0, "ThaiBuddhist");
            try {
                for (AbstractC2285a abstractC2285a : Arrays.asList(new AbstractC2285a[0])) {
                    if (!abstractC2285a.getId().equals("ISO")) {
                        AbstractC2285a.m680B(abstractC2285a, abstractC2285a.getId());
                    }
                }
                C2311r c2311r = C2311r.f803c;
                c2311r.getClass();
                AbstractC2285a.m680B(c2311r, "ISO");
            } catch (Throwable th) {
                throw new ServiceConfigurationError(th.getMessage(), th);
            }
        }
    }

    /* JADX INFO: renamed from: s */
    static InterfaceC2304k m719s(InterfaceC2376l interfaceC2376l) {
        Objects.requireNonNull(interfaceC2376l, "temporal");
        InterfaceC2304k interfaceC2304k = (InterfaceC2304k) interfaceC2376l.mo568d(AbstractC2381q.f959b);
        C2311r c2311r = C2311r.f803c;
        if (interfaceC2304k != null) {
            return interfaceC2304k;
        }
        Objects.requireNonNull(c2311r, "defaultObj");
        return c2311r;
    }

    /* JADX INFO: renamed from: A */
    List mo701A();

    /* JADX INFO: renamed from: C */
    InterfaceC2305l mo702C(int i);

    /* JADX INFO: renamed from: D */
    int mo703D(InterfaceC2305l interfaceC2305l, int i);

    /* JADX INFO: renamed from: H */
    InterfaceC2287b mo704H(InterfaceC2376l interfaceC2376l);

    /* JADX INFO: renamed from: L */
    InterfaceC2287b mo705L();

    /* JADX INFO: renamed from: M */
    default ChronoLocalDateTime mo720M(InterfaceC2376l interfaceC2376l) {
        try {
            return mo704H(interfaceC2376l).mo596K(C2354j.m806G(interfaceC2376l));
        } catch (C2284c e) {
            throw new C2284c("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + interfaceC2376l.getClass(), e);
        }
    }

    /* JADX INFO: renamed from: Q */
    InterfaceC2287b mo706Q(int i, int i2, int i3);

    /* JADX INFO: renamed from: S */
    InterfaceC2287b mo685S(Map map, EnumC2350z enumC2350z);

    /* JADX INFO: renamed from: T */
    ChronoZonedDateTime mo707T(Instant instant, ZoneId zoneId);

    boolean equals(Object obj);

    String getId();

    int hashCode();

    /* JADX INFO: renamed from: r */
    InterfaceC2287b mo708r(long j);

    String toString();

    /* JADX INFO: renamed from: v */
    String mo709v();

    /* JADX INFO: renamed from: w */
    default ChronoZonedDateTime mo721w(InterfaceC2376l interfaceC2376l) {
        try {
            ZoneId zoneIdM648t = ZoneId.m648t(interfaceC2376l);
            try {
                return mo707T(Instant.m577B(interfaceC2376l), zoneIdM648t);
            } catch (C2284c unused) {
                return C2303j.m715B(zoneIdM648t, null, C2295f.m695t(this, this.mo720M(interfaceC2376l)));
            }
        } catch (C2284c e) {
            throw new C2284c("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + interfaceC2376l.getClass(), e);
        }
    }

    /* JADX INFO: renamed from: x */
    InterfaceC2287b mo710x(int i, int i2);

    /* JADX INFO: renamed from: z */
    C2384t mo711z(EnumC2365a enumC2365a);
}
