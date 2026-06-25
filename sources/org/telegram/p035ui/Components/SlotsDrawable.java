package org.telegram.p035ui.Components;

import android.graphics.Bitmap;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.DispatchQueuePoolBackground;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.Cells.ChatMessageCell;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class SlotsDrawable extends RLottieDrawable {
    private ReelValue center;
    private final int[] frameCounts;
    private final int[] frameNums;
    private ReelValue left;
    private final long[] nativePtrs;
    private boolean playWinAnimation;
    private ReelValue right;
    private final int[] secondFrameCounts;
    private final int[] secondFrameNums;
    private final long[] secondNativePtrs;

    public enum ReelValue {
        bar,
        berries,
        lemon,
        seven,
        sevenWin
    }

    public SlotsDrawable(String str, int i, int i2) {
        super(str, i, i2);
        this.nativePtrs = new long[5];
        this.frameCounts = new int[5];
        this.frameNums = new int[5];
        this.secondNativePtrs = new long[3];
        this.secondFrameCounts = new int[3];
        this.secondFrameNums = new int[3];
        this.loadFrameRunnable = new Runnable() { // from class: org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        int frame;
        if (this.isRecycled) {
            return;
        }
        if (this.nativePtr == 0 || (this.isDice == 2 && this.secondNativePtr == 0)) {
            CountDownLatch countDownLatch = this.frameWaitSync;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            RLottieDrawable.uiHandler.post(this.uiRunnableNoFrame);
            return;
        }
        if (this.backgroundBitmap == null) {
            try {
                this.backgroundBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
            } catch (Throwable th) {
                FileLog.m1048e(th);
            }
        }
        if (this.backgroundBitmap != null) {
            try {
                if (this.isDice == 1) {
                    frame = -1;
                    int i = 0;
                    while (true) {
                        long[] jArr = this.nativePtrs;
                        if (i >= jArr.length) {
                            break;
                        }
                        frame = RLottieNative.getFrame(jArr[i], this.frameNums[i], this.backgroundBitmap, i == 0);
                        if (i != 0) {
                            int[] iArr = this.frameNums;
                            int i2 = iArr[i];
                            if (i2 + 1 < this.frameCounts[i]) {
                                iArr[i] = i2 + 1;
                            } else if (i != 4) {
                                iArr[i] = 0;
                                this.nextFrameIsLast = false;
                                if (this.secondNativePtr != 0) {
                                    this.isDice = 2;
                                }
                            }
                        }
                        i++;
                    }
                } else {
                    if (this.setLastFrame) {
                        int i3 = 0;
                        while (true) {
                            int[] iArr2 = this.secondFrameNums;
                            if (i3 >= iArr2.length) {
                                break;
                            }
                            iArr2[i3] = this.secondFrameCounts[i3] - 1;
                            i3++;
                        }
                    }
                    if (this.playWinAnimation) {
                        int[] iArr3 = this.frameNums;
                        int i4 = iArr3[0];
                        if (i4 + 1 < this.frameCounts[0]) {
                            iArr3[0] = i4 + 1;
                        } else {
                            iArr3[0] = -1;
                        }
                    }
                    RLottieNative.getFrame(this.nativePtrs[0], Math.max(this.frameNums[0], 0), this.backgroundBitmap, true);
                    int i5 = 0;
                    while (true) {
                        long[] jArr2 = this.secondNativePtrs;
                        if (i5 >= jArr2.length) {
                            break;
                        }
                        long j = jArr2[i5];
                        int i6 = this.secondFrameNums[i5];
                        if (i6 < 0) {
                            i6 = this.secondFrameCounts[i5] - 1;
                        }
                        RLottieNative.getFrame(j, i6, this.backgroundBitmap, false);
                        if (!this.nextFrameIsLast) {
                            int[] iArr4 = this.secondFrameNums;
                            int i7 = iArr4[i5];
                            if (i7 + 1 < this.secondFrameCounts[i5]) {
                                iArr4[i5] = i7 + 1;
                            } else {
                                iArr4[i5] = -1;
                            }
                        }
                        i5++;
                    }
                    frame = RLottieNative.getFrame(this.nativePtrs[4], this.frameNums[4], this.backgroundBitmap, false);
                    int[] iArr5 = this.frameNums;
                    int i8 = iArr5[4];
                    if (i8 + 1 < this.frameCounts[4]) {
                        iArr5[4] = i8 + 1;
                    }
                    int[] iArr6 = this.secondFrameNums;
                    if (iArr6[0] == -1 && iArr6[1] == -1 && iArr6[2] == -1) {
                        this.nextFrameIsLast = true;
                        this.autoRepeatPlayCount++;
                    }
                    ReelValue reelValue = this.left;
                    ReelValue reelValue2 = this.right;
                    if (reelValue == reelValue2 && reelValue2 == this.center) {
                        if (this.secondFrameNums[0] == this.secondFrameCounts[0] - 100) {
                            this.playWinAnimation = true;
                            if (reelValue == ReelValue.sevenWin) {
                                WeakReference<Runnable> weakReference = this.onFinishCallback;
                                Runnable runnable = weakReference == null ? null : weakReference.get();
                                if (runnable != null) {
                                    AndroidUtilities.runOnUIThread(runnable);
                                }
                            }
                        }
                    } else {
                        this.frameNums[0] = -1;
                    }
                }
                if (frame == -1) {
                    RLottieDrawable.uiHandler.post(this.uiRunnableNoFrame);
                    CountDownLatch countDownLatch2 = this.frameWaitSync;
                    if (countDownLatch2 != null) {
                        countDownLatch2.countDown();
                        return;
                    }
                    return;
                }
                this.nextRenderingBitmap = this.backgroundBitmap;
            } catch (Exception e) {
                FileLog.m1048e(e);
            }
        }
        RLottieDrawable.uiHandler.post(this.uiRunnable);
        CountDownLatch countDownLatch3 = this.frameWaitSync;
        if (countDownLatch3 != null) {
            countDownLatch3.countDown();
        }
    }

    private ReelValue reelValue(int i) {
        if (i == 0) {
            return ReelValue.bar;
        }
        if (i == 1) {
            return ReelValue.berries;
        }
        if (i == 2) {
            return ReelValue.lemon;
        }
        return ReelValue.seven;
    }

    private void init(int i) {
        int i2 = i - 1;
        ReelValue reelValue = reelValue(i2 & 3);
        ReelValue reelValue2 = reelValue((i2 >> 2) & 3);
        ReelValue reelValue3 = reelValue(i2 >> 4);
        ReelValue reelValue4 = ReelValue.seven;
        if (reelValue == reelValue4 && reelValue2 == reelValue4 && reelValue3 == reelValue4) {
            reelValue = ReelValue.sevenWin;
            reelValue3 = reelValue;
            reelValue2 = reelValue3;
        }
        this.left = reelValue;
        this.center = reelValue2;
        this.right = reelValue3;
    }

    public boolean setBaseDice(final ChatMessageCell chatMessageCell, final TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
        if (this.nativePtr == 0 && !this.loadingInBackground) {
            this.loadingInBackground = true;
            final MessageObject messageObject = chatMessageCell.getMessageObject();
            final int i = chatMessageCell.getMessageObject().currentAccount;
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setBaseDice$5(tL_messages_stickerSet, i, messageObject, chatMessageCell);
                }
            });
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$setBaseDice$5(org.telegram.tgnet.TLRPC.TL_messages_stickerSet r12, int r13, org.telegram.messenger.MessageObject r14, org.telegram.p035ui.Cells.ChatMessageCell r15) {
        /*
            r11 = this;
            boolean r0 = r11.destroyAfterLoading
            if (r0 == 0) goto Ld
            org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda8 r12 = new org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda8
            r12.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r12)
            return
        Ld:
            r0 = 0
            r1 = r0
            r2 = r1
        L10:
            long[] r3 = r11.nativePtrs
            int r4 = r3.length
            if (r1 >= r4) goto L8c
            r4 = r3[r1]
            r6 = 0
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 == 0) goto L23
        L1d:
            r10 = r12
            r7 = r13
            r8 = r14
            r9 = r15
            goto L85
        L23:
            r3 = 1
            if (r1 != 0) goto L28
            r4 = r3
            goto L38
        L28:
            if (r1 != r3) goto L2d
            r4 = 8
            goto L38
        L2d:
            r4 = 2
            if (r1 != r4) goto L33
            r4 = 14
            goto L38
        L33:
            r5 = 3
            if (r1 != r5) goto L38
            r4 = 20
        L38:
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r5 = r12.documents
            int r5 = r5.size()
            if (r4 < r5) goto L41
            goto L1d
        L41:
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r5 = r12.documents
            java.lang.Object r4 = r5.get(r4)
            r6 = r4
            org.telegram.tgnet.TLRPC$Document r6 = (org.telegram.tgnet.TLRPC.Document) r6
            int r4 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.FileLoader r4 = org.telegram.messenger.FileLoader.getInstance(r4)
            java.io.File r4 = r4.getPathToAttach(r6, r3)
            java.lang.String r4 = org.telegram.messenger.AndroidUtilities.readRes(r4, r0)
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 == 0) goto L6c
            org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda9 r5 = new org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda9
            r10 = r12
            r7 = r13
            r8 = r14
            r9 = r15
            r5.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r5)
            r2 = r3
            goto L85
        L6c:
            r10 = r12
            r7 = r13
            r8 = r14
            r9 = r15
            long[] r12 = r11.nativePtrs
            int[] r13 = r11.metaData
            r14 = 0
            java.lang.String r15 = "dice"
            long r13 = org.telegram.p035ui.Components.RLottieNative.createWithJson(r4, r15, r13, r14)
            r12[r1] = r13
            int[] r12 = r11.frameCounts
            int[] r13 = r11.metaData
            r13 = r13[r0]
            r12[r1] = r13
        L85:
            int r1 = r1 + 1
            r13 = r7
            r14 = r8
            r15 = r9
            r12 = r10
            goto L10
        L8c:
            r7 = r13
            r9 = r15
            if (r2 == 0) goto L99
            org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda10 r12 = new org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda10
            r12.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r12)
            return
        L99:
            org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda11 r12 = new org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda11
            r12.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.SlotsDrawable.lambda$setBaseDice$5(org.telegram.tgnet.TLRPC$TL_messages_stickerSet, int, org.telegram.messenger.MessageObject, org.telegram.ui.Cells.ChatMessageCell):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBaseDice$1() {
        this.loadingInBackground = false;
        if (this.secondLoadingInBackground || !this.destroyAfterLoading) {
            return;
        }
        recycle(true);
    }

    /* JADX INFO: renamed from: $r8$lambda$CRSx3-qZRDIU7oRxYGW02aqy5aI, reason: not valid java name */
    public static /* synthetic */ void m14288$r8$lambda$CRSx3qZRDIU7oRxYGW02aqy5aI(TLRPC.Document document, int i, MessageObject messageObject, ChatMessageCell chatMessageCell, TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
        DownloadController.getInstance(i).addLoadingFileObserver(FileLoader.getAttachFileName(document), messageObject, chatMessageCell);
        FileLoader.getInstance(i).loadFile(document, tL_messages_stickerSet, 1, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBaseDice$3() {
        this.loadingInBackground = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBaseDice$4(int i, ChatMessageCell chatMessageCell) {
        this.loadingInBackground = false;
        if (!this.secondLoadingInBackground && this.destroyAfterLoading) {
            recycle(true);
            return;
        }
        this.nativePtr = this.nativePtrs[0];
        DownloadController.getInstance(i).removeLoadingFileObserver(chatMessageCell);
        this.timeBetweenFrames = Math.max(16, (int) (1000.0f / this.metaData[1]));
        scheduleNextGetFrame();
        invalidateInternal();
    }

    public boolean setDiceNumber(final ChatMessageCell chatMessageCell, int i, final TLRPC.TL_messages_stickerSet tL_messages_stickerSet, final boolean z) {
        if (this.secondNativePtr == 0 && !this.secondLoadingInBackground) {
            init(i);
            final MessageObject messageObject = chatMessageCell.getMessageObject();
            final int i2 = chatMessageCell.getMessageObject().currentAccount;
            this.secondLoadingInBackground = true;
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setDiceNumber$10(tL_messages_stickerSet, i2, messageObject, chatMessageCell, z);
                }
            });
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$setDiceNumber$10(org.telegram.tgnet.TLRPC.TL_messages_stickerSet r18, final int r19, final org.telegram.messenger.MessageObject r20, final org.telegram.p035ui.Cells.ChatMessageCell r21, final boolean r22) {
        /*
            Method dump skipped, instruction units count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.SlotsDrawable.lambda$setDiceNumber$10(org.telegram.tgnet.TLRPC$TL_messages_stickerSet, int, org.telegram.messenger.MessageObject, org.telegram.ui.Cells.ChatMessageCell, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDiceNumber$6() {
        this.secondLoadingInBackground = false;
        if (this.loadingInBackground || !this.destroyAfterLoading) {
            return;
        }
        recycle(true);
    }

    public static /* synthetic */ void $r8$lambda$v9j9XcI5TcfNL8uE2UypDmLKDxI(TLRPC.Document document, int i, MessageObject messageObject, ChatMessageCell chatMessageCell, TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
        DownloadController.getInstance(i).addLoadingFileObserver(FileLoader.getAttachFileName(document), messageObject, chatMessageCell);
        FileLoader.getInstance(i).loadFile(document, tL_messages_stickerSet, 1, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDiceNumber$8() {
        this.secondLoadingInBackground = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDiceNumber$9(boolean z, int i, ChatMessageCell chatMessageCell) {
        if (z && this.nextRenderingBitmap == null && this.renderingBitmap == null && this.loadFrameTask == null) {
            this.isDice = 2;
            this.setLastFrame = true;
        }
        this.secondLoadingInBackground = false;
        if (!this.loadingInBackground && this.destroyAfterLoading) {
            recycle(true);
            return;
        }
        this.secondNativePtr = this.secondNativePtrs[0];
        DownloadController.getInstance(i).removeLoadingFileObserver(chatMessageCell);
        this.timeBetweenFrames = Math.max(16, (int) (1000.0f / this.metaData[1]));
        scheduleNextGetFrame();
        invalidateInternal();
    }

    @Override // org.telegram.p035ui.Components.RLottieDrawable
    public void recycle(boolean z) {
        recycle(z, false);
    }

    @Override // org.telegram.p035ui.Components.RLottieDrawable
    public void recycle(boolean z, boolean z2) {
        int i = 0;
        this.isRunning = false;
        this.isRecycled = true;
        if (z2) {
            this.loadingInBackground = false;
            this.secondLoadingInBackground = false;
        }
        checkRunningTasks();
        if (this.loadingInBackground || this.secondLoadingInBackground) {
            this.destroyAfterLoading = true;
            return;
        }
        if (this.loadFrameTask == null && this.cacheGenerateTask == null) {
            final ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (true) {
                long[] jArr = this.nativePtrs;
                if (i2 >= jArr.length) {
                    break;
                }
                long j = jArr[i2];
                if (j != 0) {
                    if (j == this.nativePtr) {
                        this.nativePtr = 0L;
                    }
                    arrayList.add(Long.valueOf(this.nativePtrs[i2]));
                    this.nativePtrs[i2] = 0;
                }
                i2++;
            }
            while (true) {
                long[] jArr2 = this.secondNativePtrs;
                if (i >= jArr2.length) {
                    break;
                }
                long j2 = jArr2[i];
                if (j2 != 0) {
                    if (j2 == this.secondNativePtr) {
                        this.secondNativePtr = 0L;
                    }
                    arrayList.add(Long.valueOf(this.secondNativePtrs[i]));
                    this.secondNativePtrs[i] = 0;
                }
                i++;
            }
            this.nativePtr = 0L;
            this.secondNativePtr = 0L;
            if (!arrayList.isEmpty()) {
                Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.SlotsDrawable$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SlotsDrawable.$r8$lambda$5UvxyCjhDQqIkvOWk06OuvFfr84(arrayList);
                    }
                };
                if (z) {
                    DispatchQueuePoolBackground.execute(runnable);
                } else {
                    Utilities.globalQueue.postRunnable(runnable);
                }
            }
            recycleResources();
            return;
        }
        this.destroyWhenDone = true;
    }

    public static /* synthetic */ void $r8$lambda$5UvxyCjhDQqIkvOWk06OuvFfr84(ArrayList arrayList) {
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            RLottieNative.destroy(((Long) obj).longValue());
        }
    }

    @Override // org.telegram.p035ui.Components.RLottieDrawable
    public void decodeFrameFinishedInternal() {
        if (this.destroyWhenDone) {
            checkRunningTasks();
            if (this.loadFrameTask == null && this.cacheGenerateTask == null) {
                int i = 0;
                int i2 = 0;
                while (true) {
                    long[] jArr = this.nativePtrs;
                    if (i2 >= jArr.length) {
                        break;
                    }
                    long j = jArr[i2];
                    if (j != 0) {
                        RLottieNative.destroy(j);
                        this.nativePtrs[i2] = 0;
                    }
                    i2++;
                }
                while (true) {
                    long[] jArr2 = this.secondNativePtrs;
                    if (i >= jArr2.length) {
                        break;
                    }
                    long j2 = jArr2[i];
                    if (j2 != 0) {
                        RLottieNative.destroy(j2);
                        this.secondNativePtrs[i] = 0;
                    }
                    i++;
                }
            }
        }
        if (this.nativePtr == 0 && this.secondNativePtr == 0) {
            recycleResources();
            return;
        }
        this.waitingForNextTask = true;
        if (!hasParentView()) {
            stop();
        }
        scheduleNextGetFrame();
    }
}
