package cn.fyd.util;

import java.util.Random;

import static cn.fyd.common.Constant.FOUR;

/**
 * 随机验证码(四位)
 * @author fanyidong
 * @date Created in 2018-12-28
 */
public class RandomUtils {

    public static char[] randomCode() {
        Random random = new Random();
        String chars = "3456789ABCDEFGHJKLMNPQRSTUVWXY";
        char[] rands = new char[4];
        for (int i = 0; i < FOUR; i++) {
            int rand = random.nextInt(30);
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }
}
