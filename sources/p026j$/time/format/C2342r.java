package p026j$.time.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import p026j$.time.ZoneId;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.r */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2342r {

    /* JADX INFO: renamed from: a */
    public final DateTimeFormatter f883a;

    /* JADX INFO: renamed from: b */
    public boolean f884b = true;

    /* JADX INFO: renamed from: c */
    public boolean f885c = true;

    /* JADX INFO: renamed from: d */
    public final ArrayList f886d;

    public C2342r(DateTimeFormatter dateTimeFormatter) {
        ArrayList arrayList = new ArrayList();
        this.f886d = arrayList;
        this.f883a = dateTimeFormatter;
        arrayList.add(new C2349y());
    }

    /* JADX INFO: renamed from: b */
    public static boolean m777b(char c2, char c3) {
        return c2 == c3 || Character.toUpperCase(c2) == Character.toUpperCase(c3) || Character.toLowerCase(c2) == Character.toLowerCase(c3);
    }

    /* JADX INFO: renamed from: a */
    public final boolean m778a(char c2, char c3) {
        return this.f884b ? c2 == c3 : m777b(c2, c3);
    }

    /* JADX INFO: renamed from: c */
    public final C2349y m779c() {
        return (C2349y) this.f886d.get(r1.size() - 1);
    }

    /* JADX INFO: renamed from: d */
    public final Long m780d(EnumC2365a enumC2365a) {
        return (Long) ((HashMap) m779c().f900a).get(enumC2365a);
    }

    /* JADX INFO: renamed from: e */
    public final void m781e(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        m779c().f901b = zoneId;
    }

    /* JADX INFO: renamed from: f */
    public final int m782f(InterfaceC2380p interfaceC2380p, long j, int i, int i2) {
        Objects.requireNonNull(interfaceC2380p, "field");
        Long l = (Long) ((HashMap) m779c().f900a).put(interfaceC2380p, Long.valueOf(j));
        return (l == null || l.longValue() == j) ? i2 : ~i;
    }

    /* JADX INFO: renamed from: g */
    public final boolean m783g(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3) {
        if (i + i3 <= charSequence.length() && i2 + i3 <= charSequence2.length()) {
            if (this.f884b) {
                for (int i4 = 0; i4 < i3; i4++) {
                    if (charSequence.charAt(i + i4) == charSequence2.charAt(i2 + i4)) {
                    }
                }
                return true;
            }
            for (int i5 = 0; i5 < i3; i5++) {
                char cCharAt = charSequence.charAt(i + i5);
                char cCharAt2 = charSequence2.charAt(i2 + i5);
                if (cCharAt == cCharAt2 || Character.toUpperCase(cCharAt) == Character.toUpperCase(cCharAt2) || Character.toLowerCase(cCharAt) == Character.toLowerCase(cCharAt2)) {
                }
            }
            return true;
        }
        return false;
    }

    public final String toString() {
        return m779c().toString();
    }
}
