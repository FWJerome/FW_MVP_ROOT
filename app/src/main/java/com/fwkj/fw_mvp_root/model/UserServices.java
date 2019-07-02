package com.fwkj.fw_mvp_root.model;

import com.fwkj.fw_mvp_root.contract.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface UserServices {
    @FormUrlEncoded
    @POST(" smscode/send")
    Observable<UserBean> getCode(@Field("phone") String phone);
}
