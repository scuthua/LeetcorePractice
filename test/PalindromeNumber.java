import org.junit.Assert;
import org.junit.Test;

/**
 * Created by daviddai on 2021/10/24
 * https://leetcode.com/problems/palindrome-number/
 * 总结：
 * 1.对数值进行处理的时候，有可能会越界。
 * 2.观察特征，可以利用特征进行小细节的优化。貌似有些问题就是观察发现特性，然后就可以用简单的实现方式了。
 */

public class PalindromeNumber {

    /**
     * 转成String，然后避免左右两边的字符是否相同
     * 审题错误，不推荐用转成string的方法
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String input = String.valueOf(x);
        int leftIndex = 0;
        int rightIndex = input.length() - 1;
        while (leftIndex < rightIndex) {
            if (input.charAt(leftIndex) != input.charAt(rightIndex)) {
                return false;
            }
            leftIndex++;
            rightIndex--;
        }
        return true;
    }

    /**
     * 参考 https://leetcode.com/problems/palindrome-number/discuss/5127/9-line-accepted-Java-code-without-the-need-of-handling-overflow
     * 可以反过来写的数字，截取出来的左右两边对称即相等。所以反过来取数字里的每一位并只取一半，比如1234会被取成43.然后剩下的12和43比较
     * 临界情况：
     * 1.121这种位数是奇数的情况，没法左右对称。但这种会被拆成1和21，可以判断1 == 12/10。因为中间的位数2没有用，可以去掉。这样两边的位数就相等了。
     * 2.负数的情况一定返回false，所以可以不考虑负数
     * 3.由于处理方式是反过来取一半的值，所以不会数组越界。如果是反过来取所有的值就可能会数组越界。
     */
    public boolean isPalindrome2(int x) {
        // 将值不是0并且个位上是0的情况排除，因为数字的首位不能是0，这种就一定不能反过来
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }
        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x = x / 10;
        }
        // 这里的两个判断分别对应于1221和121两种情况。因为如果是奇数的话，x会比rev少一位。
        return (x == rev || x == rev / 10);
    }


    @Test
    public void test() {
        // 例题提供的情况
        Assert.assertEquals(isPalindrome2(121), true);
        Assert.assertEquals(isPalindrome2(-121), false);
        Assert.assertEquals(isPalindrome2(10), false);
        Assert.assertEquals(isPalindrome2(-101), false);
        Assert.assertEquals(isPalindrome2(0), true);
        Assert.assertEquals(isPalindrome2(2222), true);
        Assert.assertEquals(isPalindrome2(222), true);
        Assert.assertEquals(isPalindrome2(1221), true);
    }

    @Test
    public void testMultiTime() {
        System.out.println("----start----");
        long startTime = System.currentTimeMillis();
        int time = 1000000;
        for (int i = 0; i < time; i++) {
            test();
        }
        System.out.printf("%d time cost:%d%n", time, (System.currentTimeMillis() - startTime));
        System.out.println("----end----");
    }
}
