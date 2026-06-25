package com.google.android.gms.internal.clearcut;

import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.android.gms.internal.clearcut.zzcg;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.HttpUrl$$ExternalSyntheticBUOutline0;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline0;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes4.dex */
final class zzds<T> implements zzef<T> {
    private static final Unsafe zzmh = zzfd.zzef();
    private final int[] zzmi;
    private final Object[] zzmj;
    private final int zzmk;
    private final int zzml;
    private final int zzmm;
    private final zzdo zzmn;
    private final boolean zzmo;
    private final boolean zzmp;
    private final boolean zzmq;
    private final boolean zzmr;
    private final int[] zzms;
    private final int[] zzmt;
    private final int[] zzmu;
    private final zzdw zzmv;
    private final zzcy zzmw;
    private final zzex<?, ?> zzmx;
    private final zzbu<?> zzmy;
    private final zzdj zzmz;

    private zzds(int[] iArr, Object[] objArr, int i, int i2, int i3, zzdo zzdoVar, boolean z, boolean z2, int[] iArr2, int[] iArr3, int[] iArr4, zzdw zzdwVar, zzcy zzcyVar, zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdj zzdjVar) {
        this.zzmi = iArr;
        this.zzmj = objArr;
        this.zzmk = i;
        this.zzml = i2;
        this.zzmm = i3;
        this.zzmp = zzdoVar instanceof zzcg;
        this.zzmq = z;
        this.zzmo = zzbuVar != null && zzbuVar.zze(zzdoVar);
        this.zzmr = false;
        this.zzms = iArr2;
        this.zzmt = iArr3;
        this.zzmu = iArr4;
        this.zzmv = zzdwVar;
        this.zzmw = zzcyVar;
        this.zzmx = zzexVar;
        this.zzmy = zzbuVar;
        this.zzmn = zzdoVar;
        this.zzmz = zzdjVar;
    }

    private static int zza(int i, byte[] bArr, int i2, int i3, Object obj, zzay zzayVar) {
        return zzax.zza(i, bArr, i2, i3, zzn(obj), zzayVar);
    }

    private static int zza(zzef<?> zzefVar, int i, byte[] bArr, int i2, int i3, zzcn<?> zzcnVar, zzay zzayVar) throws zzco {
        int iZza = zza((zzef) zzefVar, bArr, i2, i3, zzayVar);
        while (true) {
            zzcnVar.add(zzayVar.zzff);
            if (iZza >= i3) {
                break;
            }
            int iZza2 = zzax.zza(bArr, iZza, zzayVar);
            if (i != zzayVar.zzfd) {
                break;
            }
            iZza = zza((zzef) zzefVar, bArr, iZza2, i3, zzayVar);
        }
        return iZza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, int i3, zzay zzayVar) throws zzco {
        zzds zzdsVar = (zzds) zzefVar;
        Object objNewInstance = zzdsVar.newInstance();
        int iZza = zzdsVar.zza(objNewInstance, bArr, i, i2, i3, zzayVar);
        zzdsVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return iZza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int zza(zzef zzefVar, byte[] bArr, int i, int i2, zzay zzayVar) throws zzco {
        int iZza = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZza = zzax.zza(i3, bArr, iZza, zzayVar);
            i3 = zzayVar.zzfd;
        }
        int i4 = iZza;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzco.zzbl();
        }
        Object objNewInstance = zzefVar.newInstance();
        int i5 = i4 + i3;
        zzefVar.zza(objNewInstance, bArr, i4, i5, zzayVar);
        zzefVar.zzc(objNewInstance);
        zzayVar.zzff = objNewInstance;
        return i5;
    }

    private static <UT, UB> int zza(zzex<UT, UB> zzexVar, T t) {
        return zzexVar.zzm(zzexVar.zzq(t));
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzay zzayVar) throws zzco {
        int i9;
        Object objValueOf;
        int i10;
        Object objValueOf2;
        int iZzb;
        long jZza;
        int iZzm;
        Object objValueOf3;
        Object object;
        Unsafe unsafe = zzmh;
        long j2 = this.zzmi[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                objValueOf = Double.valueOf(zzax.zze(bArr, i));
                unsafe.putObject(t, j, objValueOf);
                iZzb = i9 + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 52:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                objValueOf2 = Float.valueOf(zzax.zzf(bArr, i));
                unsafe.putObject(t, j, objValueOf2);
                iZzb = i10 + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzayVar.zzfe;
                objValueOf3 = Long.valueOf(jZza);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzayVar.zzfd;
                objValueOf3 = Integer.valueOf(iZzm);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 56:
            case 65:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                objValueOf = Long.valueOf(zzax.zzd(bArr, i));
                unsafe.putObject(t, j, objValueOf);
                iZzb = i9 + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 57:
            case 64:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                objValueOf2 = Integer.valueOf(zzax.zzc(bArr, i));
                unsafe.putObject(t, j, objValueOf2);
                iZzb = i10 + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                objValueOf3 = Boolean.valueOf(zzayVar.zzfe != 0);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzax.zza(bArr, i, zzayVar);
                int i11 = zzayVar.zzfd;
                if (i11 == 0) {
                    unsafe.putObject(t, j, _UrlKt.FRAGMENT_ENCODE_SET);
                } else {
                    if ((i6 & 536870912) != 0 && !zzff.zze(bArr, iZza, iZza + i11)) {
                        throw zzco.zzbp();
                    }
                    unsafe.putObject(t, j, new String(bArr, iZza, i11, zzci.UTF_8));
                    iZza += i11;
                }
                unsafe.putInt(t, j2, i4);
                return iZza;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZza2 = zza(zzad(i8), bArr, i, i2, zzayVar);
                object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                Object objZza = zzayVar.zzff;
                if (object != null) {
                    objZza = zzci.zza(object, objZza);
                }
                unsafe.putObject(t, j, objZza);
                unsafe.putInt(t, j2, i4);
                return iZza2;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                int iZza3 = zzax.zza(bArr, i, zzayVar);
                int i12 = zzayVar.zzfd;
                if (i12 == 0) {
                    unsafe.putObject(t, j, zzbb.zzfi);
                } else {
                    unsafe.putObject(t, j, zzbb.zzb(bArr, iZza3, i12));
                    iZza3 += i12;
                }
                unsafe.putInt(t, j2, i4);
                return iZza3;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZza4 = zzax.zza(bArr, i, zzayVar);
                int i13 = zzayVar.zzfd;
                zzck<?> zzckVarZzaf = zzaf(i8);
                if (zzckVarZzaf != null && zzckVarZzaf.zzb(i13) == null) {
                    zzn(t).zzb(i3, Long.valueOf(i13));
                    return iZza4;
                }
                unsafe.putObject(t, j, Integer.valueOf(i13));
                iZzb = iZza4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 66:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zza(bArr, i, zzayVar);
                iZzm = zzbk.zzm(zzayVar.zzfd);
                objValueOf3 = Integer.valueOf(iZzm);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 67:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzax.zzb(bArr, i, zzayVar);
                jZza = zzbk.zza(zzayVar.zzfe);
                objValueOf3 = Long.valueOf(jZza);
                unsafe.putObject(t, j, objValueOf3);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 68:
                if (i5 == 3) {
                    iZzb = zza(zzad(i8), bArr, i, i2, (i3 & (-8)) | 4, zzayVar);
                    object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    objValueOf3 = zzayVar.zzff;
                    if (object != null) {
                        objValueOf3 = zzci.zza(object, objValueOf3);
                    }
                    unsafe.putObject(t, j, objValueOf3);
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
            default:
                return i;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x010f, code lost:
    
        if (r12 == 0) goto L288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x0111, code lost:
    
        r4.add(com.google.android.gms.internal.clearcut.zzbb.zzfi);
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x0117, code lost:
    
        r4.add(com.google.android.gms.internal.clearcut.zzbb.zzb(r11, r9, r12));
        r9 = r9 + r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x011f, code lost:
    
        if (r9 >= r13) goto L433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x0121, code lost:
    
        r12 = com.google.android.gms.internal.clearcut.zzax.zza(r11, r9, r23);
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x0127, code lost:
    
        if (r14 != r23.zzfd) goto L434;
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0129, code lost:
    
        r9 = com.google.android.gms.internal.clearcut.zzax.zza(r11, r12, r23);
        r12 = r23.zzfd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x012f, code lost:
    
        if (r12 != 0) goto L289;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x0132, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x011f, code lost:
    
        r4.add(com.google.android.gms.internal.clearcut.zzbb.zzb(r11, r9, r12));
        r9 = r9 + r12;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x01ad  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:294:0x012f -> B:288:0x0111). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:313:0x0186 -> B:306:0x0167). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:327:0x01bb -> B:318:0x0194). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int zza(T r10, byte[] r11, int r12, int r13, int r14, int r15, int r16, int r17, long r18, int r20, long r21, com.google.android.gms.internal.clearcut.zzay r23) throws com.google.android.gms.internal.clearcut.zzco {
        /*
            Method dump skipped, instruction units count: 808
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, byte[], int, int, int, int, int, int, long, int, long, com.google.android.gms.internal.clearcut.zzay):int");
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, long j, zzay zzayVar) throws zzco {
        Unsafe unsafe = zzmh;
        Object objZzae = zzae(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzmz.zzi(object)) {
            Object objZzk = this.zzmz.zzk(objZzae);
            this.zzmz.zzb(objZzk, object);
            unsafe.putObject(t, j, objZzk);
            object = objZzk;
        }
        this.zzmz.zzl(objZzae);
        this.zzmz.zzg(object);
        int iZza = zzax.zza(bArr, i, zzayVar);
        int i5 = zzayVar.zzfd;
        if (i5 < 0 || i5 > i2 - iZza) {
            throw zzco.zzbl();
        }
        throw null;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:256:0x0067. Please report as an issue. */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, zzay zzayVar) throws zzco {
        zzds<T> zzdsVar;
        int i4;
        Unsafe unsafe;
        int i5;
        int i6;
        T t2;
        int i7;
        int i8;
        int i9;
        zzds<T> zzdsVar2;
        zzck<?> zzckVarZzaf;
        int i10;
        int i11;
        int i12;
        byte[] bArr2;
        Unsafe unsafe2;
        int i13;
        int i14;
        int i15;
        int i16;
        int iZza;
        int i17;
        zzay zzayVar2;
        int i18;
        long j;
        Object objZza;
        zzay zzayVar3;
        int iZzb;
        Unsafe unsafe3;
        int i19;
        zzds<T> zzdsVar3 = this;
        T t3 = t;
        byte[] bArr3 = bArr;
        int i20 = i2;
        zzay zzayVar4 = zzayVar;
        Unsafe unsafe4 = zzmh;
        int i21 = -1;
        int iZza2 = i;
        int i22 = -1;
        int i23 = 0;
        int i24 = 0;
        while (true) {
            if (iZza2 < i20) {
                int iZza3 = iZza2 + 1;
                int i25 = bArr3[iZza2];
                if (i25 < 0) {
                    iZza3 = zzax.zza(i25, bArr3, iZza3, zzayVar4);
                    i25 = zzayVar4.zzfd;
                }
                int i26 = i25;
                iZza2 = iZza3;
                int i27 = (i26 == true ? 1 : 0) >>> 3;
                int i28 = (i26 == true ? 1 : 0) & 7;
                int iZzai = zzdsVar3.zzai(i27);
                if (iZzai != i21) {
                    int[] iArr = zzdsVar3.zzmi;
                    int i29 = iArr[iZzai + 1];
                    int i30 = (i29 & 267386880) >>> 20;
                    long j2 = i29 & 1048575;
                    if (i30 <= 17) {
                        int i31 = iArr[iZzai + 2];
                        int i32 = 1 << (i31 >>> 20);
                        int i33 = i31 & 1048575;
                        i5 = -1;
                        if (i33 != i22) {
                            if (i22 != -1) {
                                unsafe4.putInt(t3, i22, i24);
                            }
                            i22 = i33;
                            i24 = unsafe4.getInt(t3, i33);
                        }
                        switch (i30) {
                            case 0:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i13 = i26 == true ? 1 : 0;
                                i14 = i2;
                                i15 = i22;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 1) {
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    zzfd.zza(t3, j2, zzax.zze(bArr2, i16));
                                    iZza = i16 + 8;
                                    i24 = i11 | i32;
                                    zzayVar4 = zzayVar;
                                    i23 = i13;
                                    i20 = i14;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 1:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i13 = i26 == true ? 1 : 0;
                                i14 = i2;
                                i15 = i22;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 5) {
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    zzfd.zza((Object) t3, j2, zzax.zzf(bArr2, i16));
                                    iZza = i16 + 4;
                                    i24 = i11 | i32;
                                    zzayVar4 = zzayVar;
                                    i23 = i13;
                                    i20 = i14;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 2:
                            case 3:
                                unsafe2 = unsafe4;
                                zzayVar3 = zzayVar;
                                i15 = i22;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 0) {
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZzb = zzax.zzb(bArr, i16, zzayVar3);
                                    T t4 = t3;
                                    unsafe2.putLong(t4, j2, zzayVar3.zzfe);
                                    unsafe3 = unsafe2;
                                    t3 = t4;
                                    i24 = i11 | i32;
                                    i20 = i2;
                                    bArr3 = bArr;
                                    i23 = i26 == true ? 1 : 0;
                                    i22 = i15;
                                    i21 = -1;
                                    unsafe4 = unsafe3;
                                    iZza2 = iZzb;
                                    zzayVar4 = zzayVar3;
                                }
                                break;
                            case 4:
                            case 11:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i17 = i2;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                i16 = iZza2;
                                i11 = i24;
                                if (i28 != 0) {
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = zzax.zza(bArr2, i16, zzayVar2);
                                    unsafe2.putInt(t3, j2, zzayVar2.zzfd);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar2;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 5:
                            case 14:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                if (i28 != 1) {
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    T t5 = t3;
                                    unsafe2.putLong(t5, j2, zzax.zzd(bArr2, i18));
                                    unsafe2 = unsafe2;
                                    t3 = t5;
                                    iZza = i18 + 8;
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar2;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 6:
                            case 13:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                if (i28 != 5) {
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    unsafe2.putInt(t3, j2, zzax.zzc(bArr2, i18));
                                    iZza = i18 + 4;
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar2;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 7:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                if (i28 != 0) {
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = zzax.zzb(bArr2, i18, zzayVar2);
                                    zzfd.zza(t3, j2, zzayVar2.zzfe != 0);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar2;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 8:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                j = j2;
                                if (i28 != 2) {
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = (i29 & 536870912) == 0 ? zzax.zzc(bArr2, i18, zzayVar2) : zzax.zzd(bArr2, i18, zzayVar2);
                                    objZza = zzayVar2.zzff;
                                    unsafe2.putObject(t3, j, objZza);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar2;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 9:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                j = j2;
                                if (i28 != 2) {
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = zza(zzdsVar3.zzad(iZzai), bArr2, i18, i17, zzayVar2);
                                    objZza = (i11 & i32) == 0 ? zzayVar2.zzff : zzci.zza(unsafe2.getObject(t3, j), zzayVar2.zzff);
                                    unsafe2.putObject(t3, j, objZza);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar2;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 10:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                j = j2;
                                if (i28 != 2) {
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = zzax.zze(bArr2, i18, zzayVar2);
                                    objZza = zzayVar2.zzff;
                                    unsafe2.putObject(t3, j, objZza);
                                    i24 = i11 | i32;
                                    i20 = i17;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar2;
                                    i22 = i15;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 12:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                i17 = i2;
                                i11 = i24;
                                zzayVar2 = zzayVar;
                                i15 = i22;
                                if (i28 != 0) {
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = zzax.zza(bArr2, i18, zzayVar2);
                                    int i34 = zzayVar2.zzfd;
                                    zzck<?> zzckVarZzaf2 = zzdsVar3.zzaf(iZzai);
                                    if (zzckVarZzaf2 != null && zzckVarZzaf2.zzb(i34) == null) {
                                        zzn(t3).zzb(i26 == true ? 1 : 0, Long.valueOf(i34));
                                        i20 = i17;
                                        i23 = i26 == true ? 1 : 0;
                                        zzayVar4 = zzayVar2;
                                        i22 = i15;
                                        i24 = i11;
                                        unsafe4 = unsafe2;
                                        iZza2 = iZza;
                                        bArr3 = bArr2;
                                        i21 = -1;
                                    } else {
                                        unsafe2.putInt(t3, j2, i34);
                                        i24 = i11 | i32;
                                        i20 = i17;
                                        i23 = i26 == true ? 1 : 0;
                                        zzayVar4 = zzayVar2;
                                        i22 = i15;
                                        unsafe4 = unsafe2;
                                        iZza2 = iZza;
                                        bArr3 = bArr2;
                                        i21 = -1;
                                    }
                                }
                                break;
                            case 15:
                                bArr2 = bArr;
                                unsafe2 = unsafe4;
                                i18 = iZza2;
                                if (i28 != 0) {
                                    i15 = i22;
                                    i11 = i24;
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = zzax.zza(bArr2, i18, zzayVar);
                                    unsafe2.putInt(t3, j2, zzbk.zzm(zzayVar.zzfd));
                                    i24 |= i32;
                                    i20 = i2;
                                    i23 = i26 == true ? 1 : 0;
                                    zzayVar4 = zzayVar;
                                    unsafe4 = unsafe2;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            case 16:
                                i18 = iZza2;
                                if (i28 != 0) {
                                    unsafe2 = unsafe4;
                                    i15 = i22;
                                    i11 = i24;
                                    i16 = i18;
                                    i13 = i26 == true ? 1 : 0;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZzb = zzax.zzb(bArr, i18, zzayVar);
                                    zzayVar3 = zzayVar;
                                    T t6 = t3;
                                    Unsafe unsafe5 = unsafe4;
                                    unsafe5.putLong(t6, j2, zzbk.zza(zzayVar.zzfe));
                                    unsafe3 = unsafe5;
                                    t3 = t6;
                                    i24 |= i32;
                                    i20 = i2;
                                    bArr3 = bArr;
                                    i23 = i26 == true ? 1 : 0;
                                    i21 = -1;
                                    unsafe4 = unsafe3;
                                    iZza2 = iZzb;
                                    zzayVar4 = zzayVar3;
                                }
                                break;
                            case 17:
                                if (i28 != 3) {
                                    unsafe2 = unsafe4;
                                    i15 = i22;
                                    i13 = i26 == true ? 1 : 0;
                                    i16 = iZza2;
                                    i11 = i24;
                                    i6 = i3;
                                    zzdsVar = zzdsVar3;
                                    unsafe = unsafe2;
                                    i12 = i13;
                                    iZza2 = i16;
                                    i22 = i15;
                                    i24 = i11;
                                } else {
                                    iZza = zza(zzdsVar3.zzad(iZzai), bArr, iZza2, i2, (i27 << 3) | 4, zzayVar);
                                    bArr2 = bArr;
                                    unsafe4.putObject(t3, j2, (i24 & i32) == 0 ? zzayVar.zzff : zzci.zza(unsafe4.getObject(t3, j2), zzayVar.zzff));
                                    i24 |= i32;
                                    i20 = i2;
                                    zzayVar4 = zzayVar;
                                    i23 = i26 == true ? 1 : 0;
                                    iZza2 = iZza;
                                    bArr3 = bArr2;
                                    i21 = -1;
                                }
                                break;
                            default:
                                unsafe2 = unsafe4;
                                i15 = i22;
                                i13 = i26 == true ? 1 : 0;
                                i16 = iZza2;
                                i11 = i24;
                                i6 = i3;
                                zzdsVar = zzdsVar3;
                                unsafe = unsafe2;
                                i12 = i13;
                                iZza2 = i16;
                                i22 = i15;
                                i24 = i11;
                                break;
                        }
                    } else {
                        Unsafe unsafe6 = unsafe4;
                        i5 = -1;
                        int i35 = i20;
                        if (i30 != 27) {
                            iZza2 = iZza2;
                            if (i30 <= 49) {
                                i11 = i24;
                                unsafe = unsafe6;
                                i10 = i22;
                                int iZza4 = zzdsVar3.zza(t, bArr, iZza2, i2, i26 == true ? 1 : 0, i27, i28, iZzai, i29, i30, j2, zzayVar);
                                i19 = i26 == true ? 1 : 0;
                                if (iZza4 == iZza2) {
                                    zzdsVar = this;
                                    i6 = i3;
                                    iZza2 = iZza4;
                                    i12 = i19;
                                    i24 = i11;
                                    i22 = i10;
                                } else {
                                    zzdsVar3 = this;
                                    t3 = t;
                                    bArr3 = bArr;
                                    i20 = i2;
                                    zzayVar4 = zzayVar;
                                    iZza2 = iZza4;
                                    i23 = i19;
                                    i21 = -1;
                                    unsafe4 = unsafe;
                                    i24 = i11;
                                    i22 = i10;
                                }
                            } else {
                                unsafe = unsafe6;
                                i10 = i22;
                                i11 = i24;
                                i19 = i26 == true ? 1 : 0;
                                if (i30 != 50) {
                                    int iZza5 = zza(t, bArr, iZza2, i2, i19 == true ? 1 : 0, i27, i28, i29, i30, j2, iZzai, zzayVar);
                                    zzdsVar = this;
                                    i12 = i19 == true ? 1 : 0;
                                    if (iZza5 == iZza2) {
                                        i22 = i10;
                                        i6 = i3;
                                        iZza2 = iZza5;
                                        i24 = i11;
                                    } else {
                                        t3 = t;
                                        i22 = i10;
                                        i20 = i2;
                                        i23 = i12 == true ? 1 : 0;
                                        iZza2 = iZza5;
                                        zzdsVar3 = zzdsVar;
                                        i21 = -1;
                                        unsafe4 = unsafe;
                                        i24 = i11;
                                        bArr3 = bArr;
                                        zzayVar4 = zzayVar;
                                    }
                                } else if (i28 == 2) {
                                    int iZza6 = zza(t, bArr, iZza2, i2, iZzai, i27, j2, zzayVar);
                                    if (iZza6 == iZza2) {
                                        zzdsVar = this;
                                        i6 = i3;
                                        iZza2 = iZza6;
                                        i12 = i19;
                                        i24 = i11;
                                        i22 = i10;
                                    } else {
                                        zzdsVar3 = this;
                                        t3 = t;
                                        bArr3 = bArr;
                                        i20 = i2;
                                        zzayVar4 = zzayVar;
                                        iZza2 = iZza6;
                                        i23 = i19;
                                        i21 = -1;
                                        unsafe4 = unsafe;
                                        i24 = i11;
                                        i22 = i10;
                                    }
                                } else {
                                    zzdsVar = this;
                                    i12 = i19 == true ? 1 : 0;
                                }
                            }
                        } else if (i28 == 2) {
                            zzcn zzcnVarZzi = (zzcn) unsafe6.getObject(t3, j2);
                            if (!zzcnVarZzi.zzu()) {
                                int size = zzcnVarZzi.size();
                                zzcnVarZzi = zzcnVarZzi.zzi(size == 0 ? 10 : size << 1);
                                unsafe6.putObject(t3, j2, zzcnVarZzi);
                            }
                            zzayVar4 = zzayVar;
                            unsafe4 = unsafe6;
                            i20 = i2;
                            iZza2 = zza((zzef<?>) zzdsVar3.zzad(iZzai), i26 == true ? 1 : 0, bArr, iZza2, i35, (zzcn<?>) zzcnVarZzi, zzayVar4);
                            i23 = i26 == true ? 1 : 0;
                            i21 = -1;
                            t3 = t;
                            bArr3 = bArr;
                        } else {
                            zzdsVar = zzdsVar3;
                            i12 = i26 == true ? 1 : 0;
                            unsafe = unsafe6;
                            i10 = i22;
                            iZza2 = iZza2;
                            i11 = i24;
                        }
                    }
                    if (i12 == i6 || i6 == 0) {
                        int i36 = i12;
                        int iZza7 = zza(i36 == true ? 1 : 0, bArr, iZza2, i2, t, zzayVar);
                        i23 = i36 == true ? 1 : 0;
                        t3 = t;
                        zzdsVar3 = zzdsVar;
                        i21 = i5;
                        unsafe4 = unsafe;
                        zzayVar4 = zzayVar;
                        i20 = i2;
                        iZza2 = iZza7;
                        bArr3 = bArr;
                    } else {
                        t2 = t;
                        i4 = i2;
                        i7 = i22;
                        i9 = i24;
                        i8 = i12;
                    }
                } else {
                    zzdsVar = zzdsVar3;
                    unsafe = unsafe4;
                    i5 = i21;
                    i10 = i22;
                    i11 = i24;
                    i12 = i26 == true ? 1 : 0;
                }
                i22 = i10;
                i6 = i3;
                i24 = i11;
                if (i12 == i6) {
                }
                int i362 = i12;
                int iZza72 = zza(i362 == true ? 1 : 0, bArr, iZza2, i2, t, zzayVar);
                i23 = i362 == true ? 1 : 0;
                t3 = t;
                zzdsVar3 = zzdsVar;
                i21 = i5;
                unsafe4 = unsafe;
                zzayVar4 = zzayVar;
                i20 = i2;
                iZza2 = iZza72;
                bArr3 = bArr;
            } else {
                zzdsVar = zzdsVar3;
                i4 = i20;
                unsafe = unsafe4;
                i5 = i21;
                int i37 = i24;
                i6 = i3;
                t2 = t3;
                i7 = i22;
                i8 = i23;
                i9 = i37;
            }
        }
        int i38 = iZza2;
        if (i7 != i5) {
            unsafe.putInt(t2, i7, i9);
        }
        int[] iArr2 = zzdsVar.zzmt;
        if (iArr2 != null) {
            int length = iArr2.length;
            Object objZza2 = null;
            int i39 = 0;
            while (i39 < length) {
                int i40 = iArr2[i39];
                zzex zzexVar = zzdsVar.zzmx;
                int i41 = zzdsVar.zzmi[i40];
                Object objZzo = zzfd.zzo(t2, zzdsVar.zzag(i40) & 1048575);
                if (objZzo == null || (zzckVarZzaf = zzdsVar.zzaf(i40)) == null) {
                    zzdsVar2 = zzdsVar;
                } else {
                    Map mapZzg = zzdsVar.zzmz.zzg(objZzo);
                    zzdsVar2 = zzdsVar;
                    objZza2 = zzdsVar2.zza(i40, i41, mapZzg, zzckVarZzaf, objZza2, (zzex<UT, Object>) zzexVar);
                }
                objZza2 = (zzey) objZza2;
                i39++;
                zzdsVar = zzdsVar2;
            }
            zzds<T> zzdsVar4 = zzdsVar;
            if (objZza2 != null) {
                zzdsVar4.zzmx.zzf(t2, objZza2);
            }
        }
        if (i6 == 0) {
            if (i38 != i4) {
                throw zzco.zzbo();
            }
        } else if (i38 > i4 || i8 != i6) {
            throw zzco.zzbo();
        }
        return i38;
    }

    public static <T> zzds<T> zza(Class<T> cls, zzdm zzdmVar, zzdw zzdwVar, zzcy zzcyVar, zzex<?, ?> zzexVar, zzbu<?> zzbuVar, zzdj zzdjVar) {
        int iZzcu;
        int i;
        int i2;
        int iZza;
        int iZza2;
        int iZzdg;
        if (!(zzdmVar instanceof zzec)) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(zzdmVar);
            throw null;
        }
        zzec zzecVar = (zzec) zzdmVar;
        boolean z = zzecVar.zzcf() == zzcg.zzg.zzkm;
        if (zzecVar.getFieldCount() == 0) {
            iZzcu = 0;
            i = 0;
            i2 = 0;
        } else {
            int iZzcp = zzecVar.zzcp();
            int iZzcq = zzecVar.zzcq();
            iZzcu = zzecVar.zzcu();
            i = iZzcp;
            i2 = iZzcq;
        }
        int[] iArr = new int[iZzcu << 2];
        Object[] objArr = new Object[iZzcu << 1];
        int[] iArr2 = zzecVar.zzcr() > 0 ? new int[zzecVar.zzcr()] : null;
        int[] iArr3 = zzecVar.zzcs() > 0 ? new int[zzecVar.zzcs()] : null;
        zzed zzedVarZzco = zzecVar.zzco();
        if (zzedVarZzco.next()) {
            int iZzcx = zzedVarZzco.zzcx();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                if (iZzcx >= zzecVar.zzcv() || i3 >= ((iZzcx - i) << 2)) {
                    if (zzedVarZzco.zzda()) {
                        iZza = (int) zzfd.zza(zzedVarZzco.zzdb());
                        iZza2 = (int) zzfd.zza(zzedVarZzco.zzdc());
                        iZzdg = 0;
                    } else {
                        iZza = (int) zzfd.zza(zzedVarZzco.zzdd());
                        if (zzedVarZzco.zzde()) {
                            iZza2 = (int) zzfd.zza(zzedVarZzco.zzdf());
                            iZzdg = zzedVarZzco.zzdg();
                        } else {
                            iZza2 = 0;
                            iZzdg = 0;
                        }
                    }
                    iArr[i3] = zzedVarZzco.zzcx();
                    int i6 = i3 + 1;
                    iArr[i6] = (zzedVarZzco.zzdi() ? 536870912 : 0) | (zzedVarZzco.zzdh() ? 268435456 : 0) | (zzedVarZzco.zzcy() << 20) | iZza;
                    iArr[i3 + 2] = iZza2 | (iZzdg << 20);
                    if (zzedVarZzco.zzdl() != null) {
                        int i7 = (i3 / 4) << 1;
                        objArr[i7] = zzedVarZzco.zzdl();
                        if (zzedVarZzco.zzdj() != null) {
                            objArr[i7 + 1] = zzedVarZzco.zzdj();
                        } else if (zzedVarZzco.zzdk() != null) {
                            objArr[i7 + 1] = zzedVarZzco.zzdk();
                        }
                    } else if (zzedVarZzco.zzdj() != null) {
                        objArr[((i3 / 4) << 1) + 1] = zzedVarZzco.zzdj();
                    } else if (zzedVarZzco.zzdk() != null) {
                        objArr[((i3 / 4) << 1) + 1] = zzedVarZzco.zzdk();
                    }
                    int iZzcy = zzedVarZzco.zzcy();
                    if (iZzcy == zzcb.zziw.ordinal()) {
                        iArr2[i4] = i3;
                        i4++;
                    } else if (iZzcy >= 18 && iZzcy <= 49) {
                        iArr3[i5] = iArr[i6] & 1048575;
                        i5++;
                    }
                    if (!zzedVarZzco.next()) {
                        break;
                    }
                    iZzcx = zzedVarZzco.zzcx();
                } else {
                    for (int i8 = 0; i8 < 4; i8++) {
                        iArr[i3 + i8] = -1;
                    }
                }
                i3 += 4;
            }
        }
        return new zzds<>(iArr, objArr, i, i2, zzecVar.zzcv(), zzecVar.zzch(), z, false, zzecVar.zzct(), iArr2, iArr3, zzdwVar, zzcyVar, zzexVar, zzbuVar, zzdjVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzck<?> zzckVar, UB ub, zzex<UT, UB> zzexVar) {
        this.zzmz.zzl(zzae(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (zzckVar.zzb(((Integer) next.getValue()).intValue()) == null) {
                if (ub == null) {
                    ub = zzexVar.zzdz();
                }
                zzbg zzbgVarZzk = zzbb.zzk(zzdg.zza(null, next.getKey(), next.getValue()));
                try {
                    zzdg.zza(zzbgVarZzk.zzae(), null, next.getKey(), next.getValue());
                    zzexVar.zza(ub, i2, zzbgVarZzk.zzad());
                    it.remove();
                } catch (IOException e) {
                    HttpUrl$$ExternalSyntheticBUOutline0.m958m(e);
                    return null;
                }
            }
        }
        return ub;
    }

    private static void zza(int i, Object obj, zzfr zzfrVar) {
        if (obj instanceof String) {
            zzfrVar.zza(i, (String) obj);
        } else {
            zzfrVar.zza(i, (zzbb) obj);
        }
    }

    private static <UT, UB> void zza(zzex<UT, UB> zzexVar, T t, zzfr zzfrVar) {
        zzexVar.zza(zzexVar.zzq(t), zzfrVar);
    }

    private final <K, V> void zza(zzfr zzfrVar, int i, Object obj, int i2) {
        if (obj != null) {
            this.zzmz.zzl(zzae(i2));
            zzfrVar.zza(i, (zzdh) null, this.zzmz.zzh(obj));
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzag = zzag(i) & 1048575;
        if (zza(t2, i)) {
            Object objZzo = zzfd.zzo(t, jZzag);
            Object objZzo2 = zzfd.zzo(t2, jZzag);
            if (objZzo != null && objZzo2 != null) {
                objZzo2 = zzci.zza(objZzo, objZzo2);
            } else if (objZzo2 == null) {
                return;
            }
            zzfd.zza(t, jZzag, objZzo2);
            zzb(t, i);
        }
    }

    private final boolean zza(T t, int i) {
        if (!this.zzmq) {
            int iZzah = zzah(i);
            return (zzfd.zzj(t, (long) (iZzah & 1048575)) & (1 << (iZzah >>> 20))) != 0;
        }
        int iZzag = zzag(i);
        long j = iZzag & 1048575;
        switch ((iZzag & 267386880) >>> 20) {
            case 0:
                return zzfd.zzn(t, j) != 0.0d;
            case 1:
                return zzfd.zzm(t, j) != 0.0f;
            case 2:
                return zzfd.zzk(t, j) != 0;
            case 3:
                return zzfd.zzk(t, j) != 0;
            case 4:
                return zzfd.zzj(t, j) != 0;
            case 5:
                return zzfd.zzk(t, j) != 0;
            case 6:
                return zzfd.zzj(t, j) != 0;
            case 7:
                return zzfd.zzl(t, j);
            case 8:
                Object objZzo = zzfd.zzo(t, j);
                if (objZzo instanceof String) {
                    return !((String) objZzo).isEmpty();
                }
                if (objZzo instanceof zzbb) {
                    return !zzbb.zzfi.equals(objZzo);
                }
                Segment$$ExternalSyntheticBUOutline0.m991m();
                return false;
            case 9:
                return zzfd.zzo(t, j) != null;
            case 10:
                return !zzbb.zzfi.equals(zzfd.zzo(t, j));
            case 11:
                return zzfd.zzj(t, j) != 0;
            case 12:
                return zzfd.zzj(t, j) != 0;
            case 13:
                return zzfd.zzj(t, j) != 0;
            case 14:
                return zzfd.zzk(t, j) != 0;
            case 15:
                return zzfd.zzj(t, j) != 0;
            case 16:
                return zzfd.zzk(t, j) != 0;
            case 17:
                return zzfd.zzo(t, j) != null;
            default:
                Segment$$ExternalSyntheticBUOutline0.m991m();
                return false;
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzfd.zzj(t, (long) (zzah(i2) & 1048575)) == i;
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        return this.zzmq ? zza(t, i) : (i2 & i3) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzef zzefVar) {
        return zzefVar.zzo(zzfd.zzo(obj, i & 1048575));
    }

    private final zzef zzad(int i) {
        int i2 = (i / 4) << 1;
        zzef zzefVar = (zzef) this.zzmj[i2];
        if (zzefVar != null) {
            return zzefVar;
        }
        zzef<T> zzefVarZze = zzea.zzcm().zze((Class) this.zzmj[i2 + 1]);
        this.zzmj[i2] = zzefVarZze;
        return zzefVarZze;
    }

    private final Object zzae(int i) {
        return this.zzmj[(i / 4) << 1];
    }

    private final zzck<?> zzaf(int i) {
        return (zzck) this.zzmj[((i / 4) << 1) + 1];
    }

    private final int zzag(int i) {
        return this.zzmi[i + 1];
    }

    private final int zzah(int i) {
        return this.zzmi[i + 2];
    }

    private final int zzai(int i) {
        int i2 = this.zzmk;
        if (i >= i2) {
            int i3 = this.zzmm;
            if (i < i3) {
                int i4 = (i - i2) << 2;
                if (this.zzmi[i4] == i) {
                    return i4;
                }
                return -1;
            }
            if (i <= this.zzml) {
                int i5 = i3 - i2;
                int length = (this.zzmi.length / 4) - 1;
                while (i5 <= length) {
                    int i6 = (length + i5) >>> 1;
                    int i7 = i6 << 2;
                    int i8 = this.zzmi[i7];
                    if (i == i8) {
                        return i7;
                    }
                    if (i < i8) {
                        length = i6 - 1;
                    } else {
                        i5 = i6 + 1;
                    }
                }
            }
        }
        return -1;
    }

    private final void zzb(T t, int i) {
        if (this.zzmq) {
            return;
        }
        int iZzah = zzah(i);
        long j = iZzah & 1048575;
        zzfd.zza((Object) t, j, zzfd.zzj(t, j) | (1 << (iZzah >>> 20)));
    }

    private final void zzb(T t, int i, int i2) {
        zzfd.zza((Object) t, zzah(i2) & 1048575, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzb(T r19, com.google.android.gms.internal.clearcut.zzfr r20) {
        /*
            Method dump skipped, instruction units count: 1348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzb(java.lang.Object, com.google.android.gms.internal.clearcut.zzfr):void");
    }

    private final void zzb(T t, T t2, int i) {
        int iZzag = zzag(i);
        int i2 = this.zzmi[i];
        long j = iZzag & 1048575;
        if (zza(t2, i2, i)) {
            Object objZzo = zzfd.zzo(t, j);
            Object objZzo2 = zzfd.zzo(t2, j);
            if (objZzo != null && objZzo2 != null) {
                objZzo2 = zzci.zza(objZzo, objZzo2);
            } else if (objZzo2 == null) {
                return;
            }
            zzfd.zza(t, j, objZzo2);
            zzb(t, i2, i);
        }
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zzfd.zzo(obj, j);
    }

    private static <T> double zze(T t, long j) {
        return ((Double) zzfd.zzo(t, j)).doubleValue();
    }

    private static <T> float zzf(T t, long j) {
        return ((Float) zzfd.zzo(t, j)).floatValue();
    }

    private static <T> int zzg(T t, long j) {
        return ((Integer) zzfd.zzo(t, j)).intValue();
    }

    private static <T> long zzh(T t, long j) {
        return ((Long) zzfd.zzo(t, j)).longValue();
    }

    private static <T> boolean zzi(T t, long j) {
        return ((Boolean) zzfd.zzo(t, j)).booleanValue();
    }

    private static zzey zzn(Object obj) {
        zzcg zzcgVar = (zzcg) obj;
        zzey zzeyVar = zzcgVar.zzjp;
        if (zzeyVar != zzey.zzea()) {
            return zzeyVar;
        }
        zzey zzeyVarZzeb = zzey.zzeb();
        zzcgVar.zzjp = zzeyVarZzeb;
        return zzeyVarZzeb;
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x003a  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(T r10, T r11) {
        /*
            Method dump skipped, instruction units count: 612
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.equals(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:176:0x00f4 A[PHI: r3
  0x00f4: PHI (r3v9 java.lang.Object) = (r3v6 java.lang.Object), (r3v10 java.lang.Object) binds: [B:181:0x0110, B:175:0x00f2] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int hashCode(T r9) {
        /*
            Method dump skipped, instruction units count: 474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.hashCode(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final T newInstance() {
        return (T) this.zzmv.newInstance(this.zzmn);
    }

    /* JADX WARN: Removed duplicated region for block: B:584:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:600:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:627:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:630:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:633:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:767:0x04f6  */
    /* JADX WARN: Removed duplicated region for block: B:783:0x0533  */
    /* JADX WARN: Removed duplicated region for block: B:810:0x05ae  */
    /* JADX WARN: Removed duplicated region for block: B:813:0x05c1  */
    /* JADX WARN: Removed duplicated region for block: B:816:0x05d6  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r14, com.google.android.gms.internal.clearcut.zzfr r15) {
        /*
            Method dump skipped, instruction units count: 2752
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zza(java.lang.Object, com.google.android.gms.internal.clearcut.zzfr):void");
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zza(T t, byte[] bArr, int i, int i2, zzay zzayVar) throws zzco {
        Unsafe unsafe;
        zzay zzayVar2;
        T t2;
        int i3;
        int i4;
        byte[] bArr2;
        int i5;
        int i6;
        int iZza;
        Unsafe unsafe2;
        T t3;
        Object objZza;
        T t4;
        int iZzm;
        zzds<T> zzdsVar = this;
        byte[] bArr3 = bArr;
        int i7 = i2;
        zzay zzayVar3 = zzayVar;
        if (!zzdsVar.zzmq) {
            zza(t, bArr, i, i7, 0, zzayVar);
            return;
        }
        Unsafe unsafe3 = zzmh;
        int iZza2 = i;
        while (iZza2 < i7) {
            int iZza3 = iZza2 + 1;
            int i8 = bArr3[iZza2];
            if (i8 < 0) {
                iZza3 = zzax.zza(i8, bArr3, iZza3, zzayVar3);
                i8 = zzayVar3.zzfd;
            }
            int i9 = i8;
            int i10 = iZza3;
            int i11 = (i9 == true ? 1 : 0) >>> 3;
            int i12 = (i9 == true ? 1 : 0) & 7;
            int iZzai = zzdsVar.zzai(i11);
            if (iZzai >= 0) {
                int i13 = zzdsVar.zzmi[iZzai + 1];
                int i14 = (267386880 & i13) >>> 20;
                long j = 1048575 & i13;
                if (i14 <= 17) {
                    switch (i14) {
                        case 0:
                            unsafe = unsafe3;
                            if (i12 == 1) {
                                zzfd.zza(t, j, zzax.zze(bArr3, i10));
                                iZza2 = i10 + 8;
                                unsafe3 = unsafe;
                            }
                            i6 = i10;
                            t2 = t;
                            bArr2 = bArr;
                            i4 = i6;
                            i5 = i9;
                            i3 = i2;
                            zzayVar2 = zzayVar;
                            iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                            zzdsVar = this;
                            bArr3 = bArr;
                            zzayVar3 = zzayVar;
                            i7 = i3;
                            unsafe3 = unsafe;
                            break;
                        case 1:
                            unsafe = unsafe3;
                            if (i12 == 5) {
                                zzfd.zza((Object) t, j, zzax.zzf(bArr3, i10));
                                iZza2 = i10 + 4;
                                unsafe3 = unsafe;
                            }
                            i6 = i10;
                            t2 = t;
                            bArr2 = bArr;
                            i4 = i6;
                            i5 = i9;
                            i3 = i2;
                            zzayVar2 = zzayVar;
                            iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                            zzdsVar = this;
                            bArr3 = bArr;
                            zzayVar3 = zzayVar;
                            i7 = i3;
                            unsafe3 = unsafe;
                            break;
                        case 2:
                        case 3:
                            Unsafe unsafe4 = unsafe3;
                            if (i12 != 0) {
                                unsafe = unsafe4;
                                i6 = i10;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                int iZzb = zzax.zzb(bArr3, i10, zzayVar3);
                                unsafe3 = unsafe4;
                                unsafe3.putLong(t, j, zzayVar3.zzfe);
                                iZza2 = iZzb;
                            }
                            break;
                        case 4:
                        case 11:
                            unsafe2 = unsafe3;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                iZza2 = zzax.zza(bArr3, i10, zzayVar3);
                                unsafe2.putInt(t, j, zzayVar3.zzfd);
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 5:
                        case 14:
                            unsafe2 = unsafe3;
                            if (i12 != 1) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                unsafe2.putLong(t, j, zzax.zzd(bArr3, i10));
                                iZza2 = i10 + 8;
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 6:
                        case 13:
                            unsafe2 = unsafe3;
                            if (i12 != 5) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                unsafe2.putInt(t, j, zzax.zzc(bArr3, i10));
                                iZza2 = i10 + 4;
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 7:
                            unsafe2 = unsafe3;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                iZza2 = zzax.zzb(bArr3, i10, zzayVar3);
                                zzfd.zza(t, j, zzayVar3.zzfe != 0);
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 8:
                            unsafe2 = unsafe3;
                            t3 = t;
                            if (i12 != 2) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                iZza2 = (536870912 & i13) == 0 ? zzax.zzc(bArr3, i10, zzayVar3) : zzax.zzd(bArr3, i10, zzayVar3);
                                objZza = zzayVar3.zzff;
                                unsafe2.putObject(t3, j, objZza);
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 9:
                            unsafe2 = unsafe3;
                            t3 = t;
                            if (i12 != 2) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                iZza2 = zza(zzdsVar.zzad(iZzai), bArr3, i10, i7, zzayVar3);
                                Object object = unsafe2.getObject(t3, j);
                                objZza = object == null ? zzayVar3.zzff : zzci.zza(object, zzayVar3.zzff);
                                unsafe2.putObject(t3, j, objZza);
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 10:
                            unsafe2 = unsafe3;
                            t3 = t;
                            if (i12 != 2) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                iZza2 = zzax.zze(bArr3, i10, zzayVar3);
                                objZza = zzayVar3.zzff;
                                unsafe2.putObject(t3, j, objZza);
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 12:
                            unsafe2 = unsafe3;
                            t4 = t;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                iZza2 = zzax.zza(bArr3, i10, zzayVar3);
                                iZzm = zzayVar3.zzfd;
                                unsafe2.putInt(t4, j, iZzm);
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 15:
                            unsafe2 = unsafe3;
                            t4 = t;
                            if (i12 != 0) {
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                iZza2 = zzax.zza(bArr3, i10, zzayVar3);
                                iZzm = zzbk.zzm(zzayVar3.zzfd);
                                unsafe2.putInt(t4, j, iZzm);
                                unsafe3 = unsafe2;
                            }
                            break;
                        case 16:
                            if (i12 != 0) {
                                unsafe2 = unsafe3;
                                i6 = i10;
                                unsafe = unsafe2;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                                unsafe3 = unsafe;
                            } else {
                                int iZzb2 = zzax.zzb(bArr3, i10, zzayVar3);
                                unsafe3.putLong(t, j, zzbk.zza(zzayVar3.zzfe));
                                unsafe2 = unsafe3;
                                iZza2 = iZzb2;
                                unsafe3 = unsafe2;
                            }
                            break;
                    }
                } else {
                    unsafe = unsafe3;
                    if (i14 != 27) {
                        if (i14 <= 49) {
                            iZza = zzdsVar.zza(t, bArr, i10, i2, i9 == true ? 1 : 0, i11, i12, iZzai, i13, i14, j, zzayVar);
                            if (iZza == i10) {
                                t2 = t;
                                bArr2 = bArr;
                                i3 = i2;
                                zzayVar2 = zzayVar;
                                i4 = iZza;
                                i5 = i9 == true ? 1 : 0;
                                iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                                zzdsVar = this;
                                bArr3 = bArr;
                                zzayVar3 = zzayVar;
                                i7 = i3;
                            }
                        } else {
                            if (i14 != 50) {
                                iZza = zza(t, bArr, i10, i2, i9 == true ? 1 : 0, i11, i12, i13, i14, j, iZzai, zzayVar);
                                if (iZza == i10) {
                                    t2 = t;
                                    bArr2 = bArr;
                                    i3 = i2;
                                    i5 = i9 == true ? 1 : 0;
                                    i4 = iZza;
                                }
                            } else if (i12 == 2) {
                                int iZza4 = zza(t, bArr, i10, i2, iZzai, i11, j, zzayVar);
                                if (iZza4 == i10) {
                                    t2 = t;
                                    bArr2 = bArr;
                                    i3 = i2;
                                    i4 = iZza4;
                                    i5 = i9 == true ? 1 : 0;
                                } else {
                                    zzdsVar = this;
                                    bArr3 = bArr;
                                    i7 = i2;
                                    zzayVar3 = zzayVar;
                                    iZza2 = iZza4;
                                }
                            } else {
                                i6 = i10;
                                i9 = i9 == true ? 1 : 0;
                                t2 = t;
                                bArr2 = bArr;
                                i4 = i6;
                                i5 = i9;
                                i3 = i2;
                            }
                            zzayVar2 = zzayVar;
                            iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                            zzdsVar = this;
                            bArr3 = bArr;
                            zzayVar3 = zzayVar;
                            i7 = i3;
                        }
                        zzdsVar = this;
                        bArr3 = bArr;
                        i7 = i2;
                        zzayVar3 = zzayVar;
                        iZza2 = iZza;
                    } else if (i12 == 2) {
                        zzcn zzcnVarZzi = (zzcn) unsafe.getObject(t, j);
                        if (!zzcnVarZzi.zzu()) {
                            int size = zzcnVarZzi.size();
                            zzcnVarZzi = zzcnVarZzi.zzi(size == 0 ? 10 : size << 1);
                            unsafe.putObject(t, j, zzcnVarZzi);
                        }
                        iZza2 = zza((zzef<?>) zzdsVar.zzad(iZzai), i9 == true ? 1 : 0, bArr3, i10, i7, (zzcn<?>) zzcnVarZzi, zzayVar3);
                        bArr3 = bArr;
                        i7 = i2;
                        zzayVar3 = zzayVar;
                    } else {
                        i9 = i9 == true ? 1 : 0;
                        i6 = i10;
                        t2 = t;
                        bArr2 = bArr;
                        i4 = i6;
                        i5 = i9;
                        i3 = i2;
                        zzayVar2 = zzayVar;
                        iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
                        zzdsVar = this;
                        bArr3 = bArr;
                        zzayVar3 = zzayVar;
                        i7 = i3;
                    }
                    unsafe3 = unsafe;
                }
            }
            unsafe = unsafe3;
            i6 = i10;
            t2 = t;
            bArr2 = bArr;
            i4 = i6;
            i5 = i9;
            i3 = i2;
            zzayVar2 = zzayVar;
            iZza2 = zza(i5, bArr2, i4, i3, t2, zzayVar2);
            zzdsVar = this;
            bArr3 = bArr;
            zzayVar3 = zzayVar;
            i7 = i3;
            unsafe3 = unsafe;
        }
        if (iZza2 != i7) {
            throw zzco.zzbo();
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzef
    public final void zzc(T t) {
        int[] iArr = this.zzmt;
        if (iArr != null) {
            for (int i : iArr) {
                long jZzag = zzag(i) & 1048575;
                Object objZzo = zzfd.zzo(t, jZzag);
                if (objZzo != null) {
                    zzfd.zza(t, jZzag, this.zzmz.zzj(objZzo));
                }
            }
        }
        int[] iArr2 = this.zzmu;
        if (iArr2 != null) {
            for (int i2 : iArr2) {
                this.zzmw.zza(t, i2);
            }
        }
        this.zzmx.zzc(t);
        if (this.zzmo) {
            this.zzmy.zzc(t);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x002b  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzc(T r7, T r8) {
        /*
            Method dump skipped, instruction units count: 406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzc(java.lang.Object, java.lang.Object):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:572:0x00e2, code lost:
    
        if ((r4 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L573;
     */
    /* JADX WARN: Code restructure failed: missing block: B:573:0x00e4, code lost:
    
        r4 = (com.google.android.gms.internal.clearcut.zzbb) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:574:0x00e7, code lost:
    
        r3 = com.google.android.gms.internal.clearcut.zzbn.zzb(r3, (java.lang.String) r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:718:0x0369, code lost:
    
        if ((r4 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L573;
     */
    /* JADX WARN: Code restructure failed: missing block: B:768:0x0425, code lost:
    
        if (zza(r21, r15, r4) != false) goto L769;
     */
    /* JADX WARN: Code restructure failed: missing block: B:769:0x0427, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, (com.google.android.gms.internal.clearcut.zzdo) r2.getObject(r21, r13), zzad(r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:783:0x045e, code lost:
    
        if (zza(r21, r15, r4) != false) goto L784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:784:0x0460, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzh(r15, 0L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:786:0x0469, code lost:
    
        if (zza(r21, r15, r4) != false) goto L787;
     */
    /* JADX WARN: Code restructure failed: missing block: B:787:0x046b, code lost:
    
        r8 = com.google.android.gms.internal.clearcut.zzbn.zzk(r15, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:798:0x0494, code lost:
    
        if (zza(r21, r15, r4) != false) goto L799;
     */
    /* JADX WARN: Code restructure failed: missing block: B:799:0x0496, code lost:
    
        r6 = (com.google.android.gms.internal.clearcut.zzbb) r2.getObject(r21, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:800:0x049c, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:802:0x04a5, code lost:
    
        if (zza(r21, r15, r4) != false) goto L803;
     */
    /* JADX WARN: Code restructure failed: missing block: B:803:0x04a7, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzeh.zzc(r15, r2.getObject(r21, r13), zzad(r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:807:0x04c0, code lost:
    
        if ((r6 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L808;
     */
    /* JADX WARN: Code restructure failed: missing block: B:808:0x04c2, code lost:
    
        r6 = (com.google.android.gms.internal.clearcut.zzbb) r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:809:0x04c5, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzb(r15, (java.lang.String) r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:811:0x04d1, code lost:
    
        if (zza(r21, r15, r4) != false) goto L812;
     */
    /* JADX WARN: Code restructure failed: missing block: B:812:0x04d3, code lost:
    
        r6 = com.google.android.gms.internal.clearcut.zzbn.zzc(r15, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:923:0x0716, code lost:
    
        if ((r12 & r19) != 0) goto L769;
     */
    /* JADX WARN: Code restructure failed: missing block: B:932:0x0730, code lost:
    
        if ((r12 & r19) != 0) goto L784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:935:0x0736, code lost:
    
        if ((r12 & r19) != 0) goto L787;
     */
    /* JADX WARN: Code restructure failed: missing block: B:944:0x0750, code lost:
    
        if ((r12 & r19) != 0) goto L799;
     */
    /* JADX WARN: Code restructure failed: missing block: B:947:0x0756, code lost:
    
        if ((r12 & r19) != 0) goto L803;
     */
    /* JADX WARN: Code restructure failed: missing block: B:952:0x0764, code lost:
    
        if ((r6 instanceof com.google.android.gms.internal.clearcut.zzbb) != false) goto L808;
     */
    /* JADX WARN: Code restructure failed: missing block: B:955:0x076a, code lost:
    
        if ((r12 & r19) != 0) goto L812;
     */
    /* JADX WARN: Removed duplicated region for block: B:608:0x018b A[PHI: r4
  0x018b: PHI (r4v72 int) = 
  (r4v34 int)
  (r4v37 int)
  (r4v40 int)
  (r4v43 int)
  (r4v46 int)
  (r4v49 int)
  (r4v52 int)
  (r4v55 int)
  (r4v58 int)
  (r4v61 int)
  (r4v64 int)
  (r4v67 int)
  (r4v70 int)
  (r4v75 int)
 binds: [B:673:0x027b, B:668:0x0269, B:663:0x0257, B:658:0x0245, B:653:0x0233, B:648:0x0221, B:643:0x020f, B:638:0x01fe, B:633:0x01ed, B:628:0x01dc, B:623:0x01cb, B:618:0x01ba, B:613:0x01a9, B:607:0x0189] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:840:0x056c A[PHI: r8
  0x056c: PHI (r8v75 int) = 
  (r8v37 int)
  (r8v40 int)
  (r8v43 int)
  (r8v46 int)
  (r8v49 int)
  (r8v52 int)
  (r8v55 int)
  (r8v58 int)
  (r8v61 int)
  (r8v64 int)
  (r8v67 int)
  (r8v70 int)
  (r8v73 int)
  (r8v78 int)
 binds: [B:905:0x065c, B:900:0x064a, B:895:0x0638, B:890:0x0626, B:885:0x0614, B:880:0x0602, B:875:0x05f0, B:870:0x05df, B:865:0x05ce, B:860:0x05bd, B:855:0x05ac, B:850:0x059b, B:845:0x058a, B:839:0x056a] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zzm(T r21) {
        /*
            Method dump skipped, instruction units count: 2308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzm(java.lang.Object):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:129:0x00a7  */
    @Override // com.google.android.gms.internal.clearcut.zzef
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzo(T r18) {
        /*
            Method dump skipped, instruction units count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzds.zzo(java.lang.Object):boolean");
    }
}
