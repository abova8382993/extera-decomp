package org.telegram.ui.Cells;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class ChatMessageCell$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ ChatMessageCell f$0;

    public /* synthetic */ ChatMessageCell$$ExternalSyntheticLambda5(ChatMessageCell chatMessageCell) {
        this.f$0 = chatMessageCell;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.invalidateOutbounds();
    }
}
