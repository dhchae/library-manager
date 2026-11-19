public enum BookStatus {
    AVAILABLE("대출가능"),
    BORROWED("대출중");

    private final String label;

    BookStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
