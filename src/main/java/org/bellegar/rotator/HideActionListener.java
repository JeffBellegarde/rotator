/**
 * 
 */
package org.bellegar.rotator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class HideActionListener implements ActionListener {
    private RotatorApplication rotatorApplication;
    private Timer timer;

    public void setRotatorApplication(final RotatorApplication rotatorApplication) {
        this.rotatorApplication = rotatorApplication;
    }

    public void setTimer(final Timer timer) {
        this.timer = timer;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        rotatorApplication.changeToDefaultDesktopBackground();
        timer.stop();

    }
}
