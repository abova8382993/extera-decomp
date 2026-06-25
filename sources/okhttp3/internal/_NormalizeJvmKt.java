package okhttp3.internal;

import java.text.Normalizer;
import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0000¨\u0006\u0003"}, m877d2 = {"normalizeNfc", _UrlKt.FRAGMENT_ENCODE_SET, "string", "okhttp"}, m878k = 2, m879mv = {2, 2, 0}, m881xi = 48)
public final class _NormalizeJvmKt {
    public static final String normalizeNfc(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFC);
    }
}
