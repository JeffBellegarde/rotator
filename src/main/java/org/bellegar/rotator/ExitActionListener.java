/**
 * 
 */
package org.bellegar.rotator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitActionListener implements ActionListener {
    private RotatorApplication rotatorApplication;

    public void setRotatorApplication(final RotatorApplication rotatorApplication) {
        this.rotatorApplication = rotatorApplication;
    }

    public void actionPerformed(final ActionEvent e) {
        rotatorApplication.changeToDefaultDesktopBackground();
        System.exit(0);

    }
}
