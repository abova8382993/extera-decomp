package org.telegram.ui.Stories;

/* JADX INFO: loaded from: classes6.dex */
public final /* synthetic */ class StoriesController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ StoriesController f$0;

    public /* synthetic */ StoriesController$$ExternalSyntheticLambda6(StoriesController storiesController) {
        this.f$0 = storiesController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.invalidateStoryLimit();
    }
}
