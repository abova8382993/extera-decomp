package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.integrity.internal.C1603q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ah */
/* JADX INFO: loaded from: classes4.dex */
final class C1523ah extends IntegrityTokenResponse {

    /* JADX INFO: renamed from: a */
    private final String f419a;

    /* JADX INFO: renamed from: b */
    private final C1569u f420b;

    C1523ah(String str, C1603q c1603q, PendingIntent pendingIntent) {
        this.f419a = str;
        this.f420b = new C1569u(c1603q, pendingIntent);
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenResponse
    public final String token() {
        return this.f419a;
    }
}
