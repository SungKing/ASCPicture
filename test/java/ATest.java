import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author Created by song.wang
 * @Create Date 2018/12/20 18:41
 */
public class ATest {
    @Test
    public void test01(){
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bi.getGraphics();
        graphics.setColor(Color.WHITE);
        for (int i =0;i<=100;i++){
            graphics.drawLine(0,i,100,i);
        }
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("宋体",Font.PLAIN,95));
        graphics.drawString(new String("`"),5,75);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("/Users/song.wang/Downloads/test01.jpg");
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
            jpegEncoder.encode(bi);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("写出文件流关闭异常"+e.getMessage());
            }
        }
    }
}
