package com.google.android.recaptcha.internal;

import android.app.Application;
import com.google.android.gms.tasks.Task;
import com.google.android.recaptcha.RecaptchaAction;
import com.google.android.recaptcha.RecaptchaClient;
import com.google.android.recaptcha.RecaptchaTasksClient;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* JADX INFO: loaded from: classes5.dex */
public final class zzaw implements RecaptchaClient, RecaptchaTasksClient {
    public static final zzan zza = new zzan(null);
    private static final Regex zzb = new Regex("^[a-zA-Z0-9/_]{0,100}$");
    private final Application zzc;
    private final zzg zzd;
    private final String zze;
    private final zzab zzf;
    private final zzoe zzg;
    private final zzbd zzh;
    private final zzbg zzi;
    private final zzq zzj;
    private final zzbs zzk;
    private final zzt zzl;

    public zzaw(Application application, zzg zzgVar, String str, zzt zztVar, zzab zzabVar, zzoe zzoeVar, zzbd zzbdVar, zzbg zzbgVar, zzq zzqVar, zzbs zzbsVar) {
        this.zzc = application;
        this.zzd = zzgVar;
        this.zze = str;
        this.zzl = zztVar;
        this.zzf = zzabVar;
        this.zzg = zzoeVar;
        this.zzh = zzbdVar;
        this.zzi = zzbgVar;
        this.zzj = zzqVar;
        this.zzk = zzbsVar;
    }

    public static final /* synthetic */ void zzi(zzaw zzawVar, long j, RecaptchaAction recaptchaAction, zzbd zzbdVar) throws zzp {
        zzbb zzbbVarZza = zzbdVar.zza(zzne.EXECUTE_NATIVE);
        zzbg zzbgVar = zzawVar.zzi;
        zzbgVar.zze.put(zzbbVarZza, new zzbf(zzbbVarZza, zzbgVar.zza, new zzac()));
        zzp zzpVar = !zzb.matches(recaptchaAction.getAction()) ? new zzp(zzn.zzi, zzl.zzq, null) : null;
        if (j < 5000) {
            zzpVar = new zzp(zzn.zzc, zzl.zzT, null);
        }
        zzbg zzbgVar2 = zzawVar.zzi;
        if (zzpVar == null) {
            zzbgVar2.zza(zzbbVarZza);
        } else {
            zzbgVar2.zzb(zzbbVarZza, zzpVar, null);
            throw zzpVar;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object zzj(long r6, java.lang.String r8, com.google.android.recaptcha.internal.zzbd r9, kotlin.coroutines.Continuation r10) throws com.google.android.recaptcha.internal.zzp {
        /*
            r5 = this;
            boolean r0 = r10 instanceof com.google.android.recaptcha.internal.zzao
            if (r0 == 0) goto L13
            r0 = r10
            com.google.android.recaptcha.internal.zzao r0 = (com.google.android.recaptcha.internal.zzao) r0
            int r1 = r0.zzc
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.zzc = r1
            goto L18
        L13:
            com.google.android.recaptcha.internal.zzao r0 = new com.google.android.recaptcha.internal.zzao
            r0.<init>(r5, r10)
        L18:
            java.lang.Object r10 = r0.zza
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.zzc
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L34
            com.google.android.recaptcha.internal.zzbb r5 = r0.zze
            com.google.android.recaptcha.internal.zzaw r6 = r0.zzd
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Exception -> L30
            r9 = r5
            r5 = r6
            goto L57
        L30:
            r7 = move-exception
            r9 = r5
            r5 = r6
            goto L63
        L34:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r5)
            return r4
        L3a:
            kotlin.ResultKt.throwOnFailure(r10)
            com.google.android.recaptcha.internal.zzne r10 = com.google.android.recaptcha.internal.zzne.COLLECT_SIGNALS
            com.google.android.recaptcha.internal.zzbb r9 = r9.zza(r10)
            com.google.android.recaptcha.internal.zzbg r10 = r5.zzi
            r2 = 2
            com.google.android.recaptcha.internal.zzbg.zzc(r10, r9, r4, r2, r4)
            com.google.android.recaptcha.internal.zzg r10 = r5.zzd     // Catch: java.lang.Exception -> L5f
            r0.zzd = r5     // Catch: java.lang.Exception -> L5f
            r0.zze = r9     // Catch: java.lang.Exception -> L5f
            r0.zzc = r3     // Catch: java.lang.Exception -> L5f
            java.lang.Object r10 = r10.zza(r8, r6, r0)     // Catch: java.lang.Exception -> L5f
            if (r10 == r1) goto L62
        L57:
            com.google.android.recaptcha.internal.zzog r10 = (com.google.android.recaptcha.internal.zzog) r10     // Catch: java.lang.Exception -> L5f
            com.google.android.recaptcha.internal.zzbg r6 = r5.zzi     // Catch: java.lang.Exception -> L5f
            r6.zza(r9)     // Catch: java.lang.Exception -> L5f
            return r10
        L5f:
            r6 = move-exception
            r7 = r6
            goto L63
        L62:
            return r1
        L63:
            boolean r6 = r7 instanceof com.google.android.recaptcha.internal.zzp
            if (r6 == 0) goto L6a
            com.google.android.recaptcha.internal.zzp r7 = (com.google.android.recaptcha.internal.zzp) r7
            goto L73
        L6a:
            com.google.android.recaptcha.internal.zzp r7 = new com.google.android.recaptcha.internal.zzp
            com.google.android.recaptcha.internal.zzn r6 = com.google.android.recaptcha.internal.zzn.zzc
            com.google.android.recaptcha.internal.zzl r8 = com.google.android.recaptcha.internal.zzl.zzan
            r7.<init>(r6, r8, r4)
        L73:
            com.google.android.recaptcha.internal.zzbg r5 = r5.zzi
            r5.zzb(r9, r7, r4)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.recaptcha.internal.zzaw.zzj(long, java.lang.String, com.google.android.recaptcha.internal.zzbd, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object zzk(com.google.android.recaptcha.RecaptchaAction r13, long r14, kotlin.coroutines.Continuation r16) {
        /*
            r12 = this;
            r0 = r16
            boolean r2 = r0 instanceof com.google.android.recaptcha.internal.zzas
            if (r2 == 0) goto L16
            r2 = r0
            com.google.android.recaptcha.internal.zzas r2 = (com.google.android.recaptcha.internal.zzas) r2
            int r3 = r2.zzc
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L16
            int r3 = r3 - r4
            r2.zzc = r3
        L14:
            r8 = r2
            goto L1c
        L16:
            com.google.android.recaptcha.internal.zzas r2 = new com.google.android.recaptcha.internal.zzas
            r2.<init>(r12, r0)
            goto L14
        L1c:
            java.lang.Object r0 = r8.zza
            java.lang.Object r9 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.zzc
            r10 = 1
            r11 = 0
            if (r2 == 0) goto L3e
            if (r2 != r10) goto L38
            com.google.android.recaptcha.internal.zzbd r1 = r8.zze
            com.google.android.recaptcha.internal.zzaw r2 = r8.zzd
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Exception -> L34
            r5 = r1
            r1 = r2
            goto L74
        L34:
            r0 = move-exception
            r5 = r1
            r1 = r2
            goto L80
        L38:
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r0)
            return r11
        L3e:
            kotlin.ResultKt.throwOnFailure(r0)
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r6 = r0.toString()
            com.google.android.recaptcha.internal.zzbd r0 = r12.zzh
            com.google.android.recaptcha.internal.zzbd r5 = r0.zzb()
            r5.zzc(r6)
            com.google.android.recaptcha.internal.zzbg r0 = r12.zzi
            com.google.android.recaptcha.internal.zzne r2 = com.google.android.recaptcha.internal.zzne.EXECUTE_TOTAL
            com.google.android.recaptcha.internal.zzbb r2 = r5.zza(r2)
            r3 = 2
            com.google.android.recaptcha.internal.zzbg.zzc(r0, r2, r11, r3, r11)
            com.google.android.recaptcha.internal.zzat r0 = new com.google.android.recaptcha.internal.zzat     // Catch: java.lang.Exception -> L7e
            r7 = 0
            r1 = r12
            r4 = r13
            r2 = r14
            r0.<init>(r1, r2, r4, r5, r6, r7)     // Catch: java.lang.Exception -> L7e
            r8.zzd = r12     // Catch: java.lang.Exception -> L7e
            r8.zze = r5     // Catch: java.lang.Exception -> L7e
            r8.zzc = r10     // Catch: java.lang.Exception -> L7e
            java.lang.Object r0 = kotlinx.coroutines.TimeoutKt.withTimeout(r14, r0, r8)     // Catch: java.lang.Exception -> L7e
            if (r0 == r9) goto L7d
            r1 = r12
        L74:
            kotlin.Result r0 = (kotlin.Result) r0     // Catch: java.lang.Exception -> L7b
            java.lang.Object r0 = r0.getValue()     // Catch: java.lang.Exception -> L7b
            return r0
        L7b:
            r0 = move-exception
            goto L80
        L7d:
            return r9
        L7e:
            r0 = move-exception
            r1 = r12
        L80:
            boolean r2 = r0 instanceof com.google.android.recaptcha.internal.zzp
            if (r2 == 0) goto L87
            com.google.android.recaptcha.internal.zzp r0 = (com.google.android.recaptcha.internal.zzp) r0
            goto L99
        L87:
            java.lang.Class r0 = r0.getClass()
            com.google.android.recaptcha.internal.zzp r2 = new com.google.android.recaptcha.internal.zzp
            com.google.android.recaptcha.internal.zzn r3 = com.google.android.recaptcha.internal.zzn.zzc
            com.google.android.recaptcha.internal.zzl r4 = com.google.android.recaptcha.internal.zzl.zzaj
            java.lang.String r0 = r0.getSimpleName()
            r2.<init>(r3, r4, r0)
            r0 = r2
        L99:
            com.google.android.recaptcha.internal.zzbg r1 = r1.zzi
            com.google.android.recaptcha.internal.zzne r2 = com.google.android.recaptcha.internal.zzne.EXECUTE_TOTAL
            com.google.android.recaptcha.internal.zzbb r2 = r5.zza(r2)
            r1.zzb(r2, r0, r11)
            com.google.android.recaptcha.RecaptchaException r0 = r0.zzc()
            kotlin.Result$Companion r1 = kotlin.Result.INSTANCE
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r0)
            java.lang.Object r0 = kotlin.Result.m3494constructorimpl(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.recaptcha.internal.zzaw.zzk(com.google.android.recaptcha.RecaptchaAction, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void zzl(zzol zzolVar, zzbd zzbdVar) throws zzp {
        zzbb zzbbVarZza = zzbdVar.zza(zzne.POST_EXECUTE);
        zzbg zzbgVar = this.zzi;
        zzbgVar.zze.put(zzbbVarZza, new zzbf(zzbbVarZza, zzbgVar.zza, new zzac()));
        try {
            List<zzon> listZzj = zzolVar.zzj();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZzj, 10)), 16));
            for (zzon zzonVar : listZzj) {
                Pair pairM884to = TuplesKt.m884to(zzonVar.zzg(), zzonVar.zzi());
                linkedHashMap.put(pairM884to.getFirst(), pairM884to.getSecond());
            }
            this.zzj.zzb(linkedHashMap);
            this.zzi.zza(zzbbVarZza);
        } catch (Exception e) {
            zzp zzpVar = e instanceof zzp ? (zzp) e : new zzp(zzn.zzc, zzl.zzan, null);
            this.zzi.zzb(zzbbVarZza, zzpVar, null);
            throw zzpVar;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0013  */
    @Override // com.google.android.recaptcha.RecaptchaClient
    /* JADX INFO: renamed from: execute-0E7RQCE */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object mo3400execute0E7RQCE(com.google.android.recaptcha.RecaptchaAction r11, long r12, kotlin.coroutines.Continuation<? super kotlin.Result<java.lang.String>> r14) {
        /*
            r10 = this;
            boolean r0 = r14 instanceof com.google.android.recaptcha.internal.zzap
            if (r0 == 0) goto L13
            r0 = r14
            com.google.android.recaptcha.internal.zzap r0 = (com.google.android.recaptcha.internal.zzap) r0
            int r1 = r0.zzc
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.zzc = r1
            goto L18
        L13:
            com.google.android.recaptcha.internal.zzap r0 = new com.google.android.recaptcha.internal.zzap
            r0.<init>(r10, r14)
        L18:
            java.lang.Object r14 = r0.zza
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.zzc
            r3 = 1
            if (r2 == 0) goto L30
            if (r2 != r3) goto L29
            kotlin.ResultKt.throwOnFailure(r14)
            goto L4f
        L29:
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r10)
            r10 = 0
            return r10
        L30:
            kotlin.ResultKt.throwOnFailure(r14)
            com.google.android.recaptcha.internal.zzt r14 = r10.zzl
            kotlinx.coroutines.CoroutineScope r14 = r14.zzb()
            kotlin.coroutines.CoroutineContext r14 = r14.getCoroutineContext()
            com.google.android.recaptcha.internal.zzaq r4 = new com.google.android.recaptcha.internal.zzaq
            r9 = 0
            r5 = r10
            r6 = r11
            r7 = r12
            r4.<init>(r5, r6, r7, r9)
            r0.zzc = r3
            java.lang.Object r14 = kotlinx.coroutines.BuildersKt.withContext(r14, r4, r0)
            if (r14 != r1) goto L4f
            return r1
        L4f:
            kotlin.Result r14 = (kotlin.Result) r14
            java.lang.Object r10 = r14.getValue()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.recaptcha.internal.zzaw.mo3400execute0E7RQCE(com.google.android.recaptcha.RecaptchaAction, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0013  */
    @Override // com.google.android.recaptcha.RecaptchaClient
    /* JADX INFO: renamed from: execute-gIAlu-s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object mo3401executegIAlus(com.google.android.recaptcha.RecaptchaAction r5, kotlin.coroutines.Continuation<? super kotlin.Result<java.lang.String>> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.google.android.recaptcha.internal.zzar
            if (r0 == 0) goto L13
            r0 = r6
            com.google.android.recaptcha.internal.zzar r0 = (com.google.android.recaptcha.internal.zzar) r0
            int r1 = r0.zzc
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.zzc = r1
            goto L18
        L13:
            com.google.android.recaptcha.internal.zzar r0 = new com.google.android.recaptcha.internal.zzar
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.zza
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.zzc
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2f
            kotlin.ResultKt.throwOnFailure(r6)
            kotlin.Result r6 = (kotlin.Result) r6
            java.lang.Object r4 = r6.getValue()
            return r4
        L2f:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r4)
            r4 = 0
            return r4
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.zzc = r3
            r2 = 10000(0x2710, double:4.9407E-320)
            java.lang.Object r4 = r4.mo3400execute0E7RQCE(r5, r2, r0)
            if (r4 != r1) goto L44
            return r1
        L44:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.recaptcha.internal.zzaw.mo3401executegIAlus(com.google.android.recaptcha.RecaptchaAction, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.google.android.recaptcha.RecaptchaTasksClient
    public final Task<String> executeTask(RecaptchaAction recaptchaAction) {
        return zzj.zza(BuildersKt__Builders_commonKt.async$default(this.zzl.zzb(), null, null, new zzau(this, recaptchaAction, 10000L, null), 3, null));
    }

    public final String zzg() {
        return this.zze;
    }

    @Override // com.google.android.recaptcha.RecaptchaTasksClient
    public final Task<String> executeTask(RecaptchaAction recaptchaAction, long j) {
        return zzj.zza(BuildersKt__Builders_commonKt.async$default(this.zzl.zzb(), null, null, new zzau(this, recaptchaAction, j, null), 3, null));
    }
}
