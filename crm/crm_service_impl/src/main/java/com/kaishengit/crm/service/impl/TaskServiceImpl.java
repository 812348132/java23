package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public void saveTask(Task task) {
        task.setCreateTime(new Date());
        task.setDone(false);
        taskMapper.saveTask(task);
    }

    @Override
    public List<Task> findTaskByCustId(Integer id) {
        return taskMapper.findTaskByCustId(id);
    }

    @Override
    public List<Task> findTaskBySalesId(Integer id) {
        return taskMapper.findTaskBySalesId(id);
    }


    @Override
    public Task findAllTaskById(Integer id) {
        return taskMapper.findAllTaskById(id);
    }

    @Override
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    @Override
    public List<Task> findAllTaskByAccountId(Integer id, boolean showAll) {
        return taskMapper.findAllTaskByAccountId(id,showAll);
    }

    @Override
    public Task findTaskById(Integer id) {
        return taskMapper.findById(id);
    }

    @Override
    public void delTask(Task task) {
        taskMapper.delTask(task);
    }

}
