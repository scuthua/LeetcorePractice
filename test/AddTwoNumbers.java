import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daviddai on 2021/3/27
 * https://leetcode.com/problems/add-two-numbers/
 *
 * 总结：
 * 1.这就是个加法运算，将自己的想法用代码写出来。
 * 2.边界的细节，加法器会有最后一轮进位不为0的情况，这时候要+一位才行；也会有两个加数的位数不同的情况，这时候位数少的那个要用0继续参加加法器。
 */
public class AddTwoNumbers {

    /**
     * 自己的解法
     * 这就是一个加法运算吧。
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = null;
        ListNode next = null;
        int first = 0;
        int second = 0;
        // 每一轮计算的和
        int sum = 0;
        // 每一轮计算后保留在当前位的数
        int value = 0;
        // 每一轮计算后要进位的数
        int carry = 0;
        while (true) {
            if (l1 == null && l2 == null) {
                break;
            }
            // 当其中一个为null的时候，给0就好了，方便写代码和计算
            first = l1 == null ? 0 : l1.val;
            second = l2 == null ? 0 : l2.val;
            // 要先用了carry，后面才修改carry。不然就丢了
            sum = first + second + carry;
            value = sum > 9 ? sum - 10 : sum;
            carry = sum > 9 ? sum / 10 : 0;
            if (result == null) {
                result = new ListNode(value);
                next = result;
            } else {
                next.next = new ListNode(value);
                next = next.next;
            }

            // 重置部分参数，为下一轮准备
            l1 = l1!=null?l1.next:l1;
            l2 = l2!=null?l2.next:l2;
        }
        // 加法运算时，有可能数用完了，但还有进位。比如9+9只有个位，但是结果因为有进位要变成有十位的。
        if (carry != 0) {
            next.next = new ListNode(carry);
        }
        return result;
    }

    /**
     * 参考答案
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            /*
             * sum%10相对于sum-9是和定义更加准确的。不过10进制的进位不会超过2，所以结果一样。我想了其他的进制好像也不会超过2.进位超过2的前提是sum超过2*10，
             * 而不考虑进位的时候，相加不会超过这个数因为每一个数都少于10，考虑加上进位的话那进位一定是之前位相加来的，而前面的结果一样不会超过20
             */
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        // 默认给一个0，return的时候给next。这样子代码会好写一些，不用考虑dummyHead是null的情况。
        return dummyHead.next;
    }


    @Test
    public void test() {
        testInner(createNode(2, 4, 3), createNode(5, 6, 4), createNode(7, 0, 8)); // 例题提供的情况
        testInner(createNode(0), createNode(0), createNode(0)); // 例题提供的情况
        testInner(createNode(9, 9, 9, 9, 9, 9, 9), createNode(9, 9, 9, 9), createNode(8, 9, 9, 9, 0, 0, 0, 1)); // 例题提供的情况
    }

    private void testInner(ListNode l1, ListNode l2, ListNode result) {
        Assert.assertEquals(addTwoNumbers(l1, l2).toString(), result.toString());
        Assert.assertEquals(addTwoNumbers2(l1, l2).toString(), result.toString());
    }

    private ListNode createNode(int... data) {
        if (data == null || data.length == 0) {
            throw new RuntimeException("data must be not null");
        }
        ListNode result = new ListNode(data[0]);
        ListNode tmp = result;
        for (int i = 1; i < data.length; i++) {
            tmp.next = new ListNode(data[i]);
            tmp = tmp.next;
        }
        return result;
    }

    private static class ListNode {
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

        @Override
        public String toString() {
            List<Integer> values = new ArrayList<>();
            values.add(val);
            ListNode tmp = next;
            while (tmp != null) {
                values.add(tmp.val);
                tmp = tmp.next;
            }
            return values.toString();
        }
    }
}
