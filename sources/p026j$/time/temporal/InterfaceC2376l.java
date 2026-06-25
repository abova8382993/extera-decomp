package p026j$.time.temporal;

import java.util.Objects;
import p026j$.time.AbstractC2320d;
import p026j$.time.C2284c;
import p026j$.time.C2388x;

/* JADX INFO: renamed from: j$.time.temporal.l */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public interface InterfaceC2376l {
    /* JADX INFO: renamed from: d */
    default Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f958a || c2388x == AbstractC2381q.f959b || c2388x == AbstractC2381q.f960c) {
            return null;
        }
        return c2388x.m855m(this);
    }

    /* JADX INFO: renamed from: g */
    default int mo570g(InterfaceC2380p interfaceC2380p) {
        C2384t c2384tMo573m = mo573m(interfaceC2380p);
        if (!c2384tMo573m.m852d()) {
            throw new C2383s("Invalid field " + interfaceC2380p + " for get() method, use getLong() instead");
        }
        long jMo572k = mo572k(interfaceC2380p);
        if (c2384tMo573m.m853e(jMo572k)) {
            return (int) jMo572k;
        }
        throw new C2284c("Invalid value for " + interfaceC2380p + " (valid values " + c2384tMo573m + "): " + jMo572k);
    }

    /* JADX INFO: renamed from: i */
    boolean mo571i(InterfaceC2380p interfaceC2380p);

    /* JADX INFO: renamed from: k */
    long mo572k(InterfaceC2380p interfaceC2380p);

    /* JADX INFO: renamed from: m */
    default C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        if (!(interfaceC2380p instanceof EnumC2365a)) {
            Objects.requireNonNull(interfaceC2380p, "field");
            return interfaceC2380p.mo835B(this);
        }
        if (mo571i(interfaceC2380p)) {
            return ((EnumC2365a) interfaceC2380p).f942b;
        }
        throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
    }
}
