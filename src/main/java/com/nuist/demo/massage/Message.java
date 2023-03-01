package com.nuist.demo.massage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    int orderId; //发送消息的节点编号
    int  cid; //channel id
    String  field; //字段名
    int  oper;//表明是Read还是Write操作
    int nonce;  //操作编号，操作编号对于READ和WRITE的意义是不一样的
    int status;  //消息对应操作步骤的编号
}
