package com.yandex.mapkit.navigation;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class JamTypeColor implements Serializable {
    private int jamColor;
    private JamType jamType;

    public JamTypeColor(JamType jamType, int i) {
        if (jamType == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"jamType\" cannot be null");
            throw null;
        }
        this.jamType = jamType;
        this.jamColor = i;
    }

    public JamTypeColor() {
    }

    public JamType getJamType() {
        return this.jamType;
    }

    public int getJamColor() {
        return this.jamColor;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.jamType = (JamType) archive.add(this.jamType, false, (Class<JamType>) JamType.class);
        this.jamColor = archive.add(this.jamColor);
    }
}
