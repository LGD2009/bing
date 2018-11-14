package com.wallpaper.bing.network.bean;

/**
 * author GaoPC
 * date 2018-01-04 14:10
 * description
 */

public class BaseBean<T> {

    private String status;
    private T message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
