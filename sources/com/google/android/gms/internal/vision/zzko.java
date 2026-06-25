package com.google.android.gms.internal.vision;

import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.HttpUrl$$ExternalSyntheticBUOutline0;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline0;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes5.dex */
final class zzko<T> implements zzlc<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzma.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzkk zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzks zzo;
    private final zzju zzp;
    private final zzlu<?, ?> zzq;
    private final zziq<?> zzr;
    private final zzkh zzs;

    private zzko(int[] iArr, Object[] objArr, int i, int i2, zzkk zzkkVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzks zzksVar, zzju zzjuVar, zzlu<?, ?> zzluVar, zziq<?> zziqVar, zzkh zzkhVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzkkVar instanceof zzjb;
        this.zzj = z;
        this.zzh = zziqVar != null && zziqVar.zza(zzkkVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzksVar;
        this.zzp = zzjuVar;
        this.zzq = zzluVar;
        this.zzr = zziqVar;
        this.zzg = zzkkVar;
        this.zzs = zzkhVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:341:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x0381  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T> com.google.android.gms.internal.vision.zzko<T> zza(java.lang.Class<T> r33, com.google.android.gms.internal.vision.zzki r34, com.google.android.gms.internal.vision.zzks r35, com.google.android.gms.internal.vision.zzju r36, com.google.android.gms.internal.vision.zzlu<?, ?> r37, com.google.android.gms.internal.vision.zziq<?> r38, com.google.android.gms.internal.vision.zzkh r39) {
        /*
            Method dump skipped, instruction units count: 1010
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Class, com.google.android.gms.internal.vision.zzki, com.google.android.gms.internal.vision.zzks, com.google.android.gms.internal.vision.zzju, com.google.android.gms.internal.vision.zzlu, com.google.android.gms.internal.vision.zziq, com.google.android.gms.internal.vision.zzkh):com.google.android.gms.internal.vision.zzko");
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + name.length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final T zza() {
        return (T) this.zzo.zza(this.zzg);
    }

    /* JADX WARN: Removed duplicated region for block: B:130:0x003a  */
    @Override // com.google.android.gms.internal.vision.zzlc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zza(T r10, T r11) {
        /*
            Method dump skipped, instruction units count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final int zza(T t) {
        int i;
        int iZza;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzd = zzd(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & iZzd;
            int iHashCode = 37;
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZza = zzjf.zza(Double.doubleToLongBits(zzma.zze(t, j)));
                    i2 = i + iZza;
                    break;
                case 1:
                    i = i2 * 53;
                    iZza = Float.floatToIntBits(zzma.zzd(t, j));
                    i2 = i + iZza;
                    break;
                case 2:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 3:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 4:
                    i = i2 * 53;
                    iZza = zzma.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 5:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 6:
                    i = i2 * 53;
                    iZza = zzma.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 7:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzc(t, j));
                    i2 = i + iZza;
                    break;
                case 8:
                    i = i2 * 53;
                    iZza = ((String) zzma.zzf(t, j)).hashCode();
                    i2 = i + iZza;
                    break;
                case 9:
                    Object objZzf = zzma.zzf(t, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZza = zzma.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 11:
                    i = i2 * 53;
                    iZza = zzma.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 12:
                    i = i2 * 53;
                    iZza = zzma.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 13:
                    i = i2 * 53;
                    iZza = zzma.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 14:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 15:
                    i = i2 * 53;
                    iZza = zzma.zza(t, j);
                    i2 = i + iZza;
                    break;
                case 16:
                    i = i2 * 53;
                    iZza = zzjf.zza(zzma.zzb(t, j));
                    i2 = i + iZza;
                    break;
                case 17:
                    Object objZzf2 = zzma.zzf(t, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
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
                    i = i2 * 53;
                    iZza = zzma.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 50:
                    i = i2 * 53;
                    iZza = zzma.zzf(t, j).hashCode();
                    i2 = i + iZza;
                    break;
                case 51:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(Double.doubleToLongBits(zzb(t, j)));
                        i2 = i + iZza;
                    }
                    break;
                case 52:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = Float.floatToIntBits(zzc(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 53:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 54:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 55:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 56:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 57:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 58:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zzf(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 59:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = ((String) zzma.zzf(t, j)).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 60:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzma.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 61:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzma.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
                case 62:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 63:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 64:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 65:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 66:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzd(t, j);
                        i2 = i + iZza;
                    }
                    break;
                case 67:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzjf.zza(zze(t, j));
                        i2 = i + iZza;
                    }
                    break;
                case 68:
                    if (zza(t, i4, i3)) {
                        i = i2 * 53;
                        iZza = zzma.zzf(t, j).hashCode();
                        i2 = i + iZza;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzq.zzb(t).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzr.zza(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final void zzb(T t, T t2) {
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzd = zzd(i);
            long j = 1048575 & iZzd;
            int i2 = this.zzc[i];
            switch ((iZzd & 267386880) >>> 20) {
                case 0:
                    if (zza((Object) t2, i)) {
                        zzma.zza(t, j, zzma.zze(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 1:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zzd(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 2:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 3:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 4:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 5:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 6:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 7:
                    if (zza((Object) t2, i)) {
                        zzma.zza(t, j, zzma.zzc(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 8:
                    if (zza((Object) t2, i)) {
                        zzma.zza(t, j, zzma.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((Object) t2, i)) {
                        zzma.zza(t, j, zzma.zzf(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 11:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 12:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 13:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 14:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 15:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zza(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 16:
                    if (zza((Object) t2, i)) {
                        zzma.zza((Object) t, j, zzma.zzb(t2, j));
                        zzb((Object) t, i);
                    }
                    break;
                case 17:
                    zza(t, t2, i);
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
                    this.zzp.zza(t, t2, j);
                    break;
                case 50:
                    zzle.zza(this.zzs, t, t2, j);
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
                    if (zza(t2, i2, i)) {
                        zzma.zza(t, j, zzma.zzf(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza(t2, i2, i)) {
                        zzma.zza(t, j, zzma.zzf(t2, j));
                        zzb(t, i2, i);
                    }
                    break;
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzle.zza(this.zzq, t, t2);
        if (this.zzh) {
            zzle.zza(this.zzr, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzd = zzd(i) & 1048575;
        if (zza((Object) t2, i)) {
            Object objZzf = zzma.zzf(t, jZzd);
            Object objZzf2 = zzma.zzf(t2, jZzd);
            if (objZzf != null && objZzf2 != null) {
                zzma.zza(t, jZzd, zzjf.zza(objZzf, objZzf2));
                zzb((Object) t, i);
            } else if (objZzf2 != null) {
                zzma.zza(t, jZzd, objZzf2);
                zzb((Object) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzd = zzd(i);
        int i2 = this.zzc[i];
        long j = iZzd & 1048575;
        if (zza(t2, i2, i)) {
            Object objZzf = zza(t, i2, i) ? zzma.zzf(t, j) : null;
            Object objZzf2 = zzma.zzf(t2, j);
            if (objZzf != null && objZzf2 != null) {
                zzma.zza(t, j, zzjf.zza(objZzf, objZzf2));
                zzb(t, i2, i);
            } else if (objZzf2 != null) {
                zzma.zza(t, j, objZzf2);
                zzb(t, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.google.android.gms.internal.vision.zzlc
    public final int zzb(T t) {
        int i;
        int i2;
        boolean z;
        int iZzd;
        int iZzb;
        int iZzj;
        int iZzi;
        int iZze;
        int iZzg;
        int iZzb2;
        int iZzi2;
        int iZze2;
        int iZzg2;
        int i3 = 267386880;
        int i4 = 1048575;
        int i5 = 0;
        if (this.zzj) {
            Unsafe unsafe = zzb;
            int i6 = 0;
            int i7 = 0;
            while (i6 < this.zzc.length) {
                int iZzd2 = zzd(i6);
                int i8 = (iZzd2 & i3) >>> 20;
                int i9 = i3;
                int i10 = this.zzc[i6];
                long j = iZzd2 & 1048575;
                if (i8 >= zziv.zza.zza() && i8 <= zziv.zzb.zza()) {
                    int i11 = this.zzc[i6 + 2];
                }
                switch (i8) {
                    case 0:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzb(i10, 0.0d);
                            i7 += iZzb2;
                        }
                        break;
                    case 1:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzb(i10, 0.0f);
                            i7 += iZzb2;
                        }
                        break;
                    case 2:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzd(i10, zzma.zzb(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 3:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zze(i10, zzma.zzb(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 4:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzf(i10, zzma.zza(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 5:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzg(i10, 0L);
                            i7 += iZzb2;
                        }
                        break;
                    case 6:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzi(i10, 0);
                            i7 += iZzb2;
                        }
                        break;
                    case 7:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzb(i10, true);
                            i7 += iZzb2;
                        }
                        break;
                    case 8:
                        if (zza((Object) t, i6)) {
                            Object objZzf = zzma.zzf(t, j);
                            if (objZzf instanceof zzht) {
                                iZzb2 = zzii.zzc(i10, (zzht) objZzf);
                            } else {
                                iZzb2 = zzii.zzb(i10, (String) objZzf);
                            }
                            i7 += iZzb2;
                        }
                        break;
                    case 9:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzle.zza(i10, zzma.zzf(t, j), zza(i6));
                            i7 += iZzb2;
                        }
                        break;
                    case 10:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzc(i10, (zzht) zzma.zzf(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 11:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzg(i10, zzma.zza(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 12:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzk(i10, zzma.zza(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 13:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzj(i10, 0);
                            i7 += iZzb2;
                        }
                        break;
                    case 14:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzh(i10, 0L);
                            i7 += iZzb2;
                        }
                        break;
                    case 15:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzh(i10, zzma.zza(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 16:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzf(i10, zzma.zzb(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 17:
                        if (zza((Object) t, i6)) {
                            iZzb2 = zzii.zzc(i10, (zzkk) zzma.zzf(t, j), zza(i6));
                            i7 += iZzb2;
                        }
                        break;
                    case 18:
                        iZzb2 = zzle.zzi(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 19:
                        iZzb2 = zzle.zzh(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 20:
                        iZzb2 = zzle.zza(i10, (List<Long>) zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 21:
                        iZzb2 = zzle.zzb(i10, (List<Long>) zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 22:
                        iZzb2 = zzle.zze(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 23:
                        iZzb2 = zzle.zzi(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 24:
                        iZzb2 = zzle.zzh(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 25:
                        iZzb2 = zzle.zzj(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 26:
                        iZzb2 = zzle.zza(i10, zza(t, j));
                        i7 += iZzb2;
                        break;
                    case 27:
                        iZzb2 = zzle.zza(i10, zza(t, j), zza(i6));
                        i7 += iZzb2;
                        break;
                    case 28:
                        iZzb2 = zzle.zzb(i10, zza(t, j));
                        i7 += iZzb2;
                        break;
                    case 29:
                        iZzb2 = zzle.zzf(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 30:
                        iZzb2 = zzle.zzd(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 31:
                        iZzb2 = zzle.zzh(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 32:
                        iZzb2 = zzle.zzi(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 33:
                        iZzb2 = zzle.zzg(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 34:
                        iZzb2 = zzle.zzc(i10, zza(t, j), false);
                        i7 += iZzb2;
                        break;
                    case 35:
                        iZzi2 = zzle.zzi((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 36:
                        iZzi2 = zzle.zzh((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 37:
                        iZzi2 = zzle.zza((List<Long>) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 38:
                        iZzi2 = zzle.zzb((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 39:
                        iZzi2 = zzle.zze((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 40:
                        iZzi2 = zzle.zzi((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 41:
                        iZzi2 = zzle.zzh((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 42:
                        iZzi2 = zzle.zzj((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 43:
                        iZzi2 = zzle.zzf((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 44:
                        iZzi2 = zzle.zzd((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 45:
                        iZzi2 = zzle.zzh((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 46:
                        iZzi2 = zzle.zzi((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 47:
                        iZzi2 = zzle.zzg((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 48:
                        iZzi2 = zzle.zzc((List) unsafe.getObject(t, j));
                        if (iZzi2 > 0) {
                            iZze2 = zzii.zze(i10);
                            iZzg2 = zzii.zzg(iZzi2);
                            iZzb2 = iZze2 + iZzg2 + iZzi2;
                            i7 += iZzb2;
                        }
                        break;
                    case 49:
                        iZzb2 = zzle.zzb(i10, (List<zzkk>) zza(t, j), zza(i6));
                        i7 += iZzb2;
                        break;
                    case 50:
                        iZzb2 = this.zzs.zza(i10, zzma.zzf(t, j), zzb(i6));
                        i7 += iZzb2;
                        break;
                    case 51:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzb(i10, 0.0d);
                            i7 += iZzb2;
                        }
                        break;
                    case 52:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzb(i10, 0.0f);
                            i7 += iZzb2;
                        }
                        break;
                    case 53:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzd(i10, zze(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 54:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zze(i10, zze(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 55:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzf(i10, zzd(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 56:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzg(i10, 0L);
                            i7 += iZzb2;
                        }
                        break;
                    case 57:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzi(i10, 0);
                            i7 += iZzb2;
                        }
                        break;
                    case 58:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzb(i10, true);
                            i7 += iZzb2;
                        }
                        break;
                    case 59:
                        if (zza(t, i10, i6)) {
                            Object objZzf2 = zzma.zzf(t, j);
                            if (objZzf2 instanceof zzht) {
                                iZzb2 = zzii.zzc(i10, (zzht) objZzf2);
                            } else {
                                iZzb2 = zzii.zzb(i10, (String) objZzf2);
                            }
                            i7 += iZzb2;
                        }
                        break;
                    case 60:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzle.zza(i10, zzma.zzf(t, j), zza(i6));
                            i7 += iZzb2;
                        }
                        break;
                    case 61:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzc(i10, (zzht) zzma.zzf(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 62:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzg(i10, zzd(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 63:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzk(i10, zzd(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 64:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzj(i10, 0);
                            i7 += iZzb2;
                        }
                        break;
                    case 65:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzh(i10, 0L);
                            i7 += iZzb2;
                        }
                        break;
                    case 66:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzh(i10, zzd(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 67:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzf(i10, zze(t, j));
                            i7 += iZzb2;
                        }
                        break;
                    case 68:
                        if (zza(t, i10, i6)) {
                            iZzb2 = zzii.zzc(i10, (zzkk) zzma.zzf(t, j), zza(i6));
                            i7 += iZzb2;
                        }
                        break;
                }
                i6 += 3;
                i3 = i9;
            }
            return i7 + zza((zzlu) this.zzq, (Object) t);
        }
        Unsafe unsafe2 = zzb;
        int i12 = 1048575;
        int i13 = 0;
        int iZzb3 = 0;
        int i14 = 0;
        while (i13 < this.zzc.length) {
            int iZzd3 = zzd(i13);
            int[] iArr = this.zzc;
            int i15 = iArr[i13];
            int i16 = i4;
            int i17 = (iZzd3 & 267386880) >>> 20;
            if (i17 <= 17) {
                int i18 = iArr[i13 + 2];
                int i19 = i18 & i16;
                i = 1 << (i18 >>> 20);
                if (i19 != i12) {
                    i14 = unsafe2.getInt(t, i19);
                    i12 = i19;
                }
            } else {
                i = 0;
            }
            long j2 = iZzd3 & i16;
            switch (i17) {
                case 0:
                    i2 = 0;
                    z = false;
                    if ((i & i14) != 0) {
                        iZzb3 += zzii.zzb(i15, 0.0d);
                    }
                    break;
                case 1:
                    i2 = 0;
                    if ((i & i14) != 0) {
                        z = false;
                        iZzb3 += zzii.zzb(i15, 0.0f);
                    } else {
                        z = false;
                    }
                    break;
                case 2:
                    i2 = 0;
                    if ((i & i14) != 0) {
                        iZzd = zzii.zzd(i15, unsafe2.getLong(t, j2));
                        iZzb3 += iZzd;
                    }
                    z = false;
                    break;
                case 3:
                    i2 = 0;
                    if ((i & i14) != 0) {
                        iZzd = zzii.zze(i15, unsafe2.getLong(t, j2));
                        iZzb3 += iZzd;
                    }
                    z = false;
                    break;
                case 4:
                    i2 = 0;
                    if ((i & i14) != 0) {
                        iZzd = zzii.zzf(i15, unsafe2.getInt(t, j2));
                        iZzb3 += iZzd;
                    }
                    z = false;
                    break;
                case 5:
                    i2 = 0;
                    if ((i & i14) != 0) {
                        iZzd = zzii.zzg(i15, 0L);
                        iZzb3 += iZzd;
                    }
                    z = false;
                    break;
                case 6:
                    if ((i & i14) != 0) {
                        i2 = 0;
                        iZzd = zzii.zzi(i15, 0);
                        iZzb3 += iZzd;
                        z = false;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 7:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzb(i15, true);
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 8:
                    if ((i & i14) != 0) {
                        Object object = unsafe2.getObject(t, j2);
                        if (object instanceof zzht) {
                            iZzb = zzii.zzc(i15, (zzht) object);
                        } else {
                            iZzb = zzii.zzb(i15, (String) object);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 9:
                    if ((i & i14) != 0) {
                        iZzb = zzle.zza(i15, unsafe2.getObject(t, j2), zza(i13));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 10:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzc(i15, (zzht) unsafe2.getObject(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 11:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzg(i15, unsafe2.getInt(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 12:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzk(i15, unsafe2.getInt(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 13:
                    if ((i & i14) != 0) {
                        iZzj = zzii.zzj(i15, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 14:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzh(i15, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 15:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzh(i15, unsafe2.getInt(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 16:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzf(i15, unsafe2.getLong(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 17:
                    if ((i & i14) != 0) {
                        iZzb = zzii.zzc(i15, (zzkk) unsafe2.getObject(t, j2), zza(i13));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 18:
                    iZzb = zzle.zzi(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzb;
                    i2 = 0;
                    z = false;
                    break;
                case 19:
                    i2 = 0;
                    iZzd = zzle.zzh(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 20:
                    i2 = 0;
                    iZzd = zzle.zza(i15, (List<Long>) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 21:
                    i2 = 0;
                    iZzd = zzle.zzb(i15, (List<Long>) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 22:
                    i2 = 0;
                    iZzd = zzle.zze(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 23:
                    i2 = 0;
                    iZzd = zzle.zzi(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 24:
                    i2 = 0;
                    iZzd = zzle.zzh(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 25:
                    i2 = 0;
                    iZzd = zzle.zzj(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 26:
                    iZzb = zzle.zza(i15, (List<?>) unsafe2.getObject(t, j2));
                    iZzb3 += iZzb;
                    i2 = 0;
                    z = false;
                    break;
                case 27:
                    iZzb = zzle.zza(i15, (List<?>) unsafe2.getObject(t, j2), zza(i13));
                    iZzb3 += iZzb;
                    i2 = 0;
                    z = false;
                    break;
                case 28:
                    iZzb = zzle.zzb(i15, (List) unsafe2.getObject(t, j2));
                    iZzb3 += iZzb;
                    i2 = 0;
                    z = false;
                    break;
                case 29:
                    iZzb = zzle.zzf(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzb;
                    i2 = 0;
                    z = false;
                    break;
                case 30:
                    i2 = 0;
                    iZzd = zzle.zzd(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 31:
                    i2 = 0;
                    iZzd = zzle.zzh(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 32:
                    i2 = 0;
                    iZzd = zzle.zzi(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 33:
                    i2 = 0;
                    iZzd = zzle.zzg(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 34:
                    i2 = 0;
                    iZzd = zzle.zzc(i15, (List) unsafe2.getObject(t, j2), false);
                    iZzb3 += iZzd;
                    z = false;
                    break;
                case 35:
                    iZzi = zzle.zzi((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 36:
                    iZzi = zzle.zzh((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 37:
                    iZzi = zzle.zza((List<Long>) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 38:
                    iZzi = zzle.zzb((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 39:
                    iZzi = zzle.zze((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 40:
                    iZzi = zzle.zzi((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 41:
                    iZzi = zzle.zzh((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 42:
                    iZzi = zzle.zzj((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 43:
                    iZzi = zzle.zzf((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 44:
                    iZzi = zzle.zzd((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 45:
                    iZzi = zzle.zzh((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 46:
                    iZzi = zzle.zzi((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 47:
                    iZzi = zzle.zzg((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 48:
                    iZzi = zzle.zzc((List) unsafe2.getObject(t, j2));
                    if (iZzi > 0) {
                        iZze = zzii.zze(i15);
                        iZzg = zzii.zzg(iZzi);
                        iZzj = iZze + iZzg + iZzi;
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 49:
                    iZzb = zzle.zzb(i15, (List<zzkk>) unsafe2.getObject(t, j2), zza(i13));
                    iZzb3 += iZzb;
                    i2 = 0;
                    z = false;
                    break;
                case 50:
                    iZzb = this.zzs.zza(i15, unsafe2.getObject(t, j2), zzb(i13));
                    iZzb3 += iZzb;
                    i2 = 0;
                    z = false;
                    break;
                case 51:
                    if (zza(t, i15, i13)) {
                        iZzb3 += zzii.zzb(i15, 0.0d);
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 52:
                    if (zza(t, i15, i13)) {
                        iZzj = zzii.zzb(i15, 0.0f);
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 53:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzd(i15, zze(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 54:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zze(i15, zze(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 55:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzf(i15, zzd(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 56:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzg(i15, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 57:
                    if (zza(t, i15, i13)) {
                        iZzj = zzii.zzi(i15, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 58:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzb(i15, true);
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 59:
                    if (zza(t, i15, i13)) {
                        Object object2 = unsafe2.getObject(t, j2);
                        if (object2 instanceof zzht) {
                            iZzb = zzii.zzc(i15, (zzht) object2);
                        } else {
                            iZzb = zzii.zzb(i15, (String) object2);
                        }
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 60:
                    if (zza(t, i15, i13)) {
                        iZzb = zzle.zza(i15, unsafe2.getObject(t, j2), zza(i13));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 61:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzc(i15, (zzht) unsafe2.getObject(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 62:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzg(i15, zzd(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 63:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzk(i15, zzd(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 64:
                    if (zza(t, i15, i13)) {
                        iZzj = zzii.zzj(i15, 0);
                        iZzb3 += iZzj;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 65:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzh(i15, 0L);
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 66:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzh(i15, zzd(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 67:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzf(i15, zze(t, j2));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                case 68:
                    if (zza(t, i15, i13)) {
                        iZzb = zzii.zzc(i15, (zzkk) unsafe2.getObject(t, j2), zza(i13));
                        iZzb3 += iZzb;
                    }
                    i2 = 0;
                    z = false;
                    break;
                default:
                    i2 = 0;
                    z = false;
                    break;
            }
            i13 += 3;
            i5 = i2;
            i4 = i16;
        }
        int iZzc = i5;
        int iZza = iZzb3 + zza((zzlu) this.zzq, (Object) t);
        if (!this.zzh) {
            return iZza;
        }
        zziu<T> zziuVarZza = this.zzr.zza(t);
        int i20 = iZzc;
        while (true) {
            int iZzc2 = zziuVarZza.zza.zzc();
            zzlh<T, Object> zzlhVar = zziuVarZza.zza;
            if (i20 < iZzc2) {
                Map.Entry entryZzb = zzlhVar.zzb(i20);
                MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(entryZzb.getKey());
                iZzc += zziu.zzc(null, entryZzb.getValue());
                i20++;
            } else {
                for (Map.Entry entry : zzlhVar.zzd()) {
                    MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(entry.getKey());
                    iZzc += zziu.zzc(null, entry.getValue());
                }
                return iZza + iZzc;
            }
        }
    }

    private static <UT, UB> int zza(zzlu<UT, UB> zzluVar, T t) {
        return zzluVar.zzf(zzluVar.zzb(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzma.zzf(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:592:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:761:0x054a  */
    @Override // com.google.android.gms.internal.vision.zzlc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r14, com.google.android.gms.internal.vision.zzmr r15) {
        /*
            Method dump skipped, instruction units count: 2916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, com.google.android.gms.internal.vision.zzmr):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:249:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzb(T r18, com.google.android.gms.internal.vision.zzmr r19) {
        /*
            Method dump skipped, instruction units count: 1342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zzb(java.lang.Object, com.google.android.gms.internal.vision.zzmr):void");
    }

    private final <K, V> void zza(zzmr zzmrVar, int i, Object obj, int i2) {
        if (obj != null) {
            this.zzs.zzb(zzb(i2));
            zzmrVar.zza(i, (zzkf) null, this.zzs.zzc(obj));
        }
    }

    private static <UT, UB> void zza(zzlu<UT, UB> zzluVar, T t, zzmr zzmrVar) {
        zzluVar.zza(zzluVar.zzb(t), zzmrVar);
    }

    private static zzlx zze(Object obj) {
        zzjb zzjbVar = (zzjb) obj;
        zzlx zzlxVar = zzjbVar.zzb;
        if (zzlxVar != zzlx.zza()) {
            return zzlxVar;
        }
        zzlx zzlxVarZzb = zzlx.zzb();
        zzjbVar.zzb = zzlxVarZzb;
        return zzlxVarZzb;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzhn zzhnVar) throws zzjk {
        int iZza;
        Unsafe unsafe = zzb;
        zzjl zzjlVarZza = (zzjl) unsafe.getObject(t, j2);
        if (!zzjlVarZza.zza()) {
            int size = zzjlVarZza.size();
            zzjlVarZza = zzjlVarZza.zza(size == 0 ? 10 : size << 1);
            unsafe.putObject(t, j2, zzjlVarZza);
        }
        zzjl zzjlVar = zzjlVarZza;
        switch (i7) {
            case 18:
            case 35:
                if (i5 != 2) {
                    if (i5 == 1) {
                        zzhl.zzc(bArr, i);
                        throw null;
                    }
                    return i;
                }
                int iZza2 = zzhl.zza(bArr, i, zzhnVar);
                int i8 = zzhnVar.zza + iZza2;
                if (iZza2 < i8) {
                    zzhl.zzc(bArr, iZza2);
                    throw null;
                }
                if (iZza2 == i8) {
                    return iZza2;
                }
                throw zzjk.zza();
            case 19:
            case 36:
                if (i5 != 2) {
                    if (i5 == 5) {
                        zzhl.zzd(bArr, i);
                        throw null;
                    }
                    return i;
                }
                int iZza3 = zzhl.zza(bArr, i, zzhnVar);
                int i9 = zzhnVar.zza + iZza3;
                if (iZza3 < i9) {
                    zzhl.zzd(bArr, iZza3);
                    throw null;
                }
                if (iZza3 == i9) {
                    return iZza3;
                }
                throw zzjk.zza();
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 != 2) {
                    if (i5 == 0) {
                        zzhl.zzb(bArr, i, zzhnVar);
                        long j3 = zzhnVar.zzb;
                        throw null;
                    }
                    return i;
                }
                int iZza4 = zzhl.zza(bArr, i, zzhnVar);
                int i10 = zzhnVar.zza + iZza4;
                if (iZza4 < i10) {
                    zzhl.zzb(bArr, iZza4, zzhnVar);
                    throw null;
                }
                if (iZza4 == i10) {
                    return iZza4;
                }
                throw zzjk.zza();
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzhl.zza(bArr, i, (zzjl<?>) zzjlVar, zzhnVar);
                }
                if (i5 == 0) {
                    return zzhl.zza(i3, bArr, i, i2, (zzjl<?>) zzjlVar, zzhnVar);
                }
                return i;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 != 2) {
                    if (i5 == 1) {
                        zzhl.zzb(bArr, i);
                        throw null;
                    }
                    return i;
                }
                int iZza5 = zzhl.zza(bArr, i, zzhnVar);
                int i11 = zzhnVar.zza + iZza5;
                if (iZza5 < i11) {
                    zzhl.zzb(bArr, iZza5);
                    throw null;
                }
                if (iZza5 == i11) {
                    return iZza5;
                }
                throw zzjk.zza();
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzjd zzjdVar = (zzjd) zzjlVar;
                    int iZza6 = zzhl.zza(bArr, i, zzhnVar);
                    int i12 = zzhnVar.zza + iZza6;
                    while (iZza6 < i12) {
                        zzjdVar.zzc(zzhl.zza(bArr, iZza6));
                        iZza6 += 4;
                    }
                    if (iZza6 == i12) {
                        return iZza6;
                    }
                    throw zzjk.zza();
                }
                if (i5 == 5) {
                    zzjd zzjdVar2 = (zzjd) zzjlVar;
                    zzjdVar2.zzc(zzhl.zza(bArr, i));
                    int i13 = i + 4;
                    while (i13 < i2) {
                        int iZza7 = zzhl.zza(bArr, i13, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return i13;
                        }
                        zzjdVar2.zzc(zzhl.zza(bArr, iZza7));
                        i13 = iZza7 + 4;
                    }
                    return i13;
                }
                return i;
            case 25:
            case 42:
                if (i5 != 2) {
                    if (i5 == 0) {
                        zzhl.zzb(bArr, i, zzhnVar);
                        long j4 = zzhnVar.zzb;
                        throw null;
                    }
                    return i;
                }
                int iZza8 = zzhl.zza(bArr, i, zzhnVar);
                int i14 = zzhnVar.zza + iZza8;
                if (iZza8 < i14) {
                    zzhl.zzb(bArr, iZza8, zzhnVar);
                    throw null;
                }
                if (iZza8 == i14) {
                    return iZza8;
                }
                throw zzjk.zza();
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        int iZza9 = zzhl.zza(bArr, i, zzhnVar);
                        int i15 = zzhnVar.zza;
                        if (i15 < 0) {
                            throw zzjk.zzb();
                        }
                        if (i15 == 0) {
                            zzjlVar.add(_UrlKt.FRAGMENT_ENCODE_SET);
                        } else {
                            zzjlVar.add(new String(bArr, iZza9, i15, zzjf.zza));
                            iZza9 += i15;
                        }
                        while (iZza9 < i2) {
                            int iZza10 = zzhl.zza(bArr, iZza9, zzhnVar);
                            if (i3 != zzhnVar.zza) {
                                return iZza9;
                            }
                            iZza9 = zzhl.zza(bArr, iZza10, zzhnVar);
                            int i16 = zzhnVar.zza;
                            if (i16 < 0) {
                                throw zzjk.zzb();
                            }
                            if (i16 == 0) {
                                zzjlVar.add(_UrlKt.FRAGMENT_ENCODE_SET);
                            } else {
                                zzjlVar.add(new String(bArr, iZza9, i16, zzjf.zza));
                                iZza9 += i16;
                            }
                        }
                        return iZza9;
                    }
                    int iZza11 = zzhl.zza(bArr, i, zzhnVar);
                    int i17 = zzhnVar.zza;
                    if (i17 < 0) {
                        throw zzjk.zzb();
                    }
                    if (i17 == 0) {
                        zzjlVar.add(_UrlKt.FRAGMENT_ENCODE_SET);
                    } else {
                        int i18 = iZza11 + i17;
                        if (!zzmd.zza(bArr, iZza11, i18)) {
                            throw zzjk.zzh();
                        }
                        zzjlVar.add(new String(bArr, iZza11, i17, zzjf.zza));
                        iZza11 = i18;
                    }
                    while (iZza11 < i2) {
                        int iZza12 = zzhl.zza(bArr, iZza11, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return iZza11;
                        }
                        iZza11 = zzhl.zza(bArr, iZza12, zzhnVar);
                        int i19 = zzhnVar.zza;
                        if (i19 < 0) {
                            throw zzjk.zzb();
                        }
                        if (i19 == 0) {
                            zzjlVar.add(_UrlKt.FRAGMENT_ENCODE_SET);
                        } else {
                            int i20 = iZza11 + i19;
                            if (!zzmd.zza(bArr, iZza11, i20)) {
                                throw zzjk.zzh();
                            }
                            zzjlVar.add(new String(bArr, iZza11, i19, zzjf.zza));
                            iZza11 = i20;
                        }
                    }
                    return iZza11;
                }
                return i;
            case 27:
                if (i5 == 2) {
                    return zzhl.zza(zza(i6), i3, bArr, i, i2, zzjlVar, zzhnVar);
                }
                return i;
            case 28:
                if (i5 == 2) {
                    int iZza13 = zzhl.zza(bArr, i, zzhnVar);
                    int i21 = zzhnVar.zza;
                    if (i21 < 0) {
                        throw zzjk.zzb();
                    }
                    if (i21 > bArr.length - iZza13) {
                        throw zzjk.zza();
                    }
                    if (i21 == 0) {
                        zzjlVar.add(zzht.zza);
                    } else {
                        zzjlVar.add(zzht.zza(bArr, iZza13, i21));
                        iZza13 += i21;
                    }
                    while (iZza13 < i2) {
                        int iZza14 = zzhl.zza(bArr, iZza13, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return iZza13;
                        }
                        iZza13 = zzhl.zza(bArr, iZza14, zzhnVar);
                        int i22 = zzhnVar.zza;
                        if (i22 < 0) {
                            throw zzjk.zzb();
                        }
                        if (i22 > bArr.length - iZza13) {
                            throw zzjk.zza();
                        }
                        if (i22 == 0) {
                            zzjlVar.add(zzht.zza);
                        } else {
                            zzjlVar.add(zzht.zza(bArr, iZza13, i22));
                            iZza13 += i22;
                        }
                    }
                    return iZza13;
                }
                return i;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        iZza = zzhl.zza(i3, bArr, i, i2, (zzjl<?>) zzjlVar, zzhnVar);
                    }
                    return i;
                }
                iZza = zzhl.zza(bArr, i, (zzjl<?>) zzjlVar, zzhnVar);
                zzjb zzjbVar = (zzjb) t;
                zzlx zzlxVar = zzjbVar.zzb;
                zzlx zzlxVar2 = (zzlx) zzle.zza(i4, zzjlVar, zzc(i6), zzlxVar != zzlx.zza() ? zzlxVar : null, this.zzq);
                if (zzlxVar2 != null) {
                    zzjbVar.zzb = zzlxVar2;
                }
                return iZza;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzjd zzjdVar3 = (zzjd) zzjlVar;
                    int iZza15 = zzhl.zza(bArr, i, zzhnVar);
                    int i23 = zzhnVar.zza + iZza15;
                    while (iZza15 < i23) {
                        iZza15 = zzhl.zza(bArr, iZza15, zzhnVar);
                        zzjdVar3.zzc(zzif.zze(zzhnVar.zza));
                    }
                    if (iZza15 == i23) {
                        return iZza15;
                    }
                    throw zzjk.zza();
                }
                if (i5 == 0) {
                    zzjd zzjdVar4 = (zzjd) zzjlVar;
                    int iZza16 = zzhl.zza(bArr, i, zzhnVar);
                    zzjdVar4.zzc(zzif.zze(zzhnVar.zza));
                    while (iZza16 < i2) {
                        int iZza17 = zzhl.zza(bArr, iZza16, zzhnVar);
                        if (i3 != zzhnVar.zza) {
                            return iZza16;
                        }
                        iZza16 = zzhl.zza(bArr, iZza17, zzhnVar);
                        zzjdVar4.zzc(zzif.zze(zzhnVar.zza));
                    }
                    return iZza16;
                }
                return i;
            case 34:
            case 48:
                if (i5 != 2) {
                    if (i5 == 0) {
                        zzhl.zzb(bArr, i, zzhnVar);
                        zzif.zza(zzhnVar.zzb);
                        throw null;
                    }
                    return i;
                }
                int iZza18 = zzhl.zza(bArr, i, zzhnVar);
                int i24 = zzhnVar.zza + iZza18;
                if (iZza18 >= i24) {
                    if (iZza18 == i24) {
                        return iZza18;
                    }
                    throw zzjk.zza();
                }
                zzhl.zzb(bArr, iZza18, zzhnVar);
                zzif.zza(zzhnVar.zzb);
                throw null;
            case 49:
                if (i5 == 3) {
                    zzlc zzlcVarZza = zza(i6);
                    int i25 = (i3 & (-8)) | 4;
                    int iZza19 = zzhl.zza(zzlcVarZza, bArr, i, i2, i25, zzhnVar);
                    zzhn zzhnVar2 = zzhnVar;
                    zzjlVar.add(zzhnVar2.zzc);
                    while (iZza19 < i2) {
                        int iZza20 = zzhl.zza(bArr, iZza19, zzhnVar2);
                        if (i3 != zzhnVar2.zza) {
                            return iZza19;
                        }
                        zzhn zzhnVar3 = zzhnVar2;
                        iZza19 = zzhl.zza(zzlcVarZza, bArr, iZza20, i2, i25, zzhnVar3);
                        zzjlVar.add(zzhnVar3.zzc);
                        zzhnVar2 = zzhnVar3;
                    }
                    return iZza19;
                }
                return i;
            default:
                return i;
        }
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, long j, zzhn zzhnVar) throws zzjk {
        Unsafe unsafe = zzb;
        Object objZzb = zzb(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzs.zzd(object)) {
            Object objZzf = this.zzs.zzf(objZzb);
            this.zzs.zza(objZzf, object);
            unsafe.putObject(t, j, objZzf);
            object = objZzf;
        }
        this.zzs.zzb(objZzb);
        this.zzs.zza(object);
        int iZza = zzhl.zza(bArr, i, zzhnVar);
        int i4 = zzhnVar.zza;
        if (i4 < 0 || i4 > i2 - iZza) {
            throw zzjk.zza();
        }
        throw null;
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzhn zzhnVar) throws zzjk {
        int i9;
        int i10;
        int iZzb;
        Object object;
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                unsafe.putObject(t, j, Double.valueOf(zzhl.zzc(bArr, i)));
                iZzb = i9 + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 52:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                unsafe.putObject(t, j, Float.valueOf(zzhl.zzd(bArr, i)));
                iZzb = i10 + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 53:
            case 54:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zzb(bArr, i, zzhnVar);
                unsafe.putObject(t, j, Long.valueOf(zzhnVar.zzb));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 55:
            case 62:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zza(bArr, i, zzhnVar);
                unsafe.putObject(t, j, Integer.valueOf(zzhnVar.zza));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 56:
            case 65:
                i9 = i;
                if (i5 != 1) {
                    return i9;
                }
                unsafe.putObject(t, j, Long.valueOf(zzhl.zzb(bArr, i)));
                iZzb = i9 + 8;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 57:
            case 64:
                i10 = i;
                if (i5 != 5) {
                    return i10;
                }
                unsafe.putObject(t, j, Integer.valueOf(zzhl.zza(bArr, i)));
                iZzb = i10 + 4;
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 58:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zzb(bArr, i, zzhnVar);
                unsafe.putObject(t, j, Boolean.valueOf(zzhnVar.zzb != 0));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 59:
                if (i5 != 2) {
                    return i;
                }
                int iZza = zzhl.zza(bArr, i, zzhnVar);
                int i11 = zzhnVar.zza;
                if (i11 == 0) {
                    unsafe.putObject(t, j, _UrlKt.FRAGMENT_ENCODE_SET);
                } else {
                    if ((i6 & 536870912) != 0 && !zzmd.zza(bArr, iZza, iZza + i11)) {
                        throw zzjk.zzh();
                    }
                    unsafe.putObject(t, j, new String(bArr, iZza, i11, zzjf.zza));
                    iZza += i11;
                }
                unsafe.putInt(t, j2, i4);
                return iZza;
            case 60:
                if (i5 != 2) {
                    return i;
                }
                int iZza2 = zzhl.zza(zza(i8), bArr, i, i2, zzhnVar);
                object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                if (object == null) {
                    unsafe.putObject(t, j, zzhnVar.zzc);
                } else {
                    unsafe.putObject(t, j, zzjf.zza(object, zzhnVar.zzc));
                }
                unsafe.putInt(t, j2, i4);
                return iZza2;
            case 61:
                if (i5 != 2) {
                    return i;
                }
                iZzb = zzhl.zze(bArr, i, zzhnVar);
                unsafe.putObject(t, j, zzhnVar.zzc);
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 63:
                if (i5 != 0) {
                    return i;
                }
                int iZza3 = zzhl.zza(bArr, i, zzhnVar);
                int i12 = zzhnVar.zza;
                zzjg zzjgVarZzc = zzc(i8);
                if (zzjgVarZzc == null || zzjgVarZzc.zza(i12)) {
                    unsafe.putObject(t, j, Integer.valueOf(i12));
                    iZzb = iZza3;
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
                zze(t).zza(i3, Long.valueOf(i12));
                return iZza3;
            case 66:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zza(bArr, i, zzhnVar);
                unsafe.putObject(t, j, Integer.valueOf(zzif.zze(zzhnVar.zza)));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 67:
                if (i5 != 0) {
                    return i;
                }
                iZzb = zzhl.zzb(bArr, i, zzhnVar);
                unsafe.putObject(t, j, Long.valueOf(zzif.zza(zzhnVar.zzb)));
                unsafe.putInt(t, j2, i4);
                return iZzb;
            case 68:
                if (i5 == 3) {
                    iZzb = zzhl.zza(zza(i8), bArr, i, i2, (i3 & (-8)) | 4, zzhnVar);
                    object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, zzhnVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzjf.zza(object, zzhnVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return iZzb;
                }
            default:
                return i;
        }
    }

    private final zzlc zza(int i) {
        int i2 = (i / 3) << 1;
        zzlc zzlcVar = (zzlc) this.zzd[i2];
        if (zzlcVar != null) {
            return zzlcVar;
        }
        zzlc<T> zzlcVarZza = zzky.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zzlcVarZza;
        return zzlcVarZza;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    private final zzjg zzc(int i) {
        return (zzjg) this.zzd[((i / 3) << 1) + 1];
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:260:0x008b. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:376:0x03ec A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:381:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0423  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zza(T r27, byte[] r28, int r29, int r30, int r31, com.google.android.gms.internal.vision.zzhn r32) throws com.google.android.gms.internal.vision.zzjk {
        /*
            Method dump skipped, instruction units count: 1206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.vision.zzhn):int");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:213:0x0087. Please report as an issue. */
    @Override // com.google.android.gms.internal.vision.zzlc
    public final void zza(T t, byte[] bArr, int i, int i2, zzhn zzhnVar) throws zzjk {
        int iZzg;
        T t2;
        Unsafe unsafe;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        zzko<T> zzkoVar = this;
        T t3 = t;
        byte[] bArr2 = bArr;
        int i13 = i2;
        zzhn zzhnVar2 = zzhnVar;
        if (zzkoVar.zzj) {
            Unsafe unsafe2 = zzb;
            int i14 = -1;
            int iZzb = i;
            int i15 = -1;
            int i16 = 0;
            int i17 = 0;
            int i18 = 1048575;
            while (iZzb < i13) {
                int iZza = iZzb + 1;
                int i19 = bArr2[iZzb];
                if (i19 < 0) {
                    iZza = zzhl.zza(i19, bArr2, iZza, zzhnVar2);
                    i19 = zzhnVar2.zza;
                }
                int i20 = iZza;
                int i21 = i19 >>> 3;
                int i22 = i19 & 7;
                if (i21 > i15) {
                    iZzg = zzkoVar.zza(i21, i16 / 3);
                } else {
                    iZzg = zzkoVar.zzg(i21);
                }
                if (iZzg == i14) {
                    t2 = t3;
                    unsafe = unsafe2;
                    i3 = i19;
                    i4 = i21;
                    i5 = 0;
                } else {
                    int[] iArr = zzkoVar.zzc;
                    int i23 = iArr[iZzg + 1];
                    int i24 = (i23 & 267386880) >>> 20;
                    int i25 = i19;
                    int i26 = iZzg;
                    long j = i23 & 1048575;
                    if (i24 <= 17) {
                        int i27 = iArr[i26 + 2];
                        int i28 = 1 << (i27 >>> 20);
                        int i29 = i27 & 1048575;
                        int i30 = 1048575;
                        if (i29 != i18) {
                            if (i18 != 1048575) {
                                unsafe2.putInt(t3, i18, i17);
                                i30 = 1048575;
                            }
                            if (i29 != i30) {
                                i17 = unsafe2.getInt(t3, i29);
                            }
                            i18 = i29;
                        }
                        switch (i24) {
                            case 0:
                                i11 = i30;
                                if (i22 != 1) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    zzma.zza(t3, j, zzhl.zzc(bArr2, i20));
                                    iZzb = i20 + 8;
                                    i17 |= i28;
                                    i13 = i2;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                }
                                break;
                            case 1:
                                i11 = i30;
                                if (i22 != 5) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    zzma.zza((Object) t3, j, zzhl.zzd(bArr2, i20));
                                    iZzb = i20 + 4;
                                    i17 |= i28;
                                    i13 = i2;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                }
                                break;
                            case 2:
                            case 3:
                                i11 = i30;
                                if (i22 != 0) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    int iZzb2 = zzhl.zzb(bArr2, i20, zzhnVar2);
                                    Unsafe unsafe3 = unsafe2;
                                    T t4 = t3;
                                    unsafe3.putLong(t4, j, zzhnVar2.zzb);
                                    unsafe2 = unsafe3;
                                    t3 = t4;
                                    i17 |= i28;
                                    iZzb = iZzb2;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                    i13 = i2;
                                }
                                break;
                            case 4:
                            case 11:
                                i11 = i30;
                                if (i22 != 0) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    int iZza2 = zzhl.zza(bArr2, i20, zzhnVar2);
                                    unsafe2.putInt(t3, j, zzhnVar2.zza);
                                    i17 |= i28;
                                    i13 = i2;
                                    iZzb = iZza2;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                }
                                break;
                            case 5:
                            case 14:
                                i11 = i30;
                                if (i22 != 1) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    Unsafe unsafe4 = unsafe2;
                                    T t5 = t3;
                                    unsafe4.putLong(t5, j, zzhl.zzb(bArr2, i20));
                                    unsafe2 = unsafe4;
                                    t3 = t5;
                                    iZzb = i20 + 8;
                                    i17 |= i28;
                                    i13 = i2;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                }
                                break;
                            case 6:
                            case 13:
                                i11 = i30;
                                if (i22 != 5) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    unsafe2.putInt(t3, j, zzhl.zza(bArr2, i20));
                                    iZzb = i20 + 4;
                                    i17 |= i28;
                                    i13 = i2;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                }
                                break;
                            case 7:
                                i11 = i30;
                                if (i22 != 0) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    iZzb = zzhl.zzb(bArr2, i20, zzhnVar2);
                                    zzma.zza(t3, j, zzhnVar2.zzb != 0);
                                    i17 |= i28;
                                    i13 = i2;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                }
                                break;
                            case 8:
                                i11 = i30;
                                if (i22 != 2) {
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    if ((536870912 & i23) == 0) {
                                        iZzb = zzhl.zzc(bArr2, i20, zzhnVar2);
                                    } else {
                                        iZzb = zzhl.zzd(bArr2, i20, zzhnVar2);
                                    }
                                    unsafe2.putObject(t3, j, zzhnVar2.zzc);
                                    i17 |= i28;
                                    i15 = i21;
                                    i16 = i26;
                                    i14 = -1;
                                }
                                break;
                            case 9:
                                i11 = i30;
                                i12 = i26;
                                if (i22 != 2) {
                                    i26 = i12;
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    iZzb = zzhl.zza(zzkoVar.zza(i12), bArr2, i20, i13, zzhnVar2);
                                    Object object = unsafe2.getObject(t3, j);
                                    if (object == null) {
                                        unsafe2.putObject(t3, j, zzhnVar2.zzc);
                                    } else {
                                        unsafe2.putObject(t3, j, zzjf.zza(object, zzhnVar2.zzc));
                                    }
                                    i17 |= i28;
                                    i15 = i21;
                                    i16 = i12;
                                    i14 = -1;
                                }
                                break;
                            case 10:
                                i11 = i30;
                                i12 = i26;
                                if (i22 != 2) {
                                    i26 = i12;
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    iZzb = zzhl.zze(bArr2, i20, zzhnVar2);
                                    unsafe2.putObject(t3, j, zzhnVar2.zzc);
                                    i17 |= i28;
                                    i15 = i21;
                                    i16 = i12;
                                    i14 = -1;
                                }
                                break;
                            case 12:
                                i11 = i30;
                                i12 = i26;
                                if (i22 != 0) {
                                    i26 = i12;
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    iZzb = zzhl.zza(bArr2, i20, zzhnVar2);
                                    unsafe2.putInt(t3, j, zzhnVar2.zza);
                                    i17 |= i28;
                                    i15 = i21;
                                    i16 = i12;
                                    i14 = -1;
                                }
                                break;
                            case 15:
                                i11 = i30;
                                i12 = i26;
                                if (i22 != 0) {
                                    i26 = i12;
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    iZzb = zzhl.zza(bArr2, i20, zzhnVar2);
                                    unsafe2.putInt(t3, j, zzif.zze(zzhnVar2.zza));
                                    i17 |= i28;
                                    i15 = i21;
                                    i16 = i12;
                                    i14 = -1;
                                }
                                break;
                            case 16:
                                if (i22 != 0) {
                                    i11 = i30;
                                    t2 = t3;
                                    unsafe = unsafe2;
                                    i4 = i21;
                                    i5 = i26;
                                    i3 = i25 == true ? 1 : 0;
                                } else {
                                    int iZzb3 = zzhl.zzb(bArr2, i20, zzhnVar2);
                                    Unsafe unsafe5 = unsafe2;
                                    T t6 = t3;
                                    i12 = i26;
                                    unsafe5.putLong(t6, j, zzif.zza(zzhnVar2.zzb));
                                    unsafe2 = unsafe5;
                                    t3 = t6;
                                    i17 |= i28;
                                    iZzb = iZzb3;
                                    i15 = i21;
                                    i16 = i12;
                                    i14 = -1;
                                }
                                break;
                            default:
                                i11 = i30;
                                t2 = t3;
                                unsafe = unsafe2;
                                i4 = i21;
                                i5 = i26;
                                i3 = i25 == true ? 1 : 0;
                                break;
                        }
                    } else {
                        i5 = i26;
                        if (i24 != 27) {
                            i6 = i20;
                            Unsafe unsafe6 = unsafe2;
                            if (i24 <= 49) {
                                int i31 = i18;
                                i7 = i17;
                                unsafe = unsafe6;
                                int iZza3 = zzkoVar.zza(t, bArr, i6, i2, i25 == true ? 1 : 0, i21, i22, i5, i23, i24, j, zzhnVar);
                                if (iZza3 == i6) {
                                    i20 = iZza3;
                                    i4 = i21;
                                    i3 = i25 == true ? 1 : 0;
                                    i17 = i7;
                                    t2 = t;
                                    i18 = i31;
                                } else {
                                    zzkoVar = this;
                                    t3 = t;
                                    i18 = i31;
                                    zzhnVar2 = zzhnVar;
                                    iZzb = iZza3;
                                    i16 = i5;
                                    i15 = i21;
                                    i17 = i7;
                                    unsafe2 = unsafe;
                                    i14 = -1;
                                    bArr2 = bArr;
                                    i13 = i2;
                                }
                            } else {
                                i7 = i17;
                                unsafe = unsafe6;
                                i8 = i21;
                                i9 = i18;
                                i10 = i25 == true ? 1 : 0;
                                if (i24 != 50) {
                                    i4 = i8;
                                    int iZza4 = zza(t, bArr, i6, i2, i10 == true ? 1 : 0, i4, i22, i23, i24, j, i5, zzhnVar);
                                    t2 = t;
                                    i3 = i10 == true ? 1 : 0;
                                    i5 = i5;
                                    if (iZza4 == i6) {
                                        i20 = iZza4;
                                        i18 = i9;
                                        i17 = i7;
                                    } else {
                                        zzkoVar = this;
                                        zzhnVar2 = zzhnVar;
                                        i15 = i4;
                                        iZzb = iZza4;
                                        i16 = i5;
                                        t3 = t2;
                                        i18 = i9;
                                        i17 = i7;
                                        unsafe2 = unsafe;
                                        i14 = -1;
                                        bArr2 = bArr;
                                        i13 = i2;
                                    }
                                } else if (i22 == 2) {
                                    int iZza5 = zza(t, bArr, i6, i2, i5, j, zzhnVar);
                                    i5 = i5;
                                    if (iZza5 == i6) {
                                        i20 = iZza5;
                                        i4 = i8;
                                        i3 = i10;
                                        i18 = i9;
                                        i17 = i7;
                                        t2 = t;
                                    } else {
                                        zzkoVar = this;
                                        t3 = t;
                                        bArr2 = bArr;
                                        zzhnVar2 = zzhnVar;
                                        iZzb = iZza5;
                                        i16 = i5;
                                        i15 = i8;
                                        i18 = i9;
                                        i17 = i7;
                                        unsafe2 = unsafe;
                                        i14 = -1;
                                        i13 = i2;
                                    }
                                } else {
                                    i5 = i5;
                                    i20 = i6;
                                    i4 = i8;
                                    i3 = i10;
                                    i18 = i9;
                                    i17 = i7;
                                    t2 = t;
                                }
                            }
                        } else if (i22 == 2) {
                            zzjl zzjlVarZza = (zzjl) unsafe2.getObject(t3, j);
                            if (!zzjlVarZza.zza()) {
                                int size = zzjlVarZza.size();
                                zzjlVarZza = zzjlVarZza.zza(size == 0 ? 10 : size << 1);
                                unsafe2.putObject(t3, j, zzjlVarZza);
                            }
                            int iZza6 = zzhl.zza(zzkoVar.zza(i5), i25 == true ? 1 : 0, bArr2, i20, i2, zzjlVarZza, zzhnVar2);
                            bArr2 = bArr;
                            zzhnVar2 = zzhnVar;
                            iZzb = iZza6;
                            i16 = i5;
                            unsafe2 = unsafe2;
                            i15 = i21;
                            i14 = -1;
                            t3 = t;
                            i13 = i2;
                        } else {
                            i6 = i20;
                            i7 = i17;
                            unsafe = unsafe2;
                            i8 = i21;
                            i9 = i18;
                            i10 = i25 == true ? 1 : 0;
                            i20 = i6;
                            i4 = i8;
                            i3 = i10;
                            i18 = i9;
                            i17 = i7;
                            t2 = t;
                        }
                    }
                }
                int iZza7 = zzhl.zza(i3 == true ? 1 : 0, bArr, i20, i2, zze(t2), zzhnVar);
                bArr2 = bArr;
                zzhnVar2 = zzhnVar;
                i15 = i4;
                i16 = i5;
                t3 = t2;
                unsafe2 = unsafe;
                i14 = -1;
                i13 = i2;
                iZzb = iZza7;
                zzkoVar = this;
            }
            T t7 = t3;
            Unsafe unsafe7 = unsafe2;
            int i32 = i13;
            int i33 = i18;
            int i34 = i17;
            if (i33 != 1048575) {
                unsafe7.putInt(t7, i33, i34);
            }
            if (iZzb != i32) {
                throw zzjk.zzg();
            }
            return;
        }
        zza(t3, bArr, i, i13, 0, zzhnVar);
    }

    @Override // com.google.android.gms.internal.vision.zzlc
    public final void zzc(T t) {
        int i;
        int[] iArr;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            iArr = this.zzl;
            if (i2 >= i) {
                break;
            }
            long jZzd = zzd(iArr[i2]) & 1048575;
            Object objZzf = zzma.zzf(t, jZzd);
            if (objZzf != null) {
                zzma.zza(t, jZzd, this.zzs.zze(objZzf));
            }
            i2++;
        }
        int length = iArr.length;
        while (i < length) {
            this.zzp.zzb(t, this.zzl[i]);
            i++;
        }
        this.zzq.zzd(t);
        if (this.zzh) {
            this.zzr.zzc(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzlu<UT, UB> zzluVar) {
        zzjg zzjgVarZzc;
        int i2 = this.zzc[i];
        Object objZzf = zzma.zzf(obj, zzd(i) & 1048575);
        return (objZzf == null || (zzjgVarZzc = zzc(i)) == null) ? ub : (UB) zza(i, i2, this.zzs.zza(objZzf), zzjgVarZzc, ub, zzluVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzjg zzjgVar, UB ub, zzlu<UT, UB> zzluVar) {
        this.zzs.zzb(zzb(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzjgVar.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzluVar.zza();
                }
                zzib zzibVarZzc = zzht.zzc(zzkc.zza(null, next.getKey(), next.getValue()));
                try {
                    zzkc.zza(zzibVarZzc.zzb(), null, next.getKey(), next.getValue());
                    zzluVar.zza(ub, i2, zzibVarZzc.zza());
                    it.remove();
                } catch (IOException e) {
                    HttpUrl$$ExternalSyntheticBUOutline0.m958m(e);
                    return null;
                }
            }
        }
        return ub;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0096  */
    @Override // com.google.android.gms.internal.vision.zzlc
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzd(T r15) {
        /*
            Method dump skipped, instruction units count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zzd(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzlc zzlcVar) {
        return zzlcVar.zzd(zzma.zzf(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzmr zzmrVar) {
        if (obj instanceof String) {
            zzmrVar.zza(i, (String) obj);
        } else {
            zzmrVar.zza(i, (zzht) obj);
        }
    }

    private final int zzd(int i) {
        return this.zzc[i + 1];
    }

    private final int zze(int i) {
        return this.zzc[i + 2];
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzma.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzma.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzma.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzma.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzma.zzf(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((Object) t, i) == zza((Object) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza((Object) t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t, int i) {
        int iZze = zze(i);
        long j = iZze & 1048575;
        if (j != 1048575) {
            return ((1 << (iZze >>> 20)) & zzma.zza(t, j)) != 0;
        }
        int iZzd = zzd(i);
        long j2 = iZzd & 1048575;
        switch ((iZzd & 267386880) >>> 20) {
            case 0:
                return zzma.zze(t, j2) != 0.0d;
            case 1:
                return zzma.zzd(t, j2) != 0.0f;
            case 2:
                return zzma.zzb(t, j2) != 0;
            case 3:
                return zzma.zzb(t, j2) != 0;
            case 4:
                return zzma.zza(t, j2) != 0;
            case 5:
                return zzma.zzb(t, j2) != 0;
            case 6:
                return zzma.zza(t, j2) != 0;
            case 7:
                return zzma.zzc(t, j2);
            case 8:
                Object objZzf = zzma.zzf(t, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzht) {
                    return !zzht.zza.equals(objZzf);
                }
                Segment$$ExternalSyntheticBUOutline0.m991m();
                return false;
            case 9:
                return zzma.zzf(t, j2) != null;
            case 10:
                return !zzht.zza.equals(zzma.zzf(t, j2));
            case 11:
                return zzma.zza(t, j2) != 0;
            case 12:
                return zzma.zza(t, j2) != 0;
            case 13:
                return zzma.zza(t, j2) != 0;
            case 14:
                return zzma.zzb(t, j2) != 0;
            case 15:
                return zzma.zza(t, j2) != 0;
            case 16:
                return zzma.zzb(t, j2) != 0;
            case 17:
                return zzma.zzf(t, j2) != null;
            default:
                Segment$$ExternalSyntheticBUOutline0.m991m();
                return false;
        }
    }

    private final void zzb(T t, int i) {
        int iZze = zze(i);
        long j = 1048575 & iZze;
        if (j == 1048575) {
            return;
        }
        zzma.zza((Object) t, j, (1 << (iZze >>> 20)) | zzma.zza(t, j));
    }

    private final boolean zza(T t, int i, int i2) {
        return zzma.zza(t, (long) (zze(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzma.zza((Object) t, zze(i2) & 1048575, i);
    }

    private final int zzg(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, 0);
    }

    private final int zza(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, i2);
    }

    private final int zzb(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }
}
