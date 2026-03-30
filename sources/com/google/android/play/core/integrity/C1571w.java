package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.Task;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.w */
/* JADX INFO: loaded from: classes4.dex */
final class C1571w implements IntegrityManager {

    /* JADX INFO: renamed from: a */
    private final C1519ad f491a;

    C1571w(C1519ad c1519ad) {
        this.f491a = c1519ad;
    }

    @Override // com.google.android.play.core.integrity.IntegrityManager
    public final Task<IntegrityTokenResponse> requestIntegrityToken(IntegrityTokenRequest integrityTokenRequest) {
        return this.f491a.m350b(integrityTokenRequest);
    }
}
