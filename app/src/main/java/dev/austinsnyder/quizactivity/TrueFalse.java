package dev.austinsnyder.quizactivity;

public class TrueFalse {
    //Takes in question id #
    private int questionID;

    //Whether the question is true or false
    private boolean trueQuestion;

    public TrueFalse(int questionID, boolean trueQuestion) {
        this.questionID = questionID;
        this.trueQuestion = trueQuestion;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public boolean isTrueQuestion() {
        return trueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        this.trueQuestion = trueQuestion;
    }
}
