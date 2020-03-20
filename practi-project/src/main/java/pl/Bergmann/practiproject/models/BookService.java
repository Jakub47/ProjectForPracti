package pl.Bergmann.practiproject.models;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.Bergmann.practiproject.customexceptions.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService
{
    private final BookRepository bookRespository;

    @Autowired
    public BookService(BookRepository bookRespository) {
        this.bookRespository = bookRespository;
    }




    public List<Book> findAll(Pageable pageable) {
        return bookRespository.findAll(pageable).toList();
    }

    public Optional<Book> findById(int id)
    {
        Optional<Book> book = bookRespository.findById(id);
        if(!book.isPresent())
        {
            log.error("Id " + id + " is not existed");
        }
        return book;
    }

    public Book save(Book stock) {
        return bookRespository.save(stock);
    }

    public Book edit(Book bookToEdit,int id)
    {
        return  bookRespository.findById(id)
                .map(book -> {
                    book.setTitle(bookToEdit.getTitle());
                    book.setAuthor(bookToEdit.getAuthor());
                    book.setIsbnNumber(bookToEdit.getIsbnNumber());
                    book.setPagesAmount(bookToEdit.getPagesAmount());
                    book.setRating(bookToEdit.getRating());
                    return bookRespository.save(book);
                })
                .orElseGet(() -> {
                    bookToEdit.setId(id);
                    return bookRespository.save(bookToEdit);
                });
    }

    public Optional<Book> deleteById(int id)
    {
        Optional<Book> book = findById(id);
        if(book.isPresent())
            bookRespository.deleteById(id);
        return book;
    }
}
