import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class TestJava {
    @Test
    public void tesst(){
        File f = new File("E:\\opt\\ebos-logs\\ebos-device");
        File[] temp = f.listFiles();
        if(temp == null){
            System.out.println("目录不存在或它不是一个目录");
        }else {
            for (File filename : temp) {
                System.out.println(filename.getName());
                System.out.println(FormetFileSize(filename.length()));
                System.out.println(filename.getPath());
                System.out.println(getFileTime(filename.lastModified()));
            }
        }
    }

    public String getFileTime(long fileTime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(fileTime);
        return formatter.format(cal.getTime());
    }

    public String FormetFileSize(double fileS) {
        DecimalFormat d = new DecimalFormat("#");
        String fileSizeString = "";
        if(fileS < 1024){
            fileSizeString = d.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString =  (int) Math.ceil(fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = (int) Math.ceil(fileS / 1048576) + "MB";
        } else {
            fileSizeString = (int) Math.ceil(fileS / 1073741824) +"GB";
        }
        return fileSizeString;
    }

    @Test
    public void ttst() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);
    }
}
