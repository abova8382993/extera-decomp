package com.google.mlkit.p024nl.languageid.bundled.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.p024nl.languageid.IdentifiedLanguage;
import com.google.mlkit.p024nl.languageid.LanguageIdentificationOptions;
import com.google.mlkit.p024nl.languageid.internal.LanguageIdentifierDelegate;
import java.nio.MappedByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ThickLanguageIdentifier implements LanguageIdentifierDelegate {
    private static boolean zba;
    private final Context zbb;
    private long zbc;

    public ThickLanguageIdentifier(Context context, LanguageIdentificationOptions languageIdentificationOptions) {
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

    @Override // com.google.mlkit.p024nl.languageid.internal.LanguageIdentifierDelegate
    public final List identifyPossibleLanguages(String str, float f) {
        Preconditions.checkState(this.zbc != 0);
        IdentifiedLanguage[] identifiedLanguageArrNativeIdentifyPossibleLanguages = nativeIdentifyPossibleLanguages(this.zbc, str.getBytes(StandardCharsets.UTF_8), f);
        ArrayList arrayList = new ArrayList();
        for (IdentifiedLanguage identifiedLanguage : identifiedLanguageArrNativeIdentifyPossibleLanguages) {
            arrayList.add(new IdentifiedLanguage(identifiedLanguage.getLanguageTag(), identifiedLanguage.getConfidence()));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[Catch: IOException -> 0x0050, DONT_GENERATE, FINALLY_INSNS, SYNTHETIC, TRY_LEAVE, TryCatch #3 {IOException -> 0x0050, blocks: (B:7:0x0013, B:13:0x004c, B:34:0x0075, B:33:0x0072, B:30:0x006d, B:8:0x0020, B:12:0x0049, B:28:0x006a, B:27:0x0067, B:24:0x0062, B:9:0x002d, B:19:0x0056, B:20:0x005d), top: B:42:0x0013, inners: #0, #1 }] */
    @Override // com.google.mlkit.p024nl.languageid.internal.LanguageIdentifierDelegate
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
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L53
            java.io.FileDescriptor r5 = r4.getFileDescriptor()     // Catch: java.lang.Throwable -> L53
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L53
            java.nio.channels.FileChannel r6 = r0.getChannel()     // Catch: java.lang.Throwable -> L53
            java.nio.channels.FileChannel$MapMode r7 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch: java.lang.Throwable -> L5e
            long r8 = r4.getStartOffset()     // Catch: java.lang.Throwable -> L5e
            long r10 = r4.getDeclaredLength()     // Catch: java.lang.Throwable -> L5e
            java.nio.MappedByteBuffer r0 = r6.map(r7, r8, r10)     // Catch: java.lang.Throwable -> L5e
            long r7 = r4.getDeclaredLength()     // Catch: java.lang.Throwable -> L5e
            long r7 = r12.nativeInitFromBuffer(r0, r7)     // Catch: java.lang.Throwable -> L5e
            r12.zbc = r7     // Catch: java.lang.Throwable -> L5e
            int r12 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r12 == 0) goto L56
            r6.close()     // Catch: java.lang.Throwable -> L53
            r4.close()     // Catch: java.io.IOException -> L50
            return
        L50:
            r0 = move-exception
            r12 = r0
            goto L76
        L53:
            r0 = move-exception
            r12 = r0
            goto L6b
        L56:
            com.google.mlkit.common.MlKitException r12 = new com.google.mlkit.common.MlKitException     // Catch: java.lang.Throwable -> L5e
            java.lang.String r0 = "Couldn't load language identification model"
            r12.<init>(r0, r1)     // Catch: java.lang.Throwable -> L5e
            throw r12     // Catch: java.lang.Throwable -> L5e
        L5e:
            r0 = move-exception
            r12 = r0
            if (r6 == 0) goto L6a
            r6.close()     // Catch: java.lang.Throwable -> L66
            goto L6a
        L66:
            r0 = move-exception
            r12.addSuppressed(r0)     // Catch: java.lang.Throwable -> L53
        L6a:
            throw r12     // Catch: java.lang.Throwable -> L53
        L6b:
            if (r4 == 0) goto L75
            r4.close()     // Catch: java.lang.Throwable -> L71
            goto L75
        L71:
            r0 = move-exception
            r12.addSuppressed(r0)     // Catch: java.io.IOException -> L50
        L75:
            throw r12     // Catch: java.io.IOException -> L50
        L76:
            com.google.mlkit.common.MlKitException r0 = new com.google.mlkit.common.MlKitException
            java.lang.String r2 = "Couldn't open language identification model file"
            r0.<init>(r2, r1, r12)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.mlkit.p024nl.languageid.bundled.internal.ThickLanguageIdentifier.init():void");
    }

    @Override // com.google.mlkit.p024nl.languageid.internal.LanguageIdentifierDelegate
    public final void release() {
        long j = this.zbc;
        if (j == 0) {
            return;
        }
        nativeDestroy(j);
        this.zbc = 0L;
    }
}
