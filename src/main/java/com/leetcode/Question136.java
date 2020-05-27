package com.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素
 */
public class Question136 {
    public static void main(String[] args) {
        int[] aa = {4,1,2,1,2};
        System.out.println(singleNumber(aa));
    }
    public static int singleNumber(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        for (Integer key : map.keySet()) {
            if (map.get(key) == 1) {
                return key;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * 异或运算
     * @param nums
     * @return
     */
    public static int singleNumber1(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;

    }
}
