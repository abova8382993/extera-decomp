package com.yandex.mapkit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class Animation implements Serializable {
    private float duration;
    private Type type;

    public enum Type {
        SMOOTH,
        LINEAR
    }

    public Animation(Type type, float f) {
        if (type == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"type\" cannot be null");
            throw null;
        }
        this.type = type;
        this.duration = f;
    }

    public Animation() {
    }

    public Type getType() {
        return this.type;
    }

    public float getDuration() {
        return this.duration;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.type = (Type) archive.add(this.type, false, (Class<Type>) Type.class);
        this.duration = archive.add(this.duration);
    }
}
