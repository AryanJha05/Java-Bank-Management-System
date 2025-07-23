import java.util.*;

class BankAccount {
    private String name;
    private String accountNumber;
    private String pin;
    private double balance;

    public BankAccount(String name, String pin) {
        this.name = name;
        this.pin = pin;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
    }

    private String generateAccountNumber() {
        int randomNum = new Random().nextInt(9000) + 1000;
        return name.replaceAll("\\s+", "").toUpperCase() + randomNum;
    }

    public boolean validatePIN(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void credit(double amount) {
        balance += amount;
        System.out.println("\nAmount credited successfully.");
    }

    public boolean debit(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("\nAmount debited successfully.");
            return true;
        } else {
            System.out.println("\nInsufficient balance.");
            return false;
        }
    }

    public void displayDetails() {
        System.out.println("\n===== Account Details =====");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: Rs. " + balance);
    }

    public void changePIN(Scanner sc) {
        System.out.print("\nEnter current PIN: ");
        String oldPin = sc.nextLine();
        if (validatePIN(oldPin)) {
            System.out.print("Enter new 4-digit PIN: ");
            String newPin = sc.nextLine();
            if (newPin.matches("\\d{4}")) {
                this.pin = newPin;
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("Invalid PIN format. Must be 4 digits.");
            }
        } else {
            System.out.println("Incorrect current PIN !!!");
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

public class Bank {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<BankAccount> accounts = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("\n===== Welcome to Aryan Jha Bank =====");
        while (true) {
            System.out.println("\n1. Create New Account");
            System.out.println("2. Login to Existing Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    createAccount();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("\nThank you for using our bank. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid option. Try again.");
            }
        }
    }

    static void createAccount() {
        System.out.print("\nEnter your name: ");
        String name = sc.nextLine();

        String pin;
        while (true) {
            System.out.print("Set a 4-digit PIN: ");
            pin = sc.nextLine();
            if (pin.matches("\\d{4}")) break;
            else System.out.println("Invalid PIN. It must be exactly 4 digits !!!");
        }

        BankAccount acc = new BankAccount(name, pin);
        accounts.add(acc);

        System.out.println("\nAccount created successfully!");
        System.out.println("Your account number is: " + acc.getAccountNumber());
        pause();
    }

    static void login() {
        System.out.print("\nEnter your account number: ");
        String accNum = sc.nextLine();

        BankAccount found = null;
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accNum)) {
                found = acc;
                break;
            }
        }

        if (found == null) {
            System.out.println("Account not found !!!");
            pause();
            return;
        }

        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter your 4-digit PIN: ");
            String pin = sc.nextLine();
            if (found.validatePIN(pin)) {
                accountMenu(found);
                return;
            } else {
                attempts--;
                System.out.println("Incorrect PIN. Attempts left: " + attempts);
            }
        }

        System.out.println("Too many failed attempts. Access blocked temporarily !!!");
        pause();
    }

    static void accountMenu(BankAccount acc) {
        while (true) {
            System.out.println("\n===== Account Menu =====");
            System.out.println("1. View Account Details");
            System.out.println("2. Credit Balance");
            System.out.println("3. Debit Balance");
            System.out.println("4. Change PIN");
            System.out.println("5. Logout");
            System.out.print("==> Choose an option: ");

            String opt = sc.nextLine();
            switch (opt) {
                case "1":
                    acc.displayDetails();
                    pause();
                    break;
                case "2":
                    System.out.print("\nEnter amount to credit: ");
                    double creditAmt = Double.parseDouble(sc.nextLine());
                    acc.credit(creditAmt);
                    pause();
                    break;
                case "3": {
                    System.out.print("\nEnter amount to debit: ");
                    double amt = Double.parseDouble(sc.nextLine());
                    boolean success = acc.debit(amt); // use the return value
                    if (success) {
                        System.out.println("Transaction successful.");
                    } else {
                        System.out.println("Transaction failed.");
                    }
                    pause();
                    break;
                }

                case "4":
                    acc.changePIN(sc);
                    pause();
                    break;
                case "5":
                    System.out.println("\nLogging out...");
                    return;
                default:
                    System.out.println("Invalid option !!!");
            }
        }
    }

    static void pause() {
        System.out.print("\n ==> Press Enter to return to the previous menu...");
        sc.nextLine();
    }
}
