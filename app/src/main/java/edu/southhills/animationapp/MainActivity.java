package edu.southhills.animationapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);

    }

    public void buttonClick(View v){
        sceneTransition();
    }

    public void sceneClick(View v){ changeActivity(); }

    public void sceneTransition(){
        Transition customTransition = new CustomTransition();
        Transition slide = new Slide(Gravity.TOP);
        customTransition.setDuration(1500);
        slide.setDuration(2000);

        ViewGroup root = findViewById(R.id.scene_root);

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(customTransition);
        transitionSet.addTransition(slide);
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.addTarget(R.id.textView);


        TransitionManager.beginDelayedTransition(root, transitionSet);

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.btnHelloGoodbye);

        if(textView.getVisibility() == View.VISIBLE) {
            button.setText("Hello");
            textView.setText("GoodBye World!");
            textView.setTextColor(Color.BLACK);
            textView.setVisibility(View.INVISIBLE);
        } else {
            button.setText("GoodBye");
            textView.setText("Hello World!");
            textView.setTextColor(Color.RED);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void changeActivity(){
        Intent activityIntent = new Intent(this, TransitionActivity.class);
        startActivity(activityIntent);
    }
}
