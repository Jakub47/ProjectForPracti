package pl.Bergmann.practiproject.models;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
@Data
public class Book extends RepresentationModel<Book>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String author;

    @Column(name="isbn_number")
    @Size(min = 13,max = 13,message = "isbn must have 13 numbers")
    @Pattern(regexp="^([0-9]*)$",message = "isbn can only contain numbers")
    private String isbnNumber;

    @Column(name="pages_amount")
    private int pagesAmount;

    private float rating;

    public Book()
    {

    }

    public Book(String title, String author, String isbnNumber, int pagesAmount, float rating) {
        this.title = title;
        this.author = author;
        this.isbnNumber = isbnNumber;
        this.pagesAmount = pagesAmount;
        this.rating = rating;
    }

    public Book(int id,String title, String author, String isbnNumber, int pagesAmount, float rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbnNumber = isbnNumber;
        this.pagesAmount = pagesAmount;
        this.rating = rating;
    }


    public String getIsbnNumber() {
        String firstPrefix = isbnNumber.substring(0,3);
        String countryPrefix = isbnNumber.substring(3,5);
        String publisherPrefix = isbnNumber.substring(5,11);
        String publicationNumberPrefix = isbnNumber.substring(11,12);
        String controlPrefix = isbnNumber.substring(12,13);
        return firstPrefix + "-" + countryPrefix + "-" + publisherPrefix + "-" + publicationNumberPrefix + "-" + controlPrefix;
    }

    public void setIsbnNumber(String isbnNumber)
    {
        String isbnWithoutSpecialCharacters = isbnNumber.replaceAll("[^a-zA-Z0-9]+","");
        this.isbnNumber = isbnWithoutSpecialCharacters;
    }
}
