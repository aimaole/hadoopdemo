package main.java.com.leetcode;

/**
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class Question21 {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dumy = new ListNode(0);
        ListNode listNode = dumy;
        ListNode i = l1, j = l2;

        while (i != null && j != null) {
            if (i.val>j.val){
                listNode.next = new ListNode(j.val);
                j=j.next;
            }else {
                listNode.next = new ListNode(i.val);
                i=i.next;
            }
            listNode = listNode.next;
        }
        if (i==null){
            listNode.next = j;

        }else if (j==null){
            listNode.next = i;
        }

        return dumy.next;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
