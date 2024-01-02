package com.example.assignmnet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnBMI = findViewById(R.id.btnBMI);
        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the next line to start Activity2
                Intent intent = new Intent(ActivityHome.this, Activity4.class);
                startActivity(intent);
            }
        });

        Button btnEXC = findViewById(R.id.btnEXC);
        btnEXC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the next line to start Activity2
                Intent intent = new Intent(ActivityHome.this, Activity5.class);
                startActivity(intent);
            }
        });
    }
}