package androidx.datastore;

import android.content.Context;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, m877d2 = {"dataStoreFile", "Ljava/io/File;", "Landroid/content/Context;", "fileName", _UrlKt.FRAGMENT_ENCODE_SET, "datastore_release"}, m878k = 2, m879mv = {1, 8, 0}, m881xi = 48)
@JvmName(name = "DataStoreFile")
public abstract class DataStoreFile {
    public static final File dataStoreFile(Context context, String str) {
        return new File(context.getApplicationContext().getFilesDir(), "datastore/" + str);
    }
}
