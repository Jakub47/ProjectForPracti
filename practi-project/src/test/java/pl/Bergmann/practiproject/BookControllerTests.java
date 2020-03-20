package pl.Bergmann.practiproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.Bergmann.practiproject.controllers.BookController;
import pl.Bergmann.practiproject.models.Book;
import pl.Bergmann.practiproject.models.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTests
{
	@MockBean
	private BookService bookService;

	@Autowired
	private MockMvc mvc;

	ObjectMapper mapper = new ObjectMapper();
	Book book;

	@Test
	void getBook() throws Exception
	{
		prepareBook();
		when(bookService.findById(1)).thenReturn(Optional.of(book));
		ResultActions result = mvc.perform(get("/books/1"));
		result.andExpect(status().isOk());
		verifyJson(result);
	}

	private void verifyJson(final ResultActions action) throws Exception {
		action
				.andExpect(jsonPath("book.id", is(book.getId())))
				.andExpect(jsonPath("book.title", is(book.getTitle())))
				.andExpect(jsonPath("book.author", is(book.getAuthor())))
				.andExpect(jsonPath("book.isbnNumber", is(book.getIsbnNumber())))
				.andExpect(jsonPath("book.pagesAmount", is(book.getPagesAmount())))
				.andExpect(jsonPath("book.rating", is((double)book.getRating())))
				.andExpect(jsonPath("_links.books.href", is("http://localhost/books")))
				.andExpect(jsonPath("_links.self.href", is("http://localhost/books/0")))
		;
	}


	private void prepareBook()
	{
		book = new Book("title1","author1","9788365315014",442,4.5f);
	}

	private List<Book> prepareBooksMock()
	{
		List<Book> books = new ArrayList<>();
		books.add(new Book("title1","author1","9788365315014",442,4.5f));
		return books;
	}
}
