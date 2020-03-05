package com.search;

/**
 * 
 * @author lihaipeng
 * @date 2017年3月23日
 * @description :两种方法实现二分查找
 */
public class BinarySearch {
    /**
     * 
     * @Author lihaipeng
     * @date 2017年3月23日
     * @description :非递归方法实现二分查找！arr是要查找的数组，n是要查找的数,返回被查找的数的位置
     */
    /*{1,2,3,4,5,6}*/
    public static int search(int []arr,int n){
        int low=0;//低位
        int high=arr.length-1;//高位
        while(low<=high){//低位和高位不重复的时候或者相等的时候
            int mid=(low+high)/2;//计算中间值
            //如果相等
            if(arr[mid]==n){
                return mid;//结束方法
            }
            if(arr[mid]<n){
                low=mid+1;
            }else{
                high=mid-1;
            }
        }
        //没找到
        return -1;
    }
    /**
     * 
     * @Author lihaipeng
     * @date 2017年3月23日
     * @description :递归方法实现要查找的数，arr是要查找的数组，n是要查找的数,begin是低位，end是高位！返回被查找的数的位置
     */
    public static int search0(int []arr,int n,int begin ,int end){
         int mid=(begin+end)/2;
         if(n<arr[begin]||n>arr[end]||arr[begin]>arr[end]){
             return -1;//结束
         }
         if(arr[mid]<n){
             return search0(arr, n, mid+1, end);
         }
         else if(arr[mid]>n){
             return search0(arr, n, begin, mid-1);
         }else{
             return mid;
         }
    }
    public static void main(String[] args) {
        int [] arr={1,2,3,4,5,6};
        System.out.println(BinarySearch.search(arr,6));
        System.out.println(BinarySearch.search(arr,6));
    }
}