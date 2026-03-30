package com.google.android.play.integrity.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.b */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractBinderC1588b extends Binder implements IInterface {
    protected AbstractBinderC1588b(String str) {
        attachInterface(this, str);
    }

    /* JADX INFO: renamed from: a */
    protected abstract boolean mo413a(int i, Parcel parcel, Parcel parcel2, int i2);

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i <= 16777215) {
            parcel.enforceInterface(getInterfaceDescriptor());
        } else if (super.onTransact(i, parcel, parcel2, i2)) {
            return true;
        }
        return mo413a(i, parcel, parcel2, i2);
    }
}
