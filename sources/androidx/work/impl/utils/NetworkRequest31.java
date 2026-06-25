package androidx.work.impl.utils;

import android.net.NetworkRequest;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÃ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\t"}, m877d2 = {"Landroidx/work/impl/utils/NetworkRequest31;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "capabilities", _UrlKt.FRAGMENT_ENCODE_SET, "request", "Landroid/net/NetworkRequest;", "transportTypes", "work-runtime_release"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
final class NetworkRequest31 {
    public static final NetworkRequest31 INSTANCE = new NetworkRequest31();

    private NetworkRequest31() {
    }

    public final int[] capabilities(NetworkRequest request) {
        return request.getCapabilities();
    }

    public final int[] transportTypes(NetworkRequest request) {
        return request.getTransportTypes();
    }
}
