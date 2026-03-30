package com.google.android.play.core.integrity;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.C1603q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.av */
/* JADX INFO: loaded from: classes4.dex */
final class BinderC1537av extends BinderC1535at {

    /* JADX INFO: renamed from: c */
    private final C1603q f443c;

    BinderC1537av(C1539ax c1539ax, TaskCompletionSource taskCompletionSource) {
        super(c1539ax, taskCompletionSource);
        this.f443c = new C1603q("OnWarmUpIntegrityTokenCallback");
    }

    @Override // com.google.android.play.core.integrity.BinderC1535at, com.google.android.play.integrity.internal.InterfaceC1597k
    /* JADX INFO: renamed from: e */
    public final void mo359e(Bundle bundle) {
        super.mo359e(bundle);
        this.f443c.m430c("onWarmUpExpressIntegrityToken", new Object[0]);
        int i = bundle.getInt("error");
        if (i != 0) {
            this.f440a.trySetException(new StandardIntegrityException(i, null));
        } else {
            this.f440a.trySetResult(Long.valueOf(bundle.getLong("warm.up.sid")));
        }
    }
}
