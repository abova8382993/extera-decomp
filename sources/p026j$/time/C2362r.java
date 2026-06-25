package p026j$.time;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.util.Objects;
import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.r */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2362r implements Externalizable {
    private static final long serialVersionUID = -7683839454370182990L;

    /* JADX INFO: renamed from: a */
    public byte f932a;

    /* JADX INFO: renamed from: b */
    public Object f933b;

    public C2362r(byte b2, Object obj) {
        this.f932a = b2;
        this.f933b = obj;
    }

    /* JADX INFO: renamed from: a */
    public static Object m829a(byte b2, ObjectInput objectInput) throws IOException {
        switch (b2) {
            case 1:
                Duration duration = Duration.f721c;
                return Duration.ofSeconds(objectInput.readLong(), objectInput.readInt());
            case 2:
                Instant instant = Instant.f724c;
                return Instant.ofEpochSecond(objectInput.readLong(), objectInput.readInt());
            case 3:
                LocalDate localDate = LocalDate.f727d;
                return LocalDate.m593of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
            case 4:
                return C2354j.m809c0(objectInput);
            case 5:
                LocalDateTime localDateTime = LocalDateTime.f732c;
                LocalDate localDate2 = LocalDate.f727d;
                return LocalDateTime.m618I(LocalDate.m593of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), C2354j.m809c0(objectInput));
            case 6:
                LocalDateTime localDateTime2 = LocalDateTime.f732c;
                LocalDate localDate3 = LocalDate.f727d;
                LocalDateTime localDateTimeM618I = LocalDateTime.m618I(LocalDate.m593of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), C2354j.m809c0(objectInput));
                ZoneOffset zoneOffsetM655b0 = ZoneOffset.m655b0(objectInput);
                ZoneId zoneId = (ZoneId) m829a(objectInput.readByte(), objectInput);
                Objects.requireNonNull(zoneId, "zone");
                if (!(zoneId instanceof ZoneOffset) || zoneOffsetM655b0.equals(zoneId)) {
                    return new ZonedDateTime(localDateTimeM618I, zoneId, zoneOffsetM655b0);
                }
                throw new IllegalArgumentException("ZoneId must match ZoneOffset");
            case 7:
                int i = C2387w.f972d;
                return ZoneId.m644B(objectInput.readUTF(), false);
            case 8:
                return ZoneOffset.m655b0(objectInput);
            case 9:
                int i2 = C2361q.f929c;
                return new C2361q(C2354j.m809c0(objectInput), ZoneOffset.m655b0(objectInput));
            case 10:
                int i3 = OffsetDateTime.f736c;
                LocalDate localDate4 = LocalDate.f727d;
                return new OffsetDateTime(LocalDateTime.m618I(LocalDate.m593of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), C2354j.m809c0(objectInput)), ZoneOffset.m655b0(objectInput));
            case 11:
                int i4 = C2364t.f936b;
                return C2364t.m830t(objectInput.readInt());
            case 12:
                int i5 = YearMonth.f743c;
                return YearMonth.m637of(objectInput.readInt(), objectInput.readByte());
            case 13:
                int i6 = C2358n.f924c;
                byte b3 = objectInput.readByte();
                byte b4 = objectInput.readByte();
                EnumC2356l enumC2356lM822I = EnumC2356l.m822I(b3);
                Objects.requireNonNull(enumC2356lM822I, "month");
                EnumC2365a.DAY_OF_MONTH.m839X(b4);
                if (b4 <= enumC2356lM822I.m824G()) {
                    return new C2358n(enumC2356lM822I.getValue(), b4);
                }
                throw new C2284c("Illegal value for DayOfMonth field, value " + ((int) b4) + " is not valid for month " + enumC2356lM822I.name());
            case 14:
                Period period = Period.f739d;
                return Period.m636a(objectInput.readInt(), objectInput.readInt(), objectInput.readInt());
            default:
                throw new StreamCorruptedException("Unknown serialized type");
        }
    }

    private Object readResolve() {
        return this.f933b;
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) {
        byte b2 = objectInput.readByte();
        this.f932a = b2;
        this.f933b = m829a(b2, objectInput);
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) throws IOException {
        byte b2 = this.f932a;
        Object obj = this.f933b;
        objectOutput.writeByte(b2);
        switch (b2) {
            case 1:
                Duration duration = (Duration) obj;
                objectOutput.writeLong(duration.f722a);
                objectOutput.writeInt(duration.f723b);
                return;
            case 2:
                Instant instant = (Instant) obj;
                objectOutput.writeLong(instant.f725a);
                objectOutput.writeInt(instant.f726b);
                return;
            case 3:
                LocalDate localDate = (LocalDate) obj;
                objectOutput.writeInt(localDate.f729a);
                objectOutput.writeByte(localDate.f730b);
                objectOutput.writeByte(localDate.f731c);
                return;
            case 4:
                ((C2354j) obj).m820h0(objectOutput);
                return;
            case 5:
                LocalDateTime localDateTime = (LocalDateTime) obj;
                LocalDate localDate2 = localDateTime.f734a;
                objectOutput.writeInt(localDate2.f729a);
                objectOutput.writeByte(localDate2.f730b);
                objectOutput.writeByte(localDate2.f731c);
                localDateTime.f735b.m820h0(objectOutput);
                return;
            case 6:
                ZonedDateTime zonedDateTime = (ZonedDateTime) obj;
                LocalDateTime localDateTime2 = zonedDateTime.f753a;
                LocalDate localDate3 = localDateTime2.f734a;
                objectOutput.writeInt(localDate3.f729a);
                objectOutput.writeByte(localDate3.f730b);
                objectOutput.writeByte(localDate3.f731c);
                localDateTime2.f735b.m820h0(objectOutput);
                zonedDateTime.f754b.m656c0(objectOutput);
                zonedDateTime.f755c.mo649P(objectOutput);
                return;
            case 7:
                objectOutput.writeUTF(((C2387w) obj).f973b);
                return;
            case 8:
                ((ZoneOffset) obj).m656c0(objectOutput);
                return;
            case 9:
                C2361q c2361q = (C2361q) obj;
                c2361q.f930a.m820h0(objectOutput);
                c2361q.f931b.m656c0(objectOutput);
                return;
            case 10:
                OffsetDateTime offsetDateTime = (OffsetDateTime) obj;
                LocalDateTime localDateTime3 = offsetDateTime.f737a;
                LocalDate localDate4 = localDateTime3.f734a;
                objectOutput.writeInt(localDate4.f729a);
                objectOutput.writeByte(localDate4.f730b);
                objectOutput.writeByte(localDate4.f731c);
                localDateTime3.f735b.m820h0(objectOutput);
                offsetDateTime.f738b.m656c0(objectOutput);
                return;
            case 11:
                objectOutput.writeInt(((C2364t) obj).f937a);
                return;
            case 12:
                YearMonth yearMonth = (YearMonth) obj;
                objectOutput.writeInt(yearMonth.f744a);
                objectOutput.writeByte(yearMonth.f745b);
                return;
            case 13:
                C2358n c2358n = (C2358n) obj;
                objectOutput.writeByte(c2358n.f925a);
                objectOutput.writeByte(c2358n.f926b);
                return;
            case 14:
                Period period = (Period) obj;
                objectOutput.writeInt(period.f740a);
                objectOutput.writeInt(period.f741b);
                objectOutput.writeInt(period.f742c);
                return;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }

    public C2362r() {
    }
}
