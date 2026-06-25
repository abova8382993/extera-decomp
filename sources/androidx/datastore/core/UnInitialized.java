package androidx.datastore.core;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, m877d2 = {"Landroidx/datastore/core/UnInitialized;", "Landroidx/datastore/core/State;", _UrlKt.FRAGMENT_ENCODE_SET, "()V", "datastore-core_release"}, m878k = 1, m879mv = {1, 8, 0}, m881xi = 48)
public final class UnInitialized extends State<Object> {
    public static final UnInitialized INSTANCE = new UnInitialized();

    private UnInitialized() {
        super(-1, null);
    }
}
