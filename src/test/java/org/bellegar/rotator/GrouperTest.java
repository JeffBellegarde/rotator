package org.bellegar.rotator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GrouperTest {
    private final Grouper grouper = new Grouper();

    @Test
    public void matches1() {
        assertThat(grouper.groupForName("abc123.jpg"), equalTo("abc"));
    }
}
