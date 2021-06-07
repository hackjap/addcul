package com.example.addcul.activity.kculture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.addcul.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class KakaoActivity extends YouTubeBaseActivity {



    YouTubePlayerView playerView;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao);

        findViewById(R.id.img_kakao1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
                startActivity(intent);
            }
        });

        playerView  = (YouTubePlayerView)findViewById(R.id.youtubeView);

        /*
         * 동영상
         */
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("X348VNllZc4");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView.initialize("AIzaSyBtin4Wne1eccgkKPBq0ee1ZzsLrU8tExo",listener);
            }
        });

    }

}
