package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.StandardIntegrityManager;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.bd */
/* JADX INFO: loaded from: classes5.dex */
final class C1752bd {

    /* JADX INFO: renamed from: a */
    private final C1745ax f557a;

    public C1752bd(C1745ax c1745ax) {
        this.f557a = c1745ax;
    }

    /* JADX INFO: renamed from: a */
    public final /* synthetic */ Task m432a(long j, long j2, StandardIntegrityManager.StandardIntegrityTokenRequest standardIntegrityTokenRequest) {
        return this.f557a.m426c(standardIntegrityTokenRequest.mo400a(), j, j2);
    }
}
