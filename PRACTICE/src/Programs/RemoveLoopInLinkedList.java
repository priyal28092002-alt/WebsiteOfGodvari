package Programs;

public class RemoveLoopInLinkedList {
	
	public static void main(String[] args) {
		
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		// Create loop: 5 → 2
		head.next.next.next.next.next = head.next;

        head = removeLoop(head);

        // Print list
        ListNode curr = head;
        while (curr != null) {
            System.out.println("value : " + curr.val);
            curr = curr.next;
        }
	}
	
	static class ListNode {
		int val;
		ListNode next;

		public ListNode(int val) {
			this.val = val;
			this.next = null;
		}

		public int getVal() {
			return val;
		}

		public void setVal(int val) {
			this.val = val;
		}
		
	}
	

	static ListNode removeLoop(ListNode head) {
	    ListNode slow = head;
	    ListNode fast = head;

	    // 1. Detect loop
	    while (fast != null && fast.next != null) {
	        slow = slow.next;
	        fast = fast.next.next;

	        if (slow == fast) {
	            break;
	        }
	    }

	    // 2. No loop
	    if (fast == null || fast.next == null) {
	        return head;
	    }

	    // 3. Find start of loop
	    slow = head;
	    while (slow.next != fast.next) {
	        slow = slow.next;
	        fast = fast.next;
	    }

	    // 4. Remove loop
	    fast.next = null;

	    return head;
	}

}
