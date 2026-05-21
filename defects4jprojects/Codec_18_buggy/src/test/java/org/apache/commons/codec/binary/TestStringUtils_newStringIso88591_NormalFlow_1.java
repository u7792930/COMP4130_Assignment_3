package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_newStringIso88591_NormalFlow_1 {

    @Test
    public void test_newStringIso8859_1_validBytes() {
        byte[] input = new byte[]{72, 101, 108, 108, 111}; // "Hello" in ISO-8859-1
        String result = StringUtils.newStringIso8859_1(input);
        Assert.assertEquals("Hello", result);
    }

    @Test
    public void test_newStringIso8859_1_anotherValidInput() {
        byte[] input = new byte[]{87, 111, 114, 108, 100}; // "World" in ISO-8859-1
        String result = StringUtils.newStringIso8859_1(input);
        Assert.assertEquals("World", result);
    }

    @Test
    public void test_newStringIso8859_1_moreValidBytes() {
        byte[] input = new byte[]{65, 66, 67}; // "ABC" in ISO-8859-1
        String result = StringUtils.newStringIso8859_1(input);
        Assert.assertEquals("ABC", result);
    }
}