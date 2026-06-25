package kotlinx.serialization.json.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.text.StringsKt;
import okhttp3.internal.url._UrlKt;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\b\n\u0000\n\u0002\u0010\b\n\u0000\"\u000f\u0010\u0000\u001a\u00020\u0001X\u0082\u0084\b¢\u0006\u0002\n\u0000¨\u0006\u0002"}, m877d2 = {"MAX_CHARS_IN_POOL", _UrlKt.FRAGMENT_ENCODE_SET, "kotlinx-serialization-json"}, m878k = 2, m879mv = {2, 3, 0}, m881xi = 48)
public abstract class ArrayPoolsKt {
    private static final int MAX_CHARS_IN_POOL;

    static {
        Object objM3494constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            String property = System.getProperty("kotlinx.serialization.json.pool.size");
            objM3494constructorimpl = Result.m3494constructorimpl(property != null ? StringsKt.toIntOrNull(property) : null);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM3494constructorimpl = Result.m3494constructorimpl(ResultKt.createFailure(th));
        }
        Integer num = (Integer) (Result.m3500isFailureimpl(objM3494constructorimpl) ? null : objM3494constructorimpl);
        MAX_CHARS_IN_POOL = num != null ? num.intValue() : TLObject.FLAG_21;
    }
}
