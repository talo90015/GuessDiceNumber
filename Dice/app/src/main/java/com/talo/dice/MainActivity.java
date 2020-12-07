package com.talo.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private Button button;

    private static class StaticHandler extends Handler {
        private final WeakReference<MainActivity> aMainactivity;
        public StaticHandler(MainActivity activity){
            aMainactivity = new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message message){
            MainActivity activity = aMainactivity.get();
            if (activity == null) return;

            int iRand = (int)(Math.random() * 6 + 1);

            String s = activity.getString(R.string.dice_result);
            activity.textView.setText(s + iRand);
            switch (iRand){
                case 1:
                    activity.imageView.setImageResource(R.drawable.num1);
                    break;
                case 2:
                    activity.imageView.setImageResource(R.drawable.num2);
                    break;
                case 3:
                    activity.imageView.setImageResource(R.drawable.num3);
                    break;
                case 4:
                    activity.imageView.setImageResource(R.drawable.num4);
                    break;
                case 5:
                    activity.imageView.setImageResource(R.drawable.num5);
                    break;
                case 6:
                    activity.imageView.setImageResource(R.drawable.num6);
                    break;
            }
        }

    }
    public final StaticHandler ahandler = new StaticHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageDice);
        textView = findViewById(R.id.showDiceNumber);
        button = findViewById(R.id.RollDice);
        button.setOnClickListener(btnClick);

    }
    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = getString(R.string.dice_result);
            textView.setText(s);

            Resources res = getResources();
            final AnimationDrawable animationDrawable = (AnimationDrawable)res.getDrawable(R.drawable.dice);
            imageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    animationDrawable.stop();
                    ahandler.sendMessage(ahandler.obtainMessage());
                }
            }).start();
        }
    };
}