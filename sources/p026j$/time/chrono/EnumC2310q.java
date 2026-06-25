package p026j$.time.chrono;

import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: j$.time.chrono.q */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumC2310q implements InterfaceC2305l {

    /* JADX INFO: renamed from: AH */
    public static final EnumC2310q f801AH;

    /* JADX INFO: renamed from: a */
    public static final /* synthetic */ EnumC2310q[] f802a;

    static {
        EnumC2310q enumC2310q = new EnumC2310q("AH", 0);
        f801AH = enumC2310q;
        f802a = new EnumC2310q[]{enumC2310q};
    }

    public static EnumC2310q valueOf(String str) {
        return (EnumC2310q) Enum.valueOf(EnumC2310q.class, str);
    }

    public static EnumC2310q[] values() {
        return (EnumC2310q[]) f802a.clone();
    }

    @Override // p026j$.time.chrono.InterfaceC2305l
    public final int getValue() {
        return 1;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        return interfaceC2380p == EnumC2365a.ERA ? C2384t.m847f(1L, 1L) : super.mo573m(interfaceC2380p);
    }
}
