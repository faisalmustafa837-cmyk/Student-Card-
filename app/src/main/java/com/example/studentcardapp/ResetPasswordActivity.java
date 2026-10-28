package com.example.studentcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Screen 5: ResetPasswordActivity
 * Allows setting a new password following recovery.
 * Layout Demonstrated: LinearLayout (Vertical Form)
 */
public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilNewPassword;
    private TextInputLayout tilConfirmPassword;
    private TextInputEditText etNewPassword;
    private TextInputEditText etConfirmPassword;
    private MaterialButton btnResetPassword;
    private TextView tvBackToLogin;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Retrieve passed email extra
        userEmail = getIntent().getStringExtra(AuthManager.EXTRA_USER_EMAIL);
        if (userEmail == null) {
            userEmail = AuthManager.DEFAULT_EMAIL;
        }

        initViews();
        setupListeners();
    }

    /**
     * Initializes view references from the layout.
     */
    private void initViews() {
        tilNewPassword = findViewById(R.id.tilResetNewPassword);
        tilConfirmPassword = findViewById(R.id.tilResetConfirmPassword);
        etNewPassword = findViewById(R.id.etResetNewPassword);
        etConfirmPassword = findViewById(R.id.etResetConfirmPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        tvBackToLogin = findViewById(R.id.tvResetBackToLogin);
    }

    /**
     * Sets click listeners for reset button and navigation links.
     */
    private void setupListeners() {
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndResetPassword();
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Validates new passwords and updates simulated account credentials.
     */
    private void validateAndResetPassword() {
        tilNewPassword.setError(null);
        tilConfirmPassword.setError(null);

        String newPassword = etNewPassword.getText() != null ? etNewPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        boolean isValid = true;

        if (newPassword.isEmpty()) {
            tilNewPassword.setError(getString(R.string.error_empty_password));
            isValid = false;
        } else if (newPassword.length() < 6) {
            tilNewPassword.setError(getString(R.string.error_password_length));
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            tilConfirmPassword.setError(getString(R.string.error_empty_password));
            isValid = false;
        } else if (!newPassword.equals(confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.error_password_mismatch));
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Update password locally
        AuthManager.resetPassword(this, userEmail, newPassword);
        Toast.makeText(this, getString(R.string.password_reset_successful), Toast.LENGTH_LONG).show();

        // Navigate back to LoginActivity
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

