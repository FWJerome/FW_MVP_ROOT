package com.fwkj.fw_mvp_root.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fwkj.fw_mvp_root.R;
import com.fwkj.fw_root_library.utils.ImageLoader;
import com.jerome.ftablayout.FTabEntity;
import com.jerome.ftablayout.FTabView;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {
    private FTabView<TabEntity> tabView;
    private FTabView<TabEntity> tabView1;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();
        List<TabEntity> s = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            s.add(new TabEntity("第" + i + "个"));
        }

        new ImageLoader.Builder(this)
                .errImage(R.mipmap.ic_launcher)
                .isCircle(true)
                .tagetImageUrl("http://pic1.win4000.com/wallpaper/2018-09-06/5b90cee094f3e.jpg")
                .placeImage(R.mipmap.img_lineindicate)
                .setTagetImageView(img)
                .build()
                .create();

        new FTabView.Builder<>(tabView)
                //间隔线宽度
                .showSpaceDp2Px(3)
                //间隔线颜色
                .showSpaceColor(Color.parseColor("#ff0000"))
                //上下的间距
                .spaceTopAndBottom(10)
                //展示的条数
                .showItems(6)
                //数据
                .setDatas(s)
                .addOnClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ToastUtils.showShort("asd");
                    }
                })
                .build();

        new FTabView.Builder<>(tabView1)
                .setDatas(s)
                .build();
    }

    private void initView() {
        tabView = (FTabView) findViewById(R.id.tabView);
        tabView1 = (FTabView) findViewById(R.id.tabView1);
        img = (ImageView) findViewById(R.id.img);
    }

    class TabEntity extends FTabEntity {

        public TabEntity(String name) {
            super(name);
        }
    }
}
