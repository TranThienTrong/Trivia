package com.patecan.trivia.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.patecan.trivia.R;
import com.patecan.trivia.data.AnswerListRespone;
import com.patecan.trivia.data.QuestionBank;
import com.patecan.trivia.model.Question;
import com.patecan.trivia.model.Score;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionTextView;
    private TextView mQuestionCounterTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private int mCurrentQuestion = 0;
    private List<Question> mQuestionList;

    private TextView mScoreTextView;
    private TextView mHighestScore;

    private int scoreCounter = 0;
    private Score score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = new Score();

        mScoreTextView = findViewById(R.id.score);
        mScoreTextView.setText("Score: 0");
        int highestScore = score.getSharedPref(this);
        mHighestScore = findViewById(R.id.highest_score);
        mHighestScore.setText("Highest Score: " + Integer.toString(highestScore));

        //Toast.makeText(this, "Highest Score:" + Integer.toString(value), Toast.LENGTH_LONG).show();

        mQuestionCounterTextView = findViewById(R.id.counter_text);
        mQuestionTextView = findViewById(R.id.question_textview);
        mNextButton = findViewById(R.id.next_button);
        mPreviousButton = findViewById(R.id.previous_button);
        mTrueButton = findViewById(R.id.button_true);
        mFalseButton = findViewById(R.id.button_false);


        mPreviousButton.setOnClickListener(this);
        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("QUESTION_INDEX", MODE_PRIVATE);
        int lastQuestion = sharedPreferences.getInt("Question_Index",0);
        mCurrentQuestion = lastQuestion;

        mQuestionList = new QuestionBank().getQuestions(new AnswerListRespone() {
            @Override
            public void processFinish(ArrayList<Question> questionArrayList) {
                Log.d("Callback", "processFinish: " + questionArrayList);

                mQuestionCounterTextView.setText(Integer.toString(mCurrentQuestion + 1) + "/" + mQuestionList.size());
                mQuestionTextView.setText(mQuestionList.get(mCurrentQuestion).getmAnswer());
                Log.d("Callback", "Sau khi CallBack trong Main");
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.previous_button:
                if (mCurrentQuestion > 0) {
                    mCurrentQuestion = (mCurrentQuestion - 1) % mQuestionList.size();
                    updateQuestion();
                }
                break;
            case R.id.button_true:
                checkAnswer(true);

                updateQuestion();
                break;
            case R.id.button_false:
                checkAnswer(false);

                updateQuestion();
                break;
            case R.id.next_button:
                mCurrentQuestion = (mCurrentQuestion + 1) % mQuestionList.size();
                updateQuestion();
                break;
        }
    }

    private void updateQuestion() {
        mQuestionCounterTextView.setText(Integer.toString(mCurrentQuestion + 1) + "/" + mQuestionList.size());
        mQuestionTextView.setText(mQuestionList.get(mCurrentQuestion).getmAnswer());
    }


    private void checkAnswer(boolean userAnswer) {
        if (mQuestionList.get(mCurrentQuestion).getIsmAnswerTrue() == userAnswer) {
            mCurrentQuestion = (mCurrentQuestion + 1) % mQuestionList.size();
            addScore();
            mScoreTextView.setText("Score: " + score.getScore());
            updateHighestScore();
            Toast.makeText(this, "Yes", Toast.LENGTH_LONG).show();
        } else {
            shakeAnimation();
            Toast.makeText(this, Boolean.toString(mQuestionList.get(mCurrentQuestion).getIsmAnswerTrue()), Toast.LENGTH_LONG).show();
        }
    }


    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        score.saveInSharedPref(this);
        saveQuestion(mCurrentQuestion);
    }

    private void addScore() {
        scoreCounter += 1;
        score.setScore(scoreCounter);
    }

    private void updateHighestScore() {
        score.saveInSharedPref(this);
        int highestScore = score.getSharedPref(this);
        mHighestScore.setText("Highest Score: " + Integer.toString(highestScore));
    }

    private void saveQuestion(int index) {
        SharedPreferences sharedPreferences = getSharedPreferences("QUESTION_INDEX", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("Question_Index", index).apply();
    }
}
