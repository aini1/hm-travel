package com.shixun.travel.domain;



import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 * lombok作用：在类编译成字节码以后生成get和set方法，还有toString方法
 */
@Data
public class User implements Serializable {

    private int uid;//用户id
    private String username;//用户名，账号
    private String password;//密码
    private String telephone;//手机号
    private String salt; // 盐

    //生成get和set方法快捷键 alt+insert

}
