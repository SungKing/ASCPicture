package cn.org.wangsong.entity;

import lombok.Getter;

/**
 * 用来生成字符画的基础字符
 * maybe这个指纹似乎不太理想
 * @Author Created by song.wang
 * @Create Date 2018/12/21 14:05
 */
@Getter
public enum BaseWord {
    //前面是字符，后面是指纹
    V1('*',new Perceptual(new long[]{-50800624,10025212,0,0})),
    V2('`',new Perceptual(new long[]{100794368,3086,0,0})),
    V3('-',new Perceptual(new long[]{0,0,50529027,50529027})),
    V4('+',new Perceptual(new long[]{-252706816,0,1061094147,50529027})),
    V5('.',new Perceptual(new long[]{0,0,808452096,48})),
    V6('\'',new Perceptual(new long[]{1040580608,62,0,0})),
    V7('/',new Perceptual(new long[]{-2147483648,1899760,-475136,271})),
    V8(':',new Perceptual(new long[]{1616904192,96,808452096,48})),
    V9('^',new Perceptual(new long[]{2096152576,12644476,67176199,117899524})),
    V10('?',new Perceptual(new long[]{-829551092,3734780,926364672,1})),
    V11('_',new Perceptual(new long[]{0,0,-2139062144,-2139062144})),
    V12('=',new Perceptual(new long[]{-2139062144,-2139062144,218959117,218959117})),
    V13('#',new Perceptual(new long[]{-51355552,2096951404,908017462,101130047})),
    V14(' ',new Perceptual(new long[]{0,0,0,0})),
    V15('|',new Perceptual(new long[]{-33554432,254,-16777216,255}))
    ;



    private char c;
    private Perceptual p;
    BaseWord(char c,Perceptual p){
        this.c = c;
        this.p = p;
    }

}
