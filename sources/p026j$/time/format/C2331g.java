package p026j$.time.format;

import de.robv.android.xposed.callbacks.XCallback;
import java.util.Locale;
import org.mvel2.asm.signature.SignatureVisitor;
import p026j$.time.C2354j;
import p026j$.time.LocalDate;
import p026j$.time.LocalDateTime;
import p026j$.time.ZoneOffset;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;

/* JADX INFO: renamed from: j$.time.format.g */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2331g implements InterfaceC2329e {
    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: B */
    public final int mo748B(C2342r c2342r, CharSequence charSequence, int i) {
        C2341q c2341q = new C2341q();
        c2341q.m765a(DateTimeFormatter.ISO_LOCAL_DATE);
        c2341q.m767c('T');
        EnumC2365a enumC2365a = EnumC2365a.HOUR_OF_DAY;
        c2341q.m771g(enumC2365a, 2);
        c2341q.m767c(':');
        EnumC2365a enumC2365a2 = EnumC2365a.MINUTE_OF_HOUR;
        c2341q.m771g(enumC2365a2, 2);
        c2341q.m767c(':');
        EnumC2365a enumC2365a3 = EnumC2365a.SECOND_OF_MINUTE;
        c2341q.m771g(enumC2365a3, 2);
        EnumC2365a enumC2365a4 = EnumC2365a.NANO_OF_SECOND;
        c2341q.m766b(new C2330f(enumC2365a4));
        c2341q.m767c('Z');
        C2328d c2328d = c2341q.m776l(Locale.getDefault(), EnumC2350z.SMART, null).f825a;
        if (c2328d.f844b) {
            c2328d = new C2328d(c2328d.f843a, false);
        }
        C2342r c2342r2 = new C2342r(c2342r.f883a);
        c2342r2.f884b = c2342r.f884b;
        c2342r2.f885c = c2342r.f885c;
        int iMo748B = c2328d.mo748B(c2342r2, charSequence, i);
        if (iMo748B < 0) {
            return iMo748B;
        }
        long jLongValue = c2342r2.m780d(EnumC2365a.YEAR).longValue();
        int iIntValue = c2342r2.m780d(EnumC2365a.MONTH_OF_YEAR).intValue();
        int iIntValue2 = c2342r2.m780d(EnumC2365a.DAY_OF_MONTH).intValue();
        int iIntValue3 = c2342r2.m780d(enumC2365a).intValue();
        int iIntValue4 = c2342r2.m780d(enumC2365a2).intValue();
        Long lM780d = c2342r2.m780d(enumC2365a3);
        Long lM780d2 = c2342r2.m780d(enumC2365a4);
        int iIntValue5 = lM780d != null ? lM780d.intValue() : 0;
        int iIntValue6 = lM780d2 != null ? lM780d2.intValue() : 0;
        int i2 = 1;
        if (iIntValue3 == 24 && iIntValue4 == 0 && iIntValue5 == 0 && iIntValue6 == 0) {
            iIntValue3 = 0;
        } else {
            if (iIntValue3 == 23 && iIntValue4 == 59 && iIntValue5 == 60) {
                c2342r.m779c().f903d = true;
                iIntValue5 = 59;
            }
            i2 = 0;
        }
        int i3 = ((int) jLongValue) % XCallback.PRIORITY_HIGHEST;
        try {
            LocalDateTime localDateTime = LocalDateTime.f732c;
            LocalDate localDateM593of = LocalDate.m593of(i3, iIntValue, iIntValue2);
            C2354j c2354jM807P = C2354j.m807P(iIntValue3, iIntValue4, iIntValue5, 0);
            return c2342r.m782f(enumC2365a4, iIntValue6, i, c2342r.m782f(EnumC2365a.INSTANT_SECONDS, new LocalDateTime(localDateM593of, c2354jM807P).m627a0(localDateM593of.plusDays(i2), c2354jM807P).toEpochSecond(ZoneOffset.UTC) + Math.multiplyExact(jLongValue / 10000, 315569520000L), i, iMo748B));
        } catch (RuntimeException unused) {
            return ~i;
        }
    }

    @Override // p026j$.time.format.InterfaceC2329e
    /* JADX INFO: renamed from: t */
    public final boolean mo749t(C2344t c2344t, StringBuilder sb) {
        Long lM784a = c2344t.m784a(EnumC2365a.INSTANT_SECONDS);
        InterfaceC2376l interfaceC2376l = c2344t.f891a;
        EnumC2365a enumC2365a = EnumC2365a.NANO_OF_SECOND;
        Long lValueOf = interfaceC2376l.mo571i(enumC2365a) ? Long.valueOf(interfaceC2376l.mo572k(enumC2365a)) : null;
        int i = 0;
        if (lM784a == null) {
            return false;
        }
        long jLongValue = lM784a.longValue();
        int iM849a = enumC2365a.f942b.m849a(lValueOf != null ? lValueOf.longValue() : 0L, enumC2365a);
        if (jLongValue >= -62167219200L) {
            long j = jLongValue - 253402300800L;
            long jFloorDiv = Math.floorDiv(j, 315569520000L) + 1;
            LocalDateTime localDateTimeM619P = LocalDateTime.m619P(Math.floorMod(j, 315569520000L) - 62167219200L, 0, ZoneOffset.UTC);
            if (jFloorDiv > 0) {
                sb.append(SignatureVisitor.EXTENDS);
                sb.append(jFloorDiv);
            }
            sb.append(localDateTimeM619P);
            if (localDateTimeM619P.f735b.f918c == 0) {
                sb.append(":00");
            }
        } else {
            long j2 = jLongValue + 62167219200L;
            long j3 = j2 / 315569520000L;
            long j4 = j2 % 315569520000L;
            LocalDateTime localDateTimeM619P2 = LocalDateTime.m619P(j4 - 62167219200L, 0, ZoneOffset.UTC);
            int length = sb.length();
            sb.append(localDateTimeM619P2);
            if (localDateTimeM619P2.f735b.f918c == 0) {
                sb.append(":00");
            }
            if (j3 < 0) {
                if (localDateTimeM619P2.f734a.getYear() == -10000) {
                    sb.replace(length, length + 2, Long.toString(j3 - 1));
                } else if (j4 == 0) {
                    sb.insert(length, j3);
                } else {
                    sb.insert(length + 1, Math.abs(j3));
                }
            }
        }
        if (iM849a > 0) {
            sb.append('.');
            int i2 = 100000000;
            while (true) {
                if (iM849a <= 0 && i % 3 == 0 && i >= -2) {
                    break;
                }
                int i3 = iM849a / i2;
                sb.append((char) (i3 + 48));
                iM849a -= i3 * i2;
                i2 /= 10;
                i++;
            }
        }
        sb.append('Z');
        return true;
    }

    public final String toString() {
        return "Instant()";
    }
}
