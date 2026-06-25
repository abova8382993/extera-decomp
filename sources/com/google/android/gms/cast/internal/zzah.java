package com.google.android.gms.cast.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.zzbn;
import com.google.android.gms.common.api.ApiMetadata;
import com.google.android.gms.internal.cast.zzc;

/* JADX INFO: loaded from: classes4.dex */
public final class zzah extends com.google.android.gms.internal.cast.zza implements IInterface {
    public zzah(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.internal.ICastDeviceController");
    }

    public final void zze(ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        zzc.zzc(parcelZza, apiMetadata);
        zzd(1, parcelZza);
    }

    public final void zzg(String str, ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzc.zzc(parcelZza, apiMetadata);
        zzd(5, parcelZza);
    }

    public final void zzk(String str, String str2, long j, ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        parcelZza.writeLong(j);
        zzc.zzc(parcelZza, apiMetadata);
        zzd(9, parcelZza);
    }

    public final void zzl(String str, ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzc.zzc(parcelZza, apiMetadata);
        zzd(11, parcelZza);
    }

    public final void zzm(String str, ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzc.zzc(parcelZza, apiMetadata);
        zzd(12, parcelZza);
    }

    public final void zzn(String str, LaunchOptions launchOptions, ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        zzc.zzc(parcelZza, launchOptions);
        zzc.zzc(parcelZza, apiMetadata);
        zzd(13, parcelZza);
    }

    public final void zzo(String str, String str2, zzbn zzbnVar, ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        zzc.zzc(parcelZza, zzbnVar);
        zzc.zzc(parcelZza, apiMetadata);
        zzd(14, parcelZza);
    }

    public final void zzp(ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        zzc.zzc(parcelZza, apiMetadata);
        zzd(17, parcelZza);
    }

    public final void zzq(zzaj zzajVar, ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        zzc.zze(parcelZza, zzajVar);
        zzc.zzc(parcelZza, apiMetadata);
        zzd(18, parcelZza);
    }

    public final void zzr(ApiMetadata apiMetadata) {
        Parcel parcelZza = zza();
        zzc.zzc(parcelZza, apiMetadata);
        zzd(19, parcelZza);
    }
}
