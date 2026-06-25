package org.telegram.p035ui.Components.blur3.drawable.color;

import org.telegram.p035ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes3.dex */
public interface BlurredBackgroundColorProvider {
    int getBackgroundColor();

    int getShadowColor();

    int getStrokeColorBottom();

    int getStrokeColorTop();

    default int getStrokeColorFull() {
        return Theme.getColor(Theme.key_divider);
    }
}
