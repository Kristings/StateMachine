package com.nuist.demo.massage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/03/15:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateCache {
  int oper;
  int nonce;
  byte[] v;
  long timestamp;
  long timer;
  byte[] rands;
}
