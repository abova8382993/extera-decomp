package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.h */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractBinderC1594h extends AbstractBinderC1588b implements InterfaceC1595i {
    /* JADX INFO: renamed from: b */
    public static InterfaceC1595i m424b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.integrity.protocol.IExpressIntegrityService");
        return iInterfaceQueryLocalInterface instanceof InterfaceC1595i ? (InterfaceC1595i) iInterfaceQueryLocalInterface : new C1593g(iBinder);
    }
}
