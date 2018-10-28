package com.pamarthi.venkatesh.commonviews;

import android.content.pm.LabeledIntent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.pamarthi.venkatesh.commonviews.databinding.ActivityJokeDetailBinding;

/**
 * @author venkateshpamarthi
 */
public class JokeDetailActivity extends AppCompatActivity {

    ActivityJokeDetailBinding activityJokeDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityJokeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_joke_detail);
        if(getIntent() != null){
            String joke = getIntent().getStringExtra("joke");
            activityJokeDetailBinding.jokeTextView.setText(joke);
            activityJokeDetailBinding.jokeTextView.setVisibility(View.VISIBLE);
            activityJokeDetailBinding.progressBar.setVisibility(View.GONE);
        }
    }
}
