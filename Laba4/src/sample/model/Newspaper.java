package sample.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("newspaper")
public class Newspaper extends PrintedEdition implements Serializable {
    public enum NewspaperType {
        Local,
        Regional,
        State,
        International;
    }
    private NewspaperType type;
    private boolean isColorful;

    public Newspaper(String name, String publishingDate, String publishingCompany, boolean takeawayPermission, AdditionalInfo.Quality quality, NewspaperType type, boolean isColorful) {
        super(name, publishingDate, publishingCompany, takeawayPermission, quality);
        this.type = type;
        this.isColorful = isColorful;
    }

    public Newspaper() {
    }

    public void setType(NewspaperType type) {
        this.type = type;
    }

    public void setColorful(boolean colorful) {
        isColorful = colorful;
    }

    public NewspaperType getType() {
        return type;
    }

    public boolean isColorful() {
        return isColorful;
    }

    @Override
    public String toString() {
        return "Newspaper{" +
                "type=" + type +
                ", isColorful=" + isColorful +
                ", name='" + name + '\'' +
                ", publishingDate=" + publishingDate +
                ", publishingCompany='" + additionalInfo.publishingCompany +
                ", takeawayPermission=" + additionalInfo.takeawayPermission +
                ", quality=" + additionalInfo.quality +
                '}';
    }

    @Override
    public String writeData(){
        String result = "Newspaper;" + this.getType().toString() + ';'
                          + this.isColorful() + ';'
                          + this.getName() + ';' + this.getPublishingDate().toString() + ';'
                          + this.getAdditionalInfo().getPublishingCompany() + ';'
                          + this.getAdditionalInfo().isTakeawayPermission() + ';'
                          + this.getAdditionalInfo().getQuality().toString();
        return result;
    }
}
