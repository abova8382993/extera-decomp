package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.h */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractBinderC1800h extends AbstractBinderC1794b implements InterfaceC1801i {
    /* JADX INFO: renamed from: b */
    public static InterfaceC1801i m485b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.integrity.protocol.IExpressIntegrityService");
        return iInterfaceQueryLocalInterface instanceof InterfaceC1801i ? (InterfaceC1801i) iInterfaceQueryLocalInterface : new C1799g(iBinder);
    }
}
