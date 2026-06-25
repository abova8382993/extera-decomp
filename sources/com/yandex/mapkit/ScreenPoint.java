package com.yandex.mapkit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public class ScreenPoint implements Serializable {

    /* JADX INFO: renamed from: x */
    private float f664x;

    /* JADX INFO: renamed from: y */
    private float f665y;

    public ScreenPoint(float f, float f2) {
        this.f664x = f;
        this.f665y = f2;
    }

    public ScreenPoint() {
    }

    public float getX() {
        return this.f664x;
    }

    public float getY() {
        return this.f665y;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f664x = archive.add(this.f664x);
        this.f665y = archive.add(this.f665y);
    }
}
