package com.google.android.play.core.integrity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.android.play.integrity.internal.AbstractBinderC1599m;
import com.google.android.play.integrity.internal.AbstractC1582ag;
import com.google.android.play.integrity.internal.AbstractC1590d;
import com.google.android.play.integrity.internal.C1578ac;
import com.google.android.play.integrity.internal.C1603q;
import com.google.android.play.integrity.internal.InterfaceC1610x;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ad */
/* JADX INFO: loaded from: classes4.dex */
final class C1519ad {

    /* JADX INFO: renamed from: a */
    final C1578ac f413a;

    /* JADX INFO: renamed from: b */
    private final C1603q f414b;

    /* JADX INFO: renamed from: c */
    private final String f415c;

    C1519ad(Context context, C1603q c1603q) {
        this.f415c = context.getPackageName();
        this.f414b = c1603q;
        if (AbstractC1582ag.m409a(context)) {
            this.f413a = new C1578ac(context, c1603q, "IntegrityService", C1520ae.f416a, new InterfaceC1610x() { // from class: com.google.android.play.core.integrity.aa
                @Override // com.google.android.play.integrity.internal.InterfaceC1610x
                /* JADX INFO: renamed from: a */
                public final Object mo344a(IBinder iBinder) {
                    return AbstractBinderC1599m.m426b(iBinder);
                }
            }, null);
        } else {
            c1603q.m428a("Phonesky is not installed.", new Object[0]);
            this.f413a = null;
        }
    }

    /* JADX INFO: renamed from: a */
    static /* bridge */ /* synthetic */ Bundle m348a(C1519ad c1519ad, byte[] bArr, Long l, Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1519ad.f415c);
        bundle.putByteArray("nonce", bArr);
        bundle.putInt("playcore.integrity.version.major", 1);
        bundle.putInt("playcore.integrity.version.minor", 2);
        bundle.putInt("playcore.integrity.version.patch", 0);
        if (l != null) {
            bundle.putLong("cloud.prj", l.longValue());
        }
        ArrayList arrayList = new ArrayList();
        AbstractC1590d.m418b(3, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1590d.m417a(arrayList)));
        return bundle;
    }

    /* JADX INFO: renamed from: b */
    public final Task m350b(IntegrityTokenRequest integrityTokenRequest) {
        if (this.f413a == null) {
            return Tasks.forException(new IntegrityServiceException(-2, null));
        }
        try {
            byte[] bArrDecode = Base64.decode(integrityTokenRequest.nonce(), 10);
            Long lCloudProjectNumber = integrityTokenRequest.cloudProjectNumber();
            integrityTokenRequest.mo337a();
            this.f414b.m430c("requestIntegrityToken(%s)", integrityTokenRequest);
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            this.f413a.m404t(new C1517ab(this, taskCompletionSource, bArrDecode, lCloudProjectNumber, null, taskCompletionSource, integrityTokenRequest), taskCompletionSource);
            return taskCompletionSource.getTask();
        } catch (IllegalArgumentException e) {
            return Tasks.forException(new IntegrityServiceException(-13, e));
        }
    }
}
