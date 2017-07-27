package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.Disk;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiskMapper {
    List<Disk> findDiskByPid(@Param("pid") Integer pid);

    Disk findDiskById(@Param("id") Integer pid);

    void saveNewFolder(Disk disk);

    void updateDisk(Disk disk);

    void delDiskById(Integer id);
}
