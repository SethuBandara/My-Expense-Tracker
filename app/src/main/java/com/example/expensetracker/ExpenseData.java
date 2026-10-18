package com.example.expensetracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExpenseData extends AppCompatActivity {

    public static String[] titles = new String[20];

    public static double[] amounts = new double[20];

    public static String[] categories = new String[20];

    public static boolean[] recurring = new boolean[20];

    public static int expenseCount = 0;
    public static int monthlyBudget = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_data);

    }
    public static double getTotalExpenses() {

        double total = 0;

        for (int i = 0; i < expenseCount; i++) {

            total = total + amounts[i];
        }

        return total;
    }

}
