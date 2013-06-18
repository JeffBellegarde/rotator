package org.bellegar.rotator.applescript;

import javax.script.ScriptException;


public class RegisterWithGrowlCommand extends AppleScriptCommand {
    public void register() {
        try {
            run();
        } catch (final ScriptException e) {
            e.printStackTrace();
        }
    }
}
