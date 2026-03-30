package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.core.integrity.StandardIntegrityManager;
import com.google.android.play.integrity.internal.C1603q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.bb */
/* JADX INFO: loaded from: classes4.dex */
final class C1544bb extends StandardIntegrityManager.StandardIntegrityToken {

    /* JADX INFO: renamed from: a */
    private final String f455a;

    /* JADX INFO: renamed from: b */
    private final C1569u f456b;

    C1544bb(String str, C1603q c1603q, PendingIntent pendingIntent) {
        this.f455a = str;
        this.f456b = new C1569u(c1603q, pendingIntent);
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityToken
    public final String token() {
        return this.f455a;
    }
}
