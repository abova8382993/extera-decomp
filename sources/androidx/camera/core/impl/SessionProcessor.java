package androidx.camera.core.impl;

import android.util.Pair;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public interface SessionProcessor {
    Pair<Integer, Integer> getImplementationType();

    Set<Integer> getSupportedCameraOperations();
}
