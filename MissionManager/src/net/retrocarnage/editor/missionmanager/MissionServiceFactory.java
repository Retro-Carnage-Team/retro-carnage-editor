package net.retrocarnage.editor.missionmanager;

import net.retrocarnage.editor.missionmanager.impl.MissionServiceImpl;

/**
 * Provides a service implementation to manage missions.
 * 
 * @author Thomas Werner
 */
public enum MissionServiceFactory {
    
    INSTANCE;
    
    private final MissionService missionService;
 
    private MissionServiceFactory() {
        missionService = new MissionServiceImpl();
    }
 
    public MissionService buildMissionService() {
        return missionService;
    }
    
}
