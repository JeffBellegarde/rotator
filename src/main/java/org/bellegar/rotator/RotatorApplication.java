package org.bellegar.rotator;

import java.io.File;

import org.bellegar.rotator.RotatorApplicationImpl.Group;
import org.bellegar.rotator.RotatorApplicationImpl.Level;
import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;
import org.bellegar.rotator.RotatorApplicationImpl.Group.Direction;

public interface RotatorApplication {

    CurrentItemInfo<File> move(final Level level, final Direction direction);

    void changeToDefaultDesktopBackground();

    CurrentItemInfo<File> setPicture();

    void addGroup(Group<File> group);

}
