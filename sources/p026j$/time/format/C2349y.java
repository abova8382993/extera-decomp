package p026j$.time.format;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import p026j$.time.AbstractC2320d;
import p026j$.time.C2284c;
import p026j$.time.C2351g;
import p026j$.time.C2354j;
import p026j$.time.C2388x;
import p026j$.time.Instant;
import p026j$.time.LocalDate;
import p026j$.time.Period;
import p026j$.time.ZoneId;
import p026j$.time.ZoneOffset;
import p026j$.time.chrono.InterfaceC2287b;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.y */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2349y implements InterfaceC2376l {

    /* JADX INFO: renamed from: b */
    public ZoneId f901b;

    /* JADX INFO: renamed from: c */
    public InterfaceC2304k f902c;

    /* JADX INFO: renamed from: d */
    public boolean f903d;

    /* JADX INFO: renamed from: e */
    public EnumC2350z f904e;

    /* JADX INFO: renamed from: f */
    public InterfaceC2287b f905f;

    /* JADX INFO: renamed from: g */
    public C2354j f906g;

    /* JADX INFO: renamed from: a */
    public final Map f900a = new HashMap();

    /* JADX INFO: renamed from: h */
    public Period f907h = Period.f739d;

    /* JADX INFO: renamed from: A */
    public final void m788A(InterfaceC2380p interfaceC2380p, EnumC2365a enumC2365a, Long l) {
        Long l2 = (Long) ((HashMap) this.f900a).put(enumC2365a, l);
        if (l2 == null || l2.longValue() == l.longValue()) {
            return;
        }
        throw new C2284c("Conflict found: " + enumC2365a + " " + l2 + " differs from " + enumC2365a + " " + l + " while resolving  " + interfaceC2380p);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f958a) {
            return this.f901b;
        }
        if (c2388x == AbstractC2381q.f959b) {
            return this.f902c;
        }
        if (c2388x == AbstractC2381q.f963f) {
            InterfaceC2287b interfaceC2287b = this.f905f;
            if (interfaceC2287b != null) {
                return LocalDate.m588G(interfaceC2287b);
            }
            return null;
        }
        if (c2388x == AbstractC2381q.f964g) {
            return this.f906g;
        }
        if (c2388x == AbstractC2381q.f961d) {
            Long l = (Long) ((HashMap) this.f900a).get(EnumC2365a.OFFSET_SECONDS);
            if (l != null) {
                return ZoneOffset.m653Z(l.intValue());
            }
            ZoneId zoneId = this.f901b;
            return zoneId instanceof ZoneOffset ? zoneId : c2388x.m855m(this);
        }
        if (c2388x == AbstractC2381q.f962e) {
            return c2388x.m855m(this);
        }
        if (c2388x == AbstractC2381q.f960c) {
            return null;
        }
        return c2388x.m855m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        if (((HashMap) this.f900a).containsKey(interfaceC2380p)) {
            return true;
        }
        InterfaceC2287b interfaceC2287b = this.f905f;
        if (interfaceC2287b != null && interfaceC2287b.mo571i(interfaceC2380p)) {
            return true;
        }
        C2354j c2354j = this.f906g;
        if (c2354j == null || !c2354j.mo571i(interfaceC2380p)) {
            return (interfaceC2380p == null || (interfaceC2380p instanceof EnumC2365a) || !interfaceC2380p.mo841t(this)) ? false : true;
        }
        return true;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        Objects.requireNonNull(interfaceC2380p, "field");
        Long l = (Long) ((HashMap) this.f900a).get(interfaceC2380p);
        if (l != null) {
            return l.longValue();
        }
        InterfaceC2287b interfaceC2287b = this.f905f;
        if (interfaceC2287b != null && interfaceC2287b.mo571i(interfaceC2380p)) {
            return this.f905f.mo572k(interfaceC2380p);
        }
        C2354j c2354j = this.f906g;
        if (c2354j != null && c2354j.mo571i(interfaceC2380p)) {
            return this.f906g.mo572k(interfaceC2380p);
        }
        if (interfaceC2380p instanceof EnumC2365a) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        return interfaceC2380p.mo837P(this);
    }

    /* JADX INFO: renamed from: r */
    public final void m789r(InterfaceC2376l interfaceC2376l) {
        Iterator it = ((HashMap) this.f900a).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            InterfaceC2380p interfaceC2380p = (InterfaceC2380p) entry.getKey();
            if (interfaceC2376l.mo571i(interfaceC2380p)) {
                try {
                    long jMo572k = interfaceC2376l.mo572k(interfaceC2380p);
                    long jLongValue = ((Long) entry.getValue()).longValue();
                    if (jMo572k != jLongValue) {
                        throw new C2284c("Conflict found: Field " + interfaceC2380p + " " + jMo572k + " differs from " + interfaceC2380p + " " + jLongValue + " derived from " + interfaceC2376l);
                    }
                    it.remove();
                } catch (RuntimeException unused) {
                    continue;
                }
            }
        }
    }

    /* JADX INFO: renamed from: s */
    public final void m790s() {
        if (((HashMap) this.f900a).containsKey(EnumC2365a.INSTANT_SECONDS)) {
            ZoneId zoneId = this.f901b;
            if (zoneId != null) {
                m791t(zoneId);
                return;
            }
            Long l = (Long) ((HashMap) this.f900a).get(EnumC2365a.OFFSET_SECONDS);
            if (l != null) {
                m791t(ZoneOffset.m653Z(l.intValue()));
            }
        }
    }

    /* JADX INFO: renamed from: t */
    public final void m791t(ZoneId zoneId) {
        Map map = this.f900a;
        EnumC2365a enumC2365a = EnumC2365a.INSTANT_SECONDS;
        m795z(this.f902c.mo707T(Instant.m578t(((Long) ((HashMap) map).remove(enumC2365a)).longValue(), 0), zoneId).mo668n());
        m788A(enumC2365a, EnumC2365a.SECOND_OF_DAY, Long.valueOf(r5.mo667l().m817e0()));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(this.f900a);
        sb.append(',');
        sb.append(this.f902c);
        if (this.f901b != null) {
            sb.append(',');
            sb.append(this.f901b);
        }
        if (this.f905f != null || this.f906g != null) {
            sb.append(" resolved to ");
            InterfaceC2287b interfaceC2287b = this.f905f;
            if (interfaceC2287b != null) {
                sb.append(interfaceC2287b);
                if (this.f906g != null) {
                    sb.append('T');
                    sb.append(this.f906g);
                }
            } else {
                sb.append(this.f906g);
            }
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: v */
    public final void m792v(long j, long j2, long j3, long j4) {
        if (this.f904e == EnumC2350z.LENIENT) {
            long jAddExact = Math.addExact(Math.addExact(Math.addExact(Math.multiplyExact(j, 3600000000000L), Math.multiplyExact(j2, 60000000000L)), Math.multiplyExact(j3, 1000000000L)), j4);
            m794x(C2354j.m808V(Math.floorMod(jAddExact, 86400000000000L)), Period.m636a(0, 0, (int) Math.floorDiv(jAddExact, 86400000000000L)));
            return;
        }
        EnumC2365a enumC2365a = EnumC2365a.MINUTE_OF_HOUR;
        int iM849a = enumC2365a.f942b.m849a(j2, enumC2365a);
        EnumC2365a enumC2365a2 = EnumC2365a.NANO_OF_SECOND;
        int iM849a2 = enumC2365a2.f942b.m849a(j4, enumC2365a2);
        if (this.f904e == EnumC2350z.SMART && j == 24 && iM849a == 0 && j3 == 0 && iM849a2 == 0) {
            m794x(C2354j.f914g, Period.m636a(0, 0, 1));
            return;
        }
        EnumC2365a enumC2365a3 = EnumC2365a.HOUR_OF_DAY;
        int iM849a3 = enumC2365a3.f942b.m849a(j, enumC2365a3);
        EnumC2365a enumC2365a4 = EnumC2365a.SECOND_OF_MINUTE;
        m794x(C2354j.m807P(iM849a3, iM849a, enumC2365a4.f942b.m849a(j3, enumC2365a4), iM849a2), Period.f739d);
    }

    /* JADX INFO: renamed from: w */
    public final void m793w() {
        Map map = this.f900a;
        EnumC2365a enumC2365a = EnumC2365a.CLOCK_HOUR_OF_DAY;
        if (((HashMap) map).containsKey(enumC2365a)) {
            long jLongValue = ((Long) ((HashMap) this.f900a).remove(enumC2365a)).longValue();
            EnumC2350z enumC2350z = this.f904e;
            if (enumC2350z == EnumC2350z.STRICT || (enumC2350z == EnumC2350z.SMART && jLongValue != 0)) {
                enumC2365a.m839X(jLongValue);
            }
            EnumC2365a enumC2365a2 = EnumC2365a.HOUR_OF_DAY;
            if (jLongValue == 24) {
                jLongValue = 0;
            }
            m788A(enumC2365a, enumC2365a2, Long.valueOf(jLongValue));
        }
        Map map2 = this.f900a;
        EnumC2365a enumC2365a3 = EnumC2365a.CLOCK_HOUR_OF_AMPM;
        if (((HashMap) map2).containsKey(enumC2365a3)) {
            long jLongValue2 = ((Long) ((HashMap) this.f900a).remove(enumC2365a3)).longValue();
            EnumC2350z enumC2350z2 = this.f904e;
            if (enumC2350z2 == EnumC2350z.STRICT || (enumC2350z2 == EnumC2350z.SMART && jLongValue2 != 0)) {
                enumC2365a3.m839X(jLongValue2);
            }
            m788A(enumC2365a3, EnumC2365a.HOUR_OF_AMPM, Long.valueOf(jLongValue2 != 12 ? jLongValue2 : 0L));
        }
        Map map3 = this.f900a;
        EnumC2365a enumC2365a4 = EnumC2365a.AMPM_OF_DAY;
        if (((HashMap) map3).containsKey(enumC2365a4)) {
            Map map4 = this.f900a;
            EnumC2365a enumC2365a5 = EnumC2365a.HOUR_OF_AMPM;
            if (((HashMap) map4).containsKey(enumC2365a5)) {
                long jLongValue3 = ((Long) ((HashMap) this.f900a).remove(enumC2365a4)).longValue();
                long jLongValue4 = ((Long) ((HashMap) this.f900a).remove(enumC2365a5)).longValue();
                if (this.f904e == EnumC2350z.LENIENT) {
                    m788A(enumC2365a4, EnumC2365a.HOUR_OF_DAY, Long.valueOf(Math.addExact(Math.multiplyExact(jLongValue3, 12L), jLongValue4)));
                } else {
                    enumC2365a4.m839X(jLongValue3);
                    enumC2365a5.m839X(jLongValue3);
                    m788A(enumC2365a4, EnumC2365a.HOUR_OF_DAY, Long.valueOf((jLongValue3 * 12) + jLongValue4));
                }
            }
        }
        Map map5 = this.f900a;
        EnumC2365a enumC2365a6 = EnumC2365a.NANO_OF_DAY;
        if (((HashMap) map5).containsKey(enumC2365a6)) {
            long jLongValue5 = ((Long) ((HashMap) this.f900a).remove(enumC2365a6)).longValue();
            if (this.f904e != EnumC2350z.LENIENT) {
                enumC2365a6.m839X(jLongValue5);
            }
            m788A(enumC2365a6, EnumC2365a.HOUR_OF_DAY, Long.valueOf(jLongValue5 / 3600000000000L));
            m788A(enumC2365a6, EnumC2365a.MINUTE_OF_HOUR, Long.valueOf((jLongValue5 / 60000000000L) % 60));
            m788A(enumC2365a6, EnumC2365a.SECOND_OF_MINUTE, Long.valueOf((jLongValue5 / 1000000000) % 60));
            m788A(enumC2365a6, EnumC2365a.NANO_OF_SECOND, Long.valueOf(jLongValue5 % 1000000000));
        }
        Map map6 = this.f900a;
        EnumC2365a enumC2365a7 = EnumC2365a.MICRO_OF_DAY;
        if (((HashMap) map6).containsKey(enumC2365a7)) {
            long jLongValue6 = ((Long) ((HashMap) this.f900a).remove(enumC2365a7)).longValue();
            if (this.f904e != EnumC2350z.LENIENT) {
                enumC2365a7.m839X(jLongValue6);
            }
            m788A(enumC2365a7, EnumC2365a.SECOND_OF_DAY, Long.valueOf(jLongValue6 / 1000000));
            m788A(enumC2365a7, EnumC2365a.MICRO_OF_SECOND, Long.valueOf(jLongValue6 % 1000000));
        }
        Map map7 = this.f900a;
        EnumC2365a enumC2365a8 = EnumC2365a.MILLI_OF_DAY;
        if (((HashMap) map7).containsKey(enumC2365a8)) {
            long jLongValue7 = ((Long) ((HashMap) this.f900a).remove(enumC2365a8)).longValue();
            if (this.f904e != EnumC2350z.LENIENT) {
                enumC2365a8.m839X(jLongValue7);
            }
            m788A(enumC2365a8, EnumC2365a.SECOND_OF_DAY, Long.valueOf(jLongValue7 / 1000));
            m788A(enumC2365a8, EnumC2365a.MILLI_OF_SECOND, Long.valueOf(jLongValue7 % 1000));
        }
        Map map8 = this.f900a;
        EnumC2365a enumC2365a9 = EnumC2365a.SECOND_OF_DAY;
        if (((HashMap) map8).containsKey(enumC2365a9)) {
            long jLongValue8 = ((Long) ((HashMap) this.f900a).remove(enumC2365a9)).longValue();
            if (this.f904e != EnumC2350z.LENIENT) {
                enumC2365a9.m839X(jLongValue8);
            }
            m788A(enumC2365a9, EnumC2365a.HOUR_OF_DAY, Long.valueOf(jLongValue8 / 3600));
            m788A(enumC2365a9, EnumC2365a.MINUTE_OF_HOUR, Long.valueOf((jLongValue8 / 60) % 60));
            m788A(enumC2365a9, EnumC2365a.SECOND_OF_MINUTE, Long.valueOf(jLongValue8 % 60));
        }
        Map map9 = this.f900a;
        EnumC2365a enumC2365a10 = EnumC2365a.MINUTE_OF_DAY;
        if (((HashMap) map9).containsKey(enumC2365a10)) {
            long jLongValue9 = ((Long) ((HashMap) this.f900a).remove(enumC2365a10)).longValue();
            if (this.f904e != EnumC2350z.LENIENT) {
                enumC2365a10.m839X(jLongValue9);
            }
            m788A(enumC2365a10, EnumC2365a.HOUR_OF_DAY, Long.valueOf(jLongValue9 / 60));
            m788A(enumC2365a10, EnumC2365a.MINUTE_OF_HOUR, Long.valueOf(jLongValue9 % 60));
        }
        Map map10 = this.f900a;
        EnumC2365a enumC2365a11 = EnumC2365a.NANO_OF_SECOND;
        if (((HashMap) map10).containsKey(enumC2365a11)) {
            long jLongValue10 = ((Long) ((HashMap) this.f900a).get(enumC2365a11)).longValue();
            EnumC2350z enumC2350z3 = this.f904e;
            EnumC2350z enumC2350z4 = EnumC2350z.LENIENT;
            if (enumC2350z3 != enumC2350z4) {
                enumC2365a11.m839X(jLongValue10);
            }
            Map map11 = this.f900a;
            EnumC2365a enumC2365a12 = EnumC2365a.MICRO_OF_SECOND;
            if (((HashMap) map11).containsKey(enumC2365a12)) {
                long jLongValue11 = ((Long) ((HashMap) this.f900a).remove(enumC2365a12)).longValue();
                if (this.f904e != enumC2350z4) {
                    enumC2365a12.m839X(jLongValue11);
                }
                jLongValue10 = (jLongValue10 % 1000) + (jLongValue11 * 1000);
                m788A(enumC2365a12, enumC2365a11, Long.valueOf(jLongValue10));
            }
            Map map12 = this.f900a;
            EnumC2365a enumC2365a13 = EnumC2365a.MILLI_OF_SECOND;
            if (((HashMap) map12).containsKey(enumC2365a13)) {
                long jLongValue12 = ((Long) ((HashMap) this.f900a).remove(enumC2365a13)).longValue();
                if (this.f904e != enumC2350z4) {
                    enumC2365a13.m839X(jLongValue12);
                }
                m788A(enumC2365a13, enumC2365a11, Long.valueOf((jLongValue10 % 1000000) + (jLongValue12 * 1000000)));
            }
        }
        Map map13 = this.f900a;
        EnumC2365a enumC2365a14 = EnumC2365a.HOUR_OF_DAY;
        if (((HashMap) map13).containsKey(enumC2365a14)) {
            Map map14 = this.f900a;
            EnumC2365a enumC2365a15 = EnumC2365a.MINUTE_OF_HOUR;
            if (((HashMap) map14).containsKey(enumC2365a15)) {
                Map map15 = this.f900a;
                EnumC2365a enumC2365a16 = EnumC2365a.SECOND_OF_MINUTE;
                if (((HashMap) map15).containsKey(enumC2365a16) && ((HashMap) this.f900a).containsKey(enumC2365a11)) {
                    m792v(((Long) ((HashMap) this.f900a).remove(enumC2365a14)).longValue(), ((Long) ((HashMap) this.f900a).remove(enumC2365a15)).longValue(), ((Long) ((HashMap) this.f900a).remove(enumC2365a16)).longValue(), ((Long) ((HashMap) this.f900a).remove(enumC2365a11)).longValue());
                }
            }
        }
    }

    /* JADX INFO: renamed from: x */
    public final void m794x(C2354j c2354j, Period period) {
        C2354j c2354j2 = this.f906g;
        if (c2354j2 == null) {
            this.f906g = c2354j;
            this.f907h = period;
            return;
        }
        if (!c2354j2.equals(c2354j)) {
            C2351g.m801f("Conflict found: Fields resolved to different times: ", this.f906g, " ", c2354j);
            return;
        }
        Period period2 = this.f907h;
        period2.getClass();
        Period period3 = Period.f739d;
        if (period2 == period3 || period == period3 || this.f907h.equals(period)) {
            this.f907h = period;
        } else {
            C2351g.m801f("Conflict found: Fields resolved to different excess periods: ", this.f907h, " ", period);
        }
    }

    /* JADX INFO: renamed from: z */
    public final void m795z(InterfaceC2287b interfaceC2287b) {
        InterfaceC2287b interfaceC2287b2 = this.f905f;
        if (interfaceC2287b2 != null) {
            if (interfaceC2287b == null || interfaceC2287b2.equals(interfaceC2287b)) {
                return;
            }
            C2351g.m801f("Conflict found: Fields resolved to two different dates: ", this.f905f, " ", interfaceC2287b);
            return;
        }
        if (interfaceC2287b != null) {
            if (this.f902c.equals(interfaceC2287b.mo607f())) {
                this.f905f = interfaceC2287b;
                return;
            }
            throw new C2284c("ChronoLocalDate must use the effective parsed chronology: " + this.f902c);
        }
    }
}
