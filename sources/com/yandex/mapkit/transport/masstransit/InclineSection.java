package com.yandex.mapkit.transport.masstransit;

import com.yandex.mapkit.geometry.Subpolyline;
import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class InclineSection implements Serializable {
    private Subpolyline subpolyline;
    private InclineType type;

    public InclineSection(InclineType inclineType, Subpolyline subpolyline) {
        if (inclineType == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"type\" cannot be null");
            throw null;
        }
        if (subpolyline == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"subpolyline\" cannot be null");
            throw null;
        }
        this.type = inclineType;
        this.subpolyline = subpolyline;
    }

    public InclineSection() {
    }

    public InclineType getType() {
        return this.type;
    }

    public Subpolyline getSubpolyline() {
        return this.subpolyline;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.type = (InclineType) archive.add(this.type, false, (Class<InclineType>) InclineType.class);
        this.subpolyline = (Subpolyline) archive.add(this.subpolyline, false, (Class<Subpolyline>) Subpolyline.class);
    }
}
