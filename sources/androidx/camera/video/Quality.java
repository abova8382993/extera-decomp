package androidx.camera.video;

import android.util.Size;
import androidx.camera.core.UseCase$$ExternalSyntheticBUOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Quality {
    public static final Quality FHD;

    /* JADX INFO: renamed from: HD */
    public static final Quality f30HD;
    public static final Quality HIGHEST;
    public static final Quality LOWEST;
    static final Quality NONE;
    private static final Set<Quality> QUALITIES;
    private static final List<Quality> QUALITIES_ORDER_BY_SIZE;

    /* JADX INFO: renamed from: SD */
    public static final Quality f31SD;
    public static final Quality UHD;

    private Quality() {
    }

    static {
        ConstantQuality constantQualityM105of = ConstantQuality.m105of(4, 2002, "SD", Collections.unmodifiableList(Arrays.asList(new Size(720, 480), new Size(640, 480))));
        f31SD = constantQualityM105of;
        ConstantQuality constantQualityM105of2 = ConstantQuality.m105of(5, 2003, "HD", Collections.singletonList(new Size(1280, 720)));
        f30HD = constantQualityM105of2;
        ConstantQuality constantQualityM105of3 = ConstantQuality.m105of(6, 2004, "FHD", Collections.singletonList(new Size(1920, 1080)));
        FHD = constantQualityM105of3;
        ConstantQuality constantQualityM105of4 = ConstantQuality.m105of(8, 2005, "UHD", Collections.singletonList(new Size(3840, 2160)));
        UHD = constantQualityM105of4;
        List list = Collections.EMPTY_LIST;
        ConstantQuality constantQualityM105of5 = ConstantQuality.m105of(0, 2000, "LOWEST", list);
        LOWEST = constantQualityM105of5;
        ConstantQuality constantQualityM105of6 = ConstantQuality.m105of(1, 2001, "HIGHEST", list);
        HIGHEST = constantQualityM105of6;
        NONE = ConstantQuality.m105of(-1, -1, "NONE", list);
        QUALITIES = new HashSet(Arrays.asList(constantQualityM105of5, constantQualityM105of6, constantQualityM105of, constantQualityM105of2, constantQualityM105of3, constantQualityM105of4));
        QUALITIES_ORDER_BY_SIZE = Arrays.asList(constantQualityM105of4, constantQualityM105of3, constantQualityM105of2, constantQualityM105of);
    }

    public static boolean containsQuality(Quality quality) {
        return QUALITIES.contains(quality);
    }

    public static List<Quality> getSortedQualities() {
        return new ArrayList(QUALITIES_ORDER_BY_SIZE);
    }

    public static abstract class ConstantQuality extends Quality {
        public abstract int getHighSpeedValue();

        public abstract String getName();

        public abstract List<Size> getTypicalSizes();

        public abstract int getValue();

        public ConstantQuality() {
            super();
        }

        /* JADX INFO: renamed from: of */
        public static ConstantQuality m105of(int i, int i2, String str, List<Size> list) {
            return new AutoValue_Quality_ConstantQuality(i, i2, str, list);
        }

        public int getQualityValue(int i) {
            if (i == 1) {
                return getValue();
            }
            if (i == 2) {
                return getHighSpeedValue();
            }
            UseCase$$ExternalSyntheticBUOutline0.m85m("Unknown quality source: ", i);
            return 0;
        }
    }
}
