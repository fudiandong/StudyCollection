package com.fudd.calendarstudy.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fudd.main.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fudd on 2017-03-22 20:43.
 * Email: 5036175@qq.com
 * QQ: 5036175
 * Description:
 */
public class CalendarActivity extends AppCompatActivity implements OnDateSelectedListener {

    @BindView(R.id.mcv)
    MaterialCalendarView materialCalendarView;
    @BindView(R.id.tv_now_date)
    TextView tv_now_date;
    @BindView(R.id.tv_pre_date)
    TextView tv_pre_date;

    List<CalendarDay> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        // 获取日历时间范围 ~~~~  当前年份日历
        Calendar calendarMin = Calendar.getInstance();
        calendarMin.set(calendarMin.get(Calendar.YEAR),Calendar.JANUARY,1);
        Calendar calendarMax = Calendar.getInstance();
        calendarMax.set(calendarMax.get(Calendar.YEAR),Calendar.DECEMBER,31);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(calendarMin)
                .setMaximumDate(calendarMax)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setOnDateChangedListener(this);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        List<CalendarDay> list =  materialCalendarView.getSelectedDates();
//        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String y = "";
        for (int i = 0; i < list.size(); i++){
            y += dateFormat.format(list.get(i).getDate())+ " ";
        }
        tv_now_date.setText(y);
    }

    public void btnOnClick(View view){
        switch (view.getId()){
            case R.id.btn_confirm:
                // 1、保存现在选定的日期
                tv_pre_date.setText(tv_now_date.getText());
                // 2、改变mcv的画笔颜色
                list = materialCalendarView.getSelectedDates();
                materialCalendarView.addDecorator(new DatesDecorator(1));

                materialCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryDark));
                // 3、设置已选日期为不可点击
                break;
            case R.id.btn_rest:
                list = materialCalendarView.getSelectedDates();
                materialCalendarView.addDecorator(new DatesDecorator(2));
                break;
        }
    }

    private class DatesDecorator implements DayViewDecorator{

        private final List<CalendarDay> days;
        private final Drawable backgroundDrawable;

        public DatesDecorator(int x) {

            days = list;
            if (x == 1){
                backgroundDrawable = getResources().getDrawable(R.drawable.finished);
            } else if (x ==2){
                backgroundDrawable = getResources().getDrawable(R.drawable.rest1);
            } else {
                backgroundDrawable = getResources().getDrawable(R.drawable.wancheng);
            }

        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            for (int i = 0; i<days.size();i++){
                if (days.get(i).equals(day)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void decorate(DayViewFacade view) {
            //  设为禁用状态
            view.setDaysDisabled(true);
            view.setSelectionDrawable(backgroundDrawable);
            view.setBackgroundDrawable(backgroundDrawable);
        }
    }

}
