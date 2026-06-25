package androidx.camera.extensions;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.impl.utils.ContextUtil;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.extensions.ExtensionsManager;
import androidx.camera.extensions.internal.Camera2ExtensionsInfo;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.ListenableFutureKt;
import com.google.common.util.concurrent.ListenableFuture;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00102\u00020\u0001:\u0002\u0011\u0010B!\b\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0003\u001a\u00020\u00028\u0000X\u0080\u0004¢\u0006\f\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u000e\u001a\u00020\r8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000e\u0010\u000f¨\u0006\u0012"}, m877d2 = {"Landroidx/camera/extensions/ExtensionsManager;", _UrlKt.FRAGMENT_ENCODE_SET, "Landroidx/camera/extensions/ExtensionsManager$ExtensionsAvailability;", "extensionsAvailability", "Landroidx/camera/core/CameraProvider;", "cameraProvider", "Landroid/content/Context;", "applicationContext", "<init>", "(Landroidx/camera/extensions/ExtensionsManager$ExtensionsAvailability;Landroidx/camera/core/CameraProvider;Landroid/content/Context;)V", "Landroidx/camera/extensions/ExtensionsManager$ExtensionsAvailability;", "getExtensionsAvailability$camera_extensions", "()Landroidx/camera/extensions/ExtensionsManager$ExtensionsAvailability;", "Landroidx/camera/extensions/ExtensionsInfo;", "extensionsInfo", "Landroidx/camera/extensions/ExtensionsInfo;", "Companion", "ExtensionsAvailability", "camera-extensions"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
public final class ExtensionsManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Object EXTENSIONS_LOCK = new Object();
    private static ListenableFuture<Void> sDeinitializeFuture;
    private static ExtensionsManager sExtensionsManager;
    private static ListenableFuture<ExtensionsManager> sInitializeFuture;
    private final ExtensionsAvailability extensionsAvailability;
    private final ExtensionsInfo extensionsInfo;

    @JvmStatic
    public static final ListenableFuture<ExtensionsManager> getInstanceAsync(Context context, CameraProvider cameraProvider) {
        return INSTANCE.getInstanceAsync(context, cameraProvider);
    }

    public ExtensionsManager(ExtensionsAvailability extensionsAvailability, CameraProvider cameraProvider, Context context) {
        this.extensionsAvailability = extensionsAvailability;
        this.extensionsInfo = new ExtensionsInfo(cameraProvider, context);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    @Metadata(m876d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0080\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, m877d2 = {"Landroidx/camera/extensions/ExtensionsManager$ExtensionsAvailability;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "(Ljava/lang/String;I)V", "LIBRARY_AVAILABLE", "LIBRARY_UNAVAILABLE_ERROR_LOADING", "LIBRARY_UNAVAILABLE_MISSING_IMPLEMENTATION", "NONE", "camera-extensions"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    public static final class ExtensionsAvailability {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ExtensionsAvailability[] $VALUES;
        public static final ExtensionsAvailability LIBRARY_AVAILABLE = new ExtensionsAvailability("LIBRARY_AVAILABLE", 0);
        public static final ExtensionsAvailability LIBRARY_UNAVAILABLE_ERROR_LOADING = new ExtensionsAvailability("LIBRARY_UNAVAILABLE_ERROR_LOADING", 1);
        public static final ExtensionsAvailability LIBRARY_UNAVAILABLE_MISSING_IMPLEMENTATION = new ExtensionsAvailability("LIBRARY_UNAVAILABLE_MISSING_IMPLEMENTATION", 2);
        public static final ExtensionsAvailability NONE = new ExtensionsAvailability("NONE", 3);

        private static final /* synthetic */ ExtensionsAvailability[] $values() {
            return new ExtensionsAvailability[]{LIBRARY_AVAILABLE, LIBRARY_UNAVAILABLE_ERROR_LOADING, LIBRARY_UNAVAILABLE_MISSING_IMPLEMENTATION, NONE};
        }

        public static EnumEntries<ExtensionsAvailability> getEntries() {
            return $ENTRIES;
        }

        public static ExtensionsAvailability valueOf(String str) {
            return (ExtensionsAvailability) Enum.valueOf(ExtensionsAvailability.class, str);
        }

        public static ExtensionsAvailability[] values() {
            return (ExtensionsAvailability[]) $VALUES.clone();
        }

        private ExtensionsAvailability(String str, int i) {
        }

        static {
            ExtensionsAvailability[] extensionsAvailabilityArr$values = $values();
            $VALUES = extensionsAvailabilityArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(extensionsAvailabilityArr$values);
        }
    }

    @Metadata(m876d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J \u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0011H\u0002J\u001e\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0087@¢\u0006\u0002\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\n8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000b8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, m877d2 = {"Landroidx/camera/extensions/ExtensionsManager$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", "TAG", _UrlKt.FRAGMENT_ENCODE_SET, "MINIMUM_SUPPORTED_API_LEVEL", _UrlKt.FRAGMENT_ENCODE_SET, "EXTENSIONS_LOCK", "sInitializeFuture", "Lcom/google/common/util/concurrent/ListenableFuture;", "Landroidx/camera/extensions/ExtensionsManager;", "sDeinitializeFuture", "Ljava/lang/Void;", "sExtensionsManager", "getInstanceAsync", "context", "Landroid/content/Context;", "cameraProvider", "Landroidx/camera/core/CameraProvider;", "getOrCreateExtensionsManager", "extensionsAvailability", "Landroidx/camera/extensions/ExtensionsManager$ExtensionsAvailability;", "applicationContext", "getInstance", "(Landroid/content/Context;Landroidx/camera/core/CameraProvider;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "camera-extensions"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nExtensionsManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExtensionsManager.kt\nandroidx/camera/extensions/ExtensionsManager$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,464:1\n1#2:465\n*E\n"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ListenableFuture<ExtensionsManager> getInstanceAsync(Context context, final CameraProvider cameraProvider) {
            synchronized (ExtensionsManager.EXTENSIONS_LOCK) {
                try {
                    ListenableFuture listenableFuture = ExtensionsManager.sDeinitializeFuture;
                    if (listenableFuture != null && !listenableFuture.isDone()) {
                        throw new IllegalStateException("Not yet done deinitializing extensions");
                    }
                    ExtensionsManager.sDeinitializeFuture = null;
                    final Context persistentApplicationContext = ContextUtil.getPersistentApplicationContext(context);
                    if (Build.VERSION.SDK_INT >= 33) {
                        if (ExtensionsManager.sInitializeFuture == null) {
                            ExtensionsManager.sInitializeFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.extensions.ExtensionsManager$Companion$$ExternalSyntheticLambda0
                                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                                    return ExtensionsManager.Companion.getInstanceAsync$lambda$0$1(persistentApplicationContext, cameraProvider, completer);
                                }
                            });
                        }
                        return ExtensionsManager.sInitializeFuture;
                    }
                    return Futures.immediateFuture(ExtensionsManager.INSTANCE.getOrCreateExtensionsManager(ExtensionsAvailability.NONE, cameraProvider, persistentApplicationContext));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Object getInstanceAsync$lambda$0$1(Context context, CameraProvider cameraProvider, CallbackToFutureAdapter.Completer completer) throws CameraAccessException {
            String str;
            ExtensionsAvailability extensionsAvailability;
            CameraManager cameraManager = (CameraManager) context.getSystemService(CameraManager.class);
            Camera2ExtensionsInfo camera2ExtensionsInfo = new Camera2ExtensionsInfo(cameraManager);
            String[] cameraIdList = cameraManager.getCameraIdList();
            int length = cameraIdList.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    str = null;
                    break;
                }
                str = cameraIdList[i];
                if (!camera2ExtensionsInfo.getExtensionCharacteristics(str).getSupportedExtensions().isEmpty()) {
                    break;
                }
                i++;
            }
            boolean z = str != null;
            Companion companion = ExtensionsManager.INSTANCE;
            if (!z) {
                extensionsAvailability = ExtensionsAvailability.NONE;
            } else {
                extensionsAvailability = ExtensionsAvailability.LIBRARY_AVAILABLE;
            }
            completer.set(companion.getOrCreateExtensionsManager(extensionsAvailability, cameraProvider, context));
            return "Initialize extensions";
        }

        private final ExtensionsManager getOrCreateExtensionsManager(ExtensionsAvailability extensionsAvailability, CameraProvider cameraProvider, Context applicationContext) {
            ExtensionsManager extensionsManager;
            synchronized (ExtensionsManager.EXTENSIONS_LOCK) {
                extensionsManager = ExtensionsManager.sExtensionsManager;
                if (extensionsManager == null) {
                    extensionsManager = new ExtensionsManager(extensionsAvailability, cameraProvider, applicationContext);
                    ExtensionsManager.sExtensionsManager = extensionsManager;
                }
            }
            return extensionsManager;
        }

        @JvmStatic
        public final Object getInstance(Context context, CameraProvider cameraProvider, Continuation<? super ExtensionsManager> continuation) {
            return ListenableFutureKt.await(getInstanceAsync(context, cameraProvider), continuation);
        }
    }
}
