package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class TextStyleAtom extends Atom {

    /* JADX INFO: renamed from: at */
    private Atom f1131at;
    private String style;

    public TextStyleAtom(Atom atom, String str) {
        this.style = str;
        this.f1131at = atom;
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public Box createBox(TeXEnvironment teXEnvironment) {
        String textStyle = teXEnvironment.getTextStyle();
        teXEnvironment.setTextStyle(this.style);
        Box boxCreateBox = this.f1131at.createBox(teXEnvironment);
        teXEnvironment.setTextStyle(textStyle);
        return boxCreateBox;
    }
}
