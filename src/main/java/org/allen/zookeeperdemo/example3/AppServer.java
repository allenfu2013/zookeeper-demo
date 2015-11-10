package org.allen.zookeeperdemo.example3;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * @author : fuyong780
 * @date : 2015-11-10
 */
public class AppServer {
    private String groupNode = "sgroup";
    private String subNode = "sub";

    /**
     * 连接zookeeper
     *
     * @param address server的地址
     */
    public void connectZookeeper(String address) throws Exception {
        ZooKeeper zk = new ZooKeeper("172.19.22.124:2181", 5000, new Watcher() {
            public void process(WatchedEvent event) {
                // 不做处理
            }
        });

        Stat stat = zk.exists("/" + groupNode, false);
        if (null == stat) {
            // 创建"/sgroup"节点, 节点为永久性的(即客户端shutdown了也不会消失)
            zk.create("/" + groupNode, "groupNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        // 在"/sgroup"下创建子节点
        // 子节点的类型设置为EPHEMERAL_SEQUENTIAL, 表明这是一个临时节点, 且在子节点的名称后面加上一串数字后缀
        // 将server的地址数据关联到新创建的子节点上
        String createdPath = zk.create("/" + groupNode + "/" + subNode, address.getBytes("utf-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create: " + createdPath);
    }

    /**
     * server的工作逻辑写在这个方法中
     * 此处不做任何处理, 只让server sleep
     */
    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        String address = "localhost:8080";
        AppServer as = new AppServer();
        as.connectZookeeper(address);
        as.handle();
    }
}
