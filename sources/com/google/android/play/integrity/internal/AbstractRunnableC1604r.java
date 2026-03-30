package com.google.android.play.integrity.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.r */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractRunnableC1604r implements Runnable {

    /* JADX INFO: renamed from: a */
    private final TaskCompletionSource f522a;

    AbstractRunnableC1604r() {
        this.f522a = null;
    }

    public AbstractRunnableC1604r(TaskCompletionSource taskCompletionSource) {
        this.f522a = taskCompletionSource;
    }

    /* JADX INFO: renamed from: a */
    public void mo345a(Exception exc) {
        TaskCompletionSource taskCompletionSource = this.f522a;
        if (taskCompletionSource != null) {
            taskCompletionSource.trySetException(exc);
        }
    }

    /* JADX INFO: renamed from: b */
    protected abstract void mo346b();

    /* JADX INFO: renamed from: c */
    final TaskCompletionSource m432c() {
        return this.f522a;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            mo346b();
        } catch (Exception e) {
            mo345a(e);
        }
    }
}
