package cn.jackding.pac;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 任务配置
 *
 * @Author Jack
 * @Date 2019/10/18 15:18
 * @Version 1.0.0
 */
public class Shop {

    private boolean consumerDone;

    private boolean producerDone;

    public boolean consumerDone() {
        return consumerDone;
    }

    public boolean producerDone() {
        return producerDone;
    }

    //消费者线程数
    public int consumerNum() {
        return 10;
    }

    //生产者线程数
    public int producerNum() {
        return 10;
    }

    //消费者消费队列  完成之后consumerDone为true
    public void consumer(LinkedBlockingQueue queue) {
        Object a = null;
        try {
            a = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消费者：" + a);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //生产者增加队列  完成之后producerDone为true
    public void producer(LinkedBlockingQueue queue) {
        Object a = new Random().nextInt();
        try {
            queue.put(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("生产者：" + a);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ExecutorService getConExecutor() {
        return new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(40));
    }

    public ExecutorService getProExecutor() {
        return new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(40));
    }

}
