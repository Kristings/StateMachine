package com.nuist.demo.modules.service.impl;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/22:53
 * @Description:
 */

import com.nuist.demo.massage.Order;
import com.nuist.demo.modules.model.enums.OrderEvent;
import com.nuist.demo.modules.model.enums.OrderState;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * 状态监听器
 */
@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListenerImpl {



    @OnTransition(source = "IDLE", target = "RECEIVED_USER_REQUEST")
    public boolean readTransition(Message<OrderEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(1);
        System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }
}
