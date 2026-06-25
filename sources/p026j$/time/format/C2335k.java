package p026j$.time.format;

import java.text.ParsePosition;
import kotlin.jvm.internal.CharCompanionObject;

/* JADX INFO: renamed from: j$.time.format.k */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public class C2335k {

    /* JADX INFO: renamed from: a */
    public String f858a;

    /* JADX INFO: renamed from: b */
    public String f859b;

    /* JADX INFO: renamed from: c */
    public final char f860c;

    /* JADX INFO: renamed from: d */
    public C2335k f861d;

    /* JADX INFO: renamed from: e */
    public C2335k f862e;

    public C2335k(String str, String str2, C2335k c2335k) {
        this.f858a = str;
        this.f859b = str2;
        this.f861d = c2335k;
        if (str.isEmpty()) {
            this.f860c = CharCompanionObject.MAX_VALUE;
        } else {
            this.f860c = this.f858a.charAt(0);
        }
    }

    /* JADX INFO: renamed from: a */
    public final boolean m761a(String str, String str2) {
        int i = 0;
        while (i < str.length() && i < this.f858a.length() && mo758b(str.charAt(i), this.f858a.charAt(i))) {
            i++;
        }
        if (i != this.f858a.length()) {
            C2335k c2335kMo759d = mo759d(this.f858a.substring(i), this.f859b, this.f861d);
            this.f858a = str.substring(0, i);
            this.f861d = c2335kMo759d;
            if (i >= str.length()) {
                this.f859b = str2;
                return true;
            }
            this.f861d.f862e = mo759d(str.substring(i), str2, null);
            this.f859b = null;
            return true;
        }
        if (i >= str.length()) {
            this.f859b = str2;
            return true;
        }
        String strSubstring = str.substring(i);
        for (C2335k c2335k = this.f861d; c2335k != null; c2335k = c2335k.f862e) {
            if (mo758b(c2335k.f860c, strSubstring.charAt(0))) {
                return c2335k.m761a(strSubstring, str2);
            }
        }
        C2335k c2335kMo759d2 = mo759d(strSubstring, str2, null);
        c2335kMo759d2.f862e = this.f861d;
        this.f861d = c2335kMo759d2;
        return true;
    }

    /* JADX INFO: renamed from: b */
    public boolean mo758b(char c2, char c3) {
        return c2 == c3;
    }

    /* JADX INFO: renamed from: c */
    public final String m762c(CharSequence charSequence, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        int length = charSequence.length();
        if (!mo760e(charSequence, index, length)) {
            return null;
        }
        int length2 = this.f858a.length() + index;
        C2335k c2335k = this.f861d;
        if (c2335k != null && length2 != length) {
            while (true) {
                if (mo758b(c2335k.f860c, charSequence.charAt(length2))) {
                    parsePosition.setIndex(length2);
                    String strM762c = c2335k.m762c(charSequence, parsePosition);
                    if (strM762c != null) {
                        return strM762c;
                    }
                } else {
                    c2335k = c2335k.f862e;
                    if (c2335k == null) {
                        break;
                    }
                }
            }
        }
        parsePosition.setIndex(length2);
        return this.f859b;
    }

    /* JADX INFO: renamed from: d */
    public C2335k mo759d(String str, String str2, C2335k c2335k) {
        return new C2335k(str, str2, c2335k);
    }

    /* JADX INFO: renamed from: e */
    public boolean mo760e(CharSequence charSequence, int i, int i2) {
        boolean z = charSequence instanceof String;
        String str = this.f858a;
        if (z) {
            return ((String) charSequence).startsWith(str, i);
        }
        int length = str.length();
        if (length > i2 - i) {
            return false;
        }
        int i3 = 0;
        while (true) {
            int i4 = length - 1;
            if (length <= 0) {
                return true;
            }
            int i5 = i3 + 1;
            int i6 = i + 1;
            if (!mo758b(this.f858a.charAt(i3), charSequence.charAt(i))) {
                return false;
            }
            i = i6;
            length = i4;
            i3 = i5;
        }
    }
}
