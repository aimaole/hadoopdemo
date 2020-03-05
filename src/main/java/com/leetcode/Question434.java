package main.java.com.leetcode;

/**
 * 统计字符串中的单词个数，这里的单词指的是连续的不是空格的字符。
 *
 * 请注意，你可以假定字符串里不包括任何不可打印的字符。
 */
public class Question434 {
    public int countSegments(String s) {
        int count = 0;
        String[] s1 = s.trim().split(" ");
        for (String tmp : s1) {
            count = "".equals(tmp) ? count : count + 1;
        }
        return count;
    }
}
