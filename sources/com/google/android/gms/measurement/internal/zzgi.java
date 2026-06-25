package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzaif;
import com.google.android.gms.internal.measurement.zzaja;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import kotlin.time.DurationKt;

/* JADX INFO: loaded from: classes5.dex */
public final class zzgi extends zzg {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private final long zzg;
    private final long zzh;
    private List zzi;
    private String zzj;
    private final String zzk;
    private int zzl;
    private String zzm;
    private String zzn;
    private long zzo;
    private String zzp;

    public zzgi(zzic zzicVar, long j, long j2, String str) {
        super(zzicVar);
        this.zzo = 0L;
        this.zzp = null;
        this.zzg = j;
        this.zzh = j2;
        this.zzk = str;
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    public final boolean zze() {
        return true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:92|94|(1:96)(19:177|98|(1:102)(2:103|(1:105))|179|106|(4:108|(1:110)(1:112)|173|113)|118|(2:120|(2:122|(2:124|(2:126|(2:128|(2:130|(1:132)(1:133))(1:134))(1:135))(1:136))(1:137))(1:138))(1:139)|140|175|141|(1:143)(1:144)|145|(1:147)(1:148)|149|(1:151)|155|(2:158|(1:160)(4:161|(3:164|(1:182)(1:183)|162)|181|167))(1:167)|(2:169|170)(2:171|172))|97|118|(0)(0)|140|175|141|(0)(0)|145|(0)(0)|149|(0)|155|(0)(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x01a1, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x01a2, code lost:
    
        r11.zzu.zzaW().zzb().zzc("Fetching Google App Id failed with exception. appId", com.google.android.gms.measurement.internal.zzgu.zzl(r1), r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:120:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0175 A[Catch: IllegalStateException -> 0x01a1, TryCatch #1 {IllegalStateException -> 0x01a1, blocks: (B:141:0x016c, B:145:0x0183, B:149:0x018b, B:151:0x018f, B:144:0x0175), top: B:175:0x016c }] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x018f A[Catch: IllegalStateException -> 0x01a1, TRY_LEAVE, TryCatch #1 {IllegalStateException -> 0x01a1, blocks: (B:141:0x016c, B:145:0x0183, B:149:0x018b, B:151:0x018f, B:144:0x0175), top: B:175:0x016c }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x020a  */
    @Override // com.google.android.gms.measurement.internal.zzg
    @org.checkerframework.checker.nullness.qual.EnsuresNonNull({"appId", "appStore", "appName", "gmpAppId", "gaAppId"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzf() {
        /*
            Method dump skipped, instruction units count: 525
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgi.zzf():void");
    }

    public final zzr zzh(String str) {
        long j;
        String str2;
        long j2;
        String str3;
        boolean z;
        int i;
        long j3;
        ApplicationInfo applicationInfo;
        zzg();
        String strZzj = zzj();
        String strZzk = zzk();
        zzb();
        String str4 = this.zzb;
        zzb();
        long j4 = this.zzc;
        zzb();
        Preconditions.checkNotNull(this.zzd);
        String str5 = this.zzd;
        zzic zzicVar = this.zzu;
        zzicVar.zzc().zzi();
        zzb();
        zzg();
        long j5 = this.zzf;
        if (j5 == 0) {
            zzpp zzppVarZzk = this.zzu.zzk();
            Context contextZzaZ = zzicVar.zzaZ();
            String packageName = zzicVar.zzaZ().getPackageName();
            zzppVarZzk.zzg();
            Preconditions.checkNotNull(contextZzaZ);
            Preconditions.checkNotEmpty(packageName);
            PackageManager packageManager = contextZzaZ.getPackageManager();
            MessageDigest messageDigestZzQ = zzpp.zzQ();
            long jZzR = -1;
            if (messageDigestZzQ == null) {
                zzppVarZzk.zzu.zzaW().zzb().zza("Could not get MD5 instance");
                j = 0;
            } else {
                if (packageManager != null) {
                    try {
                        if (zzppVarZzk.zzag(contextZzaZ, packageName)) {
                            j = 0;
                            jZzR = 0;
                        } else {
                            PackageManagerWrapper packageManagerWrapperPackageManager = Wrappers.packageManager(contextZzaZ);
                            zzic zzicVar2 = zzppVarZzk.zzu;
                            j = 0;
                            try {
                                Signature[] signatureArr = packageManagerWrapperPackageManager.getPackageInfo(zzicVar2.zzaZ().getPackageName(), 64).signatures;
                                if (signatureArr == null || signatureArr.length <= 0) {
                                    zzicVar2.zzaW().zze().zza("Could not get signatures");
                                } else {
                                    jZzR = zzpp.zzR(messageDigestZzQ.digest(signatureArr[0].toByteArray()));
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                e = e;
                                zzppVarZzk.zzu.zzaW().zzb().zzb("Package name not found", e);
                                j5 = j;
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                        e = e2;
                        j = 0;
                    }
                } else {
                    j = 0;
                }
                j5 = j;
                this.zzf = j5;
            }
            j5 = jZzR;
            this.zzf = j5;
        } else {
            j = 0;
        }
        long j6 = j5;
        zzic zzicVar3 = this.zzu;
        boolean zZzB = zzicVar3.zzB();
        boolean z2 = !zzicVar3.zzd().zzm;
        zzg();
        if (zzicVar3.zzB()) {
            zzaja.zza();
            if (zzicVar3.zzc().zzp(null, zzfy.zzaH)) {
                this.zzu.zzaW().zzk().zza("Disabled IID for tests.");
            } else {
                try {
                    Class<?> clsLoadClass = zzicVar3.zzaZ().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
                    if (clsLoadClass != null) {
                        try {
                            Object objInvoke = clsLoadClass.getDeclaredMethod("getInstance", Context.class).invoke(null, this.zzu.zzaZ());
                            if (objInvoke != null) {
                                try {
                                    str2 = (String) clsLoadClass.getDeclaredMethod("getFirebaseInstanceId", null).invoke(objInvoke, null);
                                } catch (Exception unused) {
                                    this.zzu.zzaW().zzh().zza("Failed to retrieve Firebase Instance Id");
                                    str2 = null;
                                }
                            }
                        } catch (Exception unused2) {
                            this.zzu.zzaW().zzf().zza("Failed to obtain Firebase Analytics instance");
                        }
                    }
                } catch (ClassNotFoundException unused3) {
                }
            }
            str2 = null;
        } else {
            str2 = null;
        }
        zzic zzicVar4 = this.zzu;
        long jZza = zzicVar4.zzd().zzc.zza();
        long jMin = zzicVar4.zza;
        if (jZza != j) {
            jMin = Math.min(jMin, jZza);
        }
        zzb();
        int i2 = this.zzl;
        zzic zzicVar5 = this.zzu;
        boolean zZzu = zzicVar5.zzc().zzu();
        zzhh zzhhVarZzd = zzicVar5.zzd();
        zzhhVarZzd.zzg();
        long j7 = jMin;
        boolean z3 = zzhhVarZzd.zzd().getBoolean("deferred_analytics_collection", false);
        boolean z4 = zzicVar5.zzc().zzw("google_analytics_default_allow_ad_personalization_signals", true) != zzji.GRANTED;
        long j8 = this.zzg;
        Boolean boolValueOf = Boolean.valueOf(z4);
        List list = this.zzi;
        String strZzl = zzicVar5.zzd().zzl().zzl();
        if (this.zzj == null) {
            this.zzj = zzicVar5.zzk().zzaz();
        }
        String str6 = this.zzj;
        if (zzicVar5.zzd().zzl().zzo(zzjk.ANALYTICS_STORAGE)) {
            zzg();
            j2 = j8;
            if (this.zzo != j) {
                long jCurrentTimeMillis = zzicVar5.zzba().currentTimeMillis() - this.zzo;
                if (this.zzn != null && jCurrentTimeMillis > DurationKt.MILLIS_IN_DAY && this.zzp == null) {
                    zzi();
                }
            }
            if (this.zzn == null) {
                zzi();
            }
            str3 = this.zzn;
        } else {
            j2 = j8;
            str3 = null;
        }
        boolean zZzx = zzicVar5.zzc().zzx();
        zzpp zzppVarZzk2 = zzicVar5.zzk();
        String strZzj2 = zzj();
        zzic zzicVar6 = zzppVarZzk2.zzu;
        if (zzicVar6.zzaZ().getPackageManager() == null) {
            z = zZzx;
            j3 = j;
        } else {
            try {
                z = zZzx;
                i = 0;
            } catch (PackageManager.NameNotFoundException unused4) {
                z = zZzx;
                i = 0;
            }
            try {
                applicationInfo = Wrappers.packageManager(zzicVar6.zzaZ()).getApplicationInfo(strZzj2, 0);
            } catch (PackageManager.NameNotFoundException unused5) {
                zzic zzicVar7 = zzppVarZzk2.zzu;
                zzicVar7.zzaV();
                zzicVar7.zzaW().zzi().zzb("PackageManager failed to find running app: app_id", strZzj2);
            }
            int i3 = applicationInfo != null ? applicationInfo.targetSdkVersion : i;
            j3 = i3;
        }
        zzic zzicVar8 = this.zzu;
        int iZzb = zzicVar8.zzd().zzl().zzb();
        String strZze = zzicVar8.zzd().zzj().zze();
        zzaif.zza();
        zzal zzalVarZzc = zzicVar8.zzc();
        String str7 = str3;
        zzfx zzfxVar = zzfy.zzaP;
        long j9 = j3;
        int iZzW = zzalVarZzc.zzp(null, zzfxVar) ? zzicVar8.zzk().zzW() : 0;
        zzaif.zza();
        long jZzX = zzicVar8.zzc().zzp(null, zzfxVar) ? zzicVar8.zzk().zzX() : j;
        String strZzz = zzicVar8.zzc().zzz();
        int i4 = iZzW;
        String strZzb = new zze(zzicVar8.zzc().zzw("google_analytics_default_allow_ad_personalization_signals", true)).zzb();
        zzic zzicVar9 = this.zzu;
        return new zzr(strZzj, strZzk, str4, j4, str5, 161000L, j6, str, zZzB, z2, str2, j7, i2, zZzu, z3, boolValueOf, j2, list, strZzl, str6, str7, z, j9, iZzb, strZze, i4, jZzX, strZzz, strZzb, zzicVar9.zza, zzicVar9.zzx().zzj().zza(), zzicVar8.zzc().zzp(null, zzfy.zzbe) ? zzicVar9.zzb : j);
    }

    public final void zzi() {
        String str;
        zzg();
        zzic zzicVar = this.zzu;
        if (zzicVar.zzd().zzl().zzo(zzjk.ANALYTICS_STORAGE)) {
            byte[] bArr = new byte[16];
            zzicVar.zzk().zzf().nextBytes(bArr);
            str = String.format(Locale.US, "%032x", new BigInteger(1, bArr));
        } else {
            zzicVar.zzaW().zzj().zza("Analytics Storage consent is not granted");
            str = null;
        }
        zzicVar.zzaW().zzj().zza(String.format("Resetting session stitching token to %s", str == null ? "null" : "not null"));
        this.zzn = str;
        this.zzo = zzicVar.zzba().currentTimeMillis();
    }

    public final String zzj() {
        zzb();
        Preconditions.checkNotNull(this.zza);
        return this.zza;
    }

    public final String zzk() {
        zzg();
        zzb();
        Preconditions.checkNotNull(this.zzm);
        return this.zzm;
    }

    public final String zzl() {
        zzb();
        Preconditions.checkNotNull(this.zze);
        return this.zze;
    }

    public final int zzm() {
        zzb();
        return this.zzc;
    }

    public final long zzn() {
        return this.zzh;
    }

    public final int zzo() {
        zzb();
        return this.zzl;
    }

    public final List zzp() {
        return this.zzi;
    }

    public final boolean zzq(String str) {
        String str2 = this.zzp;
        boolean z = false;
        if (str2 != null && !str2.equals(str)) {
            z = true;
        }
        this.zzp = str;
        return z;
    }
}
