package com.leetcode;

import java.util.*;

/**
 * 给定一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。
 */
public class Question260 {
    public static void main(String[] args) {
        int[] aa = {1, 2, 1, 3, 2, 5};
        Arrays.stream(singleNumber(aa)).forEach(System.out::println);
    }

    public static int[] singleNumber(int[] nums) {
        Map<Integer, Integer> ints = new HashMap<>();
        int[] output = new int[2];

        for (int i = 0; i < nums.length; i++) {
            ints.put(nums[i], ints.getOrDefault(nums[i], 0) + 1);
        }
        int flag = 0;
        for (Integer key : ints.keySet()) {
            if (ints.get(key) == 1) {
                output[flag++] = key;
            }
        }

        return output;
    }
}
