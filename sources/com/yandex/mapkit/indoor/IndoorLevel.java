package com.yandex.mapkit.indoor;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class IndoorLevel implements Serializable {

    /* JADX INFO: renamed from: id */
    private String f672id;
    private boolean isUnderground;
    private String name;

    public IndoorLevel(String str, String str2, boolean z) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"id\" cannot be null");
            throw null;
        }
        if (str2 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"name\" cannot be null");
            throw null;
        }
        this.f672id = str;
        this.name = str2;
        this.isUnderground = z;
    }

    public IndoorLevel() {
    }

    public String getId() {
        return this.f672id;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsUnderground() {
        return this.isUnderground;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f672id = archive.add(this.f672id, false);
        this.name = archive.add(this.name, false);
        this.isUnderground = archive.add(this.isUnderground);
    }
}
