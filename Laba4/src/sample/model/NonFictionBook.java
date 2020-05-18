package sample.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("nonfiction")
public class NonFictionBook extends Book implements Serializable {
    public enum Sphere{
        programming,
        business,
        science;
    }
    private Sphere sphere;

    public NonFictionBook(String name, String publishingDate, String publishingCompany, boolean takeawayPermission, AdditionalInfo.Quality quality, String authorName, int pageNumber, int rage, Sphere sphere) {
        super(name, publishingDate, publishingCompany, takeawayPermission, quality, authorName, pageNumber, rage);
        this.sphere = sphere;
    }

    public NonFictionBook(){

    }

    public Sphere getSphere() {
        return sphere;
    }

    public void setSphere(Sphere sphere) {
        this.sphere = sphere;
    }

    @Override
    public String toString() {
        return "NonFictionBook{" +
                "sphere=" + sphere +
                ", authorName='" + authorName + '\'' +
                ", pageNumber=" + pageNumber +
                ", rage=" + rage +
                ", name='" + name + '\'' +
                ", publishingDate=" + publishingDate +
                ", publishingCompany='" + additionalInfo.publishingCompany +
                ", takeawayPermission=" + additionalInfo.takeawayPermission +
                ", quality=" + additionalInfo.quality +
                '}';
    }

    @Override
    public String writeData(){
        String result = "NonFictionBook;" + this.getSphere().toString() + ';'
                + this.getAuthorName() + ';'
                + this.getPageNumber() + ';'
                + this.getRage() + ';'
                + this.getName() + ';' + this.getPublishingDate().toString() + ';'
                + this.getAdditionalInfo().getPublishingCompany() + ';'
                + this.getAdditionalInfo().isTakeawayPermission() + ';'
                + this.getAdditionalInfo().getQuality().toString();
        return result;
    }
}
