package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.dto.AjaxResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/disk")
public class DiskController {

    @Autowired
    private DiskService diskService;

    /**
     * 网盘
     * @param pid
     * @param model
     * @return
     */
    @GetMapping()
    public String diskHome(@RequestParam(required = false,defaultValue = "0",value = "_") Integer pid, Model model){
        List<Disk> diskList = diskService.findDiskByPid(pid);
        if(pid != 0) {
            Disk disk = diskService.findDiskById(pid);
            model.addAttribute("disk",disk);
        }

        model.addAttribute("diskList",diskList);
        return "disk/disk";
    }

    /**
     * 新建文件夹
     * @param disk
     * @return
     */
    @PostMapping("/new/folder")
    @ResponseBody
    public AjaxResult newFolder(Disk disk){
        diskService.saveNewFolder(disk);
        System.out.println(disk.getFileSize());
        List<Disk> diskList = diskService.findDiskByPid(disk.getpId());
        return AjaxResult.success(diskList);
    }

    /**
     * 文件上传
     * @param file
     * @param
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult fileUpload(@RequestParam MultipartFile file,Integer pId,Integer accountId) throws IOException{
        //获取文件名
        String fileName = file.getOriginalFilename();
        //文件大小
        long fileSize = file.getSize();
        //获取输入流
        InputStream inputStream = file.getInputStream();

        Disk disk = new Disk();
        disk.setName(fileName);
        disk.setpId(pId);
        disk.setAccountId(accountId);
        disk.setFileSize(FileUtils.byteCountToDisplaySize(fileSize)); //10000000 -> 1KB
        diskService.saveNewFile(inputStream,disk);
        List<Disk> diskList = diskService.findDiskByPid(disk.getpId());
        return AjaxResult.success(diskList);
    }


    /**
     * 重命名
     * @param disk
     * @param session
     * @return
     */
    @PostMapping("/rename")
    @ResponseBody
    public AjaxResult reName(Disk disk, HttpSession session){
        Account account = (Account) session.getAttribute("curr_user");
        disk.setAccountId(account.getId());
        diskService.updateDiskById(disk);
        return AjaxResult.success();
    }

    /**
     * 删除
     * @param
     * @return
     */
    @PostMapping("/del")
    @ResponseBody
    public AjaxResult del(Integer id){
        diskService.delDiskById(id);
        return AjaxResult.success();
    }


    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam(name = "_") Integer id, HttpServletResponse response) throws IOException{

        //查询Disk
        Disk disk = diskService.findDiskById(id);
        if(disk == null || disk.getType().equals(Disk.FILETYPE_DIR)){
            throw new NotFoundException();
        }
        //设置响应类型
        response.setContentType("application/octet-stream");
        //处理中文乱码  设置响应头
        String fileName = new String(disk.getName().getBytes("UTF-8"),"ISO-8859-1");
        response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        //获取输出流
        OutputStream outputStream = response.getOutputStream();

        diskService.donwloadFile(disk,outputStream);

    }
}
