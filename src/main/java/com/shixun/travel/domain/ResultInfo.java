package com.shixun.travel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //lombok 加了getter和setter
@NoArgsConstructor
public class ResultInfo {
    //用来封装操作结果的一个对象
    //把数据发给客户端，封装的结果，

    private Boolean success;  //是否操作成功
    private String message;   //操作信息
    private Object data;      //其它的数据

    // 一个参数构造
    public ResultInfo(Boolean success) {
        this.success = success;
    }

    // 二个参数构造
    public ResultInfo(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    //二个参数构造方法
    public ResultInfo(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    // 三个参数构造
    public ResultInfo(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
