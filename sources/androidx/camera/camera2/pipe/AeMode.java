package androidx.camera.camera2.pipe;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes4.dex */
@kotlin.Metadata(m876d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\t\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\r\u001a\u00020\nHÖ\u0001¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000f\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\u000e\u0010\u0005J\u001a\u0010\u0013\u001a\u00020\u00062\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u0088\u0001\u0003\u0092\u0001\u00020\u0002¨\u0006\u0018"}, m877d2 = {"Landroidx/camera/camera2/pipe/AeMode;", _UrlKt.FRAGMENT_ENCODE_SET, _UrlKt.FRAGMENT_ENCODE_SET, "value", "constructor-impl", "(I)I", _UrlKt.FRAGMENT_ENCODE_SET, "isOn-impl", "(I)Z", "isOn", _UrlKt.FRAGMENT_ENCODE_SET, "toString-impl", "(I)Ljava/lang/String;", "toString", "hashCode-impl", "hashCode", "other", "equals-impl", "(ILjava/lang/Object;)Z", "equals", "I", "getValue", "()I", "Companion", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
@JvmInline
public final class AeMode {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int OFF;

    /* JADX INFO: renamed from: ON */
    private static final int f10ON;
    private static final int ON_ALWAYS_FLASH;
    private static final int ON_AUTO_FLASH;
    private static final int ON_AUTO_FLASH_REDEYE;
    private static final int ON_EXTERNAL_FLASH;
    private static final int ON_LOW_LIGHT_BOOST_BRIGHTNESS_PRIORITY;
    private static final List<AeMode> values;
    private final int value;

    /* JADX INFO: renamed from: box-impl */
    public static final /* synthetic */ AeMode m1378boximpl(int i) {
        return new AeMode(i);
    }

    /* JADX INFO: renamed from: constructor-impl */
    public static int m1379constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl */
    public static boolean m1380equalsimpl(int i, Object obj) {
        return (obj instanceof AeMode) && i == ((AeMode) obj).getValue();
    }

    /* JADX INFO: renamed from: equals-impl0 */
    public static final boolean m1381equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl */
    public static int m1382hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: isOn-impl */
    public static final boolean m1383isOnimpl(int i) {
        return i != 0;
    }

    /* JADX INFO: renamed from: toString-impl */
    public static String m1384toStringimpl(int i) {
        return "AeMode(value=" + i + ')';
    }

    public boolean equals(Object obj) {
        return m1380equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m1382hashCodeimpl(this.value);
    }

    public String toString() {
        return m1384toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ int getValue() {
        return this.value;
    }

    private /* synthetic */ AeMode(int i) {
        this.value = i;
    }

    @kotlin.Metadata(m876d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\u00068\u0006¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\u00020\u00068\u0006¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\u000f\u0010\rR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u00108\u0006¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, m877d2 = {"Landroidx/camera/camera2/pipe/AeMode$Companion;", _UrlKt.FRAGMENT_ENCODE_SET, "<init>", "()V", _UrlKt.FRAGMENT_ENCODE_SET, "value", "Landroidx/camera/camera2/pipe/AeMode;", "fromIntOrNull-kQd0u18", "(I)Landroidx/camera/camera2/pipe/AeMode;", "fromIntOrNull", "OFF", "I", "getOFF-bOjpiJc", "()I", "ON", "getON-bOjpiJc", _UrlKt.FRAGMENT_ENCODE_SET, "values", "Ljava/util/List;", "getValues", "()Ljava/util/List;", "camera-camera2-pipe"}, m878k = 1, m879mv = {2, 1, 0}, m881xi = 48)
    @SourceDebugExtension({"SMAP\nCameraControls.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CameraControls.kt\nandroidx/camera/camera2/pipe/AeMode$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,225:1\n295#2,2:226\n*S KotlinDebug\n*F\n+ 1 CameraControls.kt\nandroidx/camera/camera2/pipe/AeMode$Companion\n*L\n88#1:226,2\n*E\n"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: getOFF-bOjpiJc */
        public final int m1387getOFFbOjpiJc() {
            return AeMode.OFF;
        }

        /* JADX INFO: renamed from: getON-bOjpiJc */
        public final int m1388getONbOjpiJc() {
            return AeMode.f10ON;
        }

        public final List<AeMode> getValues() {
            return AeMode.values;
        }

        @JvmStatic
        /* JADX INFO: renamed from: fromIntOrNull-kQd0u18 */
        public final AeMode m1386fromIntOrNullkQd0u18(int value) {
            Object next;
            Iterator<T> it = getValues().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (((AeMode) next).getValue() == value) {
                    break;
                }
            }
            return (AeMode) next;
        }
    }

    static {
        int iM1379constructorimpl = m1379constructorimpl(0);
        OFF = iM1379constructorimpl;
        int iM1379constructorimpl2 = m1379constructorimpl(1);
        f10ON = iM1379constructorimpl2;
        int iM1379constructorimpl3 = m1379constructorimpl(3);
        ON_ALWAYS_FLASH = iM1379constructorimpl3;
        int iM1379constructorimpl4 = m1379constructorimpl(2);
        ON_AUTO_FLASH = iM1379constructorimpl4;
        int iM1379constructorimpl5 = m1379constructorimpl(4);
        ON_AUTO_FLASH_REDEYE = iM1379constructorimpl5;
        int iM1379constructorimpl6 = m1379constructorimpl(5);
        ON_EXTERNAL_FLASH = iM1379constructorimpl6;
        int iM1379constructorimpl7 = m1379constructorimpl(6);
        ON_LOW_LIGHT_BOOST_BRIGHTNESS_PRIORITY = iM1379constructorimpl7;
        values = CollectionsKt.listOf((Object[]) new AeMode[]{m1378boximpl(iM1379constructorimpl), m1378boximpl(iM1379constructorimpl2), m1378boximpl(iM1379constructorimpl4), m1378boximpl(iM1379constructorimpl3), m1378boximpl(iM1379constructorimpl5), m1378boximpl(iM1379constructorimpl6), m1378boximpl(iM1379constructorimpl7)});
    }
}
