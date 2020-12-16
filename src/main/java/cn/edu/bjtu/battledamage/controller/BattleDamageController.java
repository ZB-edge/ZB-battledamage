package cn.edu.bjtu.battledamage.controller;

import cn.edu.bjtu.battledamage.entity.Photos;
import cn.edu.bjtu.battledamage.service.PhotosService;
import cn.edu.bjtu.battledamage.service.SavePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/damage")
@RestController
public class BattleDamageController {

    @Autowired
    SavePhotoService savePhotoService;
    @Autowired
    PhotosService photosService;

    @CrossOrigin
    @PostMapping("/export")
    public String export(@RequestParam(value = "photo") String base64,@RequestParam(value = "info") String info,@RequestParam(value = "institution") String institution,@RequestParam(value = "name") String name) throws IOException {
        String path = "E://opt/photo/" + name + ".jpg";
        savePhotoService.GeneratePhoto(base64,path);
        photosService.save(name,info,institution);
        if (base64!=null&&institution!=null&&name!=null){
            return "成功";
        }else {
            return "失败";
        }
    }

    @CrossOrigin
    @GetMapping("/show")
    public List<Photos> show(){
        return photosService.findAll();
    }

    @CrossOrigin
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
