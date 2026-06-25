package org.commonmark.internal;

import org.commonmark.internal.util.Parsing;
import org.commonmark.node.Block;
import org.commonmark.node.BulletList;
import org.commonmark.node.ListBlock;
import org.commonmark.node.ListItem;
import org.commonmark.node.OrderedList;
import org.commonmark.parser.block.AbstractBlockParser;
import org.commonmark.parser.block.AbstractBlockParserFactory;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.BlockParser;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;

/* JADX INFO: loaded from: classes5.dex */
public class ListBlockParser extends AbstractBlockParser {
    private final ListBlock block;
    private boolean hadBlankLine;
    private int linesAfterBlank;

    @Override // org.commonmark.parser.block.AbstractBlockParser, org.commonmark.parser.block.BlockParser
    public boolean isContainer() {
        return true;
    }

    public ListBlockParser(ListBlock listBlock) {
        this.block = listBlock;
    }

    @Override // org.commonmark.parser.block.AbstractBlockParser, org.commonmark.parser.block.BlockParser
    public boolean canContain(Block block) {
        if (!(block instanceof ListItem)) {
            return false;
        }
        if (this.hadBlankLine && this.linesAfterBlank == 1) {
            this.block.setTight(false);
            this.hadBlankLine = false;
        }
        return true;
    }

    @Override // org.commonmark.parser.block.BlockParser
    public Block getBlock() {
        return this.block;
    }

    @Override // org.commonmark.parser.block.BlockParser
    public BlockContinue tryContinue(ParserState parserState) {
        if (parserState.isBlank()) {
            this.hadBlankLine = true;
            this.linesAfterBlank = 0;
        } else if (this.hadBlankLine) {
            this.linesAfterBlank++;
        }
        return BlockContinue.atIndex(parserState.getIndex());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ListData parseList(CharSequence charSequence, int i, int i2, boolean z) {
        boolean z2;
        ListMarkerData listMarker = parseListMarker(charSequence, i);
        if (listMarker == null) {
            return null;
        }
        ListBlock listBlock = listMarker.listBlock;
        int i3 = listMarker.indexAfterMarker;
        int i4 = i2 + (i3 - i);
        int length = charSequence.length();
        int iColumnsToNextTabStop = i4;
        while (true) {
            if (i3 >= length) {
                z2 = false;
                break;
            }
            char cCharAt = charSequence.charAt(i3);
            if (cCharAt != '\t') {
                if (cCharAt != ' ') {
                    z2 = true;
                    break;
                }
                iColumnsToNextTabStop++;
            } else {
                iColumnsToNextTabStop += Parsing.columnsToNextTabStop(iColumnsToNextTabStop);
            }
            i3++;
        }
        if (z && (((listBlock instanceof OrderedList) && ((OrderedList) listBlock).getStartNumber() != 1) || !z2)) {
            return null;
        }
        if (!z2 || iColumnsToNextTabStop - i4 > Parsing.CODE_BLOCK_INDENT) {
            iColumnsToNextTabStop = i4 + 1;
        }
        return new ListData(listBlock, iColumnsToNextTabStop);
    }

    private static ListMarkerData parseListMarker(CharSequence charSequence, int i) {
        char cCharAt = charSequence.charAt(i);
        if (cCharAt == '*' || cCharAt == '+' || cCharAt == '-') {
            int i2 = i + 1;
            if (!isSpaceTabOrEnd(charSequence, i2)) {
                return null;
            }
            BulletList bulletList = new BulletList();
            bulletList.setBulletMarker(cCharAt);
            return new ListMarkerData(bulletList, i2);
        }
        return parseOrderedList(charSequence, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0009  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static org.commonmark.internal.ListBlockParser.ListMarkerData parseOrderedList(java.lang.CharSequence r6, int r7) {
        /*
            int r0 = r6.length()
            r1 = 0
            r2 = r7
        L6:
            r3 = 0
            if (r2 >= r0) goto L4b
            char r4 = r6.charAt(r2)
            r5 = 41
            if (r4 == r5) goto L23
            r5 = 46
            if (r4 == r5) goto L23
            switch(r4) {
                case 48: goto L19;
                case 49: goto L19;
                case 50: goto L19;
                case 51: goto L19;
                case 52: goto L19;
                case 53: goto L19;
                case 54: goto L19;
                case 55: goto L19;
                case 56: goto L19;
                case 57: goto L19;
                default: goto L18;
            }
        L18:
            return r3
        L19:
            int r1 = r1 + 1
            r4 = 9
            if (r1 <= r4) goto L20
            return r3
        L20:
            int r2 = r2 + 1
            goto L6
        L23:
            r0 = 1
            if (r1 < r0) goto L4b
            int r0 = r2 + 1
            boolean r1 = isSpaceTabOrEnd(r6, r0)
            if (r1 == 0) goto L4b
            java.lang.CharSequence r6 = r6.subSequence(r7, r2)
            java.lang.String r6 = r6.toString()
            org.commonmark.node.OrderedList r7 = new org.commonmark.node.OrderedList
            r7.<init>()
            int r6 = java.lang.Integer.parseInt(r6)
            r7.setStartNumber(r6)
            r7.setDelimiter(r4)
            org.commonmark.internal.ListBlockParser$ListMarkerData r6 = new org.commonmark.internal.ListBlockParser$ListMarkerData
            r6.<init>(r7, r0)
            return r6
        L4b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.commonmark.internal.ListBlockParser.parseOrderedList(java.lang.CharSequence, int):org.commonmark.internal.ListBlockParser$ListMarkerData");
    }

    private static boolean isSpaceTabOrEnd(CharSequence charSequence, int i) {
        char cCharAt;
        return i >= charSequence.length() || (cCharAt = charSequence.charAt(i)) == '\t' || cCharAt == ' ';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean listsMatch(ListBlock listBlock, ListBlock listBlock2) {
        if ((listBlock instanceof BulletList) && (listBlock2 instanceof BulletList)) {
            return equals(Character.valueOf(((BulletList) listBlock).getBulletMarker()), Character.valueOf(((BulletList) listBlock2).getBulletMarker()));
        }
        if ((listBlock instanceof OrderedList) && (listBlock2 instanceof OrderedList)) {
            return equals(Character.valueOf(((OrderedList) listBlock).getDelimiter()), Character.valueOf(((OrderedList) listBlock2).getDelimiter()));
        }
        return false;
    }

    private static boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public static class Factory extends AbstractBlockParserFactory {
        @Override // org.commonmark.parser.block.BlockParserFactory
        public BlockStart tryStart(ParserState parserState, MatchedBlockParser matchedBlockParser) {
            BlockParser matchedBlockParser2 = matchedBlockParser.getMatchedBlockParser();
            if (parserState.getIndent() < Parsing.CODE_BLOCK_INDENT) {
                ListData list = ListBlockParser.parseList(parserState.getLine(), parserState.getNextNonSpaceIndex(), parserState.getColumn() + parserState.getIndent(), matchedBlockParser.getParagraphContent() != null);
                if (list == null) {
                    return BlockStart.none();
                }
                int i = list.contentColumn;
                ListItemParser listItemParser = new ListItemParser(i - parserState.getColumn());
                if ((matchedBlockParser2 instanceof ListBlockParser) && ListBlockParser.listsMatch((ListBlock) matchedBlockParser2.getBlock(), list.listBlock)) {
                    return BlockStart.m1001of(listItemParser).atColumn(i);
                }
                ListBlockParser listBlockParser = new ListBlockParser(list.listBlock);
                list.listBlock.setTight(true);
                return BlockStart.m1001of(listBlockParser, listItemParser).atColumn(i);
            }
            return BlockStart.none();
        }
    }

    public static class ListData {
        final int contentColumn;
        final ListBlock listBlock;

        public ListData(ListBlock listBlock, int i) {
            this.listBlock = listBlock;
            this.contentColumn = i;
        }
    }

    public static class ListMarkerData {
        final int indexAfterMarker;
        final ListBlock listBlock;

        public ListMarkerData(ListBlock listBlock, int i) {
            this.listBlock = listBlock;
            this.indexAfterMarker = i;
        }
    }
}
