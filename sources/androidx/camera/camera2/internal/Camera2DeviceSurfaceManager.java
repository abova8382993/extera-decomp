package androidx.camera.camera2.internal;

import android.content.Context;
import android.media.CamcorderProfile;
import android.os.Build;
import android.util.Size;
import androidx.camera.camera2.impl.FeatureCombinationQueryImpl;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.featuregroup.impl.FeatureCombinationQuery;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraUpdateException;
import androidx.camera.core.impl.StreamUseCase;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.SurfaceStreamSpecQueryResult;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class Camera2DeviceSurfaceManager implements CameraDeviceSurfaceManager {
    private final CamcorderProfileHelper mCamcorderProfileHelper;
    private final CameraManagerCompat mCameraManager;
    private final Map mCameraSupportedSurfaceCombinationMap;
    private final Context mContext;
    private final Object mLock;

    public Camera2DeviceSurfaceManager(Context context, Object obj, Set set) {
        this(context, new CamcorderProfileHelper() { // from class: androidx.camera.camera2.internal.Camera2DeviceSurfaceManager.1
            @Override // androidx.camera.camera2.internal.CamcorderProfileHelper
            public boolean hasProfile(int i, int i2) {
                return CamcorderProfile.hasProfile(i, i2);
            }

            @Override // androidx.camera.camera2.internal.CamcorderProfileHelper
            public CamcorderProfile get(int i, int i2) {
                return CamcorderProfile.get(i, i2);
            }
        }, obj, set);
    }

    Camera2DeviceSurfaceManager(Context context, CamcorderProfileHelper camcorderProfileHelper, Object obj, Set set) throws CameraUnavailableException {
        this.mLock = new Object();
        this.mCameraSupportedSurfaceCombinationMap = new HashMap();
        Preconditions.checkNotNull(camcorderProfileHelper);
        this.mCamcorderProfileHelper = camcorderProfileHelper;
        this.mContext = context;
        if (obj instanceof CameraManagerCompat) {
            this.mCameraManager = (CameraManagerCompat) obj;
        } else {
            this.mCameraManager = CameraManagerCompat.from(context);
        }
        try {
            onCamerasUpdated(new ArrayList(set));
        } catch (CameraUpdateException e) {
            if (e.getCause() instanceof CameraUnavailableException) {
                throw ((CameraUnavailableException) e.getCause());
            }
            throw new CameraUnavailableException(3, e);
        }
    }

    @Override // androidx.camera.core.impl.InternalCameraPresenceListener
    public void onCamerasUpdated(List list) throws CameraUpdateException {
        HashSet<String> hashSet;
        HashMap map = new HashMap();
        synchronized (this.mLock) {
            hashSet = new HashSet(list);
            hashSet.removeAll(this.mCameraSupportedSurfaceCombinationMap.keySet());
        }
        try {
            for (String str : hashSet) {
                map.put(str, createSurfaceCombination(str));
            }
            synchronized (this.mLock) {
                try {
                    HashMap map2 = new HashMap();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        String str2 = (String) it.next();
                        if (this.mCameraSupportedSurfaceCombinationMap.containsKey(str2)) {
                            map2.put(str2, (SupportedSurfaceCombination) this.mCameraSupportedSurfaceCombinationMap.get(str2));
                        } else {
                            map2.put(str2, (SupportedSurfaceCombination) map.get(str2));
                        }
                    }
                    this.mCameraSupportedSurfaceCombinationMap.clear();
                    this.mCameraSupportedSurfaceCombinationMap.putAll(map2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (CameraUnavailableException | RuntimeException e) {
            throw new CameraUpdateException("Failed to create SupportedSurfaceCombination", e);
        }
    }

    private SupportedSurfaceCombination createSurfaceCombination(String str) {
        FeatureCombinationQuery featureCombinationQueryImpl = FeatureCombinationQuery.NO_OP_FEATURE_COMBINATION_QUERY;
        if (Build.VERSION.SDK_INT >= 35) {
            featureCombinationQueryImpl = new FeatureCombinationQueryImpl(this.mContext, str, this.mCameraManager);
        }
        return new SupportedSurfaceCombination(this.mContext, str, this.mCameraManager, this.mCamcorderProfileHelper, featureCombinationQueryImpl);
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public SurfaceConfig transformSurfaceConfig(int i, String str, int i2, Size size, StreamUseCase streamUseCase) {
        SupportedSurfaceCombination supportedSurfaceCombination = (SupportedSurfaceCombination) this.mCameraSupportedSurfaceCombinationMap.get(str);
        Preconditions.checkArgument(supportedSurfaceCombination != null, "No such camera id in supported combination list: " + str);
        return supportedSurfaceCombination.transformSurfaceConfig(i, i2, size, streamUseCase);
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public SurfaceStreamSpecQueryResult getSuggestedStreamSpecs(int i, String str, List list, Map map, boolean z, boolean z2, boolean z3, boolean z4) {
        Preconditions.checkArgument(!map.isEmpty(), "No new use cases to be bound.");
        SupportedSurfaceCombination supportedSurfaceCombination = (SupportedSurfaceCombination) this.mCameraSupportedSurfaceCombinationMap.get(str);
        Preconditions.checkArgument(supportedSurfaceCombination != null, "No such camera id in supported combination list: " + str);
        return supportedSurfaceCombination.getSuggestedStreamSpecifications(i, list, map, z, z2, z3, z4);
    }
}
