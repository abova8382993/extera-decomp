package com.yandex.mapkit.directions.driving;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class DirectionSignToponym implements Serializable {
    private DirectionSignStyle style;
    private String text;

    public DirectionSignToponym(String str, DirectionSignStyle directionSignStyle) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"text\" cannot be null");
            throw null;
        }
        if (directionSignStyle == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"style\" cannot be null");
            throw null;
        }
        this.text = str;
        this.style = directionSignStyle;
    }

    public DirectionSignToponym() {
    }

    public String getText() {
        return this.text;
    }

    public DirectionSignStyle getStyle() {
        return this.style;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.text = archive.add(this.text, false);
        this.style = (DirectionSignStyle) archive.add(this.style, false, (Class<DirectionSignStyle>) DirectionSignStyle.class);
    }
}
