package com.google.android.play.core.integrity;

import android.net.Network;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.e */
/* JADX INFO: loaded from: classes4.dex */
final class C1551e extends IntegrityTokenRequest {

    /* JADX INFO: renamed from: a */
    private final String f464a;

    /* JADX INFO: renamed from: b */
    private final Long f465b;

    /* synthetic */ C1551e(String str, Long l, Network network, C1550d c1550d) {
        this.f464a = str;
        this.f465b = l;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    /* JADX INFO: renamed from: a */
    public final Network mo337a() {
        return null;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    public final Long cloudProjectNumber() {
        return this.f465b;
    }

    public final boolean equals(Object obj) {
        Long l;
        if (obj == this) {
            return true;
        }
        if (obj instanceof IntegrityTokenRequest) {
            IntegrityTokenRequest integrityTokenRequest = (IntegrityTokenRequest) obj;
            if (this.f464a.equals(integrityTokenRequest.nonce()) && ((l = this.f465b) != null ? l.equals(integrityTokenRequest.cloudProjectNumber()) : integrityTokenRequest.cloudProjectNumber() == null)) {
                integrityTokenRequest.mo337a();
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.f464a.hashCode() ^ 1000003;
        Long l = this.f465b;
        return ((iHashCode * 1000003) ^ (l == null ? 0 : l.hashCode())) * 1000003;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    public final String nonce() {
        return this.f464a;
    }

    public final String toString() {
        return "IntegrityTokenRequest{nonce=" + this.f464a + ", cloudProjectNumber=" + this.f465b + ", network=null}";
    }
}
