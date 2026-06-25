package org.mvel2.p031sh.command.basic;

import org.mvel2.MVEL;
import org.mvel2.p031sh.Command;
import org.mvel2.p031sh.ShellSession;

/* JADX INFO: loaded from: classes5.dex */
public class PushContext implements Command {
    @Override // org.mvel2.p031sh.Command
    public String getDescription() {
        return null;
    }

    @Override // org.mvel2.p031sh.Command
    public String getHelp() {
        return null;
    }

    @Override // org.mvel2.p031sh.Command
    public Object execute(ShellSession shellSession, String[] strArr) {
        shellSession.setCtxObject(MVEL.eval(strArr[0], shellSession.getCtxObject(), shellSession.getVariables()));
        return "Changed Context";
    }
}
