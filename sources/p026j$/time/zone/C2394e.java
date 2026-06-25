package p026j$.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import org.telegram.messenger.RichMessageLayout;
import p026j$.time.C2354j;
import p026j$.time.DayOfWeek;
import p026j$.time.EnumC2356l;
import p026j$.time.ZoneOffset;
import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.zone.e */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2394e implements Serializable {
    private static final long serialVersionUID = 6889046316657758795L;

    /* JADX INFO: renamed from: a */
    public final EnumC2356l f998a;

    /* JADX INFO: renamed from: b */
    public final byte f999b;

    /* JADX INFO: renamed from: c */
    public final DayOfWeek f1000c;

    /* JADX INFO: renamed from: d */
    public final C2354j f1001d;

    /* JADX INFO: renamed from: e */
    public final boolean f1002e;

    /* JADX INFO: renamed from: f */
    public final EnumC2393d f1003f;

    /* JADX INFO: renamed from: g */
    public final ZoneOffset f1004g;

    /* JADX INFO: renamed from: h */
    public final ZoneOffset f1005h;

    /* JADX INFO: renamed from: i */
    public final ZoneOffset f1006i;

    public C2394e(EnumC2356l enumC2356l, int i, DayOfWeek dayOfWeek, C2354j c2354j, boolean z, EnumC2393d enumC2393d, ZoneOffset zoneOffset, ZoneOffset zoneOffset2, ZoneOffset zoneOffset3) {
        this.f998a = enumC2356l;
        this.f999b = (byte) i;
        this.f1000c = dayOfWeek;
        this.f1001d = c2354j;
        this.f1002e = z;
        this.f1003f = enumC2393d;
        this.f1004g = zoneOffset;
        this.f1005h = zoneOffset2;
        this.f1006i = zoneOffset3;
    }

    /* JADX INFO: renamed from: a */
    public static C2394e m869a(DataInput dataInput) {
        EnumC2393d enumC2393d;
        C2354j c2354jM805B;
        int i = dataInput.readInt();
        EnumC2356l enumC2356lM822I = EnumC2356l.m822I(i >>> 28);
        int i2 = ((264241152 & i) >>> 22) - 32;
        int i3 = (3670016 & i) >>> 19;
        DayOfWeek dayOfWeekM567t = i3 == 0 ? null : DayOfWeek.m567t(i3);
        int i4 = (507904 & i) >>> 14;
        EnumC2393d enumC2393d2 = EnumC2393d.values()[(i & 12288) >>> 12];
        int i5 = (i & 4080) >>> 4;
        int i6 = (i & 12) >>> 2;
        int i7 = i & 3;
        if (i4 == 31) {
            long j = dataInput.readInt();
            C2354j c2354j = C2354j.f912e;
            EnumC2365a.SECOND_OF_DAY.m839X(j);
            int i8 = (int) (j / 3600);
            long j2 = j - ((long) (i8 * 3600));
            int i9 = (int) (j2 / 60);
            enumC2393d = enumC2393d2;
            c2354jM805B = C2354j.m805B(i8, i9, (int) (j2 - ((long) (i9 * 60))), 0);
        } else {
            enumC2393d = enumC2393d2;
            int i10 = i4 % 24;
            C2354j c2354j2 = C2354j.f912e;
            EnumC2365a.HOUR_OF_DAY.m839X(i10);
            c2354jM805B = C2354j.f915h[i10];
        }
        ZoneOffset zoneOffsetM653Z = ZoneOffset.m653Z(i5 == 255 ? dataInput.readInt() : (i5 - 128) * RichMessageLayout.PART_MAX_HEIGHT_DP);
        ZoneOffset zoneOffsetM653Z2 = ZoneOffset.m653Z(i6 == 3 ? dataInput.readInt() : (i6 * 1800) + zoneOffsetM653Z.getTotalSeconds());
        ZoneOffset zoneOffsetM653Z3 = ZoneOffset.m653Z(i7 == 3 ? dataInput.readInt() : (i7 * 1800) + zoneOffsetM653Z.getTotalSeconds());
        boolean z = i4 == 24;
        Objects.requireNonNull(enumC2356lM822I, "month");
        Objects.requireNonNull(c2354jM805B, "time");
        EnumC2393d enumC2393d3 = enumC2393d;
        Objects.requireNonNull(enumC2393d3, "timeDefnition");
        if (i2 < -28 || i2 > 31 || i2 == 0) {
            throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
        }
        if (z && !c2354jM805B.equals(C2354j.f914g)) {
            throw new IllegalArgumentException("Time must be midnight when end of day flag is true");
        }
        if (c2354jM805B.f919d == 0) {
            return new C2394e(enumC2356lM822I, i2, dayOfWeekM567t, c2354jM805B, z, enumC2393d3, zoneOffsetM653Z, zoneOffsetM653Z2, zoneOffsetM653Z3);
        }
        throw new IllegalArgumentException("Time's nano-of-second must be zero");
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2390a((byte) 3, this);
    }

    /* JADX INFO: renamed from: b */
    public final void m870b(DataOutput dataOutput) {
        int iM817e0 = this.f1002e ? 86400 : this.f1001d.m817e0();
        int totalSeconds = this.f1004g.getTotalSeconds();
        int totalSeconds2 = this.f1005h.getTotalSeconds() - totalSeconds;
        int totalSeconds3 = this.f1006i.getTotalSeconds() - totalSeconds;
        byte b2 = iM817e0 % 3600 == 0 ? this.f1002e ? (byte) 24 : this.f1001d.f916a : (byte) 31;
        int i = totalSeconds % RichMessageLayout.PART_MAX_HEIGHT_DP == 0 ? (totalSeconds / RichMessageLayout.PART_MAX_HEIGHT_DP) + 128 : 255;
        int i2 = (totalSeconds2 == 0 || totalSeconds2 == 1800 || totalSeconds2 == 3600) ? totalSeconds2 / 1800 : 3;
        int i3 = (totalSeconds3 == 0 || totalSeconds3 == 1800 || totalSeconds3 == 3600) ? totalSeconds3 / 1800 : 3;
        DayOfWeek dayOfWeek = this.f1000c;
        dataOutput.writeInt((this.f998a.getValue() << 28) + ((this.f999b + 32) << 22) + ((dayOfWeek == null ? 0 : dayOfWeek.getValue()) << 19) + (b2 << 14) + (this.f1003f.ordinal() << 12) + (i << 4) + (i2 << 2) + i3);
        if (b2 == 31) {
            dataOutput.writeInt(iM817e0);
        }
        if (i == 255) {
            dataOutput.writeInt(totalSeconds);
        }
        if (i2 == 3) {
            dataOutput.writeInt(this.f1005h.getTotalSeconds());
        }
        if (i3 == 3) {
            dataOutput.writeInt(this.f1006i.getTotalSeconds());
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C2394e)) {
            return false;
        }
        C2394e c2394e = (C2394e) obj;
        return this.f998a == c2394e.f998a && this.f999b == c2394e.f999b && this.f1000c == c2394e.f1000c && this.f1003f == c2394e.f1003f && this.f1001d.equals(c2394e.f1001d) && this.f1002e == c2394e.f1002e && this.f1004g.equals(c2394e.f1004g) && this.f1005h.equals(c2394e.f1005h) && this.f1006i.equals(c2394e.f1006i);
    }

    public final int hashCode() {
        int iM817e0 = ((this.f1001d.m817e0() + (this.f1002e ? 1 : 0)) << 15) + (this.f998a.ordinal() << 11) + ((this.f999b + 32) << 5);
        DayOfWeek dayOfWeek = this.f1000c;
        return this.f1006i.f751b ^ ((this.f1004g.f751b ^ (this.f1003f.ordinal() + (iM817e0 + ((dayOfWeek == null ? 7 : dayOfWeek.ordinal()) << 2)))) ^ this.f1005h.f751b);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TransitionRule[");
        sb.append(this.f1006i.f751b - this.f1005h.f751b > 0 ? "Gap " : "Overlap ");
        sb.append(this.f1005h);
        sb.append(" to ");
        sb.append(this.f1006i);
        sb.append(", ");
        DayOfWeek dayOfWeek = this.f1000c;
        if (dayOfWeek != null) {
            byte b2 = this.f999b;
            if (b2 == -1) {
                sb.append(dayOfWeek.name());
                sb.append(" on or before last day of ");
                sb.append(this.f998a.name());
            } else if (b2 < 0) {
                sb.append(dayOfWeek.name());
                sb.append(" on or before last day minus ");
                sb.append((-this.f999b) - 1);
                sb.append(" of ");
                sb.append(this.f998a.name());
            } else {
                sb.append(dayOfWeek.name());
                sb.append(" on or after ");
                sb.append(this.f998a.name());
                sb.append(' ');
                sb.append((int) this.f999b);
            }
        } else {
            sb.append(this.f998a.name());
            sb.append(' ');
            sb.append((int) this.f999b);
        }
        sb.append(" at ");
        sb.append(this.f1002e ? "24:00" : this.f1001d.toString());
        sb.append(" ");
        sb.append(this.f1003f);
        sb.append(", standard offset ");
        sb.append(this.f1004g);
        sb.append(']');
        return sb.toString();
    }
}
