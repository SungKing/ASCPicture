package cn.org.wangsong.util;

import cn.org.wangsong.entity.BaseWord;
import cn.org.wangsong.entity.Perceptual;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author Created by song.wang
 * @Create Date 2018/12/20 15:28
 */
public class ImgUtil {
    public static String NEW_LINE = System.getProperty("line.separator");

    /**
     * 转化为灰度图片
     * @param originPath
     * @param targetPath
     */
    public static void genBrayImg(String originPath,String targetPath) {
        if (originPath==null || targetPath == null){
            System.out.println("路径值为空");
            return;
        }
        File out = new File(targetPath);

        BufferedImage image ;
        try {
            image = ImageIO.read(new File(originPath));
        } catch (IOException e) {
            System.out.println("读取图片异常");
            System.out.println("图片路径："+originPath);
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i=image.getMinX();i<width;i++){
            for (int j=image.getMinY();j<height;j++){
                int rgb1 = image.getRGB(i, j);
                bufferedImage.setRGB(i,j,rgb1);
            }
        }

        wirteImg(out, bufferedImage);

    }
    //将图片写入文件
    public static void wirteImg(File out, BufferedImage bufferedImage) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(out);
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
            jpegEncoder.encode(bufferedImage);

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
    //转化为黑白图片
    //todo 黑白反转
    public static void genBlackImg(String originPath,String targetPath,float proportion){
        if (proportion<=0 || proportion>1){
            //不精确的判断，1凡事有颜色的就是黑色
            proportion=1;
        }
        if (originPath==null || targetPath == null){
            System.out.println("路径值为空");
            return;
        }
        File out = new File(targetPath);

        BufferedImage image ;
        try {
            image = ImageIO.read(new File(originPath));
        } catch (IOException e) {
            System.out.println("读取图片异常");
            System.out.println("图片路径："+originPath);
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bufferedImage.getGraphics();
        for (int i=image.getMinX();i<width;i++){
            for (int j=image.getMinY();j<height;j++){
                int rgb = image.getRGB(i, j);
                int a = (rgb & 0xff000000) >> 24;//这个其实不必了
                int r = (rgb & 0Xff0000)>>16;
                int g = (rgb & 0Xff00)>>8;
                int b = (rgb & 0Xff);
                //System.out.println(Integer.toHexString(rgb));
                if (r/255.0 >= proportion && g/255.0 >= proportion && b/255.0 >= proportion){
                    graphics.setColor(Color.WHITE);
                }else{
                    graphics.setColor(Color.BLACK);
                }
                graphics.drawLine(i,j,i,j);
            }
        }

        wirteImg(out,bufferedImage);

    }
    //实现效果不理想
    @Deprecated
    public static void genLineImg(String originPath,String targetPath,float proportion){
        if (proportion<=0 || proportion>1){
            //不精确的判断，1凡事有颜色的就是黑色
            proportion=1;
        }
        if (originPath==null || targetPath == null){
            System.out.println("路径值为空");
            return;
        }
        File out = new File(targetPath);

        BufferedImage image ;
        try {
            image = ImageIO.read(new File(originPath));
        } catch (IOException e) {
            System.out.println("读取图片异常");
            System.out.println("图片路径："+originPath);
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bufferedImage.getGraphics();
        for (int i=image.getMinX();i<width;i++){
            for (int j=image.getMinY();j<height;j++){
                int rgb = image.getRGB(i, j);
                int a = (rgb & 0xff000000) >> 24;//这个其实不必了
                int r = (rgb & 0Xff0000)>>16;
                int g = (rgb & 0Xff00)>>8;
                int b = (rgb & 0Xff);

                //计算与周围像素的误差
                long rsum = 0;
                long gsum = 0;
                long bsum = 0;
                int count = 0;
                for (int i1 = Math.max(i,i-10);i1<=Math.min(i+10,width-1);i1++){
                    for (int j1=Math.max(j,j-10);j1<=Math.min(j+10,height-1);j1++){
                        int rgb1 = image.getRGB(i1, j1);
                        rsum+=(rgb1 & 0Xff0000)>>16;
                        gsum+=(rgb1 & 0Xff00)>>8;
                        bsum+=(rgb1 & 0Xff);
                        count++;
                    }
                }
                double rProp = r/(rsum*1.0/count);
                double gProp = g/(gsum*1.0/count);
                double bProp = b/(bsum*1.0/count);
                if ((rProp+gProp+bProp)/3<proportion){
                    graphics.setColor(Color.WHITE);
                }else{
                    graphics.setColor(Color.BLACK);
                }




                //System.out.println(Integer.toHexString(rgb));
//                if (r/255.0 >= proportion || g/255.0 >= proportion ||b/255.0 >= proportion){
//                    graphics.setColor(Color.WHITE);
//                }else{
//                    graphics.setColor(Color.BLACK);
//                }
                graphics.drawLine(i,j,i,j);
            }
        }

        wirteImg(out,bufferedImage);
    }
    //这个效果比较好
    public static void genLineImgNew(String originPath,String targetPath,float proportion){
        if (proportion<=0 || proportion>1){
            //不精确的判断，1凡事有颜色的就是黑色
            proportion=1;
        }
        if (originPath==null || targetPath == null){
            System.out.println("路径值为空");
            return;
        }
        File out = new File(targetPath);

        BufferedImage bufferedImage = getBufferedLineImage(originPath, proportion);
        if (bufferedImage == null) return;

        wirteImg(out,bufferedImage);

    }
    //0.07是个比较好的比例
    private static BufferedImage getBufferedLineImage(String originPath, float proportion) {
        BufferedImage image ;
        try {
            image = ImageIO.read(new File(originPath));
        } catch (IOException e) {
            System.out.println("读取图片异常");
            System.out.println("图片路径："+originPath);
            return null;
        }

        int width = image.getWidth();
        int height = image.getHeight();


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bufferedImage.getGraphics();
        for (int i=image.getMinX();i<width;i++){
            for (int j=image.getMinY();j<height;j++){
                int grey = transformGrey(image, i, j);
                int i1,j1;
                if (i<width-1){
                    i1 =i+1;
                }else{
                    i1=i-1;
                }
                if (j<height-1){
                    j1 = j+1;
                }else{
                    j1=j-1;
                }
                int grey1 = transformGrey(image, i1, j1);
                if (Math.abs(grey-grey1)/255.0>proportion){
                    graphics.setColor(Color.BLACK);
                }else{
                    graphics.setColor(Color.WHITE);
                }
                graphics.drawLine(i,j,i,j);
            }
        }
        return bufferedImage;
    }

    private static int transformGrey(BufferedImage image, int i, int j) {
        int rgb = image.getRGB(i, j);
        //舍弃掉了 阿尔法 值
        int r = (rgb & 0Xff0000)>>16;
        int g = (rgb & 0Xff00)>>8;
        int b = (rgb & 0Xff);
        //return (r+g+b)/3;
        //这个据说最符合人类的感觉
        return new Double(0.299*r+0.587*g+0.114*b).intValue();
    }

    public static void genASCII(String originPath,String targetPath) throws IOException {

        if (originPath==null || targetPath == null){
            System.out.println("路径值为空");
            return;
        }

        File out = new File(targetPath);
        if (!out.exists()){
            out.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(out);
        BufferedImage image ;
        try {
            image = ImageIO.read(new File(originPath));
        } catch (IOException e) {
            System.out.println("读取图片异常");
            System.out.println("图片路径："+originPath);
            return;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println(width);
        System.out.println("X");
        System.out.println(height);
        //16*16
        for(int j=0;j<height-16;j+=16){
            for(int i=0;i<width-16;i+=16){
                BufferedImage sub = image.getSubimage(i, j, 16, 16);
                char c = calSimilarChar(sub);
                fileWriter.write(new String(new char[]{c,' '}));
            }
            System.out.println(j/16);
            fileWriter.write(NEW_LINE);
        }

        fileWriter.flush();
        fileWriter.close();


    }

    /**
     * 计算出醉相近的替换方案
     * @param img
     * @return
     */
    private static char calSimilarChar(BufferedImage img){
        Perceptual perceptual = ImageFingerPrintUtil.perceptualHashAlgorithm16(img);
        long min = Long.MAX_VALUE;
        BaseWord target = BaseWord.V14;//空
        for (BaseWord baseWord : BaseWord.values()) {
            long diff = 0;
            for (int i = 0;i<4;i++){
                long[] h1 = baseWord.getP().getHash();
                long[] p1 = perceptual.getHash();
                diff +=Math.abs(h1[i]-p1[i]);
            }
            if (min>diff){
                min = diff;
                target = baseWord;
            }
        }


        return target.getC();

    }

    /**
     * 计算字符的特征值
        一点都不严谨
        todo 待完成
     * 用汉明距离代替
     * @param c
     * @return
     */
    @Deprecated
    private static int calFlagVal(char c){
        BufferedImage bi = new BufferedImage(8, 8, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bi.getGraphics();
        graphics.setColor(Color.WHITE);
        for (int i =0;i<=8;i++){
            graphics.drawLine(0,i,8,i);
        }
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("宋体",Font.PLAIN,60));
        graphics.drawString(new String(new char[]{c}),2,6);

        int flagVal = 0;

        int width = bi.getWidth();
        int height = bi.getHeight();
        for (int i = bi.getMinX();i<width;i++){
            for(int j = bi.getMinY();j<height;j++){
                int rgb = bi.getRGB(i, j);
                if (rgb == 0){//黑色
                    flagVal += i*64+j;
                }
            }
        }


        return 0;
    }

    /**
     * 将指定字符生成图片
     * @param s
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage genWordPic(String s,int width,int height){
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics graphics = bi.getGraphics();
        graphics.setColor(Color.WHITE);
        for (int i =0;i<=width;i++){
            graphics.drawLine(0,i,height,i);
        }
        graphics.setColor(Color.BLACK);

        Font font = new Font("宋体", Font.PLAIN, width);

        graphics.setFont(font);
        //获取文字大小
        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        int x = (width - fontMetrics.stringWidth(s)) /2;
        int y = (height - fontMetrics.getHeight()) /2 + fontMetrics.getAscent();
        graphics.drawString(s,x,y);

        return bi;
    }

    public static void genASCIINew(String originPath,String targetPath,int xw,int yh) throws IOException {

        String ss = " `*@#.aO|-";
        HashMap<Character, BufferedImage> map = new HashMap<Character, BufferedImage>();
        for (char c : ss.toCharArray()) {
            BufferedImage bufferedImage = genWordPic(new String(new char[]{c}), xw, yh);
            map.put(c,bufferedImage);
        }

        File out = new File(targetPath);
        if (!out.exists()){
            out.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(out);
        //0。1对字符比较友好
        BufferedImage image = getBufferedLineImage(originPath, 0.02f);
        int width = image.getWidth();
        int height = image.getHeight();
        //
        for (int j=0;j<height-xw;j+=xw){
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<width-yh;i+=yh){

                BufferedImage sub = image.getSubimage(i, j, xw, yh);
                char c = getSimpChar(sub, map);
                sb.append(c);
            }
            fileWriter.write(sb.toString());
            fileWriter.write(NEW_LINE);
        }
        fileWriter.flush();
        fileWriter.close();
    }


    private static char getSimpChar(BufferedImage image,HashMap<Character, BufferedImage> map){
        char c = ' ';
        int min = Integer.MAX_VALUE;
        for (Map.Entry<Character, BufferedImage> entry : map.entrySet()) {
            int temp = 0;
            BufferedImage bi = entry.getValue();
            for (int i1 = 0;i1<bi.getWidth();i1++){
                for (int j1=0;j1<bi.getHeight();j1++){
                    if (bi.getRGB(i1,j1)!=image.getRGB(i1,j1)){
                        temp++;
                    }
                }
            }
            if (min>temp){
                min = temp;
                c = entry.getKey();
            }
        }
        return c;
    }
}
