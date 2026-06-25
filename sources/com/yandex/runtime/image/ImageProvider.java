package com.yandex.runtime.image;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public abstract class ImageProvider {
    private final boolean cacheable;

    public abstract String getId();

    public abstract Bitmap getImage();

    public ImageProvider() {
        this(true);
    }

    public ImageProvider(boolean z) {
        this.cacheable = z;
    }

    public boolean isCacheable() {
        return this.cacheable;
    }

    public static abstract class ImageProviderImpl extends ImageProvider {

        /* JADX INFO: renamed from: id */
        private final String f708id;

        public abstract Bitmap loadBitmap();

        public ImageProviderImpl(String str, boolean z) {
            super(z);
            this.f708id = str;
        }

        @Override // com.yandex.runtime.image.ImageProvider
        public String getId() {
            return this.f708id;
        }

        @Override // com.yandex.runtime.image.ImageProvider
        public Bitmap getImage() {
            return loadBitmap();
        }
    }

    public static ImageProvider fromBitmap(Bitmap bitmap) {
        return fromBitmap(bitmap, true, "bitmap:" + UUID.randomUUID().toString());
    }

    public static ImageProvider fromBitmap(Bitmap bitmap, boolean z, String str) {
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            g$$ExternalSyntheticBUOutline1.m207m("Bitmap config value should be ARGB_8888");
            return null;
        }
        return new ImageProvider(z) { // from class: com.yandex.runtime.image.ImageProvider.1
            final /* synthetic */ Bitmap val$bitmap;
            final /* synthetic */ String val$id;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C21341(boolean z2, String str2, Bitmap bitmap2) {
                super(z2);
                str = str2;
                bitmap = bitmap2;
            }

            @Override // com.yandex.runtime.image.ImageProvider
            public String getId() {
                return str;
            }

            @Override // com.yandex.runtime.image.ImageProvider
            public Bitmap getImage() {
                return bitmap;
            }
        };
    }

    /* JADX INFO: renamed from: com.yandex.runtime.image.ImageProvider$1 */
    public class C21341 extends ImageProvider {
        final /* synthetic */ Bitmap val$bitmap;
        final /* synthetic */ String val$id;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C21341(boolean z2, String str2, Bitmap bitmap2) {
            super(z2);
            str = str2;
            bitmap = bitmap2;
        }

        @Override // com.yandex.runtime.image.ImageProvider
        public String getId() {
            return str;
        }

        @Override // com.yandex.runtime.image.ImageProvider
        public Bitmap getImage() {
            return bitmap;
        }
    }

    public static ImageProvider fromAsset(Context context, String str) {
        return fromAsset(context, str, true);
    }

    /* JADX INFO: renamed from: com.yandex.runtime.image.ImageProvider$2 */
    public class C21352 extends ImageProviderImpl {
        final /* synthetic */ AssetManager val$assetManager;
        final /* synthetic */ String val$assetName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C21352(String str, boolean z, AssetManager assetManager, String str2) {
            super(str, z);
            assetManager = assetManager;
            str = str2;
        }

        @Override // com.yandex.runtime.image.ImageProvider.ImageProviderImpl
        public Bitmap loadBitmap() {
            Bitmap bitmapDecodeStream = null;
            try {
                InputStream inputStreamOpen = assetManager.open(str);
                try {
                    bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                    return bitmapDecodeStream;
                } finally {
                    inputStreamOpen.close();
                }
            } catch (IOException e) {
                Log.e("yandex.maps", "Can't load image from asset: " + str, e);
                return bitmapDecodeStream;
            }
        }
    }

    public static ImageProvider fromAsset(Context context, String str, boolean z) {
        return new ImageProviderImpl("asset:" + str, z) { // from class: com.yandex.runtime.image.ImageProvider.2
            final /* synthetic */ AssetManager val$assetManager;
            final /* synthetic */ String val$assetName;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C21352(String str2, boolean z2, AssetManager assetManager, String str3) {
                super(str2, z2);
                assetManager = assetManager;
                str = str3;
            }

            @Override // com.yandex.runtime.image.ImageProvider.ImageProviderImpl
            public Bitmap loadBitmap() {
                Bitmap bitmapDecodeStream = null;
                try {
                    InputStream inputStreamOpen = assetManager.open(str);
                    try {
                        bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                        return bitmapDecodeStream;
                    } finally {
                        inputStreamOpen.close();
                    }
                } catch (IOException e) {
                    Log.e("yandex.maps", "Can't load image from asset: " + str, e);
                    return bitmapDecodeStream;
                }
            }
        };
    }

    public static ImageProvider fromResource(Context context, int i) {
        return fromResource(context, i, true);
    }

    public static ImageProvider fromResource(Context context, int i, boolean z) {
        return new ImageProviderImpl("resource:" + i, z) { // from class: com.yandex.runtime.image.ImageProvider.3
            final /* synthetic */ int val$resourceId;
            final /* synthetic */ Resources val$resources;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C21363(String str, boolean z2, Resources resources, int i2) {
                super(str, z2);
                resources = resources;
                i = i2;
            }

            @Override // com.yandex.runtime.image.ImageProvider.ImageProviderImpl
            public Bitmap loadBitmap() {
                return BitmapFactory.decodeResource(resources, i);
            }
        };
    }

    /* JADX INFO: renamed from: com.yandex.runtime.image.ImageProvider$3 */
    public class C21363 extends ImageProviderImpl {
        final /* synthetic */ int val$resourceId;
        final /* synthetic */ Resources val$resources;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C21363(String str, boolean z2, Resources resources, int i2) {
            super(str, z2);
            resources = resources;
            i = i2;
        }

        @Override // com.yandex.runtime.image.ImageProvider.ImageProviderImpl
        public Bitmap loadBitmap() {
            return BitmapFactory.decodeResource(resources, i);
        }
    }

    public static ImageProvider fromFile(String str) {
        return fromFile(str, true);
    }

    /* JADX INFO: renamed from: com.yandex.runtime.image.ImageProvider$4 */
    public class C21374 extends ImageProviderImpl {
        final /* synthetic */ String val$fileName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C21374(String str, boolean z, String str2) {
            super(str, z);
            str = str2;
        }

        @Override // com.yandex.runtime.image.ImageProvider.ImageProviderImpl
        public Bitmap loadBitmap() {
            return BitmapFactory.decodeFile(str);
        }
    }

    public static ImageProvider fromFile(String str, boolean z) {
        return new ImageProviderImpl("file:" + str, z) { // from class: com.yandex.runtime.image.ImageProvider.4
            final /* synthetic */ String val$fileName;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C21374(String str2, boolean z2, String str3) {
                super(str2, z2);
                str = str3;
            }

            @Override // com.yandex.runtime.image.ImageProvider.ImageProviderImpl
            public Bitmap loadBitmap() {
                return BitmapFactory.decodeFile(str);
            }
        };
    }
}
