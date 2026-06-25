package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1809q;
import okio.Segment$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.a */
/* JADX INFO: loaded from: classes5.dex */
final class C1721a extends AbstractC1728ag {

    /* JADX INFO: renamed from: a */
    private String f498a;

    /* JADX INFO: renamed from: b */
    private C1809q f499b;

    /* JADX INFO: renamed from: c */
    private PendingIntent f500c;

    @Override // com.google.android.play.core.integrity.AbstractC1728ag
    /* JADX INFO: renamed from: a */
    public final AbstractC1728ag mo401a(PendingIntent pendingIntent) {
        this.f500c = pendingIntent;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1728ag
    /* JADX INFO: renamed from: b */
    public final AbstractC1728ag mo402b(C1809q c1809q) {
        if (c1809q != null) {
            this.f499b = c1809q;
            return this;
        }
        g$$ExternalSyntheticBUOutline2.m208m("Null logger");
        return null;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1728ag
    /* JADX INFO: renamed from: c */
    public final AbstractC1728ag mo403c(String str) {
        this.f498a = str;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1728ag
    /* JADX INFO: renamed from: d */
    public final C1729ah mo404d() {
        C1809q c1809q;
        String str = this.f498a;
        if (str != null && (c1809q = this.f499b) != null) {
            return new C1729ah(str, c1809q, this.f500c);
        }
        StringBuilder sb = new StringBuilder();
        if (this.f498a == null) {
            sb.append(" token");
        }
        if (this.f499b == null) {
            sb.append(" logger");
        }
        Segment$$ExternalSyntheticBUOutline1.m992m("Missing required properties:".concat(sb.toString()));
        return null;
    }
}
