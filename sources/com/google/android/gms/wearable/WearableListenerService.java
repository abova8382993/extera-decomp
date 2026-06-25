package com.google.android.gms.wearable;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.internal.zzbc;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public abstract class WearableListenerService extends Service implements ChannelApi.ChannelListener {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private ComponentName zza;
    private zzn zzb;
    private IBinder zzc;
    private Intent zzd;
    private Looper zze;
    private boolean zzg;
    private final Object zzf = new Object();
    private zzbc zzh = new zzbc(new zzk(this, null));

    public Looper getLooper() {
        if (this.zze == null) {
            HandlerThread handlerThread = new HandlerThread("WearableListenerService");
            handlerThread.start();
            this.zze = handlerThread.getLooper();
        }
        return this.zze;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0019, code lost:
    
        if (r1.equals(com.google.android.gms.wearable.WearableListenerService.BIND_LISTENER_INTENT_ACTION) != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        if (r1.equals("com.google.android.gms.wearable.CHANNEL_EVENT") != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002b, code lost:
    
        if (r1.equals("com.google.android.gms.wearable.DATA_CHANGED") != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0034, code lost:
    
        if (r1.equals("com.google.android.gms.wearable.MESSAGE_RECEIVED") != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003d, code lost:
    
        if (r1.equals("com.google.android.gms.wearable.REQUEST_RECEIVED") != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0046, code lost:
    
        if (r1.equals("com.google.android.gms.wearable.CAPABILITY_CHANGED") != false) goto L28;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.IBinder onBind(android.content.Intent r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L4
            return r0
        L4:
            java.lang.String r1 = r4.getAction()
            if (r1 != 0) goto Lb
            return r0
        Lb:
            int r2 = r1.hashCode()
            switch(r2) {
                case -1487371046: goto L40;
                case -1140095138: goto L37;
                case -786751258: goto L2e;
                case 915816236: goto L25;
                case 1003809169: goto L1c;
                case 1460975593: goto L13;
                default: goto L12;
            }
        L12:
            goto L4b
        L13:
            java.lang.String r2 = "com.google.android.gms.wearable.BIND_LISTENER"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L4b
            goto L48
        L1c:
            java.lang.String r2 = "com.google.android.gms.wearable.CHANNEL_EVENT"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L4b
            goto L48
        L25:
            java.lang.String r2 = "com.google.android.gms.wearable.DATA_CHANGED"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L4b
            goto L48
        L2e:
            java.lang.String r2 = "com.google.android.gms.wearable.MESSAGE_RECEIVED"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L4b
            goto L48
        L37:
            java.lang.String r2 = "com.google.android.gms.wearable.REQUEST_RECEIVED"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L4b
            goto L48
        L40:
            java.lang.String r2 = "com.google.android.gms.wearable.CAPABILITY_CHANGED"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L4b
        L48:
            android.os.IBinder r3 = r3.zzc
            return r3
        L4b:
            r3 = 3
            java.lang.String r1 = "WearableLS"
            boolean r3 = android.util.Log.isLoggable(r1, r3)
            if (r3 == 0) goto L6e
            java.lang.String r3 = r4.toString()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r2 = "onBind: Provided bind intent ("
            r4.<init>(r2)
            r4.append(r3)
            java.lang.String r3 = ") is not allowed"
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Log.d(r1, r3)
        L6e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.WearableListenerService.onBind(android.content.Intent):android.os.IBinder");
    }

    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
    }

    @Override // com.google.android.gms.wearable.ChannelApi.ChannelListener
    public void onChannelClosed(Channel channel, int i, int i2) {
    }

    public void onChannelClosed(ChannelClient.Channel channel, int i, int i2) {
    }

    @Override // com.google.android.gms.wearable.ChannelApi.ChannelListener
    public void onChannelOpened(Channel channel) {
    }

    public void onChannelOpened(ChannelClient.Channel channel) {
    }

    public void onConnectedNodes(List<Node> list) {
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.zza = new ComponentName(this, getClass().getName());
        if (Log.isLoggable("WearableLS", 3)) {
            Log.d("WearableLS", "onCreate: ".concat(String.valueOf(this.zza)));
        }
        this.zzb = new zzn(this, getLooper());
        Intent intent = new Intent(BIND_LISTENER_INTENT_ACTION);
        this.zzd = intent;
        intent.setComponent(this.zza);
        this.zzc = new zzaa(this, null);
    }

    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    @Override // android.app.Service
    public void onDestroy() {
        if (Log.isLoggable("WearableLS", 3)) {
            Log.d("WearableLS", "onDestroy: ".concat(String.valueOf(this.zza)));
        }
        synchronized (this.zzf) {
            this.zzg = true;
            zzn zznVar = this.zzb;
            if (zznVar == null) {
                throw new IllegalStateException("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()? component=".concat(String.valueOf(this.zza)));
            }
            zznVar.zzb();
        }
        super.onDestroy();
    }

    public void onEntityUpdate(zza zzaVar) {
    }

    @Override // com.google.android.gms.wearable.ChannelApi.ChannelListener
    public void onInputClosed(Channel channel, int i, int i2) {
    }

    public void onInputClosed(ChannelClient.Channel channel, int i, int i2) {
    }

    public abstract void onMessageReceived(MessageEvent messageEvent);

    public void onNotificationReceived(zzb zzbVar) {
    }

    @Override // com.google.android.gms.wearable.ChannelApi.ChannelListener
    public void onOutputClosed(Channel channel, int i, int i2) {
    }

    public void onOutputClosed(ChannelClient.Channel channel, int i, int i2) {
    }

    public void onPeerConnected(Node node) {
    }

    public void onPeerDisconnected(Node node) {
    }

    public Task<byte[]> onRequest(String str, String str2, byte[] bArr) {
        return null;
    }
}
