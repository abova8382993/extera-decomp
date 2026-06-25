package com.exteragram.messenger.plugins.hooks;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H&¨\u0006\u0007À\u0006\u0003"}, m877d2 = {"Lcom/exteragram/messenger/plugins/hooks/HookRecord;", _UrlKt.FRAGMENT_ENCODE_SET, "cleanup", _UrlKt.FRAGMENT_ENCODE_SET, "matches", _UrlKt.FRAGMENT_ENCODE_SET, "criteria", "TMessagesProj"}, m878k = 1, m879mv = {2, 2, 0}, m881xi = 48)
public interface HookRecord {
    void cleanup();

    boolean matches(Object criteria);
}
