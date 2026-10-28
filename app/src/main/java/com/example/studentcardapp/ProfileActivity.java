package com.example.studentcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

/**
 * Screen 7: ProfileActivity
 * Displays student personal details, quick link to ID card, and handles logout.
 * Layout Demonstrated: ConstraintLayout
 */
public class ProfileActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private TextView tvStudentID;
    private TextView tvDepartment;
    private TextView tvSession;
    private MaterialButton btnViewStudentCard;
    private MaterialButton btnLogout;

    private String userName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Retrieve extras
        userEmail = getIntent().getStringExtra(AuthManager.EXTRA_USER_EMAIL);
        if (userEmail == null || userEmail.isEmpty()) {
            userEmail = AuthManager.DEFAULT_EMAIL;
        }

        userName = getIntent().getStringExtra(AuthManager.EXTRA_USER_NAME);
        if (userName == null || userName.isEmpty()) {
            userName = AuthManager.getUserName(this, userEmail);
        }

        initViews();
        setupListeners();
    }

    /**
     * Initializes views and populates student profile details.
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnProfileBack);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvStudentID = findViewById(R.id.tvProfileStudentID);
        tvDepartment = findViewById(R.id.tvProfileDepartment);
        tvSession = findViewById(R.id.tvProfileSession);
        btnViewStudentCard = findViewById(R.id.btnViewStudentCard);
        btnLogout = findViewById(R.id.btnLogout);

        tvProfileName.setText(userName);
        tvProfileEmail.setText(userEmail);
        tvStudentID.setText(getString(R.string.default_student_id));
        tvDepartment.setText(getString(R.string.default_department));
        tvSession.setText(getString(R.string.default_session));
    }

    /**
     * Sets listeners for back, card navigation, and logout.
     */
    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Open Digital Student Card Activity
        btnViewStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, StudentCardActivity.class);
                intent.putExtra(AuthManager.EXTRA_USER_NAME, userName);
                intent.putExtra(AuthManager.EXTRA_USER_EMAIL, userEmail);
                startActivity(intent);
            }
        });

        // Logout: Clears the back stack and returns to LoginActivity
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, getString(R.string.logout_successful), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}

