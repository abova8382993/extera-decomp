package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1809q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ah */
/* JADX INFO: loaded from: classes5.dex */
final class C1729ah extends IntegrityTokenResponse {

    /* JADX INFO: renamed from: a */
    private final String f516a;

    /* JADX INFO: renamed from: b */
    private final C1775u f517b;

    public C1729ah(String str, C1809q c1809q, PendingIntent pendingIntent) {
        this.f516a = str;
        this.f517b = new C1775u(c1809q, pendingIntent);
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenResponse
    public final String token() {
        return this.f516a;
    }
}
