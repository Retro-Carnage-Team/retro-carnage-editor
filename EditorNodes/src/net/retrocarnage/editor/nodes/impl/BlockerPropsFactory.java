package net.retrocarnage.editor.nodes.impl;

import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxy;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Blocker;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;

/**
 * A factory that builds properties for Blockers.
 *
 * @author Thomas Werner
 */
public final class BlockerPropsFactory {

    private BlockerPropsFactory() {
        // Intentionally empty.
    }

    public static Sheet.Set buildBlockerSheet(final Blocker blocker, final boolean readonly) {
        final Sheet.Set blockerSet = Sheet.createPropertiesSet();
        blockerSet.setName("Blocker");
        blockerSet.setDisplayName("Blocker");

        final Node.Property obstacleProp = BlockerPropsFactory.buildObstacleProperty(blocker, readonly);
        obstacleProp.setName("Stops players");
        blockerSet.put(obstacleProp);

        final Node.Property bulletsProp = BlockerPropsFactory.buildStoppingBulletsProperty(blocker, readonly);
        bulletsProp.setName("Stops bullets");
        blockerSet.put(bulletsProp);

        final Node.Property explosivesProp = BlockerPropsFactory.buildStoppingExplosivesProperty(blocker, readonly);
        explosivesProp.setName("Stops explosives");
        blockerSet.put(explosivesProp);

        return blockerSet;
    }

    private static Node.Property buildStoppingBulletsProperty(final Blocker blocker, final boolean readonly) {
        return new Node.Property<Boolean>(Boolean.class) {

            @Override
            public Boolean getValue() {
                return blocker.isBulletStopper();
            }

            @Override
            public void setValue(final Boolean t) {
                if (!readonly) {
                    blocker.setBulletStopper(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

    private static Node.Property buildStoppingExplosivesProperty(final Blocker blocker, final boolean readonly) {
        return new Node.Property<Boolean>(Boolean.class) {

            @Override
            public Boolean getValue() {
                return blocker.isExplosiveStopper();
            }

            @Override
            public void setValue(final Boolean t) {
                if (!readonly) {
                    blocker.setExplosiveStopper(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }
        };
    }

    private static Node.Property buildObstacleProperty(final Blocker blocker, final boolean readonly) {
        return new Node.Property<Boolean>(Boolean.class) {

            @Override
            public Boolean getValue() {
                return blocker.isObstacle();
            }

            @Override
            public void setValue(final Boolean t) {
                if (!readonly) {
                    blocker.setObstacle(t);
                    GamePlayEditorProxy.getDefault().getLookup().lookup(SelectionController.class).selectionModified();
                }
            }

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean canWrite() {
                return !readonly;
            }

            @Override
            public String getShortDescription() {
                return "Prevents the user from accessing this area (when checked)";
            }
        };
    }

}
