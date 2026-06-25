package p026j$.time.chrono;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.util.concurrent.ConcurrentHashMap;
import p026j$.time.C2354j;
import p026j$.time.LocalDate;
import p026j$.time.ZoneId;
import p026j$.time.ZoneOffset;
import p026j$.time.temporal.EnumC2365a;

/* JADX INFO: renamed from: j$.time.chrono.d0 */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2292d0 implements Externalizable {
    private static final long serialVersionUID = -6103370247208168577L;

    /* JADX INFO: renamed from: a */
    public byte f764a;

    /* JADX INFO: renamed from: b */
    public Object f765b;

    public C2292d0(byte b2, Object obj) {
        this.f764a = b2;
        this.f765b = obj;
    }

    private Object readResolve() {
        return this.f765b;
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) throws IOException {
        Object objM718of;
        byte b2 = objectInput.readByte();
        this.f764a = b2;
        switch (b2) {
            case 1:
                ConcurrentHashMap concurrentHashMap = AbstractC2285a.f758a;
                objM718of = InterfaceC2304k.m718of(objectInput.readUTF());
                break;
            case 2:
                objM718of = ((InterfaceC2287b) objectInput.readObject()).mo596K((C2354j) objectInput.readObject());
                break;
            case 3:
                objM718of = ((ChronoLocalDateTime) objectInput.readObject()).mo620F((ZoneOffset) objectInput.readObject()).mo660E((ZoneId) objectInput.readObject());
                break;
            case 4:
                LocalDate localDate = C2316w.f809d;
                int i = objectInput.readInt();
                byte b3 = objectInput.readByte();
                byte b4 = objectInput.readByte();
                C2314u.f807c.getClass();
                objM718of = new C2316w(LocalDate.m593of(i, b3, b4));
                break;
            case 5:
                C2317x c2317x = C2317x.f813d;
                objM718of = C2317x.m739t(objectInput.readByte());
                break;
            case 6:
                C2307n c2307n = (C2307n) objectInput.readObject();
                int i2 = objectInput.readInt();
                byte b5 = objectInput.readByte();
                byte b6 = objectInput.readByte();
                c2307n.getClass();
                objM718of = new C2309p(c2307n, i2, b5, b6);
                break;
            case 7:
                int i3 = objectInput.readInt();
                byte b7 = objectInput.readByte();
                byte b8 = objectInput.readByte();
                C2319z.f819c.getClass();
                objM718of = new C2288b0(LocalDate.m593of(i3 + 1911, b7, b8));
                break;
            case 8:
                int i4 = objectInput.readInt();
                byte b9 = objectInput.readByte();
                byte b10 = objectInput.readByte();
                C2296f0.f770c.getClass();
                objM718of = new C2300h0(LocalDate.m593of(i4 - 543, b9, b10));
                break;
            case 9:
                int i5 = C2297g.f771e;
                objM718of = new C2297g(InterfaceC2304k.m718of(objectInput.readUTF()), objectInput.readInt(), objectInput.readInt(), objectInput.readInt());
                break;
            default:
                throw new StreamCorruptedException("Unknown serialized type");
        }
        this.f765b = objM718of;
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) throws IOException {
        byte b2 = this.f764a;
        Object obj = this.f765b;
        objectOutput.writeByte(b2);
        switch (b2) {
            case 1:
                objectOutput.writeUTF(((AbstractC2285a) obj).getId());
                return;
            case 2:
                C2295f c2295f = (C2295f) obj;
                objectOutput.writeObject(c2295f.f768a);
                objectOutput.writeObject(c2295f.f769b);
                return;
            case 3:
                C2303j c2303j = (C2303j) obj;
                objectOutput.writeObject(c2303j.f782a);
                objectOutput.writeObject(c2303j.f783b);
                objectOutput.writeObject(c2303j.f784c);
                return;
            case 4:
                C2316w c2316w = (C2316w) obj;
                c2316w.getClass();
                objectOutput.writeInt(c2316w.mo570g(EnumC2365a.YEAR));
                objectOutput.writeByte(c2316w.mo570g(EnumC2365a.MONTH_OF_YEAR));
                objectOutput.writeByte(c2316w.mo570g(EnumC2365a.DAY_OF_MONTH));
                return;
            case 5:
                objectOutput.writeByte(((C2317x) obj).f815a);
                return;
            case 6:
                C2309p c2309p = (C2309p) obj;
                objectOutput.writeObject(c2309p.f797a);
                objectOutput.writeInt(c2309p.mo570g(EnumC2365a.YEAR));
                objectOutput.writeByte(c2309p.mo570g(EnumC2365a.MONTH_OF_YEAR));
                objectOutput.writeByte(c2309p.mo570g(EnumC2365a.DAY_OF_MONTH));
                return;
            case 7:
                C2288b0 c2288b0 = (C2288b0) obj;
                c2288b0.getClass();
                objectOutput.writeInt(c2288b0.mo570g(EnumC2365a.YEAR));
                objectOutput.writeByte(c2288b0.mo570g(EnumC2365a.MONTH_OF_YEAR));
                objectOutput.writeByte(c2288b0.mo570g(EnumC2365a.DAY_OF_MONTH));
                return;
            case 8:
                C2300h0 c2300h0 = (C2300h0) obj;
                c2300h0.getClass();
                objectOutput.writeInt(c2300h0.mo570g(EnumC2365a.YEAR));
                objectOutput.writeByte(c2300h0.mo570g(EnumC2365a.MONTH_OF_YEAR));
                objectOutput.writeByte(c2300h0.mo570g(EnumC2365a.DAY_OF_MONTH));
                return;
            case 9:
                C2297g c2297g = (C2297g) obj;
                objectOutput.writeUTF(c2297g.f772a.getId());
                objectOutput.writeInt(c2297g.f773b);
                objectOutput.writeInt(c2297g.f774c);
                objectOutput.writeInt(c2297g.f775d);
                return;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }

    public C2292d0() {
    }
}
