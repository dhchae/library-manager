import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBookRepository implements BookRepository {
    private final Path path;

    public FileBookRepository(String filePath) {
        this.path = Paths.get(filePath);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        if (!Files.exists(path)) {
            return books;
        }
        try {
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("id,")) {
                    continue;
                }
                books.add(Book.fromCsv(line));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("파일을 읽을 수 없습니다: " + path, e);
        }
        return books;
    }

    @Override
    public void saveAll(List<Book> books) {
        List<String> lines = new ArrayList<>();
        lines.add("id,title,author,status,borrowDate");
        for (Book b : books) {
            lines.add(b.toCsv());
        }
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("파일을 저장할 수 없습니다: " + path, e);
        }
    }
}
