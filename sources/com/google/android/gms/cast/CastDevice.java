package com.google.android.gms.cast;

import android.net.Network;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.cast.internal.CastUtils;
import com.google.android.gms.cast.internal.zzaa;
import com.google.android.gms.cast.internal.zzz;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
public class CastDevice extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<CastDevice> CREATOR = new zzr();
    final String zza;
    final Boolean zzb;
    final Network zzc;
    private final String zzd;
    private InetAddress zze;
    private final String zzf;
    private final String zzg;
    private final String zzh;
    private final int zzi;
    private final List zzj;
    private final com.google.android.gms.cast.internal.zzp zzk;
    private final int zzl;
    private final String zzm;
    private final String zzn;
    private final int zzo;
    private final String zzp;
    private final byte[] zzq;
    private final String zzr;
    private final boolean zzs;
    private final zzaa zzt;
    private final Integer zzu;

    public CastDevice(String str, String str2, String str3, String str4, String str5, int i, List list, int i2, int i3, String str6, String str7, int i4, String str8, byte[] bArr, String str9, boolean z, zzaa zzaaVar, Integer num, Boolean bool, Network network) {
        this.zzd = zzf(str);
        String strZzf = zzf(str2);
        this.zza = strZzf;
        if (!TextUtils.isEmpty(strZzf)) {
            try {
                this.zze = InetAddress.getByName(strZzf);
            } catch (UnknownHostException e) {
                String str10 = this.zza;
                String message = e.getMessage();
                StringBuilder sb = new StringBuilder(String.valueOf(str10).length() + 48 + String.valueOf(message).length());
                sb.append("Unable to convert host address (");
                sb.append(str10);
                sb.append(") to ipaddress: ");
                sb.append(message);
                Log.i("CastDevice", sb.toString());
            }
        }
        this.zzf = zzf(str3);
        this.zzg = zzf(str4);
        this.zzh = zzf(str5);
        this.zzi = i;
        this.zzj = list == null ? new ArrayList() : list;
        this.zzl = i3;
        this.zzm = zzf(str6);
        this.zzn = str7;
        this.zzo = i4;
        this.zzp = str8;
        this.zzq = bArr;
        this.zzr = str9;
        this.zzs = z;
        this.zzt = zzaaVar;
        this.zzu = num;
        this.zzb = bool;
        this.zzc = network;
        this.zzk = new com.google.android.gms.cast.internal.zzp(i2, zzaaVar);
    }

    public static CastDevice getFromBundle(Bundle bundle) {
        ClassLoader classLoader;
        if (bundle == null || (classLoader = CastDevice.class.getClassLoader()) == null) {
            return null;
        }
        bundle.setClassLoader(classLoader);
        return (CastDevice) bundle.getParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE");
    }

    private static String zzf(String str) {
        return str == null ? _UrlKt.FRAGMENT_ENCODE_SET : str;
    }

    public boolean equals(Object obj) {
        int i;
        byte[] bArr;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CastDevice)) {
            return false;
        }
        CastDevice castDevice = (CastDevice) obj;
        String str = this.zzd;
        String str2 = castDevice.zzd;
        if (str == null) {
            return str2 == null;
        }
        if (CastUtils.zza(str, str2) && CastUtils.zza(this.zze, castDevice.zze) && CastUtils.zza(this.zzg, castDevice.zzg) && CastUtils.zza(this.zzf, castDevice.zzf)) {
            String str3 = this.zzh;
            if (CastUtils.zza(str3, castDevice.zzh) && (i = this.zzi) == castDevice.zzi && CastUtils.zza(this.zzj, castDevice.zzj) && this.zzk.zza() == castDevice.zzk.zza() && this.zzl == castDevice.zzl && CastUtils.zza(this.zzm, castDevice.zzm) && CastUtils.zza(Integer.valueOf(this.zzo), Integer.valueOf(castDevice.zzo)) && CastUtils.zza(this.zzp, castDevice.zzp) && CastUtils.zza(this.zzn, castDevice.zzn) && CastUtils.zza(str3, castDevice.getDeviceVersion()) && i == castDevice.getServicePort() && ((((bArr = this.zzq) == null && castDevice.zzq == null) || Arrays.equals(bArr, castDevice.zzq)) && CastUtils.zza(this.zzr, castDevice.zzr) && this.zzs == castDevice.zzs && CastUtils.zza(zzb(), castDevice.zzb()))) {
                if (CastUtils.zza(Boolean.valueOf(zze()), Boolean.valueOf(castDevice.zze() && CastUtils.zza(this.zzc, castDevice.zzc)))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getDeviceVersion() {
        return this.zzh;
    }

    public String getFriendlyName() {
        return this.zzf;
    }

    public List<WebImage> getIcons() {
        return Collections.unmodifiableList(this.zzj);
    }

    public String getModelName() {
        return this.zzg;
    }

    public int getServicePort() {
        return this.zzi;
    }

    public boolean hasCapability(int i) {
        return this.zzk.zzb(i);
    }

    public int hashCode() {
        String str = this.zzd;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public void putInBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        bundle.putParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE", this);
    }

    public String toString() {
        com.google.android.gms.cast.internal.zzp zzpVar = this.zzk;
        String strConcat = zzpVar.zzb(64) ? "[dynamic group]" : zzpVar.zzc() ? "[static group]" : zzpVar.zzd() ? "[speaker pair]" : _UrlKt.FRAGMENT_ENCODE_SET;
        if (zzpVar.zzb(262144)) {
            strConcat = strConcat.concat("[cast connect]");
        }
        String str = this.zzf;
        Locale locale = Locale.ROOT;
        int i = CastUtils.$r8$clinit;
        if (!TextUtils.isEmpty(str)) {
            int length = str.length();
            str = length <= 2 ? length == 2 ? "xx" : "x" : String.format(locale, "%c%d%c", Character.valueOf(str.charAt(0)), Integer.valueOf(length - 2), Character.valueOf(str.charAt(length - 1)));
        }
        return String.format(locale, "\"%s\" (%s) %s", str, this.zzd, strConcat);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        String str = this.zzd;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, str, false);
        SafeParcelWriter.writeString(parcel, 3, this.zza, false);
        SafeParcelWriter.writeString(parcel, 4, getFriendlyName(), false);
        SafeParcelWriter.writeString(parcel, 5, getModelName(), false);
        SafeParcelWriter.writeString(parcel, 6, getDeviceVersion(), false);
        SafeParcelWriter.writeInt(parcel, 7, getServicePort());
        SafeParcelWriter.writeTypedList(parcel, 8, getIcons(), false);
        SafeParcelWriter.writeInt(parcel, 9, this.zzk.zza());
        SafeParcelWriter.writeInt(parcel, 10, this.zzl);
        SafeParcelWriter.writeString(parcel, 11, this.zzm, false);
        SafeParcelWriter.writeString(parcel, 12, this.zzn, false);
        SafeParcelWriter.writeInt(parcel, 13, this.zzo);
        SafeParcelWriter.writeString(parcel, 14, this.zzp, false);
        SafeParcelWriter.writeByteArray(parcel, 15, this.zzq, false);
        SafeParcelWriter.writeString(parcel, 16, this.zzr, false);
        SafeParcelWriter.writeBoolean(parcel, 17, this.zzs);
        SafeParcelWriter.writeParcelable(parcel, 18, zzb(), i, false);
        SafeParcelWriter.writeIntegerObject(parcel, 19, this.zzu, false);
        SafeParcelWriter.writeBooleanObject(parcel, 20, Boolean.valueOf(zze()), false);
        SafeParcelWriter.writeParcelable(parcel, 21, this.zzc, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zza() {
        return this.zzn;
    }

    public final zzaa zzb() {
        zzaa zzaaVar = this.zzt;
        return (zzaaVar == null && this.zzk.zzd()) ? zzz.zza(1) : zzaaVar;
    }

    public final int zzc() {
        return this.zzk.zza();
    }

    public final int zzd() {
        com.google.android.gms.cast.internal.zzp zzpVar = this.zzk;
        if (zzpVar.zzb(64)) {
            return 4;
        }
        if (zzpVar.zzc()) {
            return 3;
        }
        if (zzpVar.zzd()) {
            return 5;
        }
        return hasCapability(1) ? 2 : 1;
    }

    public final boolean zze() {
        Boolean bool = this.zzb;
        if (bool != null) {
            return bool.booleanValue();
        }
        int i = this.zzl;
        return i != -1 && (i & 2) > 0;
    }
}
