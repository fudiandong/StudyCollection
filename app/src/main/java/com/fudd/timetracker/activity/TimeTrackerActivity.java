package com.fudd.timetracker.activity;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fudd.main.R;
import com.fudd.timetracker.adapter.TimeListAdapter;
import com.fudd.timetracker.fragment.ConfirmDialogFragment;
import com.fudd.timetracker.service.TimeService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fudd-office on 2017-3-16 10:02.
 * Email: 5036175@qq.com
 * QQ: 5036175
 * Description:
 */

public class TimeTrackerActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    @BindView(R.id.btn_switch)
    Button btn_switch;
    @BindView(R.id.btn_finished)
    Button btn_finished;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.listview)
    ListView listView;

    private TimeService timeService;
    public static final String ACTION_TIME_UPDATE = "timeupdate";
    public static final String ACTION_TIME_FINISHED = "timefinished";
    private TimeListAdapter timeListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetracker);
        ButterKnife.bind(this);
        initListener();

        if (timeListAdapter == null){
            timeListAdapter = new TimeListAdapter(this,0);
        }
        listView.setAdapter(timeListAdapter);

        // 添加 过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TIME_UPDATE);
        filter.addAction(ACTION_TIME_FINISHED);
        registerReceiver(receiver,filter);


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timetracker,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_all:
                showDialogFragment();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent(this,TimeService.class),this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeService != null){
            unbindService(this);
            timeService= null;
        }
        if (receiver != null){
            unregisterReceiver(receiver);
        }
    }

    private void initListener() {
        btn_switch.setOnClickListener(this);
        btn_finished.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_switch:
                // 开关按钮点击事件
                if (timeService == null){
                    startService(new Intent(this,TimeService.class));
                    btn_switch.setText(R.string.btn_off);
                }else if (!timeService.isRunning()){
                    timeService.startService(new Intent(this,TimeService.class));
                    btn_switch.setText(R.string.btn_off);
                }else {
                    timeService.stopTimer();
                    btn_switch.setText(R.string.btn_on);
                }
                break;
            case R.id.btn_finished:
                // 重置按钮点击事件
                if (timeService != null){
                    timeService.resetTime();
                }
                tv_time.setText(DateUtils.formatElapsedTime(0));
                btn_switch.setText(R.string.btn_on);
                break;
            default:
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        timeService = ((TimeService.MyBinder)service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        timeService = null;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long time = intent.getLongExtra("time",0);
            if (intent.getAction() == ACTION_TIME_UPDATE){
                tv_time.setText(DateUtils.formatElapsedTime(time/1000));
            } else if (intent.getAction() == ACTION_TIME_FINISHED){
                if (time > 0 && timeListAdapter != null){
                    timeListAdapter.add(time/1000);
                }
            }
        }
    };

    private void showDialogFragment() {
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag("dialog") == null){
            ConfirmDialogFragment confirmDialogFragment = ConfirmDialogFragment.instance(timeListAdapter);
            confirmDialogFragment.show(fm,"dialog");
        }
    }

}
