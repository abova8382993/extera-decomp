package com.google.android.play.core.integrity;

import com.google.android.play.core.integrity.IntegrityTokenRequest;
import okio.Segment$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.c */
/* JADX INFO: loaded from: classes5.dex */
final class C1754c extends IntegrityTokenRequest.Builder {

    /* JADX INFO: renamed from: a */
    private String f559a;

    /* JADX INFO: renamed from: b */
    private Long f560b;

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest.Builder
    public final IntegrityTokenRequest build() {
        String str = this.f559a;
        if (str != null) {
            return new C1757e(str, this.f560b, null, null);
        }
        Segment$$ExternalSyntheticBUOutline1.m992m("Missing required properties: nonce");
        return null;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest.Builder
    public final IntegrityTokenRequest.Builder setCloudProjectNumber(long j) {
        this.f560b = Long.valueOf(j);
        return this;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest.Builder
    public final IntegrityTokenRequest.Builder setNonce(String str) {
        if (str != null) {
            this.f559a = str;
            return this;
        }
        g$$ExternalSyntheticBUOutline2.m208m("Null nonce");
        return null;
    }
}
