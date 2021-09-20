package net.retrocarnage.editor.missionmanager.editor;

import net.retrocarnage.editor.model.Mission;

/**
 * A subset of the mission class which gets used in the view's JTable.
 *
 * @author Thomas Werner
 */
class SelectableMission {

    private final Mission delegate;

    SelectableMission(final Mission mission) {
        delegate = mission;
    }

    String getId() {
        return delegate.getId();
    }

    String getName() {
        return delegate.getName();
    }

}
