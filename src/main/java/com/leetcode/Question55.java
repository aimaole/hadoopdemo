package com.leetcode;

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * <p>
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * <p>
 * 判断你是否能够到达最后一个位置。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 * 示例 2:
 * <p>
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 */
public class Question55 {

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 0, 4};
        System.out.println(canJump(nums));
    }

    /**
     * 从后往前遍历数组，如果遇到的元素可以到达最后一行，则截断后边的元素。
     * 否则继续往前，弱最后剩下的元素大于1个，则可以判断为假。
     * 否则就是真，时间复杂度O(n)就可以，不知道有没有大佬可以继续优化。
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        int n = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            //n为i后面的长度
            if (nums[i] >= n) {
                n = 1;//能达到设置为1
            } else {
                n++;//不能达到长度加一
            }
            if (i == 0 && n > 1) {//遍历到头后判断长度是否为大于。
                return false;
            }
        }
        return true;
    }

    /**
     * ### 解题思路
     * 自左向右的贪心算法。不断记录从左边0号index元素向右能访问到的最远的index。
     * 首先，自左向右要保证当前的这个元素本身是可以被访问到的，也就是要求i <= max_reach。
     * 其次，为了减少不必要的计算，一旦max_reach >= len - 1也就是能够抵达最后一个元素，则终止循环跳出这个这个函数。
     * @param nums
     * @return
     */
    public static boolean canJump1(int[] nums) {
        int length = nums.length;
        int max_reach = 0;
        if (length < 2) return true;

        for (int i = 0; i < length - 1; i++) {
            if (i > max_reach) return false;
            max_reach = Math.max(i + nums[i], max_reach);
            if (max_reach >= length - 1) return true;
        }
        return max_reach >= length - 1;
    }
}
