package com.example.addcul.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.addcul.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {

    YouTubePlayerView playerView1,playerView2,playerView3,playerView4;
    Button button;

    YouTubePlayer.OnInitializedListener listener1,listener2,listener3,listener4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        playerView1  = (YouTubePlayerView)findViewById(R.id.youtubeView1);
        playerView2  = (YouTubePlayerView)findViewById(R.id.youtubeView2);
        playerView3  = (YouTubePlayerView)findViewById(R.id.youtubeView3);
        playerView4  = (YouTubePlayerView)findViewById(R.id.youtubeView4);



        /*
         * 동영상
         */
        //  라일락- 아이유
        listener1 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("v7bnOxV4jAc");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        // 롤린 - 브레이브걸스
        listener2 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("MthLgPs7oU4");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        // DNA - bts
        listener3 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("MBdVXkSdhwU");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
        // 박원 - 노력
        listener4 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("1hZa60t8wSE");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        playerView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView1.initialize("AIzaSyBtin4Wne1eccgkKPBq0ee1ZzsLrU8tExo",listener1);
            }
        });
        playerView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView2.initialize("AIzaSyBtin4Wne1eccgkKPBq0ee1ZzsLrU8tExo",listener2);
            }
        });
        playerView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView3.initialize("AIzaSyBtin4Wne1eccgkKPBq0ee1ZzsLrU8tExo",listener3);
            }
        });
        playerView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerView4.initialize("AIzaSyBtin4Wne1eccgkKPBq0ee1ZzsLrU8tExo",listener4);
            }
        });
    }

    protected void onPause() {
        super.onPause();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(playerView1, (Object[]) null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onResume", (Class[]) null)
                    .invoke(playerView1, (Object[]) null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



}
