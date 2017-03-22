package com.fudd.main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fudd.calendarstudy.activity.CalendarActivity;
import com.fudd.timetracker.activity.TimeTrackerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_timetracker)
    Button btn_TimeTracker;
    @BindView(R.id.btn_calendar)
    Button btn_CalendarStudy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initListener();
    }

    private void initListener() {
        btn_TimeTracker.setOnClickListener(this);
        btn_CalendarStudy.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_timetracker:
                startActivity(new Intent(this, TimeTrackerActivity.class));
                break;
            case R.id.btn_calendar:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            default:
                break;
        }
    }
}
