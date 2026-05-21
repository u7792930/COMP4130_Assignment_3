package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringIso88591_NullEmpty_1 {

    @Test
    public void test_newStringIso8859_1_nullInput() {
        String result = StringUtils.newStringIso8859_1(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringIso8859_1_emptyArray() {
        String result = StringUtils.newStringIso8859_1(new byte[0]);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringIso8859_1_nonEmptyArray() {
        byte[] input = {72, 101, 108, 108, 111}; // "Hello" in ISO-8859-1
        String result = StringUtils.newStringIso8859_1(input);
        Assert.assertEquals("Hello", result);
    }
}