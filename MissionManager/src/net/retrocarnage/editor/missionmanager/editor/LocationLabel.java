package net.retrocarnage.editor.missionmanager.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import javax.swing.JLabel;
import net.retrocarnage.editor.model.Location;

/**
 * A JLabel that marks the coordinates of a specified location.
 *
 * @author Thomas Werner
 */
class LocationLabel extends JLabel {

    private static final int MARKER_SIZE = 15;
    private static final int MARKER_STROKE = 5;
    private Location location;

    public void setLocation(final Location location) {
        if (null == location) {
            this.location = null;
        } else {
            this.location = new Location(location.getLongitude(), location.getLatitude());
        }
        repaint();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (null != location) {
            double scale = 300 / 783.0;
            double offset = (getHeight() - 300) / 2.0;

            final Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(MARKER_STROKE));
            g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            g2d.drawLine(
                    (int) ((location.getLongitude() - MARKER_SIZE) * scale),
                    (int) ((location.getLatitude() - MARKER_SIZE) * scale + offset),
                    (int) ((location.getLongitude() + MARKER_SIZE) * scale),
                    (int) ((location.getLatitude() + MARKER_SIZE) * scale + offset)
            );
            g2d.drawLine(
                    (int) ((location.getLongitude() - MARKER_SIZE) * scale),
                    (int) ((location.getLatitude() + MARKER_SIZE) * scale + offset),
                    (int) ((location.getLongitude() + MARKER_SIZE) * scale),
                    (int) ((location.getLatitude() - MARKER_SIZE) * scale + offset)
            );
        }
    }

}
