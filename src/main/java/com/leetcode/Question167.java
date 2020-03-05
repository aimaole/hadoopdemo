package main.java.com.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Question167 {
    public static void main(String[] args) {

        int[] ints = twoSum(new int[]{12,13,23,28,43,44,59,60,61,68,70,86,88,92,124,125,136,168,173,173,180,199,212,221,227,230,277,282,306,314,316,321,325,328,336,337,363,365,368,370,370,371,375,384,387,394,400,404,414,422,422,427,430,435,457,493,506,527,531,538,541,546,568,583,585,587,650,652,677,691,730,737,740,751,755,764,778,783,785,789,794,803,809,815,847,858,863,863,874,887,896,916,920,926,927,930,933,957,981,997}, 567);
//        [12,13,23,28,43,44,59,60,61,68,70,86,88,92,124,125,136,168,173,173,180,199,212,221,227,230,277,282,306,314,316,321,325,328,336,337,363,365,368,370,370,371,375,384,387,394,400,404,414,422,422,427,430,435,457,493,506,527,531,538,541,546,568,583,585,587,650,652,677,691,730,737,740,751,755,764,778,783,785,789,794,803,809,815,847,858,863,863,874,887,896,916,920,926,927,930,933,957,981,997]
//567
        System.out.println(ints[0]);
        System.out.println(ints[1]);
    }


    public static int[] twoSum(int[] numbers, int target) {
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0;i<numbers.length;i++){
            int tmp = target-numbers[i];
            if (map.containsKey(tmp)&&map.get(tmp)!= i){
                return new int[]{map.get(tmp)+1,i+1};
            }
            map.put(numbers[i],i);
        }
        throw new IllegalArgumentException();
    }
}
