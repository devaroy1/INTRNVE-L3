import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

// Class representing a Bank Account
class BankAccount {
    private static final AtomicInteger accountNumberGenerator = new AtomicInteger(1000);
    private final int accountNumber;
    private final String accountHolderName;
    private double balance;

    public BankAccount(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumberGenerator.getAndIncrement();
        this.balance = 0.0;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        System.out.println("Deposited: " + amount + ", New Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds. Current balance: " + balance);
        }
        balance -= amount;
        System.out.println("Withdrew: " + amount + ", New Balance: " + balance);
    }
}

// Class representing the Bank
class Bank {
    private final Map<Integer, BankAccount> accounts = new HashMap<>();

    public BankAccount createAccount(String accountHolderName) {
        BankAccount newAccount = new BankAccount(accountHolderName);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        System.out.println("Account created for " + accountHolderName + " with Account Number: " + newAccount.getAccountNumber());
        return newAccount;
    }

    public BankAccount getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public void displayAccounts() {
        System.out.println("All Accounts:");
        for (BankAccount account : accounts.values()) {
            System.out.println("Account Number: " + account.getAccountNumber() +
                               ", Account Holder: " + account.getAccountHolderName() +
                               ", Balance: " + account.getBalance());
        }
    }
}

// Main class to run the application
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("1. Create Account\n2. Deposit\n3. Withdraw\n4. Check Balance\n5. Display All Accounts\n6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter account holder's name: ");
                    String name = scanner.nextLine();
                    bank.createAccount(name);
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    int depositAccountNumber = scanner.nextInt();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    try {
                        BankAccount depositAccount = bank.getAccount(depositAccountNumber);
                        if (depositAccount != null) {
                            depositAccount.deposit(depositAmount);
                        } else {
                            System.out.println("Account not found.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    int withdrawAccountNumber = scanner.nextInt();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    try {
                        BankAccount withdrawAccount = bank.getAccount(withdrawAccountNumber);
                        if (withdrawAccount != null) {
                            withdrawAccount.withdraw(withdrawAmount);
                        } else {
                            System.out.println("Account not found.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter account number: ");
                    int balanceAccountNumber = scanner.nextInt();
                    BankAccount balanceAccount = bank.getAccount(balanceAccountNumber);
                    if (balanceAccount != null) {
                        System.out.println("Balance: " + balanceAccount.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 5:
                    bank.displayAccounts();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}