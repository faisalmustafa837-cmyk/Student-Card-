package com.example.studentcardapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * StudentCardActivity
 * Displays an official digital student identity card module.
 * Preserves and integrates the core Student Card functionality.
 * Layout Demonstrated: ConstraintLayout
 */
public class StudentCardActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvStudentName;
    private TextView tvStudentId;
    private TextView tvDepartment;
    private TextView tvValidDate;
    private TextView tvBlood;

    private String userName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_card);

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
     * Initializes card view components and binds student identity information.
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnCardBack);
        tvStudentName = findViewById(R.id.tvCardStudentName);
        tvStudentId = findViewById(R.id.tvCardStudentId);
        tvDepartment = findViewById(R.id.tvCardDepartment);
        tvValidDate = findViewById(R.id.tvCardValidDate);
        tvBlood = findViewById(R.id.tvCardBlood);

        // Set dynamic and formatted student details
        tvStudentName.setText(userName);
        tvStudentId.setText(String.format("ID: %s", getString(R.string.default_student_id)));
        tvDepartment.setText(String.format("Dept: %s", getString(R.string.default_department)));
        tvValidDate.setText(String.format("Valid: %s", getString(R.string.default_valid_thru)));
        tvBlood.setText(String.format("Blood: %s", getString(R.string.default_blood_group)));
    }

    /**
     * Sets click listener for back navigation button.
     */
    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

