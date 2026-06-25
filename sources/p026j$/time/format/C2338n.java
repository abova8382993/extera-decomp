package p026j$.time.format;

import java.util.Iterator;
import java.util.Map;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.chrono.InterfaceC2305l;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.n */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2338n implements InterfaceC2329e {

    /* JADX INFO: renamed from: a */
    public final InterfaceC2380p f865a;

    /* JADX INFO: renamed from: b */
    public final TextStyle f866b;

    /* JADX INFO: renamed from: c */
    public final C2347w f867c;

    /* JADX INFO: renamed from: d */
    public volatile C2332h f868d;

    public C2338n(InterfaceC2380p interfaceC2380p, TextStyle textStyle, C2347w c2347w) {
        this.f865a = interfaceC2380p;
        this.f866b = textStyle;
        this.f867c = c2347w;
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        C2347w c2347w = this.f867c;
        InterfaceC2380p interfaceC2380p = this.f865a;
        int length = charSequence.length();
        if (i < 0 || i > length) {
            throw new IndexOutOfBoundsException();
        }
        boolean z = c2342r.f885c;
        DateTimeFormatter dateTimeFormatter = c2342r.f883a;
        TextStyle textStyle = z ? this.f866b : null;
        InterfaceC2304k interfaceC2304k = c2342r.m779c().f902c;
        if (interfaceC2304k == null && (interfaceC2304k = c2342r.f883a.f829e) == null) {
            interfaceC2304k = C2311r.f803c;
        }
        InterfaceC2304k interfaceC2304k2 = interfaceC2304k;
        Iterator itMo747e = (interfaceC2304k2 == null || interfaceC2304k2 == C2311r.f803c) ? c2347w.mo747e(interfaceC2380p, textStyle, dateTimeFormatter.f826b) : c2347w.mo746d(interfaceC2304k2, interfaceC2380p, textStyle, dateTimeFormatter.f826b);
        if (itMo747e != null) {
            while (itMo747e.hasNext()) {
                Map.Entry entry = (Map.Entry) itMo747e.next();
                String str = (String) entry.getKey();
                if (c2342r.m783g(str, 0, charSequence, i, str.length())) {
                    return c2342r.m782f(this.f865a, ((Long) entry.getValue()).longValue(), i, str.length() + i);
                }
            }
            if (interfaceC2380p == EnumC2365a.ERA && !c2342r.f885c) {
                Iterator it = interfaceC2304k2.mo701A().iterator();
                while (it.hasNext()) {
                    String string = ((InterfaceC2305l) it.next()).toString();
                    if (c2342r.m783g(string, 0, charSequence, i, string.length())) {
                        return c2342r.m782f(this.f865a, r8.getValue(), i, string.length() + i);
                    }
                }
            }
            if (c2342r.f885c) {
                return ~i;
            }
        }
        if (this.f868d == null) {
            this.f868d = new C2332h(this.f865a, 1, 19, EnumC2324a0.NORMAL);
        }
        return this.f868d.mo748B(c2342r, charSequence, i);
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public final boolean mo749t(C2344t c2344t, StringBuilder sb) {
        Long lM784a = c2344t.m784a(this.f865a);
        DateTimeFormatter dateTimeFormatter = c2344t.f892b;
        if (lM784a == null) {
            return false;
        }
        InterfaceC2304k interfaceC2304k = (InterfaceC2304k) c2344t.f891a.mo568d(AbstractC2381q.f959b);
        String strMo745c = (interfaceC2304k == null || interfaceC2304k == C2311r.f803c) ? this.f867c.mo745c(this.f865a, lM784a.longValue(), this.f866b, dateTimeFormatter.f826b) : this.f867c.mo744b(interfaceC2304k, this.f865a, lM784a.longValue(), this.f866b, dateTimeFormatter.f826b);
        if (strMo745c != null) {
            sb.append(strMo745c);
            return true;
        }
        if (this.f868d == null) {
            this.f868d = new C2332h(this.f865a, 1, 19, EnumC2324a0.NORMAL);
        }
        return this.f868d.mo749t(c2344t, sb);
    }

    public final String toString() {
        TextStyle textStyle = TextStyle.FULL;
        TextStyle textStyle2 = this.f866b;
        InterfaceC2380p interfaceC2380p = this.f865a;
        if (textStyle2 == textStyle) {
            return "Text(" + interfaceC2380p + ")";
        }
        return "Text(" + interfaceC2380p + "," + textStyle2 + ")";
    }
}
