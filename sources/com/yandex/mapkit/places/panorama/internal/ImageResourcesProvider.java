package com.yandex.mapkit.places.panorama.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.yandex.runtime.Runtime;
import com.yandex.runtime.image.ImageProvider;

/* JADX INFO: loaded from: classes5.dex */
class ImageResourcesProvider extends ImageProvider {
    private final Bitmap bitmap;

    /* JADX INFO: renamed from: id */
    private final String f681id;

    @Override // com.yandex.runtime.image.ImageProvider
    public String getId() {
        return this.f681id;
    }

    @Override // com.yandex.runtime.image.ImageProvider
    public Bitmap getImage() {
        return this.bitmap;
    }

    public ImageResourcesProvider(String str) {
        this.f681id = str;
        Context applicationContext = Runtime.getApplicationContext();
        this.bitmap = BitmapFactory.decodeResource(applicationContext.getResources(), applicationContext.getResources().getIdentifier(str, "drawable", applicationContext.getPackageName()));
    }

    public static boolean isImageResourceAvailable(String str) {
        Context applicationContext = Runtime.getApplicationContext();
        return applicationContext.getResources().getIdentifier(str, "drawable", applicationContext.getPackageName()) != 0;
    }
}
