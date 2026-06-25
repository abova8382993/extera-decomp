package org.scilab.forge.jlatexmath;

import java.lang.Character;
import java.util.HashSet;
import java.util.Set;
import okhttp3.internal.url._UrlKt;
import org.scilab.forge.jlatexmath.TeXFormula;
import ru.noties.jlatexmath.awt.Color;

/* JADX INFO: loaded from: classes5.dex */
public class TeXParser {
    private static final char BACKPRIME = 8245;
    private static final char DEGRE = 176;
    private static final char DOLLAR = '$';
    private static final char DQUOTE = '\"';
    private static final char ESCAPE = '\\';
    private static final char L_BRACK = '[';
    private static final char L_GROUP = '{';
    private static final char PERCENT = '%';
    private static final char PRIME = '\'';
    private static final char R_BRACK = ']';
    private static final char R_GROUP = '}';
    private static final char SUBEIGHT = 8328;
    private static final char SUBEQUAL = 8332;
    private static final char SUBFIVE = 8325;
    private static final char SUBFOUR = 8324;
    private static final char SUBLPAR = 8333;
    private static final char SUBMINUS = 8331;
    private static final char SUBNINE = 8329;
    private static final char SUBONE = 8321;
    private static final char SUBPLUS = 8330;
    private static final char SUBRPAR = 8334;
    private static final char SUBSEVEN = 8327;
    private static final char SUBSIX = 8326;
    private static final char SUBTHREE = 8323;
    private static final char SUBTWO = 8322;
    private static final char SUBZERO = 8320;
    private static final char SUB_SCRIPT = '_';
    private static final char SUPEIGHT = 8312;
    private static final char SUPEQUAL = 8316;
    private static final char SUPER_SCRIPT = '^';
    private static final char SUPFIVE = 8309;
    private static final char SUPFOUR = 8308;
    private static final char SUPLPAR = 8317;
    private static final char SUPMINUS = 8315;
    private static final char SUPN = 8319;
    private static final char SUPNINE = 8313;
    private static final char SUPONE = 185;
    private static final char SUPPLUS = 8314;
    private static final char SUPRPAR = 8318;
    private static final char SUPSEVEN = 8311;
    private static final char SUPSIX = 8310;
    private static final char SUPTHREE = 179;
    private static final char SUPTWO = 178;
    private static final char SUPZERO = 8304;
    protected static boolean isLoading = false;
    private static final Set<String> unparsedContents;
    private boolean arrayMode;
    private int atIsLetter;
    private int col;
    TeXFormula formula;
    private int group;
    private boolean ignoreWhiteSpace;
    private boolean insertion;
    private boolean isPartial;
    private int len;
    private int line;
    private StringBuffer parseString;
    private int pos;
    private int spos;

    private static char convertToRomanNumber(char c2) {
        int i;
        if (c2 == 1643) {
            return '.';
        }
        if (1632 <= c2 && c2 <= 1641) {
            i = c2 - 1584;
        } else if (1776 <= c2 && c2 <= 1785) {
            i = c2 - 1728;
        } else if (2406 <= c2 && c2 <= 2415) {
            i = c2 - 2358;
        } else if (2534 <= c2 && c2 <= 2543) {
            i = c2 - 2486;
        } else if (2662 <= c2 && c2 <= 2671) {
            i = c2 - 2614;
        } else if (2790 <= c2 && c2 <= 2799) {
            i = c2 - 2742;
        } else if (2918 <= c2 && c2 <= 2927) {
            i = c2 - 2870;
        } else if (3174 <= c2 && c2 <= 3183) {
            i = c2 - 3126;
        } else if (3430 <= c2 && c2 <= 3439) {
            i = c2 - 3382;
        } else if (3664 <= c2 && c2 <= 3673) {
            i = c2 - 3616;
        } else if (3792 <= c2 && c2 <= 3801) {
            i = c2 - 3744;
        } else if (3872 <= c2 && c2 <= 3881) {
            i = c2 - 3728;
        } else if (4160 <= c2 && c2 <= 4169) {
            i = c2 - 4112;
        } else if (6112 <= c2 && c2 <= 6121) {
            i = c2 - 6064;
        } else if (6160 <= c2 && c2 <= 6169) {
            i = c2 - 6112;
        } else if (6992 <= c2 && c2 <= 7001) {
            i = c2 - 6944;
        } else if (7088 <= c2 && c2 <= 7097) {
            i = c2 - 7040;
        } else if (7232 <= c2 && c2 <= 7241) {
            i = c2 - 7184;
        } else if (7248 <= c2 && c2 <= 7257) {
            i = c2 - 7200;
        } else {
            if (43216 > c2 || c2 > 43225) {
                return c2;
            }
            i = c2 - 43168;
        }
        return (char) i;
    }

    static {
        HashSet hashSet = new HashSet(6);
        unparsedContents = hashSet;
        hashSet.add("jlmDynamic");
        hashSet.add("jlmText");
        hashSet.add("jlmTextit");
        hashSet.add("jlmTextbf");
        hashSet.add("jlmTextitbf");
        hashSet.add("jlmExternalFont");
    }

    public TeXParser(String str, TeXFormula teXFormula) {
        this(str, teXFormula, true);
    }

    public TeXParser(boolean z, String str, TeXFormula teXFormula) {
        this(str, teXFormula, false);
        this.isPartial = z;
        firstpass();
    }

    public TeXParser(boolean z, String str, TeXFormula teXFormula, boolean z2) {
        this.ignoreWhiteSpace = true;
        this.formula = teXFormula;
        this.isPartial = z;
        if (str != null) {
            this.parseString = new StringBuffer(str);
            this.len = str.length();
            this.pos = 0;
            if (z2) {
                firstpass();
                return;
            }
            return;
        }
        this.parseString = null;
        this.pos = 0;
        this.len = 0;
    }

    public TeXParser(String str, TeXFormula teXFormula, boolean z) {
        this(false, str, teXFormula, z);
    }

    public TeXParser(boolean z, String str, ArrayOfAtoms arrayOfAtoms, boolean z2) {
        this(z, str, (TeXFormula) arrayOfAtoms, z2);
        this.arrayMode = true;
    }

    public TeXParser(boolean z, String str, ArrayOfAtoms arrayOfAtoms, boolean z2, boolean z3) {
        this(z, str, (TeXFormula) arrayOfAtoms, z2, z3);
        this.arrayMode = true;
    }

    public TeXParser(String str, ArrayOfAtoms arrayOfAtoms, boolean z) {
        this(false, str, (TeXFormula) arrayOfAtoms, z);
    }

    public TeXParser(boolean z, String str, TeXFormula teXFormula, boolean z2, boolean z3) {
        this(z, str, teXFormula, z2);
        this.ignoreWhiteSpace = z3;
    }

    public TeXParser(String str, TeXFormula teXFormula, boolean z, boolean z2) {
        this(false, str, teXFormula, z);
        this.ignoreWhiteSpace = z2;
    }

    public void reset(String str) {
        StringBuffer stringBuffer = new StringBuffer(str);
        this.parseString = stringBuffer;
        this.len = stringBuffer.length();
        this.formula.root = null;
        this.pos = 0;
        this.spos = 0;
        this.line = 0;
        this.col = 0;
        this.group = 0;
        this.insertion = false;
        this.atIsLetter = 0;
        this.arrayMode = false;
        this.ignoreWhiteSpace = true;
        firstpass();
    }

    public boolean getIsPartial() {
        return this.isPartial;
    }

    public int getLine() {
        return this.line;
    }

    public int getCol() {
        return (this.pos - this.col) - 1;
    }

    public Atom getLastAtom() {
        TeXFormula teXFormula = this.formula;
        Atom atom = teXFormula.root;
        if (atom instanceof RowAtom) {
            return ((RowAtom) atom).getLastAtom();
        }
        teXFormula.root = null;
        return atom;
    }

    public Atom getFormulaAtom() {
        TeXFormula teXFormula = this.formula;
        Atom atom = teXFormula.root;
        teXFormula.root = null;
        return atom;
    }

    public void addAtom(Atom atom) {
        this.formula.add(atom);
    }

    public void makeAtLetter() {
        this.atIsLetter++;
    }

    public void makeAtOther() {
        this.atIsLetter--;
    }

    public boolean isAtLetter() {
        return this.atIsLetter != 0;
    }

    public boolean isArrayMode() {
        return this.arrayMode;
    }

    public void setArrayMode(boolean z) {
        this.arrayMode = z;
    }

    public boolean isIgnoreWhiteSpace() {
        return this.ignoreWhiteSpace;
    }

    public boolean isMathMode() {
        return this.ignoreWhiteSpace;
    }

    public int getPos() {
        return this.pos;
    }

    public int rewind(int i) {
        int i2 = this.pos - i;
        this.pos = i2;
        return i2;
    }

    public String getStringFromCurrentPos() {
        return this.parseString.substring(this.pos);
    }

    public void finish() {
        this.pos = this.parseString.length();
    }

    public void addRow() {
        if (!this.arrayMode) {
            TeXParser$$ExternalSyntheticBUOutline0.m1030m("You can add a row only in array mode !");
        } else {
            ((ArrayOfAtoms) this.formula).addRow();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:356:0x0548  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void firstpass() {
        /*
            Method dump skipped, instruction units count: 1446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.scilab.forge.jlatexmath.TeXParser.firstpass():void");
    }

    public void parse() {
        int i;
        boolean z;
        char cCharAt;
        if (this.len != 0) {
            while (true) {
                int i2 = this.pos;
                if (i2 >= this.len) {
                    break;
                }
                char cCharAt2 = this.parseString.charAt(i2);
                if (cCharAt2 != '\t') {
                    if (cCharAt2 == '\n') {
                        this.line++;
                        this.col = this.pos;
                    } else if (cCharAt2 != '\r') {
                        if (cCharAt2 == ' ') {
                            this.pos++;
                            if (!this.ignoreWhiteSpace) {
                                this.formula.add(new SpaceAtom());
                                this.formula.add(new BreakMarkAtom());
                                while (true) {
                                    int i3 = this.pos;
                                    if (i3 >= this.len || (cCharAt = this.parseString.charAt(i3)) != ' ' || cCharAt != '\t' || cCharAt != '\r') {
                                        break;
                                    } else {
                                        this.pos++;
                                    }
                                }
                            }
                        } else if (cCharAt2 == '\"') {
                            boolean z2 = this.ignoreWhiteSpace;
                            TeXFormula teXFormula = this.formula;
                            if (z2) {
                                teXFormula.add(new CumulativeScriptsAtom(getLastAtom(), null, SymbolAtom.get("prime")));
                                this.formula.add(new CumulativeScriptsAtom(getLastAtom(), null, SymbolAtom.get("prime")));
                            } else {
                                teXFormula.add(convertCharacter(PRIME, true));
                                this.formula.add(convertCharacter(PRIME, true));
                            }
                            this.pos++;
                        } else if (cCharAt2 == '$') {
                            int i4 = this.pos + 1;
                            this.pos = i4;
                            if (!this.ignoreWhiteSpace) {
                                if (this.parseString.charAt(i4) == '$') {
                                    this.pos++;
                                    z = true;
                                    i = 0;
                                } else {
                                    i = 2;
                                    z = false;
                                }
                                this.formula.add(new MathAtom(new TeXFormula(this, getDollarGroup('$'), false).root, i));
                                if (z && this.parseString.charAt(this.pos) == '$') {
                                    this.pos++;
                                }
                            }
                        } else if (cCharAt2 == '\\') {
                            Atom atomProcessEscape = processEscape();
                            this.formula.add(atomProcessEscape);
                            if (this.arrayMode && (atomProcessEscape instanceof HlineAtom)) {
                                ((ArrayOfAtoms) this.formula).addRow();
                            }
                            if (this.insertion) {
                                this.insertion = false;
                            }
                        } else if (cCharAt2 == '{') {
                            Atom argument = getArgument();
                            if (argument != null) {
                                argument.type = 0;
                            }
                            this.formula.add(argument);
                        } else if (cCharAt2 == 8245) {
                            boolean z3 = this.ignoreWhiteSpace;
                            TeXFormula teXFormula2 = this.formula;
                            if (z3) {
                                teXFormula2.add(new CumulativeScriptsAtom(getLastAtom(), null, SymbolAtom.get("backprime")));
                            } else {
                                teXFormula2.add(convertCharacter(BACKPRIME, true));
                            }
                            this.pos++;
                        } else if (cCharAt2 != '&') {
                            if (cCharAt2 == '\'') {
                                boolean z4 = this.ignoreWhiteSpace;
                                TeXFormula teXFormula3 = this.formula;
                                if (z4) {
                                    teXFormula3.add(new CumulativeScriptsAtom(getLastAtom(), null, SymbolAtom.get("prime")));
                                } else {
                                    teXFormula3.add(convertCharacter(PRIME, true));
                                }
                                this.pos++;
                            } else if (cCharAt2 == '^') {
                                this.formula.add(getScripts(cCharAt2));
                            } else if (cCharAt2 == '_') {
                                boolean z5 = this.ignoreWhiteSpace;
                                TeXFormula teXFormula4 = this.formula;
                                if (z5) {
                                    teXFormula4.add(getScripts(cCharAt2));
                                } else {
                                    teXFormula4.add(new UnderscoreAtom());
                                    this.pos++;
                                }
                            } else {
                                if (cCharAt2 == '}') {
                                    int i5 = this.group - 1;
                                    this.group = i5;
                                    this.pos++;
                                    if (i5 != -1) {
                                        return;
                                    }
                                    TeXParser$$ExternalSyntheticBUOutline0.m1030m("Found a closing '}' without an opening '{'!");
                                    return;
                                }
                                TeXFormula teXFormula5 = this.formula;
                                if (cCharAt2 == '~') {
                                    teXFormula5.add(new SpaceAtom());
                                    this.pos++;
                                } else {
                                    teXFormula5.add(convertCharacter(cCharAt2, false));
                                    this.pos++;
                                }
                            }
                        } else if (!this.arrayMode) {
                            TeXParser$$ExternalSyntheticBUOutline0.m1030m("Character '&' is only available in array mode !");
                            return;
                        } else {
                            ((ArrayOfAtoms) this.formula).addCol();
                            this.pos++;
                        }
                    }
                }
                this.pos++;
            }
        }
        TeXFormula teXFormula6 = this.formula;
        if (teXFormula6.root != null || this.arrayMode) {
            return;
        }
        teXFormula6.add(new EmptyAtom());
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.scilab.forge.jlatexmath.Atom getScripts(char r9) {
        /*
            r8 = this;
            int r0 = r8.pos
            r1 = 1
            int r0 = r0 + r1
            r8.pos = r0
            org.scilab.forge.jlatexmath.Atom r0 = r8.getArgument()
            int r2 = r8.pos
            int r3 = r8.len
            r4 = 0
            if (r2 >= r3) goto L18
            java.lang.StringBuffer r3 = r8.parseString
            char r2 = r3.charAt(r2)
            goto L19
        L18:
            r2 = r4
        L19:
            r3 = 94
            r5 = 0
            if (r9 != r3) goto L23
            if (r2 != r3) goto L23
        L20:
            r9 = r0
            r0 = r5
            goto L4a
        L23:
            r6 = 95
            if (r9 != r6) goto L33
            if (r2 != r3) goto L33
            int r9 = r8.pos
            int r9 = r9 + r1
            r8.pos = r9
            org.scilab.forge.jlatexmath.Atom r9 = r8.getArgument()
            goto L4a
        L33:
            if (r9 != r3) goto L44
            if (r2 != r6) goto L44
            int r9 = r8.pos
            int r9 = r9 + r1
            r8.pos = r9
            org.scilab.forge.jlatexmath.Atom r9 = r8.getArgument()
            r7 = r0
            r0 = r9
            r9 = r7
            goto L4a
        L44:
            if (r9 != r3) goto L49
            if (r2 == r6) goto L49
            goto L20
        L49:
            r9 = r5
        L4a:
            org.scilab.forge.jlatexmath.TeXFormula r8 = r8.formula
            org.scilab.forge.jlatexmath.Atom r2 = r8.root
            boolean r3 = r2 instanceof org.scilab.forge.jlatexmath.RowAtom
            if (r3 == 0) goto L59
            org.scilab.forge.jlatexmath.RowAtom r2 = (org.scilab.forge.jlatexmath.RowAtom) r2
            org.scilab.forge.jlatexmath.Atom r2 = r2.getLastAtom()
            goto L6c
        L59:
            if (r2 != 0) goto L6a
            org.scilab.forge.jlatexmath.PhantomAtom r2 = new org.scilab.forge.jlatexmath.PhantomAtom
            org.scilab.forge.jlatexmath.CharAtom r8 = new org.scilab.forge.jlatexmath.CharAtom
            r3 = 77
            java.lang.String r6 = "mathnormal"
            r8.<init>(r3, r6)
            r2.<init>(r8, r4, r1, r1)
            goto L6c
        L6a:
            r8.root = r5
        L6c:
            int r8 = r2.getRightType()
            if (r8 != r1) goto L78
            org.scilab.forge.jlatexmath.BigOperatorAtom r8 = new org.scilab.forge.jlatexmath.BigOperatorAtom
            r8.<init>(r2, r0, r9)
            return r8
        L78:
            boolean r8 = r2 instanceof org.scilab.forge.jlatexmath.OverUnderDelimiter
            if (r8 == 0) goto L9b
            r8 = r2
            org.scilab.forge.jlatexmath.OverUnderDelimiter r8 = (org.scilab.forge.jlatexmath.OverUnderDelimiter) r8
            boolean r1 = r8.isOver()
            if (r1 == 0) goto L90
            if (r9 == 0) goto L9b
            r8.addScript(r9)
            org.scilab.forge.jlatexmath.ScriptsAtom r8 = new org.scilab.forge.jlatexmath.ScriptsAtom
            r8.<init>(r2, r0, r5)
            return r8
        L90:
            if (r0 == 0) goto L9b
            r8.addScript(r0)
            org.scilab.forge.jlatexmath.ScriptsAtom r8 = new org.scilab.forge.jlatexmath.ScriptsAtom
            r8.<init>(r2, r5, r9)
            return r8
        L9b:
            org.scilab.forge.jlatexmath.ScriptsAtom r8 = new org.scilab.forge.jlatexmath.ScriptsAtom
            r8.<init>(r2, r0, r9)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.scilab.forge.jlatexmath.TeXParser.getScripts(char):org.scilab.forge.jlatexmath.Atom");
    }

    public String getDollarGroup(char c2) {
        char cCharAt;
        int i;
        int i2 = this.pos;
        do {
            StringBuffer stringBuffer = this.parseString;
            int i3 = this.pos;
            this.pos = i3 + 1;
            cCharAt = stringBuffer.charAt(i3);
            if (cCharAt == '\\') {
                this.pos++;
            }
            i = this.pos;
            if (i >= this.len) {
                break;
            }
        } while (cCharAt != c2);
        StringBuffer stringBuffer2 = this.parseString;
        if (cCharAt == c2) {
            return stringBuffer2.substring(i2, i - 1);
        }
        return stringBuffer2.substring(i2, i);
    }

    public String getGroup(char c2, char c3) {
        int i;
        int i2;
        int i3 = this.pos;
        if (i3 == this.len) {
            return null;
        }
        char cCharAt = this.parseString.charAt(i3);
        int i4 = this.pos;
        if (i4 < this.len && cCharAt == c2) {
            int i5 = 1;
            while (true) {
                i = this.pos;
                if (i >= this.len - 1 || i5 == 0) {
                    break;
                }
                int i6 = i + 1;
                this.pos = i6;
                char cCharAt2 = this.parseString.charAt(i6);
                if (cCharAt2 == c2) {
                    i5++;
                } else if (cCharAt2 == c3) {
                    i5--;
                } else if (cCharAt2 == '\\' && (i2 = this.pos) != this.len - 1) {
                    this.pos = i2 + 1;
                }
            }
            int i7 = i + 1;
            this.pos = i7;
            StringBuffer stringBuffer = this.parseString;
            if (i5 != 0) {
                return stringBuffer.substring(i4 + 1, i7);
            }
            return stringBuffer.substring(i4 + 1, i);
        }
        throw new ParseException("missing '" + c2 + "'!");
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0079 A[PHI: r8
  0x0079: PHI (r8v3 char) = (r8v2 char), (r8v2 char), (r8v14 char), (r8v14 char) binds: [B:74:0x003e, B:76:0x0042, B:83:0x006d, B:85:0x0073] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getGroup(java.lang.String r18, java.lang.String r19) {
        /*
            Method dump skipped, instruction units count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.scilab.forge.jlatexmath.TeXParser.getGroup(java.lang.String, java.lang.String):java.lang.String");
    }

    public Atom getArgument() {
        skipWhiteSpace();
        int i = this.pos;
        if (i < this.len) {
            char cCharAt = this.parseString.charAt(i);
            if (cCharAt != '{') {
                if (cCharAt == '\\') {
                    Atom atomProcessEscape = processEscape();
                    if (!this.insertion) {
                        return atomProcessEscape;
                    }
                    this.insertion = false;
                    return getArgument();
                }
                Atom atomConvertCharacter = convertCharacter(cCharAt, true);
                this.pos++;
                return atomConvertCharacter;
            }
            TeXFormula teXFormula = new TeXFormula();
            TeXFormula teXFormula2 = this.formula;
            this.formula = teXFormula;
            this.pos++;
            this.group++;
            parse();
            this.formula = teXFormula2;
            if (teXFormula2.root == null) {
                RowAtom rowAtom = new RowAtom();
                rowAtom.add(teXFormula.root);
                return rowAtom;
            }
            return teXFormula.root;
        }
        return new EmptyAtom();
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getOverArgument() {
        /*
            r10 = this;
            int r0 = r10.pos
            int r1 = r10.len
            if (r0 != r1) goto L8
            r10 = 0
            return r10
        L8:
            r1 = 0
            r2 = 1
            r4 = r1
            r3 = r2
        Lc:
            int r5 = r10.pos
            int r6 = r10.len
            r7 = 125(0x7d, float:1.75E-43)
            r8 = 38
            r9 = 92
            if (r5 >= r6) goto L7d
            if (r3 == 0) goto L7d
            java.lang.StringBuffer r4 = r10.parseString
            char r4 = r4.charAt(r5)
            if (r4 == r8) goto L74
            if (r4 == r9) goto L31
            r5 = 123(0x7b, float:1.72E-43)
            if (r4 == r5) goto L2e
            if (r4 == r7) goto L2b
            goto L77
        L2b:
            int r3 = r3 + (-1)
            goto L77
        L2e:
            int r3 = r3 + 1
            goto L77
        L31:
            int r5 = r10.pos
            int r5 = r5 + r2
            r10.pos = r5
            int r6 = r10.len
            if (r5 >= r6) goto L4c
            java.lang.StringBuffer r6 = r10.parseString
            char r5 = r6.charAt(r5)
            if (r5 != r9) goto L4c
            if (r3 != r2) goto L4c
            int r3 = r3 + (-1)
            int r5 = r10.pos
            int r5 = r5 - r2
            r10.pos = r5
            goto L77
        L4c:
            int r5 = r10.pos
            int r6 = r10.len
            int r6 = r6 - r2
            if (r5 >= r6) goto L77
            java.lang.StringBuffer r6 = r10.parseString
            char r5 = r6.charAt(r5)
            r6 = 99
            if (r5 != r6) goto L77
            java.lang.StringBuffer r5 = r10.parseString
            int r6 = r10.pos
            int r6 = r6 + r2
            char r5 = r5.charAt(r6)
            r6 = 114(0x72, float:1.6E-43)
            if (r5 != r6) goto L77
            if (r3 != r2) goto L77
            int r3 = r3 + (-1)
            int r5 = r10.pos
            int r5 = r5 - r2
            r10.pos = r5
            goto L77
        L74:
            if (r3 != r2) goto L77
            goto L2b
        L77:
            int r5 = r10.pos
            int r5 = r5 + r2
            r10.pos = r5
            goto Lc
        L7d:
            r6 = 2
            if (r3 >= r6) goto L9d
            java.lang.StringBuffer r6 = r10.parseString
            if (r3 != 0) goto L8b
            int r5 = r5 - r2
            java.lang.String r0 = r6.substring(r0, r5)
            r1 = r4
            goto L8f
        L8b:
            java.lang.String r0 = r6.substring(r0, r5)
        L8f:
            if (r1 == r8) goto L97
            if (r1 == r9) goto L97
            if (r1 != r7) goto L96
            goto L97
        L96:
            return r0
        L97:
            int r1 = r10.pos
            int r1 = r1 - r2
            r10.pos = r1
            return r0
        L9d:
            java.lang.String r10 = "Illegal end,  missing '}' !"
            org.scilab.forge.jlatexmath.TeXParser$$ExternalSyntheticBUOutline0.m1030m(r10)
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.scilab.forge.jlatexmath.TeXParser.getOverArgument():java.lang.String");
    }

    public float[] getLength() {
        if (this.pos == this.len) {
            return null;
        }
        skipWhiteSpace();
        int i = this.pos;
        char cCharAt = 0;
        while (true) {
            int i2 = this.pos;
            if (i2 >= this.len || cCharAt == ' ') {
                break;
            }
            StringBuffer stringBuffer = this.parseString;
            this.pos = i2 + 1;
            cCharAt = stringBuffer.charAt(i2);
        }
        skipWhiteSpace();
        return SpaceAtom.getLength(this.parseString.substring(i, this.pos - 1));
    }

    public Atom convertCharacter(char c2, boolean z) {
        String str;
        String[] strArr;
        if (this.ignoreWhiteSpace) {
            if (c2 >= 945 && c2 <= 969) {
                return SymbolAtom.get(TeXFormula.symbolMappings[c2]);
            }
            if (c2 >= 913 && c2 <= 937) {
                return new TeXFormula(TeXFormula.symbolFormulaMappings[c2]).root;
            }
        }
        char cConvertToRomanNumber = convertToRomanNumber(c2);
        if ((cConvertToRomanNumber < '0' || cConvertToRomanNumber > '9') && ((cConvertToRomanNumber < 'a' || cConvertToRomanNumber > 'z') && (cConvertToRomanNumber < 'A' || cConvertToRomanNumber > 'Z'))) {
            Character.UnicodeBlock unicodeBlockOf = Character.UnicodeBlock.of(cConvertToRomanNumber);
            if (!isLoading && !DefaultTeXFont.loadedAlphabets.contains(unicodeBlockOf)) {
                DefaultTeXFont.addAlphabet(DefaultTeXFont.registeredAlphabets.get(unicodeBlockOf));
            }
            String str2 = TeXFormula.symbolMappings[cConvertToRomanNumber];
            if (str2 == null && ((strArr = TeXFormula.symbolFormulaMappings) == null || strArr[cConvertToRomanNumber] == null)) {
                Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.BASIC_LATIN;
                boolean zEquals = unicodeBlock.equals(unicodeBlockOf);
                TeXFormula.FontInfos externalFont = (!(zEquals && TeXFormula.isRegisteredBlock(unicodeBlock)) && zEquals) ? null : TeXFormula.getExternalFont(unicodeBlockOf);
                if (externalFont == null) {
                    if (!this.isPartial) {
                        throw new ParseException("Unknown character : '" + Character.toString(cConvertToRomanNumber) + "' (or " + ((int) cConvertToRomanNumber) + ")");
                    }
                    return new ColorAtom(new RomanAtom(new TeXFormula("\\text{(Unknown char " + ((int) cConvertToRomanNumber) + ")}").root), (Color) null, Color.RED);
                }
                if (z) {
                    return new JavaFontRenderingAtom(Character.toString(cConvertToRomanNumber), externalFont);
                }
                int i = this.pos;
                this.pos = i + 1;
                int i2 = this.len - 1;
                while (true) {
                    int i3 = this.pos;
                    if (i3 >= this.len) {
                        break;
                    }
                    boolean zEquals2 = Character.UnicodeBlock.of(this.parseString.charAt(i3)).equals(unicodeBlockOf);
                    int i4 = this.pos;
                    if (!zEquals2) {
                        i2 = i4 - 1;
                        this.pos = i2;
                        break;
                    }
                    this.pos = i4 + 1;
                }
                return new JavaFontRenderingAtom(this.parseString.substring(i, i2 + 1), externalFont);
            }
            if (!this.ignoreWhiteSpace && (str = TeXFormula.symbolTextMappings[cConvertToRomanNumber]) != null) {
                return SymbolAtom.get(str).setUnicode(cConvertToRomanNumber);
            }
            String[] strArr2 = TeXFormula.symbolFormulaMappings;
            if (strArr2 != null && strArr2[cConvertToRomanNumber] != null) {
                return new TeXFormula(TeXFormula.symbolFormulaMappings[cConvertToRomanNumber]).root;
            }
            try {
                return SymbolAtom.get(str2);
            } catch (SymbolNotFoundException e) {
                throw new ParseException("The character '" + Character.toString(cConvertToRomanNumber) + "' was mapped to an unknown symbol with the name '" + str2 + "'!", e);
            }
        }
        TeXFormula.FontInfos fontInfos = TeXFormula.externalFontMap.get(Character.UnicodeBlock.BASIC_LATIN);
        if (fontInfos == null) {
            return new CharAtom(cConvertToRomanNumber, this.formula.textStyle, this.ignoreWhiteSpace);
        }
        if (z) {
            return new JavaFontRenderingAtom(Character.toString(cConvertToRomanNumber), fontInfos);
        }
        int i5 = this.pos;
        this.pos = i5 + 1;
        int i6 = this.len - 1;
        while (true) {
            int i7 = this.pos;
            if (i7 >= this.len) {
                break;
            }
            char cCharAt = this.parseString.charAt(i7);
            if ((cCharAt < '0' || cCharAt > '9') && ((cCharAt < 'a' || cCharAt > 'z') && (cCharAt < 'A' || cCharAt > 'Z'))) {
                break;
            }
            this.pos++;
        }
        i6 = this.pos - 1;
        this.pos = i6;
        return new JavaFontRenderingAtom(this.parseString.substring(i5, i6 + 1), fontInfos);
    }

    private String getCommand() {
        int i;
        int i2 = this.pos + 1;
        this.pos = i2;
        char cCharAt = 0;
        while (true) {
            int i3 = this.pos;
            if (i3 >= this.len || (((cCharAt = this.parseString.charAt(i3)) < 'a' || cCharAt > 'z') && ((cCharAt < 'A' || cCharAt > 'Z') && (this.atIsLetter == 0 || cCharAt != '@')))) {
                break;
            }
            this.pos++;
        }
        if (cCharAt == 0) {
            return _UrlKt.FRAGMENT_ENCODE_SET;
        }
        int i4 = this.pos;
        if (i4 == i2) {
            this.pos = i4 + 1;
        }
        String strSubstring = this.parseString.substring(i2, this.pos);
        if ("cr".equals(strSubstring) && (i = this.pos) < this.len && this.parseString.charAt(i) == ' ') {
            this.pos++;
        }
        return strSubstring;
    }

    private Atom processEscape() {
        this.spos = this.pos;
        String command = getCommand();
        if (command.length() == 0) {
            return new EmptyAtom();
        }
        if (MacroInfo.Commands.get(command) != null) {
            return processCommands(command);
        }
        try {
            try {
                return TeXFormula.get(command).root;
            } catch (FormulaNotFoundException unused) {
                return SymbolAtom.get(command);
            }
        } catch (SymbolNotFoundException unused2) {
            if (!this.isPartial) {
                TeXParser$$ExternalSyntheticBUOutline1.m1031m("Unknown symbol or command or predefined TeXFormula: '", command, "'");
                return null;
            }
            return new ColorAtom(new RomanAtom(new TeXFormula("\\backslash ".concat(command)).root), (Color) null, Color.RED);
        }
    }

    private void insert(int i, int i2, String str) {
        this.parseString.replace(i, i2, str);
        this.len = this.parseString.length();
        this.pos = i;
        this.insertion = true;
    }

    public String[] getOptsArgs(int i, int i2) {
        String[] strArr = new String[i + 11];
        if (i != 0) {
            if (i2 == 1) {
                for (int i3 = i + 1; i3 < i + 11; i3++) {
                    try {
                        skipWhiteSpace();
                        strArr[i3] = getGroup(L_BRACK, R_BRACK);
                    } catch (ParseException unused) {
                        strArr[i3] = null;
                    }
                }
            }
            skipWhiteSpace();
            try {
                strArr[1] = getGroup(L_GROUP, R_GROUP);
            } catch (ParseException unused2) {
                if (this.parseString.charAt(this.pos) != '\\') {
                    strArr[1] = _UrlKt.FRAGMENT_ENCODE_SET + this.parseString.charAt(this.pos);
                    this.pos = this.pos + 1;
                } else {
                    strArr[1] = getCommandWithArgs(getCommand());
                }
            }
            if (i2 == 2) {
                for (int i4 = i + 1; i4 < i + 11; i4++) {
                    try {
                        skipWhiteSpace();
                        strArr[i4] = getGroup(L_BRACK, R_BRACK);
                    } catch (ParseException unused3) {
                        strArr[i4] = null;
                    }
                }
            }
            for (int i5 = 2; i5 <= i; i5++) {
                skipWhiteSpace();
                try {
                    strArr[i5] = getGroup(L_GROUP, R_GROUP);
                } catch (ParseException unused4) {
                    if (this.parseString.charAt(this.pos) != '\\') {
                        strArr[i5] = _UrlKt.FRAGMENT_ENCODE_SET + this.parseString.charAt(this.pos);
                        this.pos = this.pos + 1;
                    } else {
                        strArr[i5] = getCommandWithArgs(getCommand());
                    }
                }
            }
            if (this.ignoreWhiteSpace) {
                skipWhiteSpace();
            }
        }
        return strArr;
    }

    private String getCommandWithArgs(String str) {
        if (str.equals("left")) {
            return getGroup("\\left", "\\right");
        }
        MacroInfo macroInfo = MacroInfo.Commands.get(str);
        if (macroInfo != null) {
            int i = 0;
            String[] optsArgs = getOptsArgs(macroInfo.nbArgs, macroInfo.hasOptions ? macroInfo.posOpts : 0);
            StringBuffer stringBuffer = new StringBuffer("\\");
            stringBuffer.append(str);
            for (int i2 = 0; i2 < macroInfo.posOpts; i2++) {
                String str2 = optsArgs[macroInfo.nbArgs + i2 + 1];
                if (str2 != null) {
                    stringBuffer.append("[");
                    stringBuffer.append(str2);
                    stringBuffer.append("]");
                }
            }
            while (i < macroInfo.nbArgs) {
                i++;
                String str3 = optsArgs[i];
                if (str3 != null) {
                    stringBuffer.append("{");
                    stringBuffer.append(str3);
                    stringBuffer.append("}");
                }
            }
            return stringBuffer.toString();
        }
        return "\\".concat(str);
    }

    private Atom processCommands(String str) {
        MacroInfo macroInfo = MacroInfo.Commands.get(str);
        String[] optsArgs = getOptsArgs(macroInfo.nbArgs, macroInfo.hasOptions ? macroInfo.posOpts : 0);
        optsArgs[0] = str;
        if (NewCommandMacro.isMacro(str)) {
            insert(this.spos, this.pos, (String) macroInfo.invoke(this, optsArgs));
            return null;
        }
        return (Atom) macroInfo.invoke(this, optsArgs);
    }

    public final boolean isValidName(String str) {
        char cCharAt = 0;
        if (str == null || _UrlKt.FRAGMENT_ENCODE_SET.equals(str) || str.charAt(0) != '\\') {
            return false;
        }
        int length = str.length();
        for (int i = 1; i < length; i++) {
            cCharAt = str.charAt(i);
            if (!Character.isLetter(cCharAt) && (this.atIsLetter == 0 || cCharAt != '@')) {
                break;
            }
        }
        return Character.isLetter(cCharAt);
    }

    public final boolean isValidCharacterInCommand(char c2) {
        if (Character.isLetter(c2)) {
            return true;
        }
        return this.atIsLetter != 0 && c2 == '@';
    }

    private final void skipWhiteSpace() {
        while (true) {
            int i = this.pos;
            if (i >= this.len) {
                return;
            }
            char cCharAt = this.parseString.charAt(i);
            if (cCharAt != ' ' && cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\r') {
                return;
            }
            if (cCharAt == '\n') {
                this.line++;
                this.col = this.pos;
            }
            this.pos++;
        }
    }
}
