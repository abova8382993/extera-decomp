package androidx.datastore.core;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, m877d2 = {"Landroidx/datastore/core/ReadException;", "T", "Landroidx/datastore/core/State;", "readException", _UrlKt.FRAGMENT_ENCODE_SET, "version", _UrlKt.FRAGMENT_ENCODE_SET, "(Ljava/lang/Throwable;I)V", "getReadException", "()Ljava/lang/Throwable;", "datastore-core_release"}, m878k = 1, m879mv = {1, 8, 0}, m881xi = 48)
public final class ReadException<T> extends State<T> {
    private final Throwable readException;

    public ReadException(Throwable th, int i) {
        super(i, null);
        this.readException = th;
    }

    public final Throwable getReadException() {
        return this.readException;
    }
}
