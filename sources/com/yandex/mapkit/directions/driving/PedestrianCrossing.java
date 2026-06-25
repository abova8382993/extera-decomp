package com.yandex.mapkit.directions.driving;

import com.yandex.mapkit.geometry.PolylinePosition;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class PedestrianCrossing implements Serializable {
    private PolylinePosition position;

    public PedestrianCrossing(PolylinePosition polylinePosition) {
        if (polylinePosition == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"position\" cannot be null");
            throw null;
        }
        this.position = polylinePosition;
    }

    public PedestrianCrossing() {
    }

    public PolylinePosition getPosition() {
        return this.position;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.position = (PolylinePosition) archive.add(this.position, false, (Class<PolylinePosition>) PolylinePosition.class);
    }
}
