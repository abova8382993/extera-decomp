package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import com.google.android.play.core.integrity.StandardIntegrityManager;
import com.google.android.play.integrity.internal.C1809q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.bb */
/* JADX INFO: loaded from: classes5.dex */
final class C1750bb extends StandardIntegrityManager.StandardIntegrityToken {

    /* JADX INFO: renamed from: a */
    private final String f552a;

    /* JADX INFO: renamed from: b */
    private final C1775u f553b;

    public C1750bb(String str, C1809q c1809q, PendingIntent pendingIntent) {
        this.f552a = str;
        this.f553b = new C1775u(c1809q, pendingIntent);
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityToken
    public final String token() {
        return this.f552a;
    }
}
