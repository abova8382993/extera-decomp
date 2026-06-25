package io.noties.markwon.inlineparser;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes5.dex */
public class CloseBracketInlineProcessor extends InlineProcessor {
    private static final Pattern WHITESPACE = MarkwonInlineParser.WHITESPACE;

    @Override // io.noties.markwon.inlineparser.InlineProcessor
    public char specialCharacter() {
        return ']';
    }

    /* JADX WARN: Removed duplicated region for block: B:94:0x00aa  */
    @Override // io.noties.markwon.inlineparser.InlineProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.commonmark.node.Node parse() {
        /*
            Method dump skipped, instruction units count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.noties.markwon.inlineparser.CloseBracketInlineProcessor.parse():org.commonmark.node.Node");
    }
}
