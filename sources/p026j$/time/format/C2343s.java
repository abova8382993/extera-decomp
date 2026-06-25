package p026j$.time.format;

import okhttp3.internal.url._UrlKt;
import p026j$.time.C2388x;
import p026j$.time.ZoneId;
import p026j$.time.chrono.InterfaceC2287b;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.AbstractC2381q;
import p026j$.time.temporal.C2384t;
import p026j$.time.temporal.InterfaceC2376l;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.s */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2343s implements InterfaceC2376l {

    /* JADX INFO: renamed from: a */
    public final /* synthetic */ InterfaceC2287b f887a;

    /* JADX INFO: renamed from: b */
    public final /* synthetic */ InterfaceC2376l f888b;

    /* JADX INFO: renamed from: c */
    public final /* synthetic */ InterfaceC2304k f889c;

    /* JADX INFO: renamed from: d */
    public final /* synthetic */ ZoneId f890d;

    public C2343s(InterfaceC2287b interfaceC2287b, InterfaceC2376l interfaceC2376l, InterfaceC2304k interfaceC2304k, ZoneId zoneId) {
        this.f887a = interfaceC2287b;
        this.f888b = interfaceC2376l;
        this.f889c = interfaceC2304k;
        this.f890d = zoneId;
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: d */
    public final Object mo568d(C2388x c2388x) {
        return c2388x == AbstractC2381q.f959b ? this.f889c : c2388x == AbstractC2381q.f958a ? this.f890d : c2388x == AbstractC2381q.f960c ? this.f888b.mo568d(c2388x) : c2388x.m855m(this);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: i */
    public final boolean mo571i(InterfaceC2380p interfaceC2380p) {
        InterfaceC2287b interfaceC2287b = this.f887a;
        return (interfaceC2287b == null || !interfaceC2380p.isDateBased()) ? this.f888b.mo571i(interfaceC2380p) : interfaceC2287b.mo571i(interfaceC2380p);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: k */
    public final long mo572k(InterfaceC2380p interfaceC2380p) {
        InterfaceC2287b interfaceC2287b = this.f887a;
        return (interfaceC2287b == null || !interfaceC2380p.isDateBased()) ? this.f888b.mo572k(interfaceC2380p) : interfaceC2287b.mo572k(interfaceC2380p);
    }

    @Override // p026j$.time.temporal.InterfaceC2376l
    /* JADX INFO: renamed from: m */
    public final C2384t mo573m(InterfaceC2380p interfaceC2380p) {
        InterfaceC2287b interfaceC2287b = this.f887a;
        return (interfaceC2287b == null || !interfaceC2380p.isDateBased()) ? this.f888b.mo573m(interfaceC2380p) : interfaceC2287b.mo573m(interfaceC2380p);
    }

    public final String toString() {
        String str;
        String str2 = _UrlKt.FRAGMENT_ENCODE_SET;
        InterfaceC2304k interfaceC2304k = this.f889c;
        if (interfaceC2304k != null) {
            str = " with chronology " + interfaceC2304k;
        } else {
            str = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        ZoneId zoneId = this.f890d;
        if (zoneId != null) {
            str2 = " with zone " + zoneId;
        }
        return this.f888b + str + str2;
    }
}
