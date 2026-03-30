package com.google.mlkit.p020nl.languageid.bundled.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.p020nl.languageid.IdentifiedLanguage;
import com.google.mlkit.p020nl.languageid.LanguageIdentificationOptions;
import com.google.mlkit.p020nl.languageid.internal.LanguageIdentifierDelegate;
import java.nio.MappedByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ThickLanguageIdentifier implements LanguageIdentifierDelegate {
    private static boolean zba;
    private final Context zbb;
    private long zbc;

    ThickLanguageIdentifier(Context context, LanguageIdentificationOptions languageIdentificationOptions) {
        this.zbb = context;
    }

    private native void nativeDestroy(long j);

    private native IdentifiedLanguage[] nativeIdentifyPossibleLanguages(long j, byte[] bArr, float f);

    private native long nativeInitFromBuffer(MappedByteBuffer mappedByteBuffer, long j);

    public static synchronized void zba() {
        if (zba) {
            return;
        }
        try {
            System.loadLibrary("language_id_l2c_jni");
            zba = true;
        } catch (UnsatisfiedLinkError e) {
            throw new MlKitException("Couldn't load language identification library.", 13, e);
        }
    }

    @Override // com.google.mlkit.p020nl.languageid.internal.LanguageIdentifierDelegate
    public final List identifyPossibleLanguages(String str, float f) {
        Preconditions.checkState(this.zbc != 0);
        IdentifiedLanguage[] identifiedLanguageArrNativeIdentifyPossibleLanguages = nativeIdentifyPossibleLanguages(this.zbc, str.getBytes(StandardCharsets.UTF_8), f);
        ArrayList arrayList = new ArrayList();
        for (IdentifiedLanguage identifiedLanguage : identifiedLanguageArrNativeIdentifyPossibleLanguages) {
            arrayList.add(new IdentifiedLanguage(identifiedLanguage.getLanguageTag(), identifiedLanguage.getConfidence()));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x006c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[Catch: IOException -> 0x0050, DONT_GENERATE, FINALLY_INSNS, SYNTHETIC, TRY_LEAVE, TryCatch #3 {IOException -> 0x0050, blocks: (B:7:0x0013, B:13:0x004c, B:34:0x0074, B:33:0x0071, B:30:0x006c, B:8:0x0020, B:12:0x0049, B:28:0x0069, B:27:0x0066, B:24:0x0061, B:9:0x002d, B:19:0x0055, B:20:0x005c), top: B:42:0x0013, inners: #0, #1 }] */
    @Override // com.google.mlkit.p020nl.languageid.internal.LanguageIdentifierDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void init() throws com.google.mlkit.common.MlKitException {
        /*
            r12 = this;
            long r0 = r12.zbc
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto La
            r0 = 1
            goto Lb
        La:
            r0 = 0
        Lb:
            com.google.android.gms.common.internal.Preconditions.checkState(r0)
            zba()
            r1 = 13
            android.content.Context r0 = r12.zbb     // Catch: java.io.IOException -> L50
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch: java.io.IOException -> L50
            java.lang.String r4 = "tflite_langid.tflite.jpg"
            android.content.res.AssetFileDescriptor r4 = r0.openFd(r4)     // Catch: java.io.IOException -> L50
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L52
            java.io.FileDescriptor r5 = r4.getFileDescriptor()     // Catch: java.lang.Throwable -> L52
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L52
            java.nio.channels.FileChannel r6 = r0.getChannel()     // Catch: java.lang.Throwable -> L52
            java.nio.channels.FileChannel$MapMode r7 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L5d
            long r8 = r4.getStartOffset()     // Catch: java.lang.Throwable -> L5d
            long r10 = r4.getDeclaredLength()     // Catch: java.lang.Throwable -> L5d
            java.nio.MappedByteBuffer r0 = r6.map(r7, r8, r10)     // Catch: java.lang.Throwable -> L5d
            long r7 = r4.getDeclaredLength()     // Catch: java.lang.Throwable -> L5d
            long r7 = r12.nativeInitFromBuffer(r0, r7)     // Catch: java.lang.Throwable -> L5d
            r12.zbc = r7     // Catch: java.lang.Throwable -> L5d
            int r0 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r0 == 0) goto L55
            r6.close()     // Catch: java.lang.Throwable -> L52
            r4.close()     // Catch: java.io.IOException -> L50
            return
        L50:
            r0 = move-exception
            goto L75
        L52:
            r0 = move-exception
            r2 = r0
            goto L6a
        L55:
            com.google.mlkit.common.MlKitException r0 = new com.google.mlkit.common.MlKitException     // Catch: java.lang.Throwable -> L5d
            java.lang.String r2 = "Couldn't load language identification model"
            r0.<init>(r2, r1)     // Catch: java.lang.Throwable -> L5d
            throw r0     // Catch: java.lang.Throwable -> L5d
        L5d:
            r0 = move-exception
            r2 = r0
            if (r6 == 0) goto L69
            r6.close()     // Catch: java.lang.Throwable -> L65
            goto L69
        L65:
            r0 = move-exception
            r2.addSuppressed(r0)     // Catch: java.lang.Throwable -> L52
        L69:
            throw r2     // Catch: java.lang.Throwable -> L52
        L6a:
            if (r4 == 0) goto L74
            r4.close()     // Catch: java.lang.Throwable -> L70
            goto L74
        L70:
            r0 = move-exception
            r2.addSuppressed(r0)     // Catch: java.io.IOException -> L50
        L74:
            throw r2     // Catch: java.io.IOException -> L50
        L75:
            com.google.mlkit.common.MlKitException r2 = new com.google.mlkit.common.MlKitException
            java.lang.String r3 = "Couldn't open language identification model file"
            r2.<init>(r3, r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.mlkit.p020nl.languageid.bundled.internal.ThickLanguageIdentifier.init():void");
    }

    @Override // com.google.mlkit.p020nl.languageid.internal.LanguageIdentifierDelegate
    public final void release() {
        long j = this.zbc;
        if (j == 0) {
            return;
        }
        nativeDestroy(j);
        this.zbc = 0L;
    }
}
