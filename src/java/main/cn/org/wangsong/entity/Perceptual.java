package cn.org.wangsong.entity;

import cn.org.wangsong.util.ImageFingerPrintUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * 指纹
 * 64 * 64
 * @Author Created by song.wang
 * @Create Date 2018/12/21 11:56
 */
@Getter
public class Perceptual {

    private long[] hash;

    private int bit;

    public Perceptual(long[] hash){
        this.hash = hash;
        this.bit= hash.length;
    }

    public static long distance(Perceptual p1,Perceptual p2){

        if (p1.bit!=p2.bit)
            return -1;
        //暂时将距离相加
        int result = 0;
        for (int i =0;i<p1.hash.length;i++){
            result += ImageFingerPrintUtil.calHammingDiatance(p1.hash[i],p2.hash[i]);
        }
        return result;

    }

    @Override
    public String toString() {
        return "Perceptual{" +
                "hash=" + Arrays.toString(hash) +
                '}';
    }
}
