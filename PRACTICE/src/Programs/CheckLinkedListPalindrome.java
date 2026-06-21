package Programs;

public class CheckLinkedListPalindrome {
	
    // ---------- Node class ----------
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }
	
	
	public static void main (String[] args) {
		  // Create linked list: 1 → 2 → 3
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        System.out.println(checkPalindrome(head)); // true
	}

	
	
	static boolean checkPalindrome(ListNode head) {
	    if (head == null || head.next == null)
	        return true;

	    // Reverse the list
	    ListNode reversedHead = reverse(head);

	    // Compare original and reversed
	    ListNode p1 = head;
	    ListNode p2 = reversedHead;

	    while (p1 != null && p2 != null) {
	        if (p1.val != p2.val)
	            return false;

	        p1 = p1.next;
	        p2 = p2.next;
	    }
	    return true;
	}
	static ListNode reverse(ListNode head) {
	    ListNode curr = head;
	    ListNode prev = null;

	    while (curr != null) {
	        ListNode nextTemp = curr.next;
	        curr.next = prev;
	        prev = curr;
	        curr = nextTemp;
	    }
	    return prev; // ✅ important
	}


}
