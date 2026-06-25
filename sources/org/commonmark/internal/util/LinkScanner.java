package org.commonmark.internal.util;

import kotlin.text.Typography;

/* JADX INFO: loaded from: classes5.dex */
public abstract class LinkScanner {
    public static int scanLinkLabelContent(CharSequence charSequence, int i) {
        while (i < charSequence.length()) {
            switch (charSequence.charAt(i)) {
                case '[':
                    return -1;
                case '\\':
                    int i2 = i + 1;
                    if (Parsing.isEscapable(charSequence, i2)) {
                        i = i2;
                    }
                    break;
                case ']':
                    return i;
            }
            i++;
        }
        return charSequence.length();
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0038, code lost:
    
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int scanLinkDestination(java.lang.CharSequence r4, int r5) {
        /*
            int r0 = r4.length()
            r1 = -1
            if (r5 < r0) goto L8
            return r1
        L8:
            char r0 = r4.charAt(r5)
            r2 = 60
            if (r0 != r2) goto L39
        L10:
            int r5 = r5 + 1
            int r0 = r4.length()
            if (r5 >= r0) goto L38
            char r0 = r4.charAt(r5)
            r3 = 10
            if (r0 == r3) goto L38
            if (r0 == r2) goto L38
            r3 = 62
            if (r0 == r3) goto L35
            r3 = 92
            if (r0 == r3) goto L2b
            goto L10
        L2b:
            int r0 = r5 + 1
            boolean r3 = org.commonmark.internal.util.Parsing.isEscapable(r4, r0)
            if (r3 == 0) goto L10
            r5 = r0
            goto L10
        L35:
            int r5 = r5 + 1
            return r5
        L38:
            return r1
        L39:
            int r4 = scanLinkDestinationWithBalancedParens(r4, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.commonmark.internal.util.LinkScanner.scanLinkDestination(java.lang.CharSequence, int):int");
    }

    public static int scanLinkTitle(CharSequence charSequence, int i) {
        if (i >= charSequence.length()) {
            return -1;
        }
        char cCharAt = charSequence.charAt(i);
        char c2 = Typography.quote;
        if (cCharAt != '\"') {
            c2 = '\'';
            if (cCharAt != '\'') {
                if (cCharAt != '(') {
                    return -1;
                }
                c2 = ')';
            }
        }
        int iScanLinkTitleContent = scanLinkTitleContent(charSequence, i + 1, c2);
        if (iScanLinkTitleContent != -1 && iScanLinkTitleContent < charSequence.length() && charSequence.charAt(iScanLinkTitleContent) == c2) {
            return iScanLinkTitleContent + 1;
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int scanLinkTitleContent(java.lang.CharSequence r3, int r4, char r5) {
        /*
        L0:
            int r0 = r3.length()
            if (r4 >= r0) goto L28
            char r0 = r3.charAt(r4)
            r1 = 92
            if (r0 != r1) goto L18
            int r1 = r4 + 1
            boolean r2 = org.commonmark.internal.util.Parsing.isEscapable(r3, r1)
            if (r2 == 0) goto L18
            r4 = r1
            goto L25
        L18:
            if (r0 != r5) goto L1b
            return r4
        L1b:
            r1 = 41
            if (r5 != r1) goto L25
            r1 = 40
            if (r0 != r1) goto L25
            r3 = -1
            return r3
        L25:
            int r4 = r4 + 1
            goto L0
        L28:
            int r3 = r3.length()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.commonmark.internal.util.LinkScanner.scanLinkTitleContent(java.lang.CharSequence, int, char):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0042 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int scanLinkDestinationWithBalancedParens(java.lang.CharSequence r6, int r7) {
        /*
            r0 = 0
            r1 = r7
        L2:
            int r2 = r6.length()
            if (r1 >= r2) goto L44
            char r2 = r6.charAt(r1)
            r3 = -1
            if (r2 == 0) goto L40
            r4 = 32
            if (r2 == r4) goto L40
            r5 = 92
            if (r2 == r5) goto L34
            r5 = 40
            if (r2 == r5) goto L2f
            r4 = 41
            if (r2 == r4) goto L29
            boolean r2 = java.lang.Character.isISOControl(r2)
            if (r2 == 0) goto L3d
            if (r1 == r7) goto L28
            goto L42
        L28:
            return r3
        L29:
            if (r0 != 0) goto L2c
            goto L42
        L2c:
            int r0 = r0 + (-1)
            goto L3d
        L2f:
            int r0 = r0 + 1
            if (r0 <= r4) goto L3d
            return r3
        L34:
            int r2 = r1 + 1
            boolean r3 = org.commonmark.internal.util.Parsing.isEscapable(r6, r2)
            if (r3 == 0) goto L3d
            r1 = r2
        L3d:
            int r1 = r1 + 1
            goto L2
        L40:
            if (r1 == r7) goto L43
        L42:
            return r1
        L43:
            return r3
        L44:
            int r6 = r6.length()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.commonmark.internal.util.LinkScanner.scanLinkDestinationWithBalancedParens(java.lang.CharSequence, int):int");
    }
}
