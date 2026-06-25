package com.google.android.recaptcha.internal;

import java.util.Iterator;
import java.util.Map;
import okio.Segment$$ExternalSyntheticBUOutline1;
import org.webrtc.GlShader$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
final class zzij {
    private static final zzij zzb = new zzij(true);
    final zzle zza = new zzku(16);
    private boolean zzc;
    private boolean zzd;

    private zzij() {
    }

    public static int zza(zzii zziiVar, Object obj) {
        int iZzd;
        int iZzy;
        zzmb zzmbVarZzd = zziiVar.zzd();
        int iZza = zziiVar.zza();
        zziiVar.zzg();
        int iZzy2 = zzhh.zzy(iZza << 3);
        if (zzmbVarZzd == zzmb.zzj) {
            byte[] bArr = zzjc.zzd;
            if (((zzke) obj) instanceof zzgg) {
                throw null;
            }
            iZzy2 += iZzy2;
        }
        zzmc zzmcVar = zzmc.INT;
        int iZzz = 4;
        switch (zzmbVarZzd.ordinal()) {
            case 0:
                ((Double) obj).getClass();
                iZzz = 8;
                return iZzy2 + iZzz;
            case 1:
                ((Float) obj).getClass();
                return iZzy2 + iZzz;
            case 2:
                iZzz = zzhh.zzz(((Long) obj).longValue());
                return iZzy2 + iZzz;
            case 3:
                iZzz = zzhh.zzz(((Long) obj).longValue());
                return iZzy2 + iZzz;
            case 4:
                iZzz = zzhh.zzu(((Integer) obj).intValue());
                return iZzy2 + iZzz;
            case 5:
                ((Long) obj).getClass();
                iZzz = 8;
                return iZzy2 + iZzz;
            case 6:
                ((Integer) obj).getClass();
                return iZzy2 + iZzz;
            case 7:
                ((Boolean) obj).getClass();
                iZzz = 1;
                return iZzy2 + iZzz;
            case 8:
                if (!(obj instanceof zzgw)) {
                    iZzz = zzhh.zzx((String) obj);
                    return iZzy2 + iZzz;
                }
                iZzd = ((zzgw) obj).zzd();
                iZzy = zzhh.zzy(iZzd);
                iZzz = iZzy + iZzd;
                return iZzy2 + iZzz;
            case 9:
                iZzz = ((zzke) obj).zzn();
                return iZzy2 + iZzz;
            case 10:
                if (!(obj instanceof zzjj)) {
                    iZzz = zzhh.zzv((zzke) obj);
                    return iZzy2 + iZzz;
                }
                iZzd = ((zzjj) obj).zza();
                iZzy = zzhh.zzy(iZzd);
                iZzz = iZzy + iZzd;
                return iZzy2 + iZzz;
            case 11:
                if (obj instanceof zzgw) {
                    iZzd = ((zzgw) obj).zzd();
                    iZzy = zzhh.zzy(iZzd);
                } else {
                    iZzd = ((byte[]) obj).length;
                    iZzy = zzhh.zzy(iZzd);
                }
                iZzz = iZzy + iZzd;
                return iZzy2 + iZzz;
            case 12:
                iZzz = zzhh.zzy(((Integer) obj).intValue());
                return iZzy2 + iZzz;
            case 13:
                iZzz = obj instanceof zziv ? zzhh.zzu(((zziv) obj).zza()) : zzhh.zzu(((Integer) obj).intValue());
                return iZzy2 + iZzz;
            case 14:
                ((Integer) obj).getClass();
                return iZzy2 + iZzz;
            case 15:
                ((Long) obj).getClass();
                iZzz = 8;
                return iZzy2 + iZzz;
            case 16:
                int iIntValue = ((Integer) obj).intValue();
                iZzz = zzhh.zzy((iIntValue >> 31) ^ (iIntValue + iIntValue));
                return iZzy2 + iZzz;
            case 17:
                long jLongValue = ((Long) obj).longValue();
                iZzz = zzhh.zzz((jLongValue >> 63) ^ (jLongValue + jLongValue));
                return iZzy2 + iZzz;
            default:
                GlShader$$ExternalSyntheticBUOutline1.m1250m("There is no way to get here, but the compiler thinks otherwise.");
                return 0;
        }
    }

    public static zzij zzd() {
        return zzb;
    }

    private static Object zzl(Object obj) {
        if (obj instanceof zzkj) {
            return ((zzkj) obj).zzd();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    private final void zzm(Map.Entry entry) {
        zzke zzkeVarZzj;
        zzii zziiVar = (zzii) entry.getKey();
        Object value = entry.getValue();
        boolean z = value instanceof zzjj;
        zziiVar.zzg();
        if (zziiVar.zze() != zzmc.MESSAGE) {
            if (z) {
                Segment$$ExternalSyntheticBUOutline1.m992m("Lazy fields must be message-valued");
                return;
            } else {
                this.zza.put(zziiVar, zzl(value));
                return;
            }
        }
        Object objZze = zze(zziiVar);
        if (objZze == null) {
            this.zza.put(zziiVar, zzl(value));
            if (z) {
                this.zzd = true;
                return;
            }
            return;
        }
        if (z) {
            throw null;
        }
        if (objZze instanceof zzkj) {
            zzkeVarZzj = zziiVar.zzc((zzkj) objZze, (zzkj) value);
        } else {
            zzkd zzkdVarZzX = ((zzke) objZze).zzX();
            zziiVar.zzb(zzkdVarZzX, (zzke) value);
            zzkeVarZzj = zzkdVarZzX.zzj();
        }
        this.zza.put(zziiVar, zzkeVarZzj);
    }

    private static boolean zzn(Map.Entry entry) {
        zzii zziiVar = (zzii) entry.getKey();
        if (zziiVar.zze() != zzmc.MESSAGE) {
            return true;
        }
        zziiVar.zzg();
        Object value = entry.getValue();
        if (value instanceof zzkf) {
            return ((zzkf) value).zzo();
        }
        if (value instanceof zzjj) {
            return true;
        }
        g$$ExternalSyntheticBUOutline1.m207m("Wrong object type used with protocol message reflection.");
        return false;
    }

    private static final int zzo(Map.Entry entry) {
        zzii zziiVar = (zzii) entry.getKey();
        Object value = entry.getValue();
        if (zziiVar.zze() != zzmc.MESSAGE) {
            return zza(zziiVar, value);
        }
        zziiVar.zzg();
        zziiVar.zzf();
        if (!(value instanceof zzjj)) {
            int iZzy = zzhh.zzy(((zzii) entry.getKey()).zza());
            int iZzy2 = zzhh.zzy(24) + zzhh.zzv((zzke) value);
            int iZzy3 = zzhh.zzy(16);
            int iZzy4 = zzhh.zzy(8);
            return iZzy4 + iZzy4 + iZzy3 + iZzy + iZzy2;
        }
        int iZzy5 = zzhh.zzy(((zzii) entry.getKey()).zza());
        int iZza = ((zzjj) value).zza();
        int iZzy6 = zzhh.zzy(iZza) + iZza;
        int iZzy7 = zzhh.zzy(24);
        int iZzy8 = zzhh.zzy(16);
        int iZzy9 = zzhh.zzy(8);
        return iZzy9 + iZzy9 + iZzy8 + iZzy5 + iZzy7 + iZzy6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzij) {
            return this.zza.equals(((zzij) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final int zzb() {
        zzle zzleVar;
        int i = 0;
        int iZzo = 0;
        while (true) {
            int iZzb = this.zza.zzb();
            zzleVar = this.zza;
            if (i >= iZzb) {
                break;
            }
            iZzo += zzo(zzleVar.zzg(i));
            i++;
        }
        Iterator it = zzleVar.zzc().iterator();
        while (it.hasNext()) {
            iZzo += zzo((Map.Entry) it.next());
        }
        return iZzo;
    }

    /* JADX INFO: renamed from: zzc */
    public final zzij clone() {
        zzle zzleVar;
        zzij zzijVar = new zzij();
        int i = 0;
        while (true) {
            int iZzb = this.zza.zzb();
            zzleVar = this.zza;
            if (i >= iZzb) {
                break;
            }
            Map.Entry entryZzg = zzleVar.zzg(i);
            zzijVar.zzi((zzii) entryZzg.getKey(), entryZzg.getValue());
            i++;
        }
        for (Map.Entry entry : zzleVar.zzc()) {
            zzijVar.zzi((zzii) entry.getKey(), entry.getValue());
        }
        zzijVar.zzd = this.zzd;
        return zzijVar;
    }

    public final Object zze(zzii zziiVar) {
        Object obj = this.zza.get(zziiVar);
        if (obj instanceof zzjj) {
            throw null;
        }
        return obj;
    }

    public final Iterator zzf() {
        boolean z = this.zzd;
        zzle zzleVar = this.zza;
        return z ? new zzji(zzleVar.entrySet().iterator()) : zzleVar.entrySet().iterator();
    }

    public final void zzg() {
        if (this.zzc) {
            return;
        }
        int i = 0;
        while (true) {
            int iZzb = this.zza.zzb();
            zzle zzleVar = this.zza;
            if (i >= iZzb) {
                zzleVar.zza();
                this.zzc = true;
                return;
            } else {
                Map.Entry entryZzg = zzleVar.zzg(i);
                if (entryZzg.getValue() instanceof zzit) {
                    ((zzit) entryZzg.getValue()).zzB();
                }
                i++;
            }
        }
    }

    public final void zzh(zzij zzijVar) {
        zzle zzleVar;
        int i = 0;
        while (true) {
            int iZzb = zzijVar.zza.zzb();
            zzleVar = zzijVar.zza;
            if (i >= iZzb) {
                break;
            }
            zzm(zzleVar.zzg(i));
            i++;
        }
        Iterator it = zzleVar.zzc().iterator();
        while (it.hasNext()) {
            zzm((Map.Entry) it.next());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0022, code lost:
    
        if ((r4 instanceof com.google.android.recaptcha.internal.zzjj) == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x002b, code lost:
    
        if ((r4 instanceof com.google.android.recaptcha.internal.zziv) == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0034, code lost:
    
        if ((r4 instanceof byte[]) == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0048, code lost:
    
        if (r0 == false) goto L66;
     */
    /* JADX WARN: Removed duplicated region for block: B:63:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzi(com.google.android.recaptcha.internal.zzii r3, java.lang.Object r4) {
        /*
            r2 = this;
            r3.zzg()
            com.google.android.recaptcha.internal.zzmb r0 = r3.zzd()
            byte[] r1 = com.google.android.recaptcha.internal.zzjc.zzd
            r4.getClass()
            com.google.android.recaptcha.internal.zzmb r1 = com.google.android.recaptcha.internal.zzmb.zza
            com.google.android.recaptcha.internal.zzmc r1 = com.google.android.recaptcha.internal.zzmc.INT
            com.google.android.recaptcha.internal.zzmc r0 = r0.zza()
            int r0 = r0.ordinal()
            switch(r0) {
                case 0: goto L46;
                case 1: goto L43;
                case 2: goto L40;
                case 3: goto L3d;
                case 4: goto L3a;
                case 5: goto L37;
                case 6: goto L2e;
                case 7: goto L25;
                case 8: goto L1c;
                default: goto L1b;
            }
        L1b:
            goto L57
        L1c:
            boolean r0 = r4 instanceof com.google.android.recaptcha.internal.zzke
            if (r0 != 0) goto L4a
            boolean r0 = r4 instanceof com.google.android.recaptcha.internal.zzjj
            if (r0 == 0) goto L57
            goto L4a
        L25:
            boolean r0 = r4 instanceof java.lang.Integer
            if (r0 != 0) goto L4a
            boolean r0 = r4 instanceof com.google.android.recaptcha.internal.zziv
            if (r0 == 0) goto L57
            goto L4a
        L2e:
            boolean r0 = r4 instanceof com.google.android.recaptcha.internal.zzgw
            if (r0 != 0) goto L4a
            boolean r0 = r4 instanceof byte[]
            if (r0 == 0) goto L57
            goto L4a
        L37:
            boolean r0 = r4 instanceof java.lang.String
            goto L48
        L3a:
            boolean r0 = r4 instanceof java.lang.Boolean
            goto L48
        L3d:
            boolean r0 = r4 instanceof java.lang.Double
            goto L48
        L40:
            boolean r0 = r4 instanceof java.lang.Float
            goto L48
        L43:
            boolean r0 = r4 instanceof java.lang.Long
            goto L48
        L46:
            boolean r0 = r4 instanceof java.lang.Integer
        L48:
            if (r0 == 0) goto L57
        L4a:
            boolean r0 = r4 instanceof com.google.android.recaptcha.internal.zzjj
            if (r0 == 0) goto L51
            r0 = 1
            r2.zzd = r0
        L51:
            com.google.android.recaptcha.internal.zzle r2 = r2.zza
            r2.put(r3, r4)
            return
        L57:
            int r2 = r3.zza()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            com.google.android.recaptcha.internal.zzmb r3 = r3.zzd()
            com.google.android.recaptcha.internal.zzmc r3 = r3.zza()
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getName()
            java.lang.Object[] r2 = new java.lang.Object[]{r2, r3, r4}
            java.lang.String r3 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            androidx.camera.core.CameraSelector$$ExternalSyntheticBUOutline0.m71m(r3, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.recaptcha.internal.zzij.zzi(com.google.android.recaptcha.internal.zzii, java.lang.Object):void");
    }

    public final boolean zzj() {
        return this.zzc;
    }

    public final boolean zzk() {
        int i = 0;
        while (true) {
            int iZzb = this.zza.zzb();
            zzle zzleVar = this.zza;
            if (i >= iZzb) {
                Iterator it = zzleVar.zzc().iterator();
                while (it.hasNext()) {
                    if (!zzn((Map.Entry) it.next())) {
                        return false;
                    }
                }
                return true;
            }
            if (!zzn(zzleVar.zzg(i))) {
                return false;
            }
            i++;
        }
    }

    private zzij(boolean z) {
        zzg();
        zzg();
    }
}
