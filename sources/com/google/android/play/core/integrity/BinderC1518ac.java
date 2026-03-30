package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1601o;
import com.google.android.play.integrity.internal.C1603q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ac */
/* JADX INFO: loaded from: classes4.dex */
final class BinderC1518ac extends AbstractBinderC1601o {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C1519ad f410a;

    /* JADX INFO: renamed from: b */
    private final C1603q f411b = new C1603q("OnRequestIntegrityTokenCallback");

    /* JADX INFO: renamed from: c */
    private final TaskCompletionSource f412c;

    BinderC1518ac(C1519ad c1519ad, TaskCompletionSource taskCompletionSource) {
        this.f410a = c1519ad;
        this.f412c = taskCompletionSource;
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1602p
    /* JADX INFO: renamed from: b */
    public final void mo347b(Bundle bundle) {
        this.f410a.f413a.m406v(this.f412c);
        this.f411b.m430c("onRequestIntegrityToken", new Object[0]);
        int i = bundle.getInt("error");
        if (i != 0) {
            this.f412c.trySetException(new IntegrityServiceException(i, null));
            return;
        }
        String string = bundle.getString("token");
        if (string == null) {
            this.f412c.trySetException(new IntegrityServiceException(-100, null));
            return;
        }
        PendingIntent pendingIntent = Build.VERSION.SDK_INT >= 33 ? (PendingIntent) bundle.getParcelable("dialog.intent", PendingIntent.class) : (PendingIntent) bundle.getParcelable("dialog.intent");
        TaskCompletionSource taskCompletionSource = this.f412c;
        C1515a c1515a = new C1515a();
        c1515a.mo342c(string);
        c1515a.mo341b(this.f411b);
        c1515a.mo340a(pendingIntent);
        taskCompletionSource.trySetResult(c1515a.mo343d());
    }
}
