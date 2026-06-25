package com.google.firebase.sessions;

import kotlin.Metadata;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m876d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bà\u0080\u0001\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m877d2 = {"Lcom/google/firebase/sessions/SessionFirelogPublisher;", _UrlKt.FRAGMENT_ENCODE_SET, "Lcom/google/firebase/sessions/SessionDetails;", "sessionDetails", _UrlKt.FRAGMENT_ENCODE_SET, "mayLogSession", "(Lcom/google/firebase/sessions/SessionDetails;)V", "com.google.firebase-firebase-sessions"}, m878k = 1, m879mv = {2, 0, 0}, m881xi = 48)
public interface SessionFirelogPublisher {
    void mayLogSession(SessionDetails sessionDetails);
}
