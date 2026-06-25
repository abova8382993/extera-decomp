package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.l */
/* JADX INFO: loaded from: classes5.dex */
public final class C1804l extends AbstractC1781a implements InterfaceC1806n {
    public C1804l(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.integrity.protocol.IIntegrityService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1806n
    /* JADX INFO: renamed from: c */
    public final void mo486c(Bundle bundle, InterfaceC1808p interfaceC1808p) {
        Parcel parcelM442a = m442a();
        AbstractC1795c.m477c(parcelM442a, bundle);
        parcelM442a.writeStrongBinder(interfaceC1808p);
        m443b(2, parcelM442a);
    }
}
