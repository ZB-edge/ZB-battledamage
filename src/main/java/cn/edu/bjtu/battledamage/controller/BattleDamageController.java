package cn.edu.bjtu.battledamage.controller;

import cn.edu.bjtu.battledamage.entity.Photo;
import cn.edu.bjtu.battledamage.service.CloudService;
import cn.edu.bjtu.battledamage.service.PhotoProcess;
import cn.edu.bjtu.battledamage.service.PhotoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/damage")
@RestController
public class BattleDamageController {

    @Autowired
    PhotoService photoService;
    @Autowired
    PhotoProcess photoProcess;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CloudService cloudService;

    @CrossOrigin
    @PostMapping("/upload")
    public String photoUpload(@RequestParam("file") MultipartFile file){
        String folder = "E:/opt/photo";
        File path = new File(folder);
        if(!path.exists()){
            path.mkdir();
        }
        File f = new File(path, Objects.requireNonNull(file.getOriginalFilename()));

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
    @GetMapping("/show")
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
    @PostMapping("/export/{institution}")
    public String export(@PathVariable String institution,String name){
        JSONObject js = new JSONObject();
        Photo photo = photoService.findByName(name);
        String file = "E://opt/photo/" + photo.getName() + ".jpg";
        System.out.println(file);
        String BASE64 = photoProcess.GetBase64(file);
        js.put("photo",BASE64);
        js.put("info",photo.getInfo());
        js.put("institution",institution);
        js.put("name",photo.getName());
        String ip = cloudService.findIp("cloud");
        String url = "http://" + ip + ":8100/api/damage/export";
        System.out.println(js);
        String result = restTemplate.postForObject(url,js,String.class);
        assert result != null;
        if(result.equals("成功")){
            photoService.updateStatus(name,"1");
            return photo.getStatus();
        }else {
            return photo.getStatus();
        }
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
