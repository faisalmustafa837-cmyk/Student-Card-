package com.example.studentcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Screen 2: LoginActivity
 * User authentication screen with email and password fields.
 * Layout Demonstrated: LinearLayout (Vertical Form)
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupListeners();
    }

    /**
     * Initializes view references from the layout.
     */
    private void initViews() {
        tilEmail = findViewById(R.id.tilLoginEmail);
        tilPassword = findViewById(R.id.tilLoginPassword);
        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
    }

    /**
     * Sets click listeners on buttons and text links.
     */
    private void setupListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndLogin();
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Validates email & password input and verifies simulated credentials.
     */
    private void validateAndLogin() {
        tilEmail.setError(null);
        tilPassword.setError(null);

        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        boolean isValid = true;

        // Email validation
        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.error_empty_email));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        // Password validation
        if (password.isEmpty()) {
            tilPassword.setError(getString(R.string.error_empty_password));
            isValid = false;
        } else if (password.length() < 6) {
            tilPassword.setError(getString(R.string.error_password_length));
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Simulated credential verification
        boolean isAuthenticated = AuthManager.authenticate(this, email, password);
        if (isAuthenticated) {
            String userName = AuthManager.getUserName(this, email);
            Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra(AuthManager.EXTRA_USER_EMAIL, email);
            intent.putExtra(AuthManager.EXTRA_USER_NAME, userName);
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(findViewById(R.id.login_root),
                    getString(R.string.error_invalid_credentials),
                    Snackbar.LENGTH_LONG).show();
        }
    }
}

