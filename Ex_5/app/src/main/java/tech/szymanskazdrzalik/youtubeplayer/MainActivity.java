package tech.szymanskazdrzalik.youtubeplayer;

import android.os.Bundle;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private final YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo("s0-f5RncxcA");
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.youtube_view);

    }

    public void playButtonListener(View v){
        youTubePlayerView.initialize("", onInitializedListener);
    }

}