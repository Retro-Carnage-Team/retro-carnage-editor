package net.retrocarnage.editor.model;

/**
 * This class holds license information about an asset.
 * 
 * @author Thomas Werner
 */
public class AttributionData {

    private String author;
    private String website;
    private String licenseLink;
    private String licenseText;        
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLicenseLink() {
        return licenseLink;
    }

    public void setLicenseLink(String licenseLink) {
        this.licenseLink = licenseLink;
    }

    public String getLicenseText() {
        return licenseText;
    }

    public void setLicenseText(String licenseText) {
        this.licenseText = licenseText;
    }    
    
}
