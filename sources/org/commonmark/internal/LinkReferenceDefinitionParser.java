package org.commonmark.internal;

import java.util.ArrayList;
import java.util.List;
import org.commonmark.internal.util.Escaping;
import org.commonmark.internal.util.LinkScanner;
import org.commonmark.internal.util.Parsing;
import org.commonmark.node.LinkReferenceDefinition;

/* JADX INFO: loaded from: classes5.dex */
public class LinkReferenceDefinitionParser {
    private String destination;
    private StringBuilder label;
    private String normalizedLabel;
    private StringBuilder title;
    private char titleDelimiter;
    private State state = State.START_DEFINITION;
    private final StringBuilder paragraph = new StringBuilder();
    private final List<LinkReferenceDefinition> definitions = new ArrayList();
    private boolean referenceValid = false;

    public enum State {
        START_DEFINITION,
        LABEL,
        DESTINATION,
        START_TITLE,
        TITLE,
        PARAGRAPH
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0044 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[LOOP:0: B:6:0x0015->B:23:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parse(java.lang.CharSequence r4) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = r3.paragraph
            int r0 = r0.length()
            if (r0 == 0) goto Lf
            java.lang.StringBuilder r0 = r3.paragraph
            r1 = 10
            r0.append(r1)
        Lf:
            java.lang.StringBuilder r0 = r3.paragraph
            r0.append(r4)
            r0 = 0
        L15:
            int r1 = r4.length()
            if (r0 >= r1) goto L48
            int[] r1 = org.commonmark.internal.LinkReferenceDefinitionParser.C25661.f1047x496a1d35
            org.commonmark.internal.LinkReferenceDefinitionParser$State r2 = r3.state
            int r2 = r2.ordinal()
            r1 = r1[r2]
            switch(r1) {
                case 1: goto L48;
                case 2: goto L3d;
                case 3: goto L38;
                case 4: goto L33;
                case 5: goto L2e;
                case 6: goto L29;
                default: goto L28;
            }
        L28:
            goto L41
        L29:
            int r0 = r3.title(r4, r0)
            goto L41
        L2e:
            int r0 = r3.startTitle(r4, r0)
            goto L41
        L33:
            int r0 = r3.destination(r4, r0)
            goto L41
        L38:
            int r0 = r3.label(r4, r0)
            goto L41
        L3d:
            int r0 = r3.startDefinition(r4, r0)
        L41:
            r1 = -1
            if (r0 != r1) goto L15
            org.commonmark.internal.LinkReferenceDefinitionParser$State r4 = org.commonmark.internal.LinkReferenceDefinitionParser.State.PARAGRAPH
            r3.state = r4
        L48:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.commonmark.internal.LinkReferenceDefinitionParser.parse(java.lang.CharSequence):void");
    }

    /* JADX INFO: renamed from: org.commonmark.internal.LinkReferenceDefinitionParser$1 */
    public static /* synthetic */ class C25661 {

        /* JADX INFO: renamed from: $SwitchMap$org$commonmark$internal$LinkReferenceDefinitionParser$State */
        static final /* synthetic */ int[] f1047x496a1d35;

        static {
            int[] iArr = new int[State.values().length];
            f1047x496a1d35 = iArr;
            try {
                iArr[State.PARAGRAPH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1047x496a1d35[State.START_DEFINITION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1047x496a1d35[State.LABEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1047x496a1d35[State.DESTINATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1047x496a1d35[State.START_TITLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1047x496a1d35[State.TITLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public CharSequence getParagraphContent() {
        return this.paragraph;
    }

    public List<LinkReferenceDefinition> getDefinitions() {
        finishReference();
        return this.definitions;
    }

    private int startDefinition(CharSequence charSequence, int i) {
        int iSkipSpaceTab = Parsing.skipSpaceTab(charSequence, i, charSequence.length());
        if (iSkipSpaceTab >= charSequence.length() || charSequence.charAt(iSkipSpaceTab) != '[') {
            return -1;
        }
        this.state = State.LABEL;
        this.label = new StringBuilder();
        int i2 = iSkipSpaceTab + 1;
        if (i2 >= charSequence.length()) {
            this.label.append('\n');
        }
        return i2;
    }

    private int label(CharSequence charSequence, int i) {
        int i2;
        int iScanLinkLabelContent = LinkScanner.scanLinkLabelContent(charSequence, i);
        if (iScanLinkLabelContent == -1) {
            return -1;
        }
        this.label.append(charSequence, i, iScanLinkLabelContent);
        if (iScanLinkLabelContent >= charSequence.length()) {
            this.label.append('\n');
            return iScanLinkLabelContent;
        }
        if (charSequence.charAt(iScanLinkLabelContent) != ']' || (i2 = iScanLinkLabelContent + 1) >= charSequence.length() || charSequence.charAt(i2) != ':' || this.label.length() > 999) {
            return -1;
        }
        String strNormalizeLabelContent = Escaping.normalizeLabelContent(this.label.toString());
        if (strNormalizeLabelContent.isEmpty()) {
            return -1;
        }
        this.normalizedLabel = strNormalizeLabelContent;
        this.state = State.DESTINATION;
        return Parsing.skipSpaceTab(charSequence, iScanLinkLabelContent + 2, charSequence.length());
    }

    private int destination(CharSequence charSequence, int i) {
        String string;
        int iSkipSpaceTab = Parsing.skipSpaceTab(charSequence, i, charSequence.length());
        int iScanLinkDestination = LinkScanner.scanLinkDestination(charSequence, iSkipSpaceTab);
        if (iScanLinkDestination == -1) {
            return -1;
        }
        if (charSequence.charAt(iSkipSpaceTab) == '<') {
            string = charSequence.subSequence(iSkipSpaceTab + 1, iScanLinkDestination - 1).toString();
        } else {
            string = charSequence.subSequence(iSkipSpaceTab, iScanLinkDestination).toString();
        }
        this.destination = string;
        int iSkipSpaceTab2 = Parsing.skipSpaceTab(charSequence, iScanLinkDestination, charSequence.length());
        if (iSkipSpaceTab2 >= charSequence.length()) {
            this.referenceValid = true;
            this.paragraph.setLength(0);
        } else if (iSkipSpaceTab2 == iScanLinkDestination) {
            return -1;
        }
        this.state = State.START_TITLE;
        return iSkipSpaceTab2;
    }

    private int startTitle(CharSequence charSequence, int i) {
        int iSkipSpaceTab = Parsing.skipSpaceTab(charSequence, i, charSequence.length());
        if (iSkipSpaceTab >= charSequence.length()) {
            this.state = State.START_DEFINITION;
            return iSkipSpaceTab;
        }
        this.titleDelimiter = (char) 0;
        char cCharAt = charSequence.charAt(iSkipSpaceTab);
        if (cCharAt == '\"' || cCharAt == '\'') {
            this.titleDelimiter = cCharAt;
        } else if (cCharAt == '(') {
            this.titleDelimiter = ')';
        }
        if (this.titleDelimiter != 0) {
            this.state = State.TITLE;
            this.title = new StringBuilder();
            int i2 = iSkipSpaceTab + 1;
            if (i2 == charSequence.length()) {
                this.title.append('\n');
            }
            return i2;
        }
        finishReference();
        this.state = State.START_DEFINITION;
        return iSkipSpaceTab;
    }

    private int title(CharSequence charSequence, int i) {
        int iScanLinkTitleContent = LinkScanner.scanLinkTitleContent(charSequence, i, this.titleDelimiter);
        if (iScanLinkTitleContent == -1) {
            return -1;
        }
        this.title.append(charSequence.subSequence(i, iScanLinkTitleContent));
        if (iScanLinkTitleContent >= charSequence.length()) {
            this.title.append('\n');
            return iScanLinkTitleContent;
        }
        int iSkipSpaceTab = Parsing.skipSpaceTab(charSequence, iScanLinkTitleContent + 1, charSequence.length());
        if (iSkipSpaceTab != charSequence.length()) {
            return -1;
        }
        this.referenceValid = true;
        finishReference();
        this.paragraph.setLength(0);
        this.state = State.START_DEFINITION;
        return iSkipSpaceTab;
    }

    private void finishReference() {
        if (this.referenceValid) {
            String strUnescapeString = Escaping.unescapeString(this.destination);
            StringBuilder sb = this.title;
            this.definitions.add(new LinkReferenceDefinition(this.normalizedLabel, strUnescapeString, sb != null ? Escaping.unescapeString(sb.toString()) : null));
            this.label = null;
            this.referenceValid = false;
            this.normalizedLabel = null;
            this.destination = null;
            this.title = null;
        }
    }
}
