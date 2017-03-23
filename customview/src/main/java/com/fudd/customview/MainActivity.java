package com.fudd.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fudd.customview.view.MyView;

public class MainActivity extends AppCompatActivity {

    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myView = (MyView) findViewById(R.id.vv);
//        new Thread(myView).start();
    }
}
