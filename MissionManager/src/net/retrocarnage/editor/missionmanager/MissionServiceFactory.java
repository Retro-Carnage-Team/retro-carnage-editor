package net.retrocarnage.editor.missionmanager;

import net.retrocarnage.editor.missionmanager.impl.MissionServiceImpl;

/**
 * Provides a service implementation to manage missions.
 * 
 * @author Thomas Werner
 */
public class MissionServiceFactory {
    
    private static class SingletonHelper {
        private static final MissionService serviceInstance = new MissionServiceImpl();
    }
    
    private MissionServiceFactory() { }

    public static MissionService buildMissionService() {
        return SingletonHelper.serviceInstance;
    }
    
}
