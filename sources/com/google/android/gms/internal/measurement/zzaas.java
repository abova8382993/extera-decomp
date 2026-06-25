package com.google.android.gms.internal.measurement;

import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
public final class zzaas extends zzaag {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final Set zzb;
    private static final zzzq zzc;
    private static final zzaaq zzd;
    private final String zze;
    private final Level zzf;
    private final Set zzg;
    private final zzzq zzh;
    private final int zzi;

    static {
        Set setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(zzxx.zza, zzyw.zza, zzyx.zza)));
        zzb = setUnmodifiableSet;
        zzc = zzzt.zza(setUnmodifiableSet).zzc();
        zzd = new zzaaq(null);
    }

    public /* synthetic */ zzaas(String str, String str2, boolean z, int i, Level level, Set set, zzzq zzzqVar, byte[] bArr) {
        super(str2);
        this.zze = zzaal.zza(_UrlKt.FRAGMENT_ENCODE_SET, str2, true);
        this.zzi = 2;
        this.zzf = level;
        this.zzg = set;
        this.zzh = zzzqVar;
    }

    public static zzaaq zze() {
        return zzd;
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void zzi(com.google.android.gms.internal.measurement.zzzd r3, java.lang.String r4, int r5, java.util.logging.Level r6, java.util.Set r7, com.google.android.gms.internal.measurement.zzzq r8) {
        /*
            Method dump skipped, instruction units count: 211
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzaas.zzi(com.google.android.gms.internal.measurement.zzzd, java.lang.String, int, java.util.logging.Level, java.util.Set, com.google.android.gms.internal.measurement.zzzq):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzzf
    public final boolean zzb(Level level) {
        String str = this.zze;
        int iZzb = zzaal.zzb(level);
        return Log.isLoggable(str, iZzb) || Log.isLoggable("all", iZzb);
    }

    @Override // com.google.android.gms.internal.measurement.zzzf
    public final void zzc(zzzd zzzdVar) {
        zzi(zzzdVar, this.zze, 2, this.zzf, this.zzg, this.zzh);
    }
}
