
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by daviddai on 2021/1/21
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * 总结：
 * 1.在有序的列表中，可以用二分法加速遍历的过程。
 */
public class TwoSum2 {

    @Test
    public void test() {
        Assert.assertEquals(Arrays.toString(twoSum2(new int[]{2, 7, 11, 15}, 9)),Arrays.toString(new int[]{1, 2}));
        Assert.assertEquals(Arrays.toString(twoSum2(new int[]{2, 3, 4}, 6)),Arrays.toString(new int[]{1, 3}));
        Assert.assertEquals(Arrays.toString(twoSum2(new int[]{-1, 0}, -1)),Arrays.toString(new int[]{1, 2}));
    }

    private static int[] twoSum2(int[] numbers, int target) {
        int[] ret = new int[2];
        HashMap<Integer, Integer> dict = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            dict.put(numbers[i], i);
        }
        int temp;
        for (int i = 0; i < numbers.length; i++) {
            temp = numbers[i];
            int remain = target - temp;
            Integer indexRemain = dict.get(remain);
            if (indexRemain != null) {
                ret[0] = i + 1;
                ret[1] = indexRemain + 1;
                break;
            }
        }
        return ret;
    }

    /**
     * 参考：https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/discuss/51282/Simple-8-line-Java-solution-with-explanation.-O(n)
     *
     *
     * 原理猜测：1.https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/discuss/51282/Simple-8-line-Java-solution-with-explanation.-O(n)/190454
     * 2.https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/discuss/51239/Share-my-java-AC-solution./51905
     *
     *      a1	    a2	    a3	    a4	    a5
     * a1	a1+a1	a1+a2	a1+a3	a1+a4	a1+a5
     * a2	a1+a2	a2+a2	a2+a3	a2+a4	a2+a5
     * a3	a1+a3	a2+a3	a3+a3	a3+a4	a3+a5
     * a4	a1+a4	a2+a4	a3+a4	a4+a4	a4+a5
     * a5	a1+a5	a2+a5	a3+a5	a4+a5	a5+a5
     *
     * 归纳法：
     * 1.从右上角开始移动，如果要增加只能往下，要减少只能往左。
     * 2.a1这一行的也只能往下或者往左，不能往上，往右的话是回头路。a5这一列同理
     * 3.a4这一列的时候也只能往下或往左。比如a4+a4，当要增加的时候可以往下或者往右，但是如果往右的话无解。a4+a4只能从a4+a5或a3+a4来，如果是a4+a5来的话回头路了，如果是a3+a4来的话，
     * 由于前面的都是只能往下和往左的，所以一定从a5这一列来的，说明a3+a5大于target，那a4+a5也大于target。当我们真的往右移动到a4+a5的时候，这时候要减少，只能往上或者往左，这两个地方我们都去过了，这样会死循环。
     * 当要减少的时候可以往左或者往上，但是如果往上无解。a4+a4只能从a4+a5或a3+a4来，如果是a3+a4来的话回头路了，如果是a4+a5来的话，由于前面的都是只能往下和往左的，所以一定从a3这一行来的，说明a3+a5小于target，
     * 那a3+a4小于target。当我们真的往右移动到a3+a4的时候，这时候要增加，只能往下或者往右，这两个地方我们都去过了，这样会死循环。a2这一行同理
     * 4.由于前面的都是只能往下或者往左，所以a4这一列的证明也可以在其他列上面使用。证明怎么写呢？好像从a4那一列抄过来就可以了。
     * >
     */
    private static int[] twoSum2_discuss(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (numbers[left] + numbers[right] != target) {
            if (numbers[left] + numbers[right] > target) {
                right--;
            } else {
                left++;
            }
        }
        return new int[]{left+1, right+1};
    }

    /**
     * 参考2：https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/discuss/51268/A-less-efficient-way-(binary-search)
     *
     * 猜测思路：
     * 1.用target减去第一个数，得到差。然后用二分法在剩余中快速查找。
     * 2.如果找不到说明第一个数一定不是结果，再从第二个数开始处理。
     */
    private static int[] twoSum2_discuss2(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int start = i + 1;
            int end = numbers.length - 1;
            int gap = target - numbers[i];
            while (start <= end) {
                int m = start + (end - start) / 2;
                if (numbers[m] == gap) {
                    return new int[]{i + 1, m + 1};
                } else if (numbers[m] > gap) {
                    end = m - 1;
                } else {
                    start = m + 1;
                }
            }
        }
        throw new RuntimeException("");
    }

}