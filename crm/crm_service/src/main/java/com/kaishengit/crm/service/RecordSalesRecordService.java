package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.RecordSalesRecord;

import java.util.List;

public interface RecordSalesRecordService {
    List<RecordSalesRecord> findBySalesRecordId(Integer id);

    void saveRecord(RecordSalesRecord recordSalesRecord);
}
