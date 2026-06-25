package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m876d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010(\n\u0000\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\u0010\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H¦\u0082\u0004¨\u0006\u0005"}, m877d2 = {"Lkotlin/sequences/Sequence;", "T", _UrlKt.FRAGMENT_ENCODE_SET, "iterator", _UrlKt.FRAGMENT_ENCODE_SET, "kotlin-stdlib"}, m878k = 1, m879mv = {2, 3, 0}, m881xi = 48)
public interface Sequence<T> {
    Iterator<T> iterator();
}
