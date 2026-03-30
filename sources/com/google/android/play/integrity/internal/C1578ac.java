package com.google.android.play.integrity.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.ac */
/* JADX INFO: loaded from: classes4.dex */
public final class C1578ac {

    /* JADX INFO: renamed from: a */
    private static final Map f497a = new HashMap();

    /* JADX INFO: renamed from: b */
    private final Context f498b;

    /* JADX INFO: renamed from: c */
    private final C1603q f499c;

    /* JADX INFO: renamed from: d */
    private final String f500d;

    /* JADX INFO: renamed from: h */
    private boolean f504h;

    /* JADX INFO: renamed from: i */
    private final Intent f505i;

    /* JADX INFO: renamed from: j */
    private final InterfaceC1610x f506j;

    /* JADX INFO: renamed from: n */
    private ServiceConnection f510n;

    /* JADX INFO: renamed from: o */
    private IInterface f511o;

    /* JADX INFO: renamed from: e */
    private final List f501e = new ArrayList();

    /* JADX INFO: renamed from: f */
    private final Set f502f = new HashSet();

    /* JADX INFO: renamed from: g */
    private final Object f503g = new Object();

    /* JADX INFO: renamed from: l */
    private final IBinder.DeathRecipient f508l = new IBinder.DeathRecipient() { // from class: com.google.android.play.integrity.internal.t
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            C1578ac.m391k(this.f525a);
        }
    };

    /* JADX INFO: renamed from: m */
    private final AtomicInteger f509m = new AtomicInteger(0);

    /* JADX INFO: renamed from: k */
    private final WeakReference f507k = new WeakReference(null);

    public C1578ac(Context context, C1603q c1603q, String str, Intent intent, InterfaceC1610x interfaceC1610x, InterfaceC1609w interfaceC1609w) {
        this.f498b = context;
        this.f499c = c1603q;
        this.f500d = str;
        this.f505i = intent;
        this.f506j = interfaceC1610x;
    }

    /* JADX INFO: renamed from: k */
    public static /* synthetic */ void m391k(C1578ac c1578ac) {
        c1578ac.f499c.m430c("reportBinderDeath", new Object[0]);
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(c1578ac.f507k.get());
        c1578ac.f499c.m430c("%s : Binder has died.", c1578ac.f500d);
        Iterator it = c1578ac.f501e.iterator();
        while (it.hasNext()) {
            ((AbstractRunnableC1604r) it.next()).mo345a(c1578ac.m400w());
        }
        c1578ac.f501e.clear();
        synchronized (c1578ac.f503g) {
            c1578ac.m401x();
        }
    }

    /* JADX INFO: renamed from: o */
    static /* bridge */ /* synthetic */ void m395o(final C1578ac c1578ac, final TaskCompletionSource taskCompletionSource) {
        c1578ac.f502f.add(taskCompletionSource);
        taskCompletionSource.getTask().addOnCompleteListener(new OnCompleteListener() { // from class: com.google.android.play.integrity.internal.s
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                this.f523a.m405u(taskCompletionSource, task);
            }
        });
    }

    /* JADX INFO: renamed from: r */
    static /* bridge */ /* synthetic */ void m398r(C1578ac c1578ac) {
        c1578ac.f499c.m430c("linkToDeath", new Object[0]);
        try {
            c1578ac.f511o.asBinder().linkToDeath(c1578ac.f508l, 0);
        } catch (RemoteException e) {
            c1578ac.f499c.m429b(e, "linkToDeath failed", new Object[0]);
        }
    }

    /* JADX INFO: renamed from: s */
    static /* bridge */ /* synthetic */ void m399s(C1578ac c1578ac) {
        c1578ac.f499c.m430c("unlinkToDeath", new Object[0]);
        c1578ac.f511o.asBinder().unlinkToDeath(c1578ac.f508l, 0);
    }

    /* JADX INFO: renamed from: x */
    public final void m401x() {
        Iterator it = this.f502f.iterator();
        while (it.hasNext()) {
            ((TaskCompletionSource) it.next()).trySetException(m400w());
        }
        this.f502f.clear();
    }

    /* JADX INFO: renamed from: c */
    public final Handler m402c() {
        Handler handler;
        Map map = f497a;
        synchronized (map) {
            try {
                if (!map.containsKey(this.f500d)) {
                    HandlerThread handlerThread = new HandlerThread(this.f500d, 10);
                    handlerThread.start();
                    map.put(this.f500d, new Handler(handlerThread.getLooper()));
                }
                handler = (Handler) map.get(this.f500d);
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    /* JADX INFO: renamed from: e */
    public final IInterface m403e() {
        return this.f511o;
    }

    /* JADX INFO: renamed from: t */
    public final void m404t(AbstractRunnableC1604r abstractRunnableC1604r, TaskCompletionSource taskCompletionSource) {
        m402c().post(new C1607u(this, abstractRunnableC1604r.m432c(), taskCompletionSource, abstractRunnableC1604r));
    }

    /* JADX INFO: renamed from: u */
    final /* synthetic */ void m405u(TaskCompletionSource taskCompletionSource, Task task) {
        synchronized (this.f503g) {
            this.f502f.remove(taskCompletionSource);
        }
    }

    /* JADX INFO: renamed from: v */
    public final void m406v(TaskCompletionSource taskCompletionSource) {
        synchronized (this.f503g) {
            this.f502f.remove(taskCompletionSource);
        }
        m402c().post(new C1608v(this));
    }

    /* JADX INFO: renamed from: w */
    private final RemoteException m400w() {
        return new RemoteException(String.valueOf(this.f500d).concat(" : Binder has died."));
    }

    /* JADX INFO: renamed from: q */
    static /* bridge */ /* synthetic */ void m397q(C1578ac c1578ac, AbstractRunnableC1604r abstractRunnableC1604r) {
        if (c1578ac.f511o != null || c1578ac.f504h) {
            if (!c1578ac.f504h) {
                abstractRunnableC1604r.run();
                return;
            } else {
                c1578ac.f499c.m430c("Waiting to bind to the service.", new Object[0]);
                c1578ac.f501e.add(abstractRunnableC1604r);
                return;
            }
        }
        c1578ac.f499c.m430c("Initiate binding to the service.", new Object[0]);
        c1578ac.f501e.add(abstractRunnableC1604r);
        ServiceConnectionC1577ab serviceConnectionC1577ab = new ServiceConnectionC1577ab(c1578ac, null);
        c1578ac.f510n = serviceConnectionC1577ab;
        c1578ac.f504h = true;
        if (c1578ac.f498b.bindService(c1578ac.f505i, serviceConnectionC1577ab, 1)) {
            return;
        }
        c1578ac.f499c.m430c("Failed to bind to the service.", new Object[0]);
        c1578ac.f504h = false;
        Iterator it = c1578ac.f501e.iterator();
        while (it.hasNext()) {
            ((AbstractRunnableC1604r) it.next()).mo345a(new C1579ad());
        }
        c1578ac.f501e.clear();
    }
}
