package com.sort;

public class Sort {
    public static void main(String[] args) {
        int[] arr = {52, 35, 82, 11, 52, 63, 85, 96, 74, 56, 55, 1, 69};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    /**
     * 快速排序
     *
     * @param arr   待排序数组
     * @param start 开始位置
     * @param end   结束位置
     * @return 返回中轴位置
     */
    public static int quickSort(int[] arr, int low, int high) {
        int start = low, end = high;
        if(start>end){
            return 0;
        }
        int zhong = arr[start];


        while (start < end) {
            //先看右边，依次往左递减
            while (zhong <= arr[end] && start < end) {
                end--;
            }
            //再看左边，依次往右递增
            while (zhong >= arr[start] && start < end) {
                start++;
            }
            //如果满足条件则交换
            if (start < end) {
                int tmp = arr[end];
                arr[end] = arr[start];
                arr[start] = tmp;
            }

        }
        arr[low] = arr[end];
        arr[end] = zhong;
        quickSort(arr, low, end - 1);
        quickSort(arr, end + 1, high);


        return 0;
    }

    public static int[] maopaoSort(int[] arr) {
        for (int i = 0; i < arr.length-1; i++) {//外层循环控制排序趟数
            for (int j = 0; j < arr.length - i - 1; j++) {//内层循环控制每一趟排序多少次
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        return arr;
    }
}
