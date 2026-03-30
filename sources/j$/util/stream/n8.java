package j$.util.stream;

import java.security.AccessController;

/* JADX INFO: loaded from: classes2.dex */
public abstract class n8 {
    public static final boolean a = ((Boolean) AccessController.doPrivileged(new j$.util.r1(2))).booleanValue();

    public static void a(Class cls, String str) {
        throw new UnsupportedOperationException(cls + " tripwire tripped but logging not supported: " + str);
    }
}
