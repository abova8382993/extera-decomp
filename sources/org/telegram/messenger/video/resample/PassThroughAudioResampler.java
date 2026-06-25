package org.telegram.messenger.video.resample;

import java.nio.ShortBuffer;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes3.dex */
public class PassThroughAudioResampler implements AudioResampler {
    @Override // org.telegram.messenger.video.resample.AudioResampler
    public void resample(ShortBuffer shortBuffer, int i, ShortBuffer shortBuffer2, int i2, int i3) {
        if (i != i2) {
            g$$ExternalSyntheticBUOutline1.m207m("Illegal use of PassThroughAudioResampler");
        } else {
            shortBuffer2.put(shortBuffer);
        }
    }
}
