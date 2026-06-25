package p026j$.time.format;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.a */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2323a extends C2347w {

    /* JADX INFO: renamed from: d */
    public final /* synthetic */ C2346v f832d;

    public C2323a(C2346v c2346v) {
        this.f832d = c2346v;
    }

    @Override // p026j$.time.format.C2347w
    /* JADX INFO: renamed from: b */
    public final String mo744b(InterfaceC2304k interfaceC2304k, InterfaceC2380p interfaceC2380p, long j, TextStyle textStyle, Locale locale) {
        return this.f832d.m786a(j, textStyle);
    }

    @Override // p026j$.time.format.C2347w
    /* JADX INFO: renamed from: c */
    public final String mo745c(InterfaceC2380p interfaceC2380p, long j, TextStyle textStyle, Locale locale) {
        return this.f832d.m786a(j, textStyle);
    }

    @Override // p026j$.time.format.C2347w
    /* JADX INFO: renamed from: d */
    public final Iterator mo746d(InterfaceC2304k interfaceC2304k, InterfaceC2380p interfaceC2380p, TextStyle textStyle, Locale locale) {
        List list = (List) ((HashMap) this.f832d.f895b).get(textStyle);
        if (list != null) {
            return list.iterator();
        }
        return null;
    }

    @Override // p026j$.time.format.C2347w
    /* JADX INFO: renamed from: e */
    public final Iterator mo747e(InterfaceC2380p interfaceC2380p, TextStyle textStyle, Locale locale) {
        List list = (List) ((HashMap) this.f832d.f895b).get(textStyle);
        if (list != null) {
            return list.iterator();
        }
        return null;
    }
}
