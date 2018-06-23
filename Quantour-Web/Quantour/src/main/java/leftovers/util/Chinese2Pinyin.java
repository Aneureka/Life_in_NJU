package leftovers.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

/**
 * Created by kevin on 2017/6/7.
 */
public class Chinese2Pinyin {
    public static final int FIRST_SPELL = 0;

    public static final int FULL_SPELL = 1;

    /**
     * 汉字转换为拼音，英文字符不变，特殊字符丢失，包含多音字则返回一个全排列
     *
     * @param chinese 汉字
     * @param spellType 拼写类型，首字母还是全拼
     * @return 首字母或全拼
     */
    public static Set<String> convert(String chinese, int spellType) {
        //设置拼音格式
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        //转换
        char[] chineseChars = chinese.toCharArray();
        StringBuffer pinyin = new StringBuffer();
        for (int i = 0; i < chineseChars.length; i++) {
            if (chineseChars[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(chineseChars[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            if (spellType == FULL_SPELL){
                                pinyin.append(strs[j]);
                            }else {
                                pinyin.append(strs[j].charAt(0));
                            }
                            if (j != strs.length - 1) {
                                pinyin.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyin.append(chineseChars[i]);
            }
            pinyin.append(" ");
        }

        return parseTheChineseByObject(removeDuplicated(pinyin.toString()));
    }

    /**
     * 去除多音字重复数据
     */
    private static List<Map<String, Integer>> removeDuplicated(String rawPinyin) {
        List<Map<String, Integer>> res = new ArrayList();

        // 处理每个字的多音字，去掉重复
        String[] characters = rawPinyin.split(" ");
        for (String character : characters) {
            Map<String, Integer> resElement = new Hashtable();

            String[] pinyinsOfCharacter = character.split(",");
            for (String pinyinOfCharacter : pinyinsOfCharacter) {
                Integer count = resElement.get(pinyinOfCharacter);
                if (count == null) {
                    resElement.put(pinyinOfCharacter, new Integer(1));
                } else {
                    resElement.remove(pinyinOfCharacter);
                    count++;
                    resElement.put(pinyinOfCharacter, count);
                }
            }

            res.add(resElement);
        }

        return res;
    }

    /**
     * 解析并组合拼音，对象合并方案
     */
    private static Set<String> parseTheChineseByObject(List<Map<String, Integer>> list) {
        // 用于统计每一次,集合组合数据
        Map<String, Integer> first = null;

        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }

        return first.keySet();
    }
}
