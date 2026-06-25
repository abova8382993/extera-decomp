package p026j$.time.format;

import java.text.ParsePosition;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import okhttp3.internal.url._UrlKt;
import p026j$.time.C2284c;
import p026j$.time.C2388x;
import p026j$.time.ZoneId;
import p026j$.time.ZoneOffset;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.zone.C2397h;

/* JADX INFO: renamed from: j$.time.format.o */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public class C2339o implements InterfaceC2329e {

    /* JADX INFO: renamed from: c */
    public static volatile Map.Entry f869c;

    /* JADX INFO: renamed from: d */
    public static volatile Map.Entry f870d;

    /* JADX INFO: renamed from: a */
    public final C2388x f871a;

    /* JADX INFO: renamed from: b */
    public final String f872b;

    public C2339o(C2388x c2388x, String str) {
        this.f871a = c2388x;
        this.f872b = str;
    }

    /* JADX INFO: renamed from: b */
    public static int m763b(C2342r c2342r, CharSequence charSequence, int i, int i2, C2333i c2333i) {
        String upperCase = charSequence.subSequence(i, i2).toString().toUpperCase();
        if (i2 >= charSequence.length()) {
            c2342r.m781e(ZoneId.m647of(upperCase));
            return i2;
        }
        if (charSequence.charAt(i2) == '0' || c2342r.m778a(charSequence.charAt(i2), 'Z')) {
            c2342r.m781e(ZoneId.m647of(upperCase));
            return i2;
        }
        C2342r c2342r2 = new C2342r(c2342r.f883a);
        c2342r2.f884b = c2342r.f884b;
        c2342r2.f885c = c2342r.f885c;
        int iMo748B = c2333i.mo748B(c2342r2, charSequence, i2);
        try {
            if (iMo748B >= 0) {
                c2342r.m781e(ZoneId.m645G(upperCase, ZoneOffset.m653Z((int) c2342r2.m780d(EnumC2365a.OFFSET_SECONDS).longValue())));
                return iMo748B;
            }
            if (c2333i == C2333i.f853e) {
                return ~i;
            }
            c2342r.m781e(ZoneId.m647of(upperCase));
            return i2;
        } catch (C2284c unused) {
            return ~i;
        }
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        int i2;
        int length = charSequence.length();
        if (i > length) {
            throw new IndexOutOfBoundsException();
        }
        if (i == length) {
            return ~i;
        }
        char cCharAt = charSequence.charAt(i);
        if (cCharAt == '+' || cCharAt == '-') {
            return m763b(c2342r, charSequence, i, i, C2333i.f853e);
        }
        int i3 = i + 2;
        if (length >= i3) {
            char cCharAt2 = charSequence.charAt(i + 1);
            if (c2342r.m778a(cCharAt, 'U') && c2342r.m778a(cCharAt2, 'T')) {
                int i4 = i + 3;
                return (length < i4 || !c2342r.m778a(charSequence.charAt(i3), 'C')) ? m763b(c2342r, charSequence, i, i3, C2333i.f854f) : m763b(c2342r, charSequence, i, i4, C2333i.f854f);
            }
            if (c2342r.m778a(cCharAt, 'G') && length >= (i2 = i + 3) && c2342r.m778a(cCharAt2, 'M') && c2342r.m778a(charSequence.charAt(i3), 'T')) {
                int i5 = i + 4;
                if (length < i5 || !c2342r.m778a(charSequence.charAt(i2), '0')) {
                    return m763b(c2342r, charSequence, i, i2, C2333i.f854f);
                }
                c2342r.m781e(ZoneId.m647of("GMT0"));
                return i5;
            }
        }
        C2335k c2335kMo764a = mo764a(c2342r);
        ParsePosition parsePosition = new ParsePosition(i);
        String strM762c = c2335kMo764a.m762c(charSequence, parsePosition);
        if (strM762c != null) {
            c2342r.m781e(ZoneId.m647of(strM762c));
            return parsePosition.getIndex();
        }
        if (!c2342r.m778a(cCharAt, 'Z')) {
            return ~i;
        }
        c2342r.m781e(ZoneOffset.UTC);
        return i + 1;
    }

    /* JADX INFO: renamed from: a */
    public C2335k mo764a(C2342r c2342r) {
        Set<String> set = C2397h.f1010d;
        int size = set.size();
        Map.Entry simpleImmutableEntry = c2342r.f884b ? f869c : f870d;
        if (simpleImmutableEntry == null || ((Integer) simpleImmutableEntry.getKey()).intValue() != size) {
            synchronized (this) {
                try {
                    simpleImmutableEntry = c2342r.f884b ? f869c : f870d;
                    if (simpleImmutableEntry == null || ((Integer) simpleImmutableEntry.getKey()).intValue() != size) {
                        Integer numValueOf = Integer.valueOf(size);
                        C2335k c2335k = c2342r.f884b ? new C2335k(_UrlKt.FRAGMENT_ENCODE_SET, null, null) : new C2334j(_UrlKt.FRAGMENT_ENCODE_SET, null, null);
                        for (String str : set) {
                            c2335k.m761a(str, str);
                        }
                        simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry(numValueOf, c2335k);
                        if (c2342r.f884b) {
                            f869c = simpleImmutableEntry;
                        } else {
                            f870d = simpleImmutableEntry;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return (C2335k) simpleImmutableEntry.getValue();
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public boolean mo749t(C2344t c2344t, StringBuilder sb) {
        ZoneId zoneId = (ZoneId) c2344t.m785b(this.f871a);
        if (zoneId == null) {
            return false;
        }
        sb.append(zoneId.getId());
        return true;
    }

    public final String toString() {
        return this.f872b;
    }
}
