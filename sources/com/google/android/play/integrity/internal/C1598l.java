package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.l */
/* JADX INFO: loaded from: classes4.dex */
public final class C1598l extends AbstractC1575a implements InterfaceC1600n {
    C1598l(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.integrity.protocol.IIntegrityService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.play.integrity.internal.InterfaceC1600n
    /* JADX INFO: renamed from: c */
    public final void mo425c(Bundle bundle, InterfaceC1602p interfaceC1602p) {
        Parcel parcelM381a = m381a();
        AbstractC1589c.m416c(parcelM381a, bundle);
        parcelM381a.writeStrongBinder(interfaceC1602p);
        m382b(2, parcelM381a);
    }
}
