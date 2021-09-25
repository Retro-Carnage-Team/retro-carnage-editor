package net.retrocarnage.editor.core.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Abstract implementation of the DocumentListener that allows handling all events with a single method.
 *
 * @author Thomas Werner
 */
public abstract class AbstractDocumentListener implements DocumentListener {

    @Override
    public void changedUpdate(DocumentEvent e) {
        handleChange(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleChange(e);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        handleChange(e);
    }

    protected void handleChange(DocumentEvent e) {

    }
}
