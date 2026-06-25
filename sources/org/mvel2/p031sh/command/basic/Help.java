package org.mvel2.p031sh.command.basic;

import org.mvel2.p031sh.Command;
import org.mvel2.p031sh.ShellSession;
import org.mvel2.p031sh.text.TextUtil;

/* JADX INFO: loaded from: classes5.dex */
public class Help implements Command {
    @Override // org.mvel2.p031sh.Command
    public Object execute(ShellSession shellSession, String[] strArr) {
        for (String str : shellSession.getCommands().keySet()) {
            System.out.println(str + TextUtil.pad(str.length(), 25) + "- " + shellSession.getCommands().get(str).getDescription());
        }
        return null;
    }

    @Override // org.mvel2.p031sh.Command
    public String getDescription() {
        return "displays help for available shell commands";
    }

    @Override // org.mvel2.p031sh.Command
    public String getHelp() {
        return "No help yet";
    }
}
