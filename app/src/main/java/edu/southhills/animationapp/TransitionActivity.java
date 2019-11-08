package edu.southhills.animationapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
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

    private Boolean opacity = true;
    private Boolean firstRun = true;
    private Boolean sceneFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_transition);

    }

    public void startButton(View v){
        if(!sceneFlag){
            scene_a();
        }else {
            scene_b();
        }
        sceneFlag = !sceneFlag;
        opacity = true;
    }

    public void backButton(View v){
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void fadeImage(View v){
        float start = 1f;
        float end = 0;

        if(!opacity){
            start = 0;
            end = 1f;
        }
        opacity = !opacity;

        ImageView imageView = findViewById(R.id.imageView);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imageView, "alpha", start, end);

        fadeOut.setDuration(1500);
        fadeOut.setRepeatCount(0);
        fadeOut.start();

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
