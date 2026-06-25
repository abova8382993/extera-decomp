package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes5.dex */
@Metadata(m876d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\t\bB¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0084\b¢\u0006\u0002\n\u0000¨\u0006\u0006"}, m877d2 = {"Lkotlin/text/SystemProperties;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "LINE_SEPARATOR", _UrlKt.FRAGMENT_ENCODE_SET, "kotlin-stdlib"}, m878k = 1, m879mv = {2, 3, 0}, m881xi = 48)
final class SystemProperties {
    public static final SystemProperties INSTANCE = new SystemProperties();

    @JvmField
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private SystemProperties() {
    }
}
