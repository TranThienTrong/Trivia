package com.patecan.trivia.model;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class Question extends AppCompatActivity {

    private String mAnswer;
    private boolean mAnswerTrue;

    public Question(String mAnswer, boolean mAnswerTrue) {
        this.mAnswer = mAnswer;
        this.mAnswerTrue = mAnswerTrue;
    }

    public Question() {
    }

    public String getmAnswer() {
        return mAnswer;
    }

    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }

    public boolean getIsmAnswerTrue() {
        return mAnswerTrue;
    }

    public void setmAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question: "+ this.getmAnswer()+
                " Answer "+ this.getIsmAnswerTrue();
    }
}
