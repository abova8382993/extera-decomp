package org.scilab.forge.jlatexmath;

/* JADX INFO: loaded from: classes5.dex */
public class Dummy {

    /* JADX INFO: renamed from: el */
    private Atom f1083el;
    private boolean textSymbol = false;
    private int type = -1;

    public Dummy(Atom atom) {
        this.f1083el = atom;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getType() {
        return this.type;
    }

    public int getLeftType() {
        int i = this.type;
        return i >= 0 ? i : this.f1083el.getLeftType();
    }

    public int getRightType() {
        int i = this.type;
        return i >= 0 ? i : this.f1083el.getRightType();
    }

    public boolean isCharSymbol() {
        return this.f1083el instanceof CharSymbol;
    }

    public boolean isCharInMathMode() {
        Atom atom = this.f1083el;
        return (atom instanceof CharAtom) && ((CharAtom) atom).isMathMode();
    }

    public CharFont getCharFont(TeXFont teXFont) {
        return ((CharSymbol) this.f1083el).getCharFont(teXFont);
    }

    public void changeAtom(FixedCharAtom fixedCharAtom) {
        this.textSymbol = false;
        this.type = -1;
        this.f1083el = fixedCharAtom;
    }

    public Box createBox(TeXEnvironment teXEnvironment) {
        if (this.textSymbol) {
            ((CharSymbol) this.f1083el).markAsTextSymbol();
        }
        Box boxCreateBox = this.f1083el.createBox(teXEnvironment);
        if (this.textSymbol) {
            ((CharSymbol) this.f1083el).removeMark();
        }
        return boxCreateBox;
    }

    public void markAsTextSymbol() {
        this.textSymbol = true;
    }

    public boolean isKern() {
        return this.f1083el instanceof SpaceAtom;
    }

    public void setPreviousAtom(Dummy dummy) {
        Cloneable cloneable = this.f1083el;
        if (cloneable instanceof Row) {
            ((Row) cloneable).setPreviousAtom(dummy);
        }
    }
}
