import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by daviddai on 2019/2/1
 * <p>
 * <br/><a href="https://leetcode.com/problems/squares-of-a-sorted-array/">题目链接</a>
 */
public class SquaresOfASortedArray {

    /**
     * 思路：
     * <ol>
     * <li>
     * for一轮将所有的input都改成平方。
     * </li>
     * <li>
     * 从数组的两端往中间进行两两比较，值大的加入到数组的最后，并且对应指针往中间靠近。直到俩个指针在同一个位置
     * </li>
     * </ol>
     */
    private int[] solution(int[] input) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i] * input[i];
        }
        int resultIndex = input.length - 1;
        /*
         * 看了参考答案，这里应该可以优化下。从中间开始向两边移动指针，当其中一个指针出界后，对另一个指针做while循环来获取剩下的值。
         * 改成这种方式后，可以将双指针的方法用在不知道数组长度的题目中。
         */
        for (int leftIndex = 0, rightIndex = input.length - 1; leftIndex <= rightIndex; ) {
            if (input[leftIndex] > input[rightIndex]) {
                result[resultIndex--] = input[leftIndex];
                leftIndex++;
            } else {
                result[resultIndex--] = input[rightIndex];
                rightIndex--;
            }
        }
        return result;
    }

    private void testInner(int[] input, int[] assertOutput) {
        // 接收到input和预期的output
        // 执行自己的算法
        // 执行Assert方法
        Assert.assertEquals(Arrays.toString(solution(input)), Arrays.toString(assertOutput));
    }

    @Test
    public void test() {
        testInner(new int[]{-1, 2, 2}, new int[]{1, 4, 4}); // 正数比负数大的情况
        testInner(new int[]{-4, 0, 1, 1}, new int[]{0, 1, 1, 16}); // 在平方的最小值的两边，一边的值比另一边的都小的情况
        testInner(new int[]{-3, -2, -1}, new int[]{1, 4, 9}); // 全部负数的情况
        testInner(new int[]{0, 2}, new int[]{0, 4}); // 全部正数的情况
        testInner(new int[]{-7, -3, 2, 3, 11}, new int[]{4, 9, 9, 49, 121}); // 例题提供的情况
        testInner(new int[]{-4, -1, 0, 3, 10}, new int[]{0, 1, 9, 16, 100}); // 例题提供的情况
    }

}
