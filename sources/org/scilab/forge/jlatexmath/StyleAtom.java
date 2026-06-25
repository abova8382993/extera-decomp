package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class StyleAtom extends Atom {

    /* JADX INFO: renamed from: at */
    private Atom f1127at;
    private int style;

    public StyleAtom(int i, Atom atom) {
        this.style = i;
        this.f1127at = atom;
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public Box createBox(TeXEnvironment teXEnvironment) {
        int style = teXEnvironment.getStyle();
        teXEnvironment.setStyle(this.style);
        Box boxCreateBox = this.f1127at.createBox(teXEnvironment);
        teXEnvironment.setStyle(style);
        return boxCreateBox;
    }
}
