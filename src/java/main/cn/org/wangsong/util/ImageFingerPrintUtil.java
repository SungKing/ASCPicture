package cn.org.wangsong.util;

import cn.org.wangsong.entity.Perceptual;

import java.awt.image.BufferedImage;

/**
 * 图像指纹算法
 * @Author Created by song.wang
 * @Create Date 2018/12/21 09:42
 */
public class ImageFingerPrintUtil {

    /**
     *
     * 感知hash算法
     * todo 目前只支持8*8的，不想压缩了，以后可以考虑实现
     * 不过8*8太小了，我的字都画不下来
     * 而且要求是灰度图片
     * @param bi
     * @return
     */
    public static long perceptualHashAlgorithm8(BufferedImage bi){
        if (bi.getWidth()!=8 || bi.getHeight()!=8){
            throw new IllegalStateException("图片大小不符合尺寸");
        }
        //计算灰度值的和
        long greySum = 0;
        for (int i =0;i<bi.getWidth();i++){
            for (int j=0;j<bi.getHeight();j++){
                int rgb = bi.getRGB(i, j);
                int r = (rgb & 0Xff0000)>>16;
                int g = (rgb & 0Xff00)>>8;
                int b = (rgb & 0Xff);
                greySum += getGrey(r,g,b);
            }
        }
        //计算hash
        long hash = 0;
        double average = greySum*1.0/(bi.getWidth()*bi.getHeight());
        //System.out.println("average = "+average);
        for (int i =0;i<bi.getWidth();i++){
            for (int j=0;j<bi.getHeight();j++){
                int rgb = bi.getRGB(i, j);//rgb=-1代表白色
                int r = (rgb & 0Xff0000)>>16;
                int g = (rgb & 0Xff00)>>8;
                int b = (rgb & 0Xff);
                int grey = getGrey(r,g,b);
                if (grey<average){//黑色
                    hash |= 1 << ( i*8 + j);
                    //System.out.println(Long.toBinaryString(hash));
                }
            }
        }
        return hash;
    }

    /**
     * 获取灰度
     * todo 待完善整理，现有多处
     * @param r
     * @param g
     * @param b
     * @return
     */
    private static int getGrey(int r,int g,int b){
        return new Double(0.299*r+0.587*g+0.114*b).intValue();
    }

    /**
     * 16*16
     * 4个perceptualHashAlgorithm8
     * @param bi
     * @return
     */
    public static Perceptual perceptualHashAlgorithm16(BufferedImage bi){
        if (bi.getWidth()!=16 || bi.getHeight()!=16){
            throw new IllegalStateException("图片大小不符合尺寸");
        }
        long[] hash = new long[4];
        BufferedImage img1 = bi.getSubimage(0, 0, 8, 8);
        hash[0] = perceptualHashAlgorithm8(img1);
        BufferedImage img2 = bi.getSubimage(8, 0, 8, 8);
        hash[1] = perceptualHashAlgorithm8(img2);
        BufferedImage img3 = bi.getSubimage(0, 8, 8, 8);
        hash[2] = perceptualHashAlgorithm8(img3);
        BufferedImage img4 = bi.getSubimage(8, 8, 8, 8);
        hash[3] = perceptualHashAlgorithm8(img4);
        return new Perceptual(hash);
    }

    /**
     * 计算汉明距离
     *
     * 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
     *
     * <a href="http://www.ruanyifeng.com/blog/2011/07/principle_of_similar_image_search.html">
     *     阮一峰的日志
     *     </a>
     * @param a
     * @param b
     * @return
     */
    public static long calHammingDiatance(long a, long b){
        long ab = a^b;
        //ab 中0的个数就是正解
        int count = 0;
        while (ab!=0Xffffffff){//ab!=-1
            ab |= (ab+1);
            count++;
        }
        return count;
    }
}
