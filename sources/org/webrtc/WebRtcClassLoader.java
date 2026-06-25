package org.webrtc;

/* JADX INFO: loaded from: classes7.dex */
class WebRtcClassLoader {
    @CalledByNative
    public static Object getClassLoader() {
        ClassLoader classLoader = WebRtcClassLoader.class.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        GlShader$$ExternalSyntheticBUOutline1.m1250m("Failed to get WebRTC class loader.");
        return null;
    }
}
