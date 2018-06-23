package leftovers.util;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by kevin on 2017/6/7.
 */
public class Chinese2PinyinTest {
    @Test
    public void convert() throws Exception {
        Set<String> strings = Chinese2Pinyin.convert("中国平安", Chinese2Pinyin.FIRST_SPELL);
    }
}