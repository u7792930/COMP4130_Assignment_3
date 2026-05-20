package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf8_1 {

    @Test
    public void test_newStringUtf8_nullInput() {
        String result = StringUtils.newStringUtf8(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringUtf8_emptyArray() {
        String result = StringUtils.newStringUtf8(new byte[0]);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringUtf8_validInput() {
        byte[] input = new byte[]{72, 101, 108, 108, 111}; // "Hello" in UTF-8
        String result = StringUtils.newStringUtf8(input);
        Assert.assertEquals("Hello", result);
    }
}