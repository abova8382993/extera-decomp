package com.google.android.play.core.integrity;

import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.C1809q;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.au */
/* JADX INFO: loaded from: classes5.dex */
final class BinderC1742au extends BinderC1741at {

    /* JADX INFO: renamed from: c */
    private final C1809q f539c;

    public BinderC1742au(C1745ax c1745ax, TaskCompletionSource taskCompletionSource) {
        super(c1745ax, taskCompletionSource);
        this.f539c = new C1809q("OnRequestIntegrityTokenCallback");
    }

    @Override // com.google.android.play.core.integrity.BinderC1741at, com.google.android.play.integrity.internal.InterfaceC1803k
    /* JADX INFO: renamed from: c */
    public final void mo418c(Bundle bundle) {
        super.mo418c(bundle);
        this.f539c.m491c("onRequestExpressIntegrityToken", new Object[0]);
        int i = bundle.getInt("error");
        if (i != 0) {
            this.f537a.trySetException(new StandardIntegrityException(i, null));
            return;
        }
        PendingIntent pendingIntent = Build.VERSION.SDK_INT >= 33 ? (PendingIntent) bundle.getParcelable("dialog.intent", PendingIntent.class) : (PendingIntent) bundle.getParcelable("dialog.intent");
        TaskCompletionSource taskCompletionSource = this.f537a;
        C1748b c1748b = new C1748b();
        c1748b.mo430c(bundle.getString("token"));
        c1748b.mo429b(this.f539c);
        c1748b.mo428a(pendingIntent);
        taskCompletionSource.trySetResult(c1748b.mo431d());
    }
}
