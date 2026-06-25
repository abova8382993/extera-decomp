package com.google.android.play.core.integrity;

import android.net.Network;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.e */
/* JADX INFO: loaded from: classes5.dex */
final class C1757e extends IntegrityTokenRequest {

    /* JADX INFO: renamed from: a */
    private final String f561a;

    /* JADX INFO: renamed from: b */
    private final Long f562b;

    public /* synthetic */ C1757e(String str, Long l, Network network, C1756d c1756d) {
        this.f561a = str;
        this.f562b = l;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    /* JADX INFO: renamed from: a */
    public final Network mo398a() {
        return null;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    public final Long cloudProjectNumber() {
        return this.f562b;
    }

    public final boolean equals(Object obj) {
        Long l;
        if (obj == this) {
            return true;
        }
        if (obj instanceof IntegrityTokenRequest) {
            IntegrityTokenRequest integrityTokenRequest = (IntegrityTokenRequest) obj;
            if (this.f561a.equals(integrityTokenRequest.nonce()) && ((l = this.f562b) != null ? l.equals(integrityTokenRequest.cloudProjectNumber()) : integrityTokenRequest.cloudProjectNumber() == null)) {
                integrityTokenRequest.mo398a();
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.f561a.hashCode() ^ 1000003;
        Long l = this.f562b;
        return ((l == null ? 0 : l.hashCode()) ^ (iHashCode * 1000003)) * 1000003;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    public final String nonce() {
        return this.f561a;
    }

    public final String toString() {
        return "IntegrityTokenRequest{nonce=" + this.f561a + ", cloudProjectNumber=" + this.f562b + ", network=null}";
    }
}
