package org.telegram.p035ui.Components.poll.attached;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.WebFile;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.ClipRoundedDrawable;
import org.telegram.p035ui.Components.CubicBezierInterpolator;
import org.telegram.p035ui.Components.poll.PollAttachedMedia;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class PollAttachedMediaLocation extends PollAttachedMedia {
    public final TLRPC.MessageMedia media;

    public PollAttachedMediaLocation(TLRPC.MessageMedia messageMedia) {
        this.media = messageMedia;
        this.imageReceiver.setRoundRadius(AndroidUtilities.m1036dp(7.0f));
        setupImageReceiver(this.imageReceiver);
    }

    private void setupImageReceiver(ImageReceiver imageReceiver) {
        TLRPC.GeoPoint geoPoint = this.media.geo;
        if (geoPoint == null) {
            imageReceiver.clearImage();
        } else {
            imageReceiver.setImage(ImageLocation.getForWebFile(WebFile.createWithGeoPoint(geoPoint, 38, 38, 13, Math.min(2, (int) Math.ceil(AndroidUtilities.density)))), (String) null, (ImageLocation) null, (String) null, (Drawable) null, (Object) null, 0);
        }
    }

    @Override // org.telegram.p035ui.Components.poll.PollAttachedMedia
    public void draw(Canvas canvas, int i, int i2) {
        this.imageReceiver.setImageCoords(0.0f, 0.0f, i, i2);
        this.imageReceiver.draw(canvas);
    }

    public Drawable createMessagePreviewDrawable(View view) {
        ImageReceiver imageReceiver = new ImageReceiver(view);
        SvgHelper.SvgDrawable svgThumb = DocumentObject.getSvgThumb(C2797R.raw.map_placeholder, Theme.key_chat_outLocationIcon, (Theme.isCurrentThemeDark() ? 3 : 6) * 0.12f);
        svgThumb.setAspectCenter(true);
        svgThumb.setColorKey(Theme.key_chat_inLocationIcon);
        imageReceiver.setImage(ImageLocation.getForWebFile(WebFile.createWithGeoPoint(this.media.geo, 300, 168, 15, Math.min(2, (int) Math.ceil(AndroidUtilities.density)))), (String) null, (ImageLocation) null, (String) null, new ClipRoundedDrawable(svgThumb), (Object) null, 0);
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: org.telegram.ui.Components.poll.attached.PollAttachedMediaLocation.1
            final /* synthetic */ ImageReceiver val$imageReceiver;

            public ViewOnAttachStateChangeListenerC53491(ImageReceiver imageReceiver2) {
                imageReceiver = imageReceiver2;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                imageReceiver.onAttachedToWindow();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
                imageReceiver.onDetachedFromWindow();
            }
        });
        imageReceiver2.setRoundRadius(AndroidUtilities.m1036dp(14.0f));
        return new Drawable() { // from class: org.telegram.ui.Components.poll.attached.PollAttachedMediaLocation.2
            final /* synthetic */ ImageReceiver val$imageReceiver;
            final /* synthetic */ Drawable val$redLocationIcon;

            @Override // android.graphics.drawable.Drawable
            public int getOpacity() {
                return 0;
            }

            @Override // android.graphics.drawable.Drawable
            public void setColorFilter(ColorFilter colorFilter) {
            }

            public C53502(ImageReceiver imageReceiver2, Drawable drawable) {
                imageReceiver = imageReceiver2;
                drawable = drawable;
            }

            @Override // android.graphics.drawable.Drawable
            public void draw(Canvas canvas) {
                imageReceiver.draw(canvas);
                int intrinsicWidth = (int) (drawable.getIntrinsicWidth() * 0.8f);
                int intrinsicHeight = (int) (drawable.getIntrinsicHeight() * 0.8f);
                int imageX = (int) (imageReceiver.getImageX() + ((imageReceiver.getImageWidth() - intrinsicWidth) / 2.0f));
                int imageY = (int) ((imageReceiver.getImageY() + ((imageReceiver.getImageHeight() / 2.0f) - intrinsicHeight)) - (AndroidUtilities.m1036dp(16.0f) * (1.0f - CubicBezierInterpolator.EASE_OUT_BACK.getInterpolation(imageReceiver.getCurrentAlpha()))));
                drawable.setAlpha((int) (Math.min(1.0f, imageReceiver.getCurrentAlpha() * 5.0f) * 255.0f * imageReceiver.getAlpha()));
                drawable.setBounds(imageX, imageY, intrinsicWidth + imageX, intrinsicHeight + imageY);
                drawable.draw(canvas);
            }

            @Override // android.graphics.drawable.Drawable
            public void setAlpha(int i) {
                imageReceiver.setAlpha(i / 255.0f);
            }

            @Override // android.graphics.drawable.Drawable
            public int getAlpha() {
                return (int) (imageReceiver.getAlpha() * 255.0f);
            }

            @Override // android.graphics.drawable.Drawable
            public void onBoundsChange(Rect rect) {
                imageReceiver.setImageCoords(rect.left + AndroidUtilities.m1036dp(2.0f), rect.top + AndroidUtilities.m1036dp(2.0f), (rect.right - AndroidUtilities.m1036dp(2.0f)) - (rect.left + AndroidUtilities.m1036dp(2.0f)), (rect.bottom - AndroidUtilities.m1036dp(2.0f)) - (rect.top + AndroidUtilities.m1036dp(2.0f)));
            }
        };
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.poll.attached.PollAttachedMediaLocation$1 */
    public class ViewOnAttachStateChangeListenerC53491 implements View.OnAttachStateChangeListener {
        final /* synthetic */ ImageReceiver val$imageReceiver;

        public ViewOnAttachStateChangeListenerC53491(ImageReceiver imageReceiver2) {
            imageReceiver = imageReceiver2;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view2) {
            imageReceiver.onAttachedToWindow();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view2) {
            imageReceiver.onDetachedFromWindow();
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.Components.poll.attached.PollAttachedMediaLocation$2 */
    public class C53502 extends Drawable {
        final /* synthetic */ ImageReceiver val$imageReceiver;
        final /* synthetic */ Drawable val$redLocationIcon;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public C53502(ImageReceiver imageReceiver2, Drawable drawable) {
            imageReceiver = imageReceiver2;
            drawable = drawable;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            imageReceiver.draw(canvas);
            int intrinsicWidth = (int) (drawable.getIntrinsicWidth() * 0.8f);
            int intrinsicHeight = (int) (drawable.getIntrinsicHeight() * 0.8f);
            int imageX = (int) (imageReceiver.getImageX() + ((imageReceiver.getImageWidth() - intrinsicWidth) / 2.0f));
            int imageY = (int) ((imageReceiver.getImageY() + ((imageReceiver.getImageHeight() / 2.0f) - intrinsicHeight)) - (AndroidUtilities.m1036dp(16.0f) * (1.0f - CubicBezierInterpolator.EASE_OUT_BACK.getInterpolation(imageReceiver.getCurrentAlpha()))));
            drawable.setAlpha((int) (Math.min(1.0f, imageReceiver.getCurrentAlpha() * 5.0f) * 255.0f * imageReceiver.getAlpha()));
            drawable.setBounds(imageX, imageY, intrinsicWidth + imageX, intrinsicHeight + imageY);
            drawable.draw(canvas);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            imageReceiver.setAlpha(i / 255.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public int getAlpha() {
            return (int) (imageReceiver.getAlpha() * 255.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public void onBoundsChange(Rect rect) {
            imageReceiver.setImageCoords(rect.left + AndroidUtilities.m1036dp(2.0f), rect.top + AndroidUtilities.m1036dp(2.0f), (rect.right - AndroidUtilities.m1036dp(2.0f)) - (rect.left + AndroidUtilities.m1036dp(2.0f)), (rect.bottom - AndroidUtilities.m1036dp(2.0f)) - (rect.top + AndroidUtilities.m1036dp(2.0f)));
        }
    }
}
