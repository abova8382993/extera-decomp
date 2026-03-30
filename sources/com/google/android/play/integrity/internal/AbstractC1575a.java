package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.a */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC1575a implements IInterface {

    /* JADX INFO: renamed from: a */
    private final IBinder f494a;

    /* JADX INFO: renamed from: b */
    private final String f495b;

    protected AbstractC1575a(IBinder iBinder, String str) {
        this.f494a = iBinder;
        this.f495b = str;
    }

    /* JADX INFO: renamed from: a */
    protected final Parcel m381a() {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInterfaceToken(this.f495b);
        return parcelObtain;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f494a;
    }

    /* JADX INFO: renamed from: b */
    protected final void m382b(int i, Parcel parcel) {
        try {
            this.f494a.transact(i, parcel, null, 1);
        } finally {
            parcel.recycle();
        }
    }
}
