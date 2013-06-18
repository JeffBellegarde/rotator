package org.bellegar.rotator.applescript;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Required;

public class AppleScriptCommand {
    private ScriptEngine scriptEngine;
    private String command;

    @Required
    public void setScriptEngine(final ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
    }

    @Required
    public void setCommand(final String command) {
        this.command = command;
    }

    protected Object run(final Object... params) throws ScriptException {
        final String fullCommand = String.format(command, params);
        System.out.println(fullCommand);
        return scriptEngine.eval(fullCommand);
    }
}
