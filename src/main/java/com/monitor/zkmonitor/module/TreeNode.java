package com.monitor.zkmonitor.module;

import lombok.Data;

import java.util.List;

/**
 * @Author: xymj
 * @Date: 2019/6/27 0027 17:41
 * @Description:
 */

@Data
public class TreeNode {

    private String parentPath;
    private String path;
    private String fullPath;
    private boolean expanded;
    private List<TreeNode> childTreeNodes;

}
