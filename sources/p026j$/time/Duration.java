package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigInteger;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.Temporal;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class Duration implements InterfaceC2379o, Comparable<Duration>, Serializable {

    /* JADX INFO: renamed from: c */
    public static final Duration f721c = new Duration(0, 0);
    private static final long serialVersionUID = 3078945930695997490L;

    /* JADX INFO: renamed from: a */
    public final long f722a;

    /* JADX INFO: renamed from: b */
    public final int f723b;

    static {
        BigInteger.valueOf(1000000000L);
    }

    public Duration(long j, int i) {
        this.f722a = j;
        this.f723b = i;
    }

    /* JADX INFO: renamed from: B */
    public static Duration m574B(long j, int i) {
        return (((long) i) | j) == 0 ? f721c : new Duration(j, i);
    }

    /* JADX INFO: renamed from: G */
    public static Duration m575G(long j) {
        long j2 = j / 1000000000;
        int i = (int) (j % 1000000000);
        if (i < 0) {
            i = (int) (((long) i) + 1000000000);
            j2--;
        }
        return m574B(j2, i);
    }

    public static Duration ofHours(long j) {
        return m574B(Math.multiplyExact(j, 3600L), 0);
    }

    public static Duration ofMinutes(long j) {
        return m574B(Math.multiplyExact(j, 60L), 0);
    }

    public static Duration ofSeconds(long j, long j2) {
        return m574B(Math.addExact(j, Math.floorDiv(j2, 1000000000L)), (int) Math.floorMod(j2, 1000000000L));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 1, this);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Duration duration) {
        Duration duration2 = duration;
        int iCompare = Long.compare(this.f722a, duration2.f722a);
        return iCompare != 0 ? iCompare : this.f723b - duration2.f723b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Duration) {
            Duration duration = (Duration) obj;
            if (this.f722a == duration.f722a && this.f723b == duration.f723b) {
                return true;
            }
        }
        return false;
    }

    public int getNano() {
        return this.f723b;
    }

    public long getSeconds() {
        return this.f722a;
    }

    public final int hashCode() {
        long j = this.f722a;
        return (this.f723b * 51) + ((int) (j ^ (j >>> 32)));
    }

    @Override // p026j$.time.temporal.InterfaceC2379o
    /* JADX INFO: renamed from: t */
    public final Temporal mo576t(Temporal temporal) {
        long j = this.f722a;
        if (j != 0) {
            temporal = temporal.mo583b(j, ChronoUnit.SECONDS);
        }
        int i = this.f723b;
        return i != 0 ? temporal.mo583b(i, ChronoUnit.NANOS) : temporal;
    }

    public long toMillis() {
        long j = this.f722a;
        long j2 = this.f723b;
        if (j < 0) {
            j++;
            j2 -= 1000000000;
        }
        return Math.addExact(Math.multiplyExact(j, 1000L), j2 / 1000000);
    }

    public final String toString() {
        if (this == f721c) {
            return "PT0S";
        }
        long j = this.f722a;
        if (j < 0 && this.f723b > 0) {
            j++;
        }
        long j2 = j / 3600;
        int i = (int) ((j % 3600) / 60);
        int i2 = (int) (j % 60);
        StringBuilder sb = new StringBuilder(24);
        sb.append("PT");
        if (j2 != 0) {
            sb.append(j2);
            sb.append('H');
        }
        if (i != 0) {
            sb.append(i);
            sb.append('M');
        }
        if (i2 == 0 && this.f723b == 0 && sb.length() > 2) {
            return sb.toString();
        }
        if (this.f722a >= 0 || this.f723b <= 0 || i2 != 0) {
            sb.append(i2);
        } else {
            sb.append("-0");
        }
        if (this.f723b > 0) {
            int length = sb.length();
            long j3 = this.f722a;
            int i3 = this.f723b;
            if (j3 < 0) {
                sb.append(2000000000 - ((long) i3));
            } else {
                sb.append(((long) i3) + 1000000000);
            }
            while (sb.charAt(sb.length() - 1) == '0') {
                sb.setLength(sb.length() - 1);
            }
            sb.setCharAt(length, '.');
        }
        sb.append('S');
        return sb.toString();
    }

    public static Duration ofSeconds(long j) {
        return m574B(j, 0);
    }
}
