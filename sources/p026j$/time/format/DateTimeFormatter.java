package p026j$.time.format;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import org.mvel2.asm.signature.SignatureVisitor;
import p026j$.time.C2284c;
import p026j$.time.C2388x;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2373i;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class DateTimeFormatter {
    public static final DateTimeFormatter ISO_LOCAL_DATE;
    public static final DateTimeFormatter RFC_1123_DATE_TIME;

    /* JADX INFO: renamed from: f */
    public static final DateTimeFormatter f824f;

    /* JADX INFO: renamed from: a */
    public final C2328d f825a;

    /* JADX INFO: renamed from: b */
    public final Locale f826b;

    /* JADX INFO: renamed from: c */
    public final C2348x f827c;

    /* JADX INFO: renamed from: d */
    public final EnumC2350z f828d;

    /* JADX INFO: renamed from: e */
    public final InterfaceC2304k f829e;

    static {
        C2341q c2341q = new C2341q();
        EnumC2365a enumC2365a = EnumC2365a.YEAR;
        EnumC2324a0 enumC2324a0 = EnumC2324a0.EXCEEDS_PAD;
        c2341q.m772h(enumC2365a, 4, 10, enumC2324a0);
        c2341q.m767c(SignatureVisitor.SUPER);
        EnumC2365a enumC2365a2 = EnumC2365a.MONTH_OF_YEAR;
        c2341q.m771g(enumC2365a2, 2);
        c2341q.m767c(SignatureVisitor.SUPER);
        EnumC2365a enumC2365a3 = EnumC2365a.DAY_OF_MONTH;
        c2341q.m771g(enumC2365a3, 2);
        EnumC2350z enumC2350z = EnumC2350z.STRICT;
        C2311r c2311r = C2311r.f803c;
        DateTimeFormatter dateTimeFormatterM775k = c2341q.m775k(enumC2350z, c2311r);
        ISO_LOCAL_DATE = dateTimeFormatterM775k;
        C2341q c2341q2 = new C2341q();
        EnumC2336l enumC2336l = EnumC2336l.INSENSITIVE;
        c2341q2.m766b(enumC2336l);
        c2341q2.m765a(dateTimeFormatterM775k);
        C2333i c2333i = C2333i.f853e;
        c2341q2.m766b(c2333i);
        c2341q2.m775k(enumC2350z, c2311r);
        C2341q c2341q3 = new C2341q();
        c2341q3.m766b(enumC2336l);
        c2341q3.m765a(dateTimeFormatterM775k);
        c2341q3.m774j();
        c2341q3.m766b(c2333i);
        c2341q3.m775k(enumC2350z, c2311r);
        C2341q c2341q4 = new C2341q();
        EnumC2365a enumC2365a4 = EnumC2365a.HOUR_OF_DAY;
        c2341q4.m771g(enumC2365a4, 2);
        c2341q4.m767c(':');
        EnumC2365a enumC2365a5 = EnumC2365a.MINUTE_OF_HOUR;
        c2341q4.m771g(enumC2365a5, 2);
        c2341q4.m774j();
        c2341q4.m767c(':');
        EnumC2365a enumC2365a6 = EnumC2365a.SECOND_OF_MINUTE;
        c2341q4.m771g(enumC2365a6, 2);
        c2341q4.m774j();
        c2341q4.m766b(new C2330f(EnumC2365a.NANO_OF_SECOND));
        DateTimeFormatter dateTimeFormatterM775k2 = c2341q4.m775k(enumC2350z, null);
        C2341q c2341q5 = new C2341q();
        c2341q5.m766b(enumC2336l);
        c2341q5.m765a(dateTimeFormatterM775k2);
        c2341q5.m766b(c2333i);
        c2341q5.m775k(enumC2350z, null);
        C2341q c2341q6 = new C2341q();
        c2341q6.m766b(enumC2336l);
        c2341q6.m765a(dateTimeFormatterM775k2);
        c2341q6.m774j();
        c2341q6.m766b(c2333i);
        c2341q6.m775k(enumC2350z, null);
        C2341q c2341q7 = new C2341q();
        c2341q7.m766b(enumC2336l);
        c2341q7.m765a(dateTimeFormatterM775k);
        c2341q7.m767c('T');
        c2341q7.m765a(dateTimeFormatterM775k2);
        DateTimeFormatter dateTimeFormatterM775k3 = c2341q7.m775k(enumC2350z, c2311r);
        C2341q c2341q8 = new C2341q();
        c2341q8.m766b(enumC2336l);
        c2341q8.m765a(dateTimeFormatterM775k3);
        EnumC2336l enumC2336l2 = EnumC2336l.LENIENT;
        c2341q8.m766b(enumC2336l2);
        c2341q8.m766b(c2333i);
        EnumC2336l enumC2336l3 = EnumC2336l.STRICT;
        c2341q8.m766b(enumC2336l3);
        DateTimeFormatter dateTimeFormatterM775k4 = c2341q8.m775k(enumC2350z, c2311r);
        C2341q c2341q9 = new C2341q();
        c2341q9.m765a(dateTimeFormatterM775k4);
        c2341q9.m774j();
        c2341q9.m767c('[');
        EnumC2336l enumC2336l4 = EnumC2336l.SENSITIVE;
        c2341q9.m766b(enumC2336l4);
        C2388x c2388x = C2341q.f877f;
        c2341q9.m766b(new C2339o(c2388x, "ZoneRegionId()"));
        c2341q9.m767c(']');
        c2341q9.m775k(enumC2350z, c2311r);
        C2341q c2341q10 = new C2341q();
        c2341q10.m765a(dateTimeFormatterM775k3);
        c2341q10.m774j();
        c2341q10.m766b(c2333i);
        c2341q10.m774j();
        c2341q10.m767c('[');
        c2341q10.m766b(enumC2336l4);
        c2341q10.m766b(new C2339o(c2388x, "ZoneRegionId()"));
        c2341q10.m767c(']');
        c2341q10.m775k(enumC2350z, c2311r);
        C2341q c2341q11 = new C2341q();
        c2341q11.m766b(enumC2336l);
        c2341q11.m772h(enumC2365a, 4, 10, enumC2324a0);
        c2341q11.m767c(SignatureVisitor.SUPER);
        c2341q11.m771g(EnumC2365a.DAY_OF_YEAR, 3);
        c2341q11.m774j();
        c2341q11.m766b(c2333i);
        c2341q11.m775k(enumC2350z, c2311r);
        C2341q c2341q12 = new C2341q();
        c2341q12.m766b(enumC2336l);
        c2341q12.m772h(AbstractC2373i.f950c, 4, 10, enumC2324a0);
        c2341q12.m768d("-W");
        c2341q12.m771g(AbstractC2373i.f949b, 2);
        c2341q12.m767c(SignatureVisitor.SUPER);
        EnumC2365a enumC2365a7 = EnumC2365a.DAY_OF_WEEK;
        c2341q12.m771g(enumC2365a7, 1);
        c2341q12.m774j();
        c2341q12.m766b(c2333i);
        c2341q12.m775k(enumC2350z, c2311r);
        C2341q c2341q13 = new C2341q();
        c2341q13.m766b(enumC2336l);
        c2341q13.m766b(new C2331g());
        f824f = c2341q13.m775k(enumC2350z, null);
        C2341q c2341q14 = new C2341q();
        c2341q14.m766b(enumC2336l);
        c2341q14.m771g(enumC2365a, 4);
        c2341q14.m771g(enumC2365a2, 2);
        c2341q14.m771g(enumC2365a3, 2);
        c2341q14.m774j();
        c2341q14.m766b(enumC2336l2);
        c2341q14.m766b(new C2333i("+HHMMss", "Z"));
        c2341q14.m766b(enumC2336l3);
        c2341q14.m775k(enumC2350z, c2311r);
        HashMap map = new HashMap();
        map.put(1L, "Mon");
        map.put(2L, "Tue");
        map.put(3L, "Wed");
        map.put(4L, "Thu");
        map.put(5L, "Fri");
        map.put(6L, "Sat");
        map.put(7L, "Sun");
        HashMap map2 = new HashMap();
        map2.put(1L, "Jan");
        map2.put(2L, "Feb");
        map2.put(3L, "Mar");
        map2.put(4L, "Apr");
        map2.put(5L, "May");
        map2.put(6L, "Jun");
        map2.put(7L, "Jul");
        map2.put(8L, "Aug");
        map2.put(9L, "Sep");
        map2.put(10L, "Oct");
        map2.put(11L, "Nov");
        map2.put(12L, "Dec");
        C2341q c2341q15 = new C2341q();
        c2341q15.m766b(enumC2336l);
        c2341q15.m766b(enumC2336l2);
        c2341q15.m774j();
        c2341q15.m769e(enumC2365a7, map);
        c2341q15.m768d(", ");
        c2341q15.m773i();
        c2341q15.m772h(enumC2365a3, 1, 2, EnumC2324a0.NOT_NEGATIVE);
        c2341q15.m767c(' ');
        c2341q15.m769e(enumC2365a2, map2);
        c2341q15.m767c(' ');
        c2341q15.m771g(enumC2365a, 4);
        c2341q15.m767c(' ');
        c2341q15.m771g(enumC2365a4, 2);
        c2341q15.m767c(':');
        c2341q15.m771g(enumC2365a5, 2);
        c2341q15.m774j();
        c2341q15.m767c(':');
        c2341q15.m771g(enumC2365a6, 2);
        c2341q15.m773i();
        c2341q15.m767c(' ');
        c2341q15.m766b(new C2333i("+HHMM", "GMT"));
        RFC_1123_DATE_TIME = c2341q15.m775k(EnumC2350z.SMART, c2311r);
    }

    public DateTimeFormatter(C2328d c2328d, Locale locale, EnumC2350z enumC2350z, InterfaceC2304k interfaceC2304k) {
        C2348x c2348x = C2348x.f899a;
        this.f825a = c2328d;
        Objects.requireNonNull(locale, "locale");
        this.f826b = locale;
        this.f827c = c2348x;
        Objects.requireNonNull(enumC2350z, "resolverStyle");
        this.f828d = enumC2350z;
        this.f829e = interfaceC2304k;
    }

    /* JADX INFO: renamed from: a */
    public final String m742a(InterfaceC2376l interfaceC2376l) {
        StringBuilder sb = new StringBuilder(32);
        try {
            this.f825a.mo749t(new C2344t(interfaceC2376l, this), sb);
            return sb.toString();
        } catch (IOException e) {
            throw new C2284c(e.getMessage(), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:268:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0316  */
    /* JADX INFO: renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final p026j$.time.format.C2349y m743b(java.lang.CharSequence r24) {
        /*
            Method dump skipped, instruction units count: 1078
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.format.DateTimeFormatter.m743b(java.lang.CharSequence):j$.time.format.y");
    }

    public final String toString() {
        String string = this.f825a.toString();
        return string.startsWith("[") ? string : string.substring(1, string.length() - 1);
    }
}
