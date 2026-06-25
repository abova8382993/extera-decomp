package com.google.android.play.integrity.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.v4.media.session.MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0;
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
/* JADX INFO: loaded from: classes5.dex */
public final class C1784ac {

    /* JADX INFO: renamed from: a */
    private static final Map f594a = new HashMap();

    /* JADX INFO: renamed from: b */
    private final Context f595b;

    /* JADX INFO: renamed from: c */
    private final C1809q f596c;

    /* JADX INFO: renamed from: d */
    private final String f597d;

    /* JADX INFO: renamed from: h */
    private boolean f601h;

    /* JADX INFO: renamed from: i */
    private final Intent f602i;

    /* JADX INFO: renamed from: j */
    private final InterfaceC1816x f603j;

    /* JADX INFO: renamed from: n */
    private ServiceConnection f607n;

    /* JADX INFO: renamed from: o */
    private IInterface f608o;

    /* JADX INFO: renamed from: e */
    private final List f598e = new ArrayList();

    /* JADX INFO: renamed from: f */
    private final Set f599f = new HashSet();

    /* JADX INFO: renamed from: g */
    private final Object f600g = new Object();

    /* JADX INFO: renamed from: l */
    private final IBinder.DeathRecipient f605l = new IBinder.DeathRecipient() { // from class: com.google.android.play.integrity.internal.t
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            C1784ac.m452k(this.f622a);
        }
    };

    /* JADX INFO: renamed from: m */
    private final AtomicInteger f606m = new AtomicInteger(0);

    /* JADX INFO: renamed from: k */
    private final WeakReference f604k = new WeakReference(null);

    public C1784ac(Context context, C1809q c1809q, String str, Intent intent, InterfaceC1816x interfaceC1816x, InterfaceC1815w interfaceC1815w) {
        this.f595b = context;
        this.f596c = c1809q;
        this.f597d = str;
        this.f602i = intent;
        this.f603j = interfaceC1816x;
    }

    /* JADX INFO: renamed from: k */
    public static /* synthetic */ void m452k(C1784ac c1784ac) {
        c1784ac.f596c.m491c("reportBinderDeath", new Object[0]);
        MediaSessionCompat$$ExternalSyntheticThrowCCEIfNotNull0.m2m(c1784ac.f604k.get());
        c1784ac.f596c.m491c("%s : Binder has died.", c1784ac.f597d);
        Iterator it = c1784ac.f598e.iterator();
        while (it.hasNext()) {
            ((AbstractRunnableC1810r) it.next()).mo406a(c1784ac.m461w());
        }
        c1784ac.f598e.clear();
        synchronized (c1784ac.f600g) {
            c1784ac.m462x();
        }
    }

    /* JADX INFO: renamed from: o */
    public static /* bridge */ /* synthetic */ void m456o(final C1784ac c1784ac, final TaskCompletionSource taskCompletionSource) {
        c1784ac.f599f.add(taskCompletionSource);
        taskCompletionSource.getTask().addOnCompleteListener(new OnCompleteListener() { // from class: com.google.android.play.integrity.internal.s
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                this.f620a.m466u(taskCompletionSource, task);
            }
        });
    }

    /* JADX INFO: renamed from: r */
    public static /* bridge */ /* synthetic */ void m459r(C1784ac c1784ac) {
        c1784ac.f596c.m491c("linkToDeath", new Object[0]);
        try {
            c1784ac.f608o.asBinder().linkToDeath(c1784ac.f605l, 0);
        } catch (RemoteException e) {
            c1784ac.f596c.m490b(e, "linkToDeath failed", new Object[0]);
        }
    }

    /* JADX INFO: renamed from: s */
    public static /* bridge */ /* synthetic */ void m460s(C1784ac c1784ac) {
        c1784ac.f596c.m491c("unlinkToDeath", new Object[0]);
        c1784ac.f608o.asBinder().unlinkToDeath(c1784ac.f605l, 0);
    }

    /* JADX INFO: renamed from: x */
    public final void m462x() {
        Iterator it = this.f599f.iterator();
        while (it.hasNext()) {
            ((TaskCompletionSource) it.next()).trySetException(m461w());
        }
        this.f599f.clear();
    }

    /* JADX INFO: renamed from: c */
    public final Handler m463c() {
        Handler handler;
        Map map = f594a;
        synchronized (map) {
            try {
                if (!map.containsKey(this.f597d)) {
                    HandlerThread handlerThread = new HandlerThread(this.f597d, 10);
                    handlerThread.start();
                    map.put(this.f597d, new Handler(handlerThread.getLooper()));
                }
                handler = (Handler) map.get(this.f597d);
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    /* JADX INFO: renamed from: e */
    public final IInterface m464e() {
        return this.f608o;
    }

    /* JADX INFO: renamed from: t */
    public final void m465t(AbstractRunnableC1810r abstractRunnableC1810r, TaskCompletionSource taskCompletionSource) {
        m463c().post(new C1813u(this, abstractRunnableC1810r.m493c(), taskCompletionSource, abstractRunnableC1810r));
    }

    /* JADX INFO: renamed from: u */
    public final /* synthetic */ void m466u(TaskCompletionSource taskCompletionSource, Task task) {
        synchronized (this.f600g) {
            this.f599f.remove(taskCompletionSource);
        }
    }

    /* JADX INFO: renamed from: v */
    public final void m467v(TaskCompletionSource taskCompletionSource) {
        synchronized (this.f600g) {
            this.f599f.remove(taskCompletionSource);
        }
        m463c().post(new C1814v(this));
    }

    /* JADX INFO: renamed from: w */
    private final RemoteException m461w() {
        return new RemoteException(String.valueOf(this.f597d).concat(" : Binder has died."));
    }

    /* JADX INFO: renamed from: q */
    public static /* bridge */ /* synthetic */ void m458q(C1784ac c1784ac, AbstractRunnableC1810r abstractRunnableC1810r) {
        if (c1784ac.f608o != null || c1784ac.f601h) {
            if (!c1784ac.f601h) {
                abstractRunnableC1810r.run();
                return;
            } else {
                c1784ac.f596c.m491c("Waiting to bind to the service.", new Object[0]);
                c1784ac.f598e.add(abstractRunnableC1810r);
                return;
            }
        }
        c1784ac.f596c.m491c("Initiate binding to the service.", new Object[0]);
        c1784ac.f598e.add(abstractRunnableC1810r);
        ServiceConnectionC1783ab serviceConnectionC1783ab = new ServiceConnectionC1783ab(c1784ac, null);
        c1784ac.f607n = serviceConnectionC1783ab;
        c1784ac.f601h = true;
        if (c1784ac.f595b.bindService(c1784ac.f602i, serviceConnectionC1783ab, 1)) {
            return;
        }
        c1784ac.f596c.m491c("Failed to bind to the service.", new Object[0]);
        c1784ac.f601h = false;
        Iterator it = c1784ac.f598e.iterator();
        while (it.hasNext()) {
            ((AbstractRunnableC1810r) it.next()).mo406a(new C1785ad());
        }
        c1784ac.f598e.clear();
    }
}
