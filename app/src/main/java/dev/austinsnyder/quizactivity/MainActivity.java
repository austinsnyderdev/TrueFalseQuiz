package dev.austinsnyder.quizactivity;

//page 21
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TrueFalseManager trueFalseManager = new TrueFalseManager();
        trueFalseManager.fillTrueFalseArray();

        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(trueFalseManager.nextQuestion());

        questionTextView.setOnClickListener(v -> questionTextView.setText(trueFalseManager.nextQuestion()));

        Button trueButton = (Button) findViewById(R.id.trueButton);
        Button falseButton = (Button) findViewById(R.id.falseButton);

        trueButton.setOnClickListener(v -> {
            boolean userAnsweredCorrectly = trueFalseManager.checkAnswer(true);
            if (userAnsweredCorrectly) {
                Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        });

        falseButton.setOnClickListener(v -> {
                boolean userAnsweredCorrectly = trueFalseManager.checkAnswer(false);
                if (userAnsweredCorrectly) {
                    Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            int nextQuestionId = trueFalseManager.nextQuestion();
            if (nextQuestionId != -1) questionTextView.setText(nextQuestionId);
        });

        Button previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(v -> {
            int previousQuestionId = trueFalseManager.previousQuestion();
            if (previousQuestionId != -1) questionTextView.setText(previousQuestionId);
        });

    }
}