package com.yandex.mapkit.directions.driving.internal;

import com.yandex.mapkit.geometry.PolylinePosition;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class TollPost implements Serializable {

    /* JADX INFO: renamed from: id */
    private Long f669id;
    private Boolean nonTransactional;
    private PolylinePosition position;
    private Double time_with_traffic;

    public TollPost(PolylinePosition polylinePosition, Long l, Double d, Boolean bool) {
        if (polylinePosition == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"position\" cannot be null");
            throw null;
        }
        this.position = polylinePosition;
        this.f669id = l;
        this.time_with_traffic = d;
        this.nonTransactional = bool;
    }

    public TollPost() {
    }

    public PolylinePosition getPosition() {
        return this.position;
    }

    public Long getId() {
        return this.f669id;
    }

    public Double getTime_with_traffic() {
        return this.time_with_traffic;
    }

    public Boolean getNonTransactional() {
        return this.nonTransactional;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.position = (PolylinePosition) archive.add(this.position, false, (Class<PolylinePosition>) PolylinePosition.class);
        this.f669id = archive.add(this.f669id, true);
        this.time_with_traffic = archive.add(this.time_with_traffic, true);
        this.nonTransactional = archive.add(this.nonTransactional, true);
    }
}
