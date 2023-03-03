package com.nuist.demo.common.configure;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/11:08
 * @Description:
 */

import com.nuist.demo.massage.Order;
import com.nuist.demo.modules.model.enums.OrderEvent;
import com.nuist.demo.modules.model.enums.OrderState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * 状态机配置
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")

public class OrderStateMachineConfigure extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {
    /**
     * 配置状态
     *
     * @param states
     * @throws Exception
     */
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderState.IDLE)
                .states(EnumSet.allOf(OrderState.class));
    }

    /**
     * 配置状态转换事件关系
     *
     * @param transitions
     * @throws Exception
     */
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderState.IDLE).target(OrderState.WORKING).event(OrderEvent.RECEIVED_NODE_REQUEST)
                .and()
                .withExternal().source(OrderState.IDLE).target(OrderState.WORKING).event(OrderEvent.RECEIVED_USER_REQUEST)
                .and()
                .withExternal().source(OrderState.WORKING).target(OrderState.IDLE).event(OrderEvent.SENT_NODE_RESPONSE)
                .and()
                .withExternal().source(OrderState.WORKING).target(OrderState.IDLE).event(OrderEvent.DECISION_MADE)
                .and()
                .withExternal().source(OrderState.WORKING).target(OrderState.IDLE).event(OrderEvent.TIME_OUT)
                .and()
                .withExternal().source(OrderState.WORKING).target(OrderState.WAITING).event(OrderEvent.SENT_NODE_REQUEST)
                .and()
                .withExternal().source(OrderState.WAITING).target(OrderState.WORKING).event(OrderEvent.RECEIVED_NODE_RESPONSE)
                .and()
                .withExternal().source(OrderState.WAITING).target(OrderState.IDLE).event(OrderEvent.TIME_OUT);

    }

    @Bean
    public StateMachineListener<OrderState, OrderEvent> listener() {
        return new StateMachineListenerAdapter<OrderState, OrderEvent>() {
            @Override
            public void stateChanged(State<OrderState, OrderEvent> from, State<OrderState, OrderEvent> to) {
                System.out.println("State change--"+from+"---"  + to);
            }
        };
    }

    /**
     * 持久化配置
     * 实际使用中，可以配合redis等，进行持久化操作
     *
     * @return
     */
    @Bean
    public DefaultStateMachinePersister persister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object, Object, Order>() {
            @Override
            public void write(StateMachineContext<Object, Object> context, Order order) throws Exception {
                //此处并没有进行持久化操作
                //此处可调用更新状态流转接口(如：更新时间）状态不需要在此进行调用
            }

            @Override
            public StateMachineContext<Object, Object> read(Order order) throws Exception {

                //此处直接获取order中的状态，其实并没有进行持久化读取操作
                return new DefaultStateMachineContext(order.getOrderState(), null, null, null);
            }
        });
    }


}
