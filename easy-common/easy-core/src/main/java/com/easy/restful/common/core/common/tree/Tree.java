package com.easy.restful.common.core.common.tree;

import com.easy.restful.common.core.constant.CommonConst;

/**
 * Tree 插件数据
 *
 * @author tengchong
 * @date 2020/10/27
 */
public class Tree {

    /**
     * 整个树范围内的所有节点的 key 值不能重复！
     */
    private String id;

    /**
     * 父id
     */
    private String pId;

    /**
     * 标题
     */
    private String title;

    /**
     * 节点的 class
     */
    private String clazz;

    /**
     * 节点的 style
     */
    private String style;

    /**
     * 当树为 checkable 时，设置独立节点是否展示 Checkbox
     */
    private Boolean checkable;

    /**
     * 禁掉 checkbox
     */
    private boolean disableCheckbox = false;

    /**
     * 禁掉响应
     */
    private boolean disabled = false;

    /**
     * 自定义图标。可接收组件，props 为当前节点 props
     */
    private String icon;

    /**
     * 设置为叶子节点(设置了loadData时有效)
     */
    private Object isLeaf;

    /**
     * 设置节点是否可被选中
     */
    private boolean selectable = true;

    private String type;
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Boolean getCheckable() {
        return checkable;
    }

    public void setCheckable(Boolean checkable) {
        this.checkable = checkable;
    }

    public boolean isDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Object getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Object isLeaf) {
        if (isLeaf != null && isLeaf instanceof String) {
            this.isLeaf = !CommonConst.FALSE.equals(isLeaf);
        } else {
            this.isLeaf = isLeaf;
        }
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
