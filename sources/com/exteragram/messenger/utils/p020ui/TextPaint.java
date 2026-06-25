package com.exteragram.messenger.utils.p020ui;

import org.telegram.messenger.AndroidUtilities;

/* JADX INFO: loaded from: classes4.dex */
public class TextPaint extends android.text.TextPaint {
    public TextPaint(int i) {
        super(i);
        setTypeface(AndroidUtilities.regular());
    }
}
