package org.allen.zookeeperdemo.example1;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.util.Arrays;
import java.util.List;

/**
 * @author : fuyong780
 * @date : 2015-11-09
 */
public class ZooKeeperOperator extends AbstractZooKeeper {
    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        /**
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect.
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect.
         */
        this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * <b>function:</b>获取节点信息
     *
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     * @author cuiran
     * @createDate 2013-01-16 15:17:22
     */
    public void getChild(String path) throws KeeperException, InterruptedException {
        try {
            List<String> list = this.zooKeeper.getChildren(path, false);
            if (list.isEmpty()) {
                debug(path + "中没有节点");
            } else {
                debug(path + "中存在节点");
                for (String child : list) {
                    debug("节点为：" + child);
                }
            }
        } catch (KeeperException.NoNodeException e) {
            throw e;
        }
    }

    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return this.zooKeeper.getData(path, false, null);
    }

    public static void main(String[] args) {
        try {
            ZooKeeperOperator zkoperator = new ZooKeeperOperator();
            zkoperator.connect("172.19.22.124:2181");

            byte[] data = new byte[]{'a', 'b', 'c', 'd'};

            zkoperator.create("/root", null);
            System.out.println(Arrays.toString(zkoperator.getData("/root")));

            zkoperator.create("/root/child1", data);
            System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));

            zkoperator.create("/root/child2", data);
            System.out.println(Arrays.toString(zkoperator.getData("/root/child2")));

            String zktest = "ZooKeeper的Java API测试";
            zkoperator.create("/root/child3", zktest.getBytes());
            debug("获取设置的信息：" + new String(zkoperator.getData("/root/child3")));

            System.out.println("节点孩子信息:");
            zkoperator.getChild("/root");

            zkoperator.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void debug(String log) {
        System.out.println("###### debug: " + log);
    }
}
