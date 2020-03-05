package main.java.com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 */
public class Question1 {
    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 3, 5, 6, 9, 8};
        int target = 8;
        int[] aa = twosum2(nums, target);
        for (int a : aa) {
            System.out.println(a);
        }
    }

    public static int[] twosum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int tmp = target - nums[i];
            if (map.containsKey(tmp)) {
                return new int[]{i, map.get(tmp)};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException();
    }
}
