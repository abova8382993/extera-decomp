package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.j */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractBinderC1596j extends AbstractBinderC1588b implements InterfaceC1597k {
    public AbstractBinderC1596j() {
        super("com.google.android.play.core.integrity.protocol.IExpressIntegrityServiceCallback");
    }

    @Override // com.google.android.play.integrity.internal.AbstractBinderC1588b
    /* JADX INFO: renamed from: a */
    protected final boolean mo413a(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 2) {
            Bundle bundle = (Bundle) AbstractC1589c.m414a(parcel, Bundle.CREATOR);
            AbstractC1589c.m415b(parcel);
            mo359e(bundle);
            return true;
        }
        if (i == 3) {
            Bundle bundle2 = (Bundle) AbstractC1589c.m414a(parcel, Bundle.CREATOR);
            AbstractC1589c.m415b(parcel);
            mo357c(bundle2);
            return true;
        }
        if (i == 4) {
            Bundle bundle3 = (Bundle) AbstractC1589c.m414a(parcel, Bundle.CREATOR);
            AbstractC1589c.m415b(parcel);
            mo358d(bundle3);
            return true;
        }
        if (i != 5) {
            return false;
        }
        Bundle bundle4 = (Bundle) AbstractC1589c.m414a(parcel, Bundle.CREATOR);
        AbstractC1589c.m415b(parcel);
        mo356b(bundle4);
        return true;
    }
}
