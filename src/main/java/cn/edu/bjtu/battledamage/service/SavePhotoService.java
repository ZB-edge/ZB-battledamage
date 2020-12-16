package cn.edu.bjtu.battledamage.service;

import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class SavePhotoService {
    public boolean GeneratePhoto(String imgData, String imgFilePath) throws IOException {
        if (imgData == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                    }
                }
            out.write(b);
            } catch (IOException e) {
            e.printStackTrace();
            } finally {
            assert out != null;
            out.flush();
            out.close();
            return true;
        }
    }
}
