package com.google.android.play.core.integrity;

import android.content.Context;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractC1788ag;
import com.google.android.play.integrity.internal.AbstractRunnableC1810r;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.aq */
/* JADX INFO: loaded from: classes5.dex */
final class C1738aq extends AbstractRunnableC1810r {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Context f527a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C1745ax f528b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1738aq(C1745ax c1745ax, TaskCompletionSource taskCompletionSource, Context context) {
        super(taskCompletionSource);
        this.f528b = c1745ax;
        this.f527a = context;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: b */
    public final void mo407b() {
        this.f528b.f545d.trySetResult(Boolean.valueOf(AbstractC1788ag.m470a(this.f527a)));
    }
}
