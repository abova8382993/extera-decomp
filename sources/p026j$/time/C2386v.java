package p026j$.time;

import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2383s;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.v */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2386v implements InterfaceC2376l {

    /* JADX INFO: renamed from: a */
    public final /* synthetic */ ZoneId f971a;

    public C2386v(ZoneId zoneId) {
        this.f971a = zoneId;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f958a ? this.f971a : super.mo568d(c2388x);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return false;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        throw new C2383s(AbstractC2320d.m741a("Unsupported field: ", interfaceC2380p));
    }
}
