package cn.jackding.pac;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者消费者耦合
 *
 * @Author Jack
 * @Date 2019/10/17 21:15
 * @Version 1.0.0
 */
public class ProducerConsumer {

    public void producerConsumer() {
        LinkedBlockingQueue queue = new LinkedBlockingQueue(100);
        Shop shop = Shop.getInstance();
        //消费者 生产者线程池分开  免得消费者或者生产者占用线程池中的全部线程
        ExecutorService conExecutor = shop.getConExecutor();
        ExecutorService proExecutor = shop.getProExecutor();
        int producerNumMax = shop.producerNum();
        int consumerNumMax = shop.consumerNum();
        int producerNum = 0;
        int consumerNum = 0;
        List<FutureTask> producerList = new ArrayList<>();
        List<FutureTask> consumerList = new ArrayList<>();
        try {
            while (!shop.producerDone() || !shop.consumerDone()) {
                //创建生产者线程
                while (producerNum < producerNumMax) {
                    Producer futureTask = new Producer(() -> {
                        shop.producer(queue);
                    });
                    proExecutor.execute(futureTask);
                    producerList.add(futureTask);
                    producerNum++;
                }
                //创建消费者线程
                while (consumerNum < consumerNumMax) {
                    Consumer futureTask = new Consumer(() -> {
                        shop.consumer(queue);
                    });
                    conExecutor.execute(futureTask);
                    consumerList.add(futureTask);
                    consumerNum++;
                }
                //判断生产者线程是否完成
                Iterator<FutureTask> proIterator = producerList.iterator();
                while (proIterator.hasNext()) {
                    if (proIterator.next().isDone()) {
                        producerNum--;
                        proIterator.remove();
                    }
                }
                //判断消费者线程是否完成
                Iterator<FutureTask> conIterator = consumerList.iterator();
                while (conIterator.hasNext()) {
                    if (conIterator.next().isDone()) {
                        consumerNum--;
                        conIterator.remove();
                    }
                }
            }
        } finally {//关闭线程池咯
            conExecutor.shutdown();
            proExecutor.shutdown();
        }
    }

}
