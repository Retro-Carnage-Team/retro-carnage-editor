package net.retrocarnage.editor.core.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import net.retrocarnage.editor.core.io.FileNameChecker;

/**
 * A DocumentFilter that prevents input of potentially dangerous filenames.
 * 
 * @author Thomas Werner
 */
public class SafeFilenameFilter extends DocumentFilter {
    
    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
        final String combined = fb.getDocument().getText(0, fb.getDocument().getLength()) + str;
        if(FileNameChecker.isValidFilenameOrEmpty(combined)) {
            super.insertString(fb, offs, str, a);
        }
    }
    
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) 
            throws BadLocationException {
        final StringBuilder builder = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        String combined;
        if(length > 0) {
            combined = builder.replace(offset, length, text).toString();
        } else {
            combined = builder.insert(offset, text).toString();
        }
        if(FileNameChecker.isValidFilenameOrEmpty(combined)) {
            fb.replace(offset, length, text, attr);
        }
    }
    
}
