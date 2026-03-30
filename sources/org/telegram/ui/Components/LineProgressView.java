package org.telegram.ui.Components;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MediaDataController;

/* JADX INFO: loaded from: classes3.dex */
public class LineProgressView extends LinearProgressIndicator {
    private float currentProgress;
    public int type;

    public LineProgressView(Context context) {
        this(context, null);
    }

    public LineProgressView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.linearProgressIndicatorStyle);
    }

    public LineProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void setProgressType(int i) {
        if (this.type == i) {
            return;
        }
        this.type = i;
        if (i == 0) {
            setWaveSpeed(0);
            setWavelength(0);
            setWaveAmplitude(0);
        } else if (i == 1) {
            setWavelengthDeterminate(AndroidUtilities.dp(40.0f));
            setWaveAmplitude(AndroidUtilities.dp(3.0f));
            setWaveSpeed(AndroidUtilities.dp(15.0f));
            setWaveAmplitudeRampProgressMin(0.05f);
        }
    }

    private void init() {
        setMax(MediaDataController.MAX_STYLE_RUNS_COUNT);
        setTrackThickness(AndroidUtilities.dp(4.0f));
        setTrackCornerRadius(AndroidUtilities.dp(2.0f));
        setTrackStopIndicatorSize(AndroidUtilities.dp(4.0f));
        setIndicatorTrackGapSize(AndroidUtilities.dp(4.0f));
        setIndeterminate(false);
        setProgressType(0);
    }

    public void setProgressColor(int i) {
        setIndicatorColor(i);
    }

    public void setBackColor(int i) {
        setTrackColor(i);
    }

    public void setProgress(float f, boolean z) {
        this.currentProgress = f;
        setProgressCompat((int) (f * getMax()), z);
    }

    public float getCurrentProgress() {
        return this.currentProgress;
    }
}
