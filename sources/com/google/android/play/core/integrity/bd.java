package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.StandardIntegrityManager;

/* JADX INFO: loaded from: classes4.dex */
final class bd {
    private final ax a;

    bd(ax axVar) {
        this.a = axVar;
    }

    final /* synthetic */ Task a(long j, long j2, StandardIntegrityManager.StandardIntegrityTokenRequest standardIntegrityTokenRequest) {
        return this.a.c(standardIntegrityTokenRequest.a(), j, j2);
    }
}
