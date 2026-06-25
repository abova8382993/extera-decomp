package androidx.camera.video.internal.encoder;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes4.dex */
public class SystemTimeProvider implements TimeProvider {
    @Override // androidx.camera.video.internal.encoder.TimeProvider
    public long uptimeUs() {
        return System.nanoTime() / 1000;
    }

    @Override // androidx.camera.video.internal.encoder.TimeProvider
    public long realtimeUs() {
        return SystemClock.elapsedRealtimeNanos() / 1000;
    }
}
