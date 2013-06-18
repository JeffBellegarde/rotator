package org.bellegar.rotator.applescript;

import java.io.File;

import javax.script.ScriptException;

import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;

public class DesktopChangeEventImpl extends AppleScriptCommand implements DesktopChangeEvent {

    public void notify(final CurrentItemInfo<File> info) {
        try {
            run(info.getItem().getName(), info.getIndex(), info.getCount(), info.getItem().getPath());
        } catch (final ScriptException e) {
            e.printStackTrace();
        }
    }
}
