package com.wallpaper.bing.rxbus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * author GaoPC
 * date 2018-01-25 15:25
 * description
 */

public class RxBus {


    private RxBus(){}

    public static RxBus getInstance(){
        return RxBusHolder.rxBus;
    }

    private static final class RxBusHolder{
        private static final RxBus rxBus = new RxBus();
    }


    private final Relay<Object> bus = PublishRelay.create().toSerialized();

    public Observable<Object> getObservable(){
        return bus;
    }

    public void send(Object o){
        bus.accept(o);
    }

    //支持背压
    public Flowable<Object> asFlowable(){
        return bus.toFlowable(BackpressureStrategy.LATEST);
    }

    private boolean hasObservers(){
        return bus.hasObservers();
    }

}
