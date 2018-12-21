package cn.org.wangsong;


import cn.org.wangsong.util.ImgUtil;

//程序入口
public class Application {
    public static void main(String[] args) {
//
//        ImgUtil.genBrayImg("/Users/song.wang/Downloads/IMG_20181117_175851.jpg",
//                "/Users/song.wang/Downloads/IMG_20181117_175851-black.jpg");

        ImgUtil.genLineImgNew("/Users/song.wang/Downloads/IMG_20181117_175851.jpg",
                "/Users/song.wang/Downloads/IMG_20181117_175851-black.jpg",
                0.05f
                );
    }
}
