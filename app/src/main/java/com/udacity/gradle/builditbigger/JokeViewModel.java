package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class JokeViewModel extends ViewModel {
    private MutableLiveData<LiveDataResource<String>> jokeResponseLiveData;

    public MutableLiveData<LiveDataResource<String>> getJokeResponseLiveData(JokeRepo jokeRepo) {
        if(jokeResponseLiveData == null){
            jokeResponseLiveData = new MutableLiveData<>();
            jokeRepo.getJokeDetails(jokeResponseLiveData);
        }
        return jokeResponseLiveData;
    }
}
