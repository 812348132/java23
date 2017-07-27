package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.exception.ServiceException;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<Disk> findDiskByPid(Integer pid) {
        return diskMapper.findDiskByPid(pid);
    }

    @Override
    public Disk findDiskById(Integer pid) {
        return diskMapper.findDiskById(pid);
    }

    @Override
    public void saveNewFolder(Disk disk) {
        disk.setType(Disk.FILETYPE_DIR);
        disk.setUpdateTime(new Date());
        diskMapper.saveNewFolder(disk);
    }

    @Override
    @Transactional
    public void saveNewFile(InputStream inputStream, Disk disk) {
        //重命名
        String fileName = disk.getName();
        String saveName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        //存入数据库
        disk.setUpdateTime(new Date());
        disk.setType(Disk.FILETYPE_FILE);
        disk.setSaveName(saveName);
        disk.setDownloadCount(0);
        diskMapper.saveNewFolder(disk);
        //存入磁盘
        try{
            OutputStream outputStream = new FileOutputStream(new File(uploadPath,saveName));
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            throw new ServiceException("文件上传异常");
        }

    }

    @Override
    public void updateDiskById(Disk disk) {
        disk.setUpdateTime(new Date());
        diskMapper.updateDisk(disk);
    }

    @Override
    @Transactional
    public void donwloadFile(Disk disk, OutputStream outputStream) {
        //跟新下载数量
        disk.setDownloadCount(disk.getDownloadCount() + 1);
        diskMapper.updateDisk(disk);

        try{
            //下载文件
            File file = new File(uploadPath,disk.getSaveName());
            //判断文件是否存在
            if(file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                IOUtils.copy(inputStream,outputStream);
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } else {
                throw new ServiceException("文件不存在");
            }
        } catch (IOException e) {
            throw new ServiceException("下载异常");
        }

    }

    @Override
    @Transactional
    public void delDiskById(Integer id) {
        Disk disk = diskMapper.findDiskById(id);
        delAll(disk);
        diskMapper.delDiskById(disk.getId());
    }

    private void delAll(Disk disk) {
        if(disk.getType().equals(Disk.FILETYPE_FILE)){
            diskMapper.delDiskById(disk.getId());
        } else {
            List<Disk> disks = diskMapper.findDiskByPid(disk.getId());
            for (Disk ds : disks) {
                delAll(ds);
                diskMapper.delDiskById(ds.getId());
            }
        }
    }
}
