package p026j$.time.chrono;

import p026j$.time.C2354j;
import p026j$.time.C2388x;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.ChronoUnit;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2379o;
import p026j$.time.temporal.InterfaceC2380p;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.chrono.b */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public interface InterfaceC2287b extends Temporal, InterfaceC2377m, Comparable {
    /* JADX INFO: renamed from: J */
    default long mo595J() {
        return mo572k(EnumC2365a.EPOCH_DAY);
    }

    /* JADX INFO: renamed from: K */
    default ChronoLocalDateTime mo596K(C2354j c2354j) {
        return new C2295f(this, c2354j);
    }

    /* JADX INFO: renamed from: N */
    default InterfaceC2305l mo597N() {
        return mo607f().mo702C(mo570g(EnumC2365a.ERA));
    }

    /* JADX INFO: renamed from: R */
    InterfaceC2287b mo599R(InterfaceC2379o interfaceC2379o);

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: U */
    default int compareTo(InterfaceC2287b interfaceC2287b) {
        int iCompare = Long.compare(mo595J(), interfaceC2287b.mo595J());
        if (iCompare != 0) {
            return iCompare;
        }
        return ((AbstractC2285a) mo607f()).getId().compareTo(interfaceC2287b.mo607f().getId());
    }

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: a */
    InterfaceC2287b mo582a(long j, InterfaceC2380p interfaceC2380p);

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: b */
    InterfaceC2287b mo583b(long j, InterfaceC2382r interfaceC2382r);

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: c */
    default InterfaceC2287b mo584c(long j, InterfaceC2382r interfaceC2382r) {
        return AbstractC2291d.m693t(mo607f(), super.mo584c(j, interfaceC2382r));
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    default Object mo568d(C2388x c2388x) {
        if (c2388x == AbstractC2381q.f958a || c2388x == AbstractC2381q.f962e || c2388x == AbstractC2381q.f961d || c2388x == AbstractC2381q.f964g) {
            return null;
        }
        return c2388x == AbstractC2381q.f959b ? mo607f() : c2388x == AbstractC2381q.f960c ? ChronoUnit.DAYS : c2388x.m855m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    default Temporal mo569e(Temporal temporal) {
        return temporal.mo582a(mo595J(), EnumC2365a.EPOCH_DAY);
    }

    boolean equals(Object obj);

    /* JADX INFO: renamed from: f */
    InterfaceC2304k mo607f();

    int hashCode();

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    default boolean mo571i(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p instanceof EnumC2365a ? ((EnumC2365a) interfaceC2380p).isDateBased() : interfaceC2380p != null && interfaceC2380p.mo841t(this);
    }

    /* JADX INFO: renamed from: j */
    InterfaceC2287b mo666j(InterfaceC2377m interfaceC2377m);

    @Override // p026j$.time.temporal.Temporal
    /* JADX INFO: renamed from: o */
    long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r);

    String toString();
}
