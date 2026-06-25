package p026j$.time;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Objects;
import p026j$.time.zone.C2395f;
import p026j$.time.zone.C2397h;
import p026j$.time.zone.ZoneRules;

/* JADX INFO: renamed from: j$.time.w */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2387w extends ZoneId {

    /* JADX INFO: renamed from: d */
    public static final /* synthetic */ int f972d = 0;
    private static final long serialVersionUID = 8386373296231747096L;

    /* JADX INFO: renamed from: b */
    public final String f973b;

    /* JADX INFO: renamed from: c */
    public final transient ZoneRules f974c;

    public C2387w(String str, ZoneRules zoneRules) {
        this.f973b = str;
        this.f974c = zoneRules;
    }

    /* JADX INFO: renamed from: V */
    public static C2387w m854V(String str, boolean z) {
        Objects.requireNonNull(str, "zoneId");
        int length = str.length();
        ZoneRules zoneRulesM871a = null;
        if (length < 2) {
            C2351g.m796a("Invalid ID for region-based ZoneId, invalid format: ".concat(str));
            return null;
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if ((cCharAt < 'a' || cCharAt > 'z') && ((cCharAt < 'A' || cCharAt > 'Z') && ((cCharAt != '/' || i == 0) && ((cCharAt < '0' || cCharAt > '9' || i == 0) && ((cCharAt != '~' || i == 0) && ((cCharAt != '.' || i == 0) && ((cCharAt != '_' || i == 0) && ((cCharAt != '+' || i == 0) && (cCharAt != '-' || i == 0))))))))) {
                C2351g.m796a("Invalid ID for region-based ZoneId, invalid format: ".concat(str));
                return null;
            }
        }
        try {
            zoneRulesM871a = C2397h.m871a(str);
        } catch (C2395f e) {
            if (z) {
                throw e;
            }
        }
        return new C2387w(str, zoneRulesM871a);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C2362r((byte) 7, this);
    }

    @Override // p026j$.time.ZoneId
    /* JADX INFO: renamed from: P */
    public final void mo649P(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(7);
        dataOutput.writeUTF(this.f973b);
    }

    @Override // p026j$.time.ZoneId
    public final String getId() {
        return this.f973b;
    }

    @Override // p026j$.time.ZoneId
    public final ZoneRules getRules() {
        ZoneRules zoneRules = this.f974c;
        return zoneRules != null ? zoneRules : C2397h.m871a(this.f973b);
    }
}
