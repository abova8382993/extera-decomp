package androidx.camera.camera2.internal.concurrent;

import android.hardware.camera2.CameraCharacteristics;
import androidx.camera.camera2.internal.CameraIdUtil;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.camera2.interop.Camera2CameraInfo;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.InitializationException;
import androidx.camera.core.Logger;
import androidx.camera.core.concurrent.CameraCoordinator;
import androidx.camera.core.impl.CameraRepository;
import androidx.camera.core.impl.CameraUpdateException;
import androidx.camera.core.impl.Identifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public class Camera2CameraCoordinator implements CameraCoordinator {
    private final CameraManagerCompat mCameraManager;
    private final Object mLock = new Object();
    private Map mConcurrentCameraIdMap = new HashMap();
    private Set mConcurrentCameraIds = new HashSet();
    private List mActiveConcurrentCameraInfos = new ArrayList();
    private int mCameraOperatingMode = 0;
    private final List mConcurrentCameraModeListeners = new ArrayList();

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public /* synthetic */ void init(CameraRepository cameraRepository) {
        CameraCoordinator.CC.$default$init(this, cameraRepository);
    }

    public Camera2CameraCoordinator(CameraManagerCompat cameraManagerCompat) {
        this.mCameraManager = cameraManagerCompat;
        try {
            onCamerasUpdated(Arrays.asList(cameraManagerCompat.getCameraIdList()));
        } catch (CameraAccessExceptionCompat | CameraUpdateException e) {
            Logger.e("Camera2CameraCoordinator", "Failed to get concurrent camera ids", e);
        }
    }

    @Override // androidx.camera.core.impl.InternalCameraPresenceListener
    public void onCamerasUpdated(List list) throws CameraUpdateException {
        HashMap map = new HashMap();
        HashSet hashSet = new HashSet();
        try {
            for (Set set : this.mCameraManager.getConcurrentCameraIds()) {
                if (list.containsAll(set)) {
                    ArrayList arrayList = new ArrayList(set);
                    if (arrayList.size() >= 2) {
                        String str = (String) arrayList.get(0);
                        String str2 = (String) arrayList.get(1);
                        try {
                            if (CameraIdUtil.isBackwardCompatible(this.mCameraManager, str) && CameraIdUtil.isBackwardCompatible(this.mCameraManager, str2)) {
                                hashSet.add(new HashSet(Arrays.asList(str, str2)));
                                if (!map.containsKey(str)) {
                                    map.put(str, new ArrayList());
                                }
                                ((List) map.get(str)).add(str2);
                                if (!map.containsKey(str2)) {
                                    map.put(str2, new ArrayList());
                                }
                                ((List) map.get(str2)).add(str);
                            }
                        } catch (InitializationException unused) {
                            Logger.d("Camera2CameraCoordinator", "Concurrent camera id pair: (" + str + ", " + str + ") is not backward compatible");
                        }
                    }
                }
            }
            synchronized (this.mLock) {
                this.mConcurrentCameraIdMap = map;
                this.mConcurrentCameraIds = hashSet;
                Logger.d("Camera2CameraCoordinator", "Updated concurrent camera map: " + this.mConcurrentCameraIdMap);
            }
        } catch (CameraAccessExceptionCompat e) {
            throw new CameraUpdateException("Failed to retrieve concurrent camera id info.", e);
        }
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public List getConcurrentCameraSelectors() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                for (Set set : this.mConcurrentCameraIds) {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(createCameraSelectorById(this.mCameraManager, (String) it.next()));
                    }
                    arrayList.add(arrayList2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public List getActiveConcurrentCameraInfos() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mActiveConcurrentCameraInfos);
        }
        return arrayList;
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public void setActiveConcurrentCameraInfos(List list) {
        synchronized (this.mLock) {
            this.mActiveConcurrentCameraInfos = new ArrayList(list);
        }
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public String getPairedConcurrentCameraId(String str) {
        synchronized (this.mLock) {
            try {
                if (!this.mConcurrentCameraIdMap.containsKey(str)) {
                    return null;
                }
                List<String> list = (List) this.mConcurrentCameraIdMap.get(str);
                if (list == null) {
                    return null;
                }
                for (String str2 : list) {
                    Iterator it = this.mActiveConcurrentCameraInfos.iterator();
                    while (it.hasNext()) {
                        if (str2.equals(Camera2CameraInfo.from((CameraInfo) it.next()).getCameraId())) {
                            return str2;
                        }
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public int getCameraOperatingMode() {
        int i;
        synchronized (this.mLock) {
            i = this.mCameraOperatingMode;
        }
        return i;
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public void setCameraOperatingMode(int i) {
        synchronized (this.mLock) {
            try {
                int i2 = this.mCameraOperatingMode;
                if (i == i2) {
                    return;
                }
                this.mCameraOperatingMode = i;
                ArrayList arrayList = new ArrayList(this.mConcurrentCameraModeListeners);
                if (i2 == 2 && i != 2) {
                    this.mActiveConcurrentCameraInfos.clear();
                }
                notifyCameraModeListener(arrayList, i2, i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void notifyCameraModeListener(List list, int i, int i2) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((CameraCoordinator.ConcurrentCameraModeListener) it.next()).onCameraOperatingModeUpdated(i, i2);
        }
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public void addListener(CameraCoordinator.ConcurrentCameraModeListener concurrentCameraModeListener) {
        synchronized (this.mLock) {
            this.mConcurrentCameraModeListeners.add(concurrentCameraModeListener);
        }
    }

    @Override // androidx.camera.core.concurrent.CameraCoordinator
    public void shutdown() {
        synchronized (this.mLock) {
            this.mConcurrentCameraModeListeners.clear();
            this.mConcurrentCameraIdMap.clear();
            this.mActiveConcurrentCameraInfos.clear();
            this.mConcurrentCameraIds.clear();
            this.mCameraOperatingMode = 0;
        }
    }

    private static CameraSelector createCameraSelectorById(CameraManagerCompat cameraManagerCompat, final String str) {
        CameraSelector.Builder builderAddCameraFilter = new CameraSelector.Builder().addCameraFilter(new CameraFilter() { // from class: androidx.camera.camera2.internal.concurrent.Camera2CameraCoordinator$$ExternalSyntheticLambda0
            @Override // androidx.camera.core.CameraFilter
            public final List filter(List list) {
                return Camera2CameraCoordinator.$r8$lambda$Jm0HrC4Oq17_M9QTLFuV3jO6OSw(str, list);
            }

            @Override // androidx.camera.core.CameraFilter
            public /* synthetic */ Identifier getIdentifier() {
                return CameraFilter.DEFAULT_ID;
            }
        });
        try {
            Integer num = (Integer) cameraManagerCompat.getCameraCharacteristicsCompat(str).get(CameraCharacteristics.LENS_FACING);
            if (num != null) {
                builderAddCameraFilter.requireLensFacing(num.intValue());
            }
            return builderAddCameraFilter.build();
        } catch (CameraAccessExceptionCompat e) {
            throw new RuntimeException("Unable to get camera characteristics for " + str, e);
        }
    }

    public static /* synthetic */ List $r8$lambda$Jm0HrC4Oq17_M9QTLFuV3jO6OSw(String str, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CameraInfo cameraInfo = (CameraInfo) it.next();
            if (str.equals(Camera2CameraInfo.from(cameraInfo).getCameraId())) {
                return Collections.singletonList(cameraInfo);
            }
        }
        throw new IllegalArgumentException("No camera can be find for id: " + str);
    }
}
