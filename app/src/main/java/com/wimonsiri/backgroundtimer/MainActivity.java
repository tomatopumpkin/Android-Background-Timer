package com.wimonsiri.backgroundtimer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {
    // counter of time since app started, a background task
    private long mStartTime = 0L;
    private TextView showMilli,showSecond,showMinute;
    //Handler to handle the message to the timer task
    private Handler mHandler = new Handler(Looper.myLooper());
    private Button startBtn, stopBtn;
    private boolean time_start = false;
    @Override
    public void onCreate(Bundle savedInstanceStace) {
        super.onCreate( savedInstanceStace) ;
        setContentView( R.layout.activity_main );
        showMilli = (TextView) findViewById( R.id.showMilli);
        showSecond = (TextView) findViewById( R.id.showSecond);
        showMinute = (TextView) findViewById( R.id.showMinute);
        showMilli.setText("0");
        showSecond.setText("0");
        showMinute.setText("00:00");
        startBtn = (Button) findViewById(R.id.button_start);
        startBtn.setOnClickListener(this);
        stopBtn = (Button) findViewById(R.id.button_stop);
        stopBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_start) {
            mStartTime = SystemClock.uptimeMillis();
            mHandler.postDelayed(mUpdateTimeTask, 1000);
            time_start = true;
            showMilli.setText("0");
            showSecond.setText("0");
            showMinute.setText("00:00");
            Toast.makeText(this,"Start Time",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.button_stop) {
            time_start = false;
            mHandler.removeCallbacks(mUpdateTimeTask);
            Toast.makeText(this,"Stop Time",Toast.LENGTH_SHORT).show();
        }
    }
    private Runnable mUpdateTimeTask = new Runnable () {
        public void run() {
            if (time_start)
                mHandler.postDelayed( this, 1000);
            final long nowTime = SystemClock.uptimeMillis();
            final long startTime = mStartTime;
            long millis = ( nowTime - startTime) ;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            showMilli.setText( Long.toString(millis) );
            showSecond.setText( Long.toString(seconds) );
            showMinute.setText( String.format("%02d",minutes) + ":" +

                    String.format("%02d",seconds));

        }
    };
}