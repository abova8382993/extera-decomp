package p026j$.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.time.DurationKt;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.telegram.messenger.MediaDataController;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.j */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2354j implements Temporal, InterfaceC2377m, Comparable, Serializable {

    /* JADX INFO: renamed from: e */
    public static final C2354j f912e;

    /* JADX INFO: renamed from: f */
    public static final C2354j f913f;

    /* JADX INFO: renamed from: g */
    public static final C2354j f914g;

    /* JADX INFO: renamed from: h */
    public static final C2354j[] f915h = new C2354j[24];
    private static final long serialVersionUID = 6414437269572265201L;

    /* JADX INFO: renamed from: a */
    public final byte f916a;

    /* JADX INFO: renamed from: b */
    public final byte f917b;

    /* JADX INFO: renamed from: c */
    public final byte f918c;

    /* JADX INFO: renamed from: d */
    public final int f919d;

    static {
        int i = 0;
        while (true) {
            C2354j[] c2354jArr = f915h;
            if (i >= c2354jArr.length) {
                C2354j c2354j = c2354jArr[0];
                f914g = c2354j;
                C2354j c2354j2 = c2354jArr[12];
                f912e = c2354j;
                f913f = new C2354j(23, 59, 59, 999999999);
                return;
            }
            c2354jArr[i] = new C2354j(i, 0, 0, 0);
            i++;
        }
    }

    public C2354j(int i, int i2, int i3, int i4) {
        this.f916a = (byte) i;
        this.f917b = (byte) i2;
        this.f918c = (byte) i3;
        this.f919d = i4;
    }

    /* JADX INFO: renamed from: B */
    public static C2354j m805B(int i, int i2, int i3, int i4) {
        return ((i2 | i3) | i4) == 0 ? f915h[i] : new C2354j(i, i2, i3, i4);
    }

    /* JADX INFO: renamed from: G */
    public static C2354j m806G(InterfaceC2376l interfaceC2376l) {
        Objects.requireNonNull(interfaceC2376l, "temporal");
        C2354j c2354j = (C2354j) interfaceC2376l.mo568d(AbstractC2381q.f964g);
        if (c2354j != null) {
            return c2354j;
        }
        C2351g.m801f("Unable to obtain LocalTime from TemporalAccessor: ", interfaceC2376l, " of type ", interfaceC2376l.getClass().getName());
        return null;
    }

    /* JADX INFO: renamed from: P */
    public static C2354j m807P(int i, int i2, int i3, int i4) {
        EnumC2365a.HOUR_OF_DAY.m839X(i);
        EnumC2365a.MINUTE_OF_HOUR.m839X(i2);
        EnumC2365a.SECOND_OF_MINUTE.m839X(i3);
        EnumC2365a.NANO_OF_SECOND.m839X(i4);
        return m805B(i, i2, i3, i4);
    }

    /* JADX INFO: renamed from: V */
    public static C2354j m808V(long j) {
        EnumC2365a.NANO_OF_DAY.m839X(j);
        int i = (int) (j / 3600000000000L);
        long j2 = j - (((long) i) * 3600000000000L);
        int i2 = (int) (j2 / 60000000000L);
        long j3 = j2 - (((long) i2) * 60000000000L);
        int i3 = (int) (j3 / 1000000000);
        return m805B(i, i2, i3, (int) (j3 - (((long) i3) * 1000000000)));
    }

    /* JADX INFO: renamed from: c0 */
    public static C2354j m809c0(DataInput dataInput) throws IOException {
        int i;
        int i2;
        int i3 = dataInput.readByte();
        int i4 = 0;
        if (i3 < 0) {
            i3 = ~i3;
            i2 = 0;
            i = 0;
        } else {
            byte b2 = dataInput.readByte();
            if (b2 < 0) {
                int i5 = ~b2;
                i = 0;
                i4 = i5;
                i2 = 0;
            } else {
                byte b3 = dataInput.readByte();
                if (b3 < 0) {
                    i2 = ~b3;
                    i = 0;
                    i4 = b2;
                } else {
                    i = dataInput.readInt();
                    i4 = b2;
                    i2 = b3;
                }
            }
        }
        return m807P(i3, i4, i2, i);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 4, this);
    }

    /* JADX INFO: renamed from: I */
    public final int m810I(InterfaceC2380p interfaceC2380p) {
        switch (AbstractC2353i.f910a[((EnumC2365a) interfaceC2380p).ordinal()]) {
            case 1:
                return this.f919d;
            case 2:
                throw new C2383s("Invalid field 'NanoOfDay' for get() method, use getLong() instead");
            case 3:
                return this.f919d / MediaDataController.MAX_STYLE_RUNS_COUNT;
            case 4:
                throw new C2383s("Invalid field 'MicroOfDay' for get() method, use getLong() instead");
            case 5:
                return this.f919d / DurationKt.NANOS_IN_MILLIS;
            case 6:
                return (int) (m816d0() / 1000000);
            case 7:
                return this.f918c;
            case 8:
                return m817e0();
            case 9:
                return this.f917b;
            case 10:
                return (this.f916a * 60) + this.f917b;
            case 11:
                return this.f916a % 12;
            case 12:
                int i = this.f916a % 12;
                if (i % 12 == 0) {
                    return 12;
                }
                return i;
            case 13:
                return this.f916a;
            case 14:
                byte b2 = this.f916a;
                if (b2 == 0) {
                    return 24;
                }
                return b2;
            case 15:
                return this.f916a / 12;
            default:
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: X */
    public final C2354j mo583b(long j, InterfaceC2382r interfaceC2382r) {
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return (C2354j) interfaceC2382r.mo834t(this, j);
        }
        switch (AbstractC2353i.f911b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return m814a0(j);
            case 2:
                return m814a0((j % 86400000000L) * 1000);
            case 3:
                return m814a0((j % DurationKt.MILLIS_IN_DAY) * 1000000);
            case 4:
                return m815b0(j);
            case 5:
                return m813Z(j);
            case 6:
                return m812Y(j);
            case 7:
                return m812Y((j % 2) * 12);
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return null;
        }
    }

    /* JADX INFO: renamed from: Y */
    public final C2354j m812Y(long j) {
        return j == 0 ? this : m805B(((((int) (j % 24)) + this.f916a) + 24) % 24, this.f917b, this.f918c, this.f919d);
    }

    /* JADX INFO: renamed from: Z */
    public final C2354j m813Z(long j) {
        if (j != 0) {
            int i = (this.f916a * 60) + this.f917b;
            int i2 = ((((int) (j % 1440)) + i) + 1440) % 1440;
            if (i != i2) {
                return m805B(i2 / 60, i2 % 60, this.f918c, this.f919d);
            }
        }
        return this;
    }

    /* JADX INFO: renamed from: a0 */
    public final C2354j m814a0(long j) {
        if (j != 0) {
            long jM816d0 = m816d0();
            long j2 = (((j % 86400000000000L) + jM816d0) + 86400000000000L) % 86400000000000L;
            if (jM816d0 != j2) {
                return m805B((int) (j2 / 3600000000000L), (int) ((j2 / 60000000000L) % 60), (int) ((j2 / 1000000000) % 60), (int) (j2 % 1000000000));
            }
        }
        return this;
    }

    /* JADX INFO: renamed from: b0 */
    public final C2354j m815b0(long j) {
        if (j != 0) {
            int i = (this.f917b * 60) + (this.f916a * 3600) + this.f918c;
            int i2 = ((((int) (j % 86400)) + i) + 86400) % 86400;
            if (i != i2) {
                return m805B(i2 / 3600, (i2 / 60) % 60, i2 % 60, this.f919d);
            }
        }
        return this;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    public final Temporal mo584c(long j, InterfaceC2382r interfaceC2382r) {
        long j2;
        if (j == Long.MIN_VALUE) {
            this = mo583b(LongCompanionObject.MAX_VALUE, interfaceC2382r);
            j2 = 1;
        } else {
            j2 = -j;
        }
        return this.mo583b(j2, interfaceC2382r);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f959b || c2388x == AbstractC2381q.f958a || c2388x == AbstractC2381q.f962e || c2388x == AbstractC2381q.f961d) {
            return null;
        }
        if (c2388x == AbstractC2381q.f964g) {
            return this;
        }
        if (c2388x == AbstractC2381q.f963f) {
            return null;
        }
        return c2388x == AbstractC2381q.f960c ? ChronoUnit.NANOS : c2388x.m855m(this);
    }

    /* JADX INFO: renamed from: d0 */
    public final long m816d0() {
        return (((long) this.f918c) * 1000000000) + (((long) this.f917b) * 60000000000L) + (((long) this.f916a) * 3600000000000L) + ((long) this.f919d);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(m816d0(), EnumC2365a.NANO_OF_DAY);
    }

    /* JADX INFO: renamed from: e0 */
    public final int m817e0() {
        return (this.f917b * 60) + (this.f916a * 3600) + this.f918c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C2354j) {
            C2354j c2354j = (C2354j) obj;
            if (this.f916a == c2354j.f916a && this.f917b == c2354j.f917b && this.f918c == c2354j.f918c && this.f919d == c2354j.f919d) {
                return true;
            }
        }
        return false;
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: f0 */
    public final C2354j mo582a(long j, InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return (C2354j) interfaceC2380p.mo838V(this, j);
        }
        EnumC2365a enumC2365a = (EnumC2365a) interfaceC2380p;
        enumC2365a.m839X(j);
        switch (AbstractC2353i.f910a[enumC2365a.ordinal()]) {
            case 1:
                return m819g0((int) j);
            case 2:
                return m808V(j);
            case 3:
                return m819g0(((int) j) * MediaDataController.MAX_STYLE_RUNS_COUNT);
            case 4:
                return m808V(j * 1000);
            case 5:
                return m819g0(((int) j) * DurationKt.NANOS_IN_MILLIS);
            case 6:
                return m808V(j * 1000000);
            case 7:
                int i = (int) j;
                if (this.f918c != i) {
                    EnumC2365a.SECOND_OF_MINUTE.m839X(i);
                    return m805B(this.f916a, this.f917b, i, this.f919d);
                }
                return this;
            case 8:
                return m815b0(j - ((long) m817e0()));
            case 9:
                int i2 = (int) j;
                if (this.f917b != i2) {
                    EnumC2365a.MINUTE_OF_HOUR.m839X(i2);
                    return m805B(this.f916a, i2, this.f918c, this.f919d);
                }
                return this;
            case 10:
                return m813Z(j - ((long) ((this.f916a * 60) + this.f917b)));
            case 11:
                return m812Y(j - ((long) (this.f916a % 12)));
            case 12:
                if (j == 12) {
                    j = 0;
                }
                return m812Y(j - ((long) (this.f916a % 12)));
            case 13:
                int i3 = (int) j;
                if (this.f916a != i3) {
                    EnumC2365a.HOUR_OF_DAY.m839X(i3);
                    return m805B(i3, this.f917b, this.f918c, this.f919d);
                }
                return this;
            case 14:
                if (j == 24) {
                    j = 0;
                }
                int i4 = (int) j;
                if (this.f916a != i4) {
                    EnumC2365a.HOUR_OF_DAY.m839X(i4);
                    return m805B(i4, this.f917b, this.f918c, this.f919d);
                }
                return this;
            case 15:
                return m812Y((j - ((long) (this.f916a / 12))) * 12);
            default:
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? m810I(interfaceC2380p) : super.mo570g(interfaceC2380p);
    }

    /* JADX INFO: renamed from: g0 */
    public final C2354j m819g0(int i) {
        if (this.f919d == i) {
            return this;
        }
        EnumC2365a.NANO_OF_SECOND.m839X(i);
        return m805B(this.f916a, this.f917b, this.f918c, i);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: h */
    public final Temporal mo666j(LocalDate localDate) {
        return (C2354j) localDate.mo569e(this);
    }

    /* JADX INFO: renamed from: h0 */
    public final void m820h0(DataOutput dataOutput) {
        if (this.f919d != 0) {
            dataOutput.writeByte(this.f916a);
            dataOutput.writeByte(this.f917b);
            dataOutput.writeByte(this.f918c);
            dataOutput.writeInt(this.f919d);
            return;
        }
        if (this.f918c != 0) {
            dataOutput.writeByte(this.f916a);
            dataOutput.writeByte(this.f917b);
            dataOutput.writeByte(~this.f918c);
            return;
        }
        byte b2 = this.f917b;
        byte b3 = this.f916a;
        if (b2 == 0) {
            dataOutput.writeByte(~b3);
        } else {
            dataOutput.writeByte(b3);
            dataOutput.writeByte(~this.f917b);
        }
    }

    public final int hashCode() {
        long jM816d0 = m816d0();
        return (int) (jM816d0 ^ (jM816d0 >>> 32));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).m840Y() : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.NANO_OF_DAY ? m816d0() : interfaceC2380p == EnumC2365a.MICRO_OF_DAY ? m816d0() / 1000 : m810I(interfaceC2380p) : interfaceC2380p.mo837P(this);
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    public final long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r) {
        C2354j c2354jM806G = m806G(temporal);
        if (!(interfaceC2382r instanceof ChronoUnit)) {
            return interfaceC2382r.between(this, c2354jM806G);
        }
        long jM816d0 = c2354jM806G.m816d0() - m816d0();
        switch (AbstractC2353i.f911b[((ChronoUnit) interfaceC2382r).ordinal()]) {
            case 1:
                return jM816d0;
            case 2:
                return jM816d0 / 1000;
            case 3:
                return jM816d0 / 1000000;
            case 4:
                return jM816d0 / 1000000000;
            case 5:
                return jM816d0 / 60000000000L;
            case 6:
                return jM816d0 / 3600000000000L;
            case 7:
                return jM816d0 / 43200000000000L;
            default:
                C2351g.m799d("Unsupported unit: ", interfaceC2382r);
                return 0L;
        }
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: t */
    public final int compareTo(C2354j c2354j) {
        int iCompare = Integer.compare(this.f916a, c2354j.f916a);
        return (iCompare == 0 && (iCompare = Integer.compare(this.f917b, c2354j.f917b)) == 0 && (iCompare = Integer.compare(this.f918c, c2354j.f918c)) == 0) ? Integer.compare(this.f919d, c2354j.f919d) : iCompare;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(18);
        byte b2 = this.f916a;
        byte b3 = this.f917b;
        byte b4 = this.f918c;
        int i = this.f919d;
        sb.append(b2 < 10 ? MVEL.VERSION_SUB : _UrlKt.FRAGMENT_ENCODE_SET);
        sb.append((int) b2);
        sb.append(b3 < 10 ? ":0" : ":");
        sb.append((int) b3);
        if (b4 > 0 || i > 0) {
            sb.append(b4 < 10 ? ":0" : ":");
            sb.append((int) b4);
            if (i > 0) {
                sb.append('.');
                if (i % DurationKt.NANOS_IN_MILLIS == 0) {
                    sb.append(Integer.toString((i / DurationKt.NANOS_IN_MILLIS) + MediaDataController.MAX_STYLE_RUNS_COUNT).substring(1));
                } else if (i % MediaDataController.MAX_STYLE_RUNS_COUNT == 0) {
                    sb.append(Integer.toString((i / MediaDataController.MAX_STYLE_RUNS_COUNT) + DurationKt.NANOS_IN_MILLIS).substring(1));
                } else {
                    sb.append(Integer.toString(i + 1000000000).substring(1));
                }
            }
        }
        return sb.toString();
    }
}
