package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        // normal case
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001); // not boundary: positive balance

        // zero balance case
        BankAccount bankAccount2 = new BankAccount("b@c.com", 0);
        assertEquals(0, bankAccount2.getBalance(), 0.001); // boundary case: zero balance

    }

    @Test
    void withdrawTest() throws InsufficientFundsException {

        // normal withdraw
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001); // Not boundary, amount is less than balance
        bankAccount.withdraw(100); // Boundary, amount is equal to balance

        // invalid inputs
        BankAccount bankAccount2 = new BankAccount("b@c.com", 100);
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(-50)); // Not boundary: Negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(0)); // Boundary: Zero amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(10.001)); // Boundary: three digits
                                                                                           // after decimal

        // insufficient funds
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(100.01)); // Boundary, amount is just
                                                                                             // over balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(150)); // Not boundary, amount is
                                                                                          // well over balance
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
        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // terminates with special character before @ (border
                                                                // case)

        // Improper Length
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); // domain suffix only has one character (border case)

        // Task 01-03: Additional Test Cases
        // Blank String
        assertFalse(BankAccount.isEmailValid("")); // string is blank (border case)
        assertTrue(BankAccount.isEmailValid("j@l.or")); // minimum valid email (border case)
        assertTrue(BankAccount.isEmailValid("def@jgh.com")); // valid string length (middle case)

        // Prefix is blank
        assertFalse(BankAccount.isEmailValid(" @google.com")); // blank prefix (border)
        assertTrue(BankAccount.isEmailValid("b@google.com")); // minimum prefix length (border)
        assertTrue(BankAccount.isEmailValid("abc@hotmail.com")); // prefix length is three (middle case)

        // Domain is blank
        assertFalse(BankAccount.isEmailValid(" baneet@")); // blank domain (border)
        assertTrue(BankAccount.isEmailValid("b@g.co")); // minimum domain length (border)
        assertTrue(BankAccount.isEmailValid("abc@yahoo.com")); // domain length more than three (middle case)

        // Domain tag length
        assertFalse(BankAccount.isEmailValid("abc@def.g")); // domain tag length one (border)
        assertTrue(BankAccount.isEmailValid("abc@def.go")); // domain tag length two (border)
        assertTrue(BankAccount.isEmailValid("yrf@outlook.com")); // doman tag length three (middle case)

        // invalid special characters
        assertFalse(BankAccount.isEmailValid("abd)def@google.com")); // one invalid special character in prefix (border)
        assertTrue(BankAccount.isEmailValid("abddef@google.com")); // no invalid special character in prefix (border)
        assertFalse(BankAccount.isEmailValid("abd())def@google.com")); // two invalid special character in prefix
                                                                       // (middle)

        // special character locations
        assertFalse(BankAccount.isEmailValid("-baneet@google.com")); // prefix starts with special character (border)
        assertFalse(BankAccount.isEmailValid("baneet-@google.com")); // prefix ends with special character (border)
        assertTrue(BankAccount.isEmailValid("ba-neet@google.com")); // prefix has special character in the middle
                                                                    // (middle case)

        // consecutive special characters
        assertTrue(BankAccount.isEmailValid("ba_neet@ithaca.edu")); // prefix has no consecutive special characters
                                                                    // (border)
        assertFalse(BankAccount.isEmailValid("ban__eet@ithaca.edu")); // prefix has two consecutive special characters
                                                                      // (border)
        assertFalse(BankAccount.isEmailValid("ba...neet@ithaca.edu")); // prefix has three consecutive special
                                                                       // characters (middle)

        // number of @ symbols
        assertTrue(BankAccount.isEmailValid("bpukhrambam@ithaca.edu")); // valid email with single @ symbol (border)
        assertFalse(BankAccount.isEmailValid("bpukh@ram@bam@ithaca")); // invalid email with multiple @ symbols (middle)
        assertFalse(BankAccount.isEmailValid("bpukhrambamithaca.edu")); // invalid email with no @ symbol (border)
        assertFalse(BankAccount.isEmailValid("bpukh@rambam@ithaca.edu")); // invalid email with two @ symbols (border)
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail()); // correct email
        assertEquals(200, bankAccount.getBalance(), 0.001); // correct balance

        BankAccount bankAccount2 = new BankAccount("b@g.com", 20.34);

        assertEquals(20.34, bankAccount2.getBalance(), 0.001); // amount with two digits after decimal

        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100)); // no email
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("b@c.com", -100.02)); // negative amount
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("b@c.com", 100.001)); // amount with more
                                                                                                 // than two digits
                                                                                                 // after decimal

    }

    @Test
    void isAmountValidTest() {
        // numbers of digits after decimal
        assertFalse(BankAccount.isAmountValid(0.001)); // boundary case: lowest three digits after decimal
        assertFalse(BankAccount.isAmountValid(54.021)); // not boundary case: three digits after decimal
        assertTrue(BankAccount.isAmountValid(0.99)); // boundary case: highest two digits after decimal
        assertTrue(BankAccount.isAmountValid(2.1)); // not boundary: one digit after decimal
        assertTrue(BankAccount.isAmountValid(200.01)); // not boundary: two digits after decimal

        // negative amount
        assertTrue(BankAccount.isAmountValid(0.0)); // boundary: lowest positive amount
        assertTrue(BankAccount.isAmountValid(20.0)); // not boundary: positive amount
        assertFalse(BankAccount.isAmountValid(-0.01)); // boundary: highest negative amount
        assertFalse(BankAccount.isAmountValid(-100.00)); // not boundary: negative amount
    }

}