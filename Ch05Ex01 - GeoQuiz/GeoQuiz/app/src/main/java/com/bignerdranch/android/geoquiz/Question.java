package com.bignerdranch.android.geoquiz;

import java.io.Serializable;

public class Question implements Serializable{

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsAnswered;
    private boolean mIsAnsweredCorrect;
    private boolean mIsCheated;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mIsAnswered = false;
        mIsAnsweredCorrect = false;
        mIsCheated = false;
    }

    public boolean isAnsweredCorrect() {
        return mIsAnsweredCorrect;
    }

    public void setAnsweredCorrect(boolean answeredCorrect) {
        mIsAnsweredCorrect = answeredCorrect;
    }

    public boolean isCheated() {
        return mIsCheated;
    }

    public void setCheated(boolean cheated) {
        mIsCheated = cheated;
    }

    public boolean isAnswered() {
        return mIsAnswered;
    }

    public void setIsAnswered(boolean answered) {
        mIsAnswered = answered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
