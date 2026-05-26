package com.bite.forum.util;

/**
 * 字符串相关的⼯具类
 *
 * @Author 梁元章
 */
public class StringUtils {
    /**
     * 字符串是否为空
     *
     * @param value 待验证的字符串
     * @return true 为空 false 不为空
     */
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }
}
