package com.google.android.gms.internal.cast;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes4.dex */
final class zzva implements zzvi {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzwj.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final zzux zze;
    private final boolean zzf;
    private final int[] zzg;
    private final int zzh;
    private final zzul zzi;
    private final zzvz zzj;
    private final zztf zzk;
    private final zzvc zzl;
    private final zzus zzm;

    private zzva(int[] iArr, Object[] objArr, int i, int i2, zzux zzuxVar, int i3, boolean z, int[] iArr2, int i4, int i5, zzvc zzvcVar, zzul zzulVar, zzvz zzvzVar, zztf zztfVar, zzus zzusVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        boolean z2 = false;
        if (zztfVar != null && zztfVar.zzc(zzuxVar)) {
            z2 = true;
        }
        this.zzf = z2;
        this.zzg = iArr2;
        this.zzh = i4;
        this.zzl = zzvcVar;
        this.zzi = zzulVar;
        this.zzj = zzvzVar;
        this.zzk = zztfVar;
        this.zze = zzuxVar;
        this.zzm = zzusVar;
    }

    private static boolean zzA(Object obj, int i, zzvi zzviVar) {
        return zzviVar.zzh(zzwj.zzf(obj, i & 1048575));
    }

    private static boolean zzB(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zztp) {
            return ((zztp) obj).zzK();
        }
        return true;
    }

    private final boolean zzC(Object obj, int i, int i2) {
        return zzwj.zzc(obj, (long) (zzm(i2) & 1048575)) == i;
    }

    private static boolean zzD(Object obj, long j) {
        return ((Boolean) zzwj.zzf(obj, j)).booleanValue();
    }

    private static final void zzE(int i, Object obj, zzwq zzwqVar) {
        if (obj instanceof String) {
            zzwqVar.zzD(i, (String) obj);
        } else {
            zzwqVar.zzd(i, (zzsu) obj);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x03a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.google.android.gms.internal.cast.zzva zzi(java.lang.Class r32, com.google.android.gms.internal.cast.zzuu r33, com.google.android.gms.internal.cast.zzvc r34, com.google.android.gms.internal.cast.zzul r35, com.google.android.gms.internal.cast.zzvz r36, com.google.android.gms.internal.cast.zztf r37, com.google.android.gms.internal.cast.zzus r38) {
        /*
            Method dump skipped, instruction units count: 1050
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zzi(java.lang.Class, com.google.android.gms.internal.cast.zzuu, com.google.android.gms.internal.cast.zzvc, com.google.android.gms.internal.cast.zzul, com.google.android.gms.internal.cast.zzvz, com.google.android.gms.internal.cast.zztf, com.google.android.gms.internal.cast.zzus):com.google.android.gms.internal.cast.zzva");
    }

    private static double zzj(Object obj, long j) {
        return ((Double) zzwj.zzf(obj, j)).doubleValue();
    }

    private static float zzk(Object obj, long j) {
        return ((Float) zzwj.zzf(obj, j)).floatValue();
    }

    private static int zzl(Object obj, long j) {
        return ((Integer) zzwj.zzf(obj, j)).intValue();
    }

    private final int zzm(int i) {
        return this.zzc[i + 2];
    }

    private static int zzn(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzo(int i) {
        return this.zzc[i + 1];
    }

    private static long zzp(Object obj, long j) {
        return ((Long) zzwj.zzf(obj, j)).longValue();
    }

    private final zzvi zzq(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzvi zzviVar = (zzvi) objArr[i3];
        if (zzviVar != null) {
            return zzviVar;
        }
        zzvi zzviVarZzb = zzvf.zza().zzb((Class) objArr[i3 + 1]);
        this.zzd[i3] = zzviVarZzb;
        return zzviVarZzb;
    }

    private final Object zzr(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static Field zzs(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private final void zzt(Object obj, Object obj2, int i) {
        if (zzy(obj2, i)) {
            int iZzo = zzo(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzo;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzvi zzviVarZzq = zzq(i);
            if (!zzy(obj, i)) {
                if (zzB(object)) {
                    Object objZzc = zzviVarZzq.zzc();
                    zzviVarZzq.zze(objZzc, object);
                    unsafe.putObject(obj, j, objZzc);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzv(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzB(object2)) {
                Object objZzc2 = zzviVarZzq.zzc();
                zzviVarZzq.zze(objZzc2, object2);
                unsafe.putObject(obj, j, objZzc2);
                object2 = objZzc2;
            }
            zzviVarZzq.zze(object2, object);
        }
    }

    private final void zzu(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzC(obj2, i2, i)) {
            int iZzo = zzo(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzo;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzvi zzviVarZzq = zzq(i);
            if (!zzC(obj, i2, i)) {
                if (zzB(object)) {
                    Object objZzc = zzviVarZzq.zzc();
                    zzviVarZzq.zze(objZzc, object);
                    unsafe.putObject(obj, j, objZzc);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzw(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzB(object2)) {
                Object objZzc2 = zzviVarZzq.zzc();
                zzviVarZzq.zze(objZzc2, object2);
                unsafe.putObject(obj, j, objZzc2);
                object2 = objZzc2;
            }
            zzviVarZzq.zze(object2, object);
        }
    }

    private final void zzv(Object obj, int i) {
        int iZzm = zzm(i);
        long j = 1048575 & iZzm;
        if (j == 1048575) {
            return;
        }
        zzwj.zzq(obj, j, (1 << (iZzm >>> 20)) | zzwj.zzc(obj, j));
    }

    private final void zzw(Object obj, int i, int i2) {
        zzwj.zzq(obj, zzm(i2) & 1048575, i);
    }

    private final boolean zzx(Object obj, Object obj2, int i) {
        return zzy(obj, i) == zzy(obj2, i);
    }

    private final boolean zzy(Object obj, int i) {
        int iZzm = zzm(i);
        long j = iZzm & 1048575;
        if (j != 1048575) {
            return (zzwj.zzc(obj, j) & (1 << (iZzm >>> 20))) != 0;
        }
        int iZzo = zzo(i);
        long j2 = iZzo & 1048575;
        switch (zzn(iZzo)) {
            case 0:
                return Double.doubleToRawLongBits(zzwj.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzwj.zzb(obj, j2)) != 0;
            case 2:
                return zzwj.zzd(obj, j2) != 0;
            case 3:
                return zzwj.zzd(obj, j2) != 0;
            case 4:
                return zzwj.zzc(obj, j2) != 0;
            case 5:
                return zzwj.zzd(obj, j2) != 0;
            case 6:
                return zzwj.zzc(obj, j2) != 0;
            case 7:
                return zzwj.zzw(obj, j2);
            case 8:
                Object objZzf = zzwj.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzsu) {
                    return !zzsu.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzwj.zzf(obj, j2) != null;
            case 10:
                return !zzsu.zzb.equals(zzwj.zzf(obj, j2));
            case 11:
                return zzwj.zzc(obj, j2) != 0;
            case 12:
                return zzwj.zzc(obj, j2) != 0;
            case 13:
                return zzwj.zzc(obj, j2) != 0;
            case 14:
                return zzwj.zzd(obj, j2) != 0;
            case 15:
                return zzwj.zzc(obj, j2) != 0;
            case 16:
                return zzwj.zzd(obj, j2) != 0;
            case 17:
                return zzwj.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzz(Object obj, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzy(obj, i) : (i3 & i4) != 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0455  */
    @Override // com.google.android.gms.internal.cast.zzvi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zza(java.lang.Object r19) {
        /*
            Method dump skipped, instruction units count: 1958
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zza(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final int zzb(Object obj) {
        int i;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int i2;
        int i3 = 0;
        for (int i4 = 0; i4 < this.zzc.length; i4 += 3) {
            int iZzo = zzo(i4);
            int[] iArr = this.zzc;
            int i5 = 1048575 & iZzo;
            int iZzn = zzn(iZzo);
            int i6 = iArr[i4];
            long j = i5;
            int iHashCode = 37;
            switch (iZzn) {
                case 0:
                    i = i3 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzwj.zza(obj, j));
                    byte[] bArr = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 1:
                    i = i3 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzwj.zzb(obj, j));
                    i3 = i + iFloatToIntBits;
                    break;
                case 2:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr2 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 3:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr3 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 4:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 5:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr4 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 6:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 7:
                    i = i3 * 53;
                    iFloatToIntBits = zzty.zza(zzwj.zzw(obj, j));
                    i3 = i + iFloatToIntBits;
                    break;
                case 8:
                    i = i3 * 53;
                    iFloatToIntBits = ((String) zzwj.zzf(obj, j)).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 9:
                    i2 = i3 * 53;
                    Object objZzf = zzwj.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i3 = i2 + iHashCode;
                    break;
                case 10:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 11:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 12:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 13:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 14:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr5 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 15:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 16:
                    i = i3 * 53;
                    jDoubleToLongBits = zzwj.zzd(obj, j);
                    byte[] bArr6 = zzty.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 17:
                    i2 = i3 * 53;
                    Object objZzf2 = zzwj.zzf(obj, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i3 = i2 + iHashCode;
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
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 50:
                    i = i3 * 53;
                    iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 51:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzj(obj, j));
                        byte[] bArr7 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 52:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzk(obj, j));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 53:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr8 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 54:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr9 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 55:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 56:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr10 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 57:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 58:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzty.zza(zzD(obj, j));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 59:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = ((String) zzwj.zzf(obj, j)).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 60:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 61:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 62:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 63:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 64:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 65:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr11 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 66:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzl(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 67:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j);
                        byte[] bArr12 = zzty.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 68:
                    if (zzC(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzwj.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
            }
        }
        int iHashCode2 = (i3 * 53) + this.zzj.zzc(obj).hashCode();
        if (!this.zzf) {
            return iHashCode2;
        }
        this.zzk.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final Object zzc() {
        return ((zztp) this.zze).zzx();
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x007c, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0069  */
    @Override // com.google.android.gms.internal.cast.zzvi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzd(java.lang.Object r7) {
        /*
            Method dump skipped, instruction units count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zzd(java.lang.Object):void");
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final void zze(Object obj, Object obj2) {
        if (!zzB(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(obj)));
        }
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzo = zzo(i);
            int i2 = 1048575 & iZzo;
            int[] iArr = this.zzc;
            int iZzn = zzn(iZzo);
            int i3 = iArr[i];
            long j = i2;
            switch (iZzn) {
                case 0:
                    if (zzy(obj2, i)) {
                        zzwj.zzo(obj, j, zzwj.zza(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 1:
                    if (zzy(obj2, i)) {
                        zzwj.zzp(obj, j, zzwj.zzb(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 2:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 3:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 4:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 5:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 6:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 7:
                    if (zzy(obj2, i)) {
                        zzwj.zzm(obj, j, zzwj.zzw(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 8:
                    if (zzy(obj2, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 9:
                    zzt(obj, obj2, i);
                    break;
                case 10:
                    if (zzy(obj2, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 11:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 12:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 13:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 14:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 15:
                    if (zzy(obj2, i)) {
                        zzwj.zzq(obj, j, zzwj.zzc(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 16:
                    if (zzy(obj2, i)) {
                        zzwj.zzr(obj, j, zzwj.zzd(obj2, j));
                        zzv(obj, i);
                    }
                    break;
                case 17:
                    zzt(obj, obj2, i);
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
                    this.zzi.zzb(obj, obj2, j);
                    break;
                case 50:
                    int i4 = zzvk.$r8$clinit;
                    Object objZzf = zzwj.zzf(obj, j);
                    Object objZzf2 = zzwj.zzf(obj2, j);
                    WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(objZzf);
                    WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(objZzf2);
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
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzw(obj, i3, i);
                    }
                    break;
                case 60:
                    zzu(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzC(obj2, i3, i)) {
                        zzwj.zzs(obj, j, zzwj.zzf(obj2, j));
                        zzw(obj, i3, i);
                    }
                    break;
                case 68:
                    zzu(obj, obj2, i);
                    break;
            }
        }
        zzvk.zzo(this.zzj, obj, obj2);
        if (this.zzf) {
            this.zzk.zza(obj2);
            throw null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.cast.zzvi
    public final void zzf(Object obj, zzwq zzwqVar) throws zzta {
        int i;
        zzva zzvaVar = this;
        if (zzvaVar.zzf) {
            zzvaVar.zzk.zza(obj);
            throw null;
        }
        int[] iArr = zzvaVar.zzc;
        Unsafe unsafe = zzb;
        int i2 = 1048575;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i4 < iArr.length) {
            int iZzo = zzvaVar.zzo(i4);
            int[] iArr2 = zzvaVar.zzc;
            int iZzn = zzn(iZzo);
            int i6 = iArr2[i4];
            if (iZzn <= 17) {
                int i7 = iArr2[i4 + 2];
                int i8 = i7 & i2;
                if (i8 != i3) {
                    i5 = i8 == i2 ? 0 : unsafe.getInt(obj, i8);
                    i3 = i8;
                }
                i = 1 << (i7 >>> 20);
            } else {
                i = 0;
            }
            long j = iZzo & i2;
            switch (iZzn) {
                case 0:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzf(i6, zzwj.zza(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 1:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzn(i6, zzwj.zzb(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 2:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzs(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 3:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzH(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 4:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzq(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 5:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzl(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 6:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzj(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 7:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzb(i6, zzwj.zzw(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 8:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzE(i6, unsafe.getObject(obj, j), zzwqVar);
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 9:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzu(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i4));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 10:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzd(i6, (zzsu) unsafe.getObject(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 11:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzF(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 12:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzh(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 13:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzv(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 14:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzx(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 15:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzz(i6, unsafe.getInt(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 16:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzB(i6, unsafe.getLong(obj, j));
                    }
                    zzvaVar = this;
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 17:
                    if (zzvaVar.zzz(obj, i4, i3, i5, i)) {
                        zzwqVar.zzp(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i4));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 18:
                    zzvk.zzr(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 19:
                    zzvk.zzv(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 20:
                    zzvk.zzx(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 21:
                    zzvk.zzD(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 22:
                    zzvk.zzw(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 23:
                    zzvk.zzu(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 24:
                    zzvk.zzt(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 25:
                    zzvk.zzq(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 26:
                    int i9 = zzvaVar.zzc[i4];
                    List list = (List) unsafe.getObject(obj, j);
                    int i10 = zzvk.$r8$clinit;
                    if (list != null && !list.isEmpty()) {
                        zzwqVar.zzE(i9, list);
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 27:
                    int i11 = zzvaVar.zzc[i4];
                    List list2 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq = zzvaVar.zzq(i4);
                    int i12 = zzvk.$r8$clinit;
                    if (list2 != null && !list2.isEmpty()) {
                        for (int i13 = 0; i13 < list2.size(); i13++) {
                            ((zztd) zzwqVar).zzu(i11, list2.get(i13), zzviVarZzq);
                        }
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 28:
                    int i14 = zzvaVar.zzc[i4];
                    List list3 = (List) unsafe.getObject(obj, j);
                    int i15 = zzvk.$r8$clinit;
                    if (list3 != null && !list3.isEmpty()) {
                        zzwqVar.zze(i14, list3);
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 29:
                    zzvk.zzC(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 30:
                    zzvk.zzs(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 31:
                    zzvk.zzy(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 32:
                    zzvk.zzz(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 33:
                    zzvk.zzA(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 34:
                    zzvk.zzB(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, false);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 35:
                    zzvk.zzr(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 36:
                    zzvk.zzv(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 37:
                    zzvk.zzx(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 38:
                    zzvk.zzD(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 39:
                    zzvk.zzw(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 40:
                    zzvk.zzu(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 41:
                    zzvk.zzt(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 42:
                    zzvk.zzq(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 43:
                    zzvk.zzC(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 44:
                    zzvk.zzs(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 45:
                    zzvk.zzy(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 46:
                    zzvk.zzz(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 47:
                    zzvk.zzA(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 48:
                    zzvk.zzB(zzvaVar.zzc[i4], (List) unsafe.getObject(obj, j), zzwqVar, true);
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 49:
                    int i16 = zzvaVar.zzc[i4];
                    List list4 = (List) unsafe.getObject(obj, j);
                    zzvi zzviVarZzq2 = zzvaVar.zzq(i4);
                    int i17 = zzvk.$r8$clinit;
                    if (list4 != null && !list4.isEmpty()) {
                        for (int i18 = 0; i18 < list4.size(); i18++) {
                            ((zztd) zzwqVar).zzp(i16, list4.get(i18), zzviVarZzq2);
                        }
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zzvaVar.zzr(i4));
                        throw null;
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 51:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzf(i6, zzj(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 52:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzn(i6, zzk(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 53:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzs(i6, zzp(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 54:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzH(i6, zzp(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 55:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzq(i6, zzl(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 56:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzl(i6, zzp(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 57:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzj(i6, zzl(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 58:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzb(i6, zzD(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 59:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzE(i6, unsafe.getObject(obj, j), zzwqVar);
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 60:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzu(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i4));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 61:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzd(i6, (zzsu) unsafe.getObject(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 62:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzF(i6, zzl(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 63:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzh(i6, zzl(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 64:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzv(i6, zzl(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 65:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzx(i6, zzp(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 66:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzz(i6, zzl(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 67:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzB(i6, zzp(obj, j));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                case 68:
                    if (zzvaVar.zzC(obj, i6, i4)) {
                        zzwqVar.zzp(i6, unsafe.getObject(obj, j), zzvaVar.zzq(i4));
                    }
                    i4 += 3;
                    i2 = 1048575;
                    break;
                default:
                    i4 += 3;
                    i2 = 1048575;
                    break;
            }
        }
        zzvz zzvzVar = zzvaVar.zzj;
        zzvzVar.zzg(zzvzVar.zzc(obj), zzwqVar);
    }

    @Override // com.google.android.gms.internal.cast.zzvi
    public final boolean zzg(Object obj, Object obj2) {
        boolean zZzE;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzo = zzo(i);
            long j = iZzo & 1048575;
            switch (zzn(iZzo)) {
                case 0:
                    if (!zzx(obj, obj2, i) || Double.doubleToLongBits(zzwj.zza(obj, j)) != Double.doubleToLongBits(zzwj.zza(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzx(obj, obj2, i) || Float.floatToIntBits(zzwj.zzb(obj, j)) != Float.floatToIntBits(zzwj.zzb(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzx(obj, obj2, i) || zzwj.zzw(obj, j) != zzwj.zzw(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzx(obj, obj2, i) || zzwj.zzc(obj, j) != zzwj.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzx(obj, obj2, i) || zzwj.zzd(obj, j) != zzwj.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzx(obj, obj2, i) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
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
                    zZzE = zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j));
                    break;
                case 50:
                    zZzE = zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j));
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
                    long jZzm = zzm(i) & 1048575;
                    if (zzwj.zzc(obj, jZzm) != zzwj.zzc(obj2, jZzm) || !zzvk.zzE(zzwj.zzf(obj, j), zzwj.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    break;
            }
            if (!zZzE) {
                return false;
            }
        }
        if (!this.zzj.zzc(obj).equals(this.zzj.zzc(obj2))) {
            return false;
        }
        if (!this.zzf) {
            return true;
        }
        this.zzk.zza(obj);
        this.zzk.zza(obj2);
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0085  */
    @Override // com.google.android.gms.internal.cast.zzvi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzh(java.lang.Object r16) {
        /*
            Method dump skipped, instruction units count: 208
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzva.zzh(java.lang.Object):boolean");
    }
}
