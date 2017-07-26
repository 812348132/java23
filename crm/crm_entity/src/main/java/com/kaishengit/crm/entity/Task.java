package com.kaishengit.crm.entity;

import java.util.Date;

public class Task {

    private Integer id;
    private String title;
    private Integer salesRecordId;
    private Integer accountId;
    private Integer customerId;
    private boolean done;
    private String doneTime;
    private String alertTime;
    private Date createTime;
    private Customer customer;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SalesRecord getSalesRecord() {
        return salesRecord;
    }

    public void setSalesRecord(SalesRecord salesRecord) {
        this.salesRecord = salesRecord;
    }

    public String getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(String doneTime) {
        this.doneTime = doneTime;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }


    private SalesRecord salesRecord;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalesRecordId() {
        return salesRecordId;
    }

    public void setSalesRecordId(Integer salesRecordId) {
        this.salesRecordId = salesRecordId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }


}
