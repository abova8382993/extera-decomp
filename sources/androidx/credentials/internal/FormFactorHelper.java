package androidx.credentials.internal;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\n"}, m877d2 = {"Landroidx/credentials/internal/FormFactorHelper;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "isTV", _UrlKt.FRAGMENT_ENCODE_SET, "ctx", "Landroid/content/Context;", "isWear", "isAuto", "credentials"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class FormFactorHelper {
    public static final FormFactorHelper INSTANCE = new FormFactorHelper();

    private FormFactorHelper() {
    }

    @JvmStatic
    public static final boolean isTV(Context ctx) {
        return ctx.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    @JvmStatic
    public static final boolean isWear(Context ctx) {
        return ctx.getPackageManager().hasSystemFeature("android.hardware.type.watch");
    }

    @JvmStatic
    public static final boolean isAuto(Context ctx) {
        return ctx.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }
}
