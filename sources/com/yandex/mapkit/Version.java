package com.yandex.mapkit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class Version implements Serializable {
    private String str;

    public Version(String str) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"str\" cannot be null");
            throw null;
        }
        this.str = str;
    }

    public Version() {
    }

    public String getStr() {
        return this.str;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.str = archive.add(this.str, false);
    }
}
