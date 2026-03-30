package com.google.android.play.core.integrity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.integrity.internal.AbstractBinderC1594h;
import com.google.android.play.integrity.internal.AbstractC1590d;
import com.google.android.play.integrity.internal.C1578ac;
import com.google.android.play.integrity.internal.C1603q;
import com.google.android.play.integrity.internal.InterfaceC1610x;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ax */
/* JADX INFO: loaded from: classes4.dex */
final class C1539ax {

    /* JADX INFO: renamed from: a */
    final C1578ac f445a;

    /* JADX INFO: renamed from: b */
    private final C1603q f446b;

    /* JADX INFO: renamed from: c */
    private final String f447c;

    /* JADX INFO: renamed from: d */
    private final TaskCompletionSource f448d;

    C1539ax(Context context, C1603q c1603q) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f448d = taskCompletionSource;
        this.f447c = context.getPackageName();
        this.f446b = c1603q;
        C1578ac c1578ac = new C1578ac(context, c1603q, "ExpressIntegrityService", C1540ay.f449a, new InterfaceC1610x() { // from class: com.google.android.play.core.integrity.ap
            @Override // com.google.android.play.integrity.internal.InterfaceC1610x
            /* JADX INFO: renamed from: a */
            public final Object mo344a(IBinder iBinder) {
                return AbstractBinderC1594h.m424b(iBinder);
            }
        }, null);
        this.f445a = c1578ac;
        c1578ac.m402c().post(new C1532aq(this, taskCompletionSource, context));
    }

    /* JADX INFO: renamed from: a */
    static /* bridge */ /* synthetic */ Bundle m360a(C1539ax c1539ax, String str, long j, long j2) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1539ax.f447c);
        bundle.putLong("cloud.prj", j);
        bundle.putString("nonce", str);
        bundle.putLong("warm.up.sid", j2);
        ArrayList arrayList = new ArrayList();
        AbstractC1590d.m418b(5, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1590d.m417a(arrayList)));
        return bundle;
    }

    /* JADX INFO: renamed from: b */
    static /* bridge */ /* synthetic */ Bundle m361b(C1539ax c1539ax, long j) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1539ax.f447c);
        bundle.putLong("cloud.prj", j);
        ArrayList arrayList = new ArrayList();
        AbstractC1590d.m418b(4, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1590d.m417a(arrayList)));
        return bundle;
    }

    /* JADX INFO: renamed from: g */
    static /* bridge */ /* synthetic */ boolean m364g(C1539ax c1539ax) {
        return c1539ax.f448d.getTask().isSuccessful() && !((Boolean) c1539ax.f448d.getTask().getResult()).booleanValue();
    }

    /* JADX INFO: renamed from: c */
    public final Task m365c(String str, long j, long j2) {
        this.f446b.m430c("requestExpressIntegrityToken(%s)", Long.valueOf(j2));
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f445a.m404t(new C1534as(this, taskCompletionSource, str, j, j2, taskCompletionSource), taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: renamed from: d */
    public final Task m366d(long j) {
        this.f446b.m430c("warmUpIntegrityToken(%s)", Long.valueOf(j));
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.f445a.m404t(new C1533ar(this, taskCompletionSource, j, taskCompletionSource), taskCompletionSource);
        return taskCompletionSource.getTask();
    }
}
