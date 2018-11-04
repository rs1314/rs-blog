package cn.rs.blog.commoms.utils;

/**
 * Created by rs
 */
public enum ActionLogType {
    TEXT(0),ARTICLE(1),WEIBO(2),GROUP_TOPIC(4);
    private Integer value;

    ActionLogType(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
