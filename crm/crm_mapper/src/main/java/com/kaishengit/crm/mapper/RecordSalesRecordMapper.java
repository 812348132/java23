package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.RecordSalesRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordSalesRecordMapper {
    void saveRecord(RecordSalesRecord recordSalesRecord);

    List<RecordSalesRecord> findBySalesRecordId(@Param("salesRecordId") Integer id);

    void delBySalesRecordId(@Param("salesRecordId") Integer id);
}
