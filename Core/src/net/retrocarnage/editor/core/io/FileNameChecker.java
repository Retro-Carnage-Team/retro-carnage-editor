package net.retrocarnage.editor.core.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks filenames so that they can be trusted.
 * 
 * @author Thomas Werner
 */
public class FileNameChecker {
   
    private static final String SAFE_FILENAME_PATTERN = "^[\\w\\s-\\.]+$";
    
    private FileNameChecker() {
        // No need to create instances of this class
    }
    
    /**
     * Checks the given string for possible use as filename.
     * 
     * @param input String to be checked
     * @return true if input can be used as filename
     */
    public static boolean isValidFilename(final String input) {
        final Pattern p = Pattern.compile(SAFE_FILENAME_PATTERN);
        final Matcher m = p.matcher(input);
        return m.matches();
    }
    
   /**
     * Checks if the given string is empty or can be used as filename.
     * 
     * @param input String to be checked
     * @return true if input is empty or can be used as filename
     */
    public static boolean isValidFilenameOrEmpty(final String input) {
        return input.isEmpty() || isValidFilename(input);
    }
    
}
