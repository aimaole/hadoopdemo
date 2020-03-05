package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadTest {
    //    private static final Logger log = LoggerFactory.getLogger(ThreadTest.class);
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
//        Thread t1 = new Thread(threadDemo);
//        t1.setPriority(1);
//        t1.start();

        /**
         * newCachedThreadPool——创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
         * newFixedThreadPool——创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         * newScheduledThreadPool——创建一个定长线程池，支持定时及周期性任务执行。
         * newSingleThreadExecutor——创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         */
        // 创建一个容量为5的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 15; i++) {
            // 向线程池提交一个任务（其实就是通过线程池来启动一个线程）
            executorService.execute(threadDemo);
            System.out.println("==========" + i);

        }
        executorService.shutdown();


    }
}

class ThreadDemo implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("使用了" + Thread.currentThread().getName());
    }
}
