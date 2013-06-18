package org.bellegar.rotator.applescript;

import javax.script.*;

import org.springframework.beans.factory.annotation.*;

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
		final Object result = scriptEngine.eval(fullCommand);
		System.out.println("result = '" + result + "'");
		return result;
	}
}
