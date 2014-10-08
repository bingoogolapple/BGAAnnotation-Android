package cn.bingoogolapple.loon.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import cn.bingoogolapple.loon.library.Loon;
import cn.bingoogolapple.loon.library.LoonLayout;

@LoonLayout(id = R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loon.injectView2Activity(this);
    }
}