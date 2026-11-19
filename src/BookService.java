import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final BookRepository repository;
    private final List<Book> books;

    public BookService(BookRepository repository) {
        this.repository = repository;
        this.books = new ArrayList<>(repository.findAll());
        if (books.isEmpty()) {
            books.add(new Book(1, "자바의 정석", "남궁성", BookStatus.AVAILABLE, null));
            books.add(new Book(2, "클린 코드", "로버트 마틴", BookStatus.AVAILABLE, null));
            books.add(new Book(3, "이펙티브 자바", "조슈아 블로크", BookStatus.AVAILABLE, null));
            repository.saveAll(books);
        }
    }

    public List<Book> findAll() {
        return List.copyOf(books);
    }

    public List<Book> search(String keyword) {
        String k = keyword.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(k)
                        || b.getAuthor().toLowerCase().contains(k))
                .toList();
    }

    public void borrow(int id) {
        find(id).borrow();
        repository.saveAll(books);
    }

    public void giveBack(int id) {
        find(id).giveBack();
        repository.saveAll(books);
    }

    public Book add(String title, String author) {
        if (title.isBlank() || author.isBlank()) {
            throw new IllegalArgumentException("제목과 저자를 모두 입력하세요.");
        }
        if (title.contains(",") || author.contains(",")) {
            throw new IllegalArgumentException("쉼표(,)는 사용할 수 없습니다.");
        }
        int nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        Book book = new Book(nextId, title, author, BookStatus.AVAILABLE, null);
        books.add(book);
        repository.saveAll(books);
        return book;
    }

    private Book find(int id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("없는 도서 번호입니다: " + id));
    }
}
