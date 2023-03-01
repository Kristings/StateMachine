package com.nuist.demo.massage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessage {
   public int cid;  //channelid
   public String field;//字段名
   public int oper; //表明是Read还是Write操作
   public byte[] data ; //如果是写操作，为写入的数据，如果是读为nil
}
