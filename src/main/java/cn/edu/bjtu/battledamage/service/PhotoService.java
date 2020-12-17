package cn.edu.bjtu.battledamage.service;

import cn.edu.bjtu.battledamage.entity.Photo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhotoService {
    void save(Photo photo);
    Photo findByName(String name);
    void deleteByName(String name);
    List<Photo> findAll();
    void updateStatus(String name,String status);
}
