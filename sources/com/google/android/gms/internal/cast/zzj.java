package com.google.android.gms.internal.cast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transport;
import com.google.android.datatransport.cct.CCTDestination;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.checkerframework.dataflow.qual.Pure;

/* JADX INFO: loaded from: classes4.dex */
public final class zzj {
    Transport zzb;
    private final Context zzd;
    private final com.google.android.gms.cast.internal.zzn zze;
    private final SessionManager zzf;
    private final zzce zzg;
    private final zzax zzh;
    private Long zzj;
    private final ExecutorService zzk;
    private zzcn zzl;
    private static final Logger zzc = new Logger("ClientCastAnalytics");
    public static boolean zza = true;
    private int zzm = 1;
    private final String zzi = UUID.randomUUID().toString();

    private zzj(Context context, com.google.android.gms.cast.internal.zzn zznVar, SessionManager sessionManager, zzce zzceVar, zzax zzaxVar) {
        this.zzd = context;
        this.zze = zznVar;
        this.zzf = sessionManager;
        this.zzg = zzceVar;
        this.zzh = zzaxVar;
        zzfj.zza();
        this.zzk = Executors.unconfigurableExecutorService(Executors.newCachedThreadPool());
    }

    public static zzj zza(Context context, com.google.android.gms.cast.internal.zzn zznVar, SessionManager sessionManager, zzce zzceVar, zzax zzaxVar) {
        return new zzj(context, zznVar, sessionManager, zzceVar, zzaxVar);
    }

    public final void zzb(Bundle bundle) {
        final int i = bundle.containsKey("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_MODE") ? bundle.getInt("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_MODE", 0) : (bundle.containsKey("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_ENABLED") && bundle.getBoolean("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_ENABLED", false)) ? 1 : 0;
        boolean z = bundle.getBoolean("com.google.android.gms.cast.FLAG_CLIENT_FEATURE_USAGE_ANALYTICS_ENABLED", false);
        boolean z2 = bundle.getBoolean("com.google.android.gms.cast.FLAG_CLIENT_ANALYTICS_ENABLED", false);
        zza = z2;
        if (i == 0) {
            if (!z && !z2) {
                return;
            } else {
                i = 0;
            }
        }
        long j = bundle.getLong("com.google.android.gms.cast.FLAG_ANALYTICS_CONSENT_TIMEOUT_SECONDS", 5L);
        Context context = this.zzd;
        this.zzl = new zzcn(context, j);
        final String packageName = context.getPackageName();
        String str = String.format(Locale.ROOT, "%s.%s", packageName, "client_cast_analytics_data");
        this.zzm = bundle.getLong("com.google.android.gms.cast.FLAG_FIRELOG_UPLOAD_MODE") != 0 ? 2 : 1;
        TransportRuntime.initialize(context);
        this.zzb = TransportRuntime.getInstance().newFactory(CCTDestination.INSTANCE).getTransport("CAST_SENDER_SDK", zzqr.class, Encoding.m294of("proto"), zzf.zza);
        if (bundle.containsKey("com.google.android.gms.cast.FLAG_ANALYTICS_LOGGING_BUCKET_SIZE")) {
            this.zzj = Long.valueOf(bundle.getLong("com.google.android.gms.cast.FLAG_ANALYTICS_LOGGING_BUCKET_SIZE"));
        }
        final SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(str, 0);
        if (i != 0) {
            this.zze.zzc(new String[]{"com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_ERROR", "com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_CHANGE_REASON"}).addOnSuccessListener(new OnSuccessListener() { // from class: com.google.android.gms.internal.cast.zzi
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final /* synthetic */ void onSuccess(Object obj) {
                    this.zza.zzc(packageName, i, sharedPreferences, (Bundle) obj);
                }
            });
        }
        if (z) {
            Preconditions.checkNotNull(sharedPreferences);
            zzr.zza(sharedPreferences, this, packageName).zzc();
            zzr.zzb(zzpm.CAST_CONTEXT);
        }
        if (zza) {
            zzu.zza(this, packageName);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ void zzc(java.lang.String r12, int r13, android.content.SharedPreferences r14, android.os.Bundle r15) {
        /*
            r11 = this;
            com.google.android.gms.cast.framework.SessionManager r0 = r11.zzf
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
            com.google.android.gms.internal.cast.zzce r1 = r11.zzg
            r2 = 3
            java.lang.Class<com.google.android.gms.cast.framework.CastSession> r3 = com.google.android.gms.cast.framework.CastSession.class
            r4 = 2
            if (r13 == r2) goto L10
            if (r13 != r4) goto L29
            r13 = r4
        L10:
            com.google.android.gms.internal.cast.zzax r2 = r11.zzh
            com.google.android.gms.internal.cast.zzy r5 = new com.google.android.gms.internal.cast.zzy
            r5.<init>(r11, r2, r12)
            com.google.android.gms.internal.cast.zzw r2 = new com.google.android.gms.internal.cast.zzw
            r2.<init>(r5)
            r0.addSessionManagerListener(r2, r3)
            if (r1 == 0) goto L29
            com.google.android.gms.internal.cast.zzx r2 = new com.google.android.gms.internal.cast.zzx
            r2.<init>(r5)
            r1.zzc(r2)
        L29:
            r2 = 1
            if (r13 == r2) goto L2e
            if (r13 != r4) goto L4b
        L2e:
            com.google.android.gms.internal.cast.zzax r8 = r11.zzh
            com.google.android.gms.internal.cast.zzn r5 = new com.google.android.gms.internal.cast.zzn
            r7 = r11
            r10 = r12
            r6 = r14
            r9 = r15
            r5.<init>(r6, r7, r8, r9, r10)
            com.google.android.gms.internal.cast.zzl r11 = new com.google.android.gms.internal.cast.zzl
            r11.<init>(r5)
            r0.addSessionManagerListener(r11, r3)
            if (r1 == 0) goto L4b
            com.google.android.gms.internal.cast.zzm r11 = new com.google.android.gms.internal.cast.zzm
            r11.<init>(r5)
            r1.zzc(r11)
        L4b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzj.zzc(java.lang.String, int, android.content.SharedPreferences, android.os.Bundle):void");
    }

    @Pure
    public final void zzd(final zzqr zzqrVar, final int i) {
        this.zzk.execute(new Runnable() { // from class: com.google.android.gms.internal.cast.zzg
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zze(zzqrVar, i);
            }
        });
    }

    public final /* synthetic */ void zze(final zzqr zzqrVar, final int i) {
        zzcn zzcnVar = this.zzl;
        if (zzcnVar == null) {
            return;
        }
        zzcnVar.zza().addOnSuccessListener(new OnSuccessListener() { // from class: com.google.android.gms.internal.cast.zzh
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final /* synthetic */ void onSuccess(Object obj) {
                this.zza.zzf(zzqrVar, i, (Boolean) obj);
            }
        });
    }

    public final /* synthetic */ void zzf(zzqr zzqrVar, int i, Boolean bool) {
        if (bool.booleanValue()) {
            zzqq zzqqVarZzd = zzqr.zzd(zzqrVar);
            String str = this.zzi;
            zzqqVarZzd.zzc(str);
            zzqqVarZzd.zzd(str);
            Long l = this.zzj;
            if (l != null) {
                zzqqVarZzd.zze((int) l.longValue());
            }
            zzqr zzqrVar2 = (zzqr) zzqqVarZzd.zzu();
            int i2 = this.zzm;
            int i3 = i2 - 1;
            if (i2 == 0) {
                throw null;
            }
            int i4 = i - 1;
            Event eventOfTelemetry = (i3 == 0 || i3 != 1) ? Event.ofTelemetry(i4, zzqrVar2) : Event.ofData(i4, zzqrVar2);
            zzc.m333d("analytics event: %s", eventOfTelemetry);
            Preconditions.checkNotNull(eventOfTelemetry);
            Transport transport = this.zzb;
            if (transport != null) {
                transport.send(eventOfTelemetry);
            }
        }
    }
}
