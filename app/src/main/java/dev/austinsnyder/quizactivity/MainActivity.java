package dev.austinsnyder.quizactivity;

//page 21 -> 66 -> 96
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import dev.austinsnyder.quizactivity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    protected static final String KEY_INDEX = "index";
    protected TrueFalseManager trueFalseManager;
    protected boolean userCheated;
    protected static final String USER_CHEATED = "dev.austinsnyder.quizactivity.user_cheated";

    private ActivityResultLauncher<Intent> cheatActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        this.initializeViewBinding();

        trueFalseManager = new TrueFalseManager();
        trueFalseManager.fillTrueFalseArray();

        Intent intent = this.getIntent();
        if (this.getIntent().getExtras() != null) {
            userCheated = intent.getBooleanExtra(USER_CHEATED, false);
            trueFalseManager.arrayIndex = intent.getIntExtra(KEY_INDEX, 0);
        }
        initializeActivityResultLauncher();

        saveInstanceStateRestore(savedInstanceState);

        binding.questionTextView.setText(trueFalseManager.updateQuestionID());

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
            Log.d("TrueButton method", String.valueOf(userAnsweredCorrectly));
            userResponseOutput(userAnsweredCorrectly, userCheated);
        });

        binding.falseButton.setOnClickListener(v -> {
            boolean userAnsweredCorrectly = trueFalseManager.checkAnswer(false);
            userResponseOutput(userAnsweredCorrectly, userCheated);
        });

        binding.nextButton.setOnClickListener(v -> {
            int nextQuestionId = trueFalseManager.nextQuestionID();
            if (nextQuestionId != -1) binding.questionTextView.setText(nextQuestionId);
            userCheated = false;
        });

        binding.previousButton.setOnClickListener(v -> {
            int previousQuestionId = trueFalseManager.previousQuestionID();
            if (previousQuestionId != -1) binding.questionTextView.setText(previousQuestionId);
            userCheated = false;
        });

        binding.cheatButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheatActivity.class);
            TrueFalse currentQuestion = trueFalseManager.trueFalseArray.get(trueFalseManager.arrayIndex);
            boolean answerIsTrue = currentQuestion.isTrueQuestion();
            intent.putExtra(CheatActivity.CURRENT_ANSWER_IS_TRUE, answerIsTrue);
            intent.putExtra(KEY_INDEX, trueFalseManager.arrayIndex);
            cheatActivityResultLauncher.launch(intent);
        });
    }

    protected void initializeActivityResultLauncher() {
        cheatActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        boolean userCheated = intent.getBooleanExtra(USER_CHEATED, false);
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState called");
        outState.putInt(KEY_INDEX, trueFalseManager.arrayIndex);
    }

    protected void saveInstanceStateRestore(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            trueFalseManager.arrayIndex = savedInstanceState.getInt(KEY_INDEX, -1);
        }
    }

    //Responds to user if they cheated - Always tells user if their answer was correct or incorrect
    protected void userResponseOutput(boolean userAnsweredCorrectly, boolean userCheated) {
        if (userAnsweredCorrectly) {
            if (userCheated) {
                Toast.makeText(MainActivity.this, R.string.cheatingWrong, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}