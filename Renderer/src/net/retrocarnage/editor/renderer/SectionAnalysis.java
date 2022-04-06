package net.retrocarnage.editor.renderer;

/**
 * Result of a structure analysis of a map.
 *
 * @author Thomas Werner
 */
public class SectionAnalysis {

    private final int mapHeight;
    private final int mapWidth;
    private final int startX;
    private final int startY;

    SectionAnalysis(int mapWidth, int mapHeight, int startX, int startY) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.startX = startX;
        this.startY = startY;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

}
