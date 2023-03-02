package com.nuist.demo.modules.model.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/11:18
 * @Description:
 */
public enum State {

    IDLE(0, "空闲"),
    WAITING(1, "等待中"),
    WORKING(2, "工作中");

    private int code;
    private String msg;

    State(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
