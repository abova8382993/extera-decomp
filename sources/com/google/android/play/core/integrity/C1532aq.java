package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractC1582ag;
import com.google.android.play.integrity.internal.AbstractRunnableC1604r;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.aq */
/* JADX INFO: loaded from: classes4.dex */
final class C1532aq extends AbstractRunnableC1604r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Context f430a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C1539ax f431b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1532aq(C1539ax c1539ax, TaskCompletionSource taskCompletionSource, Context context) {
        super(taskCompletionSource);
        this.f431b = c1539ax;
        this.f430a = context;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: b */
    protected final void mo346b() {
        this.f431b.f448d.trySetResult(Boolean.valueOf(AbstractC1582ag.m409a(this.f430a)));
    }
}
