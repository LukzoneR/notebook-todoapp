package ch.walica.sqlite_szablon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class opener extends AppCompatActivity {

    private ImageView img;
    private TextView txt, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opener);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(android.R.color.black));
        }

        getSupportActionBar().hide();

        img = findViewById(R.id.imageView);
        data = findViewById(R.id.data);
        txt = findViewById(R.id.txt);

        //date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        data.setText(currentDate);


        txt.setText("");
        animation();
        nextActivity();


    }

    private void animation(){
        Animation animFadeIn = AnimationUtils.loadAnimation(opener.this, R.anim.anim1);
        Animation animFadeIn2 = AnimationUtils.loadAnimation(opener.this, R.anim.anim2);
        animFadeIn.reset();
        img.clearAnimation();
        img.startAnimation(animFadeIn);

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                txt.setText(R.string.open_title);
                txt.clearAnimation();
                txt.startAnimation(animFadeIn2);
            }
        };
        handler.postDelayed(runnable, 2000);

    }

    private void nextActivity(){

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(opener.this, password.class);
                startActivity(intent);
                overridePendingTransition(R.anim.up,R.anim.down);
            }
        };
        handler.postDelayed(runnable, 3500);
    }
}