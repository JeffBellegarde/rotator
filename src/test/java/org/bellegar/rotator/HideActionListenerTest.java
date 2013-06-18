package org.bellegar.rotator;

import javax.swing.Timer;

import org.bellegar.assistant.Injectable;
import org.bellegar.assistant.Mock;
import org.bellegar.assistant.MockeryAssistant;
import org.bellegar.assistant.RunWithAssistants;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RunWithAssistants.class)
public class HideActionListenerTest {
    @MockeryAssistant
    private Mockery mockery;

    @Mock
    private Timer timer;

    @Mock
    private RotatorApplication rotatorApplication;

    @Injectable
    private HideActionListener listener;

    @Test
    public void actionPerformed() {
        mockery.checking(new Expectations() {
            {
                one(rotatorApplication).changeToDefaultDesktopBackground();
                one(timer).stop();
            }
        });
        listener.actionPerformed(null);
    }
}
