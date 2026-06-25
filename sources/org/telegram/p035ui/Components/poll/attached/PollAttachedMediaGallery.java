package org.telegram.p035ui.Components.poll.attached;

import android.graphics.Canvas;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.p035ui.Components.poll.PollAttachedMedia;

/* JADX INFO: loaded from: classes7.dex */
public class PollAttachedMediaGallery extends PollAttachedMedia {
    public final MediaController.PhotoEntry photoEntry;
    public final SendMessagesHelper.SendingMediaInfo sendingMediaInfo;

    public PollAttachedMediaGallery(SendMessagesHelper.SendingMediaInfo sendingMediaInfo) {
        this.sendingMediaInfo = sendingMediaInfo;
        this.photoEntry = sendingMediaInfo.originalPhotoEntry;
        this.imageReceiver.setRoundRadius(AndroidUtilities.m1036dp(7.0f));
        setupImageReceiver(this.imageReceiver);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setupImageReceiver(org.telegram.messenger.ImageReceiver r8) {
        /*
            r7 = this;
            r0 = 0
            r1 = 1
            r8.setOrientation(r0, r1)
            org.telegram.messenger.MediaController$PhotoEntry r0 = r7.photoEntry
            java.lang.String r2 = r0.coverPath
            if (r2 == 0) goto L11
            org.telegram.messenger.ImageLocation r7 = org.telegram.messenger.ImageLocation.getForPath(r2)
        Lf:
            r1 = r7
            goto L7b
        L11:
            java.lang.String r2 = r0.thumbPath
            if (r2 == 0) goto L1a
            org.telegram.messenger.ImageLocation r7 = org.telegram.messenger.ImageLocation.getForPath(r2)
            goto Lf
        L1a:
            java.lang.String r2 = r0.path
            if (r2 == 0) goto L76
            boolean r2 = r0.isVideo
            java.lang.String r3 = ":"
            if (r2 == 0) goto L4b
            boolean r0 = r0.isLivePhoto()
            if (r0 != 0) goto L4b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "vthumb://"
            r0.<init>(r1)
            org.telegram.messenger.MediaController$PhotoEntry r1 = r7.photoEntry
            int r1 = r1.imageId
            r0.append(r1)
            r0.append(r3)
            org.telegram.messenger.MediaController$PhotoEntry r7 = r7.photoEntry
            java.lang.String r7 = r7.path
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            org.telegram.messenger.ImageLocation r7 = org.telegram.messenger.ImageLocation.getForPath(r7)
            goto Lf
        L4b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "thumb://"
            r0.<init>(r2)
            org.telegram.messenger.MediaController$PhotoEntry r2 = r7.photoEntry
            int r2 = r2.imageId
            r0.append(r2)
            r0.append(r3)
            org.telegram.messenger.MediaController$PhotoEntry r2 = r7.photoEntry
            java.lang.String r2 = r2.path
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            org.telegram.messenger.ImageLocation r0 = org.telegram.messenger.ImageLocation.getForPath(r0)
            org.telegram.messenger.MediaController$PhotoEntry r7 = r7.photoEntry
            int r2 = r7.orientation
            int r7 = r7.invert
            r8.setOrientation(r2, r7, r1)
            r1 = r0
            goto L7b
        L76:
            r8.clearImage()
            r7 = 0
            goto Lf
        L7b:
            if (r1 == 0) goto L87
            r5 = 0
            r6 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r0 = r8
            r0.setImage(r1, r2, r3, r4, r5, r6)
            return
        L87:
            r0 = r8
            r0.clearImage()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.poll.attached.PollAttachedMediaGallery.setupImageReceiver(org.telegram.messenger.ImageReceiver):void");
    }

    @Override // org.telegram.p035ui.Components.poll.PollAttachedMedia
    public void draw(Canvas canvas, int i, int i2) {
        this.imageReceiver.setImageCoords(0.0f, 0.0f, i, i2);
        this.imageReceiver.draw(canvas);
    }
}
