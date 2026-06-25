package com.google.android.play.core.integrity;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1802j;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.at */
/* JADX INFO: loaded from: classes5.dex */
class BinderC1741at extends AbstractBinderC1802j {

    /* JADX INFO: renamed from: a */
    final TaskCompletionSource f537a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C1745ax f538b;

    public BinderC1741at(C1745ax c1745ax, TaskCompletionSource taskCompletionSource) {
        this.f538b = c1745ax;
        this.f537a = taskCompletionSource;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1803k
    /* JADX INFO: renamed from: b */
    public final void mo417b(Bundle bundle) {
        this.f538b.f542a.m467v(this.f537a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1803k
    /* JADX INFO: renamed from: c */
    public void mo418c(Bundle bundle) {
        this.f538b.f542a.m467v(this.f537a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1803k
    /* JADX INFO: renamed from: d */
    public final void mo419d(Bundle bundle) {
        this.f538b.f542a.m467v(this.f537a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1803k
    /* JADX INFO: renamed from: e */
    public void mo420e(Bundle bundle) {
        this.f538b.f542a.m467v(this.f537a);
    }
}
