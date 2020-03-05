package main.java.com.leetcode;

import java.util.Set;
import java.util.TreeSet;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * <p>
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * <p>
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * <p>
 * 不会做
 */
public class Question4 {
    public static void main(String[] args) {
        int[] a = {1, 1};
        int[] b = {1, 2};
        System.out.println(findMedianSortedArrays(a, b));
        System.out.println(findMedianSortedArrays1(a, b));
    }

    static double findMedianSortedArrays(int[] A, int[] B) {
        double result = 0;
        Set<Integer> set = new TreeSet<>();
        for (int tmp : A) set.add(tmp);
        for (int tmp : B) set.add(tmp);
        int size = set.size();
        if (0 == size % 2) {
            int count = size / 2;
            int i = 1;
            int before = 0, after = 0;
            for (Integer ii : set) {
                if (i == count) {
                    before = ii;
                }
                if (i == count + 1) {
                    after = ii;
                }
                i++;
            }
            result = (double) (before + after) / 2;
        } else {
            int count = size / 2 + 1;
            int i = 1;
            for (Integer ii : set) {
                if (i == count) {
                    result = ii;
                }
                i++;
            }
        }
        return result;
    }

    public static double findMedianSortedArrays1(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A;
            A = B;
            B = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = B[j - 1];
                } else if (j == 0) {
                    maxLeft = A[i - 1];
                } else {
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == m) {
                    minRight = B[j];
                } else if (j == n) {
                    minRight = A[i];
                } else {
                    minRight = Math.min(B[j], A[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }
}
