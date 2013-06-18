package org.bellegar.rotator.applescript;

import java.io.File;

import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;

public interface DesktopChangeEvent {
    void notify(final CurrentItemInfo<File> info);
}
