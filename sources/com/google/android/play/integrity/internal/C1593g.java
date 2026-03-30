package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.g */
/* JADX INFO: loaded from: classes4.dex */
public final class C1593g extends AbstractC1575a implements InterfaceC1595i {
    C1593g(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.integrity.protocol.IExpressIntegrityService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1595i
    /* JADX INFO: renamed from: c */
    public final void mo422c(Bundle bundle, InterfaceC1597k interfaceC1597k) {
        Parcel parcelM381a = m381a();
        AbstractC1589c.m416c(parcelM381a, bundle);
        parcelM381a.writeStrongBinder(interfaceC1597k);
        m382b(3, parcelM381a);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1595i
    /* JADX INFO: renamed from: d */
    public final void mo423d(Bundle bundle, InterfaceC1597k interfaceC1597k) {
        Parcel parcelM381a = m381a();
        AbstractC1589c.m416c(parcelM381a, bundle);
        parcelM381a.writeStrongBinder(interfaceC1597k);
        m382b(2, parcelM381a);
    }
}
