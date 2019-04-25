package com.fwkj.fw_root_library.transformer;


import androidx.annotation.NonNull;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;


public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io(SchedulerProvider.IoThingInter pIoThingInter);

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers(SchedulerProvider.IoThingInter pIoThingInter);
}
