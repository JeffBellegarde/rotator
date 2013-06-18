package org.bellegar.rotator;

import java.io.*;
import java.util.*;

import org.bellegar.rotator.RotatorApplicationImpl.*;
import org.bellegar.rotator.applescript.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.support.*;

public class Bootstrap {

	private RotatorApplication application;
	private Grouper grouper;
	private RotatorUI rotatorUI;
	private String pathname = "/Library/Desktop Pictures";
	private RegisterWithGrowlCommand registerWithGrowlCommand;

	@Required
	public void setRegisterWithGrowlCommand(final RegisterWithGrowlCommand registerWithGrowlCommand) {
		this.registerWithGrowlCommand = registerWithGrowlCommand;
	}

	@Required
	public void setApplication(final RotatorApplication application) {
		this.application = application;
	}

	@Required
	public void setGrouper(final Grouper grouper) {
		this.grouper = grouper;
	}

	@Required
	public void setRotatorUI(final RotatorUI rotatorUI) {
		this.rotatorUI = rotatorUI;
	}

	public void setPathname(final String pathname) {
		this.pathname = pathname;
	}

	public static void main(final String[] args) {
		final ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		final Bootstrap application = (Bootstrap) context.getBean("bootstrap");
		application.run();

	}

	public void run() {
		randomizeGroups(collectGroups());
		registerWithGrowlCommand.register();
		rotatorUI.constructUI();
		rotatorUI.notify(application.setPicture());
	}

	private Map<String, Group<File>> collectGroups() {
		final Map<String, Group<File>> groups = new HashMap<String, Group<File>>();
		for (final File file : collectFiles()) {
			final String groupName = grouper.groupForName(file.getName());
			if (!groups.containsKey(groupName)) {
				groups.put(groupName, new Group<File>());
			}
			groups.get(groupName).add(file);
		}
		return groups;
	}

	private void randomizeGroups(final Map<String, Group<File>> groups) {
		final Random random = new Random();
		while (groups.size() > 0) {
			final int nextInt = random.nextInt(groups.size());
			final Group<File> list = groups.remove(groups.keySet().toArray()[nextInt]);
			application.addGroup(list);
		}
	}

	private List<File> collectFiles() {
		final List<File> list = new LinkedList<File>();
		final File rootDirectory = new File(pathname);
		for (final File entry : rootDirectory.listFiles()) {
			if (entry.isDirectory()) {
				for (final File file : entry.listFiles()) {
					if (!file.getName().startsWith(".")) {
						list.add(file);
					}
				}
			} else {
				if (!entry.getName().startsWith(".")) {
					list.add(entry);
				}
			}
		}
		return list;
	}

}
