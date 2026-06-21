package Programs;

public class Reverse_LInkedList {

    // ---------- Node class ----------
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    // ---------- Reverse logic ----------
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null; //null
        ListNode curr = head; //1

        while (curr != null) { //1 //2
            ListNode nextTemp = curr.next; //2 //3 //null
            curr.next = prev; //null //null
            prev = curr; //1 //2 //3
            curr = nextTemp; //2 //3 //null
        }

        return prev;
    }

    // ---------- MAIN METHOD ----------
    public static void main(String[] args) {

        // Create linked list: 1 → 2 → 3
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);

        // Reverse
        head = reverseList(head);

        // Print reversed list
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
    
    
	/*
	 * // only this logic is required that's it ListNode prev = null; //null
	 * ListNode curr = head; //1 while (curr!=null) { ListNode temp= curr.next;
	 * curr.next=prev; prev=curr; curr=temp; }
	 */
}
