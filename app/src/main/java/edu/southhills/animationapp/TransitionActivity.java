package edu.southhills.animationapp;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TransitionActivity extends AppCompatActivity {

    private Boolean opacity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_transition);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(view -> fade());

        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
    }

    public void backButton(View v){
        Intent homeIntent = new Intent(this, MainActivity.class);
        Bundle animationBundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(homeIntent, animationBundle);
    }

    public void startTransition(){
        ViewGroup thisRoot = (ViewGroup)findViewById(R.id.second_root);

        TransitionManager.beginDelayedTransition(thisRoot, new TransitionSet()
                .addTransition(new ChangeBounds())
                .addTransition(new ChangeImageTransform()));

        ImageView imageView = findViewById(R.id.imageView);

        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        if(params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        imageView.setLayoutParams(params);

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    public void fade(){
        float start = 1f;
        float end = 0;

        if(opacity){
            start = 0;
            end = 1f;
            opacity = false;
        }

        ImageView imageView = findViewById(R.id.imageView);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imageView, "alpha", start, end);
        opacity = !opacity;

        fadeOut.setDuration(3000);
        fadeOut.setRepeatCount(0);
        fadeOut.start();


    }

    public void sceneButton(View v){ scene_b(); }

    private void scene_b(){
        ViewGroup sceneContainer = findViewById(R.id.scene_root);

        Scene myScene = Scene.getSceneForLayout(sceneContainer, R.layout.b_scene, this);

        Transition slide = new Slide();

        TransitionManager.go(myScene, slide);
    }
}
