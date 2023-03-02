package com.nuist.demo.util.thread;

import com.nuist.demo.massage.Order;
import com.nuist.demo.massage.RequestMessage;
import com.nuist.demo.modules.service.OrderService;
import com.nuist.demo.modules.service.impl.OrderServiceImpl;
import jakarta.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/02/25/18:26
 * @Description:
 */

public class TaskMachine implements Runnable{
    public boolean flag = false;
     OrderServiceImpl orderService;
     Order order;
     RequestMessage requestMessage;
     public TaskMachine(OrderServiceImpl orderService, RequestMessage requestMessage ,Order order){
         this.orderService = orderService;
         this.requestMessage = requestMessage;
         this.order =order;
     }

    @Override
    public void run() {
        System.out.println(orderService.responseWork(requestMessage,order));;
        flag = true;
    }


}
