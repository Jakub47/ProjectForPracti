package pl.Bergmann.practiproject;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import pl.Bergmann.practiproject.models.BookService;

@Profile("test")
@Configuration
public class BookServiceTestConfiguration
{
    @Bean
    @Primary
    public BookService nameService() {
        return Mockito.mock(BookService.class);
    }
}
