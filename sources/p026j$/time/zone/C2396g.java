package p026j$.time.zone;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: j$.time.zone.g */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2396g implements PrivilegedAction {

    /* JADX INFO: renamed from: a */
    public final /* synthetic */ List f1007a;

    public C2396g(List list) {
        this.f1007a = list;
    }

    @Override // java.security.PrivilegedAction
    public final Object run() {
        String property = System.getProperty("java.time.zone.DefaultZoneRulesProvider");
        if (property == null) {
            C2397h.m872b(new C2397h());
            return null;
        }
        try {
            C2397h c2397h = (C2397h) C2397h.class.cast(Class.forName(property, true, C2397h.class.getClassLoader()).newInstance());
            C2397h.m872b(c2397h);
            ((ArrayList) this.f1007a).add(c2397h);
            return null;
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
