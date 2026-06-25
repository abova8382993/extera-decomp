package org.commonmark.parser.block;

import org.commonmark.internal.BlockStartImpl;

/* JADX INFO: loaded from: classes5.dex */
public abstract class BlockStart {
    public static BlockStart none() {
        return null;
    }

    public abstract BlockStart atColumn(int i);

    public abstract BlockStart atIndex(int i);

    public abstract BlockStart replaceActiveBlockParser();

    /* JADX INFO: renamed from: of */
    public static BlockStart m1001of(BlockParser... blockParserArr) {
        return new BlockStartImpl(blockParserArr);
    }
}
