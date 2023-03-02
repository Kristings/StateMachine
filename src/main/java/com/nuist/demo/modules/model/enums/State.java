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
    RECEIVED_USER_REQUEST(1, "已收到应用层的请求"),
    RECEIVED_NODE_REQUEST(2, "已收到其他节点的请求"),
    RECEIVED_NODE_RESPONSE(3, "已收到其他节点的响应"),
    DECISION_MADE(4, "已达成共识"),
    TIME_OUT(5, "交易超时");

    private int code;
    private String msg;

    State(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
