package com.bagirapp.harrypotterquiz;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    private String mQuestion;
    private String mRightAnswer;
    private ArrayList<String> mAllAnswer;
    private String userAnswer;

    public Question(){

    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Question(String mQuestion, String mRightAnswer, String helytelen1, String helytelen2, String helytelen3){
        this.mQuestion = mQuestion;
        this.mRightAnswer = mRightAnswer;
        ArrayList<String> answers = new ArrayList<>();
        answers.add(mRightAnswer);
        answers.add(helytelen1);
        answers.add(helytelen2);
        answers.add(helytelen3);
        Collections.shuffle(answers);
        this.mAllAnswer = answers;


    }

    public String getmQuestion() {
        return mQuestion;
    }

    public String getmRightAnswer() {
        return mRightAnswer;
    }

    public ArrayList<String> getmAllAnswer() {
        return mAllAnswer;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public void setmRightAnswer(String mRightAnswer) {
        this.mRightAnswer = mRightAnswer;
    }

    public void setmAllAnswer(ArrayList<String> mAllAnswer) {
        this.mAllAnswer = mAllAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mRightAnswer='" + mRightAnswer + '\'' +
                ", mAllAnswer=" + mAllAnswer +
                '}';
    }
}
