package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * @author venkateshpamarthi
 */
public class EndPointsAsyncTask extends AsyncTask<Void, Void, LiveDataResource<String>> {

    private MyApi myApiService = null;
    private JokeGetTaskListener mListener = null;

    @Override
    protected LiveDataResource<String> doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://android-multi-module.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return new LiveDataResource<>(LiveDataResource.Status.SUCCESS, myApiService.sayHi("Venkatesh").execute().getData(), null);
        } catch (IOException e) {
            return new LiveDataResource<>(LiveDataResource.Status.ERROR, e.getMessage(), null);
        }
    }
     public EndPointsAsyncTask setListener(JokeGetTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onPostExecute(LiveDataResource<String> result) {
        mListener.onComplete(result);
    }
    public interface JokeGetTaskListener {
         void onComplete(LiveDataResource liveDataResource);
    }
}
