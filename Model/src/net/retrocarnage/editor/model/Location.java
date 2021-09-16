package net.retrocarnage.editor.model;

/**
 * A Location of a mission on the unscaled world map.
 * 
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/location.go
 */
public class Location {
    
    private int latitude;
    private int longitude;

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }    
    
}
