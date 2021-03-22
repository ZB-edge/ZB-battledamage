package cn.edu.bjtu.battledamage.controller;

import cn.edu.bjtu.battledamage.entity.Photos;
import cn.edu.bjtu.battledamage.service.PhotosService;
import cn.edu.bjtu.battledamage.service.SavePhotoService;
import com.alibaba.fastjson.JSONObject;
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
        String path = "/opt/photo/" + name + ".jpg";
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
    @GetMapping("/final/{institution}")
    public JSONObject data1(@PathVariable String institution){
        JSONObject js = new JSONObject();
        switch (institution) {
            case "一旅":
                js.put("08式输送车", 95);
                js.put("08式指挥车", 100);
                js.put("08式步战车", 70);
                js.put("08式突击车", 77);
                break;
            case "二旅":
                js.put("08式输送车", 92);
                js.put("08式指挥车", 97);
                js.put("08式步战车", 100);
                js.put("08式突击车", 88);
                break;
            case "三旅":
                js.put("08式输送车", 100);
                js.put("08式指挥车", 85);
                js.put("08式步战车", 100);
                js.put("08式突击车", 89);
                break;
            default:
                js.put("08式输送车", 96);
                js.put("08式指挥车", 83);
                js.put("08式步战车", 91);
                js.put("08式突击车", 79);
                break;
        }
        return js;
    }

    @CrossOrigin
    @GetMapping("/last/{institution}")
    public JSONObject data2(@PathVariable String institution){
        JSONObject js = new JSONObject();
        switch (institution) {
            case "一旅":
                js.put("08式输送车", 2);
                js.put("08式指挥车", 1);
                js.put("08式步战车", 1);
                js.put("08式突击车", 1);
                break;
            case "二旅":
                js.put("08式输送车", 1);
                js.put("08式指挥车", 2);
                js.put("08式步战车", 1);
                js.put("08式突击车", 1);
                break;
            case "三旅":
                js.put("08式输送车", 1);
                js.put("08式指挥车", 1);
                js.put("08式步战车", 2);
                js.put("08式突击车", 1);
                break;
            default:
                js.put("08式输送车", 1);
                js.put("08式指挥车", 1);
                js.put("08式步战车", 1);
                js.put("08式突击车", 2);
                break;
        }
        return js;
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public String delete(String name) throws IOException, InterruptedException {
        photosService.deleteByName(name);
        String command = "";
        command = "rm -rf /opt/photo/" + name + ".jpg";
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
