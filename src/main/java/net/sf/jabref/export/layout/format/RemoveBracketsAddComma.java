package net.sf.jabref.export.layout.format;

import net.sf.jabref.export.layout.LayoutFormatter;

/**
 * Remove brackets formatter.
 */
public class RemoveBracketsAddComma implements LayoutFormatter {
    @Override
    public String format(String fieldText) {
        StringBuilder builder = new StringBuilder(fieldText.length());

        for (char c: fieldText.toCharArray()) {
            if (c != '{' && c != '}') {
                builder.append(c);
            } else if (c == '}') {
                builder.append(',');
            }
        }
        return builder.toString();
    }
}
