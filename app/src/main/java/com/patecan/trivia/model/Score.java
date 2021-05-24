package com.patecan.trivia.model;

import android.content.Context;
import android.content.SharedPreferences;

public class Score {

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Score() {
        this.score = 0;
    }

    public void saveInSharedPref(Context context) {
        int currentScore = this.getScore();

        SharedPreferences sharedPreferences = context.getSharedPreferences("HIGHEST_SCORE", Context.MODE_PRIVATE);

        int highestScore = sharedPreferences.getInt("HighestScore",currentScore);

        if (currentScore>highestScore){
            highestScore = currentScore;
            sharedPreferences.edit().putInt("HighestScore", highestScore).apply();
        }
    }

    public int getSharedPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("HIGHEST_SCORE", Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt("HighestScore", 0);
        return value;
    }


}
