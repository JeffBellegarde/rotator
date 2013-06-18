package org.bellegar.rotator;

import java.io.File;

import org.bellegar.assistant.Injectable;
import org.bellegar.assistant.Mock;
import org.bellegar.assistant.MockeryAssistant;
import org.bellegar.assistant.RunWithAssistants;
import org.bellegar.rotator.RotatorApplicationImpl.Group;
import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;
import org.bellegar.rotator.applescript.RegisterWithGrowlCommand;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RunWithAssistants.class)
public class BootstrapTest {
    @MockeryAssistant
    private Mockery mockery;

    @Mock
    private Grouper grouper;

    @Mock
    private RotatorApplication application;

    @Mock
    private RegisterWithGrowlCommand registerWithGrowlCommand;

    @Mock
    private RotatorUI rotatorUI;

    @Injectable
    private Bootstrap bootstrap;

    @SuppressWarnings("unchecked")
    @Test
    public void startup() {
        final Sequence bootstrapSequence = mockery.sequence("bootstrapSequence");
        mockery.checking(new Expectations() {
            {
                allowing(grouper).groupForName(with(aNonNull(String.class)));
                will(returnValue("group1"));
                inSequence(bootstrapSequence);
                one(application).addGroup(with(any(Group.class)));
                inSequence(bootstrapSequence);
                one(registerWithGrowlCommand).register();
                inSequence(bootstrapSequence);
                one(rotatorUI).constructUI();
                inSequence(bootstrapSequence);
                final CurrentItemInfo<File> info = new CurrentItemInfo<File>(new File(""), 0, 0);
                one(application).setPicture();
                inSequence(bootstrapSequence);
                will(returnValue(info));
                one(rotatorUI).notify(info);
                inSequence(bootstrapSequence);
            }
        });
        bootstrap.run();
    }
}
