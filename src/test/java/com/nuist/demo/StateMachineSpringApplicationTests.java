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

        ThreadPoolUtil.awaitTerminal(1);
        ThreadPoolUtil.shutdown();
        System.out.println("***************主线程结束了****************");

    }


}
