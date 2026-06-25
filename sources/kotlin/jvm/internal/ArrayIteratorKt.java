package kotlin.jvm.internal;

import java.util.Iterator;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m876d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a)\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0086\u0080\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, m877d2 = {"iterator", _UrlKt.FRAGMENT_ENCODE_SET, "T", "array", _UrlKt.FRAGMENT_ENCODE_SET, "([Ljava/lang/Object;)Ljava/util/Iterator;", "kotlin-stdlib"}, m878k = 2, m879mv = {2, 3, 0}, m881xi = 48)
public final class ArrayIteratorKt {
    public static final <T> Iterator<T> iterator(T[] tArr) {
        return new ArrayIterator(tArr);
    }
}
