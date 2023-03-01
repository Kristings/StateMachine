package com.nuist.demo.common.configure;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/03/01/11:08
 * @Description:
 */

import com.nuist.demo.modules.model.enums.OrderEvent;
import com.nuist.demo.modules.model.enums.OrderState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

/**
 * 订单状态机配置
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
                .withExternal().source(OrderState.IDLE).target(OrderState.RECEIVED_USER_REQUEST).event(OrderEvent.RECEIVED_USER_REQUEST);

    }

    @Bean
    public StateMachineListener<OrderState, OrderEvent> listener() {
        return new StateMachineListenerAdapter<OrderState, OrderEvent>() {
            @Override
            public void stateChanged(State<OrderState, OrderEvent> from, State<OrderState, OrderEvent> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }


}
