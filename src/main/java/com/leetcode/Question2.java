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

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode tmp = result;
        int sum = 0;
        while (l1 != null || l2 != null) {
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            tmp.next = new ListNode(sum % 10);
            sum /= 10;
            tmp = tmp.next;
        }
        if (sum == 1) {
            tmp.next = new ListNode(1);
        }
        return result.next;

    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

