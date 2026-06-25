package com.google.android.gms.internal.measurement;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;

/* JADX INFO: loaded from: classes4.dex */
public final class zzmw {
    private static final zzmw zza = new zzmw(ImmutableSortedSet.m525of());
    private final ImmutableSortedSet zzb;

    public zzmw(ImmutableSortedSet immutableSortedSet) {
        this.zzb = immutableSortedSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:149:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0154  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.internal.measurement.zzmw zza(com.google.android.gms.internal.measurement.zzmw r26, com.google.common.collect.ImmutableMap r27) {
        /*
            Method dump skipped, instruction units count: 507
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzmw.zza(com.google.android.gms.internal.measurement.zzmw, com.google.common.collect.ImmutableMap):com.google.android.gms.internal.measurement.zzmw");
    }

    public static zzmw zzb() {
        return zza;
    }

    public static zzmw zzd(zzacv zzacvVar) throws zzaeh {
        String strZzl;
        long j;
        zzmv zzmvVar;
        int iZzx = zzacvVar.zzx();
        if (iZzx < 0) {
            zzmw$$ExternalSyntheticBUOutline0.m372m("Negative number of flags");
            return null;
        }
        ImmutableSortedSet.Builder builderNaturalOrder = ImmutableSortedSet.naturalOrder();
        long j2 = 0;
        for (int i = 0; i < iZzx; i++) {
            long jZzz = zzacvVar.zzz();
            int i2 = (int) jZzz;
            long j3 = jZzz >>> 3;
            if (j3 == 0) {
                j = 0;
                strZzl = zzacvVar.zzl();
            } else {
                long j4 = j3 + j2;
                if (j4 > 2305843009213693951L) {
                    zzmw$$ExternalSyntheticBUOutline0.m372m("Flag name larger than max size");
                    return null;
                }
                strZzl = null;
                j = j4;
            }
            int i3 = i2 & 7;
            if (i3 == 0 || i3 == 1) {
                zzmvVar = new zzmv(j, strZzl, i3, 0L, null);
            } else if (i3 == 2) {
                zzmvVar = new zzmv(j, strZzl, i3, zzacvVar.zzz(), null);
            } else if (i3 == 3) {
                zzmvVar = new zzmv(j, strZzl, i3, Double.doubleToRawLongBits(zzacvVar.zzd()), null);
            } else if (i3 == 4) {
                zzmvVar = new zzmv(j, strZzl, i3, 0L, zzacvVar.zzl());
            } else {
                if (i3 != 5) {
                    StringBuilder sb = new StringBuilder(String.valueOf(i3).length() + 23);
                    sb.append("Unrecognized flag type ");
                    sb.append(i3);
                    throw new zzaeh(sb.toString());
                }
                zzmvVar = new zzmv(j, strZzl, i3, 0L, zzacvVar.zzo());
            }
            long j5 = zzmvVar.zza;
            if (j5 != 0) {
                j2 = j5;
            }
            builderNaturalOrder.add(zzmvVar);
        }
        return new zzmw(builderNaturalOrder.build());
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzmw) {
            return this.zzb.equals(((zzmw) obj).zzb);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzb.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzc(ImmutableMap.Builder builder) {
        UnmodifiableIterator it = this.zzb.iterator();
        while (it.hasNext()) {
            zzmv zzmvVar = (zzmv) it.next();
            builder.put(zzmvVar.zza(), zzmvVar.zzb());
        }
    }

    public final ImmutableSortedSet zze() {
        return this.zzb;
    }

    public final int zzf() {
        return this.zzb.size();
    }
}
