package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.SalesRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SalesMapper {

    void saveSales(SalesRecord salesRecord);

    List<SalesRecord> findAll(Map<String, Object> params);

    SalesRecord findById(@Param("id") Integer id);

    void updateSales(SalesRecord salesRecord);

    void delSalesRecoredById(Integer id);

    List<SalesRecord> findAllByCustomerId(@Param("customerId") Integer id);

    void delSalesRecoredByCustomerId(@Param("customerId") Integer id);
}
