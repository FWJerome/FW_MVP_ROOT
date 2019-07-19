package com.fwkj.fw_mvp_root.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fwkj.fw_mvp_root.R;
import com.jerome.ftablayout.FTabEntity;
import com.jerome.ftablayout.FTabView;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {
    private FTabView tabView;
    private FTabView tabView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();
        List<TabEntity> s = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            s.add(new TabEntity("第" + i + "个"));
        }
        new FTabView.Builder<TabEntity>()
                .showSpaceDp2Px(3)
                .showSpaceColor(Color.parseColor("#ff0000"))
                .showItems(2)
                .setDatas(s)
                .build(tabView);

        new FTabView.Builder<TabEntity>()
                .setDatas(s)
                .build(tabView1);
    }

    private void initView() {
        tabView = (FTabView) findViewById(R.id.tabView);
        tabView1 = (FTabView) findViewById(R.id.tabView1);
    }

    class TabEntity extends FTabEntity {

        public TabEntity(String name) {
            super(name);
        }
    }
}
