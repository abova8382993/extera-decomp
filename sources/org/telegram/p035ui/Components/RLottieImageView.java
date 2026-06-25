package org.telegram.p035ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.Map;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.SvgHelper;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes3.dex */
public class RLottieImageView extends ImageView {
    private boolean attachedToWindow;
    private boolean autoRepeat;
    private int autoRepeatCount;
    public boolean cached;
    private RLottieDrawable drawable;
    private ImageReceiver imageReceiver;
    private HashMap<String, Integer> layerColors;
    private Integer layerNum;
    private boolean onlyLastFrame;
    private boolean playing;
    private boolean startOnAttach;

    public void onLoaded() {
    }

    public RLottieImageView(Context context) {
        super(context);
        this.autoRepeatCount = -1;
    }

    public void clearLayerColors() {
        this.layerColors.clear();
    }

    public void setLayerNum(Integer num) {
        this.layerNum = num;
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            imageReceiver.setLayerNum(num.intValue());
        }
    }

    public void setLayerColor(String str, int i) {
        if (this.layerColors == null) {
            this.layerColors = new HashMap<>();
        }
        this.layerColors.put(str, Integer.valueOf(i));
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.setLayerColor(str, i);
        }
    }

    public void replaceColors(int[] iArr) {
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.replaceColors(iArr);
        }
    }

    public void setAnimation(int i, int i2, int i3) {
        setAnimation(i, i2, i3, null);
    }

    public void setAnimation(int i, int i2, int i3, int[] iArr) {
        setAnimation(new RLottieDrawable(i, _UrlKt.FRAGMENT_ENCODE_SET + i, AndroidUtilities.m1036dp(i2), AndroidUtilities.m1036dp(i3), false, iArr));
    }

    public void setOnAnimationEndListener(Runnable runnable) {
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.setOnAnimationEndListener(runnable);
        }
    }

    public void setAnimation(RLottieDrawable rLottieDrawable) {
        if (this.drawable == rLottieDrawable) {
            return;
        }
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            imageReceiver.onDetachedFromWindow();
            this.imageReceiver = null;
        }
        this.drawable = rLottieDrawable;
        rLottieDrawable.setMasterParent(this);
        if (this.autoRepeat) {
            this.drawable.setAutoRepeat(1);
        }
        int i = this.autoRepeatCount;
        if (i != -1) {
            this.drawable.setAutoRepeatCount(i);
        }
        if (this.layerColors != null) {
            this.drawable.beginApplyLayerColors();
            for (Map.Entry<String, Integer> entry : this.layerColors.entrySet()) {
                this.drawable.setLayerColor(entry.getKey(), entry.getValue().intValue());
            }
            this.drawable.commitApplyLayerColors();
        }
        this.drawable.setAllowDecodeSingleFrame(true);
        setImageDrawable(this.drawable);
    }

    public void setOnlyLastFrame(boolean z) {
        this.onlyLastFrame = z;
    }

    public void setAnimation(TLRPC.Document document, int i, int i2) {
        ImageLocation forDocument;
        String str;
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            imageReceiver.onDetachedFromWindow();
            this.imageReceiver = null;
        }
        if (document == null) {
            return;
        }
        C48401 c48401 = new ImageReceiver() { // from class: org.telegram.ui.Components.RLottieImageView.1
            public C48401() {
            }

            @Override // org.telegram.messenger.ImageReceiver
            public boolean setImageBitmapByKey(Drawable drawable, String str2, int i3, boolean z, int i4) {
                if (drawable != null) {
                    RLottieImageView.this.onLoaded();
                }
                return super.setImageBitmapByKey(drawable, str2, i3, z, i4);
            }
        };
        this.imageReceiver = c48401;
        c48401.setAllowLoadingOnAttachedOnly(true);
        String str2 = document.localThumbPath;
        if (str2 != null) {
            str = i + "_" + i2;
            forDocument = ImageLocation.getForPath(str2);
        } else {
            forDocument = null;
            str = null;
        }
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 90);
        if (this.onlyLastFrame) {
            this.imageReceiver.setImage(ImageLocation.getForDocument(document), i + "_" + i2 + "_lastframe", ImageLocation.getForDocument(closestPhotoSizeWithSize, document), i + "_" + i2, forDocument, str, null, 0L, null, document, 1);
        } else {
            boolean zEquals = "video/webm".equals(document.mime_type);
            String str3 = _UrlKt.FRAGMENT_ENCODE_SET;
            if (zEquals) {
                ImageReceiver imageReceiver2 = this.imageReceiver;
                ImageLocation forDocument2 = ImageLocation.getForDocument(document);
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append("_");
                sb.append(i2);
                if (this.cached) {
                    str3 = "_pcache";
                }
                sb.append(str3);
                sb.append("_g");
                String string = sb.toString();
                if (forDocument == null) {
                    forDocument = ImageLocation.getForDocument(closestPhotoSizeWithSize, document);
                }
                imageReceiver2.setImage(forDocument2, string, forDocument, str, null, document.size, null, document, 1);
            } else {
                SvgHelper.SvgDrawable svgThumb = DocumentObject.getSvgThumb(document.thumbs, Theme.key_windowBackgroundWhiteGrayIcon, 0.2f);
                if (svgThumb != null) {
                    svgThumb.overrideWidthAndHeight(512, 512);
                }
                ImageReceiver imageReceiver3 = this.imageReceiver;
                ImageLocation forDocument3 = ImageLocation.getForDocument(document);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i);
                sb2.append("_");
                sb2.append(i2);
                if (this.cached) {
                    str3 = "_pcache";
                }
                sb2.append(str3);
                imageReceiver3.setImage(forDocument3, sb2.toString(), ImageLocation.getForDocument(closestPhotoSizeWithSize, document), i + "_" + i2, forDocument, str, svgThumb, 0L, null, document, 1);
            }
        }
        this.imageReceiver.setAspectFit(true);
        this.imageReceiver.setParentView(this);
        boolean z = this.autoRepeat;
        ImageReceiver imageReceiver4 = this.imageReceiver;
        if (z) {
            imageReceiver4.setAutoRepeat(1);
            this.imageReceiver.setAllowStartLottieAnimation(true);
            this.imageReceiver.setAllowStartAnimation(true);
        } else {
            imageReceiver4.setAutoRepeat(0);
        }
        int i3 = this.autoRepeatCount;
        if (i3 != -1) {
            this.imageReceiver.setAutoRepeatCount(i3);
        }
        ImageReceiver imageReceiver5 = this.imageReceiver;
        Integer num = this.layerNum;
        imageReceiver5.setLayerNum(num != null ? num.intValue() : 7);
        this.imageReceiver.clip = false;
        setImageDrawable(new Drawable() { // from class: org.telegram.ui.Components.RLottieImageView.2
            final /* synthetic */ int val$h;
            final /* synthetic */ int val$w;

            @Override // android.graphics.drawable.Drawable
            public int getOpacity() {
                return -2;
            }

            public C48412(int i4, int i22) {
                i = i4;
                i = i22;
            }

            @Override // android.graphics.drawable.Drawable
            public void draw(Canvas canvas) {
                Rect rect = AndroidUtilities.rectTmp2;
                rect.set(getBounds().centerX() - (AndroidUtilities.m1036dp(i) / 2), getBounds().centerY() - (AndroidUtilities.m1036dp(i) / 2), getBounds().centerX() + (AndroidUtilities.m1036dp(i) / 2), getBounds().centerY() + (AndroidUtilities.m1036dp(i) / 2));
                RLottieImageView.this.imageReceiver.setImageCoords(rect);
                RLottieImageView.this.imageReceiver.draw(canvas);
            }

            @Override // android.graphics.drawable.Drawable
            public void setAlpha(int i4) {
                RLottieImageView.this.imageReceiver.setAlpha(i4 / 255.0f);
            }

            @Override // android.graphics.drawable.Drawable
            public void setColorFilter(ColorFilter colorFilter) {
                RLottieImageView.this.imageReceiver.setColorFilter(colorFilter);
            }
        });
        if (this.attachedToWindow) {
            this.imageReceiver.onAttachedToWindow();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.RLottieImageView$1 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C48401 extends ImageReceiver {
        public C48401() {
        }

        @Override // org.telegram.messenger.ImageReceiver
        public boolean setImageBitmapByKey(Drawable drawable, String str2, int i3, boolean z, int i4) {
            if (drawable != null) {
                RLottieImageView.this.onLoaded();
            }
            return super.setImageBitmapByKey(drawable, str2, i3, z, i4);
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.RLottieImageView$2 */
    /* JADX INFO: loaded from: classes7.dex */
    public class C48412 extends Drawable {
        final /* synthetic */ int val$h;
        final /* synthetic */ int val$w;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        public C48412(int i4, int i22) {
            i = i4;
            i = i22;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Rect rect = AndroidUtilities.rectTmp2;
            rect.set(getBounds().centerX() - (AndroidUtilities.m1036dp(i) / 2), getBounds().centerY() - (AndroidUtilities.m1036dp(i) / 2), getBounds().centerX() + (AndroidUtilities.m1036dp(i) / 2), getBounds().centerY() + (AndroidUtilities.m1036dp(i) / 2));
            RLottieImageView.this.imageReceiver.setImageCoords(rect);
            RLottieImageView.this.imageReceiver.draw(canvas);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i4) {
            RLottieImageView.this.imageReceiver.setAlpha(i4 / 255.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            RLottieImageView.this.imageReceiver.setColorFilter(colorFilter);
        }
    }

    public void clearAnimationDrawable() {
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.stop();
        }
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            imageReceiver.onDetachedFromWindow();
            this.imageReceiver = null;
        }
        this.drawable = null;
        setImageDrawable(null);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attachedToWindow = true;
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            imageReceiver.onAttachedToWindow();
            if (this.playing) {
                this.imageReceiver.startAnimation();
            }
        }
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.setCallback(this);
            if (this.playing) {
                this.drawable.start();
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attachedToWindow = false;
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.stop();
        }
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            imageReceiver.onDetachedFromWindow();
        }
    }

    public boolean isPlaying() {
        RLottieDrawable rLottieDrawable = this.drawable;
        return rLottieDrawable != null && rLottieDrawable.isRunning();
    }

    public void setAutoRepeat(boolean z) {
        this.autoRepeat = z;
    }

    public void setAutoRepeatCount(int i) {
        this.autoRepeatCount = i;
    }

    public void setProgress(float f) {
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.setProgress(f);
        }
    }

    public ImageReceiver getImageReceiver() {
        return this.imageReceiver;
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        super.setImageResource(i);
        this.drawable = null;
    }

    public void playAnimation() {
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable == null && this.imageReceiver == null) {
            return;
        }
        this.playing = true;
        if (this.attachedToWindow) {
            if (rLottieDrawable != null) {
                rLottieDrawable.start();
            }
            ImageReceiver imageReceiver = this.imageReceiver;
            if (imageReceiver != null) {
                imageReceiver.startAnimation();
                return;
            }
            return;
        }
        this.startOnAttach = true;
    }

    public void stopAnimation() {
        RLottieDrawable rLottieDrawable = this.drawable;
        if (rLottieDrawable == null && this.imageReceiver == null) {
            return;
        }
        this.playing = false;
        if (this.attachedToWindow) {
            if (rLottieDrawable != null) {
                rLottieDrawable.stop();
            }
            ImageReceiver imageReceiver = this.imageReceiver;
            if (imageReceiver != null) {
                imageReceiver.stopAnimation();
                return;
            }
            return;
        }
        this.startOnAttach = false;
    }

    public RLottieDrawable getAnimatedDrawable() {
        return this.drawable;
    }
}
