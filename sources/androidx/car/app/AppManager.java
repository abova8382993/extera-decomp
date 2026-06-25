package androidx.car.app;

import androidx.car.app.IAppManager;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AppManager {

    /* JADX INFO: renamed from: androidx.car.app.AppManager$1 */
    class BinderC03241 extends IAppManager.Stub {
        final /* synthetic */ AppManager this$0;
        final /* synthetic */ CarContext val$carContext;

        public BinderC03241(AppManager appManager, CarContext carContext) {
        }

        @Override // androidx.car.app.IAppManager
        public void getTemplate(IOnDoneCallback iOnDoneCallback) {
            throw null;
        }

        @Override // androidx.car.app.IAppManager
        public void onBackPressed(IOnDoneCallback iOnDoneCallback) {
            throw null;
        }

        @Override // androidx.car.app.IAppManager
        public void startLocationUpdates(IOnDoneCallback iOnDoneCallback) {
            throw null;
        }

        @Override // androidx.car.app.IAppManager
        public void stopLocationUpdates(IOnDoneCallback iOnDoneCallback) {
            throw null;
        }
    }
}
