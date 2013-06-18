package org.bellegar.rotator;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;
import org.bellegar.rotator.applescript.DesktopChangeEvent;
import org.springframework.beans.factory.annotation.Required;

public class RotatorUI implements DesktopChangeEvent {
    private JFrame rootFrame;
    private Timer timer;
    private JLabel label;
    private TrayIcon trayIcon;
    private Image image;
    private ActionListener nextActionListener;
    private ActionListener previousActionListener;
    private ActionListener previousGroupActionListener;
    private ActionListener nextGroupActionListener;
    private ActionListener exitActionListener;
    private ActionListener hideActionListener;

    public void constructUI() {
        if (SystemTray.isSupported()) {
            createSystemTray();
        } else {
            createWindow();
        }
        timer.start();
    }

    private void createWindow() {
        rootFrame = new JFrame("Image Rotator");
        final GridBagLayout gridBagLayout = new GridBagLayout();
        rootFrame.setLayout(gridBagLayout);
        label = new JLabel("label");
        final GridBagConstraints endOfRowConstraints = new GridBagConstraints();
        endOfRowConstraints.gridwidth = GridBagConstraints.REMAINDER;
        final GridBagConstraints wideGridBigConstraints = new GridBagConstraints();
        wideGridBigConstraints.gridwidth = GridBagConstraints.REMAINDER;
        wideGridBigConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagLayout.setConstraints(label, wideGridBigConstraints);
        rootFrame.add(label);
        addButton("Prev", previousActionListener);
        final JButton nextButton = addButton("Next", nextActionListener);
        gridBagLayout.setConstraints(nextButton, endOfRowConstraints);
        addButton("Prev Group", previousGroupActionListener);
        final JButton nextGroupButton = addButton("Next Group", nextGroupActionListener);
        gridBagLayout.setConstraints(nextGroupButton, endOfRowConstraints);
        final JButton exitButton = addButton("Exit", exitActionListener);
        gridBagLayout.setConstraints(exitButton, wideGridBigConstraints);
        rootFrame.setMinimumSize(new Dimension(220, 150));
        rootFrame.setVisible(true);
        rootFrame.validate();
    }

    private void createSystemTray() {
        final SystemTray tray = SystemTray.getSystemTray();
        final Image image = Toolkit.getDefaultToolkit().getImage("/Library/Desktop Pictures/Nature/Earth Horizon.jpg");
        final PopupMenu popup = new PopupMenu();
        addMenuItem("Next", nextActionListener, popup);
        addMenuItem("Prev", previousActionListener, popup);
        addMenuItem("Next Group", nextGroupActionListener, popup);
        addMenuItem("Previous Group", previousGroupActionListener, popup);
        addMenuItem("Hide", hideActionListener, popup);
        addMenuItem("Exit", exitActionListener, popup);
        trayIcon = new TrayIcon(image, "Image Rotator", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(nextActionListener);
        try {
            tray.add(trayIcon);
        } catch (final AWTException e) {
            System.err.println(e);
        }
    }

    private void addMenuItem(final String label, final ActionListener actionListener, final PopupMenu popup) {
        final MenuItem menuItem = new MenuItem(label);
        menuItem.addActionListener(actionListener);
        popup.add(menuItem);
    }

    private JButton addButton(final String label, final ActionListener actionListener) {
        final JButton nextButton = new JButton(label);
        nextButton.addActionListener(actionListener);
        rootFrame.add(nextButton);
        return nextButton;
    }

    @Required
    public void setPreviousActionListener(final ActionListener previousActionListener) {
        this.previousActionListener = previousActionListener;
    }

    @Required
    public void setPreviousGroupActionListener(final ActionListener previousGroupActionListener) {
        this.previousGroupActionListener = previousGroupActionListener;
    }

    @Required
    public void setNextGroupActionListener(final ActionListener nextGroupActionListener) {
        this.nextGroupActionListener = nextGroupActionListener;
    }

    @Required
    public void setTimer(final Timer timer) {
        this.timer = timer;
    }

    @Required
    public void setNextActionListener(final ActionListener nextActionListener) {
        this.nextActionListener = nextActionListener;
    }

    @Required
    public void setExitActionListener(final ActionListener exitActionListener) {
        this.exitActionListener = exitActionListener;
    }

    @Required
    public void setHideActionListener(final ActionListener hideActionListener) {
        this.hideActionListener = hideActionListener;
    }

    @PostConstruct
    public void init() {
        timer.addActionListener(nextActionListener);
    }

    @Override
    public void notify(final CurrentItemInfo<File> info) {
        final String description = info.getItem().getName() + " " + info.getIndex() + "/"
                + info.getCount();
        if (label != null) {
            label.setText(description);
        }
        if (trayIcon != null) {
            final Image oldImage = image;
            image = Toolkit.getDefaultToolkit().createImage(info.getItem().getPath().toString());
            trayIcon.setImage(image);
            if (oldImage != null) {
                oldImage.flush();
            }
            trayIcon.setToolTip(description);
        }

    }

}
