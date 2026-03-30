package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.C1603q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.au */
/* JADX INFO: loaded from: classes4.dex */
final class BinderC1536au extends BinderC1535at {

    /* JADX INFO: renamed from: c */
    private final C1603q f442c;

    BinderC1536au(C1539ax c1539ax, TaskCompletionSource taskCompletionSource) {
        super(c1539ax, taskCompletionSource);
        this.f442c = new C1603q("OnRequestIntegrityTokenCallback");
    }

    @Override // com.google.android.play.core.integrity.BinderC1535at, com.google.android.play.integrity.internal.InterfaceC1597k
    /* JADX INFO: renamed from: c */
    public final void mo357c(Bundle bundle) {
        super.mo357c(bundle);
        this.f442c.m430c("onRequestExpressIntegrityToken", new Object[0]);
        int i = bundle.getInt("error");
        if (i != 0) {
            this.f440a.trySetException(new StandardIntegrityException(i, null));
            return;
        }
        PendingIntent pendingIntent = Build.VERSION.SDK_INT >= 33 ? (PendingIntent) bundle.getParcelable("dialog.intent", PendingIntent.class) : (PendingIntent) bundle.getParcelable("dialog.intent");
        TaskCompletionSource taskCompletionSource = this.f440a;
        C1542b c1542b = new C1542b();
        c1542b.mo369c(bundle.getString("token"));
        c1542b.mo368b(this.f442c);
        c1542b.mo367a(pendingIntent);
        taskCompletionSource.trySetResult(c1542b.mo370d());
    }
}
