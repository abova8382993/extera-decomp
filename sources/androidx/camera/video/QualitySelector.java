package androidx.camera.video;

import android.annotation.SuppressLint;
import android.util.Size;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.Logger;
import androidx.camera.video.FallbackStrategy;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import okhttp3.CertificatePinner$$ExternalSyntheticBUOutline0;

/* JADX INFO: loaded from: classes4.dex */
public final class QualitySelector {
    public static final QualitySelector NONE = new QualitySelector(Collections.EMPTY_LIST, FallbackStrategy.NONE);
    private final FallbackStrategy mFallbackStrategy;
    private final List<Quality> mPreferredQualityList;

    public static Size getResolution(CameraInfo cameraInfo, Quality quality) {
        checkQualityConstantsOrThrow(quality);
        return Recorder.getVideoCapabilities(cameraInfo).getResolution(quality, DynamicRange.SDR);
    }

    public static Map<Quality, Size> getQualityToResolutionMap(VideoCapabilities videoCapabilities, DynamicRange dynamicRange) {
        HashMap map = new HashMap();
        for (Quality quality : videoCapabilities.getSupportedQualities(dynamicRange)) {
            Size resolution = videoCapabilities.getResolution(quality, dynamicRange);
            Objects.requireNonNull(resolution);
            map.put(quality, resolution);
        }
        return map;
    }

    public QualitySelector(List<Quality> list, FallbackStrategy fallbackStrategy) {
        this.mPreferredQualityList = Collections.unmodifiableList(new ArrayList(list));
        this.mFallbackStrategy = fallbackStrategy;
    }

    public static QualitySelector from(Quality quality, FallbackStrategy fallbackStrategy) {
        Preconditions.checkNotNull(quality, "quality cannot be null");
        Preconditions.checkNotNull(fallbackStrategy, "fallbackStrategy cannot be null");
        checkQualityConstantsOrThrow(quality);
        return new QualitySelector(Collections.singletonList(quality), fallbackStrategy);
    }

    public static QualitySelector fromOrderedList(List<Quality> list) {
        return fromOrderedList(list, FallbackStrategy.NONE);
    }

    public static QualitySelector fromOrderedList(List<Quality> list, FallbackStrategy fallbackStrategy) {
        Preconditions.checkNotNull(list, "qualities cannot be null");
        Preconditions.checkNotNull(fallbackStrategy, "fallbackStrategy cannot be null");
        Preconditions.checkArgument(!list.isEmpty(), "qualities cannot be empty");
        checkQualityConstantsOrThrow(list);
        return new QualitySelector(list, fallbackStrategy);
    }

    @SuppressLint({"UsesNonDefaultVisibleForTesting"})
    public List<Quality> getPrioritizedQualities(List<Quality> list) {
        if (list.isEmpty()) {
            Logger.m79w("QualitySelector", "No supported quality on the device.");
            return new ArrayList();
        }
        Logger.m74d("QualitySelector", "supportedQualities = " + list);
        Set<Quality> linkedHashSet = new LinkedHashSet<>();
        Iterator<Quality> it = this.mPreferredQualityList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Quality next = it.next();
            if (next == Quality.HIGHEST) {
                linkedHashSet.addAll(list);
                break;
            }
            if (next == Quality.LOWEST) {
                ArrayList arrayList = new ArrayList(list);
                Collections.reverse(arrayList);
                linkedHashSet.addAll(arrayList);
                break;
            }
            if (list.contains(next)) {
                linkedHashSet.add(next);
            } else {
                Logger.m79w("QualitySelector", "quality is not supported and will be ignored: " + next);
            }
        }
        addByFallbackStrategy(list, linkedHashSet);
        return new ArrayList(linkedHashSet);
    }

    public String toString() {
        return "QualitySelector{preferredQualities=" + this.mPreferredQualityList + ", fallbackStrategy=" + this.mFallbackStrategy + "}";
    }

    private void addByFallbackStrategy(List<Quality> list, Set<Quality> set) {
        Quality fallbackQuality;
        if (list.isEmpty() || set.containsAll(list)) {
            return;
        }
        Logger.m74d("QualitySelector", "Select quality by fallbackStrategy = " + this.mFallbackStrategy);
        FallbackStrategy fallbackStrategy = this.mFallbackStrategy;
        if (fallbackStrategy == FallbackStrategy.NONE) {
            return;
        }
        Preconditions.checkState(fallbackStrategy instanceof FallbackStrategy.RuleStrategy, "Currently only support type RuleStrategy");
        FallbackStrategy.RuleStrategy ruleStrategy = (FallbackStrategy.RuleStrategy) this.mFallbackStrategy;
        List<Quality> sortedQualities = Quality.getSortedQualities();
        if (ruleStrategy.getFallbackQuality() == Quality.HIGHEST) {
            fallbackQuality = sortedQualities.get(0);
        } else if (ruleStrategy.getFallbackQuality() == Quality.LOWEST) {
            fallbackQuality = sortedQualities.get(sortedQualities.size() - 1);
        } else {
            fallbackQuality = ruleStrategy.getFallbackQuality();
        }
        int iIndexOf = sortedQualities.indexOf(fallbackQuality);
        Preconditions.checkState(iIndexOf != -1);
        ArrayList arrayList = new ArrayList();
        for (int i = iIndexOf - 1; i >= 0; i--) {
            Quality quality = sortedQualities.get(i);
            if (list.contains(quality)) {
                arrayList.add(quality);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = iIndexOf + 1; i2 < sortedQualities.size(); i2++) {
            Quality quality2 = sortedQualities.get(i2);
            if (list.contains(quality2)) {
                arrayList2.add(quality2);
            }
        }
        Logger.m74d("QualitySelector", "sizeSortedQualities = " + sortedQualities + ", fallback quality = " + fallbackQuality + ", largerQualities = " + arrayList + ", smallerQualities = " + arrayList2);
        int fallbackRule = ruleStrategy.getFallbackRule();
        if (fallbackRule != 0) {
            if (fallbackRule == 1) {
                set.addAll(arrayList);
                set.addAll(arrayList2);
                return;
            }
            if (fallbackRule == 2) {
                set.addAll(arrayList);
                return;
            }
            if (fallbackRule == 3) {
                set.addAll(arrayList2);
                set.addAll(arrayList);
            } else if (fallbackRule == 4) {
                set.addAll(arrayList2);
            } else {
                CertificatePinner$$ExternalSyntheticBUOutline0.m953m("Unhandled fallback strategy: ", this.mFallbackStrategy);
            }
        }
    }

    private static void checkQualityConstantsOrThrow(List<Quality> list) {
        for (Quality quality : list) {
            Preconditions.checkArgument(Quality.containsQuality(quality), "qualities contain invalid quality: " + quality);
        }
    }

    private static void checkQualityConstantsOrThrow(Quality quality) {
        Preconditions.checkArgument(Quality.containsQuality(quality), "Invalid quality: " + quality);
    }
}
