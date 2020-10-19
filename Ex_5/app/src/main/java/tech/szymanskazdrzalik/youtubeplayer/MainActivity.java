package tech.szymanskazdrzalik.youtubeplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private Boolean wasReleased = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.youtube_view);
        selectedFilms = new String[][]{new String[]{"s0-f5RncxcA", "n_1XpKHWMU0", "UWLr2va3hu0"}, new String[]{"Take you dancing", "Hey DJ", "Hey Ma"}};
        selected = 0;
    }

    private final YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            try {
                youTubePlayer.loadVideo(selectedFilms[0][selected]);
                player = youTubePlayer;
                wasReleased = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        }

    };

    public void playButtonListener(View v) {
        if (wasReleased) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("AlertDialog");
            int checkedItem = 1;
            alertDialog.setSingleChoiceItems(selectedFilms[1], checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selected = which;
                    youTubePlayerView.initialize("", onInitializedListener);
                    dialog.dismiss();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        } else {
            player.play();
        }
    }

    public void pauseButtonListener(View v) {
        player.pause();
    }

    public void stopButtonListener(View v) {
        player.release();
        wasReleased = true;
    }
}