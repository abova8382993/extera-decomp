package p026j$.time.temporal;

import java.util.Map;
import p026j$.time.format.C2349y;
import p026j$.time.format.EnumC2350z;

/* JADX INFO: renamed from: j$.time.temporal.p */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public interface InterfaceC2380p {
    /* JADX INFO: renamed from: B */
    C2384t mo835B(InterfaceC2376l interfaceC2376l);

    /* JADX INFO: renamed from: G */
    default InterfaceC2376l mo842G(Map map, C2349y c2349y, EnumC2350z enumC2350z) {
        return null;
    }

    /* JADX INFO: renamed from: I */
    C2384t mo836I();

    /* JADX INFO: renamed from: P */
    long mo837P(InterfaceC2376l interfaceC2376l);

    /* JADX INFO: renamed from: V */
    Temporal mo838V(Temporal temporal, long j);

    boolean isDateBased();

    /* JADX INFO: renamed from: t */
    boolean mo841t(InterfaceC2376l interfaceC2376l);
}
