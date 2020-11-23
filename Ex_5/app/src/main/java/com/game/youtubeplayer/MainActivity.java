package com.game.youtubeplayer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {

    private String[][] selectedFilms;
    private int selected;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer player;
    private Boolean wasReleased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        youTubePlayerView = findViewById(R.id.youtube_view);
        selectedFilms = new String[][]{new String[]{"s0-f5RncxcA", "n_1XpKHWMU0", "UWLr2va3hu0", "k2qgadSvNyU", "Nj2U6rhnucI"},
                new String[]{"Take you dancing", "Hey DJ", "Hey Ma", "New Rules", "Break My Heart"}};
        selected = 0;
        wasReleased = true;
    }

    private final YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo(selectedFilms[0][selected]);
            player = youTubePlayer;
            wasReleased = false;
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            Toast.makeText(MainActivity.this, "Error on initialization", Toast.LENGTH_SHORT).show();
        }

    };

    public void playButtonListener(View v) {
        if (wasReleased) {
            youTubePlayerView.initialize("AIzaSyClovOxoHq1r2yX6Hn1TCPVGhKpgeYCzwU", onInitializedListener);
        } else {
            player.play();
        }
    }

    public void showAlterDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("AlertDialog");
        alertDialog.setSingleChoiceItems(selectedFilms[1], 0, (dialog, which) -> {
            selected = which;
            dialog.dismiss();
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public void chooseButtonListener(View v){
        showAlterDialog();
    }

    public void pauseButtonListener(View v) {
        if (player != null) {
            player.pause();
        }
    }

    public void stopButtonListener(View v) {
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            if (!wasReleased) player.release();
            wasReleased = true;
        }
    }

    public void nextButtonListener(View v) {
        if (selected < selectedFilms[0].length - 1) {
            selected += 1;
        } else {
            selected = 0;
        }
        if (!wasReleased) releasePlayer();
        youTubePlayerView.initialize("AIzaSyClovOxoHq1r2yX6Hn1TCPVGhKpgeYCzwU", onInitializedListener);
    }

    public void previousButtonListener(View v) {
        if (selected > 0) {
            selected -= 1;
        } else {
            selected = selectedFilms[0].length - 1;
        }
        if (!wasReleased) releasePlayer();
        youTubePlayerView.initialize("AIzaSyClovOxoHq1r2yX6Hn1TCPVGhKpgeYCzwU", onInitializedListener);
    }

}