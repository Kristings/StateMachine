package com.nuist.demo;

import com.nuist.demo.massage.Order;
import com.nuist.demo.massage.RequestMessage;
import com.nuist.demo.util.thread.TaskMachine;
import com.nuist.demo.util.thread.ThreadPoolUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StateMachineSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(StateMachineSpringApplication.class, args);

    }

}
