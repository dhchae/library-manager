import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookService service = new BookService(new FileBookRepository("data/books.csv"));
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("===== 도서 관리 =====");
            System.out.println("1. 목록  2. 대출  3. 반납  4. 검색  5. 도서 등록  0. 종료");
            System.out.print("선택: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> printBooks(service.findAll());
                    case "2" -> {
                        System.out.print("대출할 도서 번호: ");
                        service.borrow(readInt(sc));
                        System.out.println("대출되었습니다.");
                    }
                    case "3" -> {
                        System.out.print("반납할 도서 번호: ");
                        service.giveBack(readInt(sc));
                        System.out.println("반납되었습니다.");
                    }
                    case "4" -> {
                        System.out.print("검색어(제목 또는 저자): ");
                        printBooks(service.search(sc.nextLine().trim()));
                    }
                    case "5" -> {
                        System.out.print("제목: ");
                        String title = sc.nextLine().trim();
                        System.out.print("저자: ");
                        String author = sc.nextLine().trim();
                        System.out.println("등록되었습니다: " + service.add(title, author));
                    }
                    case "0" -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("메뉴를 다시 선택하세요.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("[오류] " + e.getMessage());
            }
        }
    }

    private static int readInt(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력하세요.");
        }
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("도서가 없습니다.");
            return;
        }
        books.forEach(System.out::println);
    }
}
