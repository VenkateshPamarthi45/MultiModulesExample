package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;

/**
 * @author venkateshpamarthi
 */
class JokeRepo {
    void getJokeDetails(final MutableLiveData<LiveDataResource<String>> jokeResponseLiveData){
        jokeResponseLiveData.setValue(new LiveDataResource<String>(LiveDataResource.Status.LOADING, null, null));
        EndPointsAsyncTask endPointsAsyncTask = new EndPointsAsyncTask();
        endPointsAsyncTask.setListener(new EndPointsAsyncTask.JokeGetTaskListener() {
            @Override
            public void onComplete(LiveDataResource liveDataResource) {
                jokeResponseLiveData.setValue(liveDataResource);
            }
        }).execute();
    }
}
