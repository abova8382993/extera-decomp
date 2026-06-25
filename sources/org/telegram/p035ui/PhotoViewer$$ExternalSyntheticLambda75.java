package org.telegram.p035ui;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class PhotoViewer$$ExternalSyntheticLambda75 implements Runnable {
    public final /* synthetic */ PhotoViewer f$0;

    public /* synthetic */ PhotoViewer$$ExternalSyntheticLambda75(PhotoViewer photoViewer) {
        this.f$0 = photoViewer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.invalidateBlur();
    }
}
