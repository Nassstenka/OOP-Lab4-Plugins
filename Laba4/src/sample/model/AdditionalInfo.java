package sample.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;


public class AdditionalInfo implements Serializable {
    public enum Quality{
        Good,
        Normal,
        Bad;
    }
    public String publishingCompany;
    public boolean takeawayPermission;
    public Quality quality;

    public AdditionalInfo(String publishingCompany, boolean takeawayPermission, Quality quality) {
        this.publishingCompany = publishingCompany;
        this.takeawayPermission = takeawayPermission;
        this.quality = quality;
    }

    public AdditionalInfo(){

    }

    public String getPublishingCompany() {
        return publishingCompany;
    }

    public boolean isTakeawayPermission() {
        return takeawayPermission;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setPublishingCompany(String publishingCompany) {
        this.publishingCompany = publishingCompany;
    }

    public void setTakeawayPermission(boolean takeawayPermission) {
        this.takeawayPermission = takeawayPermission;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "AdditionalInfo{" +
                "publishingCompany='" + publishingCompany + '\'' +
                ", takeawayPermission=" + takeawayPermission +
                ", quality=" + quality +
                '}';
    }
}
