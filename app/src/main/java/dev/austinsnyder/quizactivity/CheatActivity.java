package dev.austinsnyder.quizactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import dev.austinsnyder.quizactivity.databinding.ActivityCheatBinding;

public class CheatActivity extends MainActivity {

    private static final String TAG = "CheatActivity";
    private ActivityCheatBinding binding;
    public static final String CURRENT_ANSWER_IS_TRUE = "dev.austinsnyder.quizactivity.answer_is_true";
    private boolean answerIsTrue;
    private boolean userCheated = false;
    private int arrayIndex;
    private static final String DISPLAY_ANSWER = "dev.austinsnyder.quizactivity.display_answer";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() called");

        savedInstanceStateRestore(savedInstanceState);
        getExtras();

        this.initializeViewBinding();
        this.buttonListeners();

        //Maintains proper UI if the user cheated and onCreate is called again on CheatActivity
        if (userCheated) {
            binding.answerText.setText(String.valueOf(answerIsTrue));
        }
    }

    private void initializeViewBinding() {
        binding = ActivityCheatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void buttonListeners() {
        binding.cheatButton.setOnClickListener(v -> {
            binding.answerText.setText(String.valueOf(answerIsTrue));
            userCheated = true;
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState() called");
        outState.putBoolean(DISPLAY_ANSWER, userCheated);
    }

    public void savedInstanceStateRestore(Bundle savedInstanceState) {
        //Correctly sets userCheated if there is a savedInstanceState available, so that text can be displayed
        if (savedInstanceState != null) {
            userCheated = savedInstanceState.getBoolean(DISPLAY_ANSWER, false);
        }
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        putExtras(i);
        Log.i(TAG, "Array Index is: " + arrayIndex);
        setResult(RESULT_OK);
        startActivity(i);
    }

    public void getExtras() {
        Bundle passedBundle = getIntent().getExtras();
        if (passedBundle != null) {
            answerIsTrue = passedBundle.containsKey(CURRENT_ANSWER_IS_TRUE) ? passedBundle.getBoolean(CURRENT_ANSWER_IS_TRUE) : false;
            arrayIndex = passedBundle.containsKey(MainActivity.KEY_INDEX) ? passedBundle.getInt(MainActivity.KEY_INDEX) : 0;
        }
    }

    public void putExtras(Intent i) {
        i.putExtra(MainActivity.USER_CHEATED, userCheated);
        i.putExtra(MainActivity.KEY_INDEX, arrayIndex);
    }
}
