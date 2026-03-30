package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractRunnableC1604r;
import com.google.android.play.integrity.internal.C1579ad;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.aw */
/* JADX INFO: loaded from: classes4.dex */
abstract class AbstractC1538aw extends AbstractRunnableC1604r {

    /* JADX INFO: renamed from: f */
    final /* synthetic */ C1539ax f444f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractC1538aw(C1539ax c1539ax, TaskCompletionSource taskCompletionSource) {
        super(taskCompletionSource);
        this.f444f = c1539ax;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1604r
    /* JADX INFO: renamed from: a */
    public final void mo345a(Exception exc) {
        if (!(exc instanceof C1579ad)) {
            super.mo345a(exc);
        } else if (C1539ax.m364g(this.f444f)) {
            super.mo345a(new StandardIntegrityException(-2, exc));
        } else {
            super.mo345a(new StandardIntegrityException(-9, exc));
        }
    }
}
