package com.yandex.mapkit.uri;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class Uri implements Serializable {
    private String value;

    public Uri(String str) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Required field \"value\" cannot be null");
            throw null;
        }
        this.value = str;
    }

    public Uri() {
    }

    public String getValue() {
        return this.value;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.value = archive.add(this.value, false);
    }
}
