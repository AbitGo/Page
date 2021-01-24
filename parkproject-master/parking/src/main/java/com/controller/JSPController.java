package com.controller;

import com.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class JSPController {
    @RequestMapping(value = "/goUploadFile",method = RequestMethod.GET)
    public String goUploadFile(){
        return "uploadimg";
    }
    @PostMapping("testuploadimg")
    @ResponseBody
    public String upload(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "上传失败，请选择文件或文件为空";
        }

        //获取上传文件的文件名，该文件名是由我自己生成的
//        String type = file.getContentType();
//        System.out.println("type:"+type);
        //获取文件名并且加上后缀
        String fileName = file.getName()+".txt";
        String filePath = Constant.filePath;
        //上传文件的目标地址
        File dest = new File(filePath+fileName);
        try{
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败！";
    }
}
