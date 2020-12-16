package cn.edu.bjtu.battledamage.service;

import cn.edu.bjtu.battledamage.entity.Photos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PhotosService {
    List<Photos> findAll();
    void deleteByName(String name);
    void save(String name,String info,String institution);
}
