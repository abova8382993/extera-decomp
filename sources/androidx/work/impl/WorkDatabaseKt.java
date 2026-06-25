package androidx.work.impl;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.time.DurationKt;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\b\n\u0002\u0010\t\n\u0002\b\u0003\"\u0014\u0010\u0001\u001a\u00020\u00008\u0006X\u0087\u0004¢\u0006\u0006\n\u0004\b\u0001\u0010\u0002¨\u0006\u0003"}, m877d2 = {_UrlKt.FRAGMENT_ENCODE_SET, "PRUNE_THRESHOLD_MILLIS", "J", "work-runtime_release"}, m878k = 2, m879mv = {2, 1, 0}, m881xi = 48)
public abstract class WorkDatabaseKt {

    @JvmField
    public static final long PRUNE_THRESHOLD_MILLIS = DurationKt.MILLIS_IN_DAY;
}
