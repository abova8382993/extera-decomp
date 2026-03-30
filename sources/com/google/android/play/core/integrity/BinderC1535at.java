package com.google.android.play.core.integrity;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1596j;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.at */
/* JADX INFO: loaded from: classes4.dex */
class BinderC1535at extends AbstractBinderC1596j {

    /* JADX INFO: renamed from: a */
    final TaskCompletionSource f440a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C1539ax f441b;

    BinderC1535at(C1539ax c1539ax, TaskCompletionSource taskCompletionSource) {
        this.f441b = c1539ax;
        this.f440a = taskCompletionSource;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1597k
    /* JADX INFO: renamed from: b */
    public final void mo356b(Bundle bundle) {
        this.f441b.f445a.m406v(this.f440a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1597k
    /* JADX INFO: renamed from: c */
    public void mo357c(Bundle bundle) {
        this.f441b.f445a.m406v(this.f440a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1597k
    /* JADX INFO: renamed from: d */
    public final void mo358d(Bundle bundle) {
        this.f441b.f445a.m406v(this.f440a);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1597k
    /* JADX INFO: renamed from: e */
    public void mo359e(Bundle bundle) {
        this.f441b.f445a.m406v(this.f440a);
    }
}
