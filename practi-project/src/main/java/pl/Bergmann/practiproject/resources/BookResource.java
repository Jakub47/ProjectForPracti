package pl.Bergmann.practiproject.resources;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.Bergmann.practiproject.controllers.BookController;
import pl.Bergmann.practiproject.models.Book;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
/**
 * Helper class for adding links to book
 */
public class BookResource extends RepresentationModel
{
    private final Book book;

    public BookResource(final Book book)
    {
        this.book = book;
        final int id = book.getId();
        add(linkTo(BookController.class).withRel("books"));
        add(linkTo(methodOn(BookController.class).get(id)).withSelfRel());
    }
}
