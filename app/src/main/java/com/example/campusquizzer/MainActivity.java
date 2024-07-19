package com.example.campusquizzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dbmsButton = findViewById(R.id.dbms_button);
        Button osButton = findViewById(R.id.os_button);
        Button cnButton = findViewById(R.id.cn_button);
        Button coaButton = findViewById(R.id.coa_button);
        Button engMathButton = findViewById(R.id.eng_math_button);
        Button dataStructureButton = findViewById(R.id.data_structure_button);
        Button programmingButton = findViewById(R.id.programming_button);

        dbmsButton.setOnClickListener(view -> startQuizActivity("dbms"));
        osButton.setOnClickListener(view -> startQuizActivity("os"));
        cnButton.setOnClickListener(view -> startQuizActivity("cn"));
        coaButton.setOnClickListener(view -> startQuizActivity("coa"));
        engMathButton.setOnClickListener(view -> startQuizActivity("maths"));
        dataStructureButton.setOnClickListener(view -> startQuizActivity("dsa"));
        programmingButton.setOnClickListener(view -> startQuizActivity("programming"));
    }

    private void startQuizActivity(String category) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}
