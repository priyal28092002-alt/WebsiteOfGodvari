package Programs;

public class findnthNodeInLinkedList {
    public static ListNode findNthFromEnd(ListNode head, int n) {
        ListNode fast = head, slow = head;
        
        for (int i = 0; i < n; i++) {
            if (fast == null) return null;
            fast = fast.next;
        }
        
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        return slow;
    }
    
    public static void main(String[] args) {
        // Create list: 1->2->3->4->5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        
        ListNode result = findNthFromEnd(head, 2);
        System.out.println(result != null ? result.val : "null"); // Output: 4
    }
}

