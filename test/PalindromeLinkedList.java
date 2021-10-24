import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by daviddai on 2021/10/24
 * https://leetcode.com/problems/palindrome-linked-list/
 * 总结：
 * 1.遇到大数的情况，不能使用int等数值类型
 * 2.链表的结构可以用两倍速来找到中间节点是哪个。
 * 3.不应该修改方法的入参，除非它是用来接收结果的。
 */
public class PalindromeLinkedList {

    public boolean isPalindrome(ListNode head) {
        // 考虑到有大数的可能，所以不能int等类型。这里我一开始用了String，因为我看到处理大数的时候就感觉要用String。不过这里用Array感觉更好理解一些。
        ArrayList<Integer> list = new ArrayList<>();
        do {
            list.add(head.val);
        } while ((head = head.next) != null);
        int left = 0;
        int right = list.size()-1;
        while (left < right) {
            if (!list.get(left++).equals(list.get(right--))) {
                return false;
            }
        }
        return true;
    }
    public boolean isPalindrome2(ListNode head) {
        // 寻找链表中间的节点
        ListNode firstHalfEnd = endOfFirstHalf(head);
        // 将链表中间节点后的子链表反转过来。
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // 经过获取中间节点，以及将后面的子链表反转。我们现在可以从链表的两头不断往中间移动了。
        ListNode left = head;
        ListNode right = secondHalfStart;
        boolean result = true;
        while (right != null) {
            if (left.val!= right.val) {
                result = false;
                break;
            }
            left = left.next;
            right = right.next;
        }
        // 这里是将被修改的参数还原。其实如果只是要要知道是否结果，前面的result就是结果了。但是不应该修改方法的入参，除非他是用来接收结果的。这一边我之前都没意识到
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }

    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    // 类似于1小时的蜡烛如果表示半小时的问题，可以两头烧蜡烛。链表可以用一倍速和两倍速寻找下一个节点
    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        // 如果位数是奇数的话，那最中间的这一位不影响是否可以反过来写的结果
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    @Test
    public void test() {
        // 例题提供的情况
        Assert.assertEquals(isPalindrome2(integer2ListNode(1,2,1)), true);
        Assert.assertEquals(isPalindrome2(integer2ListNode(1,2,2,1)), true);
        Assert.assertEquals(isPalindrome2(integer2ListNode(2,2)), true);
        Assert.assertEquals(isPalindrome2(integer2ListNode(2,2,2)), true);
        Assert.assertEquals(isPalindrome2(integer2ListNode(2,2,2,2)), true);
        Assert.assertEquals(isPalindrome2(integer2ListNode(0)), true);
        Assert.assertEquals(isPalindrome2(integer2ListNode(1,0)), false);
        Assert.assertEquals(isPalindrome2(integer2ListNode(1, 0, 0)), false);
        Assert.assertEquals(isPalindrome2(integer2ListNode(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)), false);
        Assert.assertEquals(isPalindrome2(integer2ListNode(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)), true);
    }

    private ListNode integer2ListNode(int... arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("length == 0");
        }
        ListNode node = null;
        int tmp;
        // ListNode构造的时候需要知道next是谁，所以这里反过来执行循环
        for (int i = arr.length - 1; i >= 0; i--) {
            tmp = arr[i];
            if (tmp < 0 || tmp > 9) {
                throw new IllegalArgumentException("tmp" + " < 0 || " + tmp + " > 9");
            }
            if (node == null) {
                node = new ListNode(tmp);
            } else {
                node = new ListNode(tmp, node);
            }

        }
        return node;
    }

}
