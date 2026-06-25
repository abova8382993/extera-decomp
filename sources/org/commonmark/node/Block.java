package org.commonmark.node;

import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Block extends Node {
    @Override // org.commonmark.node.Node
    public Block getParent() {
        return (Block) super.getParent();
    }

    @Override // org.commonmark.node.Node
    public void setParent(Node node) {
        if (!(node instanceof Block)) {
            g$$ExternalSyntheticBUOutline1.m207m("Parent of block must also be block (can not be inline)");
        } else {
            super.setParent(node);
        }
    }
}
