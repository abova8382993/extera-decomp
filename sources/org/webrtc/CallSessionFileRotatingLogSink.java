package org.webrtc;

import org.webrtc.Logging;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes7.dex */
public class CallSessionFileRotatingLogSink {
    private long nativeSink;

    private static native long nativeAddSink(String str, int i, int i2);

    private static native void nativeDeleteSink(long j);

    private static native byte[] nativeGetLogData(String str);

    public static byte[] getLogData(String str) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("dirPath may not be null.");
            return null;
        }
        return nativeGetLogData(str);
    }

    public CallSessionFileRotatingLogSink(String str, int i, Logging.Severity severity) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("dirPath may not be null.");
            throw null;
        }
        this.nativeSink = nativeAddSink(str, i, severity.ordinal());
    }

    public void dispose() {
        long j = this.nativeSink;
        if (j != 0) {
            nativeDeleteSink(j);
            this.nativeSink = 0L;
        }
    }
}
