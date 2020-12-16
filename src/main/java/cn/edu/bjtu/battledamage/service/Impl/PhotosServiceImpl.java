package cn.edu.bjtu.battledamage.service.Impl;

import cn.edu.bjtu.battledamage.entity.Photos;
import cn.edu.bjtu.battledamage.service.PhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class PhotosServiceImpl implements PhotosService {
    
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Photos> findAll() {
        return mongoTemplate.findAll(Photos.class,"photos");
    }

    @Override
    public void deleteByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        mongoTemplate.remove(query,Photos.class,"photo");
    }

    @Override
    public void save(String name, String info, String institution) {
        Photos photos = new Photos();
        photos.setInfo(info);
        photos.setInstitution(institution);
        photos.setName(name);
    }
}
