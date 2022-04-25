import org.junit.Assert;
import org.junit.Test;

/**
 * Created by daviddai on 2022/4/25
 *
 * https://leetcode.com/problems/implement-strstr/
 * 总结：
 * 1.引出next数组：不匹配的时候，两个子串其实是相同的，所以可以找最长公共前后缀，让模式串移动，模式串留下前缀，对应的字符串留下的就是后缀，由于是公共的，所以相等不用再比较
 * 2.next数组的值的意义：最长公共前后缀的长度，刚好等于模式串移动后下一轮要进行比较的指针的位置。
 * 3.理解next数组的实现方式：这是一个递推的过程。可以知道n=0的时候的值，以及n跟n+1的状态转移方程，所以用递推来实现。具体实现和使用next进行查找是类似的。
 *      1.0的时候没有子串，所以为0
 *      2.1的时候有子串了，它的最长取决于0和1位置上的值是否相等。
 *      3.i的时候，i的最长<=i-1的最长 + 1，当i的位置和i-1的最长前缀的下一位相等时等号成立。假如存在i的最长大于i-1的最长 + 1，那这段最长-1也是i-1的公共前后缀，这个比原本的i-1的最长还要长，有矛盾，假设不成立。
 *          1.相等，长度加1
 *          2.不相等。不相等的这一个串去掉i位置的值后是前缀，它的长度j一定少于i，那j的最长的值我们也是知道的，那我们就可以用next回溯来找到不考虑i位置的值时的最长串，然后进行下一轮比较。相等的话就找到最长串了，不相等就重复，知道next[j]的值为0并且不相等，这时候不能再回溯了又不相等就只能是最长串的长度为0。（这其实也是一个字符串匹配的过程，所以直接用next数组回溯）
 */
public class ImplementStrstr {

    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (haystack.length() < needle.length()) {
            return -1;
        }
        if (needle.length() == 1) {
            for (int i = 0; i < haystack.length(); i++) {
                if (haystack.charAt(i) == needle.charAt(0)) {
                    return i;
                }
            }
            return -1;
        }
        if (haystack.length() == needle.length()) {
            for (int i = 0; i < haystack.length(); i++) {
                if (haystack.charAt(i) != needle.charAt(i)) {
                    return -1;
                }
            }
            return 0;
        }

        int[] next = new int[needle.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < needle.length(); ) {
            if (needle.charAt(i) == needle.charAt(j)) {
                // 等于长度
                next[i] = j + 1;
                i++;
                j++;
            } else if (j == 0) {
                next[i] = 0;
                i++;
            } else {
                j = next[j - 1];
            }
        }

        int i = 0;
        int j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else if (j == 0) {
                // 这种是j==0，没法再回溯了，只能让i++
                i++;
            } else {
                // 回溯的是子串是不包括j的，所以next取值要j-1。next的值是长度，由于回溯只是确定了前面要保留多少，实际下一轮要进行匹配的是保留后的下一个，要+1。
                j = next[j - 1] - 1 + 1;
            }
        }

        // 如果是j越界了，那就是匹配到了。这时候
        if (j == needle.length()) {
            return i - j;
        }
        return -1;
    }

    private void testInner(String haystack, String needle, int assertOutput) {
        // 接收到input和预期的output
        // 执行自己的算法
        // 执行Assert方法
        Assert.assertEquals(strStr(haystack, needle), assertOutput);
    }

    @Test
    public void test() {
        testInner("hello", "ll", 2);
        testInner("aaaaa", "bba", -1);
        testInner("mississippi", "issip", 4);
        testInner("aabaaabaaac", "aabaaac", 4);
    }

}
