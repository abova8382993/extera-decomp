package com.google.android.play.integrity.internal;

import android.os.Bundle;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.j */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractBinderC1802j extends AbstractBinderC1794b implements InterfaceC1803k {
    public AbstractBinderC1802j() {
        super("com.google.android.play.core.integrity.protocol.IExpressIntegrityServiceCallback");
    }

    @Override // com.google.android.play.integrity.internal.AbstractBinderC1794b
    /* JADX INFO: renamed from: a */
    public final boolean mo474a(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 2) {
            Bundle bundle = (Bundle) AbstractC1795c.m475a(parcel, Bundle.CREATOR);
            AbstractC1795c.m476b(parcel);
            mo420e(bundle);
            return true;
        }
        if (i == 3) {
            Bundle bundle2 = (Bundle) AbstractC1795c.m475a(parcel, Bundle.CREATOR);
            AbstractC1795c.m476b(parcel);
            mo418c(bundle2);
            return true;
        }
        if (i == 4) {
            Bundle bundle3 = (Bundle) AbstractC1795c.m475a(parcel, Bundle.CREATOR);
            AbstractC1795c.m476b(parcel);
            mo419d(bundle3);
            return true;
        }
        if (i != 5) {
            return false;
        }
        Bundle bundle4 = (Bundle) AbstractC1795c.m475a(parcel, Bundle.CREATOR);
        AbstractC1795c.m476b(parcel);
        mo417b(bundle4);
        return true;
    }
}
