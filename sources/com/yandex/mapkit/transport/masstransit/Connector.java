package com.yandex.mapkit.transport.masstransit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public class Connector implements Serializable {
    private IndoorLevel from;

    /* JADX INFO: renamed from: to */
    private IndoorLevel f697to;

    public Connector(IndoorLevel indoorLevel, IndoorLevel indoorLevel2) {
        this.from = indoorLevel;
        this.f697to = indoorLevel2;
    }

    public Connector() {
    }

    public IndoorLevel getFrom() {
        return this.from;
    }

    public IndoorLevel getTo() {
        return this.f697to;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.from = (IndoorLevel) archive.add(this.from, true, (Class<IndoorLevel>) IndoorLevel.class);
        this.f697to = (IndoorLevel) archive.add(this.f697to, true, (Class<IndoorLevel>) IndoorLevel.class);
    }
}
