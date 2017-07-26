package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Task;

import java.util.List;

public interface TaskService {
    void saveTask(Task task);

    List<Task> findTaskByCustId(Integer id);

    List<Task> findTaskBySalesId(Integer id);


    Task findAllTaskById(Integer id);

    void updateTask(Task task);

    List<Task> findAllTaskByAccountId(Integer id, boolean showAll);

    Task findTaskById(Integer id);

    void delTask(Task task);
}
