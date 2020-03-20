package pl.Bergmann.practiproject.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.Bergmann.practiproject.customexceptions.BookNotFoundException;
import pl.Bergmann.practiproject.models.Book;
import pl.Bergmann.practiproject.models.BookRepository;
import org.springframework.hateoas.*;
import pl.Bergmann.practiproject.models.BookService;
import pl.Bergmann.practiproject.resources.BookResource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books", produces = "application/hal+json")
@RequiredArgsConstructor
public class BookController
{
    @NonNull
    private final BookService productService;

    @Value( "${pageSize}" )
    private int pageSize;

    /**
     * Return books in given page
     * @param page page
     * @return
     */
    @GetMapping
    public ResponseEntity<CollectionModel<BookResource>> all(@RequestParam(value = "page", defaultValue = "0") int page)
    {
        Pageable firstPageWithTwoElements = PageRequest.of(page, pageSize);

        final List<BookResource> collection =
                productService.findAll(firstPageWithTwoElements).stream().map(BookResource::new).collect(Collectors.toList());
        final CollectionModel<BookResource> resources = new CollectionModel<>(collection);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResource> get(@PathVariable final int id) {
        return productService
                .findById(id)
                .map(p -> ResponseEntity.ok(new BookResource(p)))
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<BookResource> post(@RequestBody final Book bookFromRequest) {
        productService.save(bookFromRequest);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(bookFromRequest.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(new BookResource(bookFromRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResource> put(
            @PathVariable("id") final int id, @RequestBody Book bookToEdit) {

        final Book bookToReturn = productService.edit(bookToEdit,id);
        final BookResource resource = new BookResource(bookToReturn);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final int id)
    {
        if(productService.deleteById(id).isPresent()) {
            return ResponseEntity.ok().build();
        }
        else
        {
           return ResponseEntity.badRequest().build();
        }
    }

        /*
    @GetMapping
    List<Book> getAllBooks()
    {
        return bookRepo.findAll();
    }

     @GetMapping("/books/{id}")
    public ResponseEntity<BookResource> get(@PathVariable int id) {
        return bookRepo
                .findById(id)
                .map(p -> ResponseEntity.ok(new BookResource(p)))
                .orElseThrow(() -> new BookNotFoundException(id));
    }

        @PostMapping("/books")
    Book addBook(@RequestBody Book book)
    {
       return  bookRepo.save(book);
    }

     @PutMapping("/books/{id}")
    Book replaceBook(@RequestBody Book bookToEdit, @PathVariable Integer id) {

        return bookRepo.findById(id)
                .map(book -> {
                    book.setTitle(bookToEdit.getTitle());
                    book.setAuthor(bookToEdit.getAuthor());
                    book.setIsbnNumber(bookToEdit.getIsbnNumber());
                    book.setPagesAmount(bookToEdit.getPagesAmount());
                    book.setRating(bookToEdit.getRating());
                    return bookRepo.save(book);
                })
                .orElseGet(() -> {
                    bookToEdit.setId(id);
                    return bookRepo.save(bookToEdit);
                });
    }


    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Integer id)
    {
        if(bookRepo.existsById(id))
            bookRepo.deleteById(id);
        else
            throw new BookNotFoundException(id);
    }

    */

}
