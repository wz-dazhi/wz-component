package com.example.admin;

import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;

/**
 * @projectName: wz-component
 * @package: com.example.admin
 * @className: AdminTest
 * @description:
 * @author: zhi
 * @date: 2023/2/10
 * @version: 1.0
 */
public class AdminTest {

    public static void main(String[] args) throws Exception {
        DefaultMQAdminExt admin = new DefaultMQAdminExt();
        admin.setNamesrvAddr("172.16.207.2:9876;172.16.207.3:9876");
        admin.start();
        ClusterInfo cluster = admin.examineBrokerClusterInfo();
        cluster.getBrokerAddrTable().forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("------------------");

        TopicList defaultCluster = admin.fetchTopicsByCLuster("DefaultCluster");
        defaultCluster.getTopicList().forEach(System.out::println);
    }

}
