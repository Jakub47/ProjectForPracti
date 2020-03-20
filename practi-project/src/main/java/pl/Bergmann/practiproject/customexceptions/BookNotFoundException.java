package pl.Bergmann.practiproject.customexceptions;

/**
 * Class for throwing exceptions meant for book
 */
public class BookNotFoundException  extends RuntimeException
{
    public BookNotFoundException(int id) {
        super("Could not find book with id " + id);
    }
}
