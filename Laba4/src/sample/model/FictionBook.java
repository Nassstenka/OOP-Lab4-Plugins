package sample.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("fiction")
public class FictionBook extends Book implements Serializable {
    public enum Genre{
        fairyTail,
        loveStory,
        novel,
        poetry;
    }
    private Genre genre;

    public FictionBook(String name, String publishingDate, String publishingCompany, boolean takeawayPermission, AdditionalInfo.Quality quality, String authorName, int pageNumber, int rage, Genre genre) {
        super(name, publishingDate, publishingCompany, takeawayPermission, quality, authorName, pageNumber, rage);
        this.genre = genre;
    }

    public FictionBook() {
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "FictionBook{" +
                "genre=" + genre +
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
        String result = "FictionBook;" + this.getGenre().toString() + ';'
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
