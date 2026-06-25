package org.scilab.forge.jlatexmath;

import java.util.HashMap;

/* JADX INFO: loaded from: classes5.dex */
public class NewCommandMacro {
    protected static HashMap<String, String> macrocode = new HashMap<>();
    protected static HashMap<String, String> macroreplacement = new HashMap<>();

    public static void addNewCommand(String str, String str2, int i) {
        macrocode.put(str, str2);
        MacroInfo.Commands.put(str, new MacroInfo("org.scilab.forge.jlatexmath.NewCommandMacro", "executeMacro", i));
    }

    public static void addNewCommand(String str, String str2, int i, String str3) {
        if (macrocode.get(str) != null) {
            TeXParser$$ExternalSyntheticBUOutline1.m1031m("Command ", str, " already exists ! Use renewcommand instead ...");
            return;
        }
        macrocode.put(str, str2);
        macroreplacement.put(str, str3);
        MacroInfo.Commands.put(str, new MacroInfo("org.scilab.forge.jlatexmath.NewCommandMacro", "executeMacro", i, 1.0f));
    }

    public static boolean isMacro(String str) {
        return macrocode.containsKey(str);
    }

    public static void addReNewCommand(String str, String str2, int i) {
        if (macrocode.get(str) == null) {
            TeXParser$$ExternalSyntheticBUOutline1.m1031m("Command ", str, " is not defined ! Use newcommand instead ...");
        } else {
            macrocode.put(str, str2);
            MacroInfo.Commands.put(str, new MacroInfo("org.scilab.forge.jlatexmath.NewCommandMacro", "executeMacro", i));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0040 A[LOOP:0: B:9:0x003e->B:10:0x0040, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String executeMacro(org.scilab.forge.jlatexmath.TeXParser r6, java.lang.String[] r7) {
        /*
            r5 = this;
            java.util.HashMap<java.lang.String, java.lang.String> r5 = org.scilab.forge.jlatexmath.NewCommandMacro.macrocode
            r6 = 0
            r0 = r7[r6]
            java.lang.Object r5 = r5.get(r0)
            java.lang.String r5 = (java.lang.String) r5
            int r0 = r7.length
            int r1 = r0 + (-11)
            int r0 = r0 + (-10)
            r0 = r7[r0]
            java.lang.String r2 = "#1"
            r3 = 1
            if (r0 == 0) goto L21
            java.lang.String r6 = java.util.regex.Matcher.quoteReplacement(r0)
            java.lang.String r5 = r5.replaceAll(r2, r6)
        L1f:
            r6 = r3
            goto L3e
        L21:
            java.util.HashMap<java.lang.String, java.lang.String> r0 = org.scilab.forge.jlatexmath.NewCommandMacro.macroreplacement
            r4 = r7[r6]
            java.lang.Object r0 = r0.get(r4)
            if (r0 == 0) goto L3e
            java.util.HashMap<java.lang.String, java.lang.String> r0 = org.scilab.forge.jlatexmath.NewCommandMacro.macroreplacement
            r6 = r7[r6]
            java.lang.Object r6 = r0.get(r6)
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r6 = java.util.regex.Matcher.quoteReplacement(r6)
            java.lang.String r5 = r5.replaceAll(r2, r6)
            goto L1f
        L3e:
            if (r3 > r1) goto L5d
            r0 = r7[r3]
            java.lang.String r0 = java.util.regex.Matcher.quoteReplacement(r0)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "#"
            r2.<init>(r4)
            int r4 = r3 + r6
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            java.lang.String r5 = r5.replaceAll(r2, r0)
            int r3 = r3 + 1
            goto L3e
        L5d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.scilab.forge.jlatexmath.NewCommandMacro.executeMacro(org.scilab.forge.jlatexmath.TeXParser, java.lang.String[]):java.lang.String");
    }
}
