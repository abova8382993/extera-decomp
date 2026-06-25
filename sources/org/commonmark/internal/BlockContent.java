package org.commonmark.internal;

/* JADX INFO: loaded from: classes5.dex */
class BlockContent {
    private int lineCount = 0;

    /* JADX INFO: renamed from: sb */
    private final StringBuilder f1046sb = new StringBuilder();

    public void add(CharSequence charSequence) {
        if (this.lineCount != 0) {
            this.f1046sb.append('\n');
        }
        this.f1046sb.append(charSequence);
        this.lineCount++;
    }

    public String getString() {
        return this.f1046sb.toString();
    }
}
