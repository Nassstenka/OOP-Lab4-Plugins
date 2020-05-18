package sample.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Newspaper.class, name = "newspaper"),
        @JsonSubTypes.Type(value = Magazine.class, name = "magazine"),
        @JsonSubTypes.Type(value = Book.class, name = "book"),
})
public abstract class PrintedEdition implements Serializable {
    protected String name;
    protected String publishingDate;
    protected AdditionalInfo additionalInfo;

    public PrintedEdition(String name, String publishingDate, String publishingCompany, boolean takeawayPermission, AdditionalInfo.Quality quality) {
        this.name = name;
        this.publishingDate = publishingDate;
        this.additionalInfo = new AdditionalInfo(publishingCompany, takeawayPermission, quality);
    }

    public PrintedEdition(){

    }

    public String getName() {
        return name;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    @Override
    public String toString() {
        return "PrintedEdition{" +
                "name='" + name + '\'' +
                ", publishingDate=" + publishingDate +
                ", publishingCompany='" + additionalInfo.publishingCompany +
                ", takeawayPermission=" + additionalInfo.takeawayPermission +
                ", quality=" + additionalInfo.quality +
                '}';
    }

    public String writeData(){
       return "";
    }
}
