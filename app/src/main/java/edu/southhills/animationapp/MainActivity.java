package edu.southhills.animationapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Boolean colorFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);

        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

    }

    public void worldClick(View v){
        sceneTransition();
    }

    public void sceneClick(View v){ changeActivity(); }

    public void sceneTransition(){
        Transition customTransition = new CustomTransition();
        Transition slide = new Slide(Gravity.TOP);
        customTransition.setDuration(3000);
        slide.setDuration(4000);

        ViewGroup root = findViewById(R.id.scene_root);

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(customTransition);
        transitionSet.addTransition(slide);
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.addTarget(R.id.textView);


        TransitionManager.beginDelayedTransition(root, transitionSet);

        TextView textView = findViewById(R.id.textView);

        if(textView.getVisibility() == View.VISIBLE) {
            textView.setText("GoodBye World!");
            textView.setTextColor(Color.BLACK);
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setText("Hello World!");
            textView.setTextColor(Color.RED);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void changeActivity(){
        Intent activityIntent = new Intent(this, TransitionActivity.class);
        Bundle animationBundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(activityIntent, animationBundle);
    }
}
