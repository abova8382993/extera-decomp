package com.google.android.play.integrity.internal;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.e */
/* JADX INFO: loaded from: classes4.dex */
final class C1591e extends AbstractC1592f {

    /* JADX INFO: renamed from: a */
    private final int f519a;

    /* JADX INFO: renamed from: b */
    private final long f520b;

    C1591e(int i, long j) {
        this.f519a = i;
        this.f520b = j;
    }

    @Override // com.google.android.play.integrity.internal.AbstractC1592f
    /* JADX INFO: renamed from: a */
    public final int mo419a() {
        return this.f519a;
    }

    @Override // com.google.android.play.integrity.internal.AbstractC1592f
    /* JADX INFO: renamed from: b */
    public final long mo420b() {
        return this.f520b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractC1592f) {
            AbstractC1592f abstractC1592f = (AbstractC1592f) obj;
            if (this.f519a == abstractC1592f.mo419a() && this.f520b == abstractC1592f.mo420b()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.f519a ^ 1000003;
        long j = this.f520b;
        return (i * 1000003) ^ ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        return "EventRecord{eventType=" + this.f519a + ", eventTimestamp=" + this.f520b + "}";
    }
}
