package com.nuist.demo.modules.service.impl;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/22:53
 * @Description:
 */

import com.nuist.demo.massage.OperateCache;
import com.nuist.demo.massage.Order;
import com.nuist.demo.massage.RequestMessage;
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


    /**
     * 这里从IDLE到WORKING由于RECEIVED_USER_REQUEST或者RECEIVED_NODE_REQUEST，需要判断一下是哪种情况
     * @param message
     * @return
     */
    @OnTransition(source = "IDLE", target = "WORKING" )
    public boolean idleToWorkTransition(Message<OrderEvent> message) {
        String event = (String) message.getHeaders().get("event");
        Order order = (Order) message.getHeaders().get("order");
        assert order != null;
        order.setOrderState(OrderState.WORKING);
        if (event.equals("RECEIVED_USER_REQUEST")){
            RequestMessage requestMessage = (RequestMessage) message.getHeaders().get("requestMessage");
            if (requestMessage.oper == 0){//读
            /************************读*************************/
                System.out.println("***************************oder响应work请求******************************");

            } else if (requestMessage.oper == 1) {//写
//            writeWork(requestMessage);
            }else {//出错
                System.out.println("出错了");
            }

            work();//处理用户事件
            System.out.println("状态切换中：" + message.getHeaders().toString());
        }
        else {
            //接受srv6的请求 ，根据order数据执行
            order();
        }
        System.out.println(order);
        return true;
    }

    /**
     * 三个事件SENT_NODE_RESPONSE，DECISION_MADE，TIME_OUT
     * @param message
     * @return
     */
    @OnTransition(source = "WORKING", target = "IDLE" )
    public boolean workToIdleTransition(Message<OrderEvent> message) {
        String event = (String) message.getHeaders().get("event");
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderState(OrderState.IDLE);
        if (event.equals("SENT_NODE_RESPONSE")){
            //返回送到srv6
        }

        else if (event.equals("DECISION_MADE")) {
           //达成共识，此时应该关闭状态机

        }
        //超时
        else {
           //结束当前的事务，关闭当前线程
        }
        System.out.println(order);
        return true;
    }

    /**SENT_NODE_REQUEST
     * 由work到wait  发送请求order的消息至srv6
     * @param message
     * @return
     */
    @OnTransition(source = "WORKING", target = "WAITING" )
    public boolean workToWaitTransition(Message<OrderEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderState(OrderState.WAITING);
        //发送至srv6等待回复
        System.out.println("发送至SRV6，等待回复");
        System.out.println(order);
        return true;
    }

    /** 这里是backup节点，接受路由转发的节点回复信息，满足n/3+1即可
     * RECEIVED_NODE_RESPONSE
     * @param message
     * @return
     */
    @OnTransition(source = "WAITING", target = "WORKING" )
    public boolean waitToWorkTransition(Message<OrderEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderState(OrderState.WORKING);
        System.out.println(order);
        ////////////////////////////////////事务
        return true;
    }

    /**
     * 节点等待时间过长超出times，关闭事务
     * @param message
     * @return
     */
    @OnTransition(source = "WAITING", target = "IDLE" )
    public boolean waitToIDLETransition(Message<OrderEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderState(OrderState.IDLE);
        dillTimeOut();
        System.out.println(order);
        return true;
    }






    /**
     * 用户请求事务
     */
    public void work(){
        System.out.println("处理用户请求事务");
    }

    /**
     * 节点请求事务
     */
    public void order(){
        System.out.println("处理order节点请求");
    }

    /**
     * 结束事务
     */
    public void dillTimeOut(){
        //关闭事务
    }
}
