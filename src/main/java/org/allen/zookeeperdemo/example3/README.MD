# 场景描述
在分布式应用中, 我们经常同时启动多个server, 调用方(client)选择其中之一发起请求.
分布式应用必须考虑高可用性和可扩展性: server的应用进程可能会崩溃, 或者server本身也可能会宕机. 当server不够时, 也有可能增加server的数量. 总而言之, server列表并非一成不变, 而是一直处于动态的增减中.
那么client如何才能实时的更新server列表呢? 解决的方案很多, 本文将讲述利用ZooKeeper的解决方案.

# 思路
启动server时, 在zookeeper的某个znode(假设为/sgroup)下创建一个子节点. 所创建的子节点的类型应该为ephemeral, 这样一来, 如果server进程崩溃, 或者server宕机, 与zookeeper连接的session就结束了, 那么其所创建的子节点会被zookeeper自动删除. 当崩溃的server恢复后, 或者新增server时, 同样需要在/sgroup节点下创建新的子节点.
对于client, 只需注册/sgroup子节点的监听, 当/sgroup下的子节点增加或减少时, zookeeper会通知client, 此时client更新server列表.