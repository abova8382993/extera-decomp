package com.yandex.mapkit.geometry.geo;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public class XYPoint implements Serializable {

    /* JADX INFO: renamed from: x */
    private double f670x;

    /* JADX INFO: renamed from: y */
    private double f671y;

    public XYPoint(double d, double d2) {
        this.f670x = d;
        this.f671y = d2;
    }

    public XYPoint() {
    }

    public double getX() {
        return this.f670x;
    }

    public double getY() {
        return this.f671y;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f670x = archive.add(this.f670x);
        this.f671y = archive.add(this.f671y);
    }
}
