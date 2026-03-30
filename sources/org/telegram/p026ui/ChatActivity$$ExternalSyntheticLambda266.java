package org.telegram.p026ui;

import org.telegram.p026ui.Components.ItemOptions;

/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class ChatActivity$$ExternalSyntheticLambda266 implements Runnable {
    public final /* synthetic */ ItemOptions f$0;

    public /* synthetic */ ChatActivity$$ExternalSyntheticLambda266(ItemOptions itemOptions) {
        this.f$0 = itemOptions;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.closeSwipeback();
    }
}
