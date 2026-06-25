package p026j$.time.zone;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.telegram.messenger.MediaDataController;
import p026j$.time.AbstractC2283b;
import p026j$.time.C2354j;
import p026j$.time.DayOfWeek;
import p026j$.time.EnumC2356l;
import p026j$.time.Instant;
import p026j$.time.LocalDate;
import p026j$.time.LocalDateTime;
import p026j$.time.ZoneOffset;
import p026j$.time.chrono.C2311r;
import p026j$.time.temporal.C2378n;
import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class ZoneRules implements Serializable {

    /* JADX INFO: renamed from: i */
    public static final long[] f977i = new long[0];

    /* JADX INFO: renamed from: j */
    public static final C2394e[] f978j = new C2394e[0];

    /* JADX INFO: renamed from: k */
    public static final LocalDateTime[] f979k = new LocalDateTime[0];

    /* JADX INFO: renamed from: l */
    public static final C2391b[] f980l = new C2391b[0];
    private static final long serialVersionUID = 3044319355680032515L;

    /* JADX INFO: renamed from: a */
    public final long[] f981a;

    /* JADX INFO: renamed from: b */
    public final ZoneOffset[] f982b;

    /* JADX INFO: renamed from: c */
    public final long[] f983c;

    /* JADX INFO: renamed from: d */
    public final LocalDateTime[] f984d;

    /* JADX INFO: renamed from: e */
    public final ZoneOffset[] f985e;

    /* JADX INFO: renamed from: f */
    public final C2394e[] f986f;

    /* JADX INFO: renamed from: g */
    public final TimeZone f987g;

    /* JADX INFO: renamed from: h */
    public final transient ConcurrentMap f988h = new ConcurrentHashMap();

    public ZoneRules(long[] jArr, ZoneOffset[] zoneOffsetArr, long[] jArr2, ZoneOffset[] zoneOffsetArr2, C2394e[] c2394eArr) {
        this.f981a = jArr;
        this.f982b = zoneOffsetArr;
        this.f983c = jArr2;
        this.f985e = zoneOffsetArr2;
        this.f986f = c2394eArr;
        if (jArr2.length == 0) {
            this.f984d = f979k;
        } else {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < jArr2.length) {
                int i2 = i + 1;
                C2391b c2391b = new C2391b(jArr2[i], zoneOffsetArr2[i], zoneOffsetArr2[i2]);
                boolean zM868t = c2391b.m868t();
                LocalDateTime localDateTime = c2391b.f993b;
                if (zM868t) {
                    arrayList.add(localDateTime);
                    arrayList.add(c2391b.f993b.m624X(c2391b.f995d.getTotalSeconds() - c2391b.f994c.getTotalSeconds()));
                } else {
                    arrayList.add(localDateTime.m624X(c2391b.f995d.getTotalSeconds() - c2391b.f994c.getTotalSeconds()));
                    arrayList.add(c2391b.f993b);
                }
                i = i2;
            }
            this.f984d = (LocalDateTime[]) arrayList.toArray(new LocalDateTime[arrayList.size()]);
        }
        this.f987g = null;
    }

    /* JADX INFO: renamed from: a */
    public static Object m856a(LocalDateTime localDateTime, C2391b c2391b) {
        LocalDateTime localDateTime2 = c2391b.f993b;
        if (c2391b.m868t()) {
            if (localDateTime.m621G(localDateTime2)) {
                return c2391b.f994c;
            }
            if (!localDateTime.m621G(c2391b.f993b.m624X(c2391b.f995d.getTotalSeconds() - c2391b.f994c.getTotalSeconds()))) {
                return c2391b.f995d;
            }
        } else {
            if (!localDateTime.m621G(localDateTime2)) {
                return c2391b.f995d;
            }
            if (localDateTime.m621G(c2391b.f993b.m624X(c2391b.f995d.getTotalSeconds() - c2391b.f994c.getTotalSeconds()))) {
                return c2391b.f994c;
            }
        }
        return c2391b;
    }

    /* JADX INFO: renamed from: c */
    public static int m857c(long j, ZoneOffset zoneOffset) {
        return LocalDate.m590d0(Math.floorDiv(j + ((long) zoneOffset.getTotalSeconds()), 86400L)).getYear();
    }

    /* JADX INFO: renamed from: h */
    public static ZoneOffset m858h(int i) {
        return ZoneOffset.m653Z(i / MediaDataController.MAX_STYLE_RUNS_COUNT);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2390a(this.f987g != null ? (byte) 100 : (byte) 1, this);
    }

    /* JADX INFO: renamed from: b */
    public final C2391b[] m859b(int i) {
        LocalDate localDateM587B;
        C2391b[] c2391bArr = f980l;
        Integer numValueOf = Integer.valueOf(i);
        C2391b[] c2391bArr2 = (C2391b[]) ((ConcurrentHashMap) this.f988h).get(numValueOf);
        if (c2391bArr2 != null) {
            return c2391bArr2;
        }
        long j = 1;
        int i2 = 0;
        if (this.f987g != null) {
            if (i < 1800) {
                return c2391bArr;
            }
            LocalDateTime localDateTime = LocalDateTime.f732c;
            LocalDate localDateM593of = LocalDate.m593of(i - 1, 12, 31);
            EnumC2365a.HOUR_OF_DAY.m839X(0L);
            long epochSecond = new LocalDateTime(localDateM593of, C2354j.f915h[0]).toEpochSecond(this.f982b[0]);
            long j2 = 1000;
            int offset = this.f987g.getOffset(epochSecond * 1000);
            long j3 = 31968000 + epochSecond;
            while (epochSecond < j3) {
                long j4 = epochSecond + 7776000;
                long j5 = j2;
                if (offset != this.f987g.getOffset(j4 * j5)) {
                    while (j4 - epochSecond > j) {
                        long jFloorDiv = Math.floorDiv(j4 + epochSecond, 2L);
                        if (this.f987g.getOffset(jFloorDiv * j5) == offset) {
                            epochSecond = jFloorDiv;
                        } else {
                            j4 = jFloorDiv;
                        }
                        j = 1;
                    }
                    if (this.f987g.getOffset(epochSecond * j5) == offset) {
                        epochSecond = j4;
                    }
                    ZoneOffset zoneOffsetM858h = m858h(offset);
                    int offset2 = this.f987g.getOffset(epochSecond * j5);
                    ZoneOffset zoneOffsetM858h2 = m858h(offset2);
                    if (m857c(epochSecond, zoneOffsetM858h2) == i) {
                        c2391bArr = (C2391b[]) Arrays.copyOf(c2391bArr, c2391bArr.length + 1);
                        c2391bArr[c2391bArr.length - 1] = new C2391b(epochSecond, zoneOffsetM858h, zoneOffsetM858h2);
                    }
                    offset = offset2;
                } else {
                    epochSecond = j4;
                }
                j2 = j5;
                j = 1;
            }
            if (1916 <= i && i < 2100) {
                ((ConcurrentHashMap) this.f988h).putIfAbsent(numValueOf, c2391bArr);
            }
            return c2391bArr;
        }
        C2394e[] c2394eArr = this.f986f;
        C2391b[] c2391bArr3 = new C2391b[c2394eArr.length];
        int i3 = 0;
        while (i3 < c2394eArr.length) {
            C2394e c2394e = c2394eArr[i3];
            byte b2 = c2394e.f999b;
            EnumC2356l enumC2356l = c2394e.f998a;
            if (b2 < 0) {
                long j6 = i;
                C2311r.f803c.getClass();
                int iM823B = enumC2356l.m823B(C2311r.m733X(j6)) + 1 + c2394e.f999b;
                LocalDate localDate = LocalDate.f727d;
                EnumC2365a.YEAR.m839X(j6);
                EnumC2365a.DAY_OF_MONTH.m839X(iM823B);
                localDateM587B = LocalDate.m587B(i, enumC2356l.getValue(), iM823B);
                DayOfWeek dayOfWeek = c2394e.f1000c;
                if (dayOfWeek != null) {
                    localDateM587B = localDateM587B.mo666j(new C2378n(dayOfWeek.getValue(), 1));
                }
            } else {
                LocalDate localDate2 = LocalDate.f727d;
                EnumC2365a.YEAR.m839X(i);
                EnumC2365a.DAY_OF_MONTH.m839X(b2);
                localDateM587B = LocalDate.m587B(i, enumC2356l.getValue(), b2);
                DayOfWeek dayOfWeek2 = c2394e.f1000c;
                if (dayOfWeek2 != null) {
                    localDateM587B = localDateM587B.mo666j(new C2378n(dayOfWeek2.getValue(), i2));
                }
            }
            if (c2394e.f1002e) {
                localDateM587B = localDateM587B.plusDays(1L);
            }
            LocalDateTime localDateTimeM618I = LocalDateTime.m618I(localDateM587B, c2394e.f1001d);
            EnumC2393d enumC2393d = c2394e.f1003f;
            ZoneOffset zoneOffset = c2394e.f1004g;
            ZoneOffset zoneOffset2 = c2394e.f1005h;
            int i4 = AbstractC2392c.f996a[enumC2393d.ordinal()];
            if (i4 == 1) {
                localDateTimeM618I = localDateTimeM618I.m624X(zoneOffset2.getTotalSeconds() - ZoneOffset.UTC.getTotalSeconds());
            } else if (i4 == 2) {
                localDateTimeM618I = localDateTimeM618I.m624X(zoneOffset2.getTotalSeconds() - zoneOffset.getTotalSeconds());
            }
            c2391bArr3[i3] = new C2391b(localDateTimeM618I, c2394e.f1005h, c2394e.f1006i);
            i3++;
            i2 = 0;
        }
        if (i < 2100) {
            ((ConcurrentHashMap) this.f988h).putIfAbsent(numValueOf, c2391bArr3);
        }
        return c2391bArr3;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0068  */
    /* JADX INFO: renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m860d(p026j$.time.LocalDateTime r9) {
        /*
            Method dump skipped, instruction units count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.zone.ZoneRules.m860d(j$.time.LocalDateTime):java.lang.Object");
    }

    /* JADX INFO: renamed from: e */
    public final C2391b m861e(LocalDateTime localDateTime) {
        Object objM860d = m860d(localDateTime);
        if (objM860d instanceof C2391b) {
            return (C2391b) objM860d;
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneRules) {
            ZoneRules zoneRules = (ZoneRules) obj;
            if (Objects.equals(this.f987g, zoneRules.f987g) && Arrays.equals(this.f981a, zoneRules.f981a) && Arrays.equals(this.f982b, zoneRules.f982b) && Arrays.equals(this.f983c, zoneRules.f983c) && Arrays.equals(this.f985e, zoneRules.f985e) && Arrays.equals(this.f986f, zoneRules.f986f)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: f */
    public final List m862f(LocalDateTime localDateTime) {
        Object objM860d = m860d(localDateTime);
        if (!(objM860d instanceof C2391b)) {
            return Collections.singletonList((ZoneOffset) objM860d);
        }
        C2391b c2391b = (C2391b) objM860d;
        return c2391b.m868t() ? Collections.EMPTY_LIST : AbstractC2283b.m674c(new Object[]{c2391b.f994c, c2391b.f995d});
    }

    /* JADX INFO: renamed from: g */
    public final boolean m863g(Instant instant) {
        ZoneOffset zoneOffsetM858h;
        TimeZone timeZone = this.f987g;
        if (timeZone != null) {
            zoneOffsetM858h = m858h(timeZone.getRawOffset());
        } else if (this.f983c.length == 0) {
            zoneOffsetM858h = this.f982b[0];
        } else {
            int iBinarySearch = Arrays.binarySearch(this.f981a, instant.getEpochSecond());
            if (iBinarySearch < 0) {
                iBinarySearch = (-iBinarySearch) - 2;
            }
            zoneOffsetM858h = this.f982b[iBinarySearch + 1];
        }
        return !zoneOffsetM858h.equals(getOffset(instant));
    }

    public ZoneOffset getOffset(Instant instant) {
        TimeZone timeZone = this.f987g;
        if (timeZone != null) {
            return m858h(timeZone.getOffset(instant.toEpochMilli()));
        }
        if (this.f983c.length == 0) {
            return this.f982b[0];
        }
        long epochSecond = instant.getEpochSecond();
        if (this.f986f.length > 0) {
            if (epochSecond > this.f983c[r7.length - 1]) {
                C2391b[] c2391bArrM859b = m859b(m857c(epochSecond, this.f985e[r7.length - 1]));
                C2391b c2391b = null;
                for (int i = 0; i < c2391bArrM859b.length; i++) {
                    c2391b = c2391bArrM859b[i];
                    if (epochSecond < c2391b.f992a) {
                        return c2391b.f994c;
                    }
                }
                return c2391b.f995d;
            }
        }
        int iBinarySearch = Arrays.binarySearch(this.f983c, epochSecond);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 2;
        }
        return this.f985e[iBinarySearch + 1];
    }

    public final int hashCode() {
        return Arrays.hashCode(this.f986f) ^ ((((Objects.hashCode(this.f987g) ^ Arrays.hashCode(this.f981a)) ^ Arrays.hashCode(this.f982b)) ^ Arrays.hashCode(this.f983c)) ^ Arrays.hashCode(this.f985e));
    }

    public final String toString() {
        TimeZone timeZone = this.f987g;
        if (timeZone != null) {
            return "ZoneRules[timeZone=" + timeZone.getID() + "]";
        }
        return "ZoneRules[currentStandardOffset=" + this.f982b[r3.length - 1] + "]";
    }

    public ZoneOffset getOffset(LocalDateTime localDateTime) {
        Object objM860d = m860d(localDateTime);
        if (objM860d instanceof C2391b) {
            return ((C2391b) objM860d).f994c;
        }
        return (ZoneOffset) objM860d;
    }

    public ZoneRules(ZoneOffset zoneOffset) {
        ZoneOffset[] zoneOffsetArr = {zoneOffset};
        this.f982b = zoneOffsetArr;
        long[] jArr = f977i;
        this.f981a = jArr;
        this.f983c = jArr;
        this.f984d = f979k;
        this.f985e = zoneOffsetArr;
        this.f986f = f978j;
        this.f987g = null;
    }

    public ZoneRules(TimeZone timeZone) {
        ZoneOffset[] zoneOffsetArr = {m858h(timeZone.getRawOffset())};
        this.f982b = zoneOffsetArr;
        long[] jArr = f977i;
        this.f981a = jArr;
        this.f983c = jArr;
        this.f984d = f979k;
        this.f985e = zoneOffsetArr;
        this.f986f = f978j;
        this.f987g = timeZone;
    }
}
