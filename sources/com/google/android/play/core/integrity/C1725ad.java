package com.google.android.play.core.integrity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.android.play.integrity.internal.AbstractBinderC1805m;
import com.google.android.play.integrity.internal.AbstractC1788ag;
import com.google.android.play.integrity.internal.AbstractC1796d;
import com.google.android.play.integrity.internal.C1784ac;
import com.google.android.play.integrity.internal.C1809q;
import com.google.android.play.integrity.internal.InterfaceC1816x;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.google.android.play.core.integrity.ad */
/* JADX INFO: loaded from: classes5.dex */
final class C1725ad {

    /* JADX INFO: renamed from: a */
    final C1784ac f510a;

    /* JADX INFO: renamed from: b */
    private final C1809q f511b;

    /* JADX INFO: renamed from: c */
    private final String f512c;

    public C1725ad(Context context, C1809q c1809q) {
        this.f512c = context.getPackageName();
        this.f511b = c1809q;
        if (AbstractC1788ag.m470a(context)) {
            this.f510a = new C1784ac(context, c1809q, "IntegrityService", C1726ae.f513a, new InterfaceC1816x() { // from class: com.google.android.play.core.integrity.aa
                @Override // com.google.android.play.integrity.internal.InterfaceC1816x
                /* JADX INFO: renamed from: a */
                public final Object mo405a(IBinder iBinder) {
                    return AbstractBinderC1805m.m487b(iBinder);
                }
            }, null);
        } else {
            c1809q.m489a("Phonesky is not installed.", new Object[0]);
            this.f510a = null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static /* bridge */ /* synthetic */ Bundle m409a(C1725ad c1725ad, byte[] bArr, Long l, Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putString("package.name", c1725ad.f512c);
        bundle.putByteArray("nonce", bArr);
        bundle.putInt("playcore.integrity.version.major", 1);
        bundle.putInt("playcore.integrity.version.minor", 2);
        bundle.putInt("playcore.integrity.version.patch", 0);
        if (l != null) {
            bundle.putLong("cloud.prj", l.longValue());
        }
        ArrayList arrayList = new ArrayList();
        AbstractC1796d.m479b(3, arrayList);
        bundle.putParcelableArrayList("event_timestamps", new ArrayList<>(AbstractC1796d.m478a(arrayList)));
        return bundle;
    }

    /* JADX INFO: renamed from: b */
    public final Task m411b(IntegrityTokenRequest integrityTokenRequest) {
        if (this.f510a == null) {
            return Tasks.forException(new IntegrityServiceException(-2, null));
        }
        try {
            byte[] bArrDecode = Base64.decode(integrityTokenRequest.nonce(), 10);
            Long lCloudProjectNumber = integrityTokenRequest.cloudProjectNumber();
            integrityTokenRequest.mo398a();
            this.f511b.m491c("requestIntegrityToken(%s)", integrityTokenRequest);
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            this.f510a.m465t(new C1723ab(this, taskCompletionSource, bArrDecode, lCloudProjectNumber, null, taskCompletionSource, integrityTokenRequest), taskCompletionSource);
            return taskCompletionSource.getTask();
        } catch (IllegalArgumentException e) {
            return Tasks.forException(new IntegrityServiceException(-13, e));
        }
    }
}
