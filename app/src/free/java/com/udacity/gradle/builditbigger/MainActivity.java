package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.pamarthi.venkatesh.commonviews.JokeDetailActivity;


/**
 * @author venkateshpamarthi
 */
public class MainActivity extends AppCompatActivity {

    JokeViewModel viewModel;
    ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(JokeViewModel.class);
        progressBar = findViewById(R.id.progressBar);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                viewModel.getJokeResponseLiveData(new JokeRepo()).observe(MainActivity.this, jokeObserver());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        //
    }

    @NonNull
    private Observer<LiveDataResource<String>> jokeObserver() {
        return new Observer<LiveDataResource<String>>() {
            @Override
            public void onChanged(@Nullable LiveDataResource<String> stringLiveDataResource) {
                if(stringLiveDataResource != null){
                    if(stringLiveDataResource.status == LiveDataResource.Status.SUCCESS){
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(MainActivity.this, JokeDetailActivity.class);
                        intent.putExtra("joke",stringLiveDataResource.data);
                        startActivity(intent);
                    }else if(stringLiveDataResource.status == LiveDataResource.Status.LOADING){
                        progressBar.setVisibility(View.VISIBLE);
                    }else if(stringLiveDataResource.status == LiveDataResource.Status.ERROR){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, stringLiveDataResource.data, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }


}
