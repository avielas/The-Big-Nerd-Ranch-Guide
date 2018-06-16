package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    /*** member variables ***/
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_QUESTIONS = "questions";
    private static final String KEY_IS_CHEATER = "isCheater";
    private static final String KEY_COUNT_CHEATED = "countCheated";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mCountCheatedTextView;
    private Integer mCountCheated = 0;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),};
    /************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mCheatButton = findViewById(R.id.cheat_button);
        mCountCheatedTextView = findViewById(R.id.count_cheated_text_view);

        if(null != savedInstanceState){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mQuestionBank = (Question []) savedInstanceState.getSerializable(KEY_QUESTIONS);
            mCountCheated = savedInstanceState.getInt(KEY_COUNT_CHEATED);
            updateButtonEnablement();
            handleCheatOccurences();
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();


        //This listener is implemented as an anonymous inner class.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mQuestionBank[mCurrentIndex].setIsAnswered(true);
                setAnswerButtonsEnablement(false);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mQuestionBank[mCurrentIndex].setIsAnswered(true);
                setAnswerButtonsEnablement(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                updateButtonEnablement();
                showGradeIfAnsweredAll();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
//                startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    private void showGradeIfAnsweredAll() {
        int countAnswered=0;
        double grade=0;
        //noinspection ForLoopReplaceableByForEach
        for(Question question : mQuestionBank)
        {
            countAnswered = question.isAnswered() ? countAnswered + 1 : countAnswered;
        }
        if(countAnswered == mQuestionBank.length)
        {
            grade = calculateGrade();
            Toast.makeText(this, "Your grade is " + grade + " ! !", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateGrade() {
        int countAnsweredCorrectly=0;
        for(Question question : mQuestionBank)
        {
            countAnsweredCorrectly = question.isAnsweredCorrect() ? countAnsweredCorrectly + 1 : countAnsweredCorrectly;
        }
        return (1.0*countAnsweredCorrectly/mQuestionBank.length)*100.0;
    }

    private void updateButtonEnablement() {
        if(mQuestionBank[mCurrentIndex].isAnswered())
        {
            setAnswerButtonsEnablement(false);
        }
        else {
            setAnswerButtonsEnablement(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putSerializable(KEY_QUESTIONS, mQuestionBank);
        savedInstanceState.putInt(KEY_COUNT_CHEATED, mCountCheated);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        Boolean isCheater = mQuestionBank[mCurrentIndex].isCheated();

        if (isCheater) {
            messageResId = R.string.judgment_toast;
        } else {

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mQuestionBank[mCurrentIndex].setAnsweredCorrect(true);
            } else {
                messageResId = R.string.incorrect_toast;
                mQuestionBank[mCurrentIndex].setAnsweredCorrect(false);
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void setAnswerButtonsEnablement(boolean isEnabled) {
        mTrueButton.setEnabled(isEnabled);
        mFalseButton.setEnabled(isEnabled);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            Boolean isCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheated(isCheater);
            mCountCheated = isCheater ? mCountCheated + 1 : mCountCheated;
            handleCheatOccurences();
        }
    }

    private void handleCheatOccurences() {
        if(mCountCheated == 3){
            mCheatButton.setEnabled(false);
        }
        mCountCheatedTextView.setText("You already cheated " + Integer.toString(mCountCheated) + " times!");
    }
}
