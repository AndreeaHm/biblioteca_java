public class Member extends User {
    private final String membershipType;

    public Member(String name, int id, String username, String password, String membershipType) {
        super(name, id, username, password);
        this.membershipType = membershipType;
    }

    public Member(String name, String username, String password, String membershipType) {
        super(name, username, password);
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void borrowBook(LibraryService libraryService, Book book) {
        libraryService.borrowBook(this, book);
    }

    public void returnBook(LibraryService libraryService, Book book) {
        libraryService.returnBook(this, book);
    }

    @Override
    public String toString() {
        return "Member{" +
                "membershipType='" + membershipType + '\'' +
                ", name='" + getName() + '\'' +
                ", id=" + getId() +
                '}';
    }
}