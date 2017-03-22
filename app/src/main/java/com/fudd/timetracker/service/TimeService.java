package com.fudd.timetracker.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;

import com.fudd.main.R;
import com.fudd.timetracker.activity.TimeTrackerActivity;

/**
 * Created by fudd-office on 2017-3-16 11:12.
 * Email: 5036175@qq.com
 * QQ: 5036175
 * Description:
 */

public class TimeService extends Service {
    private static final String TAG = "TimeService";
    private static final int MSG_ONE = 0;
    private long start = 0;
    private long time = 0;

    private MyBinder myBinder = new MyBinder();
    private Notification notification;
    private NotificationManager notificationManager;

    public class MyBinder extends Binder{
        public TimeService getService(){
            return TimeService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG,"onCreate");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG,"onStartCommand "+ System.currentTimeMillis());
        start = System.currentTimeMillis();

        showNotification();
        handler.removeMessages(MSG_ONE);
        handler.sendEmptyMessage(MSG_ONE);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.w(TAG,"onBind");
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(0);
        handler.removeMessages(MSG_ONE);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //  handler 的中途 就是 更新2个地方的 计时
            long current = System.currentTimeMillis();//1489646000154
            time += current - start;
            start = current;
            // 更新时间
            updateTime();
            sendEmptyMessageDelayed(MSG_ONE,250);
        }
    };

    private void updateTime() {
        Intent intent = new Intent(TimeTrackerActivity.ACTION_TIME_UPDATE);
        intent.putExtra("time",time);
        sendBroadcast(intent);
        updateNotification(time);
    }



    // 判断是否在计时
    public boolean isRunning() {
        return handler.hasMessages(MSG_ONE);
    }
    // 停止计时
    public void stopTimer() {
        handler.removeMessages(MSG_ONE);
        stopSelf();
        notificationManager.cancel(0);
    }

    public void resetTime() {
        stopTimer();
        finishedTimer(time);
        time = 0;
    }

    private void finishedTimer(long time) {
        Intent intent = new Intent(TimeTrackerActivity.ACTION_TIME_FINISHED);
        intent.putExtra("time",time);
        sendBroadcast(intent);
    }

    private void showNotification() {
        notification = new Notification.Builder(getApplicationContext()).setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .getNotification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        startForeground(0,notification);
    }

    private void updateNotification(long time) {
        Intent intent = new Intent(this,TimeTrackerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(DateUtils.formatElapsedTime(time/1000))
                .setContentTitle("定时器")
                .setContentIntent(pendingIntent)
                .getNotification();
        notificationManager.notify(0,notification);


    }
}
