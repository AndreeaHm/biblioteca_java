import java.util.Date;

public class Loan {
    private int id;
    private final Book book;
    private final Member member;
    private final Date issueDate;
    private final Date dueDate;

    public Loan(Book book, Member member, Date issueDate, Date dueDate) {
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public Loan(int id, Book book, Member member, Date issueDate, Date dueDate) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book +
                ", member=" + member +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                '}';
    }
}