package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1603q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.b */
/* JADX INFO: loaded from: classes4.dex */
final class C1542b extends AbstractC1543ba {

    /* JADX INFO: renamed from: a */
    private String f452a;

    /* JADX INFO: renamed from: b */
    private C1603q f453b;

    /* JADX INFO: renamed from: c */
    private PendingIntent f454c;

    C1542b() {
    }

    @Override // com.google.android.play.core.integrity.AbstractC1543ba
    /* JADX INFO: renamed from: a */
    final AbstractC1543ba mo367a(PendingIntent pendingIntent) {
        this.f454c = pendingIntent;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1543ba
    /* JADX INFO: renamed from: b */
    final AbstractC1543ba mo368b(C1603q c1603q) {
        if (c1603q == null) {
            throw new NullPointerException("Null logger");
        }
        this.f453b = c1603q;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1543ba
    /* JADX INFO: renamed from: c */
    final AbstractC1543ba mo369c(String str) {
        if (str == null) {
            throw new NullPointerException("Null token");
        }
        this.f452a = str;
        return this;
    }

    @Override // com.google.android.play.core.integrity.AbstractC1543ba
    /* JADX INFO: renamed from: d */
    final C1544bb mo370d() {
        C1603q c1603q;
        String str = this.f452a;
        if (str != null && (c1603q = this.f453b) != null) {
            return new C1544bb(str, c1603q, this.f454c);
        }
        StringBuilder sb = new StringBuilder();
        if (this.f452a == null) {
            sb.append(" token");
        }
        if (this.f453b == null) {
            sb.append(" logger");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
