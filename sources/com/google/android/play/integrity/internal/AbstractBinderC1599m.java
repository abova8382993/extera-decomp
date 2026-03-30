package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.m */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractBinderC1599m extends AbstractBinderC1588b implements InterfaceC1600n {
    /* JADX INFO: renamed from: b */
    public static InterfaceC1600n m426b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.integrity.protocol.IIntegrityService");
        return iInterfaceQueryLocalInterface instanceof InterfaceC1600n ? (InterfaceC1600n) iInterfaceQueryLocalInterface : new C1598l(iBinder);
    }
}
