package com.example.campusquizzer;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer timer;
    private boolean timeOut = false;
    private TextView timerText;
    private TextView questionNumberText;
    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        timerText = findViewById(R.id.timer_text);
        questionNumberText = findViewById(R.id.question_number_text);
        questionText = findViewById(R.id.question_text);
        optionsGroup = findViewById(R.id.options_group);
        submitButton = findViewById(R.id.submit_button);

        String category = getIntent().getStringExtra("CATEGORY");
        loadQuestions(category);

        startTimer();
        displayQuestion();

        submitButton.setOnClickListener(view -> {
            checkAnswer();
        });
    }

    private void loadQuestions(String category) {
        questionList = new ArrayList<>();
        String fileName = category.toLowerCase().replace(" ", "_") + ".csv";
        try (InputStream inputStream = getAssets().open(fileName)) {
            questionList = CSVParser.parseCSV(inputStream);
            // Shuffle the question list
            Collections.shuffle(questionList);
            // Take only the first 10 questions
            if (questionList.size() > 10) {
                questionList = questionList.subList(0, 10);
            }
            // Shuffle options for each question
            for (Question question : questionList) {
                List<String> options = new ArrayList<>();
                options.add(question.getOption1());
                options.add(question.getOption2());
                options.add(question.getOption3());
                options.add(question.getOption4());
                Collections.shuffle(options);
                // Set shuffled options back to the question
                question.setOption1(options.get(0));
                question.setOption2(options.get(1));
                question.setOption3(options.get(2));
                question.setOption4(options.get(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionNumberText.setText("Question " + (currentQuestionIndex + 1) + " out of " + questionList.size());
        questionText.setText(currentQuestion.getQuestion());

        ((RadioButton) optionsGroup.getChildAt(0)).setText(currentQuestion.getOption1());
        ((RadioButton) optionsGroup.getChildAt(1)).setText(currentQuestion.getOption2());
        ((RadioButton) optionsGroup.getChildAt(2)).setText(currentQuestion.getOption3());
        ((RadioButton) optionsGroup.getChildAt(3)).setText(currentQuestion.getOption4());

        optionsGroup.clearCheck();
        submitButton.setVisibility(View.VISIBLE);
    }

    private void checkAnswer() {
        int selectedOptionId = optionsGroup.getCheckedRadioButtonId();
        if (selectedOptionId == -1) {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            if (selectedRadioButton.getText().equals(questionList.get(currentQuestionIndex).getAnswer())) {
                score++;
            }

            if (currentQuestionIndex < questionList.size() - 1) {
                currentQuestionIndex++;
                displayQuestion();
            } else {
                endQuiz();
            }
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time Left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeOut = true;
                endQuiz();
            }
        }.start();
    }

    private void endQuiz() {
        if (timer != null) {
            timer.cancel();
        }
        Intent intent = new Intent(QuizActivity.this, ScoreActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL", questionList.size());
        intent.putExtra("TIME_OUT", timeOut);  // Pass the timeout flag
        startActivity(intent);
        finish();
    }
}
