package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.StandardIntegrityManager;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.bd */
/* JADX INFO: loaded from: classes4.dex */
final class C1546bd {

    /* JADX INFO: renamed from: a */
    private final C1539ax f460a;

    C1546bd(C1539ax c1539ax) {
        this.f460a = c1539ax;
    }

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Task m371a(long j, long j2, StandardIntegrityManager.StandardIntegrityTokenRequest standardIntegrityTokenRequest) {
        return this.f460a.m365c(standardIntegrityTokenRequest.mo339a(), j, j2);
    }
}
