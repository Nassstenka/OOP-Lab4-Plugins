package sample.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("book")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FictionBook.class, name = "fiction"),
        @JsonSubTypes.Type(value = NonFictionBook.class, name = "nonfiction"),
})
public abstract class Book extends PrintedEdition implements Serializable {
    protected String authorName;
    protected int pageNumber;
    protected int rage;

    public Book(String name, String publishingDate, String publishingCompany, boolean takeawayPermission, AdditionalInfo.Quality quality, String authorName, int pageNumber, int rage) {
        super(name, publishingDate, publishingCompany, takeawayPermission, quality);
        this.authorName = authorName;
        this.pageNumber = pageNumber;
        this.rage = rage;
    }

    public Book(){

    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getRage() {
        return rage;
    }

    @Override
    public String toString() {
        return "Book{" +
                "authorName='" + authorName + '\'' +
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
        return "";
    }
}
