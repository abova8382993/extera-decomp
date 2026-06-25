package p026j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p026j$.time.C2351g;
import p026j$.time.LocalDate;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.chrono.x */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2317x implements InterfaceC2305l, Serializable {

    /* JADX INFO: renamed from: d */
    public static final C2317x f813d;

    /* JADX INFO: renamed from: e */
    public static final C2317x[] f814e;
    private static final long serialVersionUID = 1466499369062886794L;

    /* JADX INFO: renamed from: a */
    public final transient int f815a;

    /* JADX INFO: renamed from: b */
    public final transient LocalDate f816b;

    /* JADX INFO: renamed from: c */
    public final transient String f817c;

    static {
        C2317x c2317x = new C2317x(-1, LocalDate.m593of(1868, 1, 1), "Meiji");
        f813d = c2317x;
        f814e = new C2317x[]{c2317x, new C2317x(0, LocalDate.m593of(1912, 7, 30), "Taisho"), new C2317x(1, LocalDate.m593of(1926, 12, 25), "Showa"), new C2317x(2, LocalDate.m593of(1989, 1, 8), "Heisei"), new C2317x(3, LocalDate.m593of(2019, 5, 1), "Reiwa")};
    }

    public C2317x(int i, LocalDate localDate, String str) {
        this.f815a = i;
        this.f816b = localDate;
        this.f817c = str;
    }

    /* JADX INFO: renamed from: r */
    public static C2317x m738r(LocalDate localDate) {
        if (localDate.m603Y(C2316w.f809d)) {
            C2351g.m796a("JapaneseDate before Meiji 6 are not supported");
            return null;
        }
        for (int length = f814e.length - 1; length >= 0; length--) {
            C2317x c2317x = f814e[length];
            if (localDate.compareTo(c2317x.f816b) >= 0) {
                return c2317x;
            }
        }
        return null;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* JADX INFO: renamed from: t */
    public static C2317x m739t(int i) {
        int i2 = i + 1;
        if (i2 >= 0) {
            C2317x[] c2317xArr = f814e;
            if (i2 < c2317xArr.length) {
                return c2317xArr[i2];
            }
        }
        C2351g.m797b("Invalid era: ", i);
        return null;
    }

    private Object writeReplace() {
        return new C2292d0((byte) 5, this);
    }

    @Override // p026j$.time.chrono.InterfaceC2305l
    public final int getValue() {
        return this.f815a;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        EnumC2365a enumC2365a = EnumC2365a.ERA;
        return interfaceC2380p == enumC2365a ? C2314u.f807c.mo711z(enumC2365a) : super.mo573m(interfaceC2380p);
    }

    /* JADX INFO: renamed from: s */
    public final C2317x m740s() {
        if (this == f814e[r0.length - 1]) {
            return null;
        }
        return m739t(this.f815a + 1);
    }

    public final String toString() {
        return this.f817c;
    }
}
