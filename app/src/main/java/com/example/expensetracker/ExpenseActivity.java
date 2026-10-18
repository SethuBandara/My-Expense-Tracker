package com.example.expensetracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExpenseActivity extends AppCompatActivity {

    EditText etTitle, etAmount;
    RadioGroup radioGroupCategory;
    RadioButton rbFood, rbTransport, rbShopping, rbOther;
    CheckBox cbRecurring;
    Button btnAdd, btnUpdate, btnDelete;
    Button btnPrevious, btnNext;
    TextView tvSavedExpense, tvRecordNumber;

    int currentIndex = -1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense);

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);

        radioGroupCategory = findViewById(R.id.radioGroupCategory);

        rbFood = findViewById(R.id.rbFood);
        rbTransport = findViewById(R.id.rbTransport);
        rbShopping = findViewById(R.id.rbShopping);
        rbOther = findViewById(R.id.rbOther);

        cbRecurring = findViewById(R.id.cbRecurring);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        tvRecordNumber = findViewById(R.id.tvRecordNumber);
        tvSavedExpense = findViewById(R.id.tvSavedExpense);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addExpense();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateExpense();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteExpense();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousExpense();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextExpense();
            }
        });
    }



//create Operation
    private void addExpense() {

        if (!validateInput()) {
            return;
        }

        if (ExpenseData.expenseCount >= 20) {
            Toast.makeText(this, "Maximum 20 expenses allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        int position = ExpenseData.expenseCount;

        ExpenseData.titles[position] = etTitle.getText().toString().trim();
        ExpenseData.amounts[position] = Double.parseDouble(etAmount.getText().toString());
        ExpenseData.categories[position] = getSelectedCategory();
        ExpenseData.recurring[position] = cbRecurring.isChecked();

        currentIndex = position;
        ExpenseData.expenseCount++;
        displayCurrentExpense();
        Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show();
    }


    //Read Operation
    private void displayCurrentExpense() {

        if (ExpenseData.expenseCount == 0 ||
                currentIndex == -1) {

            tvRecordNumber.setText("No expenses added");
            tvSavedExpense.setText("Add your first expense above.");
            clearInputFields();
            return;
        }
        tvRecordNumber.setText("Expense " + (currentIndex + 1) + " of " + ExpenseData.expenseCount);
        String recurringText;
        if (ExpenseData.recurring[currentIndex]) {
            recurringText = "Yes";
        } else {
            recurringText = "No";
        }
        tvSavedExpense.setText(
                "Title: " +
                        ExpenseData.titles[currentIndex] + "\nAmount: Rs. " +
                        ExpenseData.amounts[currentIndex] + "\nCategory: " +
                        ExpenseData.categories[currentIndex] + "\nRecurring: " +
                        recurringText);

        etTitle.setText(ExpenseData.titles[currentIndex]);
        etAmount.setText(String.valueOf(ExpenseData.amounts[currentIndex]));
        setCategoryRadioButton(ExpenseData.categories[currentIndex]);
        cbRecurring.setChecked(ExpenseData.recurring[currentIndex]);
    }


    // =========================
    // NEXT
    // =========================

    private void showNextExpense() {

        if (ExpenseData.expenseCount == 0) {

            Toast.makeText(
                    this,
                    "No expenses available",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        if (currentIndex <
                ExpenseData.expenseCount - 1) {

            currentIndex++;

            displayCurrentExpense();

        } else {

            Toast.makeText(
                    this,
                    "This is the last expense",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // =========================
    // PREVIOUS
    // =========================

    private void showPreviousExpense() {

        if (ExpenseData.expenseCount == 0) {

            Toast.makeText(
                    this,
                    "No expenses available",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        if (currentIndex > 0) {

            currentIndex--;

            displayCurrentExpense();

        } else {

            Toast.makeText(
                    this,
                    "This is the first expense",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }



    // update Operation
    private void updateExpense() {

        if (ExpenseData.expenseCount == 0 ||
                currentIndex == -1) {
            Toast.makeText(this, "No expense to update", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validateInput()) {
            return;
        }
        ExpenseData.titles[currentIndex] = etTitle.getText().toString().trim();
        ExpenseData.amounts[currentIndex] = Double.parseDouble(etAmount.getText().toString());
        ExpenseData.categories[currentIndex] = getSelectedCategory();
        ExpenseData.recurring[currentIndex] = cbRecurring.isChecked();
        displayCurrentExpense();

        Toast.makeText(this, "Expense updated successfully!", Toast.LENGTH_SHORT).show();
    }




    //Delete Operation
    private void deleteExpense() {
        if (ExpenseData.expenseCount == 0 ||
                currentIndex == -1) {
            Toast.makeText(this, "No expense to delete", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = currentIndex;
             i < ExpenseData.expenseCount - 1;
             i++) {
            ExpenseData.titles[i] = ExpenseData.titles[i + 1];
            ExpenseData.amounts[i] = ExpenseData.amounts[i + 1];
            ExpenseData.categories[i] = ExpenseData.categories[i + 1];
            ExpenseData.recurring[i] = ExpenseData.recurring[i + 1];
        }
        ExpenseData.expenseCount--;
        if (ExpenseData.expenseCount == 0) {
            currentIndex = -1;
        } else if (currentIndex >=
                ExpenseData.expenseCount) {
            currentIndex = ExpenseData.expenseCount - 1;
        }
        displayCurrentExpense();
        Toast.makeText(this, "Expense deleted successfully!", Toast.LENGTH_SHORT).show();
    }


    // =========================
    // VALIDATION
    // =========================

    private boolean validateInput() {

        String title =
                etTitle.getText()
                        .toString()
                        .trim();


        String amountText =
                etAmount.getText()
                        .toString()
                        .trim();


        if (title.isEmpty()) {

            etTitle.setError(
                    "Please enter expense title"
            );

            return false;
        }


        if (amountText.isEmpty()) {

            etAmount.setError(
                    "Please enter amount"
            );

            return false;
        }


        double amount;

        try {

            amount =
                    Double.parseDouble(
                            amountText
                    );

        } catch (NumberFormatException e) {

            etAmount.setError(
                    "Enter a valid amount"
            );

            return false;
        }


        if (amount <= 0) {

            etAmount.setError(
                    "Amount must be greater than 0"
            );

            return false;
        }


        if (radioGroupCategory
                .getCheckedRadioButtonId() == -1) {

            Toast.makeText(
                    this,
                    "Please select a category",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }


        return true;
    }


    // =========================
    // GET CATEGORY
    // =========================

    private String getSelectedCategory() {

        int selectedId =
                radioGroupCategory
                        .getCheckedRadioButtonId();


        RadioButton selectedRadioButton =
                findViewById(selectedId);


        return selectedRadioButton
                .getText()
                .toString();
    }


    // =========================
    // SET CATEGORY
    // =========================

    private void setCategoryRadioButton(
            String category) {

        if (category.equals("Food")) {

            rbFood.setChecked(true);

        } else if (category.equals("Transport")) {

            rbTransport.setChecked(true);

        } else if (category.equals("Shopping")) {

            rbShopping.setChecked(true);

        } else {

            rbOther.setChecked(true);
        }
    }


    // =========================
    // CLEAR
    // =========================

    private void clearInputFields() {

        etTitle.setText("");

        etAmount.setText("");

        radioGroupCategory.clearCheck();

        cbRecurring.setChecked(false);
    }

}