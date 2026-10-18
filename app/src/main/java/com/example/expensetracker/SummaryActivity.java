package com.example.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SummaryActivity extends AppCompatActivity {

    SeekBar seekBudget;

    Button btnCalculate;

    TextView tvBudget;
    TextView tvExpenseCount;
    TextView tvTotalSpent;
    TextView tvRemaining;
    TextView tvPercentage;
    TextView tvStatus;

    int budget = ExpenseData.monthlyBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);

        seekBudget = findViewById(R.id.seekBudget);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvBudget = findViewById(R.id.tvBudgetValue);
        tvExpenseCount = findViewById(R.id.tvExpenseCount);
        tvTotalSpent = findViewById(R.id.tvTotalSpent);
        tvRemaining = findViewById(R.id.tvRemaining);
        tvPercentage = findViewById(R.id.tvPercentage);
        tvStatus = findViewById(R.id.tvStatus);

        seekBudget.setMax(50);
        int initialProgress = ExpenseData.monthlyBudget / 1000;
        seekBudget.setProgress(initialProgress);
        budget = ExpenseData.monthlyBudget;
        tvBudget.setText("Budget: Rs. " + budget);

        seekBudget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                budget = progress * 1000;
                ExpenseData.monthlyBudget = budget;
                tvBudget.setText("Budget: Rs. " + budget);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSummary();
            }
        });
    }

    private void calculateSummary() {

        if (ExpenseData.expenseCount == 0) {

            Toast.makeText(
                    this,
                    "Please add expenses first",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        if (budget == 0) {

            Toast.makeText(
                    this,
                    "Please select a budget",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        double total = ExpenseData.getTotalExpenses();
        double remaining = budget - total;
        double percentage = (total / budget) * 100;

        String status;

        if (percentage < 50) {

            status = "Low Spending";

        } else if (percentage < 80) {

            status = "Moderate Spending";

        } else if (percentage <= 100) {

            status = "High Spending";

        } else {

            status = "Budget Exceeded";
        }

        tvExpenseCount.setText("Number of Expenses: " + ExpenseData.expenseCount);

        tvTotalSpent.setText("Total Spent: Rs. " + String.format("%.2f", total));

        tvRemaining.setText("Remaining Budget: Rs. " + String.format("%.2f", remaining));

        tvPercentage.setText("Budget Used: " + String.format("%.2f", percentage) + "%");

        tvStatus.setText("Status: " + status);
    }
}