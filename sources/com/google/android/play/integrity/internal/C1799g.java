package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.g */
/* JADX INFO: loaded from: classes5.dex */
public final class C1799g extends AbstractC1781a implements InterfaceC1801i {
    public C1799g(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.integrity.protocol.IExpressIntegrityService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1801i
    /* JADX INFO: renamed from: c */
    public final void mo483c(Bundle bundle, InterfaceC1803k interfaceC1803k) {
        Parcel parcelM442a = m442a();
        AbstractC1795c.m477c(parcelM442a, bundle);
        parcelM442a.writeStrongBinder(interfaceC1803k);
        m443b(3, parcelM442a);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1801i
    /* JADX INFO: renamed from: d */
    public final void mo484d(Bundle bundle, InterfaceC1803k interfaceC1803k) {
        Parcel parcelM442a = m442a();
        AbstractC1795c.m477c(parcelM442a, bundle);
        parcelM442a.writeStrongBinder(interfaceC1803k);
        m443b(2, parcelM442a);
    }
}
