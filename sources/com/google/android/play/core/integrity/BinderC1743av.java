package com.google.android.play.core.integrity;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.C1809q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.av */
/* JADX INFO: loaded from: classes5.dex */
final class BinderC1743av extends BinderC1741at {

    /* JADX INFO: renamed from: c */
    private final C1809q f540c;

    public BinderC1743av(C1745ax c1745ax, TaskCompletionSource taskCompletionSource) {
        super(c1745ax, taskCompletionSource);
        this.f540c = new C1809q("OnWarmUpIntegrityTokenCallback");
    }

    @Override // com.google.android.play.core.integrity.BinderC1741at, com.google.android.play.integrity.internal.InterfaceC1803k
    /* JADX INFO: renamed from: e */
    public final void mo420e(Bundle bundle) {
        super.mo420e(bundle);
        this.f540c.m491c("onWarmUpExpressIntegrityToken", new Object[0]);
        int i = bundle.getInt("error");
        TaskCompletionSource taskCompletionSource = this.f537a;
        if (i != 0) {
            taskCompletionSource.trySetException(new StandardIntegrityException(i, null));
        } else {
            taskCompletionSource.trySetResult(Long.valueOf(bundle.getLong("warm.up.sid")));
        }
    }
}
