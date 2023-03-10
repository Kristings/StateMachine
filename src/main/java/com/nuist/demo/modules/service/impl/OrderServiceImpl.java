package com.nuist.demo.modules.service.impl;

import com.nuist.demo.massage.Order;
import com.nuist.demo.massage.RequestMessage;
import com.nuist.demo.massage.ResponseMessage;
import com.nuist.demo.modules.model.enums.OrderEvent;
import com.nuist.demo.modules.model.enums.OrderState;
import com.nuist.demo.modules.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/11:19
 * @Description:
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private StateMachine<OrderState, OrderEvent> orderStateMachine;

    @Resource
    private StateMachinePersister<OrderState, OrderEvent, Order> persister;

    @Resource
    private StateMachineListener<OrderState, OrderEvent> listener;

    /**
     * 事件也记录起来
     * @param requestMessage
     * @param order
     * @return
     */
    /*********************************************转状态IDLE -> WORKING*********************************************************/
    @Override
    public ResponseMessage responseWork(RequestMessage requestMessage , Order order) {
        System.out.println("------------------------------状态转换----------------------------------------");
        Message message = MessageBuilder.withPayload(OrderEvent.RECEIVED_USER_REQUEST)
                .setHeader("order",order )
                .setHeader("requestMessage",requestMessage)
                .setHeader("event","RECEIVED_USER_REQUEST").build();
//        System.out.println("线程名称：" + Thread.currentThread().getName() + "---接受到用户请求---"+requestMessage.oper);
        if (sendEvent(message,order)){
            System.out.println("****************************work请求成功*****************************");
             message = MessageBuilder.withPayload(OrderEvent.SENT_NODE_REQUEST)
                    .setHeader("order",order )
                    .setHeader("requestMessage",requestMessage)
                    .setHeader("event","RECEIVED_NODE_REQUEST").build();
             if (sendEvent(message,order)){
                 System.out.println("***********************收到srv6回复信息********************");
                 message = MessageBuilder.withPayload(OrderEvent.RECEIVED_NODE_RESPONSE)
                         .setHeader("order",order )
                         .setHeader("requestMessage",requestMessage)
                         .setHeader("event","RECEIVED_NODE_RESPONSE").build();
                 if (sendEvent(message,order)){
                     System.out.println("状态转换为IDLE，关闭状态机");
                 }
             }
        }
        //
        return new ResponseMessage();
    }

    /**
     *
     * @param order
     * @return
     */
    @Override
    public ResponseMessage responseOrder(Order order) {
        System.out.println("------------------------------状态转换----------------------------------------");
        Message message = MessageBuilder.withPayload(OrderEvent.RECEIVED_NODE_REQUEST)
                .setHeader("order",order )
                .setHeader("event","RECEIVED_NODE_REQUEST").build();
        if (sendEvent(message,order)){
            System.out.println("线程名称：" + Thread.currentThread().getName() + "---接受到节点请求---");
            System.out.println(order);
        }
        return new ResponseMessage();
    }




    /**
     * order状态转换事件
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
//            Thread.sleep(1000);

            result = orderStateMachine.sendEvent(message);
            System.out.println(result);
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
