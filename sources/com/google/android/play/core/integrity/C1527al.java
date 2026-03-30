package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.play.core.integrity.StandardIntegrityManager;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.al */
/* JADX INFO: loaded from: classes4.dex */
final class C1527al implements StandardIntegrityManager {

    /* JADX INFO: renamed from: a */
    private final C1539ax f424a;

    /* JADX INFO: renamed from: b */
    private final C1546bd f425b;

    C1527al(C1539ax c1539ax, C1546bd c1546bd) {
        this.f424a = c1539ax;
        this.f425b = c1546bd;
    }

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Task m354a(StandardIntegrityManager.PrepareIntegrityTokenRequest prepareIntegrityTokenRequest, Long l) {
        final C1546bd c1546bd = this.f425b;
        final long jMo338a = prepareIntegrityTokenRequest.mo338a();
        final long jLongValue = l.longValue();
        return Tasks.forResult(new StandardIntegrityManager.StandardIntegrityTokenProvider() { // from class: com.google.android.play.core.integrity.bc
            @Override // com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityTokenProvider
            public final Task request(StandardIntegrityManager.StandardIntegrityTokenRequest standardIntegrityTokenRequest) {
                return c1546bd.m371a(jMo338a, jLongValue, standardIntegrityTokenRequest);
            }
        });
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager
    public final Task<StandardIntegrityManager.StandardIntegrityTokenProvider> prepareIntegrityToken(final StandardIntegrityManager.PrepareIntegrityTokenRequest prepareIntegrityTokenRequest) {
        return this.f424a.m366d(prepareIntegrityTokenRequest.mo338a()).onSuccessTask(new SuccessContinuation() { // from class: com.google.android.play.core.integrity.ak
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return this.f422a.m354a(prepareIntegrityTokenRequest, (Long) obj);
            }
        });
    }
}
