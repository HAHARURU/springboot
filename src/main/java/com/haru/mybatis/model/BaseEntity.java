package com.haru.mybatis.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author HARU
 * @since 2018/5/28
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    private String id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "valid")
    private boolean valid = true;

    @Transient  //表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性.
    private Integer page = 1;

    @Transient
    private Integer size = 9;

    @Version
    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
