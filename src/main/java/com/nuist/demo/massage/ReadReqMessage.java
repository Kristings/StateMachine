package com.nuist.demo.massage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadReqMessage extends RequestMessage{
    int orderId;//发送消息的节点编号
    int nonce;  //操作编号，操作编号对于READ和WRITE的意义是不一样的
    int status; //消息对应操作步骤的编号
    byte[] rands;//n个随机数，n为参与当前通道的ORDER节点数
}
