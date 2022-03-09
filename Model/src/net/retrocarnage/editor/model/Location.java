package net.retrocarnage.editor.model;

/**
 * A Location of a mission on the unscaled world map.
 *
 * @author Thomas Werner
 * @see https://github.com/huddeldaddel/retro-carnage/blob/main/src/assets/location.go
 */
public class Location {

    private int longitude;  // X
    private int latitude;   // Y

    /**
     * Default constructor. Initializes location with 0, 0.
     */
    public Location() {
        // This constructor is intentionally empty.
    }

    public Location(final int lng, final int lat) {
        longitude = lng;
        latitude = lat;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(final int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(final int longitude) {
        this.longitude = longitude;
    }

    @Override
    public Location clone() {
        return new Location(longitude, latitude);
    }

    @Override
    public String toString() {
        return "Location [latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
