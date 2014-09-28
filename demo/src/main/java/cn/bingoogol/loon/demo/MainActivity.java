package cn.bingoogol.loon.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import cn.bingoogol.loon.R;
import cn.bingoogol.loon.library.Loon;
import cn.bingoogol.loon.library.LoonLayout;

@LoonLayout(id = R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loon.injectView2Activity(this);
    }
}