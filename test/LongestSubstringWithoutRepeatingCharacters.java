import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daviddai on 2021/3/27
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * 总结：
 * 1.用hash实现快速查找是否重复
 * 2.如果hash保存是index的话，可以直接用
 * 3.写清楚index是开还是闭，可以解决不知道是否要-1的疑惑。
 */
public class LongestSubstringWithoutRepeatingCharacters {

    /**
     * 遍历所有的字符，每一次记录子串的开始和结束index，当遇到重复的时候就记录当前长度并修改子串。判断重复使用hash的方法。
     * 时间复杂度应该是On。for循环是n；charAt，hash都是1；内层重新生成map时虽然用了循环，但是每次的长度和外层循环指定的次数有关，合计最多n次，所以双循环是2n
     */
    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        int subStartIndex = 0; // 左闭
        int subEndIndex = 0; // 右开
        HashMap<Character, Integer> dict = new HashMap<>();
        Character tmp;
        for (int i = subStartIndex; i < s.length(); i++) {
            tmp = s.charAt(i);
            Integer hasIndex = dict.get(tmp);
            if (hasIndex == null) {
                subEndIndex = i + 1;
                dict.put(tmp, i);
            } else {
                max = Math.max(max, subEndIndex - subStartIndex);
                for (int j = subStartIndex; j < hasIndex; j++) {
                    dict.remove(s.charAt(j));
                }
                subStartIndex = hasIndex + 1;
                subEndIndex = i + 1;
                dict.replace(tmp, i);
            }
        }
        max = Math.max(max, subEndIndex - subStartIndex);
        return max;
    }

    /**
     * 参考：https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/1522594/Java%3A-O(N)-time-O(1)-Space
     * 解决方案是类似的，但是这个比我写的简单。
     * 当出现重复的时候，只要记录开始的index即可，不用清空map。清空map是考虑多余的值会影响到contains的结果，但是如果我们将判断条件改成contains并且大于startIndex，那startIndex之前的结果就不会匹配到，没影响了。
     */
    public int lengthOfLongestSubstring2(String s) {
        int max = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();
        char tmp;
        int startIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            tmp = s.charAt(i);
            // 每次循环的时候会put，所以重复的话这里会发现。
            if (charIndexMap.getOrDefault(tmp, -1) >= startIndex) {
                max = Math.max(max, i - startIndex);
                startIndex = charIndexMap.get(tmp) + 1;
            }
            charIndexMap.put(tmp, i);
        }
        max = Math.max(max, s.length()- startIndex);

        return max;
    }

    @Test
    public void test() {
        // 例题提供的情况
        Assert.assertEquals(lengthOfLongestSubstring2("abcabcbb"), 3);
        Assert.assertEquals(lengthOfLongestSubstring2("bbbbb"), 1);
        Assert.assertEquals(lengthOfLongestSubstring2("pwwkew"), 3);
        Assert.assertEquals(lengthOfLongestSubstring2(""), 0);
        Assert.assertEquals(lengthOfLongestSubstring2(" "), 1);
        Assert.assertEquals(lengthOfLongestSubstring2("cddca"), 3);
    }

}
