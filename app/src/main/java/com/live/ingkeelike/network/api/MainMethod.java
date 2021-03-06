package com.live.ingkeelike.network.api;

import android.support.annotation.Nullable;

import com.live.ingkeelike.network.request.MainBean;
import com.live.ingkeelike.network.response.HttpFun;

import java.io.IOException;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yubai on 2017/1/4.
 * 主页模块接口管理
 */

public class MainMethod extends BaseMethod{

    private static MainMethod instance;

    public static MainMethod getInstance() {

        if (null == instance) {
            synchronized(MainMethod.class){
                if(null == instance){
                    instance = new MainMethod();
                }
            }
        }
        return instance;
    }

    private MainMethod() {

    }

    @Override
    public String getBaseUrl() {
        return  "";
    }

    @Nullable
    private MainAPi getMainApi(){
        try{
            return getRetrofit(getBaseUrl()).create(MainAPi.class);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e2){
            e2.printStackTrace();
        }
        return null;

    }

    /**
     * 请求列表数据
     * @param bean
     * @return
     */
    public Subscription getListDatas(MainBean bean, Subscriber<String> subscriber){
        return getMainApi().getListDatas(bean)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new HttpFun())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
