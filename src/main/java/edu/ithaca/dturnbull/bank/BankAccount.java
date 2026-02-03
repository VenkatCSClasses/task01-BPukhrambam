package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email) && isAmountValid(startingBalance)) {
            this.email = email;
            this.balance = startingBalance;
        } else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller
     *       than balance
     *       throws an IllegalArgumentException if amount is negative or 0
     *       throws an InsufficientFundsException if amount is greater than balance
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (isAmountValid(amount)) {
            if (amount == 0) {
                throw new IllegalArgumentException("Must enter non-negative withdrawal amount");
            }
            if (amount <= balance) {
                balance -= amount;
            } else {
                throw new InsufficientFundsException("Not enough money");
            }
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
    }

    public static boolean isEmailValid(String email) {
        // Checks for empty/blank email
        if (email.isBlank()) {
            return false;
        }

        // Find @ symbol, right most period
        int atIndex = email.indexOf('@');
        int lastPeriodIndex = email.lastIndexOf('.');
        if (atIndex == -1 || lastPeriodIndex == -1 || lastPeriodIndex <= atIndex) {
            return false;
        }

        // Check if prefix is missing
        if (atIndex == 0) {
            return false;
        }

        // Deal with prefix
        for (int i = 0; i < atIndex; i++) {
            char c = email.charAt(i);
            // Check alphaNum or special char
            boolean charAlphaNum = Character.isLetterOrDigit(c);
            if (!charAlphaNum) {
                if (!isPrefixSpecial(c)) {
                    // Neither alphaNum or valid special
                    return false;
                } else {
                    // Checking multiple special chars in a row, or first char
                    if (!Character.isLetterOrDigit(email.charAt(i + 1)) || i == 0) {
                        return false;
                    }
                    // Incrementing an additional time as no need to recheck if its valid character
                    i++;
                }
            }
        }

        // Deal with domain
        if (lastPeriodIndex + 2 >= email.length()) {
            // Last portion of domain too small
            return false;
        }
        for (int i = atIndex + 1; i < email.length(); i++) {
            char c = email.charAt(i);
            // Check alphaNum, dash
            if (!(Character.isLetterOrDigit(c)) && !(c == '-')) {
                // See if its the one allowed period
                if (!(c == '.' && i == lastPeriodIndex)) {
                    return false;
                }
            }
        }

        return true;

    }

    // Returns true if it is a special character valid in the prefix
    public static boolean isPrefixSpecial(char c) {
        // Everything in the wiki link under special characters, minus # as it was
        // included in a test as invalid...
        char[] prefixSpecial = { '.', '!', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^', '_', '`', '{', '|',
                '}', '~' };
        for (char special : prefixSpecial) {
            if (special == c) {
                return true;
            }
        }
        return false;
    }

    // Takes a double and returns true if the amount is positive and has two decimal
    // points or less, and false otherwise.
    public static boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        }
        String amountString = Double.toString(amount);
        if (amountString.contains(".")) {
            String[] parts = amountString.split("\\.");
            if (parts[1].length() > 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * @post increases the balance by amount if amount is valid
     *       throws an IllegalArgumentException if amount is valid
     */
    public void deposit(double amount) {
        if (isAmountValid(amount)) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Invalid deposit amount");
        }
    }

    /**
     * @post transfers amount from this account to bankAccount if amount is valid
     *       throws an IllegalArgumentException if amount is invalid
     *       throws an InsufficientFundsException if amount is greater than balance
     */
    public void transfer(BankAccount bankAccount, double amount) {
        throw new IllegalArgumentException("Not yet implemented");
    }

}