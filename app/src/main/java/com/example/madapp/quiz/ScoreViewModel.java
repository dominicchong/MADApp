package com.example.madapp.quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {
    private final MutableLiveData<Integer> quizScore = new MutableLiveData<>();

    public LiveData<Integer> getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int score) {
        quizScore.setValue(score);
    }
}
