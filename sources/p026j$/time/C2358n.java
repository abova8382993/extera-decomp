package p026j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import okhttp3.internal.url._UrlKt;
import org.mvel2.MVEL;
import org.mvel2.asm.signature.SignatureVisitor;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.format.C2341q;
import p026j$.time.format.EnumC2350z;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.n */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2358n implements InterfaceC2376l, InterfaceC2377m, Comparable, Serializable {

    /* JADX INFO: renamed from: c */
    public static final /* synthetic */ int f924c = 0;
    private static final long serialVersionUID = -939150713474957432L;

    /* JADX INFO: renamed from: a */
    public final int f925a;

    /* JADX INFO: renamed from: b */
    public final int f926b;

    static {
        C2341q c2341q = new C2341q();
        c2341q.m768d("--");
        c2341q.m771g(EnumC2365a.MONTH_OF_YEAR, 2);
        c2341q.m767c(SignatureVisitor.SUPER);
        c2341q.m771g(EnumC2365a.DAY_OF_MONTH, 2);
        c2341q.m776l(Locale.getDefault(), EnumC2350z.SMART, null);
    }

    public C2358n(int i, int i2) {
        this.f925a = i;
        this.f926b = i2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 13, this);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        C2358n c2358n = (C2358n) obj;
        int i = this.f925a - c2358n.f925a;
        return i == 0 ? this.f926b - c2358n.f926b : i;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f959b ? C2311r.f803c : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public final Temporal mo569e(Temporal temporal) {
        if (!InterfaceC2304k.m719s(temporal).equals(C2311r.f803c)) {
            C2351g.m796a("Adjustment only supported on ISO date-time");
            return null;
        }
        Temporal temporalMo582a = temporal.mo582a(this.f925a, EnumC2365a.MONTH_OF_YEAR);
        EnumC2365a enumC2365a = EnumC2365a.DAY_OF_MONTH;
        return temporalMo582a.mo582a(Math.min(temporalMo582a.mo573m(enumC2365a).f968d, this.f926b), enumC2365a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C2358n) {
            C2358n c2358n = (C2358n) obj;
            if (this.f925a == c2358n.f925a && this.f926b == c2358n.f926b) {
                return true;
            }
        }
        return false;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    public final int mo570g(InterfaceC2380p interfaceC2380p) {
        return mo573m(interfaceC2380p).m849a(mo572k(interfaceC2380p), interfaceC2380p);
    }

    public final int hashCode() {
        return (this.f925a << 6) + this.f926b;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.MONTH_OF_YEAR || interfaceC2380p == EnumC2365a.DAY_OF_MONTH : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        int i;
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            return interfaceC2380p.mo837P(this);
        }
        int i2 = AbstractC2357m.f923a[((EnumC2365a) interfaceC2380p).ordinal()];
        if (i2 == 1) {
            i = this.f926b;
        } else {
            if (i2 != 2) {
                throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
            }
            i = this.f925a;
        }
        return i;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.MONTH_OF_YEAR) {
            return interfaceC2380p.mo836I();
        }
        if (interfaceC2380p != EnumC2365a.DAY_OF_MONTH) {
            return super.mo573m(interfaceC2380p);
        }
        EnumC2356l enumC2356lM822I = EnumC2356l.m822I(this.f925a);
        enumC2356lM822I.getClass();
        int i = AbstractC2355k.f920a[enumC2356lM822I.ordinal()];
        return C2384t.m848g(i != 1 ? (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31 : 28, EnumC2356l.m822I(this.f925a).m824G());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(10);
        sb.append("--");
        sb.append(this.f925a < 10 ? MVEL.VERSION_SUB : _UrlKt.FRAGMENT_ENCODE_SET);
        sb.append(this.f925a);
        sb.append(this.f926b < 10 ? "-0" : "-");
        sb.append(this.f926b);
        return sb.toString();
    }
}
