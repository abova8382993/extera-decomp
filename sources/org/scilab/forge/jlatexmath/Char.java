package org.scilab.forge.jlatexmath;

import ru.noties.jlatexmath.awt.Font;

/* JADX INFO: loaded from: classes5.dex */
public class Char {

    /* JADX INFO: renamed from: c */
    private final char f1077c;
    private final Font font;
    private final int fontCode;

    /* JADX INFO: renamed from: m */
    private final Metrics f1078m;

    public Char(char c2, Font font, int i, Metrics metrics) {
        this.font = font;
        this.fontCode = i;
        this.f1077c = c2;
        this.f1078m = metrics;
    }

    public CharFont getCharFont() {
        return new CharFont(this.f1077c, this.fontCode);
    }

    public char getChar() {
        return this.f1077c;
    }

    public Font getFont() {
        return this.font;
    }

    public int getFontCode() {
        return this.fontCode;
    }

    public float getWidth() {
        return this.f1078m.getWidth();
    }

    public float getItalic() {
        return this.f1078m.getItalic();
    }

    public float getHeight() {
        return this.f1078m.getHeight();
    }

    public float getDepth() {
        return this.f1078m.getDepth();
    }

    public Metrics getMetrics() {
        return this.f1078m;
    }
}
