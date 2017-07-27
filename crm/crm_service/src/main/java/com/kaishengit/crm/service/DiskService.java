package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface DiskService {
    List<Disk> findDiskByPid(Integer pid);

    Disk findDiskById(Integer pid);

    void saveNewFolder(Disk disk);

    void saveNewFile(InputStream inputStream, Disk disk);

    void updateDiskById(Disk disk);

    void donwloadFile(Disk disk, OutputStream outputStream);

    void delDiskById(Integer id);
}
