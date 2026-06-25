package com.google.android.play.integrity.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.ab */
/* JADX INFO: loaded from: classes5.dex */
final class ServiceConnectionC1783ab implements ServiceConnection {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C1784ac f593a;

    public /* synthetic */ ServiceConnectionC1783ab(C1784ac c1784ac, AbstractC1782aa abstractC1782aa) {
        this.f593a = c1784ac;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f593a.f596c.m491c("ServiceConnectionImpl.onServiceConnected(%s)", componentName);
        C1784ac c1784ac = this.f593a;
        c1784ac.m463c().post(new C1817y(this, iBinder));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.f593a.f596c.m491c("ServiceConnectionImpl.onServiceDisconnected(%s)", componentName);
        C1784ac c1784ac = this.f593a;
        c1784ac.m463c().post(new C1818z(this));
    }
}
