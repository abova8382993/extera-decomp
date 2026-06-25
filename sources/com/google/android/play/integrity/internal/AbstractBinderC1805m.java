package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: renamed from: com.google.android.play.integrity.internal.m */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractBinderC1805m extends AbstractBinderC1794b implements InterfaceC1806n {
    /* JADX INFO: renamed from: b */
    public static InterfaceC1806n m487b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.integrity.protocol.IIntegrityService");
        return iInterfaceQueryLocalInterface instanceof InterfaceC1806n ? (InterfaceC1806n) iInterfaceQueryLocalInterface : new C1804l(iBinder);
    }
}
