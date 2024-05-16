import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryGUI {
    private final LibraryService libraryService;
    private final Map<String, User> users;
    private int userIdGenerator;

    public LibraryGUI() {
        this.libraryService = new LibraryService();
        this.users = new HashMap<>();
        this.userIdGenerator = 1;
    }

    public void start() {
        User currentUser = login();
        if (currentUser == null) {
            System.out.println("Invalid login. Exiting...");
            return;
        }

        if (currentUser instanceof Staff) {
            showStaffMenu((Staff) currentUser);
        } else if (currentUser instanceof Member) {
            showMemberMenu((Member) currentUser);
        }
    }

    private User login() {
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username == null) return null;

        String password = JOptionPane.showInputDialog("Enter password:");
        if (password == null) return null;

        User user = users.get(username);
        if (user == null) {
            List<User> allUsers = libraryService.getAllUsers();
            for (User u : allUsers) {
                users.put(u.getUsername(), u);
            }
            user = users.get(username);
        }

        if (user != null && user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + user.getName() + "!");
            return user;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
            return null;
        }
    }

    private void showStaffMenu(Staff staff) {
        JFrame frame = new JFrame("Library Management System - Staff");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton addBookButton = new JButton("Add Book");
        JButton removeBookButton = new JButton("Remove Book");
        JButton registerUserButton = new JButton("Register User");
        JButton getAvailableBooksButton = new JButton("Get Available Books");
        JButton getUserByIdButton = new JButton("Get User by ID");

        panel.add(addBookButton);
        panel.add(removeBookButton);
        panel.add(registerUserButton);
        panel.add(getAvailableBooksButton);
        panel.add(getUserByIdButton);

        frame.add(panel, BorderLayout.CENTER);

        addBookButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog("Enter book title:");
            if (title == null || title.trim().isEmpty()) return;

            String author = JOptionPane.showInputDialog("Enter book author:");
            if (author == null || author.trim().isEmpty()) return;

            String isbn = JOptionPane.showInputDialog("Enter book ISBN:");
            if (isbn == null || isbn.trim().isEmpty()) return;

            Book book = new Book(title, author, isbn);
            staff.addBook(libraryService, book);
            JOptionPane.showMessageDialog(null, "Book added successfully.");
        });

        removeBookButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter book ISBN to remove:");
            if (isbn == null || isbn.trim().isEmpty()) return;

            Book book = new Book("", "", isbn);
            staff.removeBook(libraryService, book);
            JOptionPane.showMessageDialog(null, "Book removed successfully.");
        });

        registerUserButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter name:");
            if (name == null || name.trim().isEmpty()) return;

            String username = JOptionPane.showInputDialog("Enter username:");
            if (username == null || username.trim().isEmpty()) return;

            String password = JOptionPane.showInputDialog("Enter password:");
            if (password == null || password.trim().isEmpty()) return;

            String membershipType = JOptionPane.showInputDialog("Enter membership type (e.g., Gold, Silver):");
            if (membershipType == null || membershipType.trim().isEmpty()) return;

            Member member = new Member(name, userIdGenerator++, username, password, membershipType);
            staff.registerUser(libraryService, member);
            users.put(member.getUsername(), member);
            JOptionPane.showMessageDialog(null, "User registered successfully.");
        });

        getAvailableBooksButton.addActionListener(e -> {
            List<Book> availableBooks = libraryService.getAvailableBooks();
            JOptionPane.showMessageDialog(null, "Available books: " + availableBooks);
        });

        getUserByIdButton.addActionListener(e -> {
            String userIdInput = JOptionPane.showInputDialog("Enter user ID:");
            if (userIdInput == null || userIdInput.trim().isEmpty()) return;

            int userId = Integer.parseInt(userIdInput);
            User user = libraryService.getUserById(userId);
            if (user != null) {
                JOptionPane.showMessageDialog(null, "User details: " + user);
            } else {
                JOptionPane.showMessageDialog(null, "User not found.");
            }
        });

        frame.setVisible(true);
    }

    private void showMemberMenu(Member member) {
        JFrame frame = new JFrame("Library Management System - Member");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton getLoansButton = new JButton("Get Loans by Member");
        JButton getAvailableBooksButton = new JButton("Get Available Books");

        panel.add(borrowBookButton);
        panel.add(returnBookButton);
        panel.add(getLoansButton);
        panel.add(getAvailableBooksButton);

        frame.add(panel, BorderLayout.CENTER);

        borrowBookButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter book ISBN to borrow:");
            if (isbn == null || isbn.trim().isEmpty()) return;

            Book book = new Book("", "", isbn);
            member.borrowBook(libraryService, book);
            JOptionPane.showMessageDialog(null, "Book borrowed successfully.");
        });

        returnBookButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter book ISBN to return:");
            if (isbn == null || isbn.trim().isEmpty()) return;

            Book book = new Book("", "", isbn);
            member.returnBook(libraryService, book);
            JOptionPane.showMessageDialog(null, "Book returned successfully.");
        });

        getLoansButton.addActionListener(e -> {
            List<Loan> loans = libraryService.getLoansByMember(member);
            JOptionPane.showMessageDialog(null, "Loans by " + member.getName() + ": " + loans);
        });

        getAvailableBooksButton.addActionListener(e -> {
            List<Book> availableBooks = libraryService.getAvailableBooks();
            JOptionPane.showMessageDialog(null, "Available books: " + availableBooks);
        });

        frame.setVisible(true);
    }
}