package com.etc.jobsystem.controller.back;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;

import java.util.UUID;

@RestController
public class FileController {

    //封装方法，比较方便
    public String func(MultipartFile myfile){
        System.out.println(myfile);
        //tomcat usr/tomcat9/tomcat9/bin
        String path = "http://42.194.222.130:8080/usr/download/spring";

        String type = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + type;
        try {
            //使用Jersey客户端上传文件
            Client client = Client.create();
            WebResource webResource = client.resource(path + "/" + URLEncoder.encode(filename, "utf-8"));
            webResource.put(myfile.getBytes());
            System.out.println("上传成功");
            System.out.println("图片路径==》" + path +"/"+ filename);
        } catch (Exception ex) {
            System.out.println("上传失败");
        }
        //返回路径
        return path +"/"+ filename;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String Upload(@RequestParam("a") MultipartFile myfile) {

       return func(myfile);
    }

    //用户上传头像
//    @PostMapping("/upload")
//    @ResponseBody
//    public String UploadUser(@RequestParam("a") MultipartFile myfile) {
//
//
//    }
}
