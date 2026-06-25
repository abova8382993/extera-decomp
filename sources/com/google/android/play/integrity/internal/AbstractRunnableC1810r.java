package com.google.android.play.integrity.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.r */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractRunnableC1810r implements Runnable {

    /* JADX INFO: renamed from: a */
    private final TaskCompletionSource f619a;

    public AbstractRunnableC1810r() {
        this.f619a = null;
    }

    public AbstractRunnableC1810r(TaskCompletionSource taskCompletionSource) {
        this.f619a = taskCompletionSource;
    }

    /* JADX INFO: renamed from: a */
    public void mo406a(Exception exc) {
        TaskCompletionSource taskCompletionSource = this.f619a;
        if (taskCompletionSource != null) {
            taskCompletionSource.trySetException(exc);
        }
    }

    /* JADX INFO: renamed from: b */
    public abstract void mo407b();

    /* JADX INFO: renamed from: c */
    public final TaskCompletionSource m493c() {
        return this.f619a;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            mo407b();
        } catch (Exception e) {
            mo406a(e);
        }
    }
}
