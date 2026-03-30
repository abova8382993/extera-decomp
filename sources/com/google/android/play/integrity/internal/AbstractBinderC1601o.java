package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.o */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractBinderC1601o extends AbstractBinderC1588b implements InterfaceC1602p {
    public AbstractBinderC1601o() {
        super("com.google.android.play.core.integrity.protocol.IIntegrityServiceCallback");
    }

    @Override // com.google.android.play.integrity.internal.AbstractBinderC1588b
    /* JADX INFO: renamed from: a */
    protected final boolean mo413a(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 2) {
            return false;
        }
        Bundle bundle = (Bundle) AbstractC1589c.m414a(parcel, Bundle.CREATOR);
        AbstractC1589c.m415b(parcel);
        mo347b(bundle);
        return true;
    }
}
