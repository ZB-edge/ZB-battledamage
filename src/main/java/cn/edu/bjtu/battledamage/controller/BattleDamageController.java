package cn.edu.bjtu.battledamage.controller;

import cn.edu.bjtu.battledamage.entity.FileDescriptor;
import cn.edu.bjtu.battledamage.entity.FileSavingMsg;
import cn.edu.bjtu.battledamage.entity.Photo;
import cn.edu.bjtu.battledamage.service.CloudService;
import cn.edu.bjtu.battledamage.service.FileService;
import cn.edu.bjtu.battledamage.service.PhotoProcess;
import cn.edu.bjtu.battledamage.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    @Autowired
    FileService fileService;

    @CrossOrigin
    @PostMapping("/upload")
    public String photoUpload(@RequestParam("file") MultipartFile file){
        String folder = "/opt/photo";
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
        String[] p = photo.getName().split("[.]");
        Photo ph = photoService.findByName(p[0]);
        if (ph != null){
            return "图像名称重复，请重新上传！";
        }else {
            photoService.save(photo);
            return "上传成功！";
        }
    }

    @CrossOrigin
    @GetMapping("/show")
    public List<Photo> show(){
        return photoService.findAll();
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public String delete(String name) throws IOException, InterruptedException {
        photoService.deleteByName(name);
        String command = "";
        command = "rm -rf /opt/photo/" + name + ".jpg";
        String[] cmdArray = new String[]{"/bin/sh", "-c", command};
        Process process = Runtime.getRuntime().exec(cmdArray);
        process.waitFor();
        return "删除成功！";
    }

    @CrossOrigin
    @PostMapping("/export/{institution}")
    public String export(@PathVariable String institution,String name){
        MultiValueMap<String,String> js = new LinkedMultiValueMap<>();
        Photo photo = photoService.findByName(name);
        String file = "/opt/photo/" + photo.getName() + ".jpg";
        String BASE64 = photoProcess.GetBase64(file);
        js.add("photo",BASE64);
        js.add("info",photo.getInfo());
        js.add("institution",institution);
        js.add("name",photo.getName());
        String ip = cloudService.findIp("cloud");
        String url = "http://" + ip + ":8100/api/damage/export";
        String result = restTemplate.postForObject(url,js,String.class);
        assert result != null;
        if(result.equals("成功")){
            photoService.updateStatus(name,"1");
            return "导出云成功";
        }else {
            return "导出云失败";
        }
    }

    @CrossOrigin
    @GetMapping("/service")
    public List<FileDescriptor> inquireService(){
        String path = fileService.getThisJarPath();
        return fileService.getFileList(path,new String[]{"jar"});
    }

    @CrossOrigin
    @PostMapping("/service")
    public List<FileSavingMsg> postService(@RequestParam("file") MultipartFile[] multipartFiles){
        String path = fileService.getThisJarPath();
        return fileService.saveFiles(multipartFiles, path);
    }

    @CrossOrigin
    @PutMapping("/service")
    public void startService(@RequestParam String jarName){
        fileService.execJar(jarName);
    }

    @CrossOrigin
    @DeleteMapping("/service")
    public void killService(@RequestParam int port){
        fileService.killProcessByPort(port);
    }

    @CrossOrigin
    @DeleteMapping("/delete1")
    public String delete1(String name) throws IOException, InterruptedException {
        String command = "";
        command = "rm -rf /opt/zb-backend/" + name;
        String[] cmdArray = new String[]{"/bin/sh", "-c", command};
        Process process = Runtime.getRuntime().exec(cmdArray);
        process.waitFor();
        return "删除成功！";
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
