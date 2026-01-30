package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest() {

        // Alphanumeric
        assertTrue(BankAccount.isEmailValid("a@b.com"));

        // Prefix Allowed Special Characters
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com"));

        // Domain Allowed Special Characters
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));

        // Valid Domains
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.cc"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org"));

        // Missing Portions
        assertFalse(BankAccount.isEmailValid("")); // empty string (border case)
        assertFalse(BankAccount.isEmailValid("ab.com")); // missing @ symbol
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // missing domain suffix
        assertFalse(BankAccount.isEmailValid("@mail.com")); // missing local part
        assertFalse(BankAccount.isEmailValid("abc@")); // missing domain part

        // Invalid Special Characters
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); // invalid special character
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // consecutive special characters (border case)
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // special character in domain name
        assertFalse(BankAccount.isEmailValid("abc@def@mail.com")); // multiple @ symbols (border case)

        // Invalid Special Character Locations
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // consecutive special characters in domain name 
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // starts with special character (border case)
        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // terminates with special character before @ (border case)

        // Improper Length
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); // domain suffix only has one character (border case)
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
    }

}