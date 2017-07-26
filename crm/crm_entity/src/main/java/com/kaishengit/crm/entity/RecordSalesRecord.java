package com.kaishengit.crm.entity;

import java.util.Date;

public class RecordSalesRecord {

    private Integer id;
    private String content;
    private Integer salersrecordId;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getsalersrecordId() {
        return salersrecordId;
    }

    public void setSalersrecordId(Integer salersrecordId) {
        this.salersrecordId = salersrecordId;
    }

}
