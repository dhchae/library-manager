# library-manager

콘솔 화면에서 도서를 **조회 · 검색 · 대출 · 반납 · 등록**하는 도서 관리 프로그램입니다.
Java 수업에서 배운 내용을 적용한 수행평가 과제이며, 외부 라이브러리와 네트워크 없이 PC에서 바로 실행됩니다.

## 주요 기능

- 도서 목록 조회 (상태: 대출가능 / 대출중, 대출일 표시)
- 제목·저자 검색
- 도서 대출 (대출일 자동 기록)
- 도서 반납 (대출일 삭제)
- 도서 등록
- 데이터 파일(CSV) 저장: 프로그램을 껐다 켜도 대출 상태가 유지됨
- 예외 처리: 이미 대출중인 책 대출, 대출중이 아닌 책 반납, 없는 도서 번호, 숫자가 아닌 입력

## 실행 방법

### 준비물
- JDK 17 이상

### 컴파일과 실행 (Windows PowerShell)
```powershell
mkdir out
javac -encoding UTF-8 -d out src\*.java
java -cp out Main
```

프로젝트 폴더(`library-manager`)에서 실행합니다. 처음 실행하면 `data/books.csv`가 자동으로 만들어집니다.

## 사용 방법

```
===== 도서 관리 =====
1. 목록  2. 대출  3. 반납  4. 검색  5. 도서 등록  0. 종료
선택:
```

| 메뉴 | 동작 |
|---|---|
| 1 | 전체 도서 목록을 출력합니다. |
| 2 | 도서 번호를 입력하면 대출 처리하고 오늘 날짜를 기록합니다. |
| 3 | 도서 번호를 입력하면 반납 처리합니다. |
| 4 | 제목 또는 저자의 일부를 입력하면 검색합니다. |
| 5 | 제목과 저자를 입력해 새 도서를 등록합니다. (쉼표 `,` 는 사용할 수 없음) |
| 0 | 프로그램을 종료합니다. |

## 사용 기술

- Java 17 (표준 라이브러리만 사용, 외부 라이브러리 없음)
- 파일 저장: CSV 텍스트 파일 (UTF-8)
- 형상 관리: Git / GitHub

## 폴더 구조

```
library-manager/
 ├─ src/        소스 코드 (.java)
 ├─ data/       도서 데이터 (books.csv)
 ├─ docs/       발표 자료
 ├─ .gitignore
 └─ README.md
```

## 클래스 구성

| 클래스 | 역할 |
|---|---|
| `Main` | 메뉴 출력과 입력 처리 (화면 담당) |
| `BookService` | 대출·반납·검색·등록 로직 (업무 규칙 담당) |
| `Book` | 도서 정보와 대출/반납 상태 변경 규칙 |
| `BookStatus` | 도서 상태 (대출가능, 대출중) — enum |
| `BookRepository` | 저장 방식을 정의한 인터페이스 |
| `FileBookRepository` | CSV 파일로 읽고 쓰는 구현 클래스 |

### 구조

```
Main  →  BookService  →  BookRepository (interface)
                              ▲
                       FileBookRepository  →  data/books.csv
```

화면(`Main`), 업무 로직(`BookService`), 저장(`Repository`)을 나누어 각 클래스가 한 가지 역할만 하도록 설계했습니다.

## 수업 내용 적용

| 수업 내용 | 적용한 부분 |
|---|---|
| 클래스와 캡슐화 | `Book`의 필드를 `private`으로 두고 `borrow()`, `giveBack()` 메서드로만 상태를 변경 |
| enum | `BookStatus`로 도서 상태를 구분하고 화면용 이름(label)을 함께 보관 |
| 인터페이스와 다형성 | `BookRepository` 인터페이스와 `FileBookRepository` 구현으로 저장 방식을 분리 |
| 컬렉션과 스트림 | `List<Book>`으로 도서를 관리하고, 검색·번호 조회에 `stream`의 `filter`를 사용 |
| 예외 처리 | `IllegalStateException`, `IllegalArgumentException`을 발생시키고 `Main`에서 `try-catch`로 안내 메시지 출력 |
| 파일 입출력 | `Files.readAllLines`, `Files.write`로 CSV 읽기/쓰기 (UTF-8) |
| 날짜 API | `LocalDate`로 대출일을 기록하고 CSV에 저장 |

## 데이터 형식 (data/books.csv)

```
id,title,author,status,borrowDate
1,자바의 정석,남궁성,AVAILABLE,
2,클린 코드,로버트 마틴,BORROWED,2026-11-19
```

## 개선 예정

- 대출 기한(14일)과 연체일수 표시
- 도서 삭제 기능
- 대출 이력 기록
