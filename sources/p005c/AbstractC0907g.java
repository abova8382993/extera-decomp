package p005c;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/* JADX INFO: renamed from: c.g */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC0907g {
    /* JADX INFO: renamed from: a */
    public static void m205a(Member member) {
        if (member == null) {
            g$$ExternalSyntheticBUOutline2.m208m("method must not be null");
            return;
        }
        if (!(member instanceof Method) && !(member instanceof Constructor)) {
            g$$ExternalSyntheticBUOutline1.m207m("method must be a Method or Constructor");
        } else if (Modifier.isAbstract(g$$ExternalSyntheticApiModelOutline0.m206m(member).getModifiers())) {
            g$$ExternalSyntheticBUOutline1.m207m("method must not be abstract");
        }
    }
}
