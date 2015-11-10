package org.allen.zookeeperdemo.example1;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * @author : fuyong780
 * @date : 2015-11-09
 */
public class SimpleDemo {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("172.19.22.124:2181", 500000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                //dosomething
                System.out.println("######## event: " + event);
            }
        });

        //创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
//        zk.create("/root", "mydata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
//        zk.create("/root/childone", "childone".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //取得/root节点下的子节点名称,返回List<String>
        List<String> children = zk.getChildren("/sgroup", true);
        System.out.println(children);

        //删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
//        zk.delete("/root/childone", -1);
    }
}
