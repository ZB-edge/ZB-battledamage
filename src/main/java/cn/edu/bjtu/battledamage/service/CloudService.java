package cn.edu.bjtu.battledamage.service;

import org.springframework.stereotype.Service;

@Service
public interface CloudService {
    String findIp(String name);
}
