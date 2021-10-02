package dev.austinsnyder.quizactivity;

//page 21 -> 66
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import dev.austinsnyder.quizactivity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        this.initializeViewBinding();

        TrueFalseManager trueFalseManager = new TrueFalseManager();
        trueFalseManager.fillTrueFalseArray();

        binding.questionTextView.setText(trueFalseManager.nextQuestionID());
        binding.questionTextView.setOnClickListener(v -> binding.questionTextView.setText(trueFalseManager.nextQuestionID()));

        this.buttonListeners(trueFalseManager);

    }

    private void initializeViewBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void buttonListeners(TrueFalseManager trueFalseManager) {
        binding.trueButton.setOnClickListener(v -> {
            boolean userAnsweredCorrectly = trueFalseManager.checkAnswer(true);
            if (userAnsweredCorrectly) {
                Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        });

        binding.falseButton.setOnClickListener(v -> {
            boolean userAnsweredCorrectly = trueFalseManager.checkAnswer(false);
            if (userAnsweredCorrectly) {
                Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        });

        binding.nextButton.setOnClickListener(v -> {
            int nextQuestionId = trueFalseManager.nextQuestionID();
            if (nextQuestionId != -1) binding.questionTextView.setText(nextQuestionId);
        });
        binding.previousButton.setOnClickListener(v -> {
            int previousQuestionId = trueFalseManager.previousQuestionID();
            if (previousQuestionId != -1) binding.questionTextView.setText(previousQuestionId);
        });
    }
}