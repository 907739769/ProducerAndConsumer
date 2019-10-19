package cn.jackding.pac;

import java.util.concurrent.FutureTask;

/**
 * 生产者
 *
 * @Author Jack
 * @Date 2019/10/17 17:11
 * @Version 1.0.0
 */
public class Producer extends FutureTask {

    public Producer(Runnable runnable) {
        super(runnable, null);
    }

}
