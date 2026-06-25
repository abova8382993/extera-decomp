package p026j$.time.format;

import java.lang.ref.SoftReference;
import java.text.DateFormatSymbols;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.internal.url._UrlKt;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.zone.C2397h;

/* JADX INFO: renamed from: j$.time.format.p */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2340p extends C2339o {

    /* JADX INFO: renamed from: h */
    public static final Map f873h = new ConcurrentHashMap();

    /* JADX INFO: renamed from: e */
    public final TextStyle f874e;

    /* JADX INFO: renamed from: f */
    public final Map f875f;

    /* JADX INFO: renamed from: g */
    public final Map f876g;

    public C2340p(TextStyle textStyle) {
        super(AbstractC2381q.f962e, "ZoneText(" + textStyle + ")");
        this.f875f = new HashMap();
        this.f876g = new HashMap();
        Objects.requireNonNull(textStyle, "textStyle");
        this.f874e = textStyle;
    }

    @Override // p026j$.time.format.C2339o
    /* JADX INFO: renamed from: a */
    public final C2335k mo764a(C2342r c2342r) {
        C2335k c2335k;
        if (this.f874e == TextStyle.NARROW) {
            return super.mo764a(c2342r);
        }
        Locale locale = c2342r.f883a.f826b;
        boolean z = c2342r.f884b;
        Set set = C2397h.f1010d;
        int size = set.size();
        Map map = z ? this.f875f : this.f876g;
        Map.Entry entry = (Map.Entry) map.get(locale);
        if (entry != null && ((Integer) entry.getKey()).intValue() == size && (c2335k = (C2335k) ((SoftReference) entry.getValue()).get()) != null) {
            return c2335k;
        }
        C2335k c2335k2 = c2342r.f884b ? new C2335k(_UrlKt.FRAGMENT_ENCODE_SET, null, null) : new C2334j(_UrlKt.FRAGMENT_ENCODE_SET, null, null);
        for (String[] strArr : DateFormatSymbols.getInstance(locale).getZoneStrings()) {
            String str = strArr[0];
            if (set.contains(str)) {
                c2335k2.m761a(str, str);
                HashMap map2 = (HashMap) AbstractC2326b0.f838d;
                String str2 = (String) map2.get(str);
                if (str2 == null) {
                    HashMap map3 = (HashMap) AbstractC2326b0.f841g;
                    if (map3.containsKey(str)) {
                        str = (String) map3.get(str);
                        str2 = (String) map2.get(str);
                    }
                }
                if (str2 != null) {
                    Map map4 = (Map) ((HashMap) AbstractC2326b0.f840f).get(str2);
                    str = (map4 == null || !map4.containsKey(locale.getCountry())) ? (String) ((HashMap) AbstractC2326b0.f839e).get(str2) : (String) map4.get(locale.getCountry());
                }
                HashMap map5 = (HashMap) AbstractC2326b0.f841g;
                if (map5.containsKey(str)) {
                    str = (String) map5.get(str);
                }
                for (int i = this.f874e == TextStyle.FULL ? 1 : 2; i < strArr.length; i += 2) {
                    c2335k2.m761a(strArr[i], str);
                }
            }
        }
        map.put(locale, new AbstractMap.SimpleImmutableEntry(Integer.valueOf(size), new SoftReference(c2335k2)));
        return c2335k2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    @Override // p026j$.time.format.C2339o, p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean mo749t(p026j$.time.format.C2344t r13, java.lang.StringBuilder r14) {
        /*
            Method dump skipped, instruction units count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.format.C2340p.mo749t(j$.time.format.t, java.lang.StringBuilder):boolean");
    }
}
