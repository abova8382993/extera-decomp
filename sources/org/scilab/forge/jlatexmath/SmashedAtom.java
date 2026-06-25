package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class SmashedAtom extends Atom {

    /* JADX INFO: renamed from: at */
    private Atom f1123at;

    /* JADX INFO: renamed from: d */
    private boolean f1124d;

    /* JADX INFO: renamed from: h */
    private boolean f1125h;

    public SmashedAtom(Atom atom, String str) {
        this.f1125h = true;
        this.f1124d = true;
        this.f1123at = atom;
        if ("t".equals(str)) {
            this.f1124d = false;
        } else if ("b".equals(str)) {
            this.f1125h = false;
        }
    }

    public SmashedAtom(Atom atom) {
        this.f1125h = true;
        this.f1124d = true;
        this.f1123at = atom;
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public Box createBox(TeXEnvironment teXEnvironment) {
        Box boxCreateBox = this.f1123at.createBox(teXEnvironment);
        if (this.f1125h) {
            boxCreateBox.setHeight(0.0f);
        }
        if (this.f1124d) {
            boxCreateBox.setDepth(0.0f);
        }
        return boxCreateBox;
    }
}
