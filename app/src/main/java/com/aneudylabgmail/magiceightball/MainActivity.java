package com.aneudylabgmail.magiceightball;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aneudylabgmail.magiceightball.domain.MagicEightBall;
import com.aneudylabgmail.magiceightball.helpers.ShakeableActivity;


public class MainActivity extends ShakeableActivity {

    private static final int SHAKES_NEEDED_FOR_ANSWER = 3;
    private static final int SECONDS_TO_SHOW_ANSWER = 5;
    private static final int PROGRESS_BAR_REFRESH_RATE = 100/3;

    private FrameLayout answerFrame;
    private FrameLayout eightBallFrame;
    private TextView answerTextView;
    private ProgressBar progressBar;

    private boolean showingAnswer = false;

    private MagicEightBall magicEightBall;

    public MainActivity(MagicEightBall magicEightBall) {
        this.magicEightBall = magicEightBall;
    }

    public MainActivity(){
        // Poor man's DI.
        magicEightBall = new MagicEightBall();
    }
    private void initComponents(){
        answerFrame = (FrameLayout) findViewById(R.id.answer_frame);
        eightBallFrame = (FrameLayout) findViewById(R.id.eight_ball_frame);
        answerTextView = (TextView) findViewById(R.id.answer);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        answerFrame.setVisibility(View.INVISIBLE);
        progressBar.setMax(PROGRESS_BAR_REFRESH_RATE*SECONDS_TO_SHOW_ANSWER);
    }

    private void showAnswer(String answer){
        eightBallFrame.setVisibility(View.INVISIBLE);
        answerFrame.setVisibility(View.VISIBLE);
        answerFrame.invalidate();

        answerTextView.setText(answer);
        progressBar.setProgress(0);
        showingAnswer = true;
        new Thread(new ShowAnswerTask()).start();
    }

    private void hideAnswer(){
        eightBallFrame.setVisibility(View.VISIBLE);
        answerFrame.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        initComponents();

    }

    protected void handleShakeEvent(int count) {
        if (!showingAnswer && count == SHAKES_NEEDED_FOR_ANSWER ){
            showAnswer(magicEightBall.getAnswer());
        }
    }

    private class ShowAnswerTask implements Runnable {
        @Override
        public void run() {
            int iterations = SECONDS_TO_SHOW_ANSWER * PROGRESS_BAR_REFRESH_RATE;
            for (int i = 0; i <= iterations; i++) {
                try {
                    Thread.sleep(SECONDS_TO_SHOW_ANSWER * 1000/iterations);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.setProgress(i);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideAnswer();
                    showingAnswer = false;
                }
            });
        }
    }
}