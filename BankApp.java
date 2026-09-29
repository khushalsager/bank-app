import java.util.*;

/** Simple console banking simulator demonstrating OOP and exceptions in Java. */
public class BankApp {

    static class InsufficientFundsException extends Exception {
        InsufficientFundsException(String msg) { super(msg); }
    }

    static class Account {
        private final String id;
        private final String owner;
        private double balance;
        private final List<String> history = new ArrayList<>();

        Account(String id, String owner, double initial) {
            this.id = id;
            this.owner = owner;
            this.balance = initial;
            history.add(String.format("Opened with $%.2f", initial));
        }

        void deposit(double amt) {
            if (amt <= 0) throw new IllegalArgumentException("Deposit must be positive");
            balance += amt;
            history.add(String.format("Deposit  +$%.2f", amt));
        }

        void withdraw(double amt) throws InsufficientFundsException {
            if (amt <= 0) throw new IllegalArgumentException("Withdrawal must be positive");
            if (amt > balance) throw new InsufficientFundsException("Insufficient funds");
            balance -= amt;
            history.add(String.format("Withdraw -$%.2f", amt));
        }

        String getId() { return id; }
        String getOwner() { return owner; }
        double getBalance() { return balance; }
        List<String> getHistory() { return history; }
    }

    private static final Map<String, Account> accounts = new HashMap<>();
    private static final Scanner in = new Scanner(System.in);
    private static int nextId = 1001;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Bank Menu ===");
            System.out.println("1) Open account  2) Deposit  3) Withdraw");
            System.out.println("4) Transfer      5) Statement  6) Exit");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> openAccount();
                    case "2" -> find("Account ID: ").deposit(readAmount());
                    case "3" -> find("Account ID: ").withdraw(readAmount());
                    case "4" -> transfer();
                    case "5" -> statement();
                    case "6" -> { System.out.println("Goodbye!"); return; }
                    default -> System.out.println("Unknown option.");
                }
            } catch (InsufficientFundsException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void openAccount() {
        System.out.print("Owner name: ");
        String name = in.nextLine().trim();
        Account a = new Account("AC" + nextId++, name, readAmount());
        accounts.put(a.getId(), a);
        System.out.println("Created account " + a.getId() + " for " + name);
    }

    private static void transfer() throws InsufficientFundsException {
        Account from = find("From ID: ");
        Account to = find("To ID: ");
        double amt = readAmount();
        from.withdraw(amt);
        to.deposit(amt);
        System.out.println("Transferred.");
    }

    private static void statement() {
        Account a = find("Account ID: ");
        System.out.printf("%nStatement for %s (%s)%n", a.getOwner(), a.getId());
        a.getHistory().forEach(h -> System.out.println("  " + h));
        System.out.printf("Balance: $%.2f%n", a.getBalance());
    }

    private static Account find(String prompt) {
        System.out.print(prompt);
        Account a = accounts.get(in.nextLine().trim().toUpperCase());
        if (a == null) throw new IllegalArgumentException("Account not found");
        return a;
    }

    private static double readAmount() {
        System.out.print("Amount: ");
        try {
            return Double.parseDouble(in.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
}
