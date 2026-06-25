package org.telegram.messenger.video;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.video.MediaCodecVideoConvertor;
import org.telegram.p035ui.Components.AnimatedEmojiSpan;
import org.telegram.p035ui.Components.AnimatedFileDrawable;
import org.telegram.p035ui.Components.Paint.PaintTypeface;
import org.telegram.p035ui.Components.Paint.Views.EditTextOutline;
import org.telegram.p035ui.Components.RLottieNative;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes3.dex */
public class WebmEncoder {
    private static native long createEncoder(String str, int i, int i2, int i3, long j);

    public static native void stop(long j);

    private static native boolean writeFrame(long j, ByteBuffer byteBuffer, int i, int i2);

    /* JADX WARN: Removed duplicated region for block: B:120:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0142  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean convert(org.telegram.messenger.video.MediaCodecVideoConvertor.ConvertVideoParams r20, int r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.video.WebmEncoder.convert(org.telegram.messenger.video.MediaCodecVideoConvertor$ConvertVideoParams, int):boolean");
    }

    public static class FrameDrawer {

        /* JADX INFO: renamed from: H */
        private final int f1233H;

        /* JADX INFO: renamed from: W */
        private final int f1234W;
        private final Paint bitmapPaint;
        private final Paint clearPaint;
        private final Path clipPath;
        private final int fps;
        private final ArrayList<VideoEditedInfo.MediaEntity> mediaEntities;
        Path path;
        private final Bitmap photo;
        Paint textColorPaint;
        Paint xRefPaint;

        public FrameDrawer(MediaCodecVideoConvertor.ConvertVideoParams convertVideoParams) {
            ArrayList<VideoEditedInfo.MediaEntity> arrayList = new ArrayList<>();
            this.mediaEntities = arrayList;
            this.clearPaint = new Paint(1);
            this.bitmapPaint = new Paint(5);
            int i = convertVideoParams.resultWidth;
            this.f1234W = i;
            int i2 = convertVideoParams.resultHeight;
            this.f1233H = i2;
            this.fps = convertVideoParams.framerate;
            Path path = new Path();
            this.clipPath = path;
            path.addRoundRect(new RectF(0.0f, 0.0f, i, i2), i * 0.125f, i2 * 0.125f, Path.Direction.CW);
            this.photo = BitmapFactory.decodeFile(convertVideoParams.videoPath);
            arrayList.addAll(convertVideoParams.mediaEntities);
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                VideoEditedInfo.MediaEntity mediaEntity = this.mediaEntities.get(i3);
                byte b2 = mediaEntity.type;
                if (b2 == 0 || b2 == 2 || b2 == 5) {
                    initStickerEntity(mediaEntity);
                } else if (b2 == 1) {
                    initTextEntity(mediaEntity);
                }
            }
            this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }

        public void draw(Canvas canvas, int i) {
            canvas.drawPaint(this.clearPaint);
            canvas.save();
            canvas.clipPath(this.clipPath);
            Bitmap bitmap = this.photo;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            }
            long j = ((long) i) * (1000000000 / ((long) this.fps));
            int size = this.mediaEntities.size();
            for (int i2 = 0; i2 < size; i2++) {
                VideoEditedInfo.MediaEntity mediaEntity = this.mediaEntities.get(i2);
                drawEntity(canvas, mediaEntity, mediaEntity.color, j);
            }
            canvas.restore();
        }

        private void drawEntity(Canvas canvas, VideoEditedInfo.MediaEntity mediaEntity, int i, long j) {
            VideoEditedInfo.MediaEntity mediaEntity2;
            FrameDrawer frameDrawer;
            Canvas canvas2;
            long j2;
            AnimatedFileDrawable animatedFileDrawable;
            long j3 = mediaEntity.ptr;
            int i2 = 0;
            if (j3 != 0) {
                Bitmap bitmap = mediaEntity.bitmap;
                if (bitmap == null || mediaEntity.f1186W <= 0 || mediaEntity.f1185H <= 0) {
                    return;
                }
                RLottieNative.getFrame(j3, (int) mediaEntity.currentFrame, bitmap, true);
                Bitmap bitmap2 = mediaEntity.bitmap;
                if ((mediaEntity.subType & 8) == 0) {
                    i = 0;
                }
                applyRoundRadius(mediaEntity, bitmap2, i);
                canvas.drawBitmap(mediaEntity.bitmap, mediaEntity.matrix, this.bitmapPaint);
                float f = mediaEntity.currentFrame + mediaEntity.framesPerDraw;
                mediaEntity.currentFrame = f;
                if (f >= mediaEntity.metadata[0]) {
                    mediaEntity.currentFrame = 0.0f;
                    return;
                }
                return;
            }
            if (mediaEntity.animatedFileDrawable != null) {
                float f2 = mediaEntity.currentFrame;
                int i3 = (int) f2;
                float f3 = f2 + mediaEntity.framesPerDraw;
                mediaEntity.currentFrame = f3;
                int i4 = (int) f3;
                while (true) {
                    animatedFileDrawable = mediaEntity.animatedFileDrawable;
                    if (i3 == i4) {
                        break;
                    }
                    animatedFileDrawable.getNextFrame(true);
                    i4--;
                }
                Bitmap backgroundBitmap = animatedFileDrawable.getBackgroundBitmap();
                if (backgroundBitmap != null) {
                    canvas.drawBitmap(backgroundBitmap, mediaEntity.matrix, this.bitmapPaint);
                    return;
                }
                return;
            }
            canvas.drawBitmap(mediaEntity.bitmap, mediaEntity.matrix, this.bitmapPaint);
            ArrayList<VideoEditedInfo.EmojiEntity> arrayList = mediaEntity.entities;
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            while (i2 < mediaEntity.entities.size()) {
                VideoEditedInfo.EmojiEntity emojiEntity = mediaEntity.entities.get(i2);
                if (emojiEntity == null || (mediaEntity2 = emojiEntity.entity) == null) {
                    frameDrawer = this;
                    canvas2 = canvas;
                    j2 = j;
                } else {
                    frameDrawer = this;
                    canvas2 = canvas;
                    j2 = j;
                    frameDrawer.drawEntity(canvas2, mediaEntity2, mediaEntity.color, j2);
                }
                i2++;
                this = frameDrawer;
                canvas = canvas2;
                j = j2;
            }
        }

        private void initTextEntity(VideoEditedInfo.MediaEntity mediaEntity) {
            int i;
            Emoji.EmojiSpan[] emojiSpanArr;
            Typeface typeface;
            EditTextOutline editTextOutline = new EditTextOutline(ApplicationLoader.applicationContext);
            editTextOutline.getPaint().setAntiAlias(true);
            editTextOutline.drawAnimatedEmojiDrawables = false;
            editTextOutline.setBackgroundColor(0);
            editTextOutline.setPadding(AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(7.0f), AndroidUtilities.m1036dp(7.0f));
            PaintTypeface paintTypeface = mediaEntity.textTypeface;
            if (paintTypeface != null && (typeface = paintTypeface.getTypeface()) != null) {
                editTextOutline.setTypeface(typeface);
            }
            editTextOutline.setTextSize(0, mediaEntity.fontSize);
            SpannableString spannableString = new SpannableString(mediaEntity.text);
            ArrayList<VideoEditedInfo.EmojiEntity> arrayList = mediaEntity.entities;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                int i3 = i2 + 1;
                VideoEditedInfo.EmojiEntity emojiEntity = arrayList.get(i2);
                if (emojiEntity.documentAbsolutePath != null) {
                    VideoEditedInfo.MediaEntity mediaEntity2 = new VideoEditedInfo.MediaEntity();
                    emojiEntity.entity = mediaEntity2;
                    mediaEntity2.text = emojiEntity.documentAbsolutePath;
                    mediaEntity2.subType = emojiEntity.subType;
                    C28651 c28651 = new AnimatedEmojiSpan(0L, 1.0f, editTextOutline.getPaint().getFontMetricsInt()) { // from class: org.telegram.messenger.video.WebmEncoder.FrameDrawer.1
                        final /* synthetic */ VideoEditedInfo.EmojiEntity val$e;
                        final /* synthetic */ EditTextOutline val$editText;
                        final /* synthetic */ VideoEditedInfo.MediaEntity val$entity;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C28651(long j, float f, Paint.FontMetricsInt fontMetricsInt, VideoEditedInfo.MediaEntity mediaEntity3, EditTextOutline editTextOutline2, VideoEditedInfo.EmojiEntity emojiEntity2) {
                            super(j, f, fontMetricsInt);
                            mediaEntity = mediaEntity3;
                            editTextOutline = editTextOutline2;
                            emojiEntity = emojiEntity2;
                        }

                        @Override // org.telegram.p035ui.Components.AnimatedEmojiSpan, android.text.style.ReplacementSpan
                        public void draw(Canvas canvas, CharSequence charSequence, int i4, int i5, float f, int i6, int i7, int i8, Paint paint) {
                            super.draw(canvas, charSequence, i4, i5, f, i6, i7, i8, paint);
                            VideoEditedInfo.MediaEntity mediaEntity3 = mediaEntity;
                            float paddingLeft = mediaEntity.f1187x + ((((editTextOutline.getPaddingLeft() + f) + (this.measuredSize / 2.0f)) / mediaEntity3.viewWidth) * mediaEntity3.width);
                            float f2 = mediaEntity3.f1188y;
                            VideoEditedInfo.MediaEntity mediaEntity4 = mediaEntity;
                            float paddingTop = ((editTextOutline.getPaddingTop() + i6) + ((i8 - i6) / 2.0f)) / mediaEntity4.viewHeight;
                            float f3 = mediaEntity4.height;
                            float fSin = f2 + (paddingTop * f3);
                            if (mediaEntity4.rotation != 0.0f) {
                                float f4 = mediaEntity4.f1187x + (mediaEntity4.width / 2.0f);
                                float f5 = mediaEntity4.f1188y + (f3 / 2.0f);
                                float f6 = FrameDrawer.this.f1234W / FrameDrawer.this.f1233H;
                                double d = paddingLeft - f4;
                                double d2 = (fSin - f5) / f6;
                                float fCos = f4 + ((float) ((Math.cos(-mediaEntity.rotation) * d) - (Math.sin(-mediaEntity.rotation) * d2)));
                                fSin = (((float) ((d * Math.sin(-mediaEntity.rotation)) + (d2 * Math.cos(-mediaEntity.rotation)))) * f6) + f5;
                                paddingLeft = fCos;
                            }
                            VideoEditedInfo.MediaEntity mediaEntity5 = emojiEntity.entity;
                            int i9 = this.measuredSize;
                            VideoEditedInfo.MediaEntity mediaEntity6 = mediaEntity;
                            float f7 = (i9 / mediaEntity6.viewWidth) * mediaEntity6.width;
                            mediaEntity5.width = f7;
                            float f8 = (i9 / mediaEntity6.viewHeight) * mediaEntity6.height;
                            mediaEntity5.height = f8;
                            mediaEntity5.f1187x = paddingLeft - (f7 / 2.0f);
                            mediaEntity5.f1188y = fSin - (f8 / 2.0f);
                            mediaEntity5.rotation = mediaEntity6.rotation;
                            if (mediaEntity5.bitmap == null) {
                                FrameDrawer.this.initStickerEntity(mediaEntity5);
                            }
                        }
                    };
                    int i4 = emojiEntity2.offset;
                    spannableString.setSpan(c28651, i4, emojiEntity2.length + i4, 33);
                }
                i2 = i3;
            }
            CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(spannableString, editTextOutline2.getPaint().getFontMetricsInt(), false);
            if ((charSequenceReplaceEmoji instanceof Spanned) && (emojiSpanArr = (Emoji.EmojiSpan[]) ((Spanned) charSequenceReplaceEmoji).getSpans(0, charSequenceReplaceEmoji.length(), Emoji.EmojiSpan.class)) != null) {
                for (Emoji.EmojiSpan emojiSpan : emojiSpanArr) {
                    emojiSpan.scale = 0.85f;
                }
            }
            editTextOutline2.setText(charSequenceReplaceEmoji);
            editTextOutline2.setTextColor(mediaEntity3.color);
            int i5 = mediaEntity3.textAlign;
            editTextOutline2.setGravity(i5 != 1 ? i5 != 2 ? 19 : 21 : 17);
            int i6 = mediaEntity3.textAlign;
            if (i6 != 1) {
                i = (i6 == 2 ? !LocaleController.isRTL : LocaleController.isRTL) ? 3 : 2;
            } else {
                i = 4;
            }
            editTextOutline2.setTextAlignment(i);
            editTextOutline2.setHorizontallyScrolling(false);
            editTextOutline2.setImeOptions(268435456);
            editTextOutline2.setFocusableInTouchMode(true);
            editTextOutline2.setInputType(editTextOutline2.getInputType() | 16384);
            setBreakStrategy(editTextOutline2);
            byte b2 = mediaEntity3.subType;
            if (b2 == 0) {
                editTextOutline2.setFrameColor(mediaEntity3.color);
                editTextOutline2.setTextColor(AndroidUtilities.computePerceivedBrightness(mediaEntity3.color) >= 0.721f ? -16777216 : -1);
            } else if (b2 == 1) {
                editTextOutline2.setFrameColor(AndroidUtilities.computePerceivedBrightness(mediaEntity3.color) >= 0.25f ? -1728053248 : -1711276033);
                editTextOutline2.setTextColor(mediaEntity3.color);
            } else if (b2 == 2) {
                editTextOutline2.setFrameColor(AndroidUtilities.computePerceivedBrightness(mediaEntity3.color) >= 0.25f ? -16777216 : -1);
                editTextOutline2.setTextColor(mediaEntity3.color);
            } else if (b2 == 3) {
                editTextOutline2.setFrameColor(0);
                editTextOutline2.setTextColor(mediaEntity3.color);
            }
            editTextOutline2.measure(View.MeasureSpec.makeMeasureSpec(mediaEntity3.viewWidth, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(mediaEntity3.viewHeight, TLObject.FLAG_30));
            editTextOutline2.layout(0, 0, mediaEntity3.viewWidth, mediaEntity3.viewHeight);
            mediaEntity3.bitmap = Bitmap.createBitmap(mediaEntity3.viewWidth, mediaEntity3.viewHeight, Bitmap.Config.ARGB_8888);
            editTextOutline2.draw(new Canvas(mediaEntity3.bitmap));
            setupMatrix(mediaEntity3);
        }

        /* JADX INFO: renamed from: org.telegram.messenger.video.WebmEncoder$FrameDrawer$1 */
        public class C28651 extends AnimatedEmojiSpan {
            final /* synthetic */ VideoEditedInfo.EmojiEntity val$e;
            final /* synthetic */ EditTextOutline val$editText;
            final /* synthetic */ VideoEditedInfo.MediaEntity val$entity;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C28651(long j, float f, Paint.FontMetricsInt fontMetricsInt, VideoEditedInfo.MediaEntity mediaEntity3, EditTextOutline editTextOutline2, VideoEditedInfo.EmojiEntity emojiEntity2) {
                super(j, f, fontMetricsInt);
                mediaEntity = mediaEntity3;
                editTextOutline = editTextOutline2;
                emojiEntity = emojiEntity2;
            }

            @Override // org.telegram.p035ui.Components.AnimatedEmojiSpan, android.text.style.ReplacementSpan
            public void draw(Canvas canvas, CharSequence charSequence, int i4, int i5, float f, int i6, int i7, int i8, Paint paint) {
                super.draw(canvas, charSequence, i4, i5, f, i6, i7, i8, paint);
                VideoEditedInfo.MediaEntity mediaEntity3 = mediaEntity;
                float paddingLeft = mediaEntity.f1187x + ((((editTextOutline.getPaddingLeft() + f) + (this.measuredSize / 2.0f)) / mediaEntity3.viewWidth) * mediaEntity3.width);
                float f2 = mediaEntity3.f1188y;
                VideoEditedInfo.MediaEntity mediaEntity4 = mediaEntity;
                float paddingTop = ((editTextOutline.getPaddingTop() + i6) + ((i8 - i6) / 2.0f)) / mediaEntity4.viewHeight;
                float f3 = mediaEntity4.height;
                float fSin = f2 + (paddingTop * f3);
                if (mediaEntity4.rotation != 0.0f) {
                    float f4 = mediaEntity4.f1187x + (mediaEntity4.width / 2.0f);
                    float f5 = mediaEntity4.f1188y + (f3 / 2.0f);
                    float f6 = FrameDrawer.this.f1234W / FrameDrawer.this.f1233H;
                    double d = paddingLeft - f4;
                    double d2 = (fSin - f5) / f6;
                    float fCos = f4 + ((float) ((Math.cos(-mediaEntity.rotation) * d) - (Math.sin(-mediaEntity.rotation) * d2)));
                    fSin = (((float) ((d * Math.sin(-mediaEntity.rotation)) + (d2 * Math.cos(-mediaEntity.rotation)))) * f6) + f5;
                    paddingLeft = fCos;
                }
                VideoEditedInfo.MediaEntity mediaEntity5 = emojiEntity.entity;
                int i9 = this.measuredSize;
                VideoEditedInfo.MediaEntity mediaEntity6 = mediaEntity;
                float f7 = (i9 / mediaEntity6.viewWidth) * mediaEntity6.width;
                mediaEntity5.width = f7;
                float f8 = (i9 / mediaEntity6.viewHeight) * mediaEntity6.height;
                mediaEntity5.height = f8;
                mediaEntity5.f1187x = paddingLeft - (f7 / 2.0f);
                mediaEntity5.f1188y = fSin - (f8 / 2.0f);
                mediaEntity5.rotation = mediaEntity6.rotation;
                if (mediaEntity5.bitmap == null) {
                    FrameDrawer.this.initStickerEntity(mediaEntity5);
                }
            }
        }

        public void setBreakStrategy(EditTextOutline editTextOutline) {
            editTextOutline.setBreakStrategy(0);
        }

        public void initStickerEntity(VideoEditedInfo.MediaEntity mediaEntity) {
            int i;
            int i2 = (int) (mediaEntity.width * this.f1234W);
            mediaEntity.f1186W = i2;
            int i3 = (int) (mediaEntity.height * this.f1233H);
            mediaEntity.f1185H = i3;
            if (i2 > 512) {
                mediaEntity.f1185H = (int) ((i3 / i2) * 512.0f);
                mediaEntity.f1186W = 512;
            }
            int i4 = mediaEntity.f1185H;
            if (i4 > 512) {
                mediaEntity.f1186W = (int) ((mediaEntity.f1186W / i4) * 512.0f);
                mediaEntity.f1185H = 512;
            }
            byte b2 = mediaEntity.subType;
            if ((b2 & 1) != 0) {
                int i5 = mediaEntity.f1186W;
                if (i5 <= 0 || (i = mediaEntity.f1185H) <= 0) {
                    return;
                }
                mediaEntity.bitmap = Bitmap.createBitmap(i5, i, Bitmap.Config.ARGB_8888);
                int[] iArr = new int[3];
                mediaEntity.metadata = iArr;
                mediaEntity.ptr = RLottieNative.create(mediaEntity.text, null, mediaEntity.f1186W, mediaEntity.f1185H, iArr, false, null, false, 0);
                mediaEntity.framesPerDraw = mediaEntity.metadata[1] / this.fps;
            } else if ((b2 & 4) != 0) {
                mediaEntity.looped = false;
                mediaEntity.animatedFileDrawable = new AnimatedFileDrawable(new File(mediaEntity.text), true, 0L, 0, null, null, null, 0L, UserConfig.selectedAccount, true, 512, 512, null);
                mediaEntity.framesPerDraw = r6.getFps() / this.fps;
                mediaEntity.currentFrame = 1.0f;
                mediaEntity.animatedFileDrawable.getNextFrame(true);
                if (mediaEntity.type == 5) {
                    mediaEntity.firstSeek = true;
                }
            } else {
                String str = mediaEntity.text;
                if (!TextUtils.isEmpty(mediaEntity.segmentedPath) && (mediaEntity.subType & 16) != 0) {
                    str = mediaEntity.segmentedPath;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                if (mediaEntity.type == 2) {
                    options.inMutable = true;
                }
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
                mediaEntity.bitmap = bitmapDecodeFile;
                if (mediaEntity.type == 2 && bitmapDecodeFile != null) {
                    mediaEntity.roundRadius = AndroidUtilities.m1036dp(12.0f) / Math.min(mediaEntity.viewWidth, mediaEntity.viewHeight);
                    Pair<Integer, Integer> imageOrientation = AndroidUtilities.getImageOrientation(mediaEntity.text);
                    mediaEntity.rotation = (float) (((double) mediaEntity.rotation) - Math.toRadians(((Integer) imageOrientation.first).intValue()));
                    if ((((Integer) imageOrientation.first).intValue() / 90) % 2 == 1) {
                        float f = mediaEntity.f1187x;
                        float f2 = mediaEntity.width;
                        float f3 = f + (f2 / 2.0f);
                        float f4 = mediaEntity.f1188y;
                        float f5 = mediaEntity.height;
                        float f6 = f4 + (f5 / 2.0f);
                        int i6 = this.f1234W;
                        int i7 = this.f1233H;
                        float f7 = (f2 * i6) / i7;
                        float f8 = (f5 * i7) / i6;
                        mediaEntity.width = f8;
                        mediaEntity.height = f7;
                        mediaEntity.f1187x = f3 - (f8 / 2.0f);
                        mediaEntity.f1188y = f6 - (f7 / 2.0f);
                    }
                    applyRoundRadius(mediaEntity, mediaEntity.bitmap, 0);
                } else if (bitmapDecodeFile != null) {
                    float width = bitmapDecodeFile.getWidth() / mediaEntity.bitmap.getHeight();
                    if (width > 1.0f) {
                        float f9 = mediaEntity.height;
                        float f10 = f9 / width;
                        mediaEntity.f1188y += (f9 - f10) / 2.0f;
                        mediaEntity.height = f10;
                    } else if (width < 1.0f) {
                        float f11 = mediaEntity.width;
                        float f12 = width * f11;
                        mediaEntity.f1187x += (f11 - f12) / 2.0f;
                        mediaEntity.width = f12;
                    }
                }
            }
            setupMatrix(mediaEntity);
        }

        private void setupMatrix(VideoEditedInfo.MediaEntity mediaEntity) {
            AnimatedFileDrawable animatedFileDrawable;
            mediaEntity.matrix = new Matrix();
            Bitmap backgroundBitmap = mediaEntity.bitmap;
            if (backgroundBitmap == null && (animatedFileDrawable = mediaEntity.animatedFileDrawable) != null) {
                backgroundBitmap = animatedFileDrawable.getBackgroundBitmap();
            }
            if (backgroundBitmap != null) {
                mediaEntity.matrix.postScale(1.0f / backgroundBitmap.getWidth(), 1.0f / backgroundBitmap.getHeight());
            }
            if (mediaEntity.type != 1 && (mediaEntity.subType & 2) != 0) {
                mediaEntity.matrix.postScale(-1.0f, 1.0f, 0.5f, 0.5f);
            }
            mediaEntity.matrix.postScale(mediaEntity.width * this.f1234W, mediaEntity.height * this.f1233H);
            mediaEntity.matrix.postTranslate(mediaEntity.f1187x * this.f1234W, mediaEntity.f1188y * this.f1233H);
            mediaEntity.matrix.postRotate((float) ((((double) (-mediaEntity.rotation)) / 3.141592653589793d) * 180.0d), (mediaEntity.f1187x + (mediaEntity.width / 2.0f)) * this.f1234W, (mediaEntity.f1188y + (mediaEntity.height / 2.0f)) * this.f1233H);
        }

        private void applyRoundRadius(VideoEditedInfo.MediaEntity mediaEntity, Bitmap bitmap, int i) {
            if (bitmap == null || mediaEntity == null) {
                return;
            }
            if (mediaEntity.roundRadius == 0.0f && i == 0) {
                return;
            }
            if (mediaEntity.roundRadiusCanvas == null) {
                mediaEntity.roundRadiusCanvas = new Canvas(bitmap);
            }
            if (mediaEntity.roundRadius != 0.0f) {
                if (this.path == null) {
                    this.path = new Path();
                }
                if (this.xRefPaint == null) {
                    Paint paint = new Paint(1);
                    this.xRefPaint = paint;
                    paint.setColor(-16777216);
                    this.xRefPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                }
                float fMin = Math.min(bitmap.getWidth(), bitmap.getHeight()) * mediaEntity.roundRadius;
                this.path.rewind();
                this.path.addRoundRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), fMin, fMin, Path.Direction.CCW);
                this.path.toggleInverseFillType();
                mediaEntity.roundRadiusCanvas.drawPath(this.path, this.xRefPaint);
            }
            if (i != 0) {
                if (this.textColorPaint == null) {
                    Paint paint2 = new Paint(1);
                    this.textColorPaint = paint2;
                    paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                }
                this.textColorPaint.setColor(i);
                mediaEntity.roundRadiusCanvas.drawRect(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight(), this.textColorPaint);
            }
        }
    }
}
