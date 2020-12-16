package cn.edu.bjtu.battledamage.service.Impl;

import cn.edu.bjtu.battledamage.entity.Photo;
import cn.edu.bjtu.battledamage.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void save(Photo photo) {
        Photo p = new Photo();
        p.setDate(new Date());
        p.setInfo(photo.getInfo());
        p.setName(photo.getName());
        p.setStatus(photo.getStatus());
        p.setPath(photo.getPath());
        mongoTemplate.save(p,"photo");
    }

    @Override
    public List<Photo> findByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Photo.class,"photo");
    }

    @Override
    public void deleteByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));
        mongoTemplate.remove(query, Photo.class,"photo");
    }

    @Override
    public List<Photo> findAll() {
        return mongoTemplate.findAll(Photo.class,"photo");
    }

    @Override
    public void updateStatus(String name,String status) {
        Query query = Query.query(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("status",status);
        mongoTemplate.upsert(query,update,Photo.class,"photo");
    }
}
