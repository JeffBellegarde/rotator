package org.bellegar.rotator.applescript;

import java.io.*;

import javax.script.*;

import org.bellegar.rotator.RotatorApplicationImpl.Group.*;

public class ChangeDesktopCommand extends AppleScriptCommand implements DesktopChangeEvent {

	@Override
	public void notify(final CurrentItemInfo<File> info) {
		change(info.getItem().getPath().replace("/", ":"));
	}

	public void change(final String path) {
		try {
			run("Macintosh HD" + path.replace("/", ":"));
		} catch (final ScriptException e) {
			e.printStackTrace();
		}
	}
}
