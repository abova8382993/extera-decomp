package androidx.camera.video;

import android.util.Size;
import androidx.camera.video.Quality;
import java.util.List;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes4.dex */
final class AutoValue_Quality_ConstantQuality extends Quality.ConstantQuality {
    private final int highSpeedValue;
    private final String name;
    private final List<Size> typicalSizes;
    private final int value;

    public AutoValue_Quality_ConstantQuality(int i, int i2, String str, List<Size> list) {
        this.value = i;
        this.highSpeedValue = i2;
        if (str == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null name");
            throw null;
        }
        this.name = str;
        if (list == null) {
            g$$ExternalSyntheticBUOutline2.m208m("Null typicalSizes");
            throw null;
        }
        this.typicalSizes = list;
    }

    @Override // androidx.camera.video.Quality.ConstantQuality
    public int getValue() {
        return this.value;
    }

    @Override // androidx.camera.video.Quality.ConstantQuality
    public int getHighSpeedValue() {
        return this.highSpeedValue;
    }

    @Override // androidx.camera.video.Quality.ConstantQuality
    public String getName() {
        return this.name;
    }

    @Override // androidx.camera.video.Quality.ConstantQuality
    public List<Size> getTypicalSizes() {
        return this.typicalSizes;
    }

    public String toString() {
        return "ConstantQuality{value=" + this.value + ", highSpeedValue=" + this.highSpeedValue + ", name=" + this.name + ", typicalSizes=" + this.typicalSizes + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Quality.ConstantQuality) {
            Quality.ConstantQuality constantQuality = (Quality.ConstantQuality) obj;
            if (this.value == constantQuality.getValue() && this.highSpeedValue == constantQuality.getHighSpeedValue() && this.name.equals(constantQuality.getName()) && this.typicalSizes.equals(constantQuality.getTypicalSizes())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.typicalSizes.hashCode() ^ ((((((this.value ^ 1000003) * 1000003) ^ this.highSpeedValue) * 1000003) ^ this.name.hashCode()) * 1000003);
    }
}
