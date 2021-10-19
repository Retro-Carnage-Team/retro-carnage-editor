package net.retrocarnage.editor.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Enumeration of possible directions for game play sections.
 *
 * @author Thomas Werner
 */
public enum SectionDirection {

    UP("up"),
    LEFT("left"),
    RIGHT("right");

    private final String exportName;

    private SectionDirection(final String exportName) {
        this.exportName = exportName;
    }

    /**
     * @return the string value of this direction - as it's used in Retro-Carnage mission files
     */
    public String getExportName() {
        return exportName;
    }

    /**
     * @return SectionDirections that can be used in a section following a section of this SectionDirection
     */
    public Collection<SectionDirection> getPossibleSuccessors() {
        return (UP == this)
                ? Arrays.asList(LEFT, RIGHT)
                : Collections.singletonList(UP);
    }

}
