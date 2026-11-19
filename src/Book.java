import java.time.LocalDate;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private BookStatus status;
    private LocalDate borrowDate;

    public Book(int id, String title, String author, BookStatus status, LocalDate borrowDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.borrowDate = borrowDate;
    }

    public void borrow() {
        if (status == BookStatus.BORROWED) {
            throw new IllegalStateException("이미 대출중인 도서입니다.");
        }
        status = BookStatus.BORROWED;
        borrowDate = LocalDate.now();
    }

    public void giveBack() {
        if (status == BookStatus.AVAILABLE) {
            throw new IllegalStateException("대출중인 도서가 아닙니다.");
        }
        status = BookStatus.AVAILABLE;
        borrowDate = null;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookStatus getStatus() { return status; }
    public LocalDate getBorrowDate() { return borrowDate; }

    public String toCsv() {
        return id + "," + title + "," + author + "," + status + ","
                + (borrowDate == null ? "" : borrowDate);
    }

    public static Book fromCsv(String line) {
        String[] p = line.split(",", -1);
        if (p.length != 5) {
            throw new IllegalArgumentException("잘못된 데이터 형식입니다: " + line);
        }
        LocalDate date = p[4].isBlank() ? null : LocalDate.parse(p[4]);
        return new Book(Integer.parseInt(p[0]), p[1], p[2], BookStatus.valueOf(p[3]), date);
    }

    @Override
    public String toString() {
        String date = (borrowDate == null) ? "" : " (대출일 " + borrowDate + ")";
        return String.format("[%d] %s / %s / %s%s", id, title, author, status.getLabel(), date);
    }
}
