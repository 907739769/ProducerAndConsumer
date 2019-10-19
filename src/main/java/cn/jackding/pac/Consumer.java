package cn.jackding.pac;

import java.util.concurrent.FutureTask;

/**
 * 消费者
 *
 * @Author Jack
 * @Date 2019/10/17 17:11
 * @Version 1.0.0
 */
public class Consumer extends FutureTask {

    public Consumer(Runnable runnable) {
        super(runnable, null);
    }

}
