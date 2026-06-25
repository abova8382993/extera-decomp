package com.yandex.mapkit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public class TileId implements Serializable {

    /* JADX INFO: renamed from: x */
    private int f666x;

    /* JADX INFO: renamed from: y */
    private int f667y;

    /* JADX INFO: renamed from: z */
    private int f668z;

    public TileId(int i, int i2, int i3) {
        this.f666x = i;
        this.f667y = i2;
        this.f668z = i3;
    }

    public TileId() {
    }

    public int getX() {
        return this.f666x;
    }

    public int getY() {
        return this.f667y;
    }

    public int getZ() {
        return this.f668z;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f666x = archive.add(this.f666x);
        this.f667y = archive.add(this.f667y);
        this.f668z = archive.add(this.f668z);
    }
}
