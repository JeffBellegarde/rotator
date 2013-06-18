package org.bellegar.rotator;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bellegar.rotator.RotatorApplicationImpl.Group.CurrentItemInfo;
import org.bellegar.rotator.RotatorApplicationImpl.Group.Direction;
import org.bellegar.rotator.applescript.ChangeDesktopCommand;
import org.bellegar.rotator.applescript.DesktopChangeEvent;
import org.bellegar.rotator.applescript.DesktopChangeEventImpl;
import org.springframework.beans.factory.annotation.Required;

public class RotatorApplicationImpl implements RotatorApplication {
    public enum Level {
        GROUP, FILE
    }

    private final Group<Group<File>> groups = new Group<Group<File>>();
    // private RotatorUI rotatorUI;
    private DesktopChangeEvent notifyGrowlOfDesktopChange;
    private ChangeDesktopCommand changeDesktop;

    public void addGroup(final Group<File> group) {
        groups.add(group);
    }

    // @Required
    // public void setRotatorUI(final RotatorUI rotatorUI) {
    // this.rotatorUI = rotatorUI;
    // }

    @Required
    public void setNotifyGrowlOfDesktopChange(final DesktopChangeEventImpl notifyGrowlOfDesktopChange) {
        this.notifyGrowlOfDesktopChange = notifyGrowlOfDesktopChange;
    }

    public void setChangeDesktop(final ChangeDesktopCommand changeDesktop) {
        this.changeDesktop = changeDesktop;
    }

    public static class Group<T> {
        public static class CurrentItemInfo<T> {
            private final int index;
            private final int count;
            private final T item;

            public CurrentItemInfo(final T item, final int index, final int count) {
                this.item = item;
                this.index = index;
                this.count = count;
            }

            public int getIndex() {
                return index;
            }

            public int getCount() {
                return count;
            }

            public T getItem() {
                return item;
            }

        }

        private final List<T> items = new LinkedList<T>();
        private int currentIndex = 0;

        public enum Direction {
            NEXT, PREVIOUS
        }

        public boolean has(final Direction d) {
            if (d == Direction.NEXT) {
                return hasNext();
            } else {
                return hasPrevious();
            }
        }

        public T move(final Direction d) {
            if (d == Direction.NEXT) {
                return next();
            } else {
                return previous();
            }
        }

        public boolean hasNext() {
            return currentIndex + 1 < items.size();
        }

        public T next() {
            currentIndex++;
            return getCurrent();
        }

        private T getCurrent() {
            return items.get(currentIndex);
        }

        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        public T previous() {
            currentIndex--;
            return getCurrent();
        }

        public void add(final T item) {
            items.add(item);
        }

        public CurrentItemInfo<T> getCurrentInfo() {
            return new CurrentItemInfo<T>(getCurrent(), currentIndex + 1, items.size());
        }

        private class MovementRunnable implements Runnable {

            private final Direction direction;

            public MovementRunnable(final Direction direction) {
                this.direction = direction;
            }

            @Override
            public void run() {
                move(direction);

            }
        }

        public Runnable getMovementRunnable(final Direction direction) {
            return new MovementRunnable(direction);
        }

    }

    public synchronized CurrentItemInfo<File> setPicture() {
        final CurrentItemInfo<File> currentItemInfo = groups.getCurrent().getCurrentInfo();
        changeDesktop.notify(currentItemInfo);
        notifyGrowlOfDesktopChange.notify(currentItemInfo);
        return currentItemInfo;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bellegar.rotator.RotatorApplication#move(org.bellegar.rotator.RotatorApplicationImpl.Level,
     * org.bellegar.rotator.RotatorApplicationImpl.Group.Direction)
     */
    public CurrentItemInfo<File> move(final Level level, final Direction direction) {
        synchronized (groups) {
            if (level == Level.GROUP) {
                moveGroup(direction);
            } else {
                moveFile(direction);
            }
            return setPicture();
        }
    }

    private void moveGroup(final Direction direction) {
        if (groups.has(direction)) {
            groups.move(direction);
        }

    }

    private void moveFile(final Direction direction) {
        final Group<File> currentGroup = groups.getCurrent();
        if (currentGroup.has(direction)) {
            currentGroup.move(direction);
        } else {
            groups.move(direction);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bellegar.rotator.RotatorApplication#changeToDefaultDesktopBackground()
     */
    public void changeToDefaultDesktopBackground() {
        changeDesktop.change("/Library/Desktop Pictures/Nature/Earth Horizon.jpg");

    }
}
