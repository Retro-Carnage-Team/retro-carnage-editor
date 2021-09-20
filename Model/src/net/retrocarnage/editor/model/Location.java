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

    public Location() {
    }

    public Location(final int lat, final int lng) {
        latitude = lat;
        longitude = lng;
    }

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

    @Override
    public String toString() {
        return "Location [latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
