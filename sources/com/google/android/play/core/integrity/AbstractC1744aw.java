package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractRunnableC1810r;
import com.google.android.play.integrity.internal.C1785ad;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.aw */
/* JADX INFO: loaded from: classes5.dex */
abstract class AbstractC1744aw extends AbstractRunnableC1810r {

    /* JADX INFO: renamed from: f */
    final /* synthetic */ C1745ax f541f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractC1744aw(C1745ax c1745ax, TaskCompletionSource taskCompletionSource) {
        super(taskCompletionSource);
        this.f541f = c1745ax;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1810r
    /* JADX INFO: renamed from: a */
    public final void mo406a(Exception exc) {
        if (!(exc instanceof C1785ad)) {
            super.mo406a(exc);
        } else if (C1745ax.m425g(this.f541f)) {
            super.mo406a(new StandardIntegrityException(-2, exc));
        } else {
            super.mo406a(new StandardIntegrityException(-9, exc));
        }
    }
}
