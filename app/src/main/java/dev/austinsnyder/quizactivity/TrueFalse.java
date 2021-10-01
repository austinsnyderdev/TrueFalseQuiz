package dev.austinsnyder.quizactivity;

public class TrueFalse {
    //Takes in question id #
    private int question;

    //Whether the question is true or false
    private boolean trueQuestion;

    public TrueFalse(int question, boolean trueQuestion) {
        this.question = question;
        this.trueQuestion = trueQuestion;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public boolean isTrueQuestion() {
        return trueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        this.trueQuestion = trueQuestion;
    }
}
