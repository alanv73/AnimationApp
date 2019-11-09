package edu.southhills.animationapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TransitionActivity extends AppCompatActivity {

    private Boolean imageVisible = true;
    private Boolean firstRun = true;
    private Boolean sceneFlag = true;
    private final int FADE_IN = 1;
    private final int FADE_OUT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_transition);

    }

    public void startButton(View v){
        if(sceneFlag){
            scene_a();
            if(!firstRun){
                fade(FADE_OUT);
            }
        }else {
            scene_b();
            fade(FADE_IN);
        }
        sceneFlag = !sceneFlag;
        imageVisible = true;
    }

    public void backButton(View v){
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void imageClick(View v){
        if (imageVisible){
            fadeImage(FADE_OUT);
        } else {
            fadeImage(FADE_IN);
        }
        imageVisible = !imageVisible;
    }

    private void fadeImage(int direction){
        float start = 1f;
        float end = 0;

        if(direction == FADE_IN){
            start = 0;
            end = 1f;
        }

        ImageView imageView = findViewById(R.id.imageView);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imageView, "alpha", start, end);

        fadeOut.setDuration(1500);
        fadeOut.setRepeatCount(0);
        fadeOut.start();

    }

    private void fade(int direction){
        ViewGroup root = findViewById(R.id.second_root);

        Transition fade = new Fade(direction);

        ImageView imageView = findViewById(R.id.imageView);
        fade.addTarget(imageView);
        fade.setDuration(2000);

        TransitionManager.beginDelayedTransition(root, fade);


        imageView.setVisibility(direction == FADE_IN ? View.VISIBLE : View.INVISIBLE);
    }

    private void scene_a(){
        ViewGroup sceneContainer = findViewById(R.id.scene_root);
        Scene myScene = Scene.getSceneForLayout(sceneContainer, R.layout.a_scene, this);

        Transition transition = new Slide();

        if(!firstRun){
            transition = new ChangeBounds();
            transition.setDuration(1500);
            transition.setInterpolator(new OvershootInterpolator());
        }

        TransitionManager.go(myScene, transition);
        if(firstRun){
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setVisibility(View.INVISIBLE);
        }
        firstRun = false;
    }

    private void scene_b(){
        ViewGroup sceneContainer = findViewById(R.id.scene_root);
        Scene newScene = Scene.getSceneForLayout(sceneContainer, R.layout.b_scene, this);

        Transition bounce = new ChangeBounds();
        bounce.setDuration(1500);
        bounce.setInterpolator(new BounceInterpolator());

        TransitionManager.go(newScene, bounce);
    }
}
