package org.telegram.messenger.support.customtabs;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import org.telegram.messenger.support.customtabs.ICustomTabsService;

/* JADX INFO: loaded from: classes.dex */
public abstract class CustomTabsServiceConnection implements ServiceConnection {
    public abstract void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient);

    /* JADX INFO: renamed from: org.telegram.messenger.support.customtabs.CustomTabsServiceConnection$1 */
    public class C28401 extends CustomTabsClient {
        public C28401(ICustomTabsService iCustomTabsService, ComponentName componentName) {
            super(iCustomTabsService, componentName);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        onCustomTabsServiceConnected(componentName, new CustomTabsClient(ICustomTabsService.Stub.asInterface(iBinder), componentName) { // from class: org.telegram.messenger.support.customtabs.CustomTabsServiceConnection.1
            public C28401(ICustomTabsService iCustomTabsService, ComponentName componentName2) {
                super(iCustomTabsService, componentName2);
            }
        });
    }
}
