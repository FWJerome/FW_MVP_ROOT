package com.fwkj.fw_mvp_root.view;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fwkj.fw_mvp_root.R;
import com.fwkj.fw_mvp_root.component.DaggerMainActivityComponent;
import com.fwkj.fw_mvp_root.component.MainActivityModule;
import com.fwkj.fw_mvp_root.contract.MainActivityContract;
import com.fwkj.fw_mvp_root.presenter.MainActivityPresenter;
import com.fwkj.fw_root_library.BaseActivity;
import com.fwkj.fw_root_library.component.DelegateComponent;
import com.fwkj.fw_root_library.utils.LinearLayoutDecoration;
import com.fwkj.fw_root_library.widget.FTitle;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityContract.view {
    @Inject
    MainActivityPresenter mMainActivityPresenter;
    private RecyclerView mRcy;
    private FTitle mTitle;

    @Override
    public void initView(Bundle savedInstanceState) {
        ImmersionBar.with(this).init();
        mRcy = findViewById(R.id.rcy);
        mTitle = findViewById(R.id.title);
        mTitle.pullAnimator();
        mMainActivityPresenter.getCode();
    }

    @Override
    public void initEvent() {
        mRcy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRcy.addItemDecoration(new LinearLayoutDecoration(20, RecyclerView.VERTICAL));
        HomeAdapter adapter = new HomeAdapter(R.layout.adapter_home);
        List<String> strings = new ArrayList<>();
        strings.add("有对话框的请求");
        strings.add("无对话框的请求");
        strings.add("关闭标题对话");
        adapter.setNewData(strings);
        mRcy.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        mMainActivityPresenter.netDialog();
                        mMainActivityPresenter.getCode();
                        break;
                    case 1:
                        mMainActivityPresenter.noNetDialog();
                        break;
                    case 2:
                        mTitle.closePull();
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public void initComponent(DelegateComponent component) {
        DaggerMainActivityComponent.builder()
                .delegateComponent(component)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_mainactivity;
    }

    public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public HomeAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.btn, item);
        }
    }
}
