package p026j$.time.chrono;

import p026j$.time.AbstractC2320d;
import p026j$.time.C2388x;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.chrono.l */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public interface InterfaceC2305l extends InterfaceC2376l, InterfaceC2377m {
    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    default Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f960c ? ChronoUnit.ERAS : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    default Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(getValue(), EnumC2365a.ERA);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: g */
    default int mo570g(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p == EnumC2365a.ERA ? getValue() : super.mo570g(interfaceC2380p);
    }

    int getValue();

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    default boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? interfaceC2380p == EnumC2365a.ERA : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    default long mo572k(InterfaceC2380p interfaceC2380p) {
        if (interfaceC2380p == EnumC2365a.ERA) {
            return getValue();
        }
        if (interfaceC2380p instanceof EnumC2365a) {
            throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
        }
        return interfaceC2380p.mo837P(this);
    }
}
