package net.retrocarnage.editor.core.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<String, String> unsafeToSafeFielnames = new HashMap<>();
     
    @Before
    public void setUp() {
        invalidFileNames.clear();
        invalidFileNames.add("a?bc.xml");
        invalidFileNames.add("a\\bc.xml");
        invalidFileNames.add("a/bc.xml");
        invalidFileNames.add("abc,xml");
        invalidFileNames.add("a😁bc.xml");
        
        validFileNames.clear();
        validFileNames.add("cba.xml");
        validFileNames.add("abc 123.xml");
        validFileNames.add("abc-132_mork.xml");
        validFileNames.add("abc");
        validFileNames.add(".xml");
        
        unsafeToSafeFielnames.clear();
        unsafeToSafeFielnames.put("xbf.xml", "xbf.xml");
        unsafeToSafeFielnames.put("abc123.xml", "abc123.xml");
        unsafeToSafeFielnames.put("/home/tom/.bashrc", "_home_tom_.bashrc");
        unsafeToSafeFielnames.put("\\usr\\bin\\bash", "_usr_bin_bash");
        unsafeToSafeFielnames.put("abc?.xml", "abc_.xml");
        unsafeToSafeFielnames.put("kkk 878.xml", "kkk 878.xml");
        unsafeToSafeFielnames.put("a", "a");
        unsafeToSafeFielnames.put("", "");
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
    
    /**
     * Test of isFilenameAllowed method with invalid inputs
     */
    @Test
    public void testBuildSaveVersion() {
        unsafeToSafeFielnames.forEach((original, converted) -> 
            assertEquals(converted, FileNameChecker.buildSaveVersion(original))
        );
    }
    
}
