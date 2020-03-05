package main.java.com.leetcode;

public class Question2 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dumy = new ListNode(0);
        ListNode cur = dumy;
        int sum = 0;
        ListNode i = l1, j = l2;
        while (i != null || j != null) {
            if (i != null) {
                sum += i.val;
                i = i.next;
            }
            if (j != null) {
                sum += j.val;
                j = j.next;
            }
            cur.next = new ListNode(sum % 10);
            sum /= 10;
            cur = cur.next;


        }
        if (sum == 1) {
            cur.next = new ListNode(1);
        }

        return dumy.next;
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

