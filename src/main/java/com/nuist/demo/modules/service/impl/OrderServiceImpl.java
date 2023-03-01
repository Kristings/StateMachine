package com.nuist.demo.modules.service.impl;

import com.nuist.demo.massage.Order;
import com.nuist.demo.massage.RequestMessage;
import com.nuist.demo.modules.model.enums.OrderEvent;
import com.nuist.demo.modules.model.enums.OrderState;
import com.nuist.demo.modules.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/11:19
 * @Description:
 */
public class OrderServiceImpl implements OrderService {
    @Resource
    private StateMachine<OrderState, OrderEvent> orderStateMachine;

    @Resource
    private StateMachinePersister<OrderState, OrderEvent, Order> persister;

    private int id = 1;
    private Map<Integer, Order> orders = new HashMap<>();

    @Override
    public String responseWork(RequestMessage requestMessage) {
        return null;
    }




    /**
     * 发送订单状态转换事件
     *
     * @param message
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(Message<OrderEvent> message, Order order) {
        boolean result = false;
        try {
            orderStateMachine.start();
            //尝试恢复状态机状态
            persister.restore(orderStateMachine, order);
            //添加延迟用于线程安全测试
            Thread.sleep(1000);
            result = orderStateMachine.sendEvent(message);
            //持久化状态机状态
            persister.persist(orderStateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}
