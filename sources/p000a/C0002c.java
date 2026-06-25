package p000a;

import dev.exterahook.runtime.bridge.JniBridgeBindings;
import java.lang.reflect.Member;
import kotlin.Unit;
import okhttp3.internal.url._UrlKt;
import org.lsposed.hiddenapibypass.HiddenApiBypass;
import p004b.C0898a;
import p004b.C0899b;
import p005c.C0908h;

/* JADX INFO: renamed from: a.c */
/* JADX INFO: loaded from: classes3.dex */
public final class C0002c implements InterfaceC0000a {

    /* JADX INFO: renamed from: a */
    public final C0908h f0a;

    /* JADX INFO: renamed from: b */
    public final C0898a f1b;

    /* JADX INFO: renamed from: c */
    public volatile boolean f2c;

    public C0002c(C0908h c0908h, C0899b c0899b, C0898a c0898a) {
        this.f0a = c0908h;
        this.f1b = c0898a;
    }

    /* JADX INFO: renamed from: a */
    public final void m1a() {
        try {
            if (!this.f2c) {
                synchronized (this) {
                    try {
                        if (!this.f2c) {
                            try {
                                HiddenApiBypass.addHiddenApiExemptions(_UrlKt.FRAGMENT_ENCODE_SET);
                            } catch (Throwable unused) {
                            }
                            this.f2c = true;
                        }
                        Unit unit = Unit.INSTANCE;
                    } finally {
                    }
                }
            }
        } finally {
            this.f0a.m209a();
        }
    }

    @Override // p000a.InterfaceC0000a
    /* JADX INFO: renamed from: a */
    public final boolean mo0a(Member member) {
        m1a();
        return JniBridgeBindings.unhook0(member);
    }
}
