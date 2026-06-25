package p026j$.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.util.TimeZone;
import kotlin.UByte;
import org.telegram.messenger.RichMessageLayout;
import p026j$.time.ZoneOffset;

/* JADX INFO: renamed from: j$.time.zone.a */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2390a implements Externalizable {
    private static final long serialVersionUID = -8885321777449118786L;

    /* JADX INFO: renamed from: a */
    public byte f989a;

    /* JADX INFO: renamed from: b */
    public Object f990b;

    public C2390a(byte b2, Object obj) {
        this.f989a = b2;
        this.f990b = obj;
    }

    /* JADX INFO: renamed from: a */
    public static long m864a(DataInput dataInput) {
        int i = dataInput.readByte() & UByte.MAX_VALUE;
        if (i == 255) {
            return dataInput.readLong();
        }
        return (((long) (((i << 16) + ((dataInput.readByte() & UByte.MAX_VALUE) << 8)) + (dataInput.readByte() & UByte.MAX_VALUE))) * 900) - 4575744000L;
    }

    /* JADX INFO: renamed from: b */
    public static ZoneOffset m865b(DataInput dataInput) throws IOException {
        byte b2 = dataInput.readByte();
        return b2 == 127 ? ZoneOffset.m653Z(dataInput.readInt()) : ZoneOffset.m653Z(b2 * 900);
    }

    /* JADX INFO: renamed from: c */
    public static void m866c(long j, DataOutput dataOutput) throws IOException {
        if (j < -4575744000L || j >= 10413792000L || j % 900 != 0) {
            dataOutput.writeByte(255);
            dataOutput.writeLong(j);
        } else {
            int i = (int) ((j + 4575744000L) / 900);
            dataOutput.writeByte((i >>> 16) & 255);
            dataOutput.writeByte((i >>> 8) & 255);
            dataOutput.writeByte(i & 255);
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m867d(ZoneOffset zoneOffset, DataOutput dataOutput) throws IOException {
        int totalSeconds = zoneOffset.getTotalSeconds();
        int i = totalSeconds % RichMessageLayout.PART_MAX_HEIGHT_DP == 0 ? totalSeconds / RichMessageLayout.PART_MAX_HEIGHT_DP : 127;
        dataOutput.writeByte(i);
        if (i == 127) {
            dataOutput.writeInt(totalSeconds);
        }
    }

    private Object readResolve() {
        return this.f990b;
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) throws IOException {
        Object zoneRules;
        byte b2 = objectInput.readByte();
        this.f989a = b2;
        if (b2 == 1) {
            long[] jArr = ZoneRules.f977i;
            int i = objectInput.readInt();
            long[] jArr2 = i == 0 ? jArr : new long[i];
            for (int i2 = 0; i2 < i; i2++) {
                jArr2[i2] = m864a(objectInput);
            }
            int i3 = i + 1;
            ZoneOffset[] zoneOffsetArr = new ZoneOffset[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                zoneOffsetArr[i4] = m865b(objectInput);
            }
            int i5 = objectInput.readInt();
            if (i5 != 0) {
                jArr = new long[i5];
            }
            long[] jArr3 = jArr;
            for (int i6 = 0; i6 < i5; i6++) {
                jArr3[i6] = m864a(objectInput);
            }
            int i7 = i5 + 1;
            ZoneOffset[] zoneOffsetArr2 = new ZoneOffset[i7];
            for (int i8 = 0; i8 < i7; i8++) {
                zoneOffsetArr2[i8] = m865b(objectInput);
            }
            int i9 = objectInput.readByte();
            C2394e[] c2394eArr = i9 == 0 ? ZoneRules.f978j : new C2394e[i9];
            for (int i10 = 0; i10 < i9; i10++) {
                c2394eArr[i10] = C2394e.m869a(objectInput);
            }
            zoneRules = new ZoneRules(jArr2, zoneOffsetArr, jArr3, zoneOffsetArr2, c2394eArr);
        } else if (b2 == 2) {
            int i11 = C2391b.f991e;
            long jM864a = m864a(objectInput);
            ZoneOffset zoneOffsetM865b = m865b(objectInput);
            ZoneOffset zoneOffsetM865b2 = m865b(objectInput);
            if (zoneOffsetM865b.equals(zoneOffsetM865b2)) {
                throw new IllegalArgumentException("Offsets must not be equal");
            }
            zoneRules = new C2391b(jM864a, zoneOffsetM865b, zoneOffsetM865b2);
        } else if (b2 == 3) {
            zoneRules = C2394e.m869a(objectInput);
        } else {
            if (b2 != 100) {
                throw new StreamCorruptedException("Unknown serialized type");
            }
            zoneRules = new ZoneRules(TimeZone.getTimeZone(objectInput.readUTF()));
        }
        this.f990b = zoneRules;
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) throws IOException {
        byte b2 = this.f989a;
        Object obj = this.f990b;
        objectOutput.writeByte(b2);
        if (b2 != 1) {
            if (b2 == 2) {
                C2391b c2391b = (C2391b) obj;
                m866c(c2391b.f992a, objectOutput);
                m867d(c2391b.f994c, objectOutput);
                m867d(c2391b.f995d, objectOutput);
                return;
            }
            if (b2 == 3) {
                ((C2394e) obj).m870b(objectOutput);
                return;
            } else {
                if (b2 != 100) {
                    throw new InvalidClassException("Unknown serialized type");
                }
                objectOutput.writeUTF(((ZoneRules) obj).f987g.getID());
                return;
            }
        }
        ZoneRules zoneRules = (ZoneRules) obj;
        objectOutput.writeInt(zoneRules.f981a.length);
        for (long j : zoneRules.f981a) {
            m866c(j, objectOutput);
        }
        for (ZoneOffset zoneOffset : zoneRules.f982b) {
            m867d(zoneOffset, objectOutput);
        }
        objectOutput.writeInt(zoneRules.f983c.length);
        for (long j2 : zoneRules.f983c) {
            m866c(j2, objectOutput);
        }
        for (ZoneOffset zoneOffset2 : zoneRules.f985e) {
            m867d(zoneOffset2, objectOutput);
        }
        objectOutput.writeByte(zoneRules.f986f.length);
        for (C2394e c2394e : zoneRules.f986f) {
            c2394e.m870b(objectOutput);
        }
    }

    public C2390a() {
    }
}
