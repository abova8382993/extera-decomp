package org.telegram.p035ui.Components;

import android.view.View;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes7.dex */
public interface IPhotoPaintView {
    default void setOffsetTranslationX(float f) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    default View getView() {
        if (this instanceof View) {
            return (View) this;
        }
        g$$ExternalSyntheticBUOutline1.m207m("You should override getView() if you're not inheriting from it.");
        return null;
    }
}
