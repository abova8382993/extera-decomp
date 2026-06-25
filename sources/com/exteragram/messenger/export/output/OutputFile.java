package com.exteragram.messenger.export.output;

import android.util.Log;
import com.android.p006dx.DexMaker$$ExternalSyntheticBUOutline0;
import com.exteragram.messenger.export.output.AbstractWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.HttpUrl$$ExternalSyntheticBUOutline0;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.NativeByteBuffer;

/* JADX INFO: loaded from: classes4.dex */
public class OutputFile {
    public final File _file;
    private boolean _inStats = false;
    private long _offset = 0;
    private final Stats _stats;

    public OutputFile(String str, Stats stats) {
        File file = new File(str);
        this._file = file;
        try {
            if (file.getPath().contains("/")) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            this._stats = stats;
        } catch (IOException e) {
            HttpUrl$$ExternalSyntheticBUOutline0.m958m(e);
            throw null;
        }
    }

    public static String PrepareRelativePath(String str, String str2) {
        String str3;
        if (!new File(str + "/" + str2).exists()) {
            return str2;
        }
        int iIndexOf = str2.indexOf(46);
        int i = 0;
        final String strSubstring = str2.substring(0, iIndexOf);
        final String strSubstring2 = iIndexOf >= 0 ? str2.substring(iIndexOf) : _UrlKt.FRAGMENT_ENCODE_SET;
        Utilities.CallbackReturn callbackReturn = new Utilities.CallbackReturn() { // from class: com.exteragram.messenger.export.output.OutputFile$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.CallbackReturn
            public final Object run(Object obj) {
                return OutputFile.$r8$lambda$ApE4zRWH4xloOxmAXEkNaQI5bA4(strSubstring, strSubstring2, (Integer) obj);
            }
        };
        do {
            i++;
            str3 = (String) callbackReturn.run(Integer.valueOf(i));
        } while (new File(str + str3).exists());
        return str3;
    }

    public static /* synthetic */ String $r8$lambda$ApE4zRWH4xloOxmAXEkNaQI5bA4(String str, String str2, Integer num) {
        return str + " (" + num + ")" + str2;
    }

    public long size() {
        return this._offset;
    }

    public boolean empty() {
        return this._offset == 0;
    }

    public AbstractWriter.Result writeBlock(String str) throws Throwable {
        AbstractWriter.Result resultWriteBlockAttempt = writeBlockAttempt(str);
        if (resultWriteBlockAttempt.isSuccess()) {
            return resultWriteBlockAttempt;
        }
        DexMaker$$ExternalSyntheticBUOutline0.m217m("result is not success for block: ", str);
        return null;
    }

    public AbstractWriter.Result writeBlock(NativeByteBuffer nativeByteBuffer) {
        AbstractWriter.Result resultWriteBlockAttempt = writeBlockAttempt(nativeByteBuffer);
        if (resultWriteBlockAttempt.isSuccess()) {
            return resultWriteBlockAttempt;
        }
        DexMaker$$ExternalSyntheticBUOutline0.m217m("result is not success for block: ", nativeByteBuffer);
        return null;
    }

    public AbstractWriter.Result writeBlock(byte[] bArr) throws Throwable {
        AbstractWriter.Result resultWriteBlockAttempt = writeBlockAttempt(bArr);
        if (resultWriteBlockAttempt.isSuccess()) {
            return resultWriteBlockAttempt;
        }
        DexMaker$$ExternalSyntheticBUOutline0.m217m("result is not success for block: ", bArr);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0068 A[Catch: IOException -> 0x005c, TRY_ENTER, TRY_LEAVE, TryCatch #4 {IOException -> 0x005c, blocks: (B:40:0x0068, B:32:0x0058), top: B:47:0x0021 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.exteragram.messenger.export.output.AbstractWriter.Result writeBlockAttempt(java.lang.String r9) throws java.lang.Throwable {
        /*
            r8 = this;
            com.exteragram.messenger.export.output.OutputFile$Stats r0 = r8._stats
            r1 = 1
            if (r0 == 0) goto Le
            boolean r2 = r8._inStats
            if (r2 != 0) goto Le
            r8._inStats = r1
            r0.incrementFiles()
        Le:
            int r0 = r9.length()
            if (r0 != 0) goto L20
            java.lang.String r8 = "exteraGram"
            java.lang.String r9 = "size of block to write was zero!"
            android.util.Log.e(r8, r9)
            com.exteragram.messenger.export.output.AbstractWriter$Result r8 = com.exteragram.messenger.export.output.AbstractWriter.Result.Success()
            return r8
        L20:
            r2 = 0
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            java.io.File r4 = r8._file     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r3.<init>(r4, r1)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            byte[] r9 = r9.getBytes()     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            r3.write(r9)     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            long r4 = r8._offset     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            long r6 = (long) r0     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            long r4 = r4 + r6
            r8._offset = r4     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            com.exteragram.messenger.export.output.OutputFile$Stats r8 = r8._stats     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            if (r8 == 0) goto L41
            r8.incrementBytes(r0)     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L3f
            goto L41
        L3d:
            r8 = move-exception
            goto L66
        L3f:
            r8 = move-exception
            goto L53
        L41:
            r3.close()     // Catch: java.io.IOException -> L49
            com.exteragram.messenger.export.output.AbstractWriter$Result r8 = com.exteragram.messenger.export.output.AbstractWriter.Result.Success()     // Catch: java.io.IOException -> L49
            return r8
        L49:
            r8 = move-exception
            okhttp3.HttpUrl$$ExternalSyntheticBUOutline0.m958m(r8)
            return r2
        L4e:
            r8 = move-exception
            r3 = r2
            goto L66
        L51:
            r8 = move-exception
            r3 = r2
        L53:
            org.telegram.messenger.FileLog.m1048e(r8)     // Catch: java.lang.Throwable -> L3d
            if (r3 == 0) goto L61
            r3.close()     // Catch: java.io.IOException -> L5c
            goto L61
        L5c:
            r8 = move-exception
            okhttp3.HttpUrl$$ExternalSyntheticBUOutline0.m958m(r8)
            return r2
        L61:
            com.exteragram.messenger.export.output.AbstractWriter$Result r8 = com.exteragram.messenger.export.output.AbstractWriter.Result.Error()
            return r8
        L66:
            if (r3 == 0) goto L6b
            r3.close()     // Catch: java.io.IOException -> L5c
        L6b:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.export.output.OutputFile.writeBlockAttempt(java.lang.String):com.exteragram.messenger.export.output.AbstractWriter$Result");
    }

    public AbstractWriter.Result writeBlockAttempt(byte[] bArr) throws Throwable {
        FileOutputStream fileOutputStream;
        Stats stats = this._stats;
        if (stats != null && !this._inStats) {
            this._inStats = true;
            stats.incrementFiles();
        }
        int length = bArr.length;
        if (length == 0) {
            Log.e("exteraGram", "size of block to write was zero!");
            return AbstractWriter.Result.Success();
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(this._file, true);
                } catch (Exception e) {
                    e = e;
                    fileOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    if (0 != 0) {
                        fileOutputStream2.close();
                    }
                    throw th;
                }
                try {
                    fileOutputStream.write(bArr);
                    this._offset += (long) length;
                    Stats stats2 = this._stats;
                    if (stats2 != null) {
                        stats2.incrementBytes(length);
                    }
                    try {
                        fileOutputStream.close();
                        return AbstractWriter.Result.Success();
                    } catch (IOException e2) {
                        HttpUrl$$ExternalSyntheticBUOutline0.m958m(e2);
                        return null;
                    }
                } catch (Exception e3) {
                    e = e3;
                    FileLog.m1048e(e);
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    return AbstractWriter.Result.Error();
                }
            } catch (IOException e4) {
                HttpUrl$$ExternalSyntheticBUOutline0.m958m(e4);
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public AbstractWriter.Result writeBlockAttempt(NativeByteBuffer nativeByteBuffer) {
        RandomAccessFile randomAccessFile;
        Stats stats = this._stats;
        if (stats != null && !this._inStats) {
            this._inStats = true;
            stats.incrementFiles();
        }
        int iLimit = nativeByteBuffer.buffer.limit();
        if (iLimit == 0) {
            Log.e("exteraGram", "size of block to write was zero!");
            return AbstractWriter.Result.Success();
        }
        try {
            randomAccessFile = new RandomAccessFile(this._file, "rws");
            try {
                randomAccessFile.seek(randomAccessFile.length());
            } catch (IOException e) {
                e = e;
                FileLog.m1048e(e);
            }
        } catch (IOException e2) {
            e = e2;
            randomAccessFile = null;
        }
        try {
            try {
                try {
                    FileChannel channel = randomAccessFile.getChannel();
                    channel.write(nativeByteBuffer.buffer);
                    this._offset += (long) iLimit;
                    Stats stats2 = this._stats;
                    if (stats2 != null) {
                        stats2.incrementBytes(iLimit);
                    }
                    channel.close();
                    randomAccessFile.close();
                    return AbstractWriter.Result.Success();
                } catch (Exception e3) {
                    FileLog.m1048e(e3);
                    randomAccessFile.close();
                    return AbstractWriter.Result.Error();
                }
            } catch (IOException e4) {
                HttpUrl$$ExternalSyntheticBUOutline0.m958m(e4);
                return null;
            }
        } catch (Throwable th) {
            randomAccessFile.close();
            throw th;
        }
    }

    public static class Stats {
        private final AtomicInteger _files = new AtomicInteger(0);
        private final AtomicLong _bytes = new AtomicLong(0);

        public void incrementFiles() {
            this._files.getAndIncrement();
        }

        public void incrementBytes(int i) {
            this._bytes.addAndGet(i);
        }

        public int filesCount() {
            return this._files.get();
        }

        public long bytesCount() {
            return this._bytes.get();
        }
    }
}
