package com.yandex.mapkit.map;

import android.graphics.PointF;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class Rect implements Serializable {
    private PointF max;
    private PointF min;

    public Rect(PointF pointF, PointF pointF2) {
        if (pointF == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"min\" cannot be null");
            throw null;
        }
        if (pointF2 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"max\" cannot be null");
            throw null;
        }
        this.min = pointF;
        this.max = pointF2;
    }

    public Rect() {
    }

    public PointF getMin() {
        return this.min;
    }

    public PointF getMax() {
        return this.max;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.min = archive.add(this.min, false);
        this.max = archive.add(this.max, false);
    }
}
