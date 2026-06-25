package com.google.android.gms.cast.framework;

import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.cast.zzb;
import com.google.android.gms.internal.cast.zzc;

/* JADX INFO: loaded from: classes4.dex */
public abstract class zzba extends zzb implements zzbb {
    public zzba() {
        super("com.google.android.gms.cast.framework.ISessionProvider");
    }

    @Override // com.google.android.gms.internal.cast.zzb
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1) {
            String string = parcel.readString();
            zzc.zzf(parcel);
            IObjectWrapper iObjectWrapperZzb = zzb(string);
            parcel2.writeNoException();
            zzc.zze(parcel2, iObjectWrapperZzb);
        } else if (i == 2) {
            boolean zZzc = zzc();
            parcel2.writeNoException();
            int i3 = zzc.$r8$clinit;
            parcel2.writeInt(zZzc ? 1 : 0);
        } else if (i == 3) {
            String strZzd = zzd();
            parcel2.writeNoException();
            parcel2.writeString(strZzd);
        } else {
            if (i != 4) {
                return false;
            }
            parcel2.writeNoException();
            parcel2.writeInt(12451000);
        }
        return true;
    }
}
