package com.google.android.play.core.integrity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1800h;
import com.google.android.play.integrity.internal.AbstractC1796d;
import com.google.android.play.integrity.internal.C1784ac;
import com.google.android.play.integrity.internal.C1809q;
import com.google.android.play.integrity.internal.InterfaceC1816x;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ax */
/* JADX INFO: loaded from: classes5.dex */
final class C1745ax {

    /* JADX INFO: renamed from: a */
    final C1784ac f542a;

    /* JADX INFO: renamed from: b */
    private final C1809q f543b;

    /* JADX INFO: renamed from: c */
    private final String f544c;

    /* JADX INFO: renamed from: d */
    private final TaskCompletionSource f545d;

    public C1745ax(Context context, C1809q c1809q) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f545d = taskCompletionSource;
        this.f544c = context.getPackageName();
        this.f543b = c1809q;
        C1784ac c1784ac = new C1784ac(context, c1809q, "ExpressIntegrityService", C1746ay.f546a, new InterfaceC1816x() { // from class: com.google.android.play.core.integrity.ap
            @Override // com.google.android.play.integrity.internal.InterfaceC1816x
            /* JADX INFO: renamed from: a */
            public final Object mo405a(IBinder iBinder) {
                return AbstractBinderC1800h.m485b(iBinder);
            }
        }, null);
        this.f542a = c1784ac;
        c1784ac.m463c().post(new C1738aq(this, taskCompletionSource, context));
    }

    /* JADX INFO: renamed from: a */
    public static /* bridge */ /* synthetic */ Bundle m421a(C1745ax c1745ax, String str, long j, long j2) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1745ax.f544c);
        bundle.putLong("cloud.prj", j);
        bundle.putString("nonce", str);
        bundle.putLong("warm.up.sid", j2);
        ArrayList arrayList = new ArrayList();
        AbstractC1796d.m479b(5, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1796d.m478a(arrayList)));
        return bundle;
    }

    /* JADX INFO: renamed from: b */
    public static /* bridge */ /* synthetic */ Bundle m422b(C1745ax c1745ax, long j) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1745ax.f544c);
        bundle.putLong("cloud.prj", j);
        ArrayList arrayList = new ArrayList();
        AbstractC1796d.m479b(4, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1796d.m478a(arrayList)));
        return bundle;
    }

    /* JADX INFO: renamed from: g */
    public static /* bridge */ /* synthetic */ boolean m425g(C1745ax c1745ax) {
        return c1745ax.f545d.getTask().isSuccessful() && !((Boolean) c1745ax.f545d.getTask().getResult()).booleanValue();
    }

    /* JADX INFO: renamed from: c */
    public final Task m426c(String str, long j, long j2) {
        this.f543b.m491c("requestExpressIntegrityToken(%s)", Long.valueOf(j2));
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f542a.m465t(new C1740as(this, taskCompletionSource, str, j, j2, taskCompletionSource), taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: renamed from: d */
    public final Task m427d(long j) {
        this.f543b.m491c("warmUpIntegrityToken(%s)", Long.valueOf(j));
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f542a.m465t(new C1739ar(this, taskCompletionSource, j, taskCompletionSource), taskCompletionSource);
        return taskCompletionSource.getTask();
    }
}
