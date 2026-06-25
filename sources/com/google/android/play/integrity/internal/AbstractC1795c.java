package com.google.android.play.integrity.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.fido.zzc$$ExternalSyntheticBUOutline0;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.c */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractC1795c {

    /* JADX INFO: renamed from: a */
    private static final ClassLoader f615a = AbstractC1795c.class.getClassLoader();

    /* JADX INFO: renamed from: a */
    public static Parcelable m475a(Parcel parcel, Parcelable.Creator creator) {
        if (parcel.readInt() == 0) {
            return null;
        }
        return (Parcelable) creator.createFromParcel(parcel);
    }

    /* JADX INFO: renamed from: b */
    public static void m476b(Parcel parcel) {
        int iDataAvail = parcel.dataAvail();
        if (iDataAvail <= 0) {
            return;
        }
        zzc$$ExternalSyntheticBUOutline0.m364m(iDataAvail);
    }

    /* JADX INFO: renamed from: c */
    public static void m477c(Parcel parcel, Parcelable parcelable) {
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 0);
    }
}
