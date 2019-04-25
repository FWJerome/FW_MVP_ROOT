package com.fwkj.fw_root_library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.fwkj.fw_root_library.inter.IFragment;
import com.trello.rxlifecycle3.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment implements IFragment {
    protected FragmentActivity _mActivity;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(initLayout(), container, false);
        findView(view);
        return view;
    }

    protected abstract void findView(View view);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(Root_App.getDelegateComponent());
        init();
    }
}