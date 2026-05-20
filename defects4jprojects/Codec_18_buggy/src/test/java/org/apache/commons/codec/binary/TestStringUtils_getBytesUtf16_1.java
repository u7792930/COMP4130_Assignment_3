package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.codec.binary.StringUtils;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16_1 {

    @Test
    public void test_getBytesUtf16_nullInput() {
        byte[] result = StringUtils.getBytesUtf16(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUtf16_emptyString() {
        byte[] result = StringUtils.getBytesUtf16("");
        Assert.assertArrayEquals(new byte[]{}, result);
    }

    @Test
    public void test_getBytesUtf16_validString() {
        String input = "Hello";
        byte[] expected = new byte[]{72, 0, 101, 0, 108, 0, 108, 0, 111, 0}; // UTF-16 encoding of "Hello"
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }
}