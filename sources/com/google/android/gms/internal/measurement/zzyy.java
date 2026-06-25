package com.google.android.gms.internal.measurement;

import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public final class zzyy extends zzabm implements zzabi {
    protected final Object[] zza;
    protected final StringBuilder zzb;
    private int zzc;

    public zzyy(zzaaf zzaafVar, Object[] objArr, StringBuilder sb) {
        super(zzaafVar);
        this.zzc = 0;
        this.zza = objArr;
        this.zzb = sb;
    }

    public static StringBuilder zza(zzzd zzzdVar, StringBuilder sb) {
        if (zzzdVar.zzh() == null) {
            sb.append(zzzh.zza(zzzdVar.zzj()));
            return sb;
        }
        zzyy zzyyVar = new zzyy(zzzdVar.zzh(), zzzdVar.zzi(), sb);
        StringBuilder sb2 = (StringBuilder) zzyyVar.zzl();
        if (zzzdVar.zzi().length > zzyyVar.zzj()) {
            sb2.append(" [ERROR: UNUSED LOG ARGUMENTS]");
        }
        return sb2;
    }

    private static void zzm(StringBuilder sb, Object obj, String str) {
        sb.append("[INVALID: format=");
        sb.append(str);
        sb.append(", type=");
        sb.append(obj.getClass().getCanonicalName());
        sb.append(", value=");
        sb.append(zzzh.zza(obj));
        sb.append("]");
    }

    @Override // com.google.android.gms.internal.measurement.zzabm
    public final void zzb(int i, int i2, zzabh zzabhVar) {
        zzh().zzd(this.zzb, zzi(), this.zzc, i);
        zzabhVar.zze(this, this.zza);
        this.zzc = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b3  */
    @Override // com.google.android.gms.internal.measurement.zzabi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzc(java.lang.Object r7, com.google.android.gms.internal.measurement.zzyz r8, com.google.android.gms.internal.measurement.zzza r9) {
        /*
            Method dump skipped, instruction units count: 269
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzyy.zzc(java.lang.Object, com.google.android.gms.internal.measurement.zzyz, com.google.android.gms.internal.measurement.zzza):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzabi
    public final void zzd(Object obj, zzabf zzabfVar, zzza zzzaVar) {
        if ((obj instanceof Date) || (obj instanceof Calendar) || (obj instanceof Long)) {
            StringBuilder sb = new StringBuilder("%");
            zzzaVar.zzl(sb);
            sb.append(true != zzzaVar.zzk() ? 't' : 'T');
            sb.append(zzabfVar.zzb());
            this.zzb.append(String.format(zzzh.zza, sb.toString(), obj));
            return;
        }
        StringBuilder sb2 = this.zzb;
        char cZzb = zzabfVar.zzb();
        StringBuilder sb3 = new StringBuilder(String.valueOf(cZzb).length() + 2);
        sb3.append("%t");
        sb3.append(cZzb);
        zzm(sb2, obj, sb3.toString());
    }

    @Override // com.google.android.gms.internal.measurement.zzabi
    public final void zze() {
        this.zzb.append("[ERROR: MISSING LOG ARGUMENT]");
    }

    @Override // com.google.android.gms.internal.measurement.zzabi
    public final void zzf() {
        this.zzb.append("null");
    }

    @Override // com.google.android.gms.internal.measurement.zzabm
    public final /* bridge */ /* synthetic */ Object zzg() {
        zzabn zzabnVarZzh = zzh();
        String strZzi = zzi();
        int i = this.zzc;
        int length = zzi().length();
        StringBuilder sb = this.zzb;
        zzabnVarZzh.zzd(sb, strZzi, i, length);
        return sb;
    }
}
