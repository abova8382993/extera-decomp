package org.telegram.ui.web;

import android.os.AsyncTask;
import androidx.annotation.Keep;
import java.io.File;
import org.telegram.messenger.Utilities;

/* JADX INFO: loaded from: classes6.dex */
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

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00ec, code lost:
    
        r18.file.delete();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:140:0x00dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0126 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007f A[Catch: Exception -> 0x0041, PHI: r7 r10
  0x007f: PHI (r7v4 ??) = (r7v18 ??), (r7v19 ??), (r7v16 ??) binds: [B:21:0x0062, B:23:0x0066, B:32:0x007d] A[DONT_GENERATE, DONT_INLINE]
  0x007f: PHI (r10v2 boolean) = (r10v1 boolean), (r10v1 boolean), (r10v5 boolean) binds: [B:21:0x0062, B:23:0x0066, B:32:0x007d] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #13 {Exception -> 0x0041, blocks: (B:9:0x0013, B:11:0x0025, B:14:0x0046, B:18:0x0055, B:20:0x005e, B:24:0x0068, B:33:0x007f, B:35:0x0085, B:37:0x008f, B:41:0x0099, B:43:0x00a0, B:45:0x00a3, B:47:0x00a7, B:51:0x00b8, B:50:0x00ac, B:52:0x00c0, B:36:0x008a, B:19:0x005a), top: B:144:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0085 A[Catch: Exception -> 0x0041, TryCatch #13 {Exception -> 0x0041, blocks: (B:9:0x0013, B:11:0x0025, B:14:0x0046, B:18:0x0055, B:20:0x005e, B:24:0x0068, B:33:0x007f, B:35:0x0085, B:37:0x008f, B:41:0x0099, B:43:0x00a0, B:45:0x00a3, B:47:0x00a7, B:51:0x00b8, B:50:0x00ac, B:52:0x00c0, B:36:0x008a, B:19:0x005a), top: B:144:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008a A[Catch: Exception -> 0x0041, TryCatch #13 {Exception -> 0x0041, blocks: (B:9:0x0013, B:11:0x0025, B:14:0x0046, B:18:0x0055, B:20:0x005e, B:24:0x0068, B:33:0x007f, B:35:0x0085, B:37:0x008f, B:41:0x0099, B:43:0x00a0, B:45:0x00a3, B:47:0x00a7, B:51:0x00b8, B:50:0x00ac, B:52:0x00c0, B:36:0x008a, B:19:0x005a), top: B:144:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a7 A[Catch: Exception -> 0x0041, TryCatch #13 {Exception -> 0x0041, blocks: (B:9:0x0013, B:11:0x0025, B:14:0x0046, B:18:0x0055, B:20:0x005e, B:24:0x0068, B:33:0x007f, B:35:0x0085, B:37:0x008f, B:41:0x0099, B:43:0x00a0, B:45:0x00a3, B:47:0x00a7, B:51:0x00b8, B:50:0x00ac, B:52:0x00c0, B:36:0x008a, B:19:0x005a), top: B:144:0x0013 }] */
    /* JADX WARN: Type inference failed for: r13v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v10 */
    /* JADX WARN: Type inference failed for: r16v11, types: [long] */
    /* JADX WARN: Type inference failed for: r16v12 */
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
    /* JADX WARN: Type inference failed for: r7v11, types: [long] */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13, types: [long] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
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
            Method dump skipped, instruction units count: 399
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.web.HttpGetFileTask.doInBackground(java.lang.String[]):java.io.File");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doInBackground$0(float f) {
        this.progressCallback.run(Float.valueOf(f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doInBackground$1() {
        this.progressCallback.run(Float.valueOf(1.0f));
    }

    /* JADX INFO: Access modifiers changed from: protected */
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
