package org.telegram.p026ui.Components.voip;

import android.view.View;
import androidx.annotation.Keep;

/* JADX INFO: loaded from: classes5.dex */
public abstract class CallSwipeView extends View {
    /* JADX INFO: renamed from: -$$Nest$fgetarrowAlphas, reason: not valid java name */
    static /* bridge */ /* synthetic */ int[] m12563$$Nest$fgetarrowAlphas(CallSwipeView callSwipeView) {
        throw null;
    }

    private class ArrowAnimWrapper {
        private int index;

        @Keep
        public int getArrowAlpha() {
            return CallSwipeView.m12563$$Nest$fgetarrowAlphas(null)[this.index];
        }

        @Keep
        public void setArrowAlpha(int i) {
            CallSwipeView.m12563$$Nest$fgetarrowAlphas(null)[this.index] = i;
        }
    }
}
