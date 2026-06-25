package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.o */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractBinderC1807o extends AbstractBinderC1794b implements InterfaceC1808p {
    public AbstractBinderC1807o() {
        super("com.google.android.play.core.integrity.protocol.IIntegrityServiceCallback");
    }

    @Override // com.google.android.play.integrity.internal.AbstractBinderC1794b
    /* JADX INFO: renamed from: a */
    public final boolean mo474a(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 2) {
            return false;
        }
        Bundle bundle = (Bundle) AbstractC1795c.m475a(parcel, Bundle.CREATOR);
        AbstractC1795c.m476b(parcel);
        mo408b(bundle);
        return true;
    }
}
