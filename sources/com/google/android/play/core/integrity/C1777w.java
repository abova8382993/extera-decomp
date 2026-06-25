package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.Task;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.w */
/* JADX INFO: loaded from: classes5.dex */
final class C1777w implements IntegrityManager {

    /* JADX INFO: renamed from: a */
    private final C1725ad f588a;

    public C1777w(C1725ad c1725ad) {
        this.f588a = c1725ad;
    }

    @Override // com.google.android.play.core.integrity.IntegrityManager
    public final Task<IntegrityTokenResponse> requestIntegrityToken(IntegrityTokenRequest integrityTokenRequest) {
        return this.f588a.m411b(integrityTokenRequest);
    }
}
