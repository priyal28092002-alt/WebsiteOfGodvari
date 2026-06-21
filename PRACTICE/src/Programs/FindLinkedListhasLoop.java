package Programs;

import Programs.Reverse_LInkedList.ListNode;

public class FindLinkedListhasLoop {

	public static void main(String[] args) {
		// Create linked list: 1 → 2 → 3 → 4 → 5
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);

		// Create loop: 5 → 2
		head.next.next.next.next.next = head.next;

		System.out.println(IsLoopPresentOrNot(head));
	}

	static boolean IsLoopPresentOrNot(ListNode head) {
		if (head == null) {
			return false;
		}
		ListNode slow = head;
		ListNode fast = head;

		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				return true;
			}
		}

		return false;
	}
}
