package org.bellegar.rotator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grouper {
    private static final Pattern pattern1 = Pattern.compile("(\\D+)\\d+.*");

    public String groupForName(final String name) {
        final Matcher matcher = pattern1.matcher(name);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return name.substring(0, 1);
        }
    }
}
