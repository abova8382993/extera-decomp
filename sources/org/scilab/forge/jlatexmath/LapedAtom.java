package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class LapedAtom extends Atom {

    /* JADX INFO: renamed from: at */
    private Atom f1092at;
    private char type;

    public LapedAtom(Atom atom, char c2) {
        this.f1092at = atom;
        this.type = c2;
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public Box createBox(TeXEnvironment teXEnvironment) {
        Box boxCreateBox = this.f1092at.createBox(teXEnvironment);
        VerticalBox verticalBox = new VerticalBox();
        verticalBox.add(boxCreateBox);
        verticalBox.setWidth(0.0f);
        char c2 = this.type;
        if (c2 == 'l') {
            boxCreateBox.setShift(-boxCreateBox.getWidth());
            return verticalBox;
        }
        if (c2 == 'r') {
            boxCreateBox.setShift(0.0f);
            return verticalBox;
        }
        boxCreateBox.setShift((-boxCreateBox.getWidth()) / 2.0f);
        return verticalBox;
    }
}
