package com.example.studentcardapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * AuthManager manages simulated local authentication using hardcoded credentials
 * and SharedPreferences for persistent registered user accounts.
 */
public class AuthManager {

    // Default Hardcoded Credentials
    public static final String DEFAULT_EMAIL = "student@example.com";
    public static final String DEFAULT_PASSWORD = "Pass@1234";
    public static final String DEFAULT_NAME = "Alex Morgan";
    public static final String DEFAULT_STUDENT_ID = "STU-2026-8942";
    public static final String DEFAULT_DEPARTMENT = "Computer Science & IT";

    // Intent Extra Keys
    public static final String EXTRA_USER_NAME = "extra_user_name";
    public static final String EXTRA_USER_EMAIL = "extra_user_email";

    private static final String PREF_NAME = "student_app_auth_prefs";
    private static final String KEY_PASS_PREFIX = "pass_";
    private static final String KEY_NAME_PREFIX = "name_";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Authenticates user against hardcoded credentials or registered accounts.
     */
    public static boolean authenticate(Context context, String email, String password) {
        if (email == null || password == null) {
            return false;
        }

        String normalizedEmail = email.trim().toLowerCase();

        // 1. Check against default hardcoded credentials
        String storedDefaultPass = getPrefs(context).getString(KEY_PASS_PREFIX + DEFAULT_EMAIL, DEFAULT_PASSWORD);
        if (normalizedEmail.equalsIgnoreCase(DEFAULT_EMAIL) && password.equals(storedDefaultPass)) {
            return true;
        }

        // 2. Check against registered users in SharedPreferences
        String registeredPassword = getPrefs(context).getString(KEY_PASS_PREFIX + normalizedEmail, null);
        return registeredPassword != null && registeredPassword.equals(password);
    }

    /**
     * Registers a new user account into SharedPreferences.
     */
    public static boolean registerUser(Context context, String name, String email, String password) {
        if (email == null || password == null || name == null) {
            return false;
        }

        String normalizedEmail = email.trim().toLowerCase();
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(KEY_NAME_PREFIX + normalizedEmail, name.trim());
        editor.putString(KEY_PASS_PREFIX + normalizedEmail, password);
        return editor.commit();
    }

    /**
     * Updates/resets a user's password.
     */
    public static boolean resetPassword(Context context, String email, String newPassword) {
        if (email == null || newPassword == null) {
            return false;
        }

        String normalizedEmail = email.trim().toLowerCase();
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(KEY_PASS_PREFIX + normalizedEmail, newPassword);
        return editor.commit();
    }

    /**
     * Resolves the display name for a given email address.
     */
    public static String getUserName(Context context, String email) {
        if (email == null) {
            return DEFAULT_NAME;
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (normalizedEmail.equalsIgnoreCase(DEFAULT_EMAIL)) {
            return DEFAULT_NAME;
        }

        String registeredName = getPrefs(context).getString(KEY_NAME_PREFIX + normalizedEmail, null);
        if (registeredName != null && !registeredName.isEmpty()) {
            return registeredName;
        }

        // Fallback: extract username prefix from email
        int atIndex = normalizedEmail.indexOf('@');
        if (atIndex > 0) {
            String prefix = normalizedEmail.substring(0, atIndex);
            return Character.toUpperCase(prefix.charAt(0)) + prefix.substring(1);
        }

        return DEFAULT_NAME;
    }
}

