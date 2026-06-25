package com.yandex.mapkit.navigation.automotive;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class SpeedLimitsRules implements Serializable {
    private Type expressway;
    private Type rural;
    private Type urban;

    public enum Type {
        ABSOLUTE,
        RELATIVE
    }

    public SpeedLimitsRules(Type type, Type type2, Type type3) {
        if (type == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"urban\" cannot be null");
            throw null;
        }
        if (type2 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"rural\" cannot be null");
            throw null;
        }
        if (type3 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"expressway\" cannot be null");
            throw null;
        }
        this.urban = type;
        this.rural = type2;
        this.expressway = type3;
    }

    public SpeedLimitsRules() {
    }

    public Type getUrban() {
        return this.urban;
    }

    public Type getRural() {
        return this.rural;
    }

    public Type getExpressway() {
        return this.expressway;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.urban = (Type) archive.add(this.urban, false, (Class<Type>) Type.class);
        this.rural = (Type) archive.add(this.rural, false, (Class<Type>) Type.class);
        this.expressway = (Type) archive.add(this.expressway, false, (Class<Type>) Type.class);
    }
}
