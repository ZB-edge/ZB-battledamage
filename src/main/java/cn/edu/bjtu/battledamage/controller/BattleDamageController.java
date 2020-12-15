package cn.edu.bjtu.battledamage.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/damage")
@RestController
public class BattleDamageController {

    @CrossOrigin
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
