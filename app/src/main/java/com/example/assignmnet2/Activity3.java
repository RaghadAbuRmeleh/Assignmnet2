package com.example.assignmnet2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class Activity3 extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText pass;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        firstName = findViewById(R.id.edtFirst);
        lastName = findViewById(R.id.edtLast);
        email = findViewById(R.id.edtEmailC);
        pass = findViewById(R.id.edtPassC);
        signUp = findViewById(R.id.btnSignHome);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkDataEntered()) {
                        saveUserData();
                        showToast("User data saved successfully!");
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    showToast("An error occurred while processing your request.");
                }
            }
        });
    }



    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isPasswordValid(EditText text) {
        return text.getText().toString().length() >= 6;
    }

    boolean checkDataEntered() {
        if (isEmpty(firstName)) {
            showToast("You must enter first name to register!");
            return false;
        }

        if (isEmpty(lastName)) {
            lastName.setError("Last name is required!");
            return false;
        }

        if (!isEmail(email)) {
            email.setError("Enter valid email!");
            return false;
        }

        if (!isPasswordValid(pass)) {
            pass.setError("Password must be at least 6 characters long!");
            return false;
        }

        return true;
    }

    private void saveUserData() {
        User user = new User(
                firstName.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                pass.getText().toString()
        );

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", userJson);
        editor.apply();

        showToast("User data saved successfully!");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
