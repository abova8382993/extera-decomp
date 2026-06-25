package com.yandex.mapkit.search;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class Chain implements Serializable {

    /* JADX INFO: renamed from: id */
    private String f687id;
    private String name;

    public Chain(String str, String str2) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"id\" cannot be null");
            throw null;
        }
        if (str2 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"name\" cannot be null");
            throw null;
        }
        this.f687id = str;
        this.name = str2;
    }

    public Chain() {
    }

    public String getId() {
        return this.f687id;
    }

    public String getName() {
        return this.name;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f687id = archive.add(this.f687id, false);
        this.name = archive.add(this.name, false);
    }
}
