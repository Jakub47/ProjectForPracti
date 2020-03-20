package pl.Bergmann.practiproject.initalization;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.Bergmann.practiproject.models.Book;
import pl.Bergmann.practiproject.models.BookRepository;

@Component
@RequiredArgsConstructor
public class DataLoader  implements ApplicationRunner
{
    @NonNull
    private BookRepository bookRepository;

    /**
     * The method inflating the role of a seeder
     */
    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        if(!(bookRepository.existsByIsbnNumber("9788365315014")))
            bookRepository.save(new Book("tytul1","autor1","9788365315014",442,4.5f));
        if(!(bookRepository.existsByIsbnNumber("9782361315014")))
            bookRepository.save(new Book("tytul2","autor2","9782361315014",221,3.0f));
    }
}
