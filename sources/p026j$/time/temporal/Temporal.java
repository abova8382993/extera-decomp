package p026j$.time.temporal;

import kotlin.jvm.internal.LongCompanionObject;
import p026j$.time.LocalDate;

/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public interface Temporal extends InterfaceC2376l {
    /* JADX INFO: renamed from: a */
    Temporal mo582a(long j, InterfaceC2380p interfaceC2380p);

    /* JADX INFO: renamed from: b */
    Temporal mo583b(long j, InterfaceC2382r interfaceC2382r);

    /* JADX INFO: renamed from: c */
    default Temporal mo584c(long j, InterfaceC2382r interfaceC2382r) {
        long j2;
        if (j == Long.MIN_VALUE) {
            this = mo583b(LongCompanionObject.MAX_VALUE, interfaceC2382r);
            j2 = 1;
        } else {
            j2 = -j;
        }
        return this.mo583b(j2, interfaceC2382r);
    }

    /* JADX INFO: renamed from: h */
    default Temporal mo666j(LocalDate localDate) {
        return localDate.mo569e(this);
    }

    /* JADX INFO: renamed from: o */
    long mo586o(Temporal temporal, InterfaceC2382r interfaceC2382r);
}
