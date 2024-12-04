package net.retrocarnage.editor.nodes.impl;

import java.beans.PropertyEditor;
import net.retrocarnage.editor.gameplayeditor.interfaces.GamePlayEditorProxyFactory;
import net.retrocarnage.editor.gameplayeditor.interfaces.SelectionController;
import net.retrocarnage.editor.model.Enemy;
import net.retrocarnage.editor.nodes.propeditors.SkinPropertyEditor;
import net.retrocarnage.editor.nodes.propeditors.SpeedPropertyEditor;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;

/**
 * A factory that builds properties for Enemies.
 *
 * @author Thomas Werner
 */
public final class EnemyPropsFactory {

    private EnemyPropsFactory() {
        // Intentionally empty.
    }

    public static Sheet.Set buildEnemySheet(final Enemy enemy, final boolean readonly) {
        final Sheet.Set enemySet = Sheet.createPropertiesSet();
        enemySet.setName("Enemy");
        enemySet.setDisplayName("Enemy");

        final Node.Property<String> skinProp = buildSkinProperty(enemy, readonly);
        skinProp.setName(Enemy.PROPERTY_SKIN);
        enemySet.put(skinProp);

        final Node.Property<Integer> speedProp = buildSpeedProperty(enemy, readonly);
        speedProp.setName(Enemy.PROPERTY_SPEED);
        enemySet.put(speedProp);

        return enemySet;
    }

    private static Node.Property<String> buildSkinProperty(final Enemy enemy, final boolean readonly) {
        return new Node.Property<String>(String.class) {

            @Override
            public String getValue() {
                return enemy.getSkin();
            }

            @Override
            public void setValue(final String skin) {
                if (!readonly) {
                    enemy.setSkin(skin);
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
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
            public PropertyEditor getPropertyEditor() {
                return new SkinPropertyEditor();
            }

        };
    }

    private static Node.Property<Integer> buildSpeedProperty(final Enemy enemy, final boolean readonly) {
        return new Node.Property<Integer>(Integer.class) {

            @Override
            public Integer getValue() {
                return enemy.getSpeed();
            }

            @Override
            public void setValue(final Integer speed) {
                if (!readonly) {
                    enemy.setSpeed(speed);
                    GamePlayEditorProxyFactory
                            .buildGamePlayEditorProxy()
                            .getLookup()
                            .lookup(SelectionController.class)
                            .selectionModified();
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
            public PropertyEditor getPropertyEditor() {
                return new SpeedPropertyEditor();
            }

        };
    }

}
