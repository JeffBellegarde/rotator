package org.bellegar.rotator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import org.bellegar.rotator.RotatorApplicationImpl.Level;
import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;
import org.bellegar.rotator.RotatorApplicationImpl.Group.Direction;
import org.bellegar.rotator.applescript.DesktopChangeEvent;
import org.springframework.beans.factory.annotation.Required;

public class GroupMovementActionListener implements ActionListener {
    private Direction direction;
    private Level level;
    private RotatorApplication rotatorApplication;
    private DesktopChangeEvent desktopChangeEvent;
    private Timer timer;

    @Required
    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    @Required
    public void setLevel(final Level level) {
        this.level = level;
    }

    @Required
    public void setTimer(final Timer timer) {
        this.timer = timer;
    }

    @Required
    public void setRotatorApplication(final RotatorApplication rotatorApplication) {
        this.rotatorApplication = rotatorApplication;
    }

    @Required
    public void setDesktopChangeEvent(final DesktopChangeEvent desktopChangeEvent) {
        this.desktopChangeEvent = desktopChangeEvent;
    }

    public void actionPerformed(final ActionEvent e) {
        final CurrentItemInfo<File> currentItemInfo = rotatorApplication.move(level, direction);
        timer.restart();
        desktopChangeEvent.notify(currentItemInfo);

    }

}
