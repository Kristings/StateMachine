package com.nuist.demo.modules.model.enums;

public enum Event {

    RECEIVED_USER_REQUEST, // 收到应用层的请求
    RECEIVED_NODE_REQUEST, // 收到其他节点的请求
    RECEIVED_NODE_RESPONSE, // 收到其他节点的响应
    DECISION_MADE, // 达成共识
    TIME_OUT; // 交易超时
}
