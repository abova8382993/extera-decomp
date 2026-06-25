package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class FixedCharAtom extends CharSymbol {

    /* JADX INFO: renamed from: cf */
    private final CharFont f1087cf;

    public FixedCharAtom(CharFont charFont) {
        this.f1087cf = charFont;
    }

    @Override // org.scilab.forge.jlatexmath.CharSymbol
    public CharFont getCharFont(TeXFont teXFont) {
        return this.f1087cf;
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public Box createBox(TeXEnvironment teXEnvironment) {
        return new CharBox(teXEnvironment.getTeXFont().getChar(this.f1087cf, teXEnvironment.getStyle()));
    }
}
