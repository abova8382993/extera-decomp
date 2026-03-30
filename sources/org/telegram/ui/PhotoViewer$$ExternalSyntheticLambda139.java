package org.telegram.ui;

import java.io.FileNotFoundException;

/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class PhotoViewer$$ExternalSyntheticLambda139 implements Runnable {
    public final /* synthetic */ PhotoViewer f$0;

    public /* synthetic */ PhotoViewer$$ExternalSyntheticLambda139(PhotoViewer photoViewer) {
        this.f$0 = photoViewer;
    }

    @Override // java.lang.Runnable
    public final void run() throws FileNotFoundException {
        this.f$0.applyCurrentEditMode();
    }
}
