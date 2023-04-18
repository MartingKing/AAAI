package com.bsyun.aaai;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bsyun.aaai.utils.CommmonUtil;
import com.bsyun.aaai.utils.DateUtil;
import com.loper7.date_time_picker.DateTimePicker;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

    private DateTimePicker dateTimePicker;
    private TextView textView;
    private RippleBackground mStart;
    private ImageView imageView;
    private Animation rotateAnimation;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateTimePicker = findViewById(R.id.dateTimePicker);
        mStart = findViewById(R.id.rb_start);
        imageView = findViewById(R.id.ib_yu);
        textView = findViewById(R.id.tvDateTimePickerValue);
        dateTimePicker.setLabelText("年", "月", "日", "时", "分");
        dateTimePicker.setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
            @Override
            public void onDateTimeChanged(@Nullable DateTimePicker dateTimePicker, long l) {
                time = l;
                textView.setText("当前选择时间为：" + DateUtil.getDateToString(l));
            }
        });
//        Lunar lunar = Lunar.fromYmdHms(2021, 12, 2, 23, 21, 20);
//        Log.e("TAG", "onCreate: "+ lunar.toFullString());
    }

    public void startPaipan(View view) {
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
        }
        if (mStart.isRippleAnimationRunning()) {
            mStart.stopRippleAnimation();
        }
        startActivity(new Intent(this, PaipanActivity.class).putExtra(CommmonUtil.TIME, time));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
        }
        if (mStart.isRippleAnimationRunning()) {
            mStart.stopRippleAnimation();
        }
    }

    public void startAnimation(View view) {
        mStart.startRippleAnimation();
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        imageView.startAnimation(rotateAnimation);
    }

    public void resetTime(View view) {
        dateTimePicker.setDefaultMillisecond(System.currentTimeMillis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}