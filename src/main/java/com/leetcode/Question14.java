package main.java.com.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 */
public class Question14 {
    public static void main(String[] args) {
        String[] ss = {"flower","flow","flight"};
        longestCommonPrefix2(ss);

    }
    public static String longestCommonPrefix(String[] strs) {
        boolean flag = true;
        Set<Character> set = new HashSet<>();
        int i = 0;
        int index = 0;
        try {
            while (flag) {
                for (int j = 0; j < strs.length; j++) {
                    set.add(strs[j].charAt(i));
                }
                if (set.size() == 1) {
                    index += 1;
                    i++;
                    set.clear();
                } else {
                    flag = false;
                    break;
                }

            }

        } catch (NullPointerException e) {

        } finally {
            if (index == 0) {
                return "";

            } else {

                return strs[0].substring(0, index);
            }
        }

    }
    public static String longestCommonPrefix1(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        return prefix;
    }
    public static String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        for (int i = 0; i < strs[0].length() ; i++){
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j ++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }
}
