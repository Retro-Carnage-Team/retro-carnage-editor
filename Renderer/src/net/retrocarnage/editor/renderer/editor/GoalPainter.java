package net.retrocarnage.editor.renderer.editor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.retrocarnage.editor.model.Goal;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Position;
import net.retrocarnage.editor.renderer.common.TextureFactory;

/**
 * Paints the Goal that is contained in a stack of Layers.
 *
 * @author Thomas Werner
 */
class GoalPainter {

    private static final Map<String, BufferedImage> textureCache = new HashMap<>();

    private final List<Layer> layers;
    private final Graphics2D g2d;
    private final float scalingFactor;

    public GoalPainter(final List<Layer> layers, final Graphics2D g2d, final float scalingFactor) {
        this.layers = layers;
        this.g2d = g2d;
        this.scalingFactor = scalingFactor;
    }

    public void paintGoal() {
        final Optional<Goal> optionalGoal = layers.stream()
                .filter(layer -> layer.isVisible())
                .filter(layer -> layer.getGoal() != null)
                .map(layer -> layer.getGoal())
                .findAny();
        if (optionalGoal.isPresent()) {
            paintGoal(optionalGoal.get());
        }
    }

    /**
     * Paints a single Goal. This is done by using a texture to fill the goal area.
     *
     * @param goal the goal to be painted
     */
    private void paintGoal(final Goal goal) {
        final Position scaledPosition = goal.getPosition().scale(scalingFactor);
        final int sideLength = (int) (10 * scalingFactor);
        final BufferedImage texture = getGoalTexture(sideLength);
        final Rectangle anchor = new Rectangle(0, 0, sideLength, sideLength);
        g2d.setPaint(new TexturePaint(texture, anchor));
        g2d.fill(scaledPosition.toRectangle());
    }

    /**
     * Gets the texture to be used to paint a goal with the given parameters.
     *
     * @param sideLength specifies the width / height of the texture image. Used for zooming.
     * @return the texture
     */
    private static BufferedImage getGoalTexture(final int sideLength) {
        final String imageKey = String.format("%d", sideLength);
        if (textureCache.containsKey(imageKey)) {
            return textureCache.get(imageKey);
        }

        final BufferedImage result = TextureFactory.buildGoalTexture(sideLength);
        textureCache.put(imageKey, result);
        return result;
    }

}
