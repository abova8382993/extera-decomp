package org.telegram.p035ui.p036iv;

import android.graphics.Bitmap;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class MediaUploadState {
    public TLRPC.Document document;
    public int duration;
    public int height;
    public int imageId;
    public boolean isVideo;
    public String localPath;
    public Bitmap localThumbBitmap;
    public TLRPC.Photo photo;
    public float progress;
    public int state = 0;
    public String thumbPath;
    public int width;

    public boolean isReady() {
        if (this.state != 2) {
            return false;
        }
        return this.isVideo ? this.document != null : this.photo != null;
    }

    public boolean isPending() {
        return this.state == 1;
    }
}
