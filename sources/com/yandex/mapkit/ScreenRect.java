package com.yandex.mapkit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class ScreenRect implements Serializable {
    private ScreenPoint bottomRight;
    private ScreenPoint topLeft;

    public ScreenRect(ScreenPoint screenPoint, ScreenPoint screenPoint2) {
        if (screenPoint == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"topLeft\" cannot be null");
            throw null;
        }
        if (screenPoint2 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"bottomRight\" cannot be null");
            throw null;
        }
        this.topLeft = screenPoint;
        this.bottomRight = screenPoint2;
    }

    public ScreenRect() {
    }

    public ScreenPoint getTopLeft() {
        return this.topLeft;
    }

    public ScreenPoint getBottomRight() {
        return this.bottomRight;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.topLeft = (ScreenPoint) archive.add(this.topLeft, false, (Class<ScreenPoint>) ScreenPoint.class);
        this.bottomRight = (ScreenPoint) archive.add(this.bottomRight, false, (Class<ScreenPoint>) ScreenPoint.class);
    }
}
