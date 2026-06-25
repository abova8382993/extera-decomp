package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.play.core.integrity.StandardIntegrityManager;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.al */
/* JADX INFO: loaded from: classes5.dex */
final class C1733al implements StandardIntegrityManager {

    /* JADX INFO: renamed from: a */
    private final C1745ax f521a;

    /* JADX INFO: renamed from: b */
    private final C1752bd f522b;

    public C1733al(C1745ax c1745ax, C1752bd c1752bd) {
        this.f521a = c1745ax;
        this.f522b = c1752bd;
    }

    /* JADX INFO: renamed from: a */
    public final /* synthetic */ Task m415a(StandardIntegrityManager.PrepareIntegrityTokenRequest prepareIntegrityTokenRequest, Long l) {
        final C1752bd c1752bd = this.f522b;
        final long jMo399a = prepareIntegrityTokenRequest.mo399a();
        final long jLongValue = l.longValue();
        return Tasks.forResult(new StandardIntegrityManager.StandardIntegrityTokenProvider() { // from class: com.google.android.play.core.integrity.bc
            @Override // com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityTokenProvider
            public final Task request(StandardIntegrityManager.StandardIntegrityTokenRequest standardIntegrityTokenRequest) {
                return c1752bd.m432a(jMo399a, jLongValue, standardIntegrityTokenRequest);
            }
        });
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager
    public final Task<StandardIntegrityManager.StandardIntegrityTokenProvider> prepareIntegrityToken(final StandardIntegrityManager.PrepareIntegrityTokenRequest prepareIntegrityTokenRequest) {
        return this.f521a.m427d(prepareIntegrityTokenRequest.mo399a()).onSuccessTask(new SuccessContinuation() { // from class: com.google.android.play.core.integrity.ak
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return this.f519a.m415a(prepareIntegrityTokenRequest, (Long) obj);
            }
        });
    }
}
