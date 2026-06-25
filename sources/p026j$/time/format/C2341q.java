package p026j$.time.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import p026j$.time.C2351g;
import p026j$.time.C2388x;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2373i;
import p026j$.time.temporal.AbstractC2375k;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.EnumC2371g;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.q */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2341q {

    /* JADX INFO: renamed from: f */
    public static final C2388x f877f = new C2388x(1);

    /* JADX INFO: renamed from: a */
    public C2341q f878a;

    /* JADX INFO: renamed from: b */
    public final C2341q f879b;

    /* JADX INFO: renamed from: c */
    public final List f880c;

    /* JADX INFO: renamed from: d */
    public final boolean f881d;

    /* JADX INFO: renamed from: e */
    public int f882e;

    static {
        HashMap map = new HashMap();
        map.put('G', EnumC2365a.ERA);
        map.put('y', EnumC2365a.YEAR_OF_ERA);
        map.put('u', EnumC2365a.YEAR);
        EnumC2371g enumC2371g = AbstractC2373i.f948a;
        map.put('Q', enumC2371g);
        map.put('q', enumC2371g);
        EnumC2365a enumC2365a = EnumC2365a.MONTH_OF_YEAR;
        map.put('M', enumC2365a);
        map.put('L', enumC2365a);
        map.put('D', EnumC2365a.DAY_OF_YEAR);
        map.put('d', EnumC2365a.DAY_OF_MONTH);
        map.put('F', EnumC2365a.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        EnumC2365a enumC2365a2 = EnumC2365a.DAY_OF_WEEK;
        map.put('E', enumC2365a2);
        map.put('c', enumC2365a2);
        map.put('e', enumC2365a2);
        map.put('a', EnumC2365a.AMPM_OF_DAY);
        map.put('H', EnumC2365a.HOUR_OF_DAY);
        map.put('k', EnumC2365a.CLOCK_HOUR_OF_DAY);
        map.put('K', EnumC2365a.HOUR_OF_AMPM);
        map.put('h', EnumC2365a.CLOCK_HOUR_OF_AMPM);
        map.put('m', EnumC2365a.MINUTE_OF_HOUR);
        map.put('s', EnumC2365a.SECOND_OF_MINUTE);
        EnumC2365a enumC2365a3 = EnumC2365a.NANO_OF_SECOND;
        map.put('S', enumC2365a3);
        map.put('A', EnumC2365a.MILLI_OF_DAY);
        map.put('n', enumC2365a3);
        map.put('N', EnumC2365a.NANO_OF_DAY);
        map.put('g', AbstractC2375k.f955a);
    }

    public C2341q() {
        this.f878a = this;
        this.f880c = new ArrayList();
        this.f882e = -1;
        this.f879b = null;
        this.f881d = false;
    }

    /* JADX INFO: renamed from: a */
    public final void m765a(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        C2328d c2328d = dateTimeFormatter.f825a;
        if (c2328d.f844b) {
            c2328d = new C2328d(c2328d.f843a, false);
        }
        m766b(c2328d);
    }

    /* JADX INFO: renamed from: b */
    public final int m766b(InterfaceC2329e interfaceC2329e) {
        Objects.requireNonNull(interfaceC2329e, "pp");
        C2341q c2341q = this.f878a;
        c2341q.getClass();
        ((ArrayList) c2341q.f880c).add(interfaceC2329e);
        this.f878a.f882e = -1;
        return ((ArrayList) r1.f880c).size() - 1;
    }

    /* JADX INFO: renamed from: c */
    public final void m767c(char c2) {
        m766b(new C2327c(c2));
    }

    /* JADX INFO: renamed from: d */
    public final void m768d(String str) {
        if (str.isEmpty()) {
            return;
        }
        if (str.length() == 1) {
            m766b(new C2327c(str.charAt(0)));
        } else {
            m766b(new C2337m(str));
        }
    }

    /* JADX INFO: renamed from: e */
    public final void m769e(EnumC2365a enumC2365a, Map map) {
        Objects.requireNonNull(enumC2365a, "field");
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        TextStyle textStyle = TextStyle.FULL;
        m766b(new C2338n(enumC2365a, textStyle, new C2323a(new C2346v(Collections.singletonMap(textStyle, linkedHashMap)))));
    }

    /* JADX INFO: renamed from: f */
    public final void m770f(C2332h c2332h) {
        C2332h c2332hMo751b;
        C2341q c2341q = this.f878a;
        int i = c2341q.f882e;
        if (i < 0) {
            c2341q.f882e = m766b(c2332h);
            return;
        }
        C2332h c2332h2 = (C2332h) ((ArrayList) c2341q.f880c).get(i);
        int i2 = c2332h.f848b;
        int i3 = c2332h.f849c;
        if (i2 == i3 && c2332h.f850d == EnumC2324a0.NOT_NEGATIVE) {
            c2332hMo751b = c2332h2.mo752c(i3);
            m766b(c2332h.mo751b());
            this.f878a.f882e = i;
        } else {
            c2332hMo751b = c2332h2.mo751b();
            this.f878a.f882e = m766b(c2332h);
        }
        ((ArrayList) this.f878a.f880c).set(i, c2332hMo751b);
    }

    /* JADX INFO: renamed from: g */
    public final void m771g(InterfaceC2380p interfaceC2380p, int i) {
        Objects.requireNonNull(interfaceC2380p, "field");
        if (i < 1 || i > 19) {
            C2351g.m803h("The width must be from 1 to 19 inclusive but was ", i);
        } else {
            m770f(new C2332h(interfaceC2380p, i, i, EnumC2324a0.NOT_NEGATIVE));
        }
    }

    /* JADX INFO: renamed from: h */
    public final void m772h(InterfaceC2380p interfaceC2380p, int i, int i2, EnumC2324a0 enumC2324a0) {
        if (i == i2 && enumC2324a0 == EnumC2324a0.NOT_NEGATIVE) {
            m771g(interfaceC2380p, i2);
            return;
        }
        Objects.requireNonNull(interfaceC2380p, "field");
        Objects.requireNonNull(enumC2324a0, "signStyle");
        if (i < 1 || i > 19) {
            C2351g.m803h("The minimum width must be from 1 to 19 inclusive but was ", i);
            return;
        }
        if (i2 < 1 || i2 > 19) {
            C2351g.m803h("The maximum width must be from 1 to 19 inclusive but was ", i2);
            return;
        }
        if (i2 >= i) {
            m770f(new C2332h(interfaceC2380p, i, i2, enumC2324a0));
            return;
        }
        throw new IllegalArgumentException("The maximum width must exceed or equal the minimum width but " + i2 + " < " + i);
    }

    /* JADX INFO: renamed from: i */
    public final void m773i() {
        C2341q c2341q = this.f878a;
        if (c2341q.f879b == null) {
            throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
        }
        int size = ((ArrayList) c2341q.f880c).size();
        C2341q c2341q2 = this.f878a;
        if (size <= 0) {
            this.f878a = c2341q2.f879b;
            return;
        }
        C2328d c2328d = new C2328d(c2341q2.f880c, c2341q2.f881d);
        this.f878a = this.f878a.f879b;
        m766b(c2328d);
    }

    /* JADX INFO: renamed from: j */
    public final void m774j() {
        C2341q c2341q = this.f878a;
        c2341q.f882e = -1;
        this.f878a = new C2341q(c2341q);
    }

    /* JADX INFO: renamed from: k */
    public final DateTimeFormatter m775k(EnumC2350z enumC2350z, InterfaceC2304k interfaceC2304k) {
        return m776l(Locale.getDefault(), enumC2350z, interfaceC2304k);
    }

    /* JADX INFO: renamed from: l */
    public final DateTimeFormatter m776l(Locale locale, EnumC2350z enumC2350z, InterfaceC2304k interfaceC2304k) {
        Objects.requireNonNull(locale, "locale");
        while (this.f878a.f879b != null) {
            m773i();
        }
        C2328d c2328d = new C2328d(this.f880c, false);
        C2348x c2348x = C2348x.f899a;
        return new DateTimeFormatter(c2328d, locale, enumC2350z, interfaceC2304k);
    }

    public C2341q(C2341q c2341q) {
        this.f878a = this;
        this.f880c = new ArrayList();
        this.f882e = -1;
        this.f879b = c2341q;
        this.f881d = true;
    }
}
