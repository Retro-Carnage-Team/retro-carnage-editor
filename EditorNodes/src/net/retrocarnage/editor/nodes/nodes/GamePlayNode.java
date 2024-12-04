package net.retrocarnage.editor.nodes.nodes;

import java.lang.reflect.InvocationTargetException;
import net.retrocarnage.editor.gameplayeditor.interfaces.LayerController;
import net.retrocarnage.editor.missionmanager.MissionServiceFactory;
import net.retrocarnage.editor.model.GamePlay;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;

/**
 * A Node for a GamePlay.
 *
 * @author Thomas Werner
 */
public class GamePlayNode extends AbstractNode {

    private final String name;

    public GamePlayNode(final GamePlay gamePlay, final LayerController layerCtrl) {
        super(new GamePlayGroupsChildren(layerCtrl), Lookups.singleton(gamePlay));
        name = MissionServiceFactory
                .buildMissionService()
                .getMission(gamePlay.getMissionId())
                .getName();
    }

    @Override
    protected Sheet createSheet() {
        final Sheet sheet = Sheet.createDefault();
        final Sheet.Set set = Sheet.createPropertiesSet();
        set.put(new PropertySupport.ReadOnly<String>("Name", String.class, "Name", "Name") {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return name;
            }
        });
        sheet.put(set);
        return sheet;
    }

}
