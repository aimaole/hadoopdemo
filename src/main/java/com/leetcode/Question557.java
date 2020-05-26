package com.leetcode;


public class Question557 {

    public String reverseWords(String s) {
        StringBuilder result = new StringBuilder();
        String[] split = s.split(" ");
        for (int i = 0; i < split.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = split[i].toCharArray().length - 1; j >= 0; j--) {
                sb.append(split[i].toCharArray()[j]);
            }
            result.append(sb.toString());
            result.append(" ");
        }
        String s1 = result.toString();


        return s1.substring(0, s1.length() - 1);
    }
    public String reverseWords1(String s) {
        String[] words = s.split(" ");
        StringBuilder res=new StringBuilder();
        for (String word: words)
            res.append(new StringBuffer(word).reverse().toString() + " ");
        return res.toString().trim();
    }

}
