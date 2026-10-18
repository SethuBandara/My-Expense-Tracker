package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnManageExpense;
    Button btnSummary;

    TextView tvDashboardTotal;
    TextView tvDashboardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnManageExpense = findViewById(R.id.btnManageExpense);
        btnSummary = findViewById(R.id.btnSummary);
        tvDashboardTotal = findViewById(R.id.tvDashboardTotal);
        tvDashboardCount = findViewById(R.id.tvDashboardCount);

        btnManageExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivity(intent);
            }
            });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateDashboard();
    }
    private void updateDashboard() {

        double total = ExpenseData.getTotalExpenses();

        tvDashboardTotal.setText("Rs. " + String.format("%.2f", total));
        tvDashboardCount.setText(String.valueOf(ExpenseData.expenseCount));
    }
}