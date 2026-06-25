package com.android.p006dx.rop.annotation;

import com.android.p006dx.util.ToHuman;

/* JADX INFO: loaded from: classes4.dex */
public enum AnnotationVisibility implements ToHuman {
    RUNTIME("runtime"),
    BUILD("build"),
    SYSTEM("system"),
    EMBEDDED("embedded");

    private final String human;

    AnnotationVisibility(String str) {
        this.human = str;
    }

    @Override // com.android.p006dx.util.ToHuman
    public String toHuman() {
        return this.human;
    }
}
