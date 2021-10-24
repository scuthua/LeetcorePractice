import org.junit.Assert;
import org.junit.Test;

/**
 * Created by daviddai on 2021/10/24
 * https://leetcode.com/problems/valid-palindrome/submissions/
 * 总结：
 * 1.判断是否为字母数字的时候，可以预先弄一个数组，然后用数组对应位置是否有值来判断是否为字母数字。虽然占用了空间，但是如果遇到计算频繁的情况可能会有用。
 * 2.自己写的validChar方法有重复的操作，整个过程中多次询问相同的结果,比如输入为”aaa“的时候，会多次判断‘a’是不是字母数字，可以考虑用缓存
 */
public class ValidPalindrome {

    public boolean isPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return true;
        }
        int left = 0;
        int right = s.length()-1;
        while (left < right) {
            if (!validChar(s.charAt(left))) {
                left++;
                continue;
            }
            if (!validChar(s.charAt(right))) {
                right--;
                continue;
            }
            if (Character.toLowerCase(s.charAt(left++)) != Character.toLowerCase(s.charAt(right--))) {
                return false;
            }
        }
        return true;
    }

    private final char[] cache = new char[256]; // ascii最多256个

    private boolean validChar(char character) {
        char val = cache[character];
        if (val != 0) {
            return val == '1';
        }
        boolean ret = (97 <= character && character <= 122) || (65 <= character && character <= 90) || (48 <= character && character <= 57);
        cache[character] = ret?'1':'0';
        return ret;
    }

    /**
     * 参考 https://leetcode.com/problems/valid-palindrome/discuss/39993/3ms-java-solution(beat-100-of-java-solution)
     * 思路：判断是不是数字或者字母，每次都要进行，换成数组可以加快速度。
     */
    public boolean isPalindrome2(String s) {
        char[] charMap = new char[256];
        for (int i = 0; i < 10; i++)
            charMap['0'+i] = (char) (1+i);  // numeric - don't use 0 as it's reserved for illegal chars
        for (int i = 0; i < 26; i++)
            charMap['a'+i] = charMap['A'+i] = (char) (11+i);  //alphabetic, ignore cases, continue from 11

        for (int start = 0, end = s.length()-1; start < end;) {
            if (charMap[s.charAt(start)] == 0)
                start++;
            else if (charMap[s.charAt(end)] == 0)
                end--;
            else if (charMap[s.charAt(start++)] != charMap[s.charAt(end--)])
                return false;
        }
        return true;
    }

    @Test
    public void test() {
        // 例题提供的情况
        Assert.assertEquals(isPalindrome("A man, a plan, a canal: Panama"), true);
        Assert.assertEquals(isPalindrome("121"), true);
        Assert.assertEquals(isPalindrome("-121"), true);
        Assert.assertEquals(isPalindrome("10"), false);
        Assert.assertEquals(isPalindrome("-101"), true);
        Assert.assertEquals(isPalindrome("0"), true);
        Assert.assertEquals(isPalindrome("2222"), true);
        Assert.assertEquals(isPalindrome("222"), true);
        Assert.assertEquals(isPalindrome("1221"), true);
        Assert.assertEquals(isPalindrome("race a car"), false);
    }
}
