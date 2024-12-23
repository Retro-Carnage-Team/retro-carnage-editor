package net.retrocarnage.editor.core.io;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for FileNameChecker.
 * 
 * @author Thomas Werner
 */
public class FileNameCheckerTest {
    
    private final List<String> invalidFileNames = new ArrayList<>();
    private final List<String> validFileNames = new ArrayList<>();
     
    @Before
    public void setUp() {
        invalidFileNames.clear();
        invalidFileNames.add("a?bc.xml");
        invalidFileNames.add("a\\bc.xml");
        invalidFileNames.add("a/bc.xml");
        invalidFileNames.add("abc,xml");
        invalidFileNames.add("a😁bc.xml");
        
        validFileNames.clear();
        validFileNames.add("abc.xml");
        validFileNames.add("abc 123.xml");
        validFileNames.add("abc-132_mork.xml");
        validFileNames.add("abc");
        validFileNames.add(".xml");
    }
    
    /**
     * Test of isFilenameAllowed method with valid inputs
     */
    @Test
    public void testIsValidFilenameWithValidNames() {
        for(String input: validFileNames) {
            assertEquals(true, FileNameChecker.isValidFilename(input));
        }
    }
    
    /**
     * Test of isFilenameAllowed method with invalid inputs
     */
    @Test
    public void testIsValidFilenameWithInvalidNames() {
        for(String input: invalidFileNames) {
            assertEquals(false, FileNameChecker.isValidFilename(input));
        }
    }
    
    /**
     * Test of isFilenameAllowed method with valid inputs
     */
    @Test
    public void testIsValidFilenameOrEmptyWithValidNames() {
        for(String input: validFileNames) {
            assertEquals(true, FileNameChecker.isValidFilenameOrEmpty(input));
        }
        assertEquals(true, FileNameChecker.isValidFilenameOrEmpty(""));
    }
    
    /**
     * Test of isFilenameAllowed method with invalid inputs
     */
    @Test
    public void testIsValidFilenameOrEmptyWithInvalidNames() {
        for(String input: invalidFileNames) {
            assertEquals(false, FileNameChecker.isValidFilenameOrEmpty(input));
        }
    }
    
}
