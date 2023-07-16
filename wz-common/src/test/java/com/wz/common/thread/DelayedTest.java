package com.wz.common.thread;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @projectName: wz-component
 * @package: com.wz.common.thread
 * @className: DelayedTest
 * @description:
 * @author: zhi
 * @date: 2023/7/15
 * @version: 1.0
 */
public class DelayedTest {

    public static void main(String[] args) throws InterruptedException {
//        DelayQueue<DelayedTask> queue = new DelayQueue<>();
//        DelayedQueueConsumer consumer = new DelayedQueueConsumer(queue, true);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
/*        executor.submit(() -> {
            for (int i = 0; i < 5; i++) {
                int delayTime = 1000 * i * RandomUtil.randomInt(1, 3);
                System.out.println(delayTime);
                queue.add(new DelayedTask("task-" + i, delayTime));
            }
        });
        executor.submit(consumer);*/


//        executor.awaitTermination(5, TimeUnit.SECONDS);
//        executor.shutdown();
//        for (int i = 0; i < 5; i++) {
//            int delayTime = 1000 * i * RandomUtil.randomInt(1, 3);
//            System.out.println(delayTime);
            executor.execute(new DelayedTask("task-特殊的任务-一直存在避免队列中没有新加入的任务" + -1, 1000, executor, true));
            executor.execute(new DelayedTask("task-" + 0, 1000, executor, true));
            executor.execute(new DelayedTask("task-" + 1, 2000, executor, true));
            executor.execute(new DelayedTask("task-" + 2, 4000, executor, true));
            executor.execute(new DelayedTask("task-" + 3, 6000, executor, true));
//        }
        Queue<DelayedTask> deleteQueue = new ArrayDeque<>();
        deleteQueue.add(new DelayedTask("task-" + 1, 0, executor, true));
        deleteQueue.add(new DelayedTask("task-" + 3, 0, executor, true));

        new Thread(() -> {
            while (true) try {
                TimeUnit.SECONDS.sleep(6);
                System.out.println("队列数量: " + executor.getQueue().size());
                while (deleteQueue.size() != 0) {
                    Iterator<DelayedTask> iter = deleteQueue.iterator();
                    while (iter.hasNext()) {
                        DelayedTask deleteTask = iter.next();
                        Iterator<Runnable> iterTask = executor.getQueue().iterator();
                        while (iterTask.hasNext()) {
                            DelayedTask task = (DelayedTask) iterTask.next();
                            if (deleteTask.getName().equals(task.getName())) {
                                iterTask.remove();
                                deleteTask.stop();
                                System.out.println("-----删除成功--" + task.getName());
                                iter.remove();
                            }
                        }
                    }
                }
                if (deleteQueue.size() == 0) {
                    System.out.println("----队列删除任务-------");
                    break;
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}

@Data
class DelayedTask implements Delayed, Runnable {
    private String name;
    private long delayTime;
    private long avaibleTime;
    private volatile boolean running;
    private ThreadPoolExecutor executor;

    public DelayedTask(String name, long delayTime,  ThreadPoolExecutor executor, boolean running) {
        this.name = name;
        this.delayTime = delayTime;
        //avaibleTime = 当前时间+ delayTime
        this.avaibleTime = delayTime + System.currentTimeMillis();
        this.running = running;
        this.executor = executor;

    }

    @Override
    public long getDelay(TimeUnit unit) {
        //判断avaibleTime是否大于当前系统时间，并将结果转换成MILLISECONDS
        long diffTime = avaibleTime - System.currentTimeMillis();
        return unit.convert(diffTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        //compareTo用在DelayedUser的排序
        return (int) (this.avaibleTime - ((DelayedTask) o).getAvaibleTime());
    }

    @Override
    public void run() {
        while (running) {
            long delay = getDelay(MILLISECONDS);
            if (delay <= 0L) {
                System.out.println(getName() + "------执行任务------" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                // 3s after repeat add
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                executor.execute(new DelayedTask(name, delayTime, executor, running));
                break;
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}

@Data
class DelayedQueueConsumer implements Runnable {
    private final DelayQueue<DelayedTask> delayQueue;
    private volatile boolean running;

    DelayedQueueConsumer(DelayQueue<DelayedTask> delayQueue, boolean running) {
        this.delayQueue = delayQueue;
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("----consumer-----");
            try {
                DelayedTask element = delayQueue.take();
                System.out.println("Task: " + element + ", Now: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                delayQueue.add(element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}

