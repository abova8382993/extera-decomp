package com.yandex.mapkit.directions.driving;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class ManeuverStyle implements Serializable {
    private ArrowManeuverStyle arrow;

    public ManeuverStyle(ArrowManeuverStyle arrowManeuverStyle) {
        if (arrowManeuverStyle == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"arrow\" cannot be null");
            throw null;
        }
        this.arrow = arrowManeuverStyle;
    }

    public ManeuverStyle() {
    }

    public ArrowManeuverStyle getArrow() {
        return this.arrow;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.arrow = (ArrowManeuverStyle) archive.add(this.arrow, false, (Class<ArrowManeuverStyle>) ArrowManeuverStyle.class);
    }
}
