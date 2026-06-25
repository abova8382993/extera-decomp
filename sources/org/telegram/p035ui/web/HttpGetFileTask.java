package org.telegram.p035ui.web;

import android.os.AsyncTask;
import androidx.annotation.Keep;
import java.io.File;
import org.telegram.messenger.Utilities;

/* JADX INFO: loaded from: classes7.dex */
@Keep
public class HttpGetFileTask extends AsyncTask<String, Void, File> {
    private Utilities.Callback<File> doneCallback;
    private Exception exception;
    private File file;
    private long max_size = -1;
    private String overrideExt;
    private Utilities.Callback<Float> progressCallback;

    public HttpGetFileTask(Utilities.Callback<File> callback, Utilities.Callback<Float> callback2) {
        this.doneCallback = callback;
        this.progressCallback = callback2;
    }

    @Keep
    public HttpGetFileTask setOverrideExtension(String str) {
        this.overrideExt = str;
        return this;
    }

    @Keep
    public HttpGetFileTask setDestFile(File file) {
        this.file = file;
        return this;
    }

    @Keep
    public HttpGetFileTask setMaxSize(long j) {
        this.max_size = j;
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:218:0x00e0, code lost:
    
        r18.file.delete();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:194:0x007f A[Catch: Exception -> 0x0041, PHI: r7 r10
  0x007f: PHI (r7v4 ??) = (r7v19 ??), (r7v20 ??), (r7v17 ??) binds: [B:182:0x0062, B:184:0x0066, B:193:0x007d] A[DONT_GENERATE, DONT_INLINE]
  0x007f: PHI (r10v2 boolean) = (r10v1 boolean), (r10v1 boolean), (r10v5 boolean) binds: [B:182:0x0062, B:184:0x0066, B:193:0x007d] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #13 {Exception -> 0x0041, blocks: (B:170:0x0013, B:172:0x0025, B:175:0x0046, B:179:0x0055, B:181:0x005e, B:185:0x0068, B:194:0x007f, B:198:0x008d, B:200:0x0094, B:202:0x0097, B:204:0x009b, B:208:0x00ac, B:207:0x00a0, B:209:0x00b4, B:180:0x005a), top: B:302:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x009b A[Catch: Exception -> 0x0041, TryCatch #13 {Exception -> 0x0041, blocks: (B:170:0x0013, B:172:0x0025, B:175:0x0046, B:179:0x0055, B:181:0x005e, B:185:0x0068, B:194:0x007f, B:198:0x008d, B:200:0x0094, B:202:0x0097, B:204:0x009b, B:208:0x00ac, B:207:0x00a0, B:209:0x00b4, B:180:0x005a), top: B:302:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:284:0x00d1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:310:0x011a A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v10 */
    /* JADX WARN: Type inference failed for: r16v11 */
    /* JADX WARN: Type inference failed for: r16v12, types: [long] */
    /* JADX WARN: Type inference failed for: r16v13 */
    /* JADX WARN: Type inference failed for: r16v2, types: [int] */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    /* JADX WARN: Type inference failed for: r16v9 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [long] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [long] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12, types: [long] */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14, types: [long] */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.io.File doInBackground(java.lang.String... r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 387
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.web.HttpGetFileTask.doInBackground(java.lang.String[]):java.io.File");
    }

    public /* synthetic */ void lambda$doInBackground$0(float f) {
        this.progressCallback.run(Float.valueOf(f));
    }

    public /* synthetic */ void lambda$doInBackground$1() {
        this.progressCallback.run(Float.valueOf(1.0f));
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(File file) {
        Utilities.Callback<File> callback = this.doneCallback;
        if (callback != null) {
            if (this.exception == null) {
                callback.run(file);
            } else {
                callback.run(null);
            }
        }
    }
}
