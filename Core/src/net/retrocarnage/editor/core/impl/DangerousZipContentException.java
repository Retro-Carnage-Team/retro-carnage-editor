package net.retrocarnage.editor.core.impl;

/**
 * Indicates that handling a ZIP archive failed due to suspicious content.
 * 
 * @author Thomas Werner
 */
public class DangerousZipContentException extends Exception {

    /**
     * Creates a new instance of <code>DangerousZipContentException</code> without detail message.
     */
    public DangerousZipContentException() {
    }

    /**
     * Constructs an instance of <code>DangerousZipContentException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DangerousZipContentException(String msg) {
        super(msg);
    }
}
