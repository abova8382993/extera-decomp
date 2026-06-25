package androidx.core.backported.fixes;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, m877d2 = {"Landroidx/core/backported/fixes/StatusResolver;", _UrlKt.FRAGMENT_ENCODE_SET, "getStatus", "Landroidx/core/backported/fixes/Status;", "ki", "Landroidx/core/backported/fixes/KnownIssue;", "core-backported-fixes"}, m878k = 1, m879mv = {2, 0, 0}, m881xi = 48)
public interface StatusResolver {
    Status getStatus(KnownIssue ki);
}
