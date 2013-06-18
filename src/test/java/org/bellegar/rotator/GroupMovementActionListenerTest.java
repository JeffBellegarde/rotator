package org.bellegar.rotator;

import java.io.File;

import javax.swing.Timer;

import org.bellegar.assistant.Injectable;
import org.bellegar.assistant.Mock;
import org.bellegar.assistant.MockeryAssistant;
import org.bellegar.assistant.RunWithAssistants;
import org.bellegar.rotator.RotatorApplicationImpl.Level;
import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;
import org.bellegar.rotator.RotatorApplicationImpl.Group.Direction;
import org.bellegar.rotator.applescript.DesktopChangeEvent;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RunWithAssistants.class)
public class GroupMovementActionListenerTest {
    @MockeryAssistant
    private Mockery mockery;

    @Mock
    private Timer timer;

    @Mock
    private RotatorApplication rotatorApplication;

    @Mock
    private DesktopChangeEvent desktopChangeEvent;

    @Injectable
    private GroupMovementActionListener listener;

    @Test
    public void actionPerformed() {
        listener.setDirection(Direction.NEXT);
        listener.setLevel(Level.GROUP);
        mockery.checking(new Expectations() {
            {
                one(rotatorApplication).move(Level.GROUP, Direction.NEXT);
                final CurrentItemInfo<File> currentItemInfo = new CurrentItemInfo<File>(new File(""), 0, 0);
                will(returnValue(currentItemInfo));
                one(timer).restart();
                one(desktopChangeEvent).notify(currentItemInfo);
            }
        });
        listener.actionPerformed(null);
    }
}
