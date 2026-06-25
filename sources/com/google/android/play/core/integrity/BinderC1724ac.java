package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1807o;
import com.google.android.play.integrity.internal.C1809q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ac */
/* JADX INFO: loaded from: classes5.dex */
final class BinderC1724ac extends AbstractBinderC1807o {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C1725ad f507a;

    /* JADX INFO: renamed from: b */
    private final C1809q f508b = new C1809q("OnRequestIntegrityTokenCallback");

    /* JADX INFO: renamed from: c */
    private final TaskCompletionSource f509c;

    public BinderC1724ac(C1725ad c1725ad, TaskCompletionSource taskCompletionSource) {
        this.f507a = c1725ad;
        this.f509c = taskCompletionSource;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1808p
    /* JADX INFO: renamed from: b */
    public final void mo408b(Bundle bundle) {
        this.f507a.f510a.m467v(this.f509c);
        this.f508b.m491c("onRequestIntegrityToken", new Object[0]);
        int i = bundle.getInt("error");
        if (i != 0) {
            this.f509c.trySetException(new IntegrityServiceException(i, null));
            return;
        }
        String string = bundle.getString("token");
        if (string == null) {
            this.f509c.trySetException(new IntegrityServiceException(-100, null));
            return;
        }
        PendingIntent pendingIntent = Build.VERSION.SDK_INT >= 33 ? (PendingIntent) bundle.getParcelable("dialog.intent", PendingIntent.class) : (PendingIntent) bundle.getParcelable("dialog.intent");
        TaskCompletionSource taskCompletionSource = this.f509c;
        C1721a c1721a = new C1721a();
        c1721a.mo403c(string);
        c1721a.mo402b(this.f508b);
        c1721a.mo401a(pendingIntent);
        taskCompletionSource.trySetResult(c1721a.mo404d());
    }
}
