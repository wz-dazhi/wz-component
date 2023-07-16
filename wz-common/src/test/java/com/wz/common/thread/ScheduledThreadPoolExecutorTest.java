//package com.wz.common.thread;
//
//import java.time.LocalDateTime;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Delayed;
//import java.util.concurrent.FutureTask;
//import java.util.concurrent.RunnableScheduledFuture;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.concurrent.locks.LockSupport;
//
//import static java.util.concurrent.TimeUnit.NANOSECONDS;
//
///**
// * @projectName: wz-component
// * @package: com.wz.common.thread
// * @className: ScheduledThreadPoolExecutor
// * @description:
// * @author: zhi
// * @date: 2023/7/15
// * @version: 1.0
// */
//public class ScheduledThreadPoolExecutorTest {
//
//    public static void main(String[] args) throws InterruptedException {
////        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, r -> {
////            Thread t = new Thread(r, "scheduled-thread");
////            t.setDaemon(true);
////            return t;
////        });
//
////        executor.scheduleAtFixedRate(() -> System.out.println("hello" + LocalDateTime.now()), 1, 1, TimeUnit.SECONDS);
////        System.out.println("--------");
////        new Thread(() -> {
////            BlockingQueue<Runnable> q = executor.getQueue();
////            System.out.println("--------" + q.size());
////            q.add();
////            System.out.println(q.take());
////        }).start();
//
//
//
//        System.out.println("---end---");
//        LockSupport.park();
//    }
//
//    private static class FutureTaskTest<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {
//        private static final AtomicLong sequencer = new AtomicLong();
//
//        private final ThreadPoolExecutor executor;
//
//        private final long sequenceNumber;
//
//        private volatile long time;
//
//        private final long period;
//
//        FutureTaskTest<V> outerTask = this;
//
//        int heapIndex;
//
//        FutureTaskTest(Runnable r, V result, ScheduledThreadPoolExecutor executor, long triggerTime) {
//            super(r, result);
//            this.executor = executor;
//            this.time = triggerTime;
//            this.period = 0;
//            this.sequenceNumber = sequencer.getAndIncrement();
//        }
//
//        FutureTaskTest(Runnable r, V result, ScheduledThreadPoolExecutor executor, long triggerTime, long period) {
//            super(r, result);
//            this.executor = executor;
//            this.time = triggerTime;
//            this.period = period;
//            this.sequenceNumber = sequencer.getAndIncrement();
//        }
//
//        FutureTaskTest(Callable<V> callable, ScheduledThreadPoolExecutor executor, long triggerTime) {
//            super(callable);
//            this.executor = executor;
//            this.time = triggerTime;
//            this.period = 0;
//            this.sequenceNumber = sequencer.getAndIncrement();
//        }
//
//        public long getDelay(TimeUnit unit) {
//            return unit.convert(time - System.nanoTime(), NANOSECONDS);
//        }
//
//        public int compareTo(Delayed other) {
//            if (other == this) // compare zero if same object
//                return 0;
//            if (other instanceof FutureTaskTest) {
//                FutureTaskTest<?> x = (FutureTaskTest<?>) other;
//                long diff = time - x.time;
//                if (diff < 0)
//                    return -1;
//                else if (diff > 0)
//                    return 1;
//                else if (sequenceNumber < x.sequenceNumber)
//                    return -1;
//                else
//                    return 1;
//            }
//            long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
//            return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
//        }
//
//        public boolean isPeriodic() {
//            return period != 0;
//        }
//
//        private void setNextRunTime() {
//            long p = period;
//            if (p > 0)
//                time += p;
//            else
//                time = triggerTime(-p);
//        }
//
//        public boolean cancel(boolean mayInterruptIfRunning) {
//            boolean cancelled = super.cancel(mayInterruptIfRunning);
//            if (cancelled && executor.getRemoveOnCancelPolicy() && heapIndex >= 0)
//                executor.remove(this);
//            return cancelled;
//        }
//
//        public void run() {
//            if (!canRunInCurrentRunState(this))
//                cancel(false);
//            else if (!isPeriodic())
//                super.run();
//            else if (super.runAndReset()) {
//                setNextRunTime();
//                reExecutePeriodic(outerTask);
//            }
//        }
//        void reExecutePeriodic(FutureTaskTest<?> task) {
//            if (canRunInCurrentRunState(task)) {
//                executor.getQueue().add(task);
//                if (canRunInCurrentRunState(task) || !executor.remove(task)) {
//                    //ensurePrestart();
//                    return;
//                }
//            }
//            task.cancel(false);
//        }
//        boolean canRunInCurrentRunState(FutureTaskTest<?> task) {
//            if (!executor.isShutdown())
//                return true;
////            if (executor.isStopped())
////                return false;
//            return task.isPeriodic() || task.getDelay(NANOSECONDS) <= 0;
//        }
//
//        long triggerTime(long delay) {
//            return System.nanoTime() +
//                    ((delay < (Long.MAX_VALUE >> 1)) ? delay : overflowFree(delay));
//        }
//
//        private long overflowFree(long delay) {
//            Delayed head = (Delayed) executor.getQueue().peek();
//            if (head != null) {
//                long headDelay = head.getDelay(NANOSECONDS);
//                if (headDelay < 0 && (delay - headDelay < 0))
//                    delay = Long.MAX_VALUE + headDelay;
//            }
//            return delay;
//        }
//    }
//
//}
