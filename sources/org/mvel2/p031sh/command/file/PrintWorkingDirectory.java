package org.mvel2.p031sh.command.file;

import org.mvel2.p031sh.Command;
import org.mvel2.p031sh.ShellSession;

/* JADX INFO: loaded from: classes5.dex */
public class PrintWorkingDirectory implements Command {
    @Override // org.mvel2.p031sh.Command
    public Object execute(ShellSession shellSession, String[] strArr) {
        System.out.println(shellSession.getEnv().get("$CWD"));
        return null;
    }

    @Override // org.mvel2.p031sh.Command
    public String getDescription() {
        return "prints the current working directory";
    }

    @Override // org.mvel2.p031sh.Command
    public String getHelp() {
        return "no help yet.";
    }
}
