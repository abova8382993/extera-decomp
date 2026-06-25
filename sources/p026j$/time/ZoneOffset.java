package p026j$.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.RichMessageLayout;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.Temporal;
import p026j$.time.zone.ZoneRules;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class ZoneOffset extends ZoneId implements InterfaceC2376l, InterfaceC2377m, Comparable<ZoneOffset>, Serializable {
    private static final long serialVersionUID = 2357656521762053153L;

    /* JADX INFO: renamed from: b */
    public final int f751b;

    /* JADX INFO: renamed from: c */
    public final transient String f752c;

    /* JADX INFO: renamed from: d */
    public static final ConcurrentMap f747d = new ConcurrentHashMap(16, 0.75f, 4);

    /* JADX INFO: renamed from: e */
    public static final ConcurrentMap f748e = new ConcurrentHashMap(16, 0.75f, 4);
    public static final ZoneOffset UTC = m653Z(0);

    /* JADX INFO: renamed from: f */
    public static final ZoneOffset f749f = m653Z(-64800);

    /* JADX INFO: renamed from: g */
    public static final ZoneOffset f750g = m653Z(64800);

    public ZoneOffset(int i) {
        String string;
        this.f751b = i;
        if (i == 0) {
            string = "Z";
        } else {
            int iAbs = Math.abs(i);
            StringBuilder sb = new StringBuilder();
            int i2 = iAbs / 3600;
            int i3 = (iAbs / 60) % 60;
            sb.append(i < 0 ? "-" : "+");
            sb.append(i2 < 10 ? MVEL.VERSION_SUB : _UrlKt.FRAGMENT_ENCODE_SET);
            sb.append(i2);
            sb.append(i3 < 10 ? ":0" : ":");
            sb.append(i3);
            int i4 = iAbs % 60;
            if (i4 != 0) {
                sb.append(i4 < 10 ? ":0" : ":");
                sb.append(i4);
            }
            string = sb.toString();
        }
        this.f752c = string;
    }

    /* JADX INFO: renamed from: V */
    public static ZoneOffset m650V(Temporal temporal) {
        Objects.requireNonNull(temporal, "temporal");
        ZoneOffset zoneOffset = (ZoneOffset) temporal.mo568d(AbstractC2381q.f961d);
        if (zoneOffset != null) {
            return zoneOffset;
        }
        C2351g.m801f("Unable to obtain ZoneOffset from TemporalAccessor: ", temporal, " of type ", temporal.getClass().getName());
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00a7  */
    /* JADX INFO: renamed from: X */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static p026j$.time.ZoneOffset m651X(java.lang.String r8) {
        /*
            java.lang.String r0 = "offsetId"
            java.util.Objects.requireNonNull(r8, r0)
            java.util.concurrent.ConcurrentMap r0 = p026j$.time.ZoneOffset.f748e
            java.util.concurrent.ConcurrentHashMap r0 = (java.util.concurrent.ConcurrentHashMap) r0
            java.lang.Object r0 = r0.get(r8)
            j$.time.ZoneOffset r0 = (p026j$.time.ZoneOffset) r0
            if (r0 == 0) goto L12
            return r0
        L12:
            int r0 = r8.length()
            r1 = 2
            r2 = 0
            r3 = 1
            r4 = 0
            if (r0 == r1) goto L64
            r1 = 3
            if (r0 == r1) goto L80
            r5 = 5
            if (r0 == r5) goto L5b
            r6 = 6
            r7 = 4
            if (r0 == r6) goto L51
            r6 = 7
            if (r0 == r6) goto L44
            r1 = 9
            if (r0 != r1) goto L3a
            int r0 = m654a0(r8, r3, r4)
            int r1 = m654a0(r8, r7, r3)
            int r3 = m654a0(r8, r6, r3)
            goto L86
        L3a:
            java.lang.String r0 = "Invalid ID for ZoneOffset, invalid format: "
            java.lang.String r8 = r0.concat(r8)
            p026j$.time.C2351g.m796a(r8)
            return r2
        L44:
            int r0 = m654a0(r8, r3, r4)
            int r1 = m654a0(r8, r1, r4)
            int r3 = m654a0(r8, r5, r4)
            goto L86
        L51:
            int r0 = m654a0(r8, r3, r4)
            int r1 = m654a0(r8, r7, r3)
        L59:
            r3 = r4
            goto L86
        L5b:
            int r0 = m654a0(r8, r3, r4)
            int r1 = m654a0(r8, r1, r4)
            goto L59
        L64:
            char r0 = r8.charAt(r4)
            char r8 = r8.charAt(r3)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "0"
            r1.append(r0)
            r1.append(r8)
            java.lang.String r8 = r1.toString()
        L80:
            int r0 = m654a0(r8, r3, r4)
            r1 = r4
            r3 = r1
        L86:
            char r4 = r8.charAt(r4)
            r5 = 43
            r6 = 45
            if (r4 == r5) goto L9d
            if (r4 != r6) goto L93
            goto L9d
        L93:
            java.lang.String r0 = "Invalid ID for ZoneOffset, plus/minus not found when expected: "
            java.lang.String r8 = r0.concat(r8)
            p026j$.time.C2351g.m796a(r8)
            return r2
        L9d:
            if (r4 != r6) goto La7
            int r8 = -r0
            int r0 = -r1
            int r1 = -r3
            j$.time.ZoneOffset r8 = m652Y(r8, r0, r1)
            return r8
        La7:
            j$.time.ZoneOffset r8 = m652Y(r0, r1, r3)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: p026j$.time.ZoneOffset.m651X(java.lang.String):j$.time.ZoneOffset");
    }

    /* JADX INFO: renamed from: Y */
    public static ZoneOffset m652Y(int i, int i2, int i3) {
        if (i < -18 || i > 18) {
            C2351g.m798c("Zone offset hours not in valid range: value ", i, " is not in the range -18 to 18");
            return null;
        }
        if (i > 0) {
            if (i2 < 0 || i3 < 0) {
                C2351g.m796a("Zone offset minutes and seconds must be positive because hours is positive");
                return null;
            }
        } else if (i < 0) {
            if (i2 > 0 || i3 > 0) {
                C2351g.m796a("Zone offset minutes and seconds must be negative because hours is negative");
                return null;
            }
        } else if ((i2 > 0 && i3 < 0) || (i2 < 0 && i3 > 0)) {
            C2351g.m796a("Zone offset minutes and seconds must have the same sign");
            return null;
        }
        if (i2 < -59 || i2 > 59) {
            C2351g.m798c("Zone offset minutes not in valid range: value ", i2, " is not in the range -59 to 59");
            return null;
        }
        if (i3 < -59 || i3 > 59) {
            C2351g.m798c("Zone offset seconds not in valid range: value ", i3, " is not in the range -59 to 59");
            return null;
        }
        if (Math.abs(i) != 18 || (i2 | i3) == 0) {
            return m653Z((i2 * 60) + (i * 3600) + i3);
        }
        C2351g.m796a("Zone offset not in valid range: -18:00 to +18:00");
        return null;
    }

    /* JADX INFO: renamed from: Z */
    public static ZoneOffset m653Z(int i) {
        if (i < -64800 || i > 64800) {
            C2351g.m796a("Zone offset not in valid range: -18:00 to +18:00");
            return null;
        }
        if (i % RichMessageLayout.PART_MAX_HEIGHT_DP != 0) {
            return new ZoneOffset(i);
        }
        Integer numValueOf = Integer.valueOf(i);
        ConcurrentMap concurrentMap = f747d;
        ZoneOffset zoneOffset = (ZoneOffset) concurrentMap.get(numValueOf);
        if (zoneOffset != null) {
            return zoneOffset;
        }
        concurrentMap.putIfAbsent(numValueOf, new ZoneOffset(i));
        ZoneOffset zoneOffset2 = (ZoneOffset) concurrentMap.get(numValueOf);
        f748e.putIfAbsent(zoneOffset2.f752c, zoneOffset2);
        return zoneOffset2;
    }

    /* JADX INFO: renamed from: a0 */
    public static int m654a0(CharSequence charSequence, int i, boolean z) {
        if (z) {
            String str = (String) charSequence;
            if (str.charAt(i - 1) != ':') {
                C2351g.m804i("Invalid ID for ZoneOffset, colon not found when expected: ", str);
                return 0;
            }
        }
        String str2 = (String) charSequence;
        char cCharAt = str2.charAt(i);
        char cCharAt2 = str2.charAt(i + 1);
        if (cCharAt < '0' || cCharAt > '9' || cCharAt2 < '0' || cCharAt2 > '9') {
            C2351g.m804i("Invalid ID for ZoneOffset, non numeric characters found: ", str2);
            return 0;
        }
        return (cCharAt2 - '0') + ((cCharAt - '0') * 10);
    }

    /* JADX INFO: renamed from: b0 */
    public static ZoneOffset m655b0(DataInput dataInput) throws IOException {
        byte b2 = dataInput.readByte();
        return b2 == 127 ? m653Z(dataInput.readInt()) : m653Z(b2 * 900);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 8, this);
    }

    @Override // p026j$.time.ZoneId
    /* JADX INFO: renamed from: P */
    public final void mo649P(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(8);
        m656c0(dataOutput);
    }

    /* JADX INFO: renamed from: c0 */
    public final void m656c0(DataOutput dataOutput) throws IOException {
        int i = this.f751b;
        int i2 = i % RichMessageLayout.PART_MAX_HEIGHT_DP == 0 ? i / RichMessageLayout.PART_MAX_HEIGHT_DP : 127;
        dataOutput.writeByte(i2);
        if (i2 == 127) {
            dataOutput.writeInt(i);
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(ZoneOffset zoneOffset) {
        return zoneOffset.f751b - this.f751b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return (c2388x == AbstractC2381q.f961d || c2388x == AbstractC2381q.f962e) ? this : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(this.f751b, EnumC2365a.OFFSET_SECONDS);
    }

    @Override // p026j$.time.ZoneId
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ZoneOffset) && this.f751b == ((ZoneOffset) obj).f751b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.OFFSET_SECONDS) {
            return this.f751b;
        }
        if (interfaceC2380p == null) {
            return super.mo573m(interfaceC2380p).m849a(mo572k(interfaceC2380p), interfaceC2380p);
        }
        throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
    }

    @Override // p026j$.time.ZoneId
    public final String getId() {
        return this.f752c;
    }

    @Override // p026j$.time.ZoneId
    public final ZoneRules getRules() {
        return new ZoneRules(this);
    }

    public int getTotalSeconds() {
        return this.f751b;
    }

    @Override // p026j$.time.ZoneId
    public final int hashCode() {
        return this.f751b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.OFFSET_SECONDS : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.OFFSET_SECONDS) {
            return this.f751b;
        }
        if (interfaceC2380p instanceof EnumC2365a) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        return interfaceC2380p.mo837P(this);
    }

    @Override // p026j$.time.ZoneId
    public final String toString() {
        return this.f752c;
    }
}
