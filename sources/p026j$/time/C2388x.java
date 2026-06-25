package p026j$.time;

import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2377m;
import p026j$.time.temporal.InterfaceC2382r;
import p026j$.time.temporal.Temporal;

/* JADX INFO: renamed from: j$.time.x */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class C2388x implements InterfaceC2377m {

    /* JADX INFO: renamed from: a */
    public final /* synthetic */ int f975a;

    public /* synthetic */ C2388x(int i) {
        this.f975a = i;
    }

    @Override // p026j$.time.temporal.InterfaceC2377m
    /* JADX INFO: renamed from: e */
    public Temporal mo569e(Temporal temporal) {
        EnumC2365a enumC2365a = EnumC2365a.DAY_OF_MONTH;
        return temporal.mo582a(temporal.mo573m(enumC2365a).f968d, enumC2365a);
    }

    /* JADX INFO: renamed from: m */
    public Object m855m(InterfaceC2376l interfaceC2376l) {
        int i = this.f975a;
        C2388x c2388x = AbstractC2381q.f958a;
        switch (i) {
            case 0:
                return ZonedDateTime.m657B(interfaceC2376l);
            case 1:
                ZoneId zoneId = (ZoneId) interfaceC2376l.mo568d(c2388x);
                if (zoneId == null || (zoneId instanceof ZoneOffset)) {
                    return null;
                }
                return zoneId;
            case 2:
            default:
                EnumC2365a enumC2365a = EnumC2365a.NANO_OF_DAY;
                if (interfaceC2376l.mo571i(enumC2365a)) {
                    return C2354j.m808V(interfaceC2376l.mo572k(enumC2365a));
                }
                return null;
            case 3:
                return (ZoneId) interfaceC2376l.mo568d(c2388x);
            case 4:
                return (InterfaceC2304k) interfaceC2376l.mo568d(AbstractC2381q.f959b);
            case 5:
                return (InterfaceC2382r) interfaceC2376l.mo568d(AbstractC2381q.f960c);
            case 6:
                EnumC2365a enumC2365a2 = EnumC2365a.OFFSET_SECONDS;
                if (interfaceC2376l.mo571i(enumC2365a2)) {
                    return ZoneOffset.m653Z(interfaceC2376l.mo570g(enumC2365a2));
                }
                return null;
            case 7:
                ZoneId zoneId2 = (ZoneId) interfaceC2376l.mo568d(c2388x);
                return zoneId2 != null ? zoneId2 : (ZoneId) interfaceC2376l.mo568d(AbstractC2381q.f961d);
            case 8:
                EnumC2365a enumC2365a3 = EnumC2365a.EPOCH_DAY;
                if (interfaceC2376l.mo571i(enumC2365a3)) {
                    return LocalDate.m590d0(interfaceC2376l.mo572k(enumC2365a3));
                }
                return null;
        }
    }

    public String toString() {
        switch (this.f975a) {
            case 3:
                return "ZoneId";
            case 4:
                return "Chronology";
            case 5:
                return "Precision";
            case 6:
                return "ZoneOffset";
            case 7:
                return "Zone";
            case 8:
                return "LocalDate";
            case 9:
                return "LocalTime";
            default:
                return super.toString();
        }
    }
}
