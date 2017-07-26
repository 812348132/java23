package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    void saveTask(Task task);

    List<Task> findTaskByCustId(@Param("customerId") Integer id);

    List<Task> findTaskBySalesId(@Param("salesRecordId") Integer id);

    List<Task> findAllTaskByAccountId(@Param("accountId") Integer id,@Param("showAll") boolean showAll);

    Task findAllTaskById(Integer id);

    void updateTask(Task task);

    Task findById(Integer id);

    void delTask(Task task);
}
