package com.google.android.gms.cast.framework.media;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaError;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaLoadRequestData;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaSeekOptions;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.SessionState;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzfk;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class RemoteMediaClient implements Cast.MessageReceivedCallback {
    private final com.google.android.gms.cast.internal.zzar zzd;
    private final zzax zze;

    @NotOnlyInitialized
    private final MediaQueue zzf;
    private com.google.android.gms.cast.zzq zzg;
    private static final Logger zza = new Logger("RemoteMediaClient");
    public static final String NAMESPACE = com.google.android.gms.cast.internal.zzar.zzb;
    private final List zzi = new CopyOnWriteArrayList();
    private final List zzj = new CopyOnWriteArrayList();
    private final Map zzk = new ConcurrentHashMap();
    private final Map zzl = new ConcurrentHashMap();
    private final Object zzb = new Object();
    private final Handler zzc = new zzfk(Looper.getMainLooper());

    public static abstract class Callback {
        public void onAdBreakStatusUpdated() {
        }

        public void onMediaError(MediaError mediaError) {
        }

        public void onMetadataUpdated() {
        }

        public void onPreloadStatusUpdated() {
        }

        public void onQueueStatusUpdated() {
        }

        public void onSendingRemoteMediaRequest() {
        }

        public abstract void onStatusUpdated();

        public void zza(String str, long j, int i, long j2, long j3) {
        }

        public void zzb(int[] iArr) {
        }

        public void zzc(int[] iArr, int i) {
        }

        public void zzd(int[] iArr) {
        }

        public void zze(int[] iArr) {
        }

        public void zzf(MediaQueueItem[] mediaQueueItemArr) {
        }

        public void zzg(List list, List list2, int i) {
        }

        public void zzh() {
        }
    }

    public interface MediaChannelResult extends Result {
    }

    public interface ParseAdsInfoCallback {
    }

    public RemoteMediaClient(com.google.android.gms.cast.internal.zzar zzarVar) {
        zzax zzaxVar = new zzax(this);
        this.zze = zzaxVar;
        com.google.android.gms.cast.internal.zzar zzarVar2 = (com.google.android.gms.cast.internal.zzar) Preconditions.checkNotNull(zzarVar);
        this.zzd = zzarVar2;
        zzarVar2.zzi(new zzbe(this, null));
        zzarVar2.zze(zzaxVar);
        this.zzf = new MediaQueue(this, 20, 20);
    }

    public static PendingResult zzn(int i, String str) {
        zzaz zzazVar = new zzaz();
        zzazVar.setResult(new zzay(zzazVar, new Status(i, str)));
        return zzazVar;
    }

    private final boolean zzx() {
        return this.zzg != null;
    }

    private static final zzbc zzz(zzbc zzbcVar) {
        try {
            zzbcVar.zzc();
            return zzbcVar;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Throwable unused) {
            zzbcVar.setResult(new zzbb(zzbcVar, new Status(2100)));
            return zzbcVar;
        }
    }

    public long getApproximateStreamPosition() {
        long jZzv;
        synchronized (this.zzb) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            jZzv = this.zzd.zzv();
        }
        return jZzv;
    }

    public int getIdleReason() {
        int idleReason;
        synchronized (this.zzb) {
            try {
                Preconditions.checkMainThread("Must be called from the main thread.");
                MediaStatus mediaStatus = getMediaStatus();
                idleReason = mediaStatus != null ? mediaStatus.getIdleReason() : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
        return idleReason;
    }

    public MediaQueueItem getLoadingItem() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        if (mediaStatus == null) {
            return null;
        }
        return mediaStatus.getQueueItemById(mediaStatus.getLoadingItemId());
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfoZzB;
        synchronized (this.zzb) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            mediaInfoZzB = this.zzd.zzB();
        }
        return mediaInfoZzB;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatusZzA;
        synchronized (this.zzb) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            mediaStatusZzA = this.zzd.zzA();
        }
        return mediaStatusZzA;
    }

    public String getNamespace() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return this.zzd.zzd();
    }

    public int getPlayerState() {
        int playerState;
        synchronized (this.zzb) {
            try {
                Preconditions.checkMainThread("Must be called from the main thread.");
                MediaStatus mediaStatus = getMediaStatus();
                playerState = mediaStatus != null ? mediaStatus.getPlayerState() : 1;
            } catch (Throwable th) {
                throw th;
            }
        }
        return playerState;
    }

    public long getStreamDuration() {
        long jZzz;
        synchronized (this.zzb) {
            Preconditions.checkMainThread("Must be called from the main thread.");
            jZzz = this.zzd.zzz();
        }
        return jZzz;
    }

    public boolean hasMediaSession() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return isBuffering() || zzi() || isPlaying() || isPaused() || isLoadingNextItem();
    }

    public boolean isBuffering() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && mediaStatus.getPlayerState() == 4;
    }

    public boolean isLiveStream() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaInfo mediaInfo = getMediaInfo();
        return mediaInfo != null && mediaInfo.getStreamType() == 2;
    }

    public boolean isLoadingNextItem() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return (mediaStatus == null || mediaStatus.getLoadingItemId() == 0) ? false : true;
    }

    public boolean isPaused() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        if (mediaStatus == null) {
            return false;
        }
        if (mediaStatus.getPlayerState() != 3) {
            return isLiveStream() && getIdleReason() == 2;
        }
        return true;
    }

    public boolean isPlaying() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && mediaStatus.getPlayerState() == 2;
    }

    public boolean isPlayingAd() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && mediaStatus.isPlayingAd();
    }

    @Override // com.google.android.gms.cast.Cast.MessageReceivedCallback
    public void onMessageReceived(CastDevice castDevice, String str, String str2) {
        this.zzd.zzL(str2);
    }

    public PendingResult<MediaChannelResult> pause() {
        return pause(null);
    }

    public PendingResult<MediaChannelResult> play() {
        return play(null);
    }

    public PendingResult<MediaChannelResult> queueNext(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzaf zzafVar = new zzaf(this, jSONObject);
        zzz(zzafVar);
        return zzafVar;
    }

    public PendingResult<MediaChannelResult> queuePrev(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzae zzaeVar = new zzae(this, jSONObject);
        zzz(zzaeVar);
        return zzaeVar;
    }

    public PendingResult<MediaChannelResult> queueSetRepeatMode(int i, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzag zzagVar = new zzag(this, i, jSONObject);
        zzz(zzagVar);
        return zzagVar;
    }

    public void registerCallback(Callback callback) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (callback != null) {
            this.zzj.add(callback);
        }
    }

    public PendingResult<MediaChannelResult> requestStatus() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzu zzuVar = new zzu(this);
        zzz(zzuVar);
        return zzuVar;
    }

    @Deprecated
    public PendingResult<MediaChannelResult> seek(long j) {
        return seek(j, 0, null);
    }

    public PendingResult<MediaChannelResult> setPlaybackRate(double d) {
        return setPlaybackRate(d, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(double d) {
        return setStreamVolume(d, null);
    }

    public void togglePlayback() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        int playerState = getPlayerState();
        if (playerState == 4 || playerState == 2) {
            pause();
        } else {
            play();
        }
    }

    public void unregisterCallback(Callback callback) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (callback != null) {
            this.zzj.remove(callback);
        }
    }

    public final void zza(com.google.android.gms.cast.zzq zzqVar) {
        com.google.android.gms.cast.zzq zzqVar2 = this.zzg;
        if (zzqVar2 == zzqVar) {
            return;
        }
        if (zzqVar2 != null) {
            this.zzd.zzh();
            this.zzf.zza();
            zzqVar2.zzr(getNamespace());
            this.zze.zza(null);
            this.zzc.removeCallbacksAndMessages(null);
        }
        this.zzg = zzqVar;
        if (zzqVar != null) {
            this.zze.zza(zzqVar);
        }
    }

    public final void zzb() {
        com.google.android.gms.cast.zzq zzqVar = this.zzg;
        if (zzqVar == null) {
            return;
        }
        zzqVar.zzq(getNamespace(), this);
        requestStatus();
    }

    public final PendingResult zzc() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzak zzakVar = new zzak(this, true);
        zzz(zzakVar);
        return zzakVar;
    }

    public final PendingResult zzd(int[] iArr) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzal zzalVar = new zzal(this, true, iArr);
        zzz(zzalVar);
        return zzalVar;
    }

    public final void zzg(SessionState sessionState) {
        MediaLoadRequestData loadRequestData;
        if (sessionState == null || (loadRequestData = sessionState.getLoadRequestData()) == null) {
            return;
        }
        zza.m333d("resume SessionState", new Object[0]);
        load(loadRequestData);
    }

    public final boolean zzi() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        MediaStatus mediaStatus = getMediaStatus();
        return mediaStatus != null && mediaStatus.getPlayerState() == 5;
    }

    public final int zzk() {
        MediaQueueItem loadingItem;
        if (getMediaInfo() != null && hasMediaSession()) {
            if (isBuffering()) {
                return 6;
            }
            if (isPlaying()) {
                return 3;
            }
            if (isPaused()) {
                return 2;
            }
            if (isLoadingNextItem() && (loadingItem = getLoadingItem()) != null && loadingItem.getMedia() != null) {
                return 6;
            }
        }
        return 0;
    }

    public final boolean zzl() {
        if (!hasMediaSession()) {
            return false;
        }
        MediaStatus mediaStatus = (MediaStatus) Preconditions.checkNotNull(getMediaStatus());
        if (mediaStatus.isMediaCommandSupported(128L) || mediaStatus.getQueueRepeatMode() != 0) {
            return true;
        }
        Integer indexById = mediaStatus.getIndexById(mediaStatus.getCurrentItemId());
        return indexById != null && indexById.intValue() > 0;
    }

    public final boolean zzm() {
        if (!hasMediaSession()) {
            return false;
        }
        MediaStatus mediaStatus = (MediaStatus) Preconditions.checkNotNull(getMediaStatus());
        if (mediaStatus.isMediaCommandSupported(64L) || mediaStatus.getQueueRepeatMode() != 0) {
            return true;
        }
        Integer indexById = mediaStatus.getIndexById(mediaStatus.getCurrentItemId());
        return indexById != null && indexById.intValue() < mediaStatus.getQueueItemCount() + (-1);
    }

    public final /* synthetic */ void zzp() {
        Iterator it = this.zzl.values().iterator();
        if (it.hasNext()) {
            MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(it.next());
            if (!hasMediaSession() && hasMediaSession()) {
                throw null;
            }
            throw null;
        }
    }

    public final /* synthetic */ Object zzr() {
        return this.zzb;
    }

    public final /* synthetic */ com.google.android.gms.cast.internal.zzar zzt() {
        return this.zzd;
    }

    public final /* synthetic */ List zzu() {
        return this.zzi;
    }

    public final /* synthetic */ List zzv() {
        return this.zzj;
    }

    public final /* synthetic */ ParseAdsInfoCallback zzw() {
        return null;
    }

    public PendingResult<MediaChannelResult> load(MediaInfo mediaInfo, MediaLoadOptions mediaLoadOptions) {
        MediaLoadRequestData.Builder builder = new MediaLoadRequestData.Builder();
        builder.setMediaInfo(mediaInfo);
        builder.setAutoplay(Boolean.valueOf(mediaLoadOptions.getAutoplay()));
        builder.setCurrentTime(mediaLoadOptions.getPlayPosition());
        builder.setPlaybackRate(mediaLoadOptions.getPlaybackRate());
        builder.setActiveTrackIds(mediaLoadOptions.getActiveTrackIds());
        builder.setCustomData(mediaLoadOptions.getCustomData());
        builder.setCredentials(mediaLoadOptions.getCredentials());
        builder.setCredentialsType(mediaLoadOptions.getCredentialsType());
        return load(builder.build());
    }

    public PendingResult<MediaChannelResult> pause(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzap zzapVar = new zzap(this, jSONObject);
        zzz(zzapVar);
        return zzapVar;
    }

    public PendingResult<MediaChannelResult> play(JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzar zzarVar = new zzar(this, jSONObject);
        zzz(zzarVar);
        return zzarVar;
    }

    public PendingResult<MediaChannelResult> setPlaybackRate(double d, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzav zzavVar = new zzav(this, d, jSONObject);
        zzz(zzavVar);
        return zzavVar;
    }

    public PendingResult<MediaChannelResult> setStreamVolume(double d, JSONObject jSONObject) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzat zzatVar = new zzat(this, d, jSONObject);
        zzz(zzatVar);
        return zzatVar;
    }

    @Deprecated
    public PendingResult<MediaChannelResult> seek(long j, int i, JSONObject jSONObject) {
        MediaSeekOptions.Builder builder = new MediaSeekOptions.Builder();
        builder.setPosition(j);
        builder.setResumeState(i);
        builder.setCustomData(jSONObject);
        return seek(builder.build());
    }

    public PendingResult<MediaChannelResult> seek(MediaSeekOptions mediaSeekOptions) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzas zzasVar = new zzas(this, mediaSeekOptions);
        zzz(zzasVar);
        return zzasVar;
    }

    public PendingResult<MediaChannelResult> load(MediaLoadRequestData mediaLoadRequestData) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (!zzx()) {
            return zzn(17, null);
        }
        zzan zzanVar = new zzan(this, mediaLoadRequestData);
        zzz(zzanVar);
        return zzanVar;
    }
}
