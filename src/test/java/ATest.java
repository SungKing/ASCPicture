
import cn.org.wangsong.entity.Perceptual;
import cn.org.wangsong.util.ImageFingerPrintUtil;
import cn.org.wangsong.util.ImgUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author Created by song.wang
 * @Create Date 2018/12/20 18:41
 */
public class ATest {
    @Test
    public void test01(){
        final int width = 8;
        final int height = 8;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bi.getGraphics();
        graphics.setColor(Color.WHITE);
        for (int i =0;i<=width;i++){
            graphics.drawLine(0,i,height,i);
        }
        graphics.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.PLAIN, width);
        graphics.setFont(font);
        //获取文字大小 *@
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        int x = (width - fontMetrics.stringWidth(new String("#"))) /2;
        int y = (height - fontMetrics.getHeight()) /2 + fontMetrics.getAscent();

        graphics.drawString(new String("#"),x,y);
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
    @Test
    public void test02(){
        //System.out.println(0Xffffffff);
        long ab = 0Xffff0000;
        int count = 0;
        while (ab!=0Xffffffff){//ab!=-1
            ab |= (ab+1);
            System.out.println(Long.toHexString(ab));
            count++;
        }
        System.out.println(count);
    }
    @Test
    public void test03(){
        String s = "|#*`-+.'/:^?_=";
        int i = 1;
        for (char c : s.toCharArray()) {
            BufferedImage bi = ImgUtil.genWordPic(new String(new char[]{c}), 16, 16);
            File f = new File("/Users/song.wang/Downloads/" + c + "0.jpg");
            ImgUtil.wirteImg(f,bi);
            BufferedImage img1 = bi.getSubimage(0, 0, 8, 8);
            File f1 = new File("/Users/song.wang/Downloads/" + c + "1.jpg");
            ImgUtil.wirteImg(f1,img1);
            BufferedImage img2 = bi.getSubimage(8, 0, 8, 8);
            File f2 = new File("/Users/song.wang/Downloads/" + c + "2.jpg");
            ImgUtil.wirteImg(f2,img2);
            BufferedImage img3 = bi.getSubimage(0, 8, 8, 8);
            File f3 = new File("/Users/song.wang/Downloads/" + c + "3.jpg");
            ImgUtil.wirteImg(f3,img3);
            BufferedImage img4 = bi.getSubimage(8, 8, 8, 8);
            File f4 = new File("/Users/song.wang/Downloads/" + c + "4.jpg");
            ImgUtil.wirteImg(f4,img4);
            Perceptual perceptual = ImageFingerPrintUtil.perceptualHashAlgorithm16(bi);
            //System.out.println(new String(new char[]{c})+ "======" + perceptual);
            System.out.println(String.format("V%d('%s',new Perceptual(new long[]{%d,%d,%d,%d})),",i,c,
                    perceptual.getHash()[0],
                    perceptual.getHash()[1],
                    perceptual.getHash()[2],
                    perceptual.getHash()[3]

                    ));
            i++;
            break;
            //break;
//            Perceptual perceptual = ImageFingerPrintUtil.perceptualHashAlgorithm16(bi);
//            System.out.println(new String(new char[]{c})+ "======" + perceptual);
        }
    }

    @Test
    public void test04() throws IOException {
        //先进的算法并不能解决生产力
        //ImgUtil.genASCIINew("/Users/song.wang/Downloads/IMG_20181117_175851-black.jpg","/Users/song.wang/Downloads/123.txt",20,20);
        ImgUtil.genASCIIBYHM2("/Users/song.wang/Downloads/IMG_20181117_175851.jpg","/Users/song.wang/Downloads/123qq.jpg",8);
        //ImgUtil.genASCIIPicByHanming("/Users/song.wang/Downloads/IMG_20181117_175851.jpg","/Users/song.wang/Downloads/123.jpg",16,16);
    }

    @Test
    public void test05() throws IOException {
        ImgUtil.genASCIINew("F:\\Downloads\\1875164769.jpg","F:\\Downloads\\123.txt",5,5);
    }
    @Test
    public void test06(){
        ImgUtil.genLineImgNew("F:\\Downloads\\1875164769.jpg","F:\\Downloads\\1875164769-line.jpg",0.02f);
    }

    @Test
    public void test07(){
        ImgUtil.genLineImgNew("/Users/song.wang/Downloads/1234q.jpg","/Users/song.wang/Downloads/1234q-line.jpg",0.02f);
    }

}
