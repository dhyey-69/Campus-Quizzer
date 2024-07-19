package com.example.campusquizzer;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score); // Ensure this matches your layout file name

        TextView messagePrefixText = findViewById(R.id.message_prefix);
        TextView scoreText = findViewById(R.id.score_text);
        TextView totalText = findViewById(R.id.total_text);
        ProgressBar scoreProgressBar = findViewById(R.id.score_progress_bar);
        Button backToHomeButton = findViewById(R.id.back_to_home_button);

        Intent intent = getIntent();
        boolean timeOut = intent.getBooleanExtra("TIME_OUT", false);  // Retrieve the timeout flag
        int score = intent.getIntExtra("SCORE", 0);
        int total = intent.getIntExtra("TOTAL", 10); // Default total if not passed

        int progress = (int) ((score / (float) total) * 100);

        if (timeOut) {
            messagePrefixText.setText("Time Over!");
        } else {
            messagePrefixText.setText("Quiz Completed!");
        }

        scoreText.setText("Score: " + score);
        totalText.setText("Total Questions: " + total);

        scoreProgressBar.setProgress(progress);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}
