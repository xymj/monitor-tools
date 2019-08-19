package com.monitor.zkmonitor.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.monitor.common.service.LocalCacheService;
import com.monitor.common.utils.CommonUtils;
import com.monitor.zkmonitor.constant.CommonConstant;
import com.monitor.zkmonitor.dao.ZookeeperDao;
import com.monitor.zkmonitor.dao.ZookeeperSourceDao;
import com.monitor.zkmonitor.module.TreeNode;
import com.monitor.zkmonitor.module.ZKACLMetaData;
import com.monitor.zkmonitor.module.ZKSource;
import com.monitor.zkmonitor.module.ZKStatMetaData;
import javafx.beans.binding.ObjectExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundPathAndBytesable;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.shaded.com.google.common.collect.ImmutableList;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 15:25
 * @Description:
 */

@Slf4j
@Repository
public class ZookeeperDaoImpl implements ZookeeperDao, DisposableBean {

    private static final String ZK_NODE_DATA = "node_data";
    private static final String ZK_STAT_DATA = "stat_data";
    private static final String ZK_ACL_DATA = "acl_data";
    private static final String CHARSET_ENCODE = "utf-8";


    @Autowired
    private LocalCacheService cacheService;

    @Autowired
    private ZookeeperSourceDao zookeeperSourceDao;

    @Override
    public String getZkConnectStatus(String cacheId) throws Exception {
        return checkNodeExist(getZookeeperClient(cacheId), CommonConstant.DEFAULT_ROOT_PATH)
                ? CommonConstant.CONNECTED_STATUS : CommonConstant.DISCONNECTED_STATUS;
    }

    @Override
    public String getZkNodeInfo(String cacheId, String path) throws Exception {
        CuratorFramework zookeeperClient = getZookeeperClient(cacheId);
        if (null == zookeeperClient) {
            return StringUtils.EMPTY;
        }
        //List<String> path1 = zookeeperClient.getChildren().forPath(path);
        //System.out.println(path1);
        String nodeData = getNodeData(zookeeperClient, path);
        ZKStatMetaData statMetaData = getNodeStatMetaData(zookeeperClient, path);
        List<ZKACLMetaData> aclMetaData = getNodeAclMetaData(zookeeperClient, path);

        Map<String, Object> nodeInfo = Maps.newHashMap();
        nodeInfo.put(ZK_NODE_DATA, nodeData);
        nodeInfo.put(ZK_STAT_DATA, statMetaData == null ? "" : statMetaData);
        nodeInfo.put(ZK_ACL_DATA, CollectionUtils.isEmpty(aclMetaData) ? "" : aclMetaData);


        return CommonUtils.toJson(nodeInfo);
    }

    @Override
    public String getZkNodeTree(String cacheId, String path) throws Exception {
        CuratorFramework client = getZookeeperClient(cacheId);
        if (!checkNodeExist(client, path)) {
            return CommonUtils.toJson(ImmutableList.of());
        }
        TreeNode treeRoot = new TreeNode();
        treeRoot.setParentPath(StringUtils.EMPTY);
        treeRoot.setFullPath(path);
        treeRoot.setPath(path);
        buildTreeRoot(treeRoot,convertTree(client,path));

        System.out.println(treeRoot.getChildTreeNodes().size());
        System.out.println(CommonUtils.toJson(treeRoot));
        return CommonUtils.toJson(treeRoot);
    }

    @Override
    public boolean updateZkNodeData(String cacheId, String path, String data) throws Exception {
        CuratorFramework client = getZookeeperClient(cacheId);
        if (!checkNodeExist(client, path)) {
            return Boolean.FALSE;
        }
        Stat stat = client.setData().forPath(path,
                data.getBytes(Charset.forName(CommonConstant.ENCODE_CHARSET)));
        return stat != null;
    }

    @Override
    public boolean addZkNode(String cacheId, String path, String data, int modeFlag) throws Exception {
        CuratorFramework client = getZookeeperClient(cacheId);
        if (null == client) {
            return Boolean.FALSE;
        }
        if (checkNodeExist(client, path)) {
            return null != client.setData()
                    .forPath(path, data.getBytes(Charset.forName(CommonConstant.ENCODE_CHARSET)));
        }
        BackgroundPathAndBytesable<String> bytesable = client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.fromFlag(modeFlag))
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE);
        if (StringUtils.isBlank(data)) {
            bytesable.forPath(path);
        } else {
            bytesable.forPath(path, data.getBytes(Charset.forName(CommonConstant.ENCODE_CHARSET)));
        }
        return Boolean.TRUE;
    }

    @Override
    public boolean deleteZkNode(String cacheId, String path) throws Exception {
        CuratorFramework client = getZookeeperClient(cacheId);
        if (!checkNodeExist(client, path)) {
            return Boolean.FALSE;
        }

        client.delete().guaranteed()
                .deletingChildrenIfNeeded()
                .forPath(path);

        return Boolean.TRUE;
    }

    private List<TreeNode> convertTree(CuratorFramework client, String path) throws Exception {
        List<String> childPaths = client.getChildren().forPath(path);
        //System.out.println(childPaths);
        List<TreeNode> treeNodes = Lists.newArrayList();
        if (CollectionUtils.isEmpty(childPaths)) {
            System.out.println("child is empty");
            return treeNodes;
        }
        for (String childPath : childPaths) {
            TreeNode treeNode = new TreeNode();
            treeNode.setPath(childPath);
            treeNode.setFullPath(String.format("%s%s%s",
                    CommonConstant.DEFAULT_ROOT_PATH.equals(path) ? "" : path,
                    CommonConstant.DEFAULT_ROOT_PATH, childPath));
            treeNode.setParentPath(path);

            buildTreeRoot(treeNode, convertTree(client,treeNode.getFullPath()));
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    private void buildTreeRoot(TreeNode treeNode, List<TreeNode> childTreeNodes) {
        if (CollectionUtils.isNotEmpty(childTreeNodes)) {
            treeNode.setExpanded(Boolean.TRUE);
            treeNode.setChildTreeNodes(childTreeNodes);
        } else {
            treeNode.setExpanded(Boolean.FALSE);
            treeNode.setChildTreeNodes(ImmutableList.of());
        }
    }

    private String getNodeData(CuratorFramework zookeeperClient, String path) throws Exception {
        byte[] data = zookeeperClient.getData().forPath(path);
        if (null == data || data.length <= 0) {
            return "";
        }
        String nodeData = new String(data, Charset.forName(CHARSET_ENCODE));
        if (StringUtils.isBlank(nodeData)) {
            nodeData = "";
        }
        return nodeData;
    }

    private ZKStatMetaData getNodeStatMetaData(CuratorFramework zookeeperClient, String path) throws Exception {
        Stat stat = zookeeperClient.checkExists().forPath(path);
        if (null == stat) {
            return null;
        }
        return new ZKStatMetaData(stat);
    }

    private List<ZKACLMetaData> getNodeAclMetaData(CuratorFramework zookeeperClient, String path) throws Exception {
        if (!checkNodeExist(zookeeperClient, path)) {
            return Lists.newArrayList();
        }
        List<ACL> acls = zookeeperClient.getACL().forPath(path);
        if (CollectionUtils.isEmpty(acls)) {
            return Lists.newArrayList();
        }

        return acls.stream().map(acl -> {
            ZKACLMetaData zkaclMetaData = new ZKACLMetaData();
            zkaclMetaData.setId(acl.getId().getId());
            zkaclMetaData.setScheme(acl.getId().getScheme());
            StringBuilder permission = new StringBuilder();
            int perms = acl.getPerms();
            if ((perms & ZooDefs.Perms.READ) == ZooDefs.Perms.READ) {
                permission.append("Read, ");
            }
            if ((perms & ZooDefs.Perms.WRITE) == ZooDefs.Perms.WRITE) {
                permission.append("Write, ");
            }
            if ((perms & ZooDefs.Perms.CREATE) == ZooDefs.Perms.CREATE) {
                permission.append("Create, ");
            }
            if ((perms & ZooDefs.Perms.DELETE) == ZooDefs.Perms.DELETE) {
                permission.append("Delete, ");
            }
            if ((perms & ZooDefs.Perms.ADMIN) == ZooDefs.Perms.ADMIN) {
                permission.append("Admin, ");
            }
            if ((perms & ZooDefs.Perms.ALL) == ZooDefs.Perms.ALL) {
                permission.append("ALL ");
            }
            permission.append("Authority.");
            zkaclMetaData.setPermission(permission.toString());

            return zkaclMetaData;
        }).collect(Collectors.toList());


    }


    @PostConstruct
    public void test() {
        System.out.println(cacheService.toString());
    }


    private boolean checkNodeExist(CuratorFramework client, String path) throws Exception {
        if (null == client || StringUtils.isBlank(path)) {
            return false;
        }
        return null != client.checkExists().forPath(path);
    }

    private CuratorFramework getZookeeperClient(String cacheId) throws SQLException {
        if (StringUtils.isBlank(cacheId)) {
            return null;
        }
        CuratorFramework client = (CuratorFramework) cacheService.mget(cacheId);

        if (null == client) {
            System.out.println("miss hint");
            ZKSource zkSource = zookeeperSourceDao.selectById(cacheId);
            if (null == zkSource) {
                return null;
            }
            client = CuratorFrameworkFactory.builder()
                    .connectString(zkSource.getConnectStr().trim())
                    .sessionTimeoutMs((int) zkSource.getSessiontimeout())
                    //.connectionTimeoutMs(5000)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
            client.start();
            cacheService.set(cacheId, client);

        } else {
            System.out.println("hint");
        }
        CuratorFrameworkState state = client.getState();
        if (state.equals(CuratorFrameworkState.STOPPED)) {
            System.out.println("client stopped");
            client.start();
        }
        return client;
    }


    @Override
    public void destroy() {
        log.info("destroy resource");
        try {
            List<ZKSource> zkSources = zookeeperSourceDao.selectAll();
            if (CollectionUtils.isEmpty(zkSources)) {
                return;
            }
            List<String> ids = zkSources.stream().map(ZKSource::getId).collect(Collectors.toList());
            cacheService.mget(ids).forEach((k, v) -> {
                if (null == v) {
                    return;
                }
                if (v instanceof CuratorFramework) {
                    ((CuratorFramework) v).close();
                }
            });
            cacheService.destory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
