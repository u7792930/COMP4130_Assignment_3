package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import org.apache.commons.codec.binary.StringUtils;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf8_1 {

    @Test
    public void test_getBytesUtf8_nullInput() {
        byte[] result = StringUtils.getBytesUtf8(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUtf8_emptyString() {
        byte[] result = StringUtils.getBytesUtf8("");
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesUtf8_validString() {
        String input = "Hello, World!";
        byte[] expected = new byte[] { 72, 101, 108, 108, 111, 44, 32, 87, 111, 114, 108, 100, 33 };
        byte[] result = StringUtils.getBytesUtf8(input);
        Assert.assertArrayEquals(expected, result);
    }
}