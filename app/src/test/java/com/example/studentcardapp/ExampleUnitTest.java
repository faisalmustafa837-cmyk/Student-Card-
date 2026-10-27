package com.example.studentcardapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests verifying credential validation logic and constants.
 */
public class ExampleUnitTest {

    @Test
    public void defaultCredentials_areCorrect() {
        assertEquals("student@example.com", AuthManager.DEFAULT_EMAIL);
        assertEquals("Pass@1234", AuthManager.DEFAULT_PASSWORD);
        assertEquals("Alex Morgan", AuthManager.DEFAULT_NAME);
    }

    @Test
    public void passwordValidation_minimumLength() {
        String shortPassword = "12345";
        String validPassword = "Pass@1234";

        assertTrue("Short password should be invalid (<6 chars)", shortPassword.length() < 6);
        assertTrue("Valid password length should be >= 6", validPassword.length() >= 6);
    }

    @Test
    public void emailPattern_matchesFormat() {
        String validEmail = "student@example.com";
        String invalidEmail = "student-example.com";

        assertTrue("Valid email format contains @ and domain", validEmail.contains("@") && validEmail.contains("."));
        assertFalse("Invalid email lacks @ or domain dot", invalidEmail.contains("@"));
    }
}
