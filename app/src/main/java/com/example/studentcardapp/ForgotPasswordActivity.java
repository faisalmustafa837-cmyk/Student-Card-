package com.example.studentcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Screen 4: ForgotPasswordActivity
 * Simulated password recovery screen.
 * Layout Demonstrated: LinearLayout (Vertical Form)
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private MaterialButton btnSendResetLink;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupListeners();
    }

    /**
     * Initializes view references from the layout.
     */
    private void initViews() {
        tilEmail = findViewById(R.id.tilForgotEmail);
        etEmail = findViewById(R.id.etForgotEmail);
        btnSendResetLink = findViewById(R.id.btnSendResetLink);
        tvBackToLogin = findViewById(R.id.tvForgotBackToLogin);
    }

    /**
     * Sets click listeners for reset link button and back to login link.
     */
    private void setupListeners() {
        btnSendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSendResetLink();
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Validates email format and navigates to ResetPasswordActivity with simulated link.
     */
    private void validateAndSendResetLink() {
        tilEmail.setError(null);

        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";

        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.error_empty_email));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            return;
        }

        // Show simulated recovery Toast
        Toast.makeText(this, getString(R.string.reset_link_sent), Toast.LENGTH_SHORT).show();

        // Pass email to ResetPasswordActivity via explicit Intent
        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        intent.putExtra(AuthManager.EXTRA_USER_EMAIL, email);
        startActivity(intent);
        finish();
    }
}
