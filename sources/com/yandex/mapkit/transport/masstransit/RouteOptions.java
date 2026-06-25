package com.yandex.mapkit.transport.masstransit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class RouteOptions implements Serializable {
    private FitnessOptions fitnessOptions;

    public RouteOptions(FitnessOptions fitnessOptions) {
        if (fitnessOptions == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"fitnessOptions\" cannot be null");
            throw null;
        }
        this.fitnessOptions = fitnessOptions;
    }

    public RouteOptions() {
    }

    public FitnessOptions getFitnessOptions() {
        return this.fitnessOptions;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.fitnessOptions = (FitnessOptions) archive.add(this.fitnessOptions, false, (Class<FitnessOptions>) FitnessOptions.class);
    }
}
