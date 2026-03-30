package com.google.android.play.integrity.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.ab */
/* JADX INFO: loaded from: classes4.dex */
final class ServiceConnectionC1577ab implements ServiceConnection {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C1578ac f496a;

    /* synthetic */ ServiceConnectionC1577ab(C1578ac c1578ac, AbstractC1576aa abstractC1576aa) {
        this.f496a = c1578ac;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f496a.f499c.m430c("ServiceConnectionImpl.onServiceConnected(%s)", componentName);
        this.f496a.m402c().post(new C1611y(this, iBinder));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.f496a.f499c.m430c("ServiceConnectionImpl.onServiceDisconnected(%s)", componentName);
        this.f496a.m402c().post(new C1612z(this));
    }
}
