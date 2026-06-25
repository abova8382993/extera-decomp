package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.view.MotionEvent;
import android.view.View;
import java.io.File;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Utilities;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VideoTimelinePlayView extends View {
    public static int TYPE_LEFT = 0;
    public static int TYPE_PROGRESS = 2;
    public static int TYPE_RIGHT = 1;
    private static final Object sync = new Object();
    Paint bitmapPaint;
    private Path clipPath;
    private int currentMode;
    private AsyncTask<Integer, Integer, Bitmap> currentTask;
    private final Paint cutPaint;
    private VideoTimelineViewDelegate delegate;
    private final Paint dimPaint;
    private ArrayList<Rect> exclusionRects;
    private Rect exclustionRect;

    /* JADX INFO: renamed from: fd */
    private ParcelFileDescriptor f1709fd;
    private int frameHeight;
    private long frameTimeOffset;
    private int frameWidth;
    private ArrayList<BitmapFrame> frames;
    private int framesToLoad;
    private final Paint handlePaint;
    private boolean hasBlur;
    private boolean isLivePhoto;
    private int lastWidth;
    private final AnimatedFloat loopProgress;
    private float maxProgressDiff;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private float minProgressDiff;
    private float playProgress;
    private float pressDx;
    private boolean pressedLeft;
    private boolean pressedPlay;
    private boolean pressedRight;
    private float progressLeft;
    private float progressPreview;
    private float progressRight;
    private RectF rect3;
    private final Paint shadowPaint;
    private int videoHeight;
    private long videoLength;
    private int videoWidth;
    private final Paint whitePaint;
    private final Paint yellowPaint;

    /* JADX INFO: loaded from: classes7.dex */
    public interface VideoTimelineViewDelegate {
        void didStartDragging(int i);

        void didStopDragging(int i);

        void onLeftProgressChanged(float f);

        void onPlayProgressChanged(float f);

        void onRightProgressChanged(float f);
    }

    public abstract boolean customBlur();

    public abstract void drawBlur(Canvas canvas, RectF rectF);

    public VideoTimelinePlayView(Context context) {
        super(context);
        this.progressRight = 1.0f;
        this.playProgress = 0.5f;
        this.frames = new ArrayList<>();
        this.maxProgressDiff = 1.0f;
        this.minProgressDiff = 0.0f;
        this.rect3 = new RectF();
        this.currentMode = 0;
        this.bitmapPaint = new Paint(3);
        this.exclusionRects = new ArrayList<>();
        this.exclustionRect = new Rect();
        Paint paint = new Paint(1);
        this.whitePaint = paint;
        Paint paint2 = new Paint(1);
        this.yellowPaint = paint2;
        Paint paint3 = new Paint(1);
        this.shadowPaint = paint3;
        Paint paint4 = new Paint(1);
        this.dimPaint = paint4;
        Paint paint5 = new Paint(1);
        this.cutPaint = paint5;
        Paint paint6 = new Paint(1);
        this.handlePaint = paint6;
        this.loopProgress = new AnimatedFloat(0.0f, this, 0L, 200L, CubicBezierInterpolator.EASE_BOTH);
        this.clipPath = new Path();
        paint.setColor(-1);
        paint2.setColor(-256);
        paint3.setColor(637534208);
        paint4.setColor(1291845632);
        paint5.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint6.setColor(-16777216);
        this.exclusionRects.add(this.exclustionRect);
    }

    public float getProgress() {
        return this.playProgress;
    }

    public float getLeftProgress() {
        return this.progressLeft;
    }

    public float getRightProgress() {
        return this.progressRight;
    }

    public void setMinProgressDiff(float f) {
        this.minProgressDiff = f;
    }

    public void setMode(int i) {
        if (this.currentMode == i) {
            return;
        }
        this.currentMode = i;
        invalidate();
    }

    public void setMaxProgressDiff(float f) {
        this.maxProgressDiff = f;
        float f2 = this.progressRight;
        float f3 = this.progressLeft;
        if (f2 - f3 > f) {
            this.progressRight = f3 + f;
            invalidate();
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (Build.VERSION.SDK_INT >= 29) {
            this.exclustionRect.set(i, 0, i3, getMeasuredHeight());
            setSystemGestureExclusionRects(this.exclusionRects);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int measuredWidth = getMeasuredWidth() - AndroidUtilities.m1036dp(44.0f);
        float f = measuredWidth;
        int iM1036dp = ((int) (this.progressLeft * f)) + AndroidUtilities.m1036dp(22.0f);
        int iM1036dp2 = ((int) (this.playProgress * f)) + AndroidUtilities.m1036dp(22.0f);
        int iM1036dp3 = ((int) (this.progressRight * f)) + AndroidUtilities.m1036dp(22.0f);
        if (motionEvent.getAction() == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (this.mediaMetadataRetriever == null) {
                return false;
            }
            int iM1036dp4 = AndroidUtilities.m1036dp(16.0f);
            int iM1036dp5 = AndroidUtilities.m1036dp(8.0f);
            if (iM1036dp3 != iM1036dp && iM1036dp2 - iM1036dp5 <= x && x <= iM1036dp5 + iM1036dp2 && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate = this.delegate;
                if (videoTimelineViewDelegate != null) {
                    videoTimelineViewDelegate.didStartDragging(TYPE_PROGRESS);
                }
                this.pressedPlay = true;
                this.pressDx = (int) (x - iM1036dp2);
                invalidate();
                return true;
            }
            if (iM1036dp - iM1036dp4 <= x && x <= Math.min(iM1036dp + iM1036dp4, iM1036dp3) && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate2 = this.delegate;
                if (videoTimelineViewDelegate2 != null) {
                    videoTimelineViewDelegate2.didStartDragging(TYPE_LEFT);
                }
                this.pressedLeft = true;
                this.pressDx = (int) (x - iM1036dp);
                invalidate();
                return true;
            }
            if (iM1036dp3 - iM1036dp4 <= x && x <= iM1036dp4 + iM1036dp3 && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate3 = this.delegate;
                if (videoTimelineViewDelegate3 != null) {
                    videoTimelineViewDelegate3.didStartDragging(TYPE_RIGHT);
                }
                this.pressedRight = true;
                this.pressDx = (int) (x - iM1036dp3);
                invalidate();
                return true;
            }
            if (iM1036dp <= x && x <= iM1036dp3 && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate4 = this.delegate;
                if (videoTimelineViewDelegate4 != null) {
                    videoTimelineViewDelegate4.didStartDragging(TYPE_PROGRESS);
                }
                this.pressedPlay = true;
                float fM1036dp = (x - AndroidUtilities.m1036dp(16.0f)) / f;
                this.playProgress = fM1036dp;
                VideoTimelineViewDelegate videoTimelineViewDelegate5 = this.delegate;
                if (videoTimelineViewDelegate5 != null) {
                    videoTimelineViewDelegate5.onPlayProgressChanged(fM1036dp);
                }
                this.pressDx = 0.0f;
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (this.pressedLeft) {
                VideoTimelineViewDelegate videoTimelineViewDelegate6 = this.delegate;
                if (videoTimelineViewDelegate6 != null) {
                    videoTimelineViewDelegate6.didStopDragging(TYPE_LEFT);
                }
                this.pressedLeft = false;
                return true;
            }
            if (this.pressedRight) {
                VideoTimelineViewDelegate videoTimelineViewDelegate7 = this.delegate;
                if (videoTimelineViewDelegate7 != null) {
                    videoTimelineViewDelegate7.didStopDragging(TYPE_RIGHT);
                }
                this.pressedRight = false;
                return true;
            }
            if (this.pressedPlay) {
                VideoTimelineViewDelegate videoTimelineViewDelegate8 = this.delegate;
                if (videoTimelineViewDelegate8 != null) {
                    videoTimelineViewDelegate8.didStopDragging(TYPE_PROGRESS);
                }
                this.pressedPlay = false;
            }
        } else if (motionEvent.getAction() == 2) {
            if (this.pressedPlay) {
                float fM1036dp2 = (((int) (x - this.pressDx)) - AndroidUtilities.m1036dp(16.0f)) / f;
                this.playProgress = fM1036dp2;
                float f2 = this.progressLeft;
                if (fM1036dp2 < f2) {
                    this.playProgress = f2;
                } else {
                    float f3 = this.progressRight;
                    if (fM1036dp2 > f3) {
                        this.playProgress = f3;
                    }
                }
                VideoTimelineViewDelegate videoTimelineViewDelegate9 = this.delegate;
                if (videoTimelineViewDelegate9 != null) {
                    videoTimelineViewDelegate9.onPlayProgressChanged(this.playProgress);
                }
                invalidate();
                return true;
            }
            if (this.pressedLeft) {
                int i = (int) (x - this.pressDx);
                if (i < AndroidUtilities.m1036dp(16.0f)) {
                    iM1036dp3 = AndroidUtilities.m1036dp(16.0f);
                } else if (i <= iM1036dp3) {
                    iM1036dp3 = i;
                }
                float fM1036dp3 = (iM1036dp3 - AndroidUtilities.m1036dp(16.0f)) / f;
                this.progressLeft = fM1036dp3;
                float f4 = this.progressRight;
                float f5 = f4 - fM1036dp3;
                float f6 = this.maxProgressDiff;
                if (f5 > f6) {
                    this.progressRight = fM1036dp3 + f6;
                } else {
                    float f7 = this.minProgressDiff;
                    if (f7 != 0.0f && f4 - fM1036dp3 < f7) {
                        float f8 = f4 - f7;
                        this.progressLeft = f8;
                        if (f8 < 0.0f) {
                            this.progressLeft = 0.0f;
                        }
                    }
                }
                float f9 = this.progressLeft;
                float f10 = this.playProgress;
                if (f9 > f10) {
                    this.playProgress = f9;
                } else {
                    float f11 = this.progressRight;
                    if (f11 < f10) {
                        this.playProgress = f11;
                    }
                }
                VideoTimelineViewDelegate videoTimelineViewDelegate10 = this.delegate;
                if (videoTimelineViewDelegate10 != null) {
                    videoTimelineViewDelegate10.onLeftProgressChanged(f9);
                }
                invalidate();
                return true;
            }
            if (this.pressedRight) {
                int i2 = (int) (x - this.pressDx);
                if (i2 >= iM1036dp) {
                    iM1036dp = i2 > AndroidUtilities.m1036dp(16.0f) + measuredWidth ? measuredWidth + AndroidUtilities.m1036dp(16.0f) : i2;
                }
                float fM1036dp4 = (iM1036dp - AndroidUtilities.m1036dp(16.0f)) / f;
                this.progressRight = fM1036dp4;
                float f12 = this.progressLeft;
                float f13 = fM1036dp4 - f12;
                float f14 = this.maxProgressDiff;
                if (f13 > f14) {
                    this.progressLeft = fM1036dp4 - f14;
                } else {
                    float f15 = this.minProgressDiff;
                    if (f15 != 0.0f && fM1036dp4 - f12 < f15) {
                        float f16 = f12 + f15;
                        this.progressRight = f16;
                        if (f16 > 1.0f) {
                            this.progressRight = 1.0f;
                        }
                    }
                }
                float f17 = this.progressLeft;
                float f18 = this.playProgress;
                if (f17 > f18) {
                    this.playProgress = f17;
                } else {
                    float f19 = this.progressRight;
                    if (f19 < f18) {
                        this.playProgress = f19;
                    }
                }
                VideoTimelineViewDelegate videoTimelineViewDelegate11 = this.delegate;
                if (videoTimelineViewDelegate11 != null) {
                    videoTimelineViewDelegate11.onRightProgressChanged(this.progressRight);
                }
                invalidate();
                return true;
            }
        }
        return true;
    }

    public void setVideoPath(String str, long j, float f, float f2, long j2) {
        int i;
        destroy();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        this.mediaMetadataRetriever = mediaMetadataRetriever;
        this.isLivePhoto = j > 0;
        this.progressLeft = f;
        this.progressRight = f2;
        float f3 = this.playProgress;
        if (f3 < f) {
            this.playProgress = f;
        } else if (f3 > f2) {
            this.playProgress = f2;
        }
        try {
            if (j > 0) {
                File file = new File(str);
                ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 268435456);
                this.f1709fd = parcelFileDescriptorOpen;
                this.mediaMetadataRetriever.setDataSource(parcelFileDescriptorOpen.getFileDescriptor(), j, file.length() - j);
            } else {
                mediaMetadataRetriever.setDataSource(str);
            }
            String strExtractMetadata = this.mediaMetadataRetriever.extractMetadata(9);
            if (strExtractMetadata != null) {
                this.videoLength = Long.parseLong(strExtractMetadata);
            }
            String strExtractMetadata2 = this.mediaMetadataRetriever.extractMetadata(18);
            if (strExtractMetadata2 != null) {
                this.videoWidth = Integer.parseInt(strExtractMetadata2);
            }
            String strExtractMetadata3 = this.mediaMetadataRetriever.extractMetadata(19);
            if (strExtractMetadata3 != null) {
                this.videoHeight = Integer.parseInt(strExtractMetadata3);
            }
            String strExtractMetadata4 = this.mediaMetadataRetriever.extractMetadata(24);
            if (strExtractMetadata4 != null && ((i = Integer.parseInt(strExtractMetadata4)) == 90 || i == 270)) {
                int i2 = this.videoWidth;
                this.videoWidth = this.videoHeight;
                this.videoHeight = i2;
            }
            if (this.isLivePhoto) {
                this.progressPreview = (float) ((j2 / 1000.0d) / this.videoLength);
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        invalidate();
    }

    public long getLength() {
        return Math.max(1L, this.videoLength);
    }

    public void setRightProgress(float f) {
        this.progressRight = f;
        VideoTimelineViewDelegate videoTimelineViewDelegate = this.delegate;
        if (videoTimelineViewDelegate != null) {
            videoTimelineViewDelegate.didStartDragging(TYPE_RIGHT);
        }
        VideoTimelineViewDelegate videoTimelineViewDelegate2 = this.delegate;
        if (videoTimelineViewDelegate2 != null) {
            videoTimelineViewDelegate2.onRightProgressChanged(this.progressRight);
        }
        VideoTimelineViewDelegate videoTimelineViewDelegate3 = this.delegate;
        if (videoTimelineViewDelegate3 != null) {
            videoTimelineViewDelegate3.didStopDragging(TYPE_RIGHT);
        }
        invalidate();
    }

    public void setDelegate(VideoTimelineViewDelegate videoTimelineViewDelegate) {
        this.delegate = videoTimelineViewDelegate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadFrames(int i) {
        int i2;
        if (this.mediaMetadataRetriever == null) {
            return;
        }
        if (i == 0) {
            this.frameHeight = AndroidUtilities.m1036dp(38.0f);
            this.framesToLoad = Math.max(1, (int) Math.ceil((getMeasuredWidth() - AndroidUtilities.m1036dp(32.0f)) / (this.frameHeight * Utilities.clamp((this.videoWidth == 0 || (i2 = this.videoHeight) == 0) ? 1.0f : r0 / i2, 1.3333334f, 0.5625f))));
            this.frameWidth = (int) Math.ceil((getMeasuredWidth() - AndroidUtilities.m1036dp(32.0f)) / this.framesToLoad);
            this.frameTimeOffset = this.videoLength / ((long) this.framesToLoad);
        }
        AsyncTask<Integer, Integer, Bitmap> asyncTask = new AsyncTask<Integer, Integer, Bitmap>() { // from class: org.telegram.ui.Components.VideoTimelinePlayView.1
            private int frameNum = 0;
            private final Paint paint = new Paint(3);

            @Override // android.os.AsyncTask
            public Bitmap doInBackground(Integer... numArr) {
                this.frameNum = numArr[0].intValue();
                Bitmap bitmap = null;
                if (isCancelled()) {
                    return null;
                }
                try {
                    Bitmap frameAtTime = VideoTimelinePlayView.this.mediaMetadataRetriever.getFrameAtTime(VideoTimelinePlayView.this.frameTimeOffset * ((long) this.frameNum) * 1000, 2);
                    try {
                        if (isCancelled()) {
                            return null;
                        }
                        if (frameAtTime == null) {
                            return frameAtTime;
                        }
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(VideoTimelinePlayView.this.frameWidth, VideoTimelinePlayView.this.frameHeight, frameAtTime.getConfig());
                        Canvas canvas = new Canvas(bitmapCreateBitmap);
                        float fMax = Math.max(VideoTimelinePlayView.this.frameWidth / frameAtTime.getWidth(), VideoTimelinePlayView.this.frameHeight / frameAtTime.getHeight());
                        int width = (int) (frameAtTime.getWidth() * fMax);
                        int height = (int) (frameAtTime.getHeight() * fMax);
                        canvas.drawBitmap(frameAtTime, new Rect(0, 0, frameAtTime.getWidth(), frameAtTime.getHeight()), new Rect((VideoTimelinePlayView.this.frameWidth - width) / 2, (VideoTimelinePlayView.this.frameHeight - height) / 2, (VideoTimelinePlayView.this.frameWidth + width) / 2, (VideoTimelinePlayView.this.frameHeight + height) / 2), this.paint);
                        frameAtTime.recycle();
                        return bitmapCreateBitmap;
                    } catch (Exception e) {
                        e = e;
                        bitmap = frameAtTime;
                        FileLog.m1048e(e);
                        return bitmap;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(Bitmap bitmap) {
                if (isCancelled()) {
                    return;
                }
                VideoTimelinePlayView.this.frames.add(new BitmapFrame(bitmap));
                VideoTimelinePlayView.this.invalidate();
                if (this.frameNum < VideoTimelinePlayView.this.framesToLoad) {
                    VideoTimelinePlayView.this.reloadFrames(this.frameNum + 1);
                }
            }
        };
        this.currentTask = asyncTask;
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Integer.valueOf(i), null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0018 A[Catch: all -> 0x000e, Exception -> 0x001e, TRY_LEAVE, TryCatch #1 {Exception -> 0x001e, blocks: (B:13:0x0014, B:15:0x0018), top: B:37:0x0014, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0022 A[Catch: all -> 0x000e, DONT_GENERATE, TRY_LEAVE, TryCatch #2 {, blocks: (B:5:0x0004, B:7:0x0008, B:13:0x0014, B:15:0x0018, B:19:0x0022, B:18:0x001f, B:12:0x0011), top: B:35:0x0004, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void destroy() {
        /*
            r4 = this;
            java.lang.Object r0 = org.telegram.p035ui.Components.VideoTimelinePlayView.sync
            monitor-enter(r0)
            r1 = 0
            android.os.ParcelFileDescriptor r2 = r4.f1709fd     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L10
            if (r2 == 0) goto L14
            r2.close()     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L10
            r4.f1709fd = r1     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L10
            goto L14
        Le:
            r4 = move-exception
            goto L4e
        L10:
            r2 = move-exception
            org.telegram.messenger.FileLog.m1048e(r2)     // Catch: java.lang.Throwable -> Le
        L14:
            android.media.MediaMetadataRetriever r2 = r4.mediaMetadataRetriever     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L1e
            if (r2 == 0) goto L22
            r2.release()     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L1e
            r4.mediaMetadataRetriever = r1     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L1e
            goto L22
        L1e:
            r2 = move-exception
            org.telegram.messenger.FileLog.m1048e(r2)     // Catch: java.lang.Throwable -> Le
        L22:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            r0 = 0
        L24:
            java.util.ArrayList<org.telegram.ui.Components.VideoTimelinePlayView$BitmapFrame> r2 = r4.frames
            int r2 = r2.size()
            java.util.ArrayList<org.telegram.ui.Components.VideoTimelinePlayView$BitmapFrame> r3 = r4.frames
            if (r0 >= r2) goto L40
            java.lang.Object r2 = r3.get(r0)
            org.telegram.ui.Components.VideoTimelinePlayView$BitmapFrame r2 = (org.telegram.ui.Components.VideoTimelinePlayView.BitmapFrame) r2
            if (r2 == 0) goto L3d
            android.graphics.Bitmap r2 = r2.bitmap
            if (r2 == 0) goto L3d
            r2.recycle()
        L3d:
            int r0 = r0 + 1
            goto L24
        L40:
            r3.clear()
            android.os.AsyncTask<java.lang.Integer, java.lang.Integer, android.graphics.Bitmap> r0 = r4.currentTask
            if (r0 == 0) goto L4d
            r2 = 1
            r0.cancel(r2)
            r4.currentTask = r1
        L4d:
            return
        L4e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.VideoTimelinePlayView.destroy():void");
    }

    public boolean isDragging() {
        return this.pressedPlay;
    }

    public void setProgress(float f) {
        if (this.isLivePhoto && (f <= 0.0f || f >= 1.0f)) {
            f = this.progressPreview;
        }
        long j = this.videoLength;
        float f2 = j != 0 ? 240.0f / j : 0.0f;
        float f3 = this.playProgress;
        if (f < f3 && f <= this.progressLeft + f2 && f3 + f2 >= this.progressRight) {
            this.loopProgress.set(1.0f, true);
        }
        this.playProgress = f;
        invalidate();
    }

    public void clearFrames() {
        ArrayList<BitmapFrame> arrayList;
        Bitmap bitmap;
        int i = 0;
        while (true) {
            int size = this.frames.size();
            arrayList = this.frames;
            if (i >= size) {
                break;
            }
            BitmapFrame bitmapFrame = arrayList.get(i);
            if (bitmapFrame != null && (bitmap = bitmapFrame.bitmap) != null) {
                bitmap.recycle();
            }
            i++;
        }
        arrayList.clear();
        AsyncTask<Integer, Integer, Bitmap> asyncTask = this.currentTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.currentTask = null;
        }
        invalidate();
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        if (this.lastWidth != size) {
            clearFrames();
            this.lastWidth = size;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0135  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDraw(android.graphics.Canvas r21) {
        /*
            Method dump skipped, instruction units count: 653
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.VideoTimelinePlayView.onDraw(android.graphics.Canvas):void");
    }

    private void drawProgress(Canvas canvas, float f, float f2, Paint paint) {
        float fDpf2 = AndroidUtilities.dpf2(12.0f);
        float fM1036dp = AndroidUtilities.m1036dp(2.0f);
        float fM1036dp2 = AndroidUtilities.m1036dp(46.0f) + fM1036dp;
        float f3 = ((fM1036dp2 - fM1036dp) / 2.0f) * (1.0f - f2);
        float f4 = fM1036dp + f3;
        float f5 = fM1036dp2 - f3;
        this.shadowPaint.setAlpha((int) (38.0f * f2));
        paint.setAlpha((int) (f2 * 255.0f));
        float fM1036dp3 = fDpf2 + AndroidUtilities.m1036dp(10.0f) + (((getMeasuredWidth() - (fDpf2 * 2.0f)) - AndroidUtilities.m1036dp(20.0f)) * f);
        this.rect3.set(fM1036dp3 - AndroidUtilities.dpf2(1.5f), f4, AndroidUtilities.dpf2(1.5f) + fM1036dp3, f5);
        this.rect3.inset(-AndroidUtilities.dpf2(0.66f), -AndroidUtilities.dpf2(0.66f));
        canvas.drawRoundRect(this.rect3, AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), this.shadowPaint);
        this.rect3.set(fM1036dp3 - AndroidUtilities.dpf2(1.5f), f4, fM1036dp3 + AndroidUtilities.dpf2(1.5f), f5);
        canvas.drawRoundRect(this.rect3, AndroidUtilities.m1036dp(6.0f), AndroidUtilities.m1036dp(6.0f), paint);
    }

    /* JADX INFO: loaded from: classes7.dex */
    public static class BitmapFrame {
        float alpha;
        Bitmap bitmap;

        public BitmapFrame(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

    public void invalidateBlur() {
        if (customBlur() && this.hasBlur) {
            invalidate();
        }
    }
}
