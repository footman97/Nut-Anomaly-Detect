package com.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import org.apache.commons.io.FileUtils;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


@Controller
public class Controllers {

    @ResponseBody
    @RequestMapping(value = "/upload" ,method = RequestMethod.POST)
    public Map<String, Object> uploadApkFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        Map<String, Object> json = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        /** 页面控件的文件流* */
        MultipartFile multipartFile = null;
        Map map =multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFile=(MultipartFile) map.get(obj);      // 只取最后一个?

        }
        /** 获取文件的后缀* */
        String filename = multipartFile.getOriginalFilename();

        InputStream inputStream;
        String path = "";
        try {

            inputStream = multipartFile.getInputStream();
            // 上传文件目录
            File dir = new File("/Users/liujun/helloWorld/src/main/resources/static/files");
            // 生成随机名字文件
            File tmpFile = File.createTempFile(filename,filename.substring(filename.lastIndexOf(".")),dir);
            FileUtils.copyInputStreamToFile(inputStream, tmpFile);

            path = tmpFile.getPath();


        } catch (Exception e) {
            e.printStackTrace();
        }

        json.put("message", "应用上传成功");
        json.put("path",path);

        return json;
    }

}


//    // for method1(without bootstrap-inputfile)
//    @RequestMapping("/upload")
//    @ResponseBody
//    public String upload(@RequestParam("file") MultipartFile file) {
//        String fileName = file.getOriginalFilename();
//        if(fileName.indexOf("\\") != -1){
//            fileName = fileName.substring(fileName.lastIndexOf("\\"));
//        }
//        String filePath = "src/main/resources/static/files/";
//        File targetFile = new File(filePath);
//        if(!targetFile.exists()){
//            targetFile.mkdirs();
//        }
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream(filePath+fileName);
//            out.write(file.getBytes());
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "上传失败";
//        }
//        return "上传成功!";
//    }


