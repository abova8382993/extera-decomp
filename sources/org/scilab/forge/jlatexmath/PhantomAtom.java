package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class PhantomAtom extends Atom implements Row {

    /* JADX INFO: renamed from: d */
    private boolean f1100d;
    private RowAtom elements;

    /* JADX INFO: renamed from: h */
    private boolean f1101h;

    /* JADX INFO: renamed from: w */
    private boolean f1102w;

    public PhantomAtom(Atom atom) {
        this.f1102w = true;
        this.f1101h = true;
        this.f1100d = true;
        if (atom == null) {
            this.elements = new RowAtom();
        } else {
            this.elements = new RowAtom(atom);
        }
    }

    public PhantomAtom(Atom atom, boolean z, boolean z2, boolean z3) {
        this(atom);
        this.f1102w = z;
        this.f1101h = z2;
        this.f1100d = z3;
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public Box createBox(TeXEnvironment teXEnvironment) {
        Box boxCreateBox = this.elements.createBox(teXEnvironment);
        return new StrutBox(this.f1102w ? boxCreateBox.getWidth() : 0.0f, this.f1101h ? boxCreateBox.getHeight() : 0.0f, this.f1100d ? boxCreateBox.getDepth() : 0.0f, boxCreateBox.getShift());
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public int getLeftType() {
        return this.elements.getLeftType();
    }

    @Override // org.scilab.forge.jlatexmath.Atom
    public int getRightType() {
        return this.elements.getRightType();
    }

    @Override // org.scilab.forge.jlatexmath.Row
    public void setPreviousAtom(Dummy dummy) {
        this.elements.setPreviousAtom(dummy);
    }
}
