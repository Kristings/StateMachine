package com.nuist.demo.modules.service;

import com.nuist.demo.massage.Order;
import com.nuist.demo.massage.RequestMessage;
import com.nuist.demo.massage.ResponseMessage;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/21:43
 * @Description:
 */
public interface OrderService {
   ResponseMessage responseWork(RequestMessage requestMessage , Order order);
   ResponseMessage responseOrder(Order order);


}
