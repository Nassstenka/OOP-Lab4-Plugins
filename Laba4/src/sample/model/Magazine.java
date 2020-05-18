package sample.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("magazine")
public class Magazine extends PrintedEdition implements Serializable {
    public enum AgeCategory {
        forKids,
        forTeenagers,
        forAdults;
    }
    private AgeCategory ageCategory;

    public Magazine(String name, String publishingDate, String publishingCompany, boolean takeawayPermission, AdditionalInfo.Quality quality, AgeCategory ageCategory) {
        super(name, publishingDate, publishingCompany, takeawayPermission, quality);
        this.ageCategory = ageCategory;
    }

    public Magazine(){

    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "ageCategory=" + ageCategory +
                ", name='" + name + '\'' +
                ", publishingDate=" + publishingDate +
                ", publishingCompany='" + additionalInfo.publishingCompany +
                ", takeawayPermission=" + additionalInfo.takeawayPermission +
                ", quality=" + additionalInfo.quality +
                '}';
    }

    @Override
    public String writeData(){
        String result = "Magazine;" + this.getAgeCategory().toString() + ';'
                + this.getName() + ';' + this.getPublishingDate().toString() + ';'
                + this.getAdditionalInfo().getPublishingCompany() + ';'
                + this.getAdditionalInfo().isTakeawayPermission() + ';'
                + this.getAdditionalInfo().getQuality().toString();
        return result;
    }
}
