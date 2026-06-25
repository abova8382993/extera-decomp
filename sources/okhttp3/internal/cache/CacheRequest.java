package okhttp3.internal.cache;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;
import okio.Sink;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m876d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006À\u0006\u0003"}, m877d2 = {"Lokhttp3/internal/cache/CacheRequest;", _UrlKt.FRAGMENT_ENCODE_SET, "body", "Lokio/Sink;", "abort", _UrlKt.FRAGMENT_ENCODE_SET, "okhttp"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public interface CacheRequest {
    void abort();

    Sink body();
}
