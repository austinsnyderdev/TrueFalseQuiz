package dev.austinsnyder.quizactivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TrueFalseManager {

    private final ArrayList<TrueFalse> trueFalseArray;
    private int arrayIndex = -1;
    private static final String TAG = "TrueFalseManager";
    public TrueFalseManager(ArrayList<TrueFalse> trueFalseArray) {
        this.trueFalseArray = trueFalseArray;
    }

    public TrueFalseManager() {
        this.trueFalseArray = new ArrayList<TrueFalse>();
    }

    public void fillTrueFalseArray() {
        trueFalseArray.add(new TrueFalse(R.string.questionOcean, false));
        trueFalseArray.add(new TrueFalse(R.string.questionAnimal, false));
        trueFalseArray.add(new TrueFalse(R.string.questionSkyscraper, true));
        trueFalseArray.add(new TrueFalse(R.string.questionMovie, true));
        trueFalseArray.add(new TrueFalse(R.string.questionInternet, true));
    }

    public ArrayList<TrueFalse> getTrueFalseArray() {
        return trueFalseArray;
    }

    //Displays the next question in the array to the controller TextView, and notifies the user if there are no more questions
    public int nextQuestion() {
        int questionNumber;
        if (!(arrayIndex > trueFalseArray.size() - 1)) {
            arrayIndex++;
            questionNumber = trueFalseArray.get(arrayIndex).getQuestion();
        }
        else {
            Log.i(TAG, "User attempted to access NEXT question OOB");
            return -1;
        }
        return questionNumber;
    }

    //Displays the previous question in the array to the controller TextView, and notifies the user if there are no more questions
    public int previousQuestion() {
        int questionNumber;
        if (arrayIndex != 0) {
            arrayIndex--;
            questionNumber = trueFalseArray.get(arrayIndex).getQuestion();
        }
        else {
            Log.i(TAG, "User attempted to access PREVIOUS question OOB");
            return -1;
        }
        return questionNumber;
    }

    //returns true if the answer is correct and false if it is not
    public boolean checkAnswer(boolean userAnswer) {
        TrueFalse currentQuestion = trueFalseArray.get(arrayIndex - 1);
        boolean answer = currentQuestion.isTrueQuestion();
        return userAnswer == answer;
    }
}
