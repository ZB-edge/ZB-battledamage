package cn.edu.bjtu.battledamage.service;

import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PhotoProcess {
    public String GetBase64(String path){
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        String encode = null;
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
            } catch (IOException e) {
            e.printStackTrace();
            } finally {
            try {
                in.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
            }
        return encode;
    }
}
