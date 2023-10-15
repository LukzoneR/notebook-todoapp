package ch.walica.sqlite_szablon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class password extends AppCompatActivity {

    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;
    private NumberPicker numberPicker3;
    private Button btn1;
    private ImageView img;
    private int count1, count2, count3;
    private String data;
    private char[] charArray = new char[3];
    private int num1, num2, num3;
    private TextView tvI;

    public static final String SHARED = "shared";
    public static final String SHARED_KEY = "shared_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(android.R.color.black));
        }

        getSupportActionBar().hide();


        //references
        numberPicker1 = findViewById(R.id.numberPicker1);
        numberPicker2 = findViewById(R.id.numberPicker2);
        numberPicker3 = findViewById(R.id.numberPicker3);
        btn1 = findViewById(R.id.btn1);
        img = findViewById(R.id.img);
        tvI = findViewById(R.id.tvI);


        //NumberPicker values
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(9);

        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);

        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);

        //new code value

        loadName();

        for(int i = 0; i < 3; i++){
            charArray[i] = data.charAt(i);
        }

        num1 = Integer.parseInt(String.valueOf(charArray[0]));
        num2 = Integer.parseInt(String.valueOf(charArray[1]));
        num3 = Integer.parseInt(String.valueOf(charArray[2]));

        //function execution
        npChange();


        //MediaPlayer
        MediaPlayer lockOpen = MediaPlayer.create(this, R.raw.padlockopen);
        MediaPlayer lockClose = MediaPlayer.create(this, R.raw.error);


        //confirm button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count1 == num1 && count2 == num2 && count3 == num3){
                    lockOpen.start();
                    img.setImageResource(R.drawable.greenlockeropen);

                    Handler handler = new Handler(Looper.getMainLooper());
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(password.this, MainActivity.class);
                            startActivity(intent);
                        }
                    };
                    handler.postDelayed(runnable, 1000);

                }else{
                    Toast.makeText(password.this, R.string.code, Toast.LENGTH_SHORT).show();
                    lockClose.start();
                    final Animation shake_animation = AnimationUtils.loadAnimation(password.this, R.anim.shake_animation);
                    img.startAnimation(shake_animation);
                }
            }
        });

    }

    //back button implementation, which close app
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }

    //declaring values to counters
    private void npChange(){
        //Media Player
        MediaPlayer mp = MediaPlayer.create(this, R.raw.clap);

        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                mp.start();
                count1 = i1;
                check(count1,count2,count3);
            }
        });

        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                count2 = i1;
                mp.start();
                check(count1,count2,count3);
            }
        });

        numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                count3 = i1;
                mp.start();
                check(count1,count2,count3);
            }

        });

    }

    public void check(int var1,int var2,int var3){
        if(var1 == num1 && var2 == num2 && var3 == num3){
            img.setImageResource(R.drawable.greenlocker);
        }else{
            img.setImageResource(R.drawable.redlocker);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("key",count1);
        outState.putInt("key2",count2);
        outState.putInt("key3",count3);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        count1 = savedInstanceState.getInt("key", 0);
        count2 = savedInstanceState.getInt("key2", 0);
        count3 = savedInstanceState.getInt("key3", 0);


        numberPicker1.setValue(count1);
        numberPicker2.setValue(count2);
        numberPicker3.setValue(count3);

    }


    public void loadName(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED, MODE_PRIVATE);
        data = sharedPreferences.getString(SHARED_KEY, "123");
    }

}