package pl.Bergmann.practiproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.Bergmann.practiproject.models.Book;
import pl.Bergmann.practiproject.models.BookRepository;
import pl.Bergmann.practiproject.models.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class BookServiceTests
{
	BookService bookService;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void isBookTheSameFromRepo()
	{
		BookRepository localMockRepository = Mockito.mock(BookRepository.class);
		bookService = new BookService(localMockRepository);

		int id = 1;
		when(localMockRepository.findById(id)).thenReturn(Optional.of(prepareBooksMock().get(0)));
		Optional<Book> book = bookService.findById(id);
		Assert.assertEquals(book.get().getIsbnNumber(),prepareBooksMock().get(0).getIsbnNumber());
	}

	@Test
	void shouldSaveNew()
	{
		BookRepository localMockRepository = Mockito.mock(BookRepository.class);
		bookService = new BookService(localMockRepository);

		Book b = new Book(66,"title66",
				"author66","9788365215014",442,4.5f);

		when(localMockRepository.save(any(Book.class))).thenReturn(b);
		Book created = bookService.save(b);
		Assert.assertEquals(created.getTitle(),b.getTitle());

	}

	@Test
	void shouldReturnSameAmount()
	{
		final Page<Book> page = new PageImpl<>(prepareBooksMock());

		BookRepository localMockRepository = Mockito.mock(BookRepository.class);
		when(localMockRepository.findAll(any(Pageable.class))).thenReturn(page);
		bookService = new BookService(localMockRepository);



		List<Book> all = bookService.findAll(PageRequest.of(0, 50));
		Assert.assertEquals(all.size(),prepareBooksMock().size());

	}

	private List<Book> prepareBooksMock()
	{
		List<Book> books = new ArrayList<>();
		books.add(new Book(1,"title1","author1","9788365315014",442,4.5f));
		books.add(new Book(2,"title2","author2","9788365315013",221,3.0f));
		books.add(new Book(3,"title3","author3","9788365315012",222,1.5f));
		books.add(new Book(4,"title4","author4","9788365315011",333,2.0f));
		return books;
	}
}
