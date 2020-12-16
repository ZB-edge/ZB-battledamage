package cn.edu.bjtu.battledamage.controller;

import cn.edu.bjtu.battledamage.entity.Photo;
import cn.edu.bjtu.battledamage.service.PhotoService;
import cn.edu.bjtu.battledamage.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/damage")
@RestController
public class BattleDamageController {

    @Autowired
    PhotoService photoService;

    @CrossOrigin
    @PostMapping("/upload")
    public String photoUpload(@RequestParam("file") MultipartFile file){
        String folder = "E:/opt/photo";
        File path = new File(folder);
        if(!path.exists()){
            path.mkdir();
        }
        File f = new File(path,StringUtils.getRandomString(4) + file.getOriginalFilename().substring(file.getOriginalFilename().length()-4));

        try {
            file.transferTo(f);
            String url = f.getName();
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @CrossOrigin
    @PostMapping("/save")
    public String save(@RequestBody Photo photo){
        photoService.save(photo);
        return "上传成功！";
    }

    @CrossOrigin
    @PostMapping("/show")
    public List<Photo> show(){
        return photoService.findAll();
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public String delete(String name){
        photoService.deleteByName(name);
        return "删除成功！";
    }

    @CrossOrigin
    @PostMapping("/export")
    public String export(){
        return "导出到云端成功";
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
