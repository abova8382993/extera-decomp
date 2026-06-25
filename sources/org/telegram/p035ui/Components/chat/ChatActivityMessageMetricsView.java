package org.telegram.p035ui.Components.chat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.core.math.MathUtils;
import com.google.android.exoplayer2.util.Log;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class ChatActivityMessageMetricsView extends View implements ViewTreeObserver.OnPreDrawListener, ViewTreeObserver.OnScrollChangedListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final RectF tmpRect = new RectF();
    private final boolean DRAW_DEBUG;
    private int currentAccount;
    private long dialogId;
    private final LongSparseArray<RectF> groupedPositions;
    private long lastTime;
    private long lastUserActivityTime;
    private ViewGroup list;
    private ViewTreeObserver observer;
    private Runnable pendingFlush;
    private final ArrayList<TLRPC.TL_inputMessageReadMetric> pendingMetrics;
    private ViewGroup root;
    private final Runnable scheduledCheckRunnable;
    private TextPaint tmpTextPaint;
    private boolean updateInNextDraw;
    private final RectF viewPort;
    private final RectF viewPortInsets;
    private final LongSparseArray<MessageWatcher> watchers;

    public static /* synthetic */ void $r8$lambda$OTgxqPY4rg_4WF2e8sYw8x4osdg(TLRPC.Bool bool, TLRPC.TL_error tL_error) {
    }

    public ChatActivityMessageMetricsView(Context context) {
        super(context);
        this.viewPortInsets = new RectF();
        this.viewPort = new RectF();
        this.pendingMetrics = new ArrayList<>();
        this.watchers = new LongSparseArray<>();
        this.groupedPositions = new LongSparseArray<>();
        this.scheduledCheckRunnable = new Runnable() { // from class: org.telegram.ui.Components.chat.ChatActivityMessageMetricsView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.scheduledCheck();
            }
        };
        this.DRAW_DEBUG = SharedConfig.debugViewMetrics;
    }

    public void init(int i, long j, ViewGroup viewGroup, ViewGroup viewGroup2) {
        this.dialogId = j;
        this.currentAccount = i;
        this.root = viewGroup;
        this.list = viewGroup2;
    }

    public void setViewportPadding(float f, float f2, float f3, float f4) {
        this.viewPortInsets.set(f, f2, f3, f4);
        invalidateViewPort();
    }

    public void setIsUserActive() {
        this.lastUserActivityTime = SystemClock.uptimeMillis();
    }

    private void invalidateViewPort() {
        RectF rectF = this.viewPort;
        RectF rectF2 = this.viewPortInsets;
        rectF.set(rectF2.left, rectF2.top, getMeasuredWidth() - this.viewPortInsets.right, getMeasuredHeight() - this.viewPortInsets.bottom);
    }

    public void finish() {
        int size = this.watchers.size();
        for (int i = 0; i < size; i++) {
            MessageWatcher messageWatcherValueAt = this.watchers.valueAt(i);
            if (messageWatcherValueAt.visible) {
                this.pendingMetrics.add(messageWatcherValueAt.buildMetrics());
            }
        }
        if (BuildVars.LOGS_ENABLED) {
            Log.m321d("ViewMetrics", "finish");
        }
        this.watchers.clear();
        flushImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flushImpl() {
        Runnable runnable = this.pendingFlush;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            this.pendingFlush = null;
        }
        if (this.pendingMetrics.isEmpty()) {
            return;
        }
        TLRPC.TL_messages_reportReadMetrics tL_messages_reportReadMetrics = new TLRPC.TL_messages_reportReadMetrics();
        tL_messages_reportReadMetrics.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(this.dialogId);
        tL_messages_reportReadMetrics.metrics = new ArrayList<>(this.pendingMetrics);
        ConnectionsManager.getInstance(this.currentAccount).sendRequestTyped(tL_messages_reportReadMetrics, null, new Utilities.Callback2() { // from class: org.telegram.ui.Components.chat.ChatActivityMessageMetricsView$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                ChatActivityMessageMetricsView.$r8$lambda$OTgxqPY4rg_4WF2e8sYw8x4osdg((TLRPC.Bool) obj, (TLRPC.TL_error) obj2);
            }
        });
        this.pendingMetrics.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x017f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void processCurrentFrame() {
        /*
            Method dump skipped, instruction units count: 822
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.chat.ChatActivityMessageMetricsView.processCurrentFrame():void");
    }

    public static class MessageWatcher {
        private long activeTime;
        public final long groupId;
        private long lastUpdateMillis;
        private long lastViewMillis;
        private float maxPostTotalHeight;
        private float maxViewPortHeight;
        public final int messageId;
        private final RectF position;
        private float seenBottomPx;
        private float seenTopPx;
        private final long viewId;
        private boolean visible;
        private long visibleTime;

        private MessageWatcher(int i, long j) {
            this.position = new RectF();
            this.maxPostTotalHeight = 0.0f;
            this.maxViewPortHeight = 0.0f;
            this.seenTopPx = Float.MAX_VALUE;
            this.seenBottomPx = 0.0f;
            this.messageId = i;
            this.groupId = j;
            this.viewId = Utilities.random.nextLong();
        }

        public TLRPC.TL_inputMessageReadMetric buildMetrics() {
            TLRPC.TL_inputMessageReadMetric tL_inputMessageReadMetric = new TLRPC.TL_inputMessageReadMetric();
            tL_inputMessageReadMetric.msg_id = this.messageId;
            tL_inputMessageReadMetric.view_id = this.viewId;
            tL_inputMessageReadMetric.time_in_view_ms = (int) this.visibleTime;
            tL_inputMessageReadMetric.active_time_in_view_ms = (int) this.activeTime;
            tL_inputMessageReadMetric.height_to_viewport_ratio_permille = getHeightToViewportRatioPermille();
            tL_inputMessageReadMetric.seen_range_ratio_permille = getSeenRangeRatioPermille();
            return tL_inputMessageReadMetric;
        }

        public int getHeightToViewportRatioPermille() {
            float f = this.maxViewPortHeight;
            return f == 0.0f ? MediaDataController.MAX_STYLE_RUNS_COUNT : Math.round((this.maxPostTotalHeight / f) * 1000.0f);
        }

        public int getSeenRangeRatioPermille() {
            float f = this.maxPostTotalHeight;
            if (f == 0.0f) {
                return 0;
            }
            float f2 = this.seenTopPx;
            float f3 = this.seenBottomPx;
            if (f2 > f3) {
                return 0;
            }
            return Math.round(((f3 - f2) / f) * 1000.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduledCheck() {
        AndroidUtilities.runOnUIThread(this.scheduledCheckRunnable, 400L);
        processCurrentFrame();
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.lastTime = 0L;
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        this.observer = viewTreeObserver;
        viewTreeObserver.addOnPreDrawListener(this);
        this.observer.addOnGlobalLayoutListener(this);
        this.observer.addOnScrollChangedListener(this);
        AndroidUtilities.runOnUIThread(this.scheduledCheckRunnable, 400L);
        if (BuildVars.LOGS_ENABLED) {
            Log.m321d("ViewMetrics", "attach");
        }
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver = this.observer;
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            this.observer.removeOnPreDrawListener(this);
            this.observer.removeOnGlobalLayoutListener(this);
            this.observer.removeOnScrollChangedListener(this);
        }
        this.observer = null;
        this.lastTime = 0L;
        AndroidUtilities.cancelRunOnUIThread(this.scheduledCheckRunnable);
        if (BuildVars.LOGS_ENABLED) {
            Log.m321d("ViewMetrics", "detach");
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        invalidateViewPort();
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        this.updateInNextDraw = true;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        this.updateInNextDraw = true;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        if (!this.updateInNextDraw) {
            return true;
        }
        processCurrentFrame();
        this.updateInNextDraw = false;
        return true;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.DRAW_DEBUG) {
            if (this.tmpTextPaint == null) {
                TextPaint textPaint = new TextPaint(1);
                this.tmpTextPaint = textPaint;
                textPaint.setColor(-16776961);
                this.tmpTextPaint.setTextSize(AndroidUtilities.m1036dp(10.0f));
            }
            super.onDraw(canvas);
            canvas.drawRect(this.viewPort, Theme.DEBUG_RED_STROKE);
            int size = this.watchers.size();
            int i = 0;
            while (i < size) {
                MessageWatcher messageWatcherValueAt = this.watchers.valueAt(i);
                canvas.drawRect(messageWatcherValueAt.position, Theme.DEBUG_GREEN_STROKE);
                canvas.save();
                canvas.translate(messageWatcherValueAt.position.left, MathUtils.clamp(MathUtils.clamp(messageWatcherValueAt.position.centerY() - AndroidUtilities.m1036dp(20.0f), this.viewPort.top - AndroidUtilities.m1036dp(40.0f), this.viewPort.bottom), messageWatcherValueAt.position.top, messageWatcherValueAt.position.bottom - AndroidUtilities.m1036dp(40.0f)));
                Canvas canvas2 = canvas;
                canvas2.drawRect(0.0f, 0.0f, messageWatcherValueAt.position.width(), AndroidUtilities.m1036dp(40.0f), Theme.DEBUG_GREEN_B0);
                canvas2.translate(AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(16.0f));
                canvas2.save();
                canvas2.drawText("time_in_view_ms: " + messageWatcherValueAt.visibleTime, 0.0f, 0.0f, this.tmpTextPaint);
                canvas2.translate(0.0f, (float) AndroidUtilities.m1036dp(16.0f));
                canvas2.drawText("active_time_in_view_ms: " + messageWatcherValueAt.activeTime, 0.0f, 0.0f, this.tmpTextPaint);
                canvas2.restore();
                canvas2.save();
                canvas2.translate(getWidth() / 2.0f, 0.0f);
                canvas2.drawText("height_to_viewport_ratio_permille: " + messageWatcherValueAt.getHeightToViewportRatioPermille(), 0.0f, 0.0f, this.tmpTextPaint);
                canvas2.translate(0.0f, (float) AndroidUtilities.m1036dp(16.0f));
                canvas2.drawText("seen_range_ratio_permille: " + messageWatcherValueAt.getSeenRangeRatioPermille(), 0.0f, 0.0f, this.tmpTextPaint);
                canvas2.restore();
                canvas2.restore();
                i++;
                canvas = canvas2;
            }
        }
    }
}
