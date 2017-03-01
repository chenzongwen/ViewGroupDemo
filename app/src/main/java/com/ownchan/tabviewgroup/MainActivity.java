package com.ownchan.tabviewgroup;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ownchan.tabviewgroup.view.TabsViewGroup;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TabsViewGroup tabsViewGroup = (TabsViewGroup) findViewById(R.id.tabs_view);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        String[] tabs = new String[]{"世预赛欧洲区",
                "世预赛南美区", "五人世界杯", "意甲", "美国大联盟", "乌超", "英冠", "国际冠军杯", "巴西圣保罗州杯", "巴西杯"};
        for (int i = 0; i < tabs.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(tabs[i]);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.tabs_bg));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(10, 0, 10, 0);
            textView.setTextSize(16);
            tabsViewGroup.addView(textView, params);
        }
    }
}
