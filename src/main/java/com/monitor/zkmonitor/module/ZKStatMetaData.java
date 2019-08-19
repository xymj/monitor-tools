package com.monitor.zkmonitor.module;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.zookeeper.data.Stat;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 19:04
 * @Description:
 */

@Data
@NoArgsConstructor
public class ZKStatMetaData {
    //节点被创建时的事务id  Create ZXID
    private Long czxid;
    //节点最后一次被更新时的事务id
    private Long mzxid;
    //节点被创建时时间
    private Long ctime;
    //节点最后一次被更新的时时间
    private Long mtime;
    //节点版本号
    private Integer version;
    //子节点版本号
    private Integer cversion;
    //节点的ACL版本号
    private Integer aversion;
    //创建临时数据节点的会话sessionID，如果该节点是持久节点，那么这个属性值为0
    private Long ephemeralOwner;
    //节点数据内容的长度
    private Integer dataLength;
    //当前节点的子节点个数
    private Integer numChildren;
    //表示该节点的子节点列表最后一次被修改时的事务ID。注意：只有子节点列表变更才会变更pzxid,子节点内容变更不会影响pzxid
    private Long pzxid;

    public ZKStatMetaData(Stat stat) {
        czxid = stat.getCzxid();
        mzxid = stat.getMzxid();
        ctime = stat.getCtime();
        mtime = stat.getMtime();
        version = stat.getVersion();
        cversion = stat.getCversion();
        aversion = stat.getAversion();
        ephemeralOwner = stat.getEphemeralOwner();
        dataLength = stat.getDataLength();
        numChildren = stat.getNumChildren();
        pzxid = stat.getPzxid();
    }
}
