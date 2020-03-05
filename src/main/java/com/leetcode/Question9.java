package main.java.com.leetcode;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 */
public class Question9 {
    public static void main(String[] args) {
        isPalindrome(987654321);
    }
    public static boolean isPalindrome(int x) {
        int rs = 0;
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        } else {
            while (x > rs) {
                rs = x % 10 + rs * 10;
                x/=10;
            }
            return x == rs || x == rs/10;
        }

    }
}
