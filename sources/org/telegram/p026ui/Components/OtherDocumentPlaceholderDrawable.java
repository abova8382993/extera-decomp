package org.telegram.p026ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2702R;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes5.dex */
public class OtherDocumentPlaceholderDrawable extends RecyclableDrawable implements DownloadController.FileDownloadProgressListener {
    private int TAG;
    private String ext;
    private String fileName;
    private String fileSize;
    private boolean loaded;
    private boolean loading;
    private MessageObject parentMessageObject;
    private View parentView;
    private String progress;
    private boolean progressVisible;
    private Drawable thumbDrawable;
    private static Paint paint = new Paint();
    private static Paint progressPaint = new Paint(1);
    private static TextPaint docPaint = new TextPaint(1);
    private static TextPaint namePaint = new TextPaint(1);
    private static TextPaint sizePaint = new TextPaint(1);
    private static TextPaint buttonPaint = new TextPaint(1);
    private static TextPaint percentPaint = new TextPaint(1);
    private static TextPaint openPaint = new TextPaint(1);
    private static DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private long lastUpdateTime = 0;
    private float currentProgress = 0.0f;
    private float animationProgressStart = 0.0f;
    private long currentProgressTime = 0;
    private float animatedProgressValue = 0.0f;
    private float animatedAlphaValue = 1.0f;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressUpload(String str, long j, long j2, boolean z) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    static {
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(-14209998);
        docPaint.setColor(-1);
        namePaint.setColor(-1);
        sizePaint.setColor(-10327179);
        buttonPaint.setColor(-10327179);
        percentPaint.setColor(-1);
        openPaint.setColor(-1);
        docPaint.setTypeface(AndroidUtilities.bold());
        namePaint.setTypeface(AndroidUtilities.bold());
        buttonPaint.setTypeface(AndroidUtilities.bold());
        percentPaint.setTypeface(AndroidUtilities.bold());
        openPaint.setTypeface(AndroidUtilities.bold());
    }

    public OtherDocumentPlaceholderDrawable(Context context, View view, MessageObject messageObject) {
        docPaint.setTextSize(AndroidUtilities.m1081dp(14.0f));
        namePaint.setTextSize(AndroidUtilities.m1081dp(19.0f));
        sizePaint.setTextSize(AndroidUtilities.m1081dp(15.0f));
        buttonPaint.setTextSize(AndroidUtilities.m1081dp(15.0f));
        percentPaint.setTextSize(AndroidUtilities.m1081dp(15.0f));
        openPaint.setTextSize(AndroidUtilities.m1081dp(15.0f));
        progressPaint.setStrokeWidth(AndroidUtilities.m1081dp(2.0f));
        this.parentView = view;
        this.parentMessageObject = messageObject;
        this.TAG = DownloadController.getInstance(messageObject.currentAccount).generateObserverTag();
        TLRPC.Document document = messageObject.getDocument();
        if (document != null) {
            String documentFileName = FileLoader.getDocumentFileName(messageObject.getDocument());
            this.fileName = documentFileName;
            if (TextUtils.isEmpty(documentFileName)) {
                this.fileName = "name";
            }
            int iLastIndexOf = this.fileName.lastIndexOf(46);
            this.ext = iLastIndexOf == -1 ? _UrlKt.FRAGMENT_ENCODE_SET : this.fileName.substring(iLastIndexOf + 1).toUpperCase();
            if (((int) Math.ceil(docPaint.measureText(r0))) > AndroidUtilities.m1081dp(40.0f)) {
                this.ext = TextUtils.ellipsize(this.ext, docPaint, AndroidUtilities.m1081dp(40.0f), TextUtils.TruncateAt.END).toString();
            }
            this.thumbDrawable = context.getResources().getDrawable(AndroidUtilities.getThumbForNameOrMime(this.fileName, messageObject.getDocument().mime_type, true)).mutate();
            this.fileSize = AndroidUtilities.formatFileSize(document.size);
            if (((int) Math.ceil(namePaint.measureText(this.fileName))) > AndroidUtilities.m1081dp(320.0f)) {
                this.fileName = TextUtils.ellipsize(this.fileName, namePaint, AndroidUtilities.m1081dp(320.0f), TextUtils.TruncateAt.END).toString();
            }
        }
        checkFileExist();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.thumbDrawable;
        if (drawable != null) {
            drawable.setAlpha(i);
        }
        paint.setAlpha(i);
        docPaint.setAlpha(i);
        namePaint.setAlpha(i);
        sizePaint.setAlpha(i);
        buttonPaint.setAlpha(i);
        percentPaint.setAlpha(i);
        openPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        String string;
        int iM1081dp;
        TextPaint textPaint;
        Rect bounds = getBounds();
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        canvas.save();
        canvas.translate(bounds.left, bounds.top);
        canvas.drawRect(0.0f, 0.0f, iWidth, iHeight, paint);
        int iM1081dp2 = (iHeight - AndroidUtilities.m1081dp(240.0f)) / 2;
        int iM1081dp3 = (iWidth - AndroidUtilities.m1081dp(48.0f)) / 2;
        this.thumbDrawable.setBounds(iM1081dp3, iM1081dp2, AndroidUtilities.m1081dp(48.0f) + iM1081dp3, AndroidUtilities.m1081dp(48.0f) + iM1081dp2);
        this.thumbDrawable.draw(canvas);
        canvas.drawText(this.ext, (iWidth - ((int) Math.ceil(docPaint.measureText(this.ext)))) / 2, AndroidUtilities.m1081dp(31.0f) + iM1081dp2, docPaint);
        canvas.drawText(this.fileName, (iWidth - ((int) Math.ceil(namePaint.measureText(this.fileName)))) / 2, AndroidUtilities.m1081dp(96.0f) + iM1081dp2, namePaint);
        canvas.drawText(this.fileSize, (iWidth - ((int) Math.ceil(sizePaint.measureText(this.fileSize)))) / 2, AndroidUtilities.m1081dp(125.0f) + iM1081dp2, sizePaint);
        if (this.loaded) {
            string = LocaleController.getString(C2702R.string.OpenFile);
            textPaint = openPaint;
            iM1081dp = 0;
        } else {
            if (this.loading) {
                string = LocaleController.getString(C2702R.string.Cancel).toUpperCase();
            } else {
                string = LocaleController.getString(C2702R.string.TapToDownload);
            }
            iM1081dp = AndroidUtilities.m1081dp(28.0f);
            textPaint = buttonPaint;
        }
        canvas.drawText(string, (iWidth - ((int) Math.ceil(textPaint.measureText(string)))) / 2, AndroidUtilities.m1081dp(235.0f) + iM1081dp2 + iM1081dp, textPaint);
        if (this.progressVisible) {
            if (this.progress != null) {
                canvas.drawText(this.progress, (iWidth - ((int) Math.ceil(percentPaint.measureText(r1)))) / 2, AndroidUtilities.m1081dp(210.0f) + iM1081dp2, percentPaint);
            }
            int iM1081dp4 = (iWidth - AndroidUtilities.m1081dp(240.0f)) / 2;
            int iM1081dp5 = iM1081dp2 + AndroidUtilities.m1081dp(232.0f);
            progressPaint.setColor(-10327179);
            progressPaint.setAlpha((int) (this.animatedAlphaValue * 255.0f));
            float f = iM1081dp5;
            canvas.drawRect(((int) (AndroidUtilities.m1081dp(240.0f) * this.animatedProgressValue)) + iM1081dp4, f, AndroidUtilities.m1081dp(240.0f) + iM1081dp4, AndroidUtilities.m1081dp(2.0f) + iM1081dp5, progressPaint);
            progressPaint.setColor(-1);
            progressPaint.setAlpha((int) (this.animatedAlphaValue * 255.0f));
            float f2 = iM1081dp4;
            canvas.drawRect(f2, f, f2 + (AndroidUtilities.m1081dp(240.0f) * this.animatedProgressValue), iM1081dp5 + AndroidUtilities.m1081dp(2.0f), progressPaint);
            updateAnimation();
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.parentView.getMeasuredWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.parentView.getMeasuredHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return this.parentView.getMeasuredWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return this.parentView.getMeasuredHeight();
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onFailedDownload(String str, boolean z) {
        checkFileExist();
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onSuccessDownload(String str) {
        setProgress(1.0f, true);
        checkFileExist();
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressDownload(String str, long j, long j2) {
        if (!this.progressVisible) {
            checkFileExist();
        }
        setProgress(Math.min(1.0f, j / j2), true);
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public int getObserverTag() {
        return this.TAG;
    }

    @Override // org.telegram.p026ui.Components.RecyclableDrawable
    public void recycle() {
        DownloadController.getInstance(this.parentMessageObject.currentAccount).removeLoadingFileObserver(this);
        this.parentView = null;
        this.parentMessageObject = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void checkFileExist() {
        /*
            r5 = this;
            org.telegram.messenger.MessageObject r0 = r5.parentMessageObject
            r1 = 0
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L92
            org.telegram.tgnet.TLRPC$Message r0 = r0.messageOwner
            org.telegram.tgnet.TLRPC$MessageMedia r4 = r0.media
            if (r4 == 0) goto L92
            java.lang.String r0 = r0.attachPath
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L26
            java.io.File r0 = new java.io.File
            org.telegram.messenger.MessageObject r4 = r5.parentMessageObject
            org.telegram.tgnet.TLRPC$Message r4 = r4.messageOwner
            java.lang.String r4 = r4.attachPath
            r0.<init>(r4)
            boolean r0 = r0.exists()
            if (r0 != 0) goto L45
        L26:
            int r0 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.FileLoader r0 = org.telegram.messenger.FileLoader.getInstance(r0)
            org.telegram.messenger.MessageObject r4 = r5.parentMessageObject
            org.telegram.tgnet.TLRPC$Message r4 = r4.messageOwner
            java.io.File r0 = r0.getPathToMessage(r4)
            boolean r0 = r0.exists()
            if (r0 != 0) goto L45
            org.telegram.messenger.MessageObject r0 = r5.parentMessageObject
            org.telegram.tgnet.TLRPC$Document r0 = r0.getDocument()
            java.lang.String r0 = org.telegram.messenger.FileLoader.getAttachFileName(r0)
            goto L46
        L45:
            r0 = 0
        L46:
            r5.loaded = r3
            if (r0 != 0) goto L5c
            r5.progressVisible = r3
            r5.loading = r3
            r5.loaded = r2
            org.telegram.messenger.MessageObject r0 = r5.parentMessageObject
            int r0 = r0.currentAccount
            org.telegram.messenger.DownloadController r0 = org.telegram.messenger.DownloadController.getInstance(r0)
            r0.removeLoadingFileObserver(r5)
            goto La6
        L5c:
            org.telegram.messenger.MessageObject r4 = r5.parentMessageObject
            int r4 = r4.currentAccount
            org.telegram.messenger.DownloadController r4 = org.telegram.messenger.DownloadController.getInstance(r4)
            r4.addLoadingFileObserver(r0, r5)
            org.telegram.messenger.MessageObject r4 = r5.parentMessageObject
            int r4 = r4.currentAccount
            org.telegram.messenger.FileLoader r4 = org.telegram.messenger.FileLoader.getInstance(r4)
            boolean r4 = r4.isLoadingFile(r0)
            r5.loading = r4
            if (r4 == 0) goto L8f
            r5.progressVisible = r2
            org.telegram.messenger.ImageLoader r2 = org.telegram.messenger.ImageLoader.getInstance()
            java.lang.Float r0 = r2.getFileProgress(r0)
            if (r0 != 0) goto L87
            java.lang.Float r0 = java.lang.Float.valueOf(r1)
        L87:
            float r0 = r0.floatValue()
            r5.setProgress(r0, r3)
            goto La6
        L8f:
            r5.progressVisible = r3
            goto La6
        L92:
            r5.loading = r3
            r5.loaded = r2
            r5.progressVisible = r3
            r5.setProgress(r1, r3)
            org.telegram.messenger.MessageObject r0 = r5.parentMessageObject
            int r0 = r0.currentAccount
            org.telegram.messenger.DownloadController r0 = org.telegram.messenger.DownloadController.getInstance(r0)
            r0.removeLoadingFileObserver(r5)
        La6:
            android.view.View r0 = r5.parentView
            r0.invalidate()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p026ui.Components.OtherDocumentPlaceholderDrawable.checkFileExist():void");
    }

    private void updateAnimation() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = jCurrentTimeMillis;
        float f = this.animatedProgressValue;
        if (f != 1.0f) {
            float f2 = this.currentProgress;
            if (f != f2) {
                float f3 = this.animationProgressStart;
                float f4 = f2 - f3;
                if (f4 > 0.0f) {
                    long j2 = this.currentProgressTime + j;
                    this.currentProgressTime = j2;
                    if (j2 >= 300) {
                        this.animatedProgressValue = f2;
                        this.animationProgressStart = f2;
                        this.currentProgressTime = 0L;
                    } else {
                        this.animatedProgressValue = f3 + (f4 * decelerateInterpolator.getInterpolation(j2 / 300.0f));
                    }
                }
                this.parentView.invalidate();
            }
        }
        float f5 = this.animatedProgressValue;
        if (f5 < 1.0f || f5 != 1.0f) {
            return;
        }
        float f6 = this.animatedAlphaValue;
        if (f6 != 0.0f) {
            float f7 = f6 - (j / 200.0f);
            this.animatedAlphaValue = f7;
            if (f7 <= 0.0f) {
                this.animatedAlphaValue = 0.0f;
            }
            this.parentView.invalidate();
        }
    }

    public void setProgress(float f, boolean z) {
        if (!z) {
            this.animatedProgressValue = f;
            this.animationProgressStart = f;
        } else {
            this.animationProgressStart = this.animatedProgressValue;
        }
        this.progress = String.format("%d%%", Integer.valueOf((int) (100.0f * f)));
        if (f != 1.0f) {
            this.animatedAlphaValue = 1.0f;
        }
        this.currentProgress = f;
        this.currentProgressTime = 0L;
        this.lastUpdateTime = System.currentTimeMillis();
        this.parentView.invalidate();
    }
}
