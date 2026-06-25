package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.e */
/* JADX INFO: loaded from: classes5.dex */
final class C1797e extends AbstractC1798f {

    /* JADX INFO: renamed from: a */
    private final int f616a;

    /* JADX INFO: renamed from: b */
    private final long f617b;

    public C1797e(int i, long j) {
        this.f616a = i;
        this.f617b = j;
    }

    @Override // com.google.android.play.integrity.internal.AbstractC1798f
    /* JADX INFO: renamed from: a */
    public final int mo480a() {
        return this.f616a;
    }

    @Override // com.google.android.play.integrity.internal.AbstractC1798f
    /* JADX INFO: renamed from: b */
    public final long mo481b() {
        return this.f617b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractC1798f) {
            AbstractC1798f abstractC1798f = (AbstractC1798f) obj;
            if (this.f616a == abstractC1798f.mo480a() && this.f617b == abstractC1798f.mo481b()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.f616a ^ 1000003;
        long j = this.f617b;
        return ((int) (j ^ (j >>> 32))) ^ (i * 1000003);
    }

    public final String toString() {
        return "EventRecord{eventType=" + this.f616a + ", eventTimestamp=" + this.f617b + "}";
    }
}
