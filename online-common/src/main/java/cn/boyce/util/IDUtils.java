package cn.boyce.util;

import java.util.Random;

/**
 * 各种 ID 生成策略
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 12:39 2019/4/28
 **/
public class IDUtils {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        // 如果不足三位前面补 0
        String str = millis + String.format("%03d", end3);
        return str;
    }

    /**
     * 商品 id 生成
     */
    public static long genItemId() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        // 加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        // 如果不足两位前面补 0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }
}
