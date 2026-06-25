package com.google.android.gms.internal.cast;

import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import okio.Segment$$ExternalSyntheticBUOutline0;
import p005c.g$$ExternalSyntheticBUOutline1;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes4.dex */
final class zzzl<T> implements zzzs<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzaak.zzq();
    private final int[] zzc;
    private final Object[] zzd;
    private final zzzi zze;
    private final boolean zzf = false;
    private final int[] zzg;
    private final int zzh;
    private final zzaad zzi;
    private final zzxs zzj;

    private zzzl(int[] iArr, Object[] objArr, int i, int i2, zzzi zzziVar, boolean z, int[] iArr2, int i3, int i4, zzzn zzznVar, zzyv zzyvVar, zzaad zzaadVar, zzxs zzxsVar, zzzd zzzdVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzg = iArr2;
        this.zzh = i3;
        this.zzi = zzaadVar;
        this.zzj = zzxsVar;
        this.zze = zzziVar;
    }

    private final boolean zzA(Object obj, int i) {
        int iZzq = zzq(i);
        long j = iZzq & 1048575;
        if (j != 1048575) {
            return ((1 << (iZzq >>> 20)) & zzaak.zzd(obj, j)) != 0;
        }
        int iZzp = zzp(i);
        long j2 = iZzp & 1048575;
        switch (zzr(iZzp)) {
            case 0:
                return Double.doubleToRawLongBits(zzaak.zzl(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzaak.zzj(obj, j2)) != 0;
            case 2:
                return zzaak.zzf(obj, j2) != 0;
            case 3:
                return zzaak.zzf(obj, j2) != 0;
            case 4:
                return zzaak.zzd(obj, j2) != 0;
            case 5:
                return zzaak.zzf(obj, j2) != 0;
            case 6:
                return zzaak.zzd(obj, j2) != 0;
            case 7:
                return zzaak.zzh(obj, j2);
            case 8:
                Object objZzn = zzaak.zzn(obj, j2);
                if (objZzn instanceof String) {
                    return !((String) objZzn).isEmpty();
                }
                if (objZzn instanceof zzxk) {
                    return !zzxk.zza.equals(objZzn);
                }
                Segment$$ExternalSyntheticBUOutline0.m991m();
                return false;
            case 9:
                return zzaak.zzn(obj, j2) != null;
            case 10:
                return !zzxk.zza.equals(zzaak.zzn(obj, j2));
            case 11:
                return zzaak.zzd(obj, j2) != 0;
            case 12:
                return zzaak.zzd(obj, j2) != 0;
            case 13:
                return zzaak.zzd(obj, j2) != 0;
            case 14:
                return zzaak.zzf(obj, j2) != 0;
            case 15:
                return zzaak.zzd(obj, j2) != 0;
            case 16:
                return zzaak.zzf(obj, j2) != 0;
            case 17:
                return zzaak.zzn(obj, j2) != null;
            default:
                Segment$$ExternalSyntheticBUOutline0.m991m();
                return false;
        }
    }

    private final void zzB(Object obj, int i) {
        int iZzq = zzq(i);
        long j = 1048575 & iZzq;
        if (j == 1048575) {
            return;
        }
        zzaak.zze(obj, j, (1 << (iZzq >>> 20)) | zzaak.zzd(obj, j));
    }

    private final boolean zzC(Object obj, int i, int i2) {
        return zzaak.zzd(obj, (long) (zzq(i2) & 1048575)) == i;
    }

    private final void zzD(Object obj, int i, int i2) {
        zzaak.zze(obj, zzq(i2) & 1048575, i);
    }

    private static final void zzE(int i, Object obj, zzaar zzaarVar) {
        if (obj instanceof String) {
            zzaarVar.zzm(i, (String) obj);
        } else {
            zzaarVar.zzn(i, (zzxk) obj);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:353:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0350  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x03a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.internal.cast.zzzl zzi(java.lang.Class r32, com.google.android.gms.internal.cast.zzzf r33, com.google.android.gms.internal.cast.zzzn r34, com.google.android.gms.internal.cast.zzyv r35, com.google.android.gms.internal.cast.zzaad r36, com.google.android.gms.internal.cast.zzxs r37, com.google.android.gms.internal.cast.zzzd r38) {
        /*
            Method dump skipped, instruction units count: 1047
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzzl.zzi(java.lang.Class, com.google.android.gms.internal.cast.zzzf, com.google.android.gms.internal.cast.zzzn, com.google.android.gms.internal.cast.zzyv, com.google.android.gms.internal.cast.zzaad, com.google.android.gms.internal.cast.zzxs, com.google.android.gms.internal.cast.zzzd):com.google.android.gms.internal.cast.zzzl");
    }

    private static Field zzj(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11 + name.length() + 29 + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString(), e);
        }
    }

    private final void zzk(Object obj, Object obj2, int i) {
        if (zzA(obj2, i)) {
            int iZzp = zzp(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzp;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                int i2 = this.zzc[i];
                String string = obj2.toString();
                zzzl$$ExternalSyntheticBUOutline0.m360m(String.valueOf(i2).length() + 38 + string.length(), i2, string);
                return;
            }
            zzzs zzzsVarZzm = zzm(i);
            if (!zzA(obj, i)) {
                if (zzs(object)) {
                    Object objZza = zzzsVarZzm.zza();
                    zzzsVarZzm.zzd(objZza, object);
                    unsafe.putObject(obj, j, objZza);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzB(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzs(object2)) {
                Object objZza2 = zzzsVarZzm.zza();
                zzzsVarZzm.zzd(objZza2, object2);
                unsafe.putObject(obj, j, objZza2);
                object2 = objZza2;
            }
            zzzsVarZzm.zzd(object2, object);
        }
    }

    private final void zzl(Object obj, Object obj2, int i) {
        int[] iArr = this.zzc;
        int i2 = iArr[i];
        if (zzC(obj2, i2, i)) {
            int iZzp = zzp(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzp;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                int i3 = iArr[i];
                String string = obj2.toString();
                zzzl$$ExternalSyntheticBUOutline0.m360m(String.valueOf(i3).length() + 38 + string.length(), i3, string);
                return;
            }
            zzzs zzzsVarZzm = zzm(i);
            if (!zzC(obj, i2, i)) {
                if (zzs(object)) {
                    Object objZza = zzzsVarZzm.zza();
                    zzzsVarZzm.zzd(objZza, object);
                    unsafe.putObject(obj, j, objZza);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzD(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzs(object2)) {
                Object objZza2 = zzzsVarZzm.zza();
                zzzsVarZzm.zzd(objZza2, object2);
                unsafe.putObject(obj, j, objZza2);
                object2 = objZza2;
            }
            zzzsVarZzm.zzd(object2, object);
        }
    }

    private final zzzs zzm(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzzs zzzsVar = (zzzs) objArr[i3];
        if (zzzsVar != null) {
            return zzzsVar;
        }
        zzzs zzzsVarZzb = zzzp.zza().zzb((Class) objArr[i3 + 1]);
        objArr[i3] = zzzsVarZzb;
        return zzzsVarZzb;
    }

    private final Object zzn(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static boolean zzo(Object obj, int i, zzzs zzzsVar) {
        return zzzsVar.zzh(zzaak.zzn(obj, i & 1048575));
    }

    private final int zzp(int i) {
        return this.zzc[i + 1];
    }

    private final int zzq(int i) {
        return this.zzc[i + 2];
    }

    private static int zzr(int i) {
        return (i >>> 20) & 255;
    }

    private static boolean zzs(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzyd) {
            return ((zzyd) obj).zzv();
        }
        return true;
    }

    private static double zzt(Object obj, long j) {
        return ((Double) zzaak.zzn(obj, j)).doubleValue();
    }

    private static float zzu(Object obj, long j) {
        return ((Float) zzaak.zzn(obj, j)).floatValue();
    }

    private static int zzv(Object obj, long j) {
        return ((Integer) zzaak.zzn(obj, j)).intValue();
    }

    private static long zzw(Object obj, long j) {
        return ((Long) zzaak.zzn(obj, j)).longValue();
    }

    private static boolean zzx(Object obj, long j) {
        return ((Boolean) zzaak.zzn(obj, j)).booleanValue();
    }

    private final boolean zzy(Object obj, Object obj2, int i) {
        return zzA(obj, i) == zzA(obj2, i);
    }

    private final boolean zzz(Object obj, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzA(obj, i) : (i3 & i4) != 0;
    }

    @Override // com.google.android.gms.internal.cast.zzzs
    public final Object zza() {
        return ((zzyd) this.zze).zzy();
    }

    @Override // com.google.android.gms.internal.cast.zzzs
    public final boolean zzb(Object obj, Object obj2) {
        boolean zZzC;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzp = zzp(i);
            long j = iZzp & 1048575;
            switch (zzr(iZzp)) {
                case 0:
                    if (!zzy(obj, obj2, i) || Double.doubleToLongBits(zzaak.zzl(obj, j)) != Double.doubleToLongBits(zzaak.zzl(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzy(obj, obj2, i) || Float.floatToIntBits(zzaak.zzj(obj, j)) != Float.floatToIntBits(zzaak.zzj(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzy(obj, obj2, i) || zzaak.zzf(obj, j) != zzaak.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzy(obj, obj2, i) || zzaak.zzf(obj, j) != zzaak.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzy(obj, obj2, i) || zzaak.zzd(obj, j) != zzaak.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzy(obj, obj2, i) || zzaak.zzf(obj, j) != zzaak.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzy(obj, obj2, i) || zzaak.zzd(obj, j) != zzaak.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzy(obj, obj2, i) || zzaak.zzh(obj, j) != zzaak.zzh(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzy(obj, obj2, i) || !zzzu.zzC(zzaak.zzn(obj, j), zzaak.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzy(obj, obj2, i) || !zzzu.zzC(zzaak.zzn(obj, j), zzaak.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzy(obj, obj2, i) || !zzzu.zzC(zzaak.zzn(obj, j), zzaak.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzy(obj, obj2, i) || zzaak.zzd(obj, j) != zzaak.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzy(obj, obj2, i) || zzaak.zzd(obj, j) != zzaak.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzy(obj, obj2, i) || zzaak.zzd(obj, j) != zzaak.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzy(obj, obj2, i) || zzaak.zzf(obj, j) != zzaak.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzy(obj, obj2, i) || zzaak.zzd(obj, j) != zzaak.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzy(obj, obj2, i) || zzaak.zzf(obj, j) != zzaak.zzf(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzy(obj, obj2, i) || !zzzu.zzC(zzaak.zzn(obj, j), zzaak.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zZzC = zzzu.zzC(zzaak.zzn(obj, j), zzaak.zzn(obj2, j));
                    break;
                case 50:
                    zZzC = zzzu.zzC(zzaak.zzn(obj, j), zzaak.zzn(obj2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzq = zzq(i) & 1048575;
                    if (zzaak.zzd(obj, jZzq) != zzaak.zzd(obj2, jZzq) || !zzzu.zzC(zzaak.zzn(obj, j), zzaak.zzn(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    break;
            }
            if (!zZzC) {
                return false;
            }
        }
        if (!((zzyd) obj).zzc.equals(((zzyd) obj2).zzc)) {
            return false;
        }
        if (!this.zzf) {
            return true;
        }
        MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.cast.zzzs
    public final int zzc(Object obj) {
        int i;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int i2;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int[] iArr = this.zzc;
            if (i3 >= iArr.length) {
                int iHashCode = (i4 * 53) + ((zzyd) obj).zzc.hashCode();
                if (!this.zzf) {
                    return iHashCode;
                }
                MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(obj);
                throw null;
            }
            int iZzp = zzp(i3);
            int i5 = 1048575 & iZzp;
            int iZzr = zzr(iZzp);
            int i6 = iArr[i3];
            long j = i5;
            int iHashCode2 = 37;
            switch (iZzr) {
                case 0:
                    i = i4 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzaak.zzl(obj, j));
                    byte[] bArr = zzym.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 1:
                    i = i4 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzaak.zzj(obj, j));
                    i4 = i + iFloatToIntBits;
                    break;
                case 2:
                    i = i4 * 53;
                    jDoubleToLongBits = zzaak.zzf(obj, j);
                    byte[] bArr2 = zzym.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 3:
                    i = i4 * 53;
                    jDoubleToLongBits = zzaak.zzf(obj, j);
                    byte[] bArr3 = zzym.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 4:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 5:
                    i = i4 * 53;
                    jDoubleToLongBits = zzaak.zzf(obj, j);
                    byte[] bArr4 = zzym.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 6:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 7:
                    i = i4 * 53;
                    iFloatToIntBits = zzym.zza(zzaak.zzh(obj, j));
                    i4 = i + iFloatToIntBits;
                    break;
                case 8:
                    i = i4 * 53;
                    iFloatToIntBits = ((String) zzaak.zzn(obj, j)).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 9:
                    i2 = i4 * 53;
                    Object objZzn = zzaak.zzn(obj, j);
                    if (objZzn != null) {
                        iHashCode2 = objZzn.hashCode();
                    }
                    i4 = i2 + iHashCode2;
                    break;
                case 10:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 11:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 12:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 13:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 14:
                    i = i4 * 53;
                    jDoubleToLongBits = zzaak.zzf(obj, j);
                    byte[] bArr5 = zzym.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 15:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzd(obj, j);
                    i4 = i + iFloatToIntBits;
                    break;
                case 16:
                    i = i4 * 53;
                    jDoubleToLongBits = zzaak.zzf(obj, j);
                    byte[] bArr6 = zzym.zzb;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i4 = i + iFloatToIntBits;
                    break;
                case 17:
                    i2 = i4 * 53;
                    Object objZzn2 = zzaak.zzn(obj, j);
                    if (objZzn2 != null) {
                        iHashCode2 = objZzn2.hashCode();
                    }
                    i4 = i2 + iHashCode2;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 50:
                    i = i4 * 53;
                    iFloatToIntBits = zzaak.zzn(obj, j).hashCode();
                    i4 = i + iFloatToIntBits;
                    break;
                case 51:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzt(obj, j));
                        byte[] bArr7 = zzym.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 52:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzu(obj, j));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 53:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzw(obj, j);
                        byte[] bArr8 = zzym.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 54:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzw(obj, j);
                        byte[] bArr9 = zzym.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 55:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzv(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 56:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzw(obj, j);
                        byte[] bArr10 = zzym.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 57:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzv(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 58:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzym.zza(zzx(obj, j));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 59:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = ((String) zzaak.zzn(obj, j)).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 60:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzaak.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 61:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzaak.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 62:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzv(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 63:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzv(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 64:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzv(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 65:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzw(obj, j);
                        byte[] bArr11 = zzym.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 66:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzv(obj, j);
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 67:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        jDoubleToLongBits = zzw(obj, j);
                        byte[] bArr12 = zzym.zzb;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i4 = i + iFloatToIntBits;
                    }
                    break;
                case 68:
                    if (zzC(obj, i6, i3)) {
                        i = i4 * 53;
                        iFloatToIntBits = zzaak.zzn(obj, j).hashCode();
                        i4 = i + iFloatToIntBits;
                    }
                    break;
            }
            i3 += 3;
        }
    }

    @Override // com.google.android.gms.internal.cast.zzzs
    public final void zzd(Object obj, Object obj2) {
        if (!zzs(obj)) {
            g$$ExternalSyntheticBUOutline1.m207m("Mutating immutable message: ".concat(String.valueOf(obj)));
            return;
        }
        obj2.getClass();
        int i = 0;
        while (true) {
            int[] iArr = this.zzc;
            if (i >= iArr.length) {
                zzzu.zzE(this.zzi, obj, obj2);
                if (this.zzf) {
                    zzzu.zzD(this.zzj, obj, obj2);
                    return;
                }
                return;
            }
            int iZzp = zzp(i);
            int i2 = 1048575 & iZzp;
            int iZzr = zzr(iZzp);
            int i3 = iArr[i];
            long j = i2;
            switch (iZzr) {
                case 0:
                    if (zzA(obj2, i)) {
                        zzaak.zzm(obj, j, zzaak.zzl(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 1:
                    if (zzA(obj2, i)) {
                        zzaak.zzk(obj, j, zzaak.zzj(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 2:
                    if (zzA(obj2, i)) {
                        zzaak.zzg(obj, j, zzaak.zzf(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 3:
                    if (zzA(obj2, i)) {
                        zzaak.zzg(obj, j, zzaak.zzf(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 4:
                    if (zzA(obj2, i)) {
                        zzaak.zze(obj, j, zzaak.zzd(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 5:
                    if (zzA(obj2, i)) {
                        zzaak.zzg(obj, j, zzaak.zzf(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 6:
                    if (zzA(obj2, i)) {
                        zzaak.zze(obj, j, zzaak.zzd(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 7:
                    if (zzA(obj2, i)) {
                        zzaak.zzi(obj, j, zzaak.zzh(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 8:
                    if (zzA(obj2, i)) {
                        zzaak.zzo(obj, j, zzaak.zzn(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 9:
                    zzk(obj, obj2, i);
                    break;
                case 10:
                    if (zzA(obj2, i)) {
                        zzaak.zzo(obj, j, zzaak.zzn(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 11:
                    if (zzA(obj2, i)) {
                        zzaak.zze(obj, j, zzaak.zzd(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 12:
                    if (zzA(obj2, i)) {
                        zzaak.zze(obj, j, zzaak.zzd(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 13:
                    if (zzA(obj2, i)) {
                        zzaak.zze(obj, j, zzaak.zzd(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 14:
                    if (zzA(obj2, i)) {
                        zzaak.zzg(obj, j, zzaak.zzf(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 15:
                    if (zzA(obj2, i)) {
                        zzaak.zze(obj, j, zzaak.zzd(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 16:
                    if (zzA(obj2, i)) {
                        zzaak.zzg(obj, j, zzaak.zzf(obj2, j));
                        zzB(obj, i);
                    }
                    break;
                case 17:
                    zzk(obj, obj2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zzyl zzylVarZzf = (zzyl) zzaak.zzn(obj, j);
                    zzyl zzylVar = (zzyl) zzaak.zzn(obj2, j);
                    int size = zzylVarZzf.size();
                    int size2 = zzylVar.size();
                    if (size > 0 && size2 > 0) {
                        if (!zzylVarZzf.zza()) {
                            zzylVarZzf = zzylVarZzf.zzf(size2 + size);
                        }
                        zzylVarZzf.addAll(zzylVar);
                    }
                    if (size > 0) {
                        zzylVar = zzylVarZzf;
                    }
                    zzaak.zzo(obj, j, zzylVar);
                    break;
                case 50:
                    int i4 = zzzu.$r8$clinit;
                    Object objZzn = zzaak.zzn(obj, j);
                    Object objZzn2 = zzaak.zzn(obj2, j);
                    MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(objZzn);
                    MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(objZzn2);
                    throw null;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzC(obj2, i3, i)) {
                        zzaak.zzo(obj, j, zzaak.zzn(obj2, j));
                        zzD(obj, i3, i);
                    }
                    break;
                case 60:
                    zzl(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzC(obj2, i3, i)) {
                        zzaak.zzo(obj, j, zzaak.zzn(obj2, j));
                        zzD(obj, i3, i);
                    }
                    break;
                case 68:
                    zzl(obj, obj2, i);
                    break;
            }
            i += 3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:499:0x0453  */
    @Override // com.google.android.gms.internal.cast.zzzs
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zze(java.lang.Object r18) {
        /*
            Method dump skipped, instruction units count: 1922
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzzl.zze(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.cast.zzzs
    public final void zzf(Object obj, zzaar zzaarVar) throws Throwable {
        Throwable th;
        int i;
        zzzl<T> zzzlVar = this;
        if (zzzlVar.zzf) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(obj);
            throw null;
        }
        int[] iArr = zzzlVar.zzc;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i4 < iArr.length) {
            int iZzp = zzzlVar.zzp(i4);
            int iZzr = zzr(iZzp);
            int i6 = iArr[i4];
            if (iZzr <= 17) {
                int i7 = iArr[i4 + 2];
                th = null;
                int i8 = i7 & i2;
                if (i8 != i3) {
                    i5 = i8 == i2 ? 0 : unsafe.getInt(obj, i8);
                    i3 = i8;
                }
                i = 1 << (i7 >>> 20);
            } else {
                th = null;
                i = 0;
            }
            long j = iZzp & i2;
            switch (iZzr) {
                case 0:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzf(i6, zzaak.zzl(obj, j));
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 1:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zze(i6, zzaak.zzj(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 2:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzc(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 3:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzh(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 4:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzi(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 5:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzj(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 6:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzk(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 7:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzl(i6, zzaak.zzh(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 8:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzE(i6, unsafe.getObject(obj, j), zzaarVar);
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 9:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzr(i6, unsafe.getObject(obj, j), zzzlVar.zzm(i4));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 10:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzn(i6, (zzxk) unsafe.getObject(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 11:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzo(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 12:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzg(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 13:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzb(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 14:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzd(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 15:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzp(i6, unsafe.getInt(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 16:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzq(i6, unsafe.getLong(obj, j));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 17:
                    if (zzzlVar.zzz(obj, i4, i3, i5, i)) {
                        zzaarVar.zzs(i6, unsafe.getObject(obj, j), zzzlVar.zzm(i4));
                    } else {
                        continue;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 18:
                    zzzu.zza(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 19:
                    zzzu.zzb(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 20:
                    zzzu.zzc(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 21:
                    zzzu.zzd(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 22:
                    zzzu.zzh(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 23:
                    zzzu.zzf(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 24:
                    zzzu.zzk(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 25:
                    zzzu.zzn(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 26:
                    int i9 = iArr[i4];
                    List list = (List) unsafe.getObject(obj, j);
                    int i10 = zzzu.$r8$clinit;
                    if (list != null && !list.isEmpty()) {
                        zzaarVar.zzD(i9, list);
                    }
                    break;
                case 27:
                    int i11 = iArr[i4];
                    List list2 = (List) unsafe.getObject(obj, j);
                    zzzs zzzsVarZzm = zzzlVar.zzm(i4);
                    int i12 = zzzu.$r8$clinit;
                    if (list2 != null && !list2.isEmpty()) {
                        for (int i13 = 0; i13 < list2.size(); i13++) {
                            ((zzxq) zzaarVar).zzr(i11, list2.get(i13), zzzsVarZzm);
                        }
                    }
                    break;
                case 28:
                    int i14 = iArr[i4];
                    List list3 = (List) unsafe.getObject(obj, j);
                    int i15 = zzzu.$r8$clinit;
                    if (list3 != null && !list3.isEmpty()) {
                        zzaarVar.zzE(i14, list3);
                    }
                    break;
                case 29:
                    zzzu.zzi(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 30:
                    zzzu.zzm(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 31:
                    zzzu.zzl(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 32:
                    zzzu.zzg(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 33:
                    zzzu.zzj(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 34:
                    zzzu.zze(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, false);
                    continue;
                    i4 += 3;
                    i2 = 1048575;
                    zzzlVar = this;
                    break;
                case 35:
                    zzzu.zza(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 36:
                    zzzu.zzb(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 37:
                    zzzu.zzc(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 38:
                    zzzu.zzd(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 39:
                    zzzu.zzh(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 40:
                    zzzu.zzf(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 41:
                    zzzu.zzk(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 42:
                    zzzu.zzn(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 43:
                    zzzu.zzi(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 44:
                    zzzu.zzm(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 45:
                    zzzu.zzl(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 46:
                    zzzu.zzg(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 47:
                    zzzu.zzj(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 48:
                    zzzu.zze(iArr[i4], (List) unsafe.getObject(obj, j), zzaarVar, true);
                    break;
                case 49:
                    int i16 = iArr[i4];
                    List list4 = (List) unsafe.getObject(obj, j);
                    zzzs zzzsVarZzm2 = zzzlVar.zzm(i4);
                    int i17 = zzzu.$r8$clinit;
                    if (list4 != null && !list4.isEmpty()) {
                        for (int i18 = 0; i18 < list4.size(); i18++) {
                            ((zzxq) zzaarVar).zzs(i16, list4.get(i18), zzzsVarZzm2);
                        }
                    }
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(zzzlVar.zzn(i4));
                        throw th;
                    }
                    break;
                case 51:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzf(i6, zzt(obj, j));
                    }
                    break;
                case 52:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zze(i6, zzu(obj, j));
                    }
                    break;
                case 53:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzc(i6, zzw(obj, j));
                    }
                    break;
                case 54:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzh(i6, zzw(obj, j));
                    }
                    break;
                case 55:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzi(i6, zzv(obj, j));
                    }
                    break;
                case 56:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzj(i6, zzw(obj, j));
                    }
                    break;
                case 57:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzk(i6, zzv(obj, j));
                    }
                    break;
                case 58:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzl(i6, zzx(obj, j));
                    }
                    break;
                case 59:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzE(i6, unsafe.getObject(obj, j), zzaarVar);
                    }
                    break;
                case 60:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzr(i6, unsafe.getObject(obj, j), zzzlVar.zzm(i4));
                    }
                    break;
                case 61:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzn(i6, (zzxk) unsafe.getObject(obj, j));
                    }
                    break;
                case 62:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzo(i6, zzv(obj, j));
                    }
                    break;
                case 63:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzg(i6, zzv(obj, j));
                    }
                    break;
                case 64:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzb(i6, zzv(obj, j));
                    }
                    break;
                case 65:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzd(i6, zzw(obj, j));
                    }
                    break;
                case 66:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzp(i6, zzv(obj, j));
                    }
                    break;
                case 67:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzq(i6, zzw(obj, j));
                    }
                    break;
                case 68:
                    if (zzzlVar.zzC(obj, i6, i4)) {
                        zzaarVar.zzs(i6, unsafe.getObject(obj, j), zzzlVar.zzm(i4));
                    }
                    break;
            }
            i4 += 3;
            i2 = 1048575;
            zzzlVar = this;
        }
        zzaae zzaaeVar = ((zzyd) obj).zzc;
    }

    /* JADX WARN: Code restructure failed: missing block: B:90:0x007e, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:75:0x006b  */
    @Override // com.google.android.gms.internal.cast.zzzs
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzg(java.lang.Object r7) {
        /*
            Method dump skipped, instruction units count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzzl.zzg(java.lang.Object):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0082  */
    @Override // com.google.android.gms.internal.cast.zzzs
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzh(java.lang.Object r16) {
        /*
            Method dump skipped, instruction units count: 203
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzzl.zzh(java.lang.Object):boolean");
    }
}
