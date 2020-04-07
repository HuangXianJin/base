package com.huangxj.common.core.model;

import java.util.List;

/**
 * @ClassName: OptionVo
 * @Description:
 * @Author: fangjianrun
 * @Create: 2018/12/19 16:04
 * @Version: 1.0
 **/
public class OptionVo {
    private Object id;
    private Object value;
    private Object text;
    private Object label;
    private Object parentValue;
    private List<OptionVo> children;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public Object getParentValue() {
        return parentValue;
    }

    public void setParentValue(Object parentValue) {
        this.parentValue = parentValue;
    }

    public List<OptionVo> getChildren() {
        return children;
    }

    public void setChildren(List<OptionVo> children) {
        this.children = children;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }
}
