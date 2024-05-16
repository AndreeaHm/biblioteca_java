public class Staff extends User {
    private final String position;

    public Staff(String name, int id, String username, String password, String position) {
        super(name, id, username, password);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void addBook(LibraryService libraryService, Book book) {
        libraryService.addBook(book);
    }

    public void removeBook(LibraryService libraryService, Book book) {
        libraryService.removeBook(book);
    }

    public void registerUser(LibraryService libraryService, User user) {
        libraryService.registerUser(user);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "position='" + position + '\'' +
                ", name='" + getName() + '\'' +
                ", id=" + getId() +
                '}';
    }
}