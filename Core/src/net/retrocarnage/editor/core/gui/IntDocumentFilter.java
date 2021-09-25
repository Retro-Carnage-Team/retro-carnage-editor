package net.retrocarnage.editor.core.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * A DocumentFilter that can be used to restrict the input of JTextFields to integers.
 *
 * @author Thomas Werner
 * @see https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
 */
public class IntDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(
            final FilterBypass fb, final int offset, final String input, final AttributeSet attr
    ) throws BadLocationException {
        final Document doc = fb.getDocument();
        final String text = new StringBuilder()
                .append(doc.getText(0, doc.getLength()))
                .insert(offset, input).toString();
        if (test(text)) {
            super.insertString(fb, offset, input, attr);
        }
    }

    private boolean test(final String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void replace(
            final FilterBypass fb, final int offset, final int length, final String input, final AttributeSet attrs
    ) throws BadLocationException {
        final Document doc = fb.getDocument();
        final String text = new StringBuilder()
                .append(doc.getText(0, doc.getLength()))
                .replace(offset, offset + length, input)
                .toString();
        if (test(text)) {
            super.replace(fb, offset, length, input, attrs);
        }
    }

    @Override
    public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
        final Document doc = fb.getDocument();
        final String text = new StringBuilder()
                .append(doc.getText(0, doc.getLength()))
                .delete(offset, offset + length)
                .toString();
        if (test(text)) {
            super.remove(fb, offset, length);
        }
    }

}
