import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by daviddai on 2021/1/21
 * https://leetcode.com/problems/two-sum/solution/
 *
 * 总结：
 * 1.以nums = [3,2,4], target = 6为例。当我看到4后，我会想列表里有没有2，这时候如果我不记得了，我会在列表里一个个找，但如果我还对之前的结果有影响，我就能直接找到2.hash在这里的作用好像就是这个作用。将脑子里想的“2”这个东西变成一个string，让电脑也能马上找到这个。
 * 2.当需要快速从一坨东西里找到一个东西的时候，可以考虑下hash
 */
public class TwoSum {

    // 自己的解法
    public int[] twoSum(int[] nums, int target) {
        // 这个变量是多余的，可以去掉的。
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            left = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] + left == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // 看了参考答案后，自己照着写一遍
    public int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 用减法算出来满足条件的另一个数是多少。
            int left = target - nums[i];
            // 用hashmap找下之前遍历的结果里有没有符合条件的值。
            // hash找东西的时间复杂度是O(1)，会比两次循环的方法快。
            // 题目里有限制条件结果只有一份，所以最终结果里的两个数一定是唯一的。所以可以直接将值放在key里也不怕覆盖掉。
            if (map.containsKey(left)) {
                return new int[]{map.get(left), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No solution");
    }


    @Test
    public void test() {
        testInner(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}); // 例题提供的情况
        testInner(new int[]{3, 2, 4}, 6, new int[]{1, 2}); // 例题提供的情况
        testInner(new int[]{3,3}, 6, new int[]{0, 1}); // 例题提供的情况
    }

    private void testInner(int[] nums, int target, int[] result) {
        Assert.assertEquals(Arrays.toString(twoSum(nums, target)), Arrays.toString(result));
        Assert.assertEquals(Arrays.toString(twoSum2(nums, target)), Arrays.toString(result));
    }
}
