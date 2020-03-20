package pl.Bergmann.practiproject.models;


import org.springframework.data.repository.PagingAndSortingRepository;


public interface BookRepository extends PagingAndSortingRepository<Book,Integer>
{
    boolean existsByIsbnNumber(String isbnNumber);
}
