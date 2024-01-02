package com.example.assignmnet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    public static final String EMAIL = "EMAIL";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText edtEmail;
    private EditText edtPassword;
    private CheckBox chk;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        setupViews();
        setupSharedPrefs();
        checkPrefs();


    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag){
            String email = prefs.getString(EMAIL, "");
            String password = prefs.getString(PASS, "");
            edtEmail.setText(email);
            edtPassword.setText(password);
            chk.setChecked(true);
        }
    }

    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setupViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPass);
        chk = findViewById(R.id.chk);
    }

    public void btnRememberMe(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if(chk.isChecked()){
            if(!flag) {
                editor.putString(EMAIL, email);
                editor.putString(PASS, password);
                editor.putBoolean(FLAG, true);
                editor.commit();
            }

        }
        // authenticate the user

    }
public void btnLoginOnClick(View view) {
    String enteredEmail = edtEmail.getText().toString();
    String enteredPassword = edtPassword.getText().toString();

    String storedEmail = prefs.getString(EMAIL, "");
    String storedPassword = prefs.getString(PASS, "");

    if (enteredEmail.equals(storedEmail) && enteredPassword.equals(storedPassword)) {
        Intent intent = new Intent(Activity2.this, ActivityHome.class);
        startActivity(intent);
        finish();
    } else {
        showToast("Invalid email or password. Please try again.");
    }
}

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}