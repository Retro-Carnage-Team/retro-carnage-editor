package net.retrocarnage.editor.renderer.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.model.EnemyMovement;
import net.retrocarnage.editor.model.EnemySkin;
import net.retrocarnage.editor.model.EnemyType;
import net.retrocarnage.editor.model.Layer;
import net.retrocarnage.editor.model.Position;
import net.retrocarnage.editor.renderer.common.MemoizedImageScaler;

/**
 * Paints the Obstacles that are contained in a stack of Layers.
 *
 * @author Thomas Werner
 */
class EnemyPainter {

    private static final Logger logger = Logger.getLogger(EnemyPainter.class.getName());
    private static final MemoizedImageScaler imageScaler = new MemoizedImageScaler();
    private static final String MODEL_PATH = "/net/retrocarnage/editor/renderer/images/enemies/%d-%s.png";
    private static final String MODEL_KEY = "%d-%s-%.4f";

    private final List<Layer> layers;
    private final Graphics2D g2d;
    private final float scalingFactor;

    public EnemyPainter(final List<Layer> layers, final Graphics2D g2d, final float scalingFactor) {
        this.layers = layers;
        this.g2d = g2d;
        this.scalingFactor = scalingFactor;
    }

    public void paintEnemies() {
        layers.stream()
                .filter(l -> (l.isVisible()))
                .map(l -> l.getEnemies())
                .forEachOrdered(enemies -> {
                    for (int idx = enemies.size() - 1; idx >= 0; idx--) {
                        paintEnemy(enemies.get(idx));
                    }
                });
    }

    /**
     * Paints a single enemy.
     *
     * @param enemy the Enemy to be painted
     */
    private void paintEnemy(final Enemy enemy) {
        final BufferedImage enemyModel = loadEnemyImage(enemy);
        if (null == enemyModel) {
            logger.log(Level.SEVERE, "Enemy asset not found");
            return;
        }

        final Position unscaledPosition = (enemy.getType() == EnemyType.Person.getValue())
                ? applySkinOffset(EnemySkin.findByName(enemy.getSkin()), enemy.getPosition())
                : enemy.getPosition();
        try {
            final String key = String.format(MODEL_KEY, enemy.getType(), enemy.getSkin(), scalingFactor);
            final BufferedImage scaledImage = imageScaler.getScaledImage(enemyModel, key, scalingFactor);
            final Position scaledPosition = unscaledPosition.scale(scalingFactor);
            g2d.drawImage(
                    scaledImage,
                    scaledPosition.getX(),
                    scaledPosition.getY(),
                    scaledImage.getWidth(),
                    scaledImage.getHeight(),
                    null
            );
                        
            paintEnemyMovements(enemy, enemy.getPosition().scale(scalingFactor).getCenter()); 
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to scale or draw enemy image", ex);
        }
    }
    
    private void paintEnemyMovements(final Enemy enemy, final Point startPosition) {
        Point a = startPosition;        
        for(EnemyMovement em: enemy.getMovements()) {            
            final Point b = new Point(a.x + (int)(em.getDistanceX() * scalingFactor), 
                                      a.y + (int)(em.getDistanceY() * scalingFactor)
            );
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(a.x, a.y, b.x, b.y);
            a = b;            
        }
    }

    private BufferedImage loadEnemyImage(final Enemy enemy) {
        try {
            final String path = String.format(MODEL_PATH, enemy.getType(), enemy.getSkin());
            return ImageIO.read(EnemyPainter.class.getResource(path));
        } catch (final IOException ex) {
            logger.log(Level.WARNING, "Failed to load resource", ex);
            return null;
        }
    }

    private Position applySkinOffset(final EnemySkin skin, final Position playerPosition) {
        final Position result = playerPosition.clone();
        switch (skin) {
            case WoodlandWithSMG:
                result.setY(result.getY() - 30);
                break;
            case GreyJumperWithRifle:
                result.setY(result.getY() - 30);
                break;
            case DigitalWithPistols:
                result.setY(result.getY() - 30);
                break;
            case WoodlandWithBulletproofVest:
                result.setY(result.getY() - 30);
                break;
            default:
                logger.log(Level.WARNING, "Unknown enemy skin: can't apply offsets.");
                break;
        }
        return result;
    }

}
