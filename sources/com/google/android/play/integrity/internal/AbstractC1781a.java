package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.a */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractC1781a implements IInterface {

    /* JADX INFO: renamed from: a */
    private final IBinder f591a;

    /* JADX INFO: renamed from: b */
    private final String f592b;

    public AbstractC1781a(IBinder iBinder, String str) {
        this.f591a = iBinder;
        this.f592b = str;
    }

    /* JADX INFO: renamed from: a */
    public final Parcel m442a() {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInterfaceToken(this.f592b);
        return parcelObtain;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f591a;
    }

    /* JADX INFO: renamed from: b */
    public final void m443b(int i, Parcel parcel) {
        try {
            this.f591a.transact(i, parcel, null, 1);
        } finally {
            parcel.recycle();
        }
    }
}
