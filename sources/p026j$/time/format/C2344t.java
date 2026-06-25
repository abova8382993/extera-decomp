package p026j$.time.format;

import java.util.Objects;
import p026j$.time.C2284c;
import p026j$.time.C2388x;
import p026j$.time.ZoneId;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2287b;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.t */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2344t {

    /* JADX INFO: renamed from: a */
    public final InterfaceC2376l f891a;

    /* JADX INFO: renamed from: b */
    public final DateTimeFormatter f892b;

    /* JADX INFO: renamed from: c */
    public int f893c;

    public C2344t(InterfaceC2376l interfaceC2376l, DateTimeFormatter dateTimeFormatter) {
        InterfaceC2304k interfaceC2304k = dateTimeFormatter.f829e;
        if (interfaceC2304k != null) {
            InterfaceC2304k interfaceC2304k2 = (InterfaceC2304k) interfaceC2376l.mo568d(AbstractC2381q.f959b);
            ZoneId zoneId = (ZoneId) interfaceC2376l.mo568d(AbstractC2381q.f958a);
            InterfaceC2287b interfaceC2287bMo704H = null;
            interfaceC2304k = Objects.equals(interfaceC2304k, interfaceC2304k2) ? null : interfaceC2304k;
            if (interfaceC2304k != null) {
                InterfaceC2304k interfaceC2304k3 = interfaceC2304k != null ? interfaceC2304k : interfaceC2304k2;
                if (interfaceC2304k != null) {
                    if (interfaceC2376l.mo571i(EnumC2365a.EPOCH_DAY)) {
                        interfaceC2287bMo704H = interfaceC2304k3.mo704H(interfaceC2376l);
                    } else if (interfaceC2304k != C2311r.f803c || interfaceC2304k2 != null) {
                        for (EnumC2365a enumC2365a : EnumC2365a.values()) {
                            if (enumC2365a.isDateBased() && interfaceC2376l.mo571i(enumC2365a)) {
                                throw new C2284c("Unable to apply override chronology '" + interfaceC2304k + "' because the temporal object being formatted contains date fields but does not represent a whole date: " + interfaceC2376l);
                            }
                        }
                    }
                }
                interfaceC2376l = new C2343s(interfaceC2287bMo704H, interfaceC2376l, interfaceC2304k3, zoneId);
            }
        }
        this.f891a = interfaceC2376l;
        this.f892b = dateTimeFormatter;
    }

    /* JADX INFO: renamed from: a */
    public final Long m784a(InterfaceC2380p interfaceC2380p) {
        int i = this.f893c;
        InterfaceC2376l interfaceC2376l = this.f891a;
        if (i <= 0 || interfaceC2376l.mo571i(interfaceC2380p)) {
            return Long.valueOf(interfaceC2376l.mo572k(interfaceC2380p));
        }
        return null;
    }

    /* JADX INFO: renamed from: b */
    public final Object m785b(C2388x c2388x) {
        InterfaceC2376l interfaceC2376l = this.f891a;
        Object objMo568d = interfaceC2376l.mo568d(c2388x);
        if (objMo568d != null || this.f893c != 0) {
            return objMo568d;
        }
        throw new C2284c("Unable to extract " + c2388x + " from temporal " + interfaceC2376l);
    }

    public final String toString() {
        return this.f891a.toString();
    }
}
