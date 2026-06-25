package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1809q;
import okio.Segment$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.b */
/* JADX INFO: loaded from: classes5.dex */
final class C1748b extends AbstractC1749ba {

    /* JADX INFO: renamed from: a */
    private String f549a;

    /* JADX INFO: renamed from: b */
    private C1809q f550b;

    /* JADX INFO: renamed from: c */
    private PendingIntent f551c;

    @Override // com.google.android.play.core.integrity.AbstractC1749ba
    /* JADX INFO: renamed from: a */
    public final AbstractC1749ba mo428a(PendingIntent pendingIntent) {
        this.f551c = pendingIntent;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1749ba
    /* JADX INFO: renamed from: b */
    public final AbstractC1749ba mo429b(C1809q c1809q) {
        if (c1809q != null) {
            this.f550b = c1809q;
            return this;
        }
        g$$ExternalSyntheticBUOutline2.m208m("Null logger");
        return null;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1749ba
    /* JADX INFO: renamed from: c */
    public final AbstractC1749ba mo430c(String str) {
        if (str != null) {
            this.f549a = str;
            return this;
        }
        g$$ExternalSyntheticBUOutline2.m208m("Null token");
        return null;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1749ba
    /* JADX INFO: renamed from: d */
    public final C1750bb mo431d() {
        C1809q c1809q;
        String str = this.f549a;
        if (str != null && (c1809q = this.f550b) != null) {
            return new C1750bb(str, c1809q, this.f551c);
        }
        StringBuilder sb = new StringBuilder();
        if (this.f549a == null) {
            sb.append(" token");
        }
        if (this.f550b == null) {
            sb.append(" logger");
        }
        Segment$$ExternalSyntheticBUOutline1.m992m("Missing required properties:".concat(sb.toString()));
        return null;
    }
}
