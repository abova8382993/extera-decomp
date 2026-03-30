package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1603q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.a */
/* JADX INFO: loaded from: classes4.dex */
final class C1515a extends AbstractC1522ag {

    /* JADX INFO: renamed from: a */
    private String f401a;

    /* JADX INFO: renamed from: b */
    private C1603q f402b;

    /* JADX INFO: renamed from: c */
    private PendingIntent f403c;

    C1515a() {
    }

    @Override // com.google.android.play.core.integrity.AbstractC1522ag
    /* JADX INFO: renamed from: a */
    final AbstractC1522ag mo340a(PendingIntent pendingIntent) {
        this.f403c = pendingIntent;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1522ag
    /* JADX INFO: renamed from: b */
    final AbstractC1522ag mo341b(C1603q c1603q) {
        if (c1603q == null) {
            throw new NullPointerException("Null logger");
        }
        this.f402b = c1603q;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1522ag
    /* JADX INFO: renamed from: c */
    final AbstractC1522ag mo342c(String str) {
        this.f401a = str;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1522ag
    /* JADX INFO: renamed from: d */
    final C1523ah mo343d() {
        C1603q c1603q;
        String str = this.f401a;
        if (str != null && (c1603q = this.f402b) != null) {
            return new C1523ah(str, c1603q, this.f403c);
        }
        StringBuilder sb = new StringBuilder();
        if (this.f401a == null) {
            sb.append(" token");
        }
        if (this.f402b == null) {
            sb.append(" logger");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
