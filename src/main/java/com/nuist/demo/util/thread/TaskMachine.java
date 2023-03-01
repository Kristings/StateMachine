package com.nuist.demo.util.thread;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HouYun
 * @Date: 2023/02/25/18:26
 * @Description:
 */

public class TaskMachine implements Runnable{
    @Override
    public void run() {
        System.out.println("你好!");
    }


}
