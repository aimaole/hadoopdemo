package com.test;

/**
 * 找出连续最长小写
 */
public class Test1 {
    public static void main(String[] args) {
        String s = "AabBcdefg";
        int len = 0;
        int tmplen = 0;
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            char chr = s.charAt(i);
            if (97 <= chr && chr <= 122) {
                tmplen += 1;
                if (tmplen > len) {
                    len = tmplen;
                    index = i;
                }


            } else {
                tmplen = 0;
            }
        }
        System.out.println(s.substring(index - len + 1, index + 1));
    }
}
