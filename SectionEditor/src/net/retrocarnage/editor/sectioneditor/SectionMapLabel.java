package net.retrocarnage.editor.sectioneditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.minimap.MinimapRenderer;

/**
 * A JLabel that displays a mini-map of the sections that the user configured.
 *
 * @author Thomas Werner
 */
class SectionMapLabel extends JLabel {

    private static final int borderWidth = 10;
    private List<Section> sections;

    /**
     * Updates the sections to be drawn.
     *
     * Changes to the given list will not be monitored. To update the map you'll have to call this method again.
     *
     * @param sections the sections to be drawn.
     */
    public void setSections(final List<Section> sections) {
        if (null == sections) {
            this.sections = null;
        } else {
            this.sections = Collections.unmodifiableList(sections);
            repaint();
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (null != sections && !sections.isEmpty()) {
            final Graphics2D g2d = (Graphics2D) g;

            final MinimapRenderer renderer = new MinimapRenderer(sections);
            final Dimension mapSize = renderer.getSize(getWidth() - borderWidth * 2, getHeight() - borderWidth * 2);
            g2d.translate((getWidth() - mapSize.getWidth()) / 2, (getHeight() - mapSize.getHeight()) / 2);
            renderer.render(g2d, getWidth() - borderWidth * 2, getHeight() - borderWidth * 2);
        }
    }

}
