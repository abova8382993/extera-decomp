package org.telegram.ui.Components.voip;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.SystemClock;
import okhttp3.internal.url._UrlKt;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.Components.RLottieDrawable;
import org.telegram.ui.Components.RLottieImageView;

/* JADX INFO: loaded from: classes5.dex */
public class GroupCallStatusIcon {
    Callback callback;
    RLottieImageView iconView;
    boolean isSpeaking;
    boolean lastMuted;
    boolean lastRaisedHand;
    private boolean mutedByMe;
    TLRPC.GroupCallParticipant participant;
    boolean updateRunnableScheduled;
    private Runnable shakeHandCallback = new Runnable() { // from class: org.telegram.ui.Components.voip.GroupCallStatusIcon$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$0();
        }
    };
    private Runnable raiseHandCallback = new Runnable() { // from class: org.telegram.ui.Components.voip.GroupCallStatusIcon$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$1();
        }
    };
    private Runnable updateRunnable = new Runnable() { // from class: org.telegram.ui.Components.voip.GroupCallStatusIcon$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$2();
        }
    };
    private Runnable checkRaiseRunnable = new Runnable() { // from class: org.telegram.ui.Components.voip.GroupCallStatusIcon$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.lambda$new$3();
        }
    };
    RLottieDrawable micDrawable = new RLottieDrawable(R.raw.voice_mini, _UrlKt.FRAGMENT_ENCODE_SET + R.raw.voice_mini, AndroidUtilities.dp(24.0f), AndroidUtilities.dp(24.0f), true, null);
    RLottieDrawable shakeHandDrawable = new RLottieDrawable(R.raw.hand_2, _UrlKt.FRAGMENT_ENCODE_SET + R.raw.hand_2, AndroidUtilities.dp(15.0f), AndroidUtilities.dp(15.0f), true, null);

    public interface Callback {
        void onStatusChanged();
    }

    public /* synthetic */ void lambda$new$0() {
        this.shakeHandDrawable.setOnFinishCallback(null, 0);
        this.micDrawable.setOnFinishCallback(null, 0);
        RLottieImageView rLottieImageView = this.iconView;
        if (rLottieImageView != null) {
            rLottieImageView.setAnimation(this.micDrawable);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0017 A[PHI: r2 r3
  0x0017: PHI (r2v3 int) = (r2v0 int), (r2v1 int) binds: [B:26:0x0015, B:32:0x0026] A[DONT_GENERATE, DONT_INLINE]
  0x0017: PHI (r3v2 int) = (r3v0 int), (r3v1 int) binds: [B:26:0x0015, B:32:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$new$1() {
        /*
            r5 = this;
            java.security.SecureRandom r0 = org.telegram.messenger.Utilities.random
            r1 = 100
            int r0 = r0.nextInt(r1)
            r1 = 32
            r2 = 120(0x78, float:1.68E-43)
            if (r0 >= r1) goto L11
            r0 = 0
            r3 = r0
            goto L2b
        L11:
            r1 = 64
            r3 = 240(0xf0, float:3.36E-43)
            if (r0 >= r1) goto L1b
        L17:
            r4 = r3
            r3 = r2
            r2 = r4
            goto L2b
        L1b:
            r1 = 97
            r2 = 420(0x1a4, float:5.89E-43)
            if (r0 >= r1) goto L22
            goto L2b
        L22:
            r1 = 98
            r3 = 540(0x21c, float:7.57E-43)
            if (r0 != r1) goto L29
            goto L17
        L29:
            r2 = 720(0x2d0, float:1.009E-42)
        L2b:
            org.telegram.ui.Components.RLottieDrawable r0 = r5.shakeHandDrawable
            r0.setCustomEndFrame(r2)
            org.telegram.ui.Components.RLottieDrawable r0 = r5.shakeHandDrawable
            java.lang.Runnable r1 = r5.shakeHandCallback
            int r2 = r2 + (-1)
            r0.setOnFinishCallback(r1, r2)
            org.telegram.ui.Components.RLottieDrawable r0 = r5.shakeHandDrawable
            r0.setCurrentFrame(r3)
            org.telegram.ui.Components.RLottieImageView r0 = r5.iconView
            if (r0 == 0) goto L4c
            org.telegram.ui.Components.RLottieDrawable r1 = r5.shakeHandDrawable
            r0.setAnimation(r1)
            org.telegram.ui.Components.RLottieImageView r0 = r5.iconView
            r0.playAnimation()
        L4c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.voip.GroupCallStatusIcon.lambda$new$1():void");
    }

    public /* synthetic */ void lambda$new$2() {
        this.isSpeaking = false;
        Callback callback = this.callback;
        if (callback != null) {
            callback.onStatusChanged();
        }
        this.updateRunnableScheduled = false;
    }

    public void setAmplitude(double d) {
        if (d > 1.5d) {
            if (this.updateRunnableScheduled) {
                AndroidUtilities.cancelRunOnUIThread(this.updateRunnable);
            }
            if (!this.isSpeaking) {
                this.isSpeaking = true;
                Callback callback = this.callback;
                if (callback != null) {
                    callback.onStatusChanged();
                }
            }
            AndroidUtilities.runOnUIThread(this.updateRunnable, 500L);
            this.updateRunnableScheduled = true;
        }
    }

    public /* synthetic */ void lambda$new$3() {
        updateIcon(true);
    }

    public void setImageView(RLottieImageView rLottieImageView) {
        this.iconView = rLottieImageView;
        updateIcon(false);
    }

    public void setParticipant(TLRPC.GroupCallParticipant groupCallParticipant, boolean z) {
        this.participant = groupCallParticipant;
        updateIcon(z);
    }

    public void updateIcon(boolean z) {
        TLRPC.GroupCallParticipant groupCallParticipant;
        boolean z2;
        boolean customEndFrame;
        boolean z3;
        if (this.iconView == null || (groupCallParticipant = this.participant) == null || this.micDrawable == null) {
            return;
        }
        boolean z4 = groupCallParticipant.muted_by_you && !groupCallParticipant.self;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        TLRPC.GroupCallParticipant groupCallParticipant2 = this.participant;
        if (jElapsedRealtime - groupCallParticipant2.lastVoiceUpdateTime < 500) {
            z2 = groupCallParticipant2.hasVoiceDelayed;
        } else {
            z2 = groupCallParticipant2.hasVoice;
        }
        boolean z5 = !groupCallParticipant2.self ? (!groupCallParticipant2.muted || (this.isSpeaking && z2)) && !z4 : VoIPService.getSharedInstance() == null || !VoIPService.getSharedInstance().isMicMute() || (this.isSpeaking && z2);
        TLRPC.GroupCallParticipant groupCallParticipant3 = this.participant;
        boolean z6 = ((groupCallParticipant3.muted && !this.isSpeaking) || z4) && !(((z3 = groupCallParticipant3.can_self_unmute) && !z4) || z3 || groupCallParticipant3.raise_hand_rating == 0);
        if (z6) {
            long jElapsedRealtime2 = SystemClock.elapsedRealtime();
            long j = this.participant.lastRaiseHandDate;
            long j2 = jElapsedRealtime2 - j;
            if (j != 0 && j2 <= 5000) {
                AndroidUtilities.runOnUIThread(this.checkRaiseRunnable, 5000 - j2);
            }
            customEndFrame = this.micDrawable.setCustomEndFrame(Opcodes.L2I);
        } else {
            this.iconView.setAnimation(this.micDrawable);
            this.micDrawable.setOnFinishCallback(null, 0);
            if (z5 && this.lastRaisedHand) {
                customEndFrame = this.micDrawable.setCustomEndFrame(36);
            } else {
                customEndFrame = this.micDrawable.setCustomEndFrame(z5 ? 99 : 69);
            }
        }
        if (!z) {
            RLottieDrawable rLottieDrawable = this.micDrawable;
            rLottieDrawable.setCurrentFrame(rLottieDrawable.getCustomEndFrame() - 1, false, true);
            this.iconView.invalidate();
        } else if (customEndFrame) {
            if (z6) {
                this.micDrawable.setCurrentFrame(99);
                this.micDrawable.setCustomEndFrame(Opcodes.L2I);
            } else if (z5 && this.lastRaisedHand && !z6) {
                this.micDrawable.setCurrentFrame(0);
                this.micDrawable.setCustomEndFrame(36);
            } else if (z5) {
                this.micDrawable.setCurrentFrame(69);
                this.micDrawable.setCustomEndFrame(99);
            } else {
                this.micDrawable.setCurrentFrame(36);
                this.micDrawable.setCustomEndFrame(69);
            }
            this.iconView.playAnimation();
            this.iconView.invalidate();
        }
        this.iconView.setAnimation(this.micDrawable);
        this.lastMuted = z5;
        this.lastRaisedHand = z6;
        if (this.mutedByMe != z4) {
            this.mutedByMe = z4;
            Callback callback = this.callback;
            if (callback != null) {
                callback.onStatusChanged();
            }
        }
    }

    public boolean isSpeaking() {
        return this.isSpeaking;
    }

    public boolean isMutedByMe() {
        return this.mutedByMe;
    }

    public boolean isMutedByAdmin() {
        TLRPC.GroupCallParticipant groupCallParticipant = this.participant;
        return (groupCallParticipant == null || !groupCallParticipant.muted || groupCallParticipant.can_self_unmute) ? false : true;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
        if (callback == null) {
            this.isSpeaking = false;
            AndroidUtilities.cancelRunOnUIThread(this.updateRunnable);
            AndroidUtilities.cancelRunOnUIThread(this.raiseHandCallback);
            AndroidUtilities.cancelRunOnUIThread(this.checkRaiseRunnable);
            this.micDrawable.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.MULTIPLY));
        }
    }
}
