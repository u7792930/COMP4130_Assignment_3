package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16_1 {

    @Test
    public void test_newStringUtf16_nullInput() {
        String result = StringUtils.newStringUtf16(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringUtf16_emptyInput() {
        String result = StringUtils.newStringUtf16(new byte[0]);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringUtf16_validInput() {
        byte[] input = new byte[]{72, 0, 101, 0}; // "He" in UTF-16
        String result = StringUtils.newStringUtf16(input);
        Assert.assertEquals("He", result);
    }
}