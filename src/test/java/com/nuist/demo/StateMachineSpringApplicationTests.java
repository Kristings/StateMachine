package com.nuist.demo;

import com.nuist.demo.massage.Order;
import com.nuist.demo.massage.RequestMessage;
import com.nuist.demo.modules.model.enums.OrderState;
import com.nuist.demo.modules.service.impl.OrderServiceImpl;
import com.nuist.demo.util.thread.TaskMachine;
import com.nuist.demo.util.thread.ThreadPoolUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
class StateMachineSpringApplicationTests {
@Resource
OrderServiceImpl orderService;
    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        Order order = new Order();
        order.setOrderState(OrderState.IDLE);
        Thread.currentThread().setName("主线程");
        System.out.println(Thread.currentThread().getName());
        System.out.println("进入线程");
        TaskMachine taskMachine = new TaskMachine(orderService, new RequestMessage(), order);
        ThreadPoolUtil.submit(taskMachine);

        if (!ThreadPoolUtil.awaitTerminal(1)){ //超时
            System.out.println("等一等，超时ing");
        }
        ThreadPoolUtil.shutdown();
        System.out.println("***************结束了****************");

    }


}
