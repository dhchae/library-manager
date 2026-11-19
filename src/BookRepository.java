import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    void saveAll(List<Book> books);
}
