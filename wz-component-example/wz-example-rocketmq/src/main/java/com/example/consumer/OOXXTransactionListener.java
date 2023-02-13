package com.example.consumer;

import com.example.Utils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @projectName: wz-component
 * @package: com.example.consumer
 * @className: OOXXTransactionListener
 * @description:
 * @author: zhi
 * @date: 2023/2/13
 * @version: 1.0
 */
@RocketMQTransactionListener(txProducerGroup = OOXXTransactionListener.OOXX_TRANSACTION_PRODUCER)
public class OOXXTransactionListener implements RocketMQLocalTransactionListener {
    /**
     * 事物producer group, 发送消息的时候需要带上这个
     */
    public final static String OOXX_TRANSACTION_PRODUCER = "ooxx_transaction_producer2";

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 1. 本地操作, 本地操作全部执行成功.  COMMIT
        // 2. 本地操作, 部分操作失败 or 全部失败. ROLLBACK
        // 3. 本地操作, 部分操作无响应 or 暂没返回, 可能需要等待. UNKNOWN -> 需要在checkLocalTransaction 回调检查(Rocket提供的回调机制, 在应用集群环境下, 假如挂了一台, 那么Rocket会回调到另一台)

        MessageHeaders headers = msg.getHeaders();
        System.out.println("---------------executeLocalTransaction()---------------------" + headers.get("rocketmq_TRANSACTION_ID"));
        Utils.sleepSeconds(1);
        System.out.println("executeLocalTransaction() -> payload: " + new String((byte[]) msg.getPayload()));
        System.out.println("executeLocalTransaction() -> headers: " + headers);
        System.out.println("executeLocalTransaction() -> arg: " + arg);

        // 模拟操作

        // step1: 本地逻辑全部执行完成, 返回status=2, COMMIT发送消息到MQ
        // step2: 本地逻辑部分执行完成, 返回status=1, UNKNOWN 告诉Rocket 回调check, 检查是否执行完成
        // step3: 本地逻辑全部执行失败, 返回其他, ROLLBACK 告诉Rocket不发送消息到MQ

        if (!(arg instanceof Integer)) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        int status = (int) arg;
        // 1代表没有操作完, 需要check
        if (1 == status) {
            return RocketMQLocalTransactionState.UNKNOWN;
        } else if (2 == status) {
            return RocketMQLocalTransactionState.COMMIT;
        }

        // 其他情况, 回滚
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        System.out.println("---------------checkLocalTransaction()---------------------");
        Utils.sleepSeconds(1);
        System.out.println("checkLocalTransaction() -> payload: " + new String((byte[]) msg.getPayload()));
        MessageHeaders headers = msg.getHeaders();
        System.out.println("checkLocalTransaction() -> headers: " + headers);
        // step1: 模拟根据transactionId查询数据状态, 如果状态执行完成, COMMIT.
        // step2: 还有部分没有执行完成, 继续check UNKNOWN
        // step3: 存在部分执行失败, 观察业务是否需要全部回滚(因为executeLocalTransaction方法是否已经回滚过了)
        Object checkTimes = headers.get("TRANSACTION_CHECK_TIMES"); // 事物检查次数, 使用这个模拟
        int ct = Integer.parseInt(String.valueOf(checkTimes));
        // 模拟状态完整, COMMIT
        if (ct >= 3) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        // 状态不完整, UNKNOWN
        return RocketMQLocalTransactionState.UNKNOWN;
    }

}
