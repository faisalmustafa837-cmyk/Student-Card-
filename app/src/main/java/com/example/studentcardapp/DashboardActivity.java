package com.example.studentcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

/**
 * Screen 6: DashboardActivity
 * Central portal displaying 2-column grid of module cards.
 * Layout Demonstrated: GridLayout (2 Columns)
 */
public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private MaterialCardView cardHeaderProfile;
    private MaterialCardView cardStudentCard;
    private MaterialCardView cardCourses;
    private MaterialCardView cardAttendance;
    private MaterialCardView cardResults;
    private MaterialCardView cardProfile;
    private MaterialCardView cardSettings;

    private String userName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Retrieve user data passed via Intent extras
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
     * Initializes views and displays personalized welcome greeting.
     */
    private void initViews() {
        tvWelcome = findViewById(R.id.tvDashboardWelcome);
        cardHeaderProfile = findViewById(R.id.cardHeaderProfile);
        cardStudentCard = findViewById(R.id.cardStudentCard);
        cardCourses = findViewById(R.id.cardCourses);
        cardAttendance = findViewById(R.id.cardAttendance);
        cardResults = findViewById(R.id.cardResults);
        cardProfile = findViewById(R.id.cardProfile);
        cardSettings = findViewById(R.id.cardSettings);

        // Format welcome text with user's name
        tvWelcome.setText(getString(R.string.welcome_format, userName));
    }

    /**
     * Configures click listeners for all module cards and profile header.
     */
    private void setupListeners() {
        // Quick profile header avatar click
        cardHeaderProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        // 1. Student Card Module -> opens digital Student Card activity
        cardStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, StudentCardActivity.class);
                intent.putExtra(AuthManager.EXTRA_USER_NAME, userName);
                intent.putExtra(AuthManager.EXTRA_USER_EMAIL, userEmail);
                startActivity(intent);
            }
        });

        // 2. Courses Module -> Coming soon Toast
        cardCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComingSoonToast(getString(R.string.module_courses));
            }
        });

        // 3. Attendance Module -> Coming soon Toast
        cardAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComingSoonToast(getString(R.string.module_attendance));
            }
        });

        // 4. Results Module -> Coming soon Toast
        cardResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComingSoonToast(getString(R.string.module_results));
            }
        });

        // 5. Profile Module -> opens ProfileActivity
        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        // 6. Settings Module -> Coming soon Toast
        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComingSoonToast(getString(R.string.module_settings));
            }
        });
    }

    /**
     * Navigates to ProfileActivity using an explicit Intent.
     */
    private void openProfileActivity() {
        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
        intent.putExtra(AuthManager.EXTRA_USER_NAME, userName);
        intent.putExtra(AuthManager.EXTRA_USER_EMAIL, userEmail);
        startActivity(intent);
    }

    /**
     * Displays a polite "coming soon" notification for placeholder modules.
     */
    private void showComingSoonToast(String moduleName) {
        Toast.makeText(this, getString(R.string.msg_feature_coming_soon, moduleName), Toast.LENGTH_SHORT).show();
    }
}
