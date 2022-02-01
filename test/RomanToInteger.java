import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by daviddai on 2022/2/1
 *
 * https://leetcode.com/problems/roman-to-integer/
 * 总结：
 * 1.查找的时候用map。对于这种有些key重复的用map也有局限，不知道用Tree是否可行，之前有了解一点T9搜索，用Tree进行搜索。
 * 2.当for循环要-1的时候，可以将这个放到外面来。这样子for循环里面就容易写代码。String的join方法就有这种处理。放前面还是后台不确定，这次我是放在前面感觉好写一些。
 */
public class RomanToInteger {

    // 看了下这应该就是一个map就可以解决了。前置的情况是固定的，后置的情况可以拆开成一个个。
    public int romanToInt1(String s) {
        Map<String, Integer> dict = new HashMap<>();
        dict.put("I", 1);
        dict.put("IV", 4);
        dict.put("V", 5);
        dict.put("IX", 9);
        dict.put("X", 10);
        dict.put("XL", 40);
        dict.put("L", 50);
        dict.put("XC", 90);
        dict.put("C", 100);
        dict.put("CD", 400);
        dict.put("D", 500);
        dict.put("CM", 900);
        dict.put("M", 1000);
        String tmp = "";
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i != s.length() - 1) {
                tmp = s.substring(i, i + 2);
                if (dict.containsKey(tmp)) {
                    sum += dict.get(tmp);
                    i++;
                    continue;
                }
            }
            tmp = s.substring(i, i + 1);
            sum += dict.get(tmp);
        }
        return sum;
    }

    // discuss里的答案。都使用了map，但是他们注意到一个细节，就是前置的情况如IX等于-I+X，并且从左往右是越来越小的，除了前置的情况
    public int romanToInt(String s) {
        Map<Character, Integer> dict = new HashMap<>();
        dict.put('I', 1);
        dict.put('V', 5);
        dict.put('X', 10);
        dict.put('L', 50);
        dict.put('C', 100);
        dict.put('D', 500);
        dict.put('M', 1000);
        int sum = 0;
        sum += dict.get(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (dict.get(s.charAt(i - 1)) < dict.get(s.charAt(i))) {
                sum -= 2 * dict.get(s.charAt(i - 1));
            }
            sum += dict.get(s.charAt(i));
        }
        return sum;
    }

    private void testInner(String input, int assertOutput) {
        // 接收到input和预期的output
        // 执行自己的算法
        // 执行Assert方法
        Assert.assertEquals(romanToInt(input), assertOutput);
    }

    @Test
    public void test() {
        testInner("IX", 9);
        testInner("III", 3);
        testInner("LVIII", 58);
        testInner("MCMXCIV", 1994);
    }

}
