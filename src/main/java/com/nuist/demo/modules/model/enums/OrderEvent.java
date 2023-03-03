package com.nuist.demo.modules.model.enums;

public enum OrderEvent {

    RECEIVED_USER_REQUEST,  //收到应用层的请求
    RECEIVED_NODE_REQUEST,  //收到其他节点的请求
    SENT_NODE_REQUEST,      //发出请求给其他节点
    RECEIVED_NODE_RESPONSE, //收到其他节点的响应
    SENT_NODE_RESPONSE,     //发出响应给其他节点
    DECISION_MADE,          //达成共识
    TIME_OUT;               //交易超时
}
